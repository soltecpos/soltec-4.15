/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.data.gui;

import com.openbravo.data.gui.NullIcon;
import com.openbravo.data.user.DirtyListener;
import com.openbravo.data.user.DirtyManager;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class JLabelDirty
extends JLabel {
    private static Icon m_IconModif = null;
    private static Icon m_IconNull = null;

    public JLabelDirty(DirtyManager dm) {
        if (m_IconModif == null) {
            m_IconModif = new ImageIcon(this.getClass().getResource("/com/openbravo/images/edit.png"));
        }
        if (m_IconNull == null) {
            m_IconNull = new NullIcon(16, 16);
        }
        dm.addDirtyListener(new DirtyListener(){

            @Override
            public void changedDirty(boolean bDirty) {
                JLabelDirty.this.setIcon(bDirty ? m_IconModif : m_IconNull);
            }
        });
    }
}

