/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.scripting;

import com.openbravo.pos.scripting.ScriptEngine;
import com.openbravo.pos.scripting.ScriptEngineBeanshell;
import com.openbravo.pos.scripting.ScriptEngineVelocity;
import com.openbravo.pos.scripting.ScriptException;

public class ScriptFactory {
    public static final String VELOCITY = "velocity";
    public static final String BEANSHELL = "beanshell";
    public static final String RHINO = "rhino";

    private ScriptFactory() {
    }

    public static ScriptEngine getScriptEngine(String name) throws ScriptException {
        switch (name) {
            case "velocity": {
                return new ScriptEngineVelocity();
            }
            case "beanshell": {
                return new ScriptEngineBeanshell();
            }
        }
        throw new ScriptException("Script engine not found: " + name);
    }
}

