/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.sf.jasperreports.engine.JRDataSource
 *  net.sf.jasperreports.engine.JREmptyDataSource
 *  net.sf.jasperreports.engine.JRException
 *  net.sf.jasperreports.engine.JasperCompileManager
 *  net.sf.jasperreports.engine.JasperFillManager
 *  net.sf.jasperreports.engine.JasperPrint
 *  net.sf.jasperreports.engine.JasperReport
 *  net.sf.jasperreports.engine.design.JasperDesign
 *  net.sf.jasperreports.engine.xml.JRXmlLoader
 */
package com.openbravo.pos.voucher;

import com.openbravo.data.gui.MessageInf;
import com.openbravo.data.loader.BaseSentence;
import com.openbravo.data.loader.Datas;
import com.openbravo.data.loader.QBFBuilder;
import com.openbravo.data.loader.SerializerReadBasic;
import com.openbravo.data.loader.StaticSentence;
import com.openbravo.pos.forms.AppLocal;
import com.openbravo.pos.forms.AppView;
import com.openbravo.pos.reports.ReportFields;
import com.openbravo.pos.reports.ReportFieldsArray;
import com.openbravo.pos.util.JRViewer400;
import com.openbravo.pos.voucher.VoucherInfo;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Insets;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

public class JDialogReportPanel
extends JDialog {
    private JRViewer400 reportviewer = null;
    private JasperReport jr = null;
    private AppView m_App;
    private List<String> paramnames = new ArrayList<String>();
    private List<Datas> fielddatas = new ArrayList<Datas>();
    private List<String> fieldnames = new ArrayList<String>();
    private String sentence;
    private JButton jButton1;
    private JPanel jPanel1;
    private JPanel jPanel3;
    private JPanel jPanel4;
    private JPanel jPanel8;
    private JButton jcmdCancel;
    private JButton jcmdOK;

    private JDialogReportPanel(Frame parent, boolean modal) {
        super(parent, modal);
    }

    private JDialogReportPanel(Dialog parent, boolean modal) {
        super(parent, modal);
    }

    public static JDialogReportPanel getDialog(Component parent, AppView _App, VoucherInfo voucherInfo, BufferedImage image) {
        Window window = JDialogReportPanel.getWindow(parent);
        JDialogReportPanel myMsg = window instanceof Frame ? new JDialogReportPanel((Frame)window, true) : new JDialogReportPanel((Dialog)window, true);
        myMsg.init(_App, voucherInfo, image);
        myMsg.applyComponentOrientation(parent.getComponentOrientation());
        return myMsg;
    }

    protected BaseSentence<Object, Object[]> getSentence() {
        return new StaticSentence<Object, Object[]>(this.m_App.getSession(), new QBFBuilder(this.sentence, this.paramnames.toArray(new String[this.paramnames.size()])), null, new SerializerReadBasic(this.fielddatas.toArray(new Datas[this.fielddatas.size()])));
    }

    protected ReportFields getReportFields() {
        return new ReportFieldsArray(this.fieldnames.toArray(new String[this.fieldnames.size()]));
    }

    private void launchreport(VoucherInfo voucherInfo, BufferedImage image) {
        if (this.jr != null) {
            try {
                String res = "com/openbravo/reports/voucher_messages";
                HashMap<String, Object> reportparams = new HashMap<String, Object>();
                reportparams.put("CUSTOMER_NAME", voucherInfo.getCustomerName());
                reportparams.put("LOGO", image);
                reportparams.put("CODE", voucherInfo.getVoucherNumber());
                reportparams.put("ISSUED", new Date());
                reportparams.put("VALUE", voucherInfo.getAmount());
                if (res != null) {
                    reportparams.put("REPORT_RESOURCE_BUNDLE", ResourceBundle.getBundle(res));
                }
                JasperPrint jp = JasperFillManager.fillReport((JasperReport)this.jr, reportparams, (JRDataSource)new JREmptyDataSource());
                this.reportviewer.loadJasperPrint(jp);
            }
            catch (MissingResourceException e) {
                MessageInf msg = new MessageInf(-33554432, AppLocal.getIntString("message.cannotloadresourcedata"), e);
                msg.show(this);
            }
            catch (JRException e) {
                MessageInf msg = new MessageInf(-33554432, AppLocal.getIntString("message.cannotfillreport"), (Object)e);
                msg.show(this);
            }
        }
    }

    private void init(AppView _App, VoucherInfo voucherInfo, BufferedImage image) {
        this.m_App = _App;
        this.initComponents();
        this.reportviewer = new JRViewer400(null);
        this.jPanel4.add((Component)this.reportviewer, "Center");
        try {
            JasperDesign jd = JRXmlLoader.load((InputStream)this.getClass().getResourceAsStream(this.getReport() + ".jrxml"));
            this.jr = JasperCompileManager.compileReport((JasperDesign)jd);
        }
        catch (JRException e) {
            MessageInf msg = new MessageInf(-33554432, AppLocal.getIntString("message.cannotloadreport"), (Object)e);
            msg.show(this);
            this.jr = null;
        }
        this.launchreport(voucherInfo, image);
        this.getRootPane().setDefaultButton(this.jcmdOK);
    }

    private static Window getWindow(Component parent) {
        if (parent == null) {
            return new JFrame();
        }
        if (parent instanceof Frame || parent instanceof Dialog) {
            return (Window)parent;
        }
        return JDialogReportPanel.getWindow(parent.getParent());
    }

    private void initComponents() {
        this.jPanel3 = new JPanel();
        this.jPanel4 = new JPanel();
        this.jPanel8 = new JPanel();
        this.jPanel1 = new JPanel();
        this.jcmdOK = new JButton();
        this.jcmdCancel = new JButton();
        this.jButton1 = new JButton();
        this.setDefaultCloseOperation(2);
        this.setTitle(AppLocal.getIntString("form.customertitle"));
        this.setCursor(new Cursor(0));
        this.jPanel3.setLayout(new BorderLayout());
        this.jPanel4.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        this.jPanel4.setLayout(new BorderLayout());
        this.jPanel3.add((Component)this.jPanel4, "Center");
        this.jPanel8.setLayout(new BorderLayout());
        this.jcmdOK.setIcon(new ImageIcon(this.getClass().getResource("/com/openbravo/images/ok.png")));
        this.jcmdOK.setText(AppLocal.getIntString("Button.OK"));
        this.jcmdOK.setEnabled(false);
        this.jcmdOK.setFocusPainted(false);
        this.jcmdOK.setFocusable(false);
        this.jcmdOK.setMargin(new Insets(8, 16, 8, 16));
        this.jcmdOK.setRequestFocusEnabled(false);
        this.jcmdOK.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                JDialogReportPanel.this.jcmdOKActionPerformed(evt);
            }
        });
        this.jPanel1.add(this.jcmdOK);
        this.jcmdCancel.setIcon(new ImageIcon(this.getClass().getResource("/com/openbravo/images/cancel.png")));
        this.jcmdCancel.setText(AppLocal.getIntString("Button.Cancel"));
        this.jcmdCancel.setFocusPainted(false);
        this.jcmdCancel.setFocusable(false);
        this.jcmdCancel.setMargin(new Insets(8, 16, 8, 16));
        this.jcmdCancel.setRequestFocusEnabled(false);
        this.jcmdCancel.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                JDialogReportPanel.this.jcmdCancelActionPerformed(evt);
            }
        });
        this.jPanel1.add(this.jcmdCancel);
        this.jPanel8.add((Component)this.jPanel1, "After");
        this.jButton1.setText("jButton1");
        this.jPanel8.add((Component)this.jButton1, "Center");
        this.jPanel3.add((Component)this.jPanel8, "South");
        this.getContentPane().add((Component)this.jPanel3, "Center");
        this.setSize(new Dimension(837, 687));
        this.setLocationRelativeTo(null);
    }

    private void jcmdOKActionPerformed(ActionEvent evt) {
        this.dispose();
    }

    private void jcmdCancelActionPerformed(ActionEvent evt) {
        this.dispose();
    }

    private String getReport() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}

