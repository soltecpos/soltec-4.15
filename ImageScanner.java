
import javax.imageio.ImageIO;
import java.io.File;
import java.awt.image.BufferedImage;

public class ImageScanner {
    public static void main(String[] args) {
        String dirPath = "c:\\Users\\aux1\\.gemini\\antigravity\\brain\\0ef25e6f-c481-43ed-a125-392f3a4202d7";
        File dir = new File(dirPath);
        
        if (!dir.exists() || !dir.isDirectory()) {
            System.out.println("Directorio no encontrado: " + dirPath);
            return;
        }

        System.out.println("Escaneando imagenes en: " + dirPath);
        File[] files = dir.listFiles((d, name) -> name.startsWith("uploaded_media") && name.endsWith(".img"));

        if (files == null) {
            System.out.println("No se encontraron archivos.");
            return;
        }

        for (File f : files) {
            try {
                BufferedImage bi = ImageIO.read(f);
                if (bi != null) {
                    System.out.println("[VALIDA] " + f.getName() + " | Dimensiones: " + bi.getWidth() + "x" + bi.getHeight());
                } else {
                    System.out.println("[INVALIDA - NULL] " + f.getName());
                }
            } catch (Exception e) {
                System.out.println("[INVALIDA - ERROR] " + f.getName() + ": " + e.getMessage());
            }
        }
        System.out.println("Escaneo completado.");
    }
}
