/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.data.gui;

import com.openbravo.data.loader.IKeyed;
import java.util.ArrayList;
import java.util.List;

public class ListKeyed<K extends IKeyed>
extends ArrayList<K> {
    public ListKeyed(List<K> list) {
        this.addAll(list);
    }

    public K get(Object key) {
        for (IKeyed elem : this) {
            if ((key != null || elem.getKey() != null) && (key == null || !key.equals(elem.getKey()))) continue;
            return (K)elem;
        }
        return null;
    }
}

