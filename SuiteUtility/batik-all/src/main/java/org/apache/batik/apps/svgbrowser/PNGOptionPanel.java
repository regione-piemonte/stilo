// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.apps.svgbrowser;

import javax.swing.JPanel;
import java.awt.Component;
import javax.swing.JLabel;
import java.awt.Insets;
import org.apache.batik.util.gui.ExtendedGridBagConstraints;
import java.awt.LayoutManager;
import java.awt.GridBagLayout;
import javax.swing.JCheckBox;

public class PNGOptionPanel extends OptionPanel
{
    protected JCheckBox check;
    
    public PNGOptionPanel() {
        super(new GridBagLayout());
        final ExtendedGridBagConstraints extendedGridBagConstraints = new ExtendedGridBagConstraints();
        extendedGridBagConstraints.insets = new Insets(5, 5, 5, 5);
        extendedGridBagConstraints.weightx = 0.0;
        extendedGridBagConstraints.weighty = 0.0;
        extendedGridBagConstraints.setGridBounds(extendedGridBagConstraints.fill = 0, 0, 1, 1);
        this.add(new JLabel(PNGOptionPanel.resources.getString("PNGOptionPanel.label")), extendedGridBagConstraints);
        this.check = new JCheckBox();
        extendedGridBagConstraints.weightx = 1.0;
        extendedGridBagConstraints.fill = 2;
        extendedGridBagConstraints.setGridBounds(1, 0, 1, 1);
        this.add(this.check, extendedGridBagConstraints);
    }
    
    public boolean isIndexed() {
        return this.check.isSelected();
    }
    
    public static boolean showDialog(final Component component) {
        final String string = PNGOptionPanel.resources.getString("PNGOptionPanel.dialog.title");
        final PNGOptionPanel pngOptionPanel = new PNGOptionPanel();
        final Dialog dialog = new Dialog(component, string, pngOptionPanel);
        dialog.pack();
        dialog.setVisible(true);
        return pngOptionPanel.isIndexed();
    }
}
