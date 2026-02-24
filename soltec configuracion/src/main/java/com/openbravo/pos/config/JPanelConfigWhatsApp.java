package com.openbravo.pos.config;

import com.openbravo.data.user.DirtyManager;
import com.openbravo.pos.forms.AppConfig;
import com.openbravo.pos.forms.AppLocal;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class JPanelConfigWhatsApp extends JPanel implements PanelConfig {

    private DirtyManager dirty = new DirtyManager();
    private JLabel jLblPhone;
    private JTextField jTxtPhone;
    private JLabel jLblApiKey;
    private JTextField jTxtApiKey;
    private JLabel jLblLabel;
    private JTextField jTxtLabel;
    private JLabel jLblInfo;

    public JPanelConfigWhatsApp() {
        initComponents();
        
        jTxtPhone.getDocument().addDocumentListener(dirty);
        jTxtApiKey.getDocument().addDocumentListener(dirty);
        jTxtLabel.getDocument().addDocumentListener(dirty);
    }

    private void initComponents() {
        setLayout(null); // Absolute layout for simplicity matching other panels

        jLblPhone = new JLabel("WhatsApp Phone (with country code):");
        jLblPhone.setBounds(20, 20, 250, 25);
        add(jLblPhone);

        jTxtPhone = new JTextField();
        jTxtPhone.setBounds(20, 50, 200, 25);
        add(jTxtPhone);

        jLblApiKey = new JLabel("CallMeBot API Key:");
        jLblApiKey.setBounds(20, 90, 250, 25);
        add(jLblApiKey);

        jTxtApiKey = new JTextField();
        jTxtApiKey.setBounds(20, 120, 200, 25);
        add(jTxtApiKey);

        jLblLabel = new JLabel("Business Label (Sede):");
        jLblLabel.setBounds(20, 160, 250, 25);
        add(jLblLabel);

        jTxtLabel = new JTextField();
        jTxtLabel.setBounds(20, 190, 200, 25);
        add(jTxtLabel);
        
        jLblInfo = new JLabel("<html><body><b>Instructions:</b><br>"
                + "1. Add <b>+34 603 21 25 97</b> to your contacts.<br>"
                + "2. Send message: <b>I allow callmebot to send me messages</b><br>"
                + "3. Enter your phone and the API key received.</body></html>");
        jLblInfo.setBounds(300, 20, 400, 200);
        jLblInfo.setVerticalAlignment(JLabel.TOP);
        add(jLblInfo);
    }

    @Override
    public boolean hasChanged() {
        return dirty.isDirty();
    }

    @Override
    public Component getConfigComponent() {
        return this;
    }

    @Override
    public void loadProperties(AppConfig config) {
        jTxtPhone.setText(config.getProperty("whatsapp.phone"));
        jTxtApiKey.setText(config.getProperty("whatsapp.apikey"));
        jTxtLabel.setText(config.getProperty("whatsapp.label"));
        dirty.setDirty(false);
    }

    @Override
    public void saveProperties(AppConfig config) {
        config.setProperty("whatsapp.phone", jTxtPhone.getText());
        config.setProperty("whatsapp.apikey", jTxtApiKey.getText());
        config.setProperty("whatsapp.label", jTxtLabel.getText());
        dirty.setDirty(false);
    }
}
