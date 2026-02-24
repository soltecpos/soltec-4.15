/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.api;

import com.openbravo.pos.forms.AppConfig;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.IOException;
import java.io.OutputStream;
import java.util.logging.Logger;

public class ConfigHandler
implements HttpHandler {
    private static final Logger logger = Logger.getLogger("com.openbravo.pos.api.ConfigHandler");

    @Override
    public void handle(HttpExchange t) throws IOException {
        t.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
        if (t.getRequestMethod().equalsIgnoreCase("GET")) {
            try {
                AppConfig config = AppConfig.getInstance();
                StringBuilder json = new StringBuilder();
                json.append("{");
                json.append(this.jsonData("currency_pattern", config.getProperty("format.currency")));
                json.append(",");
                json.append(this.jsonData("percent_pattern", config.getProperty("format.percent")));
                json.append(",");
                json.append(this.jsonData("ticket_header_1", config.getProperty("tkt.header1")));
                json.append(",");
                json.append(this.jsonData("ticket_footer_1", config.getProperty("tkt.footer1")));
                json.append("}");
                this.sendResponse(t, 200, json.toString());
            }
            catch (Exception e) {
                this.sendResponse(t, 500, "{\"error\": \"" + e.getMessage() + "\"}");
            }
        } else {
            this.sendResponse(t, 405, "{\"error\":\"Method Not Allowed\"}");
        }
    }

    private String jsonData(String key, String value) {
        if (value == null) {
            value = "";
        }
        value = value.replace("\"", "\\\"");
        return "\"" + key + "\": \"" + value + "\"";
    }

    private void sendResponse(HttpExchange t, int code, String response) throws IOException {
        t.getResponseHeaders().add("Content-Type", "application/json; charset=UTF-8");
        t.sendResponseHeaders(code, response.getBytes("UTF-8").length);
        OutputStream os = t.getResponseBody();
        os.write(response.getBytes("UTF-8"));
        os.close();
    }
}

