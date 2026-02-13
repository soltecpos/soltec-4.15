/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.data.loader;

import com.openbravo.data.loader.Session;
import com.openbravo.pos.forms.AppConfig;
import java.io.File;

public class TicketFooter {
    private String footer_line1;
    private String footer_line2;
    private String footer_line3;
    private String footer_line4;
    private String footer_line5;
    private String footer_line6;
    private File m_config;
    private Session session;

    public TicketFooter() {
        AppConfig config = new AppConfig(this.m_config);
    }

    public void loadProperties(AppConfig config) {
        this.footer_line1 = config.getProperty("till.footer1");
        this.footer_line2 = config.getProperty("till.footer2");
        this.footer_line3 = config.getProperty("till.footer3");
        this.footer_line4 = config.getProperty("till.footer4");
        this.footer_line5 = config.getProperty("till.footer5");
        this.footer_line6 = config.getProperty("till.footer6");
    }

    public String getTicketFooterLine1() {
        return this.footer_line1;
    }

    public String getTicketFooterLine2() {
        return this.footer_line2;
    }

    public String getTicketFooterLine3() {
        return this.footer_line3;
    }

    public String getTicketFooterLine4() {
        return this.footer_line4;
    }

    public String getTicketFooterLine5() {
        return this.footer_line5;
    }

    public String getTicketFooterLine6() {
        return this.footer_line6;
    }
}

