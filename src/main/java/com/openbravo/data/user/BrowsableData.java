/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.data.user;

import com.openbravo.basic.BasicException;
import com.openbravo.data.loader.LocalRes;
import com.openbravo.data.user.Finder;
import com.openbravo.data.user.ListProvider;
import com.openbravo.data.user.SaveProvider;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.EventListener;
import java.util.List;
import javax.swing.ListModel;
import javax.swing.event.EventListenerList;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

public class BrowsableData<T>
implements ListModel<T> {
    protected EventListenerList listeners = new EventListenerList();
    private boolean m_bIsAdjusting;
    private ListProvider<T> m_dataprov;
    private SaveProvider<T> m_saveprov;
    private List<T> m_aData;
    private Comparator<? super T> m_comparer;

    public BrowsableData(ListProvider<T> dataprov, SaveProvider<T> saveprov, Comparator<? super T> c) {
        this.m_dataprov = dataprov;
        this.m_saveprov = saveprov;
        this.m_comparer = c;
        this.m_bIsAdjusting = false;
        this.m_aData = new ArrayList<T>();
    }

    public BrowsableData(ListProvider<T> dataprov, SaveProvider<T> saveprov) {
        this(dataprov, saveprov, null);
    }

    public BrowsableData(ListProvider<T> dataprov) {
        this(dataprov, null, null);
    }

    @Override
    public final void addListDataListener(ListDataListener l) {
        this.listeners.add(ListDataListener.class, l);
    }

    @Override
    public final void removeListDataListener(ListDataListener l) {
        this.listeners.remove(ListDataListener.class, l);
    }

    @Override
    public final T getElementAt(int index) {
        return this.m_aData.get(index);
    }

    @Override
    public final int getSize() {
        return this.m_aData.size();
    }

    public final boolean isAdjusting() {
        return this.m_bIsAdjusting;
    }

    protected void fireDataIntervalAdded(int index0, int index1) {
        this.m_bIsAdjusting = true;
        EventListener[] l = this.listeners.getListeners(ListDataListener.class);
        ListDataEvent e = null;
        for (int i = 0; i < l.length; ++i) {
            if (e == null) {
                e = new ListDataEvent(this, 1, index0, index1);
            }
            ((ListDataListener)l[i]).intervalAdded(e);
        }
        this.m_bIsAdjusting = false;
    }

    protected void fireDataContentsChanged(int index0, int index1) {
        this.m_bIsAdjusting = true;
        EventListener[] l = this.listeners.getListeners(ListDataListener.class);
        ListDataEvent e = null;
        for (int i = 0; i < l.length; ++i) {
            if (e == null) {
                e = new ListDataEvent(this, 0, index0, index1);
            }
            ((ListDataListener)l[i]).contentsChanged(e);
        }
        this.m_bIsAdjusting = false;
    }

    protected void fireDataIntervalRemoved(int index0, int index1) {
        this.m_bIsAdjusting = true;
        EventListener[] l = this.listeners.getListeners(ListDataListener.class);
        ListDataEvent e = null;
        for (int i = 0; i < l.length; ++i) {
            if (e == null) {
                e = new ListDataEvent(this, 2, index0, index1);
            }
            ((ListDataListener)l[i]).intervalRemoved(e);
        }
        this.m_bIsAdjusting = false;
    }

    public void refreshData() throws BasicException {
        this.putNewData(this.m_dataprov == null ? null : this.m_dataprov.refreshData());
    }

    public void loadData() throws BasicException {
        this.putNewData(this.m_dataprov == null ? null : this.m_dataprov.loadData());
    }

    public void unloadData() throws BasicException {
        this.putNewData(null);
    }

    public void loadList(List<T> l) {
        this.putNewData(l);
    }

    public void sort(Comparator<? super T> c) {
        Collections.sort(this.m_aData, c);
        this.putNewData(this.m_aData);
    }

    public final boolean canLoadData() {
        return this.m_dataprov != null;
    }

    public boolean canInsertData() {
        return this.m_saveprov != null && this.m_saveprov.canInsert();
    }

    public boolean canDeleteData() {
        return this.m_saveprov != null && this.m_saveprov.canDelete();
    }

    public boolean canUpdateData() {
        return this.m_saveprov != null && this.m_saveprov.canUpdate();
    }

    public final int findNext(int index, Finder f) throws BasicException {
        int i;
        for (i = index + 1; i < this.m_aData.size(); ++i) {
            if (!f.match(this.getElementAt(i))) continue;
            return i;
        }
        for (i = 0; i < index; ++i) {
            if (!f.match(this.getElementAt(i))) continue;
            return i;
        }
        return -1;
    }

    public final int removeRecord(int index) throws BasicException {
        if (this.canDeleteData() && index >= 0 && index < this.m_aData.size()) {
            if (this.m_saveprov.deleteData(this.getElementAt(index)) > 0) {
                this.m_aData.remove(index);
                this.fireDataIntervalRemoved(index, index);
                int newindex = index < this.m_aData.size() ? index : this.m_aData.size() - 1;
                return newindex;
            }
            throw new BasicException(LocalRes.getIntString("exception.nodelete"));
        }
        throw new BasicException(LocalRes.getIntString("exception.nodelete"));
    }

    public final int updateRecord(int index, T value) throws BasicException {
        if (this.canUpdateData() && index >= 0 && index < this.m_aData.size()) {
            if (this.m_saveprov.updateData(value) > 0) {
                int newindex;
                if (this.m_comparer == null) {
                    newindex = index;
                    this.m_aData.set(newindex, value);
                } else {
                    newindex = this.insertionPoint(value);
                    if (newindex == index + 1) {
                        newindex = index;
                        this.m_aData.set(newindex, value);
                    } else if (newindex > index + 1) {
                        this.m_aData.remove(index);
                        this.m_aData.add(--newindex, value);
                    } else {
                        this.m_aData.remove(index);
                        this.m_aData.add(newindex, value);
                    }
                }
                if (newindex >= index) {
                    this.fireDataContentsChanged(index, newindex);
                } else {
                    this.fireDataContentsChanged(newindex, index);
                }
                return newindex;
            }
            throw new BasicException(LocalRes.getIntString("exception.noupdate"));
        }
        throw new BasicException(LocalRes.getIntString("exception.noupdate"));
    }

    public final int insertRecord(T value) throws BasicException {
        if (this.canInsertData() && this.m_saveprov.insertData(value) > 0) {
            int newindex = this.m_comparer == null ? this.m_aData.size() : this.insertionPoint(value);
            this.m_aData.add(newindex, value);
            this.fireDataIntervalAdded(newindex, newindex);
            return newindex;
        }
        throw new BasicException(LocalRes.getIntString("exception.noinsert"));
    }

    private final void putNewData(List<T> aData) {
        int oldSize = this.m_aData.size();
        this.m_aData = aData == null ? new ArrayList() : aData;
        int newSize = this.m_aData.size();
        if (this.m_comparer != null) {
            Collections.sort(this.m_aData, this.m_comparer);
        }
        this.fireDataContentsChanged(0, newSize - 1);
        if (oldSize > newSize) {
            this.fireDataIntervalRemoved(newSize, oldSize - 1);
        } else if (oldSize < newSize) {
            this.fireDataIntervalAdded(oldSize, newSize - 1);
        }
    }

    private final int insertionPoint(T value) {
        int low = 0;
        int high = this.m_aData.size() - 1;
        while (low <= high) {
            int mid = low + high >> 1;
            T midVal = this.m_aData.get(mid);
            int cmp = this.m_comparer.compare(midVal, value);
            if (cmp <= 0) {
                low = mid + 1;
                continue;
            }
            high = mid - 1;
        }
        return low;
    }
}

