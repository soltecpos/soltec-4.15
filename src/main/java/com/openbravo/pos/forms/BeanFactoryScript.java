/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.forms;

import com.openbravo.pos.forms.AppView;
import com.openbravo.pos.forms.BeanFactory;
import com.openbravo.pos.forms.BeanFactoryApp;
import com.openbravo.pos.forms.BeanFactoryException;
import com.openbravo.pos.scripting.ScriptEngine;
import com.openbravo.pos.scripting.ScriptException;
import com.openbravo.pos.scripting.ScriptFactory;
import com.openbravo.pos.util.StringUtils;
import java.io.IOException;

public class BeanFactoryScript
implements BeanFactoryApp {
    private BeanFactory bean = null;
    private String script;

    public BeanFactoryScript(String script) {
        this.script = script;
    }

    @Override
    public void init(AppView app) throws BeanFactoryException {
        try {
            ScriptEngine eng = ScriptFactory.getScriptEngine("beanshell");
            eng.put("app", app);
            this.bean = (BeanFactory)eng.eval(StringUtils.readResource(this.script));
            if (this.bean == null) {
                this.bean = (BeanFactory)eng.get("bean");
            }
            if (this.bean instanceof BeanFactoryApp) {
                ((BeanFactoryApp)this.bean).init(app);
            }
        }
        catch (ScriptException | IOException e) {
            throw new BeanFactoryException(e);
        }
    }

    @Override
    public Object getBean() {
        return this.bean.getBean();
    }
}

