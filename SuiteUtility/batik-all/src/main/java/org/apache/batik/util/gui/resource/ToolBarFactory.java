// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.util.gui.resource;

import javax.swing.JButton;
import org.apache.batik.util.resources.ResourceFormatException;
import java.util.MissingResourceException;
import java.util.Iterator;
import java.awt.Component;
import javax.swing.JToolBar;
import java.util.ResourceBundle;
import org.apache.batik.util.resources.ResourceManager;

public class ToolBarFactory extends ResourceManager
{
    private static final String SEPARATOR = "-";
    private ButtonFactory buttonFactory;
    
    public ToolBarFactory(final ResourceBundle resourceBundle, final ActionMap actionMap) {
        super(resourceBundle);
        this.buttonFactory = new ButtonFactory(resourceBundle, actionMap);
    }
    
    public JToolBar createJToolBar(final String s) throws MissingResourceException, ResourceFormatException, MissingListenerException {
        final JToolBar toolBar = new JToolBar();
        for (final String s2 : this.getStringList(s)) {
            if (s2.equals("-")) {
                toolBar.add(new JToolbarSeparator());
            }
            else {
                toolBar.add(this.createJButton(s2));
            }
        }
        return toolBar;
    }
    
    public JButton createJButton(final String s) throws MissingResourceException, ResourceFormatException, MissingListenerException {
        return this.buttonFactory.createJToolbarButton(s);
    }
}
