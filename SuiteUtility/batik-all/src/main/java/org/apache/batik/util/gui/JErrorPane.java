// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.util.gui;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import java.util.Locale;
import org.apache.batik.util.gui.resource.MissingListenerException;
import javax.swing.Action;
import java.awt.FlowLayout;
import javax.swing.JOptionPane;
import javax.swing.JDialog;
import java.awt.Insets;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import java.awt.BorderLayout;
import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import java.io.Writer;
import java.io.PrintWriter;
import java.io.StringWriter;
import javax.swing.BorderFactory;
import java.util.HashMap;
import java.awt.LayoutManager;
import java.awt.GridBagLayout;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.JComponent;
import org.apache.batik.util.gui.resource.ButtonFactory;
import org.apache.batik.util.resources.ResourceManager;
import java.util.ResourceBundle;
import org.apache.batik.util.gui.resource.ActionMap;
import javax.swing.JPanel;

public class JErrorPane extends JPanel implements ActionMap
{
    protected static final String RESOURCES = "org.apache.batik.util.gui.resources.JErrorPane";
    protected static ResourceBundle bundle;
    protected static ResourceManager resources;
    protected String msg;
    protected String stacktrace;
    protected ButtonFactory bf;
    protected JComponent detailsArea;
    protected JButton showDetailButton;
    protected boolean isDetailShown;
    protected JPanel subpanel;
    protected Map listeners;
    
    public JErrorPane(final Throwable t, final int n) {
        super(new GridBagLayout());
        this.bf = new ButtonFactory(JErrorPane.bundle, this);
        this.isDetailShown = false;
        this.listeners = new HashMap();
        this.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        this.listeners.put("ShowDetailButtonAction", new ShowDetailButtonAction());
        this.listeners.put("OKButtonAction", new OKButtonAction());
        this.msg = JErrorPane.bundle.getString("Heading.text") + "\n\n" + t.getMessage();
        final StringWriter out = new StringWriter();
        t.printStackTrace(new PrintWriter(out));
        out.flush();
        this.stacktrace = out.toString();
        final ExtendedGridBagConstraints constraints = new ExtendedGridBagConstraints();
        final JTextArea comp = new JTextArea();
        comp.setText(this.msg);
        comp.setColumns(50);
        comp.setFont(new JLabel().getFont());
        comp.setForeground(new JLabel().getForeground());
        comp.setOpaque(false);
        comp.setEditable(false);
        comp.setLineWrap(true);
        constraints.setWeight(0.0, 0.0);
        constraints.anchor = 17;
        constraints.setGridBounds(constraints.fill = 0, 0, 1, 1);
        this.add(comp, constraints);
        constraints.setWeight(1.0, 0.0);
        constraints.anchor = 10;
        constraints.fill = 2;
        constraints.setGridBounds(0, 1, 1, 1);
        this.add(this.createButtonsPanel(), constraints);
        final JTextArea view = new JTextArea();
        comp.setColumns(50);
        view.setText(this.stacktrace);
        view.setEditable(false);
        (this.detailsArea = new JPanel(new BorderLayout(0, 10))).add(new JSeparator(), "North");
        this.detailsArea.add(new JScrollPane(view), "Center");
        this.subpanel = new JPanel(new BorderLayout());
        constraints.insets = new Insets(10, 4, 4, 4);
        constraints.setWeight(1.0, 1.0);
        constraints.anchor = 10;
        constraints.setGridBounds(0, 2, constraints.fill = 1, 1);
        this.add(this.subpanel, constraints);
    }
    
    public JDialog createDialog(final Component parentComponent, final String title) {
        final JDialog dialog = new JDialog(JOptionPane.getFrameForComponent(parentComponent), title);
        dialog.getContentPane().add(this, "Center");
        dialog.pack();
        return dialog;
    }
    
    protected JPanel createButtonsPanel() {
        final JPanel panel = new JPanel(new FlowLayout(2));
        panel.add(this.showDetailButton = this.bf.createJButton("ShowDetailButton"));
        panel.add(this.bf.createJButton("OKButton"));
        return panel;
    }
    
    public Action getAction(final String s) throws MissingListenerException {
        return this.listeners.get(s);
    }
    
    static {
        JErrorPane.bundle = ResourceBundle.getBundle("org.apache.batik.util.gui.resources.JErrorPane", Locale.getDefault());
        JErrorPane.resources = new ResourceManager(JErrorPane.bundle);
    }
    
    protected class ShowDetailButtonAction extends AbstractAction
    {
        public void actionPerformed(final ActionEvent actionEvent) {
            if (JErrorPane.this.isDetailShown) {
                JErrorPane.this.subpanel.remove(JErrorPane.this.detailsArea);
                JErrorPane.this.isDetailShown = false;
                JErrorPane.this.showDetailButton.setText(JErrorPane.resources.getString("ShowDetailButton.text"));
            }
            else {
                JErrorPane.this.subpanel.add(JErrorPane.this.detailsArea, "Center");
                JErrorPane.this.showDetailButton.setText(JErrorPane.resources.getString("ShowDetailButton.text2"));
                JErrorPane.this.isDetailShown = true;
            }
            ((JDialog)JErrorPane.this.getTopLevelAncestor()).pack();
        }
    }
    
    protected class OKButtonAction extends AbstractAction
    {
        public void actionPerformed(final ActionEvent actionEvent) {
            ((JDialog)JErrorPane.this.getTopLevelAncestor()).dispose();
        }
    }
}
