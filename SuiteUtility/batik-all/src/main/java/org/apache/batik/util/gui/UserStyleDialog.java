// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.util.gui;

import javax.swing.JOptionPane;
import javax.swing.event.ChangeEvent;
import java.io.IOException;
import javax.swing.JFileChooser;
import java.io.File;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import java.awt.event.ActionListener;
import javax.swing.event.ChangeListener;
import java.awt.Insets;
import javax.swing.border.Border;
import javax.swing.BorderFactory;
import java.awt.GridBagLayout;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JCheckBox;
import java.util.Locale;
import org.apache.batik.util.gui.resource.MissingListenerException;
import javax.swing.Action;
import org.apache.batik.util.gui.resource.ButtonFactory;
import java.awt.LayoutManager;
import java.awt.FlowLayout;
import javax.swing.JPanel;
import java.awt.Component;
import java.util.HashMap;
import java.awt.Frame;
import javax.swing.JFrame;
import java.util.Map;
import org.apache.batik.util.resources.ResourceManager;
import java.util.ResourceBundle;
import org.apache.batik.util.gui.resource.ActionMap;
import javax.swing.JDialog;

public class UserStyleDialog extends JDialog implements ActionMap
{
    public static final int OK_OPTION = 0;
    public static final int CANCEL_OPTION = 1;
    protected static final String RESOURCES = "org.apache.batik.util.gui.resources.UserStyleDialog";
    protected static ResourceBundle bundle;
    protected static ResourceManager resources;
    protected Panel panel;
    protected String chosenPath;
    protected int returnCode;
    protected Map listeners;
    
    public UserStyleDialog(final JFrame owner) {
        super(owner);
        this.listeners = new HashMap();
        this.setModal(true);
        this.setTitle(UserStyleDialog.resources.getString("Dialog.title"));
        this.listeners.put("OKButtonAction", new OKButtonAction());
        this.listeners.put("CancelButtonAction", new CancelButtonAction());
        this.getContentPane().add(this.panel = new Panel());
        this.getContentPane().add(this.createButtonsPanel(), "South");
        this.pack();
    }
    
    public int showDialog() {
        this.pack();
        this.setVisible(true);
        return this.returnCode;
    }
    
    public String getPath() {
        return this.chosenPath;
    }
    
    public void setPath(final String s) {
        this.chosenPath = s;
        this.panel.fileTextField.setText(s);
        this.panel.fileCheckBox.setSelected(true);
    }
    
    protected JPanel createButtonsPanel() {
        final JPanel panel = new JPanel(new FlowLayout(2));
        final ButtonFactory buttonFactory = new ButtonFactory(UserStyleDialog.bundle, this);
        panel.add(buttonFactory.createJButton("OKButton"));
        panel.add(buttonFactory.createJButton("CancelButton"));
        return panel;
    }
    
    public Action getAction(final String s) throws MissingListenerException {
        return this.listeners.get(s);
    }
    
    static {
        UserStyleDialog.bundle = ResourceBundle.getBundle("org.apache.batik.util.gui.resources.UserStyleDialog", Locale.getDefault());
        UserStyleDialog.resources = new ResourceManager(UserStyleDialog.bundle);
    }
    
    public static class Panel extends JPanel
    {
        protected JCheckBox fileCheckBox;
        protected JLabel fileLabel;
        protected JTextField fileTextField;
        protected JButton browseButton;
        
        public Panel() {
            super(new GridBagLayout());
            this.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), UserStyleDialog.resources.getString("Panel.title")));
            final ExtendedGridBagConstraints extendedGridBagConstraints = new ExtendedGridBagConstraints();
            extendedGridBagConstraints.insets = new Insets(5, 5, 5, 5);
            (this.fileCheckBox = new JCheckBox(UserStyleDialog.resources.getString("PanelFileCheckBox.text"))).addChangeListener(new FileCheckBoxChangeListener());
            extendedGridBagConstraints.weightx = 0.0;
            extendedGridBagConstraints.weighty = 0.0;
            extendedGridBagConstraints.setGridBounds(0, extendedGridBagConstraints.fill = 2, 3, 1);
            this.add(this.fileCheckBox, extendedGridBagConstraints);
            this.fileLabel = new JLabel(UserStyleDialog.resources.getString("PanelFileLabel.text"));
            extendedGridBagConstraints.weightx = 0.0;
            extendedGridBagConstraints.weighty = 0.0;
            extendedGridBagConstraints.fill = 2;
            extendedGridBagConstraints.setGridBounds(0, 3, 3, 1);
            this.add(this.fileLabel, extendedGridBagConstraints);
            this.fileTextField = new JTextField(30);
            extendedGridBagConstraints.weightx = 1.0;
            extendedGridBagConstraints.weighty = 0.0;
            extendedGridBagConstraints.setGridBounds(0, 4, extendedGridBagConstraints.fill = 2, 1);
            this.add(this.fileTextField, extendedGridBagConstraints);
            final ButtonFactory buttonFactory = new ButtonFactory(UserStyleDialog.bundle, null);
            extendedGridBagConstraints.weightx = 0.0;
            extendedGridBagConstraints.weighty = 0.0;
            extendedGridBagConstraints.fill = 0;
            extendedGridBagConstraints.anchor = 13;
            extendedGridBagConstraints.setGridBounds(2, 4, 1, 1);
            this.add(this.browseButton = buttonFactory.createJButton("PanelFileBrowseButton"), extendedGridBagConstraints);
            this.browseButton.addActionListener(new FileBrowseButtonAction());
            this.fileLabel.setEnabled(false);
            this.fileTextField.setEnabled(false);
            this.browseButton.setEnabled(false);
        }
        
        public String getPath() {
            if (this.fileCheckBox.isSelected()) {
                return this.fileTextField.getText();
            }
            return null;
        }
        
        public void setPath(final String text) {
            if (text == null) {
                this.fileTextField.setEnabled(false);
                this.fileCheckBox.setSelected(false);
            }
            else {
                this.fileTextField.setEnabled(true);
                this.fileTextField.setText(text);
                this.fileCheckBox.setSelected(true);
            }
        }
        
        protected class FileBrowseButtonAction extends AbstractAction
        {
            public void actionPerformed(final ActionEvent actionEvent) {
                final JFileChooser fileChooser = new JFileChooser(new File("."));
                fileChooser.setFileHidingEnabled(false);
                if (fileChooser.showOpenDialog(Panel.this) == 0) {
                    final File selectedFile = fileChooser.getSelectedFile();
                    try {
                        Panel.this.fileTextField.setText(selectedFile.getCanonicalPath());
                    }
                    catch (IOException ex) {}
                }
            }
        }
        
        protected class FileCheckBoxChangeListener implements ChangeListener
        {
            public void stateChanged(final ChangeEvent changeEvent) {
                final boolean selected = Panel.this.fileCheckBox.isSelected();
                Panel.this.fileLabel.setEnabled(selected);
                Panel.this.fileTextField.setEnabled(selected);
                Panel.this.browseButton.setEnabled(selected);
            }
        }
    }
    
    protected class CancelButtonAction extends AbstractAction
    {
        public void actionPerformed(final ActionEvent actionEvent) {
            UserStyleDialog.this.returnCode = 1;
            UserStyleDialog.this.dispose();
        }
    }
    
    protected class OKButtonAction extends AbstractAction
    {
        public void actionPerformed(final ActionEvent actionEvent) {
            if (UserStyleDialog.this.panel.fileCheckBox.isSelected()) {
                String chosenPath = UserStyleDialog.this.panel.fileTextField.getText();
                if (chosenPath.equals("")) {
                    JOptionPane.showMessageDialog(UserStyleDialog.this, UserStyleDialog.resources.getString("StyleDialogError.text"), UserStyleDialog.resources.getString("StyleDialogError.title"), 0);
                    return;
                }
                final File file = new File(chosenPath);
                if (file.exists()) {
                    if (file.isDirectory()) {
                        chosenPath = null;
                    }
                    else {
                        chosenPath = "file:" + chosenPath;
                    }
                }
                UserStyleDialog.this.chosenPath = chosenPath;
            }
            else {
                UserStyleDialog.this.chosenPath = null;
            }
            UserStyleDialog.this.returnCode = 0;
            UserStyleDialog.this.dispose();
        }
    }
}
