/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.api;

import com.openbravo.pos.api.ConfigHandler;
import com.openbravo.pos.api.PaymentHandler;
import com.openbravo.pos.api.TicketHandler;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.logging.Level;
import java.util.logging.Logger;

public class APIServer {
    private static final Logger logger = Logger.getLogger("com.openbravo.pos.api.APIServer");
    private HttpServer server;
    private final int port;

    public APIServer(int port) {
        this.port = port;
    }

    public void start() {
        try {
            this.server = HttpServer.create(new InetSocketAddress(this.port), 0);
            this.server.createContext("/api/bridge/tickets", new TicketHandler());
            this.server.createContext("/api/system/config", new ConfigHandler());
            this.server.createContext("/api/sales/payment-methods", new PaymentHandler());
            this.server.createContext("/api/system/ping", new PingHandler());
            this.server.setExecutor(null);
            this.server.start();
            logger.log(Level.INFO, "SOLTEC API Server started on port {0}", this.port);
        }
        catch (IOException e) {
            logger.log(Level.SEVERE, "Could not start API Server", e);
        }
    }

    public void stop() {
        if (this.server != null) {
            this.server.stop(0);
            logger.info("SOLTEC API Server stopped");
        }
    }

    static class PingHandler
    implements HttpHandler {
        PingHandler() {
        }

        @Override
        public void handle(HttpExchange t) throws IOException {
            String response = "{\"status\":\"ok\",\"system\":\"SOLTEC POS\"}";
            t.getResponseHeaders().add("Content-Type", "application/json");
            t.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
            t.sendResponseHeaders(200, response.length());
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }
}

