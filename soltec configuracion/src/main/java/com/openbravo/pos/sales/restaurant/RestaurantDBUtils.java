/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.sales.restaurant;

import com.openbravo.data.loader.Session;
import com.openbravo.pos.forms.AppView;
import com.openbravo.pos.forms.DataLogicSystem;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class RestaurantDBUtils {
    private Session s;
    private Connection con;
    private Statement stmt;
    private PreparedStatement pstmt;
    private String SQL;
    private ResultSet rs;
    private AppView m_App;
    protected DataLogicSystem dlSystem;

    public RestaurantDBUtils(AppView oApp) {
        this.m_App = oApp;
        try {
            this.s = this.m_App.getSession();
            this.con = this.s.getConnection();
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    public void moveCustomer(String newTable, String ticketID) {
        String oldTable = this.getTableDetails(ticketID);
        if (this.countTicketIdInTable(ticketID) > 1) {
            this.setCustomerNameInTable(this.getCustomerNameInTable(oldTable), newTable);
            this.setWaiterNameInTable(this.getWaiterNameInTable(oldTable), newTable);
            this.setTicketIdInTable(ticketID, newTable);
            oldTable = this.getTableMovedName(ticketID);
            if (oldTable != null && oldTable != newTable) {
                this.clearCustomerNameInTable(oldTable);
                this.clearWaiterNameInTable(oldTable);
                this.clearTicketIdInTable(oldTable);
                this.clearTableMovedFlag(oldTable);
            } else {
                oldTable = this.getTableMovedName(ticketID);
                this.clearTableMovedFlag(oldTable);
            }
        }
    }

    public void setCustomerNameInTable(String custName, String tableName) {
        try {
            this.SQL = "UPDATE places SET CUSTOMER=? WHERE NAME=?";
            this.pstmt = this.con.prepareStatement(this.SQL);
            this.pstmt.setString(1, custName);
            this.pstmt.setString(2, tableName);
            this.pstmt.executeUpdate();
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    public void setCustomerNameInTableById(String custName, String tableID) {
        try {
            this.SQL = "UPDATE places SET CUSTOMER=? WHERE ID=?";
            this.pstmt = this.con.prepareStatement(this.SQL);
            this.pstmt.setString(1, custName);
            this.pstmt.setString(2, tableID);
            this.pstmt.executeUpdate();
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    public void setCustomerNameInTableByTicketId(String custName, String ticketID) {
        try {
            this.SQL = "UPDATE places SET CUSTOMER=? WHERE TICKETID=?";
            this.pstmt = this.con.prepareStatement(this.SQL);
            this.pstmt.setString(1, custName);
            this.pstmt.setString(2, ticketID);
            this.pstmt.executeUpdate();
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    public String getCustomerNameInTable(String tableName) {
        try {
            this.SQL = "SELECT CUSTOMER FROM places WHERE NAME='" + tableName + "'";
            this.stmt = this.con.createStatement();
            this.rs = this.stmt.executeQuery(this.SQL);
            if (this.rs.next()) {
                String customer = this.rs.getString("CUSTOMER");
                return customer;
            }
        }
        catch (Exception exception) {
            // empty catch block
        }
        return "";
    }

    public String getCustomerNameInTableById(String tableId) {
        try {
            this.SQL = "SELECT CUSTOMER FROM places WHERE ID='" + tableId + "'";
            this.stmt = this.con.createStatement();
            this.rs = this.stmt.executeQuery(this.SQL);
            if (this.rs.next()) {
                String customer = this.rs.getString("CUSTOMER");
                return customer;
            }
        }
        catch (Exception exception) {
            // empty catch block
        }
        return "";
    }

    public void clearCustomerNameInTable(String tableName) {
        try {
            this.SQL = "UPDATE places SET CUSTOMER=null WHERE NAME=?";
            this.pstmt = this.con.prepareStatement(this.SQL);
            this.pstmt.setString(1, tableName);
            this.pstmt.executeUpdate();
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    public void clearCustomerNameInTableById(String tableID) {
        try {
            this.SQL = "UPDATE places SET CUSTOMER=null WHERE ID=?";
            this.pstmt = this.con.prepareStatement(this.SQL);
            this.pstmt.setString(1, tableID);
            this.pstmt.executeUpdate();
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    public void setWaiterNameInTable(String waiterName, String tableName) {
        try {
            this.SQL = "UPDATE places SET WAITER=? WHERE NAME=?";
            this.pstmt = this.con.prepareStatement(this.SQL);
            this.pstmt.setString(1, waiterName);
            this.pstmt.setString(2, tableName);
            this.pstmt.executeUpdate();
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    public void setWaiterNameInTableById(String waiterName, String tableID) {
        try {
            this.SQL = "UPDATE places SET WAITER=? WHERE ID=?";
            this.pstmt = this.con.prepareStatement(this.SQL);
            this.pstmt.setString(1, waiterName);
            this.pstmt.setString(2, tableID);
            this.pstmt.executeUpdate();
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    public String getWaiterNameInTable(String tableName) {
        try {
            this.SQL = "SELECT WAITER FROM places WHERE NAME='" + tableName + "'";
            this.stmt = this.con.createStatement();
            this.rs = this.stmt.executeQuery(this.SQL);
            if (this.rs.next()) {
                String waiter = this.rs.getString("WAITER");
                return waiter;
            }
        }
        catch (Exception exception) {
            // empty catch block
        }
        return "";
    }

    public String getWaiterNameInTableById(String tableID) {
        try {
            this.SQL = "SELECT WAITER FROM places WHERE ID='" + tableID + "'";
            this.stmt = this.con.createStatement();
            this.rs = this.stmt.executeQuery(this.SQL);
            if (this.rs.next()) {
                String waiter = this.rs.getString("WAITER");
                return waiter;
            }
        }
        catch (Exception exception) {
            // empty catch block
        }
        return "";
    }

    public void clearWaiterNameInTable(String tableName) {
        try {
            this.SQL = "UPDATE places SET WAITER=null WHERE NAME=?";
            this.pstmt = this.con.prepareStatement(this.SQL);
            this.pstmt.setString(1, tableName);
            this.pstmt.executeUpdate();
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    public void clearWaiterNameInTableById(String tableID) {
        try {
            this.SQL = "UPDATE places SET WAITER=null WHERE ID=?";
            this.pstmt = this.con.prepareStatement(this.SQL);
            this.pstmt.setString(1, tableID);
            this.pstmt.executeUpdate();
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    public String getTicketIdInTable(String ID) {
        try {
            this.SQL = "SELECT TICKETID FROM places WHERE ID='" + ID + "'";
            this.stmt = this.con.createStatement();
            this.rs = this.stmt.executeQuery(this.SQL);
            if (this.rs.next()) {
                String customer = this.rs.getString("TICKETID");
                return customer;
            }
        }
        catch (Exception exception) {
            // empty catch block
        }
        return "";
    }

    public void setTicketIdInTable(String TicketID, String tableName) {
        try {
            this.SQL = "UPDATE places SET TICKETID=? WHERE NAME=?";
            this.pstmt = this.con.prepareStatement(this.SQL);
            this.pstmt.setString(1, TicketID);
            this.pstmt.setString(2, tableName);
            this.pstmt.executeUpdate();
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    public void clearTicketIdInTable(String tableName) {
        try {
            this.SQL = "UPDATE places SET TICKETID=null WHERE NAME=?";
            this.pstmt = this.con.prepareStatement(this.SQL);
            this.pstmt.setString(1, tableName);
            this.pstmt.executeUpdate();
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    public void clearTicketIdInTableById(String tableID) {
        try {
            this.SQL = "UPDATE places SET TICKETID=null WHERE ID=?";
            this.pstmt = this.con.prepareStatement(this.SQL);
            this.pstmt.setString(1, tableID);
            this.pstmt.executeUpdate();
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    public Integer countTicketIdInTable(String ticketID) {
        try {
            this.SQL = "SELECT COUNT(*) AS RECORDCOUNT FROM places WHERE TICKETID='" + ticketID + "'";
            this.stmt = this.con.createStatement();
            this.rs = this.stmt.executeQuery(this.SQL);
            if (this.rs.next()) {
                Integer count = this.rs.getInt("RECORDCOUNT");
                return count;
            }
        }
        catch (Exception exception) {
            // empty catch block
        }
        return 0;
    }

    public String getTableDetails(String ticketID) {
        try {
            this.SQL = "SELECT NAME FROM places WHERE TICKETID='" + ticketID + "'";
            this.stmt = this.con.createStatement();
            this.rs = this.stmt.executeQuery(this.SQL);
            if (this.rs.next()) {
                String name = this.rs.getString("NAME");
                return name;
            }
        }
        catch (Exception exception) {
            // empty catch block
        }
        return "";
    }

    public void setTableMovedFlag(String tableID) {
        try {
            this.SQL = "UPDATE places SET TABLEMOVED='true' WHERE ID=?";
            this.pstmt = this.con.prepareStatement(this.SQL);
            this.pstmt.setString(1, tableID);
            this.pstmt.executeUpdate();
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    public String getTableMovedName(String ticketID) {
        try {
            this.SQL = "SELECT NAME FROM places WHERE TICKETID='" + ticketID + "' AND TABLEMOVED ='true'";
            this.stmt = this.con.createStatement();
            this.rs = this.stmt.executeQuery(this.SQL);
            if (this.rs.next()) {
                String name = this.rs.getString("NAME");
                return name;
            }
        }
        catch (Exception exception) {
            // empty catch block
        }
        return null;
    }

    public Boolean getTableMovedFlag(String ticketID) {
        try {
            this.SQL = "SELECT TABLEMOVED FROM places WHERE TICKETID='" + ticketID + "'";
            this.stmt = this.con.createStatement();
            this.rs = this.stmt.executeQuery(this.SQL);
            if (this.rs.next()) {
                return this.rs.getBoolean("TABLEMOVED");
            }
        }
        catch (Exception exception) {
            // empty catch block
        }
        return false;
    }

    public void clearTableMovedFlag(String tableID) {
        try {
            this.SQL = "UPDATE places SET TABLEMOVED='false' WHERE NAME=?";
            this.pstmt = this.con.prepareStatement(this.SQL);
            this.pstmt.setString(1, tableID);
            this.pstmt.executeUpdate();
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    public int getNextTableNumber() {
        try {
            this.SQL = "SELECT NAME FROM places";
            this.stmt = this.con.createStatement();
            this.rs = this.stmt.executeQuery(this.SQL);
            int max = 0;
            while (this.rs.next()) {
                String name = this.rs.getString("NAME");
                try {
                    int num;
                    String numberOnly = name.replaceAll("[^0-9]", "");
                    if (numberOnly.isEmpty() || (num = Integer.parseInt(numberOnly)) <= max) continue;
                    max = num;
                }
                catch (NumberFormatException numberFormatException) {}
            }
            return max + 1;
        }
        catch (Exception e) {
            return 1;
        }
    }

    public void createPlace(String id, String name, int x, int y, String floor) {
        try {
            this.SQL = "INSERT INTO places (ID, NAME, X, Y, FLOOR) VALUES (?, ?, ?, ?, ?)";
            this.pstmt = this.con.prepareStatement(this.SQL);
            this.pstmt.setString(1, id);
            this.pstmt.setString(2, name);
            this.pstmt.setInt(3, x);
            this.pstmt.setInt(4, y);
            this.pstmt.setString(5, floor);
            this.pstmt.executeUpdate();
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    public boolean isPlaceNameExists(String name) {
        try {
            this.SQL = "SELECT ID FROM places WHERE NAME = ?";
            this.pstmt = this.con.prepareStatement(this.SQL);
            this.pstmt.setString(1, name);
            this.rs = this.pstmt.executeQuery();
            return this.rs.next();
        }
        catch (Exception e) {
            return false;
        }
    }
}

