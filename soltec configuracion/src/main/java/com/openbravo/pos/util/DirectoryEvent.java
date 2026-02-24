/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.util;

import com.openbravo.pos.forms.AppLocal;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;
import javax.swing.text.JTextComponent;

public class DirectoryEvent
implements ActionListener {
    private JTextComponent m_jTxtField;
    private JFileChooser m_fc;

    public DirectoryEvent(JTextComponent TxtField) {
        this.m_jTxtField = TxtField;
        this.m_fc = new JFileChooser();
        this.m_fc.resetChoosableFileFilters();
        this.m_fc.addChoosableFileFilter(new FileFilter(){

            @Override
            public boolean accept(File f) {
                if (f.isDirectory()) {
                    return true;
                }
                String filename = f.getName();
                return filename.endsWith(".jar") || filename.endsWith(".JAR") || filename.endsWith(".zip") || filename.endsWith(".ZIP");
            }

            @Override
            public String getDescription() {
                return AppLocal.getIntString("filter.dbdriverlib");
            }
        });
        this.m_fc.setFileSelectionMode(0);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        this.m_fc.setCurrentDirectory(new File(this.m_jTxtField.getText()));
        if (this.m_fc.showOpenDialog(this.m_jTxtField) == 0) {
            this.m_jTxtField.setText(this.m_fc.getSelectedFile().getAbsolutePath());
        }
    }
}

