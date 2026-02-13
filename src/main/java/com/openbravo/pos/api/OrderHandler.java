/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.api;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.logging.Logger;

public class OrderHandler
implements HttpHandler {
    private static final Logger logger = Logger.getLogger("OrderHandler");

    @Override
    public void handle(HttpExchange t) throws IOException {
        if ("POST".equals(t.getRequestMethod())) {
            this.handlePost(t);
        } else {
            String response = "{\"error\": \"Method not allowed\"}";
            this.sendResponse(t, 405, response);
        }
    }

    private void handlePost(HttpExchange t) throws IOException {
        String line;
        InputStreamReader isr = new InputStreamReader(t.getRequestBody(), StandardCharsets.UTF_8);
        BufferedReader br = new BufferedReader(isr);
        StringBuilder requestBody = new StringBuilder();
        while ((line = br.readLine()) != null) {
            requestBody.append(line);
        }
        logger.info("Received Order Payload: " + requestBody.toString());
        String response = "{\"success\": true, \"message\": \"Order received via Native API\"}";
        this.sendResponse(t, 200, response);
    }

    private void sendResponse(HttpExchange t, int statusCode, String response) throws IOException {
        t.getResponseHeaders().set("Content-Type", "application/json");
        t.sendResponseHeaders(statusCode, response.length());
        OutputStream os = t.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }
}

