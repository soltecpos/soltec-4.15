/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.panels;

import com.openbravo.pos.panels.SQLTable;
import java.util.Enumeration;
import javax.swing.tree.TreeNode;

public class SQLColumn
implements TreeNode {
    private SQLTable m_table;
    private String m_sName;

    public SQLColumn(SQLTable t, String name) {
        this.m_table = t;
        this.m_sName = name;
    }

    public String toString() {
        return this.m_sName;
    }

    @Override
    public Enumeration children() {
        return null;
    }

    @Override
    public boolean getAllowsChildren() {
        return false;
    }

    @Override
    public TreeNode getChildAt(int childIndex) {
        throw new ArrayIndexOutOfBoundsException();
    }

    @Override
    public int getChildCount() {
        return 0;
    }

    @Override
    public int getIndex(TreeNode node) {
        throw new ArrayIndexOutOfBoundsException();
    }

    @Override
    public TreeNode getParent() {
        return this.m_table;
    }

    @Override
    public boolean isLeaf() {
        return true;
    }
}

