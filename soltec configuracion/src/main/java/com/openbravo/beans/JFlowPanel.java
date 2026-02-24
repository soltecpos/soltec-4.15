/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.beans;

import java.awt.Component;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.Rectangle;
import javax.swing.JPanel;
import javax.swing.JViewport;
import javax.swing.Scrollable;

public class JFlowPanel
extends JPanel
implements Scrollable {
    private int hgap = 5;
    private int vgap = 5;

    public JFlowPanel() {
        this(5, 5);
    }

    public JFlowPanel(int hgap, int vgap) {
        this.hgap = hgap;
        this.vgap = vgap;
    }

    public void setHorizontalGap(int iValue) {
        this.hgap = iValue;
    }

    public int getHorizontalGap() {
        return this.hgap;
    }

    public void setVerticalGap(int iValue) {
        this.vgap = iValue;
    }

    public int getVerticalGap(int iValue) {
        return this.vgap;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private Dimension calculateFlowLayout(boolean bDoChilds) {
        int maxWidth;
        Dimension dim = new Dimension(0, this.hgap);
        if (this.getParent() != null && this.getParent() instanceof JViewport) {
            JViewport viewport = (JViewport)this.getParent();
            maxWidth = viewport.getExtentSize().width;
        } else {
            maxWidth = this.getParent() != null ? this.getParent().getWidth() : this.getWidth();
        }
        Object object = this.getTreeLock();
        synchronized (object) {
            int compCount = this.getComponentCount();
            int maxRowWidth = 0;
            int maxRowHeight = 0;
            int x = 0;
            for (int i = 0; i < compCount; ++i) {
                Component m = this.getComponent(i);
                if (!m.isVisible()) continue;
                Dimension d = m.getPreferredSize();
                if (x == 0 || x + this.hgap + d.width + this.hgap <= maxWidth) {
                    x += this.hgap;
                    if (bDoChilds) {
                        m.setBounds(this.getPosition(x, maxWidth - d.width), dim.height, d.width, d.height);
                    }
                    x += d.width;
                    if (d.height <= maxRowHeight) continue;
                    maxRowHeight = d.height;
                    continue;
                }
                dim.height += maxRowHeight + this.vgap;
                if (bDoChilds) {
                    m.setBounds(this.getPosition(this.hgap, maxWidth - d.width), dim.height, d.width, d.height);
                }
                if (x > maxRowWidth) {
                    maxRowWidth = x;
                }
                x = this.hgap + d.width;
                maxRowHeight = d.height;
            }
            dim.height += maxRowHeight + this.vgap;
            if (x > maxRowWidth) {
                maxRowWidth = x;
            }
            dim.width = maxRowWidth;
        }
        return dim;
    }

    private int getPosition(int x, int width) {
        if (this.getComponentOrientation() == ComponentOrientation.RIGHT_TO_LEFT) {
            return width - x;
        }
        return x;
    }

    @Override
    public Dimension getPreferredSize() {
        return this.calculateFlowLayout(false);
    }

    @Override
    public Dimension getMinimumSize() {
        return this.calculateFlowLayout(false);
    }

    @Override
    public Dimension getMaximumSize() {
        return this.calculateFlowLayout(false);
    }

    @Override
    public Dimension getPreferredScrollableViewportSize() {
        return this.calculateFlowLayout(false);
    }

    @Override
    public void doLayout() {
        this.calculateFlowLayout(true);
    }

    @Override
    public boolean getScrollableTracksViewportHeight() {
        return this.getParent().getHeight() > this.getPreferredSize().height;
    }

    @Override
    public boolean getScrollableTracksViewportWidth() {
        return this.getParent().getWidth() > this.getPreferredSize().width;
    }

    @Override
    public int getScrollableUnitIncrement(Rectangle visibleRect, int orientation, int direction) {
        if (this.getComponentCount() == 0) {
            return orientation == 0 ? this.hgap : this.vgap;
        }
        return orientation == 0 ? this.getComponent(0).getWidth() + this.hgap : this.getComponent(0).getHeight() + this.vgap;
    }

    @Override
    public int getScrollableBlockIncrement(Rectangle visibleRect, int orientation, int direction) {
        if (this.getComponentCount() == 0) {
            return orientation == 0 ? this.hgap : this.vgap;
        }
        if (orientation == 0) {
            int hunit = this.getComponent(0).getWidth() + this.hgap;
            return visibleRect.width / hunit * hunit;
        }
        int vunit = this.getComponent(0).getHeight() + this.vgap;
        return visibleRect.width / vunit * vunit;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void setEnabled(boolean value) {
        Object object = this.getTreeLock();
        synchronized (object) {
            int compCount = this.getComponentCount();
            for (int i = 0; i < compCount; ++i) {
                this.getComponent(i).setEnabled(value);
            }
        }
        super.setEnabled(value);
    }
}

