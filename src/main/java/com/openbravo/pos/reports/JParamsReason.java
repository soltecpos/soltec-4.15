/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.reports;

import com.openbravo.basic.BasicException;
import com.openbravo.data.gui.ComboBoxValModel;
import com.openbravo.data.loader.Datas;
import com.openbravo.data.loader.QBFCompareEnum;
import com.openbravo.data.loader.SerializerWrite;
import com.openbravo.data.loader.SerializerWriteBasic;
import com.openbravo.pos.forms.AppLocal;
import com.openbravo.pos.forms.AppView;
import com.openbravo.pos.inventory.MovementReason;
import com.openbravo.pos.reports.ReportEditorCreator;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.GroupLayout;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.LayoutStyle;

public class JParamsReason
extends JPanel
implements ReportEditorCreator {
    private static final long serialVersionUID = 1L;
    private ComboBoxValModel<MovementReason> m_ReasonModel;
    private JLabel jLabel2;
    private JComboBox<MovementReason> m_jreason;

    public JParamsReason() {
        this.initComponents();
        this.m_ReasonModel = new ComboBoxValModel();
        this.m_ReasonModel.add(null);
        this.m_ReasonModel.add(MovementReason.IN_PURCHASE);
        this.m_ReasonModel.add(MovementReason.OUT_SALE);
        this.m_ReasonModel.add(MovementReason.IN_REFUND);
        this.m_ReasonModel.add(MovementReason.OUT_REFUND);
        this.m_ReasonModel.add(MovementReason.IN_MOVEMENT);
        this.m_ReasonModel.add(MovementReason.OUT_MOVEMENT);
        this.m_ReasonModel.add(MovementReason.OUT_SUBTRACT);
        this.m_ReasonModel.add(MovementReason.OUT_BREAK);
        this.m_ReasonModel.add(MovementReason.OUT_FREE);
        this.m_ReasonModel.add(MovementReason.OUT_SAMPLE);
        this.m_ReasonModel.add(MovementReason.OUT_USED);
        this.m_ReasonModel.add(MovementReason.OUT_CROSSING);
        this.m_jreason.setModel(this.m_ReasonModel);
    }

    @Override
    public void init(AppView app) {
    }

    @Override
    public void activate() throws BasicException {
    }

    @Override
    public SerializerWrite getSerializerWrite() {
        return new SerializerWriteBasic(Datas.OBJECT, Datas.INT);
    }

    @Override
    public Component getComponent() {
        return this;
    }

    @Override
    public Object createValue() throws BasicException {
        return new Object[]{this.m_ReasonModel.getSelectedItem() == null ? QBFCompareEnum.COMP_NONE : QBFCompareEnum.COMP_EQUALS, this.m_ReasonModel.getSelectedKey()};
    }

    private void initComponents() {
        this.jLabel2 = new JLabel();
        this.m_jreason = new JComboBox();
        this.setPreferredSize(new Dimension(400, 50));
        this.jLabel2.setFont(new Font("Arial", 0, 14));
        this.jLabel2.setText(AppLocal.getIntString("label.stockreason"));
        this.jLabel2.setPreferredSize(new Dimension(100, 30));
        this.m_jreason.setFont(new Font("Arial", 0, 14));
        this.m_jreason.setMaximumRowCount(12);
        this.m_jreason.setPreferredSize(new Dimension(250, 30));
        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addComponent(this.jLabel2, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.m_jreason, -2, -1, -2).addContainerGap(-1, Short.MAX_VALUE)));
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.m_jreason, -2, -1, -2).addComponent(this.jLabel2, -2, -1, -2)).addContainerGap()));
    }
}

