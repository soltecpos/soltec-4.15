/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.velocity.VelocityContext
 *  org.apache.velocity.app.VelocityEngine
 *  org.apache.velocity.context.Context
 *  org.apache.velocity.exception.MethodInvocationException
 *  org.apache.velocity.exception.ParseErrorException
 */
package com.openbravo.pos.scripting;

import com.openbravo.pos.forms.AppView;
import com.openbravo.pos.scripting.ScriptEngine;
import com.openbravo.pos.scripting.ScriptException;
import com.unicenta.pos.util.ScriptletUtil;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.context.Context;
import org.apache.velocity.exception.MethodInvocationException;
import org.apache.velocity.exception.ParseErrorException;

class ScriptEngineVelocity
implements ScriptEngine {
    private static VelocityEngine m_ve = null;
    private VelocityContext c = null;
    private AppView m_App;

    public ScriptEngineVelocity() throws ScriptException {
        if (m_ve == null) {
            m_ve = new VelocityEngine();
            m_ve.setProperty("runtime.log.logsystem.class", (Object)"org.apache.velocity.runtime.log.NullLogSystem");
            m_ve.setProperty("ISO-8859-1", (Object)"UTF-8");
            m_ve.setProperty("input.encoding", (Object)"UTF-8");
            try {
                m_ve.init();
            }
            catch (Exception e) {
                throw new ScriptException("Cannot initialize Velocity Engine", e);
            }
        }
        this.c = new VelocityContext();
        this.put("scriptletutil", new ScriptletUtil());
    }

    @Override
    public void put(String key, Object value) {
        this.c.put(key, value);
    }

    @Override
    public Object get(String key) {
        return this.c.get(key);
    }

    @Override
    public Object eval(String src) throws ScriptException {
        if (m_ve == null) {
            throw new ScriptException("Velocity engine not initialized.");
        }
        StringWriter w = new StringWriter();
        try {
            if (m_ve.evaluate((Context)this.c, (Writer)w, "log", (Reader)new StringReader(src))) {
                return ((Object)w).toString();
            }
            throw new ScriptException("Velocity engine unexpected error.");
        }
        catch (ParseErrorException e) {
            throw new ScriptException(e.getMessage(), e);
        }
        catch (MethodInvocationException e) {
            throw new ScriptException(e.getMessage(), e);
        }
        catch (Exception e) {
            throw new ScriptException(e.getMessage(), e);
        }
    }
}

