/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.admin;

import com.openbravo.data.loader.Datas;
import com.openbravo.data.loader.SentenceList;
import com.openbravo.data.loader.SerializerReadClass;
import com.openbravo.data.loader.Session;
import com.openbravo.data.loader.StaticSentence;
import com.openbravo.data.loader.TableDefinition;
import com.openbravo.format.Formats;
import com.openbravo.pos.admin.PeopleInfo;
import com.openbravo.pos.admin.RoleInfo;
import com.openbravo.pos.forms.AppLocal;
import com.openbravo.pos.forms.BeanFactoryDataSingle;

public class DataLogicAdmin
extends BeanFactoryDataSingle {
    private Session s;
    private TableDefinition m_tpeople;
    private TableDefinition m_troles;
    private TableDefinition m_tresources;

    @Override
    public void init(Session s) {
        this.s = s;
        this.m_tpeople = new TableDefinition(s, "people", new String[]{"ID", "NAME", "APPPASSWORD", "ROLE", "VISIBLE", "CARD", "IMAGE"}, new String[]{"ID", AppLocal.getIntString("label.peoplename"), AppLocal.getIntString("label.Password"), AppLocal.getIntString("label.role"), AppLocal.getIntString("label.peoplevisible"), AppLocal.getIntString("label.card"), AppLocal.getIntString("label.peopleimage")}, new Datas[]{Datas.STRING, Datas.STRING, Datas.STRING, Datas.STRING, Datas.BOOLEAN, Datas.STRING, Datas.IMAGE}, new Formats[]{Formats.STRING, Formats.STRING, Formats.STRING, Formats.STRING, Formats.BOOLEAN, Formats.STRING, Formats.NULL}, new int[]{0});
        this.m_troles = new TableDefinition(s, "roles", new String[]{"ID", "NAME", "PERMISSIONS"}, new String[]{"ID", AppLocal.getIntString("label.name"), "PERMISSIONS"}, new Datas[]{Datas.STRING, Datas.STRING, Datas.BYTES}, new Formats[]{Formats.STRING, Formats.STRING, Formats.NULL}, new int[]{0});
        this.m_tresources = new TableDefinition(s, "resources", new String[]{"ID", "NAME", "RESTYPE", "CONTENT"}, new String[]{"ID", AppLocal.getIntString("label.name"), AppLocal.getIntString("label.type"), "CONTENT"}, new Datas[]{Datas.STRING, Datas.STRING, Datas.INT, Datas.BYTES}, new Formats[]{Formats.STRING, Formats.STRING, Formats.INT, Formats.NULL}, new int[]{0});
    }

    public final SentenceList<RoleInfo> getRolesList() {
        return new StaticSentence(this.s, "SELECT ID, NAME FROM roles ORDER BY NAME", null, new SerializerReadClass<RoleInfo>(RoleInfo.class));
    }

    public final TableDefinition getTablePeople() {
        return this.m_tpeople;
    }

    public final TableDefinition getTableRoles() {
        return this.m_troles;
    }

    public final TableDefinition getTableResources() {
        return this.m_tresources;
    }

    public final SentenceList<PeopleInfo> getPeopleList() {
        return new StaticSentence(this.s, "SELECT ID, NAME FROM people ORDER BY NAME", null, new SerializerReadClass<PeopleInfo>(PeopleInfo.class));
    }
}

