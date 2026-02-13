/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.data.loader;

import com.openbravo.data.loader.Session;
import com.openbravo.pos.forms.AppConfig;
import java.io.File;

public class TicketHeader {
    private String header_line1;
    private String header_line2;
    private String header_line3;
    private String header_line4;
    private String header_line5;
    private String header_line6;
    private File m_config;
    private Session session;

    public TicketHeader() {
        AppConfig config = new AppConfig(this.m_config);
    }

    public void loadProperties(AppConfig config) {
        this.header_line1 = config.getProperty("till.header1");
        this.header_line2 = config.getProperty("till.header2");
        this.header_line3 = config.getProperty("till.header3");
        this.header_line4 = config.getProperty("till.header4");
        this.header_line5 = config.getProperty("till.header5");
        this.header_line6 = config.getProperty("till.header6");
    }

    public String getTicketHeaderLine1() {
        return this.header_line1;
    }

    public String getTicketHeaderLine2() {
        return this.header_line2;
    }

    public String getTicketHeaderLine3() {
        return this.header_line3;
    }

    public String getTicketHeaderLine4() {
        return this.header_line4;
    }

    public String getTicketHeaderLine5() {
        return this.header_line5;
    }

    public String getTicketHeaderLine6() {
        return this.header_line6;
    }
}

