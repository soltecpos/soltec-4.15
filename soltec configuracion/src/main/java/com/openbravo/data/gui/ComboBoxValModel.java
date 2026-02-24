/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.data.gui;

import com.openbravo.data.loader.IKeyGetter;
import com.openbravo.data.loader.KeyGetterBuilder;
import java.util.ArrayList;
import java.util.List;
import javax.swing.AbstractListModel;
import javax.swing.ComboBoxModel;

public class ComboBoxValModel<E>
extends AbstractListModel<E>
implements ComboBoxModel<E> {
    private static final long serialVersionUID = 1L;
    private List<E> m_aData;
    private IKeyGetter m_keygetter;
    private Object m_selected;

    public ComboBoxValModel(List<E> aData, IKeyGetter keygetter) {
        this.m_aData = aData;
        this.m_keygetter = keygetter;
        this.m_selected = null;
    }

    public ComboBoxValModel(List<E> aData) {
        this(aData, KeyGetterBuilder.INSTANCE);
    }

    public ComboBoxValModel(IKeyGetter keygetter) {
        this(new ArrayList(), keygetter);
    }

    public ComboBoxValModel() {
        this(new ArrayList(), KeyGetterBuilder.INSTANCE);
    }

    public void add(E c) {
        this.m_aData.add(c);
    }

    public void del(E c) {
        this.m_aData.remove(c);
    }

    public void add(int index, E c) {
        this.m_aData.add(index, c);
    }

    public void refresh(List<E> aData) {
        this.m_aData = aData;
        this.m_selected = null;
    }

    public Object getSelectedKey() {
        if (this.m_selected == null) {
            return null;
        }
        return this.m_keygetter.getKey(this.m_selected);
    }

    public String getSelectedText() {
        if (this.m_selected == null) {
            return null;
        }
        return this.m_selected.toString();
    }

    public void setSelectedKey(Object aKey) {
        this.setSelectedItem(this.getElementByKey(aKey));
    }

    public void setSelectedFirst() {
        this.m_selected = this.m_aData.isEmpty() ? null : this.m_aData.get(0);
    }

    public Object getElementByKey(Object aKey) {
        if (aKey != null) {
            for (E value : this.m_aData) {
                if (!aKey.equals(this.m_keygetter.getKey(value))) continue;
                return value;
            }
        }
        return null;
    }

    @Override
    public E getElementAt(int index) {
        return this.m_aData.get(index);
    }

    @Override
    public Object getSelectedItem() {
        return this.m_selected;
    }

    @Override
    public int getSize() {
        return this.m_aData.size();
    }

    @Override
    public void setSelectedItem(Object anItem) {
        if (this.m_selected != null && !this.m_selected.equals(anItem) || this.m_selected == null && anItem != null) {
            this.m_selected = anItem;
            this.fireContentsChanged(this, -1, -1);
        }
    }
}

