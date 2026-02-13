/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.data.gui;

import com.openbravo.basic.BasicException;
import com.openbravo.data.gui.FindInfo;
import com.openbravo.data.gui.JFind;
import com.openbravo.data.gui.JSort;
import com.openbravo.data.gui.MessageInf;
import com.openbravo.data.loader.ComparatorCreator;
import com.openbravo.data.loader.LocalRes;
import com.openbravo.data.loader.Vectorer;
import com.openbravo.data.user.BrowsableEditableData;
import com.openbravo.data.user.BrowseListener;
import com.openbravo.data.user.StateListener;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Comparator;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JSeparator;

public class JNavigator<T>
extends JPanel
implements BrowseListener,
StateListener {
    public static final int BUTTONS_ALL = 0;
    public static final int BUTTONS_NONAVIGATE = 1;
    protected BrowsableEditableData<T> m_bd;
    protected ComparatorCreator m_cc;
    protected FindInfo m_LastFindInfo;
    private JButton jbtnFind = null;
    private JButton jbtnSort = null;
    private JButton jbtnFirst = null;
    private JButton jbtnLast = null;
    private JButton jbtnNext = null;
    private JButton jbtnPrev = null;
    private JButton jbtnRefresh = null;
    private JButton jbtnReload = null;

    public JNavigator(BrowsableEditableData<T> bd, Vectorer vec, ComparatorCreator cc, int iButtons) {
        this.initComponents();
        if (iButtons == 0) {
            this.jbtnFirst = new JButton();
            this.jbtnFirst.setPreferredSize(new Dimension(60, 45));
            this.jbtnFirst.setIcon(new ImageIcon(this.getClass().getResource("/com/openbravo/images/2leftarrow.png")));
            this.jbtnFirst.setMargin(new Insets(2, 2, 2, 2));
            this.jbtnFirst.setFocusPainted(false);
            this.jbtnFirst.setFocusable(false);
            this.jbtnFirst.setRequestFocusEnabled(false);
            this.jbtnFirst.addActionListener(new ActionListener(){

                @Override
                public void actionPerformed(ActionEvent evt) {
                    JNavigator.this.jbtnFirstActionPerformed(evt);
                }
            });
            this.add(this.jbtnFirst);
        }
        if (iButtons == 0) {
            this.jbtnPrev = new JButton();
            this.jbtnPrev.setPreferredSize(new Dimension(60, 45));
            this.jbtnPrev.setIcon(new ImageIcon(this.getClass().getResource("/com/openbravo/images/1leftarrow.png")));
            this.jbtnPrev.setMargin(new Insets(2, 2, 2, 2));
            this.jbtnPrev.setFocusPainted(false);
            this.jbtnPrev.setFocusable(false);
            this.jbtnPrev.setRequestFocusEnabled(false);
            this.jbtnPrev.addActionListener(new ActionListener(){

                @Override
                public void actionPerformed(ActionEvent evt) {
                    JNavigator.this.jbtnPrevActionPerformed(evt);
                }
            });
            this.add(this.jbtnPrev);
        }
        this.jbtnRefresh = new JButton();
        this.jbtnRefresh.setPreferredSize(new Dimension(60, 45));
        this.jbtnRefresh.setIcon(new ImageIcon(this.getClass().getResource("/com/openbravo/images/1downarrow.png")));
        this.jbtnRefresh.setMargin(new Insets(2, 2, 2, 2));
        this.jbtnRefresh.setFocusPainted(false);
        this.jbtnRefresh.setFocusable(false);
        this.jbtnRefresh.setRequestFocusEnabled(false);
        this.jbtnRefresh.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                JNavigator.this.jbtnRefreshActionPerformed(evt);
            }
        });
        this.add(this.jbtnRefresh);
        if (iButtons == 0) {
            this.jbtnNext = new JButton();
            this.jbtnNext.setPreferredSize(new Dimension(60, 45));
            this.jbtnNext.setIcon(new ImageIcon(this.getClass().getResource("/com/openbravo/images/1rightarrow.png")));
            this.jbtnNext.setMargin(new Insets(2, 2, 2, 2));
            this.jbtnNext.setFocusPainted(false);
            this.jbtnNext.setFocusable(false);
            this.jbtnNext.setRequestFocusEnabled(false);
            this.jbtnNext.addActionListener(new ActionListener(){

                @Override
                public void actionPerformed(ActionEvent evt) {
                    JNavigator.this.jbtnNextActionPerformed(evt);
                }
            });
            this.add(this.jbtnNext);
        }
        if (iButtons == 0) {
            this.jbtnLast = new JButton();
            this.jbtnLast.setPreferredSize(new Dimension(60, 45));
            this.jbtnLast.setIcon(new ImageIcon(this.getClass().getResource("/com/openbravo/images/2rightarrow.png")));
            this.jbtnLast.setMargin(new Insets(2, 2, 2, 2));
            this.jbtnLast.setFocusPainted(false);
            this.jbtnLast.setFocusable(false);
            this.jbtnLast.setRequestFocusEnabled(false);
            this.jbtnLast.addActionListener(new ActionListener(){

                @Override
                public void actionPerformed(ActionEvent evt) {
                    JNavigator.this.jbtnLastActionPerformed(evt);
                }
            });
            this.add(this.jbtnLast);
        }
        this.add(new JSeparator());
        if (bd.canLoadData()) {
            this.jbtnReload = new JButton();
            this.jbtnReload.setPreferredSize(new Dimension(60, 45));
            this.jbtnReload.setIcon(new ImageIcon(this.getClass().getResource("/com/openbravo/images/reload.png")));
            this.jbtnReload.setMargin(new Insets(2, 2, 2, 2));
            this.jbtnReload.setFocusPainted(false);
            this.jbtnReload.setFocusable(false);
            this.jbtnReload.setRequestFocusEnabled(false);
            this.jbtnReload.addActionListener(new ActionListener(){

                @Override
                public void actionPerformed(ActionEvent evt) {
                    JNavigator.this.jbtnReloadActionPerformed(evt);
                }
            });
            this.add(this.jbtnReload);
            this.add(new JSeparator());
        }
        if (vec == null) {
            this.m_LastFindInfo = null;
        } else {
            this.m_LastFindInfo = new FindInfo(vec);
            this.jbtnFind = new JButton();
            this.jbtnFind.setPreferredSize(new Dimension(60, 45));
            this.jbtnFind.setIcon(new ImageIcon(this.getClass().getResource("/com/openbravo/images/search24.png")));
            this.jbtnFind.setMargin(new Insets(2, 2, 2, 2));
            this.jbtnFind.setFocusPainted(false);
            this.jbtnFind.setFocusable(false);
            this.jbtnFind.setRequestFocusEnabled(false);
            this.jbtnFind.addActionListener(new ActionListener(){

                @Override
                public void actionPerformed(ActionEvent evt) {
                    JNavigator.this.jbtnFindActionPerformed(evt);
                }
            });
            this.add(this.jbtnFind);
        }
        this.m_cc = cc;
        if (this.m_cc != null) {
            this.jbtnSort = new JButton();
            this.jbtnSort.setPreferredSize(new Dimension(60, 45));
            this.jbtnSort.setIcon(new ImageIcon(this.getClass().getResource("/com/openbravo/images/sort_incr.png")));
            this.jbtnSort.setMargin(new Insets(2, 2, 2, 2));
            this.jbtnSort.setFocusPainted(false);
            this.jbtnSort.setFocusable(false);
            this.jbtnSort.setRequestFocusEnabled(false);
            this.jbtnSort.addActionListener(new ActionListener(){

                @Override
                public void actionPerformed(ActionEvent evt) {
                    JNavigator.this.jbtnSortActionPerformed(evt);
                }
            });
            this.add(this.jbtnSort);
        }
        this.m_bd = bd;
        bd.addBrowseListener(this);
        bd.addStateListener(this);
    }

    public JNavigator(BrowsableEditableData<T> bd) {
        this(bd, null, null, 0);
    }

    public JNavigator(BrowsableEditableData<T> bd, Vectorer vec, ComparatorCreator cc) {
        this(bd, vec, cc, 0);
    }

    @Override
    public void updateState(int iState) {
        if (iState == 3 || iState == 2) {
            if (this.jbtnFirst != null) {
                this.jbtnFirst.setEnabled(false);
            }
            if (this.jbtnPrev != null) {
                this.jbtnPrev.setEnabled(false);
            }
            if (this.jbtnNext != null) {
                this.jbtnNext.setEnabled(false);
            }
            if (this.jbtnLast != null) {
                this.jbtnLast.setEnabled(false);
            }
            if (this.jbtnRefresh != null) {
                this.jbtnRefresh.setEnabled(true);
            }
        }
    }

    @Override
    public void updateIndex(int iIndex, int iCounter) {
        if (iIndex >= 0 && iIndex < iCounter) {
            if (this.jbtnFirst != null) {
                this.jbtnFirst.setEnabled(iIndex > 0);
            }
            if (this.jbtnPrev != null) {
                this.jbtnPrev.setEnabled(iIndex > 0);
            }
            if (this.jbtnNext != null) {
                this.jbtnNext.setEnabled(iIndex < iCounter - 1);
            }
            if (this.jbtnLast != null) {
                this.jbtnLast.setEnabled(iIndex < iCounter - 1);
            }
            if (this.jbtnRefresh != null) {
                this.jbtnRefresh.setEnabled(true);
            }
        } else {
            if (this.jbtnFirst != null) {
                this.jbtnFirst.setEnabled(false);
            }
            if (this.jbtnPrev != null) {
                this.jbtnPrev.setEnabled(false);
            }
            if (this.jbtnNext != null) {
                this.jbtnNext.setEnabled(false);
            }
            if (this.jbtnLast != null) {
                this.jbtnLast.setEnabled(false);
            }
            if (this.jbtnRefresh != null) {
                this.jbtnRefresh.setEnabled(false);
            }
        }
    }

    private void jbtnSortActionPerformed(ActionEvent evt) {
        try {
            Comparator<?> c = JSort.showMessage(this, this.m_cc);
            if (c != null) {
                this.m_bd.sort((Comparator<? super T>)c);
            }
        }
        catch (BasicException eD) {
            MessageInf msg = new MessageInf(-67108864, LocalRes.getIntString("message.nolistdata"), eD);
            msg.show(this);
        }
    }

    private void jbtnFindActionPerformed(ActionEvent evt) {
        try {
            FindInfo newFindInfo = JFind.showMessage(this, this.m_LastFindInfo);
            if (newFindInfo != null) {
                this.m_LastFindInfo = newFindInfo;
                int index = this.m_bd.findNext(newFindInfo);
                if (index < 0) {
                    MessageInf msg = new MessageInf(-67108864, LocalRes.getIntString("message.norecord"));
                    msg.show(this);
                } else {
                    this.m_bd.moveTo(index);
                }
            }
        }
        catch (BasicException eD) {
            MessageInf msg = new MessageInf(-67108864, LocalRes.getIntString("message.nolistdata"), eD);
            msg.show(this);
        }
    }

    private void jbtnRefreshActionPerformed(ActionEvent evt) {
        this.m_bd.actionReloadCurrent(this);
    }

    private void jbtnReloadActionPerformed(ActionEvent evt) {
        try {
            this.m_bd.actionLoad();
        }
        catch (BasicException eD) {
            MessageInf msg = new MessageInf(-67108864, LocalRes.getIntString("message.noreload"), eD);
            msg.show(this);
        }
    }

    private void jbtnLastActionPerformed(ActionEvent evt) {
        try {
            this.m_bd.moveLast();
        }
        catch (BasicException eD) {
            MessageInf msg = new MessageInf(-67108864, LocalRes.getIntString("message.nomove"), eD);
            msg.show(this);
        }
    }

    private void jbtnFirstActionPerformed(ActionEvent evt) {
        try {
            this.m_bd.moveFirst();
        }
        catch (BasicException eD) {
            MessageInf msg = new MessageInf(-67108864, LocalRes.getIntString("message.nomove"), eD);
            msg.show(this);
        }
    }

    private void jbtnPrevActionPerformed(ActionEvent evt) {
        try {
            this.m_bd.movePrev();
        }
        catch (BasicException eD) {
            MessageInf msg = new MessageInf(-67108864, LocalRes.getIntString("message.nomove"), eD);
            msg.show(this);
        }
    }

    private void jbtnNextActionPerformed(ActionEvent evt) {
        try {
            this.m_bd.moveNext();
        }
        catch (BasicException eD) {
            MessageInf msg = new MessageInf(-67108864, LocalRes.getIntString("message.nomove"), eD);
            msg.show(this);
        }
    }

    private void initComponents() {
        this.setFont(new Font("Arial", 0, 12));
    }
}

