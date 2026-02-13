/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  bsh.EvalError
 *  bsh.Interpreter
 */
package com.openbravo.pos.scripting;

import bsh.EvalError;
import bsh.Interpreter;
import com.openbravo.pos.scripting.ScriptEngine;
import com.openbravo.pos.scripting.ScriptException;

class ScriptEngineBeanshell
implements ScriptEngine {
    private Interpreter i = new Interpreter();

    @Override
    public void put(String key, Object value) {
        try {
            this.i.set(key, value);
        }
        catch (EvalError evalError) {
            // empty catch block
        }
    }

    @Override
    public Object get(String key) {
        try {
            return this.i.get(key);
        }
        catch (EvalError e) {
            return null;
        }
    }

    @Override
    public Object eval(String src) throws ScriptException {
        try {
            return this.i.eval(src);
        }
        catch (EvalError e) {
            throw new ScriptException(e.getMessage(), e);
        }
    }
}

