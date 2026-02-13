/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.data.gui;

import com.openbravo.data.loader.LocalRes;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.UIManager;
import javax.swing.filechooser.FileFilter;
import org.netbeans.lib.awtextra.AbsoluteConstraints;
import org.netbeans.lib.awtextra.AbsoluteLayout;

public class JImageEditor
extends JPanel {
    private Dimension m_maxsize;
    private ZoomIcon m_icon;
    private BufferedImage m_Img = null;
    private static File m_fCurrentDirectory = null;
    private static NumberFormat m_percentformat = new DecimalFormat("#,##0.##%");
    private JPanel jPanel1;
    private JPanel jPanel2;
    private JLabel m_jImage;
    private JLabel m_jPercent;
    private JScrollPane m_jScr;
    private JButton m_jbtnclose;
    private JButton m_jbtnopen;
    private JButton m_jbtnzoomin;
    private JButton m_jbtnzoomout;

    public JImageEditor() {
        this.initComponents();
        this.m_Img = null;
        this.m_maxsize = null;
        this.m_icon = new ZoomIcon();
        this.m_jImage.setIcon(this.m_icon);
        this.m_jPercent.setText(m_percentformat.format(this.m_icon.getZoom()));
        this.privateSetEnabled(this.isEnabled());
    }

    public void setMaxDimensions(Dimension size) {
        this.m_maxsize = size;
    }

    public Dimension getMaxDimensions() {
        return this.m_maxsize;
    }

    @Override
    public void setEnabled(boolean value) {
        this.privateSetEnabled(value);
        super.setEnabled(value);
    }

    private void privateSetEnabled(boolean value) {
        this.m_jbtnopen.setEnabled(value);
        this.m_jbtnclose.setEnabled(value && this.m_Img != null);
        this.m_jbtnzoomin.setEnabled(value && this.m_Img != null);
        this.m_jbtnzoomout.setEnabled(value && this.m_Img != null);
        this.m_jPercent.setEnabled(value && this.m_Img != null);
        this.m_jScr.setEnabled(value && this.m_Img != null);
    }

    public void setImage(BufferedImage img) {
        BufferedImage oldimg = this.m_Img;
        this.m_Img = img;
        this.m_icon.setIcon(this.m_Img == null ? null : new ImageIcon(this.m_Img));
        this.m_jPercent.setText(m_percentformat.format(this.m_icon.getZoom()));
        this.m_jImage.revalidate();
        this.m_jScr.revalidate();
        this.m_jScr.repaint();
        this.privateSetEnabled(this.isEnabled());
        this.firePropertyChange("image", oldimg, this.m_Img);
    }

    public BufferedImage getImage() {
        return this.m_Img;
    }

    public double getZoom() {
        return this.m_icon.getZoom();
    }

    public void setZoom(double zoom) {
        double oldzoom = this.m_icon.getZoom();
        this.m_icon.setZoom(zoom);
        this.m_jPercent.setText(m_percentformat.format(this.m_icon.getZoom()));
        this.m_jImage.revalidate();
        this.m_jScr.revalidate();
        this.m_jScr.repaint();
        this.firePropertyChange("zoom", oldzoom, zoom);
    }

    public void incZoom() {
        double zoom = this.m_icon.getZoom();
        this.setZoom(zoom > 4.0 ? 8.0 : zoom * 2.0);
    }

    public void decZoom() {
        double zoom = this.m_icon.getZoom();
        this.setZoom(zoom < 0.5 ? 0.25 : zoom / 2.0);
    }

    public void doLoad() {
        JFileChooser fc = new JFileChooser(m_fCurrentDirectory);
        fc.addChoosableFileFilter(new ExtensionsFilter(LocalRes.getIntString("label.imagefiles"), "png", "gif", "jpg", "jpeg", "bmp"));
        if (fc.showOpenDialog(this) == 0) {
            try {
                BufferedImage img = ImageIO.read(fc.getSelectedFile());
                if (img != null) {
                    if (this.m_maxsize != null && (img.getHeight() > this.m_maxsize.height || img.getWidth() > this.m_maxsize.width) && JOptionPane.showConfirmDialog(this, LocalRes.getIntString("message.resizeimage"), LocalRes.getIntString("title.editor"), 0, 3) == 0) {
                        img = this.resizeImage(img);
                    }
                    this.setImage(img);
                    m_fCurrentDirectory = fc.getCurrentDirectory();
                }
            }
            catch (IOException iOException) {
                // empty catch block
            }
        }
    }

    private BufferedImage resizeImage(BufferedImage img) {
        int myheight = img.getHeight();
        int mywidth = img.getWidth();
        if (myheight > this.m_maxsize.height) {
            mywidth = mywidth * this.m_maxsize.height / myheight;
            myheight = this.m_maxsize.height;
        }
        if (mywidth > this.m_maxsize.width) {
            myheight = myheight * this.m_maxsize.width / mywidth;
            mywidth = this.m_maxsize.width;
        }
        BufferedImage thumb = new BufferedImage(mywidth, myheight, 6);
        double scalex = (double)mywidth / (double)img.getWidth(null);
        double scaley = (double)myheight / (double)img.getHeight(null);
        Graphics2D g2d = thumb.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.setColor(new Color(0, 0, 0, 0));
        g2d.fillRect(0, 0, mywidth, myheight);
        if (scalex < scaley) {
            g2d.drawImage(img, 0, (int)(((double)myheight - (double)img.getHeight(null) * scalex) / 2.0), mywidth, (int)((double)img.getHeight(null) * scalex), null);
        } else {
            g2d.drawImage(img, (int)(((double)mywidth - (double)img.getWidth(null) * scaley) / 2.0), 0, (int)((double)img.getWidth(null) * scaley), myheight, null);
        }
        g2d.dispose();
        return thumb;
    }

    private void initComponents() {
        this.m_jScr = new JScrollPane();
        this.m_jImage = new JLabel();
        this.jPanel1 = new JPanel();
        this.jPanel2 = new JPanel();
        this.m_jbtnopen = new JButton();
        this.m_jbtnclose = new JButton();
        this.m_jbtnzoomin = new JButton();
        this.m_jPercent = new JLabel();
        this.m_jbtnzoomout = new JButton();
        this.setLayout(new BorderLayout());
        this.m_jImage.setHorizontalAlignment(0);
        this.m_jImage.setIcon(new ImageIcon(this.getClass().getResource("/com/openbravo/images/no_photo.png")));
        this.m_jImage.setHorizontalTextPosition(0);
        this.m_jScr.setViewportView(this.m_jImage);
        this.add((Component)this.m_jScr, "Center");
        this.jPanel1.setLayout(new AbsoluteLayout());
        this.jPanel2.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));
        this.jPanel2.setFont(new Font("Arial", 0, 12));
        this.jPanel2.setLayout(new GridLayout(0, 1, 0, 2));
        this.m_jbtnopen.setIcon(new ImageIcon(this.getClass().getResource("/com/openbravo/images/camera.png")));
        this.m_jbtnopen.setToolTipText("Open Folder");
        this.m_jbtnopen.setPreferredSize(new Dimension(50, 45));
        this.m_jbtnopen.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                JImageEditor.this.m_jbtnopenActionPerformed(evt);
            }
        });
        this.jPanel2.add(this.m_jbtnopen);
        this.m_jbtnclose.setIcon(new ImageIcon(this.getClass().getResource("/com/openbravo/images/fileclose.png")));
        this.m_jbtnclose.setToolTipText("Remove Picture");
        this.m_jbtnclose.setPreferredSize(new Dimension(50, 45));
        this.m_jbtnclose.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                JImageEditor.this.m_jbtncloseActionPerformed(evt);
            }
        });
        this.jPanel2.add(this.m_jbtnclose);
        this.m_jbtnzoomin.setIcon(new ImageIcon(this.getClass().getResource("/com/openbravo/images/viewmag+.png")));
        this.m_jbtnzoomin.setToolTipText("Zoom In");
        this.m_jbtnzoomin.setPreferredSize(new Dimension(50, 45));
        this.m_jbtnzoomin.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                JImageEditor.this.m_jbtnzoominActionPerformed(evt);
            }
        });
        this.jPanel2.add(this.m_jbtnzoomin);
        this.m_jPercent.setHorizontalAlignment(4);
        this.m_jPercent.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(UIManager.getDefaults().getColor("Button.darkShadow")), BorderFactory.createEmptyBorder(1, 4, 1, 4)));
        this.m_jPercent.setOpaque(true);
        this.m_jPercent.setPreferredSize(new Dimension(10, 30));
        this.jPanel2.add(this.m_jPercent);
        this.m_jbtnzoomout.setIcon(new ImageIcon(this.getClass().getResource("/com/openbravo/images/viewmag-.png")));
        this.m_jbtnzoomout.setToolTipText("Zoom Out");
        this.m_jbtnzoomout.setPreferredSize(new Dimension(50, 45));
        this.m_jbtnzoomout.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                JImageEditor.this.m_jbtnzoomoutActionPerformed(evt);
            }
        });
        this.jPanel2.add(this.m_jbtnzoomout);
        this.jPanel1.add((Component)this.jPanel2, new AbsoluteConstraints(0, 0, -1, -1));
        this.add((Component)this.jPanel1, "After");
    }

    private void m_jbtnzoomoutActionPerformed(ActionEvent evt) {
        this.decZoom();
    }

    private void m_jbtnzoominActionPerformed(ActionEvent evt) {
        this.incZoom();
    }

    private void m_jbtncloseActionPerformed(ActionEvent evt) {
        this.setImage(null);
    }

    private void m_jbtnopenActionPerformed(ActionEvent evt) {
        this.doLoad();
    }

    private static class ZoomIcon
    implements Icon {
        private Icon ico = null;
        private double zoom = 1.0;

        @Override
        public int getIconHeight() {
            return this.ico == null ? 0 : (int)(this.zoom * (double)this.ico.getIconHeight());
        }

        @Override
        public int getIconWidth() {
            return this.ico == null ? 0 : (int)(this.zoom * (double)this.ico.getIconWidth());
        }

        @Override
        public void paintIcon(Component c, Graphics g, int x, int y) {
            if (this.ico != null) {
                Graphics2D g2d = (Graphics2D)g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
                AffineTransform oldt = g2d.getTransform();
                g2d.transform(AffineTransform.getScaleInstance(this.zoom, this.zoom));
                this.ico.paintIcon(c, g2d, (int)((double)x / this.zoom), (int)((double)y / this.zoom));
                g2d.setTransform(oldt);
            }
        }

        public void setIcon(Icon ico) {
            this.ico = ico;
            this.zoom = 1.0;
        }

        public void setZoom(double zoom) {
            this.zoom = zoom;
        }

        public double getZoom() {
            return this.zoom;
        }
    }

    private static class ExtensionsFilter
    extends FileFilter {
        private String message;
        private String[] extensions;

        public ExtensionsFilter(String message, String ... extensions) {
            this.message = message;
            this.extensions = extensions;
        }

        @Override
        public boolean accept(File f) {
            if (f.isDirectory()) {
                return true;
            }
            String sFileName = f.getName();
            int ipos = sFileName.lastIndexOf(46);
            if (ipos >= 0) {
                String sExt = sFileName.substring(ipos + 1);
                for (String s : this.extensions) {
                    if (!s.equalsIgnoreCase(sExt)) continue;
                    return true;
                }
            }
            return false;
        }

        @Override
        public String getDescription() {
            return this.message;
        }
    }
}

