/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.printer.escpos;

import com.openbravo.pos.printer.escpos.Codes;

public class CodesEpson
extends Codes {
    private static final byte[] INITSEQUENCE = new byte[0];
    private static final byte[] CHAR_SIZE_0 = new byte[]{29, 33, 0};
    private static final byte[] CHAR_SIZE_1 = new byte[]{29, 33, 1};
    private static final byte[] CHAR_SIZE_2 = new byte[]{29, 33, 48};
    private static final byte[] CHAR_SIZE_3 = new byte[]{29, 33, 49};
    public static final byte[] BOLD_SET = new byte[]{27, 69, 1};
    public static final byte[] BOLD_RESET = new byte[]{27, 69, 0};
    public static final byte[] UNDERLINE_SET = new byte[]{27, 45, 1};
    public static final byte[] UNDERLINE_RESET = new byte[]{27, 45, 0};
    private static final byte[] OPEN_DRAWER = new byte[]{27, 112, 0, 50, -6};
    private static final byte[] PARTIAL_CUT_1 = new byte[]{27, 105};
    private static final byte[] IMAGE_HEADER = new byte[]{29, 118, 48, 3};
    private static final byte[] NEW_LINE = new byte[]{13, 10};
    private static final byte[] IMAGE_LOGO = new byte[]{27, 28, 112, 1, 0};

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
        return PARTIAL_CUT_1;
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
        return 256;
    }

    @Override
    public byte[] getImageLogo() {
        return IMAGE_LOGO;
    }
}

