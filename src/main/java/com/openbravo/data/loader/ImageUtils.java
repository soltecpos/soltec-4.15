/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.data.loader;

import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Properties;
import javax.imageio.ImageIO;

public class ImageUtils {
    private static char[] HEXCHARS = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

    private ImageUtils() {
    }

    private static byte[] readStream(InputStream in) throws IOException {
        int n;
        byte[] buffer = new byte[1024];
        byte[] resource = new byte[]{};
        while ((n = in.read(buffer)) != -1) {
            byte[] b = new byte[resource.length + n];
            System.arraycopy(resource, 0, b, 0, resource.length);
            System.arraycopy(buffer, 0, b, resource.length, n);
            resource = b;
        }
        return resource;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static byte[] getBytesFromResource(String file) {
        InputStream in = ImageUtils.class.getResourceAsStream(file);
        if (in == null) {
            return null;
        }
        try {
            byte[] byArray = ImageUtils.readStream(in);
            return byArray;
        }
        catch (IOException e) {
            byte[] byArray = new byte[]{};
            return byArray;
        }
        finally {
            try {
                in.close();
            }
            catch (IOException iOException) {}
        }
    }

    public static BufferedImage readImageFromResource(String file) {
        return ImageUtils.readImage(ImageUtils.getBytesFromResource(file));
    }

    public static BufferedImage readImage(String url) {
        try {
            return ImageUtils.readImage(new URL(url));
        }
        catch (MalformedURLException e) {
            return null;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static BufferedImage readImage(URL url) {
        InputStream in = null;
        try {
            URLConnection urlConnection = url.openConnection();
            in = urlConnection.getInputStream();
            BufferedImage bufferedImage = ImageUtils.readImage(ImageUtils.readStream(in));
            return bufferedImage;
        }
        catch (IOException e) {
            BufferedImage bufferedImage = null;
            return bufferedImage;
        }
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            }
            catch (IOException iOException) {}
        }
    }

    public static BufferedImage readImage(byte[] b) {
        if (b == null) {
            return null;
        }
        try {
            return ImageIO.read(new ByteArrayInputStream(b));
        }
        catch (IOException e) {
            return null;
        }
    }

    public static byte[] writeImage(BufferedImage img) {
        if (img == null) {
            return null;
        }
        try {
            ByteArrayOutputStream b = new ByteArrayOutputStream();
            ImageIO.write((RenderedImage)img, "png", b);
            b.flush();
            b.close();
            return b.toByteArray();
        }
        catch (IOException e) {
            return null;
        }
    }

    public static Object readSerializable(byte[] b) {
        if (b == null) {
            return null;
        }
        try {
            ObjectInputStream in = new ObjectInputStream(new ByteArrayInputStream(b));
            Object obj = in.readObject();
            in.close();
            return obj;
        }
        catch (ClassNotFoundException eCNF) {
            return null;
        }
        catch (IOException eIO) {
            return null;
        }
    }

    public static byte[] writeSerializable(Object o) {
        if (o == null) {
            return null;
        }
        try {
            ByteArrayOutputStream b = new ByteArrayOutputStream();
            ObjectOutputStream out = new ObjectOutputStream(b);
            out.writeObject(o);
            out.flush();
            out.close();
            return b.toByteArray();
        }
        catch (IOException eIO) {
            eIO.printStackTrace();
            return null;
        }
    }

    public static Properties readProperties(byte[] b) {
        Properties prop = new Properties();
        try {
            if (b != null) {
                prop.loadFromXML(new ByteArrayInputStream(b));
            }
        }
        catch (IOException iOException) {
            // empty catch block
        }
        return prop;
    }

    public static String bytes2hex(byte[] binput) {
        StringBuilder s = new StringBuilder(binput.length * 2);
        for (int i = 0; i < binput.length; ++i) {
            byte b = binput[i];
            s.append(HEXCHARS[(b & 0xF0) >> 4]);
            s.append(HEXCHARS[b & 0xF]);
        }
        return s.toString();
    }
}

