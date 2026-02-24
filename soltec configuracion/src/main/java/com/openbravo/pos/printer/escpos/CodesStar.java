/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.printer.escpos;

import com.openbravo.pos.printer.DeviceTicket;
import com.openbravo.pos.printer.escpos.Codes;
import com.openbravo.pos.printer.escpos.PrinterWritter;
import java.awt.image.BufferedImage;

public class CodesStar
extends Codes {
    public static final byte[] INITSEQUENCE = new byte[]{27, 122, 1};
    private static final byte[] CHAR_SIZE_0 = new byte[]{27, 105, 0, 0};
    private static final byte[] CHAR_SIZE_1 = new byte[]{27, 105, 1, 0};
    private static final byte[] CHAR_SIZE_2 = new byte[]{27, 105, 0, 1};
    private static final byte[] CHAR_SIZE_3 = new byte[]{27, 105, 1, 1};
    private static final byte[] BOLD_SET = new byte[]{27, 69};
    private static final byte[] BOLD_RESET = new byte[]{27, 70};
    private static final byte[] UNDERLINE_SET = new byte[]{27, 45, 1};
    private static final byte[] UNDERLINE_RESET = new byte[]{27, 45, 0};
    private static final byte[] OPEN_DRAWER = new byte[]{28};
    private static final byte[] PARTIAL_CUT = new byte[]{27, 100, 48};
    private static final byte[] IMAGE_BEGIN = new byte[]{27, 48};
    private static final byte[] IMAGE_END = new byte[]{27, 122, 1};
    private static final byte[] IMAGE_HEADER = new byte[]{27, 75};
    private static final byte[] IMAGE_LOGO = new byte[]{27, 28, 112, 1, 0};
    private static final byte[] NEW_LINE = new byte[]{13, 10};

    @Override
    public byte[] getInitSequence() {
        return INITSEQUENCE;
    }

    @Override
    public byte[] getSize0() {
        return CHAR_SIZE_0;
    }

    @Override
    public byte[] getSize1() {
        return CHAR_SIZE_1;
    }

    @Override
    public byte[] getSize2() {
        return CHAR_SIZE_2;
    }

    @Override
    public byte[] getSize3() {
        return CHAR_SIZE_3;
    }

    @Override
    public byte[] getBoldSet() {
        return BOLD_SET;
    }

    @Override
    public byte[] getBoldReset() {
        return BOLD_RESET;
    }

    @Override
    public byte[] getUnderlineSet() {
        return UNDERLINE_SET;
    }

    @Override
    public byte[] getUnderlineReset() {
        return UNDERLINE_RESET;
    }

    @Override
    public byte[] getOpenDrawer() {
        return OPEN_DRAWER;
    }

    @Override
    public byte[] getCutReceipt() {
        return PARTIAL_CUT;
    }

    @Override
    public byte[] getNewLine() {
        return NEW_LINE;
    }

    @Override
    public byte[] getImageHeader() {
        return IMAGE_HEADER;
    }

    @Override
    public int getImageWidth() {
        return 192;
    }

    @Override
    public byte[] getImageLogo() {
        return IMAGE_LOGO;
    }

    @Override
    public byte[] transImage(BufferedImage image) {
        Codes.CenteredImage centeredimage = new Codes.CenteredImage(image, this.getImageWidth());
        int iWidth = centeredimage.getWidth();
        int iHeight = (centeredimage.getHeight() + 7) / 8;
        byte[] bData = new byte[IMAGE_BEGIN.length + (this.getImageHeader().length + 2 + iWidth + this.getNewLine().length) * iHeight + IMAGE_END.length];
        int index = 0;
        System.arraycopy(IMAGE_BEGIN, 0, bData, index, IMAGE_BEGIN.length);
        index += IMAGE_BEGIN.length;
        for (int i = 0; i < centeredimage.getHeight(); i += 8) {
            System.arraycopy(this.getImageHeader(), 0, bData, index, this.getImageHeader().length);
            index += this.getImageHeader().length;
            bData[index++] = (byte)(iWidth % 256);
            bData[index++] = (byte)(iWidth / 256);
            for (int j = 0; j < centeredimage.getWidth(); ++j) {
                int p = 0;
                for (int d = 0; d < 8; ++d) {
                    p <<= 1;
                    if (!centeredimage.isBlack(j, i + d)) continue;
                    p |= 1;
                }
                bData[index++] = (byte)p;
            }
            System.arraycopy(this.getNewLine(), 0, bData, index, this.getNewLine().length);
            index += this.getNewLine().length;
        }
        System.arraycopy(IMAGE_END, 0, bData, index, IMAGE_END.length);
        index += IMAGE_END.length;
        return bData;
    }

    @Override
    public void printBarcode(PrinterWritter out, String type, String position, String code) {
        if ("EAN13".equals(type)) {
            out.write(new byte[]{27, 29, 97, 1});
            out.write(new byte[]{27, 98, 3});
            if ("none".equals(position)) {
                out.write(new byte[]{1});
            } else {
                out.write(new byte[]{2});
            }
            out.write(new byte[]{2});
            out.write(new byte[]{80});
            out.write(DeviceTicket.transNumber(DeviceTicket.alignBarCode(code, 13).substring(0, 12)));
            out.write(new byte[]{30});
            out.write(new byte[]{27, 29, 97, 0});
        }
    }
}

