/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.sales.restaurant;

import com.openbravo.basic.BasicException;
import com.openbravo.data.loader.DataRead;
import com.openbravo.data.loader.ImageUtils;
import com.openbravo.data.loader.SerializableRead;
import com.openbravo.pos.util.ThumbNailBuilder;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class Floor
implements SerializableRead {
    private static final long serialVersionUID = 8694154682897L;
    private String m_sID;
    private String m_sName;
    private Container m_container;
    private Icon m_icon;
    private static Image defimg = null;

    public Floor() {
        try {
            defimg = ImageIO.read(this.getClass().getClassLoader().getResourceAsStream("com/openbravo/images/floors.png"));
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    @Override
    public void readValues(DataRead dr) throws BasicException {
        this.m_sID = dr.getString(1);
        this.m_sName = dr.getString(2);
        BufferedImage img = ImageUtils.readImage(dr.getBytes(3));
        ThumbNailBuilder tnbcat = new ThumbNailBuilder(32, 32, defimg);
        this.m_container = new JPanelDrawing(img);
        this.m_icon = new ImageIcon(tnbcat.getThumbNail(img));
    }

    public String getID() {
        return this.m_sID;
    }

    public String getName() {
        return this.m_sName;
    }

    public Icon getIcon() {
        return this.m_icon;
    }

    public Container getContainer() {
        return this.m_container;
    }

    private static class JPanelDrawing
    extends JPanel {
        private Image img;

        public JPanelDrawing(Image img) {
            this.img = img;
            this.setLayout(null);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (this.img != null) {
                g.drawImage(this.img, 0, 0, this);
            }
        }

        @Override
        public Dimension getPreferredSize() {
            return this.img == null ? new Dimension(950, 560) : new Dimension(this.img.getWidth(this), this.img.getHeight(this));
        }

        @Override
        public Dimension getMinimumSize() {
            return this.getPreferredSize();
        }

        @Override
        public Dimension getMaximumSize() {
            return this.getPreferredSize();
        }
    }
}

