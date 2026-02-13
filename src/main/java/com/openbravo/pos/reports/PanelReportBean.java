/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.reports;

import com.openbravo.basic.BasicException;
import com.openbravo.data.loader.BaseSentence;
import com.openbravo.data.loader.Datas;
import com.openbravo.data.loader.QBFBuilder;
import com.openbravo.data.loader.SerializerReadBasic;
import com.openbravo.data.loader.StaticSentence;
import com.openbravo.data.user.EditorCreator;
import com.openbravo.pos.forms.AppLocal;
import com.openbravo.pos.forms.AppView;
import com.openbravo.pos.forms.BeanFactoryException;
import com.openbravo.pos.reports.JPanelReport;
import com.openbravo.pos.reports.JParamsComposed;
import com.openbravo.pos.reports.ReportEditorCreator;
import com.openbravo.pos.reports.ReportFields;
import com.openbravo.pos.reports.ReportFieldsArray;
import java.util.ArrayList;
import java.util.List;

public class PanelReportBean
extends JPanelReport {
    private String title;
    private String report;
    private String resourcebundle = null;
    private String sentence;
    private List<Datas> fielddatas = new ArrayList<Datas>();
    private List<String> fieldnames = new ArrayList<String>();
    private List<String> paramnames = new ArrayList<String>();
    private JParamsComposed qbffilter = new JParamsComposed();

    @Override
    public void init(AppView app) throws BeanFactoryException {
        this.qbffilter.init(app);
        super.init(app);
    }

    @Override
    public void activate() throws BasicException {
        this.qbffilter.activate();
        super.activate();
        if (this.qbffilter.isEmpty()) {
            this.setVisibleFilter(false);
            this.setVisibleButtonFilter(false);
        }
    }

    @Override
    protected EditorCreator getEditorCreator() {
        return this.qbffilter;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setTitleKey(String titlekey) {
        this.title = AppLocal.getIntString(titlekey);
    }

    @Override
    public String getTitle() {
        return this.title;
    }

    public void setReport(String report) {
        this.report = report;
    }

    @Override
    protected String getReport() {
        return this.report;
    }

    public void setResourceBundle(String resourcebundle) {
        this.resourcebundle = resourcebundle;
    }

    @Override
    protected String getResourceBundle() {
        return this.resourcebundle == null ? this.report : this.resourcebundle;
    }

    public void setSentence(String sentence) {
        this.sentence = sentence;
    }

    public void addField(String name, Datas data) {
        this.fieldnames.add(name);
        this.fielddatas.add(data);
    }

    public void addParameter(String name) {
        this.paramnames.add(name);
    }

    @Override
    protected BaseSentence<Object, Object> getSentence() {
        return new StaticSentence(this.m_App.getSession(), new QBFBuilder(this.sentence, this.paramnames.toArray(new String[this.paramnames.size()])), this.qbffilter.getSerializerWrite(), new SerializerReadBasic(this.fielddatas.toArray(new Datas[this.fielddatas.size()])));
    }

    @Override
    protected ReportFields getReportFields() {
        return new ReportFieldsArray(this.fieldnames.toArray(new String[this.fieldnames.size()]));
    }

    public void addQBFFilter(ReportEditorCreator qbff) {
        this.qbffilter.addEditor(qbff);
    }
}

