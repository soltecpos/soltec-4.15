/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.Gson
 */
package com.openbravo.pos.api;

import com.google.gson.Gson;
import com.openbravo.pos.forms.AppConfig;
import com.openbravo.pos.ticket.TaxInfo;
import com.openbravo.pos.ticket.TicketInfo;
import com.openbravo.pos.ticket.TicketLineInfo;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TicketHandler
implements HttpHandler {
    private static final Logger logger = Logger.getLogger("com.openbravo.pos.api.TicketHandler");

    @Override
    public void handle(HttpExchange t) throws IOException {
        t.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
        t.getResponseHeaders().add("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
        t.getResponseHeaders().add("Access-Control-Allow-Headers", "Content-Type,Authorization");
        if (t.getRequestMethod().equalsIgnoreCase("OPTIONS")) {
            t.sendResponseHeaders(204, -1L);
            return;
        }
        if (t.getRequestMethod().equalsIgnoreCase("POST")) {
            this.handlePost(t);
        } else if (t.getRequestMethod().equalsIgnoreCase("GET")) {
            this.handleGet(t);
        } else {
            this.sendResponse(t, 405, "{\"error\":\"Method Not Allowed\"}");
        }
    }

    private void handlePost(HttpExchange t) throws IOException {
        try {
            String body = this.readBody(t.getRequestBody());
            Gson gson = new Gson();
            Map payload = (Map)gson.fromJson(body, Map.class);
            TicketInfo ticket = this.createTicketFromPayload(payload);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream out = new ObjectOutputStream(bos);
            out.writeObject(ticket);
            out.close();
            byte[] content = bos.toByteArray();
            this.insertSharedTicket(ticket, content, payload);
            this.sendResponse(t, 200, "{\"status\":\"created\", \"id\":\"" + ticket.getId() + "\"}");
        }
        catch (Exception e) {
            logger.log(Level.SEVERE, "Error creating ticket", e);
            this.sendResponse(t, 500, "{\"error\":\"" + e.getMessage() + "\"}");
        }
    }

    private void handleGet(HttpExchange t) throws IOException {
        this.sendResponse(t, 200, "[]");
    }

    private TicketInfo createTicketFromPayload(Map<String, Object> payload) throws Exception {
        TicketInfo t = new TicketInfo();
        t.setTicketType(0);
        String id = (String)payload.get("id");
        if (id == null) {
            id = UUID.randomUUID().toString();
        }
        try {
            Field idField = TicketInfo.class.getDeclaredField("m_sId");
            idField.setAccessible(true);
            idField.set(t, id);
        }
        catch (Exception idField) {
            // empty catch block
        }
        List items = (List)payload.get("items");
        if (items != null) {
            for (Object obj : items) {
                Map item = (Map) obj;
                String productId = (String) item.getOrDefault("id", "0");
                String name = (String) item.getOrDefault("name", "Item");
                double price = ((Number)item.getOrDefault("price", 0.0)).doubleValue();
                double units = ((Number)item.getOrDefault("units", 1.0)).doubleValue();
                double taxRate = ((Number)item.getOrDefault("taxRate", 0.0)).doubleValue();
                TaxInfo tax = new TaxInfo("001", "Tax", "000", null, null, taxRate, false, 10);
                TicketLineInfo line = new TicketLineInfo(productId, name, "000", units, price, tax);
                try {
                    Field pidField = TicketLineInfo.class.getDeclaredField("productid");
                    pidField.setAccessible(true);
                    pidField.set(line, productId);
                }
                catch (Exception exception) {
                    // empty catch block
                }
                t.addLine(line);
            }
        }
        return t;
    }

    private void insertSharedTicket(TicketInfo t, byte[] content, Map<String, Object> payload) throws Exception {
        AppConfig config = AppConfig.getInstance();
        String url = config.getProperty("db.URL");
        String user = config.getProperty("db.user");
        String pass = config.getProperty("db.password");
        Connection conn = DriverManager.getConnection(url, user, pass);
        String sql = "INSERT INTO sharedtickets (id, name, appuser, content) VALUES (?, ?, ?, ?)";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, t.getId());
        pstmt.setString(2, (String)payload.getOrDefault("name", "Web Ticket"));
        pstmt.setString(3, (String)payload.getOrDefault("appuser", "1"));
        pstmt.setBytes(4, content);
        pstmt.executeUpdate();
        conn.close();
    }

    private String readBody(InputStream is) throws IOException {
        int nRead;
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        byte[] data = new byte[1024];
        while ((nRead = is.read(data, 0, data.length)) != -1) {
            buffer.write(data, 0, nRead);
        }
        buffer.flush();
        return new String(buffer.toByteArray(), "UTF-8");
    }

    private void sendResponse(HttpExchange t, int code, String response) throws IOException {
        t.getResponseHeaders().add("Content-Type", "application/json");
        t.sendResponseHeaders(code, response.length());
        OutputStream os = t.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }
}

