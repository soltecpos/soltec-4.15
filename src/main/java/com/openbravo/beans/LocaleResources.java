/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.beans;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.text.MessageFormat;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class LocaleResources {
    private List<ResourceBundle> m_resources = new LinkedList<ResourceBundle>();
    private ClassLoader m_localeloader;

    public LocaleResources() {
        File fuserdir = new File(System.getProperty("user.dir"));
        File fresources = new File(fuserdir, "locales");
        try {
            this.m_localeloader = URLClassLoader.newInstance(new URL[]{fresources.toURI().toURL()}, Thread.currentThread().getContextClassLoader());
        }
        catch (MalformedURLException e) {
            this.m_localeloader = Thread.currentThread().getContextClassLoader();
        }
    }

    public ResourceBundle getBundle(String bundlename) {
        return ResourceBundle.getBundle(bundlename, Locale.getDefault(), this.m_localeloader);
    }

    public void addBundleName(String bundlename) {
        this.m_resources.add(ResourceBundle.getBundle(bundlename));
    }

    public String getString(String sKey) {
        if (sKey == null) {
            return null;
        }
        for (ResourceBundle r : this.m_resources) {
            try {
                return r.getString(sKey);
            }
            catch (MissingResourceException missingResourceException) {
            }
        }
        return "** " + sKey + " **";
    }

    public String getString(String sKey, Object ... sValues) {
        if (sKey == null) {
            return null;
        }
        for (ResourceBundle r : this.m_resources) {
            try {
                return MessageFormat.format(r.getString(sKey), sValues);
            }
            catch (MissingResourceException missingResourceException) {
            }
        }
        StringBuilder sreturn = new StringBuilder();
        sreturn.append("** ");
        sreturn.append(sKey);
        for (Object value : sValues) {
            sreturn.append(" < ");
            sreturn.append(value.toString());
        }
        sreturn.append("** ");
        return sreturn.toString();
    }
}

