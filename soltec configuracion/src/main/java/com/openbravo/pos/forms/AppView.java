/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.forms;

import com.openbravo.data.loader.Session;
import com.openbravo.pos.forms.AppProperties;
import com.openbravo.pos.forms.AppUserView;
import com.openbravo.pos.forms.BeanFactoryException;
import com.openbravo.pos.printer.DeviceTicket;
import com.openbravo.pos.scale.DeviceScale;
import com.openbravo.pos.scanpal2.DeviceScanner;
import java.util.Date;

public interface AppView {
    public DeviceScale getDeviceScale();

    public DeviceTicket getDeviceTicket();

    public DeviceScanner getDeviceScanner();

    public Session getSession();

    public AppProperties getProperties();

    public Object getBean(String var1) throws BeanFactoryException;

    public void setActiveCash(String var1, int var2, Date var3, Date var4);

    public String getActiveCashIndex();

    public int getActiveCashSequence();

    public Date getActiveCashDateStart();

    public Date getActiveCashDateEnd();

    public void setClosedCash(String var1, int var2, Date var3, Date var4);

    public String getClosedCashIndex();

    public int getClosedCashSequence();

    public Date getClosedCashDateStart();

    public Date getClosedCashDateEnd();

    public String getInventoryLocation();

    public void waitCursorBegin();

    public void waitCursorEnd();

    public AppUserView getAppUserView();
}

