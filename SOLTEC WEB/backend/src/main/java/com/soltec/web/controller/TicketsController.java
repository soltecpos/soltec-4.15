package com.soltec.web.controller;

import com.soltec.web.entity.*;
import com.soltec.web.repository.*;
import com.openbravo.pos.ticket.TicketInfo;
import com.openbravo.pos.ticket.TicketLineInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/tickets")
public class TicketsController {

    @Autowired private SharedTicketRepository sharedTicketRepository;
    @Autowired private TicketRepository ticketRepository;
    @Autowired private TicketLineRepository ticketLineRepository;
    @Autowired private WebKitchenOrderRepository kitchenOrderRepository;

    @Autowired private ReceiptRepository receiptRepository;
    @Autowired private ProductRepository productRepository;
    @Autowired private PersonRepository personRepository;

    @GetMapping("/open")
    public List<Map<String, Object>> getOpenTickets() {
        return sharedTicketRepository.findAllByOrderByIdAsc().stream().map(st -> {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("id", st.getId());
            map.put("name", st.getName());
            map.put("appuser", st.getAppuser());
            map.put("pickupid", st.getPickupid());
            map.put("locked", st.getLocked());
            return map;
        }).collect(Collectors.toList());
    }

    @GetMapping("/closed")
    public List<Map<String, Object>> getClosedTickets(
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date date) {
        Date from, to;
        if (date != null) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            cal.set(Calendar.HOUR_OF_DAY, 0);
            cal.set(Calendar.MINUTE, 0);
            cal.set(Calendar.SECOND, 0);
            from = cal.getTime();
            cal.set(Calendar.HOUR_OF_DAY, 23);
            cal.set(Calendar.MINUTE, 59);
            cal.set(Calendar.SECOND, 59);
            to = cal.getTime();
        } else {
            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.HOUR_OF_DAY, 0);
            cal.set(Calendar.MINUTE, 0);
            cal.set(Calendar.SECOND, 0);
            from = cal.getTime();
            to = new Date();
        }

        List<TicketEntity> tickets = ticketRepository.findByDateRange(from, to);
        return tickets.stream().map(t -> {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("id", t.getId());
            map.put("ticketid", t.getTicketid());
            map.put("tickettype", t.getTickettype());
            map.put("personId", t.getPersonId());
            map.put("customerId", t.getCustomerId());
            map.put("status", t.getStatus());
            if (t.getReceipt() != null) {
                map.put("date", t.getReceipt().getDatenew());
            }
            return map;
        }).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getTicketDetail(@PathVariable String id) {
        // Try shared tickets first
        Optional<SharedTicket> shared = sharedTicketRepository.findById(id);
        if (shared.isPresent()) {
            SharedTicket st = shared.get();
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("id", st.getId());
            map.put("name", st.getName());
            map.put("appuser", st.getAppuser());
            map.put("pickupid", st.getPickupid());
            map.put("locked", st.getLocked());
            map.put("type", "open");

            // Deserialize the BLOB to get the lines
            if (st.getContent() != null) {
                try (ByteArrayInputStream bis = new ByteArrayInputStream(st.getContent());
                     ObjectInputStream ois = new ObjectInputStream(bis)) {
                    TicketInfo ti = (TicketInfo) ois.readObject();
                    List<Map<String, Object>> lines = new ArrayList<>();
                    for (int i = 0; i < ti.getLinesCount(); i++) {
                        TicketLineInfo line = ti.getLine(i);
                        Map<String, Object> lm = new LinkedHashMap<>();
                        lm.put("line", i);
                        lm.put("productName", line.getProductName());
                        lm.put("units", line.getMultiply());
                        lm.put("price", line.getPrice());
                        lm.put("total", line.getMultiply() * line.getPrice());
                        lm.put("sendstatus", line.getProperty("sendstatus"));
                        lm.put("printer", line.getProperty("product.printer"));
                        lines.add(lm);
                    }
                    map.put("lines", lines);
                } catch (Exception e) {
                    System.out.println("Error deserializing blob: " + e.getMessage());
                }
            }

            return ResponseEntity.ok(map);
        }

        // Try closed tickets
        return ticketRepository.findById(id).map(t -> {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("id", t.getId());
            map.put("ticketid", t.getTicketid());
            map.put("tickettype", t.getTickettype());
            map.put("personId", t.getPersonId());
            map.put("status", t.getStatus());
            map.put("type", "closed");

            if (t.getReceipt() != null) {
                map.put("date", t.getReceipt().getDatenew());
            }

            // Get ticket lines
            List<TicketLine> lines = ticketLineRepository.findByTicket(id);
            map.put("lines", lines.stream().map(l -> {
                Map<String, Object> lineMap = new LinkedHashMap<>();
                lineMap.put("line", l.getLine());
                lineMap.put("productId", l.getProductId());
                if (l.getProduct() != null) {
                    lineMap.put("productName", l.getProduct().getName());
                }
                lineMap.put("units", l.getUnits());
                lineMap.put("price", l.getPrice());
                lineMap.put("total", Math.round(l.getUnits() * l.getPrice() * 100.0) / 100.0);
                return lineMap;
            }).collect(Collectors.toList()));

            return ResponseEntity.ok(map);
        }).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/{id}/kitchen")
    public ResponseEntity<?> sendToKitchen(@PathVariable String id) {
        Optional<SharedTicket> shared = sharedTicketRepository.findById(id);
        if (!shared.isPresent()) return ResponseEntity.notFound().build();

        SharedTicket st = shared.get();
        if (st.getContent() == null) return ResponseEntity.badRequest().body("Empty order");

        try (ByteArrayInputStream bis = new ByteArrayInputStream(st.getContent());
             ObjectInputStream ois = new ObjectInputStream(bis)) {
            TicketInfo ti = (TicketInfo) ois.readObject();
            boolean modified = false;

            for (int i = 0; i < ti.getLinesCount(); i++) {
                TicketLineInfo line = ti.getLine(i);
                String p = line.getProperty("product.printer");
                String s = line.getProperty("sendstatus");

                // If it has a printer and hasn't been sent yet
                if (p != null && (s == null || !s.equals("Yes"))) {
                    // Send it to the web_kitchen_orders table
                    WebKitchenOrder kord = new WebKitchenOrder();
                    kord.setId(UUID.randomUUID().toString());
                    kord.setTicketId(ti.getId());
                    kord.setTableName(st.getName());
                    kord.setWaiterName(st.getAppuser());
                    kord.setProductName(line.getProductName());
                    kord.setMultiplier(line.getMultiply());
                    kord.setStatus("PENDING");
                    kord.setCreatedAt(new Timestamp(System.currentTimeMillis()));
                    
                    kitchenOrderRepository.save(kord);

                    // Mark as sent
                    line.setProperty("sendstatus", "Yes");
                    line.getProperties().remove("product.sent_status");
                    modified = true;
                }
            }

            if (modified) {
                // Serialize it back and save to DB
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                ObjectOutputStream oos = new ObjectOutputStream(bos);
                oos.writeObject(ti);
                oos.flush();
                st.setContent(bos.toByteArray());
                sharedTicketRepository.save(st);
            }

            return ResponseEntity.ok(Collections.singletonMap("success", true));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("Deserialization error: " + e.getMessage());
        }
    }
}
