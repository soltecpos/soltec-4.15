/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.editor;

import com.openbravo.basic.BasicException;
import com.openbravo.editor.JEditorAbstract;
import com.openbravo.format.DoubleUtils;
import com.openbravo.format.Formats;
import java.awt.Toolkit;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public abstract class JEditorNumber
extends JEditorAbstract {
    private static final long serialVersionUID = 1L;
    private static final int NUMBER_ZERONULL = 0;
    private static final int NUMBER_INT = 1;
    private static final int NUMBER_DEC = 2;
    private char DEC_SEP = (char)46;
    private int m_iNumberStatus;
    private String m_sNumber;
    private boolean m_bNegative;
    private Formats m_fmt = this.getFormat();

    public JEditorNumber() {
        Properties props = new Properties();
        String homeDir = System.getProperty("user.home");
        File propertiesFile = new File(homeDir, "unicentaopos.properties");
        if (propertiesFile.exists()) {
            try {
                FileInputStream in = new FileInputStream(propertiesFile);
                props.load(in);
                in.close();
            }
            catch (IOException iOException) {
                // empty catch block
            }
        }
        this.reset();
    }

    protected abstract Formats getFormat();

    public void reset() {
        String sOldText = this.getText();
        this.m_sNumber = "";
        this.m_bNegative = false;
        this.m_iNumberStatus = 0;
        this.reprintText();
        this.firePropertyChange("Text", sOldText, this.getText());
    }

    public void setDoubleValue(Double dvalue) {
        String sOldText = this.getText();
        if (dvalue == null) {
            this.m_sNumber = "";
            this.m_bNegative = false;
            this.m_iNumberStatus = 0;
        } else if (dvalue >= 0.0) {
            this.m_sNumber = this.formatDouble(dvalue);
            this.m_bNegative = false;
            this.m_iNumberStatus = 0;
        } else {
            this.m_sNumber = this.formatDouble(-dvalue.doubleValue());
            this.m_bNegative = true;
            this.m_iNumberStatus = 0;
        }
        this.reprintText();
        this.firePropertyChange("Text", sOldText, this.getText());
    }

    public Double getDoubleValue() {
        String text = this.getText();
        if (text == null || text.equals("")) {
            return null;
        }
        try {
            return Double.parseDouble(text);
        }
        catch (NumberFormatException e) {
            return null;
        }
    }

    public void setValueInteger(int ivalue) {
        String sOldText = this.getText();
        if (ivalue >= 0) {
            this.m_sNumber = Integer.toString(ivalue);
            this.m_bNegative = false;
            this.m_iNumberStatus = 0;
        } else {
            this.m_sNumber = Integer.toString(-ivalue);
            this.m_bNegative = true;
            this.m_iNumberStatus = 0;
        }
        this.reprintText();
        this.firePropertyChange("Text", sOldText, this.getText());
    }

    public int getValueInteger() throws BasicException {
        try {
            return Integer.parseInt(this.getText());
        }
        catch (NumberFormatException e) {
            throw new BasicException(e);
        }
    }

    private String formatDouble(Double value) {
        String sNumber = Double.toString(DoubleUtils.fixDecimals(value));
        if (sNumber.endsWith(".0")) {
            sNumber = sNumber.substring(0, sNumber.length() - 2);
        }
        return sNumber;
    }

    @Override
    protected String getEditMode() {
        return "-1.23";
    }

    public String getText() {
        return (this.m_bNegative ? "-" : "") + this.m_sNumber;
    }

    @Override
    protected int getAlignment() {
        return 4;
    }

    @Override
    protected String getTextEdit() {
        return this.getText();
    }

    @Override
    protected String getTextFormat() throws BasicException {
        return this.m_fmt.formatValue(this.getDoubleValue());
    }

    @Override
    protected void typeCharInternal(char cTrans) {
        this.transChar(cTrans);
    }

    @Override
    protected void transCharInternal(char cTrans) {
        String sOldText = this.getText();
        if (cTrans == '\u007f') {
            this.reset();
        } else if (cTrans == '-') {
            this.m_bNegative = !this.m_bNegative;
        } else if (cTrans == '0' && this.m_iNumberStatus == 0) {
            this.m_sNumber = "0";
        } else if ((cTrans == '1' || cTrans == '2' || cTrans == '3' || cTrans == '4' || cTrans == '5' || cTrans == '6' || cTrans == '7' || cTrans == '8' || cTrans == '9') && this.m_iNumberStatus == 0) {
            this.m_iNumberStatus = 1;
            this.m_sNumber = Character.toString(cTrans);
        } else if (cTrans == this.DEC_SEP && this.m_iNumberStatus == 0) {
            this.m_iNumberStatus = 2;
            this.m_sNumber = "0" + this.DEC_SEP;
        } else if (cTrans == this.DEC_SEP && this.m_iNumberStatus == 0) {
            this.m_iNumberStatus = 1;
            this.m_sNumber = "0";
        } else if ((cTrans == '0' || cTrans == '1' || cTrans == '2' || cTrans == '3' || cTrans == '4' || cTrans == '5' || cTrans == '6' || cTrans == '7' || cTrans == '8' || cTrans == '9') && this.m_iNumberStatus == 1) {
            this.m_sNumber = this.m_sNumber + cTrans;
        } else if (cTrans == this.DEC_SEP && this.m_iNumberStatus == 1) {
            this.m_iNumberStatus = 2;
            this.m_sNumber = this.m_sNumber + this.DEC_SEP;
        } else if (cTrans == this.DEC_SEP && this.m_iNumberStatus == 1) {
            this.m_sNumber = this.m_sNumber + "00";
        } else if ((cTrans == '0' || cTrans == '1' || cTrans == '2' || cTrans == '3' || cTrans == '4' || cTrans == '5' || cTrans == '6' || cTrans == '7' || cTrans == '8' || cTrans == '9') && this.m_iNumberStatus == 2) {
            this.m_sNumber = this.m_sNumber + cTrans;
        } else {
            Toolkit.getDefaultToolkit().beep();
        }
        this.firePropertyChange("Text", sOldText, this.getText());
    }
}

