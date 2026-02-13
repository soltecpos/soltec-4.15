/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.data.loader;

import com.openbravo.basic.BasicException;
import com.openbravo.data.loader.DataRead;
import com.openbravo.data.loader.Datas;
import com.openbravo.data.loader.SerializerRead;
import java.util.Date;

public class SerializerReadDate
implements SerializerRead<Date> {
    public static final SerializerRead<Date> INSTANCE = new SerializerReadDate();

    private SerializerReadDate() {
    }

    @Override
    public Date readValues(DataRead dr) throws BasicException {
        return (Date)Datas.TIMESTAMP.getValue(dr, 1);
    }
}

