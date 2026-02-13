/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.sales.restaurant;

import com.openbravo.basic.BasicException;
import com.openbravo.data.gui.NullIcon;
import com.openbravo.data.loader.DataRead;
import com.openbravo.data.loader.SerializableRead;
import java.awt.Dimension;
import java.awt.Insets;
import java.io.Serializable;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;

public class Place
implements SerializableRead,
Serializable {
    private static final long serialVersionUID = 8652254694281L;
    private static final Icon ICO_OCU = new ImageIcon(Place.class.getResource("/com/openbravo/images/edit_group.png"));
    private static final Icon ICO_FRE = new NullIcon(22, 22);
    private String m_sId;
    private String m_sName;
    private int m_ix;
    private int m_iy;
    private String m_sfloor;
    private String m_customer;
    private String m_waiter;
    private String m_ticketId;
    private Boolean m_tableMoved;
    private boolean m_bPeople;
    private JButton m_btn;

    @Override
    public void readValues(DataRead dr) throws BasicException {
        this.m_sId = dr.getString(1);
        this.m_sName = dr.getString(2);
        this.m_ix = dr.getInt(3);
        this.m_iy = dr.getInt(4);
        this.m_sfloor = dr.getString(5);
        this.m_customer = dr.getString(6);
        this.m_waiter = dr.getString(7);
        this.m_ticketId = dr.getString(8);
        this.m_tableMoved = dr.getBoolean(9);
        this.m_bPeople = false;
        this.m_btn = new JButton();
        this.m_btn.setFocusPainted(false);
        this.m_btn.setFocusable(false);
        this.m_btn.setRequestFocusEnabled(false);
        this.m_btn.setHorizontalTextPosition(0);
        this.m_btn.setVerticalTextPosition(3);
        this.m_btn.setIcon(ICO_FRE);
        this.m_btn.setText(this.m_sName);
        this.m_btn.setMargin(new Insets(2, 5, 2, 5));
    }

    public String getId() {
        return this.m_sId;
    }

    public String getName() {
        return this.m_sName;
    }

    public int getX() {
        return this.m_ix;
    }

    public void setX(int x) {
        this.m_ix = x;
    }

    public int getY() {
        return this.m_iy;
    }

    public void setY(int y) {
        this.m_iy = y;
    }

    public String getFloor() {
        return this.m_sfloor;
    }

    public JButton getButton() {
        return this.m_btn;
    }

    public String getCustomer() {
        return this.m_customer;
    }

    public String getWaiter() {
        return this.m_waiter;
    }

    public boolean hasPeople() {
        return this.m_bPeople;
    }

    public void setPeople(boolean bValue) {
        this.m_bPeople = bValue;
        this.m_btn.setIcon(bValue ? ICO_OCU : ICO_FRE);
    }

    public void setButtonBounds() {
        Dimension d = this.m_btn.getPreferredSize();
        this.m_btn.setPreferredSize(new Dimension(d.width + 60, d.height + 30));
        d = this.m_btn.getPreferredSize();
        this.m_btn.setBounds(this.m_ix - d.width / 2, this.m_iy - d.height / 2, d.width, d.height);
    }

    public void setButtonText(String btnText) {
        this.m_btn.setText(btnText);
    }
}

