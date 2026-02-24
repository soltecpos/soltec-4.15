/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.data.loader;

import com.openbravo.basic.BasicException;
import com.openbravo.data.loader.BaseSentence;
import com.openbravo.data.loader.DataResultSet;
import com.openbravo.data.loader.LocalRes;
import com.openbravo.data.loader.SentenceEnum;

public class BasicSentenceEnum
implements SentenceEnum {
    BaseSentence<Object, Object> sent;
    DataResultSet SRS;

    public BasicSentenceEnum(BaseSentence<Object, Object> sent) {
        this.sent = sent;
        this.SRS = null;
    }

    @Override
    public void load() throws BasicException {
        this.load(null);
    }

    @Override
    public void load(Object params) throws BasicException {
        this.SRS = this.sent.openExec(params);
    }

    @Override
    public Object getCurrent() throws BasicException {
        if (this.SRS == null) {
            throw new BasicException(LocalRes.getIntString("exception.nodataset"));
        }
        return this.SRS.getCurrent();
    }

    @Override
    public boolean next() throws BasicException {
        if (this.SRS == null) {
            throw new BasicException(LocalRes.getIntString("exception.nodataset"));
        }
        if (this.SRS.next()) {
            return true;
        }
        this.SRS.close();
        this.SRS = null;
        this.sent.closeExec();
        return false;
    }
}

