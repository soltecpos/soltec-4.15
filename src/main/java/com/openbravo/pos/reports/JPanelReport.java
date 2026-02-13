/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.sf.jasperreports.engine.JRDataSource
 *  net.sf.jasperreports.engine.JRException
 *  net.sf.jasperreports.engine.JasperCompileManager
 *  net.sf.jasperreports.engine.JasperFillManager
 *  net.sf.jasperreports.engine.JasperPrint
 *  net.sf.jasperreports.engine.JasperReport
 *  net.sf.jasperreports.engine.design.JasperDesign
 *  net.sf.jasperreports.engine.xml.JRXmlLoader
 */
package com.openbravo.pos.reports;

import com.openbravo.basic.BasicException;
import com.openbravo.data.gui.MessageInf;
import com.openbravo.data.loader.BaseSentence;
import com.openbravo.data.loader.SentenceList;
import com.openbravo.data.loader.Session;
import com.openbravo.data.user.EditorCreator;
import com.openbravo.pos.forms.AppLocal;
import com.openbravo.pos.forms.AppView;
import com.openbravo.pos.forms.BeanFactoryApp;
import com.openbravo.pos.forms.BeanFactoryException;
import com.openbravo.pos.forms.DataLogicSales;
import com.openbravo.pos.forms.JPanelView;
import com.openbravo.pos.reports.JRDataSourceBasic;
import com.openbravo.pos.reports.ReportEditorCreator;
import com.openbravo.pos.reports.ReportFields;
import com.openbravo.pos.sales.TaxesLogic;
import com.openbravo.pos.ticket.TaxInfo;
import com.openbravo.pos.util.JRViewer400;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.sql.Connection;
import java.util.HashMap;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

public abstract class JPanelReport
extends JPanel
implements JPanelView,
BeanFactoryApp {
    private JRViewer400 reportviewer = null;
    private JasperReport jr = null;
    private EditorCreator editor = null;
    protected AppView m_App;
    private Session s;
    private Connection con;
    protected SentenceList<TaxInfo> taxsent;
    protected TaxesLogic taxeslogic;
    private JButton jButton1;
    private JPanel jPanel1;
    private JPanel jPanelFilter;
    private JPanel jPanelHeader;
    private JToggleButton jToggleFilter;

    public JPanelReport() {
        this.initComponents();
    }

    @Override
    public void init(AppView app) throws BeanFactoryException {
        block9: {
            this.m_App = app;
            DataLogicSales dlSales2 = (DataLogicSales)app.getBean("com.openbravo.pos.forms.DataLogicSales");
            this.taxsent = dlSales2.getTaxList();
            this.editor = this.getEditorCreator();
            if (this.editor instanceof ReportEditorCreator) {
                this.jPanelFilter.add(((ReportEditorCreator)this.editor).getComponent(), "Center");
            }
            this.reportviewer = new JRViewer400(null);
            this.add((Component)this.reportviewer, "Center");
            try {
                InputStream in = this.getClass().getResourceAsStream(this.getReport() + ".ser");
                if (in == null) {
                    JasperDesign jd = JRXmlLoader.load((InputStream)this.getClass().getResourceAsStream(this.getReport() + ".jrxml"));
                    this.jr = JasperCompileManager.compileReport((JasperDesign)jd);
                    break block9;
                }
                try (ObjectInputStream oin = new ObjectInputStream(in);){
                    this.jr = (JasperReport)oin.readObject();
                }
            }
            catch (IOException | ClassNotFoundException | JRException e) {
                MessageInf msg = new MessageInf(-33554432, AppLocal.getIntString("message.cannotloadreport"), e);
                msg.show(this);
                this.jr = null;
            }
        }
    }

    @Override
    public Object getBean() {
        return this;
    }

    protected abstract String getReport();

    protected abstract String getResourceBundle();

    protected abstract BaseSentence getSentence();

    protected abstract ReportFields getReportFields();

    protected EditorCreator getEditorCreator() {
        return null;
    }

    @Override
    public JComponent getComponent() {
        return this;
    }

    @Override
    public void activate() throws BasicException {
        this.setVisibleFilter(true);
        this.taxeslogic = new TaxesLogic(this.taxsent.list());
    }

    @Override
    public boolean deactivate() {
        this.reportviewer.loadJasperPrint(null);
        return true;
    }

    protected void setVisibleButtonFilter(boolean value) {
        this.jToggleFilter.setVisible(value);
    }

    protected void setVisibleFilter(boolean value) {
        this.jToggleFilter.setSelected(value);
        this.jToggleFilterActionPerformed(null);
    }

    private void launchreport() {
        this.m_App.waitCursorBegin();
        if (this.jr != null) {
            try {
                String res = this.getResourceBundle();
                Object params = this.editor == null ? null : this.editor.createValue();
                JRDataSourceBasic data = new JRDataSourceBasic(this.getSentence(), this.getReportFields(), params);
                HashMap<String, Object> reportparams = new HashMap<String, Object>();
                reportparams.put("ARG", params);
                if (res != null) {
                    reportparams.put("REPORT_RESOURCE_BUNDLE", ResourceBundle.getBundle(res));
                }
                reportparams.put("TAXESLOGIC", this.taxeslogic);
                JasperPrint jp = JasperFillManager.fillReport((JasperReport)this.jr, reportparams, (JRDataSource)data);
                this.reportviewer.loadJasperPrint(jp);
                this.setVisibleFilter(false);
            }
            catch (MissingResourceException e) {
                MessageInf msg = new MessageInf(-33554432, AppLocal.getIntString("message.cannotloadresourcedata"), e);
                msg.show(this);
            }
            catch (JRException e) {
                MessageInf msg = new MessageInf(-33554432, AppLocal.getIntString("message.cannotfillreport"), (Object)e);
                msg.show(this);
            }
            catch (BasicException e) {
                MessageInf msg = new MessageInf(-33554432, AppLocal.getIntString("message.cannotloadreportdata"), e);
                msg.show(this);
            }
        }
        this.m_App.waitCursorEnd();
    }

    private void initComponents() {
        this.jPanelHeader = new JPanel();
        this.jPanelFilter = new JPanel();
        this.jPanel1 = new JPanel();
        this.jToggleFilter = new JToggleButton();
        this.jButton1 = new JButton();
        this.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        this.setFont(new Font("Arial", 0, 12));
        this.setLayout(new BorderLayout());
        this.jPanelHeader.setLayout(new BorderLayout());
        this.jPanelFilter.setLayout(new BorderLayout());
        this.jPanelHeader.add((Component)this.jPanelFilter, "Center");
        this.jPanel1.setLayout(new FlowLayout(2));
        this.jToggleFilter.setIcon(new ImageIcon(this.getClass().getResource("/com/openbravo/images/1downarrow.png")));
        this.jToggleFilter.setSelected(true);
        this.jToggleFilter.setToolTipText("Hide/Show Filter");
        this.jToggleFilter.setPreferredSize(new Dimension(80, 45));
        this.jToggleFilter.setSelectedIcon(new ImageIcon(this.getClass().getResource("/com/openbravo/images/1uparrow.png")));
        this.jToggleFilter.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                JPanelReport.this.jToggleFilterActionPerformed(evt);
            }
        });
        this.jPanel1.add(this.jToggleFilter);
        this.jButton1.setFont(new Font("Arial", 0, 12));
        this.jButton1.setIcon(new ImageIcon(this.getClass().getResource("/com/openbravo/images/ok.png")));
        this.jButton1.setText(AppLocal.getIntString("button.executereport"));
        this.jButton1.setToolTipText("Execute Report");
        this.jButton1.setPreferredSize(new Dimension(150, 45));
        this.jButton1.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                JPanelReport.this.jButton1ActionPerformed(evt);
            }
        });
        this.jPanel1.add(this.jButton1);
        this.jPanelHeader.add((Component)this.jPanel1, "South");
        this.add((Component)this.jPanelHeader, "North");
    }

    private void jButton1ActionPerformed(ActionEvent evt) {
        this.launchreport();
    }

    private void jToggleFilterActionPerformed(ActionEvent evt) {
        this.jPanelFilter.setVisible(this.jToggleFilter.isSelected());
    }
}

