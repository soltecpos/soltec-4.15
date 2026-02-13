/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.ticket;

import com.openbravo.basic.BasicException;
import com.openbravo.data.loader.DataRead;
import com.openbravo.data.loader.IKeyed;
import com.openbravo.data.loader.ImageUtils;
import com.openbravo.data.loader.SerializerRead;
import java.awt.image.BufferedImage;

public class CategoryInfo
implements IKeyed {
    private static final long serialVersionUID = 8612449444103L;
    private String m_sID;
    private String m_sName;
    private String m_sTextTip;
    private BufferedImage m_Image;
    private Boolean m_bCatShowName;

    public CategoryInfo(String id, String name, BufferedImage image, String texttip, Boolean catshowname) {
        this.m_sID = id;
        this.m_sName = name;
        this.m_Image = image;
        this.m_sTextTip = texttip;
        this.m_bCatShowName = catshowname;
    }

    @Override
    public Object getKey() {
        return this.m_sID;
    }

    public void setID(String sID) {
        this.m_sID = sID;
    }

    public String getID() {
        return this.m_sID;
    }

    public String getName() {
        return this.m_sName;
    }

    public void setName(String sName) {
        this.m_sName = sName;
    }

    public String getTextTip() {
        return this.m_sTextTip;
    }

    public void setTextTip(String sName) {
        this.m_sTextTip = sName;
    }

    public Boolean getCatShowName() {
        return this.m_bCatShowName;
    }

    public void setCatShowName(Boolean bcatshowname) {
        this.m_bCatShowName = bcatshowname;
    }

    public BufferedImage getImage() {
        return this.m_Image;
    }

    public void setImage(BufferedImage img) {
        this.m_Image = img;
    }

    public String toString() {
        return this.m_sName;
    }

    public static SerializerRead<CategoryInfo> getSerializerRead() {
        return new SerializerRead<CategoryInfo>(){

            @Override
            public CategoryInfo readValues(DataRead dr) throws BasicException {
                return new CategoryInfo(dr.getString(1), dr.getString(2), ImageUtils.readImage(dr.getBytes(3)), dr.getString(4), dr.getBoolean(5));
            }
        };
    }
}

