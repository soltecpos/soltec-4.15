/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.inventory;

import com.openbravo.data.gui.MessageInf;
import com.openbravo.data.user.BrowsableEditableData;
import com.openbravo.pos.forms.AppLocal;
import com.openbravo.pos.scanpal2.DeviceScanner;
import com.openbravo.pos.scanpal2.DeviceScannerException;
import java.awt.Component;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.ListModel;

public class JDlgUploadProducts
extends JDialog {
    private DeviceScanner m_scanner;
    private BrowsableEditableData m_bd;
    private JLabel jLabel1;
    private JPanel jPanel1;
    private JPanel jPanel2;
    private JButton jcmdCancel;
    private JButton jcmdOK;

    private JDlgUploadProducts(Frame parent, boolean modal) {
        super(parent, modal);
    }

    private JDlgUploadProducts(Dialog parent, boolean modal) {
        super(parent, modal);
    }

    private void init(DeviceScanner scanner, BrowsableEditableData bd) {
        this.initComponents();
        this.getRootPane().setDefaultButton(this.jcmdOK);
        this.m_scanner = scanner;
        this.m_bd = bd;
        this.setVisible(true);
    }

    private static Window getWindow(Component parent) {
        if (parent == null) {
            return new JFrame();
        }
        if (parent instanceof Frame || parent instanceof Dialog) {
            return (Window)parent;
        }
        return JDlgUploadProducts.getWindow(parent.getParent());
    }

    public static void showMessage(Component parent, DeviceScanner scanner, BrowsableEditableData bd) {
        Window window = JDlgUploadProducts.getWindow(parent);
        JDlgUploadProducts myMsg = window instanceof Frame ? new JDlgUploadProducts((Frame)window, true) : new JDlgUploadProducts((Dialog)window, true);
        myMsg.init(scanner, bd);
    }

    private void initComponents() {
        this.jPanel2 = new JPanel();
        this.jcmdCancel = new JButton();
        this.jcmdOK = new JButton();
        this.jPanel1 = new JPanel();
        this.jLabel1 = new JLabel();
        this.setDefaultCloseOperation(2);
        this.setTitle(AppLocal.getIntString("caption.upload"));
        this.setResizable(false);
        this.jPanel2.setLayout(new FlowLayout(2));
        this.jcmdCancel.setFont(new Font("Arial", 0, 12));
        this.jcmdCancel.setIcon(new ImageIcon(this.getClass().getResource("/com/openbravo/images/cancel.png")));
        this.jcmdCancel.setText(AppLocal.getIntString("button.cancel"));
        this.jcmdCancel.setPreferredSize(new Dimension(110, 45));
        this.jcmdCancel.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                JDlgUploadProducts.this.jcmdCancelActionPerformed(evt);
            }
        });
        this.jPanel2.add(this.jcmdCancel);
        this.jcmdOK.setFont(new Font("Arial", 0, 12));
        this.jcmdOK.setIcon(new ImageIcon(this.getClass().getResource("/com/openbravo/images/ok.png")));
        this.jcmdOK.setText(AppLocal.getIntString("button.OK"));
        this.jcmdOK.setPreferredSize(new Dimension(110, 45));
        this.jcmdOK.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                JDlgUploadProducts.this.jcmdOKActionPerformed(evt);
            }
        });
        this.jPanel2.add(this.jcmdOK);
        this.getContentPane().add((Component)this.jPanel2, "South");
        this.jPanel1.setLayout(null);
        this.jLabel1.setFont(new Font("Arial", 0, 14));
        this.jLabel1.setHorizontalAlignment(0);
        this.jLabel1.setText(AppLocal.getIntString("message.preparescanner"));
        this.jLabel1.setPreferredSize(new Dimension(450, 30));
        this.jPanel1.add(this.jLabel1);
        this.jLabel1.setBounds(0, 20, 460, 30);
        this.getContentPane().add((Component)this.jPanel1, "Center");
        this.setSize(new Dimension(474, 161));
        this.setLocationRelativeTo(null);
    }

    private void jcmdCancelActionPerformed(ActionEvent evt) {
        this.dispose();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void jcmdOKActionPerformed(ActionEvent evt) {
        String stext = this.jLabel1.getText();
        this.jLabel1.setText(AppLocal.getIntString("label.uploadingproducts"));
        this.jcmdOK.setEnabled(false);
        this.jcmdCancel.setEnabled(false);
        try {
            this.m_scanner.connectDevice();
            this.m_scanner.startUploadProduct();
            ListModel l = this.m_bd.getListModel();
            for (int i = 0; i < l.getSize(); ++i) {
                Object[] myprod = (Object[])l.getElementAt(i);
                this.m_scanner.sendProduct((String)myprod[3], (String)myprod[2], (Double)myprod[6]);
            }
            this.m_scanner.stopUploadProduct();
            MessageInf msg = new MessageInf(-83886080, AppLocal.getIntString("message.scannerok"));
            msg.show(this);
        }
        catch (DeviceScannerException e) {
            MessageInf msg = new MessageInf(-33554432, AppLocal.getIntString("message.scannerfail"), e);
            msg.show(this);
        }
        finally {
            this.m_scanner.disconnectDevice();
        }
        this.jLabel1.setText(stext);
        this.jcmdOK.setEnabled(true);
        this.jcmdCancel.setEnabled(true);
        this.dispose();
    }
}

