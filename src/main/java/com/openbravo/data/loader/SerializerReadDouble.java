/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.data.loader;

import com.openbravo.basic.BasicException;
import com.openbravo.data.loader.DataRead;
import com.openbravo.data.loader.Datas;
import com.openbravo.data.loader.SerializerRead;

public class SerializerReadDouble
implements SerializerRead<Double> {
    public static final SerializerRead<Double> INSTANCE = new SerializerReadDouble();

    @Override
    public Double readValues(DataRead dr) throws BasicException {
        return (Double)Datas.DOUBLE.getValue(dr, 1);
    }
}

