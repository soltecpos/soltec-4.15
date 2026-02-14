
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import java.awt.Image;
import java.net.URL;
import java.io.InputStream;

public class LogoTest {
    public static void main(String[] args) {
        System.out.println("Iniciando diagnostico de imagenes...");
        testImage("/com/openbravo/images/yast_printer.png");
        testImage("/com/openbravo/images/printer.png");
    }

    private static void testImage(String path) {
        System.out.println("--------------------------------------------------");
        System.out.println("Probando: " + path);
        try {
            URL url = LogoTest.class.getResource(path);
            if (url == null) {
                System.out.println("ERROR: Recurso no encontrado (URL es null).");
                return;
            }
            System.out.println("URL encontrada: " + url);
            
            // Intento 1: ImageIO
            try {
                InputStream is = LogoTest.class.getResourceAsStream(path);
                if (is == null) {
                     System.out.println("ERROR: InputStream es null.");
                } else {
                    java.awt.image.BufferedImage bi = ImageIO.read(is);
                    if (bi == null) {
                        System.out.println("FALLO ImageIO.read(): Retorno null (Formato no soportado o corrupto).");
                    } else {
                        System.out.println("EXITO ImageIO: Dimensiones " + bi.getWidth() + "x" + bi.getHeight());
                    }
                }
            } catch (Exception e) {
                System.out.println("EXCEPCION ImageIO: " + e.getMessage());
                e.printStackTrace();
            }

            // Intento 2: ImageIcon (AWT Toolkit)
            try {
                ImageIcon icon = new ImageIcon(url);
                if (icon.getImageLoadStatus() == java.awt.MediaTracker.COMPLETE) {
                     System.out.println("EXITO ImageIcon: Dimensiones " + icon.getIconWidth() + "x" + icon.getIconHeight());
                } else {
                     System.out.println("FALLO ImageIcon: Status " + icon.getImageLoadStatus());
                }
            } catch (Exception e) {
                 System.out.println("EXCEPCION ImageIcon: " + e.getMessage());
            }

        } catch (Exception e) {
            System.out.println("ERROR GENERAL: " + e.getMessage());
        }
    }
}
