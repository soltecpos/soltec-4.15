/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.forms;

import com.openbravo.pos.forms.BeanFactory;

public class BeanFactoryObj
implements BeanFactory {
    private Object bean = null;

    public BeanFactoryObj(Object bean) {
        this.bean = bean;
    }

    @Override
    public Object getBean() {
        return this.bean;
    }
}

