/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.usb4java.Context
 *  org.usb4java.Device
 *  org.usb4java.DeviceDescriptor
 *  org.usb4java.DeviceList
 *  org.usb4java.LibUsb
 *  org.usb4java.LibUsbException
 */
package org.usb4java.examples;

import org.usb4java.Context;
import org.usb4java.Device;
import org.usb4java.DeviceDescriptor;
import org.usb4java.DeviceList;
import org.usb4java.LibUsb;
import org.usb4java.LibUsbException;

public class ListDevices {
    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void main(String[] args) {
        Context context = new Context();
        int result = LibUsb.init((Context)context);
        if (result < 0) {
            throw new LibUsbException("Unable to initialize libusb", result);
        }
        DeviceList list = new DeviceList();
        result = LibUsb.getDeviceList((Context)context, (DeviceList)list);
        if (result < 0) {
            throw new LibUsbException("Unable to get device list", result);
        }
        try {
            for (Device device : list) {
                int address = LibUsb.getDeviceAddress((Device)device);
                int busNumber = LibUsb.getBusNumber((Device)device);
                DeviceDescriptor descriptor = new DeviceDescriptor();
                result = LibUsb.getDeviceDescriptor((Device)device, (DeviceDescriptor)descriptor);
                if (result < 0) {
                    throw new LibUsbException("Unable to read device descriptor", result);
                }
                System.out.format("Bus %03d, Device %03d: Vendor %04x, Product %04x%n", busNumber, address, descriptor.idVendor(), descriptor.idProduct());
            }
        }
        finally {
            LibUsb.freeDeviceList((DeviceList)list, (boolean)true);
        }
        LibUsb.exit((Context)context);
    }
}

