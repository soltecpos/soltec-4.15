/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.data.gui;

import com.openbravo.data.gui.MessageInf;
import com.openbravo.data.loader.LocalRes;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ResourceBundle;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class JMessageDialog
extends JDialog {
    private JPanel jPanel2;
    private JPanel jPanel3;
    private JPanel jPanel4;
    private JButton jcmdMore;
    private JButton jcmdOK;
    private JLabel jlblErrorCode;
    private JLabel jlblIcon;
    private JLabel jlblMessage;
    private JScrollPane jscrException;
    private JTextArea jtxtException;

    private JMessageDialog(Frame parent, boolean modal) {
        super(parent, modal);
    }

    private JMessageDialog(Dialog parent, boolean modal) {
        super(parent, modal);
    }

    private static Window getWindow(Component parent) {
        if (parent == null) {
            return new JFrame();
        }
        if (parent instanceof Frame || parent instanceof Dialog) {
            return (Window)parent;
        }
        return JMessageDialog.getWindow(parent.getParent());
    }

    public static void showMessage(Component parent, MessageInf inf) {
        Window window = JMessageDialog.getWindow(parent);
        JMessageDialog myMsg = window instanceof Frame ? new JMessageDialog((Frame)window, true) : new JMessageDialog((Dialog)window, true);
        myMsg.initComponents();
        myMsg.applyComponentOrientation(parent.getComponentOrientation());
        myMsg.jscrException.setVisible(false);
        myMsg.getRootPane().setDefaultButton(myMsg.jcmdOK);
        myMsg.jlblIcon.setIcon(inf.getSignalWordIcon());
        myMsg.jlblErrorCode.setText(inf.getErrorCodeMsg());
        myMsg.jlblMessage.setText("<html>" + inf.getMessageMsg());
        if (inf.getCause() == null) {
            myMsg.jtxtException.setText(null);
        } else {
            StringBuilder sb = new StringBuilder();
            if (inf.getCause() instanceof Throwable) {
                for (Throwable t = (Throwable)inf.getCause(); t != null; t = t.getCause()) {
                    sb.append(t.getClass().getName());
                    sb.append(": \n");
                    sb.append(t.getMessage());
                    sb.append("\n\n");
                }
            } else if (inf.getCause() instanceof Throwable[]) {
                Throwable[] m_aExceptions = (Throwable[])inf.getCause();
                for (int i = 0; i < m_aExceptions.length; ++i) {
                    sb.append(m_aExceptions[i].getClass().getName());
                    sb.append(": \n");
                    sb.append(m_aExceptions[i].getMessage());
                    sb.append("\n\n");
                }
            } else if (inf.getCause() instanceof Object[]) {
                Object[] m_aObjects = (Object[])inf.getCause();
                for (int i = 0; i < m_aObjects.length; ++i) {
                    sb.append(m_aObjects[i].toString());
                    sb.append("\n\n");
                }
            } else if (inf.getCause() instanceof String) {
                sb.append(inf.getCause().toString());
            } else {
                sb.append(inf.getCause().getClass().getName());
                sb.append(": \n");
                sb.append(inf.getCause().toString());
            }
            myMsg.jtxtException.setText(sb.toString());
        }
        myMsg.jtxtException.setCaretPosition(0);
        myMsg.setVisible(true);
    }

    private void initComponents() {
        this.jPanel4 = new JPanel();
        this.jlblErrorCode = new JLabel();
        this.jlblMessage = new JLabel();
        this.jscrException = new JScrollPane();
        this.jtxtException = new JTextArea();
        this.jlblIcon = new JLabel();
        this.jPanel3 = new JPanel();
        this.jPanel2 = new JPanel();
        this.jcmdOK = new JButton();
        this.jcmdMore = new JButton();
        this.setTitle(LocalRes.getIntString("title.message"));
        this.setResizable(false);
        this.addWindowListener(new WindowAdapter(){

            @Override
            public void windowClosing(WindowEvent evt) {
                JMessageDialog.this.closeDialog(evt);
            }
        });
        this.jPanel4.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        this.jPanel4.setFont(new Font("Arial", 0, 12));
        this.jPanel4.setLayout(new BoxLayout(this.jPanel4, 1));
        this.jlblErrorCode.setFont(new Font("Arial", 0, 14));
        this.jlblErrorCode.setText("jlblErrorCode");
        this.jPanel4.add(this.jlblErrorCode);
        this.jlblMessage.setFont(new Font("Arial", 0, 14));
        this.jlblMessage.setText("jlblMessage");
        this.jlblMessage.setVerticalAlignment(1);
        this.jlblMessage.setMinimumSize(new Dimension(200, 100));
        this.jlblMessage.setPreferredSize(new Dimension(200, 100));
        this.jPanel4.add(this.jlblMessage);
        this.jscrException.setAlignmentX(0.0f);
        this.jtxtException.setEditable(false);
        this.jtxtException.setFont(new Font("Arial", 0, 12));
        this.jscrException.setViewportView(this.jtxtException);
        this.jPanel4.add(this.jscrException);
        this.getContentPane().add((Component)this.jPanel4, "Center");
        this.jlblIcon.setVerticalAlignment(1);
        this.jlblIcon.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        this.getContentPane().add((Component)this.jlblIcon, "Before");
        this.jPanel3.setLayout(new BorderLayout());
        this.jcmdOK.setFont(new Font("Arial", 0, 12));
        this.jcmdOK.setText(LocalRes.getIntString("button.OK"));
        ResourceBundle bundle = ResourceBundle.getBundle("pos_messages");
        this.jcmdOK.setActionCommand(bundle.getString("button.OK"));
        this.jcmdOK.setMaximumSize(new Dimension(65, 33));
        this.jcmdOK.setMinimumSize(new Dimension(65, 33));
        this.jcmdOK.setPreferredSize(new Dimension(65, 33));
        this.jcmdOK.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                JMessageDialog.this.jcmdOKActionPerformed(evt);
            }
        });
        this.jPanel2.add(this.jcmdOK);
        this.jcmdMore.setFont(new Font("Arial", 0, 12));
        this.jcmdMore.setText(LocalRes.getIntString("button.information"));
        this.jcmdMore.setMaximumSize(new Dimension(65, 33));
        this.jcmdMore.setMinimumSize(new Dimension(65, 33));
        this.jcmdMore.setPreferredSize(new Dimension(65, 33));
        this.jcmdMore.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                JMessageDialog.this.jcmdMoreActionPerformed(evt);
            }
        });
        this.jPanel2.add(this.jcmdMore);
        this.jPanel3.add((Component)this.jPanel2, "After");
        this.getContentPane().add((Component)this.jPanel3, "South");
        this.setSize(new Dimension(455, 277));
        this.setLocationRelativeTo(null);
    }

    private void jcmdMoreActionPerformed(ActionEvent evt) {
        this.jcmdMore.setEnabled(false);
        this.jscrException.setVisible(true);
        this.setSize(this.getWidth(), 310);
        this.validate();
    }

    private void jcmdOKActionPerformed(ActionEvent evt) {
        this.setVisible(false);
        this.dispose();
    }

    private void closeDialog(WindowEvent evt) {
        this.setVisible(false);
        this.dispose();
    }
}

