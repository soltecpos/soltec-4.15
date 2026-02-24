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
package com.openbravo.pos.util;

import com.dalsemi.onewire.OneWireAccessProvider;
import com.dalsemi.onewire.adapter.DSPortAdapter;
import com.dalsemi.onewire.application.monitor.DeviceMonitor;
import com.dalsemi.onewire.application.monitor.DeviceMonitorEvent;
import com.dalsemi.onewire.application.monitor.DeviceMonitorEventListener;
import com.dalsemi.onewire.application.monitor.DeviceMonitorException;

public class uOWWatch
implements DeviceMonitorEventListener {
    public static String ibuttonid;
    private DeviceMonitor dm;

    public static void iButtonOn() {
        try {
            DSPortAdapter adapter = OneWireAccessProvider.getDefaultAdapter();
            adapter.setSearchAllDevices();
            adapter.targetAllFamilies();
            adapter.setSpeed(0);
            uOWWatch uOWWatch2 = new uOWWatch(adapter);
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    public uOWWatch(DSPortAdapter adapter) {
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
            ibuttonid = devt.getAddressAsStringAt(i);
        }
    }

    public void deviceDeparture(DeviceMonitorEvent devt) {
        for (int i = 0; i < devt.getDeviceCount(); ++i) {
            ibuttonid = "";
        }
    }

    public void networkException(DeviceMonitorException dexc) {
    }

    public static String getibuttonid() {
        return ibuttonid;
    }
}

