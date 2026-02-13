/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  com.google.gson.JsonParser
 *  org.apache.http.entity.StringEntity
 *  org.json.simple.JSONArray
 *  org.json.simple.JSONObject
 */
package com.openbravo.pos.forms;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.openbravo.data.gui.MessageInf;
import com.openbravo.data.loader.Datas;
import com.openbravo.data.loader.SentenceExec;
import com.openbravo.data.loader.Session;
import com.openbravo.data.model.Row;
import com.openbravo.pos.customers.CustomerInfoExt;
import com.openbravo.pos.forms.AppProperties;
import com.openbravo.pos.forms.AppView;
import com.openbravo.pos.forms.DataLogicSales;
import com.openbravo.pos.payment.PaymentInfo;
import com.openbravo.pos.sales.JPanelTicket;
import com.openbravo.pos.sales.restaurant.RestaurantDBUtils;
import com.openbravo.pos.ticket.TicketInfo;
import com.openbravo.pos.ticket.TicketLineInfo;
import com.openbravo.pos.ticket.TicketTaxInfo;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import javax.swing.JOptionPane;
import javax.swing.ProgressMonitor;
import javax.swing.SwingUtilities;
import org.apache.http.entity.StringEntity;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class EmisionAPI {
    protected Session s;
    protected Datas[] auxiliarDatas;
    protected Datas[] stockdiaryDatas;
    protected Datas[] paymenttabledatas;
    protected Datas[] stockdatas;
    protected Datas[] stockAdjustDatas;
    protected Row productsRow;
    protected Row customersRow;
    protected DataLogicSales dlSales;
    protected AppView m_App;
    private RestaurantDBUtils restDB;
    public static final String DEBT = "debt";
    public static final String DEBT_PAID = "debtpaid";
    protected static final String PREPAY = "prepay";
    protected SentenceExec m_createCat;
    protected SentenceExec m_createSupp;
    private boolean isNoc = false;
    private String nocNumber = "";
    private String nocUuid = "";
    private String nocIssue = "";
    private String nocConcepto = "";
    private String bearerToken = "";

    public static String sanitizeString(String input) {
        if (input == null) {
            return null;
        }
        String sanitized = input;
        sanitized = sanitized.replace("\u00f1", "n").replace("\u00d1", "N");
        sanitized = sanitized.replace("\u00e1", "a").replace("\u00e9", "e").replace("\u00ed", "i").replace("\u00f3", "o").replace("\u00fa", "u").replace("\u00c1", "A").replace("\u00c9", "E").replace("\u00cd", "I").replace("\u00d3", "O").replace("\u00da", "U");
        sanitized = sanitized.replaceAll("[@/*&]", " ");
        sanitized = sanitized.replaceAll("[\"',]", " ");
        sanitized = sanitized.replaceAll("\\s+", " ").trim();
        return sanitized;
    }

    public CompletableFuture<AsyncSaveTicketResponse> saveTicketAsync(TicketInfo ticket, String location, JPanelTicket jpanel, List<PaymentInfo> payments, AppView app, Object ticketext, AppProperties m_config, List<TicketTaxInfo> taxes) {
        ProgressMonitor pm = new ProgressMonitor(jpanel, "Enviando Factura Electr\u00c3\u00b3nica...", "Procesando...", 0, 100);
        pm.setMillisToDecideToPopup(0);
        pm.setMillisToPopup(0);
        pm.setProgress(1);
        return CompletableFuture.supplyAsync(() -> {
            try {
                SwingUtilities.invokeLater(() -> pm.setProgress(10));
                this.m_App = app;
                this.restDB = new RestaurantDBUtils(this.m_App);
                this.dlSales = (DataLogicSales)this.m_App.getBean("com.openbravo.pos.forms.DataLogicSales");
                this.bearerToken = m_config.getProperty("fe.api_token");
                HashMap<String, String> metodosPagoCodigo = new HashMap<String, String>();
                metodosPagoCodigo.put("cash", "10");
                metodosPagoCodigo.put("cashdevolution", "1");
                metodosPagoCodigo.put("cheque", "20");
                metodosPagoCodigo.put("chequedevolution", "1");
                metodosPagoCodigo.put("voucherin", "1");
                metodosPagoCodigo.put("voucherout", "1");
                metodosPagoCodigo.put("slip", "1");
                metodosPagoCodigo.put("free", "ZZZ");
                metodosPagoCodigo.put(DEBT, "31");
                metodosPagoCodigo.put("nequi", "46");
                metodosPagoCodigo.put("tdebido", "49");
                metodosPagoCodigo.put("magcard", "49");
                metodosPagoCodigo.put("debitovisa", "49");
                metodosPagoCodigo.put("debitomastercard", "49");
                metodosPagoCodigo.put("damericanexpress", "49");
                metodosPagoCodigo.put("tcredito", "48");
                metodosPagoCodigo.put("daviplata", "46");
                metodosPagoCodigo.put("bank", "42");
                metodosPagoCodigo.put("creditofalabella", "48");
                metodosPagoCodigo.put("creditomastercard", "48");
                metodosPagoCodigo.put("creditovisa", "48");
                metodosPagoCodigo.put("creditocodensa", "48");
                metodosPagoCodigo.put("creditosistecredito", "48");
                metodosPagoCodigo.put("creditoalkosto", "48");
                metodosPagoCodigo.put("creditoexito", "48");
                metodosPagoCodigo.put("caddi", "48");
                HashMap<String, String> taxCodes = new HashMap<String, String>();
                taxCodes.put("Exento", "01");
                taxCodes.put("IVA", "01");
                taxCodes.put("IVA_5", "01");
                taxCodes.put("IVA_19", "01");
                taxCodes.put("IC", "04");
                taxCodes.put("ICA", "05");
                taxCodes.put("INC", "06");
                taxCodes.put("ReteIVA", "07");
                taxCodes.put("ReteRenta", "08");
                taxCodes.put("ReteICA", "09");
                taxCodes.put("FtoHorticultura", "20");
                taxCodes.put("Timbre", "21");
                taxCodes.put("INC Bolsas", "22");
                taxCodes.put("INCarbono", "23");
                taxCodes.put("INCombustibles", "24");
                taxCodes.put("Sobretasa Combustibles", "25");
                taxCodes.put("Sordicom", "26");
                taxCodes.put("Nombre de la figura tributaria", "ZZ");
                taxCodes.put("IC Porcentual", "28");
                taxCodes.put("IC Datos", "30");
                taxCodes.put("ICL", "32");
                taxCodes.put("INPP", "33");
                taxCodes.put("IBUA", "34");
                taxCodes.put("ICUI", "35");
                taxCodes.put("ADV", "36");
                if (ticket.getIsFe() || ticket.getIsPose() || ticket.getIsDocs()) {
                    String string;
                    String code;
                    String taxName;
                    JSONObject objLineasTax;
                    JSONArray lineasTax;
                    JSONObject jSONObject;
                    Integer consecutivoNOC = 0;
                    Integer consecutivoFE = 0;
                    Integer consecutivoPOSE = 0;
                    SimpleDateFormat formatFecha = new SimpleDateFormat("yyyy-MM-dd");
                    SimpleDateFormat formatHora = new SimpleDateFormat("HH:mm:ss");
                    String fecha = formatFecha.format(ticket.getDate());
                    String hora = formatHora.format(ticket.getDate());
                    CustomerInfoExt cliente = ticket.getCustomer();
                    JSONObject obj = new JSONObject();
                    JSONObject objCliente = new JSONObject();
                    JSONObject objCuentas = new JSONObject();
                    JSONArray lineas = new JSONArray();
                    JSONArray taxGlobal = new JSONArray();
                    if (ticket.getIsNoc()) {
                        consecutivoNOC = this.dlSales.getNextTicketNocIndex();
                        ticket.setTicketId(consecutivoNOC);
                        JSONObject objBilling = new JSONObject();
                        objBilling.put((Object)"number", (Object)ticket.getNocuuid());
                        objBilling.put((Object)"uuid", (Object)ticket.getNocnumber());
                        objBilling.put((Object)"issue_date", (Object)ticket.getNocissue());
                        if (ticket.getIsPose()) {
                            objBilling.put((Object)"type_document_code", (Object)"20");
                        }
                        obj.put((Object)"billing_reference", (Object)objBilling);
                        JSONObject objDiscrepancy = new JSONObject();
                        objDiscrepancy.put((Object)"correction_concept_code", (Object)ticket.getnocConcepto());
                        obj.put((Object)"discrepancy_response", (Object)objDiscrepancy);
                        obj.put((Object)"number", (Object)consecutivoNOC);
                        obj.put((Object)"prefix", (Object)m_config.getProperty("noc.prefix"));
                        if (ticket.getIsPose()) {
                            obj.put((Object)"operation_type_code", (Object)"20");
                            obj.put((Object)"document_type_code", (Object)"94");
                        } else if (ticket.getIsDocs()) {
                            obj.put((Object)"operation_type_code", (Object)"10");
                            obj.put((Object)"document_type_code", (Object)"95");
                        } else {
                            obj.put((Object)"operation_type_code", (Object)"20");
                            obj.put((Object)"document_type_code", (Object)"91");
                        }
                        if (m_config.getProperty("pose.provider") == null || m_config.getProperty("pose.provider").isEmpty()) {
                            obj.put((Object)"identification_number", (Object)m_config.getProperty("pose.provider_id"));
                        }
                        if (ticket.getIsPose()) {
                            obj.put((Object)"resolution_number", (Object)m_config.getProperty("noc.poseresolucion"));
                        } else if (ticket.getIsDocs()) {
                            obj.put((Object)"resolution_number", (Object)m_config.getProperty("noc.docsresolucion"));
                        } else {
                            obj.put((Object)"resolution_number", (Object)m_config.getProperty("noc.resolucion"));
                        }
                        obj.put((Object)"due_date", (Object)fecha);
                        obj.put((Object)"date", (Object)fecha);
                        obj.put((Object)"time", (Object)hora);
                        obj.put((Object)"currency_type_code", (Object)"COP");
                        if (ticket.getCustomer() != null) {
                            obj.put((Object)"send", (Object)true);
                            objCliente.put((Object)"identification_number", (Object)(cliente.getTaxid() == null ? "222222222222" : cliente.getTaxid().replaceAll("\\D+", "")));
                            if (cliente.getPhone2() != null && !cliente.getPhone2().isEmpty()) {
                                System.out.println("-----------------------------------------------> Es Empresa: " + cliente.getPhone2());
                                objCliente.put((Object)"dv", (Object)cliente.getPhone2().replaceAll("\\D+", ""));
                                objCliente.put((Object)"identification_type_code", (Object)"31");
                                objCliente.put((Object)"organization_type_code", (Object)"1");
                            } else {
                                System.out.println("-----------------------------------------------> NO es Empresa ");
                                if (ticket.getIsDocs()) {
                                    objCliente.put((Object)"identification_type_code", (Object)"31");
                                } else {
                                    objCliente.put((Object)"identification_type_code", (Object)"13");
                                }
                                objCliente.put((Object)"organization_type_code", (Object)"2");
                            }
                            objCliente.put((Object)"name", (Object)EmisionAPI.sanitizeString(cliente.getName()));
                            if (cliente.getPhone() != null && !cliente.getPhone().isEmpty()) {
                                objCliente.put((Object)"phone", (Object)cliente.getPhone().replaceAll("\\D+", ""));
                            } else {
                                objCliente.put((Object)"phone", (Object)"3111111111");
                            }
                            objCliente.put((Object)"address", (Object)EmisionAPI.sanitizeString(cliente.getAddress()));
                            objCliente.put((Object)"email", (Object)cliente.getEmail());
                            objCliente.put((Object)"merchant_registration", (Object)"N.A");
                            objCliente.put((Object)"language_code", (Object)"es");
                            objCliente.put((Object)"country_code", (Object)"CO");
                            objCliente.put((Object)"municipality_code", (Object)(cliente.getCity() == null || cliente.getCity().isEmpty() ? "11001" : cliente.getCity()));
                            objCliente.put((Object)"regime_type_code", (Object)(cliente.getTaxCustCategoryID() == null || cliente.getTaxCustCategoryID().isEmpty() ? "49" : cliente.getTaxCustCategoryID()));
                            if ("48".equals(cliente.getTaxCustCategoryID())) {
                                objCliente.put((Object)"tax_code", (Object)"01");
                            } else {
                                objCliente.put((Object)"tax_code", (Object)"ZZ");
                            }
                            objCliente.put((Object)"liability_type_code", (Object)(cliente.getAddress2() == null || cliente.getAddress2().isEmpty() ? "O-47" : cliente.getAddress2()));
                        } else {
                            obj.put((Object)"send", (Object)false);
                            objCliente.put((Object)"identification_number", (Object)"222222222222");
                            objCliente.put((Object)"identification_type_code", (Object)"13");
                            objCliente.put((Object)"organization_type_code", (Object)"2");
                            objCliente.put((Object)"liability_type_code", (Object)"R-99-PN");
                            objCliente.put((Object)"name", (Object)"Consumidor Final");
                            objCliente.put((Object)"phone", (Object)"3111111111");
                            objCliente.put((Object)"address", (Object)"Consumidor Final");
                            objCliente.put((Object)"email", (Object)"consumidorfinal@smartpos.com.co");
                            objCliente.put((Object)"merchant_registration", (Object)"N.A");
                            objCliente.put((Object)"language_code", (Object)"es");
                            objCliente.put((Object)"country_code", (Object)"CO");
                            objCliente.put((Object)"municipality_code", (Object)"11001");
                            objCliente.put((Object)"regime_type_code", (Object)"49");
                            objCliente.put((Object)"tax_code", (Object)"ZZ");
                        }
                        obj.put((Object)"customer", (Object)objCliente);
                        JSONArray jSONArray = new JSONArray();
                        for (PaymentInfo paymentInfo : payments) {
                            jSONObject = new JSONObject();
                            jSONObject.put((Object)"payment_form_code", (Object)"1");
                            String codigo = metodosPagoCodigo.getOrDefault(paymentInfo.getName(), "10");
                            jSONObject.put((Object)"payment_method_code", (Object)codigo);
                            jSONArray.add((Object)jSONObject);
                        }
                        obj.put((Object)"payment_forms", (Object)jSONArray);
                        objCuentas.put((Object)"line_extension_amount", (Object)ticket.getSubTotal());
                        objCuentas.put((Object)"tax_exclusive_amount", (Object)ticket.getSubTotal());
                        objCuentas.put((Object)"tax_inclusive_amount", (Object)ticket.getTotal());
                        objCuentas.put((Object)"allowance_total_amount", (Object)0);
                        objCuentas.put((Object)"charge_total_amount", (Object)0);
                        objCuentas.put((Object)"payable_amount", (Object)ticket.getTotal());
                        obj.put((Object)"legal_monetary_totals", (Object)objCuentas);
                        for (TicketLineInfo ticketLineInfo : ticket.getLines()) {
                            if (ticketLineInfo.getProductID() == null) continue;
                            jSONObject = new JSONObject();
                            jSONObject.put((Object)"unit_measure_code", (Object)"94");
                            jSONObject.put((Object)"invoiced_quantity", (Object)ticketLineInfo.getMultiply());
                            jSONObject.put((Object)"line_extension_amount", (Object)(ticketLineInfo.getPrice() * ticketLineInfo.getMultiply()));
                            jSONObject.put((Object)"free_of_charge_indicator", (Object)false);
                            jSONObject.put((Object)"description", (Object)EmisionAPI.sanitizeString(ticketLineInfo.getProductName()));
                            jSONObject.put((Object)"code", (Object)ticketLineInfo.getProductID());
                            jSONObject.put((Object)"item_identification_type_code", (Object)"999");
                            jSONObject.put((Object)"price_amount", (Object)ticketLineInfo.getPrice());
                            jSONObject.put((Object)"base_quantity", (Object)ticketLineInfo.getMultiply());
                            lineasTax = new JSONArray();
                            objLineasTax = new JSONObject();
                            objLineasTax.put((Object)"unit_measure_code", (Object)"94");
                            objLineasTax.put((Object)"per_unit_amount", (Object)ticketLineInfo.getPrice());
                            objLineasTax.put((Object)"base_unit_measure", (Object)1.0);
                            taxName = ticketLineInfo.getTaxInfo().getName();
                            code = "ZZ";
                            for (Map.Entry entry : taxCodes.entrySet()) {
                                if (!((String)entry.getKey()).equals(taxName)) continue;
                                code = (String)entry.getValue();
                                break;
                            }
                            objLineasTax.put((Object)"tax_code", (Object)code);
                            objLineasTax.put((Object)"tax_amount", (Object)ticketLineInfo.getTax());
                            objLineasTax.put((Object)"taxable_amount", (Object)(ticketLineInfo.getPrice() * ticketLineInfo.getMultiply()));
                            objLineasTax.put((Object)"percent", (Object)(ticketLineInfo.getTaxRate() * 100.0));
                            lineasTax.add((Object)objLineasTax);
                            taxGlobal.add((Object)objLineasTax);
                            jSONObject.put((Object)"tax_totals", (Object)lineasTax);
                            lineas.add((Object)jSONObject);
                        }
                        obj.put((Object)"tax_totals", (Object)taxGlobal);
                        obj.put((Object)"credit_note_lines", (Object)lineas);
                        String urlNoc = "";
                        urlNoc = ticket.getIsPose() ? "https://endpoint.emision.co/api/v1/service/credit-note-equivalent" : (ticket.getIsDocs() ? "https://endpoint.emision.co/api/v1/service/note-support-invoice" : "https://endpoint.emision.co/api/v1/service/credit-note");
                        String string5 = this.sendHTTPData(urlNoc, obj, jpanel);
                        JsonObject jsonObject = new JsonParser().parse((String)((Object)string5)).getAsJsonObject();
                        String sinComillas = jsonObject.getAsJsonObject("document").get("number").toString().replaceAll("^[\"']+|[\"']+$", "");
                        if (jsonObject != null && jsonObject.getAsJsonObject("document") != null) {
                            JsonObject jsonObject2 = jsonObject.getAsJsonObject("document");
                            JsonElement cufeElement = jsonObject2.get("uuid");
                            if (cufeElement != null && !cufeElement.isJsonNull()) {
                                String cufe = cufeElement.getAsString();
                                ticket.setCufe(cufe);
                            } else {
                                System.out.println("Cufe es null o no est\u00e1 presente en el documento.");
                            }
                        } else {
                            System.out.println("El objeto 'document' es null o no est\u00e1 presente en la respuesta.");
                        }
                        File file = new File(System.getProperty("user.home") + "/Desktop/FacturasEmitidas/SmartPOS-NotaCredito-" + sinComillas + ".pdf");
                        try (FileOutputStream fos = new FileOutputStream(file);){
                            String b64 = jsonObject.getAsJsonObject("document").get("pdfBase64Bytes").toString().replaceAll("^[\"']+|[\"']+$", "");
                            byte[] decoder = Base64.getDecoder().decode(b64);
                            fos.write(decoder);
                            System.out.println("Factura Guardada en : " + file);
                        }
                        catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else if (ticket.getIsPose()) {
                        String var35_48 = "";
                        consecutivoPOSE = this.dlSales.getNextTicketPoseIndex();
                        ticket.setTicketId(consecutivoPOSE);
                        obj.put((Object)"number", (Object)consecutivoPOSE);
                        obj.put((Object)"prefix", (Object)m_config.getProperty("till.receiptprefix"));
                        obj.put((Object)"operation_type_code", (Object)"10");
                        obj.put((Object)"document_type_code", (Object)"20");
                        obj.put((Object)"resolution_number", (Object)m_config.getProperty("pose.resolution_name"));
                        obj.put((Object)"date", (Object)fecha);
                        obj.put((Object)"time", (Object)hora);
                        obj.put((Object)"currency_type_code", (Object)"COP");
                        obj.put((Object)"due_date", (Object)fecha);
                        JSONObject cashRegister = new JSONObject();
                        cashRegister.put((Object)"name", (Object)m_config.getProperty("pose.cashier_code"));
                        cashRegister.put((Object)"ubication", (Object)m_config.getProperty("pose.cash_register_code"));
                        cashRegister.put((Object)"cashier", (Object)ticket.printUser());
                        cashRegister.put((Object)"type", (Object)m_config.getProperty("pose.cashier_type"));
                        cashRegister.put((Object)"sale_reference", (Object)consecutivoPOSE.toString());
                        cashRegister.put((Object)"subtotal", (Object)ticket.getSubTotal());
                        obj.put((Object)"cash_register", (Object)cashRegister);
                        JSONObject softwareManufacturer = new JSONObject();
                        softwareManufacturer.put((Object)"software_developer", (Object)"Alejandro Camargo");
                        softwareManufacturer.put((Object)"registration_name", (Object)"Sm@rt Ingenieria y Desarrollo SAS");
                        softwareManufacturer.put((Object)"software_name", (Object)"SmartPOS");
                        obj.put((Object)"software_manufacturer", (Object)softwareManufacturer);
                        JSONObject jSONObject2 = new JSONObject();
                        if (ticket.getCustomer() != null) {
                            obj.put((Object)"send", (Object)true);
                            objCliente.put((Object)"identification_number", (Object)(cliente.getTaxid() == null ? "222222222222" : cliente.getTaxid().replaceAll("\\D+", "")));
                            if (cliente.getPhone2() != null && !cliente.getPhone2().isEmpty()) {
                                objCliente.put((Object)"dv", (Object)cliente.getPhone2().replaceAll("\\D+", ""));
                                objCliente.put((Object)"identification_type_code", (Object)"31");
                                objCliente.put((Object)"organization_type_code", (Object)"1");
                            } else {
                                objCliente.put((Object)"identification_type_code", (Object)"13");
                                objCliente.put((Object)"organization_type_code", (Object)"2");
                            }
                            objCliente.put((Object)"name", (Object)EmisionAPI.sanitizeString(cliente.getName()));
                            objCliente.put((Object)"phone", (Object)(cliente.getPhone() != null && !cliente.getPhone().isEmpty() ? cliente.getPhone().replaceAll("\\D+", "") : "3111111111"));
                            objCliente.put((Object)"address", (Object)EmisionAPI.sanitizeString(cliente.getAddress()));
                            objCliente.put((Object)"email", (Object)cliente.getEmail());
                            objCliente.put((Object)"merchant_registration", (Object)"N.A");
                            objCliente.put((Object)"language_code", (Object)"es");
                            objCliente.put((Object)"country_code", (Object)"CO");
                            objCliente.put((Object)"municipality_code", (Object)(cliente.getCity() == null || cliente.getCity().isEmpty() ? "11001" : cliente.getCity()));
                            objCliente.put((Object)"regime_type_code", (Object)(cliente.getTaxCustCategoryID() == null || cliente.getTaxCustCategoryID().isEmpty() ? "49" : cliente.getTaxCustCategoryID()));
                            objCliente.put((Object)"tax_code", (Object)("48".equals(cliente.getTaxCustCategoryID()) ? "01" : "ZZ"));
                            objCliente.put((Object)"liability_type_code", (Object)(cliente.getAddress2() == null || cliente.getAddress2().isEmpty() ? "O-47" : cliente.getAddress2()));
                            jSONObject2.put((Object)"identification_number", (Object)(cliente.getTaxid() == null ? "222222222222" : cliente.getTaxid().replaceAll("\\D+", "")));
                            jSONObject2.put((Object)"name", (Object)cliente.getName());
                            jSONObject2.put((Object)"points", (Object)0);
                        } else {
                            obj.put((Object)"send", (Object)false);
                            objCliente.put((Object)"identification_number", (Object)"222222222222");
                            objCliente.put((Object)"identification_type_code", (Object)"13");
                            objCliente.put((Object)"organization_type_code", (Object)"2");
                            objCliente.put((Object)"liability_type_code", (Object)"R-99-PN");
                            objCliente.put((Object)"name", (Object)"Consumidor Final");
                            objCliente.put((Object)"phone", (Object)"3111111111");
                            objCliente.put((Object)"address", (Object)"Consumidor Final");
                            objCliente.put((Object)"email", (Object)"consumidorfinal@smartpos.com.co");
                            objCliente.put((Object)"merchant_registration", (Object)"N.A");
                            objCliente.put((Object)"language_code", (Object)"es");
                            objCliente.put((Object)"country_code", (Object)"CO");
                            objCliente.put((Object)"municipality_code", (Object)"11001");
                            objCliente.put((Object)"regime_type_code", (Object)"49");
                            objCliente.put((Object)"tax_code", (Object)"ZZ");
                            jSONObject2.put((Object)"identification_number", (Object)"222222222222");
                            jSONObject2.put((Object)"name", (Object)"Consumidor Final");
                            jSONObject2.put((Object)"points", (Object)0);
                        }
                        obj.put((Object)"customer_benefits", (Object)jSONObject2);
                        obj.put((Object)"customer", (Object)objCliente);
                        JSONArray jSONArray = new JSONArray();
                        for (PaymentInfo paymentInfo : payments) {
                            JSONObject objPago = new JSONObject();
                            objPago.put((Object)"payment_form_code", (Object)"1");
                            String string2 = metodosPagoCodigo.getOrDefault(paymentInfo.getName(), "10");
                            objPago.put((Object)"payment_method_code", (Object)string2);
                            jSONArray.add((Object)objPago);
                        }
                        obj.put((Object)"payment_forms", (Object)jSONArray);
                        objCuentas.put((Object)"line_extension_amount", (Object)ticket.getSubTotal());
                        objCuentas.put((Object)"tax_exclusive_amount", (Object)ticket.getSubTotal());
                        objCuentas.put((Object)"tax_inclusive_amount", (Object)ticket.getTotal());
                        objCuentas.put((Object)"allowance_total_amount", (Object)0);
                        objCuentas.put((Object)"charge_total_amount", (Object)0);
                        objCuentas.put((Object)"payable_amount", (Object)ticket.getTotal());
                        obj.put((Object)"legal_monetary_totals", (Object)objCuentas);
                        for (TicketLineInfo ticketLineInfo : ticket.getLines()) {
                            if (ticketLineInfo.getProductID() == null) continue;
                            JSONObject objLinea = new JSONObject();
                            objLinea.put((Object)"unit_measure_code", (Object)"94");
                            objLinea.put((Object)"invoiced_quantity", (Object)ticketLineInfo.getMultiply());
                            objLinea.put((Object)"line_extension_amount", (Object)(ticketLineInfo.getPrice() * ticketLineInfo.getMultiply()));
                            objLinea.put((Object)"free_of_charge_indicator", (Object)false);
                            objLinea.put((Object)"description", (Object)EmisionAPI.sanitizeString(ticketLineInfo.getProductName()));
                            objLinea.put((Object)"code", (Object)ticketLineInfo.getProductID());
                            objLinea.put((Object)"item_identification_type_code", (Object)"999");
                            objLinea.put((Object)"price_amount", (Object)ticketLineInfo.getPrice());
                            objLinea.put((Object)"base_quantity", (Object)ticketLineInfo.getMultiply());
                            JSONArray jSONArray2 = new JSONArray();
                            JSONObject objLineasTax2 = new JSONObject();
                            objLineasTax2.put((Object)"unit_measure_code", (Object)"94");
                            objLineasTax2.put((Object)"per_unit_amount", (Object)ticketLineInfo.getPrice());
                            objLineasTax2.put((Object)"base_unit_measure", (Object)1.0);
                            String taxName2 = ticketLineInfo.getTaxInfo().getName();
                            String code2 = taxCodes.getOrDefault(taxName2, "ZZ");
                            objLineasTax2.put((Object)"tax_code", (Object)code2);
                            objLineasTax2.put((Object)"tax_amount", (Object)ticketLineInfo.getTax());
                            objLineasTax2.put((Object)"taxable_amount", (Object)(ticketLineInfo.getPrice() * ticketLineInfo.getMultiply()));
                            objLineasTax2.put((Object)"percent", (Object)(ticketLineInfo.getTaxRate() * 100.0));
                            jSONArray2.add((Object)objLineasTax2);
                            taxGlobal.add((Object)objLineasTax2);
                            objLinea.put((Object)"tax_totals", (Object)jSONArray2);
                            lineas.add((Object)objLinea);
                        }
                        obj.put((Object)"tax_totals", (Object)taxGlobal);
                        obj.put((Object)"invoice_lines", (Object)lineas);
                        JSONObject jSONObject22 = new JSONObject();
                        jSONObject22.put((Object)"payment_form_code", (Object)"2");
                        jSONObject22.put((Object)"payment_method_code", (Object)"10");
                        jSONObject22.put((Object)"payment_due_date", (Object)fecha);
                        jSONObject22.put((Object)"duration_measure", (Object)1);
                        obj.put((Object)"payment_form", (Object)jSONObject22);
                        string = this.sendHTTPData("https://endpoint.emision.co/api/v1/service/pos", obj, jpanel);
                        JsonObject objRespuesta = new JsonParser().parse(string).getAsJsonObject();
                        String string3 = "";
                        if (objRespuesta != null && objRespuesta.has("document") && !objRespuesta.get("document").isJsonNull() && objRespuesta.getAsJsonObject("document").has("number")) {
                            String string4 = objRespuesta.getAsJsonObject("document").get("number").toString().replaceAll("^[\"']+|[\"']+$", "");
                        }
                        if (objRespuesta != null && objRespuesta.has("document") && !objRespuesta.get("document").isJsonNull()) {
                            JsonObject documentObject = objRespuesta.getAsJsonObject("document");
                            JsonElement cufeElement = documentObject.get("uuid");
                            if (cufeElement != null && !cufeElement.isJsonNull()) {
                                String cufe = cufeElement.getAsString();
                                ticket.setCufe(cufe);
                            } else {
                                System.out.println("Cufe es null o no est\u00e1 presente en el documento.");
                            }
                        } else {
                            System.out.println("El objeto 'document' es null o no est\u00e1 presente en la respuesta.");
                        }
                        File file = new File(System.getProperty("user.home") + "/Desktop/FacturasEmitidas/SmartPOS-" + (String)var35_48 + ".pdf");
                        try (FileOutputStream fos = new FileOutputStream(file);){
                            String b64;
                            String string5 = b64 = objRespuesta != null && objRespuesta.has("document") && !objRespuesta.get("document").isJsonNull() && objRespuesta.getAsJsonObject("document").has("pdfBase64Bytes") ? objRespuesta.getAsJsonObject("document").get("pdfBase64Bytes").toString().replaceAll("^[\"']+|[\"']+$", "") : "";
                            if (!b64.isEmpty()) {
                                byte[] decoder = Base64.getDecoder().decode(b64);
                                fos.write(decoder);
                                System.out.println("Factura Guardada en : " + file);
                            }
                        }
                        catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else if (ticket.getIsDocs()) {
                        Integer consecutivoDocs = this.dlSales.getNextTicketDocsIndex();
                        ticket.setTicketId(consecutivoDocs);
                        obj.put((Object)"number", (Object)consecutivoDocs);
                        obj.put((Object)"identification_number", (Object)Integer.valueOf(m_config.getProperty("docs.nit")));
                        obj.put((Object)"prefix", (Object)m_config.getProperty("docs.prefix"));
                        obj.put((Object)"document_type_code", (Object)"05");
                        obj.put((Object)"resolution_number", (Object)m_config.getProperty("docs.resolucionfe"));
                        obj.put((Object)"date", (Object)fecha);
                        obj.put((Object)"time", (Object)hora);
                        obj.put((Object)"currency_type_code", (Object)"COP");
                        if (ticket.getCustomer() != null) {
                            obj.put((Object)"send", (Object)true);
                            objCliente.put((Object)"identification_number", (Object)(cliente.getTaxid() == null ? "222222222222" : cliente.getTaxid().replaceAll("\\D+", "")));
                            if (cliente.getPhone2() != null && !cliente.getPhone2().isEmpty()) {
                                objCliente.put((Object)"dv", (Object)cliente.getPhone2().replaceAll("\\D+", ""));
                                objCliente.put((Object)"identification_type_code", (Object)"31");
                                objCliente.put((Object)"organization_type_code", (Object)"1");
                                obj.put((Object)"operation_type_code", (Object)"10");
                            } else {
                                objCliente.put((Object)"identification_type_code", (Object)"13");
                                objCliente.put((Object)"organization_type_code", (Object)"2");
                                obj.put((Object)"operation_type_code", (Object)"11");
                            }
                            objCliente.put((Object)"name", (Object)EmisionAPI.sanitizeString(cliente.getName()));
                            if (cliente.getPhone() != null && !cliente.getPhone().isEmpty()) {
                                objCliente.put((Object)"phone", (Object)cliente.getPhone().replaceAll("\\D+", ""));
                            } else {
                                objCliente.put((Object)"phone", (Object)"3111111111");
                            }
                            objCliente.put((Object)"address", (Object)EmisionAPI.sanitizeString(cliente.getAddress()));
                            objCliente.put((Object)"email", (Object)cliente.getEmail());
                            objCliente.put((Object)"merchant_registration", (Object)"N.A");
                            objCliente.put((Object)"language_code", (Object)"es");
                            objCliente.put((Object)"country_code", (Object)"CO");
                            objCliente.put((Object)"municipality_code", (Object)(cliente.getCity() == null || cliente.getCity().isEmpty() ? "11001" : cliente.getCity()));
                            objCliente.put((Object)"regime_type_code", (Object)(cliente.getTaxCustCategoryID() == null || cliente.getTaxCustCategoryID().isEmpty() ? "49" : cliente.getTaxCustCategoryID()));
                            if ("48".equals(cliente.getTaxCustCategoryID())) {
                                objCliente.put((Object)"tax_code", (Object)"01");
                            } else {
                                objCliente.put((Object)"tax_code", (Object)"ZZ");
                            }
                            objCliente.put((Object)"liability_type_code", (Object)(cliente.getAddress2() == null || cliente.getAddress2().isEmpty() ? "O-47" : cliente.getAddress2()));
                        } else {
                            obj.put((Object)"send", (Object)false);
                            objCliente.put((Object)"identification_number", (Object)"222222222222");
                            objCliente.put((Object)"identification_type_code", (Object)"13");
                            objCliente.put((Object)"organization_type_code", (Object)"2");
                            objCliente.put((Object)"liability_type_code", (Object)"R-99-PN");
                            objCliente.put((Object)"name", (Object)"Consumidor Final");
                            objCliente.put((Object)"phone", (Object)"3111111111");
                            objCliente.put((Object)"address", (Object)"Consumidor Final");
                            objCliente.put((Object)"email", (Object)"consumidorfinal@smartpos.com.co");
                            objCliente.put((Object)"merchant_registration", (Object)"N.A");
                            objCliente.put((Object)"language_code", (Object)"es");
                            objCliente.put((Object)"country_code", (Object)"CO");
                            objCliente.put((Object)"municipality_code", (Object)"11001");
                            objCliente.put((Object)"regime_type_code", (Object)"49");
                            objCliente.put((Object)"tax_code", (Object)"ZZ");
                        }
                        obj.put((Object)"customer", (Object)objCliente);
                        JSONArray metodosPagos = new JSONArray();
                        for (PaymentInfo paymentInfo : payments) {
                            JSONObject jSONObject3 = new JSONObject();
                            jSONObject3.put((Object)"payment_form_code", (Object)"1");
                            string = metodosPagoCodigo.getOrDefault(paymentInfo.getName(), "10");
                            jSONObject3.put((Object)"payment_method_code", (Object)string);
                            metodosPagos.add((Object)jSONObject3);
                        }
                        obj.put((Object)"payment_forms", (Object)metodosPagos);
                        objCuentas.put((Object)"line_extension_amount", (Object)ticket.getSubTotal());
                        objCuentas.put((Object)"tax_exclusive_amount", (Object)ticket.getSubTotal());
                        objCuentas.put((Object)"tax_inclusive_amount", (Object)ticket.getTotal());
                        objCuentas.put((Object)"allowance_total_amount", (Object)0);
                        objCuentas.put((Object)"charge_total_amount", (Object)0);
                        objCuentas.put((Object)"payable_amount", (Object)ticket.getTotal());
                        obj.put((Object)"legal_monetary_totals", (Object)objCuentas);
                        for (TicketLineInfo ticketLineInfo : ticket.getLines()) {
                            if (ticketLineInfo.getProductID() == null) continue;
                            JSONObject jSONObject2 = new JSONObject();
                            JSONObject jSONObject32 = new JSONObject();
                            if (obj.get((Object)"operation_type_code") == "11") {
                                jSONObject2.put((Object)"item_sector_identification_type_code", (Object)" 0");
                            }
                            jSONObject2.put((Object)"unit_measure_code", (Object)"94");
                            jSONObject2.put((Object)"invoiced_quantity", (Object)ticketLineInfo.getMultiply());
                            jSONObject2.put((Object)"line_extension_amount", (Object)(ticketLineInfo.getPrice() * ticketLineInfo.getMultiply()));
                            jSONObject2.put((Object)"description", (Object)EmisionAPI.sanitizeString(ticketLineInfo.getProductName()));
                            jSONObject2.put((Object)"code", (Object)ticketLineInfo.getProductID());
                            jSONObject2.put((Object)"item_identification_type_code", (Object)"999");
                            jSONObject2.put((Object)"price_amount", (Object)ticketLineInfo.getPrice());
                            jSONObject2.put((Object)"base_quantity", (Object)ticketLineInfo.getMultiply());
                            jSONObject2.put((Object)"tax_amount", (Object)ticketLineInfo.getTax());
                            jSONObject2.put((Object)"taxable_amount", (Object)(ticketLineInfo.getPrice() * ticketLineInfo.getMultiply()));
                            jSONObject32.put((Object)"form_generation_transmission_code", (Object)1);
                            jSONObject32.put((Object)"start_date", (Object)fecha);
                            jSONObject2.put((Object)"invoice_period", (Object)jSONObject32);
                            lineasTax = new JSONArray();
                            objLineasTax = new JSONObject();
                            objLineasTax.put((Object)"unit_measure_code", (Object)"94");
                            objLineasTax.put((Object)"per_unit_amount", (Object)ticketLineInfo.getPrice());
                            objLineasTax.put((Object)"base_unit_measure", (Object)1.0);
                            taxName = ticketLineInfo.getTaxInfo().getName();
                            code = "ZZ";
                            for (Map.Entry entry : taxCodes.entrySet()) {
                                if (!((String)entry.getKey()).equals(taxName)) continue;
                                code = (String)entry.getValue();
                                break;
                            }
                            objLineasTax.put((Object)"tax_code", (Object)code);
                            objLineasTax.put((Object)"tax_amount", (Object)ticketLineInfo.getTax());
                            objLineasTax.put((Object)"taxable_amount", (Object)(ticketLineInfo.getPrice() * ticketLineInfo.getMultiply()));
                            objLineasTax.put((Object)"percent", (Object)(ticketLineInfo.getTaxRate() * 100.0));
                            lineasTax.add((Object)objLineasTax);
                            taxGlobal.add((Object)objLineasTax);
                            jSONObject2.put((Object)"tax_totals", (Object)lineasTax);
                            lineas.add((Object)jSONObject2);
                        }
                        obj.put((Object)"tax_totals", (Object)taxGlobal);
                        obj.put((Object)"invoice_lines", (Object)lineas);
                        String string3 = this.sendHTTPData("https://endpoint.emision.co/api/v1/service/support-invoice", obj, jpanel);
                        JsonObject jsonObject = new JsonParser().parse(string3).getAsJsonObject();
                        String string6 = jsonObject.getAsJsonObject("document").get("number").toString().replaceAll("^[\"']+|[\"']+$", "");
                        if (jsonObject != null && jsonObject.getAsJsonObject("document") != null) {
                            JsonObject jsonObject2 = jsonObject.getAsJsonObject("document");
                            JsonElement cufeElement = jsonObject2.get("uuid");
                            if (cufeElement != null && !cufeElement.isJsonNull()) {
                                String string7 = cufeElement.getAsString();
                                ticket.setCufe(string7);
                            } else {
                                System.out.println("Cufe es null o no est\u00e1 presente en el documento.");
                            }
                        } else {
                            System.out.println("El objeto 'document' es null o no est\u00e1 presente en la respuesta.");
                        }
                        File file = new File(System.getProperty("user.home") + "/Desktop/FacturasEmitidas/SmartPOS-" + string6 + ".pdf");
                        try (FileOutputStream fos = new FileOutputStream(file);){
                            String string8 = jsonObject.getAsJsonObject("document").get("pdfBase64Bytes").toString().replaceAll("^[\"']+|[\"']+$", "");
                            byte[] decoder = Base64.getDecoder().decode(string8);
                            fos.write(decoder);
                            System.out.println("Factura Guardada en : " + file);
                        }
                        catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        consecutivoFE = this.dlSales.getNextTicketFeIndex();
                        ticket.setTicketId(consecutivoFE);
                        String prefixFe = m_config.getProperty("till.receiptprefixfe");
                        String resolucionFe = m_config.getProperty("till.resolucionfe");
                        obj.put((Object)"number", (Object)(consecutivoFE != null ? consecutivoFE : 0));
                        obj.put((Object)"prefix", (Object)(prefixFe != null ? prefixFe : "SET"));
                        obj.put((Object)"operation_type_code", (Object)"10");
                        obj.put((Object)"document_type_code", (Object)"01");
                        obj.put((Object)"resolution_number", (Object)(resolucionFe != null ? resolucionFe : "000000"));
                        obj.put((Object)"date", (Object)fecha);
                        obj.put((Object)"time", (Object)hora);
                        obj.put((Object)"currency_type_code", (Object)"COP");
                        if (ticket.getCustomer() != null) {
                            obj.put((Object)"send", (Object)true);
                            objCliente.put((Object)"identification_number", (Object)(cliente.getTaxid() == null ? "222222222222" : cliente.getTaxid().replaceAll("\\D+", "")));
                            if (cliente.getPhone2() != null && !cliente.getPhone2().isEmpty()) {
                                System.out.println("-----------------------------------------------> Es Empresa: " + cliente.getPhone2());
                                objCliente.put((Object)"dv", (Object)cliente.getPhone2().replaceAll("\\D+", ""));
                                objCliente.put((Object)"identification_type_code", (Object)"31");
                                objCliente.put((Object)"organization_type_code", (Object)"1");
                            } else {
                                System.out.println("-----------------------------------------------> NO es Empresa ");
                                objCliente.put((Object)"identification_type_code", (Object)"13");
                                objCliente.put((Object)"organization_type_code", (Object)"2");
                            }
                            objCliente.put((Object)"name", (Object)EmisionAPI.sanitizeString(cliente.getName()));
                            if (cliente.getPhone() != null && !cliente.getPhone().isEmpty()) {
                                objCliente.put((Object)"phone", (Object)cliente.getPhone().replaceAll("\\D+", ""));
                            } else {
                                objCliente.put((Object)"phone", (Object)"3111111111");
                            }
                            objCliente.put((Object)"address", (Object)EmisionAPI.sanitizeString(cliente.getAddress()));
                            objCliente.put((Object)"email", (Object)cliente.getEmail());
                            objCliente.put((Object)"merchant_registration", (Object)"N.A");
                            objCliente.put((Object)"language_code", (Object)"es");
                            objCliente.put((Object)"country_code", (Object)"CO");
                            objCliente.put((Object)"municipality_code", (Object)(cliente.getCity() == null || cliente.getCity().isEmpty() ? "11001" : cliente.getCity()));
                            objCliente.put((Object)"regime_type_code", (Object)(cliente.getTaxCustCategoryID() == null || cliente.getTaxCustCategoryID().isEmpty() ? "49" : cliente.getTaxCustCategoryID()));
                            if ("48".equals(cliente.getTaxCustCategoryID())) {
                                objCliente.put((Object)"tax_code", (Object)"01");
                            } else {
                                objCliente.put((Object)"tax_code", (Object)"ZZ");
                            }
                            objCliente.put((Object)"liability_type_code", (Object)(cliente.getAddress2() == null || cliente.getAddress2().isEmpty() ? "O-47" : cliente.getAddress2()));
                        } else {
                            obj.put((Object)"send", (Object)false);
                            objCliente.put((Object)"identification_number", (Object)"222222222222");
                            objCliente.put((Object)"identification_type_code", (Object)"13");
                            objCliente.put((Object)"organization_type_code", (Object)"2");
                            objCliente.put((Object)"liability_type_code", (Object)"R-99-PN");
                            objCliente.put((Object)"name", (Object)"Consumidor Final");
                            objCliente.put((Object)"phone", (Object)"3111111111");
                            objCliente.put((Object)"address", (Object)"Consumidor Final");
                            objCliente.put((Object)"email", (Object)"consumidorfinal@smartpos.com.co");
                            objCliente.put((Object)"merchant_registration", (Object)"N.A");
                            objCliente.put((Object)"language_code", (Object)"es");
                            objCliente.put((Object)"country_code", (Object)"CO");
                            objCliente.put((Object)"municipality_code", (Object)"11001");
                            objCliente.put((Object)"regime_type_code", (Object)"49");
                            objCliente.put((Object)"tax_code", (Object)"ZZ");
                        }
                        obj.put((Object)"customer", (Object)objCliente);
                        JSONArray metodosPagos = new JSONArray();
                        for (PaymentInfo paymentInfo : payments) {
                            jSONObject = new JSONObject();
                            jSONObject.put((Object)"payment_form_code", (Object)"1");
                            String string4 = metodosPagoCodigo.getOrDefault(paymentInfo.getName(), "10");
                            jSONObject.put((Object)"payment_method_code", (Object)string4);
                            metodosPagos.add((Object)jSONObject);
                        }
                        obj.put((Object)"payment_forms", (Object)metodosPagos);
                        objCuentas.put((Object)"line_extension_amount", (Object)ticket.getSubTotal());
                        objCuentas.put((Object)"tax_exclusive_amount", (Object)ticket.getSubTotal());
                        objCuentas.put((Object)"tax_inclusive_amount", (Object)ticket.getTotal());
                        objCuentas.put((Object)"allowance_total_amount", (Object)0);
                        objCuentas.put((Object)"charge_total_amount", (Object)0);
                        objCuentas.put((Object)"payable_amount", (Object)ticket.getTotal());
                        obj.put((Object)"legal_monetary_totals", (Object)objCuentas);
                        for (TicketLineInfo ticketLineInfo : ticket.getLines()) {
                            if (ticketLineInfo.getProductID() == null) continue;
                            jSONObject = new JSONObject();
                            jSONObject.put((Object)"unit_measure_code", (Object)"94");
                            jSONObject.put((Object)"invoiced_quantity", (Object)ticketLineInfo.getMultiply());
                            jSONObject.put((Object)"line_extension_amount", (Object)(ticketLineInfo.getPrice() * ticketLineInfo.getMultiply()));
                            jSONObject.put((Object)"free_of_charge_indicator", (Object)false);
                            jSONObject.put((Object)"description", (Object)EmisionAPI.sanitizeString(ticketLineInfo.getProductName()));
                            jSONObject.put((Object)"code", (Object)ticketLineInfo.getProductID());
                            jSONObject.put((Object)"item_identification_type_code", (Object)"999");
                            jSONObject.put((Object)"price_amount", (Object)ticketLineInfo.getPrice());
                            jSONObject.put((Object)"base_quantity", (Object)ticketLineInfo.getMultiply());
                            JSONArray jSONArray = new JSONArray();
                            JSONObject jSONObject3 = new JSONObject();
                            jSONObject3.put((Object)"unit_measure_code", (Object)"94");
                            jSONObject3.put((Object)"per_unit_amount", (Object)ticketLineInfo.getPrice());
                            jSONObject3.put((Object)"base_unit_measure", (Object)1.0);
                            String taxName3 = ticketLineInfo.getTaxInfo().getName();
                            String code3 = "ZZ";
                            for (Map.Entry entry : taxCodes.entrySet()) {
                                if (!((String)entry.getKey()).equals(taxName3)) continue;
                                code3 = (String)entry.getValue();
                                break;
                            }
                            jSONObject3.put((Object)"tax_code", (Object)code3);
                            jSONObject3.put((Object)"tax_amount", (Object)ticketLineInfo.getTax());
                            jSONObject3.put((Object)"taxable_amount", (Object)(ticketLineInfo.getPrice() * ticketLineInfo.getMultiply()));
                            jSONObject3.put((Object)"percent", (Object)(ticketLineInfo.getTaxRate() * 100.0));
                            jSONArray.add((Object)jSONObject3);
                            taxGlobal.add((Object)jSONObject3);
                            jSONObject.put((Object)"tax_totals", (Object)jSONArray);
                            lineas.add((Object)jSONObject);
                        }
                        obj.put((Object)"tax_totals", (Object)taxGlobal);
                        obj.put((Object)"invoice_lines", (Object)lineas);
                        String respuesta = this.sendHTTPData("https://endpoint.emision.co/api/v1/service/invoice", obj, jpanel);
                        JsonObject jsonObject = new JsonParser().parse(respuesta).getAsJsonObject();
                        String stringNumber = "";
                        if (jsonObject != null && jsonObject.has("document") && !jsonObject.get("document").isJsonNull() && jsonObject.getAsJsonObject("document").has("number")) {
                            stringNumber = jsonObject.getAsJsonObject("document").get("number").toString().replaceAll("^[\"']+|[\"']+$", "");
                        }
                        if (jsonObject != null && jsonObject.getAsJsonObject("document") != null) {
                            JsonObject jsonObject3 = jsonObject.getAsJsonObject("document");
                            JsonElement jsonElement = jsonObject3.get("uuid");
                            if (jsonElement != null && !jsonElement.isJsonNull()) {
                                String cufe = jsonElement.getAsString();
                                ticket.setCufe(cufe);
                            } else {
                                System.out.println("Cufe es null o no est\u00e1 presente en el documento.");
                            }
                        } else {
                            System.out.println("El objeto 'document' es null o no est\u00e1 presente en la respuesta.");
                        }
                        File file = new File(System.getProperty("user.home") + "/Desktop/FacturasEmitidas/SmartPOS-" + stringNumber + ".pdf");
                        try (FileOutputStream fileOutputStream = new FileOutputStream(file);){
                            String b64;
                            String string9 = b64 = jsonObject != null && jsonObject.has("document") && !jsonObject.get("document").isJsonNull() && jsonObject.getAsJsonObject("document").has("pdfBase64Bytes") ? jsonObject.getAsJsonObject("document").get("pdfBase64Bytes").toString().replaceAll("^[\"']+|[\"']+$", "") : "";
                            if (!b64.isEmpty()) {
                                byte[] decoder = Base64.getDecoder().decode(b64);
                                fileOutputStream.write(decoder);
                                System.out.println("Factura Guardada en : " + file);
                            }
                            SwingUtilities.invokeLater(() -> {
                                pm.close();
                                JOptionPane.showMessageDialog(jpanel, "Factura Electr\u00c3\u00b3nica Emitida Correctamente.\nCUFE/UUID: " + ticket.getCufe() + "\nGuardada en: " + file.getAbsolutePath(), "\u00c3\u2030xito FE", 1);
                            });
                        }
                        catch (Exception exception) {
                            exception.printStackTrace();
                        }
                    }
                }
                ArrayList<String> cufeDiv = new ArrayList<String>();
                if (ticket.getCufe() != null && !ticket.getCufe().isEmpty()) {
                    for (int start = 0; start < ticket.getCufe().length(); start += 52) {
                        cufeDiv.add(ticket.getCufe().substring(start, Math.min(ticket.getCufe().length(), start + 52)));
                    }
                }
                ticket.setcufeDiv(cufeDiv);
                ticket.setPayments(payments);
                ticket.setTaxes(taxes);
                AsyncSaveTicketResponse asyncSaveTicketResponse = new AsyncSaveTicketResponse(ticket, ticketext != null ? ticketext : new Object(){

                    public String toString() {
                        return "N.A";
                    }
                });
                return asyncSaveTicketResponse;
            }
            catch (Exception e) {
                throw new RuntimeException(e);
            }
            finally {
                SwingUtilities.invokeLater(() -> pm.close());
            }
        });
    }

    public void setIsNoc(String cufe, String factura, String fecha, String concepto) {
        this.isNoc = true;
        this.nocNumber = factura;
        this.nocUuid = cufe;
        this.nocIssue = fecha;
        this.nocConcepto = concepto;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public String sendHTTPData(String urlpath, JSONObject json, JPanelTicket jpanel) {
        HttpURLConnection connection = null;
        boolean errorp = false;
        try {
            String responseStr;
            block21: {
                InputStream _is;
                URL url = new URL(urlpath);
                connection = (HttpURLConnection)url.openConnection();
                connection.setDoOutput(true);
                connection.setDoInput(true);
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Content-Type", "application/json");
                connection.setRequestProperty("Accept", "application/json");
                connection.setRequestProperty("Authorization", "Bearer " + this.bearerToken);
                OutputStreamWriter streamWriter = new OutputStreamWriter(connection.getOutputStream());
                String jsons = new StringEntity(json.toString(), "UTF-8").toString();
                System.out.println("REST: Antes de envio json.toString: ---------------------------------> " + json.toString());
                streamWriter.write(json.toString());
                streamWriter.flush();
                StringBuilder stringBuilder = new StringBuilder();
                if (connection.getResponseCode() < 400) {
                    _is = connection.getInputStream();
                    errorp = false;
                } else {
                    _is = connection.getErrorStream();
                    errorp = true;
                }
                if (_is == null) {
                    String string = "{\"error\": \"No response stream available\"}";
                    return string;
                }
                InputStreamReader streamReader = new InputStreamReader(_is);
                BufferedReader bufferedReader = new BufferedReader(streamReader);
                String responseLine = null;
                while ((responseLine = bufferedReader.readLine()) != null) {
                    stringBuilder.append(responseLine + "\n");
                }
                bufferedReader.close();
                System.out.println("REST: Envio OK respuesta: ---------------------------------> " + stringBuilder.toString() + " esto es cuerpo: " + connection.getResponseMessage());
                responseStr = stringBuilder.toString();
                try {
                    JsonElement jsonElement = new JsonParser().parse(responseStr);
                    if (jsonElement != null && jsonElement.isJsonObject()) {
                        JsonObject objRespuesta = jsonElement.getAsJsonObject();
                        if (errorp) {
                            String razon = "Error desconocido";
                            String errores = "Sin detalles";
                            if (objRespuesta.has("document") && !objRespuesta.get("document").isJsonNull()) {
                                JsonObject doc = objRespuesta.getAsJsonObject("document");
                                if (doc != null) {
                                    if (doc.has("statusDescription") && !doc.get("statusDescription").isJsonNull()) {
                                        razon = doc.get("statusDescription").getAsString();
                                    }
                                    if (doc.has("errors") && !doc.get("errors").isJsonNull()) {
                                        errores = doc.get("errors").toString();
                                    }
                                }
                            } else if (objRespuesta.has("message") && !objRespuesta.get("message").isJsonNull()) {
                                razon = objRespuesta.get("message").getAsString();
                            }
                            MessageInf msg = new MessageInf(-33554432, "Emisi\u00f3n API - Error: " + razon, errores);
                            msg.show(jpanel);
                        }
                    }
                }
                catch (Exception e) {
                    System.err.println("Error parsing response: " + e.getMessage());
                    if (!errorp) break block21;
                    MessageInf msg = new MessageInf(-33554432, "Error en la comunicaci\u00f3n con el proveedor (HTTP " + connection.getResponseCode() + ")", responseStr);
                    msg.show(jpanel);
                }
            }
            String string = responseStr;
            return string;
        }
        catch (IOException exception) {
            String string;
            MessageInf msg = new MessageInf(-67108864, "La factura electr\u00f3nica no pudo ser enviada a la DIAN - Revise Su resoluci\u00f3n, consecutivo y ajustes de emisi\u00f3n. - Recomendamos Reiniciar SmartPOS", json.toString());
            msg.show(jpanel);
            System.out.println("REST: Excepcion -----------------> " + exception.toString());
            String string2 = string = null;
            return string2;
        }
        finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

    public class AsyncSaveTicketResponse {
        private final TicketInfo ticket;
        private final Object ticketext;

        public AsyncSaveTicketResponse(TicketInfo ticket, Object ticketext) {
            this.ticket = ticket;
            this.ticketext = ticketext;
        }

        public TicketInfo getTicket() {
            return this.ticket;
        }

        public Object getTicketext() {
            return this.ticketext;
        }
    }
}

