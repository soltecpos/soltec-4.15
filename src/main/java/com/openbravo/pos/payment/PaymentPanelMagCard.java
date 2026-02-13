/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  jpos.JposException
 *  jpos.MSR
 *  jpos.MSRControl113
 *  jpos.events.DataEvent
 *  jpos.events.DataListener
 *  jpos.events.ErrorEvent
 *  jpos.events.ErrorListener
 */
package com.openbravo.pos.payment;

import com.openbravo.pos.forms.AppConfig;
import com.openbravo.pos.forms.AppLocal;
import com.openbravo.pos.payment.JPaymentNotifier;
import com.openbravo.pos.payment.MagCardReader;
import com.openbravo.pos.payment.PaymentInfoMagcard;
import com.openbravo.pos.payment.PaymentInfoMagcardRefund;
import com.openbravo.pos.payment.PaymentPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.HeadlessException;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.LayoutStyle;
import javax.swing.UIManager;
import jpos.JposException;
import jpos.MSR;
import jpos.MSRControl113;
import jpos.events.DataEvent;
import jpos.events.DataListener;
import jpos.events.ErrorEvent;
import jpos.events.ErrorListener;

public class PaymentPanelMagCard
extends JPanel
implements PaymentPanel,
DataListener,
ErrorListener {
    private JPaymentNotifier m_notifier;
    private MagCardReader m_cardreader;
    private String track1 = null;
    private String track2 = null;
    private String track3 = null;
    private String m_sTransactionID;
    private double m_dTotal;
    String track1DataTextField = null;
    String track2DataTextField = null;
    String track3DataTextField = null;
    String additionalSecurityInformationTextField = null;
    String track1DataEncryptedTextField = null;
    String track2DataEncryptedTextField = null;
    String track3DataEncryptedTextField = null;
    String infoLine = "";
    String title = "";
    String resultValue = "";
    boolean enabled = false;
    boolean openDevice = false;
    boolean init = true;
    String logicalDeviceName = "MagTekMSR_Encrypted";
    String encryptedCardData = null;
    String encryptionKey = null;
    boolean isMercury;
    MSRControl113 msr = null;
    private JLabel jLabel1;
    private JLabel jLabel6;
    private JLabel jLabel7;
    private JLabel jLabel8;
    private JPanel jPanel1;
    private JPanel jPanel2;
    private JButton jReset;
    private JLabel m_jCardNumber;
    private JLabel m_jExpirationDate;
    private JLabel m_jHolderName;
    private JTextArea m_jKeyFactory;

    public PaymentPanelMagCard(MagCardReader cardreader, JPaymentNotifier notifier) {
        this.m_notifier = notifier;
        this.m_cardreader = cardreader;
        this.initComponents();
        AppConfig config = new AppConfig(new File(new File(System.getProperty("user.home")), "soltecpos.properties"));
        config.load();
        String payProcessor = config.getProperty("payment.gateway");
        this.isMercury = "MercuryPay".equals(payProcessor);
        if (this.m_cardreader != null) {
            this.m_jKeyFactory.addKeyListener(new KeyBarsListener());
            this.jReset.setEnabled(true);
        } else {
            this.jReset.setEnabled(false);
        }
        this.msr = new MSR();
        this.msr.addDataListener((DataListener)this);
        this.msr.addErrorListener((ErrorListener)this);
        if (this.isMercury) {
            this.processMSRSession();
        }
    }

    private void processMSRSession() {
        if (this.logicalDeviceName != null && !"".equals(this.logicalDeviceName)) {
            if (this.openDevice) {
                try {
                    this.msr.close();
                }
                catch (JposException localJposException1) {
                    JOptionPane.showMessageDialog(this, "Error Occured " + localJposException1.getMessage());
                }
            }
            try {
                this.msr.open(this.logicalDeviceName);
                this.openDevice = true;
            }
            catch (JposException localJposException2) {
                JOptionPane.showMessageDialog(this, "Error Occured " + localJposException2.getMessage());
            }
            this.clear();
            try {
                this.msr.claim(1000);
            }
            catch (JposException localJposException3) {
                JOptionPane.showMessageDialog(this, "Error Occured " + localJposException3.getMessage());
            }
            try {
                this.msr.setDataEventEnabled(true);
            }
            catch (JposException localJposException5) {
                JOptionPane.showMessageDialog(this, "Error Occured " + localJposException5.getMessage());
            }
            try {
                this.msr.setDeviceEnabled(true);
                this.enabled = true;
            }
            catch (JposException localJposException4) {
                this.enabled = false;
                JOptionPane.showMessageDialog(this, "Error Occured " + localJposException4.getMessage());
            }
        }
    }

    public void clear() {
        this.track1DataTextField = "";
        this.track2DataTextField = "";
        this.track3DataTextField = "";
        this.additionalSecurityInformationTextField = "";
        this.track1DataEncryptedTextField = "";
        this.track2DataEncryptedTextField = "";
        this.track3DataEncryptedTextField = "";
    }

    public void dataOccurred(DataEvent paramDataEvent) {
        String str = "";
        byte[] arrayOfByte = new byte[]{};
        try {
            arrayOfByte = this.msr.getTrack1Data();
            str = new String(arrayOfByte);
            if (str.length() == 0) {
                str = "";
            }
            this.track1DataTextField = str;
            arrayOfByte = this.msr.getTrack2Data();
            str = new String(arrayOfByte);
            if (str.length() == 0) {
                str = "";
            }
            this.track2DataTextField = str;
            arrayOfByte = this.msr.getTrack3Data();
            str = new String(arrayOfByte);
            if (str.length() == 0) {
                str = "";
            }
            this.track3DataTextField = str;
            arrayOfByte = this.msr.getAdditionalSecurityInformation();
            str = new String(arrayOfByte);
            if (str.length() == 0) {
                str = "";
            }
            this.additionalSecurityInformationTextField = str;
            arrayOfByte = this.msr.getTrack1EncryptedData();
            str = new String(arrayOfByte);
            if (str.length() == 0) {
                str = "";
            }
            this.track1DataEncryptedTextField = str;
            arrayOfByte = this.msr.getTrack2EncryptedData();
            str = new String(arrayOfByte);
            if (str.length() == 0) {
                str = "";
            }
            this.track2DataEncryptedTextField = str;
            arrayOfByte = this.msr.getTrack3EncryptedData();
            str = new String(arrayOfByte);
            if (str.length() == 0) {
                str = "";
            }
            this.track3DataEncryptedTextField = str;
            this.msr.setDataEventEnabled(true);
            this.encryptedCardData = this.track2DataEncryptedTextField;
            this.encryptionKey = this.additionalSecurityInformationTextField;
            char[] cData = ("%" + this.track1DataTextField + "?;" + this.track2DataTextField + "?").toCharArray();
            if (cData.length > 0) {
                for (int i = 0; i < cData.length; ++i) {
                    this.stateTransition(cData[i]);
                }
            }
        }
        catch (JposException localJposException) {
            this.processJposException(localJposException, "");
        }
    }

    public void errorOccurred(ErrorEvent paramErrorEvent) {
        String str1 = Integer.toString(paramErrorEvent.getErrorCode());
        String str2 = Integer.toString(paramErrorEvent.getErrorCodeExtended());
        String str3 = Integer.toString(paramErrorEvent.getErrorLocus());
        String str4 = Integer.toString(paramErrorEvent.getErrorResponse());
        JOptionPane.showMessageDialog(this, "ErrorEvent: EC=" + str1 + ", ECE=" + str2 + ", EL=" + str3 + ", ER=" + str4, this.title, 0);
    }

    private void processJposException(JposException paramJposException, String paramString) {
        JOptionPane.showMessageDialog(this, "Exception...message = " + paramJposException.getMessage() + " with errorCode = " + paramJposException.getErrorCode() + ", errorCodeExtended = " + paramJposException.getErrorCodeExtended(), this.title, 0);
    }

    @Override
    public JComponent getComponent() {
        return this;
    }

    @Override
    public void activate(String sTransaction, double dTotal) {
        this.m_sTransactionID = sTransaction;
        this.m_dTotal = dTotal;
        this.resetState();
        this.m_jKeyFactory.setText(null);
        EventQueue.invokeLater(new Runnable(){

            @Override
            public void run() {
                PaymentPanelMagCard.this.m_jKeyFactory.requestFocus();
            }
        });
    }

    private void resetState() {
        this.m_notifier.setStatus(false, false);
        this.m_jHolderName.setText(null);
        this.m_jCardNumber.setText(null);
        this.m_jExpirationDate.setText(null);
        this.track1 = null;
        this.track2 = null;
        this.track3 = null;
        if (this.m_cardreader != null) {
            this.m_cardreader.reset();
        }
    }

    @Override
    public PaymentInfoMagcard getPaymentInfoMagcard() {
        if (this.m_dTotal > 0.0) {
            return new PaymentInfoMagcard(this.m_jHolderName.getText(), this.m_jCardNumber.getText(), this.m_jExpirationDate.getText(), this.track1, this.track2, this.track3, this.encryptedCardData, this.encryptionKey, this.m_sTransactionID, this.m_dTotal);
        }
        return new PaymentInfoMagcardRefund(this.m_jHolderName.getText(), this.m_jCardNumber.getText(), this.m_jExpirationDate.getText(), this.track1, this.track2, this.track3, this.encryptedCardData, this.encryptionKey, this.m_sTransactionID, this.m_dTotal);
    }

    private void stateTransition(char cTrans) {
        this.m_cardreader.appendChar(cTrans);
        if (this.m_cardreader.isComplete()) {
            this.m_jHolderName.setText(this.m_cardreader.getHolderName());
            this.m_jCardNumber.setText(this.m_cardreader.getCardNumber());
            this.m_jExpirationDate.setText(this.m_cardreader.getExpirationDate());
            this.track1 = this.m_cardreader.getTrack1();
            this.track2 = this.m_cardreader.getTrack2();
            this.track3 = this.m_cardreader.getTrack3();
            this.m_notifier.setStatus(true, true);
            this.m_jKeyFactory.addKeyListener(new KeyBarsListener());
            this.jReset.setEnabled(true);
        } else {
            this.m_jHolderName.setText(null);
            this.m_jCardNumber.setText(null);
            this.m_jExpirationDate.setText(null);
            this.track1 = null;
            this.track3 = null;
            this.track3 = null;
            this.m_notifier.setStatus(false, false);
        }
    }

    private void initComponents() {
        this.jPanel2 = new JPanel();
        this.jLabel1 = new JLabel();
        this.jPanel1 = new JPanel();
        this.jReset = new JButton();
        this.m_jKeyFactory = new JTextArea();
        this.jLabel6 = new JLabel();
        this.jLabel7 = new JLabel();
        this.m_jExpirationDate = new JLabel();
        this.m_jCardNumber = new JLabel();
        this.jLabel8 = new JLabel();
        this.m_jHolderName = new JLabel();
        this.setLayout(new BorderLayout());
        this.jLabel1.setFont(new Font("Arial", 0, 18));
        this.jLabel1.setHorizontalAlignment(0);
        this.jLabel1.setText(AppLocal.getIntString("message.paymentgatewayswipe"));
        this.jLabel1.setPreferredSize(new Dimension(263, 30));
        this.jPanel2.add(this.jLabel1);
        this.add((Component)this.jPanel2, "North");
        this.jReset.setFont(new Font("Arial", 0, 11));
        this.jReset.setIcon(new ImageIcon(this.getClass().getResource("/com/openbravo/images/reload.png")));
        this.jReset.setText(AppLocal.getIntString("button.reset"));
        this.jReset.setFocusPainted(false);
        this.jReset.setFocusable(false);
        this.jReset.setPreferredSize(new Dimension(100, 45));
        this.jReset.setRequestFocusEnabled(false);
        this.jReset.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                PaymentPanelMagCard.this.jResetActionPerformed(evt);
            }
        });
        this.m_jKeyFactory.setPreferredSize(new Dimension(1, 1));
        this.jLabel6.setFont(new Font("Arial", 0, 14));
        this.jLabel6.setText(AppLocal.getIntString("label.cardnumber"));
        this.jLabel6.setPreferredSize(new Dimension(150, 30));
        this.jLabel7.setFont(new Font("Arial", 0, 14));
        this.jLabel7.setText(AppLocal.getIntString("label.cardexpdate"));
        this.jLabel7.setPreferredSize(new Dimension(150, 30));
        this.m_jExpirationDate.setFont(new Font("Arial", 0, 14));
        this.m_jExpirationDate.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(UIManager.getDefaults().getColor("Button.darkShadow")), BorderFactory.createEmptyBorder(1, 4, 1, 4)));
        this.m_jExpirationDate.setOpaque(true);
        this.m_jExpirationDate.setPreferredSize(new Dimension(180, 30));
        this.m_jCardNumber.setFont(new Font("Arial", 0, 14));
        this.m_jCardNumber.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(UIManager.getDefaults().getColor("Button.darkShadow")), BorderFactory.createEmptyBorder(1, 4, 1, 4)));
        this.m_jCardNumber.setOpaque(true);
        this.m_jCardNumber.setPreferredSize(new Dimension(200, 30));
        this.jLabel8.setFont(new Font("Arial", 0, 14));
        this.jLabel8.setText(AppLocal.getIntString("label.cardholder"));
        this.jLabel8.setPreferredSize(new Dimension(150, 30));
        this.m_jHolderName.setBackground(Color.white);
        this.m_jHolderName.setFont(new Font("Arial", 0, 14));
        this.m_jHolderName.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(UIManager.getDefaults().getColor("Button.darkShadow")), BorderFactory.createEmptyBorder(1, 4, 1, 4)));
        this.m_jHolderName.setOpaque(true);
        this.m_jHolderName.setPreferredSize(new Dimension(200, 30));
        GroupLayout jPanel1Layout = new GroupLayout(this.jPanel1);
        this.jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel1Layout.createSequentialGroup().addComponent(this.m_jKeyFactory, -2, -1, -2).addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel1Layout.createSequentialGroup().addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jLabel6, -2, -1, -2).addComponent(this.jLabel7, -2, -1, -2))).addGroup(jPanel1Layout.createSequentialGroup().addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jLabel8, -2, -1, -2))).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel1Layout.createSequentialGroup().addComponent(this.m_jExpirationDate, -2, 70, -2).addGap(165, 165, 165)).addGroup(jPanel1Layout.createSequentialGroup().addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.TRAILING).addComponent(this.m_jHolderName, -2, -1, -2).addComponent(this.m_jCardNumber, GroupLayout.Alignment.LEADING, -2, -1, -2)).addGap(18, 18, 18).addComponent(this.jReset, -2, -1, -2).addGap(22, 22, 22)))));
        jPanel1Layout.setVerticalGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel1Layout.createSequentialGroup().addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.m_jKeyFactory, -2, -1, -2).addGroup(jPanel1Layout.createSequentialGroup().addContainerGap().addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jReset, -2, -1, -2).addGroup(jPanel1Layout.createSequentialGroup().addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jLabel8, -2, -1, -2).addComponent(this.m_jHolderName, -2, -1, -2)).addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel1Layout.createSequentialGroup().addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(this.jLabel6, -2, -1, -2)).addGroup(jPanel1Layout.createSequentialGroup().addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(this.m_jCardNumber, -2, -1, -2))))).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.m_jExpirationDate, -2, -1, -2).addComponent(this.jLabel7, -2, -1, -2)))).addGap(17, 17, 17)));
        this.add((Component)this.jPanel1, "Center");
    }

    private void jResetActionPerformed(ActionEvent evt) {
        this.resetState();
    }

    private class KeyBarsListener
    extends KeyAdapter {
        private KeyBarsListener() {
        }

        @Override
        public void keyTyped(KeyEvent e) {
            PaymentPanelMagCard.this.m_jKeyFactory.setText(null);
            PaymentPanelMagCard.this.stateTransition(e.getKeyChar());
        }
    }

    class XmlDialog
    extends JDialog {
        JScrollPane jScrollPane = new JScrollPane();
        JTextArea jTextArea = new JTextArea();
        GridBagLayout gridBagLayout1 = new GridBagLayout();

        public XmlDialog(String arg2) throws HeadlessException {
            try {
                this.jaInit();
                String str = "";
                this.jTextArea.append(str);
            }
            catch (Exception localException) {
                localException.printStackTrace();
            }
        }

        private void jaInit() throws Exception {
            this.getContentPane().setLayout(this.gridBagLayout1);
            this.jTextArea.setColumns(16);
            this.jTextArea.setRows(18);
            this.getContentPane().add((Component)this.jScrollPane, new GridBagConstraints(0, 0, 2, 1, 1.0, 1.0, 10, 1, new Insets(15, 17, 0, 15), 364, 213));
            this.jScrollPane.getViewport().add((Component)this.jTextArea, null);
        }
    }
}

