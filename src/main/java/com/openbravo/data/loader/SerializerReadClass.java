/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.data.loader;

import com.openbravo.basic.BasicException;
import com.openbravo.data.loader.DataRead;
import com.openbravo.data.loader.SerializableRead;
import com.openbravo.data.loader.SerializerRead;
import java.lang.reflect.InvocationTargetException;

public class SerializerReadClass<T>
implements SerializerRead<T> {
    private final Class<T> m_clazz;

    public SerializerReadClass(Class<T> clazz) {
        this.m_clazz = clazz;
    }

    @Override
    public T readValues(DataRead dr) throws BasicException {
        try {
            SerializableRead sr = (SerializableRead)this.m_clazz.getDeclaredConstructor(new Class[0]).newInstance(new Object[0]);
            sr.readValues(dr);
            return (T)sr;
        }
        catch (IllegalAccessException | InstantiationException | NoSuchMethodException | InvocationTargetException eIns) {
            return null;
        }
    }
}

