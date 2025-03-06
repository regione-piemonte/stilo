// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.apps.svgbrowser;

import javax.swing.JPanel;
import java.awt.Dimension;
import java.util.Dictionary;
import java.util.Hashtable;
import javax.swing.BorderFactory;
import java.awt.Component;
import javax.swing.JLabel;
import java.awt.Insets;
import org.apache.batik.util.gui.ExtendedGridBagConstraints;
import java.awt.LayoutManager;
import java.awt.GridBagLayout;
import javax.swing.JSlider;

public class JPEGOptionPanel extends OptionPanel
{
    protected JSlider quality;
    
    public JPEGOptionPanel() {
        super(new GridBagLayout());
        final ExtendedGridBagConstraints extendedGridBagConstraints = new ExtendedGridBagConstraints();
        extendedGridBagConstraints.insets = new Insets(5, 5, 5, 5);
        extendedGridBagConstraints.weightx = 0.0;
        extendedGridBagConstraints.weighty = 0.0;
        extendedGridBagConstraints.setGridBounds(extendedGridBagConstraints.fill = 0, 0, 1, 1);
        this.add(new JLabel(JPEGOptionPanel.resources.getString("JPEGOptionPanel.label")), extendedGridBagConstraints);
        (this.quality = new JSlider()).setMinimum(0);
        this.quality.setMaximum(100);
        this.quality.setMajorTickSpacing(10);
        this.quality.setMinorTickSpacing(5);
        this.quality.setPaintTicks(true);
        this.quality.setPaintLabels(true);
        this.quality.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        final Hashtable<Integer, JLabel> labelTable = new Hashtable<Integer, JLabel>();
        for (int i = 0; i < 100; i += 10) {
            labelTable.put(new Integer(i), new JLabel("0." + i / 10));
        }
        labelTable.put(new Integer(100), new JLabel("1"));
        this.quality.setLabelTable(labelTable);
        this.quality.setPreferredSize(new Dimension(350, this.quality.getPreferredSize().height));
        extendedGridBagConstraints.weightx = 1.0;
        extendedGridBagConstraints.fill = 2;
        extendedGridBagConstraints.setGridBounds(1, 0, 1, 1);
        this.add(this.quality, extendedGridBagConstraints);
    }
    
    public float getQuality() {
        return this.quality.getValue() / 100.0f;
    }
    
    public static float showDialog(final Component component) {
        final String string = JPEGOptionPanel.resources.getString("JPEGOptionPanel.dialog.title");
        final JPEGOptionPanel jpegOptionPanel = new JPEGOptionPanel();
        final Dialog dialog = new Dialog(component, string, jpegOptionPanel);
        dialog.pack();
        dialog.setVisible(true);
        return jpegOptionPanel.getQuality();
    }
}
