/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.forms;

import com.openbravo.basic.BasicException;
import com.openbravo.pos.forms.JPanelView;
import com.openbravo.pos.forms.MenuDefinition;
import com.openbravo.pos.forms.MenuElement;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JPanel;

public class JPanelMenu
extends JPanel
implements JPanelView {
    private final MenuDefinition m_menu;
    private boolean created = false;
    private JPanel currententrypanel = null;
    private JPanel menucontainer;

    public JPanelMenu(MenuDefinition menu) {
        this.m_menu = menu;
        this.created = false;
        this.initComponents();
    }

    @Override
    public JComponent getComponent() {
        return this;
    }

    @Override
    public String getTitle() {
        return this.m_menu.getTitle();
    }

    @Override
    public void activate() throws BasicException {
        if (!this.created) {
            for (int i = 0; i < this.m_menu.countMenuElements(); ++i) {
                MenuElement menuitem = this.m_menu.getMenuElement(i);
                menuitem.addComponent(this);
            }
            this.created = true;
        }
    }

    @Override
    public boolean deactivate() {
        return true;
    }

    public void addTitle(Component title) {
        this.currententrypanel = null;
        JPanel titlepanel = new JPanel();
        titlepanel.setLayout(new BorderLayout());
        titlepanel.add(title, "Center");
        titlepanel.applyComponentOrientation(this.getComponentOrientation());
        this.menucontainer.add(titlepanel);
    }

    public void addEntry(Component entry) {
        if (this.currententrypanel == null) {
            this.currententrypanel = new JPanel();
            this.currententrypanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 20, 0));
            this.currententrypanel.setLayout(new GridLayout(0, 6, 5, 5));
            this.menucontainer.add(this.currententrypanel);
        }
        this.currententrypanel.add(entry);
        this.currententrypanel.applyComponentOrientation(this.getComponentOrientation());
    }

    private void initComponents() {
        this.menucontainer = new JPanel();
        this.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        this.setFont(new Font("Arial", 0, 14));
        this.setLayout(new BorderLayout());
        this.menucontainer.setBackground(new Color(102, 102, 102));
        this.menucontainer.setFont(new Font("Arial", 0, 14));
        this.menucontainer.setLayout(new BoxLayout(this.menucontainer, 1));
        this.add((Component)this.menucontainer, "North");
    }
}

