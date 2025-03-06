// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.ext.swing;

import java.awt.Container;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import java.awt.FlowLayout;
import javax.swing.JPanel;
import java.awt.LayoutManager;
import java.awt.BorderLayout;
import javax.swing.JOptionPane;
import javax.swing.JDialog;
import java.awt.event.WindowEvent;
import java.awt.event.WindowAdapter;
import java.awt.Window;
import java.awt.event.ComponentEvent;
import java.io.Serializable;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentListener;
import java.awt.event.WindowListener;
import java.awt.event.ActionListener;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.BorderFactory;
import javax.swing.JComponent;
import java.awt.Component;
import javax.swing.text.Document;
import java.awt.geom.AffineTransform;

public class JAffineTransformChooser extends JGridBagPanel
{
    public static final String LABEL_ANGLE = "JAffineTransformChooser.label.angle";
    public static final String LABEL_DEGREE = "JAffineTransformChooser.label.degree";
    public static final String LABEL_PERCENT = "JAffineTransformChooser.label.percent";
    public static final String LABEL_ROTATE = "JAffineTransformChooser.label.rotate";
    public static final String LABEL_SCALE = "JAffineTransformChooser.label.scale";
    public static final String LABEL_RX = "JAffineTransformChooser.label.rx";
    public static final String LABEL_RY = "JAffineTransformChooser.label.ry";
    public static final String LABEL_SX = "JAffineTransformChooser.label.sx";
    public static final String LABEL_SY = "JAffineTransformChooser.label.sy";
    public static final String LABEL_TRANSLATE = "JAffineTransformChooser.label.translate";
    public static final String LABEL_TX = "JAffineTransformChooser.label.tx";
    public static final String LABEL_TY = "JAffineTransformChooser.label.ty";
    public static final String CONFIG_TEXT_FIELD_WIDTH = "JAffineTransformChooser.config.text.field.width";
    public static final String CONFIG_TOP_PAD = "JAffineTransformChooser.config.top.pad";
    public static final String CONFIG_LEFT_PAD = "JAffineTransformChooser.config.left.pad";
    public static final String CONFIG_BOTTOM_PAD = "JAffineTransformChooser.config.bottom.pad";
    public static final String CONFIG_RIGHT_PAD = "JAffineTransformChooser.config.right.pad";
    protected AffineTransform txf;
    protected DoubleDocument txModel;
    protected DoubleDocument tyModel;
    protected DoubleDocument sxModel;
    protected DoubleDocument syModel;
    protected DoubleDocument rxModel;
    protected DoubleDocument ryModel;
    protected DoubleDocument rotateModel;
    protected static final double RAD_TO_DEG = 57.29577951308232;
    protected static final double DEG_TO_RAD = 0.017453292519943295;
    
    public JAffineTransformChooser() {
        this.txModel = new DoubleDocument();
        this.tyModel = new DoubleDocument();
        this.sxModel = new DoubleDocument();
        this.syModel = new DoubleDocument();
        this.rxModel = new DoubleDocument();
        this.ryModel = new DoubleDocument();
        this.rotateModel = new DoubleDocument();
        this.build();
        this.setAffineTransform(new AffineTransform());
    }
    
    protected void build() {
        final Component buildPanel = this.buildPanel(Resources.getString("JAffineTransformChooser.label.translate"), Resources.getString("JAffineTransformChooser.label.tx"), this.txModel, Resources.getString("JAffineTransformChooser.label.ty"), this.tyModel, "", "", true);
        final Component buildPanel2 = this.buildPanel(Resources.getString("JAffineTransformChooser.label.scale"), Resources.getString("JAffineTransformChooser.label.sx"), this.sxModel, Resources.getString("JAffineTransformChooser.label.sy"), this.syModel, Resources.getString("JAffineTransformChooser.label.percent"), Resources.getString("JAffineTransformChooser.label.percent"), true);
        final Component buildRotatePanel = this.buildRotatePanel();
        this.add(buildPanel, 0, 0, 1, 1, 10, 1, 1.0, 1.0);
        this.add(buildPanel2, 1, 0, 1, 1, 10, 1, 1.0, 1.0);
        this.add(buildRotatePanel, 0, 1, 2, 1, 10, 1, 1.0, 1.0);
    }
    
    protected Component buildRotatePanel() {
        final JGridBagPanel gridBagPanel = new JGridBagPanel();
        final Component buildPanel = this.buildPanel(Resources.getString("JAffineTransformChooser.label.rotate"), Resources.getString("JAffineTransformChooser.label.angle"), this.rotateModel, null, null, Resources.getString("JAffineTransformChooser.label.degree"), null, false);
        final Component buildPanel2 = this.buildPanel("", Resources.getString("JAffineTransformChooser.label.rx"), this.rxModel, Resources.getString("JAffineTransformChooser.label.ry"), this.ryModel, null, null, false);
        gridBagPanel.add(buildPanel, 0, 0, 1, 1, 10, 1, 1.0, 1.0);
        gridBagPanel.add(buildPanel2, 1, 0, 1, 1, 10, 1, 1.0, 1.0);
        this.setPanelBorder(gridBagPanel, Resources.getString("JAffineTransformChooser.label.rotate"));
        return gridBagPanel;
    }
    
    protected Component buildPanel(final String s, final String s2, final Document document, final String s3, final Document document2, final String s4, final String s5, final boolean b) {
        final JGridBagPanel gridBagPanel = new JGridBagPanel();
        this.addToPanelAtRow(s2, document, s4, gridBagPanel, 0);
        if (s3 != null) {
            this.addToPanelAtRow(s3, document2, s5, gridBagPanel, 1);
        }
        if (b) {
            this.setPanelBorder(gridBagPanel, s);
        }
        return gridBagPanel;
    }
    
    public void setPanelBorder(final JComponent component, final String title) {
        component.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), title), BorderFactory.createEmptyBorder(Resources.getInteger("JAffineTransformChooser.config.top.pad"), Resources.getInteger("JAffineTransformChooser.config.left.pad"), Resources.getInteger("JAffineTransformChooser.config.bottom.pad"), Resources.getInteger("JAffineTransformChooser.config.right.pad"))));
    }
    
    protected void addToPanelAtRow(final String text, final Document document, final String text2, final JGridBagPanel gridBagPanel, final int n) {
        final JTextField textField = new JTextField(Resources.getInteger("JAffineTransformChooser.config.text.field.width"));
        textField.setDocument(document);
        gridBagPanel.add(new JLabel(text), 0, n, 1, 1, 17, 2, 0.0, 0.0);
        gridBagPanel.add(textField, 1, n, 1, 1, 10, 2, 1.0, 0.0);
        gridBagPanel.add(new JLabel(text2), 2, n, 1, 1, 17, 2, 0.0, 0.0);
    }
    
    public AffineTransform getAffineTransform() {
        final double n = this.sxModel.getValue() / 100.0;
        final double n2 = this.syModel.getValue() / 100.0;
        final double n3 = this.rotateModel.getValue() * 0.017453292519943295;
        final double value = this.rxModel.getValue();
        final double value2 = this.ryModel.getValue();
        final double value3 = this.txModel.getValue();
        final double value4 = this.tyModel.getValue();
        final double[] flatmatrix = new double[6];
        final double sin = Math.sin(n3);
        final double cos = Math.cos(n3);
        flatmatrix[0] = n * cos;
        flatmatrix[1] = n * sin;
        flatmatrix[2] = -n2 * sin;
        flatmatrix[3] = n2 * cos;
        flatmatrix[4] = value3 + value - value * cos + value2 * sin;
        flatmatrix[5] = value4 + value2 - value * sin - value2 * cos;
        return this.txf = new AffineTransform(flatmatrix);
    }
    
    public void setAffineTransform(AffineTransform txf) {
        if (txf == null) {
            txf = new AffineTransform();
        }
        this.txf = txf;
        final double[] flatmatrix = new double[6];
        txf.getMatrix(flatmatrix);
        this.txModel.setValue(flatmatrix[4]);
        this.tyModel.setValue(flatmatrix[5]);
        final double sqrt = Math.sqrt(flatmatrix[0] * flatmatrix[0] + flatmatrix[1] * flatmatrix[1]);
        final double sqrt2 = Math.sqrt(flatmatrix[2] * flatmatrix[2] + flatmatrix[3] * flatmatrix[3]);
        this.sxModel.setValue(100.0 * sqrt);
        this.syModel.setValue(100.0 * sqrt2);
        double atan2 = 0.0;
        if (flatmatrix[0] > 0.0) {
            atan2 = Math.atan2(flatmatrix[1], flatmatrix[0]);
        }
        this.rotateModel.setValue(57.29577951308232 * atan2);
        this.rxModel.setValue(0.0);
        this.ryModel.setValue(0.0);
    }
    
    public static AffineTransform showDialog(final Component component, final String s) {
        final JAffineTransformChooser affineTransformChooser = new JAffineTransformChooser();
        final AffineTransformTracker affineTransformTracker = new AffineTransformTracker(affineTransformChooser);
        final Dialog dialog = new Dialog(component, s, true, affineTransformChooser, affineTransformTracker, null);
        dialog.addWindowListener(new Closer());
        dialog.addComponentListener(new DisposeOnClose());
        dialog.setVisible(true);
        return affineTransformTracker.getAffineTransform();
    }
    
    public static Dialog createDialog(final Component component, final String s) {
        final JAffineTransformChooser affineTransformChooser = new JAffineTransformChooser();
        final Dialog dialog = new Dialog(component, s, true, affineTransformChooser, new AffineTransformTracker(affineTransformChooser), null);
        dialog.addWindowListener(new Closer());
        dialog.addComponentListener(new DisposeOnClose());
        return dialog;
    }
    
    public static void main(final String[] array) {
        final AffineTransform showDialog = showDialog(null, "Hello");
        if (showDialog == null) {
            System.out.println("Cancelled");
        }
        else {
            System.out.println("t = " + showDialog);
        }
    }
    
    static class DisposeOnClose extends ComponentAdapter implements Serializable
    {
        public void componentHidden(final ComponentEvent componentEvent) {
            ((Window)componentEvent.getComponent()).dispose();
        }
    }
    
    static class Closer extends WindowAdapter implements Serializable
    {
        public void windowClosing(final WindowEvent windowEvent) {
            windowEvent.getWindow().setVisible(false);
        }
    }
    
    public static class Dialog extends JDialog
    {
        private JAffineTransformChooser chooserPane;
        private AffineTransformTracker tracker;
        public static final String LABEL_OK = "JAffineTransformChooser.label.ok";
        public static final String LABEL_CANCEL = "JAffineTransformChooser.label.cancel";
        public static final String LABEL_RESET = "JAffineTransformChooser.label.reset";
        public static final String ACTION_COMMAND_OK = "OK";
        public static final String ACTION_COMMAND_CANCEL = "cancel";
        
        public Dialog(final Component component, final String title, final boolean modal, final JAffineTransformChooser affineTransformChooser, final AffineTransformTracker affineTransformTracker, final ActionListener actionListener) {
            super(JOptionPane.getFrameForComponent(component), title, modal);
            this.chooserPane = affineTransformChooser;
            this.tracker = affineTransformTracker;
            final String string = Resources.getString("JAffineTransformChooser.label.ok");
            final String string2 = Resources.getString("JAffineTransformChooser.label.cancel");
            final String string3 = Resources.getString("JAffineTransformChooser.label.reset");
            final Container contentPane = this.getContentPane();
            contentPane.setLayout(new BorderLayout());
            contentPane.add(affineTransformChooser, "Center");
            final JPanel comp = new JPanel();
            comp.setLayout(new FlowLayout(1));
            final JButton button = new JButton(string);
            this.getRootPane().setDefaultButton(button);
            button.setActionCommand("OK");
            if (affineTransformTracker != null) {
                button.addActionListener(affineTransformTracker);
            }
            button.addActionListener(new ActionListener() {
                private final /* synthetic */ Dialog this$0 = this$0;
                
                public void actionPerformed(final ActionEvent actionEvent) {
                    this.this$0.setVisible(false);
                }
            });
            comp.add(button);
            final JButton comp2 = new JButton(string2);
            this.addKeyListener(new KeyAdapter() {
                private final /* synthetic */ Dialog this$0 = this$0;
                
                public void keyPressed(final KeyEvent keyEvent) {
                    if (keyEvent.getKeyCode() == 27) {
                        this.this$0.setVisible(false);
                    }
                }
            });
            comp2.addActionListener(new ActionListener() {
                private final /* synthetic */ Dialog this$0 = this$0;
                
                public void actionPerformed(final ActionEvent actionEvent) {
                    this.this$0.setVisible(false);
                }
            });
            comp.add(comp2);
            final JButton comp3 = new JButton(string3);
            comp3.addActionListener(new ActionListener() {
                private final /* synthetic */ Dialog this$0 = this$0;
                
                public void actionPerformed(final ActionEvent actionEvent) {
                    this.this$0.reset();
                }
            });
            comp.add(comp3);
            contentPane.add(comp, "South");
            this.pack();
            this.setLocationRelativeTo(component);
        }
        
        public void setVisible(final boolean visible) {
            if (visible) {
                this.tracker.reset();
            }
            super.setVisible(visible);
        }
        
        public AffineTransform showDialog() {
            this.setVisible(true);
            return this.tracker.getAffineTransform();
        }
        
        public void reset() {
            this.chooserPane.setAffineTransform(new AffineTransform());
        }
        
        public void setTransform(AffineTransform affineTransform) {
            if (affineTransform == null) {
                affineTransform = new AffineTransform();
            }
            this.chooserPane.setAffineTransform(affineTransform);
        }
    }
}
