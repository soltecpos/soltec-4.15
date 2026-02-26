import java.io.*;
import java.net.*;

public class Downloader {
    public static void main(String[] args) {
        try {
            URL url = new URL("https://www.webswing.org/download/webswing-22.2.14.zip");
            HttpURLConnection httpCon = (HttpURLConnection) url.openConnection();
            httpCon.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36");
            httpCon.setRequestMethod("GET");
            httpCon.setDoInput(true);
            httpCon.connect();

            int responseCode = httpCon.getResponseCode();
            System.out.println("Response Code: " + responseCode);

            if (responseCode == HttpURLConnection.HTTP_OK) {
                InputStream inputStream = httpCon.getInputStream();
                FileOutputStream outputStream = new FileOutputStream("webswing_java.zip");

                byte[] buffer = new byte[4096];
                int bytesRead = -1;
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }

                outputStream.close();
                inputStream.close();
                System.out.println("File downloaded.");
            } else {
                System.out.println("Server returned non-OK status.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
