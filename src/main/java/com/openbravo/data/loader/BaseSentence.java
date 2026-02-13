/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.data.loader;

import com.openbravo.basic.BasicException;
import com.openbravo.data.loader.DataResultSet;
import com.openbravo.data.loader.LocalRes;
import com.openbravo.data.loader.SentenceExec;
import com.openbravo.data.loader.SentenceFind;
import com.openbravo.data.loader.SentenceList;
import java.util.ArrayList;
import java.util.List;

public abstract class BaseSentence<P, R>
implements SentenceList<R>,
SentenceFind<R>,
SentenceExec<P> {
    public abstract DataResultSet openExec(P var1) throws BasicException;

    public abstract DataResultSet moreResults() throws BasicException;

    public abstract void closeExec() throws BasicException;

    @Override
    public final int exec() throws BasicException {
        return this.exec((P)null);
    }

    @Override
    public final int exec(P params) throws BasicException {
        DataResultSet SRS = this.openExec(params);
        if (SRS == null) {
            throw new BasicException(LocalRes.getIntString("exception.noupdatecount"));
        }
        int iResult = SRS.updateCount();
        SRS.close();
        this.closeExec();
        return iResult;
    }

    @Override
    public final List<R> list() throws BasicException {
        return this.list((Object)null);
    }

    @Override
    public final List<R> list(Object ... params) throws BasicException {
        return this.list((Object)params);
    }

    @Override
    public final List<R> list(Object params) throws BasicException {
        DataResultSet SRS = this.openExec((P)params);
        List<R> aSO = (List<R>)(List)this.fetchAll(SRS);
        SRS.close();
        this.closeExec();
        return aSO;
    }

    @Override
    public final List<R> listPage(int offset, int length) throws BasicException {
        return this.listPage(null, offset, length);
    }

    @Override
    public final List<R> listPage(Object params, int offset, int length) throws BasicException {
        DataResultSet SRS = this.openExec((P)params);
        List<R> aSO = (List<R>)(List)this.fetchPage(SRS, offset, length);
        SRS.close();
        this.closeExec();
        return aSO;
    }

    @Override
    public final R find() throws BasicException {
        return this.find((Object)null);
    }

    @Override
    public final R find(Object ... params) throws BasicException {
        return this.find((Object)params);
    }

    @Override
    public final R find(Object params) throws BasicException {
        DataResultSet SRS = this.openExec((P)params);
        Object obj = this.fetchOne(SRS);
        SRS.close();
        this.closeExec();
        return (R)obj;
    }

    public final List<Object> fetchAll(DataResultSet SRS) throws BasicException {
        if (SRS == null) {
            throw new BasicException(LocalRes.getIntString("exception.nodataset"));
        }
        ArrayList<Object> aSO = new ArrayList<Object>();
        while (SRS.next()) {
            aSO.add(SRS.getCurrent());
        }
        return aSO;
    }

    public final List<Object> fetchPage(DataResultSet SRS, int offset, int length) throws BasicException {
        if (SRS == null) {
            throw new BasicException(LocalRes.getIntString("exception.nodataset"));
        }
        if (offset < 0 || length < 0) {
            throw new BasicException(LocalRes.getIntString("exception.nonegativelimits"));
        }
        while (offset > 0 && SRS.next()) {
            --offset;
        }
        ArrayList<Object> aSO = new ArrayList<Object>();
        if (offset == 0) {
            while (length > 0 && SRS.next()) {
                --length;
                aSO.add(SRS.getCurrent());
            }
        }
        return aSO;
    }

    public final Object fetchOne(DataResultSet SRS) throws BasicException {
        if (SRS == null) {
            throw new BasicException(LocalRes.getIntString("exception.nodataset"));
        }
        if (SRS.next()) {
            return SRS.getCurrent();
        }
        return null;
    }
}

