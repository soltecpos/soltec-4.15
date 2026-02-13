/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.data.gui;

import com.openbravo.data.loader.QBFCompareEnum;
import javax.swing.AbstractListModel;
import javax.swing.ComboBoxModel;

public class ListQBFModelNumber
extends AbstractListModel<Object>
implements ComboBoxModel<Object> {
    private Object[] m_items;
    private Object m_sel;

    public ListQBFModelNumber(Object ... items) {
        this.m_items = items;
        this.m_sel = this.m_items[0];
    }

    public static ListQBFModelNumber getMandatoryString() {
        return new ListQBFModelNumber(QBFCompareEnum.COMP_NONE, QBFCompareEnum.COMP_EQUALS, QBFCompareEnum.COMP_RE, QBFCompareEnum.COMP_DISTINCT, QBFCompareEnum.COMP_GREATER, QBFCompareEnum.COMP_LESS, QBFCompareEnum.COMP_GREATEROREQUALS, QBFCompareEnum.COMP_LESSOREQUALS);
    }

    public static ListQBFModelNumber getMandatoryNumber() {
        return new ListQBFModelNumber(QBFCompareEnum.COMP_NONE, QBFCompareEnum.COMP_EQUALS, QBFCompareEnum.COMP_DISTINCT, QBFCompareEnum.COMP_GREATER, QBFCompareEnum.COMP_LESS, QBFCompareEnum.COMP_GREATEROREQUALS, QBFCompareEnum.COMP_LESSOREQUALS);
    }

    public static ListQBFModelNumber getNonMandatoryString() {
        return new ListQBFModelNumber(QBFCompareEnum.COMP_NONE, QBFCompareEnum.COMP_EQUALS, QBFCompareEnum.COMP_RE, QBFCompareEnum.COMP_DISTINCT, QBFCompareEnum.COMP_GREATER, QBFCompareEnum.COMP_LESS, QBFCompareEnum.COMP_GREATEROREQUALS, QBFCompareEnum.COMP_LESSOREQUALS, QBFCompareEnum.COMP_ISNULL, QBFCompareEnum.COMP_ISNOTNULL);
    }

    public static ListQBFModelNumber getNonMandatoryNumber() {
        return new ListQBFModelNumber(QBFCompareEnum.COMP_NONE, QBFCompareEnum.COMP_EQUALS, QBFCompareEnum.COMP_DISTINCT, QBFCompareEnum.COMP_GREATER, QBFCompareEnum.COMP_LESS, QBFCompareEnum.COMP_GREATEROREQUALS, QBFCompareEnum.COMP_LESSOREQUALS, QBFCompareEnum.COMP_ISNULL, QBFCompareEnum.COMP_ISNOTNULL);
    }

    @Override
    public Object getElementAt(int index) {
        return this.m_items[index];
    }

    @Override
    public int getSize() {
        return this.m_items.length;
    }

    @Override
    public Object getSelectedItem() {
        return this.m_sel;
    }

    @Override
    public void setSelectedItem(Object anItem) {
        this.m_sel = anItem;
    }
}

