/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.scripting;

import com.openbravo.pos.scripting.ScriptException;

public interface ScriptEngine {
    public void put(String var1, Object var2);

    public Object get(String var1);

    public Object eval(String var1) throws ScriptException;
}

