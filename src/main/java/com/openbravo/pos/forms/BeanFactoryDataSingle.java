/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.forms;

import com.openbravo.data.loader.Session;
import com.openbravo.pos.forms.AppView;
import com.openbravo.pos.forms.BeanFactoryApp;
import com.openbravo.pos.forms.BeanFactoryException;

public abstract class BeanFactoryDataSingle
implements BeanFactoryApp {
    public abstract void init(Session var1);

    @Override
    public void init(AppView app) throws BeanFactoryException {
        this.init(app.getSession());
    }

    @Override
    public Object getBean() {
        return this;
    }
}

