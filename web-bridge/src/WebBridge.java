import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;
import java.io.*;
import java.net.InetSocketAddress;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;
import java.util.UUID;

public class WebBridge {

    private static final int PORT = 8080;
    private static final String DB_URL = "jdbc:mysql://localhost:3306/soltec";
    private static final String DB_USER = "root";
    private static final String DB_PASS = "admin";

    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(PORT), 0);
        server.createContext("/api/products", new ProductsHandler());
        server.createContext("/api/sales", new SalesHandler());
        server.createContext("/api/health", new HealthHandler());
        server.createContext("/", new RootHandler());
        server.setExecutor(null);
        System.out.println("Web Bridge started on port " + PORT);
        System.out.println("Open in browser: http://localhost:" + PORT);
        server.start();
    }

    static class HealthHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange t) throws IOException {
            sendResponse(t, "{\"status\":\"UP\"}", "application/json");
        }
    }

    static class RootHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange t) throws IOException {
            File file = new File("web-bridge/src/index.html");
            if (file.exists()) {
                byte[] bytes = Files.readAllBytes(Paths.get(file.getPath()));
                t.getResponseHeaders().set("Content-Type", "text/html");
                t.sendResponseHeaders(200, bytes.length);
                OutputStream os = t.getResponseBody();
                os.write(bytes);
                os.close();
            } else {
                sendResponse(t, "index.html not found", "text/plain", 404);
            }
        }
    }

    static class ProductsHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange t) throws IOException {
            StringBuilder json = new StringBuilder("[");
            try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS)) {
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT ID, REFERENCE, NAME, PRICEBUY, PRICESELL FROM PRODUCTS LIMIT 100");
                boolean first = true;
                while (rs.next()) {
                    if (!first) json.append(",");
                    json.append("{")
                        .append("\"id\":\"").append(rs.getString("ID")).append("\",")
                        .append("\"reference\":\"").append(rs.getString("REFERENCE")).append("\",")
                        .append("\"name\":\"").append(rs.getString("NAME")).append("\",")
                        .append("\"price\":").append(rs.getDouble("PRICESELL"))
                        .append("}");
                    first = false;
                }
            } catch (SQLException e) {
                sendResponse(t, "{\"error\":\"" + e.getMessage() + "\"}", "application/json", 500);
                return;
            }
            json.append("]");
            sendResponse(t, json.toString(), "application/json");
        }
    }

    static class SalesHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange t) throws IOException {
            if ("OPTIONS".equals(t.getRequestMethod())) {
                t.getResponseHeaders().set("Access-Control-Allow-Methods", "POST, OPTIONS");
                t.getResponseHeaders().set("Access-Control-Allow-Headers", "Content-Type");
                sendResponse(t, "", "text/plain");
                return;
            }
            
            if ("POST".equals(t.getRequestMethod())) {
                // Simplified sale recording logic
                // In a real scenario, we'd parse the JSON body and insert into TICKETS, TICKETLINES, and RECEIPTS
                sendResponse(t, "{\"status\":\"Sale recorded (stub)\"}", "application/json");
            } else {
                sendResponse(t, "Method not allowed", "text/plain", 405);
            }
        }
    }

    private static void sendResponse(HttpExchange t, String response, String contentType) throws IOException {
        sendResponse(t, response, contentType, 200);
    }

    private static void sendResponse(HttpExchange t, String response, String contentType, int code) throws IOException {
        t.getResponseHeaders().set("Content-Type", contentType);
        t.getResponseHeaders().set("Access-Control-Allow-Origin", "*");
        t.sendResponseHeaders(code, response.getBytes().length);
        OutputStream os = t.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }
}
