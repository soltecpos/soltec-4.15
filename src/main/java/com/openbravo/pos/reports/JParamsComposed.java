/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.reports;

import com.openbravo.basic.BasicException;
import com.openbravo.data.loader.SerializerWrite;
import com.openbravo.data.loader.SerializerWriteComposed;
import com.openbravo.pos.forms.AppView;
import com.openbravo.pos.reports.ReportEditorCreator;
import java.awt.Component;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

public class JParamsComposed
extends JPanel
implements ReportEditorCreator {
    private List<ReportEditorCreator> editors = new ArrayList<ReportEditorCreator>();

    public JParamsComposed() {
        this.initComponents();
    }

    @Override
    public void init(AppView app) {
        for (ReportEditorCreator qbff : this.editors) {
            qbff.init(app);
        }
    }

    @Override
    public void activate() throws BasicException {
        for (ReportEditorCreator qbff : this.editors) {
            qbff.activate();
        }
    }

    @Override
    public SerializerWrite getSerializerWrite() {
        SerializerWriteComposed sw = new SerializerWriteComposed();
        for (ReportEditorCreator qbff : this.editors) {
            sw.add(qbff.getSerializerWrite());
        }
        return sw;
    }

    @Override
    public Component getComponent() {
        return this;
    }

    @Override
    public Object createValue() throws BasicException {
        Object[] value = new Object[this.editors.size()];
        for (int i = 0; i < this.editors.size(); ++i) {
            value[i] = this.editors.get(i).createValue();
        }
        return value;
    }

    public void addEditor(ReportEditorCreator c) {
        this.editors.add(c);
        this.add(c.getComponent());
    }

    public boolean isEmpty() {
        return this.editors.isEmpty();
    }

    private void initComponents() {
        this.setFont(new Font("Arial", 0, 12));
        this.setLayout(new BoxLayout(this, 1));
    }
}

