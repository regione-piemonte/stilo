// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.util.gui;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListDataEvent;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import javax.swing.JComboBox;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import java.awt.FlowLayout;
import javax.swing.JOptionPane;
import javax.swing.JDialog;
import java.util.Locale;
import org.apache.batik.util.gui.resource.MissingListenerException;
import javax.swing.Action;
import java.util.Enumeration;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.Iterator;
import java.util.List;
import org.apache.batik.util.gui.resource.ButtonFactory;
import java.awt.Component;
import javax.swing.JScrollPane;
import javax.swing.event.ListDataListener;
import javax.swing.event.ListSelectionListener;
import javax.swing.ListModel;
import java.awt.Insets;
import javax.swing.border.Border;
import javax.swing.BorderFactory;
import java.util.HashMap;
import java.awt.LayoutManager;
import java.awt.GridBagLayout;
import java.util.Map;
import javax.swing.JList;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import org.apache.batik.util.resources.ResourceManager;
import java.util.ResourceBundle;
import org.apache.batik.util.gui.resource.ActionMap;
import javax.swing.JPanel;

public class CSSMediaPanel extends JPanel implements ActionMap
{
    protected static final String RESOURCES = "org.apache.batik.util.gui.resources.CSSMediaPanel";
    protected static ResourceBundle bundle;
    protected static ResourceManager resources;
    protected JButton removeButton;
    protected JButton addButton;
    protected JButton clearButton;
    protected DefaultListModel listModel;
    protected JList mediaList;
    protected Map listeners;
    
    public CSSMediaPanel() {
        super(new GridBagLayout());
        this.listModel = new DefaultListModel();
        (this.listeners = new HashMap()).put("AddButtonAction", new AddButtonAction());
        this.listeners.put("RemoveButtonAction", new RemoveButtonAction());
        this.listeners.put("ClearButtonAction", new ClearButtonAction());
        this.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), CSSMediaPanel.resources.getString("Panel.title")));
        final ExtendedGridBagConstraints extendedGridBagConstraints = new ExtendedGridBagConstraints();
        extendedGridBagConstraints.insets = new Insets(5, 5, 5, 5);
        (this.mediaList = new JList()).setSelectionMode(0);
        this.mediaList.setModel(this.listModel);
        this.mediaList.addListSelectionListener(new MediaListSelectionListener());
        this.listModel.addListDataListener(new MediaListDataListener());
        final JScrollPane comp = new JScrollPane();
        comp.setBorder(BorderFactory.createLoweredBevelBorder());
        extendedGridBagConstraints.weightx = 1.0;
        extendedGridBagConstraints.weighty = 1.0;
        extendedGridBagConstraints.setGridBounds(0, 0, extendedGridBagConstraints.fill = 1, 3);
        comp.getViewport().add(this.mediaList);
        this.add(comp, extendedGridBagConstraints);
        final ButtonFactory buttonFactory = new ButtonFactory(CSSMediaPanel.bundle, this);
        extendedGridBagConstraints.weightx = 0.0;
        extendedGridBagConstraints.weighty = 0.0;
        extendedGridBagConstraints.fill = 2;
        extendedGridBagConstraints.anchor = 11;
        this.addButton = buttonFactory.createJButton("AddButton");
        extendedGridBagConstraints.setGridBounds(1, 0, 1, 1);
        this.add(this.addButton, extendedGridBagConstraints);
        this.removeButton = buttonFactory.createJButton("RemoveButton");
        extendedGridBagConstraints.setGridBounds(1, 1, 1, 1);
        this.add(this.removeButton, extendedGridBagConstraints);
        this.clearButton = buttonFactory.createJButton("ClearButton");
        extendedGridBagConstraints.setGridBounds(1, 2, 1, 1);
        this.add(this.clearButton, extendedGridBagConstraints);
        this.updateButtons();
    }
    
    protected void updateButtons() {
        this.removeButton.setEnabled(!this.mediaList.isSelectionEmpty());
        this.clearButton.setEnabled(!this.listModel.isEmpty());
    }
    
    public void setMedia(final List list) {
        this.listModel.removeAllElements();
        final Iterator<Object> iterator = list.iterator();
        while (iterator.hasNext()) {
            this.listModel.addElement(iterator.next());
        }
    }
    
    public void setMedia(final String str) {
        this.listModel.removeAllElements();
        final StringTokenizer stringTokenizer = new StringTokenizer(str, " ");
        while (stringTokenizer.hasMoreTokens()) {
            this.listModel.addElement(stringTokenizer.nextToken());
        }
    }
    
    public List getMedia() {
        final ArrayList<Object> list = new ArrayList<Object>(this.listModel.size());
        final Enumeration<Object> elements = this.listModel.elements();
        while (elements.hasMoreElements()) {
            list.add(elements.nextElement());
        }
        return list;
    }
    
    public String getMediaAsString() {
        final StringBuffer sb = new StringBuffer();
        final Enumeration<String> elements = this.listModel.elements();
        while (elements.hasMoreElements()) {
            sb.append(elements.nextElement());
            sb.append(' ');
        }
        return sb.toString();
    }
    
    public static int showDialog(final Component component, final String s) {
        return showDialog(component, s, "");
    }
    
    public static int showDialog(final Component component, final String s, final List list) {
        final Dialog dialog = new Dialog(component, s, list);
        dialog.setModal(true);
        dialog.pack();
        dialog.setVisible(true);
        return dialog.getReturnCode();
    }
    
    public static int showDialog(final Component component, final String s, final String s2) {
        final Dialog dialog = new Dialog(component, s, s2);
        dialog.setModal(true);
        dialog.pack();
        dialog.setVisible(true);
        return dialog.getReturnCode();
    }
    
    public Action getAction(final String s) throws MissingListenerException {
        return this.listeners.get(s);
    }
    
    public static void main(final String[] array) {
        System.out.println(showDialog(null, "Test", "all aural braille embossed handheld print projection screen tty tv"));
        System.exit(0);
    }
    
    static {
        CSSMediaPanel.bundle = ResourceBundle.getBundle("org.apache.batik.util.gui.resources.CSSMediaPanel", Locale.getDefault());
        CSSMediaPanel.resources = new ResourceManager(CSSMediaPanel.bundle);
    }
    
    public static class Dialog extends JDialog implements ActionMap
    {
        public static final int OK_OPTION = 0;
        public static final int CANCEL_OPTION = 1;
        protected int returnCode;
        protected Map listeners;
        
        public Dialog() {
            this(null, "", "");
        }
        
        public Dialog(final Component parentComponent, final String title, final List media) {
            super(JOptionPane.getFrameForComponent(parentComponent), title);
            (this.listeners = new HashMap()).put("OKButtonAction", new OKButtonAction());
            this.listeners.put("CancelButtonAction", new CancelButtonAction());
            final CSSMediaPanel comp = new CSSMediaPanel();
            comp.setMedia(media);
            this.getContentPane().add(comp, "Center");
            this.getContentPane().add(this.createButtonsPanel(), "South");
        }
        
        public Dialog(final Component parentComponent, final String title, final String media) {
            super(JOptionPane.getFrameForComponent(parentComponent), title);
            (this.listeners = new HashMap()).put("OKButtonAction", new OKButtonAction());
            this.listeners.put("CancelButtonAction", new CancelButtonAction());
            final CSSMediaPanel comp = new CSSMediaPanel();
            comp.setMedia(media);
            this.getContentPane().add(comp, "Center");
            this.getContentPane().add(this.createButtonsPanel(), "South");
        }
        
        public int getReturnCode() {
            return this.returnCode;
        }
        
        protected JPanel createButtonsPanel() {
            final JPanel panel = new JPanel(new FlowLayout(2));
            final ButtonFactory buttonFactory = new ButtonFactory(CSSMediaPanel.bundle, this);
            panel.add(buttonFactory.createJButton("OKButton"));
            panel.add(buttonFactory.createJButton("CancelButton"));
            return panel;
        }
        
        public Action getAction(final String s) throws MissingListenerException {
            return this.listeners.get(s);
        }
        
        protected class CancelButtonAction extends AbstractAction
        {
            public void actionPerformed(final ActionEvent actionEvent) {
                Dialog.this.returnCode = 1;
                Dialog.this.dispose();
            }
        }
        
        protected class OKButtonAction extends AbstractAction
        {
            public void actionPerformed(final ActionEvent actionEvent) {
                Dialog.this.returnCode = 0;
                Dialog.this.dispose();
            }
        }
    }
    
    public static class AddMediumDialog extends JDialog implements ActionMap
    {
        public static final int OK_OPTION = 0;
        public static final int CANCEL_OPTION = 1;
        protected JComboBox medium;
        protected int returnCode;
        protected Map listeners;
        
        public AddMediumDialog(final Component parentComponent) {
            super(JOptionPane.getFrameForComponent(parentComponent), CSSMediaPanel.resources.getString("AddMediumDialog.title"));
            this.listeners = new HashMap();
            this.setModal(true);
            this.listeners.put("OKButtonAction", new OKButtonAction());
            this.listeners.put("CancelButtonAction", new CancelButtonAction());
            this.getContentPane().add(this.createContentPanel(), "Center");
            this.getContentPane().add(this.createButtonsPanel(), "South");
        }
        
        public String getMedium() {
            return (String)this.medium.getSelectedItem();
        }
        
        protected Component createContentPanel() {
            final JPanel panel = new JPanel(new BorderLayout());
            panel.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
            panel.add(new JLabel(CSSMediaPanel.resources.getString("AddMediumDialog.label")), "West");
            (this.medium = new JComboBox()).setEditable(true);
            final StringTokenizer stringTokenizer = new StringTokenizer(CSSMediaPanel.resources.getString("Media.list"), " ");
            while (stringTokenizer.hasMoreTokens()) {
                this.medium.addItem(stringTokenizer.nextToken());
            }
            panel.add(this.medium, "Center");
            return panel;
        }
        
        protected Component createButtonsPanel() {
            final JPanel panel = new JPanel(new FlowLayout(2));
            final ButtonFactory buttonFactory = new ButtonFactory(CSSMediaPanel.bundle, this);
            panel.add(buttonFactory.createJButton("OKButton"));
            panel.add(buttonFactory.createJButton("CancelButton"));
            return panel;
        }
        
        public int getReturnCode() {
            return this.returnCode;
        }
        
        public Action getAction(final String s) throws MissingListenerException {
            return this.listeners.get(s);
        }
        
        protected class CancelButtonAction extends AbstractAction
        {
            public void actionPerformed(final ActionEvent actionEvent) {
                AddMediumDialog.this.returnCode = 1;
                AddMediumDialog.this.dispose();
            }
        }
        
        protected class OKButtonAction extends AbstractAction
        {
            public void actionPerformed(final ActionEvent actionEvent) {
                AddMediumDialog.this.returnCode = 0;
                AddMediumDialog.this.dispose();
            }
        }
    }
    
    protected class MediaListDataListener implements ListDataListener
    {
        public void contentsChanged(final ListDataEvent listDataEvent) {
            CSSMediaPanel.this.updateButtons();
        }
        
        public void intervalAdded(final ListDataEvent listDataEvent) {
            CSSMediaPanel.this.updateButtons();
        }
        
        public void intervalRemoved(final ListDataEvent listDataEvent) {
            CSSMediaPanel.this.updateButtons();
        }
    }
    
    protected class MediaListSelectionListener implements ListSelectionListener
    {
        public void valueChanged(final ListSelectionEvent listSelectionEvent) {
            CSSMediaPanel.this.updateButtons();
        }
    }
    
    protected class ClearButtonAction extends AbstractAction
    {
        public void actionPerformed(final ActionEvent actionEvent) {
            CSSMediaPanel.this.mediaList.clearSelection();
            CSSMediaPanel.this.listModel.removeAllElements();
        }
    }
    
    protected class RemoveButtonAction extends AbstractAction
    {
        public void actionPerformed(final ActionEvent actionEvent) {
            final int selectedIndex = CSSMediaPanel.this.mediaList.getSelectedIndex();
            CSSMediaPanel.this.mediaList.clearSelection();
            if (selectedIndex >= 0) {
                CSSMediaPanel.this.listModel.removeElementAt(selectedIndex);
            }
        }
    }
    
    protected class AddButtonAction extends AbstractAction
    {
        public void actionPerformed(final ActionEvent actionEvent) {
            final AddMediumDialog addMediumDialog = new AddMediumDialog(CSSMediaPanel.this);
            addMediumDialog.pack();
            addMediumDialog.setVisible(true);
            if (addMediumDialog.getReturnCode() == 1 || addMediumDialog.getMedium() == null) {
                return;
            }
            String trim = addMediumDialog.getMedium().trim();
            if (trim.length() == 0 || CSSMediaPanel.this.listModel.contains(trim)) {
                return;
            }
            for (int n = 0; n < CSSMediaPanel.this.listModel.size() && trim != null; ++n) {
                final int compareTo = trim.compareTo((String)CSSMediaPanel.this.listModel.getElementAt(n));
                if (compareTo == 0) {
                    trim = null;
                }
                else if (compareTo < 0) {
                    CSSMediaPanel.this.listModel.insertElementAt(trim, n);
                    trim = null;
                }
            }
            if (trim != null) {
                CSSMediaPanel.this.listModel.addElement(trim);
            }
        }
    }
}
