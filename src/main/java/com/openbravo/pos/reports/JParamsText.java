/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.reports;

import com.openbravo.basic.BasicException;
import com.openbravo.data.loader.Datas;
import com.openbravo.data.loader.QBFCompareEnum;
import com.openbravo.data.loader.SerializerWrite;
import com.openbravo.data.loader.SerializerWriteBasic;
import com.openbravo.format.Formats;
import com.openbravo.pos.forms.AppLocal;
import com.openbravo.pos.forms.AppView;
import com.openbravo.pos.reports.ReportEditorCreator;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public final class JParamsText
extends JPanel
implements ReportEditorCreator {
    private Datas datasvalue;
    private Formats formatsvalue;
    private QBFCompareEnum comparevalue;
    private JLabel lblField;
    private JTextField txtField;

    public JParamsText() {
        this.initComponents();
        this.setLabel(AppLocal.getIntString("label.value"));
        this.setType(Formats.STRING);
    }

    public JParamsText(String label) {
        this.initComponents();
        this.setLabel(label);
        this.setType(Formats.STRING);
    }

    public JParamsText(String label, Formats format) {
        this.initComponents();
        this.setLabel(label);
        this.setType(format);
    }

    public JParamsText(String label, Formats format, Datas data) {
        this.initComponents();
        this.setLabel(label);
        this.setType(format, data);
    }

    public void setLabel(String label) {
        this.lblField.setText(label);
    }

    public void setType(Formats format, Datas data) {
        this.formatsvalue = format;
        this.datasvalue = data;
        this.setDefaultCompare();
    }

    public void setType(Formats format) {
        if (Formats.INT == format) {
            this.setType(format, Datas.INT);
        } else if (Formats.DOUBLE == format || Formats.CURRENCY == format || Formats.PERCENT == format) {
            this.setType(format, Datas.DOUBLE);
        } else if (Formats.DATE == format || Formats.TIME == format || Formats.TIMESTAMP == format) {
            this.setType(format, Datas.TIMESTAMP);
        } else if (Formats.BOOLEAN == format) {
            this.setType(format, Datas.BOOLEAN);
        } else {
            this.setType(format, Datas.STRING);
        }
    }

    public void setCompare(QBFCompareEnum compare) {
        this.comparevalue = compare;
    }

    private void setDefaultCompare() {
        this.comparevalue = Formats.INT == this.formatsvalue ? QBFCompareEnum.COMP_LESSOREQUALS : (Formats.DOUBLE == this.formatsvalue || Formats.CURRENCY == this.formatsvalue || Formats.PERCENT == this.formatsvalue ? QBFCompareEnum.COMP_LESSOREQUALS : (Formats.DATE == this.formatsvalue || Formats.TIME == this.formatsvalue || Formats.TIMESTAMP == this.formatsvalue ? QBFCompareEnum.COMP_GREATEROREQUALS : (Formats.BOOLEAN == this.formatsvalue ? QBFCompareEnum.COMP_EQUALS : QBFCompareEnum.COMP_RE)));
    }

    @Override
    public void init(AppView app) {
    }

    @Override
    public void activate() throws BasicException {
        this.txtField.setText(null);
    }

    @Override
    public SerializerWrite getSerializerWrite() {
        return new SerializerWriteBasic(Datas.OBJECT, this.datasvalue);
    }

    @Override
    public Component getComponent() {
        return this;
    }

    @Override
    public Object createValue() throws BasicException {
        Object value = this.formatsvalue.parseValue(this.txtField.getText());
        this.txtField.setText(this.formatsvalue.formatValue(value));
        if (value == null) {
            return new Object[]{QBFCompareEnum.COMP_NONE, null};
        }
        return new Object[]{this.comparevalue, value};
    }

    private void initComponents() {
        this.lblField = new JLabel();
        this.txtField = new JTextField();
        this.setPreferredSize(new Dimension(0, 30));
        this.setLayout(null);
        this.lblField.setFont(new Font("Arial", 0, 14));
        this.lblField.setText("***");
        this.lblField.setPreferredSize(new Dimension(0, 30));
        this.add(this.lblField);
        this.lblField.setBounds(20, 10, 120, 30);
        this.txtField.setFont(new Font("Arial", 0, 14));
        this.txtField.setPreferredSize(new Dimension(0, 30));
        this.add(this.txtField);
        this.txtField.setBounds(140, 10, 200, 30);
    }
}

