/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.printer.escpos;

import com.openbravo.pos.printer.DeviceTicket;
import com.openbravo.pos.printer.escpos.ESCPOS;
import com.openbravo.pos.printer.escpos.PrinterWritter;
import java.awt.image.BufferedImage;

public abstract class Codes {
    public abstract byte[] getInitSequence();

    public abstract byte[] getSize0();

    public abstract byte[] getSize1();

    public abstract byte[] getSize2();

    public abstract byte[] getSize3();

    public abstract byte[] getBoldSet();

    public abstract byte[] getBoldReset();

    public abstract byte[] getUnderlineSet();

    public abstract byte[] getUnderlineReset();

    public abstract byte[] getOpenDrawer();

    public abstract byte[] getCutReceipt();

    public abstract byte[] getNewLine();

    public abstract byte[] getImageHeader();

    public abstract int getImageWidth();

    public abstract byte[] getImageLogo();

    public void printBarcode(PrinterWritter out, String type, String position, String code) {
        if ("EAN13".equals(type)) {
            out.write(this.getNewLine());
            out.write(ESCPOS.BAR_HEIGHT);
            if ("none".equals(position)) {
                out.write(ESCPOS.BAR_POSITIONNONE);
            } else {
                out.write(ESCPOS.BAR_POSITIONDOWN);
            }
            out.write(ESCPOS.BAR_HRIFONT1);
            out.write(ESCPOS.BAR_CODE02);
            out.write(DeviceTicket.transNumber(DeviceTicket.alignBarCode(code, 13).substring(0, 12)));
            out.write(new byte[]{0});
            out.write(this.getNewLine());
        }
    }

    public byte[] transImage(BufferedImage image) {
        CenteredImage centeredimage = new CenteredImage(image, this.getImageWidth());
        int iWidth = (centeredimage.getWidth() + 7) / 8;
        int iHeight = centeredimage.getHeight();
        byte[] bData = new byte[this.getImageHeader().length + 4 + iWidth * iHeight];
        System.arraycopy(this.getImageHeader(), 0, bData, 0, this.getImageHeader().length);
        int index = this.getImageHeader().length;
        bData[index++] = (byte)(iWidth % 256);
        bData[index++] = (byte)(iWidth / 256);
        bData[index++] = (byte)(iHeight % 256);
        bData[index++] = (byte)(iHeight / 256);
        for (int i = 0; i < centeredimage.getHeight(); ++i) {
            for (int j = 0; j < centeredimage.getWidth(); j += 8) {
                int p = 0;
                for (int d = 0; d < 8; ++d) {
                    p <<= 1;
                    if (!centeredimage.isBlack(j + d, i)) continue;
                    p |= 1;
                }
                bData[index++] = (byte)p;
            }
        }
        return bData;
    }

    protected class CenteredImage {
        private BufferedImage image;
        private int width;

        public CenteredImage(BufferedImage image, int width) {
            this.image = image;
            this.width = width;
        }

        public int getHeight() {
            return this.image.getHeight();
        }

        public int getWidth() {
            return this.width;
        }

        public boolean isBlack(int x, int y) {
            int centeredx = x + (this.image.getWidth() - this.width) / 2;
            if (centeredx < 0 || centeredx >= this.image.getWidth() || y < 0 || y >= this.image.getHeight()) {
                return false;
            }
            int rgb = this.image.getRGB(centeredx, y);
            int gray = (int)(0.3 * (double)(rgb >> 16 & 0xFF) + 0.59 * (double)(rgb >> 8 & 0xFF) + 0.11 * (double)(rgb & 0xFF));
            return gray < 128;
        }
    }
}

