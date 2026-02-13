/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.sales;

import com.openbravo.data.loader.LocalRes;
import com.openbravo.pos.forms.AppLocal;
import com.openbravo.pos.forms.AppUser;
import com.openbravo.pos.sales.JPanelTicket;
import com.openbravo.pos.util.ThumbNailBuilder;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class JPanelButtons
extends JPanel {
    private static final Logger logger = Logger.getLogger("com.openbravo.pos.sales.JPanelButtons");
    private static SAXParser m_sp = null;
    private Properties props;
    private Map<String, String> events;
    private ThumbNailBuilder tnbmacro;
    private JPanelTicket panelticket;

    public JPanelButtons(String sConfigKey, JPanelTicket panelticket) {
        this.initComponents();
        this.tnbmacro = new ThumbNailBuilder(42, 36, "com/openbravo/images/run_script.png");
        this.panelticket = panelticket;
        this.props = new Properties();
        this.events = new HashMap<String, String>();
        String sConfigRes = panelticket.getResourceAsXML(sConfigKey);
        if (sConfigRes != null) {
            try {
                if (m_sp == null) {
                    SAXParserFactory spf = SAXParserFactory.newInstance();
                    m_sp = spf.newSAXParser();
                }
                m_sp.parse(new InputSource(new StringReader(sConfigRes)), (DefaultHandler)new ConfigurationHandler());
            }
            catch (ParserConfigurationException ePC) {
                logger.log(Level.WARNING, LocalRes.getIntString("exception.parserconfig"), ePC);
            }
            catch (SAXException eSAX) {
                logger.log(Level.WARNING, LocalRes.getIntString("exception.xmlfile"), eSAX);
            }
            catch (IOException eIO) {
                logger.log(Level.WARNING, LocalRes.getIntString("exception.iofile"), eIO);
            }
        }
    }

    public void setPermissions(AppUser user) {
        for (Component c : this.getComponents()) {
            String sKey = c.getName();
            if (sKey == null || sKey.equals("")) {
                c.setEnabled(true);
                continue;
            }
            c.setEnabled(user.hasPermission(c.getName()));
        }
    }

    public String getProperty(String key) {
        return this.props.getProperty(key);
    }

    public String getProperty(String key, String defaultvalue) {
        return this.props.getProperty(key, defaultvalue);
    }

    public String getEvent(String key) {
        return this.events.get(key);
    }

    private void initComponents() {
        this.setFont(new Font("Arial", 0, 12));
    }

    private class ConfigurationHandler
    extends DefaultHandler {
        private ConfigurationHandler() {
        }

        @Override
        public void startDocument() throws SAXException {
        }

        @Override
        public void endDocument() throws SAXException {
        }

        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
            switch (qName) {
                case "button": {
                    String titlekey = attributes.getValue("titlekey");
                    if (titlekey == null) {
                        titlekey = attributes.getValue("name");
                    }
                    String title = titlekey == null ? attributes.getValue("title") : AppLocal.getIntString(titlekey);
                    JButtonFunc btn = new JButtonFunc(attributes.getValue("key"), attributes.getValue("image"), title);
                    final String template = attributes.getValue("template");
                    if (template == null) {
                        final String code = attributes.getValue("code");
                        btn.addActionListener(new ActionListener(){

                            @Override
                            public void actionPerformed(ActionEvent evt) {
                                JPanelButtons.this.panelticket.evalScriptAndRefresh(code, new JPanelTicket.ScriptArg[0]);
                            }
                        });
                    } else {
                        btn.addActionListener(new ActionListener(){

                            @Override
                            public void actionPerformed(ActionEvent evt) {
                                JPanelButtons.this.panelticket.printTicket(template);
                            }
                        });
                    }
                    JPanelButtons.this.add(btn);
                    break;
                }
                case "event": {
                    JPanelButtons.this.events.put(attributes.getValue("key"), attributes.getValue("code"));
                    break;
                }
                default: {
                    String value = attributes.getValue("value");
                    if (value == null) break;
                    JPanelButtons.this.props.setProperty(qName, attributes.getValue("value"));
                }
            }
        }

        @Override
        public void endElement(String uri, String localName, String qName) throws SAXException {
        }

        @Override
        public void characters(char[] ch, int start, int length) throws SAXException {
        }
    }

    private class JButtonFunc
    extends JButton {
        public JButtonFunc(String sKey, String sImage, String title) {
            this.setName(sKey);
            this.setText(title);
            this.setIcon(new ImageIcon(JPanelButtons.this.tnbmacro.getThumbNail(JPanelButtons.this.panelticket.getResourceAsImage(sImage))));
            this.setFocusPainted(false);
            this.setFocusable(false);
            this.setRequestFocusEnabled(false);
            this.setMargin(new Insets(0, 0, 0, 0));
            this.setFont(new Font("Segoe UI", 1, 12));
            this.setBackground(Color.WHITE);
            this.setPreferredSize(new Dimension(110, 80));
            this.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220), 1));
            this.setHorizontalTextPosition(0);
            this.setVerticalTextPosition(3);
        }
    }
}

