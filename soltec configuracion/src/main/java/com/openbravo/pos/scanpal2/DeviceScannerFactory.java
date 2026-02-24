/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.scanpal2;

import com.openbravo.pos.forms.AppProperties;
import com.openbravo.pos.scanpal2.DeviceScanner;
import com.openbravo.pos.scanpal2.DeviceScannerComm;
import com.openbravo.pos.util.StringParser;

public class DeviceScannerFactory {
    private DeviceScannerFactory() {
    }

    public static DeviceScanner createInstance(AppProperties props) {
        StringParser sd = new StringParser(props.getProperty("machine.scanner"));
        String sScannerType = sd.nextToken(':');
        String sScannerParam1 = sd.nextToken(',');
        if ("scanpal2".equals(sScannerType)) {
            return new DeviceScannerComm(sScannerParam1);
        }
        return null;
    }
}

