// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.apps.svgbrowser;

import javax.swing.UIManager;
import javax.swing.plaf.FontUIResource;
import java.awt.Font;
import java.net.URLDecoder;
import java.util.StringTokenizer;
import java.net.URLEncoder;
import org.apache.batik.util.XMLResourceDescriptor;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.Action;
import java.util.Iterator;
import javax.swing.JOptionPane;
import java.io.IOException;
import java.io.FileWriter;
import java.io.Reader;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import org.apache.batik.util.ParsedURL;
import org.apache.batik.swing.JSVGCanvas;
import java.awt.Dimension;
import org.apache.batik.swing.gvt.GVTTreeRendererListener;
import org.apache.batik.swing.gvt.GVTTreeRendererEvent;
import org.apache.batik.swing.gvt.GVTTreeRendererAdapter;
import org.apache.batik.swing.svg.GVTTreeBuilderListener;
import org.apache.batik.swing.svg.GVTTreeBuilderEvent;
import org.apache.batik.swing.svg.GVTTreeBuilderAdapter;
import org.apache.batik.swing.svg.SVGDocumentLoaderListener;
import org.apache.batik.swing.svg.SVGDocumentLoaderEvent;
import org.apache.batik.swing.svg.SVGDocumentLoaderAdapter;
import java.awt.Component;
import javax.swing.JProgressBar;
import java.awt.BorderLayout;
import java.net.Authenticator;
import org.apache.batik.util.PreferenceManager;
import java.io.File;
import java.util.Locale;
import java.lang.reflect.Proxy;
import java.awt.Frame;
import java.lang.reflect.Method;
import java.lang.reflect.InvocationHandler;
import org.apache.batik.util.Platform;
import java.util.LinkedList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.batik.util.ApplicationSecurityEnforcer;
import java.util.Vector;
import javax.swing.ImageIcon;
import org.apache.batik.util.resources.ResourceManager;
import java.util.ResourceBundle;

public class Main implements Application
{
    public static final String UNKNOWN_SCRIPT_TYPE_LOAD_KEY_EXTENSION = ".load";
    public static final String PROPERTY_USER_HOME = "user.home";
    public static final String PROPERTY_JAVA_SECURITY_POLICY = "java.security.policy";
    public static final String BATIK_CONFIGURATION_SUBDIRECTORY = ".batik";
    public static final String SQUIGGLE_CONFIGURATION_FILE = "preferences.xml";
    public static final String SQUIGGLE_POLICY_FILE = "__svgbrowser.policy";
    public static final String POLICY_GRANT_SCRIPT_NETWORK_ACCESS = "grant {\n  permission java.net.SocketPermission \"*\", \"listen, connect, resolve, accept\";\n};\n\n";
    public static final String POLICY_GRANT_SCRIPT_FILE_ACCESS = "grant {\n  permission java.io.FilePermission \"<<ALL FILES>>\", \"read\";\n};\n\n";
    public static final String PREFERENCE_KEY_VISITED_URI_LIST = "preference.key.visited.uri.list";
    public static final String PREFERENCE_KEY_VISITED_URI_LIST_LENGTH = "preference.key.visited.uri.list.length";
    public static final String URI_SEPARATOR = " ";
    public static final String DEFAULT_DEFAULT_FONT_FAMILY = "Arial, Helvetica, sans-serif";
    public static final String SVG_INITIALIZATION = "resources/init.svg";
    protected String svgInitializationURI;
    public static final String RESOURCES = "org.apache.batik.apps.svgbrowser.resources.Main";
    public static final String SQUIGGLE_SECURITY_POLICY = "org/apache/batik/apps/svgbrowser/resources/svgbrowser.policy";
    protected static ResourceBundle bundle;
    protected static ResourceManager resources;
    protected static ImageIcon frameIcon;
    protected XMLPreferenceManager preferenceManager;
    public static final int MAX_VISITED_URIS = 10;
    protected Vector lastVisited;
    protected int maxVisitedURIs;
    protected String[] arguments;
    protected boolean overrideSecurityPolicy;
    protected ApplicationSecurityEnforcer securityEnforcer;
    protected Map handlers;
    protected List viewerFrames;
    protected PreferenceDialog preferenceDialog;
    protected String uiSpecialization;
    
    public static void main(final String[] array) {
        new Main(array);
    }
    
    public Main(final String[] arguments) {
        this.lastVisited = new Vector();
        this.maxVisitedURIs = 10;
        this.overrideSecurityPolicy = false;
        (this.handlers = new HashMap()).put("-font-size", new FontSizeHandler());
        this.viewerFrames = new LinkedList();
        this.arguments = arguments;
        if (Platform.isOSX) {
            this.uiSpecialization = "OSX";
            System.setProperty("apple.laf.useScreenMenuBar", "true");
            try {
                final Class<?> forName = Class.forName("com.apple.eawt.Application");
                final Class<?> forName2 = Class.forName("com.apple.eawt.ApplicationListener");
                final Class<?> forName3 = Class.forName("com.apple.eawt.ApplicationEvent");
                final Method method = forName.getMethod("getApplication", (Class[])new Class[0]);
                final Method method2 = forName.getMethod("addApplicationListener", forName2);
                final Method method3 = forName3.getMethod("setHandled", Boolean.TYPE);
                final Method method4 = forName.getMethod("setEnabledPreferencesMenu", Boolean.TYPE);
                final InvocationHandler h = new InvocationHandler() {
                    public Object invoke(final Object o, final Method method, final Object[] array) {
                        final String name = method.getName();
                        if (name.equals("handleAbout")) {
                            final JSVGViewerFrame locationRelativeTo = Main.this.viewerFrames.isEmpty() ? null : Main.this.viewerFrames.get(0);
                            final AboutDialog aboutDialog = new AboutDialog(locationRelativeTo);
                            aboutDialog.setSize(aboutDialog.getPreferredSize());
                            aboutDialog.setLocationRelativeTo(locationRelativeTo);
                            aboutDialog.setVisible(true);
                            aboutDialog.toFront();
                        }
                        else if (name.equals("handlePreferences")) {
                            Main.this.showPreferenceDialog(Main.this.viewerFrames.isEmpty() ? null : Main.this.viewerFrames.get(0));
                        }
                        else if (!name.equals("handleQuit")) {
                            return null;
                        }
                        try {
                            method3.invoke(array[0], Boolean.TRUE);
                        }
                        catch (Exception ex) {}
                        return null;
                    }
                };
                final Object invoke = method.invoke(null, (Object[])null);
                method4.invoke(invoke, Boolean.TRUE);
                method2.invoke(invoke, Proxy.newProxyInstance(Main.class.getClassLoader(), new Class[] { forName2 }, h));
            }
            catch (Exception ex) {
                ex.printStackTrace();
                this.uiSpecialization = null;
            }
        }
        final HashMap<String, Float> hashMap = new HashMap<String, Float>(11);
        hashMap.put("preference.key.languages", (Float)Locale.getDefault().getLanguage());
        hashMap.put("preference.key.show.rendering", (Float)(Object)Boolean.FALSE);
        hashMap.put("preference.key.auto.adjust.window", (Float)(Object)Boolean.TRUE);
        hashMap.put("preference.key.selection.xor.mode", (Float)(Object)Boolean.FALSE);
        hashMap.put("preference.key.enable.double.buffering", (Float)(Object)Boolean.TRUE);
        hashMap.put("preference.key.show.debug.trace", (Float)(Object)Boolean.FALSE);
        hashMap.put("preference.key.proxy.host", (Float)"");
        hashMap.put("preference.key.proxy.port", (Float)"");
        hashMap.put("preference.key.cssmedia", (Float)"screen");
        hashMap.put("preference.key.default.font.family", (Float)"Arial, Helvetica, sans-serif");
        hashMap.put("preference.key.is.xml.parser.validating", (Float)(Object)Boolean.FALSE);
        hashMap.put("preference.key.enforce.secure.scripting", (Float)(Object)Boolean.TRUE);
        hashMap.put("preference.key.grant.script.file.access", (Float)(Object)Boolean.FALSE);
        hashMap.put("preference.key.grant.script.network.access", (Float)(Object)Boolean.FALSE);
        hashMap.put("preference.key.load.java.script", (Float)(Object)Boolean.TRUE);
        hashMap.put("preference.key.load.ecmascript", (Float)(Object)Boolean.TRUE);
        hashMap.put("preference.key.allowed.script.origin", (Float)(Object)new Integer(2));
        hashMap.put("preference.key.allowed.external.resource.origin", (Float)(Object)new Integer(1));
        hashMap.put("preference.key.visited.uri.list", (Float)"");
        hashMap.put("preference.key.visited.uri.list.length", (Float)(Object)new Integer(10));
        hashMap.put("preference.key.animation.rate.limiting.mode", (Float)(Object)new Integer(1));
        hashMap.put("preference.key.animation.rate.limiting.cpu", new Float(0.75f));
        hashMap.put("preference.key.animation.rate.limiting.fps", new Float(10.0f));
        hashMap.put("preference.key.user.stylesheet.enabled", (Float)(Object)Boolean.TRUE);
        this.securityEnforcer = new ApplicationSecurityEnforcer(this.getClass(), "org/apache/batik/apps/svgbrowser/resources/svgbrowser.policy");
        try {
            this.preferenceManager = new XMLPreferenceManager("preferences.xml", hashMap);
            final File file = new File(System.getProperty("user.home"), ".batik");
            file.mkdir();
            PreferenceManager.setPreferenceDirectory(file.getCanonicalPath());
            this.preferenceManager.load();
            this.setPreferences();
            this.initializeLastVisited();
            Authenticator.setDefault(new JAuthenticator());
        }
        catch (Exception ex2) {
            ex2.printStackTrace();
        }
        final AboutDialog aboutDialog = new AboutDialog();
        ((BorderLayout)aboutDialog.getContentPane().getLayout()).setVgap(8);
        final JProgressBar comp = new JProgressBar(0, 3);
        aboutDialog.getContentPane().add(comp, "South");
        final Dimension screenSize = aboutDialog.getToolkit().getScreenSize();
        final Dimension preferredSize = aboutDialog.getPreferredSize();
        aboutDialog.setLocation((screenSize.width - preferredSize.width) / 2, (screenSize.height - preferredSize.height) / 2);
        aboutDialog.setSize(preferredSize);
        aboutDialog.setVisible(true);
        final JSVGViewerFrame jsvgViewerFrame = new JSVGViewerFrame(this);
        final JSVGCanvas jsvgCanvas = jsvgViewerFrame.getJSVGCanvas();
        jsvgCanvas.addSVGDocumentLoaderListener(new SVGDocumentLoaderAdapter() {
            public void documentLoadingStarted(final SVGDocumentLoaderEvent svgDocumentLoaderEvent) {
                comp.setValue(1);
            }
            
            public void documentLoadingCompleted(final SVGDocumentLoaderEvent svgDocumentLoaderEvent) {
                comp.setValue(2);
            }
        });
        jsvgCanvas.addGVTTreeBuilderListener(new GVTTreeBuilderAdapter() {
            public void gvtBuildCompleted(final GVTTreeBuilderEvent gvtTreeBuilderEvent) {
                comp.setValue(3);
            }
        });
        jsvgCanvas.addGVTTreeRendererListener(new GVTTreeRendererAdapter() {
            public void gvtRenderingCompleted(final GVTTreeRendererEvent gvtTreeRendererEvent) {
                aboutDialog.dispose();
                jsvgViewerFrame.dispose();
                System.gc();
                Main.this.run();
            }
        });
        jsvgCanvas.setSize(100, 100);
        jsvgCanvas.loadSVGDocument(this.svgInitializationURI = Main.class.getResource("resources/init.svg").toString());
    }
    
    public void installCustomPolicyFile() throws IOException {
        final String property = System.getProperty("java.security.policy");
        if (this.overrideSecurityPolicy || property == null || "".equals(property)) {
            final ParsedURL parsedURL = new ParsedURL(this.securityEnforcer.getPolicyURL());
            final File file = new File(new File(System.getProperty("user.home"), ".batik"), "__svgbrowser.policy");
            final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(parsedURL.openStream()));
            final FileWriter fileWriter = new FileWriter(file);
            final char[] array = new char[1024];
            int read;
            while ((read = bufferedReader.read(array, 0, array.length)) != -1) {
                fileWriter.write(array, 0, read);
            }
            bufferedReader.close();
            final boolean boolean1 = this.preferenceManager.getBoolean("preference.key.grant.script.network.access");
            final boolean boolean2 = this.preferenceManager.getBoolean("preference.key.grant.script.file.access");
            if (boolean1) {
                fileWriter.write("grant {\n  permission java.net.SocketPermission \"*\", \"listen, connect, resolve, accept\";\n};\n\n");
            }
            if (boolean2) {
                fileWriter.write("grant {\n  permission java.io.FilePermission \"<<ALL FILES>>\", \"read\";\n};\n\n");
            }
            fileWriter.close();
            this.overrideSecurityPolicy = true;
            System.setProperty("java.security.policy", file.toURL().toString());
        }
    }
    
    public void run() {
        try {
            int i;
            OptionHandler optionHandler;
            for (i = 0; i < this.arguments.length; i = optionHandler.handleOption(i), ++i) {
                optionHandler = this.handlers.get(this.arguments[i]);
                if (optionHandler == null) {
                    break;
                }
            }
            JSVGViewerFrame parentComponent = this.createAndShowJSVGViewerFrame();
            while (i < this.arguments.length) {
                if (this.arguments[i].length() == 0) {
                    ++i;
                }
                else {
                    final File file = new File(this.arguments[i]);
                    String string = null;
                    try {
                        if (file.canRead()) {
                            string = file.toURL().toString();
                        }
                    }
                    catch (SecurityException ex2) {}
                    if (string == null) {
                        string = this.arguments[i];
                        if (!new ParsedURL(this.arguments[i]).complete()) {
                            string = null;
                        }
                    }
                    if (string != null) {
                        if (parentComponent == null) {
                            parentComponent = this.createAndShowJSVGViewerFrame();
                        }
                        parentComponent.showSVGDocument(string);
                        parentComponent = null;
                    }
                    else {
                        JOptionPane.showMessageDialog(parentComponent, Main.resources.getString("Error.skipping.file") + this.arguments[i]);
                    }
                    ++i;
                }
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
            this.printUsage();
        }
    }
    
    protected void printUsage() {
        System.out.println();
        System.out.println(Main.resources.getString("Command.header"));
        System.out.println(Main.resources.getString("Command.syntax"));
        System.out.println();
        System.out.println(Main.resources.getString("Command.options"));
        final Iterator<String> iterator = this.handlers.keySet().iterator();
        while (iterator.hasNext()) {
            System.out.println(((OptionHandler)this.handlers.get(iterator.next())).getDescription());
        }
    }
    
    public JSVGViewerFrame createAndShowJSVGViewerFrame() {
        final JSVGViewerFrame preferences = new JSVGViewerFrame(this);
        preferences.setSize(Main.resources.getInteger("Frame.width"), Main.resources.getInteger("Frame.height"));
        preferences.setIconImage(Main.frameIcon.getImage());
        preferences.setTitle(Main.resources.getString("Frame.title"));
        preferences.setVisible(true);
        this.viewerFrames.add(preferences);
        this.setPreferences(preferences);
        return preferences;
    }
    
    public void closeJSVGViewerFrame(final JSVGViewerFrame jsvgViewerFrame) {
        jsvgViewerFrame.getJSVGCanvas().stopProcessing();
        this.viewerFrames.remove(jsvgViewerFrame);
        if (this.viewerFrames.size() == 0) {
            System.exit(0);
        }
        jsvgViewerFrame.dispose();
    }
    
    public Action createExitAction(final JSVGViewerFrame jsvgViewerFrame) {
        return new AbstractAction() {
            public void actionPerformed(final ActionEvent actionEvent) {
                System.exit(0);
            }
        };
    }
    
    public void openLink(final String s) {
        this.createAndShowJSVGViewerFrame().getJSVGCanvas().loadSVGDocument(s);
    }
    
    public String getXMLParserClassName() {
        return XMLResourceDescriptor.getXMLParserClassName();
    }
    
    public boolean isXMLParserValidating() {
        return this.preferenceManager.getBoolean("preference.key.is.xml.parser.validating");
    }
    
    public void showPreferenceDialog(final JSVGViewerFrame jsvgViewerFrame) {
        if (this.preferenceDialog == null) {
            this.preferenceDialog = new PreferenceDialog(jsvgViewerFrame, this.preferenceManager);
        }
        if (this.preferenceDialog.showDialog() == 0) {
            try {
                this.preferenceManager.save();
                this.setPreferences();
            }
            catch (Exception ex) {}
        }
    }
    
    private void setPreferences() throws IOException {
        final Iterator<JSVGViewerFrame> iterator = this.viewerFrames.iterator();
        while (iterator.hasNext()) {
            this.setPreferences(iterator.next());
        }
        System.setProperty("proxyHost", this.preferenceManager.getString("preference.key.proxy.host"));
        System.setProperty("proxyPort", this.preferenceManager.getString("preference.key.proxy.port"));
        this.installCustomPolicyFile();
        this.securityEnforcer.enforceSecurity(this.preferenceManager.getBoolean("preference.key.enforce.secure.scripting"));
    }
    
    private void setPreferences(final JSVGViewerFrame jsvgViewerFrame) {
        jsvgViewerFrame.getJSVGCanvas().setDoubleBufferedRendering(this.preferenceManager.getBoolean("preference.key.enable.double.buffering"));
        jsvgViewerFrame.getJSVGCanvas().setProgressivePaint(this.preferenceManager.getBoolean("preference.key.show.rendering"));
        jsvgViewerFrame.setDebug(this.preferenceManager.getBoolean("preference.key.show.debug.trace"));
        jsvgViewerFrame.setAutoAdjust(this.preferenceManager.getBoolean("preference.key.auto.adjust.window"));
        jsvgViewerFrame.getJSVGCanvas().setSelectionOverlayXORMode(this.preferenceManager.getBoolean("preference.key.selection.xor.mode"));
        int integer = this.preferenceManager.getInteger("preference.key.animation.rate.limiting.mode");
        if (integer < 0 || integer > 2) {
            integer = 1;
        }
        switch (integer) {
            case 0: {
                jsvgViewerFrame.getJSVGCanvas().setAnimationLimitingNone();
                break;
            }
            case 1: {
                float float1 = this.preferenceManager.getFloat("preference.key.animation.rate.limiting.cpu");
                if (float1 <= 0.0f || float1 > 1.0f) {
                    float1 = 0.75f;
                }
                jsvgViewerFrame.getJSVGCanvas().setAnimationLimitingCPU(float1);
                break;
            }
            case 2: {
                float float2 = this.preferenceManager.getFloat("preference.key.animation.rate.limiting.fps");
                if (float2 <= 0.0f) {
                    float2 = 10.0f;
                }
                jsvgViewerFrame.getJSVGCanvas().setAnimationLimitingFPS(float2);
                break;
            }
        }
    }
    
    public String getLanguages() {
        final String string = this.preferenceManager.getString("preference.key.languages");
        return (string == null) ? Locale.getDefault().getLanguage() : string;
    }
    
    public String getUserStyleSheetURI() {
        final boolean boolean1 = this.preferenceManager.getBoolean("preference.key.user.stylesheet.enabled");
        final String string = this.preferenceManager.getString("preference.key.user.stylesheet");
        if (!boolean1 || string.length() == 0) {
            return null;
        }
        try {
            final File file = new File(string);
            if (file.exists()) {
                return file.toURL().toString();
            }
        }
        catch (IOException ex) {}
        return string;
    }
    
    public String getDefaultFontFamily() {
        return this.preferenceManager.getString("preference.key.default.font.family");
    }
    
    public String getMedia() {
        final String string = this.preferenceManager.getString("preference.key.cssmedia");
        return (string == null) ? "screen" : string;
    }
    
    public boolean isSelectionOverlayXORMode() {
        return this.preferenceManager.getBoolean("preference.key.selection.xor.mode");
    }
    
    public boolean canLoadScriptType(final String s) {
        if ("text/ecmascript".equals(s) || "application/ecmascript".equals(s) || "text/javascript".equals(s) || "application/javascript".equals(s)) {
            return this.preferenceManager.getBoolean("preference.key.load.ecmascript");
        }
        if ("application/java-archive".equals(s)) {
            return this.preferenceManager.getBoolean("preference.key.load.java.script");
        }
        return this.preferenceManager.getBoolean(s + ".load");
    }
    
    public int getAllowedScriptOrigin() {
        return this.preferenceManager.getInteger("preference.key.allowed.script.origin");
    }
    
    public int getAllowedExternalResourceOrigin() {
        return this.preferenceManager.getInteger("preference.key.allowed.external.resource.origin");
    }
    
    public void addVisitedURI(final String s) {
        if (this.svgInitializationURI.equals(s)) {
            return;
        }
        int integer = this.preferenceManager.getInteger("preference.key.visited.uri.list.length");
        if (integer < 0) {
            integer = 0;
        }
        if (this.lastVisited.contains(s)) {
            this.lastVisited.removeElement(s);
        }
        while (this.lastVisited.size() > 0 && this.lastVisited.size() > integer - 1) {
            this.lastVisited.removeElementAt(0);
        }
        if (integer > 0) {
            this.lastVisited.addElement(s);
        }
        final StringBuffer sb = new StringBuffer(this.lastVisited.size() * 8);
        for (int i = 0; i < this.lastVisited.size(); ++i) {
            sb.append(URLEncoder.encode(this.lastVisited.get(i).toString()));
            sb.append(" ");
        }
        this.preferenceManager.setString("preference.key.visited.uri.list", sb.toString());
        try {
            this.preferenceManager.save();
        }
        catch (Exception ex) {}
    }
    
    public String[] getVisitedURIs() {
        final String[] a = new String[this.lastVisited.size()];
        this.lastVisited.toArray(a);
        return a;
    }
    
    public String getUISpecialization() {
        return this.uiSpecialization;
    }
    
    protected void initializeLastVisited() {
        final StringTokenizer stringTokenizer = new StringTokenizer(this.preferenceManager.getString("preference.key.visited.uri.list"), " ");
        int countTokens = stringTokenizer.countTokens();
        final int integer = this.preferenceManager.getInteger("preference.key.visited.uri.list.length");
        if (countTokens > integer) {
            countTokens = integer;
        }
        for (int i = 0; i < countTokens; ++i) {
            this.lastVisited.addElement(URLDecoder.decode(stringTokenizer.nextToken()));
        }
    }
    
    static {
        Main.bundle = ResourceBundle.getBundle("org.apache.batik.apps.svgbrowser.resources.Main", Locale.getDefault());
        Main.resources = new ResourceManager(Main.bundle);
        Main.frameIcon = new ImageIcon(Main.class.getResource(Main.resources.getString("Frame.icon")));
    }
    
    protected class FontSizeHandler implements OptionHandler
    {
        public int handleOption(int n) {
            final FontUIResource fontUIResource = new FontUIResource(new Font("Dialog", 0, Integer.parseInt(Main.this.arguments[++n])));
            UIManager.put("CheckBox.font", fontUIResource);
            UIManager.put("PopupMenu.font", fontUIResource);
            UIManager.put("TextPane.font", fontUIResource);
            UIManager.put("MenuItem.font", fontUIResource);
            UIManager.put("ComboBox.font", fontUIResource);
            UIManager.put("Button.font", fontUIResource);
            UIManager.put("Tree.font", fontUIResource);
            UIManager.put("ScrollPane.font", fontUIResource);
            UIManager.put("TabbedPane.font", fontUIResource);
            UIManager.put("EditorPane.font", fontUIResource);
            UIManager.put("TitledBorder.font", fontUIResource);
            UIManager.put("Menu.font", fontUIResource);
            UIManager.put("TextArea.font", fontUIResource);
            UIManager.put("OptionPane.font", fontUIResource);
            UIManager.put("DesktopIcon.font", fontUIResource);
            UIManager.put("MenuBar.font", fontUIResource);
            UIManager.put("ToolBar.font", fontUIResource);
            UIManager.put("RadioButton.font", fontUIResource);
            UIManager.put("RadioButtonMenuItem.font", fontUIResource);
            UIManager.put("ToggleButton.font", fontUIResource);
            UIManager.put("ToolTip.font", fontUIResource);
            UIManager.put("ProgressBar.font", fontUIResource);
            UIManager.put("TableHeader.font", fontUIResource);
            UIManager.put("Panel.font", fontUIResource);
            UIManager.put("List.font", fontUIResource);
            UIManager.put("ColorChooser.font", fontUIResource);
            UIManager.put("PasswordField.font", fontUIResource);
            UIManager.put("TextField.font", fontUIResource);
            UIManager.put("Table.font", fontUIResource);
            UIManager.put("Label.font", fontUIResource);
            UIManager.put("InternalFrameTitlePane.font", fontUIResource);
            UIManager.put("CheckBoxMenuItem.font", fontUIResource);
            return n;
        }
        
        public String getDescription() {
            return Main.resources.getString("Command.font-size");
        }
    }
    
    protected interface OptionHandler
    {
        int handleOption(final int p0);
        
        String getDescription();
    }
}
