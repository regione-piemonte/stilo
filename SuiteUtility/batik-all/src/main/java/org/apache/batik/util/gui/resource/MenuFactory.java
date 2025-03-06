// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.util.gui.resource;

import java.net.URL;
import javax.swing.Action;
import javax.swing.KeyStroke;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JMenuItem;
import javax.swing.AbstractButton;
import javax.swing.JMenu;
import javax.swing.JSeparator;
import javax.swing.JComponent;
import java.util.List;
import java.util.Iterator;
import java.awt.Component;
import org.apache.batik.util.resources.ResourceFormatException;
import java.util.MissingResourceException;
import javax.swing.JMenuBar;
import java.util.ResourceBundle;
import javax.swing.ButtonGroup;
import org.apache.batik.util.resources.ResourceManager;

public class MenuFactory extends ResourceManager
{
    private static final String TYPE_MENU = "MENU";
    private static final String TYPE_ITEM = "ITEM";
    private static final String TYPE_RADIO = "RADIO";
    private static final String TYPE_CHECK = "CHECK";
    private static final String SEPARATOR = "-";
    private static final String TYPE_SUFFIX = ".type";
    private static final String TEXT_SUFFIX = ".text";
    private static final String MNEMONIC_SUFFIX = ".mnemonic";
    private static final String ACCELERATOR_SUFFIX = ".accelerator";
    private static final String ACTION_SUFFIX = ".action";
    private static final String SELECTED_SUFFIX = ".selected";
    private static final String ENABLED_SUFFIX = ".enabled";
    private static final String ICON_SUFFIX = ".icon";
    private ActionMap actions;
    private ButtonGroup buttonGroup;
    
    public MenuFactory(final ResourceBundle resourceBundle, final ActionMap actions) {
        super(resourceBundle);
        this.actions = actions;
        this.buttonGroup = null;
    }
    
    public JMenuBar createJMenuBar(final String s) throws MissingResourceException, ResourceFormatException, MissingListenerException {
        return this.createJMenuBar(s, null);
    }
    
    public JMenuBar createJMenuBar(final String s, final String s2) throws MissingResourceException, ResourceFormatException, MissingListenerException {
        final JMenuBar menuBar = new JMenuBar();
        final Iterator<String> iterator = this.getSpecializedStringList(s, s2).iterator();
        while (iterator.hasNext()) {
            menuBar.add(this.createJMenuComponent(iterator.next(), s2));
        }
        return menuBar;
    }
    
    protected String getSpecializedString(final String str, final String str2) {
        String s;
        try {
            s = this.getString(str + '.' + str2);
        }
        catch (MissingResourceException ex) {
            s = this.getString(str);
        }
        return s;
    }
    
    protected List getSpecializedStringList(final String str, final String str2) {
        List list;
        try {
            list = this.getStringList(str + '.' + str2);
        }
        catch (MissingResourceException ex) {
            list = this.getStringList(str);
        }
        return list;
    }
    
    protected boolean getSpecializedBoolean(final String str, final String str2) {
        boolean b;
        try {
            b = this.getBoolean(str + '.' + str2);
        }
        catch (MissingResourceException ex) {
            b = this.getBoolean(str);
        }
        return b;
    }
    
    protected JComponent createJMenuComponent(final String s, final String s2) throws MissingResourceException, ResourceFormatException, MissingListenerException {
        if (s.equals("-")) {
            this.buttonGroup = null;
            return new JSeparator();
        }
        final String specializedString = this.getSpecializedString(s + ".type", s2);
        if (specializedString.equals("RADIO")) {
            if (this.buttonGroup == null) {
                this.buttonGroup = new ButtonGroup();
            }
        }
        else {
            this.buttonGroup = null;
        }
        JMenuItem menuItem;
        if (specializedString.equals("MENU")) {
            menuItem = this.createJMenu(s, s2);
        }
        else if (specializedString.equals("ITEM")) {
            menuItem = this.createJMenuItem(s, s2);
        }
        else if (specializedString.equals("RADIO")) {
            menuItem = this.createJRadioButtonMenuItem(s, s2);
            this.buttonGroup.add(menuItem);
        }
        else {
            if (!specializedString.equals("CHECK")) {
                throw new ResourceFormatException("Malformed resource", this.bundle.getClass().getName(), s + ".type");
            }
            menuItem = this.createJCheckBoxMenuItem(s, s2);
        }
        return menuItem;
    }
    
    public JMenu createJMenu(final String s) throws MissingResourceException, ResourceFormatException, MissingListenerException {
        return this.createJMenu(s, null);
    }
    
    public JMenu createJMenu(final String str, final String s) throws MissingResourceException, ResourceFormatException, MissingListenerException {
        final JMenu menu = new JMenu(this.getSpecializedString(str + ".text", s));
        this.initializeJMenuItem(menu, str, s);
        final Iterator<String> iterator = this.getSpecializedStringList(str, s).iterator();
        while (iterator.hasNext()) {
            menu.add(this.createJMenuComponent(iterator.next(), s));
        }
        return menu;
    }
    
    public JMenuItem createJMenuItem(final String s) throws MissingResourceException, ResourceFormatException, MissingListenerException {
        return this.createJMenuItem(s, null);
    }
    
    public JMenuItem createJMenuItem(final String str, final String s) throws MissingResourceException, ResourceFormatException, MissingListenerException {
        final JMenuItem menuItem = new JMenuItem(this.getSpecializedString(str + ".text", s));
        this.initializeJMenuItem(menuItem, str, s);
        return menuItem;
    }
    
    public JRadioButtonMenuItem createJRadioButtonMenuItem(final String s) throws MissingResourceException, ResourceFormatException, MissingListenerException {
        return this.createJRadioButtonMenuItem(s, null);
    }
    
    public JRadioButtonMenuItem createJRadioButtonMenuItem(final String s, final String s2) throws MissingResourceException, ResourceFormatException, MissingListenerException {
        final JRadioButtonMenuItem radioButtonMenuItem = new JRadioButtonMenuItem(this.getSpecializedString(s + ".text", s2));
        this.initializeJMenuItem(radioButtonMenuItem, s, s2);
        try {
            radioButtonMenuItem.setSelected(this.getSpecializedBoolean(s + ".selected", s2));
        }
        catch (MissingResourceException ex) {}
        return radioButtonMenuItem;
    }
    
    public JCheckBoxMenuItem createJCheckBoxMenuItem(final String s) throws MissingResourceException, ResourceFormatException, MissingListenerException {
        return this.createJCheckBoxMenuItem(s, null);
    }
    
    public JCheckBoxMenuItem createJCheckBoxMenuItem(final String s, final String s2) throws MissingResourceException, ResourceFormatException, MissingListenerException {
        final JCheckBoxMenuItem checkBoxMenuItem = new JCheckBoxMenuItem(this.getSpecializedString(s + ".text", s2));
        this.initializeJMenuItem(checkBoxMenuItem, s, s2);
        try {
            checkBoxMenuItem.setSelected(this.getSpecializedBoolean(s + ".selected", s2));
        }
        catch (MissingResourceException ex) {}
        return checkBoxMenuItem;
    }
    
    protected void initializeJMenuItem(final JMenuItem menuItem, final String str, final String s) throws ResourceFormatException, MissingListenerException {
        try {
            final Action action = this.actions.getAction(this.getSpecializedString(str + ".action", s));
            if (action == null) {
                throw new MissingListenerException("", "Action", str + ".action");
            }
            menuItem.setAction(action);
            menuItem.setText(this.getSpecializedString(str + ".text", s));
            if (action instanceof JComponentModifier) {
                ((JComponentModifier)action).addJComponent(menuItem);
            }
        }
        catch (MissingResourceException ex) {}
        try {
            final URL resource = this.actions.getClass().getResource(this.getSpecializedString(str + ".icon", s));
            if (resource != null) {
                menuItem.setIcon(new ImageIcon(resource));
            }
        }
        catch (MissingResourceException ex2) {}
        try {
            final String specializedString = this.getSpecializedString(str + ".mnemonic", s);
            if (specializedString.length() != 1) {
                throw new ResourceFormatException("Malformed mnemonic", this.bundle.getClass().getName(), str + ".mnemonic");
            }
            menuItem.setMnemonic(specializedString.charAt(0));
        }
        catch (MissingResourceException ex3) {}
        try {
            if (!(menuItem instanceof JMenu)) {
                final KeyStroke keyStroke = KeyStroke.getKeyStroke(this.getSpecializedString(str + ".accelerator", s));
                if (keyStroke == null) {
                    throw new ResourceFormatException("Malformed accelerator", this.bundle.getClass().getName(), str + ".accelerator");
                }
                menuItem.setAccelerator(keyStroke);
            }
        }
        catch (MissingResourceException ex4) {}
        try {
            menuItem.setEnabled(this.getSpecializedBoolean(str + ".enabled", s));
        }
        catch (MissingResourceException ex5) {}
    }
}
