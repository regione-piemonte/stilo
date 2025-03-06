// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.util.gui;

import java.awt.GridBagConstraints;

public class ExtendedGridBagConstraints extends GridBagConstraints
{
    public void setGridBounds(final int gridx, final int gridy, final int gridwidth, final int gridheight) {
        this.gridx = gridx;
        this.gridy = gridy;
        this.gridwidth = gridwidth;
        this.gridheight = gridheight;
    }
    
    public void setWeight(final double weightx, final double weighty) {
        this.weightx = weightx;
        this.weighty = weighty;
    }
}
