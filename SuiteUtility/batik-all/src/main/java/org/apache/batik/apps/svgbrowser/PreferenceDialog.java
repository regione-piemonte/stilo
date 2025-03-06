// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.apps.svgbrowser;

import java.awt.Container;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.border.Border;
import javax.swing.JToggleButton;
import java.awt.CardLayout;
import javax.swing.JToolBar;
import java.awt.Font;
import org.apache.batik.util.gui.CSSMediaPanel;
import javax.swing.JScrollPane;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.ListModel;
import java.io.IOException;
import javax.swing.JFileChooser;
import java.io.File;
import java.awt.FileDialog;
import java.awt.Color;
import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import java.awt.Dimension;
import javax.swing.BorderFactory;
import java.awt.Insets;
import org.apache.batik.ext.swing.JGridBagPanel;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.FlowLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import java.awt.Component;
import java.awt.LayoutManager;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.util.Enumeration;
import java.util.StringTokenizer;
import java.awt.event.WindowListener;
import org.apache.batik.util.Platform;
import java.awt.event.WindowEvent;
import java.awt.event.WindowAdapter;
import java.awt.Frame;
import javax.swing.UIManager;
import javax.swing.DefaultListModel;
import org.apache.batik.util.gui.LanguageDialog;
import javax.swing.JList;
import javax.swing.JComboBox;
import javax.swing.JRadioButton;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JCheckBox;
import org.apache.batik.util.PreferenceManager;
import org.apache.batik.ext.swing.GridBagConstants;
import javax.swing.JDialog;

public class PreferenceDialog extends JDialog implements GridBagConstants
{
    public static final int OK_OPTION = 0;
    public static final int CANCEL_OPTION = 1;
    public static final String PREFERENCE_KEY_TITLE_PREFIX = "PreferenceDialog.title.";
    public static final String PREFERENCE_KEY_TITLE_DIALOG = "PreferenceDialog.title.dialog";
    public static final String PREFERENCE_KEY_LABEL_RENDERING_OPTIONS = "PreferenceDialog.label.rendering.options";
    public static final String PREFERENCE_KEY_LABEL_ANIMATION_RATE_LIMITING = "PreferenceDialog.label.animation.rate.limiting";
    public static final String PREFERENCE_KEY_LABEL_OTHER_OPTIONS = "PreferenceDialog.label.other.options";
    public static final String PREFERENCE_KEY_LABEL_ENABLE_DOUBLE_BUFFERING = "PreferenceDialog.label.enable.double.buffering";
    public static final String PREFERENCE_KEY_LABEL_SHOW_RENDERING = "PreferenceDialog.label.show.rendering";
    public static final String PREFERENCE_KEY_LABEL_AUTO_ADJUST_WINDOW = "PreferenceDialog.label.auto.adjust.window";
    public static final String PREFERENCE_KEY_LABEL_SELECTION_XOR_MODE = "PreferenceDialog.label.selection.xor.mode";
    public static final String PREFERENCE_KEY_LABEL_ANIMATION_LIMIT_CPU = "PreferenceDialog.label.animation.limit.cpu";
    public static final String PREFERENCE_KEY_LABEL_PERCENT = "PreferenceDialog.label.percent";
    public static final String PREFERENCE_KEY_LABEL_ANIMATION_LIMIT_FPS = "PreferenceDialog.label.animation.limit.fps";
    public static final String PREFERENCE_KEY_LABEL_FPS = "PreferenceDialog.label.fps";
    public static final String PREFERENCE_KEY_LABEL_ANIMATION_LIMIT_UNLIMITED = "PreferenceDialog.label.animation.limit.unlimited";
    public static final String PREFERENCE_KEY_LABEL_SHOW_DEBUG_TRACE = "PreferenceDialog.label.show.debug.trace";
    public static final String PREFERENCE_KEY_LABEL_IS_XML_PARSER_VALIDATING = "PreferenceDialog.label.is.xml.parser.validating";
    public static final String PREFERENCE_KEY_LABEL_GRANT_SCRIPTS_ACCESS_TO = "PreferenceDialog.label.grant.scripts.access.to";
    public static final String PREFERENCE_KEY_LABEL_LOAD_SCRIPTS = "PreferenceDialog.label.load.scripts";
    public static final String PREFERENCE_KEY_LABEL_ALLOWED_SCRIPT_ORIGIN = "PreferenceDialog.label.allowed.script.origin";
    public static final String PREFERENCE_KEY_LABEL_ALLOWED_RESOURCE_ORIGIN = "PreferenceDialog.label.allowed.resource.origin";
    public static final String PREFERENCE_KEY_LABEL_ENFORCE_SECURE_SCRIPTING = "PreferenceDialog.label.enforce.secure.scripting";
    public static final String PREFERENCE_KEY_LABEL_FILE_SYSTEM = "PreferenceDialog.label.file.system";
    public static final String PREFERENCE_KEY_LABEL_ALL_NETWORK = "PreferenceDialog.label.all.network";
    public static final String PREFERENCE_KEY_LABEL_JAVA_JAR_FILES = "PreferenceDialog.label.java.jar.files";
    public static final String PREFERENCE_KEY_LABEL_ECMASCRIPT = "PreferenceDialog.label.ecmascript";
    public static final String PREFERENCE_KEY_LABEL_ORIGIN_ANY = "PreferenceDialog.label.origin.any";
    public static final String PREFERENCE_KEY_LABEL_ORIGIN_DOCUMENT = "PreferenceDialog.label.origin.document";
    public static final String PREFERENCE_KEY_LABEL_ORIGIN_EMBEDDED = "PreferenceDialog.label.origin.embedded";
    public static final String PREFERENCE_KEY_LABEL_ORIGIN_NONE = "PreferenceDialog.label.origin.none";
    public static final String PREFERENCE_KEY_LABEL_USER_STYLESHEET = "PreferenceDialog.label.user.stylesheet";
    public static final String PREFERENCE_KEY_LABEL_CSS_MEDIA_TYPES = "PreferenceDialog.label.css.media.types";
    public static final String PREFERENCE_KEY_LABEL_ENABLE_USER_STYLESHEET = "PreferenceDialog.label.enable.user.stylesheet";
    public static final String PREFERENCE_KEY_LABEL_BROWSE = "PreferenceDialog.label.browse";
    public static final String PREFERENCE_KEY_LABEL_ADD = "PreferenceDialog.label.add";
    public static final String PREFERENCE_KEY_LABEL_REMOVE = "PreferenceDialog.label.remove";
    public static final String PREFERENCE_KEY_LABEL_CLEAR = "PreferenceDialog.label.clear";
    public static final String PREFERENCE_KEY_LABEL_HTTP_PROXY = "PreferenceDialog.label.http.proxy";
    public static final String PREFERENCE_KEY_LABEL_HOST = "PreferenceDialog.label.host";
    public static final String PREFERENCE_KEY_LABEL_PORT = "PreferenceDialog.label.port";
    public static final String PREFERENCE_KEY_LABEL_COLON = "PreferenceDialog.label.colon";
    public static final String PREFERENCE_KEY_BROWSE_TITLE = "PreferenceDialog.BrowseWindow.title";
    public static final String PREFERENCE_KEY_LANGUAGES = "preference.key.languages";
    public static final String PREFERENCE_KEY_IS_XML_PARSER_VALIDATING = "preference.key.is.xml.parser.validating";
    public static final String PREFERENCE_KEY_USER_STYLESHEET = "preference.key.user.stylesheet";
    public static final String PREFERENCE_KEY_USER_STYLESHEET_ENABLED = "preference.key.user.stylesheet.enabled";
    public static final String PREFERENCE_KEY_SHOW_RENDERING = "preference.key.show.rendering";
    public static final String PREFERENCE_KEY_AUTO_ADJUST_WINDOW = "preference.key.auto.adjust.window";
    public static final String PREFERENCE_KEY_ENABLE_DOUBLE_BUFFERING = "preference.key.enable.double.buffering";
    public static final String PREFERENCE_KEY_SHOW_DEBUG_TRACE = "preference.key.show.debug.trace";
    public static final String PREFERENCE_KEY_SELECTION_XOR_MODE = "preference.key.selection.xor.mode";
    public static final String PREFERENCE_KEY_PROXY_HOST = "preference.key.proxy.host";
    public static final String PREFERENCE_KEY_CSS_MEDIA = "preference.key.cssmedia";
    public static final String PREFERENCE_KEY_DEFAULT_FONT_FAMILY = "preference.key.default.font.family";
    public static final String PREFERENCE_KEY_PROXY_PORT = "preference.key.proxy.port";
    public static final String PREFERENCE_KEY_ENFORCE_SECURE_SCRIPTING = "preference.key.enforce.secure.scripting";
    public static final String PREFERENCE_KEY_GRANT_SCRIPT_FILE_ACCESS = "preference.key.grant.script.file.access";
    public static final String PREFERENCE_KEY_GRANT_SCRIPT_NETWORK_ACCESS = "preference.key.grant.script.network.access";
    public static final String PREFERENCE_KEY_LOAD_ECMASCRIPT = "preference.key.load.ecmascript";
    public static final String PREFERENCE_KEY_LOAD_JAVA = "preference.key.load.java.script";
    public static final String PREFERENCE_KEY_ALLOWED_SCRIPT_ORIGIN = "preference.key.allowed.script.origin";
    public static final String PREFERENCE_KEY_ALLOWED_EXTERNAL_RESOURCE_ORIGIN = "preference.key.allowed.external.resource.origin";
    public static final String PREFERENCE_KEY_ANIMATION_RATE_LIMITING_MODE = "preference.key.animation.rate.limiting.mode";
    public static final String PREFERENCE_KEY_ANIMATION_RATE_LIMITING_CPU = "preference.key.animation.rate.limiting.cpu";
    public static final String PREFERENCE_KEY_ANIMATION_RATE_LIMITING_FPS = "preference.key.animation.rate.limiting.fps";
    public static final String LABEL_OK = "PreferenceDialog.label.ok";
    public static final String LABEL_CANCEL = "PreferenceDialog.label.cancel";
    protected PreferenceManager model;
    protected JConfigurationPanel configurationPanel;
    protected JCheckBox userStylesheetEnabled;
    protected JLabel userStylesheetLabel;
    protected JTextField userStylesheet;
    protected JButton userStylesheetBrowse;
    protected JCheckBox showRendering;
    protected JCheckBox autoAdjustWindow;
    protected JCheckBox enableDoubleBuffering;
    protected JCheckBox showDebugTrace;
    protected JCheckBox selectionXorMode;
    protected JCheckBox isXMLParserValidating;
    protected JRadioButton animationLimitUnlimited;
    protected JRadioButton animationLimitCPU;
    protected JRadioButton animationLimitFPS;
    protected JLabel animationLimitCPULabel;
    protected JLabel animationLimitFPSLabel;
    protected JTextField animationLimitCPUAmount;
    protected JTextField animationLimitFPSAmount;
    protected JCheckBox enforceSecureScripting;
    protected JCheckBox grantScriptFileAccess;
    protected JCheckBox grantScriptNetworkAccess;
    protected JCheckBox loadJava;
    protected JCheckBox loadEcmascript;
    protected JComboBox allowedScriptOrigin;
    protected JComboBox allowedResourceOrigin;
    protected JList mediaList;
    protected JButton mediaListRemoveButton;
    protected JButton mediaListClearButton;
    protected JTextField host;
    protected JTextField port;
    protected LanguageDialog.Panel languagePanel;
    protected DefaultListModel mediaListModel;
    protected int returnCode;
    
    protected static boolean isMetalSteel() {
        if (!UIManager.getLookAndFeel().getName().equals("Metal")) {
            return false;
        }
        try {
            UIManager.getLookAndFeel().getClass().getMethod("getCurrentTheme", (Class<?>[])new Class[0]);
            return false;
        }
        catch (Exception ex) {
            return true;
        }
    }
    
    public PreferenceDialog(final Frame owner, final PreferenceManager model) {
        super(owner, true);
        this.mediaListModel = new DefaultListModel();
        if (model == null) {
            throw new IllegalArgumentException();
        }
        this.model = model;
        this.buildGUI();
        this.initializeGUI();
        this.pack();
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(final WindowEvent windowEvent) {
                if (Platform.isOSX) {
                    PreferenceDialog.this.savePreferences();
                }
            }
        });
    }
    
    public PreferenceManager getPreferenceManager() {
        return this.model;
    }
    
    protected void initializeGUI() {
        this.enableDoubleBuffering.setSelected(this.model.getBoolean("preference.key.enable.double.buffering"));
        this.showRendering.setSelected(this.model.getBoolean("preference.key.show.rendering"));
        this.autoAdjustWindow.setSelected(this.model.getBoolean("preference.key.auto.adjust.window"));
        this.selectionXorMode.setSelected(this.model.getBoolean("preference.key.selection.xor.mode"));
        switch (this.model.getInteger("preference.key.animation.rate.limiting.mode")) {
            case 0: {
                this.animationLimitUnlimited.setSelected(true);
                break;
            }
            case 2: {
                this.animationLimitFPS.setSelected(true);
                break;
            }
            default: {
                this.animationLimitCPU.setSelected(true);
                break;
            }
        }
        final float float1 = this.model.getFloat("preference.key.animation.rate.limiting.cpu");
        float f;
        if (float1 <= 0.0f || float1 > 100.0f) {
            f = 85.0f;
        }
        else {
            f = float1 * 100.0f;
        }
        if ((int)f == f) {
            this.animationLimitCPUAmount.setText(Integer.toString((int)f));
        }
        else {
            this.animationLimitCPUAmount.setText(Float.toString(f));
        }
        float float2 = this.model.getFloat("preference.key.animation.rate.limiting.fps");
        if (float2 <= 0.0f) {
            float2 = 10.0f;
        }
        if ((int)float2 == float2) {
            this.animationLimitFPSAmount.setText(Integer.toString((int)float2));
        }
        else {
            this.animationLimitFPSAmount.setText(Float.toString(float2));
        }
        this.showDebugTrace.setSelected(this.model.getBoolean("preference.key.show.debug.trace"));
        this.isXMLParserValidating.setSelected(this.model.getBoolean("preference.key.is.xml.parser.validating"));
        this.enforceSecureScripting.setSelected(this.model.getBoolean("preference.key.enforce.secure.scripting"));
        this.grantScriptFileAccess.setSelected(this.model.getBoolean("preference.key.grant.script.file.access"));
        this.grantScriptNetworkAccess.setSelected(this.model.getBoolean("preference.key.grant.script.network.access"));
        this.loadJava.setSelected(this.model.getBoolean("preference.key.load.java.script"));
        this.loadEcmascript.setSelected(this.model.getBoolean("preference.key.load.ecmascript"));
        switch (this.model.getInteger("preference.key.allowed.script.origin")) {
            case 1: {
                this.allowedScriptOrigin.setSelectedIndex(0);
                break;
            }
            case 2: {
                this.allowedScriptOrigin.setSelectedIndex(1);
                break;
            }
            case 4: {
                this.allowedScriptOrigin.setSelectedIndex(2);
                break;
            }
            default: {
                this.allowedScriptOrigin.setSelectedIndex(3);
                break;
            }
        }
        switch (this.model.getInteger("preference.key.allowed.external.resource.origin")) {
            case 1: {
                this.allowedResourceOrigin.setSelectedIndex(0);
                break;
            }
            case 2: {
                this.allowedResourceOrigin.setSelectedIndex(1);
                break;
            }
            case 4: {
                this.allowedResourceOrigin.setSelectedIndex(2);
                break;
            }
            default: {
                this.allowedResourceOrigin.setSelectedIndex(3);
                break;
            }
        }
        this.languagePanel.setLanguages(this.model.getString("preference.key.languages"));
        final String string = this.model.getString("preference.key.cssmedia");
        this.mediaListModel.removeAllElements();
        final StringTokenizer stringTokenizer = new StringTokenizer(string, " ");
        while (stringTokenizer.hasMoreTokens()) {
            this.mediaListModel.addElement(stringTokenizer.nextToken());
        }
        this.userStylesheet.setText(this.model.getString("preference.key.user.stylesheet"));
        this.userStylesheetEnabled.setSelected(this.model.getBoolean("preference.key.user.stylesheet.enabled"));
        this.host.setText(this.model.getString("preference.key.proxy.host"));
        this.port.setText(this.model.getString("preference.key.proxy.port"));
        this.showRendering.setEnabled(this.enableDoubleBuffering.isSelected());
        final boolean selected = this.animationLimitCPU.isSelected();
        this.animationLimitCPUAmount.setEnabled(selected);
        this.animationLimitCPULabel.setEnabled(selected);
        final boolean selected2 = this.animationLimitFPS.isSelected();
        this.animationLimitFPSAmount.setEnabled(selected2);
        this.animationLimitFPSLabel.setEnabled(selected2);
        final boolean selected3 = this.enforceSecureScripting.isSelected();
        this.grantScriptFileAccess.setEnabled(selected3);
        this.grantScriptNetworkAccess.setEnabled(selected3);
        final boolean selected4 = this.userStylesheetEnabled.isSelected();
        this.userStylesheetLabel.setEnabled(selected4);
        this.userStylesheet.setEnabled(selected4);
        this.userStylesheetBrowse.setEnabled(selected4);
        this.mediaListRemoveButton.setEnabled(!this.mediaList.isSelectionEmpty());
        this.mediaListClearButton.setEnabled(!this.mediaListModel.isEmpty());
    }
    
    protected void savePreferences() {
        this.model.setString("preference.key.languages", this.languagePanel.getLanguages());
        this.model.setString("preference.key.user.stylesheet", this.userStylesheet.getText());
        this.model.setBoolean("preference.key.user.stylesheet.enabled", this.userStylesheetEnabled.isSelected());
        this.model.setBoolean("preference.key.show.rendering", this.showRendering.isSelected());
        this.model.setBoolean("preference.key.auto.adjust.window", this.autoAdjustWindow.isSelected());
        this.model.setBoolean("preference.key.enable.double.buffering", this.enableDoubleBuffering.isSelected());
        this.model.setBoolean("preference.key.show.debug.trace", this.showDebugTrace.isSelected());
        this.model.setBoolean("preference.key.selection.xor.mode", this.selectionXorMode.isSelected());
        this.model.setBoolean("preference.key.is.xml.parser.validating", this.isXMLParserValidating.isSelected());
        this.model.setBoolean("preference.key.enforce.secure.scripting", this.enforceSecureScripting.isSelected());
        this.model.setBoolean("preference.key.grant.script.file.access", this.grantScriptFileAccess.isSelected());
        this.model.setBoolean("preference.key.grant.script.network.access", this.grantScriptNetworkAccess.isSelected());
        this.model.setBoolean("preference.key.load.java.script", this.loadJava.isSelected());
        this.model.setBoolean("preference.key.load.ecmascript", this.loadEcmascript.isSelected());
        int n = 0;
        switch (this.allowedScriptOrigin.getSelectedIndex()) {
            case 0: {
                n = 1;
                break;
            }
            case 1: {
                n = 2;
                break;
            }
            case 2: {
                n = 4;
                break;
            }
            default: {
                n = 8;
                break;
            }
        }
        this.model.setInteger("preference.key.allowed.script.origin", n);
        int n2 = 0;
        switch (this.allowedResourceOrigin.getSelectedIndex()) {
            case 0: {
                n2 = 1;
                break;
            }
            case 1: {
                n2 = 2;
                break;
            }
            case 2: {
                n2 = 4;
                break;
            }
            default: {
                n2 = 8;
                break;
            }
        }
        this.model.setInteger("preference.key.allowed.external.resource.origin", n2);
        int n3 = 1;
        if (this.animationLimitFPS.isSelected()) {
            n3 = 2;
        }
        else if (this.animationLimitUnlimited.isSelected()) {
            n3 = 0;
        }
        this.model.setInteger("preference.key.animation.rate.limiting.mode", n3);
        float n4;
        try {
            n4 = Float.parseFloat(this.animationLimitCPUAmount.getText()) / 100.0f;
            if (n4 <= 0.0f || n4 >= 1.0f) {
                n4 = 0.85f;
            }
        }
        catch (NumberFormatException ex) {
            n4 = 0.85f;
        }
        this.model.setFloat("preference.key.animation.rate.limiting.cpu", n4);
        float float1;
        try {
            float1 = Float.parseFloat(this.animationLimitFPSAmount.getText());
            if (float1 <= 0.0f) {
                float1 = 15.0f;
            }
        }
        catch (NumberFormatException ex2) {
            float1 = 15.0f;
        }
        this.model.setFloat("preference.key.animation.rate.limiting.fps", float1);
        this.model.setString("preference.key.proxy.host", this.host.getText());
        this.model.setString("preference.key.proxy.port", this.port.getText());
        final StringBuffer sb = new StringBuffer();
        final Enumeration<String> elements = (Enumeration<String>)this.mediaListModel.elements();
        while (elements.hasMoreElements()) {
            sb.append(elements.nextElement());
            sb.append(' ');
        }
        this.model.setString("preference.key.cssmedia", sb.toString());
    }
    
    protected void buildGUI() {
        final JPanel comp = new JPanel(new BorderLayout());
        this.configurationPanel = new JConfigurationPanel();
        this.addConfigPanel("general", this.buildGeneralPanel());
        this.addConfigPanel("security", this.buildSecurityPanel());
        this.addConfigPanel("language", this.buildLanguagePanel());
        this.addConfigPanel("stylesheet", this.buildStylesheetPanel());
        this.addConfigPanel("network", this.buildNetworkPanel());
        comp.add(this.configurationPanel);
        if (!Platform.isOSX) {
            this.setTitle(Resources.getString("PreferenceDialog.title.dialog"));
            comp.add(this.buildButtonsPanel(), "South");
        }
        this.setResizable(false);
        this.getContentPane().add(comp);
    }
    
    protected void addConfigPanel(final String str, final JPanel panel) {
        this.configurationPanel.addPanel(Resources.getString("PreferenceDialog.title." + str), new ImageIcon(PreferenceDialog.class.getResource("resources/icon-" + str + ".png")), new ImageIcon(PreferenceDialog.class.getResource("resources/icon-" + str + "-dark.png")), panel);
    }
    
    protected JPanel buildButtonsPanel() {
        final JPanel panel = new JPanel(new FlowLayout(2));
        final JButton comp = new JButton(Resources.getString("PreferenceDialog.label.ok"));
        final JButton comp2 = new JButton(Resources.getString("PreferenceDialog.label.cancel"));
        panel.add(comp);
        panel.add(comp2);
        comp.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent actionEvent) {
                PreferenceDialog.this.setVisible(false);
                PreferenceDialog.this.returnCode = 0;
                PreferenceDialog.this.savePreferences();
                PreferenceDialog.this.dispose();
            }
        });
        comp2.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent actionEvent) {
                PreferenceDialog.this.setVisible(false);
                PreferenceDialog.this.returnCode = 1;
                PreferenceDialog.this.dispose();
            }
        });
        this.addKeyListener(new KeyAdapter() {
            public void keyPressed(final KeyEvent keyEvent) {
                switch (keyEvent.getKeyCode()) {
                    case 27: {
                        PreferenceDialog.this.returnCode = 1;
                        break;
                    }
                    case 10: {
                        PreferenceDialog.this.returnCode = 0;
                        break;
                    }
                    default: {
                        return;
                    }
                }
                PreferenceDialog.this.setVisible(false);
                PreferenceDialog.this.dispose();
            }
        });
        return panel;
    }
    
    protected JPanel buildGeneralPanel() {
        final JGridBagPanel gridBagPanel = new JGridBagPanel(new JGridBagPanel.InsetsManager() {
            protected Insets i1 = new Insets(5, 5, 0, 0);
            protected Insets i2 = new Insets(5, 0, 0, 0);
            protected Insets i3 = new Insets(0, 5, 0, 0);
            protected Insets i4 = new Insets(0, 0, 0, 0);
            
            public Insets getInsets(final int n, final int n2) {
                if (n2 == 4 || n2 == 9) {
                    return (n == 0) ? this.i2 : this.i1;
                }
                return (n == 0) ? this.i4 : this.i3;
            }
        });
        gridBagPanel.setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));
        final JLabel label = new JLabel(Resources.getString("PreferenceDialog.label.rendering.options"));
        (this.enableDoubleBuffering = new JCheckBox(Resources.getString("PreferenceDialog.label.enable.double.buffering"))).addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent actionEvent) {
                PreferenceDialog.this.showRendering.setEnabled(PreferenceDialog.this.enableDoubleBuffering.isSelected());
            }
        });
        this.showRendering = new JCheckBox(Resources.getString("PreferenceDialog.label.show.rendering"));
        final Insets margin = this.showRendering.getMargin();
        this.showRendering.setMargin(new Insets(margin.top, margin.left + 24, margin.bottom, margin.right));
        this.selectionXorMode = new JCheckBox(Resources.getString("PreferenceDialog.label.selection.xor.mode"));
        this.autoAdjustWindow = new JCheckBox(Resources.getString("PreferenceDialog.label.auto.adjust.window"));
        final JLabel label2 = new JLabel(Resources.getString("PreferenceDialog.label.animation.rate.limiting"));
        this.animationLimitCPU = new JRadioButton(Resources.getString("PreferenceDialog.label.animation.limit.cpu"));
        final JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(3, 3, 0));
        panel.setBorder(BorderFactory.createEmptyBorder(0, 24, 0, 0));
        (this.animationLimitCPUAmount = new JTextField()).setPreferredSize(new Dimension(40, 20));
        panel.add(this.animationLimitCPUAmount);
        panel.add(this.animationLimitCPULabel = new JLabel(Resources.getString("PreferenceDialog.label.percent")));
        this.animationLimitFPS = new JRadioButton(Resources.getString("PreferenceDialog.label.animation.limit.fps"));
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new FlowLayout(3, 3, 0));
        panel2.setBorder(BorderFactory.createEmptyBorder(0, 24, 0, 0));
        (this.animationLimitFPSAmount = new JTextField()).setPreferredSize(new Dimension(40, 20));
        panel2.add(this.animationLimitFPSAmount);
        panel2.add(this.animationLimitFPSLabel = new JLabel(Resources.getString("PreferenceDialog.label.fps")));
        this.animationLimitUnlimited = new JRadioButton(Resources.getString("PreferenceDialog.label.animation.limit.unlimited"));
        final ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(this.animationLimitCPU);
        buttonGroup.add(this.animationLimitFPS);
        buttonGroup.add(this.animationLimitUnlimited);
        final ActionListener l = new ActionListener() {
            public void actionPerformed(final ActionEvent actionEvent) {
                final boolean selected = PreferenceDialog.this.animationLimitCPU.isSelected();
                PreferenceDialog.this.animationLimitCPUAmount.setEnabled(selected);
                PreferenceDialog.this.animationLimitCPULabel.setEnabled(selected);
                final boolean selected2 = PreferenceDialog.this.animationLimitFPS.isSelected();
                PreferenceDialog.this.animationLimitFPSAmount.setEnabled(selected2);
                PreferenceDialog.this.animationLimitFPSLabel.setEnabled(selected2);
            }
        };
        this.animationLimitCPU.addActionListener(l);
        this.animationLimitFPS.addActionListener(l);
        this.animationLimitUnlimited.addActionListener(l);
        final JLabel label3 = new JLabel(Resources.getString("PreferenceDialog.label.other.options"));
        this.showDebugTrace = new JCheckBox(Resources.getString("PreferenceDialog.label.show.debug.trace"));
        this.isXMLParserValidating = new JCheckBox(Resources.getString("PreferenceDialog.label.is.xml.parser.validating"));
        gridBagPanel.add(label, 0, 0, 1, 1, 13, 0, 0.0, 0.0);
        gridBagPanel.add(this.enableDoubleBuffering, 1, 0, 1, 1, 17, 0, 0.0, 0.0);
        gridBagPanel.add(this.showRendering, 1, 1, 1, 1, 17, 0, 0.0, 0.0);
        gridBagPanel.add(this.autoAdjustWindow, 1, 2, 1, 1, 17, 0, 0.0, 0.0);
        gridBagPanel.add(this.selectionXorMode, 1, 3, 1, 1, 17, 0, 0.0, 0.0);
        gridBagPanel.add(label2, 0, 4, 1, 1, 13, 0, 0.0, 0.0);
        gridBagPanel.add(this.animationLimitCPU, 1, 4, 1, 1, 17, 0, 0.0, 0.0);
        gridBagPanel.add(panel, 1, 5, 1, 1, 17, 0, 0.0, 0.0);
        gridBagPanel.add(this.animationLimitFPS, 1, 6, 1, 1, 17, 0, 0.0, 0.0);
        gridBagPanel.add(panel2, 1, 7, 1, 1, 17, 0, 0.0, 0.0);
        gridBagPanel.add(this.animationLimitUnlimited, 1, 8, 1, 1, 17, 0, 0.0, 0.0);
        gridBagPanel.add(label3, 0, 9, 1, 1, 13, 0, 0.0, 0.0);
        gridBagPanel.add(this.showDebugTrace, 1, 9, 1, 1, 17, 0, 0.0, 0.0);
        gridBagPanel.add(this.isXMLParserValidating, 1, 10, 1, 1, 17, 0, 0.0, 0.0);
        return gridBagPanel;
    }
    
    protected JPanel buildSecurityPanel() {
        final JGridBagPanel gridBagPanel = new JGridBagPanel(new JGridBagPanel.InsetsManager() {
            protected Insets i1 = new Insets(5, 5, 0, 0);
            protected Insets i2 = new Insets(5, 0, 0, 0);
            protected Insets i3 = new Insets(0, 5, 0, 0);
            protected Insets i4 = new Insets(0, 0, 0, 0);
            
            public Insets getInsets(final int n, final int n2) {
                if (n2 == 1 || n2 == 3 || n2 == 5 || n2 == 6) {
                    return (n == 0) ? this.i2 : this.i1;
                }
                return (n == 0) ? this.i4 : this.i3;
            }
        });
        gridBagPanel.setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));
        (this.enforceSecureScripting = new JCheckBox(Resources.getString("PreferenceDialog.label.enforce.secure.scripting"))).addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent actionEvent) {
                final boolean selected = PreferenceDialog.this.enforceSecureScripting.isSelected();
                PreferenceDialog.this.grantScriptFileAccess.setEnabled(selected);
                PreferenceDialog.this.grantScriptNetworkAccess.setEnabled(selected);
            }
        });
        final JLabel label = new JLabel(Resources.getString("PreferenceDialog.label.grant.scripts.access.to"));
        label.setVerticalAlignment(1);
        label.setOpaque(true);
        this.grantScriptFileAccess = new JCheckBox(Resources.getString("PreferenceDialog.label.file.system"));
        this.grantScriptNetworkAccess = new JCheckBox(Resources.getString("PreferenceDialog.label.all.network"));
        final JLabel label2 = new JLabel(Resources.getString("PreferenceDialog.label.load.scripts"));
        label2.setVerticalAlignment(1);
        this.loadJava = new JCheckBox(Resources.getString("PreferenceDialog.label.java.jar.files"));
        this.loadEcmascript = new JCheckBox(Resources.getString("PreferenceDialog.label.ecmascript"));
        final String[] array = { Resources.getString("PreferenceDialog.label.origin.any"), Resources.getString("PreferenceDialog.label.origin.document"), Resources.getString("PreferenceDialog.label.origin.embedded"), Resources.getString("PreferenceDialog.label.origin.none") };
        final JLabel label3 = new JLabel(Resources.getString("PreferenceDialog.label.allowed.script.origin"));
        this.allowedScriptOrigin = new JComboBox(array);
        final JLabel label4 = new JLabel(Resources.getString("PreferenceDialog.label.allowed.resource.origin"));
        this.allowedResourceOrigin = new JComboBox(array);
        gridBagPanel.add(this.enforceSecureScripting, 1, 0, 1, 1, 17, 0, 1.0, 0.0);
        gridBagPanel.add(label, 0, 1, 1, 1, 13, 0, 1.0, 0.0);
        gridBagPanel.add(this.grantScriptFileAccess, 1, 1, 1, 1, 17, 0, 1.0, 0.0);
        gridBagPanel.add(this.grantScriptNetworkAccess, 1, 2, 1, 1, 17, 0, 1.0, 0.0);
        gridBagPanel.add(label2, 0, 3, 1, 1, 13, 0, 1.0, 0.0);
        gridBagPanel.add(this.loadJava, 1, 3, 1, 1, 17, 0, 1.0, 0.0);
        gridBagPanel.add(this.loadEcmascript, 1, 4, 1, 1, 17, 0, 1.0, 0.0);
        gridBagPanel.add(label3, 0, 5, 1, 1, 13, 0, 1.0, 0.0);
        gridBagPanel.add(this.allowedScriptOrigin, 1, 5, 1, 1, 17, 0, 1.0, 0.0);
        gridBagPanel.add(label4, 0, 6, 1, 1, 13, 0, 1.0, 0.0);
        gridBagPanel.add(this.allowedResourceOrigin, 1, 6, 1, 1, 17, 0, 1.0, 0.0);
        return gridBagPanel;
    }
    
    protected JPanel buildLanguagePanel() {
        final JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));
        (this.languagePanel = new LanguageDialog.Panel()).setBorder(BorderFactory.createEmptyBorder());
        final Color color = UIManager.getColor("Window.background");
        this.languagePanel.getComponent(0).setBackground(color);
        this.languagePanel.getComponent(1).setBackground(color);
        panel.add(this.languagePanel);
        return panel;
    }
    
    protected JPanel buildStylesheetPanel() {
        final JGridBagPanel gridBagPanel = new JGridBagPanel(new JGridBagPanel.InsetsManager() {
            protected Insets i1 = new Insets(5, 5, 0, 0);
            protected Insets i2 = new Insets(5, 0, 0, 0);
            protected Insets i3 = new Insets(0, 5, 0, 0);
            protected Insets i4 = new Insets(0, 0, 0, 0);
            
            public Insets getInsets(final int n, final int n2) {
                if (n2 >= 1 && n2 <= 5) {
                    return (n == 0) ? this.i2 : this.i1;
                }
                return (n == 0) ? this.i4 : this.i3;
            }
        });
        gridBagPanel.setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));
        (this.userStylesheetEnabled = new JCheckBox(Resources.getString("PreferenceDialog.label.enable.user.stylesheet"))).addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent actionEvent) {
                final boolean selected = PreferenceDialog.this.userStylesheetEnabled.isSelected();
                PreferenceDialog.this.userStylesheetLabel.setEnabled(selected);
                PreferenceDialog.this.userStylesheet.setEnabled(selected);
                PreferenceDialog.this.userStylesheetBrowse.setEnabled(selected);
            }
        });
        this.userStylesheetLabel = new JLabel(Resources.getString("PreferenceDialog.label.user.stylesheet"));
        this.userStylesheet = new JTextField();
        (this.userStylesheetBrowse = new JButton(Resources.getString("PreferenceDialog.label.browse"))).addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent actionEvent) {
                File selectedFile = null;
                if (Platform.isOSX) {
                    final FileDialog fileDialog = new FileDialog((Frame)PreferenceDialog.this.getOwner(), Resources.getString("PreferenceDialog.BrowseWindow.title"));
                    fileDialog.setVisible(true);
                    final String file = fileDialog.getFile();
                    if (file != null) {
                        selectedFile = new File(fileDialog.getDirectory(), file);
                    }
                }
                else {
                    final JFileChooser fileChooser = new JFileChooser(new File("."));
                    fileChooser.setDialogTitle(Resources.getString("PreferenceDialog.BrowseWindow.title"));
                    fileChooser.setFileHidingEnabled(false);
                    if (fileChooser.showOpenDialog(PreferenceDialog.this) == 0) {
                        selectedFile = fileChooser.getSelectedFile();
                    }
                }
                if (selectedFile != null) {
                    try {
                        PreferenceDialog.this.userStylesheet.setText(selectedFile.getCanonicalPath());
                    }
                    catch (IOException ex) {}
                }
            }
        });
        final JLabel label = new JLabel(Resources.getString("PreferenceDialog.label.css.media.types"));
        label.setVerticalAlignment(1);
        (this.mediaList = new JList()).setSelectionMode(0);
        this.mediaList.setModel(this.mediaListModel);
        this.mediaList.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(final ListSelectionEvent listSelectionEvent) {
                PreferenceDialog.this.updateMediaListButtons();
            }
        });
        this.mediaListModel.addListDataListener(new ListDataListener() {
            public void contentsChanged(final ListDataEvent listDataEvent) {
                PreferenceDialog.this.updateMediaListButtons();
            }
            
            public void intervalAdded(final ListDataEvent listDataEvent) {
                PreferenceDialog.this.updateMediaListButtons();
            }
            
            public void intervalRemoved(final ListDataEvent listDataEvent) {
                PreferenceDialog.this.updateMediaListButtons();
            }
        });
        final JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBorder(BorderFactory.createLoweredBevelBorder());
        scrollPane.getViewport().add(this.mediaList);
        final JButton button = new JButton(Resources.getString("PreferenceDialog.label.add"));
        button.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent actionEvent) {
                final CSSMediaPanel.AddMediumDialog addMediumDialog = new CSSMediaPanel.AddMediumDialog((Component)PreferenceDialog.this);
                addMediumDialog.pack();
                addMediumDialog.setVisible(true);
                if (addMediumDialog.getReturnCode() == 1 || addMediumDialog.getMedium() == null) {
                    return;
                }
                String trim = addMediumDialog.getMedium().trim();
                if (trim.length() == 0 || PreferenceDialog.this.mediaListModel.contains(trim)) {
                    return;
                }
                for (int n = 0; n < PreferenceDialog.this.mediaListModel.size() && trim != null; ++n) {
                    final int compareTo = trim.compareTo((String)PreferenceDialog.this.mediaListModel.getElementAt(n));
                    if (compareTo == 0) {
                        trim = null;
                    }
                    else if (compareTo < 0) {
                        PreferenceDialog.this.mediaListModel.insertElementAt(trim, n);
                        trim = null;
                    }
                }
                if (trim != null) {
                    PreferenceDialog.this.mediaListModel.addElement(trim);
                }
            }
        });
        (this.mediaListRemoveButton = new JButton(Resources.getString("PreferenceDialog.label.remove"))).addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent actionEvent) {
                final int selectedIndex = PreferenceDialog.this.mediaList.getSelectedIndex();
                PreferenceDialog.this.mediaList.clearSelection();
                if (selectedIndex >= 0) {
                    PreferenceDialog.this.mediaListModel.removeElementAt(selectedIndex);
                }
            }
        });
        (this.mediaListClearButton = new JButton(Resources.getString("PreferenceDialog.label.clear"))).addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent actionEvent) {
                PreferenceDialog.this.mediaList.clearSelection();
                PreferenceDialog.this.mediaListModel.removeAllElements();
            }
        });
        gridBagPanel.add(this.userStylesheetEnabled, 1, 0, 2, 1, 17, 0, 0.0, 0.0);
        gridBagPanel.add(this.userStylesheetLabel, 0, 1, 1, 1, 13, 0, 0.0, 0.0);
        gridBagPanel.add(this.userStylesheet, 1, 1, 1, 1, 17, 2, 1.0, 0.0);
        gridBagPanel.add(this.userStylesheetBrowse, 2, 1, 1, 1, 17, 2, 0.0, 0.0);
        gridBagPanel.add(label, 0, 2, 1, 1, 13, 3, 0.0, 0.0);
        gridBagPanel.add(scrollPane, 1, 2, 1, 4, 17, 1, 1.0, 1.0);
        gridBagPanel.add(new JPanel(), 2, 2, 1, 1, 17, 1, 0.0, 1.0);
        gridBagPanel.add(button, 2, 3, 1, 1, 16, 2, 0.0, 0.0);
        gridBagPanel.add(this.mediaListRemoveButton, 2, 4, 1, 1, 16, 2, 0.0, 0.0);
        gridBagPanel.add(this.mediaListClearButton, 2, 5, 1, 1, 16, 2, 0.0, 0.0);
        return gridBagPanel;
    }
    
    protected void updateMediaListButtons() {
        this.mediaListRemoveButton.setEnabled(!this.mediaList.isSelectionEmpty());
        this.mediaListClearButton.setEnabled(!this.mediaListModel.isEmpty());
    }
    
    protected JPanel buildNetworkPanel() {
        final JGridBagPanel gridBagPanel = new JGridBagPanel();
        gridBagPanel.setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));
        final JLabel label = new JLabel(Resources.getString("PreferenceDialog.label.http.proxy"));
        final JLabel label2 = new JLabel(Resources.getString("PreferenceDialog.label.host"));
        final JLabel label3 = new JLabel(Resources.getString("PreferenceDialog.label.port"));
        final JLabel label4 = new JLabel(Resources.getString("PreferenceDialog.label.colon"));
        final Font font = label2.getFont();
        final Font deriveFont = font.deriveFont(font.getSize2D() * 0.85f);
        label2.setFont(deriveFont);
        label3.setFont(deriveFont);
        (this.host = new JTextField()).setPreferredSize(new Dimension(200, 20));
        (this.port = new JTextField()).setPreferredSize(new Dimension(40, 20));
        gridBagPanel.add(label, 0, 0, 1, 1, 13, 0, 0.0, 0.0);
        gridBagPanel.add(this.host, 1, 0, 1, 1, 17, 2, 0.0, 0.0);
        gridBagPanel.add(label4, 2, 0, 1, 1, 17, 0, 0.0, 0.0);
        gridBagPanel.add(this.port, 3, 0, 1, 1, 17, 2, 0.0, 0.0);
        gridBagPanel.add(label2, 1, 1, 1, 1, 17, 0, 0.0, 0.0);
        gridBagPanel.add(label3, 3, 1, 1, 1, 17, 0, 0.0, 0.0);
        return gridBagPanel;
    }
    
    public int showDialog() {
        if (Platform.isOSX) {
            this.returnCode = 0;
        }
        else {
            this.returnCode = 1;
        }
        this.pack();
        this.setVisible(true);
        return this.returnCode;
    }
    
    protected class JConfigurationPanel extends JPanel
    {
        protected JToolBar toolbar;
        protected JPanel panel;
        protected CardLayout layout;
        protected ButtonGroup group;
        protected int page;
        
        public JConfigurationPanel() {
            this.page = -1;
            (this.toolbar = new JToolBar()).setFloatable(false);
            this.toolbar.setLayout(new FlowLayout(3, 0, 0));
            this.toolbar.add(new JToolBar.Separator(new Dimension(8, 8)));
            if (Platform.isOSX || PreferenceDialog.isMetalSteel()) {
                this.toolbar.setBackground(new Color(248, 248, 248));
            }
            this.toolbar.setOpaque(true);
            this.panel = new JPanel();
            this.layout = (Platform.isOSX ? new ResizingCardLayout() : new CardLayout());
            this.group = new ButtonGroup();
            this.setLayout(new BorderLayout());
            this.panel.setLayout(this.layout);
            this.add(this.toolbar, "North");
            this.add(this.panel);
        }
        
        public void addPanel(final String text, final Icon icon, final Icon pressedIcon, final JPanel comp) {
            final JToggleButton comp2 = new JToggleButton(text, icon);
            comp2.setVerticalTextPosition(3);
            comp2.setHorizontalTextPosition(0);
            comp2.setContentAreaFilled(false);
            try {
                AbstractButton.class.getMethod("setIconTextGap", Integer.TYPE).invoke(comp2, new Integer(0));
            }
            catch (Exception ex) {}
            comp2.setPressedIcon(pressedIcon);
            this.group.add(comp2);
            this.toolbar.add(comp2);
            this.toolbar.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.gray));
            comp2.addItemListener(new ItemListener() {
                private final /* synthetic */ JConfigurationPanel this$1 = this$1;
                
                public void itemStateChanged(final ItemEvent itemEvent) {
                    final JToggleButton toggleButton = (JToggleButton)itemEvent.getSource();
                    switch (itemEvent.getStateChange()) {
                        case 1: {
                            this.this$1.select(toggleButton);
                            break;
                        }
                        case 2: {
                            this.this$1.unselect(toggleButton);
                            break;
                        }
                    }
                }
            });
            if (this.panel.getComponentCount() == 0) {
                comp2.setSelected(true);
                this.page = 0;
            }
            else {
                this.unselect(comp2);
            }
            this.panel.add(comp, text.intern());
        }
        
        protected int getComponentIndex(final Component component) {
            final Container parent = component.getParent();
            for (int componentCount = parent.getComponentCount(), i = 0; i < componentCount; ++i) {
                if (parent.getComponent(i) == component) {
                    return i;
                }
            }
            return -1;
        }
        
        protected void select(final JToggleButton toggleButton) {
            toggleButton.setOpaque(true);
            toggleButton.setBackground(Platform.isOSX ? new Color(216, 216, 216) : UIManager.getColor("List.selectionBackground"));
            toggleButton.setForeground(UIManager.getColor("List.selectionForeground"));
            toggleButton.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createMatteBorder(0, 1, 0, 1, new Color(160, 160, 160)), BorderFactory.createEmptyBorder(4, 3, 4, 3)));
            this.layout.show(this.panel, toggleButton.getText().intern());
            this.page = this.getComponentIndex(toggleButton) - 1;
            if (Platform.isOSX) {
                PreferenceDialog.this.setTitle(toggleButton.getText());
            }
            PreferenceDialog.this.pack();
            this.panel.grabFocus();
        }
        
        protected void unselect(final JToggleButton toggleButton) {
            toggleButton.setOpaque(false);
            toggleButton.setBackground(null);
            toggleButton.setForeground(UIManager.getColor("Button.foreground"));
            toggleButton.setBorder(BorderFactory.createEmptyBorder(5, 4, 5, 4));
        }
        
        protected class ResizingCardLayout extends CardLayout
        {
            public ResizingCardLayout() {
                super(0, 0);
            }
            
            public Dimension preferredLayoutSize(final Container parent) {
                Dimension preferredLayoutSize = super.preferredLayoutSize(parent);
                if (JConfigurationPanel.this.page != -1) {
                    preferredLayoutSize = new Dimension((int)preferredLayoutSize.getWidth(), (int)JConfigurationPanel.this.panel.getComponent(JConfigurationPanel.this.page).getPreferredSize().getHeight());
                }
                return preferredLayoutSize;
            }
        }
    }
}
