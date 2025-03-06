// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.apps.svgbrowser;

import javax.swing.JPanel;
import java.awt.Component;
import javax.swing.JLabel;
import java.awt.LayoutManager;
import java.awt.BorderLayout;
import javax.swing.JCheckBox;

public class SVGOptionPanel extends OptionPanel
{
    protected JCheckBox xmlbaseCB;
    protected JCheckBox prettyPrintCB;
    
    public SVGOptionPanel() {
        super(new BorderLayout());
        this.add(new JLabel(SVGOptionPanel.resources.getString("SVGOptionPanel.label")), "North");
        (this.xmlbaseCB = new JCheckBox(SVGOptionPanel.resources.getString("SVGOptionPanel.UseXMLBase"))).setSelected(SVGOptionPanel.resources.getBoolean("SVGOptionPanel.UseXMLBaseDefault"));
        this.add(this.xmlbaseCB, "Center");
        (this.prettyPrintCB = new JCheckBox(SVGOptionPanel.resources.getString("SVGOptionPanel.PrettyPrint"))).setSelected(SVGOptionPanel.resources.getBoolean("SVGOptionPanel.PrettyPrintDefault"));
        this.add(this.prettyPrintCB, "South");
    }
    
    public boolean getUseXMLBase() {
        return this.xmlbaseCB.isSelected();
    }
    
    public boolean getPrettyPrint() {
        return this.prettyPrintCB.isSelected();
    }
    
    public static SVGOptionPanel showDialog(final Component component) {
        final String string = SVGOptionPanel.resources.getString("SVGOptionPanel.dialog.title");
        final SVGOptionPanel svgOptionPanel = new SVGOptionPanel();
        final Dialog dialog = new Dialog(component, string, svgOptionPanel);
        dialog.pack();
        dialog.setVisible(true);
        return svgOptionPanel;
    }
}
