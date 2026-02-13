/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.data.loader;

import com.openbravo.data.loader.ComparatorCreator;
import com.openbravo.data.loader.Datas;
import java.util.Comparator;

public class ComparatorCreatorBasic
implements ComparatorCreator {
    private String[] m_sHeaders;
    private Datas[] m_aDatas;
    private int[] m_iAvailableIndexes;

    public ComparatorCreatorBasic(String[] sHeaders, Datas[] aDatas, int[] iAvailableIndexes) {
        this.m_sHeaders = sHeaders;
        this.m_aDatas = aDatas;
        this.m_iAvailableIndexes = iAvailableIndexes;
    }

    public ComparatorCreatorBasic(String[] sHeaders, Datas[] aDatas) {
        this.m_sHeaders = sHeaders;
        this.m_aDatas = aDatas;
        this.m_iAvailableIndexes = new int[aDatas.length];
        for (int i = 0; i < aDatas.length; ++i) {
            this.m_iAvailableIndexes[i] = i;
        }
    }

    @Override
    public String[] getHeaders() {
        String[] sTempHeaders = new String[this.m_iAvailableIndexes.length];
        for (int i = 0; i < this.m_iAvailableIndexes.length; ++i) {
            sTempHeaders[i] = this.m_sHeaders[this.m_iAvailableIndexes[i]];
        }
        return sTempHeaders;
    }

    @Override
    public Comparator<?> createComparator(int[] aiOrderBy) {
        return new ComparatorBasic(aiOrderBy);
    }

    public class ComparatorBasic
    implements Comparator<Object> {
        private int[] m_aiOrderBy;

        public ComparatorBasic(int[] aiOrderBy) {
            this.m_aiOrderBy = aiOrderBy;
        }

        @Override
        public int compare(Object o1, Object o2) {
            if (o1 == null) {
                if (o2 == null) {
                    return 0;
                }
                return -1;
            }
            if (o2 == null) {
                return 1;
            }
            Object[] ao1 = (Object[])o1;
            Object[] ao2 = (Object[])o2;
            for (int i = 0; i < this.m_aiOrderBy.length; ++i) {
                int result = ComparatorCreatorBasic.this.m_aDatas[ComparatorCreatorBasic.this.m_iAvailableIndexes[this.m_aiOrderBy[i]]].compare(ao1[ComparatorCreatorBasic.this.m_iAvailableIndexes[this.m_aiOrderBy[i]]], ao2[ComparatorCreatorBasic.this.m_iAvailableIndexes[this.m_aiOrderBy[i]]]);
                if (result == 0) continue;
                return result;
            }
            return 0;
        }
    }
}

