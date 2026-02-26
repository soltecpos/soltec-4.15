/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.forms;

import com.openbravo.pos.forms.AppLocal;
import com.openbravo.pos.forms.AppView;
import com.openbravo.pos.forms.JPrincipalApp;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.ImageIcon;

public class MenuPanelAction
extends AbstractAction {
    private final AppView m_App;
    private final String m_sMyView;

    private ImageIcon safeIcon(String path) {
        java.net.URL imgUrl = JPrincipalApp.class.getResource(path);
        if (imgUrl != null) {
            return new ImageIcon(imgUrl);
        } else {
            // System.err.println("WARNING: Menu icon not found: " + path);
            return null;
        }
    }

    public MenuPanelAction(AppView app, String icon, String keytext, String sMyView) {
        if (icon != null && !icon.isEmpty()) {
            this.putValue("SmallIcon", safeIcon(icon));
        }
        // If keytext starts with "Menu." it's a localization key; otherwise use as-is
        if (keytext != null && keytext.startsWith("Menu.")) {
            this.putValue("Name", AppLocal.getIntString(keytext));
        } else {
            this.putValue("Name", keytext);
        }
        this.putValue("taskname", sMyView);
        this.m_App = app;
        this.m_sMyView = sMyView;
    }

    @Override
    public void actionPerformed(ActionEvent evt) {
        this.m_App.getAppUserView().showTask(this.m_sMyView);
    }
}

