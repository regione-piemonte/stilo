// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.util.gui.resource;

import java.net.URL;
import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JCheckBox;
import javax.swing.JRadioButton;
import javax.swing.JToggleButton;
import org.apache.batik.util.resources.ResourceFormatException;
import javax.swing.AbstractButton;
import java.util.MissingResourceException;
import javax.swing.JButton;
import java.util.ResourceBundle;
import org.apache.batik.util.resources.ResourceManager;

public class ButtonFactory extends ResourceManager
{
    private static final String ICON_SUFFIX = ".icon";
    private static final String TEXT_SUFFIX = ".text";
    private static final String MNEMONIC_SUFFIX = ".mnemonic";
    private static final String ACTION_SUFFIX = ".action";
    private static final String SELECTED_SUFFIX = ".selected";
    private static final String TOOLTIP_SUFFIX = ".tooltip";
    private ActionMap actions;
    
    public ButtonFactory(final ResourceBundle resourceBundle, final ActionMap actions) {
        super(resourceBundle);
        this.actions = actions;
    }
    
    public JButton createJButton(final String str) throws MissingResourceException, ResourceFormatException, MissingListenerException {
        JButton button;
        try {
            button = new JButton(this.getString(str + ".text"));
        }
        catch (MissingResourceException ex) {
            button = new JButton();
        }
        this.initializeButton(button, str);
        return button;
    }
    
    public JButton createJToolbarButton(final String str) throws MissingResourceException, ResourceFormatException, MissingListenerException {
        JToolbarButton toolbarButton;
        try {
            toolbarButton = new JToolbarButton(this.getString(str + ".text"));
        }
        catch (MissingResourceException ex) {
            toolbarButton = new JToolbarButton();
        }
        this.initializeButton(toolbarButton, str);
        return toolbarButton;
    }
    
    public JToggleButton createJToolbarToggleButton(final String str) throws MissingResourceException, ResourceFormatException, MissingListenerException {
        JToolbarToggleButton toolbarToggleButton;
        try {
            toolbarToggleButton = new JToolbarToggleButton(this.getString(str + ".text"));
        }
        catch (MissingResourceException ex) {
            toolbarToggleButton = new JToolbarToggleButton();
        }
        this.initializeButton(toolbarToggleButton, str);
        return toolbarToggleButton;
    }
    
    public JRadioButton createJRadioButton(final String s) throws MissingResourceException, ResourceFormatException, MissingListenerException {
        final JRadioButton radioButton = new JRadioButton(this.getString(s + ".text"));
        this.initializeButton(radioButton, s);
        try {
            radioButton.setSelected(this.getBoolean(s + ".selected"));
        }
        catch (MissingResourceException ex) {}
        return radioButton;
    }
    
    public JCheckBox createJCheckBox(final String s) throws MissingResourceException, ResourceFormatException, MissingListenerException {
        final JCheckBox checkBox = new JCheckBox(this.getString(s + ".text"));
        this.initializeButton(checkBox, s);
        try {
            checkBox.setSelected(this.getBoolean(s + ".selected"));
        }
        catch (MissingResourceException ex) {}
        return checkBox;
    }
    
    private void initializeButton(final AbstractButton abstractButton, final String str) throws ResourceFormatException, MissingListenerException {
        try {
            final Action action = this.actions.getAction(this.getString(str + ".action"));
            if (action == null) {
                throw new MissingListenerException("", "Action", str + ".action");
            }
            abstractButton.setAction(action);
            try {
                abstractButton.setText(this.getString(str + ".text"));
            }
            catch (MissingResourceException ex) {}
            if (action instanceof JComponentModifier) {
                ((JComponentModifier)action).addJComponent(abstractButton);
            }
        }
        catch (MissingResourceException ex2) {}
        try {
            final URL resource = this.actions.getClass().getResource(this.getString(str + ".icon"));
            if (resource != null) {
                abstractButton.setIcon(new ImageIcon(resource));
            }
        }
        catch (MissingResourceException ex3) {}
        try {
            final String string = this.getString(str + ".mnemonic");
            if (string.length() != 1) {
                throw new ResourceFormatException("Malformed mnemonic", this.bundle.getClass().getName(), str + ".mnemonic");
            }
            abstractButton.setMnemonic(string.charAt(0));
        }
        catch (MissingResourceException ex4) {}
        try {
            final String string2 = this.getString(str + ".tooltip");
            if (string2 != null) {
                abstractButton.setToolTipText(string2);
            }
        }
        catch (MissingResourceException ex5) {}
    }
}
