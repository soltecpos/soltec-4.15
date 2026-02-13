/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.data.model;

import com.openbravo.data.model.Column;

public class Table {
    private String name;
    private Column[] columns;

    public Table(String name, Column ... columns) {
        this.name = name;
        this.columns = columns;
    }

    public String getName() {
        return this.name;
    }

    public Column[] getColumns() {
        return this.columns;
    }

    public String getListSQL() {
        StringBuilder sent = new StringBuilder();
        sent.append("select ");
        for (int i = 0; i < this.columns.length; ++i) {
            if (i > 0) {
                sent.append(", ");
            }
            sent.append(this.columns[i].getName());
        }
        sent.append(" from ");
        sent.append(this.name);
        return sent.toString();
    }

    public String getInsertSQL() {
        StringBuilder sent = new StringBuilder();
        StringBuilder values = new StringBuilder();
        sent.append("insert into ");
        sent.append(this.name);
        sent.append(" (");
        for (int i = 0; i < this.columns.length; ++i) {
            if (i > 0) {
                sent.append(", ");
                values.append(", ");
            }
            sent.append(this.columns[i].getName());
            values.append("?");
        }
        sent.append(") values (");
        sent.append(values.toString());
        sent.append(")");
        return sent.toString();
    }

    public String getUpdateSQL() {
        StringBuilder values = new StringBuilder();
        StringBuilder filter = new StringBuilder();
        for (int i = 0; i < this.columns.length; ++i) {
            if (this.columns[i].isPK()) {
                if (filter.length() == 0) {
                    filter.append(" where ");
                } else {
                    filter.append(" and ");
                }
                filter.append(this.columns[i].getName());
                filter.append(" = ?");
                continue;
            }
            if (values.length() > 0) {
                values.append(", ");
            }
            values.append(this.columns[i].getName());
            values.append(" = ?");
        }
        return "update " + this.name + " set " + values + filter;
    }

    public String getDeleteSQL() {
        StringBuilder filter = new StringBuilder();
        for (int i = 0; i < this.columns.length; ++i) {
            if (!this.columns[i].isPK()) continue;
            if (filter.length() == 0) {
                filter.append(" where ");
            } else {
                filter.append(" and ");
            }
            filter.append(this.columns[i].getName());
            filter.append(" = ?");
        }
        return "delete from " + this.name + filter;
    }
}

