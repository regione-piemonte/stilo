// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.util.gui.resource;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Dimension;
import javax.swing.JComponent;

public class JToolbarSeparator extends JComponent
{
    public JToolbarSeparator() {
        this.setMaximumSize(new Dimension(15, Integer.MAX_VALUE));
    }
    
    protected void paintComponent(final Graphics g) {
        super.paintComponent(g);
        final Dimension size = this.getSize();
        final int n = size.width / 2;
        g.setColor(Color.gray);
        g.drawLine(n, 3, n, size.height - 5);
        g.drawLine(n, 2, n + 1, 2);
        g.setColor(Color.white);
        g.drawLine(n + 1, 3, n + 1, size.height - 5);
        g.drawLine(n, size.height - 4, n + 1, size.height - 4);
    }
}
