/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.forms;

import com.openbravo.pos.forms.AppView;
import com.openbravo.pos.forms.BeanFactoryApp;
import com.openbravo.pos.forms.BeanFactoryException;

public abstract class BeanFactoryCache
implements BeanFactoryApp {
    private Object bean = null;

    public abstract Object constructBean(AppView var1) throws BeanFactoryException;

    @Override
    public void init(AppView app) throws BeanFactoryException {
        this.bean = this.constructBean(app);
    }

    @Override
    public Object getBean() {
        return this.bean;
    }
}

