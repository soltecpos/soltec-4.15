/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.maintenance;

import com.openbravo.basic.BasicException;
import com.openbravo.data.gui.JMessageDialog;
import com.openbravo.data.gui.MessageInf;
import com.openbravo.data.loader.Datas;
import com.openbravo.data.loader.PreparedSentence;
import com.openbravo.data.loader.SerializerReadBasic;
import com.openbravo.pos.forms.AppView;
import com.openbravo.pos.forms.BeanFactoryApp;
import com.openbravo.pos.forms.BeanFactoryException;
import com.openbravo.pos.forms.JPanelView;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class JPanelDBMaintenance
extends JPanel
implements JPanelView,
BeanFactoryApp {
    private AppView m_App;

    public JPanelDBMaintenance() {
        this.initComponents();
    }

    @Override
    public void init(AppView app) throws BeanFactoryException {
        this.m_App = app;
    }

    @Override
    public Object getBean() {
        return this;
    }

    @Override
    public String getTitle() {
        return "Mantenimiento DB";
    }

    @Override
    public void activate() throws BasicException {
    }

    @Override
    public boolean deactivate() {
        return true;
    }

    @Override
    public JComponent getComponent() {
        return this;
    }

    private void fixData() {
        try {
            boolean hasPaymentCategories = false;
            try {
                new PreparedSentence(this.m_App.getSession(), "SELECT count(*) FROM PAYMENT_CATEGORIES", null, new SerializerReadBasic(new Datas[]{Datas.INT})).find();
                hasPaymentCategories = true;
            }
            catch (Exception exception) {
                // empty catch block
            }
            if (!hasPaymentCategories) {
                new PreparedSentence(this.m_App.getSession(), "CREATE TABLE PAYMENT_CATEGORIES (ID VARCHAR(255) NOT NULL, NAME VARCHAR(255) NOT NULL, IS_GLOBAL BIT DEFAULT 0, PRIMARY KEY (ID))", null).exec();
                JMessageDialog.showMessage(this, new MessageInf(-83886080, "Created PAYMENT_CATEGORIES table."));
            } else {
                JMessageDialog.showMessage(this, new MessageInf(-83886080, "Table PAYMENT_CATEGORIES already exists."));
            }
            boolean hasCategoryColumn = false;
            try {
                new PreparedSentence(this.m_App.getSession(), "SELECT CATEGORY FROM PAYMENTS WHERE 1=0", null).exec();
                hasCategoryColumn = true;
            }
            catch (Exception exception) {
                // empty catch block
            }
            if (!hasCategoryColumn) {
                new PreparedSentence(this.m_App.getSession(), "ALTER TABLE PAYMENTS ADD COLUMN CATEGORY VARCHAR(255)", null).exec();
                JMessageDialog.showMessage(this, new MessageInf(-83886080, "Added CATEGORY column to PAYMENTS."));
            } else {
                JMessageDialog.showMessage(this, new MessageInf(-83886080, "Column PAYMENTS.CATEGORY already exists."));
            }
            JMessageDialog.showMessage(this, new MessageInf(-83886080, "Database Update Check Completed."));
        }
        catch (BasicException e) {
            JMessageDialog.showMessage(this, new MessageInf(-33554432, "Error updating data: " + e.getMessage()));
        }
    }

    private void initComponents() {
        JLabel jLabel1 = new JLabel();
        JButton btnUpdate = new JButton();
        this.setLayout(null);
        jLabel1.setFont(new Font("Arial", 1, 18));
        jLabel1.setText("Mantenimiento de Base de Datos");
        jLabel1.setBounds(20, 20, 300, 30);
        this.add(jLabel1);
        btnUpdate.setText("Actualizar Estructura DB");
        btnUpdate.setBounds(20, 70, 200, 40);
        btnUpdate.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                JPanelDBMaintenance.this.fixData();
            }
        });
        this.add(btnUpdate);
    }
}

