package com.openbravo.pos.admin;

import java.io.*;
import java.sql.*;
import java.util.Properties;
import java.util.UUID;
import com.openbravo.pos.util.AltEncrypter;

public class CreateMaestroRole {
    public static void main(String[] args) {
        Connection conn = null;
        try {
            Properties props = new Properties();
            File propertiesFile = new File("soltec.properties");
            if (!propertiesFile.exists()) {
                propertiesFile = new File(System.getProperty("user.home"), "soltec.properties");
            }
            props.load(new FileInputStream(propertiesFile));

            String dbUrl = props.getProperty("db.URL");
            String dbUser = props.getProperty("db.user");
            String dbPass = props.getProperty("db.password");

            if (dbPass.startsWith("crypt:")) {
                AltEncrypter cypher = new AltEncrypter("cypherkey" + dbUser);
                dbPass = cypher.decrypt(dbPass.substring(6));
            }

            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(dbUrl, dbUser, dbPass);

            // Construct the Permissions XML in the "Manager" style
            StringBuilder xml = new StringBuilder();
            xml.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
            xml.append("<!--\n");
            xml.append("    Maestro Role - Full System Access\n");
            xml.append("    Formatted as requested by User\n");
            xml.append("-->\n");
            xml.append("<permissions>\n");
            
            xml.append("    <!-- *************************************************************** -->\n");
            xml.append("    <!-- MAIN MENU & SYSTEM -->\n");
            xml.append("    <!-- *************************************************************** -->\n");
            xml.append("    <class name=\"Menu.Main\"/>\n");
            xml.append("    <class name=\"Menu.Sales\"/>\n");
            xml.append("    <class name=\"Menu.Customers\"/>\n");
            xml.append("    <class name=\"Menu.Stock\"/>\n");
            xml.append("    <class name=\"Menu.Inventory\"/>\n");
            xml.append("    <class name=\"Menu.Maintenance\"/>\n");
            xml.append("    <class name=\"Menu.System\"/>\n");
            xml.append("    <class name=\"Menu.ChangePassword\"/>\n");
            xml.append("    <class name=\"Menu.Exit\"/>\n");
            xml.append("    <class name=\"Menu.GlobalExpenses\"/>\n");

            xml.append("\n");
            xml.append("    <!-- *************************************************************** -->\n");
            xml.append("    <!-- SALES -->\n");
            xml.append("    <!-- *************************************************************** -->\n");
            xml.append("    <class name=\"com.openbravo.pos.sales.JPanelTicket\"/>\n");
            xml.append("    <class name=\"com.openbravo.pos.sales.JPanelTicketSales\"/>\n");
            xml.append("    <class name=\"com.openbravo.pos.sales.JPanelTicketEdits\"/>\n");
            xml.append("    <class name=\"sales.EditLines\"/>\n");
            xml.append("    <class name=\"sales.EditTicket\"/>\n");
            xml.append("    <class name=\"sales.RefundTicket\"/>\n");
            xml.append("    <class name=\"sales.PrintTicket\"/>\n");
            xml.append("    <class name=\"sales.Total\"/>\n");
            xml.append("    <class name=\"sales.Override\"/>\n");
            xml.append("    <class name=\"sales.ViewSharedTicket\"/>\n");
            xml.append("    <class name=\"sales.DeleteLines\"/>\n");
            xml.append("    <class name=\"sales.ChangeTaxOptions\"/>\n");
            xml.append("    <class name=\"sales.PrintKitchen\"/>\n");
            xml.append("    <class name=\"sales.TablePlan\"/>\n");

            xml.append("\n");
            xml.append("    <!-- *************************************************************** -->\n");
            xml.append("    <!-- PAYMENTS -->\n");
            xml.append("    <!-- *************************************************************** -->\n");
            xml.append("    <class name=\"com.openbravo.pos.panels.JPanelPayments\"/>\n");
            xml.append("    <class name=\"com.openbravo.pos.panels.JPanelCloseMoney\"/>\n");
            xml.append("    <class name=\"com.openbravo.pos.panels.JPanelCloseMoneyReprint\"/>\n");
            xml.append("    <class name=\"payment.cash\"/>\n");
            xml.append("    <class name=\"payment.cheque\"/>\n");
            xml.append("    <class name=\"payment.paper\"/>\n");
            xml.append("    <class name=\"payment.magcard\"/>\n");
            xml.append("    <class name=\"payment.free\"/>\n");
            xml.append("    <class name=\"payment.debt\"/>\n");
            xml.append("    <class name=\"payment.gateway\"/>\n");
            xml.append("    <class name=\"payment.bank\"/>\n");
            xml.append("    <class name=\"payment.voucher\"/>\n");
            xml.append("    <class name=\"payment.slip\"/>\n");

            xml.append("\n");
            xml.append("    <!-- *************************************************************** -->\n");
            xml.append("    <!-- CUSTOMERS -->\n");
            xml.append("    <!-- *************************************************************** -->\n");
            xml.append("    <class name=\"com.openbravo.pos.forms.MenuCustomers\"/>\n");
            xml.append("    <class name=\"com.openbravo.pos.customers.CustomersPanel\"/>\n");
            xml.append("    <class name=\"com.openbravo.pos.customers.CustomersPayment\"/>\n");
            
            xml.append("\n");
            xml.append("    <!-- *************************************************************** -->\n");
            xml.append("    <!-- INVENTORY & STOCK -->\n");
            xml.append("    <!-- *************************************************************** -->\n");
            xml.append("    <class name=\"com.openbravo.pos.forms.MenuStockManagement\"/>\n");
            xml.append("    <class name=\"com.openbravo.pos.inventory.StockManagement\"/>\n");
            xml.append("    <class name=\"com.openbravo.pos.inventory.ProductsPanel\"/>\n");
            xml.append("    <class name=\"com.openbravo.pos.inventory.ProductsWarehousePanel\"/>\n");
            xml.append("    <class name=\"com.openbravo.pos.inventory.CategoriesPanel\"/>\n");
            xml.append("    <class name=\"com.openbravo.pos.inventory.TaxPanel\"/>\n");
            xml.append("    <class name=\"com.openbravo.pos.inventory.StockDiaryPanel\"/>\n");
            xml.append("    <class name=\"com.openbravo.pos.inventory.LocationsPanel\"/>\n");
            xml.append("    <class name=\"com.openbravo.pos.inventory.AttributeUsePanel\"/>\n");
            xml.append("    <class name=\"com.openbravo.pos.inventory.AttributeSetsPanel\"/>\n");
            xml.append("    <class name=\"com.openbravo.pos.inventory.AttributePanel\"/>\n");
            xml.append("    <class name=\"com.openbravo.pos.inventory.AttributeValuesPanel\"/>\n");
            xml.append("    <class name=\"com.openbravo.pos.inventory.AuxiliarPanel\"/>\n");
            xml.append("    <class name=\"com.openbravo.pos.inventory.BundlePanel\"/>\n");
            xml.append("    <class name=\"com.openbravo.pos.inventory.JPanelLowStock\"/>\n");
            xml.append("    <class name=\"com.openbravo.pos.inventory.JPanelAudit\"/>\n"); // Added
            xml.append("    <class name=\"com.openbravo.pos.inventory.TaxCategoriesPanel\"/>\n");
            xml.append("    <class name=\"com.openbravo.pos.inventory.TaxCustCategoriesPanel\"/>\n");
            xml.append("    <class name=\"com.openbravo.pos.inventory.UomPanel\"/>\n");
            
            xml.append("\n");
            xml.append("    <!-- *************************************************************** -->\n");
            xml.append("    <!-- MAINTENANCE & ADMIN -->\n");
            xml.append("    <!-- *************************************************************** -->\n");
            xml.append("    <class name=\"com.openbravo.pos.forms.MenuMaintenance\"/>\n");
            xml.append("    <class name=\"com.openbravo.pos.mant.JPanelPlaces\"/>\n");
            xml.append("    <class name=\"com.openbravo.pos.mant.JPanelFloors\"/>\n");
            xml.append("    <class name=\"com.openbravo.pos.admin.PeoplePanel\"/>\n");
            xml.append("    <class name=\"com.openbravo.pos.admin.RolesPanel\"/>\n");
            xml.append("    <class name=\"com.openbravo.pos.admin.ResourcesPanel\"/>\n");
            xml.append("    <class name=\"com.openbravo.pos.config.JPanelConfiguration\"/>\n");
            xml.append("    <class name=\"com.openbravo.pos.maintenance.JPanelDBMaintenance\"/>\n");
            xml.append("    <class name=\"com.openbravo.pos.printer.JPanelPrinter\"/>\n");
            xml.append("    <class name=\"com.openbravo.pos.panels.JPanelGlobalExpenses\"/>\n");
            
            xml.append("\n");
            xml.append("    <!-- *************************************************************** -->\n");
            xml.append("    <!-- SUPPLIERS -->\n");
            xml.append("    <!-- *************************************************************** -->\n");
            xml.append("    <class name=\"com.openbravo.pos.forms.MenuSuppliers\"/>\n");
            xml.append("    <class name=\"com.openbravo.pos.suppliers.SuppliersPanel\"/>\n");

            xml.append("\n");
            xml.append("    <!-- *************************************************************** -->\n");
            xml.append("    <!-- IMPORTS -->\n");
            xml.append("    <!-- *************************************************************** -->\n");
            xml.append("    <class name=\"com.openbravo.pos.imports.JPanelCSV\"/>\n");
            xml.append("    <class name=\"com.openbravo.pos.imports.JPanelCSVImport\"/>\n");
            xml.append("    <class name=\"com.openbravo.pos.imports.JPanelCSVCleardb\"/>\n");
            xml.append("    <class name=\"/com/openbravo/reports/updatedprices.bs\"/>\n"); // Added
            xml.append("    <class name=\"/com/openbravo/reports/newproducts.bs\"/>\n"); // Added
            xml.append("    <class name=\"/com/openbravo/reports/missingdata.bs\"/>\n"); // Added
            xml.append("    <class name=\"/com/openbravo/reports/invaliddata.bs\"/>\n"); // Added
            xml.append("    <class name=\"/com/openbravo/reports/badprice.bs\"/>\n"); // Added
            xml.append("    <class name=\"/com/openbravo/reports/invalidcategory.bs\"/>\n"); // Added

            xml.append("\n");
            xml.append("    <!-- *************************************************************** -->\n");
            xml.append("    <!-- BUTTON SCRIPTS -->\n");
            xml.append("    <!-- *************************************************************** -->\n");
            xml.append("    <class name=\"button.totaldiscount\"/>\n");
            xml.append("    <class name=\"button.print\"/>\n");
            xml.append("    <class name=\"button.opendrawer\"/>\n");
            xml.append("    <class name=\"button.linediscount\"/>\n");
            xml.append("    <class name=\"button.sendorder\"/>\n");
            xml.append("    <class name=\"button.refundit\"/>\n");
            xml.append("    <class name=\"button.scharge\"/>\n");

            xml.append("\n");
            xml.append("    <!-- *************************************************************** -->\n");
            xml.append("    <!-- REPORTS -->\n");
            xml.append("    <!-- *************************************************************** -->\n");
            xml.append("    <class name=\"com.openbravo.pos.forms.MenuSalesManagement\"/>\n");
            xml.append("    <class name=\"/com/openbravo/reports/sales_paymentsummary.bs\"/>\n");
            xml.append("    <class name=\"/com/openbravo/reports/closedpos.bs\"/>\n");
            xml.append("    <class name=\"/com/openbravo/reports/products.bs\"/>\n");
            xml.append("    <class name=\"/com/openbravo/reports/productscatalog.bs\"/>\n");
            xml.append("    <class name=\"/com/openbravo/reports/inventory.bs\"/>\n");
            xml.append("    <class name=\"/com/openbravo/reports/inventoryb.bs\"/>\n");
            xml.append("    <class name=\"/com/openbravo/reports/inventorybroken.bs\"/>\n");
            xml.append("    <class name=\"/com/openbravo/reports/inventorydiff.bs\"/>\n");
            xml.append("    <class name=\"/com/openbravo/reports/people.bs\"/>\n");
            xml.append("    <class name=\"/com/openbravo/reports/taxes.bs\"/>\n");
            xml.append("    <class name=\"/com/openbravo/reports/usersales.bs\"/>\n");
            xml.append("    <class name=\"/com/openbravo/reports/customers.bs\"/>\n");
            xml.append("    <class name=\"/com/openbravo/reports/customersb.bs\"/>\n");
            xml.append("    <class name=\"/com/openbravo/reports/closedproducts.bs\"/>\n");
            xml.append("    <class name=\"/com/openbravo/reports/chartsales.bs\"/>\n");
            xml.append("    <class name=\"/com/openbravo/reports/customers_sales.bs\"/>\n");
            xml.append("    <class name=\"/com/openbravo/reports/customers_debtors.bs\"/>\n");
            xml.append("    <class name=\"/com/openbravo/reports/customers_diary.bs\"/>\n");
            xml.append("    <class name=\"/com/openbravo/reports/customers_cards.bs\"/>\n");
            xml.append("    <class name=\"/com/openbravo/reports/customers_list.bs\"/>\n");
            xml.append("    <class name=\"/com/openbravo/reports/customers_export.bs\"/>\n");
            xml.append("    <class name=\"/com/openbravo/reports/suppliers.bs\"/>\n");
            xml.append("    <class name=\"/com/openbravo/reports/suppliers_b.bs\"/>\n");
            xml.append("    <class name=\"/com/openbravo/reports/suppliers_creditors.bs\"/>\n");
            xml.append("    <class name=\"/com/openbravo/reports/suppliers_diary.bs\"/>\n");
            xml.append("    <class name=\"/com/openbravo/reports/suppliers_list.bs\"/>\n");
            xml.append("    <class name=\"/com/openbravo/reports/suppliers_sales.bs\"/>\n");
            xml.append("    <class name=\"/com/openbravo/reports/suppliers_export.bs\"/>\n");
            xml.append("    <class name=\"/com/openbravo/reports/suppliers_products.bs\"/>\n");
            xml.append("    <class name=\"/com/openbravo/reports/barcode_sheet.bs\"/>\n");
            xml.append("    <class name=\"/com/openbravo/reports/barcode_shelfedgelabels.bs\"/>\n");
            xml.append("    <class name=\"/com/openbravo/reports/inventory_diary.bs\"/>\n");
            xml.append("    <class name=\"/com/openbravo/reports/inventorydiffdetail.bs\"/>\n");
            xml.append("    <class name=\"/com/openbravo/reports/inventorylistdetail.bs\"/>\n");
            xml.append("    <class name=\"/com/openbravo/reports/productlabels.bs\"/>\n");
            xml.append("    <class name=\"/com/openbravo/reports/salecatalog.bs\"/>\n");
            xml.append("    <class name=\"/com/openbravo/reports/sales_cashflow.bs\"/>\n");
            xml.append("    <class name=\"/com/openbravo/reports/sales_cashregisterlog.bs\"/>\n");
            xml.append("    <class name=\"/com/openbravo/reports/sales_categorysales.bs\"/>\n");
            xml.append("    <class name=\"/com/openbravo/reports/sales_closedpos.bs\"/>\n");
            xml.append("    <class name=\"/com/openbravo/reports/sales_closedpos_1.bs\"/>\n");
            xml.append("    <class name=\"/com/openbravo/reports/sales_closedproducts.bs\"/>\n");
            xml.append("    <class name=\"/com/openbravo/reports/sales_closedproducts_1.bs\"/>\n");
            xml.append("    <class name=\"/com/openbravo/reports/sales_extendedcashregisterlog.bs\"/>\n");
            xml.append("    <class name=\"/com/openbravo/reports/sales_extproducts.bs\"/>\n");
            xml.append("    <class name=\"/com/openbravo/reports/sales_paymentreport.bs\"/>\n");
            xml.append("    <class name=\"/com/openbravo/reports/sales_expenses.bs\"/>\n");
            xml.append("    <class name=\"/com/openbravo/reports/sales_productsalesprofit.bs\"/>\n");
            xml.append("    <class name=\"/com/openbravo/reports/sales_saletaxes.bs\"/>\n");
            xml.append("    <class name=\"/com/openbravo/reports/sales_taxcatsales.bs\"/>\n");
            xml.append("    <class name=\"/com/openbravo/reports/sales_taxes.bs\"/>\n");
            xml.append("    <class name=\"/com/openbravo/reports/sales_chart_chartsales.bs\"/>\n");
            xml.append("    <class name=\"/com/openbravo/reports/sales_chart_piesalescat.bs\"/>\n");
            xml.append("    <class name=\"/com/openbravo/reports/sales_chart_productsales.bs\"/>\n");
            xml.append("    <class name=\"/com/openbravo/reports/sales_chart_timeseriesproduct.bs\"/>\n");
            xml.append("    <class name=\"/com/openbravo/reports/sales_chart_top10sales.bs\"/>\n");
            xml.append("    <class name=\"/com/openbravo/reports/usernosales.bs\"/>\n");
            
            xml.append("</permissions>");

            String maestroPermissions = xml.toString();
            String maestroRoleName = "maestro";
            String maestroId = UUID.randomUUID().toString();

            // Check if role exists
            PreparedStatement checkStmt = conn.prepareStatement("SELECT ID FROM ROLES WHERE NAME = ?");
            checkStmt.setString(1, maestroRoleName);
            ResultSet checkRs = checkStmt.executeQuery();
            
            if (checkRs.next()) {
                System.out.println("Role 'maestro' already exists. Updating permissions...");
                PreparedStatement updateStmt = conn.prepareStatement("UPDATE ROLES SET PERMISSIONS = ? WHERE NAME = ?");
                updateStmt.setBytes(1, maestroPermissions.getBytes("UTF-8"));
                updateStmt.setString(2, maestroRoleName);
                updateStmt.executeUpdate();
                System.out.println("Permissions updated.");
            } else {
                System.out.println("Creating new role 'maestro'...");
                PreparedStatement insertStmt = conn.prepareStatement("INSERT INTO ROLES (ID, NAME, PERMISSIONS) VALUES (?, ?, ?)");
                insertStmt.setString(1, maestroId);
                insertStmt.setString(2, maestroRoleName);
                insertStmt.setBytes(3, maestroPermissions.getBytes("UTF-8"));
                insertStmt.executeUpdate();
                System.out.println("Role created.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try { if (conn != null) conn.close(); } catch (SQLException e) {}
        }
    }
}
