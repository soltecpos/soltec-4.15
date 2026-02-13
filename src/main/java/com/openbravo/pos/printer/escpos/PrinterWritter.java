/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.printer.escpos;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public abstract class PrinterWritter {
    private boolean initialized = false;
    private ExecutorService exec = Executors.newSingleThreadExecutor();

    protected abstract void internalWrite(byte[] var1);

    protected abstract void internalFlush();

    protected abstract void internalClose();

    public void init(byte[] data) {
        if (!this.initialized) {
            this.write(data);
            this.initialized = true;
        }
    }

    public void write(String sValue) {
        this.write(sValue.getBytes());
    }

    public void write(final byte[] data) {
        this.exec.execute(new Runnable(){

            @Override
            public void run() {
                PrinterWritter.this.internalWrite(data);
            }
        });
    }

    public void flush() {
        this.exec.execute(new Runnable(){

            @Override
            public void run() {
                PrinterWritter.this.internalFlush();
            }
        });
    }

    public void close() {
        this.exec.execute(new Runnable(){

            @Override
            public void run() {
                PrinterWritter.this.internalClose();
            }
        });
        this.exec.shutdown();
    }
}

