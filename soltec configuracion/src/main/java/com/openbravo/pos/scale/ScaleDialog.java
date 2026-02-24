/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.scale;

import com.openbravo.beans.JNumberDialog;
import com.openbravo.pos.forms.AppLocal;
import com.openbravo.pos.scale.Scale;
import com.openbravo.pos.scale.ScaleException;
import java.awt.Component;
import javax.swing.ImageIcon;

public class ScaleDialog
implements Scale {
    private Component parent;

    public ScaleDialog(Component parent) {
        this.parent = parent;
    }

    @Override
    public Double readWeight() throws ScaleException {
        return JNumberDialog.showEditNumber(this.parent, AppLocal.getIntString("label.scale"), AppLocal.getIntString("label.scaleinput"), new ImageIcon(ScaleDialog.class.getResource("/com/openbravo/images/ark2.png")));
    }
}

