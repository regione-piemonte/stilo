// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.apps.svgbrowser;

import javax.swing.JOptionPane;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import java.util.Locale;
import org.apache.batik.util.gui.resource.MissingListenerException;
import javax.swing.Action;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Shape;
import org.apache.batik.gvt.text.Mark;
import java.text.AttributedCharacterIterator;
import java.awt.geom.AffineTransform;
import org.apache.batik.gvt.TextNode;
import java.awt.FlowLayout;
import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import java.awt.Insets;
import org.apache.batik.util.gui.ExtendedGridBagConstraints;
import javax.swing.border.Border;
import java.awt.GridBagLayout;
import java.awt.Component;
import javax.swing.BorderFactory;
import java.awt.LayoutManager;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.util.HashMap;
import java.awt.Frame;
import java.util.Map;
import javax.swing.JRadioButton;
import org.apache.batik.swing.JSVGCanvas;
import javax.swing.JCheckBox;
import javax.swing.JButton;
import javax.swing.JTextField;
import org.apache.batik.gvt.GVTTreeWalker;
import org.apache.batik.gvt.GraphicsNode;
import org.apache.batik.util.gui.resource.ButtonFactory;
import org.apache.batik.util.resources.ResourceManager;
import java.util.ResourceBundle;
import org.apache.batik.util.gui.resource.ActionMap;
import javax.swing.JDialog;

public class FindDialog extends JDialog implements ActionMap
{
    protected static final String RESOURCES = "org.apache.batik.apps.svgbrowser.resources.FindDialog";
    public static final String FIND_ACTION = "FindButtonAction";
    public static final String CLEAR_ACTION = "ClearButtonAction";
    public static final String CLOSE_ACTION = "CloseButtonAction";
    protected static ResourceBundle bundle;
    protected static ResourceManager resources;
    protected ButtonFactory buttonFactory;
    protected GraphicsNode gvtRoot;
    protected GVTTreeWalker walker;
    protected int currentIndex;
    protected JTextField search;
    protected JButton findButton;
    protected JButton clearButton;
    protected JButton closeButton;
    protected JCheckBox caseSensitive;
    protected JSVGCanvas svgCanvas;
    protected JRadioButton highlightButton;
    protected JRadioButton highlightCenterButton;
    protected JRadioButton highlightCenterZoomButton;
    protected Map listeners;
    
    public FindDialog(final JSVGCanvas jsvgCanvas) {
        this(null, jsvgCanvas);
    }
    
    public FindDialog(final Frame owner, final JSVGCanvas svgCanvas) {
        super(owner, FindDialog.resources.getString("Dialog.title"));
        this.listeners = new HashMap(10);
        this.svgCanvas = svgCanvas;
        this.buttonFactory = new ButtonFactory(FindDialog.bundle, this);
        this.listeners.put("FindButtonAction", new FindButtonAction());
        this.listeners.put("ClearButtonAction", new ClearButtonAction());
        this.listeners.put("CloseButtonAction", new CloseButtonAction());
        final JPanel comp = new JPanel(new BorderLayout());
        comp.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
        comp.add(this.createFindPanel(), "Center");
        comp.add(this.createShowResultPanel(), "South");
        this.getContentPane().add(comp, "Center");
        this.getContentPane().add(this.createButtonsPanel(), "South");
    }
    
    protected JPanel createFindPanel() {
        final JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), FindDialog.resources.getString("Panel.title")));
        final ExtendedGridBagConstraints constraints = new ExtendedGridBagConstraints();
        constraints.insets = new Insets(2, 2, 2, 2);
        constraints.anchor = 13;
        constraints.fill = 0;
        constraints.setWeight(0.0, 0.0);
        constraints.setGridBounds(0, 0, 1, 1);
        panel.add(new JLabel(FindDialog.resources.getString("FindLabel.text")), constraints);
        constraints.fill = 2;
        constraints.setWeight(1.0, 0.0);
        constraints.setGridBounds(1, 0, 2, 1);
        panel.add(this.search = new JTextField(20), constraints);
        constraints.fill = 0;
        constraints.anchor = 17;
        constraints.setWeight(0.0, 0.0);
        constraints.setGridBounds(1, 1, 1, 1);
        panel.add(this.caseSensitive = this.buttonFactory.createJCheckBox("CaseSensitiveCheckBox"), constraints);
        return panel;
    }
    
    protected JPanel createShowResultPanel() {
        final JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), FindDialog.resources.getString("ShowResultPanel.title")));
        final ExtendedGridBagConstraints constraints = new ExtendedGridBagConstraints();
        constraints.insets = new Insets(2, 2, 2, 2);
        constraints.anchor = 17;
        constraints.fill = 0;
        constraints.setWeight(0.0, 0.0);
        final ButtonGroup buttonGroup = new ButtonGroup();
        (this.highlightButton = this.buttonFactory.createJRadioButton("Highlight")).setSelected(true);
        buttonGroup.add(this.highlightButton);
        constraints.setGridBounds(0, 0, 1, 1);
        panel.add(this.highlightButton, constraints);
        buttonGroup.add(this.highlightCenterButton = this.buttonFactory.createJRadioButton("HighlightAndCenter"));
        constraints.setGridBounds(0, 1, 1, 1);
        panel.add(this.highlightCenterButton, constraints);
        buttonGroup.add(this.highlightCenterZoomButton = this.buttonFactory.createJRadioButton("HighlightCenterAndZoom"));
        constraints.setGridBounds(0, 2, 1, 1);
        panel.add(this.highlightCenterZoomButton, constraints);
        return panel;
    }
    
    protected JPanel createButtonsPanel() {
        final JPanel panel = new JPanel(new FlowLayout(2));
        panel.add(this.findButton = this.buttonFactory.createJButton("FindButton"));
        panel.add(this.clearButton = this.buttonFactory.createJButton("ClearButton"));
        panel.add(this.closeButton = this.buttonFactory.createJButton("CloseButton"));
        return panel;
    }
    
    public void setGraphicsNode(final GraphicsNode gvtRoot) {
        this.gvtRoot = gvtRoot;
        if (gvtRoot != null) {
            this.walker = new GVTTreeWalker(gvtRoot);
        }
        else {
            this.walker = null;
        }
    }
    
    protected GraphicsNode getNext(final String s) {
        if (this.walker == null && this.gvtRoot != null) {
            this.walker = new GVTTreeWalker(this.gvtRoot);
        }
        GraphicsNode graphicsNode = this.walker.getCurrentGraphicsNode();
        final int match = this.match(graphicsNode, s, this.currentIndex + s.length());
        if (match >= 0) {
            this.currentIndex = match;
        }
        else {
            this.currentIndex = 0;
            for (graphicsNode = this.walker.nextGraphicsNode(); graphicsNode != null && (this.currentIndex = this.match(graphicsNode, s, this.currentIndex)) < 0; graphicsNode = this.walker.nextGraphicsNode()) {
                this.currentIndex = 0;
            }
        }
        return graphicsNode;
    }
    
    protected int match(final GraphicsNode graphicsNode, String lowerCase, final int fromIndex) {
        if (!(graphicsNode instanceof TextNode) || !graphicsNode.isVisible() || lowerCase == null || lowerCase.length() == 0) {
            return -1;
        }
        String s = ((TextNode)graphicsNode).getText();
        if (!this.caseSensitive.isSelected()) {
            s = s.toLowerCase();
            lowerCase = lowerCase.toLowerCase();
        }
        return s.indexOf(lowerCase, fromIndex);
    }
    
    protected void showSelectedGraphicsNode() {
        final GraphicsNode currentGraphicsNode = this.walker.getCurrentGraphicsNode();
        if (!(currentGraphicsNode instanceof TextNode)) {
            return;
        }
        final TextNode textNode = (TextNode)currentGraphicsNode;
        String s = textNode.getText();
        String str = this.search.getText();
        if (!this.caseSensitive.isSelected()) {
            s = s.toLowerCase();
            str = str.toLowerCase();
        }
        final int index = s.indexOf(str, this.currentIndex);
        final AttributedCharacterIterator attributedCharacterIterator = textNode.getAttributedCharacterIterator();
        attributedCharacterIterator.first();
        for (int i = 0; i < index; ++i) {
            attributedCharacterIterator.next();
        }
        final Mark markerForChar = textNode.getMarkerForChar(attributedCharacterIterator.getIndex(), true);
        for (int j = 0; j < str.length() - 1; ++j) {
            attributedCharacterIterator.next();
        }
        this.svgCanvas.select(markerForChar, textNode.getMarkerForChar(attributedCharacterIterator.getIndex(), false));
        if (this.highlightButton.isSelected()) {
            return;
        }
        final Shape highlightShape = textNode.getHighlightShape();
        AffineTransform tx;
        if (this.highlightCenterZoomButton.isSelected()) {
            tx = this.svgCanvas.getInitialTransform();
        }
        else {
            tx = this.svgCanvas.getRenderingTransform();
        }
        final Rectangle bounds = tx.createTransformedShape(highlightShape).getBounds();
        final Dimension size = this.svgCanvas.getSize();
        final AffineTransform translateInstance = AffineTransform.getTranslateInstance(-bounds.getX() - bounds.getWidth() / 2.0, -bounds.getY() - bounds.getHeight() / 2.0);
        if (this.highlightCenterZoomButton.isSelected()) {
            final double n = Math.min(size.width / bounds.getWidth(), size.height / bounds.getHeight()) / 8.0;
            if (n > 1.0) {
                translateInstance.preConcatenate(AffineTransform.getScaleInstance(n, n));
            }
        }
        translateInstance.preConcatenate(AffineTransform.getTranslateInstance(size.width / 2, size.height / 2));
        final AffineTransform renderingTransform = new AffineTransform(tx);
        renderingTransform.preConcatenate(translateInstance);
        this.svgCanvas.setRenderingTransform(renderingTransform);
    }
    
    public Action getAction(final String s) throws MissingListenerException {
        return this.listeners.get(s);
    }
    
    static {
        FindDialog.bundle = ResourceBundle.getBundle("org.apache.batik.apps.svgbrowser.resources.FindDialog", Locale.getDefault());
        FindDialog.resources = new ResourceManager(FindDialog.bundle);
    }
    
    protected class CloseButtonAction extends AbstractAction
    {
        public void actionPerformed(final ActionEvent actionEvent) {
            FindDialog.this.dispose();
        }
    }
    
    protected class ClearButtonAction extends AbstractAction
    {
        public void actionPerformed(final ActionEvent actionEvent) {
            FindDialog.this.search.setText(null);
            FindDialog.this.walker = null;
        }
    }
    
    protected class FindButtonAction extends AbstractAction
    {
        public void actionPerformed(final ActionEvent actionEvent) {
            final String text = FindDialog.this.search.getText();
            if (text == null || text.length() == 0) {
                return;
            }
            if (FindDialog.this.getNext(text) != null) {
                FindDialog.this.showSelectedGraphicsNode();
            }
            else {
                FindDialog.this.walker = null;
                JOptionPane.showMessageDialog(FindDialog.this, FindDialog.resources.getString("End.text"), FindDialog.resources.getString("End.title"), 1);
            }
        }
    }
}
