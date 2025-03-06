// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.apps.svgbrowser;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import java.awt.event.ActionListener;
import java.awt.FlowLayout;
import javax.swing.JOptionPane;
import java.awt.Component;
import javax.swing.JButton;
import javax.swing.JDialog;
import java.util.Locale;
import java.awt.LayoutManager;
import org.apache.batik.util.resources.ResourceManager;
import java.util.ResourceBundle;
import javax.swing.JPanel;

public class OptionPanel extends JPanel
{
    public static final String RESOURCES = "org.apache.batik.apps.svgbrowser.resources.GUI";
    protected static ResourceBundle bundle;
    protected static ResourceManager resources;
    
    public OptionPanel(final LayoutManager layout) {
        super(layout);
    }
    
    static {
        OptionPanel.bundle = ResourceBundle.getBundle("org.apache.batik.apps.svgbrowser.resources.GUI", Locale.getDefault());
        OptionPanel.resources = new ResourceManager(OptionPanel.bundle);
    }
    
    public static class Dialog extends JDialog
    {
        protected JButton ok;
        protected JPanel panel;
        
        public Dialog(final Component parentComponent, final String title, final JPanel panel) {
            super(JOptionPane.getFrameForComponent(parentComponent), title);
            this.setModal(true);
            this.panel = panel;
            this.getContentPane().add(panel, "Center");
            this.getContentPane().add(this.createButtonPanel(), "South");
        }
        
        protected JPanel createButtonPanel() {
            final JPanel panel = new JPanel(new FlowLayout());
            (this.ok = new JButton(OptionPanel.resources.getString("OKButton.text"))).addActionListener(new OKButtonAction());
            panel.add(this.ok);
            return panel;
        }
        
        protected class OKButtonAction extends AbstractAction
        {
            public void actionPerformed(final ActionEvent actionEvent) {
                Dialog.this.dispose();
            }
        }
    }
}
