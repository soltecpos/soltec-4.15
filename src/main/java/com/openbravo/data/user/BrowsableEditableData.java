/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.data.user;

import com.openbravo.basic.BasicException;
import com.openbravo.data.loader.LocalRes;
import com.openbravo.data.user.BrowsableData;
import com.openbravo.data.user.BrowseListener;
import com.openbravo.data.user.DirtyManager;
import com.openbravo.data.user.EditorListener;
import com.openbravo.data.user.EditorRecord;
import com.openbravo.data.user.Finder;
import com.openbravo.data.user.ListProvider;
import com.openbravo.data.user.SaveProvider;
import com.openbravo.data.user.StateListener;
import java.awt.Component;
import java.util.Comparator;
import java.util.EventListener;
import javax.swing.JOptionPane;
import javax.swing.ListModel;
import javax.swing.event.EventListenerList;

public class BrowsableEditableData<T> {
    public static final int ST_NORECORD = 0;
    public static final int ST_UPDATE = 1;
    public static final int ST_DELETE = 2;
    public static final int ST_INSERT = 3;
    private static final int INX_EOF = -1;
    private BrowsableData<T> m_bd;
    protected EventListenerList listeners = new EventListenerList();
    private EditorRecord m_editorrecord;
    private DirtyManager m_Dirty;
    private int m_iState;
    private int m_iIndex;
    private boolean m_bIsAdjusting;
    private boolean iseditable = true;

    public BrowsableEditableData(BrowsableData<T> bd, EditorRecord ed, DirtyManager dirty) {
        this.m_bd = bd;
        this.m_editorrecord = ed;
        this.m_Dirty = dirty;
        this.m_iState = 0;
        this.m_iIndex = -1;
        this.m_bIsAdjusting = false;
        this.m_editorrecord.writeValueEOF();
        this.m_Dirty.setDirty(false);
    }

    public BrowsableEditableData(ListProvider<T> dataprov, SaveProvider<T> saveprov, Comparator<? super T> c, EditorRecord ed, DirtyManager dirty) {
        this(new BrowsableData<T>(dataprov, saveprov, c), ed, dirty);
    }

    public BrowsableEditableData(ListProvider<T> dataprov, SaveProvider<T> saveprov, EditorRecord ed, DirtyManager dirty) {
        this(new BrowsableData<T>(dataprov, saveprov, null), ed, dirty);
    }

    public final ListModel<T> getListModel() {
        return this.m_bd;
    }

    public final boolean isAdjusting() {
        return this.m_bIsAdjusting || this.m_bd.isAdjusting();
    }

    private T getCurrentElement() {
        return this.m_iIndex >= 0 && this.m_iIndex < this.m_bd.getSize() ? (T)this.m_bd.getElementAt(this.m_iIndex) : null;
    }

    public final int getIndex() {
        return this.m_iIndex;
    }

    public final void addStateListener(StateListener l) {
        this.listeners.add(StateListener.class, l);
    }

    public final void removeStateListener(StateListener l) {
        this.listeners.remove(StateListener.class, l);
    }

    public final void addEditorListener(EditorListener l) {
        this.listeners.add(EditorListener.class, l);
    }

    public final void removeEditorListener(EditorListener l) {
        this.listeners.remove(EditorListener.class, l);
    }

    public final void addBrowseListener(BrowseListener l) {
        this.listeners.add(BrowseListener.class, l);
    }

    public final void removeBrowseListener(BrowseListener l) {
        this.listeners.remove(BrowseListener.class, l);
    }

    public int getState() {
        return this.m_iState;
    }

    private void fireStateUpdate() {
        EventListener[] l = this.listeners.getListeners(StateListener.class);
        int iState = this.getState();
        for (EventListener l1 : l) {
            ((StateListener)l1).updateState(iState);
        }
    }

    protected void fireDataBrowse() {
        int i;
        this.m_bIsAdjusting = true;
        T obj = this.getCurrentElement();
        int iIndex = this.getIndex();
        int iCount = this.m_bd.getSize();
        if (obj == null) {
            this.m_iState = 0;
            this.m_editorrecord.writeValueEOF();
        } else {
            this.m_iState = 1;
            this.m_editorrecord.writeValueEdit(obj);
        }
        this.m_Dirty.setDirty(false);
        this.fireStateUpdate();
        EventListener[] l = this.listeners.getListeners(EditorListener.class);
        for (i = 0; i < l.length; ++i) {
            ((EditorListener)l[i]).updateValue(obj);
        }
        l = this.listeners.getListeners(BrowseListener.class);
        for (i = 0; i < l.length; ++i) {
            ((BrowseListener)l[i]).updateIndex(iIndex, iCount);
        }
        this.m_bIsAdjusting = false;
    }

    public boolean canLoadData() {
        return this.m_bd.canLoadData();
    }

    public void setEditable(boolean value) {
        this.iseditable = value;
    }

    public boolean canInsertData() {
        return this.iseditable && this.m_bd.canInsertData();
    }

    public boolean canDeleteData() {
        return this.iseditable && this.m_bd.canDeleteData();
    }

    public boolean canUpdateData() {
        return this.iseditable && this.m_bd.canUpdateData();
    }

    public void refreshCurrent() {
        this.baseMoveTo(this.m_iIndex);
    }

    public void refreshData() throws BasicException {
        this.saveData();
        this.m_bd.refreshData();
        this.m_editorrecord.refresh();
        this.baseMoveTo(0);
    }

    public void loadData() throws BasicException {
        this.saveData();
        this.m_bd.loadData();
        this.m_editorrecord.refresh();
        this.baseMoveTo(0);
    }

    public void unloadData() throws BasicException {
        this.saveData();
        this.m_bd.unloadData();
        this.m_editorrecord.refresh();
        this.baseMoveTo(0);
    }

    public void sort(Comparator<? super T> c) throws BasicException {
        this.saveData();
        this.m_bd.sort(c);
        this.baseMoveTo(0);
    }

    public void moveTo(int i) throws BasicException {
        this.saveData();
        if (this.m_iIndex != i) {
            this.baseMoveTo(i);
        }
    }

    public final void movePrev() throws BasicException {
        this.saveData();
        if (this.m_iIndex > 0) {
            this.baseMoveTo(this.m_iIndex - 1);
        }
    }

    public final void moveNext() throws BasicException {
        this.saveData();
        if (this.m_iIndex < this.m_bd.getSize() - 1) {
            this.baseMoveTo(this.m_iIndex + 1);
        }
    }

    public final void moveFirst() throws BasicException {
        this.saveData();
        if (this.m_bd.getSize() > 0) {
            this.baseMoveTo(0);
        }
    }

    public final void moveLast() throws BasicException {
        this.saveData();
        if (this.m_bd.getSize() > 0) {
            this.baseMoveTo(this.m_bd.getSize() - 1);
        }
    }

    public final int findNext(Finder f) throws BasicException {
        return this.m_bd.findNext(this.m_iIndex, f);
    }

    public void saveData() throws BasicException {
        if (this.m_Dirty.isDirty()) {
            if (this.m_iState == 1) {
                int i = this.m_bd.updateRecord(this.m_iIndex, (T)this.m_editorrecord.createValue());
                this.m_editorrecord.refresh();
                this.baseMoveTo(i);
            } else if (this.m_iState == 3) {
                int i = this.m_bd.insertRecord((T)this.m_editorrecord.createValue());
                this.m_editorrecord.refresh();
                this.baseMoveTo(i);
            } else if (this.m_iState == 2) {
                int i = this.m_bd.removeRecord(this.m_iIndex);
                this.m_editorrecord.refresh();
                this.baseMoveTo(i);
            }
        }
    }

    public void actionReloadCurrent(Component c) {
        if (!this.m_Dirty.isDirty() || JOptionPane.showConfirmDialog(c, LocalRes.getIntString("message.changeslost"), LocalRes.getIntString("title.editor"), 0, 3) == 0) {
            this.refreshCurrent();
        }
    }

    public boolean actionClosingForm(Component c) throws BasicException {
        if (this.m_Dirty.isDirty()) {
            int res = JOptionPane.showConfirmDialog(c, LocalRes.getIntString("message.wannasave"), LocalRes.getIntString("title.editor"), 1, 3);
            if (res == 0) {
                this.saveData();
                return true;
            }
            if (res == 1) {
                try {
                    this.refreshCurrent();
                }
                catch (Exception e) {
                    this.m_Dirty.setDirty(false);
                }
                return true;
            }
            return false;
        }
        return true;
    }

    public final void actionLoad() throws BasicException {
        this.loadData();
        if (this.m_bd.getSize() == 0) {
            this.actionInsert();
        }
    }

    public final void actionInsert() throws BasicException {
        this.saveData();
        if (this.canInsertData()) {
            this.m_iState = 3;
            this.m_editorrecord.writeValueInsert();
            this.m_Dirty.setDirty(false);
            this.fireStateUpdate();
        }
    }

    public final void actionDelete() throws BasicException {
        this.saveData();
        if (this.canDeleteData()) {
            T obj = this.getCurrentElement();
            int iIndex = this.getIndex();
            int iCount = this.m_bd.getSize();
            if (iIndex >= 0 && iIndex < iCount) {
                this.m_iState = 2;
                this.m_editorrecord.writeValueDelete(obj);
                this.m_Dirty.setDirty(true);
                this.fireStateUpdate();
            }
        }
    }

    private void baseMoveTo(int i) {
        this.m_iIndex = i >= 0 && i < this.m_bd.getSize() ? i : -1;
        this.fireDataBrowse();
    }
}

