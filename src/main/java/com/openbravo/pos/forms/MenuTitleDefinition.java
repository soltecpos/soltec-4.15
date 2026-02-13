/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.forms;

import com.openbravo.pos.forms.AppLocal;
import com.openbravo.pos.forms.JPanelMenu;
import com.openbravo.pos.forms.MenuElement;
import java.awt.Color;
import java.awt.Insets;
import javax.swing.JLabel;
import javax.swing.border.MatteBorder;

public class MenuTitleDefinition
implements MenuElement {
    public String KeyText;

    @Override
    public void addComponent(JPanelMenu menu) {
        JLabel lbl = new JLabel(AppLocal.getIntString(this.KeyText));
        lbl.applyComponentOrientation(menu.getComponentOrientation());
        lbl.setBorder(new MatteBorder(new Insets(0, 0, 1, 0), new Color(0, 0, 0)));
        menu.addTitle(lbl);
    }
}

