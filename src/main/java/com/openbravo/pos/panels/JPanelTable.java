/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.panels;

import com.openbravo.basic.BasicException;
import com.openbravo.data.gui.JCounter;
import com.openbravo.data.gui.JLabelDirty;
import com.openbravo.data.gui.JListNavigator;
import com.openbravo.data.gui.JNavigator;
import com.openbravo.data.gui.JSaver;
import com.openbravo.data.gui.MessageInf;
import com.openbravo.data.loader.ComparatorCreator;
import com.openbravo.data.loader.Vectorer;
import com.openbravo.data.user.BrowsableEditableData;
import com.openbravo.data.user.DirtyManager;
import com.openbravo.data.user.EditorRecord;
import com.openbravo.data.user.ListProvider;
import com.openbravo.data.user.SaveProvider;
import com.openbravo.pos.customers.CustomerInfoGlobal;
import com.openbravo.pos.forms.AppLocal;
import com.openbravo.pos.forms.AppView;
import com.openbravo.pos.forms.BeanFactoryApp;
import com.openbravo.pos.forms.BeanFactoryException;
import com.openbravo.pos.forms.JPanelView;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;

public abstract class JPanelTable
extends JPanel
implements JPanelView,
BeanFactoryApp {
    private static final long serialVersionUID = 1L;
    protected BrowsableEditableData<Object[]> bd;
    protected DirtyManager dirty;
    protected AppView app;
    public JPanel container;
    public JPanel toolbar;

    public JPanelTable() {
        this.initComponents();
    }

    @Override
    public void init(AppView app) throws BeanFactoryException {
        this.app = app;
        this.dirty = new DirtyManager();
        this.bd = null;
        this.init();
    }

    @Override
    public Object getBean() {
        return this;
    }

    protected void startNavigation() {
        if (this.bd == null) {
            ListCellRenderer<? super Object[]> cr;
            this.bd = new BrowsableEditableData<Object[]>(this.getListProvider(), this.getSaveProvider(), this.getEditor(), this.dirty);
            Component c = this.getFilter();
            if (c != null) {
                ((Component)c).applyComponentOrientation(this.getComponentOrientation());
                this.add((Component)c, "North");
            }
            if ((c = this.getEditor().getComponent()) != null) {
                ((Component)c).applyComponentOrientation(this.getComponentOrientation());
                this.container.add((Component)c, "Center");
            }
            if ((cr = this.getListCellRenderer()) != null) {
                JListNavigator<Object[]> nl = new JListNavigator<Object[]>(this.bd);
                nl.applyComponentOrientation(this.getComponentOrientation());
                nl.setCellRenderer(cr);
                this.container.add(nl, "Before");
            }
            if ((c = this.getToolbarExtras()) != null) {
                ((Component)c).applyComponentOrientation(this.getComponentOrientation());
                this.toolbar.add(c);
            }
            c = new JLabelDirty(this.dirty);
            ((Component)c).applyComponentOrientation(this.getComponentOrientation());
            this.toolbar.add(c);
            c = new JCounter(this.bd);
            ((Component)c).applyComponentOrientation(this.getComponentOrientation());
            this.toolbar.add(c);
            c = new JNavigator<Object[]>(this.bd, this.getVectorer(), this.getComparatorCreator());
            ((Component)c).applyComponentOrientation(this.getComponentOrientation());
            this.toolbar.add(c);
            c = new JSaver(this.bd);
            ((Component)c).applyComponentOrientation(this.getComponentOrientation());
            this.toolbar.add(c);
        }
    }

    public Component getToolbarExtras() {
        return null;
    }

    public Component getFilter() {
        return null;
    }

    protected abstract void init();

    public abstract EditorRecord getEditor();

    public abstract ListProvider<Object[]> getListProvider();

    public abstract SaveProvider<Object[]> getSaveProvider();

    public Vectorer getVectorer() {
        return null;
    }

    public ComparatorCreator getComparatorCreator() {
        return null;
    }

    public ListCellRenderer<? super Object[]> getListCellRenderer() {
        return null;
    }

    @Override
    public JComponent getComponent() {
        return this;
    }

    @Override
    public void activate() throws BasicException {
        this.startNavigation();
        this.bd.actionLoad();
        if (CustomerInfoGlobal.getInstance() != null) {
            this.bd.actionInsert();
        }
    }

    @Override
    public boolean deactivate() {
        try {
            return this.bd.actionClosingForm(this);
        }
        catch (BasicException eD) {
            MessageInf msg = new MessageInf(-67108864, AppLocal.getIntString("message.CannotMove"), eD);
            msg.show(this);
            return false;
        }
    }

    private void initComponents() {
        this.container = new JPanel();
        this.toolbar = new JPanel();
        this.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        this.setLayout(new BorderLayout());
        this.container.setFont(new Font("Arial", 0, 12));
        this.container.setLayout(new BorderLayout());
        this.container.add((Component)this.toolbar, "North");
        this.add((Component)this.container, "Center");
    }
}

