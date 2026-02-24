/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.panels;

import com.openbravo.pos.panels.EnumerationIter;
import com.openbravo.pos.panels.SQLColumn;
import com.openbravo.pos.panels.SQLDatabase;
import java.util.ArrayList;
import java.util.Enumeration;
import javax.swing.tree.TreeNode;

public class SQLTable
implements TreeNode {
    private SQLDatabase m_db;
    private String m_sName;
    private ArrayList m_aColumns;

    public SQLTable(SQLDatabase db, String name) {
        this.m_db = db;
        this.m_sName = name;
        this.m_aColumns = new ArrayList();
    }

    public String getName() {
        return this.m_sName;
    }

    public void addColumn(String name) {
        SQLColumn c = new SQLColumn(this, name);
        this.m_aColumns.add(c);
    }

    public String toString() {
        return this.m_sName;
    }

    @Override
    public Enumeration children() {
        return new EnumerationIter(this.m_aColumns.iterator());
    }

    @Override
    public boolean getAllowsChildren() {
        return true;
    }

    @Override
    public TreeNode getChildAt(int childIndex) {
        return (TreeNode)this.m_aColumns.get(childIndex);
    }

    @Override
    public int getChildCount() {
        return this.m_aColumns.size();
    }

    @Override
    public int getIndex(TreeNode node) {
        return this.m_aColumns.indexOf(node);
    }

    @Override
    public TreeNode getParent() {
        return this.m_db;
    }

    @Override
    public boolean isLeaf() {
        return this.m_aColumns.isEmpty();
    }
}

