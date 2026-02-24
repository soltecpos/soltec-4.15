/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.panels;

import com.openbravo.pos.panels.EnumerationIter;
import com.openbravo.pos.panels.SQLTable;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import javax.swing.tree.TreeNode;

public class SQLDatabase
implements TreeNode {
    private ArrayList m_aTables;
    private HashMap m_mTables;
    private String m_sName;

    public SQLDatabase(String name) {
        this.m_sName = name;
        this.m_aTables = new ArrayList();
        this.m_mTables = new HashMap();
    }

    public String toString() {
        return this.m_sName;
    }

    public void addTable(String sTable) {
        SQLTable t = new SQLTable(this, sTable);
        this.m_aTables.add(t);
        this.m_mTables.put(sTable, t);
    }

    public SQLTable getTable(String sTable) {
        return (SQLTable)this.m_mTables.get(sTable);
    }

    @Override
    public Enumeration children() {
        return new EnumerationIter(this.m_aTables.iterator());
    }

    @Override
    public boolean getAllowsChildren() {
        return true;
    }

    @Override
    public TreeNode getChildAt(int childIndex) {
        return (TreeNode)this.m_aTables.get(childIndex);
    }

    @Override
    public int getChildCount() {
        return this.m_aTables.size();
    }

    @Override
    public int getIndex(TreeNode node) {
        return this.m_aTables.indexOf(node);
    }

    @Override
    public TreeNode getParent() {
        return null;
    }

    @Override
    public boolean isLeaf() {
        return this.m_aTables.isEmpty();
    }
}

