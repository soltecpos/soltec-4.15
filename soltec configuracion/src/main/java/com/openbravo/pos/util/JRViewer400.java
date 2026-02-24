/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.sf.jasperreports.engine.DefaultJasperReportsContext
 *  net.sf.jasperreports.engine.ImageMapRenderable
 *  net.sf.jasperreports.engine.JRException
 *  net.sf.jasperreports.engine.JRPrintAnchorIndex
 *  net.sf.jasperreports.engine.JRPrintElement
 *  net.sf.jasperreports.engine.JRPrintFrame
 *  net.sf.jasperreports.engine.JRPrintHyperlink
 *  net.sf.jasperreports.engine.JRPrintImage
 *  net.sf.jasperreports.engine.JRPrintImageAreaHyperlink
 *  net.sf.jasperreports.engine.JRPrintPage
 *  net.sf.jasperreports.engine.JRPropertiesUtil
 *  net.sf.jasperreports.engine.JRRuntimeException
 *  net.sf.jasperreports.engine.JasperPrint
 *  net.sf.jasperreports.engine.JasperPrintManager
 *  net.sf.jasperreports.engine.JasperReportsContext
 *  net.sf.jasperreports.engine.PrintPageFormat
 *  net.sf.jasperreports.engine.PrintPart
 *  net.sf.jasperreports.engine.PrintParts
 *  net.sf.jasperreports.engine.Renderable
 *  net.sf.jasperreports.engine.export.JRGraphics2DExporter
 *  net.sf.jasperreports.engine.type.HyperlinkTypeEnum
 *  net.sf.jasperreports.engine.util.FileResolver
 *  net.sf.jasperreports.engine.util.JRLoader
 *  net.sf.jasperreports.engine.util.LocalJasperReportsContext
 *  net.sf.jasperreports.engine.util.SimpleFileResolver
 *  net.sf.jasperreports.engine.xml.JRPrintXmlLoader
 *  net.sf.jasperreports.export.ExporterInput
 *  net.sf.jasperreports.export.ExporterOutput
 *  net.sf.jasperreports.export.ReportExportConfiguration
 *  net.sf.jasperreports.export.SimpleExporterInput
 *  net.sf.jasperreports.export.SimpleGraphics2DExporterOutput
 *  net.sf.jasperreports.export.SimpleGraphics2DReportConfiguration
 *  net.sf.jasperreports.view.JRHyperlinkListener
 *  net.sf.jasperreports.view.JRSaveContributor
 *  net.sf.jasperreports.view.SaveContributorUtils
 *  net.sf.jasperreports.view.save.JRPrintSaveContributor
 *  org.apache.commons.logging.Log
 *  org.apache.commons.logging.LogFactory
 */
package com.openbravo.pos.util;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseMotionListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.MessageFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.JViewport;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileFilter;
import net.sf.jasperreports.engine.DefaultJasperReportsContext;
import net.sf.jasperreports.engine.ImageMapRenderable;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRPrintAnchorIndex;
import net.sf.jasperreports.engine.JRPrintElement;
import net.sf.jasperreports.engine.JRPrintFrame;
import net.sf.jasperreports.engine.JRPrintHyperlink;
import net.sf.jasperreports.engine.JRPrintImage;
import net.sf.jasperreports.engine.JRPrintImageAreaHyperlink;
import net.sf.jasperreports.engine.JRPrintPage;
import net.sf.jasperreports.engine.JRPropertiesUtil;
import net.sf.jasperreports.engine.JRRuntimeException;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;
import net.sf.jasperreports.engine.JasperReportsContext;
import net.sf.jasperreports.engine.PrintPageFormat;
import net.sf.jasperreports.engine.PrintPart;
import net.sf.jasperreports.engine.PrintParts;
import net.sf.jasperreports.engine.Renderable;
import net.sf.jasperreports.engine.export.JRGraphics2DExporter;
import net.sf.jasperreports.engine.type.HyperlinkTypeEnum;
import net.sf.jasperreports.engine.util.FileResolver;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.engine.util.LocalJasperReportsContext;
import net.sf.jasperreports.engine.util.SimpleFileResolver;
import net.sf.jasperreports.engine.xml.JRPrintXmlLoader;
import net.sf.jasperreports.export.ExporterInput;
import net.sf.jasperreports.export.ExporterOutput;
import net.sf.jasperreports.export.ReportExportConfiguration;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleGraphics2DExporterOutput;
import net.sf.jasperreports.export.SimpleGraphics2DReportConfiguration;
import net.sf.jasperreports.view.JRHyperlinkListener;
import net.sf.jasperreports.view.JRSaveContributor;
import net.sf.jasperreports.view.SaveContributorUtils;
import net.sf.jasperreports.view.save.JRPrintSaveContributor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public final class JRViewer400
extends JPanel
implements JRHyperlinkListener {
    private static final Log log = LogFactory.getLog(JRViewer400.class);
    private static final long serialVersionUID = 10200L;
    public static final String VIEWER_RENDER_BUFFER_MAX_SIZE = "net.sf.jasperreports.viewer.render.buffer.max.size";
    protected static final int TYPE_FILE_NAME = 1;
    protected static final int TYPE_INPUT_STREAM = 2;
    protected static final int TYPE_OBJECT = 3;
    public static final int REPORT_RESOLUTION = 72;
    protected final float MIN_ZOOM = 0.5f;
    protected final float MAX_ZOOM = 10.0f;
    protected int[] zooms = new int[]{50, 75, 100, 125, 150, 175, 200, 250, 400, 800};
    protected int defaultZoomIndex = 2;
    protected int type = 1;
    protected boolean isXML;
    protected String reportFileName;
    JasperPrint jasperPrint;
    private int pageIndex;
    private boolean pageError;
    protected float zoom;
    private JRGraphics2DExporter exporter;
    private int screenResolution = 72;
    protected float realZoom;
    private DecimalFormat zoomDecimalFormat;
    protected JasperReportsContext jasperReportsContext;
    protected LocalJasperReportsContext localJasperReportsContext;
    private ResourceBundle resourceBundle;
    private int downX;
    private int downY;
    private boolean pnlTabsChangeListenerEnabled = true;
    private List<JRHyperlinkListener> hyperlinkListeners = new ArrayList<JRHyperlinkListener>();
    private Map<JPanel, JRPrintHyperlink> linksMap = new HashMap<JPanel, JRPrintHyperlink>();
    private MouseListener mouseListener = new MouseAdapter(){

        @Override
        public void mouseClicked(MouseEvent evt) {
            JRViewer400.this.hyperlinkClicked(evt);
        }
    };
    protected KeyListener keyNavigationListener = new KeyListener(){

        @Override
        public void keyTyped(KeyEvent evt) {
        }

        @Override
        public void keyPressed(KeyEvent evt) {
            JRViewer400.this.keyNavigate(evt);
        }

        @Override
        public void keyReleased(KeyEvent evt) {
        }
    };
    protected List<JRSaveContributor> saveContributors = new ArrayList<JRSaveContributor>();
    protected File lastFolder;
    protected JRSaveContributor lastSaveContributor;
    protected JToggleButton btnActualSize;
    protected JButton btnFirst;
    protected JToggleButton btnFitPage;
    protected JToggleButton btnFitWidth;
    protected JButton btnLast;
    protected JButton btnNext;
    protected JButton btnPrevious;
    protected JButton btnPrint;
    protected JButton btnReload;
    protected JButton btnSave;
    protected JButton btnZoomIn;
    protected JButton btnZoomOut;
    protected JComboBox<String> cmbZoom;
    private JLabel jLabel1;
    private JPanel jPanel4;
    private JPanel jPanel5;
    private JPanel jPanel6;
    private JPanel jPanel7;
    private JPanel jPanel8;
    private JPanel jPanel9;
    private JToolBar.Separator jSeparator1;
    private JToolBar.Separator jSeparator2;
    private JToolBar.Separator jSeparator3;
    private JToolBar jToolBar;
    private JLabel lblPage;
    protected JLabel lblStatus;
    private JPanel pnlInScroll;
    private JPanel pnlLinks;
    private JPanel pnlMain;
    private JPanel pnlPage;
    protected JPanel pnlStatus;
    private JTabbedPane pnlTabs;
    private JScrollPane scrollPane;
    protected JTextField txtGoTo;

    public JRViewer400(String fileName, boolean isXML) throws JRException {
        this(fileName, isXML, null);
    }

    public JRViewer400(InputStream is, boolean isXML) throws JRException {
        this(is, isXML, null);
    }

    public JRViewer400(JasperPrint jrPrint) {
        this(jrPrint, null);
    }

    public JRViewer400(String fileName, boolean isXML, Locale locale) throws JRException {
        this(fileName, isXML, locale, null);
    }

    public JRViewer400(InputStream is, boolean isXML, Locale locale) throws JRException {
        this(is, isXML, locale, null);
    }

    public JRViewer400(JasperPrint jrPrint, Locale locale) {
        this(jrPrint, locale, null);
    }

    public JRViewer400(String fileName, boolean isXML, Locale locale, ResourceBundle resBundle) throws JRException {
        this((JasperReportsContext)DefaultJasperReportsContext.getInstance(), fileName, isXML, locale, resBundle);
    }

    public JRViewer400(InputStream is, boolean isXML, Locale locale, ResourceBundle resBundle) throws JRException {
        this((JasperReportsContext)DefaultJasperReportsContext.getInstance(), is, isXML, locale, resBundle);
    }

    public JRViewer400(JasperPrint jrPrint, Locale locale, ResourceBundle resBundle) {
        this((JasperReportsContext)DefaultJasperReportsContext.getInstance(), jrPrint, locale, resBundle);
    }

    public JRViewer400(JasperReportsContext jasperReportsContext, String fileName, boolean isXML, Locale locale, ResourceBundle resBundle) throws JRException {
        this.jasperReportsContext = jasperReportsContext;
        this.initResources(locale, resBundle);
        this.setScreenDetails();
        this.setZooms();
        this.initComponents();
        this.loadReport(fileName, isXML);
        this.cmbZoom.setSelectedIndex(this.defaultZoomIndex);
        this.initSaveContributors();
        this.addHyperlinkListener(this);
    }

    public JRViewer400(JasperReportsContext jasperReportsContext, InputStream is, boolean isXML, Locale locale, ResourceBundle resBundle) throws JRException {
        this.jasperReportsContext = jasperReportsContext;
        this.initResources(locale, resBundle);
        this.setScreenDetails();
        this.setZooms();
        this.initComponents();
        this.loadReport(is, isXML);
        this.cmbZoom.setSelectedIndex(this.defaultZoomIndex);
        this.initSaveContributors();
        this.addHyperlinkListener(this);
    }

    public JRViewer400(JasperReportsContext jasperReportsContext, JasperPrint jrPrint, Locale locale, ResourceBundle resBundle) {
        this.jasperReportsContext = jasperReportsContext;
        this.initResources(locale, resBundle);
        this.setScreenDetails();
        this.setZooms();
        this.initComponents();
        this.loadReport(jrPrint);
        this.cmbZoom.setSelectedIndex(this.defaultZoomIndex);
        this.initSaveContributors();
        this.addHyperlinkListener(this);
    }

    public void loadJasperPrint(JasperPrint jrPrint) {
        this.loadReport(jrPrint);
        this.setZoomRatio((float)this.zooms[this.defaultZoomIndex] / 100.0f);
        this.cmbZoomItemStateChanged(null);
        this.refreshPage();
    }

    private void setScreenDetails() {
        this.screenResolution = Toolkit.getDefaultToolkit().getScreenResolution();
    }

    public void clear() {
        this.emptyContainer(this);
        this.jasperPrint = null;
    }

    protected void setZooms() {
    }

    public void addSaveContributor(JRSaveContributor contributor) {
        this.saveContributors.add(contributor);
    }

    public void removeSaveContributor(JRSaveContributor contributor) {
        this.saveContributors.remove(contributor);
    }

    public JRSaveContributor[] getSaveContributors() {
        return this.saveContributors.toArray(new JRSaveContributor[this.saveContributors.size()]);
    }

    public void setSaveContributors(JRSaveContributor[] saveContribs) {
        this.saveContributors = new ArrayList<JRSaveContributor>();
        if (saveContribs != null) {
            this.saveContributors.addAll(Arrays.asList(saveContribs));
        }
    }

    public void addHyperlinkListener(JRHyperlinkListener listener) {
        this.hyperlinkListeners.add(listener);
    }

    public void removeHyperlinkListener(JRHyperlinkListener listener) {
        this.hyperlinkListeners.remove(listener);
    }

    public JRHyperlinkListener[] getHyperlinkListeners() {
        return this.hyperlinkListeners.toArray(new JRHyperlinkListener[this.hyperlinkListeners.size()]);
    }

    protected void initResources(Locale locale, ResourceBundle resBundle) {
        if (locale != null) {
            this.setLocale(locale);
        } else {
            this.setLocale(Locale.getDefault());
        }
        this.resourceBundle = resBundle == null ? ResourceBundle.getBundle("net/sf/jasperreports/view/viewer", this.getLocale()) : resBundle;
        this.zoomDecimalFormat = new DecimalFormat("#.##", DecimalFormatSymbols.getInstance(this.getLocale()));
    }

    protected JasperReportsContext getJasperReportsContext() {
        return this.jasperReportsContext;
    }

    protected String getBundleString(String key) {
        return this.resourceBundle.getString(key);
    }

    protected void initSaveContributors() {
        List builtinContributors = SaveContributorUtils.createBuiltinContributors((JasperReportsContext)this.jasperReportsContext, (Locale)this.getLocale(), (ResourceBundle)this.resourceBundle);
        this.saveContributors.addAll(builtinContributors);
    }

    public void gotoHyperlink(JRPrintHyperlink hyperlink) {
        switch (hyperlink.getHyperlinkTypeValue()) {
            case REFERENCE: {
                if (!this.isOnlyHyperlinkListener()) break;
                System.out.println("Hyperlink reference : " + hyperlink.getHyperlinkReference());
                System.out.println("Implement your own JRHyperlinkListener to manage this type of event.");
                break;
            }
            case LOCAL_ANCHOR: {
                Container container;
                if (hyperlink.getHyperlinkAnchor() == null) break;
                Map anchorIndexes = this.jasperPrint.getAnchorIndexes();
                JRPrintAnchorIndex anchorIndex = (JRPrintAnchorIndex)anchorIndexes.get(hyperlink.getHyperlinkAnchor());
                if (anchorIndex.getPageIndex() != this.pageIndex) {
                    this.setPageIndex(anchorIndex.getPageIndex());
                    this.refreshPage();
                }
                if (!((container = this.pnlInScroll.getParent()) instanceof JViewport)) break;
                JViewport viewport = (JViewport)container;
                int newX = (int)((float)anchorIndex.getElementAbsoluteX() * this.realZoom);
                int newY = (int)((float)anchorIndex.getElementAbsoluteY() * this.realZoom);
                int maxX = this.pnlInScroll.getWidth() - viewport.getWidth();
                int maxY = this.pnlInScroll.getHeight() - viewport.getHeight();
                if (newX < 0) {
                    newX = 0;
                }
                if (newX > maxX) {
                    newX = maxX;
                }
                if (newY < 0) {
                    newY = 0;
                }
                if (newY > maxY) {
                    newY = maxY;
                }
                viewport.setViewPosition(new Point(newX, newY));
                break;
            }
            case LOCAL_PAGE: {
                int page = this.pageIndex + 1;
                if (hyperlink.getHyperlinkPage() != null) {
                    page = hyperlink.getHyperlinkPage();
                }
                if (page < 1 || page > this.jasperPrint.getPages().size() || page == this.pageIndex + 1) break;
                this.setPageIndex(page - 1);
                this.refreshPage();
                Container container = this.pnlInScroll.getParent();
                if (!(container instanceof JViewport)) break;
                JViewport viewport = (JViewport)container;
                viewport.setViewPosition(new Point(0, 0));
                break;
            }
            case REMOTE_ANCHOR: {
                if (!this.isOnlyHyperlinkListener()) break;
                System.out.println("Hyperlink reference : " + hyperlink.getHyperlinkReference());
                System.out.println("Hyperlink anchor    : " + hyperlink.getHyperlinkAnchor());
                System.out.println("Implement your own JRHyperlinkListener to manage this type of event.");
                break;
            }
            case REMOTE_PAGE: {
                if (!this.isOnlyHyperlinkListener()) break;
                System.out.println("Hyperlink reference : " + hyperlink.getHyperlinkReference());
                System.out.println("Hyperlink page      : " + hyperlink.getHyperlinkPage());
                System.out.println("Implement your own JRHyperlinkListener to manage this type of event.");
                break;
            }
            case CUSTOM: {
                if (!this.isOnlyHyperlinkListener()) break;
                System.out.println("Hyperlink of type " + hyperlink.getLinkType());
                System.out.println("Implement your own JRHyperlinkListener to manage this type of event.");
                break;
            }
        }
    }

    protected boolean isOnlyHyperlinkListener() {
        int listenerCount;
        if (this.hyperlinkListeners == null) {
            listenerCount = 0;
        } else {
            listenerCount = this.hyperlinkListeners.size();
            if (this.hyperlinkListeners.contains(this)) {
                --listenerCount;
            }
        }
        return listenerCount == 0;
    }

    private void initComponents() {
        this.jToolBar = new JToolBar();
        this.btnSave = new JButton();
        this.btnPrint = new JButton();
        this.btnReload = new JButton();
        this.jSeparator1 = new JToolBar.Separator();
        this.btnActualSize = new JToggleButton();
        this.btnFitPage = new JToggleButton();
        this.btnFitWidth = new JToggleButton();
        this.jSeparator2 = new JToolBar.Separator();
        this.btnZoomIn = new JButton();
        this.cmbZoom = new JComboBox();
        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<String>();
        for (int i = 0; i < this.zooms.length; ++i) {
            model.addElement("" + this.zooms[i] + "%");
        }
        this.cmbZoom.setModel(model);
        this.btnZoomOut = new JButton();
        this.jSeparator3 = new JToolBar.Separator();
        this.btnFirst = new JButton();
        this.btnPrevious = new JButton();
        this.txtGoTo = new JTextField();
        this.btnNext = new JButton();
        this.btnLast = new JButton();
        this.pnlMain = new JPanel();
        this.scrollPane = new JScrollPane();
        this.scrollPane.getHorizontalScrollBar().setUnitIncrement(5);
        this.scrollPane.getVerticalScrollBar().setUnitIncrement(5);
        this.pnlInScroll = new JPanel();
        this.pnlPage = new JPanel();
        this.jPanel4 = new JPanel();
        this.pnlLinks = new JPanel();
        this.jPanel5 = new JPanel();
        this.jPanel6 = new JPanel();
        this.jPanel7 = new JPanel();
        this.jPanel8 = new JPanel();
        this.jLabel1 = new JLabel();
        this.jPanel9 = new JPanel();
        this.lblPage = new PageRenderer(this);
        this.pnlTabs = new JTabbedPane();
        this.pnlStatus = new JPanel();
        this.lblStatus = new JLabel();
        this.setMinimumSize(new Dimension(450, 150));
        this.setPreferredSize(new Dimension(450, 150));
        this.setLayout(new BorderLayout());
        this.jToolBar.setFloatable(false);
        this.jToolBar.setFont(new Font("Arial", 0, 12));
        this.btnSave.setIcon(new ImageIcon(this.getClass().getResource("/com/openbravo/images/filesave.png")));
        this.btnSave.setToolTipText(this.getBundleString("save"));
        this.btnSave.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                JRViewer400.this.btnSaveActionPerformed(evt);
            }
        });
        this.jToolBar.add(this.btnSave);
        this.btnPrint.setIcon(new ImageIcon(this.getClass().getResource("/com/openbravo/images/yast_printer.png")));
        this.btnPrint.setToolTipText(this.getBundleString("print"));
        this.btnPrint.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                JRViewer400.this.btnPrintActionPerformed(evt);
            }
        });
        this.jToolBar.add(this.btnPrint);
        this.btnReload.setIcon(new ImageIcon(this.getClass().getResource("/com/openbravo/images/reload.png")));
        this.btnReload.setToolTipText(this.getBundleString("reload"));
        this.btnReload.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                JRViewer400.this.btnReloadActionPerformed(evt);
            }
        });
        this.jToolBar.add(this.btnReload);
        this.jToolBar.add(this.jSeparator1);
        this.btnActualSize.setIcon(new ImageIcon(this.getClass().getResource("/com/openbravo/images/mime.png")));
        this.btnActualSize.setToolTipText(this.getBundleString("actual.size"));
        this.btnActualSize.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                JRViewer400.this.btnActualSizeActionPerformed(evt);
            }
        });
        this.jToolBar.add(this.btnActualSize);
        this.btnFitPage.setIcon(new ImageIcon(this.getClass().getResource("/com/openbravo/images/mime2.png")));
        this.btnFitPage.setToolTipText(this.getBundleString("fit.page"));
        this.btnFitPage.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                JRViewer400.this.btnFitPageActionPerformed(evt);
            }
        });
        this.jToolBar.add(this.btnFitPage);
        this.btnFitWidth.setIcon(new ImageIcon(this.getClass().getResource("/com/openbravo/images/mime3.png")));
        this.btnFitWidth.setToolTipText(this.getBundleString("fit.width"));
        this.btnFitWidth.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                JRViewer400.this.btnFitWidthActionPerformed(evt);
            }
        });
        this.jToolBar.add(this.btnFitWidth);
        this.jToolBar.add(this.jSeparator2);
        this.btnZoomIn.setIcon(new ImageIcon(this.getClass().getResource("/com/openbravo/images/viewmag+.png")));
        this.btnZoomIn.setToolTipText(this.getBundleString("zoom.in"));
        this.btnZoomIn.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                JRViewer400.this.btnZoomInActionPerformed(evt);
            }
        });
        this.jToolBar.add(this.btnZoomIn);
        this.cmbZoom.setEditable(true);
        this.cmbZoom.setToolTipText(this.getBundleString("zoom.ratio"));
        this.cmbZoom.setMaximumSize(new Dimension(80, 23));
        this.cmbZoom.setMinimumSize(new Dimension(80, 23));
        this.cmbZoom.setPreferredSize(new Dimension(80, 23));
        this.cmbZoom.addItemListener(new ItemListener(){

            @Override
            public void itemStateChanged(ItemEvent evt) {
                JRViewer400.this.cmbZoomItemStateChanged(evt);
            }
        });
        this.cmbZoom.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                JRViewer400.this.cmbZoomActionPerformed(evt);
            }
        });
        this.jToolBar.add(this.cmbZoom);
        this.btnZoomOut.setIcon(new ImageIcon(this.getClass().getResource("/com/openbravo/images/viewmag-.png")));
        this.btnZoomOut.setToolTipText(this.getBundleString("zoom.out"));
        this.btnZoomOut.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                JRViewer400.this.btnZoomOutActionPerformed(evt);
            }
        });
        this.jToolBar.add(this.btnZoomOut);
        this.jToolBar.add(this.jSeparator3);
        this.btnFirst.setIcon(new ImageIcon(this.getClass().getResource("/com/openbravo/images/2leftarrow.png")));
        this.btnFirst.setToolTipText(this.getBundleString("first.page"));
        this.btnFirst.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                JRViewer400.this.btnFirstActionPerformed(evt);
            }
        });
        this.jToolBar.add(this.btnFirst);
        this.btnPrevious.setIcon(new ImageIcon(this.getClass().getResource("/com/openbravo/images/1leftarrow.png")));
        this.btnPrevious.setToolTipText(this.getBundleString("previous.page"));
        this.btnPrevious.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                JRViewer400.this.btnPreviousActionPerformed(evt);
            }
        });
        this.jToolBar.add(this.btnPrevious);
        this.txtGoTo.setToolTipText(this.getBundleString("go.to.page"));
        this.txtGoTo.setMaximumSize(new Dimension(40, 23));
        this.txtGoTo.setMinimumSize(new Dimension(40, 23));
        this.txtGoTo.setPreferredSize(new Dimension(40, 23));
        this.txtGoTo.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                JRViewer400.this.txtGoToActionPerformed(evt);
            }
        });
        this.jToolBar.add(this.txtGoTo);
        this.btnNext.setIcon(new ImageIcon(this.getClass().getResource("/com/openbravo/images/1rightarrow.png")));
        this.btnNext.setToolTipText(this.getBundleString("next.page"));
        this.btnNext.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                JRViewer400.this.btnNextActionPerformed(evt);
            }
        });
        this.jToolBar.add(this.btnNext);
        this.btnLast.setIcon(new ImageIcon(this.getClass().getResource("/com/openbravo/images/2rightarrow.png")));
        this.btnLast.setToolTipText(this.getBundleString("last.page"));
        this.btnLast.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                JRViewer400.this.btnLastActionPerformed(evt);
            }
        });
        this.jToolBar.add(this.btnLast);
        this.add((Component)this.jToolBar, "North");
        this.pnlMain.addComponentListener(new ComponentAdapter(){

            @Override
            public void componentResized(ComponentEvent evt) {
                JRViewer400.this.pnlMainComponentResized(evt);
            }
        });
        this.pnlMain.setLayout(new BorderLayout());
        this.scrollPane.setHorizontalScrollBarPolicy(32);
        this.scrollPane.setVerticalScrollBarPolicy(22);
        this.pnlInScroll.setLayout(new GridBagLayout());
        this.pnlPage.setMinimumSize(new Dimension(100, 100));
        this.pnlPage.setPreferredSize(new Dimension(100, 100));
        this.pnlPage.setLayout(new BorderLayout());
        this.jPanel4.setMinimumSize(new Dimension(100, 120));
        this.jPanel4.setPreferredSize(new Dimension(100, 120));
        this.jPanel4.setLayout(new GridBagLayout());
        this.pnlLinks.setMinimumSize(new Dimension(5, 5));
        this.pnlLinks.setOpaque(false);
        this.pnlLinks.setPreferredSize(new Dimension(5, 5));
        this.pnlLinks.addMouseListener(new MouseAdapter(){

            @Override
            public void mousePressed(MouseEvent evt) {
                JRViewer400.this.pnlLinksMousePressed(evt);
            }

            @Override
            public void mouseReleased(MouseEvent evt) {
                JRViewer400.this.pnlLinksMouseReleased(evt);
            }
        });
        this.pnlLinks.addMouseMotionListener(new MouseMotionAdapter(){

            @Override
            public void mouseDragged(MouseEvent evt) {
                JRViewer400.this.pnlLinksMouseDragged(evt);
            }
        });
        this.pnlLinks.setLayout(null);
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.fill = 1;
        this.jPanel4.add((Component)this.pnlLinks, gridBagConstraints);
        this.jPanel5.setMinimumSize(new Dimension(5, 5));
        this.jPanel5.setPreferredSize(new Dimension(5, 5));
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = 3;
        this.jPanel4.add((Component)this.jPanel5, gridBagConstraints);
        this.jPanel6.setMinimumSize(new Dimension(5, 5));
        this.jPanel6.setPreferredSize(new Dimension(5, 5));
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        this.jPanel4.add((Component)this.jPanel6, gridBagConstraints);
        this.jPanel7.setMinimumSize(new Dimension(5, 5));
        this.jPanel7.setPreferredSize(new Dimension(5, 5));
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = 2;
        this.jPanel4.add((Component)this.jPanel7, gridBagConstraints);
        this.jPanel8.setMinimumSize(new Dimension(5, 5));
        this.jPanel8.setPreferredSize(new Dimension(5, 5));
        this.jLabel1.setText("jLabel1");
        this.jPanel8.add(this.jLabel1);
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        this.jPanel4.add((Component)this.jPanel8, gridBagConstraints);
        this.jPanel9.setMinimumSize(new Dimension(5, 5));
        this.jPanel9.setPreferredSize(new Dimension(5, 5));
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        this.jPanel4.add((Component)this.jPanel9, gridBagConstraints);
        this.lblPage.setFont(new Font("Agency FB", 0, 12));
        this.lblPage.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0)));
        this.lblPage.setOpaque(true);
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.fill = 1;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        this.jPanel4.add((Component)this.lblPage, gridBagConstraints);
        this.pnlPage.add((Component)this.jPanel4, "Center");
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.insets = new Insets(5, 5, 5, 5);
        this.pnlInScroll.add((Component)this.pnlPage, gridBagConstraints);
        this.scrollPane.setViewportView(this.pnlInScroll);
        this.pnlMain.add((Component)this.scrollPane, "Center");
        this.pnlTabs.addChangeListener(new ChangeListener(){

            @Override
            public void stateChanged(ChangeEvent evt) {
                JRViewer400.this.pnlTabsStateChanged(evt);
            }
        });
        this.pnlMain.add((Component)this.pnlTabs, "Center");
        this.add((Component)this.pnlMain, "Center");
        this.pnlStatus.setFont(new Font("Arial", 0, 12));
        this.pnlStatus.setPreferredSize(new Dimension(59, 20));
        this.pnlStatus.setLayout(new FlowLayout(1, 0, 0));
        this.lblStatus.setFont(new Font("Arial", 1, 12));
        this.lblStatus.setText("Page i of n");
        this.lblStatus.setMaximumSize(new Dimension(59, 18));
        this.pnlStatus.add(this.lblStatus);
        this.add((Component)this.pnlStatus, "South");
        this.addKeyListener(this.keyNavigationListener);
    }

    void txtGoToActionPerformed(ActionEvent evt) {
        try {
            int pageNumber = Integer.parseInt(this.txtGoTo.getText());
            if (pageNumber != this.pageIndex + 1 && pageNumber > 0 && pageNumber <= this.jasperPrint.getPages().size()) {
                this.setPageIndex(pageNumber - 1);
                this.refreshPage();
            }
        }
        catch (NumberFormatException numberFormatException) {
            // empty catch block
        }
    }

    void cmbZoomItemStateChanged(ItemEvent evt) {
        this.btnActualSize.setSelected(false);
        this.btnFitPage.setSelected(false);
        this.btnFitWidth.setSelected(false);
    }

    void pnlMainComponentResized(ComponentEvent evt) {
        if (this.btnFitPage.isSelected()) {
            this.fitPage();
            this.btnFitPage.setSelected(true);
        } else if (this.btnFitWidth.isSelected()) {
            this.setRealZoomRatio(((float)this.pnlInScroll.getVisibleRect().getWidth() - 20.0f) / (float)this.getPageFormat().getPageWidth().intValue());
            this.btnFitWidth.setSelected(true);
        }
    }

    void btnActualSizeActionPerformed(ActionEvent evt) {
        if (this.btnActualSize.isSelected()) {
            this.btnFitPage.setSelected(false);
            this.btnFitWidth.setSelected(false);
            this.cmbZoom.setSelectedIndex(-1);
            this.setZoomRatio(1.0f);
            this.btnActualSize.setSelected(true);
        }
    }

    void btnFitWidthActionPerformed(ActionEvent evt) {
        if (this.btnFitWidth.isSelected()) {
            this.btnActualSize.setSelected(false);
            this.btnFitPage.setSelected(false);
            this.cmbZoom.setSelectedIndex(-1);
            this.setRealZoomRatio(((float)this.pnlInScroll.getVisibleRect().getWidth() - 20.0f) / (float)this.getPageFormat().getPageWidth().intValue());
            this.btnFitWidth.setSelected(true);
        }
    }

    void btnFitPageActionPerformed(ActionEvent evt) {
        if (this.btnFitPage.isSelected()) {
            this.btnActualSize.setSelected(false);
            this.btnFitWidth.setSelected(false);
            this.cmbZoom.setSelectedIndex(-1);
            this.fitPage();
            this.btnFitPage.setSelected(true);
        }
    }

    void btnSaveActionPerformed(ActionEvent evt) {
        int retValue;
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setLocale(this.getLocale());
        fileChooser.updateUI();
        for (int i = 0; i < this.saveContributors.size(); ++i) {
            fileChooser.addChoosableFileFilter((FileFilter)this.saveContributors.get(i));
        }
        if (this.saveContributors.contains(this.lastSaveContributor)) {
            fileChooser.setFileFilter((FileFilter)this.lastSaveContributor);
        } else if (this.saveContributors.size() > 0) {
            fileChooser.setFileFilter((FileFilter)this.saveContributors.get(0));
        }
        if (this.lastFolder != null) {
            fileChooser.setCurrentDirectory(this.lastFolder);
        }
        if ((retValue = fileChooser.showSaveDialog(this)) == 0) {
            FileFilter fileFilter = fileChooser.getFileFilter();
            File file = fileChooser.getSelectedFile();
            this.lastFolder = file.getParentFile();
            JRSaveContributor contributor = null;
            if (fileFilter instanceof JRSaveContributor) {
                contributor = (JRSaveContributor)fileFilter;
            } else {
                int i = 0;
                while (contributor == null && i < this.saveContributors.size()) {
                    if ((contributor = this.saveContributors.get(i++)).accept(file)) continue;
                    contributor = null;
                }
                if (contributor == null) {
                    contributor = new JRPrintSaveContributor(this.jasperReportsContext, this.getLocale(), this.resourceBundle);
                }
            }
            this.lastSaveContributor = contributor;
            try {
                contributor.save(this.jasperPrint, file);
            }
            catch (JRException e) {
                if (log.isErrorEnabled()) {
                    log.error((Object)"Save error.", (Throwable)e);
                }
                JOptionPane.showMessageDialog(this, this.getBundleString("error.saving"));
            }
        }
    }

    void pnlLinksMouseDragged(MouseEvent evt) {
        Container container = this.pnlInScroll.getParent();
        if (container instanceof JViewport) {
            JViewport viewport = (JViewport)container;
            Point point = viewport.getViewPosition();
            int newX = point.x - (evt.getX() - this.downX);
            int newY = point.y - (evt.getY() - this.downY);
            int maxX = this.pnlInScroll.getWidth() - viewport.getWidth();
            int maxY = this.pnlInScroll.getHeight() - viewport.getHeight();
            if (newX < 0) {
                newX = 0;
            }
            if (newX > maxX) {
                newX = maxX;
            }
            if (newY < 0) {
                newY = 0;
            }
            if (newY > maxY) {
                newY = maxY;
            }
            viewport.setViewPosition(new Point(newX, newY));
        }
    }

    void pnlLinksMouseReleased(MouseEvent evt) {
        this.pnlLinks.setCursor(new Cursor(0));
    }

    void pnlLinksMousePressed(MouseEvent evt) {
        this.pnlLinks.setCursor(new Cursor(13));
        this.downX = evt.getX();
        this.downY = evt.getY();
    }

    void btnPrintActionPerformed(ActionEvent evt) {
        Thread thread = new Thread(new Runnable(){

            @Override
            public void run() {
                try {
                    JRViewer400.this.btnPrint.setEnabled(false);
                    JRViewer400.this.setCursor(Cursor.getPredefinedCursor(3));
                    JasperPrintManager.getInstance((JasperReportsContext)JRViewer400.this.jasperReportsContext).print(JRViewer400.this.jasperPrint, true);
                }
                catch (Exception ex) {
                    if (log.isErrorEnabled()) {
                        log.error((Object)"Print error.", (Throwable)ex);
                    }
                    JOptionPane.showMessageDialog(JRViewer400.this, JRViewer400.this.getBundleString("error.printing"));
                }
                finally {
                    JRViewer400.this.setCursor(Cursor.getPredefinedCursor(0));
                    JRViewer400.this.btnPrint.setEnabled(true);
                }
            }
        });
        thread.start();
    }

    void btnLastActionPerformed(ActionEvent evt) {
        this.setPageIndex(this.jasperPrint.getPages().size() - 1);
        this.refreshPage();
    }

    void btnNextActionPerformed(ActionEvent evt) {
        this.setPageIndex(this.pageIndex + 1);
        this.refreshPage();
    }

    void btnPreviousActionPerformed(ActionEvent evt) {
        this.setPageIndex(this.pageIndex - 1);
        this.refreshPage();
    }

    void btnFirstActionPerformed(ActionEvent evt) {
        this.setPageIndex(0);
        this.refreshPage();
    }

    void btnReloadActionPerformed(ActionEvent evt) {
        if (this.type == 1) {
            try {
                this.loadReport(this.reportFileName, this.isXML);
            }
            catch (JRException e) {
                if (log.isErrorEnabled()) {
                    log.error((Object)"Reload error.", (Throwable)e);
                }
                this.jasperPrint = null;
                this.refreshTabs();
                this.setPageIndex(0);
                this.refreshPage();
                JOptionPane.showMessageDialog(this, this.getBundleString("error.loading"));
            }
            this.forceRefresh();
        }
    }

    protected void forceRefresh() {
        this.zoom = 0.0f;
        this.realZoom = 0.0f;
        this.setZoomRatio(1.0f);
    }

    void btnZoomInActionPerformed(ActionEvent evt) {
        this.btnActualSize.setSelected(false);
        this.btnFitPage.setSelected(false);
        this.btnFitWidth.setSelected(false);
        int newZoomInt = (int)(100.0f * this.getZoomRatio());
        int index = Arrays.binarySearch(this.zooms, newZoomInt);
        if (index < 0) {
            this.setZoomRatio((float)this.zooms[-index - 1] / 100.0f);
        } else if (index < this.cmbZoom.getModel().getSize() - 1) {
            this.setZoomRatio((float)this.zooms[index + 1] / 100.0f);
        }
    }

    void btnZoomOutActionPerformed(ActionEvent evt) {
        this.btnActualSize.setSelected(false);
        this.btnFitPage.setSelected(false);
        this.btnFitWidth.setSelected(false);
        int newZoomInt = (int)(100.0f * this.getZoomRatio());
        int index = Arrays.binarySearch(this.zooms, newZoomInt);
        if (index > 0) {
            this.setZoomRatio((float)this.zooms[index - 1] / 100.0f);
        } else if (index < -1) {
            this.setZoomRatio((float)this.zooms[-index - 2] / 100.0f);
        }
    }

    void cmbZoomActionPerformed(ActionEvent evt) {
        float newZoom = this.getZoomRatio();
        if (newZoom < 0.5f) {
            newZoom = 0.5f;
        }
        if (newZoom > 10.0f) {
            newZoom = 10.0f;
        }
        this.setZoomRatio(newZoom);
    }

    private void pnlTabsStateChanged(ChangeEvent evt) {
        if (this.pnlTabsChangeListenerEnabled) {
            ((JPanel)this.pnlTabs.getSelectedComponent()).add(this.scrollPane);
            Integer pgIdx = 0;
            Integer partIndex = this.pnlTabs.getSelectedIndex();
            if (partIndex > 0) {
                PrintParts parts;
                PrintParts printParts = parts = this.jasperPrint == null ? null : this.jasperPrint.getParts();
                if (parts != null && parts.hasParts()) {
                    partIndex = parts.startsAtZero() ? partIndex : partIndex - 1;
                    pgIdx = parts.getStartPageIndex(partIndex.intValue());
                }
            }
            this.setPageIndex(pgIdx);
            this.refreshPage();
        }
    }

    void hyperlinkClicked(MouseEvent evt) {
        JPanel link = (JPanel)evt.getSource();
        JRPrintHyperlink element = this.linksMap.get(link);
        this.hyperlinkClicked(element);
    }

    protected void hyperlinkClicked(JRPrintHyperlink hyperlink) {
        try {
            JRHyperlinkListener listener = null;
            for (int i = 0; i < this.hyperlinkListeners.size(); ++i) {
                listener = this.hyperlinkListeners.get(i);
                listener.gotoHyperlink(hyperlink);
            }
        }
        catch (JRException e) {
            if (log.isErrorEnabled()) {
                log.error((Object)"Hyperlink click error.", (Throwable)e);
            }
            JOptionPane.showMessageDialog(this, this.getBundleString("error.hyperlink"));
        }
    }

    public int getPageIndex() {
        return this.pageIndex;
    }

    private void setPageIndex(int index) {
        if (this.jasperPrint != null && this.jasperPrint.getPages() != null && this.jasperPrint.getPages().size() > 0) {
            if (index >= 0 && index < this.jasperPrint.getPages().size()) {
                PrintParts parts;
                int partIndex;
                int tabIndex;
                this.pageIndex = index;
                this.pageError = false;
                this.btnFirst.setEnabled(this.pageIndex > 0);
                this.btnPrevious.setEnabled(this.pageIndex > 0);
                this.btnNext.setEnabled(this.pageIndex < this.jasperPrint.getPages().size() - 1);
                this.btnLast.setEnabled(this.pageIndex < this.jasperPrint.getPages().size() - 1);
                this.txtGoTo.setEnabled(this.btnFirst.isEnabled() || this.btnLast.isEnabled());
                this.txtGoTo.setText("" + (this.pageIndex + 1));
                this.lblStatus.setText(MessageFormat.format(this.getBundleString("page"), this.pageIndex + 1, this.jasperPrint.getPages().size()));
                if (this.jasperPrint.hasParts() && (tabIndex = (partIndex = (parts = this.jasperPrint.getParts()).getPartIndex(this.pageIndex)) - (parts.startsAtZero() ? 1 : 0)) < this.pnlTabs.getComponentCount()) {
                    this.pnlTabsChangeListenerEnabled = false;
                    this.pnlTabs.setSelectedIndex(tabIndex);
                    ((JPanel)this.pnlTabs.getSelectedComponent()).add(this.scrollPane);
                    this.pnlTabsChangeListenerEnabled = true;
                }
            }
        } else {
            this.btnFirst.setEnabled(false);
            this.btnPrevious.setEnabled(false);
            this.btnNext.setEnabled(false);
            this.btnLast.setEnabled(false);
            this.txtGoTo.setEnabled(false);
            this.txtGoTo.setText("");
            this.lblStatus.setText("");
        }
    }

    private PrintPageFormat getPageFormat() {
        return this.jasperPrint.getPageFormat(this.pageIndex);
    }

    protected void loadReport(String fileName, boolean isXmlReport) throws JRException {
        this.jasperPrint = isXmlReport ? JRPrintXmlLoader.loadFromFile((JasperReportsContext)this.jasperReportsContext, (String)fileName) : (JasperPrint)JRLoader.loadObjectFromFile((String)fileName);
        this.refreshTabs();
        this.type = 1;
        this.isXML = isXmlReport;
        this.reportFileName = fileName;
        SimpleFileResolver fileResolver = new SimpleFileResolver(Arrays.asList(new File(fileName).getParentFile(), new File(".")));
        fileResolver.setResolveAbsolutePath(true);
        if (this.localJasperReportsContext == null) {
            this.localJasperReportsContext = new LocalJasperReportsContext(this.jasperReportsContext);
            this.jasperReportsContext = this.localJasperReportsContext;
        }
        this.localJasperReportsContext.setFileResolver((FileResolver)fileResolver);
        this.btnReload.setEnabled(true);
        this.setPageIndex(0);
    }

    protected void loadReport(InputStream is, boolean isXmlReport) throws JRException {
        this.jasperPrint = isXmlReport ? JRPrintXmlLoader.load((JasperReportsContext)this.jasperReportsContext, (InputStream)is) : (JasperPrint)JRLoader.loadObject((InputStream)is);
        this.refreshTabs();
        this.type = 2;
        this.isXML = isXmlReport;
        this.btnReload.setEnabled(false);
        this.setPageIndex(0);
    }

    protected void loadReport(JasperPrint jrPrint) {
        this.jasperPrint = jrPrint;
        this.refreshTabs();
        this.type = 3;
        this.isXML = false;
        this.btnReload.setEnabled(false);
        this.setPageIndex(0);
    }

    protected void refreshTabs() {
        this.pnlTabsChangeListenerEnabled = false;
        this.pnlTabs.removeAll();
        this.pnlMain.removeAll();
        if (this.jasperPrint == null || !this.jasperPrint.hasParts()) {
            this.pnlMain.add((Component)this.scrollPane, "Center");
        } else {
            PrintParts parts = this.jasperPrint.getParts();
            if (!parts.startsAtZero()) {
                JPanel partTab = new JPanel();
                partTab.setLayout(new BorderLayout());
                partTab.setName(this.jasperPrint.getName());
                this.pnlTabs.add(partTab);
            }
            Iterator it = parts.partsIterator();
            while (it.hasNext()) {
                PrintPart part = (PrintPart)((Map.Entry)it.next()).getValue();
                JPanel partTab = new JPanel();
                partTab.setLayout(new BorderLayout());
                partTab.setName(part.getName());
                this.pnlTabs.add(partTab);
            }
            this.pnlMain.add((Component)this.pnlTabs, "Center");
        }
        this.pnlTabsChangeListenerEnabled = true;
    }

    protected void refreshPage() {
        long imageSize;
        if (this.jasperPrint == null || this.jasperPrint.getPages() == null || this.jasperPrint.getPages().size() == 0) {
            this.pnlPage.setVisible(false);
            this.btnSave.setEnabled(false);
            this.btnPrint.setEnabled(false);
            this.btnActualSize.setEnabled(false);
            this.btnFitPage.setEnabled(false);
            this.btnFitWidth.setEnabled(false);
            this.btnZoomIn.setEnabled(false);
            this.btnZoomOut.setEnabled(false);
            this.cmbZoom.setEnabled(false);
            if (this.jasperPrint != null) {
                JOptionPane.showMessageDialog(this, this.getBundleString("no.pages"));
            }
            return;
        }
        this.pnlPage.setVisible(true);
        this.btnSave.setEnabled(true);
        this.btnPrint.setEnabled(true);
        this.btnActualSize.setEnabled(true);
        this.btnFitPage.setEnabled(true);
        this.btnFitWidth.setEnabled(true);
        this.btnZoomIn.setEnabled(this.zoom < 10.0f);
        this.btnZoomOut.setEnabled(this.zoom > 0.5f);
        this.cmbZoom.setEnabled(true);
        PrintPageFormat pageFormat = this.getPageFormat();
        Dimension dim = new Dimension((int)((float)pageFormat.getPageWidth().intValue() * this.realZoom) + 8, (int)((float)pageFormat.getPageHeight().intValue() * this.realZoom) + 8);
        this.pnlPage.setMaximumSize(dim);
        this.pnlPage.setMinimumSize(dim);
        this.pnlPage.setPreferredSize(dim);
        long maxImageSize = JRPropertiesUtil.getInstance((JasperReportsContext)this.jasperReportsContext).getLongProperty(VIEWER_RENDER_BUFFER_MAX_SIZE);
        boolean renderImage = maxImageSize <= 0L ? false : (imageSize = (long)(((int)((float)pageFormat.getPageWidth().intValue() * this.realZoom) + 1) * ((int)((float)pageFormat.getPageHeight().intValue() * this.realZoom) + 1))) <= maxImageSize;
        ((PageRenderer)this.lblPage).setRenderImage(renderImage);
        if (renderImage) {
            this.setPageImage();
        }
        this.pnlLinks.removeAll();
        this.linksMap = new HashMap<JPanel, JRPrintHyperlink>();
        this.createHyperlinks();
        if (!renderImage) {
            this.lblPage.setIcon(null);
            this.pnlMain.validate();
            this.pnlMain.repaint();
        }
    }

    protected void setPageImage() {
        Image image;
        if (this.pageError) {
            image = this.getPageErrorImage();
        } else {
            try {
                image = JasperPrintManager.getInstance((JasperReportsContext)this.jasperReportsContext).printToImage(this.jasperPrint, this.pageIndex, this.realZoom);
            }
            catch (Exception e) {
                if (log.isErrorEnabled()) {
                    log.error((Object)"Print page to image error.", (Throwable)e);
                }
                this.pageError = true;
                image = this.getPageErrorImage();
                JOptionPane.showMessageDialog(this, ResourceBundle.getBundle("net/sf/jasperreports/view/viewer").getString("error.displaying"));
            }
        }
        ImageIcon imageIcon = new ImageIcon(image);
        this.lblPage.setIcon(imageIcon);
    }

    protected Image getPageErrorImage() {
        PrintPageFormat pageFormat = this.getPageFormat();
        BufferedImage image = new BufferedImage((int)((float)pageFormat.getPageWidth().intValue() * this.realZoom) + 1, (int)((float)pageFormat.getPageHeight().intValue() * this.realZoom) + 1, 1);
        Graphics2D grx = (Graphics2D)((Image)image).getGraphics();
        AffineTransform transform = new AffineTransform();
        transform.scale(this.realZoom, this.realZoom);
        grx.transform(transform);
        this.drawPageError(grx);
        return image;
    }

    protected void createHyperlinks() {
        List pages = this.jasperPrint.getPages();
        JRPrintPage page = (JRPrintPage)pages.get(this.pageIndex);
        this.createHyperlinks(page.getElements(), 0, 0);
    }

    protected void createHyperlinks(List<JRPrintElement> elements, int offsetX, int offsetY) {
        if (elements != null && elements.size() > 0) {
            for (JRPrintElement element : elements) {
                boolean hasTooltip;
                Renderable renderer;
                ImageMapRenderable imageMap = null;
                if (element instanceof JRPrintImage && (renderer = ((JRPrintImage)element).getRenderable()) instanceof ImageMapRenderable && !(imageMap = (ImageMapRenderable)renderer).hasImageAreaHyperlinks()) {
                    imageMap = null;
                }
                boolean hasImageMap = imageMap != null;
                JRPrintHyperlink hyperlink = null;
                if (element instanceof JRPrintHyperlink) {
                    hyperlink = (JRPrintHyperlink)element;
                }
                boolean hasHyperlink = !hasImageMap && hyperlink != null && hyperlink.getHyperlinkTypeValue() != HyperlinkTypeEnum.NONE;
                boolean bl = hasTooltip = hyperlink != null && hyperlink.getHyperlinkTooltip() != null;
                if (hasHyperlink || hasImageMap || hasTooltip) {
                    JPanel link;
                    if (hasImageMap) {
                        Rectangle renderingArea = new Rectangle(0, 0, element.getWidth(), element.getHeight());
                        link = new ImageMapPanel(renderingArea, imageMap);
                    } else {
                        link = new JPanel();
                        if (hasHyperlink) {
                            link.addMouseListener(this.mouseListener);
                        }
                    }
                    if (hasHyperlink) {
                        link.setCursor(new Cursor(12));
                    }
                    link.setLocation((int)((float)(element.getX() + offsetX) * this.realZoom), (int)((float)(element.getY() + offsetY) * this.realZoom));
                    link.setSize((int)((float)element.getWidth() * this.realZoom), (int)((float)element.getHeight() * this.realZoom));
                    link.setOpaque(false);
                    String toolTip = this.getHyperlinkTooltip(hyperlink);
                    if (toolTip == null && hasImageMap) {
                        toolTip = "";
                    }
                    link.setToolTipText(toolTip);
                    this.pnlLinks.add(link);
                    this.linksMap.put(link, hyperlink);
                }
                if (!(element instanceof JRPrintFrame)) continue;
                JRPrintFrame frame = (JRPrintFrame)element;
                int frameOffsetX = offsetX + frame.getX() + frame.getLineBox().getLeftPadding();
                int frameOffsetY = offsetY + frame.getY() + frame.getLineBox().getTopPadding();
                this.createHyperlinks(frame.getElements(), frameOffsetX, frameOffsetY);
            }
        }
    }

    protected String getHyperlinkTooltip(JRPrintHyperlink hyperlink) {
        String toolTip = hyperlink.getHyperlinkTooltip();
        if (toolTip == null) {
            toolTip = this.getFallbackTooltip(hyperlink);
        }
        return toolTip;
    }

    protected String getFallbackTooltip(JRPrintHyperlink hyperlink) {
        String toolTip = null;
        switch (hyperlink.getHyperlinkTypeValue()) {
            case REFERENCE: {
                toolTip = hyperlink.getHyperlinkReference();
                break;
            }
            case LOCAL_ANCHOR: {
                if (hyperlink.getHyperlinkAnchor() == null) break;
                toolTip = "#" + hyperlink.getHyperlinkAnchor();
                break;
            }
            case LOCAL_PAGE: {
                if (hyperlink.getHyperlinkPage() == null) break;
                toolTip = "#page " + hyperlink.getHyperlinkPage();
                break;
            }
            case REMOTE_ANCHOR: {
                toolTip = "";
                if (hyperlink.getHyperlinkReference() != null) {
                    toolTip = toolTip + hyperlink.getHyperlinkReference();
                }
                if (hyperlink.getHyperlinkAnchor() == null) break;
                toolTip = toolTip + "#" + hyperlink.getHyperlinkAnchor();
                break;
            }
            case REMOTE_PAGE: {
                toolTip = "";
                if (hyperlink.getHyperlinkReference() != null) {
                    toolTip = toolTip + hyperlink.getHyperlinkReference();
                }
                if (hyperlink.getHyperlinkPage() == null) break;
                toolTip = toolTip + "#page " + hyperlink.getHyperlinkPage();
                break;
            }
        }
        return toolTip;
    }

    private void emptyContainer(Container container) {
        Component[] components = container.getComponents();
        if (components != null) {
            for (int i = 0; i < components.length; ++i) {
                if (!(components[i] instanceof Container)) continue;
                this.emptyContainer((Container)components[i]);
            }
        }
        components = null;
        container.removeAll();
        container = null;
    }

    private float getZoomRatio() {
        float newZoom = this.zoom;
        try {
            newZoom = this.zoomDecimalFormat.parse(String.valueOf(this.cmbZoom.getEditor().getItem())).floatValue() / 100.0f;
        }
        catch (ParseException parseException) {
            // empty catch block
        }
        return newZoom;
    }

    public void setZoomRatio(float newZoom) {
        if (newZoom > 0.0f) {
            this.cmbZoom.getEditor().setItem(this.zoomDecimalFormat.format(newZoom * 100.0f) + "%");
            if (this.zoom != newZoom) {
                this.zoom = newZoom;
                this.realZoom = this.zoom * (float)this.screenResolution / 72.0f;
                this.refreshPage();
            }
        }
    }

    private void setRealZoomRatio(float newZoom) {
        if (newZoom > 0.0f && this.realZoom != newZoom) {
            this.zoom = newZoom * 72.0f / (float)this.screenResolution;
            this.realZoom = newZoom;
            this.cmbZoom.getEditor().setItem(this.zoomDecimalFormat.format(this.zoom * 100.0f) + "%");
            this.refreshPage();
        }
    }

    public void setFitWidthZoomRatio() {
        this.setRealZoomRatio(((float)this.pnlInScroll.getVisibleRect().getWidth() - 20.0f) / (float)this.getPageFormat().getPageWidth().intValue());
    }

    public void setFitPageZoomRatio() {
        this.setRealZoomRatio(((float)this.pnlInScroll.getVisibleRect().getHeight() - 20.0f) / (float)this.getPageFormat().getPageHeight().intValue());
    }

    protected JRGraphics2DExporter getGraphics2DExporter() throws JRException {
        return new JRGraphics2DExporter(this.jasperReportsContext);
    }

    protected void paintPage(Graphics2D grx) {
        if (this.pageError) {
            this.paintPageError(grx);
            return;
        }
        try {
            if (this.exporter == null) {
                this.exporter = this.getGraphics2DExporter();
            } else {
                this.exporter.reset();
            }
            this.exporter.setExporterInput((ExporterInput)new SimpleExporterInput(this.jasperPrint));
            SimpleGraphics2DExporterOutput output = new SimpleGraphics2DExporterOutput();
            output.setGraphics2D((Graphics2D)grx.create());
            this.exporter.setExporterOutput(output);
            SimpleGraphics2DReportConfiguration configuration = new SimpleGraphics2DReportConfiguration();
            configuration.setPageIndex(Integer.valueOf(this.pageIndex));
            configuration.setZoomRatio(Float.valueOf(this.realZoom));
            configuration.setOffsetX(Integer.valueOf(1));
            configuration.setOffsetY(Integer.valueOf(1));
            this.exporter.setConfiguration(configuration);
            this.exporter.exportReport();
        }
        catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error((Object)"Page paint error.", (Throwable)e);
            }
            this.pageError = true;
            this.paintPageError(grx);
            SwingUtilities.invokeLater(new Runnable(){

                @Override
                public void run() {
                    JOptionPane.showMessageDialog(JRViewer400.this, JRViewer400.this.getBundleString("error.displaying"));
                }
            });
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected void paintPageError(Graphics2D grx) {
        AffineTransform origTransform = grx.getTransform();
        AffineTransform transform = new AffineTransform();
        transform.translate(1.0, 1.0);
        transform.scale(this.realZoom, this.realZoom);
        grx.transform(transform);
        try {
            this.drawPageError(grx);
        }
        finally {
            grx.setTransform(origTransform);
        }
    }

    protected void drawPageError(Graphics grx) {
        PrintPageFormat pageFormat = this.getPageFormat();
        grx.setColor(Color.white);
        grx.fillRect(0, 0, pageFormat.getPageWidth() + 1, pageFormat.getPageHeight() + 1);
    }

    protected void keyNavigate(KeyEvent evt) {
        boolean refresh = true;
        switch (evt.getKeyCode()) {
            case 34: 
            case 40: {
                this.dnNavigate(evt);
                break;
            }
            case 33: 
            case 38: {
                this.upNavigate(evt);
                break;
            }
            case 36: {
                this.homeEndNavigate(0);
                break;
            }
            case 35: {
                this.homeEndNavigate(this.jasperPrint.getPages().size() - 1);
                break;
            }
            default: {
                refresh = false;
            }
        }
        if (refresh) {
            this.refreshPage();
        }
    }

    private void dnNavigate(KeyEvent evt) {
        int bottomPosition = this.scrollPane.getVerticalScrollBar().getValue();
        this.scrollPane.dispatchEvent(evt);
        if ((this.scrollPane.getViewport().getHeight() > this.pnlPage.getHeight() || this.scrollPane.getVerticalScrollBar().getValue() == bottomPosition) && this.pageIndex < this.jasperPrint.getPages().size() - 1) {
            this.setPageIndex(this.pageIndex + 1);
            if (this.scrollPane.isEnabled()) {
                this.scrollPane.getVerticalScrollBar().setValue(0);
            }
        }
    }

    private void upNavigate(KeyEvent evt) {
        if ((this.scrollPane.getViewport().getHeight() > this.pnlPage.getHeight() || this.scrollPane.getVerticalScrollBar().getValue() == 0) && this.pageIndex > 0) {
            this.setPageIndex(this.pageIndex - 1);
            if (this.scrollPane.isEnabled()) {
                this.scrollPane.getVerticalScrollBar().setValue(this.scrollPane.getVerticalScrollBar().getMaximum());
            }
        } else {
            this.scrollPane.dispatchEvent(evt);
        }
    }

    private void homeEndNavigate(int pageNumber) {
        this.setPageIndex(pageNumber);
        if (this.scrollPane.isEnabled()) {
            this.scrollPane.getVerticalScrollBar().setValue(0);
        }
    }

    private void fitPage() {
        float widthRatio;
        PrintPageFormat pageFormat = this.getPageFormat();
        float heightRatio = ((float)this.pnlInScroll.getVisibleRect().getHeight() - 20.0f) / (float)pageFormat.getPageHeight().intValue();
        this.setRealZoomRatio(heightRatio < (widthRatio = ((float)this.pnlInScroll.getVisibleRect().getWidth() - 20.0f) / (float)pageFormat.getPageWidth().intValue()) ? heightRatio : widthRatio);
    }

    class PageRenderer
    extends JLabel {
        private static final long serialVersionUID = 10200L;
        private boolean renderImage;
        JRViewer400 viewer = null;

        public PageRenderer(JRViewer400 viewer) {
            this.viewer = viewer;
        }

        @Override
        public void paintComponent(Graphics g) {
            if (this.isRenderImage()) {
                super.paintComponent(g);
            } else {
                this.viewer.paintPage((Graphics2D)g.create());
            }
        }

        public boolean isRenderImage() {
            return this.renderImage;
        }

        public void setRenderImage(boolean renderImage) {
            this.renderImage = renderImage;
        }
    }

    protected class ImageMapPanel
    extends JPanel
    implements MouseListener,
    MouseMotionListener {
        private static final long serialVersionUID = 10200L;
        protected final List<JRPrintImageAreaHyperlink> imageAreaHyperlinks;

        public ImageMapPanel(Rectangle renderingArea, ImageMapRenderable imageMap) {
            try {
                this.imageAreaHyperlinks = imageMap.getImageAreaHyperlinks((Rectangle2D)renderingArea);
            }
            catch (JRException e) {
                throw new JRRuntimeException((Throwable)e);
            }
            this.addMouseListener(this);
            this.addMouseMotionListener(this);
        }

        @Override
        public String getToolTipText(MouseEvent event) {
            String tooltip = null;
            JRPrintImageAreaHyperlink imageMapArea = this.getImageMapArea(event);
            if (imageMapArea != null) {
                tooltip = JRViewer400.this.getHyperlinkTooltip(imageMapArea.getHyperlink());
            }
            if (tooltip == null) {
                tooltip = super.getToolTipText(event);
            }
            return tooltip;
        }

        @Override
        public void mouseDragged(MouseEvent e) {
            JRViewer400.this.pnlLinksMouseDragged(e);
        }

        @Override
        public void mouseMoved(MouseEvent e) {
            JRPrintImageAreaHyperlink imageArea = this.getImageMapArea(e);
            if (imageArea != null && imageArea.getHyperlink().getHyperlinkTypeValue() != HyperlinkTypeEnum.NONE) {
                e.getComponent().setCursor(Cursor.getPredefinedCursor(12));
            } else {
                e.getComponent().setCursor(Cursor.getDefaultCursor());
            }
        }

        protected JRPrintImageAreaHyperlink getImageMapArea(MouseEvent e) {
            return this.getImageMapArea((int)((float)e.getX() / JRViewer400.this.realZoom), (int)((float)e.getY() / JRViewer400.this.realZoom));
        }

        protected JRPrintImageAreaHyperlink getImageMapArea(int x, int y) {
            JRPrintImageAreaHyperlink image = null;
            if (this.imageAreaHyperlinks != null) {
                ListIterator<JRPrintImageAreaHyperlink> it = this.imageAreaHyperlinks.listIterator(this.imageAreaHyperlinks.size());
                while (image == null && it.hasPrevious()) {
                    JRPrintImageAreaHyperlink area = it.previous();
                    if (!area.getArea().containsPoint(x, y)) continue;
                    image = area;
                }
            }
            return image;
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            JRPrintImageAreaHyperlink imageMapArea = this.getImageMapArea(e);
            if (imageMapArea != null) {
                JRViewer400.this.hyperlinkClicked(imageMapArea.getHyperlink());
            }
        }

        @Override
        public void mouseEntered(MouseEvent e) {
        }

        @Override
        public void mouseExited(MouseEvent e) {
        }

        @Override
        public void mousePressed(MouseEvent e) {
            e.getComponent().setCursor(Cursor.getPredefinedCursor(13));
            JRViewer400.this.pnlLinksMousePressed(e);
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            e.getComponent().setCursor(Cursor.getDefaultCursor());
            JRViewer400.this.pnlLinksMouseReleased(e);
        }
    }
}

