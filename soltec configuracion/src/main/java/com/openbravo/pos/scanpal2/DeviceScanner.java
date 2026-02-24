/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.scanpal2;

import com.openbravo.pos.scanpal2.DeviceScannerException;
import com.openbravo.pos.scanpal2.ProductDownloaded;

public interface DeviceScanner {
    public void connectDevice() throws DeviceScannerException;

    public void disconnectDevice();

    public void startDownloadProduct() throws DeviceScannerException;

    public ProductDownloaded recieveProduct() throws DeviceScannerException;

    public void startUploadProduct() throws DeviceScannerException;

    public void sendProduct(String var1, String var2, Double var3) throws DeviceScannerException;

    public void stopUploadProduct() throws DeviceScannerException;
}

