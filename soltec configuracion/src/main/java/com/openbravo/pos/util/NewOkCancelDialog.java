/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.usb.UsbDevice
 *  javax.usb.UsbDeviceDescriptor
 *  javax.usb.UsbHub
 */
package com.openbravo.pos.util;

import java.awt.EventQueue;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.GroupLayout;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.KeyStroke;
import javax.swing.LayoutStyle;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.usb.UsbDevice;
import javax.usb.UsbDeviceDescriptor;
import javax.usb.UsbHub;

public class NewOkCancelDialog
extends JDialog {
    public static final int RET_CANCEL = 0;
    public static final int RET_OK = 1;
    private JButton cancelButton;
    private JScrollPane jScrollPane1;
    private JTextPane jTextPane1;
    private JButton okButton;
    private int returnStatus = 0;

    public NewOkCancelDialog(Frame parent, boolean modal) {
        super(parent, modal);
        this.initComponents();
        String cancelName = "cancel";
        InputMap inputMap = this.getRootPane().getInputMap(1);
        inputMap.put(KeyStroke.getKeyStroke(27, 0), cancelName);
        ActionMap actionMap = this.getRootPane().getActionMap();
        actionMap.put(cancelName, new AbstractAction(){

            @Override
            public void actionPerformed(ActionEvent e) {
                NewOkCancelDialog.this.doClose(0);
            }
        });
    }

    public int getReturnStatus() {
        return this.returnStatus;
    }

    public UsbDevice findDevice(UsbHub hub, short vendorId, short productId) {
        for (Object obj : hub.getAttachedUsbDevices()) {
            UsbDevice device = (UsbDevice) obj;
            UsbDeviceDescriptor desc = device.getUsbDeviceDescriptor();
            if (desc.idVendor() == vendorId && desc.idProduct() == productId) {
                return device;
            }
            if (!device.isUsbHub() || (device = this.findDevice((UsbHub)device, vendorId, productId)) == null) continue;
            return device;
        }
        return null;
    }

    private void initComponents() {
        this.okButton = new JButton();
        this.cancelButton = new JButton();
        this.jScrollPane1 = new JScrollPane();
        this.jTextPane1 = new JTextPane();
        this.addWindowListener(new WindowAdapter(){

            @Override
            public void windowClosing(WindowEvent evt) {
                NewOkCancelDialog.this.closeDialog(evt);
            }
        });
        this.okButton.setText("OK");
        this.okButton.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                NewOkCancelDialog.this.okButtonActionPerformed(evt);
            }
        });
        this.cancelButton.setText("Cancel");
        this.cancelButton.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                NewOkCancelDialog.this.cancelButtonActionPerformed(evt);
            }
        });
        this.jScrollPane1.setViewportView(this.jTextPane1);
        GroupLayout layout = new GroupLayout(this.getContentPane());
        this.getContentPane().setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup().addContainerGap(250, Short.MAX_VALUE).addComponent(this.okButton, -2, 67, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.cancelButton).addContainerGap()).addGroup(layout.createSequentialGroup().addContainerGap().addComponent(this.jScrollPane1, -2, 255, -2).addContainerGap(-1, Short.MAX_VALUE)));
        layout.linkSize(0, this.cancelButton, this.okButton);
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup().addContainerGap().addComponent(this.jScrollPane1, -1, 244, Short.MAX_VALUE).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.cancelButton).addComponent(this.okButton)).addContainerGap()));
        this.getRootPane().setDefaultButton(this.okButton);
        this.pack();
    }

    private void okButtonActionPerformed(ActionEvent evt) {
        UsbHub hub = null;
        this.findDevice(hub, 0, 0);
    }

    private void cancelButtonActionPerformed(ActionEvent evt) {
        this.doClose(0);
    }

    private void closeDialog(WindowEvent evt) {
        this.doClose(0);
    }

    private void doClose(int retStatus) {
        this.returnStatus = retStatus;
        this.setVisible(false);
        this.dispose();
    }

    public static void main(String[] args) {
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if (!"Nimbus".equals(info.getName())) continue;
                UIManager.setLookAndFeel(info.getClassName());
                break;
            }
        }
        catch (ClassNotFoundException ex) {
            Logger.getLogger(NewOkCancelDialog.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (InstantiationException ex) {
            Logger.getLogger(NewOkCancelDialog.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (IllegalAccessException ex) {
            Logger.getLogger(NewOkCancelDialog.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (UnsupportedLookAndFeelException ex) {
            Logger.getLogger(NewOkCancelDialog.class.getName()).log(Level.SEVERE, null, ex);
        }
        EventQueue.invokeLater(new Runnable(){

            @Override
            public void run() {
                NewOkCancelDialog dialog = new NewOkCancelDialog((Frame)new JFrame(), true);
                dialog.addWindowListener(new WindowAdapter(){

                    @Override
                    public void windowClosing(WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    private void findDevice(UsbHub hub, int i, int i0) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}

