/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.instance;

import com.openbravo.pos.instance.AppMessage;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class InstanceQuery {
    private final AppMessage m_appstub;

    public InstanceQuery() throws RemoteException, NotBoundException {
        Registry registry = LocateRegistry.getRegistry();
        this.m_appstub = (AppMessage)registry.lookup("AppMessage");
    }

    public AppMessage getAppMessage() {
        return this.m_appstub;
    }
}

