package com.soltec.web.controller;

import com.soltec.web.service.ReportsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/reports")
public class ReportsController {

    @Autowired
    private ReportsService reportsService;

    @Autowired
    private com.soltec.web.repository.ClosedCashRepository closedCashRepository;

    @GetMapping("/sales/today")
    public Map<String, Object> salesToday() {
        return reportsService.getSalesToday();
    }

    @GetMapping("/sales/range")
    public Map<String, Object> salesByRange(
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date from,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date to) {
        // Set end of day for 'to' date
        Calendar cal = Calendar.getInstance();
        cal.setTime(to);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        return reportsService.getSalesForRange(from, cal.getTime());
    }

    @GetMapping("/products/top")
    public List<Map<String, Object>> topProducts(
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date from,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date to,
            @RequestParam(defaultValue = "10") int limit) {
        if (from == null) {
            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.HOUR_OF_DAY, 0);
            cal.set(Calendar.MINUTE, 0);
            cal.set(Calendar.SECOND, 0);
            from = cal.getTime();
        }
        if (to == null) {
            to = new Date();
        }
        return reportsService.getTopProducts(from, to, limit);
    }

    @GetMapping("/cash/current")
    public Map<String, Object> currentCash() {
        return reportsService.getCurrentCash();
    }

    @GetMapping("/cash/history")
    public List<Map<String, Object>> cashHistory(
            @RequestParam(defaultValue = "20") int limit) {
        return reportsService.getCashHistory(limit);
    }
    
    @GetMapping("/dump-tickets")
    public List<Map<String, Object>> dumpTickets() {
        List<Map<String, Object>> results = new ArrayList<>();
        try (java.sql.Connection c = java.sql.DriverManager.getConnection("jdbc:mysql://100.112.128.16:3306/soltec?useSSL=false&serverTimezone=UTC", "admin", "root");
             java.sql.Statement s = c.createStatement();
             java.sql.ResultSet rs = s.executeQuery("SELECT r.datenew, p.total FROM payments p JOIN receipts r ON p.receipt = r.id WHERE r.money = '33d689a8-7ba0-40da-ad2d-1a2ba155486e' ORDER BY r.datenew")) {
            while (rs.next()) {
                Map<String, Object> row = new LinkedHashMap<>();
                row.put("date", rs.getTimestamp("datenew"));
                row.put("total", rs.getDouble("total"));
                results.add(row);
            }
        } catch (Exception e) {
            Map<String, Object> err = new LinkedHashMap<>();
            err.put("error", e.getMessage());
            results.add(err);
        }
        return results;
    }

    @PostMapping("/cash/close")
    public org.springframework.http.ResponseEntity<?> closeCash() {
        java.util.List<com.soltec.web.entity.ClosedCash> openSessions = closedCashRepository.findOpenCashSessions();
        if (openSessions.isEmpty()) {
            return org.springframework.http.ResponseEntity.badRequest().body("No open cash session found.");
        }

        com.soltec.web.entity.ClosedCash current = openSessions.get(0);
        current.setDateend(new java.util.Date());
        closedCashRepository.save(current);

        com.soltec.web.entity.ClosedCash nextSession = new com.soltec.web.entity.ClosedCash();
        nextSession.setMoney(java.util.UUID.randomUUID().toString());
        nextSession.setHost(current.getHost());
        nextSession.setHostsequence(current.getHostsequence() + 1);
        nextSession.setDatestart(new java.util.Date());
        nextSession.setNosales(0);
        closedCashRepository.save(nextSession);

        return org.springframework.http.ResponseEntity.ok(java.util.Collections.singletonMap("success", true));
    }
}
