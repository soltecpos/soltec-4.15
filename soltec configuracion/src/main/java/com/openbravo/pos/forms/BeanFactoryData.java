/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.forms;

import com.openbravo.pos.forms.AppView;
import com.openbravo.pos.forms.BeanFactoryApp;
import com.openbravo.pos.forms.BeanFactoryException;

public class BeanFactoryData
implements BeanFactoryApp {
    private BeanFactoryApp bf;

    @Override
    public void init(AppView app) throws BeanFactoryException {
        try {
            String sfactoryname = this.getClass().getName();
            if (sfactoryname.endsWith("Create")) {
                sfactoryname = sfactoryname.substring(0, sfactoryname.length() - 6);
            }
            this.bf = (BeanFactoryApp)Class.forName(sfactoryname + app.getSession().DB.getName()).newInstance();
            this.bf.init(app);
        }
        catch (BeanFactoryException | ClassNotFoundException | IllegalAccessException | InstantiationException ex) {
            throw new BeanFactoryException(ex);
        }
    }

    @Override
    public Object getBean() {
        return this.bf.getBean();
    }
}

