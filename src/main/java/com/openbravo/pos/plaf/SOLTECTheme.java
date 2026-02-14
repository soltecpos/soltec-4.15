/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.plaf;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JToggleButton;
import javax.swing.UIManager;
import javax.swing.plaf.FontUIResource;
import javax.swing.plaf.basic.BasicButtonUI;
import javax.swing.plaf.basic.BasicToggleButtonUI;

public class SOLTECTheme {
    public static final Color MAIN_BLUE = new Color(0, 51, 102);
    public static final Color ACCENT_ORANGE = new Color(255, 102, 0);
    public static final Color LIGHT_BLUE = new Color(220, 230, 240);
    public static final Color WHITE = Color.WHITE;

    public static void setup() {
        try {
            Font font = new Font("Segoe UI", 0, 18);
            FontUIResource fontRes = new FontUIResource(font);
            UIManager.put("defaultFont", fontRes);
            UIManager.put("Button.font", new FontUIResource("Segoe UI", 1, 18));
            UIManager.put("Label.font", fontRes);
            UIManager.put("TextField.font", fontRes);
            UIManager.put("Table.font", fontRes);
            UIManager.put("TableHeader.font", new FontUIResource("Segoe UI", 1, 18));
            UIManager.put("@accentColor", ACCENT_ORANGE);
            Color softBackground = new Color(250, 251, 253);
            UIManager.put("@background", softBackground);
            UIManager.put("Panel.background", softBackground);
            UIManager.put("Viewport.background", softBackground);
            UIManager.put("Button.arc", 16);
            UIManager.put("Component.arc", 16);
            UIManager.put("TextComponent.arc", 16);
            UIManager.put("ProgressBar.arc", 16);
            UIManager.put("Button.background", WHITE);
            UIManager.put("Button.hoverBackground", LIGHT_BLUE);
            UIManager.put("Button.focusedBorderColor", ACCENT_ORANGE);
            UIManager.put("Button.borderWidth", 1);
            UIManager.put("Button.margin", new Insets(8, 15, 8, 15));
            UIManager.put("List.selectionBackground", MAIN_BLUE);
            UIManager.put("List.selectionForeground", WHITE);
            UIManager.put("Table.selectionBackground", MAIN_BLUE);
            UIManager.put("Table.selectionForeground", WHITE);
            UIManager.put("Table.rowHeight", 35);
            UIManager.put("Table.showVerticalLines", false);
            UIManager.put("Table.intercellSpacing", new Dimension(0, 0));
            UIManager.put("List.selectionBackground", MAIN_BLUE);
            UIManager.put("List.selectionForeground", WHITE);
            UIManager.put("Table.selectionBackground", MAIN_BLUE);
            UIManager.put("Table.selectionForeground", WHITE);
            UIManager.put("Tree.selectionBackground", MAIN_BLUE);
            UIManager.put("ScrollBar.width", 16);
            UIManager.put("ScrollBar.thumbArc", 16);
            UIManager.put("ScrollBar.thumbInsets", new Insets(2, 2, 2, 2));
            UIManager.put("Component.focusColor", ACCENT_ORANGE);
            UIManager.put("Component.focusWidth", 2);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void applyIconButtonStyle(final JButton button) {
        button.setUI(new BasicButtonUI());
        button.setBorder(null);
        button.setBorderPainted(false);
        button.setContentAreaFilled(true);
        button.setFocusPainted(false);
        button.setOpaque(true);
        button.setBackground(Color.WHITE);
        button.setMargin(new Insets(0, 0, 0, 0));
        final Color HOVER_COLOR = new Color(255, 240, 230);
        final Color PRESSED_COLOR = new Color(255, 220, 200);
        final Color DEFAULT_COLOR = Color.WHITE;
        button.addMouseListener(new MouseAdapter(){

            @Override
            public void mouseEntered(MouseEvent evt) {
                if (button.isEnabled()) {
                    button.setBackground(HOVER_COLOR);
                    button.setCursor(new Cursor(12));
                }
            }

            @Override
            public void mouseExited(MouseEvent evt) {
                button.setBackground(DEFAULT_COLOR);
                button.setCursor(new Cursor(0));
            }

            @Override
            public void mousePressed(MouseEvent evt) {
                if (button.isEnabled()) {
                    button.setBackground(PRESSED_COLOR);
                }
            }

            @Override
            public void mouseReleased(MouseEvent evt) {
                if (button.isEnabled() && button.contains(evt.getPoint())) {
                    button.setBackground(HOVER_COLOR);
                } else {
                    button.setBackground(DEFAULT_COLOR);
                }
            }
        });
        button.setCursor(new Cursor(12));
    }

    public static ImageIcon getScaledIcon(URL imageUrl, int w, int h) {
        if (imageUrl == null) {
            return new ImageIcon(new java.awt.image.BufferedImage(w, h, java.awt.image.BufferedImage.TYPE_INT_ARGB));
        }
        ImageIcon original = new ImageIcon(imageUrl);
        if (original.getIconWidth() <= 0) {
            return new ImageIcon(new java.awt.image.BufferedImage(w, h, java.awt.image.BufferedImage.TYPE_INT_ARGB));
        }
        // Use smooth scaling
        Image scaled = original.getImage().getScaledInstance(w, h, Image.SCALE_SMOOTH);
        return new ImageIcon(scaled);
    }

    public static void applyBorderlessButtonStyle(final JButton button) {
        button.setOpaque(false);
        button.setBackground(Color.WHITE);
        button.setBorder(null);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setFont(new Font("Arial", Font.BOLD, 12));
        button.setForeground(Color.BLACK);
        button.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        button.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        
        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                if (button.isEnabled()) {
                    button.setContentAreaFilled(true);
                    button.setBackground(new Color(245, 245, 245));
                    button.setCursor(new Cursor(Cursor.HAND_CURSOR));
                }
            }
            public void mouseExited(MouseEvent evt) {
                button.setContentAreaFilled(false);
                button.setBackground(Color.WHITE);
            }
        });
    }

    public static void applyModernButtonStyle(final JButton button) {
        button.setOpaque(true);
        button.setBackground(new Color(245, 245, 245)); // Light gray background
        button.setBorder(javax.swing.BorderFactory.createCompoundBorder(
            javax.swing.BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
            javax.swing.BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        button.setFocusPainted(false);
        button.setFont(new Font("Arial", Font.BOLD, 12));
        button.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        button.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        
        final Color HOVER_COLOR = new Color(220, 230, 240); // Soft blue
        final Color DEFAULT_COLOR = new Color(245, 245, 245);
        final Color PRESSED_COLOR = new Color(200, 210, 220);

        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                if (button.isEnabled()) {
                    button.setBackground(HOVER_COLOR);
                    button.setCursor(new Cursor(Cursor.HAND_CURSOR));
                }
            }
            public void mouseExited(MouseEvent evt) {
                if (button.isEnabled()) {
                    button.setBackground(DEFAULT_COLOR);
                    button.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                }
            }
            public void mousePressed(MouseEvent evt) {
                if (button.isEnabled()) {
                    button.setBackground(PRESSED_COLOR);
                }
            }
            public void mouseReleased(MouseEvent evt) {
                if (button.isEnabled() && button.contains(evt.getPoint())) {
                    button.setBackground(HOVER_COLOR);
                } else {
                    button.setBackground(DEFAULT_COLOR);
                }
            }
        });
    }

    public static void applyToggleButtonStyle(final JToggleButton button) {
        button.setUI(new BasicToggleButtonUI());
        button.setBorder(null);
        button.setBorderPainted(false);
        button.setContentAreaFilled(true);
        button.setFocusPainted(false);
        button.setOpaque(true);
        button.setBackground(Color.WHITE);
        button.setMargin(new Insets(0, 0, 0, 0));
        final Color HOVER_COLOR = new Color(255, 240, 230);
        final Color PRESSED_COLOR = new Color(255, 220, 200);
        final Color DEFAULT_COLOR = Color.WHITE;
        button.addMouseListener(new MouseAdapter(){

            @Override
            public void mouseEntered(MouseEvent evt) {
                if (button.isEnabled()) {
                    button.setBackground(HOVER_COLOR);
                    button.setCursor(new Cursor(12));
                }
            }

            @Override
            public void mouseExited(MouseEvent evt) {
                if (!button.isSelected()) {
                    button.setBackground(DEFAULT_COLOR);
                } else {
                    button.setBackground(PRESSED_COLOR);
                }
                button.setCursor(new Cursor(0));
            }

            @Override
            public void mousePressed(MouseEvent evt) {
                if (button.isEnabled()) {
                    button.setBackground(PRESSED_COLOR);
                }
            }

            @Override
            public void mouseReleased(MouseEvent evt) {
                if (button.isEnabled() && button.contains(evt.getPoint())) {
                    button.setBackground(HOVER_COLOR);
                } else if (button.isSelected()) {
                    button.setBackground(PRESSED_COLOR);
                } else {
                    button.setBackground(DEFAULT_COLOR);
                }
            }
        });
        button.setCursor(new Cursor(12));
    }
}

