// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.apps.svgbrowser;

import java.net.MalformedURLException;
import javax.swing.filechooser.FileSystemView;
import java.io.FilenameFilter;
import java.awt.FileDialog;
import org.apache.batik.swing.svg.SVGFileFilter;
import java.awt.print.PrinterException;
import org.apache.batik.transcoder.print.PrintTranscoder;
import org.apache.batik.bridge.UpdateManager;
import org.w3c.dom.svg.SVGSVGElement;
import java.awt.EventQueue;
import org.apache.batik.dom.util.DOMUtilities;
import java.io.Writer;
import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.svg2svg.SVGTranscoder;
import java.io.OutputStreamWriter;
import java.awt.Graphics2D;
import java.awt.Color;
import org.apache.batik.transcoder.image.PNGTranscoder;
import org.apache.batik.transcoder.TranscoderOutput;
import java.io.OutputStream;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.awt.image.BufferedImage;
import org.apache.batik.transcoder.image.ImageTranscoder;
import java.awt.image.BufferedImageOp;
import org.apache.batik.transcoder.image.JPEGTranscoder;
import org.apache.batik.transcoder.image.TIFFTranscoder;
import javax.swing.JFileChooser;
import javax.swing.text.AttributeSet;
import org.apache.batik.xml.XMLUtilities;
import org.apache.batik.util.gui.xmleditor.XMLDocument;
import javax.swing.JScrollPane;
import java.awt.Font;
import org.apache.batik.util.gui.xmleditor.XMLTextEditor;
import javax.swing.JMenu;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Constructor;
import org.apache.batik.dom.util.HashTable;
import javax.swing.AbstractButton;
import org.apache.batik.dom.svg.SVGOMDocument;
import javax.swing.JRadioButtonMenuItem;
import org.apache.batik.dom.StyleSheetProcessingInstruction;
import javax.swing.ButtonGroup;
import javax.swing.JComponent;
import java.util.LinkedList;
import java.util.List;
import org.apache.batik.util.gui.resource.JComponentModifier;
import org.apache.batik.util.gui.MemoryMonitor;
import java.awt.Rectangle;
import org.w3c.dom.Node;
import org.apache.batik.swing.gvt.Overlay;
import org.apache.batik.bridge.NoLoadExternalResourceSecurity;
import org.apache.batik.bridge.EmbededExternalResourceSecurity;
import org.apache.batik.bridge.DefaultExternalResourceSecurity;
import org.apache.batik.bridge.RelaxedExternalResourceSecurity;
import org.apache.batik.bridge.ExternalResourceSecurity;
import org.apache.batik.bridge.EmbededScriptSecurity;
import org.apache.batik.bridge.DefaultScriptSecurity;
import org.apache.batik.bridge.RelaxedScriptSecurity;
import org.apache.batik.bridge.NoLoadScriptSecurity;
import org.apache.batik.bridge.ScriptSecurity;
import org.w3c.dom.Element;
import org.apache.batik.util.gui.JErrorPane;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;
import java.util.Locale;
import org.apache.batik.bridge.UpdateManagerEvent;
import org.apache.batik.swing.svg.LinkActivationEvent;
import org.apache.batik.swing.gvt.GVTTreeRendererEvent;
import org.apache.batik.swing.svg.SVGLoadEventDispatcherEvent;
import org.apache.batik.swing.svg.GVTTreeBuilderEvent;
import org.w3c.dom.Document;
import org.w3c.dom.css.ViewCSS;
import org.apache.batik.swing.svg.SVGDocumentLoaderEvent;
import org.apache.batik.util.gui.resource.MissingListenerException;
import org.apache.batik.util.Service;
import java.util.Iterator;
import org.apache.batik.swing.JSVGCanvas;
import javax.swing.JToolBar;
import javax.swing.JMenuBar;
import javax.swing.InputMap;
import java.awt.event.ActionListener;
import org.apache.batik.util.ParsedURL;
import java.io.IOException;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import java.awt.event.ComponentListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentAdapter;
import java.awt.event.MouseListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseMotionListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.geom.Point2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.MissingResourceException;
import javax.swing.BorderFactory;
import javax.swing.JSeparator;
import java.awt.Component;
import org.apache.batik.util.gui.resource.ToolBarFactory;
import java.awt.LayoutManager;
import java.awt.BorderLayout;
import org.apache.batik.util.gui.resource.MenuFactory;
import javax.swing.KeyStroke;
import javax.swing.Action;
import java.awt.Frame;
import org.apache.batik.util.Platform;
import java.awt.Toolkit;
import java.awt.Dimension;
import java.awt.event.WindowListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowAdapter;
import java.util.HashMap;
import java.util.Map;
import org.apache.batik.util.gui.LocationBar;
import org.apache.batik.ext.swing.JAffineTransformChooser;
import org.apache.batik.util.gui.URIChooser;
import org.w3c.dom.svg.SVGDocument;
import org.apache.batik.swing.svg.SVGUserAgent;
import java.io.File;
import javax.swing.JWindow;
import javax.swing.JPanel;
import org.apache.batik.util.resources.ResourceManager;
import java.util.ResourceBundle;
import java.util.Vector;
import java.awt.Cursor;
import org.apache.batik.bridge.UpdateManagerListener;
import org.apache.batik.swing.svg.LinkActivationListener;
import org.apache.batik.swing.gvt.GVTTreeRendererListener;
import org.apache.batik.swing.svg.SVGLoadEventDispatcherListener;
import org.apache.batik.swing.svg.GVTTreeBuilderListener;
import org.apache.batik.swing.svg.SVGDocumentLoaderListener;
import org.apache.batik.util.gui.resource.ActionMap;
import javax.swing.JFrame;

public class JSVGViewerFrame extends JFrame implements ActionMap, SVGDocumentLoaderListener, GVTTreeBuilderListener, SVGLoadEventDispatcherListener, GVTTreeRendererListener, LinkActivationListener, UpdateManagerListener
{
    private static String EOL;
    protected static boolean priorJDK1_4;
    protected static final String JDK_1_4_PRESENCE_TEST_CLASS = "java.util.logging.LoggingPermission";
    public static final String RESOURCES = "org.apache.batik.apps.svgbrowser.resources.GUI";
    public static final String ABOUT_ACTION = "AboutAction";
    public static final String OPEN_ACTION = "OpenAction";
    public static final String OPEN_LOCATION_ACTION = "OpenLocationAction";
    public static final String NEW_WINDOW_ACTION = "NewWindowAction";
    public static final String RELOAD_ACTION = "ReloadAction";
    public static final String SAVE_AS_ACTION = "SaveAsAction";
    public static final String BACK_ACTION = "BackAction";
    public static final String FORWARD_ACTION = "ForwardAction";
    public static final String FULL_SCREEN_ACTION = "FullScreenAction";
    public static final String PRINT_ACTION = "PrintAction";
    public static final String EXPORT_AS_JPG_ACTION = "ExportAsJPGAction";
    public static final String EXPORT_AS_PNG_ACTION = "ExportAsPNGAction";
    public static final String EXPORT_AS_TIFF_ACTION = "ExportAsTIFFAction";
    public static final String PREFERENCES_ACTION = "PreferencesAction";
    public static final String CLOSE_ACTION = "CloseAction";
    public static final String VIEW_SOURCE_ACTION = "ViewSourceAction";
    public static final String EXIT_ACTION = "ExitAction";
    public static final String RESET_TRANSFORM_ACTION = "ResetTransformAction";
    public static final String ZOOM_IN_ACTION = "ZoomInAction";
    public static final String ZOOM_OUT_ACTION = "ZoomOutAction";
    public static final String PREVIOUS_TRANSFORM_ACTION = "PreviousTransformAction";
    public static final String NEXT_TRANSFORM_ACTION = "NextTransformAction";
    public static final String USE_STYLESHEET_ACTION = "UseStylesheetAction";
    public static final String PLAY_ACTION = "PlayAction";
    public static final String PAUSE_ACTION = "PauseAction";
    public static final String STOP_ACTION = "StopAction";
    public static final String MONITOR_ACTION = "MonitorAction";
    public static final String DOM_VIEWER_ACTION = "DOMViewerAction";
    public static final String SET_TRANSFORM_ACTION = "SetTransformAction";
    public static final String FIND_DIALOG_ACTION = "FindDialogAction";
    public static final String THUMBNAIL_DIALOG_ACTION = "ThumbnailDialogAction";
    public static final String FLUSH_ACTION = "FlushAction";
    public static final String TOGGLE_DEBUGGER_ACTION = "ToggleDebuggerAction";
    public static final Cursor WAIT_CURSOR;
    public static final Cursor DEFAULT_CURSOR;
    public static final String PROPERTY_OS_NAME;
    public static final String PROPERTY_OS_NAME_DEFAULT;
    public static final String PROPERTY_OS_WINDOWS_PREFIX;
    protected static final String OPEN_TITLE = "Open.title";
    protected static Vector handlers;
    protected static SquiggleInputHandler defaultHandler;
    protected static ResourceBundle bundle;
    protected static ResourceManager resources;
    protected Application application;
    protected Canvas svgCanvas;
    protected JPanel svgCanvasPanel;
    protected JWindow window;
    protected static JFrame memoryMonitorFrame;
    protected File currentPath;
    protected File currentSavePath;
    protected BackAction backAction;
    protected ForwardAction forwardAction;
    protected PlayAction playAction;
    protected PauseAction pauseAction;
    protected StopAction stopAction;
    protected PreviousTransformAction previousTransformAction;
    protected NextTransformAction nextTransformAction;
    protected UseStylesheetAction useStylesheetAction;
    protected boolean debug;
    protected boolean autoAdjust;
    protected boolean managerStopped;
    protected SVGUserAgent userAgent;
    protected SVGDocument svgDocument;
    protected URIChooser uriChooser;
    protected DOMViewer domViewer;
    protected FindDialog findDialog;
    protected ThumbnailDialog thumbnailDialog;
    protected JAffineTransformChooser.Dialog transformDialog;
    protected LocationBar locationBar;
    protected StatusBar statusBar;
    protected String title;
    protected LocalHistory localHistory;
    protected TransformHistory transformHistory;
    protected String alternateStyleSheet;
    protected Debugger debugger;
    protected Map listeners;
    long time;
    
    public JSVGViewerFrame(final Application application) {
        this.currentPath = new File("");
        this.currentSavePath = new File("");
        this.backAction = new BackAction();
        this.forwardAction = new ForwardAction();
        this.playAction = new PlayAction();
        this.pauseAction = new PauseAction();
        this.stopAction = new StopAction();
        this.previousTransformAction = new PreviousTransformAction();
        this.nextTransformAction = new NextTransformAction();
        this.useStylesheetAction = new UseStylesheetAction();
        this.autoAdjust = true;
        this.userAgent = new UserAgent();
        this.transformHistory = new TransformHistory();
        this.listeners = new HashMap();
        this.application = application;
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(final WindowEvent windowEvent) {
                JSVGViewerFrame.this.application.closeJSVGViewerFrame(JSVGViewerFrame.this);
            }
        });
        this.svgCanvas = new Canvas(this.userAgent, true, true) {
            Dimension screenSize;
            
            {
                this.setMaximumSize(this.screenSize = Toolkit.getDefaultToolkit().getScreenSize());
            }
            
            public Dimension getPreferredSize() {
                final Dimension preferredSize = super.getPreferredSize();
                if (preferredSize.width > this.screenSize.width) {
                    preferredSize.width = this.screenSize.width;
                }
                if (preferredSize.height > this.screenSize.height) {
                    preferredSize.height = this.screenSize.height;
                }
                return preferredSize;
            }
            
            public void setMySize(final Dimension preferredSize) {
                this.setPreferredSize(preferredSize);
                this.invalidate();
                if (JSVGViewerFrame.this.autoAdjust) {
                    Platform.unmaximize(JSVGViewerFrame.this);
                    JSVGViewerFrame.this.pack();
                }
            }
            
            public void setDisableInteractions(final boolean disableInteractions) {
                super.setDisableInteractions(disableInteractions);
                JSVGViewerFrame.this.listeners.get("SetTransformAction").setEnabled(!disableInteractions);
                if (JSVGViewerFrame.this.thumbnailDialog != null) {
                    JSVGViewerFrame.this.thumbnailDialog.setInteractionEnabled(!disableInteractions);
                }
            }
        };
        this.svgCanvas.getActionMap().put("FullScreenAction", new FullScreenAction());
        final InputMap inputMap = this.svgCanvas.getInputMap(0);
        inputMap.put(KeyStroke.getKeyStroke(122, 0), "FullScreenAction");
        this.svgCanvas.setDoubleBufferedRendering(true);
        this.listeners.put("AboutAction", new AboutAction());
        this.listeners.put("OpenAction", new OpenAction());
        this.listeners.put("OpenLocationAction", new OpenLocationAction());
        this.listeners.put("NewWindowAction", new NewWindowAction());
        this.listeners.put("ReloadAction", new ReloadAction());
        this.listeners.put("SaveAsAction", new SaveAsAction());
        this.listeners.put("BackAction", this.backAction);
        this.listeners.put("ForwardAction", this.forwardAction);
        this.listeners.put("PrintAction", new PrintAction());
        this.listeners.put("ExportAsJPGAction", new ExportAsJPGAction());
        this.listeners.put("ExportAsPNGAction", new ExportAsPNGAction());
        this.listeners.put("ExportAsTIFFAction", new ExportAsTIFFAction());
        this.listeners.put("PreferencesAction", new PreferencesAction());
        this.listeners.put("CloseAction", new CloseAction());
        this.listeners.put("ExitAction", this.application.createExitAction(this));
        this.listeners.put("ViewSourceAction", new ViewSourceAction());
        final javax.swing.ActionMap actionMap = this.svgCanvas.getActionMap();
        this.listeners.put("ResetTransformAction", actionMap.get("ResetTransform"));
        this.listeners.put("ZoomInAction", actionMap.get("ZoomIn"));
        this.listeners.put("ZoomOutAction", actionMap.get("ZoomOut"));
        this.listeners.put("PreviousTransformAction", this.previousTransformAction);
        inputMap.put(KeyStroke.getKeyStroke(75, 2), this.previousTransformAction);
        this.listeners.put("NextTransformAction", this.nextTransformAction);
        inputMap.put(KeyStroke.getKeyStroke(76, 2), this.nextTransformAction);
        this.listeners.put("UseStylesheetAction", this.useStylesheetAction);
        this.listeners.put("PlayAction", this.playAction);
        this.listeners.put("PauseAction", this.pauseAction);
        this.listeners.put("StopAction", this.stopAction);
        this.listeners.put("MonitorAction", new MonitorAction());
        this.listeners.put("DOMViewerAction", new DOMViewerAction());
        this.listeners.put("SetTransformAction", new SetTransformAction());
        this.listeners.put("FindDialogAction", new FindDialogAction());
        this.listeners.put("ThumbnailDialogAction", new ThumbnailDialogAction());
        this.listeners.put("FlushAction", new FlushAction());
        this.listeners.put("ToggleDebuggerAction", new ToggleDebuggerAction());
        try {
            final JMenuBar jMenuBar = new MenuFactory(JSVGViewerFrame.bundle, this).createJMenuBar("MenuBar", this.application.getUISpecialization());
            this.setJMenuBar(jMenuBar);
            this.localHistory = new LocalHistory(jMenuBar, this);
            final String[] visitedURIs = this.application.getVisitedURIs();
            for (int i = 0; i < visitedURIs.length; ++i) {
                if (visitedURIs[i] != null && !"".equals(visitedURIs[i])) {
                    this.localHistory.update(visitedURIs[i]);
                }
            }
            final JPanel comp = new JPanel(new BorderLayout());
            final JToolBar jToolBar = new ToolBarFactory(JSVGViewerFrame.bundle, this).createJToolBar("ToolBar");
            jToolBar.setFloatable(false);
            this.getContentPane().add(comp, "North");
            comp.add(jToolBar, "North");
            comp.add(new JSeparator(), "Center");
            comp.add(this.locationBar = new LocationBar(), "South");
            this.locationBar.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
        }
        catch (MissingResourceException ex) {
            System.out.println(ex.getMessage());
            System.exit(0);
        }
        (this.svgCanvasPanel = new JPanel(new BorderLayout())).setBorder(BorderFactory.createEtchedBorder());
        this.svgCanvasPanel.add(this.svgCanvas, "Center");
        final JPanel comp2 = new JPanel(new BorderLayout());
        comp2.add(this.svgCanvasPanel, "Center");
        comp2.add(this.statusBar = new StatusBar(), "South");
        this.getContentPane().add(comp2, "Center");
        this.svgCanvas.addSVGDocumentLoaderListener(this);
        this.svgCanvas.addGVTTreeBuilderListener(this);
        this.svgCanvas.addSVGLoadEventDispatcherListener(this);
        this.svgCanvas.addGVTTreeRendererListener(this);
        this.svgCanvas.addLinkActivationListener(this);
        this.svgCanvas.addUpdateManagerListener(this);
        this.svgCanvas.addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseMoved(final MouseEvent mouseEvent) {
                if (JSVGViewerFrame.this.svgDocument == null) {
                    JSVGViewerFrame.this.statusBar.setXPosition((float)mouseEvent.getX());
                    JSVGViewerFrame.this.statusBar.setYPosition((float)mouseEvent.getY());
                }
                else {
                    try {
                        final AffineTransform viewBoxTransform = JSVGViewerFrame.this.svgCanvas.getViewBoxTransform();
                        if (viewBoxTransform != null) {
                            final Point2D transform = viewBoxTransform.createInverse().transform(new Point2D.Float((float)mouseEvent.getX(), (float)mouseEvent.getY()), null);
                            JSVGViewerFrame.this.statusBar.setXPosition((float)transform.getX());
                            JSVGViewerFrame.this.statusBar.setYPosition((float)transform.getY());
                            return;
                        }
                    }
                    catch (NoninvertibleTransformException ex) {}
                    JSVGViewerFrame.this.statusBar.setXPosition((float)mouseEvent.getX());
                    JSVGViewerFrame.this.statusBar.setYPosition((float)mouseEvent.getY());
                }
            }
        });
        this.svgCanvas.addMouseListener(new MouseAdapter() {
            public void mouseExited(final MouseEvent mouseEvent) {
                final Dimension size = JSVGViewerFrame.this.svgCanvas.getSize();
                if (JSVGViewerFrame.this.svgDocument == null) {
                    JSVGViewerFrame.this.statusBar.setWidth((float)size.width);
                    JSVGViewerFrame.this.statusBar.setHeight((float)size.height);
                }
                else {
                    try {
                        final AffineTransform viewBoxTransform = JSVGViewerFrame.this.svgCanvas.getViewBoxTransform();
                        if (viewBoxTransform != null) {
                            final AffineTransform inverse = viewBoxTransform.createInverse();
                            final Point2D transform = inverse.transform(new Point2D.Float(0.0f, 0.0f), null);
                            final Point2D transform2 = inverse.transform(new Point2D.Float((float)size.width, (float)size.height), null);
                            JSVGViewerFrame.this.statusBar.setWidth((float)(transform2.getX() - transform.getX()));
                            JSVGViewerFrame.this.statusBar.setHeight((float)(transform2.getY() - transform.getY()));
                            return;
                        }
                    }
                    catch (NoninvertibleTransformException ex) {}
                    JSVGViewerFrame.this.statusBar.setWidth((float)size.width);
                    JSVGViewerFrame.this.statusBar.setHeight((float)size.height);
                }
            }
        });
        this.svgCanvas.addComponentListener(new ComponentAdapter() {
            public void componentResized(final ComponentEvent componentEvent) {
                final Dimension size = JSVGViewerFrame.this.svgCanvas.getSize();
                if (JSVGViewerFrame.this.svgDocument == null) {
                    JSVGViewerFrame.this.statusBar.setWidth((float)size.width);
                    JSVGViewerFrame.this.statusBar.setHeight((float)size.height);
                }
                else {
                    try {
                        final AffineTransform viewBoxTransform = JSVGViewerFrame.this.svgCanvas.getViewBoxTransform();
                        if (viewBoxTransform != null) {
                            final AffineTransform inverse = viewBoxTransform.createInverse();
                            final Point2D transform = inverse.transform(new Point2D.Float(0.0f, 0.0f), null);
                            final Point2D transform2 = inverse.transform(new Point2D.Float((float)size.width, (float)size.height), null);
                            JSVGViewerFrame.this.statusBar.setWidth((float)(transform2.getX() - transform.getX()));
                            JSVGViewerFrame.this.statusBar.setHeight((float)(transform2.getY() - transform.getY()));
                            return;
                        }
                    }
                    catch (NoninvertibleTransformException ex) {}
                    JSVGViewerFrame.this.statusBar.setWidth((float)size.width);
                    JSVGViewerFrame.this.statusBar.setHeight((float)size.height);
                }
            }
        });
        this.locationBar.addActionListener(new AbstractAction() {
            public void actionPerformed(final ActionEvent actionEvent) {
                String text = JSVGViewerFrame.this.locationBar.getText().trim();
                final int index = text.indexOf(35);
                String substring = "";
                if (index != -1) {
                    substring = text.substring(index + 1);
                    text = text.substring(0, index);
                }
                if (text.equals("")) {
                    return;
                }
                try {
                    final File file = new File(text);
                    if (file.exists()) {
                        if (file.isDirectory()) {
                            return;
                        }
                        try {
                            text = file.getCanonicalPath();
                            if (text.startsWith("/")) {
                                text = "file:" + text;
                            }
                            else {
                                text = "file:/" + text;
                            }
                        }
                        catch (IOException ex) {}
                    }
                }
                catch (SecurityException ex2) {}
                final String fragmentIdentifier = JSVGViewerFrame.this.svgCanvas.getFragmentIdentifier();
                if (JSVGViewerFrame.this.svgDocument != null) {
                    final ParsedURL parsedURL = new ParsedURL(JSVGViewerFrame.this.svgDocument.getURL());
                    final ParsedURL parsedURL2 = new ParsedURL(parsedURL, text);
                    final String anObject = (fragmentIdentifier == null) ? "" : fragmentIdentifier;
                    if (parsedURL.equals(parsedURL2) && substring.equals(anObject)) {
                        return;
                    }
                }
                if (substring.length() != 0) {
                    text = text + '#' + substring;
                }
                JSVGViewerFrame.this.locationBar.setText(text);
                JSVGViewerFrame.this.locationBar.addToHistory(text);
                JSVGViewerFrame.this.showSVGDocument(text);
            }
        });
    }
    
    public void dispose() {
        this.hideDebugger();
        this.svgCanvas.dispose();
        super.dispose();
    }
    
    public void setDebug(final boolean debug) {
        this.debug = debug;
    }
    
    public void setAutoAdjust(final boolean autoAdjust) {
        this.autoAdjust = autoAdjust;
    }
    
    public JSVGCanvas getJSVGCanvas() {
        return this.svgCanvas;
    }
    
    private static File makeAbsolute(final File file) {
        if (!file.isAbsolute()) {
            return file.getAbsoluteFile();
        }
        return file;
    }
    
    public void showDebugger() {
        if (this.debugger == null && Debugger.isPresent) {
            (this.debugger = new Debugger(this, this.locationBar.getText())).initialize();
        }
    }
    
    public void hideDebugger() {
        if (this.debugger != null) {
            this.debugger.clearAllBreakpoints();
            this.debugger.go();
            this.debugger.dispose();
            this.debugger = null;
        }
    }
    
    public void showSVGDocument(final String s) {
        try {
            final ParsedURL parsedURL = new ParsedURL(s);
            this.getInputHandler(parsedURL).handle(parsedURL, this);
        }
        catch (Exception ex) {
            if (this.userAgent != null) {
                this.userAgent.displayError(ex);
            }
        }
    }
    
    public SquiggleInputHandler getInputHandler(final ParsedURL parsedURL) throws IOException {
        final Iterator<SquiggleInputHandler> iterator = getHandlers().iterator();
        SquiggleInputHandler defaultHandler = null;
        while (iterator.hasNext()) {
            final SquiggleInputHandler squiggleInputHandler = iterator.next();
            if (squiggleInputHandler.accept(parsedURL)) {
                defaultHandler = squiggleInputHandler;
                break;
            }
        }
        if (defaultHandler == null) {
            defaultHandler = JSVGViewerFrame.defaultHandler;
        }
        return defaultHandler;
    }
    
    protected static Vector getHandlers() {
        if (JSVGViewerFrame.handlers != null) {
            return JSVGViewerFrame.handlers;
        }
        JSVGViewerFrame.handlers = new Vector();
        registerHandler(new SVGInputHandler());
        final Iterator providers = Service.providers(SquiggleInputHandler.class);
        while (providers.hasNext()) {
            registerHandler(providers.next());
        }
        return JSVGViewerFrame.handlers;
    }
    
    public static synchronized void registerHandler(final SquiggleInputHandler obj) {
        getHandlers().addElement(obj);
    }
    
    public Action getAction(final String s) throws MissingListenerException {
        final Action action = this.listeners.get(s);
        if (action == null) {
            throw new MissingListenerException("Can't find action.", "org.apache.batik.apps.svgbrowser.resources.GUI", s);
        }
        return action;
    }
    
    public void documentLoadingStarted(final SVGDocumentLoaderEvent svgDocumentLoaderEvent) {
        final String string = JSVGViewerFrame.resources.getString("Message.documentLoad");
        if (this.debug) {
            System.out.println(string);
            this.time = System.currentTimeMillis();
        }
        this.statusBar.setMainMessage(string);
        this.stopAction.update(true);
        this.svgCanvas.setCursor(JSVGViewerFrame.WAIT_CURSOR);
    }
    
    public void documentLoadingCompleted(final SVGDocumentLoaderEvent svgDocumentLoaderEvent) {
        if (this.debug) {
            System.out.print(JSVGViewerFrame.resources.getString("Message.documentLoadTime"));
            System.out.println(System.currentTimeMillis() - this.time + " ms");
        }
        this.setSVGDocument(svgDocumentLoaderEvent.getSVGDocument(), svgDocumentLoaderEvent.getSVGDocument().getURL(), svgDocumentLoaderEvent.getSVGDocument().getTitle());
    }
    
    public void setSVGDocument(final SVGDocument svgDocument, final String str, final String str2) {
        this.svgDocument = svgDocument;
        if (this.domViewer != null) {
            if (this.domViewer.isVisible() && svgDocument != null) {
                this.domViewer.setDocument((Document)svgDocument, (ViewCSS)svgDocument.getDocumentElement());
            }
            else {
                this.domViewer.dispose();
                this.domViewer = null;
            }
        }
        this.stopAction.update(false);
        this.svgCanvas.setCursor(JSVGViewerFrame.DEFAULT_CURSOR);
        this.locationBar.setText(str);
        if (this.debugger != null) {
            this.debugger.detach();
            this.debugger.setDocumentURL(str);
        }
        if (this.title == null) {
            this.title = this.getTitle();
        }
        if (str2.length() != 0) {
            this.setTitle(this.title + ": " + str2);
        }
        else {
            int n = str.lastIndexOf("/");
            if (n == -1) {
                n = str.lastIndexOf("\\");
            }
            if (n == -1) {
                this.setTitle(this.title + ": " + str);
            }
            else {
                this.setTitle(this.title + ": " + str.substring(n + 1));
            }
        }
        this.localHistory.update(str);
        this.application.addVisitedURI(str);
        this.backAction.update();
        this.forwardAction.update();
        this.transformHistory = new TransformHistory();
        this.previousTransformAction.update();
        this.nextTransformAction.update();
        this.useStylesheetAction.update();
    }
    
    public void documentLoadingCancelled(final SVGDocumentLoaderEvent svgDocumentLoaderEvent) {
        final String string = JSVGViewerFrame.resources.getString("Message.documentCancelled");
        if (this.debug) {
            System.out.println(string);
        }
        this.statusBar.setMainMessage("");
        this.statusBar.setMessage(string);
        this.stopAction.update(false);
        this.svgCanvas.setCursor(JSVGViewerFrame.DEFAULT_CURSOR);
    }
    
    public void documentLoadingFailed(final SVGDocumentLoaderEvent svgDocumentLoaderEvent) {
        final String string = JSVGViewerFrame.resources.getString("Message.documentFailed");
        if (this.debug) {
            System.out.println(string);
        }
        this.statusBar.setMainMessage("");
        this.statusBar.setMessage(string);
        this.stopAction.update(false);
        this.svgCanvas.setCursor(JSVGViewerFrame.DEFAULT_CURSOR);
    }
    
    public void gvtBuildStarted(final GVTTreeBuilderEvent gvtTreeBuilderEvent) {
        final String string = JSVGViewerFrame.resources.getString("Message.treeBuild");
        if (this.debug) {
            System.out.println(string);
            this.time = System.currentTimeMillis();
        }
        this.statusBar.setMainMessage(string);
        this.stopAction.update(true);
        this.svgCanvas.setCursor(JSVGViewerFrame.WAIT_CURSOR);
    }
    
    public void gvtBuildCompleted(final GVTTreeBuilderEvent gvtTreeBuilderEvent) {
        if (this.debug) {
            System.out.print(JSVGViewerFrame.resources.getString("Message.treeBuildTime"));
            System.out.println(System.currentTimeMillis() - this.time + " ms");
        }
        if (this.findDialog != null) {
            if (this.findDialog.isVisible()) {
                this.findDialog.setGraphicsNode(this.svgCanvas.getGraphicsNode());
            }
            else {
                this.findDialog.dispose();
                this.findDialog = null;
            }
        }
        this.stopAction.update(false);
        this.svgCanvas.setCursor(JSVGViewerFrame.DEFAULT_CURSOR);
        this.svgCanvas.setSelectionOverlayXORMode(this.application.isSelectionOverlayXORMode());
        this.svgCanvas.requestFocus();
        if (this.debugger != null) {
            this.debugger.attach();
        }
    }
    
    public void gvtBuildCancelled(final GVTTreeBuilderEvent gvtTreeBuilderEvent) {
        final String string = JSVGViewerFrame.resources.getString("Message.treeCancelled");
        if (this.debug) {
            System.out.println(string);
        }
        this.statusBar.setMainMessage("");
        this.statusBar.setMessage(string);
        this.stopAction.update(false);
        this.svgCanvas.setCursor(JSVGViewerFrame.DEFAULT_CURSOR);
        this.svgCanvas.setSelectionOverlayXORMode(this.application.isSelectionOverlayXORMode());
    }
    
    public void gvtBuildFailed(final GVTTreeBuilderEvent gvtTreeBuilderEvent) {
        final String string = JSVGViewerFrame.resources.getString("Message.treeFailed");
        if (this.debug) {
            System.out.println(string);
        }
        this.statusBar.setMainMessage("");
        this.statusBar.setMessage(string);
        this.stopAction.update(false);
        this.svgCanvas.setCursor(JSVGViewerFrame.DEFAULT_CURSOR);
        this.svgCanvas.setSelectionOverlayXORMode(this.application.isSelectionOverlayXORMode());
        if (this.autoAdjust) {
            this.pack();
        }
    }
    
    public void svgLoadEventDispatchStarted(final SVGLoadEventDispatcherEvent svgLoadEventDispatcherEvent) {
        final String string = JSVGViewerFrame.resources.getString("Message.onload");
        if (this.debug) {
            System.out.println(string);
            this.time = System.currentTimeMillis();
        }
        this.stopAction.update(true);
        this.statusBar.setMainMessage(string);
    }
    
    public void svgLoadEventDispatchCompleted(final SVGLoadEventDispatcherEvent svgLoadEventDispatcherEvent) {
        if (this.debug) {
            System.out.print(JSVGViewerFrame.resources.getString("Message.onloadTime"));
            System.out.println(System.currentTimeMillis() - this.time + " ms");
        }
        this.stopAction.update(false);
        this.statusBar.setMainMessage("");
        this.statusBar.setMessage(JSVGViewerFrame.resources.getString("Message.done"));
    }
    
    public void svgLoadEventDispatchCancelled(final SVGLoadEventDispatcherEvent svgLoadEventDispatcherEvent) {
        final String string = JSVGViewerFrame.resources.getString("Message.onloadCancelled");
        if (this.debug) {
            System.out.println(string);
        }
        this.stopAction.update(false);
        this.statusBar.setMainMessage("");
        this.statusBar.setMessage(string);
    }
    
    public void svgLoadEventDispatchFailed(final SVGLoadEventDispatcherEvent svgLoadEventDispatcherEvent) {
        final String string = JSVGViewerFrame.resources.getString("Message.onloadFailed");
        if (this.debug) {
            System.out.println(string);
        }
        this.stopAction.update(false);
        this.statusBar.setMainMessage("");
        this.statusBar.setMessage(string);
    }
    
    public void gvtRenderingPrepare(final GVTTreeRendererEvent gvtTreeRendererEvent) {
        if (this.debug) {
            System.out.println(JSVGViewerFrame.resources.getString("Message.treeRenderingPrep"));
            this.time = System.currentTimeMillis();
        }
        this.stopAction.update(true);
        this.svgCanvas.setCursor(JSVGViewerFrame.WAIT_CURSOR);
        this.statusBar.setMainMessage(JSVGViewerFrame.resources.getString("Message.treeRendering"));
    }
    
    public void gvtRenderingStarted(final GVTTreeRendererEvent gvtTreeRendererEvent) {
        if (this.debug) {
            System.out.print(JSVGViewerFrame.resources.getString("Message.treeRenderingPrepTime"));
            System.out.println(System.currentTimeMillis() - this.time + " ms");
            this.time = System.currentTimeMillis();
            System.out.println(JSVGViewerFrame.resources.getString("Message.treeRenderingStart"));
        }
    }
    
    public void gvtRenderingCompleted(final GVTTreeRendererEvent gvtTreeRendererEvent) {
        if (this.debug) {
            System.out.print(JSVGViewerFrame.resources.getString("Message.treeRenderingTime"));
            System.out.println(System.currentTimeMillis() - this.time + " ms");
        }
        this.statusBar.setMainMessage("");
        this.statusBar.setMessage(JSVGViewerFrame.resources.getString("Message.done"));
        if (!this.svgCanvas.isDynamic() || this.managerStopped) {
            this.stopAction.update(false);
        }
        this.svgCanvas.setCursor(JSVGViewerFrame.DEFAULT_CURSOR);
        this.transformHistory.update(this.svgCanvas.getRenderingTransform());
        this.previousTransformAction.update();
        this.nextTransformAction.update();
    }
    
    public void gvtRenderingCancelled(final GVTTreeRendererEvent gvtTreeRendererEvent) {
        final String string = JSVGViewerFrame.resources.getString("Message.treeRenderingCancelled");
        if (this.debug) {
            System.out.println(string);
        }
        this.statusBar.setMainMessage("");
        this.statusBar.setMessage(string);
        if (!this.svgCanvas.isDynamic()) {
            this.stopAction.update(false);
        }
        this.svgCanvas.setCursor(JSVGViewerFrame.DEFAULT_CURSOR);
    }
    
    public void gvtRenderingFailed(final GVTTreeRendererEvent gvtTreeRendererEvent) {
        final String string = JSVGViewerFrame.resources.getString("Message.treeRenderingFailed");
        if (this.debug) {
            System.out.println(string);
        }
        this.statusBar.setMainMessage("");
        this.statusBar.setMessage(string);
        if (!this.svgCanvas.isDynamic()) {
            this.stopAction.update(false);
        }
        this.svgCanvas.setCursor(JSVGViewerFrame.DEFAULT_CURSOR);
    }
    
    public void linkActivated(final LinkActivationEvent linkActivationEvent) {
        final String referencedURI = linkActivationEvent.getReferencedURI();
        if (this.svgDocument != null) {
            final ParsedURL parsedURL = new ParsedURL(this.svgDocument.getURL());
            if (!new ParsedURL(parsedURL, referencedURI).sameFile(parsedURL)) {
                return;
            }
            if (referencedURI.indexOf(35) != -1) {
                this.localHistory.update(referencedURI);
                this.locationBar.setText(referencedURI);
                if (this.debugger != null) {
                    this.debugger.detach();
                    this.debugger.setDocumentURL(referencedURI);
                }
                this.application.addVisitedURI(referencedURI);
                this.backAction.update();
                this.forwardAction.update();
                this.transformHistory = new TransformHistory();
                this.previousTransformAction.update();
                this.nextTransformAction.update();
            }
        }
    }
    
    public void managerStarted(final UpdateManagerEvent updateManagerEvent) {
        if (this.debug) {
            System.out.println(JSVGViewerFrame.resources.getString("Message.updateManagerStarted"));
        }
        this.managerStopped = false;
        this.playAction.update(false);
        this.pauseAction.update(true);
        this.stopAction.update(true);
    }
    
    public void managerSuspended(final UpdateManagerEvent updateManagerEvent) {
        if (this.debug) {
            System.out.println(JSVGViewerFrame.resources.getString("Message.updateManagerSuspended"));
        }
        this.playAction.update(true);
        this.pauseAction.update(false);
    }
    
    public void managerResumed(final UpdateManagerEvent updateManagerEvent) {
        if (this.debug) {
            System.out.println(JSVGViewerFrame.resources.getString("Message.updateManagerResumed"));
        }
        this.playAction.update(false);
        this.pauseAction.update(true);
    }
    
    public void managerStopped(final UpdateManagerEvent updateManagerEvent) {
        if (this.debug) {
            System.out.println(JSVGViewerFrame.resources.getString("Message.updateManagerStopped"));
        }
        this.managerStopped = true;
        this.playAction.update(false);
        this.pauseAction.update(false);
        this.stopAction.update(false);
    }
    
    public void updateStarted(final UpdateManagerEvent updateManagerEvent) {
    }
    
    public void updateCompleted(final UpdateManagerEvent updateManagerEvent) {
    }
    
    public void updateFailed(final UpdateManagerEvent updateManagerEvent) {
    }
    
    static {
        try {
            JSVGViewerFrame.EOL = System.getProperty("line.separator", "\n");
        }
        catch (SecurityException ex) {
            JSVGViewerFrame.EOL = "\n";
        }
        JSVGViewerFrame.priorJDK1_4 = true;
        try {
            Class.forName("java.util.logging.LoggingPermission");
            JSVGViewerFrame.priorJDK1_4 = false;
        }
        catch (ClassNotFoundException ex2) {}
        WAIT_CURSOR = new Cursor(3);
        DEFAULT_CURSOR = new Cursor(0);
        PROPERTY_OS_NAME = Resources.getString("JSVGViewerFrame.property.os.name");
        PROPERTY_OS_NAME_DEFAULT = Resources.getString("JSVGViewerFrame.property.os.name.default");
        PROPERTY_OS_WINDOWS_PREFIX = Resources.getString("JSVGViewerFrame.property.os.windows.prefix");
        JSVGViewerFrame.defaultHandler = new SVGInputHandler();
        JSVGViewerFrame.bundle = ResourceBundle.getBundle("org.apache.batik.apps.svgbrowser.resources.GUI", Locale.getDefault());
        JSVGViewerFrame.resources = new ResourceManager(JSVGViewerFrame.bundle);
    }
    
    protected static class ImageFileFilter extends FileFilter
    {
        protected String extension;
        
        public ImageFileFilter(final String extension) {
            this.extension = extension;
        }
        
        public boolean accept(final File file) {
            boolean b = false;
            if (file != null) {
                if (file.isDirectory()) {
                    b = true;
                }
                else if (file.getPath().toLowerCase().endsWith(this.extension)) {
                    b = true;
                }
            }
            return b;
        }
        
        public String getDescription() {
            return this.extension;
        }
    }
    
    protected class UserAgent implements SVGUserAgent
    {
        public void displayError(final String s) {
            if (JSVGViewerFrame.this.debug) {
                System.err.println(s);
            }
            final JDialog dialog = new JOptionPane(s, 0).createDialog(JSVGViewerFrame.this, "ERROR");
            dialog.setModal(false);
            dialog.setVisible(true);
        }
        
        public void displayError(final Exception ex) {
            if (JSVGViewerFrame.this.debug) {
                ex.printStackTrace();
            }
            final JDialog dialog = new JErrorPane(ex, 0).createDialog(JSVGViewerFrame.this, "ERROR");
            dialog.setModal(false);
            dialog.setVisible(true);
        }
        
        public void displayMessage(final String message) {
            JSVGViewerFrame.this.statusBar.setMessage(message);
        }
        
        public void showAlert(final String s) {
            JSVGViewerFrame.this.svgCanvas.showAlert(s);
        }
        
        public String showPrompt(final String s) {
            return JSVGViewerFrame.this.svgCanvas.showPrompt(s);
        }
        
        public String showPrompt(final String s, final String s2) {
            return JSVGViewerFrame.this.svgCanvas.showPrompt(s, s2);
        }
        
        public boolean showConfirm(final String s) {
            return JSVGViewerFrame.this.svgCanvas.showConfirm(s);
        }
        
        public float getPixelUnitToMillimeter() {
            return 0.26458332f;
        }
        
        public float getPixelToMM() {
            return this.getPixelUnitToMillimeter();
        }
        
        public String getDefaultFontFamily() {
            return JSVGViewerFrame.this.application.getDefaultFontFamily();
        }
        
        public float getMediumFontSize() {
            return 228.59999f / (72.0f * this.getPixelUnitToMillimeter());
        }
        
        public float getLighterFontWeight(final float f) {
            switch ((int)((f + 50.0f) / 100.0f) * 100) {
                case 100: {
                    return 100.0f;
                }
                case 200: {
                    return 100.0f;
                }
                case 300: {
                    return 200.0f;
                }
                case 400: {
                    return 300.0f;
                }
                case 500: {
                    return 400.0f;
                }
                case 600: {
                    return 400.0f;
                }
                case 700: {
                    return 400.0f;
                }
                case 800: {
                    return 400.0f;
                }
                case 900: {
                    return 400.0f;
                }
                default: {
                    throw new IllegalArgumentException("Bad Font Weight: " + f);
                }
            }
        }
        
        public float getBolderFontWeight(final float f) {
            switch ((int)((f + 50.0f) / 100.0f) * 100) {
                case 100: {
                    return 600.0f;
                }
                case 200: {
                    return 600.0f;
                }
                case 300: {
                    return 600.0f;
                }
                case 400: {
                    return 600.0f;
                }
                case 500: {
                    return 600.0f;
                }
                case 600: {
                    return 700.0f;
                }
                case 700: {
                    return 800.0f;
                }
                case 800: {
                    return 900.0f;
                }
                case 900: {
                    return 900.0f;
                }
                default: {
                    throw new IllegalArgumentException("Bad Font Weight: " + f);
                }
            }
        }
        
        public String getLanguages() {
            return JSVGViewerFrame.this.application.getLanguages();
        }
        
        public String getUserStyleSheetURI() {
            return JSVGViewerFrame.this.application.getUserStyleSheetURI();
        }
        
        public String getXMLParserClassName() {
            return JSVGViewerFrame.this.application.getXMLParserClassName();
        }
        
        public boolean isXMLParserValidating() {
            return JSVGViewerFrame.this.application.isXMLParserValidating();
        }
        
        public String getMedia() {
            return JSVGViewerFrame.this.application.getMedia();
        }
        
        public String getAlternateStyleSheet() {
            return JSVGViewerFrame.this.alternateStyleSheet;
        }
        
        public void openLink(final String s, final boolean b) {
            if (b) {
                JSVGViewerFrame.this.application.openLink(s);
            }
            else {
                JSVGViewerFrame.this.showSVGDocument(s);
            }
        }
        
        public boolean supportExtension(final String s) {
            return false;
        }
        
        public void handleElement(final Element element, final Object o) {
        }
        
        public ScriptSecurity getScriptSecurity(final String s, final ParsedURL parsedURL, final ParsedURL parsedURL2) {
            if (!JSVGViewerFrame.this.application.canLoadScriptType(s)) {
                return new NoLoadScriptSecurity(s);
            }
            switch (JSVGViewerFrame.this.application.getAllowedScriptOrigin()) {
                case 1: {
                    return new RelaxedScriptSecurity(s, parsedURL, parsedURL2);
                }
                case 2: {
                    return new DefaultScriptSecurity(s, parsedURL, parsedURL2);
                }
                case 4: {
                    return new EmbededScriptSecurity(s, parsedURL, parsedURL2);
                }
                default: {
                    return new NoLoadScriptSecurity(s);
                }
            }
        }
        
        public void checkLoadScript(final String s, final ParsedURL parsedURL, final ParsedURL parsedURL2) throws SecurityException {
            final ScriptSecurity scriptSecurity = this.getScriptSecurity(s, parsedURL, parsedURL2);
            if (scriptSecurity != null) {
                scriptSecurity.checkLoadScript();
            }
        }
        
        public ExternalResourceSecurity getExternalResourceSecurity(final ParsedURL parsedURL, final ParsedURL parsedURL2) {
            switch (JSVGViewerFrame.this.application.getAllowedExternalResourceOrigin()) {
                case 1: {
                    return new RelaxedExternalResourceSecurity(parsedURL, parsedURL2);
                }
                case 2: {
                    return new DefaultExternalResourceSecurity(parsedURL, parsedURL2);
                }
                case 4: {
                    return new EmbededExternalResourceSecurity(parsedURL);
                }
                default: {
                    return new NoLoadExternalResourceSecurity();
                }
            }
        }
        
        public void checkLoadExternalResource(final ParsedURL parsedURL, final ParsedURL parsedURL2) throws SecurityException {
            final ExternalResourceSecurity externalResourceSecurity = this.getExternalResourceSecurity(parsedURL, parsedURL2);
            if (externalResourceSecurity != null) {
                externalResourceSecurity.checkLoadExternalResource();
            }
        }
    }
    
    protected class Canvas extends JSVGCanvas
    {
        public Canvas(final SVGUserAgent svgUserAgent, final boolean b, final boolean b2) {
            super(svgUserAgent, b, b2);
        }
        
        public Object getRhinoInterpreter() {
            if (this.bridgeContext == null) {
                return null;
            }
            return this.bridgeContext.getInterpreter("text/ecmascript");
        }
        
        protected class JSVGViewerDOMViewerController implements DOMViewerController
        {
            public boolean canEdit() {
                return Canvas.this.getUpdateManager() != null;
            }
            
            public ElementOverlayManager createSelectionManager() {
                if (this.canEdit()) {
                    return new ElementOverlayManager(Canvas.this);
                }
                return null;
            }
            
            public Document getDocument() {
                return (Document)Canvas.this.svgDocument;
            }
            
            public void performUpdate(final Runnable runnable) {
                if (this.canEdit()) {
                    Canvas.this.getUpdateManager().getUpdateRunnableQueue().invokeLater(runnable);
                }
                else {
                    runnable.run();
                }
            }
            
            public void removeSelectionOverlay(final Overlay overlay) {
                Canvas.this.getOverlays().remove(overlay);
            }
            
            public void selectNode(final Node node) {
                ((DOMViewerAction)JSVGViewerFrame.this.getAction("DOMViewerAction")).openDOMViewer();
                JSVGViewerFrame.this.domViewer.selectNode(node);
            }
        }
    }
    
    public class DOMViewerAction extends AbstractAction
    {
        public void actionPerformed(final ActionEvent actionEvent) {
            this.openDOMViewer();
        }
        
        public void openDOMViewer() {
            if (JSVGViewerFrame.this.domViewer == null || JSVGViewerFrame.this.domViewer.isDisplayable()) {
                JSVGViewerFrame.this.domViewer = new DOMViewer(JSVGViewerFrame.this.svgCanvas.new JSVGViewerDOMViewerController());
                final Rectangle bounds = JSVGViewerFrame.this.getBounds();
                final Dimension size = JSVGViewerFrame.this.domViewer.getSize();
                JSVGViewerFrame.this.domViewer.setLocation(bounds.x + (bounds.width - size.width) / 2, bounds.y + (bounds.height - size.height) / 2);
            }
            JSVGViewerFrame.this.domViewer.setVisible(true);
        }
        
        public DOMViewer getDOMViewer() {
            return JSVGViewerFrame.this.domViewer;
        }
    }
    
    public class FullScreenAction extends AbstractAction
    {
        public void actionPerformed(final ActionEvent actionEvent) {
            if (JSVGViewerFrame.this.window == null || !JSVGViewerFrame.this.window.isVisible()) {
                if (JSVGViewerFrame.this.window == null) {
                    (JSVGViewerFrame.this.window = new JWindow(JSVGViewerFrame.this)).setSize(Toolkit.getDefaultToolkit().getScreenSize());
                }
                JSVGViewerFrame.this.svgCanvas.getParent().remove(JSVGViewerFrame.this.svgCanvas);
                JSVGViewerFrame.this.window.getContentPane().add(JSVGViewerFrame.this.svgCanvas);
                JSVGViewerFrame.this.window.setVisible(true);
                JSVGViewerFrame.this.window.toFront();
                JSVGViewerFrame.this.svgCanvas.requestFocus();
            }
            else {
                JSVGViewerFrame.this.svgCanvas.getParent().remove(JSVGViewerFrame.this.svgCanvas);
                JSVGViewerFrame.this.svgCanvasPanel.add(JSVGViewerFrame.this.svgCanvas, "Center");
                JSVGViewerFrame.this.window.setVisible(false);
            }
        }
    }
    
    public class ThumbnailDialogAction extends AbstractAction
    {
        public void actionPerformed(final ActionEvent actionEvent) {
            if (JSVGViewerFrame.this.thumbnailDialog == null) {
                (JSVGViewerFrame.this.thumbnailDialog = new ThumbnailDialog(JSVGViewerFrame.this, JSVGViewerFrame.this.svgCanvas)).pack();
                final Rectangle bounds = JSVGViewerFrame.this.getBounds();
                final Dimension size = JSVGViewerFrame.this.thumbnailDialog.getSize();
                JSVGViewerFrame.this.thumbnailDialog.setLocation(bounds.x + (bounds.width - size.width) / 2, bounds.y + (bounds.height - size.height) / 2);
            }
            JSVGViewerFrame.this.thumbnailDialog.setInteractionEnabled(!JSVGViewerFrame.this.svgCanvas.getDisableInteractions());
            JSVGViewerFrame.this.thumbnailDialog.setVisible(true);
        }
    }
    
    public class FindDialogAction extends AbstractAction
    {
        public void actionPerformed(final ActionEvent actionEvent) {
            if (JSVGViewerFrame.this.findDialog == null) {
                (JSVGViewerFrame.this.findDialog = new FindDialog(JSVGViewerFrame.this, JSVGViewerFrame.this.svgCanvas)).setGraphicsNode(JSVGViewerFrame.this.svgCanvas.getGraphicsNode());
                JSVGViewerFrame.this.findDialog.pack();
                final Rectangle bounds = JSVGViewerFrame.this.getBounds();
                final Dimension size = JSVGViewerFrame.this.findDialog.getSize();
                JSVGViewerFrame.this.findDialog.setLocation(bounds.x + (bounds.width - size.width) / 2, bounds.y + (bounds.height - size.height) / 2);
            }
            JSVGViewerFrame.this.findDialog.setVisible(true);
        }
    }
    
    public class MonitorAction extends AbstractAction
    {
        public void actionPerformed(final ActionEvent actionEvent) {
            if (JSVGViewerFrame.memoryMonitorFrame == null) {
                JSVGViewerFrame.memoryMonitorFrame = new MemoryMonitor();
                final Rectangle bounds = JSVGViewerFrame.this.getBounds();
                final Dimension size = JSVGViewerFrame.memoryMonitorFrame.getSize();
                JSVGViewerFrame.memoryMonitorFrame.setLocation(bounds.x + (bounds.width - size.width) / 2, bounds.y + (bounds.height - size.height) / 2);
            }
            JSVGViewerFrame.memoryMonitorFrame.setVisible(true);
        }
    }
    
    public class SetTransformAction extends AbstractAction
    {
        public void actionPerformed(final ActionEvent actionEvent) {
            if (JSVGViewerFrame.this.transformDialog == null) {
                JSVGViewerFrame.this.transformDialog = JAffineTransformChooser.createDialog(JSVGViewerFrame.this, JSVGViewerFrame.resources.getString("SetTransform.title"));
            }
            final AffineTransform showDialog = JSVGViewerFrame.this.transformDialog.showDialog();
            if (showDialog != null) {
                AffineTransform renderingTransform = JSVGViewerFrame.this.svgCanvas.getRenderingTransform();
                if (renderingTransform == null) {
                    renderingTransform = new AffineTransform();
                }
                showDialog.concatenate(renderingTransform);
                JSVGViewerFrame.this.svgCanvas.setRenderingTransform(showDialog);
            }
        }
    }
    
    public class StopAction extends AbstractAction implements JComponentModifier
    {
        List components;
        
        public StopAction() {
            this.components = new LinkedList();
        }
        
        public void actionPerformed(final ActionEvent actionEvent) {
            JSVGViewerFrame.this.svgCanvas.stopProcessing();
        }
        
        public void addJComponent(final JComponent component) {
            this.components.add(component);
            component.setEnabled(false);
        }
        
        public void update(final boolean enabled) {
            final Iterator<JComponent> iterator = this.components.iterator();
            while (iterator.hasNext()) {
                iterator.next().setEnabled(enabled);
            }
        }
    }
    
    public class PauseAction extends AbstractAction implements JComponentModifier
    {
        List components;
        
        public PauseAction() {
            this.components = new LinkedList();
        }
        
        public void actionPerformed(final ActionEvent actionEvent) {
            JSVGViewerFrame.this.svgCanvas.suspendProcessing();
        }
        
        public void addJComponent(final JComponent component) {
            this.components.add(component);
            component.setEnabled(false);
        }
        
        public void update(final boolean enabled) {
            final Iterator<JComponent> iterator = this.components.iterator();
            while (iterator.hasNext()) {
                iterator.next().setEnabled(enabled);
            }
        }
    }
    
    public class PlayAction extends AbstractAction implements JComponentModifier
    {
        List components;
        
        public PlayAction() {
            this.components = new LinkedList();
        }
        
        public void actionPerformed(final ActionEvent actionEvent) {
            JSVGViewerFrame.this.svgCanvas.resumeProcessing();
        }
        
        public void addJComponent(final JComponent component) {
            this.components.add(component);
            component.setEnabled(false);
        }
        
        public void update(final boolean enabled) {
            final Iterator<JComponent> iterator = this.components.iterator();
            while (iterator.hasNext()) {
                iterator.next().setEnabled(enabled);
            }
        }
    }
    
    public class UseStylesheetAction extends AbstractAction implements JComponentModifier
    {
        List components;
        private final /* synthetic */ JSVGViewerFrame this$0;
        
        public UseStylesheetAction() {
            this.components = new LinkedList();
        }
        
        public void actionPerformed(final ActionEvent actionEvent) {
        }
        
        public void addJComponent(final JComponent component) {
            this.components.add(component);
            component.setEnabled(false);
        }
        
        protected void update() {
            JSVGViewerFrame.this.alternateStyleSheet = null;
            final Iterator<JComponent> iterator = (Iterator<JComponent>)this.components.iterator();
            final SVGDocument svgDocument = JSVGViewerFrame.this.svgCanvas.getSVGDocument();
            while (iterator.hasNext()) {
                final JComponent component = iterator.next();
                component.removeAll();
                component.setEnabled(false);
                final ButtonGroup buttonGroup = new ButtonGroup();
                for (Node node = svgDocument.getFirstChild(); node != null && node.getNodeType() != 1; node = node.getNextSibling()) {
                    if (node instanceof StyleSheetProcessingInstruction) {
                        final HashTable pseudoAttributes = ((StyleSheetProcessingInstruction)node).getPseudoAttributes();
                        final String text = (String)pseudoAttributes.get("title");
                        final String anObject = (String)pseudoAttributes.get("alternate");
                        if (text != null && "yes".equals(anObject)) {
                            final JRadioButtonMenuItem radioButtonMenuItem = new JRadioButtonMenuItem(text);
                            radioButtonMenuItem.addActionListener(new ActionListener() {
                                private final /* synthetic */ UseStylesheetAction this$1 = this$1;
                                
                                public void actionPerformed(final ActionEvent actionEvent) {
                                    final SVGOMDocument svgDocument = (SVGOMDocument)this.this$1.this$0.svgCanvas.getSVGDocument();
                                    svgDocument.clearViewCSS();
                                    this.this$1.this$0.alternateStyleSheet = text;
                                    this.this$1.this$0.svgCanvas.setSVGDocument((SVGDocument)svgDocument);
                                }
                            });
                            buttonGroup.add(radioButtonMenuItem);
                            component.add(radioButtonMenuItem);
                            component.setEnabled(true);
                        }
                    }
                }
            }
        }
    }
    
    public class NextTransformAction extends AbstractAction implements JComponentModifier
    {
        List components;
        
        public NextTransformAction() {
            this.components = new LinkedList();
        }
        
        public void actionPerformed(final ActionEvent actionEvent) {
            if (JSVGViewerFrame.this.transformHistory.canGoForward()) {
                JSVGViewerFrame.this.transformHistory.forward();
                this.update();
                JSVGViewerFrame.this.previousTransformAction.update();
                JSVGViewerFrame.this.svgCanvas.setRenderingTransform(JSVGViewerFrame.this.transformHistory.currentTransform());
            }
        }
        
        public void addJComponent(final JComponent component) {
            this.components.add(component);
            component.setEnabled(false);
        }
        
        protected void update() {
            final boolean canGoForward = JSVGViewerFrame.this.transformHistory.canGoForward();
            final Iterator<JComponent> iterator = (Iterator<JComponent>)this.components.iterator();
            while (iterator.hasNext()) {
                iterator.next().setEnabled(canGoForward);
            }
        }
    }
    
    public class PreviousTransformAction extends AbstractAction implements JComponentModifier
    {
        List components;
        
        public PreviousTransformAction() {
            this.components = new LinkedList();
        }
        
        public void actionPerformed(final ActionEvent actionEvent) {
            if (JSVGViewerFrame.this.transformHistory.canGoBack()) {
                JSVGViewerFrame.this.transformHistory.back();
                this.update();
                JSVGViewerFrame.this.nextTransformAction.update();
                JSVGViewerFrame.this.svgCanvas.setRenderingTransform(JSVGViewerFrame.this.transformHistory.currentTransform());
            }
        }
        
        public void addJComponent(final JComponent component) {
            this.components.add(component);
            component.setEnabled(false);
        }
        
        protected void update() {
            final boolean canGoBack = JSVGViewerFrame.this.transformHistory.canGoBack();
            final Iterator<JComponent> iterator = (Iterator<JComponent>)this.components.iterator();
            while (iterator.hasNext()) {
                iterator.next().setEnabled(canGoBack);
            }
        }
    }
    
    public class ToggleDebuggerAction extends AbstractAction
    {
        public ToggleDebuggerAction() {
            super("Toggle Debugger Action");
        }
        
        public void actionPerformed(final ActionEvent actionEvent) {
            if (JSVGViewerFrame.this.debugger == null) {
                JSVGViewerFrame.this.showDebugger();
            }
            else {
                JSVGViewerFrame.this.hideDebugger();
            }
        }
    }
    
    protected static class Debugger
    {
        protected static boolean isPresent;
        protected static Class debuggerClass;
        protected static Class contextFactoryClass;
        protected static final int CLEAR_ALL_BREAKPOINTS_METHOD = 0;
        protected static final int GO_METHOD = 1;
        protected static final int SET_EXIT_ACTION_METHOD = 2;
        protected static final int ATTACH_TO_METHOD = 3;
        protected static final int DETACH_METHOD = 4;
        protected static final int DISPOSE_METHOD = 5;
        protected static final int GET_DEBUG_FRAME_METHOD = 6;
        protected static Constructor debuggerConstructor;
        protected static Method[] debuggerMethods;
        protected static Class rhinoInterpreterClass;
        protected static Method getContextFactoryMethod;
        protected Object debuggerInstance;
        protected JSVGViewerFrame svgFrame;
        
        public Debugger(final JSVGViewerFrame svgFrame, final String str) {
            this.svgFrame = svgFrame;
            try {
                this.debuggerInstance = Debugger.debuggerConstructor.newInstance("JavaScript Debugger - " + str);
            }
            catch (IllegalAccessException ex) {
                throw new RuntimeException(ex.getMessage());
            }
            catch (InvocationTargetException ex2) {
                ex2.printStackTrace();
                throw new RuntimeException(ex2.getMessage());
            }
            catch (InstantiationException ex3) {
                throw new RuntimeException(ex3.getMessage());
            }
        }
        
        public void setDocumentURL(final String str) {
            this.getDebugFrame().setTitle("JavaScript Debugger - " + str);
        }
        
        public void initialize() {
            final JFrame debugFrame = this.getDebugFrame();
            final JMenu menu = debugFrame.getJMenuBar().getMenu(0);
            menu.getItem(0).setEnabled(false);
            menu.getItem(1).setEnabled(false);
            menu.getItem(3).setText(Resources.getString("Close.text"));
            menu.getItem(3).setAccelerator(KeyStroke.getKeyStroke(87, 2));
            debugFrame.setSize(600, 460);
            debugFrame.pack();
            this.setExitAction(new Runnable() {
                private final /* synthetic */ Debugger this$0 = this$0;
                
                public void run() {
                    this.this$0.svgFrame.hideDebugger();
                }
            });
            debugFrame.addWindowListener(new WindowAdapter() {
                private final /* synthetic */ Debugger this$0 = this$0;
                
                public void windowClosing(final WindowEvent windowEvent) {
                    this.this$0.svgFrame.hideDebugger();
                }
            });
            debugFrame.setVisible(true);
            this.attach();
        }
        
        public void attach() {
            final Object rhinoInterpreter = this.svgFrame.svgCanvas.getRhinoInterpreter();
            if (rhinoInterpreter != null) {
                this.attachTo(this.getContextFactory(rhinoInterpreter));
            }
        }
        
        protected JFrame getDebugFrame() {
            try {
                return (JFrame)Debugger.debuggerMethods[6].invoke(this.debuggerInstance, (Object[])null);
            }
            catch (InvocationTargetException ex) {
                throw new RuntimeException(ex.getMessage());
            }
            catch (IllegalAccessException ex2) {
                throw new RuntimeException(ex2.getMessage());
            }
        }
        
        protected void setExitAction(final Runnable runnable) {
            try {
                Debugger.debuggerMethods[2].invoke(this.debuggerInstance, runnable);
            }
            catch (InvocationTargetException ex) {
                throw new RuntimeException(ex.getMessage());
            }
            catch (IllegalAccessException ex2) {
                throw new RuntimeException(ex2.getMessage());
            }
        }
        
        public void attachTo(final Object o) {
            try {
                Debugger.debuggerMethods[3].invoke(this.debuggerInstance, o);
            }
            catch (InvocationTargetException ex) {
                throw new RuntimeException(ex.getMessage());
            }
            catch (IllegalAccessException ex2) {
                throw new RuntimeException(ex2.getMessage());
            }
        }
        
        public void detach() {
            try {
                Debugger.debuggerMethods[4].invoke(this.debuggerInstance, (Object[])null);
            }
            catch (InvocationTargetException ex) {
                throw new RuntimeException(ex.getMessage());
            }
            catch (IllegalAccessException ex2) {
                throw new RuntimeException(ex2.getMessage());
            }
        }
        
        public void go() {
            try {
                Debugger.debuggerMethods[1].invoke(this.debuggerInstance, (Object[])null);
            }
            catch (InvocationTargetException ex) {
                throw new RuntimeException(ex.getMessage());
            }
            catch (IllegalAccessException ex2) {
                throw new RuntimeException(ex2.getMessage());
            }
        }
        
        public void clearAllBreakpoints() {
            try {
                Debugger.debuggerMethods[0].invoke(this.debuggerInstance, (Object[])null);
            }
            catch (InvocationTargetException ex) {
                throw new RuntimeException(ex.getMessage());
            }
            catch (IllegalAccessException ex2) {
                throw new RuntimeException(ex2.getMessage());
            }
        }
        
        public void dispose() {
            try {
                Debugger.debuggerMethods[5].invoke(this.debuggerInstance, (Object[])null);
            }
            catch (InvocationTargetException ex) {
                throw new RuntimeException(ex.getMessage());
            }
            catch (IllegalAccessException ex2) {
                throw new RuntimeException(ex2.getMessage());
            }
        }
        
        protected Object getContextFactory(final Object obj) {
            try {
                return Debugger.getContextFactoryMethod.invoke(obj, (Object[])null);
            }
            catch (InvocationTargetException ex) {
                throw new RuntimeException(ex.getMessage());
            }
            catch (IllegalAccessException ex2) {
                throw new RuntimeException(ex2.getMessage());
            }
        }
        
        static {
            try {
                final Class<?> forName = Class.forName("org.mozilla.javascript.tools.debugger.Main");
                final Class<?> forName2 = Class.forName("org.mozilla.javascript.ContextFactory");
                Debugger.rhinoInterpreterClass = Class.forName("org.apache.batik.script.rhino.RhinoInterpreter");
                Debugger.debuggerConstructor = forName.getConstructor(String.class);
                Debugger.debuggerMethods = new Method[] { forName.getMethod("clearAllBreakpoints", (Class[])null), forName.getMethod("go", (Class[])null), forName.getMethod("setExitAction", Runnable.class), forName.getMethod("attachTo", forName2), forName.getMethod("detach", (Class[])null), forName.getMethod("dispose", (Class[])null), forName.getMethod("getDebugFrame", (Class[])null) };
                Debugger.getContextFactoryMethod = Debugger.rhinoInterpreterClass.getMethod("getContextFactory", (Class[])null);
                Debugger.debuggerClass = forName;
                Debugger.isPresent = true;
            }
            catch (ClassNotFoundException ex) {}
            catch (NoSuchMethodException ex2) {}
            catch (SecurityException ex3) {}
        }
    }
    
    public class FlushAction extends AbstractAction
    {
        public void actionPerformed(final ActionEvent actionEvent) {
            JSVGViewerFrame.this.svgCanvas.flush();
            JSVGViewerFrame.this.svgCanvas.setRenderingTransform(JSVGViewerFrame.this.svgCanvas.getRenderingTransform());
        }
    }
    
    public class ViewSourceAction extends AbstractAction
    {
        private final /* synthetic */ JSVGViewerFrame this$0;
        
        public void actionPerformed(final ActionEvent actionEvent) {
            if (JSVGViewerFrame.this.svgDocument == null) {
                return;
            }
            final ParsedURL parsedURL = new ParsedURL(JSVGViewerFrame.this.svgDocument.getURL());
            final JFrame frame = new JFrame(parsedURL.toString());
            frame.setSize(JSVGViewerFrame.resources.getInteger("ViewSource.width"), JSVGViewerFrame.resources.getInteger("ViewSource.height"));
            final XMLTextEditor comp = new XMLTextEditor();
            comp.setFont(new Font("monospaced", 0, 12));
            final JScrollPane comp2 = new JScrollPane();
            comp2.getViewport().add(comp);
            comp2.setVerticalScrollBarPolicy(22);
            frame.getContentPane().add(comp2, "Center");
            new Thread() {
                private final /* synthetic */ ViewSourceAction this$1 = this$1;
                
                public void run() {
                    final char[] value = new char[4096];
                    try {
                        final XMLDocument document = new XMLDocument();
                        int read;
                        while ((read = XMLUtilities.createXMLDocumentReader(parsedURL.openStream(this.this$1.this$0.getInputHandler(new ParsedURL(this.this$1.this$0.svgDocument.getURL())).getHandledMimeTypes())).read(value, 0, value.length)) != -1) {
                            document.insertString(document.getLength(), new String(value, 0, read), null);
                        }
                        comp.setDocument(document);
                        comp.setEditable(false);
                        frame.setVisible(true);
                    }
                    catch (Exception ex) {
                        this.this$1.this$0.userAgent.displayError(ex);
                    }
                }
            }.start();
        }
    }
    
    public class ExportAsTIFFAction extends AbstractAction
    {
        private final /* synthetic */ JSVGViewerFrame this$0;
        
        public void actionPerformed(final ActionEvent actionEvent) {
            final JFileChooser fileChooser = new JFileChooser(makeAbsolute(JSVGViewerFrame.this.currentSavePath));
            fileChooser.setDialogTitle(JSVGViewerFrame.resources.getString("ExportAsTIFF.title"));
            fileChooser.setFileHidingEnabled(false);
            fileChooser.setFileSelectionMode(0);
            fileChooser.addChoosableFileFilter(new ImageFileFilter(".tiff"));
            if (fileChooser.showSaveDialog(JSVGViewerFrame.this) == 0) {
                final File selectedFile = fileChooser.getSelectedFile();
                final BufferedImage offScreen = JSVGViewerFrame.this.svgCanvas.getOffScreen();
                if (offScreen != null) {
                    JSVGViewerFrame.this.statusBar.setMessage(JSVGViewerFrame.resources.getString("Message.exportAsTIFF"));
                    final int width = offScreen.getWidth();
                    final int height = offScreen.getHeight();
                    final TIFFTranscoder tiffTranscoder = new TIFFTranscoder();
                    if (JSVGViewerFrame.this.application.getXMLParserClassName() != null) {
                        tiffTranscoder.addTranscodingHint(JPEGTranscoder.KEY_XML_PARSER_CLASSNAME, JSVGViewerFrame.this.application.getXMLParserClassName());
                    }
                    final BufferedImage image = tiffTranscoder.createImage(width, height);
                    image.createGraphics().drawImage(offScreen, null, 0, 0);
                    new Thread() {
                        private final /* synthetic */ ImageTranscoder val$trans = tiffTranscoder;
                        private final /* synthetic */ ExportAsTIFFAction this$1 = this$1;
                        
                        public void run() {
                            try {
                                this.this$1.this$0.currentSavePath = selectedFile;
                                final BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(selectedFile));
                                this.val$trans.writeImage(image, new TranscoderOutput(bufferedOutputStream));
                                bufferedOutputStream.close();
                            }
                            catch (Exception ex) {}
                            this.this$1.this$0.statusBar.setMessage(JSVGViewerFrame.resources.getString("Message.done"));
                        }
                    }.start();
                }
            }
        }
    }
    
    public class ExportAsPNGAction extends AbstractAction
    {
        private final /* synthetic */ JSVGViewerFrame this$0;
        
        public void actionPerformed(final ActionEvent actionEvent) {
            final JFileChooser fileChooser = new JFileChooser(makeAbsolute(JSVGViewerFrame.this.currentSavePath));
            fileChooser.setDialogTitle(JSVGViewerFrame.resources.getString("ExportAsPNG.title"));
            fileChooser.setFileHidingEnabled(false);
            fileChooser.setFileSelectionMode(0);
            fileChooser.addChoosableFileFilter(new ImageFileFilter(".png"));
            if (fileChooser.showSaveDialog(JSVGViewerFrame.this) == 0) {
                final boolean showDialog = PNGOptionPanel.showDialog(JSVGViewerFrame.this);
                final File selectedFile = fileChooser.getSelectedFile();
                final BufferedImage offScreen = JSVGViewerFrame.this.svgCanvas.getOffScreen();
                if (offScreen != null) {
                    JSVGViewerFrame.this.statusBar.setMessage(JSVGViewerFrame.resources.getString("Message.exportAsPNG"));
                    final int width = offScreen.getWidth();
                    final int height = offScreen.getHeight();
                    final PNGTranscoder pngTranscoder = new PNGTranscoder();
                    if (JSVGViewerFrame.this.application.getXMLParserClassName() != null) {
                        pngTranscoder.addTranscodingHint(JPEGTranscoder.KEY_XML_PARSER_CLASSNAME, JSVGViewerFrame.this.application.getXMLParserClassName());
                    }
                    pngTranscoder.addTranscodingHint(PNGTranscoder.KEY_FORCE_TRANSPARENT_WHITE, Boolean.TRUE);
                    if (showDialog) {
                        pngTranscoder.addTranscodingHint(PNGTranscoder.KEY_INDEXED, new Integer(256));
                    }
                    final BufferedImage image = pngTranscoder.createImage(width, height);
                    image.createGraphics().drawImage(offScreen, null, 0, 0);
                    new Thread() {
                        private final /* synthetic */ ImageTranscoder val$trans = pngTranscoder;
                        private final /* synthetic */ ExportAsPNGAction this$1 = this$1;
                        
                        public void run() {
                            try {
                                this.this$1.this$0.currentSavePath = selectedFile;
                                final BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(selectedFile));
                                this.val$trans.writeImage(image, new TranscoderOutput(bufferedOutputStream));
                                bufferedOutputStream.close();
                            }
                            catch (Exception ex) {}
                            this.this$1.this$0.statusBar.setMessage(JSVGViewerFrame.resources.getString("Message.done"));
                        }
                    }.start();
                }
            }
        }
    }
    
    public class ExportAsJPGAction extends AbstractAction
    {
        private final /* synthetic */ JSVGViewerFrame this$0;
        
        public void actionPerformed(final ActionEvent actionEvent) {
            final JFileChooser fileChooser = new JFileChooser(makeAbsolute(JSVGViewerFrame.this.currentSavePath));
            fileChooser.setDialogTitle(JSVGViewerFrame.resources.getString("ExportAsJPG.title"));
            fileChooser.setFileHidingEnabled(false);
            fileChooser.setFileSelectionMode(0);
            fileChooser.addChoosableFileFilter(new ImageFileFilter(".jpg"));
            if (fileChooser.showSaveDialog(JSVGViewerFrame.this) == 0) {
                final float showDialog = JPEGOptionPanel.showDialog(JSVGViewerFrame.this);
                final File selectedFile = fileChooser.getSelectedFile();
                final BufferedImage offScreen = JSVGViewerFrame.this.svgCanvas.getOffScreen();
                if (offScreen != null) {
                    JSVGViewerFrame.this.statusBar.setMessage(JSVGViewerFrame.resources.getString("Message.exportAsJPG"));
                    final int width = offScreen.getWidth();
                    final int height = offScreen.getHeight();
                    final JPEGTranscoder jpegTranscoder = new JPEGTranscoder();
                    if (JSVGViewerFrame.this.application.getXMLParserClassName() != null) {
                        jpegTranscoder.addTranscodingHint(JPEGTranscoder.KEY_XML_PARSER_CLASSNAME, JSVGViewerFrame.this.application.getXMLParserClassName());
                    }
                    jpegTranscoder.addTranscodingHint(JPEGTranscoder.KEY_QUALITY, new Float(showDialog));
                    final BufferedImage image = jpegTranscoder.createImage(width, height);
                    final Graphics2D graphics = image.createGraphics();
                    graphics.setColor(Color.white);
                    graphics.fillRect(0, 0, width, height);
                    graphics.drawImage(offScreen, null, 0, 0);
                    new Thread() {
                        private final /* synthetic */ ImageTranscoder val$trans = jpegTranscoder;
                        private final /* synthetic */ ExportAsJPGAction this$1 = this$1;
                        
                        public void run() {
                            try {
                                this.this$1.this$0.currentSavePath = selectedFile;
                                final BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(selectedFile));
                                this.val$trans.writeImage(image, new TranscoderOutput(bufferedOutputStream));
                                bufferedOutputStream.close();
                            }
                            catch (Exception ex) {}
                            this.this$1.this$0.statusBar.setMessage(JSVGViewerFrame.resources.getString("Message.done"));
                        }
                    }.start();
                }
            }
        }
    }
    
    public class SaveAsAction extends AbstractAction
    {
        private final /* synthetic */ JSVGViewerFrame this$0;
        
        public void actionPerformed(final ActionEvent actionEvent) {
            final JFileChooser fileChooser = new JFileChooser(makeAbsolute(JSVGViewerFrame.this.currentSavePath));
            fileChooser.setDialogTitle(JSVGViewerFrame.resources.getString("SaveAs.title"));
            fileChooser.setFileHidingEnabled(false);
            fileChooser.setFileSelectionMode(0);
            fileChooser.addChoosableFileFilter(new ImageFileFilter(".svg"));
            if (fileChooser.showSaveDialog(JSVGViewerFrame.this) != 0) {
                return;
            }
            final File selectedFile = fileChooser.getSelectedFile();
            final SVGOptionPanel showDialog = SVGOptionPanel.showDialog(JSVGViewerFrame.this);
            final boolean useXMLBase = showDialog.getUseXMLBase();
            final boolean prettyPrint = showDialog.getPrettyPrint();
            final SVGDocument svgDocument = JSVGViewerFrame.this.svgCanvas.getSVGDocument();
            if (svgDocument == null) {
                return;
            }
            JSVGViewerFrame.this.statusBar.setMessage(JSVGViewerFrame.resources.getString("Message.saveAs"));
            JSVGViewerFrame.this.currentSavePath = selectedFile;
            OutputStreamWriter outputStreamWriter;
            try {
                outputStreamWriter = new OutputStreamWriter(new BufferedOutputStream(new FileOutputStream(selectedFile)), "utf-8");
            }
            catch (Exception ex) {
                JSVGViewerFrame.this.userAgent.displayError(ex);
                return;
            }
            final Runnable runnable = new Runnable() {
                private final /* synthetic */ Runnable val$doneRun = new Runnable() {
                    private final /* synthetic */ SaveAsAction this$1 = this$1;
                    
                    public void run() {
                        this.this$1.this$0.statusBar.setMessage(JSVGViewerFrame.resources.getString("Message.done"));
                    }
                };
                private final /* synthetic */ SaveAsAction this$1 = this$1;
                
                public void run() {
                    try {
                        outputStreamWriter.write("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
                        outputStreamWriter.write(JSVGViewerFrame.EOL);
                        if (svgDocument.getFirstChild().getNodeType() != 10) {
                            outputStreamWriter.write("<!DOCTYPE svg PUBLIC '");
                            outputStreamWriter.write("-//W3C//DTD SVG 1.0//EN");
                            outputStreamWriter.write("' '");
                            outputStreamWriter.write("http://www.w3.org/TR/2001/REC-SVG-20010904/DTD/svg10.dtd");
                            outputStreamWriter.write("'>");
                            outputStreamWriter.write(JSVGViewerFrame.EOL);
                            outputStreamWriter.write(JSVGViewerFrame.EOL);
                        }
                        final SVGSVGElement rootElement = svgDocument.getRootElement();
                        boolean val$useXMLBase = useXMLBase;
                        if (((Element)rootElement).hasAttributeNS("http://www.w3.org/XML/1998/namespace", "base")) {
                            val$useXMLBase = false;
                        }
                        if (val$useXMLBase) {
                            ((Element)rootElement).setAttributeNS("http://www.w3.org/XML/1998/namespace", "xml:base", svgDocument.getURL());
                        }
                        if (prettyPrint) {
                            new SVGTranscoder().transcode(new TranscoderInput((Document)svgDocument), new TranscoderOutput(outputStreamWriter));
                        }
                        else {
                            DOMUtilities.writeDocument((Document)svgDocument, outputStreamWriter);
                        }
                        outputStreamWriter.close();
                        if (val$useXMLBase) {
                            ((Element)rootElement).removeAttributeNS("http://www.w3.org/XML/1998/namespace", "xml:base");
                        }
                        if (EventQueue.isDispatchThread()) {
                            this.val$doneRun.run();
                        }
                        else {
                            EventQueue.invokeLater(this.val$doneRun);
                        }
                    }
                    catch (Exception ex) {
                        this.this$1.this$0.userAgent.displayError(ex);
                    }
                }
            };
            final UpdateManager updateManager = JSVGViewerFrame.this.svgCanvas.getUpdateManager();
            if (updateManager != null && updateManager.isRunning()) {
                updateManager.getUpdateRunnableQueue().invokeLater(runnable);
            }
            else {
                runnable.run();
            }
        }
    }
    
    public class PrintAction extends AbstractAction
    {
        private final /* synthetic */ JSVGViewerFrame this$0;
        
        public void actionPerformed(final ActionEvent actionEvent) {
            if (JSVGViewerFrame.this.svgDocument != null) {
                new Thread() {
                    private final /* synthetic */ SVGDocument val$doc = JSVGViewerFrame.this.svgDocument;
                    private final /* synthetic */ PrintAction this$1 = this$1;
                    
                    public void run() {
                        String str = this.val$doc.getURL();
                        final String fragmentIdentifier = this.this$1.this$0.svgCanvas.getFragmentIdentifier();
                        if (fragmentIdentifier != null) {
                            str = str + '#' + fragmentIdentifier;
                        }
                        final PrintTranscoder printTranscoder = new PrintTranscoder();
                        if (this.this$1.this$0.application.getXMLParserClassName() != null) {
                            printTranscoder.addTranscodingHint(JPEGTranscoder.KEY_XML_PARSER_CLASSNAME, this.this$1.this$0.application.getXMLParserClassName());
                        }
                        printTranscoder.addTranscodingHint(PrintTranscoder.KEY_SHOW_PAGE_DIALOG, Boolean.TRUE);
                        printTranscoder.addTranscodingHint(PrintTranscoder.KEY_SHOW_PRINTER_DIALOG, Boolean.TRUE);
                        printTranscoder.transcode(new TranscoderInput(str), null);
                        try {
                            printTranscoder.print();
                        }
                        catch (PrinterException ex) {
                            this.this$1.this$0.userAgent.displayError(ex);
                        }
                    }
                }.start();
            }
        }
    }
    
    public class ForwardAction extends AbstractAction implements JComponentModifier
    {
        List components;
        
        public ForwardAction() {
            this.components = new LinkedList();
        }
        
        public void actionPerformed(final ActionEvent actionEvent) {
            if (JSVGViewerFrame.this.localHistory.canGoForward()) {
                JSVGViewerFrame.this.localHistory.forward();
            }
        }
        
        public void addJComponent(final JComponent component) {
            this.components.add(component);
            component.setEnabled(false);
        }
        
        protected void update() {
            final boolean canGoForward = JSVGViewerFrame.this.localHistory.canGoForward();
            final Iterator<JComponent> iterator = (Iterator<JComponent>)this.components.iterator();
            while (iterator.hasNext()) {
                iterator.next().setEnabled(canGoForward);
            }
        }
    }
    
    public class BackAction extends AbstractAction implements JComponentModifier
    {
        List components;
        
        public BackAction() {
            this.components = new LinkedList();
        }
        
        public void actionPerformed(final ActionEvent actionEvent) {
            if (JSVGViewerFrame.this.localHistory.canGoBack()) {
                JSVGViewerFrame.this.localHistory.back();
            }
        }
        
        public void addJComponent(final JComponent component) {
            this.components.add(component);
            component.setEnabled(false);
        }
        
        protected void update() {
            final boolean canGoBack = JSVGViewerFrame.this.localHistory.canGoBack();
            final Iterator<JComponent> iterator = (Iterator<JComponent>)this.components.iterator();
            while (iterator.hasNext()) {
                iterator.next().setEnabled(canGoBack);
            }
        }
    }
    
    public class ReloadAction extends AbstractAction
    {
        public void actionPerformed(final ActionEvent actionEvent) {
            if ((actionEvent.getModifiers() & 0x1) == 0x1) {
                JSVGViewerFrame.this.svgCanvas.flushImageCache();
            }
            if (JSVGViewerFrame.this.svgDocument != null) {
                JSVGViewerFrame.this.localHistory.reload();
            }
        }
    }
    
    public class CloseAction extends AbstractAction
    {
        public void actionPerformed(final ActionEvent actionEvent) {
            JSVGViewerFrame.this.application.closeJSVGViewerFrame(JSVGViewerFrame.this);
        }
    }
    
    public class PreferencesAction extends AbstractAction
    {
        public void actionPerformed(final ActionEvent actionEvent) {
            JSVGViewerFrame.this.application.showPreferenceDialog(JSVGViewerFrame.this);
        }
    }
    
    public class NewWindowAction extends AbstractAction
    {
        public void actionPerformed(final ActionEvent actionEvent) {
            final JSVGViewerFrame andShowJSVGViewerFrame = JSVGViewerFrame.this.application.createAndShowJSVGViewerFrame();
            andShowJSVGViewerFrame.autoAdjust = JSVGViewerFrame.this.autoAdjust;
            andShowJSVGViewerFrame.debug = JSVGViewerFrame.this.debug;
            andShowJSVGViewerFrame.svgCanvas.setProgressivePaint(JSVGViewerFrame.this.svgCanvas.getProgressivePaint());
            andShowJSVGViewerFrame.svgCanvas.setDoubleBufferedRendering(JSVGViewerFrame.this.svgCanvas.getDoubleBufferedRendering());
        }
    }
    
    public class OpenLocationAction extends AbstractAction
    {
        public void actionPerformed(final ActionEvent actionEvent) {
            if (JSVGViewerFrame.this.uriChooser == null) {
                (JSVGViewerFrame.this.uriChooser = new URIChooser(JSVGViewerFrame.this)).setFileFilter(new SVGFileFilter());
                JSVGViewerFrame.this.uriChooser.pack();
                final Rectangle bounds = JSVGViewerFrame.this.getBounds();
                final Dimension size = JSVGViewerFrame.this.uriChooser.getSize();
                JSVGViewerFrame.this.uriChooser.setLocation(bounds.x + (bounds.width - size.width) / 2, bounds.y + (bounds.height - size.height) / 2);
            }
            if (JSVGViewerFrame.this.uriChooser.showDialog() == 0) {
                String s = JSVGViewerFrame.this.uriChooser.getText();
                if (s == null) {
                    return;
                }
                final int index = s.indexOf(35);
                String substring = "";
                if (index != -1) {
                    substring = s.substring(index + 1);
                    s = s.substring(0, index);
                }
                if (!s.equals("")) {
                    final File file = new File(s);
                    if (file.exists()) {
                        if (file.isDirectory()) {
                            s = null;
                        }
                        else {
                            try {
                                s = file.getCanonicalPath();
                                if (s.startsWith("/")) {
                                    s = "file:" + s;
                                }
                                else {
                                    s = "file:/" + s;
                                }
                            }
                            catch (IOException ex) {}
                        }
                    }
                    if (s != null) {
                        if (JSVGViewerFrame.this.svgDocument != null) {
                            final ParsedURL parsedURL = new ParsedURL(JSVGViewerFrame.this.svgDocument.getURL());
                            final ParsedURL parsedURL2 = new ParsedURL(parsedURL, s);
                            final String fragmentIdentifier = JSVGViewerFrame.this.svgCanvas.getFragmentIdentifier();
                            if (parsedURL.equals(parsedURL2) && substring.equals(fragmentIdentifier)) {
                                return;
                            }
                        }
                        if (substring.length() != 0) {
                            s = s + '#' + substring;
                        }
                        JSVGViewerFrame.this.showSVGDocument(s);
                    }
                }
            }
        }
    }
    
    public class OpenAction extends AbstractAction
    {
        public void actionPerformed(final ActionEvent actionEvent) {
            File selectedFile = null;
            if (Platform.isOSX) {
                final FileDialog fileDialog = new FileDialog(JSVGViewerFrame.this, Resources.getString("Open.title"));
                fileDialog.setFilenameFilter(new FilenameFilter() {
                    public boolean accept(final File parent, final String child) {
                        final Iterator<SquiggleInputHandler> iterator = JSVGViewerFrame.getHandlers().iterator();
                        while (iterator.hasNext()) {
                            if (iterator.next().accept(new File(parent, child))) {
                                return true;
                            }
                        }
                        return false;
                    }
                });
                fileDialog.setVisible(true);
                final String file = fileDialog.getFile();
                if (fileDialog != null) {
                    selectedFile = new File(fileDialog.getDirectory(), file);
                }
            }
            else {
                final String property = System.getProperty(JSVGViewerFrame.PROPERTY_OS_NAME, JSVGViewerFrame.PROPERTY_OS_NAME_DEFAULT);
                final SecurityManager securityManager = System.getSecurityManager();
                JFileChooser fileChooser;
                if (JSVGViewerFrame.priorJDK1_4 && securityManager != null && property.indexOf(JSVGViewerFrame.PROPERTY_OS_WINDOWS_PREFIX) != -1) {
                    fileChooser = new JFileChooser(makeAbsolute(JSVGViewerFrame.this.currentPath), new WindowsAltFileSystemView());
                }
                else {
                    fileChooser = new JFileChooser(makeAbsolute(JSVGViewerFrame.this.currentPath));
                }
                fileChooser.setFileHidingEnabled(false);
                fileChooser.setFileSelectionMode(0);
                final Iterator<SquiggleInputHandler> iterator = JSVGViewerFrame.getHandlers().iterator();
                while (iterator.hasNext()) {
                    fileChooser.addChoosableFileFilter(new SquiggleInputHandlerFilter(iterator.next()));
                }
                if (fileChooser.showOpenDialog(JSVGViewerFrame.this) == 0) {
                    selectedFile = fileChooser.getSelectedFile();
                    JSVGViewerFrame.this.currentPath = selectedFile;
                }
            }
            if (selectedFile != null) {
                try {
                    JSVGViewerFrame.this.showSVGDocument(selectedFile.toURL().toString());
                }
                catch (MalformedURLException ex) {
                    if (JSVGViewerFrame.this.userAgent != null) {
                        JSVGViewerFrame.this.userAgent.displayError(ex);
                    }
                }
            }
        }
    }
    
    public class AboutAction extends AbstractAction
    {
        public void actionPerformed(final ActionEvent actionEvent) {
            final AboutDialog aboutDialog = new AboutDialog(JSVGViewerFrame.this);
            aboutDialog.setSize(aboutDialog.getPreferredSize());
            aboutDialog.setLocationRelativeTo(JSVGViewerFrame.this);
            aboutDialog.setVisible(true);
            aboutDialog.toFront();
        }
    }
}
