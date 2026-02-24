/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.sales;

import com.openbravo.data.loader.Session;
import com.openbravo.pos.forms.AppView;
import com.openbravo.pos.forms.DataLogicSystem;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

public class KitchenDisplay {
    private Session s;
    private Connection con;
    private Statement stmt;
    private PreparedStatement pstmt;
    private String SQL;
    private ResultSet rs;
    private AppView m_App;
    protected DataLogicSystem dlSystem;

    public KitchenDisplay(AppView oApp) {
        this.m_App = oApp;
        try {
            this.s = this.m_App.getSession();
            this.con = this.s.getConnection();
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    public void addRecord(String ID, String table, String pickupID, String product, String multiply, String attributes) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        Date date = new Date();
        try {
            this.SQL = "INSERT INTO KITCHENDISPLAY (ID, ORDERTIME, PLACE, PICKUPID, PRODUCT, MULTIPLY, ATTRIBUTES) VALUES (?, ?, ?, ?, ?, ?, ?) ";
            this.pstmt = this.con.prepareStatement(this.SQL);
            this.pstmt.setString(1, ID);
            this.pstmt.setString(2, dateFormat.format(date).toString());
            this.pstmt.setString(3, table);
            this.pstmt.setString(4, pickupID);
            this.pstmt.setString(5, product);
            this.pstmt.setString(6, multiply);
            this.pstmt.setString(7, attributes);
            this.pstmt.executeUpdate();
        }
        catch (Exception exception) {
            // empty catch block
        }
    }
}

