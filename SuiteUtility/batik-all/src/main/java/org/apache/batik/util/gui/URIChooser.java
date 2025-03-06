// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.util.gui;

import javax.swing.event.DocumentEvent;
import java.io.File;
import java.io.IOException;
import javax.swing.JFileChooser;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import java.util.Locale;
import org.apache.batik.util.gui.resource.MissingListenerException;
import javax.swing.Action;
import java.awt.FlowLayout;
import javax.swing.event.DocumentListener;
import javax.swing.JLabel;
import java.awt.Insets;
import javax.swing.BorderFactory;
import java.awt.LayoutManager;
import java.awt.GridBagLayout;
import javax.swing.JPanel;
import java.awt.Component;
import java.awt.Frame;
import javax.swing.JFrame;
import java.util.HashMap;
import java.awt.Dialog;
import java.util.Map;
import javax.swing.filechooser.FileFilter;
import javax.swing.JButton;
import javax.swing.JTextField;
import org.apache.batik.util.gui.resource.ButtonFactory;
import org.apache.batik.util.resources.ResourceManager;
import java.util.ResourceBundle;
import org.apache.batik.util.gui.resource.ActionMap;
import javax.swing.JDialog;

public class URIChooser extends JDialog implements ActionMap
{
    public static final int OK_OPTION = 0;
    public static final int CANCEL_OPTION = 1;
    protected static final String RESOURCES = "org.apache.batik.util.gui.resources.URIChooserMessages";
    protected static ResourceBundle bundle;
    protected static ResourceManager resources;
    protected ButtonFactory buttonFactory;
    protected JTextField textField;
    protected JButton okButton;
    protected JButton clearButton;
    protected String currentPath;
    protected FileFilter fileFilter;
    protected int returnCode;
    protected String chosenPath;
    protected Map listeners;
    
    public URIChooser(final JDialog owner) {
        super(owner);
        this.currentPath = ".";
        this.listeners = new HashMap(10);
        this.initialize();
    }
    
    public URIChooser(final JFrame owner) {
        super(owner);
        this.currentPath = ".";
        this.listeners = new HashMap(10);
        this.initialize();
    }
    
    public int showDialog() {
        this.pack();
        this.setVisible(true);
        return this.returnCode;
    }
    
    public String getText() {
        return this.chosenPath;
    }
    
    public void setFileFilter(final FileFilter fileFilter) {
        this.fileFilter = fileFilter;
    }
    
    protected void initialize() {
        this.setModal(true);
        this.listeners.put("BrowseButtonAction", new BrowseButtonAction());
        this.listeners.put("OKButtonAction", new OKButtonAction());
        this.listeners.put("CancelButtonAction", new CancelButtonAction());
        this.listeners.put("ClearButtonAction", new ClearButtonAction());
        this.setTitle(URIChooser.resources.getString("Dialog.title"));
        this.buttonFactory = new ButtonFactory(URIChooser.bundle, this);
        this.getContentPane().add(this.createURISelectionPanel(), "North");
        this.getContentPane().add(this.createButtonsPanel(), "South");
    }
    
    protected JPanel createURISelectionPanel() {
        final JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        final ExtendedGridBagConstraints constraints = new ExtendedGridBagConstraints();
        constraints.insets = new Insets(5, 5, 5, 5);
        constraints.weightx = 0.0;
        constraints.weighty = 0.0;
        constraints.setGridBounds(0, 0, constraints.fill = 2, 1);
        panel.add(new JLabel(URIChooser.resources.getString("Dialog.label")), constraints);
        this.textField = new JTextField(30);
        this.textField.getDocument().addDocumentListener(new DocumentAdapter());
        constraints.weightx = 1.0;
        constraints.weighty = 0.0;
        constraints.fill = 2;
        constraints.setGridBounds(0, 1, 1, 1);
        panel.add(this.textField, constraints);
        constraints.weightx = 0.0;
        constraints.weighty = 0.0;
        constraints.fill = 0;
        constraints.setGridBounds(1, 1, 1, 1);
        panel.add(this.buttonFactory.createJButton("BrowseButton"), constraints);
        return panel;
    }
    
    protected JPanel createButtonsPanel() {
        final JPanel panel = new JPanel(new FlowLayout());
        panel.add(this.okButton = this.buttonFactory.createJButton("OKButton"));
        panel.add(this.buttonFactory.createJButton("CancelButton"));
        panel.add(this.clearButton = this.buttonFactory.createJButton("ClearButton"));
        this.okButton.setEnabled(false);
        this.clearButton.setEnabled(false);
        return panel;
    }
    
    protected void updateOKButtonAction() {
        this.okButton.setEnabled(!this.textField.getText().equals(""));
    }
    
    protected void updateClearButtonAction() {
        this.clearButton.setEnabled(!this.textField.getText().equals(""));
    }
    
    public Action getAction(final String s) throws MissingListenerException {
        return this.listeners.get(s);
    }
    
    static {
        URIChooser.bundle = ResourceBundle.getBundle("org.apache.batik.util.gui.resources.URIChooserMessages", Locale.getDefault());
        URIChooser.resources = new ResourceManager(URIChooser.bundle);
    }
    
    protected class ClearButtonAction extends AbstractAction
    {
        public void actionPerformed(final ActionEvent actionEvent) {
            URIChooser.this.textField.setText("");
        }
    }
    
    protected class CancelButtonAction extends AbstractAction
    {
        public void actionPerformed(final ActionEvent actionEvent) {
            URIChooser.this.returnCode = 1;
            URIChooser.this.dispose();
            URIChooser.this.textField.setText(URIChooser.this.chosenPath);
        }
    }
    
    protected class OKButtonAction extends AbstractAction
    {
        public void actionPerformed(final ActionEvent actionEvent) {
            URIChooser.this.returnCode = 0;
            URIChooser.this.chosenPath = URIChooser.this.textField.getText();
            URIChooser.this.dispose();
        }
    }
    
    protected class BrowseButtonAction extends AbstractAction
    {
        public void actionPerformed(final ActionEvent actionEvent) {
            final JFileChooser fileChooser = new JFileChooser(URIChooser.this.currentPath);
            fileChooser.setFileHidingEnabled(false);
            fileChooser.setFileSelectionMode(2);
            if (URIChooser.this.fileFilter != null) {
                fileChooser.setFileFilter(URIChooser.this.fileFilter);
            }
            if (fileChooser.showOpenDialog(URIChooser.this) == 0) {
                final File selectedFile = fileChooser.getSelectedFile();
                try {
                    URIChooser.this.textField.setText(URIChooser.this.currentPath = selectedFile.getCanonicalPath());
                }
                catch (IOException ex) {}
            }
        }
    }
    
    protected class DocumentAdapter implements DocumentListener
    {
        public void changedUpdate(final DocumentEvent documentEvent) {
            URIChooser.this.updateOKButtonAction();
            URIChooser.this.updateClearButtonAction();
        }
        
        public void insertUpdate(final DocumentEvent documentEvent) {
            URIChooser.this.updateOKButtonAction();
            URIChooser.this.updateClearButtonAction();
        }
        
        public void removeUpdate(final DocumentEvent documentEvent) {
            URIChooser.this.updateOKButtonAction();
            URIChooser.this.updateClearButtonAction();
        }
    }
}
