package com.soltec.web.service;

import com.soltec.web.entity.*;
import com.soltec.web.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ReportsService {

    @Autowired private ReceiptRepository receiptRepository;
    @Autowired private TicketRepository ticketRepository;
    @Autowired private TicketLineRepository ticketLineRepository;
    @Autowired private PaymentRepository paymentRepository;
    @Autowired private ClosedCashRepository closedCashRepository;
    @Autowired private ProductRepository productRepository;

    public Map<String, Object> getSalesToday() {
        List<ClosedCash> openSessions = closedCashRepository.findOpenCashSessions();
        if (openSessions.isEmpty()) {
            return calculateSalesData(Collections.emptyList(), new Date(), new Date());
        }
        Date minStart = openSessions.stream()
            .map(ClosedCash::getDatestart)
            .min(Date::compareTo)
            .orElseGet(() -> openSessions.get(0).getDatestart());
        List<String> moneyIds = openSessions.stream().map(ClosedCash::getMoney).collect(Collectors.toList());
        List<Receipt> activeReceipts = receiptRepository.findByMoneyIn(moneyIds);

        Date minDate = activeReceipts.stream()
            .map(Receipt::getDatenew)
            .min(Date::compareTo)
            .orElse(minStart);
        Date maxDate = new Date();
        return calculateSalesData(activeReceipts, minDate, maxDate);
    }

    public Map<String, Object> getSalesForRange(Date from, Date to) {
        List<Receipt> receipts = receiptRepository.findByDateRange(from, to);
        return calculateSalesData(receipts, from, to);
    }

    private Map<String, Object> calculateSalesData(List<Receipt> receipts, Date from, Date to) {
        List<String> receiptIds = receipts.stream().map(Receipt::getId).collect(Collectors.toList());

        double totalSales = 0;
        int totalTickets = 0;
        Map<String, Object> paymentSummary = new LinkedHashMap<>();

        if (!receiptIds.isEmpty()) {
            totalTickets = (int) ticketRepository.countByReceiptIdInAndTickettype(receiptIds, 0);
            List<Object[]> paymentData = paymentRepository.summarizeByPaymentType(receiptIds);
            for (Object[] row : paymentData) {
                String type = (String) row[0];
                Double sum = ((Number) row[1]).doubleValue();
                Long count = ((Number) row[2]).longValue();
                totalSales += sum;
                Map<String, Object> detail = new LinkedHashMap<>();
                detail.put("total", Math.round(sum * 100.0) / 100.0);
                detail.put("count", count);
                paymentSummary.put(type, detail);
            }
        }

        Map<String, Object> result = new LinkedHashMap<>();
        result.put("totalSales", Math.round(totalSales * 100.0) / 100.0);
        result.put("totalTickets", totalTickets);
        result.put("paymentBreakdown", paymentSummary);
        result.put("from", from);
        result.put("to", to);
        return result;
    }

    public List<Map<String, Object>> getTopProducts(Date from, Date to, int limit) {
        List<Receipt> receipts = receiptRepository.findByDateRange(from, to);
        List<String> receiptIds = receipts.stream().map(Receipt::getId).collect(Collectors.toList());

        if (receiptIds.isEmpty()) return Collections.emptyList();

        List<Object[]> topData = ticketLineRepository.findTopProductsByTickets(receiptIds);
        List<Map<String, Object>> result = new ArrayList<>();

        Map<String, Product> productCache = new HashMap<>();

        int count = 0;
        for (Object[] row : topData) {
            if (count >= limit) break;
            String productId = (String) row[0];
            double totalUnits = ((Number) row[1]).doubleValue();
            double totalSales = ((Number) row[2]).doubleValue();

            Product product = productCache.computeIfAbsent(productId,
                id -> productRepository.findById(id).orElse(null));

            Map<String, Object> item = new LinkedHashMap<>();
            item.put("productId", productId);
            item.put("productName", product != null ? product.getName() : "Desconocido");
            item.put("totalUnits", totalUnits);
            item.put("totalSales", Math.round(totalSales * 100.0) / 100.0);
            result.add(item);
            count++;
        }
        return result;
    }

    public Map<String, Object> getCurrentCash() {
        List<ClosedCash> openSessions = closedCashRepository.findOpenCashSessions();
        Map<String, Object> result = new LinkedHashMap<>();

        if (openSessions.isEmpty()) {
            result.put("status", "CLOSED");
            result.put("message", "No hay caja abierta");
            return result;
        }

        Date minStart = openSessions.stream()
            .map(ClosedCash::getDatestart)
            .min(Date::compareTo)
            .orElseGet(() -> openSessions.get(0).getDatestart());

        result.put("status", "OPEN");
        result.put("sessionId", "multiple");
        result.put("host", "all_open");
        result.put("openedAt", minStart);

        // Get sales for this session based on exact closedcash money ids
        List<String> moneyIds = openSessions.stream().map(ClosedCash::getMoney).collect(Collectors.toList());
        List<Receipt> receipts = receiptRepository.findByMoneyIn(moneyIds);
        List<String> receiptIds = receipts.stream().map(Receipt::getId).collect(Collectors.toList());
        double total = 0;
        long ticketCount = 0;
        if (!receiptIds.isEmpty()) {
            ticketCount = ticketRepository.countByReceiptIdInAndTickettype(receiptIds, 0);
            List<Object[]> payments = paymentRepository.summarizeByPaymentType(receiptIds);
            for (Object[] row : payments) {
                total += ((Number) row[1]).doubleValue();
            }
        }
        result.put("totalSales", Math.round(total * 100.0) / 100.0);
        result.put("ticketCount", ticketCount);

        return result;
    }

    public List<Map<String, Object>> getCashHistory(int limit) {
        List<ClosedCash> all = closedCashRepository.findAllOrderByDateDesc();
        List<Map<String, Object>> result = new ArrayList<>();

        for (int i = 0; i < Math.min(limit, all.size()); i++) {
            ClosedCash cc = all.get(i);
            Map<String, Object> item = new LinkedHashMap<>();
            item.put("sessionId", cc.getMoney());
            item.put("host", cc.getHost());
            item.put("sequence", cc.getHostsequence());
            item.put("openedAt", cc.getDatestart());
            item.put("closedAt", cc.getDateend());
            item.put("status", cc.getDateend() != null ? "CLOSED" : "OPEN");
            result.add(item);
        }
        return result;
    }

    private Date getStartOfDay(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    private Date getEndOfDay(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MILLISECOND, 999);
        return cal.getTime();
    }
}
