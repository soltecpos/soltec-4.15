/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.util;

import com.openbravo.basic.BasicException;
import com.openbravo.data.loader.DataRead;
import com.openbravo.data.loader.IKeyed;
import com.openbravo.data.loader.SerializableRead;
import com.openbravo.data.loader.SerializerRead;

public class ReturnInfo
implements SerializableRead,
IKeyed {
    private static final long serialVersionUID = 8906929819402L;
    private Integer idret;

    public ReturnInfo() {
        this.idret = null;
    }

    @Override
    public Object getKey() {
        return this.idret;
    }

    @Override
    public void readValues(DataRead dr) throws BasicException {
        this.idret = dr.getInt(1);
    }

    public void setId(Integer id) {
        this.idret = id;
    }

    public Integer getId() {
        return this.idret;
    }

    public static SerializerRead<ReturnInfo> getSerializerRead() {
        return new SerializerRead<ReturnInfo>(){

            @Override
            public ReturnInfo readValues(DataRead dr) throws BasicException {
                return new ReturnInfo(dr.getInt(1));
            }
        };
    }

    public ReturnInfo(Integer id) {
        this.idret = id;
    }
}

