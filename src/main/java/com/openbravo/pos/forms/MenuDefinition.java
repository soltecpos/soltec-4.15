/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.forms;

import com.openbravo.pos.forms.AppLocal;
import com.openbravo.pos.forms.MenuElement;
import com.openbravo.pos.forms.MenuItemDefinition;
import com.openbravo.pos.forms.MenuTitleDefinition;
import java.util.ArrayList;
import javax.swing.Action;

public class MenuDefinition {
    private final String m_sKey;
    private final ArrayList m_aMenuElements;

    public MenuDefinition(String skey) {
        this.m_sKey = skey;
        this.m_aMenuElements = new ArrayList();
    }

    public String getKey() {
        return this.m_sKey;
    }

    public String getTitle() {
        return AppLocal.getIntString(this.m_sKey);
    }

    public void addMenuItem(Action act) {
        MenuItemDefinition menuitem = new MenuItemDefinition(act);
        this.m_aMenuElements.add(menuitem);
    }

    public void addMenuTitle(String keytext) {
        MenuTitleDefinition menutitle = new MenuTitleDefinition();
        menutitle.KeyText = keytext;
        this.m_aMenuElements.add(menutitle);
    }

    public MenuElement getMenuElement(int i) {
        return (MenuElement)this.m_aMenuElements.get(i);
    }

    public int countMenuElements() {
        return this.m_aMenuElements.size();
    }
}

