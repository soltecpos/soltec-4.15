/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.csvreader.CsvReader
 */
package com.openbravo.pos.imports;

import com.csvreader.CsvReader;
import com.openbravo.basic.BasicException;
import com.openbravo.data.loader.Session;
import com.openbravo.data.user.SaveProvider;
import com.openbravo.pos.customers.CustomerInfoExt;
import com.openbravo.pos.customers.DataLogicCustomers;
import com.openbravo.pos.forms.AppConfig;
import com.openbravo.pos.forms.AppLocal;
import com.openbravo.pos.forms.AppProperties;
import com.openbravo.pos.forms.AppView;
import com.openbravo.pos.forms.AppViewConnection;
import com.openbravo.pos.forms.DataLogicSales;
import com.openbravo.pos.forms.DataLogicSystem;
import com.openbravo.pos.forms.JPanelView;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import javax.swing.SwingWorker;
import javax.swing.border.LineBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileNameExtensionFilter;

public class CustomerCSVImport
extends JPanel
implements JPanelView {
    private ArrayList<String> Headers = new ArrayList();
    private Session s;
    private Connection con;
    private String csvFileName;
    private String csvMessage = "";
    private CsvReader customers;
    private int currentRecord;
    private int rowCount = 0;
    private String last_folder;
    private File config_file;
    private DataLogicSales m_dlSales;
    private DataLogicSystem m_dlSystem;
    private DataLogicCustomers m_dlCustomer;
    protected SaveProvider<Object[]> spr;
    private String customerSearchKey;
    private String customerAccount;
    private String customerName;
    private String customerAddress1;
    private String customerAddress2;
    private String customerPostal;
    private String customerCity;
    private String customerRegion;
    private String customerFirstName;
    private String customerLastName;
    private String customerEmail;
    private String customerPhone;
    private String customerPhone2;
    private DocumentListener documentListener;
    private CustomerInfoExt custInfo;
    private String recordType = null;
    private int newRecords = 0;
    private int invalid = 0;
    private int updated = 0;
    private int missing = 0;
    private int noChange = 0;
    private int bad = 0;
    private Integer progress = 0;
    private JCheckBox jCheckVisible;
    private JComboBox<String> jComboAccountID;
    private JComboBox<String> jComboAddress1;
    private JComboBox<String> jComboAddress2;
    private JComboBox<String> jComboCity;
    private JComboBox<String> jComboEmail;
    private JComboBox<String> jComboFirstName;
    private JComboBox<String> jComboLastName;
    private JComboBox<String> jComboName;
    private JComboBox<String> jComboPhone;
    private JComboBox<String> jComboPhone2;
    private JComboBox<String> jComboPostal;
    private JComboBox<String> jComboRegion;
    private JComboBox<String> jComboSearchKey;
    private JComboBox<String> jComboSeparator;
    private JPanel jFileChooserPanel;
    private JTextField jFileName;
    private JLabel jLabel1;
    private JLabel jLabel10;
    private JLabel jLabel11;
    private JLabel jLabel12;
    private JLabel jLabel18;
    private JLabel jLabel20;
    private JLabel jLabel21;
    private JLabel jLabel22;
    private JLabel jLabel23;
    private JLabel jLabel3;
    private JLabel jLabel4;
    private JLabel jLabel5;
    private JLabel jLabel6;
    private JLabel jLabel7;
    private JLabel jLabel8;
    private JLabel jLabel9;
    private JPanel jPanel1;
    private JPanel jPanel2;
    private JScrollPane jScrollPane1;
    private JButton jbtnFileChoose;
    private JButton jbtnImport;
    private JButton jbtnRead;
    private JButton jbtnReset;
    private JLabel jlblBad;
    private JLabel jlblInvalid;
    private JLabel jlblMissing;
    private JLabel jlblNew;
    private JLabel jlblNotChanged;
    private JLabel jlblRecords;
    private JLabel jlblUpdates;
    private JTextField jtxtBad;
    private JTextField jtxtInvalid;
    private JTextField jtxtMissing;
    private JTextField jtxtNew;
    private JTextField jtxtNoChange;
    private JTextField jtxtRecords;
    private JTextField jtxtUpdate;
    private JProgressBar webPBar;

    public CustomerCSVImport(AppView oApp) {
        this(oApp.getProperties());
    }

    public CustomerCSVImport(AppProperties props) {
        this.initComponents();
        try {
            this.s = AppViewConnection.createSession(props);
            this.con = this.s.getConnection();
        }
        catch (BasicException | SQLException exception) {
            // empty catch block
        }
        this.m_dlSales = new DataLogicSales();
        this.m_dlSales.init(this.s);
        this.m_dlSystem = new DataLogicSystem();
        this.m_dlSystem.init(this.s);
        this.spr = new SaveProvider<Object[]>(this.m_dlSales.getCustomerUpdate(), this.m_dlSales.getCustomerInsert(), this.m_dlSales.getCustomerDelete());
        this.last_folder = props.getProperty("CSV.last_folder");
        this.config_file = props.getConfigFile();
        this.jFileName.getDocument().addDocumentListener(this.documentListener);
        this.documentListener = new DocumentListener(){

            @Override
            public void changedUpdate(DocumentEvent documentEvent) {
                CustomerCSVImport.this.jbtnRead.setEnabled(true);
            }

            @Override
            public void insertUpdate(DocumentEvent documentEvent) {
                if (!"".equals(CustomerCSVImport.this.jFileName.getText().trim())) {
                    CustomerCSVImport.this.jbtnRead.setEnabled(true);
                }
            }

            @Override
            public void removeUpdate(DocumentEvent documentEvent) {
                if (CustomerCSVImport.this.jFileName.getText().trim().equals("")) {
                    CustomerCSVImport.this.jbtnRead.setEnabled(false);
                }
            }
        };
        this.jFileName.getDocument().addDocumentListener(this.documentListener);
    }

    private void GetheadersFromFile(String CSVFileName) throws IOException {
        File f = new File(CSVFileName);
        if (f.exists()) {
            this.customers = new CsvReader(CSVFileName);
            this.customers.setDelimiter(((String)this.jComboSeparator.getSelectedItem()).charAt(0));
            this.customers.readHeaders();
            if (this.customers.getHeaderCount() < 5) {
                JOptionPane.showMessageDialog(null, "Incorrect header in your source file", "Header Error", 2);
                this.customers.close();
                return;
            }
            this.rowCount = 0;
            this.Headers.clear();
            this.Headers.add("");
            this.jComboName.addItem("");
            this.jComboAccountID.addItem("");
            this.jComboSearchKey.addItem("");
            this.jComboAddress1.addItem("");
            this.jComboAddress2.addItem("");
            this.jComboPostal.addItem("");
            this.jComboCity.addItem("");
            this.jComboRegion.addItem("");
            this.jComboFirstName.addItem("");
            this.jComboLastName.addItem("");
            this.jComboEmail.addItem("");
            this.jComboPhone.addItem("");
            this.jComboPhone2.addItem("");
            for (int i = 0; i < this.customers.getHeaderCount(); ++i) {
                this.jComboName.addItem(this.customers.getHeader(i));
                this.jComboAccountID.addItem(this.customers.getHeader(i));
                this.jComboSearchKey.addItem(this.customers.getHeader(i));
                this.jComboAddress1.addItem(this.customers.getHeader(i));
                this.jComboAddress2.addItem(this.customers.getHeader(i));
                this.jComboPostal.addItem(this.customers.getHeader(i));
                this.jComboCity.addItem(this.customers.getHeader(i));
                this.jComboRegion.addItem(this.customers.getHeader(i));
                this.jComboFirstName.addItem(this.customers.getHeader(i));
                this.jComboLastName.addItem(this.customers.getHeader(i));
                this.jComboEmail.addItem(this.customers.getHeader(i));
                this.jComboPhone.addItem(this.customers.getHeader(i));
                this.jComboPhone2.addItem(this.customers.getHeader(i));
                this.Headers.add(this.customers.getHeader(i));
            }
            this.enableCheckBoxes();
            while (this.customers.readRecord()) {
                ++this.rowCount;
            }
            this.jtxtRecords.setText(Long.toString(this.rowCount));
            this.customers.close();
        } else {
            JOptionPane.showMessageDialog(null, "Unable to locate " + CSVFileName, "File not found", 2);
        }
    }

    private void enableCheckBoxes() {
        this.jbtnRead.setEnabled(false);
        this.jbtnImport.setEnabled(false);
        this.jbtnReset.setEnabled(true);
        this.jComboAccountID.setEnabled(true);
        this.jComboName.setEnabled(true);
        this.jComboSearchKey.setEnabled(true);
        this.jComboAddress1.setEnabled(true);
        this.jComboAddress2.setEnabled(true);
        this.jComboPostal.setEnabled(true);
        this.jComboCity.setEnabled(true);
        this.jComboRegion.setEnabled(true);
        this.jComboFirstName.setEnabled(true);
        this.jComboLastName.setEnabled(true);
        this.jComboEmail.setEnabled(true);
        this.jComboPhone.setEnabled(true);
        this.jComboPhone2.setEnabled(true);
        this.jCheckVisible.setEnabled(true);
    }

    private void setWorker() {
        this.progress = 0;
        this.webPBar.setStringPainted(true);
        SwingWorker<Integer, Integer> pbWorker = new SwingWorker<Integer, Integer>(){

            @Override
            protected final Integer doInBackground() throws Exception {
                while (CustomerCSVImport.this.progress >= 0 && CustomerCSVImport.this.progress < 100) {
                    Thread.sleep(50L);
                    this.publish(CustomerCSVImport.this.progress);
                }
                this.publish(100);
                this.done();
                return 100;
            }

            @Override
            protected final void process(List<Integer> chunks) {
                CustomerCSVImport.this.webPBar.setValue(chunks.get(0));
                if (CustomerCSVImport.this.progress > 100) {
                    CustomerCSVImport.this.progress = 100;
                    CustomerCSVImport.this.webPBar.setString("Imported 100%");
                } else {
                    CustomerCSVImport.this.webPBar.setString("Imported " + CustomerCSVImport.this.progress + "%");
                }
            }
        };
        pbWorker.execute();
    }

    private void ImportCsvFile(String CSVFileName) throws IOException, BasicException {
        File f = new File(CSVFileName);
        if (f.exists()) {
            this.webPBar.setString("Starting...");
            this.webPBar.setVisible(true);
            this.jbtnImport.setEnabled(false);
            this.customers = new CsvReader(CSVFileName);
            this.customers.setDelimiter(((String)this.jComboSeparator.getSelectedItem()).charAt(0));
            this.customers.readHeaders();
            this.currentRecord = 0;
            block12: while (this.customers.readRecord()) {
                this.customerSearchKey = this.customers.get((String)this.jComboSearchKey.getSelectedItem());
                this.customerAccount = this.customers.get((String)this.jComboAccountID.getSelectedItem());
                this.customerName = this.customers.get((String)this.jComboName.getSelectedItem());
                this.customerAddress1 = this.customers.get((String)this.jComboAddress1.getSelectedItem());
                this.customerAddress2 = this.customers.get((String)this.jComboAddress2.getSelectedItem());
                this.customerPostal = this.customers.get((String)this.jComboPostal.getSelectedItem());
                this.customerCity = this.customers.get((String)this.jComboCity.getSelectedItem());
                this.customerRegion = this.customers.get((String)this.jComboRegion.getSelectedItem());
                this.customerFirstName = this.customers.get((String)this.jComboFirstName.getSelectedItem());
                this.customerLastName = this.customers.get((String)this.jComboLastName.getSelectedItem());
                this.customerEmail = this.customers.get((String)this.jComboEmail.getSelectedItem());
                this.customerPhone = this.customers.get((String)this.jComboPhone.getSelectedItem());
                this.customerPhone2 = this.customers.get((String)this.jComboPhone2.getSelectedItem());
                ++this.currentRecord;
                this.progress = this.currentRecord;
                if ("".equals(this.customerSearchKey) | "".equals(this.customerName)) {
                    this.createCustomerCSVEntry(this.csvMessage, null, null);
                    continue;
                }
                switch (this.recordType = this.getRecord()) {
                    case "new": {
                        this.createCustomer("new");
                        ++this.newRecords;
                        this.createCustomerCSVEntry("New Customer", null, null);
                        continue block12;
                    }
                    case "name error": 
                    case "searchkey error": 
                    case "Duplicate searchkey found.": 
                    case "Duplicate name found.": 
                    case "Exception": {
                        ++this.invalid;
                        this.createCustomerCSVEntry(this.recordType, null, null);
                        continue block12;
                    }
                }
                this.updateRecord(this.recordType);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Unable to locate " + CSVFileName, "File not found", 2);
        }
        this.jtxtNew.setText(Integer.toString(this.newRecords));
        this.jtxtUpdate.setText(Integer.toString(this.updated));
        this.jtxtInvalid.setText(Integer.toString(this.invalid));
        this.jtxtMissing.setText(Integer.toString(this.missing));
        this.jtxtNoChange.setText(Integer.toString(this.noChange));
        this.jtxtBad.setText(Integer.toString(this.bad));
        JOptionPane.showMessageDialog(null, "Import Complete", "Imported", 2);
        this.progress = 100;
        this.webPBar.setValue(this.progress);
        this.webPBar.setString("Imported" + this.progress);
    }

    private Boolean validateNumber(String testString) {
        try {
            Double res = Double.parseDouble(testString);
            return true;
        }
        catch (NumberFormatException e) {
            return false;
        }
    }

    private void updateRecord(String cID) throws BasicException {
        this.custInfo = this.m_dlSales.getCustomerInfo(cID);
        this.createCustomer("update");
        ++this.noChange;
    }

    @Override
    public String getTitle() {
        return AppLocal.getIntString("Menu.CustomerCSVImport");
    }

    @Override
    public JComponent getComponent() {
        return this;
    }

    @Override
    public void activate() throws BasicException {
        this.jComboSeparator.removeAllItems();
        this.jComboSeparator.addItem(",");
        this.jComboSeparator.addItem(";");
        this.jComboSeparator.addItem("~");
        this.jComboSeparator.addItem("^");
    }

    public void resetFields() {
        this.jComboAccountID.removeAllItems();
        this.jComboAccountID.setEnabled(false);
        this.jComboName.removeAllItems();
        this.jComboName.setEnabled(false);
        this.jComboSearchKey.removeAllItems();
        this.jComboSearchKey.setEnabled(false);
        this.jComboAddress1.removeAllItems();
        this.jComboAddress1.setEnabled(false);
        this.jComboAddress2.removeAllItems();
        this.jComboAddress2.setEnabled(false);
        this.jComboCity.removeAllItems();
        this.jComboCity.setEnabled(false);
        this.jComboRegion.removeAllItems();
        this.jComboRegion.setEnabled(false);
        this.jComboPostal.removeAllItems();
        this.jComboPostal.setEnabled(false);
        this.jComboFirstName.removeAllItems();
        this.jComboFirstName.setEnabled(false);
        this.jComboLastName.removeAllItems();
        this.jComboLastName.setEnabled(false);
        this.jComboEmail.removeAllItems();
        this.jComboEmail.setEnabled(false);
        this.jComboPhone.removeAllItems();
        this.jComboPhone.setEnabled(false);
        this.jComboPhone2.removeAllItems();
        this.jComboPhone2.setEnabled(false);
        this.jCheckVisible.setSelected(false);
        this.jCheckVisible.setEnabled(true);
        this.jbtnImport.setEnabled(false);
        this.jbtnReset.setEnabled(true);
        this.jbtnRead.setEnabled(false);
        this.jFileName.setText(null);
        this.csvFileName = "";
        this.jtxtNew.setText("");
        this.jtxtUpdate.setText("");
        this.jtxtInvalid.setText("");
        this.jtxtMissing.setText("");
        this.jtxtNoChange.setText("");
        this.jtxtRecords.setText("");
        this.jtxtBad.setText("");
        this.progress = 0;
        this.Headers.clear();
    }

    public void checkFieldMapping() {
        if (this.jComboSearchKey.getSelectedItem() != "" & this.jComboName.getSelectedItem() != "") {
            this.jbtnImport.setEnabled(true);
            this.jbtnReset.setEnabled(true);
        } else {
            this.jbtnImport.setEnabled(false);
            this.jbtnReset.setEnabled(false);
        }
    }

    @Override
    public boolean deactivate() {
        this.resetFields();
        return true;
    }

    public void createCustomer(String cType) {
        Object[] mycust = new Object[]{"new".equals(cType) ? UUID.randomUUID().toString() : this.custInfo.getId(), this.customerSearchKey, this.customerAccount, this.customerName, null, null, 0.0, this.customerAddress1, this.customerAddress2, this.customerPostal, this.customerCity, this.customerRegion, null, this.customerFirstName, this.customerLastName, this.customerEmail, this.customerPhone, this.customerPhone2, null, null, this.jCheckVisible.isSelected(), null, 0.0, null, false, 0.0};
        try {
            if ("new".equals(cType)) {
                this.spr.insertData(mycust);
                this.webPBar.setString("Adding record " + this.progress);
            } else {
                this.spr.updateData(mycust);
                this.webPBar.setString("Updating record " + this.progress);
            }
        }
        catch (BasicException ex) {
            Logger.getLogger(CustomerCSVImport.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void createCustomerCSVEntry(String csvError, String searchKey, String Name2) {
        Object[] mycust = new Object[]{UUID.randomUUID().toString(), Integer.toString(this.currentRecord), csvError, this.customerSearchKey, this.customerName};
        try {
            this.m_dlSystem.execCustomerAddCSVEntry(mycust);
        }
        catch (BasicException ex) {
            Logger.getLogger(CustomerCSVImport.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String getRecord() {
        Object[] mycust = new Object[]{this.customerSearchKey, this.customerName};
        try {
            return this.m_dlSystem.getCustomerRecordType(mycust);
        }
        catch (BasicException ex) {
            Logger.getLogger(CustomerCSVImport.class.getName()).log(Level.SEVERE, null, ex);
            return "Exception";
        }
    }

    private void initComponents() {
        this.jFileChooserPanel = new JPanel();
        this.jLabel1 = new JLabel();
        this.jFileName = new JTextField();
        this.jbtnFileChoose = new JButton();
        this.jLabel18 = new JLabel();
        this.jbtnRead = new JButton();
        this.jPanel2 = new JPanel();
        this.jlblRecords = new JLabel();
        this.jlblNew = new JLabel();
        this.jlblInvalid = new JLabel();
        this.jlblUpdates = new JLabel();
        this.jlblMissing = new JLabel();
        this.jlblBad = new JLabel();
        this.jlblNotChanged = new JLabel();
        this.jtxtRecords = new JTextField();
        this.jtxtNew = new JTextField();
        this.jtxtInvalid = new JTextField();
        this.jtxtUpdate = new JTextField();
        this.jtxtMissing = new JTextField();
        this.jtxtBad = new JTextField();
        this.jtxtNoChange = new JTextField();
        this.jComboSeparator = new JComboBox();
        this.webPBar = new JProgressBar();
        this.jScrollPane1 = new JScrollPane();
        this.jPanel1 = new JPanel();
        this.jComboSearchKey = new JComboBox();
        this.jComboName = new JComboBox();
        this.jComboAccountID = new JComboBox();
        this.jComboAddress1 = new JComboBox();
        this.jComboAddress2 = new JComboBox();
        this.jComboCity = new JComboBox();
        this.jComboRegion = new JComboBox();
        this.jComboPostal = new JComboBox();
        this.jComboFirstName = new JComboBox();
        this.jComboLastName = new JComboBox();
        this.jComboEmail = new JComboBox();
        this.jComboPhone = new JComboBox();
        this.jComboPhone2 = new JComboBox();
        this.jCheckVisible = new JCheckBox();
        this.jbtnReset = new JButton();
        this.jbtnImport = new JButton();
        this.jLabel3 = new JLabel();
        this.jLabel4 = new JLabel();
        this.jLabel5 = new JLabel();
        this.jLabel10 = new JLabel();
        this.jLabel11 = new JLabel();
        this.jLabel7 = new JLabel();
        this.jLabel8 = new JLabel();
        this.jLabel20 = new JLabel();
        this.jLabel6 = new JLabel();
        this.jLabel21 = new JLabel();
        this.jLabel22 = new JLabel();
        this.jLabel23 = new JLabel();
        this.jLabel9 = new JLabel();
        this.jLabel12 = new JLabel();
        this.setFont(new Font("Arial", 0, 14));
        this.setOpaque(false);
        this.setPreferredSize(new Dimension(750, 500));
        this.jLabel1.setFont(new Font("Arial", 0, 14));
        ResourceBundle bundle = ResourceBundle.getBundle("pos_messages");
        this.jLabel1.setText(bundle.getString("label.csvfile"));
        this.jLabel1.setPreferredSize(new Dimension(100, 30));
        this.jFileName.setFont(new Font("Arial", 0, 14));
        this.jFileName.setPreferredSize(new Dimension(400, 30));
        this.jFileName.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                CustomerCSVImport.this.jFileNameActionPerformed(evt);
            }
        });
        this.jbtnFileChoose.setIcon(new ImageIcon(this.getClass().getResource("/com/openbravo/images/fileopen.png")));
        this.jbtnFileChoose.setMaximumSize(new Dimension(64, 32));
        this.jbtnFileChoose.setMinimumSize(new Dimension(64, 32));
        this.jbtnFileChoose.setPreferredSize(new Dimension(80, 45));
        this.jbtnFileChoose.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                CustomerCSVImport.this.jbtnFileChooseActionPerformed(evt);
            }
        });
        GroupLayout jFileChooserPanelLayout = new GroupLayout(this.jFileChooserPanel);
        this.jFileChooserPanel.setLayout(jFileChooserPanelLayout);
        jFileChooserPanelLayout.setHorizontalGroup(jFileChooserPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jFileChooserPanelLayout.createSequentialGroup().addComponent(this.jLabel1, -1, -1, Short.MAX_VALUE).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(this.jFileName, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jbtnFileChoose, -2, -1, -2).addGap(120, 120, 120)));
        jFileChooserPanelLayout.setVerticalGroup(jFileChooserPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jFileChooserPanelLayout.createSequentialGroup().addGroup(jFileChooserPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel1, -2, -1, -2).addComponent(this.jFileName, -2, -1, -2)).addContainerGap()).addComponent(this.jbtnFileChoose, -1, -1, Short.MAX_VALUE));
        this.jLabel18.setFont(new Font("Arial", 0, 14));
        this.jLabel18.setText(bundle.getString("label.csvdelimit"));
        this.jLabel18.setPreferredSize(new Dimension(100, 30));
        this.jbtnRead.setFont(new Font("Arial", 0, 12));
        this.jbtnRead.setText(bundle.getString("label.csvread"));
        this.jbtnRead.setEnabled(false);
        this.jbtnRead.setPreferredSize(new Dimension(110, 45));
        this.jbtnRead.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                CustomerCSVImport.this.jbtnReadActionPerformed(evt);
            }
        });
        this.jPanel2.setBorder(BorderFactory.createTitledBorder(new LineBorder(new Color(153, 153, 153), 1, true), bundle.getString("title.CSVImport"), 0, 0, new Font("Arial", 1, 14), new Color(102, 102, 102)));
        this.jlblRecords.setFont(new Font("Arial", 0, 14));
        this.jlblRecords.setText(bundle.getString("label.csvrecordsfound"));
        this.jlblRecords.setPreferredSize(new Dimension(150, 30));
        this.jlblNew.setFont(new Font("Arial", 0, 14));
        this.jlblNew.setText(bundle.getString("label.csvnewcustomers"));
        this.jlblNew.setMaximumSize(new Dimension(77, 14));
        this.jlblNew.setMinimumSize(new Dimension(77, 14));
        this.jlblNew.setPreferredSize(new Dimension(150, 30));
        this.jlblInvalid.setFont(new Font("Arial", 0, 14));
        this.jlblInvalid.setText(bundle.getString("label.invalidcustomers"));
        this.jlblInvalid.setPreferredSize(new Dimension(150, 30));
        this.jlblUpdates.setFont(new Font("Arial", 0, 14));
        this.jlblUpdates.setText(bundle.getString("label.customerupdated"));
        this.jlblUpdates.setPreferredSize(new Dimension(150, 30));
        this.jlblMissing.setFont(new Font("Arial", 0, 14));
        this.jlblMissing.setText(bundle.getString("label.csvmissing"));
        this.jlblMissing.setPreferredSize(new Dimension(150, 30));
        this.jlblBad.setFont(new Font("Arial", 0, 14));
        this.jlblBad.setText(bundle.getString("label.csvbad"));
        this.jlblBad.setPreferredSize(new Dimension(150, 30));
        this.jlblNotChanged.setFont(new Font("Arial", 0, 14));
        this.jlblNotChanged.setText(bundle.getString("label.cvsnotchanged"));
        this.jlblNotChanged.setPreferredSize(new Dimension(150, 30));
        this.jtxtRecords.setFont(new Font("Arial", 0, 14));
        this.jtxtRecords.setForeground(new Color(102, 102, 102));
        this.jtxtRecords.setHorizontalAlignment(4);
        this.jtxtRecords.setBorder(null);
        this.jtxtRecords.setDisabledTextColor(new Color(0, 0, 0));
        this.jtxtRecords.setEnabled(false);
        this.jtxtRecords.setPreferredSize(new Dimension(100, 30));
        this.jtxtNew.setFont(new Font("Arial", 0, 14));
        this.jtxtNew.setForeground(new Color(102, 102, 102));
        this.jtxtNew.setHorizontalAlignment(4);
        this.jtxtNew.setBorder(null);
        this.jtxtNew.setDisabledTextColor(new Color(0, 0, 0));
        this.jtxtNew.setEnabled(false);
        this.jtxtNew.setPreferredSize(new Dimension(100, 30));
        this.jtxtInvalid.setFont(new Font("Arial", 0, 14));
        this.jtxtInvalid.setForeground(new Color(102, 102, 102));
        this.jtxtInvalid.setHorizontalAlignment(4);
        this.jtxtInvalid.setBorder(null);
        this.jtxtInvalid.setDisabledTextColor(new Color(0, 0, 0));
        this.jtxtInvalid.setEnabled(false);
        this.jtxtInvalid.setPreferredSize(new Dimension(100, 30));
        this.jtxtUpdate.setFont(new Font("Arial", 0, 14));
        this.jtxtUpdate.setForeground(new Color(102, 102, 102));
        this.jtxtUpdate.setHorizontalAlignment(4);
        this.jtxtUpdate.setBorder(null);
        this.jtxtUpdate.setDisabledTextColor(new Color(0, 0, 0));
        this.jtxtUpdate.setEnabled(false);
        this.jtxtUpdate.setPreferredSize(new Dimension(100, 30));
        this.jtxtMissing.setFont(new Font("Arial", 0, 14));
        this.jtxtMissing.setForeground(new Color(102, 102, 102));
        this.jtxtMissing.setHorizontalAlignment(4);
        this.jtxtMissing.setBorder(null);
        this.jtxtMissing.setDisabledTextColor(new Color(0, 0, 0));
        this.jtxtMissing.setEnabled(false);
        this.jtxtMissing.setPreferredSize(new Dimension(100, 30));
        this.jtxtBad.setFont(new Font("Arial", 0, 14));
        this.jtxtBad.setForeground(new Color(255, 0, 204));
        this.jtxtBad.setHorizontalAlignment(4);
        this.jtxtBad.setBorder(null);
        this.jtxtBad.setDisabledTextColor(new Color(0, 0, 0));
        this.jtxtBad.setEnabled(false);
        this.jtxtBad.setPreferredSize(new Dimension(100, 30));
        this.jtxtNoChange.setFont(new Font("Arial", 0, 14));
        this.jtxtNoChange.setForeground(new Color(102, 102, 102));
        this.jtxtNoChange.setHorizontalAlignment(4);
        this.jtxtNoChange.setBorder(null);
        this.jtxtNoChange.setDisabledTextColor(new Color(0, 0, 0));
        this.jtxtNoChange.setEnabled(false);
        this.jtxtNoChange.setPreferredSize(new Dimension(100, 30));
        GroupLayout jPanel2Layout = new GroupLayout(this.jPanel2);
        this.jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel2Layout.createSequentialGroup().addContainerGap().addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel2Layout.createSequentialGroup().addComponent(this.jlblRecords, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jtxtRecords, -2, -1, -2)).addGroup(jPanel2Layout.createSequentialGroup().addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.TRAILING, false).addComponent(this.jlblNotChanged, GroupLayout.Alignment.LEADING, -1, -1, -2).addComponent(this.jlblInvalid, GroupLayout.Alignment.LEADING, -1, -1, -2).addComponent(this.jlblUpdates, GroupLayout.Alignment.LEADING, -1, -1, -2).addComponent(this.jlblMissing, GroupLayout.Alignment.LEADING, -1, -1, -2).addComponent(this.jlblBad, GroupLayout.Alignment.LEADING, -1, -1, -2).addComponent(this.jlblNew, -1, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jtxtNew, -2, -1, -2).addComponent(this.jtxtInvalid, -2, -1, -2).addComponent(this.jtxtUpdate, -2, -1, -2).addComponent(this.jtxtMissing, -2, -1, -2).addComponent(this.jtxtBad, -2, -1, -2).addComponent(this.jtxtNoChange, -2, -1, -2)))).addContainerGap()));
        jPanel2Layout.setVerticalGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel2Layout.createSequentialGroup().addGap(0, 0, 0).addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jlblRecords, -2, -1, -2).addComponent(this.jtxtRecords, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jlblNew, -2, -1, -2).addComponent(this.jtxtNew, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jlblInvalid, -2, -1, -2).addComponent(this.jtxtInvalid, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jlblUpdates, -2, -1, -2).addComponent(this.jtxtUpdate, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jlblMissing, -2, -1, -2).addComponent(this.jtxtMissing, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jlblBad, -2, -1, -2).addComponent(this.jtxtBad, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jlblNotChanged, -2, -1, -2).addComponent(this.jtxtNoChange, -2, -1, -2)).addContainerGap()));
        this.jComboSeparator.setFont(new Font("Arial", 0, 12));
        this.jComboSeparator.setPreferredSize(new Dimension(50, 30));
        this.webPBar.setFont(new Font("Arial", 0, 13));
        this.webPBar.setPreferredSize(new Dimension(240, 30));
        this.jScrollPane1.setVerticalScrollBarPolicy(22);
        this.jScrollPane1.setAutoscrolls(true);
        this.jPanel1.setFont(new Font("Arial", 0, 14));
        this.jPanel1.setPreferredSize(new Dimension(430, 650));
        this.jComboSearchKey.setFont(new Font("Arial", 0, 14));
        this.jComboSearchKey.setEnabled(false);
        this.jComboSearchKey.setMinimumSize(new Dimension(32, 25));
        this.jComboSearchKey.setPreferredSize(new Dimension(300, 30));
        this.jComboSearchKey.addItemListener(new ItemListener(){

            @Override
            public void itemStateChanged(ItemEvent evt) {
                CustomerCSVImport.this.jComboSearchKeyItemStateChanged(evt);
            }
        });
        this.jComboSearchKey.addFocusListener(new FocusAdapter(){

            @Override
            public void focusGained(FocusEvent evt) {
                CustomerCSVImport.this.jComboSearchKeyFocusGained(evt);
            }
        });
        this.jComboName.setFont(new Font("Arial", 0, 14));
        this.jComboName.setEnabled(false);
        this.jComboName.setMinimumSize(new Dimension(32, 25));
        this.jComboName.setPreferredSize(new Dimension(300, 30));
        this.jComboName.addItemListener(new ItemListener(){

            @Override
            public void itemStateChanged(ItemEvent evt) {
                CustomerCSVImport.this.jComboNameItemStateChanged(evt);
            }
        });
        this.jComboName.addFocusListener(new FocusAdapter(){

            @Override
            public void focusGained(FocusEvent evt) {
                CustomerCSVImport.this.jComboNameFocusGained(evt);
            }
        });
        this.jComboAccountID.setFont(new Font("Arial", 0, 14));
        this.jComboAccountID.setEnabled(false);
        this.jComboAccountID.setMinimumSize(new Dimension(32, 25));
        this.jComboAccountID.setPreferredSize(new Dimension(300, 30));
        this.jComboAccountID.addItemListener(new ItemListener(){

            @Override
            public void itemStateChanged(ItemEvent evt) {
                CustomerCSVImport.this.jComboAccountIDItemStateChanged(evt);
            }
        });
        this.jComboAccountID.addFocusListener(new FocusAdapter(){

            @Override
            public void focusGained(FocusEvent evt) {
                CustomerCSVImport.this.jComboAccountIDFocusGained(evt);
            }
        });
        this.jComboAddress1.setFont(new Font("Arial", 0, 14));
        this.jComboAddress1.setModel(new DefaultComboBoxModel<String>(new String[]{""}));
        this.jComboAddress1.setSelectedIndex(-1);
        this.jComboAddress1.setEnabled(false);
        this.jComboAddress1.setMinimumSize(new Dimension(32, 25));
        this.jComboAddress1.setPreferredSize(new Dimension(300, 30));
        this.jComboAddress1.addItemListener(new ItemListener(){

            @Override
            public void itemStateChanged(ItemEvent evt) {
                CustomerCSVImport.this.jComboAddress1ItemStateChanged(evt);
            }
        });
        this.jComboAddress1.addFocusListener(new FocusAdapter(){

            @Override
            public void focusGained(FocusEvent evt) {
                CustomerCSVImport.this.jComboAddress1FocusGained(evt);
            }
        });
        this.jComboAddress2.setFont(new Font("Arial", 0, 14));
        this.jComboAddress2.setEnabled(false);
        this.jComboAddress2.setMinimumSize(new Dimension(32, 25));
        this.jComboAddress2.setPreferredSize(new Dimension(300, 30));
        this.jComboAddress2.addItemListener(new ItemListener(){

            @Override
            public void itemStateChanged(ItemEvent evt) {
                CustomerCSVImport.this.jComboAddress2ItemStateChanged(evt);
            }
        });
        this.jComboAddress2.addFocusListener(new FocusAdapter(){

            @Override
            public void focusGained(FocusEvent evt) {
                CustomerCSVImport.this.jComboAddress2FocusGained(evt);
            }
        });
        this.jComboCity.setFont(new Font("Arial", 0, 14));
        this.jComboCity.setEnabled(false);
        this.jComboCity.setPreferredSize(new Dimension(300, 30));
        this.jComboCity.addItemListener(new ItemListener(){

            @Override
            public void itemStateChanged(ItemEvent evt) {
                CustomerCSVImport.this.jComboCityItemStateChanged(evt);
            }
        });
        this.jComboCity.addFocusListener(new FocusAdapter(){

            @Override
            public void focusGained(FocusEvent evt) {
                CustomerCSVImport.this.jComboCityFocusGained(evt);
            }
        });
        this.jComboRegion.setFont(new Font("Arial", 0, 14));
        this.jComboRegion.setEnabled(false);
        this.jComboRegion.setMinimumSize(new Dimension(32, 25));
        this.jComboRegion.setName("");
        this.jComboRegion.setPreferredSize(new Dimension(300, 30));
        this.jComboRegion.addItemListener(new ItemListener(){

            @Override
            public void itemStateChanged(ItemEvent evt) {
                CustomerCSVImport.this.jComboRegionItemStateChanged(evt);
            }
        });
        this.jComboRegion.addFocusListener(new FocusAdapter(){

            @Override
            public void focusGained(FocusEvent evt) {
                CustomerCSVImport.this.jComboRegionFocusGained(evt);
            }
        });
        this.jComboPostal.setFont(new Font("Arial", 0, 14));
        this.jComboPostal.setEnabled(false);
        this.jComboPostal.setMinimumSize(new Dimension(32, 25));
        this.jComboPostal.setName("");
        this.jComboPostal.setPreferredSize(new Dimension(300, 30));
        this.jComboPostal.addItemListener(new ItemListener(){

            @Override
            public void itemStateChanged(ItemEvent evt) {
                CustomerCSVImport.this.jComboPostalItemStateChanged(evt);
            }
        });
        this.jComboPostal.addFocusListener(new FocusAdapter(){

            @Override
            public void focusGained(FocusEvent evt) {
                CustomerCSVImport.this.jComboPostalFocusGained(evt);
            }
        });
        this.jComboFirstName.setFont(new Font("Arial", 0, 14));
        this.jComboFirstName.setEnabled(false);
        this.jComboFirstName.setMinimumSize(new Dimension(32, 25));
        this.jComboFirstName.setPreferredSize(new Dimension(300, 30));
        this.jComboFirstName.addItemListener(new ItemListener(){

            @Override
            public void itemStateChanged(ItemEvent evt) {
                CustomerCSVImport.this.jComboFirstNameItemStateChanged(evt);
            }
        });
        this.jComboFirstName.addFocusListener(new FocusAdapter(){

            @Override
            public void focusGained(FocusEvent evt) {
                CustomerCSVImport.this.jComboFirstNameFocusGained(evt);
            }
        });
        this.jComboLastName.setFont(new Font("Arial", 0, 14));
        this.jComboLastName.setEnabled(false);
        this.jComboLastName.setMinimumSize(new Dimension(32, 25));
        this.jComboLastName.setPreferredSize(new Dimension(300, 30));
        this.jComboLastName.addItemListener(new ItemListener(){

            @Override
            public void itemStateChanged(ItemEvent evt) {
                CustomerCSVImport.this.jComboLastNameItemStateChanged(evt);
            }
        });
        this.jComboLastName.addFocusListener(new FocusAdapter(){

            @Override
            public void focusGained(FocusEvent evt) {
                CustomerCSVImport.this.jComboLastNameFocusGained(evt);
            }
        });
        this.jComboEmail.setFont(new Font("Arial", 0, 14));
        this.jComboEmail.setEnabled(false);
        this.jComboEmail.setMinimumSize(new Dimension(32, 25));
        this.jComboEmail.setName("");
        this.jComboEmail.setPreferredSize(new Dimension(300, 30));
        this.jComboEmail.addItemListener(new ItemListener(){

            @Override
            public void itemStateChanged(ItemEvent evt) {
                CustomerCSVImport.this.jComboEmailItemStateChanged(evt);
            }
        });
        this.jComboEmail.addFocusListener(new FocusAdapter(){

            @Override
            public void focusGained(FocusEvent evt) {
                CustomerCSVImport.this.jComboEmailFocusGained(evt);
            }
        });
        this.jComboPhone.setFont(new Font("Arial", 0, 14));
        this.jComboPhone.setEnabled(false);
        this.jComboPhone.setMinimumSize(new Dimension(32, 25));
        this.jComboPhone.setPreferredSize(new Dimension(300, 30));
        this.jComboPhone.addItemListener(new ItemListener(){

            @Override
            public void itemStateChanged(ItemEvent evt) {
                CustomerCSVImport.this.jComboPhoneItemStateChanged(evt);
            }
        });
        this.jComboPhone.addFocusListener(new FocusAdapter(){

            @Override
            public void focusGained(FocusEvent evt) {
                CustomerCSVImport.this.jComboPhoneFocusGained(evt);
            }
        });
        this.jComboPhone2.setFont(new Font("Arial", 0, 14));
        this.jComboPhone2.setEnabled(false);
        this.jComboPhone2.setMinimumSize(new Dimension(32, 25));
        this.jComboPhone2.setPreferredSize(new Dimension(300, 30));
        this.jComboPhone2.addItemListener(new ItemListener(){

            @Override
            public void itemStateChanged(ItemEvent evt) {
                CustomerCSVImport.this.jComboPhone2ItemStateChanged(evt);
            }
        });
        this.jComboPhone2.addFocusListener(new FocusAdapter(){

            @Override
            public void focusGained(FocusEvent evt) {
                CustomerCSVImport.this.jComboPhone2FocusGained(evt);
            }
        });
        this.jCheckVisible.setFont(new Font("Arial", 0, 14));
        this.jCheckVisible.setSelected(true);
        this.jCheckVisible.setEnabled(false);
        this.jCheckVisible.setPreferredSize(new Dimension(30, 30));
        this.jbtnReset.setFont(new Font("Arial", 0, 14));
        this.jbtnReset.setText(bundle.getString("button.reset"));
        this.jbtnReset.setPreferredSize(new Dimension(110, 45));
        this.jbtnReset.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                CustomerCSVImport.this.jbtnResetActionPerformed(evt);
            }
        });
        this.jbtnImport.setFont(new Font("Arial", 0, 14));
        this.jbtnImport.setText(bundle.getString("label.csvimpostbtn"));
        this.jbtnImport.setEnabled(false);
        this.jbtnImport.setPreferredSize(new Dimension(110, 45));
        this.jbtnImport.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                CustomerCSVImport.this.jbtnImportActionPerformed(evt);
            }
        });
        this.jLabel3.setFont(new Font("Arial", 0, 14));
        this.jLabel3.setText(bundle.getString("label.taxid"));
        this.jLabel3.setPreferredSize(new Dimension(100, 30));
        this.jLabel4.setFont(new Font("Arial", 0, 14));
        this.jLabel4.setText(bundle.getString("label.searchkeym"));
        this.jLabel4.setPreferredSize(new Dimension(100, 30));
        this.jLabel5.setFont(new Font("Arial", 0, 14));
        this.jLabel5.setText(bundle.getString("label.namem"));
        this.jLabel5.setPreferredSize(new Dimension(100, 30));
        this.jLabel10.setFont(new Font("Arial", 0, 14));
        this.jLabel10.setText(bundle.getString("label.address"));
        this.jLabel10.setPreferredSize(new Dimension(100, 30));
        this.jLabel11.setFont(new Font("Arial", 0, 14));
        this.jLabel11.setText(bundle.getString("label.region"));
        this.jLabel11.setPreferredSize(new Dimension(100, 30));
        this.jLabel7.setFont(new Font("Arial", 0, 14));
        this.jLabel7.setText(bundle.getString("label.city"));
        this.jLabel7.setPreferredSize(new Dimension(100, 30));
        this.jLabel8.setFont(new Font("Arial", 0, 14));
        this.jLabel8.setHorizontalAlignment(2);
        this.jLabel8.setText(bundle.getString("label.visible"));
        this.jLabel8.setPreferredSize(new Dimension(100, 30));
        this.jLabel20.setFont(new Font("Arial", 0, 14));
        this.jLabel20.setText(bundle.getString("label.address2"));
        this.jLabel20.setPreferredSize(new Dimension(100, 30));
        this.jLabel6.setFont(new Font("Arial", 0, 14));
        this.jLabel6.setText(bundle.getString("label.postal"));
        this.jLabel6.setPreferredSize(new Dimension(100, 30));
        this.jLabel21.setFont(new Font("Arial", 0, 14));
        this.jLabel21.setText(bundle.getString("label.email"));
        this.jLabel21.setPreferredSize(new Dimension(100, 30));
        this.jLabel22.setFont(new Font("Arial", 0, 14));
        this.jLabel22.setText(bundle.getString("label.phone"));
        this.jLabel22.setPreferredSize(new Dimension(100, 30));
        this.jLabel23.setFont(new Font("Arial", 0, 14));
        this.jLabel23.setText(bundle.getString("label.phone2"));
        this.jLabel23.setPreferredSize(new Dimension(100, 30));
        this.jLabel9.setFont(new Font("Arial", 0, 14));
        this.jLabel9.setText(bundle.getString("label.firstname"));
        this.jLabel9.setPreferredSize(new Dimension(100, 30));
        this.jLabel12.setFont(new Font("Arial", 0, 14));
        this.jLabel12.setText(bundle.getString("label.lastname"));
        this.jLabel12.setPreferredSize(new Dimension(100, 30));
        GroupLayout jPanel1Layout = new GroupLayout(this.jPanel1);
        this.jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel1Layout.createSequentialGroup().addContainerGap().addComponent(this.jLabel8, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jCheckVisible, -2, -1, -2).addGroup(GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup().addComponent(this.jbtnReset, -2, -1, -2).addGap(12, 12, 12).addComponent(this.jbtnImport, -2, -1, -2).addGap(93, 93, 93)))).addGroup(jPanel1Layout.createSequentialGroup().addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.TRAILING, false).addGroup(jPanel1Layout.createSequentialGroup().addContainerGap().addComponent(this.jLabel7, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, -1, Short.MAX_VALUE).addComponent(this.jComboCity, -2, -1, -2)).addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel1Layout.createSequentialGroup().addComponent(this.jLabel4, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jComboSearchKey, -2, -1, -2)).addGroup(jPanel1Layout.createSequentialGroup().addComponent(this.jLabel3, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jComboAccountID, -2, -1, -2)).addGroup(jPanel1Layout.createSequentialGroup().addComponent(this.jLabel5, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jComboName, -2, -1, -2)).addGroup(jPanel1Layout.createSequentialGroup().addComponent(this.jLabel10, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jComboAddress1, -2, -1, -2)).addGroup(jPanel1Layout.createSequentialGroup().addComponent(this.jLabel20, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jComboAddress2, -2, -1, -2)))).addGroup(jPanel1Layout.createSequentialGroup().addContainerGap().addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel1Layout.createSequentialGroup().addComponent(this.jLabel23, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jComboPhone2, -2, -1, -2)).addGroup(jPanel1Layout.createSequentialGroup().addComponent(this.jLabel22, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jComboPhone, -2, -1, -2)).addGroup(jPanel1Layout.createSequentialGroup().addComponent(this.jLabel21, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jComboEmail, -2, -1, -2)).addGroup(jPanel1Layout.createSequentialGroup().addComponent(this.jLabel12, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jComboLastName, -2, -1, -2)).addGroup(jPanel1Layout.createSequentialGroup().addComponent(this.jLabel9, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jComboFirstName, -2, -1, -2)).addGroup(jPanel1Layout.createSequentialGroup().addComponent(this.jLabel11, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jComboRegion, -2, -1, -2)).addGroup(jPanel1Layout.createSequentialGroup().addComponent(this.jLabel6, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jComboPostal, -2, -1, -2))))).addContainerGap()));
        jPanel1Layout.setVerticalGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel1Layout.createSequentialGroup().addContainerGap().addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel4, -2, -1, -2).addComponent(this.jComboSearchKey, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jComboName, -2, -1, -2).addComponent(this.jLabel5, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jComboAccountID, -2, -1, -2).addComponent(this.jLabel3, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jComboAddress1, -2, -1, -2).addComponent(this.jLabel10, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jComboAddress2, -2, -1, -2).addComponent(this.jLabel20, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jComboCity, -2, -1, -2).addComponent(this.jLabel7, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jComboRegion, -2, -1, -2).addComponent(this.jLabel11, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jComboPostal, -2, -1, -2).addComponent(this.jLabel6, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jComboFirstName, -2, -1, -2).addComponent(this.jLabel9, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jComboLastName, -2, -1, -2).addComponent(this.jLabel12, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jComboEmail, -2, -1, -2).addComponent(this.jLabel21, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jComboPhone, -2, -1, -2).addComponent(this.jLabel22, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jComboPhone2, -2, -1, -2).addComponent(this.jLabel23, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.TRAILING).addComponent(this.jLabel8, -2, -1, -2).addComponent(this.jCheckVisible, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jbtnImport, -2, -1, -2).addComponent(this.jbtnReset, -2, -1, -2)).addContainerGap(74, Short.MAX_VALUE)));
        this.jScrollPane1.setViewportView(this.jPanel1);
        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jFileChooserPanel, -2, -1, -2).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addComponent(this.jLabel18, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jComboSeparator, -2, -1, -2).addGap(138, 138, 138).addComponent(this.jbtnRead, -2, -1, -2)).addComponent(this.jScrollPane1, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false).addComponent(this.jPanel2, -1, -1, Short.MAX_VALUE).addComponent(this.webPBar, -1, -1, Short.MAX_VALUE)))).addContainerGap(11, Short.MAX_VALUE)));
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addComponent(this.jFileChooserPanel, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jComboSeparator, -2, -1, -2).addComponent(this.jLabel18, -2, -1, -2).addComponent(this.jbtnRead, -2, -1, -2)).addComponent(this.webPBar, -2, 32, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addComponent(this.jPanel2, -2, -1, -2).addContainerGap()).addComponent(this.jScrollPane1, -1, 588, Short.MAX_VALUE))));
    }

    private void jbtnReadActionPerformed(ActionEvent evt) {
        try {
            this.GetheadersFromFile(this.jFileName.getText());
            this.webPBar.setString("Source file Header OK");
        }
        catch (IOException ex) {
            Logger.getLogger(CustomerCSVImport.class.getName()).log(Level.SEVERE, null, ex);
            this.webPBar.setString("Source file Header error!");
        }
    }

    private void jFileNameActionPerformed(ActionEvent evt) {
        this.jbtnImport.setEnabled(false);
        this.jbtnRead.setEnabled(true);
    }

    private void jbtnFileChooseActionPerformed(ActionEvent evt) {
        String csv;
        this.resetFields();
        this.setWorker();
        JFileChooser chooser = new JFileChooser(this.last_folder == null ? "C:\\" : this.last_folder);
        FileNameExtensionFilter filter = new FileNameExtensionFilter("csv files", "csv");
        chooser.setFileFilter(filter);
        chooser.showOpenDialog(null);
        File csvFile = chooser.getSelectedFile();
        if (csvFile == null) {
            return;
        }
        File current_folder = chooser.getCurrentDirectory();
        if (this.last_folder == null || !this.last_folder.equals(current_folder.getAbsolutePath())) {
            AppConfig CSVConfig = new AppConfig(this.config_file);
            CSVConfig.load();
            CSVConfig.setProperty("CSV.last_folder", current_folder.getAbsolutePath());
            this.last_folder = current_folder.getAbsolutePath();
            try {
                CSVConfig.save();
            }
            catch (IOException ex) {
                Logger.getLogger(CustomerCSVImport.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (!(csv = csvFile.getName()).trim().equals("")) {
            this.csvFileName = csvFile.getAbsolutePath();
            this.jFileName.setText(this.csvFileName);
        }
    }

    private void jComboPostalFocusGained(FocusEvent evt) {
        this.jComboPostal.removeAllItems();
        this.jComboPostal.addItem("");
        for (int i = 1; i < this.Headers.size(); ++i) {
            if (!(this.Headers.get(i) != this.jComboSearchKey.getSelectedItem() & this.Headers.get(i) != this.jComboAccountID.getSelectedItem() & this.Headers.get(i) != this.jComboName.getSelectedItem() & this.Headers.get(i) != this.jComboAddress1.getSelectedItem() & this.Headers.get(i) != this.jComboAddress2.getSelectedItem() & this.Headers.get(i) != this.jComboCity.getSelectedItem() & this.Headers.get(i) != this.jComboRegion.getSelectedItem() & this.Headers.get(i) != this.jComboFirstName.getSelectedItem() & this.Headers.get(i) != this.jComboLastName.getSelectedItem() & this.Headers.get(i) != this.jComboEmail.getSelectedItem() & this.Headers.get(i) != this.jComboPhone.getSelectedItem() & this.Headers.get(i) != this.jComboPhone2.getSelectedItem())) continue;
            this.jComboPostal.addItem(this.Headers.get(i));
        }
    }

    private void jComboPostalItemStateChanged(ItemEvent evt) {
        this.checkFieldMapping();
    }

    private void jComboLastNameItemStateChanged(ItemEvent evt) {
        this.checkFieldMapping();
    }

    private void jComboFirstNameItemStateChanged(ItemEvent evt) {
        this.checkFieldMapping();
    }

    private void jComboPhone2ItemStateChanged(ItemEvent evt) {
        this.checkFieldMapping();
    }

    private void jComboPhoneItemStateChanged(ItemEvent evt) {
        this.checkFieldMapping();
    }

    private void jComboEmailFocusGained(FocusEvent evt) {
        this.jComboEmail.removeAllItems();
        this.jComboEmail.addItem("");
        for (int i = 1; i < this.Headers.size(); ++i) {
            if (!(this.Headers.get(i) != this.jComboSearchKey.getSelectedItem() & this.Headers.get(i) != this.jComboAccountID.getSelectedItem() & this.Headers.get(i) != this.jComboName.getSelectedItem() & this.Headers.get(i) != this.jComboAddress1.getSelectedItem() & this.Headers.get(i) != this.jComboAddress2.getSelectedItem() & this.Headers.get(i) != this.jComboPostal.getSelectedItem() & this.Headers.get(i) != this.jComboCity.getSelectedItem() & this.Headers.get(i) != this.jComboRegion.getSelectedItem() & this.Headers.get(i) != this.jComboFirstName.getSelectedItem() & this.Headers.get(i) != this.jComboLastName.getSelectedItem() & this.Headers.get(i) != this.jComboPhone.getSelectedItem() & this.Headers.get(i) != this.jComboPhone2.getSelectedItem())) continue;
            this.jComboEmail.addItem(this.Headers.get(i));
        }
    }

    private void jComboEmailItemStateChanged(ItemEvent evt) {
        this.checkFieldMapping();
    }

    private void jbtnResetActionPerformed(ActionEvent evt) {
        this.resetFields();
        this.progress = -1;
        this.webPBar.setString("Waiting...");
    }

    private void jbtnImportActionPerformed(ActionEvent evt) {
        this.jbtnImport.setEnabled(false);
        workProcess work = new workProcess();
        Thread thread2 = new Thread(work);
        thread2.start();
    }

    private void jComboRegionFocusGained(FocusEvent evt) {
        this.jComboRegion.removeAllItems();
        this.jComboRegion.addItem("");
        for (int i = 1; i < this.Headers.size(); ++i) {
            if (!(this.Headers.get(i) != this.jComboSearchKey.getSelectedItem() & this.Headers.get(i) != this.jComboAccountID.getSelectedItem() & this.Headers.get(i) != this.jComboName.getSelectedItem() & this.Headers.get(i) != this.jComboAddress1.getSelectedItem() & this.Headers.get(i) != this.jComboAddress2.getSelectedItem() & this.Headers.get(i) != this.jComboPostal.getSelectedItem() & this.Headers.get(i) != this.jComboCity.getSelectedItem() & this.Headers.get(i) != this.jComboFirstName.getSelectedItem() & this.Headers.get(i) != this.jComboLastName.getSelectedItem() & this.Headers.get(i) != this.jComboEmail.getSelectedItem() & this.Headers.get(i) != this.jComboPhone.getSelectedItem() & this.Headers.get(i) != this.jComboPhone2.getSelectedItem())) continue;
            this.jComboRegion.addItem(this.Headers.get(i));
        }
    }

    private void jComboRegionItemStateChanged(ItemEvent evt) {
        this.checkFieldMapping();
    }

    private void jComboCityFocusGained(FocusEvent evt) {
        this.jComboCity.removeAllItems();
        this.jComboCity.addItem("");
        for (int i = 1; i < this.Headers.size(); ++i) {
            if (!(this.Headers.get(i) != this.jComboSearchKey.getSelectedItem() & this.Headers.get(i) != this.jComboAccountID.getSelectedItem() & this.Headers.get(i) != this.jComboName.getSelectedItem() & this.Headers.get(i) != this.jComboAddress1.getSelectedItem() & this.Headers.get(i) != this.jComboAddress2.getSelectedItem() & this.Headers.get(i) != this.jComboPostal.getSelectedItem() & this.Headers.get(i) != this.jComboRegion.getSelectedItem() & this.Headers.get(i) != this.jComboFirstName.getSelectedItem() & this.Headers.get(i) != this.jComboLastName.getSelectedItem() & this.Headers.get(i) != this.jComboEmail.getSelectedItem() & this.Headers.get(i) != this.jComboPhone.getSelectedItem() & this.Headers.get(i) != this.jComboPhone2.getSelectedItem())) continue;
            this.jComboCity.addItem(this.Headers.get(i));
        }
    }

    private void jComboCityItemStateChanged(ItemEvent evt) {
        this.checkFieldMapping();
    }

    private void jComboAddress2FocusGained(FocusEvent evt) {
        this.jComboAddress2.removeAllItems();
        this.jComboAddress2.addItem("");
        for (int i = 1; i < this.Headers.size(); ++i) {
            if (!(this.Headers.get(i) != this.jComboSearchKey.getSelectedItem() & this.Headers.get(i) != this.jComboAccountID.getSelectedItem() & this.Headers.get(i) != this.jComboName.getSelectedItem() & this.Headers.get(i) != this.jComboAddress1.getSelectedItem() & this.Headers.get(i) != this.jComboPostal.getSelectedItem() & this.Headers.get(i) != this.jComboCity.getSelectedItem() & this.Headers.get(i) != this.jComboRegion.getSelectedItem() & this.Headers.get(i) != this.jComboFirstName.getSelectedItem() & this.Headers.get(i) != this.jComboLastName.getSelectedItem() & this.Headers.get(i) != this.jComboEmail.getSelectedItem() & this.Headers.get(i) != this.jComboPhone.getSelectedItem() & this.Headers.get(i) != this.jComboPhone2.getSelectedItem())) continue;
            this.jComboAddress2.addItem(this.Headers.get(i));
        }
    }

    private void jComboAddress2ItemStateChanged(ItemEvent evt) {
        this.checkFieldMapping();
    }

    private void jComboAddress1FocusGained(FocusEvent evt) {
        this.jComboAddress1.removeAllItems();
        this.jComboAddress1.addItem("");
        for (int i = 1; i < this.Headers.size(); ++i) {
            if (!(this.Headers.get(i) != this.jComboSearchKey.getSelectedItem() & this.Headers.get(i) != this.jComboAccountID.getSelectedItem() & this.Headers.get(i) != this.jComboName.getSelectedItem() & this.Headers.get(i) != this.jComboAddress2.getSelectedItem() & this.Headers.get(i) != this.jComboPostal.getSelectedItem() & this.Headers.get(i) != this.jComboCity.getSelectedItem() & this.Headers.get(i) != this.jComboRegion.getSelectedItem() & this.Headers.get(i) != this.jComboFirstName.getSelectedItem() & this.Headers.get(i) != this.jComboLastName.getSelectedItem() & this.Headers.get(i) != this.jComboEmail.getSelectedItem() & this.Headers.get(i) != this.jComboPhone.getSelectedItem() & this.Headers.get(i) != this.jComboPhone2.getSelectedItem())) continue;
            this.jComboAddress1.addItem(this.Headers.get(i));
        }
    }

    private void jComboAddress1ItemStateChanged(ItemEvent evt) {
        this.checkFieldMapping();
    }

    private void jComboNameFocusGained(FocusEvent evt) {
        this.jComboName.removeAllItems();
        this.jComboName.addItem("");
        for (int i = 1; i < this.Headers.size(); ++i) {
            if (!(this.Headers.get(i) != this.jComboSearchKey.getSelectedItem() & this.Headers.get(i) != this.jComboAccountID.getSelectedItem() & this.Headers.get(i) != this.jComboAddress1.getSelectedItem() & this.Headers.get(i) != this.jComboAddress2.getSelectedItem() & this.Headers.get(i) != this.jComboPostal.getSelectedItem() & this.Headers.get(i) != this.jComboCity.getSelectedItem() & this.Headers.get(i) != this.jComboRegion.getSelectedItem() & this.Headers.get(i) != this.jComboFirstName.getSelectedItem() & this.Headers.get(i) != this.jComboLastName.getSelectedItem() & this.Headers.get(i) != this.jComboEmail.getSelectedItem() & this.Headers.get(i) != this.jComboPhone.getSelectedItem() & this.Headers.get(i) != this.jComboPhone2.getSelectedItem())) continue;
            this.jComboName.addItem(this.Headers.get(i));
        }
    }

    private void jComboNameItemStateChanged(ItemEvent evt) {
        this.checkFieldMapping();
    }

    private void jComboSearchKeyFocusGained(FocusEvent evt) {
        this.jComboName.removeAllItems();
        this.jComboName.addItem("");
        for (int i = 1; i < this.Headers.size(); ++i) {
            if (!(this.Headers.get(i) != this.jComboAccountID.getSelectedItem() & this.Headers.get(i) != this.jComboName.getSelectedItem() & this.Headers.get(i) != this.jComboAddress1.getSelectedItem() & this.Headers.get(i) != this.jComboAddress2.getSelectedItem() & this.Headers.get(i) != this.jComboPostal.getSelectedItem() & this.Headers.get(i) != this.jComboCity.getSelectedItem() & this.Headers.get(i) != this.jComboRegion.getSelectedItem() & this.Headers.get(i) != this.jComboFirstName.getSelectedItem() & this.Headers.get(i) != this.jComboLastName.getSelectedItem() & this.Headers.get(i) != this.jComboEmail.getSelectedItem() & this.Headers.get(i) != this.jComboPhone.getSelectedItem() & this.Headers.get(i) != this.jComboPhone2.getSelectedItem())) continue;
            this.jComboSearchKey.addItem(this.Headers.get(i));
        }
    }

    private void jComboSearchKeyItemStateChanged(ItemEvent evt) {
        this.checkFieldMapping();
    }

    private void jComboAccountIDFocusGained(FocusEvent evt) {
        this.jComboAccountID.removeAllItems();
        this.jComboAccountID.addItem("");
        for (int i = 1; i < this.Headers.size(); ++i) {
            if (!(this.Headers.get(i) != this.jComboSearchKey.getSelectedItem() & this.Headers.get(i) != this.jComboName.getSelectedItem() & this.Headers.get(i) != this.jComboAddress1.getSelectedItem() & this.Headers.get(i) != this.jComboAddress2.getSelectedItem() & this.Headers.get(i) != this.jComboPostal.getSelectedItem() & this.Headers.get(i) != this.jComboCity.getSelectedItem() & this.Headers.get(i) != this.jComboRegion.getSelectedItem() & this.Headers.get(i) != this.jComboFirstName.getSelectedItem() & this.Headers.get(i) != this.jComboLastName.getSelectedItem() & this.Headers.get(i) != this.jComboEmail.getSelectedItem() & this.Headers.get(i) != this.jComboPhone.getSelectedItem() & this.Headers.get(i) != this.jComboPhone2.getSelectedItem())) continue;
            this.jComboAccountID.addItem(this.Headers.get(i));
        }
    }

    private void jComboAccountIDItemStateChanged(ItemEvent evt) {
        this.checkFieldMapping();
    }

    private void jComboFirstNameFocusGained(FocusEvent evt) {
        this.jComboFirstName.removeAllItems();
        this.jComboFirstName.addItem("");
        for (int i = 1; i < this.Headers.size(); ++i) {
            if (!(this.Headers.get(i) != this.jComboSearchKey.getSelectedItem() & this.Headers.get(i) != this.jComboAccountID.getSelectedItem() & this.Headers.get(i) != this.jComboName.getSelectedItem() & this.Headers.get(i) != this.jComboAddress1.getSelectedItem() & this.Headers.get(i) != this.jComboAddress2.getSelectedItem() & this.Headers.get(i) != this.jComboPostal.getSelectedItem() & this.Headers.get(i) != this.jComboCity.getSelectedItem() & this.Headers.get(i) != this.jComboRegion.getSelectedItem() & this.Headers.get(i) != this.jComboLastName.getSelectedItem() & this.Headers.get(i) != this.jComboEmail.getSelectedItem() & this.Headers.get(i) != this.jComboPhone.getSelectedItem() & this.Headers.get(i) != this.jComboPhone2.getSelectedItem())) continue;
            this.jComboFirstName.addItem(this.Headers.get(i));
        }
    }

    private void jComboLastNameFocusGained(FocusEvent evt) {
        this.jComboLastName.removeAllItems();
        this.jComboLastName.addItem("");
        for (int i = 1; i < this.Headers.size(); ++i) {
            if (!(this.Headers.get(i) != this.jComboSearchKey.getSelectedItem() & this.Headers.get(i) != this.jComboAccountID.getSelectedItem() & this.Headers.get(i) != this.jComboName.getSelectedItem() & this.Headers.get(i) != this.jComboAddress1.getSelectedItem() & this.Headers.get(i) != this.jComboAddress2.getSelectedItem() & this.Headers.get(i) != this.jComboPostal.getSelectedItem() & this.Headers.get(i) != this.jComboCity.getSelectedItem() & this.Headers.get(i) != this.jComboRegion.getSelectedItem() & this.Headers.get(i) != this.jComboFirstName.getSelectedItem() & this.Headers.get(i) != this.jComboEmail.getSelectedItem() & this.Headers.get(i) != this.jComboPhone.getSelectedItem() & this.Headers.get(i) != this.jComboPhone2.getSelectedItem())) continue;
            this.jComboLastName.addItem(this.Headers.get(i));
        }
    }

    private void jComboPhoneFocusGained(FocusEvent evt) {
        this.jComboPhone.removeAllItems();
        this.jComboPhone.addItem("");
        for (int i = 1; i < this.Headers.size(); ++i) {
            if (!(this.Headers.get(i) != this.jComboSearchKey.getSelectedItem() & this.Headers.get(i) != this.jComboAccountID.getSelectedItem() & this.Headers.get(i) != this.jComboName.getSelectedItem() & this.Headers.get(i) != this.jComboAddress1.getSelectedItem() & this.Headers.get(i) != this.jComboAddress2.getSelectedItem() & this.Headers.get(i) != this.jComboPostal.getSelectedItem() & this.Headers.get(i) != this.jComboCity.getSelectedItem() & this.Headers.get(i) != this.jComboRegion.getSelectedItem() & this.Headers.get(i) != this.jComboFirstName.getSelectedItem() & this.Headers.get(i) != this.jComboLastName.getSelectedItem() & this.Headers.get(i) != this.jComboEmail.getSelectedItem() & this.Headers.get(i) != this.jComboPhone2.getSelectedItem())) continue;
            this.jComboPhone.addItem(this.Headers.get(i));
        }
    }

    private void jComboPhone2FocusGained(FocusEvent evt) {
        this.jComboPhone2.removeAllItems();
        this.jComboPhone2.addItem("");
        for (int i = 1; i < this.Headers.size(); ++i) {
            if (!(this.Headers.get(i) != this.jComboSearchKey.getSelectedItem() & this.Headers.get(i) != this.jComboAccountID.getSelectedItem() & this.Headers.get(i) != this.jComboName.getSelectedItem() & this.Headers.get(i) != this.jComboAddress1.getSelectedItem() & this.Headers.get(i) != this.jComboAddress2.getSelectedItem() & this.Headers.get(i) != this.jComboPostal.getSelectedItem() & this.Headers.get(i) != this.jComboCity.getSelectedItem() & this.Headers.get(i) != this.jComboRegion.getSelectedItem() & this.Headers.get(i) != this.jComboFirstName.getSelectedItem() & this.Headers.get(i) != this.jComboLastName.getSelectedItem() & this.Headers.get(i) != this.jComboEmail.getSelectedItem() & this.Headers.get(i) != this.jComboPhone.getSelectedItem())) continue;
            this.jComboPhone2.addItem(this.Headers.get(i));
        }
    }

    private class workProcess
    implements Runnable {
        private workProcess() {
        }

        @Override
        public void run() {
            try {
                CustomerCSVImport.this.ImportCsvFile(CustomerCSVImport.this.jFileName.getText());
            }
            catch (BasicException | IOException ex) {
                Logger.getLogger(CustomerCSVImport.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}

