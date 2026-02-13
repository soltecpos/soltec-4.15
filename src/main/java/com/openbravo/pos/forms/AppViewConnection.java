/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.forms;

import com.openbravo.basic.BasicException;
import com.openbravo.data.loader.Session;
import com.openbravo.pos.forms.AppLocal;
import com.openbravo.pos.forms.AppProperties;
import com.openbravo.pos.forms.DriverWrapper;
import com.openbravo.pos.util.AltEncrypter;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

public class AppViewConnection {
    private AppViewConnection() {
    }

    public static Session createSession(AppProperties props) throws BasicException {
        try {
            String dbURL = null;
            String sDBUser = null;
            String sDBPassword = null;
            if (AppViewConnection.isJavaWebStart()) {
                Class.forName(props.getProperty("db.driver"), true, Thread.currentThread().getContextClassLoader());
            } else {
                URLClassLoader cloader = new URLClassLoader(new URL[]{new File(props.getProperty("db.driverlib")).toURI().toURL()});
                DriverManager.registerDriver(new DriverWrapper((Driver)Class.forName(props.getProperty("db.driver"), true, cloader).newInstance()));
            }
            if ("true".equals(props.getProperty("db.multi"))) {
                ImageIcon icon = new ImageIcon("../unicentaopos4.2-src/unicentaopos.png");
                Object[] dbs = new Object[]{"0 - " + props.getProperty("db.name"), "1 - " + props.getProperty("db1.name")};
                Object s = JOptionPane.showInputDialog(null, AppLocal.getIntString("message.databasechoose"), "Selection", 2, icon, dbs, props.getProperty("db.name"));
                if (s == null) {
                    System.exit(1);
                }
                if (s.toString().startsWith("1")) {
                    sDBUser = props.getProperty("db1.user");
                    sDBPassword = props.getProperty("db1.password");
                    if (sDBUser != null && sDBPassword != null && sDBPassword.startsWith("crypt:")) {
                        AltEncrypter cypher = new AltEncrypter("cypherkey" + sDBUser);
                        sDBPassword = cypher.decrypt(sDBPassword.substring(6));
                    }
                    dbURL = props.getProperty("db1.URL");
                } else if (s.toString().startsWith("0")) {
                    sDBUser = props.getProperty("db.user");
                    sDBPassword = props.getProperty("db.password");
                    if (sDBUser != null && sDBPassword != null && sDBPassword.startsWith("crypt:")) {
                        AltEncrypter cypher = new AltEncrypter("cypherkey" + sDBUser);
                        sDBPassword = cypher.decrypt(sDBPassword.substring(6));
                    }
                    dbURL = props.getProperty("db.URL");
                }
            } else {
                sDBUser = props.getProperty("db.user");
                sDBPassword = props.getProperty("db.password");
                if (sDBUser != null && sDBPassword != null && sDBPassword.startsWith("crypt:")) {
                    AltEncrypter cypher = new AltEncrypter("cypherkey" + sDBUser);
                    sDBPassword = cypher.decrypt(sDBPassword.substring(6));
                }
                dbURL = props.getProperty("db.URL");
            }
            return new Session(dbURL, sDBUser, sDBPassword);
        }
        catch (ClassNotFoundException | IllegalAccessException | InstantiationException | MalformedURLException e) {
            throw new BasicException(AppLocal.getIntString("message.databasedrivererror"), e);
        }
        catch (SQLException eSQL) {
            throw new BasicException(AppLocal.getIntString("message.databaseconnectionerror"), eSQL);
        }
    }

    private static boolean isJavaWebStart() {
        try {
            Class.forName("javax.jnlp.ServiceManager");
            return true;
        }
        catch (ClassNotFoundException ue) {
            return false;
        }
    }
}

