/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.forms;

import com.openbravo.pos.forms.JPanelMenu;
import com.openbravo.pos.forms.MenuElement;
import java.awt.Dimension;
import javax.swing.Action;
import javax.swing.JButton;

public class MenuItemDefinition
implements MenuElement {
    private Action act;

    public MenuItemDefinition(Action act) {
        this.act = act;
    }

    @Override
    public void addComponent(JPanelMenu menu) {
        JButton btn = new JButton(this.act);
        
        // Fix for truncated text: Use HTML to allow wrapping
        String text = (String) this.act.getValue(Action.NAME);
        if (text != null) {
            btn.setText("<html><center>" + text + "</center></html>");
        }

        btn.setFocusPainted(false);
        btn.setFocusable(false);
        btn.setRequestFocusEnabled(false);
        btn.setHorizontalAlignment(10);
        btn.setPreferredSize(new Dimension(100, 100)); // Consider increasing if needed, but HTML check first
        menu.addEntry(btn);
    }
}

