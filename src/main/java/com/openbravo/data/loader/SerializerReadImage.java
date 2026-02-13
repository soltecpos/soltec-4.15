/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.data.loader;

import com.openbravo.basic.BasicException;
import com.openbravo.data.loader.DataRead;
import com.openbravo.data.loader.Datas;
import com.openbravo.data.loader.SerializerRead;
import java.awt.image.BufferedImage;

public class SerializerReadImage
implements SerializerRead<BufferedImage> {
    public static final SerializerRead<BufferedImage> INSTANCE = new SerializerReadImage();

    private SerializerReadImage() {
    }

    @Override
    public BufferedImage readValues(DataRead dr) throws BasicException {
        return (BufferedImage)Datas.IMAGE.getValue(dr, 1);
    }
}

