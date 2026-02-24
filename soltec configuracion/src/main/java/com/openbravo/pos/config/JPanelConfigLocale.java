/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.config;

import com.openbravo.data.user.DirtyManager;
import com.openbravo.pos.config.LocaleComparator;
import com.openbravo.pos.config.PanelConfig;
import com.openbravo.pos.forms.AppConfig;
import com.openbravo.pos.forms.AppLocal;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import javax.swing.GroupLayout;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.LayoutStyle;

public class JPanelConfigLocale
extends JPanel
implements PanelConfig {
    private final DirtyManager dirty = new DirtyManager();
    private static final String DEFAULT_VALUE = "(Default)";
    private JLabel jLabel1;
    private JLabel jLabel2;
    private JLabel jLabel3;
    private JLabel jLabel4;
    private JLabel jLabel5;
    private JLabel jLabel6;
    private JLabel jLabel7;
    private JLabel jLabel8;
    private JPanel jPanel1;
    private JComboBox<String> jcboCurrency;
    private JComboBox<String> jcboDate;
    private JComboBox<String> jcboDatetime;
    private JComboBox<String> jcboDouble;
    private JComboBox<String> jcboInteger;
    private JComboBox<LocaleInfo> jcboLocale;
    private JComboBox<String> jcboPercent;
    private JComboBox<String> jcboTime;

    public JPanelConfigLocale() {
        this.initComponents();
        this.jcboLocale.addActionListener(this.dirty);
        this.jcboInteger.addActionListener(this.dirty);
        this.jcboDouble.addActionListener(this.dirty);
        this.jcboCurrency.addActionListener(this.dirty);
        this.jcboPercent.addActionListener(this.dirty);
        this.jcboDate.addActionListener(this.dirty);
        this.jcboTime.addActionListener(this.dirty);
        this.jcboDatetime.addActionListener(this.dirty);
        ArrayList<Locale> availablelocales = new ArrayList<Locale>();
        availablelocales.addAll(Arrays.asList(Locale.getAvailableLocales()));
        Collections.sort(availablelocales, new LocaleComparator());
        this.jcboLocale.addItem(new LocaleInfo(null));
        for (Locale l : availablelocales) {
            this.jcboLocale.addItem(new LocaleInfo(l));
        }
        this.jcboInteger.addItem(DEFAULT_VALUE);
        this.jcboInteger.addItem("#0");
        this.jcboInteger.addItem("#,##0");
        this.jcboDouble.addItem(DEFAULT_VALUE);
        this.jcboDouble.addItem("#0.0");
        this.jcboDouble.addItem("#,##0.#");
        this.jcboCurrency.addItem(DEFAULT_VALUE);
        this.jcboCurrency.addItem("\u00a4 #0.00");
        this.jcboCurrency.addItem("'$' #,##0.00");
        this.jcboPercent.addItem(DEFAULT_VALUE);
        this.jcboPercent.addItem("#,##0.##%");
        this.jcboDate.addItem(DEFAULT_VALUE);
        this.jcboTime.addItem(DEFAULT_VALUE);
        this.jcboDatetime.addItem(DEFAULT_VALUE);
    }

    private void addLocale(List<Locale> ll, Locale l) {
        if (!ll.contains(l)) {
            ll.add(l);
        }
    }

    @Override
    public boolean hasChanged() {
        return this.dirty.isDirty();
    }

    @Override
    public Component getConfigComponent() {
        return this;
    }

    @Override
    public void loadProperties(AppConfig config) {
        String slang = config.getProperty("user.language");
        String scountry = config.getProperty("user.country");
        String svariant = config.getProperty("user.variant");
        if (slang != null && !slang.equals("") && scountry != null && svariant != null) {
            Locale currentlocale = new Locale(slang, scountry, svariant);
            for (int i = 0; i < this.jcboLocale.getItemCount(); ++i) {
                LocaleInfo l = this.jcboLocale.getItemAt(i);
                if (!currentlocale.equals(l.getLocale())) continue;
                this.jcboLocale.setSelectedIndex(i);
                break;
            }
        } else {
            this.jcboLocale.setSelectedIndex(0);
        }
        this.jcboInteger.setSelectedItem(this.writeWithDefault(config.getProperty("format.integer")));
        this.jcboDouble.setSelectedItem(this.writeWithDefault(config.getProperty("format.double")));
        this.jcboCurrency.setSelectedItem(this.writeWithDefault(config.getProperty("format.currency")));
        this.jcboPercent.setSelectedItem(this.writeWithDefault(config.getProperty("format.percent")));
        this.jcboDate.setSelectedItem(this.writeWithDefault(config.getProperty("format.date")));
        this.jcboTime.setSelectedItem(this.writeWithDefault(config.getProperty("format.time")));
        this.jcboDatetime.setSelectedItem(this.writeWithDefault(config.getProperty("format.datetime")));
        this.dirty.setDirty(false);
    }

    @Override
    public void saveProperties(AppConfig config) {
        Locale l = ((LocaleInfo)this.jcboLocale.getSelectedItem()).getLocale();
        if (l == null) {
            config.setProperty("user.language", "");
            config.setProperty("user.country", "");
            config.setProperty("user.variant", "");
        } else {
            config.setProperty("user.language", l.getLanguage());
            config.setProperty("user.country", l.getCountry());
            config.setProperty("user.variant", l.getVariant());
        }
        config.setProperty("format.integer", this.readWithDefault(this.jcboInteger.getSelectedItem()));
        config.setProperty("format.double", this.readWithDefault(this.jcboDouble.getSelectedItem()));
        config.setProperty("format.currency", this.readWithDefault(this.jcboCurrency.getSelectedItem()));
        config.setProperty("format.percent", this.readWithDefault(this.jcboPercent.getSelectedItem()));
        config.setProperty("format.date", this.readWithDefault(this.jcboDate.getSelectedItem()));
        config.setProperty("format.time", this.readWithDefault(this.jcboTime.getSelectedItem()));
        config.setProperty("format.datetime", this.readWithDefault(this.jcboDatetime.getSelectedItem()));
        this.dirty.setDirty(false);
    }

    private String readWithDefault(Object value) {
        if (DEFAULT_VALUE.equals(value)) {
            return "";
        }
        return value.toString();
    }

    private Object writeWithDefault(String value) {
        if (value == null || value.equals("") || value.equals(DEFAULT_VALUE)) {
            return DEFAULT_VALUE;
        }
        return value;
    }

    private void initComponents() {
        this.jPanel1 = new JPanel();
        this.jLabel5 = new JLabel();
        this.jcboLocale = new JComboBox();
        this.jLabel1 = new JLabel();
        this.jcboInteger = new JComboBox();
        this.jLabel2 = new JLabel();
        this.jcboDouble = new JComboBox();
        this.jLabel3 = new JLabel();
        this.jcboCurrency = new JComboBox();
        this.jLabel4 = new JLabel();
        this.jcboPercent = new JComboBox();
        this.jLabel6 = new JLabel();
        this.jcboDate = new JComboBox();
        this.jLabel7 = new JLabel();
        this.jcboTime = new JComboBox();
        this.jLabel8 = new JLabel();
        this.jcboDatetime = new JComboBox();
        this.setBackground(new Color(255, 255, 255));
        this.setMinimumSize(new Dimension(0, 0));
        this.setPreferredSize(new Dimension(650, 450));
        this.jPanel1.setBackground(new Color(255, 255, 255));
        this.jPanel1.setPreferredSize(new Dimension(600, 400));
        this.jLabel5.setFont(new Font("Arial", 0, 14));
        this.jLabel5.setText(AppLocal.getIntString("label.locale"));
        this.jLabel5.setPreferredSize(new Dimension(150, 30));
        this.jcboLocale.setFont(new Font("Arial", 0, 14));
        this.jcboLocale.setPreferredSize(new Dimension(0, 30));
        this.jLabel1.setFont(new Font("Arial", 0, 14));
        this.jLabel1.setText(AppLocal.getIntString("label.integer"));
        this.jLabel1.setPreferredSize(new Dimension(150, 30));
        this.jcboInteger.setEditable(true);
        this.jcboInteger.setFont(new Font("Arial", 0, 14));
        this.jcboInteger.setPreferredSize(new Dimension(0, 30));
        this.jLabel2.setFont(new Font("Arial", 0, 14));
        this.jLabel2.setText(AppLocal.getIntString("label.double"));
        this.jLabel2.setPreferredSize(new Dimension(150, 30));
        this.jcboDouble.setEditable(true);
        this.jcboDouble.setFont(new Font("Arial", 0, 14));
        this.jcboDouble.setPreferredSize(new Dimension(0, 30));
        this.jLabel3.setFont(new Font("Arial", 0, 14));
        this.jLabel3.setText(AppLocal.getIntString("label.currency"));
        this.jLabel3.setPreferredSize(new Dimension(150, 30));
        this.jcboCurrency.setEditable(true);
        this.jcboCurrency.setFont(new Font("Arial", 0, 14));
        this.jcboCurrency.setPreferredSize(new Dimension(0, 30));
        this.jLabel4.setFont(new Font("Arial", 0, 14));
        this.jLabel4.setText(AppLocal.getIntString("label.percent"));
        this.jLabel4.setPreferredSize(new Dimension(150, 30));
        this.jcboPercent.setEditable(true);
        this.jcboPercent.setFont(new Font("Arial", 0, 14));
        this.jcboPercent.setPreferredSize(new Dimension(0, 30));
        this.jLabel6.setFont(new Font("Arial", 0, 14));
        this.jLabel6.setText(AppLocal.getIntString("label.date"));
        this.jLabel6.setPreferredSize(new Dimension(150, 30));
        this.jcboDate.setEditable(true);
        this.jcboDate.setFont(new Font("Arial", 0, 14));
        this.jcboDate.setPreferredSize(new Dimension(0, 30));
        this.jLabel7.setFont(new Font("Arial", 0, 14));
        this.jLabel7.setText(AppLocal.getIntString("label.time"));
        this.jLabel7.setPreferredSize(new Dimension(150, 30));
        this.jcboTime.setEditable(true);
        this.jcboTime.setFont(new Font("Arial", 0, 14));
        this.jcboTime.setPreferredSize(new Dimension(0, 30));
        this.jLabel8.setFont(new Font("Arial", 0, 14));
        this.jLabel8.setText(AppLocal.getIntString("label.datetime"));
        this.jLabel8.setPreferredSize(new Dimension(150, 30));
        this.jcboDatetime.setEditable(true);
        this.jcboDatetime.setFont(new Font("Arial", 0, 14));
        this.jcboDatetime.setPreferredSize(new Dimension(0, 30));
        GroupLayout jPanel1Layout = new GroupLayout(this.jPanel1);
        this.jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel1Layout.createSequentialGroup().addContainerGap().addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel1Layout.createSequentialGroup().addComponent(this.jLabel8, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(this.jcboDatetime, -2, 200, -2)).addGroup(jPanel1Layout.createSequentialGroup().addComponent(this.jLabel7, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(this.jcboTime, -2, 200, -2)).addGroup(jPanel1Layout.createSequentialGroup().addComponent(this.jLabel6, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(this.jcboDate, -2, 200, -2)).addGroup(jPanel1Layout.createSequentialGroup().addComponent(this.jLabel4, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(this.jcboPercent, -2, 200, -2)).addGroup(jPanel1Layout.createSequentialGroup().addComponent(this.jLabel3, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(this.jcboCurrency, -2, 200, -2)).addGroup(jPanel1Layout.createSequentialGroup().addComponent(this.jLabel2, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(this.jcboDouble, -2, 200, -2)).addGroup(jPanel1Layout.createSequentialGroup().addComponent(this.jLabel1, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(this.jcboInteger, -2, 200, -2)).addGroup(jPanel1Layout.createSequentialGroup().addComponent(this.jLabel5, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(this.jcboLocale, -2, 200, -2))).addGap(51, 51, 51)));
        jPanel1Layout.setVerticalGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel1Layout.createSequentialGroup().addContainerGap().addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jcboLocale, -2, -1, -2).addComponent(this.jLabel5, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel1, -2, -1, -2).addComponent(this.jcboInteger, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel2, -2, -1, -2).addComponent(this.jcboDouble, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel3, -2, -1, -2).addComponent(this.jcboCurrency, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel4, -2, -1, -2).addComponent(this.jcboPercent, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel6, -2, -1, -2).addComponent(this.jcboDate, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel7, -2, -1, -2).addComponent(this.jcboTime, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel8, -2, -1, -2).addComponent(this.jcboDatetime, -2, -1, -2)).addContainerGap(100, Short.MAX_VALUE)));
        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addComponent(this.jPanel1, -2, -1, -2).addContainerGap(200, Short.MAX_VALUE)));
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGap(2, 2, 2).addComponent(this.jPanel1, -2, -1, -2).addContainerGap(148, Short.MAX_VALUE)));
    }

    private static class LocaleInfo {
        private final Locale locale;

        public LocaleInfo(Locale locale) {
            this.locale = locale;
        }

        public Locale getLocale() {
            return this.locale;
        }

        public String toString() {
            return this.locale == null ? "(System default)" : this.locale.getDisplayName();
        }
    }
}

