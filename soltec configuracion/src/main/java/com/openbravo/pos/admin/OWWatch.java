/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.dalsemi.onewire.OneWireAccessProvider
 *  com.dalsemi.onewire.adapter.DSPortAdapter
 *  com.dalsemi.onewire.application.monitor.DeviceMonitor
 *  com.dalsemi.onewire.application.monitor.DeviceMonitorEvent
 *  com.dalsemi.onewire.application.monitor.DeviceMonitorEventListener
 *  com.dalsemi.onewire.application.monitor.DeviceMonitorException
 */
package com.openbravo.pos.admin;

import com.dalsemi.onewire.OneWireAccessProvider;
import com.dalsemi.onewire.adapter.DSPortAdapter;
import com.dalsemi.onewire.application.monitor.DeviceMonitor;
import com.dalsemi.onewire.application.monitor.DeviceMonitorEvent;
import com.dalsemi.onewire.application.monitor.DeviceMonitorEventListener;
import com.dalsemi.onewire.application.monitor.DeviceMonitorException;

public class OWWatch
implements DeviceMonitorEventListener {
    private DeviceMonitor dm;

    public static void main(String[] args) {
        try {
            DSPortAdapter adapter = OneWireAccessProvider.getDefaultAdapter();
            System.out.println();
            System.out.println("Adapter: " + adapter.getAdapterName() + " Port: " + adapter.getPortName());
            System.out.println();
            adapter.setSearchAllDevices();
            adapter.targetAllFamilies();
            adapter.setSpeed(0);
            OWWatch nw = new OWWatch(adapter);
            int delay = args.length >= 1 ? Integer.decode(args[0]) : 20000;
            System.out.println("Monitor run for: " + delay + "ms");
            Thread.sleep(delay);
            nw.killWatch();
            adapter.freePort();
        }
        catch (Exception e) {
            System.out.println(e);
        }
    }

    public OWWatch(DSPortAdapter adapter) {
        this.dm = new DeviceMonitor(adapter);
        try {
            this.dm.addDeviceMonitorEventListener((DeviceMonitorEventListener)this);
        }
        catch (Exception exception) {
            // empty catch block
        }
        Thread t = new Thread((Runnable)this.dm);
        t.start();
    }

    public void killWatch() {
        this.dm.killMonitor();
    }

    public void deviceArrival(DeviceMonitorEvent devt) {
        for (int i = 0; i < devt.getDeviceCount(); ++i) {
            System.out.println("ADD: " + devt.getAddressAsStringAt(i));
        }
    }

    public void deviceDeparture(DeviceMonitorEvent devt) {
        for (int i = 0; i < devt.getDeviceCount(); ++i) {
            System.out.println("REMOVE: " + devt.getAddressAsStringAt(i));
        }
    }

    public void networkException(DeviceMonitorException dexc) {
        System.out.println("ERROR: " + dexc.toString());
    }
}

