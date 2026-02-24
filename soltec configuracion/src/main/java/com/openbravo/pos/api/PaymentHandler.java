/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.api;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.IOException;
import java.io.OutputStream;
import java.util.logging.Logger;

public class PaymentHandler
implements HttpHandler {
    private static final Logger logger = Logger.getLogger("com.openbravo.pos.api.PaymentHandler");

    @Override
    public void handle(HttpExchange t) throws IOException {
        t.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
        if (t.getRequestMethod().equalsIgnoreCase("GET")) {
            String json = "[{\"id\":\"cash\", \"name\":\"Efectivo\", \"type\":\"cash\", \"active\":1},{\"id\":\"magcard\", \"name\":\"Tarjeta\", \"type\":\"card\", \"active\":1},{\"id\":\"free\", \"name\":\"Gratis\", \"type\":\"free\", \"active\":1}]";
            this.sendResponse(t, 200, json);
        } else {
            this.sendResponse(t, 405, "{\"error\":\"Method Not Allowed\"}");
        }
    }

    private void sendResponse(HttpExchange t, int code, String response) throws IOException {
        t.getResponseHeaders().add("Content-Type", "application/json; charset=UTF-8");
        t.sendResponseHeaders(code, response.getBytes("UTF-8").length);
        OutputStream os = t.getResponseBody();
        os.write(response.getBytes("UTF-8"));
        os.close();
    }
}

