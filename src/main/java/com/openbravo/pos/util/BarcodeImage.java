/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.krysalis.barcode4j.BarcodeDimension
 *  org.krysalis.barcode4j.HumanReadablePlacement
 *  org.krysalis.barcode4j.impl.AbstractBarcodeBean
 *  org.krysalis.barcode4j.impl.codabar.CodabarBean
 *  org.krysalis.barcode4j.impl.code128.Code128Bean
 *  org.krysalis.barcode4j.impl.code39.Code39Bean
 *  org.krysalis.barcode4j.impl.int2of5.Interleaved2Of5Bean
 *  org.krysalis.barcode4j.impl.postnet.POSTNETBean
 *  org.krysalis.barcode4j.impl.upcean.EAN13Bean
 *  org.krysalis.barcode4j.impl.upcean.EAN8Bean
 *  org.krysalis.barcode4j.impl.upcean.UPCABean
 *  org.krysalis.barcode4j.impl.upcean.UPCEBean
 *  org.krysalis.barcode4j.output.CanvasProvider
 *  org.krysalis.barcode4j.output.java2d.Java2DCanvasProvider
 */
package com.openbravo.pos.util;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import org.krysalis.barcode4j.BarcodeDimension;
import org.krysalis.barcode4j.HumanReadablePlacement;
import org.krysalis.barcode4j.impl.AbstractBarcodeBean;
import org.krysalis.barcode4j.impl.codabar.CodabarBean;
import org.krysalis.barcode4j.impl.code128.Code128Bean;
import org.krysalis.barcode4j.impl.code39.Code39Bean;
import org.krysalis.barcode4j.impl.int2of5.Interleaved2Of5Bean;
import org.krysalis.barcode4j.impl.postnet.POSTNETBean;
import org.krysalis.barcode4j.impl.upcean.EAN13Bean;
import org.krysalis.barcode4j.impl.upcean.EAN8Bean;
import org.krysalis.barcode4j.impl.upcean.UPCABean;
import org.krysalis.barcode4j.impl.upcean.UPCEBean;
import org.krysalis.barcode4j.output.CanvasProvider;
import org.krysalis.barcode4j.output.java2d.Java2DCanvasProvider;

public class BarcodeImage {
    public static Image getBarcodeCodabar(String value) {
        CodabarBean barcode = new CodabarBean();
        barcode.setMsgPosition(HumanReadablePlacement.HRP_BOTTOM);
        return BarcodeImage.getBarcode(value, (AbstractBarcodeBean)barcode);
    }

    public static Image getBarcodeCode39(String value) {
        Code39Bean barcode = new Code39Bean();
        barcode.setMsgPosition(HumanReadablePlacement.HRP_BOTTOM);
        return BarcodeImage.getBarcode(value, (AbstractBarcodeBean)barcode);
    }

    public static Image getBarcodeInterleaved2Of5(String value) {
        Interleaved2Of5Bean barcode = new Interleaved2Of5Bean();
        barcode.setMsgPosition(HumanReadablePlacement.HRP_BOTTOM);
        return BarcodeImage.getBarcode(value, (AbstractBarcodeBean)barcode);
    }

    public static Image getBarcodePOSTNET(String value) {
        POSTNETBean barcode = new POSTNETBean();
        barcode.setMsgPosition(HumanReadablePlacement.HRP_BOTTOM);
        return BarcodeImage.getBarcode(value, (AbstractBarcodeBean)barcode);
    }

    public static Image getBarcodeUPCA(String value) {
        UPCABean barcode = new UPCABean();
        barcode.setMsgPosition(HumanReadablePlacement.HRP_BOTTOM);
        return BarcodeImage.getBarcode(value, (AbstractBarcodeBean)barcode);
    }

    public static Image getBarcodeUPCE(String value) {
        UPCEBean barcode = new UPCEBean();
        barcode.setMsgPosition(HumanReadablePlacement.HRP_BOTTOM);
        return BarcodeImage.getBarcode(value, (AbstractBarcodeBean)barcode);
    }

    public static Image getBarcodeEAN13(String value) {
        EAN13Bean barcode = new EAN13Bean();
        barcode.setMsgPosition(HumanReadablePlacement.HRP_BOTTOM);
        return BarcodeImage.getBarcode(value, (AbstractBarcodeBean)barcode);
    }

    public static Image getBarcodeEAN8(String value) {
        EAN8Bean barcode = new EAN8Bean();
        barcode.setMsgPosition(HumanReadablePlacement.HRP_BOTTOM);
        return BarcodeImage.getBarcode(value, (AbstractBarcodeBean)barcode);
    }

    public static Image getBarcode128(String value) {
        Code128Bean barcode = new Code128Bean();
        barcode.setMsgPosition(HumanReadablePlacement.HRP_NONE);
        return BarcodeImage.getBarcode(value, (AbstractBarcodeBean)barcode);
    }

    private static Image getBarcode(String value, AbstractBarcodeBean barcode) {
        barcode.setModuleWidth(1.0);
        barcode.setBarHeight(40.0);
        barcode.setFontSize(10.0);
        barcode.setQuietZone(10.0);
        barcode.doQuietZone(true);
        BarcodeDimension dim = barcode.calcDimensions(value);
        int width = (int)dim.getWidth(0) + 20;
        int height = (int)dim.getHeight(0);
        BufferedImage imgtext = new BufferedImage(width, height, 1);
        Graphics2D g2d = imgtext.createGraphics();
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, width, height);
        g2d.setColor(Color.BLACK);
        try {
            barcode.generateBarcode((CanvasProvider)new Java2DCanvasProvider(g2d, 0), value);
        }
        catch (IllegalArgumentException e) {
            g2d.drawRect(0, 0, width - 1, height - 1);
            g2d.drawString(value, 2, height - 3);
        }
        g2d.dispose();
        return imgtext;
    }
}

