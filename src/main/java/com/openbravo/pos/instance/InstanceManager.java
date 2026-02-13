/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.instance;

import com.openbravo.pos.instance.AppMessage;
import java.rmi.AlreadyBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class InstanceManager {
    private final Registry m_registry = LocateRegistry.createRegistry(1099);
    private final AppMessage m_message;

    public InstanceManager(AppMessage message) throws RemoteException, AlreadyBoundException {
        this.m_message = message;
        AppMessage stub = (AppMessage)UnicastRemoteObject.exportObject((Remote)this.m_message, 0);
        this.m_registry.bind("AppMessage", stub);
    }
}

