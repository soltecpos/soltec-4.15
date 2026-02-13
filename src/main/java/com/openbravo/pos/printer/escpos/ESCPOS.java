/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.printer.escpos;

public class ESCPOS {
    public static final byte[] INIT = new byte[]{27, 64};
    public static final byte[] SELECT_PRINTER = new byte[]{27, 61, 1};
    public static final byte[] SELECT_DISPLAY = new byte[]{27, 61, 2};
    public static final byte[] HT = new byte[]{9};
    public static final byte[] FF = new byte[]{12};
    public static final byte[] CHAR_FONT_0 = new byte[]{27, 77, 0};
    public static final byte[] CHAR_FONT_1 = new byte[]{27, 77, 1};
    public static final byte[] CHAR_FONT_2 = new byte[]{27, 77, 48};
    public static final byte[] CHAR_FONT_3 = new byte[]{27, 77, 49};
    public static final byte[] BAR_HEIGHT = new byte[]{29, 104, 64};
    public static final byte[] BAR_POSITIONDOWN = new byte[]{29, 72, 2};
    public static final byte[] BAR_POSITIONNONE = new byte[]{29, 72, 0};
    public static final byte[] BAR_HRIFONT1 = new byte[]{29, 102, 1};
    public static final byte[] BAR_CODE02 = new byte[]{29, 107, 2};
    public static final byte[] VISOR_HIDE_CURSOR = new byte[]{31, 67, 0};
    public static final byte[] VISOR_SHOW_CURSOR = new byte[]{31, 67, 1};
    public static final byte[] VISOR_HOME = new byte[]{11};
    public static final byte[] VISOR_CLEAR = new byte[]{12};
    public static final byte[] CODE_TABLE_00 = new byte[]{27, 116, 0};
    public static final byte[] CODE_TABLE_13 = new byte[]{27, 116, 19};

    private ESCPOS() {
    }
}

