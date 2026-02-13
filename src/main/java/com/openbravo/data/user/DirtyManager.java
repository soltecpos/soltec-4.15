/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.data.user;

import com.openbravo.data.user.DirtyListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Enumeration;
import java.util.Vector;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class DirtyManager
implements DocumentListener,
ChangeListener,
ActionListener,
PropertyChangeListener {
    private boolean m_bDirty = false;
    protected Vector<DirtyListener> listeners = new Vector();

    public void addDirtyListener(DirtyListener l) {
        this.listeners.add(l);
    }

    public void removeDirtyListener(DirtyListener l) {
        this.listeners.remove(l);
    }

    protected void fireChangedDirty() {
        Enumeration<DirtyListener> e = this.listeners.elements();
        while (e.hasMoreElements()) {
            DirtyListener l = e.nextElement();
            l.changedDirty(this.m_bDirty);
        }
    }

    public void setDirty(boolean bValue) {
        if (this.m_bDirty != bValue) {
            this.m_bDirty = bValue;
            this.fireChangedDirty();
        }
    }

    public boolean isDirty() {
        return this.m_bDirty;
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
        this.setDirty(true);
    }

    @Override
    public void insertUpdate(DocumentEvent e) {
        this.setDirty(true);
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        this.setDirty(true);
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        this.setDirty(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        this.setDirty(true);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        this.setDirty(true);
    }
}

