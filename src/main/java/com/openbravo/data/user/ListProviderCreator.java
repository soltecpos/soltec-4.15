/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.data.user;

import com.openbravo.basic.BasicException;
import com.openbravo.data.loader.SentenceList;
import com.openbravo.data.loader.TableDefinition;
import com.openbravo.data.user.EditorCreator;
import com.openbravo.data.user.ListProvider;
import java.util.List;

public class ListProviderCreator<T>
implements ListProvider<T> {
    private SentenceList<T> sent;
    private EditorCreator prov;
    private Object params;

    public ListProviderCreator(SentenceList<T> sent, EditorCreator prov) {
        this.sent = sent;
        this.prov = prov;
        this.params = null;
    }

    public ListProviderCreator(SentenceList<T> sent) {
        this(sent, null);
    }

    public ListProviderCreator(TableDefinition table) {
        this((SentenceList)table.getListSentence(), null);
    }

    @Override
    public List<T> loadData() throws BasicException {
        this.params = this.prov == null ? null : this.prov.createValue();
        return this.refreshData();
    }

    @Override
    public List<T> refreshData() throws BasicException {
        return this.sent.list(this.params);
    }
}

