/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.sales;

import com.openbravo.basic.BasicException;
import com.openbravo.data.loader.DataRead;
import com.openbravo.data.loader.Datas;
import com.openbravo.data.loader.PreparedSentence;
import com.openbravo.data.loader.SentenceExec;
import com.openbravo.data.loader.SentenceFind;
import com.openbravo.data.loader.SentenceList;
import com.openbravo.data.loader.SerializerRead;
import com.openbravo.data.loader.SerializerReadString;
import com.openbravo.data.loader.SerializerWriteBasic;
import com.openbravo.data.loader.SerializerWriteString;
import com.openbravo.data.loader.Session;
import com.openbravo.editor.JEditorKeys;
import com.openbravo.pos.forms.AppLocal;
import com.openbravo.pos.inventory.AttributeSetInfo;
import com.openbravo.pos.sales.JProductAttEditI;
import com.openbravo.pos.sales.JProductAttEditItem;
import com.openbravo.pos.sales.JProductAttListItem;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Insets;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class JProductAttEdit
extends JDialog {
    private SentenceFind<AttributeSetInfo> attsetSent;
    private SentenceList<String> attvaluesSent;
    private SentenceList<AttributeInstInfo> attinstSent;
    private SentenceList<AttributeInstInfo> attinstSent2;
    private SentenceFind<String> attsetinstExistsSent;
    private SentenceExec<Object[]> attsetSave;
    private SentenceExec<Object[]> attinstSave;
    private List<JProductAttEditI> itemslist;
    private String attsetid;
    private String attInstanceId;
    private String attInstanceDescription;
    private boolean ok;
    private JPanel jPanel1;
    private JPanel jPanel2;
    private JPanel jPanel3;
    private JPanel jPanel4;
    private JPanel jPanel5;
    private JButton m_jButtonCancel;
    private JButton m_jButtonOK;
    private JEditorKeys m_jKeys;

    private JProductAttEdit(Frame parent, boolean modal) {
        super(parent, modal);
    }

    private JProductAttEdit(Dialog parent, boolean modal) {
        super(parent, modal);
    }

    private void init(Session s) {
        this.initComponents();
        this.attsetSave = new PreparedSentence(s, "INSERT INTO attributesetinstance (ID, ATTRIBUTESET_ID, DESCRIPTION) VALUES (?, ?, ?)", new SerializerWriteBasic(Datas.STRING, Datas.STRING, Datas.STRING));
        this.attinstSave = new PreparedSentence(s, "INSERT INTO attributeinstance(ID, ATTRIBUTESETINSTANCE_ID, ATTRIBUTE_ID, VALUE) VALUES (?, ?, ?, ?)", new SerializerWriteBasic(Datas.STRING, Datas.STRING, Datas.STRING, Datas.STRING));
        this.attsetSent = new PreparedSentence<String, AttributeSetInfo>(s, "SELECT ID, NAME FROM attributeset WHERE ID = ?", SerializerWriteString.INSTANCE, new SerializerRead<AttributeSetInfo>(){

            @Override
            public AttributeSetInfo readValues(DataRead dr) throws BasicException {
                return new AttributeSetInfo(dr.getString(1), dr.getString(2));
            }
        });
        this.attsetinstExistsSent = new PreparedSentence<Object[], String>(s, "SELECT ID FROM attributesetinstance WHERE ATTRIBUTESET_ID = ? AND DESCRIPTION = ?", new SerializerWriteBasic(Datas.STRING, Datas.STRING), SerializerReadString.INSTANCE);
        this.attinstSent = new PreparedSentence<String, AttributeInstInfo>(s, "SELECT A.ID, A.NAME, " + s.DB.CHAR_NULL() + ", " + s.DB.CHAR_NULL() + " FROM attributeuse AU JOIN attribute A ON AU.ATTRIBUTE_ID = A.ID WHERE AU.ATTRIBUTESET_ID = ? ORDER BY AU.LINENO", SerializerWriteString.INSTANCE, new SerializerRead<AttributeInstInfo>(){

            @Override
            public AttributeInstInfo readValues(DataRead dr) throws BasicException {
                return new AttributeInstInfo(dr.getString(1), dr.getString(2), dr.getString(3), dr.getString(4));
            }
        });
        this.attinstSent2 = new PreparedSentence<Object[], AttributeInstInfo>(s, "SELECT A.ID, A.NAME, AI.ID, AI.VALUE FROM attributeuse AU JOIN attribute A ON AU.ATTRIBUTE_ID = A.ID LEFT OUTER JOIN attributeinstance AI ON AI.ATTRIBUTE_ID = A.ID WHERE AU.ATTRIBUTESET_ID = ? AND AI.ATTRIBUTESETINSTANCE_ID = ?ORDER BY AU.LINENO", new SerializerWriteBasic(Datas.STRING, Datas.STRING), new SerializerRead<AttributeInstInfo>(){

            @Override
            public AttributeInstInfo readValues(DataRead dr) throws BasicException {
                return new AttributeInstInfo(dr.getString(1), dr.getString(2), dr.getString(3), dr.getString(4));
            }
        });
        this.attvaluesSent = new PreparedSentence<String, String>(s, "SELECT VALUE FROM attributevalue WHERE ATTRIBUTE_ID = ? ORDER BY VALUE", SerializerWriteString.INSTANCE, SerializerReadString.INSTANCE);
        this.getRootPane().setDefaultButton(this.m_jButtonOK);
    }

    public static JProductAttEdit getAttributesEditor(Component parent, Session s) {
        Window window = SwingUtilities.getWindowAncestor(parent);
        JProductAttEdit myMsg = window instanceof Frame ? new JProductAttEdit((Frame)window, true) : new JProductAttEdit((Dialog)window, true);
        myMsg.init(s);
        myMsg.applyComponentOrientation(parent.getComponentOrientation());
        return myMsg;
    }

    public void editAttributes(String attsetid, String attsetinstid) throws BasicException {
        if (attsetid == null) {
            throw new BasicException(AppLocal.getIntString("message.cannotfindattributes"));
        }
        this.attsetid = attsetid;
        this.attInstanceId = null;
        this.attInstanceDescription = null;
        this.ok = false;
        AttributeSetInfo asi = this.attsetSent.find((Object)attsetid);
        if (asi == null) {
            throw new BasicException(AppLocal.getIntString("message.cannotfindattributes"));
        }
        this.setTitle(asi.getName());
        List<AttributeInstInfo> attinstinfo = attsetinstid == null ? this.attinstSent.list((Object)attsetid) : this.attinstSent2.list(attsetid, attsetinstid);
        this.itemslist = new ArrayList<JProductAttEditI>();
        for (AttributeInstInfo aii : attinstinfo) {
            List<String> values = this.attvaluesSent.list((Object)aii.getAttid());
            JPanel item = values.isEmpty() ? new JProductAttEditItem(aii.getAttid(), aii.getAttname(), aii.getValue(), this.m_jKeys) : new JProductAttListItem(aii.getAttid(), aii.getAttname(), aii.getValue(), values);
            this.itemslist.add((JProductAttEditI)((Object)item));
            this.jPanel2.add(item);
        }
        if (this.itemslist.size() > 0) {
            this.itemslist.get(0).assignSelection();
        }
    }

    public boolean isOK() {
        return this.ok;
    }

    public String getAttributeSetInst() {
        return this.attInstanceId;
    }

    public String getAttributeSetInstDescription() {
        return this.attInstanceDescription;
    }

    private void initComponents() {
        this.jPanel5 = new JPanel();
        this.jPanel2 = new JPanel();
        this.jPanel3 = new JPanel();
        this.jPanel4 = new JPanel();
        this.m_jKeys = new JEditorKeys();
        this.jPanel1 = new JPanel();
        this.m_jButtonCancel = new JButton();
        this.m_jButtonOK = new JButton();
        this.setDefaultCloseOperation(2);
        this.jPanel5.setFont(new Font("Arial", 0, 12));
        this.jPanel5.setLayout(new BorderLayout());
        this.jPanel2.setLayout(new BoxLayout(this.jPanel2, 3));
        this.jPanel5.add((Component)this.jPanel2, "North");
        this.getContentPane().add((Component)this.jPanel5, "Center");
        this.jPanel3.setFont(new Font("Arial", 0, 12));
        this.jPanel3.setLayout(new BorderLayout());
        this.jPanel4.setLayout(new BoxLayout(this.jPanel4, 1));
        this.jPanel4.add(this.m_jKeys);
        this.jPanel3.add((Component)this.jPanel4, "North");
        this.jPanel1.setLayout(new FlowLayout(2));
        this.m_jButtonCancel.setFont(new Font("Arial", 0, 12));
        this.m_jButtonCancel.setIcon(new ImageIcon(this.getClass().getResource("/com/openbravo/images/cancel.png")));
        this.m_jButtonCancel.setText(AppLocal.getIntString("button.cancel"));
        this.m_jButtonCancel.setFocusPainted(false);
        this.m_jButtonCancel.setFocusable(false);
        this.m_jButtonCancel.setMargin(new Insets(8, 16, 8, 16));
        this.m_jButtonCancel.setPreferredSize(new Dimension(110, 45));
        this.m_jButtonCancel.setRequestFocusEnabled(false);
        this.m_jButtonCancel.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                JProductAttEdit.this.m_jButtonCancelActionPerformed(evt);
            }
        });
        this.jPanel1.add(this.m_jButtonCancel);
        this.m_jButtonOK.setFont(new Font("Arial", 0, 12));
        this.m_jButtonOK.setIcon(new ImageIcon(this.getClass().getResource("/com/openbravo/images/ok.png")));
        this.m_jButtonOK.setText(AppLocal.getIntString("button.OK"));
        this.m_jButtonOK.setFocusPainted(false);
        this.m_jButtonOK.setFocusable(false);
        this.m_jButtonOK.setMargin(new Insets(8, 16, 8, 16));
        this.m_jButtonOK.setPreferredSize(new Dimension(110, 45));
        this.m_jButtonOK.setRequestFocusEnabled(false);
        this.m_jButtonOK.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                JProductAttEdit.this.m_jButtonOKActionPerformed(evt);
            }
        });
        this.jPanel1.add(this.m_jButtonOK);
        this.jPanel3.add((Component)this.jPanel1, "Last");
        this.getContentPane().add((Component)this.jPanel3, "East");
        this.setSize(new Dimension(658, 388));
        this.setLocationRelativeTo(null);
    }

    private void m_jButtonOKActionPerformed(ActionEvent evt) {
        String id;
        StringBuilder description = new StringBuilder();
        for (JProductAttEditI item : this.itemslist) {
            String value = item.getValue();
            if (value == null || value.length() <= 0) continue;
            if (description.length() > 0) {
                description.append(", ");
            }
            description.append(value);
        }
        if (description.length() == 0) {
            id = null;
        } else {
            try {
                id = this.attsetinstExistsSent.find(this.attsetid, description.toString());
            }
            catch (BasicException ex) {
                return;
            }
            if (id == null) {
                id = UUID.randomUUID().toString();
                try {
                    this.attsetSave.exec(new Object[]{id, this.attsetid, description.toString()});
                    for (JProductAttEditI item : this.itemslist) {
                        this.attinstSave.exec(new Object[]{UUID.randomUUID().toString(), id, item.getAttribute(), item.getValue()});
                    }
                }
                catch (BasicException ex) {
                    return;
                }
            }
        }
        this.ok = true;
        this.attInstanceId = id;
        this.attInstanceDescription = description.toString();
        this.dispose();
    }

    private void m_jButtonCancelActionPerformed(ActionEvent evt) {
        this.dispose();
    }

    private static class AttributeInstInfo {
        private String attid;
        private String attname;
        private String id;
        private String value;

        public AttributeInstInfo(String attid, String attname, String id, String value) {
            this.attid = attid;
            this.attname = attname;
            this.id = id;
            this.value = value;
        }

        public String getAttid() {
            return this.attid;
        }

        public String getAttname() {
            return this.attname;
        }

        public String getId() {
            return this.id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getValue() {
            return this.value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }
}

