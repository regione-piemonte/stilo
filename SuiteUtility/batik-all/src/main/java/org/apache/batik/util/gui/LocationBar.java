// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.util.gui;

import java.util.Locale;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.MissingResourceException;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import java.awt.Component;
import javax.swing.JLabel;
import java.awt.LayoutManager;
import java.awt.BorderLayout;
import javax.swing.JComboBox;
import org.apache.batik.util.resources.ResourceManager;
import java.util.ResourceBundle;
import javax.swing.JPanel;

public class LocationBar extends JPanel
{
    protected static final String RESOURCES = "org.apache.batik.util.gui.resources.LocationBar";
    protected static ResourceBundle bundle;
    protected static ResourceManager rManager;
    protected JComboBox comboBox;
    
    public LocationBar() {
        super(new BorderLayout(5, 5));
        final JLabel comp = new JLabel(LocationBar.rManager.getString("Panel.label"));
        this.add("West", comp);
        try {
            final URL resource = this.getClass().getResource(LocationBar.rManager.getString("Panel.icon"));
            if (resource != null) {
                comp.setIcon(new ImageIcon(resource));
            }
        }
        catch (MissingResourceException ex) {}
        this.add("Center", this.comboBox = new JComboBox());
        this.comboBox.setEditable(true);
    }
    
    public void addActionListener(final ActionListener l) {
        this.comboBox.addActionListener(l);
    }
    
    public String getText() {
        return (String)this.comboBox.getEditor().getItem();
    }
    
    public void setText(final String item) {
        this.comboBox.getEditor().setItem(item);
    }
    
    public void addToHistory(final String item) {
        this.comboBox.addItem(item);
        this.comboBox.setPreferredSize(new Dimension(0, this.comboBox.getPreferredSize().height));
    }
    
    static {
        LocationBar.bundle = ResourceBundle.getBundle("org.apache.batik.util.gui.resources.LocationBar", Locale.getDefault());
        LocationBar.rManager = new ResourceManager(LocationBar.bundle);
    }
}
