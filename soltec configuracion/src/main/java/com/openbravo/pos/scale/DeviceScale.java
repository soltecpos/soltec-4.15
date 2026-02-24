/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.scale;

import com.openbravo.pos.forms.AppLocal;
import com.openbravo.pos.forms.AppProperties;
import com.openbravo.pos.scale.Scale;
import com.openbravo.pos.scale.ScaleCASPDII;
import com.openbravo.pos.scale.ScaleCasioPD1;
import com.openbravo.pos.scale.ScaleComm;
import com.openbravo.pos.scale.ScaleDialog;
import com.openbravo.pos.scale.ScaleException;
import com.openbravo.pos.scale.ScaleFake;
import com.openbravo.pos.scale.ScaleSamsungEsp;
import com.openbravo.pos.util.StringParser;
import java.awt.Component;

public class DeviceScale {
    private Scale m_scale;

    public DeviceScale(Component parent, AppProperties props) {
        StringParser sd = new StringParser(props.getProperty("machine.scale"));
        String sScaleType = sd.nextToken(':');
        String sScaleParam1 = sd.nextToken(',');
        switch (sScaleType) {
            case "casiopd1": {
                this.m_scale = new ScaleCasioPD1(sScaleParam1);
                break;
            }
            case "dialog1": {
                this.m_scale = new ScaleComm(sScaleParam1);
                break;
            }
            case "samsungesp": {
                this.m_scale = new ScaleSamsungEsp(sScaleParam1);
                break;
            }
            case "caspdii": {
                this.m_scale = new ScaleCASPDII(sScaleParam1);
                break;
            }
            case "fake": {
                this.m_scale = new ScaleFake();
                break;
            }
            case "screen": {
                this.m_scale = new ScaleDialog(parent);
                break;
            }
            default: {
                this.m_scale = null;
            }
        }
    }

    public boolean existsScale() {
        return this.m_scale != null;
    }

    public Double readWeight() throws ScaleException {
        if (this.m_scale == null) {
            throw new ScaleException(AppLocal.getIntString("scale.notdefined"));
        }
        Double result = this.m_scale.readWeight();
        if (result == null) {
            return null;
        }
        if (result < 0.002) {
            throw new ScaleException(AppLocal.getIntString("scale.invalidvalue"));
        }
        return result;
    }
}

