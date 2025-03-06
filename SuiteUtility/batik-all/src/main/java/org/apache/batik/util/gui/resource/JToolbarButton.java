// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.util.gui.resource;

import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseListener;
import java.awt.Insets;
import java.awt.Color;
import javax.swing.JButton;

public class JToolbarButton extends JButton
{
    public JToolbarButton() {
        this.initialize();
    }
    
    public JToolbarButton(final String text) {
        super(text);
        this.initialize();
    }
    
    protected void initialize() {
        if (!System.getProperty("java.version").startsWith("1.3")) {
            this.setOpaque(false);
            this.setBackground(new Color(0, 0, 0, 0));
        }
        this.setBorderPainted(false);
        this.setMargin(new Insets(2, 2, 2, 2));
        this.addMouseListener(new MouseListener());
    }
    
    protected class MouseListener extends MouseAdapter
    {
        public void mouseEntered(final MouseEvent mouseEvent) {
            JToolbarButton.this.setBorderPainted(true);
        }
        
        public void mouseExited(final MouseEvent mouseEvent) {
            JToolbarButton.this.setBorderPainted(false);
        }
    }
}
