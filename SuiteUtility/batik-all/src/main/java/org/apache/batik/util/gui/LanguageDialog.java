// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.util.gui;

import javax.swing.event.ListSelectionEvent;
import java.net.URL;
import java.util.MissingResourceException;
import javax.swing.ImageIcon;
import javax.swing.Icon;
import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.event.ListSelectionListener;
import javax.swing.JScrollPane;
import java.awt.Insets;
import java.util.StringTokenizer;
import javax.swing.ListCellRenderer;
import javax.swing.ListModel;
import javax.swing.border.Border;
import javax.swing.BorderFactory;
import java.awt.GridBagLayout;
import javax.swing.JButton;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import java.util.Locale;
import org.apache.batik.util.gui.resource.ButtonFactory;
import java.awt.LayoutManager;
import java.awt.FlowLayout;
import javax.swing.JPanel;
import org.apache.batik.util.gui.resource.MissingListenerException;
import javax.swing.Action;
import java.awt.Component;
import java.util.HashMap;
import java.awt.Frame;
import javax.swing.JFrame;
import java.util.Map;
import org.apache.batik.util.resources.ResourceManager;
import java.util.ResourceBundle;
import org.apache.batik.util.gui.resource.ActionMap;
import javax.swing.JDialog;

public class LanguageDialog extends JDialog implements ActionMap
{
    public static final int OK_OPTION = 0;
    public static final int CANCEL_OPTION = 1;
    protected static final String RESOURCES = "org.apache.batik.util.gui.resources.LanguageDialogMessages";
    protected static ResourceBundle bundle;
    protected static ResourceManager resources;
    protected Map listeners;
    protected Panel panel;
    protected int returnCode;
    
    public LanguageDialog(final JFrame owner) {
        super(owner);
        this.listeners = new HashMap();
        this.panel = new Panel();
        this.setModal(true);
        this.setTitle(LanguageDialog.resources.getString("Dialog.title"));
        this.listeners.put("OKButtonAction", new OKButtonAction());
        this.listeners.put("CancelButtonAction", new CancelButtonAction());
        this.getContentPane().add(this.panel);
        this.getContentPane().add(this.createButtonsPanel(), "South");
        this.pack();
    }
    
    public int showDialog() {
        this.setVisible(true);
        return this.returnCode;
    }
    
    public void setLanguages(final String languages) {
        this.panel.setLanguages(languages);
    }
    
    public String getLanguages() {
        return this.panel.getLanguages();
    }
    
    public Action getAction(final String s) throws MissingListenerException {
        return this.listeners.get(s);
    }
    
    protected JPanel createButtonsPanel() {
        final JPanel panel = new JPanel(new FlowLayout(2));
        final ButtonFactory buttonFactory = new ButtonFactory(LanguageDialog.bundle, this);
        panel.add(buttonFactory.createJButton("OKButton"));
        panel.add(buttonFactory.createJButton("CancelButton"));
        return panel;
    }
    
    static {
        LanguageDialog.bundle = ResourceBundle.getBundle("org.apache.batik.util.gui.resources.LanguageDialogMessages", Locale.getDefault());
        LanguageDialog.resources = new ResourceManager(LanguageDialog.bundle);
    }
    
    protected class CancelButtonAction extends AbstractAction
    {
        public void actionPerformed(final ActionEvent actionEvent) {
            LanguageDialog.this.returnCode = 1;
            LanguageDialog.this.dispose();
        }
    }
    
    protected class OKButtonAction extends AbstractAction
    {
        public void actionPerformed(final ActionEvent actionEvent) {
            LanguageDialog.this.returnCode = 0;
            LanguageDialog.this.dispose();
        }
    }
    
    public static class Panel extends JPanel implements ActionMap
    {
        protected JList userList;
        protected JList languageList;
        protected DefaultListModel userListModel;
        protected DefaultListModel languageListModel;
        protected JButton addLanguageButton;
        protected JButton removeLanguageButton;
        protected JButton upLanguageButton;
        protected JButton downLanguageButton;
        protected JButton clearLanguageButton;
        protected Map listeners;
        private static Map iconMap;
        
        public Panel() {
            super(new GridBagLayout());
            this.userListModel = new DefaultListModel();
            this.languageListModel = new DefaultListModel();
            this.listeners = new HashMap();
            initCountryIcons();
            this.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), LanguageDialog.resources.getString("Panel.title")));
            this.listeners.put("AddLanguageButtonAction", new AddLanguageButtonAction());
            this.listeners.put("RemoveLanguageButtonAction", new RemoveLanguageButtonAction());
            this.listeners.put("UpLanguageButtonAction", new UpLanguageButtonAction());
            this.listeners.put("DownLanguageButtonAction", new DownLanguageButtonAction());
            this.listeners.put("ClearLanguageButtonAction", new ClearLanguageButtonAction());
            (this.userList = new JList(this.userListModel)).setCellRenderer(new IconAndTextCellRenderer());
            (this.languageList = new JList(this.languageListModel)).setCellRenderer(new IconAndTextCellRenderer());
            final StringTokenizer stringTokenizer = new StringTokenizer(LanguageDialog.resources.getString("Country.list"), " ");
            while (stringTokenizer.hasMoreTokens()) {
                this.languageListModel.addElement(stringTokenizer.nextToken());
            }
            final ExtendedGridBagConstraints constraints = new ExtendedGridBagConstraints();
            constraints.insets = new Insets(5, 5, 5, 5);
            constraints.weightx = 1.0;
            constraints.weighty = 1.0;
            constraints.setGridBounds(0, 0, constraints.fill = 1, 1);
            final JScrollPane comp = new JScrollPane();
            comp.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(), LanguageDialog.resources.getString("Languages.title")), BorderFactory.createLoweredBevelBorder()));
            comp.getViewport().add(this.languageList);
            this.add(comp, constraints);
            this.languageList.setSelectionMode(0);
            this.languageList.addListSelectionListener(new LanguageListSelectionListener());
            constraints.setGridBounds(2, 0, 1, 1);
            final JScrollPane comp2 = new JScrollPane();
            comp2.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(), LanguageDialog.resources.getString("User.title")), BorderFactory.createLoweredBevelBorder()));
            comp2.getViewport().add(this.userList);
            this.add(comp2, constraints);
            this.userList.setSelectionMode(0);
            this.userList.addListSelectionListener(new UserListSelectionListener());
            constraints.setGridBounds(0, 1, 3, 1);
            constraints.weightx = 0.0;
            constraints.weighty = 0.0;
            this.add(new JLabel(LanguageDialog.resources.getString("InfoLabel.text")), constraints);
            final ButtonFactory buttonFactory = new ButtonFactory(LanguageDialog.bundle, this);
            final JPanel comp3 = new JPanel(new GridLayout(5, 1, 0, 3));
            comp3.add(this.addLanguageButton = buttonFactory.createJButton("AddLanguageButton"));
            this.addLanguageButton.setEnabled(false);
            comp3.add(this.removeLanguageButton = buttonFactory.createJButton("RemoveLanguageButton"));
            this.removeLanguageButton.setEnabled(false);
            comp3.add(this.upLanguageButton = buttonFactory.createJButton("UpLanguageButton"));
            this.upLanguageButton.setEnabled(false);
            comp3.add(this.downLanguageButton = buttonFactory.createJButton("DownLanguageButton"));
            this.downLanguageButton.setEnabled(false);
            comp3.add(this.clearLanguageButton = buttonFactory.createJButton("ClearLanguageButton"));
            this.clearLanguageButton.setEnabled(false);
            final JPanel comp4 = new JPanel(new GridBagLayout());
            constraints.setGridBounds(1, 0, 1, 1);
            this.add(comp4, constraints);
            constraints.fill = 2;
            constraints.setGridBounds(0, 0, 1, 1);
            constraints.insets = new Insets(0, 0, 0, 0);
            comp4.add(comp3, constraints);
            comp2.setPreferredSize(comp.getPreferredSize());
        }
        
        public static synchronized void initCountryIcons() {
            if (Panel.iconMap == null) {
                Panel.iconMap = new HashMap();
                final StringTokenizer stringTokenizer = new StringTokenizer(LanguageDialog.resources.getString("Country.list"), " ");
                while (stringTokenizer.hasMoreTokens()) {
                    computeCountryIcon(Panel.class, stringTokenizer.nextToken());
                }
            }
        }
        
        public String getLanguages() {
            final StringBuffer sb = new StringBuffer();
            if (this.userListModel.getSize() > 0) {
                sb.append(this.userListModel.getElementAt(0));
                for (int i = 1; i < this.userListModel.getSize(); ++i) {
                    sb.append(',');
                    sb.append(this.userListModel.getElementAt(i));
                }
            }
            return sb.toString();
        }
        
        public void setLanguages(final String str) {
            for (int size = this.userListModel.getSize(), i = 0; i < size; ++i) {
                final String element = this.userListModel.getElementAt(0);
                this.userListModel.removeElementAt(0);
                String s;
                int size2;
                int n;
                for (s = element, size2 = this.languageListModel.getSize(), n = 0; n < size2 && s.compareTo((String)this.languageListModel.getElementAt(n)) <= 0; ++n) {}
                this.languageListModel.insertElementAt(element, n);
            }
            final StringTokenizer stringTokenizer = new StringTokenizer(str, ",");
            while (stringTokenizer.hasMoreTokens()) {
                final String nextToken = stringTokenizer.nextToken();
                this.userListModel.addElement(nextToken);
                this.languageListModel.removeElement(nextToken);
            }
            this.updateButtons();
        }
        
        protected void updateButtons() {
            final int size = this.userListModel.size();
            final int selectedIndex = this.userList.getSelectedIndex();
            final boolean b = size == 0;
            final boolean b2 = selectedIndex != -1;
            final boolean b3 = selectedIndex == 0;
            final boolean b4 = selectedIndex == size - 1;
            this.removeLanguageButton.setEnabled(!b && b2);
            this.upLanguageButton.setEnabled(!b && b2 && !b3);
            this.downLanguageButton.setEnabled(!b && b2 && !b4);
            this.clearLanguageButton.setEnabled(!b);
            final int size2 = this.languageListModel.size();
            final int selectedIndex2 = this.languageList.getSelectedIndex();
            final boolean b5 = size2 == 0;
            final boolean b6 = selectedIndex2 != -1;
            this.addLanguageButton.setEnabled(!b5 && b6);
        }
        
        protected String getCountryText(final String str) {
            return LanguageDialog.resources.getString(str + ".text");
        }
        
        protected Icon getCountryIcon(final String s) {
            return computeCountryIcon(this.getClass(), s);
        }
        
        private static Icon computeCountryIcon(final Class clazz, final String str) {
            try {
                final ImageIcon imageIcon;
                if ((imageIcon = Panel.iconMap.get(str)) != null) {
                    return imageIcon;
                }
                final URL resource = clazz.getResource(LanguageDialog.resources.getString(str + ".icon"));
                if (resource != null) {
                    final ImageIcon imageIcon2;
                    Panel.iconMap.put(str, imageIcon2 = new ImageIcon(resource));
                    return imageIcon2;
                }
            }
            catch (MissingResourceException ex) {}
            return new ImageIcon(clazz.getResource("resources/blank.gif"));
        }
        
        public Action getAction(final String s) throws MissingListenerException {
            return this.listeners.get(s);
        }
        
        static {
            Panel.iconMap = null;
        }
        
        protected class IconAndTextCellRenderer extends JLabel implements ListCellRenderer
        {
            public IconAndTextCellRenderer() {
                this.setOpaque(true);
                this.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));
            }
            
            public Component getListCellRendererComponent(final JList list, final Object o, final int n, final boolean b, final boolean b2) {
                final String s = (String)o;
                this.setText(Panel.this.getCountryText(s));
                this.setIcon(Panel.this.getCountryIcon(s));
                this.setEnabled(list.isEnabled());
                this.setFont(list.getFont());
                if (b) {
                    this.setBackground(list.getSelectionBackground());
                    this.setForeground(list.getSelectionForeground());
                }
                else {
                    this.setBackground(list.getBackground());
                    this.setForeground(list.getForeground());
                }
                return this;
            }
        }
        
        protected class UserListSelectionListener implements ListSelectionListener
        {
            public void valueChanged(final ListSelectionEvent listSelectionEvent) {
                final int selectedIndex = Panel.this.userList.getSelectedIndex();
                Panel.this.languageList.getSelectionModel().clearSelection();
                Panel.this.userList.setSelectedIndex(selectedIndex);
                Panel.this.updateButtons();
            }
        }
        
        protected class LanguageListSelectionListener implements ListSelectionListener
        {
            public void valueChanged(final ListSelectionEvent listSelectionEvent) {
                final int selectedIndex = Panel.this.languageList.getSelectedIndex();
                Panel.this.userList.getSelectionModel().clearSelection();
                Panel.this.languageList.setSelectedIndex(selectedIndex);
                Panel.this.updateButtons();
            }
        }
        
        protected class ClearLanguageButtonAction extends AbstractAction
        {
            public void actionPerformed(final ActionEvent actionEvent) {
                for (int size = Panel.this.userListModel.getSize(), i = 0; i < size; ++i) {
                    final String element = Panel.this.userListModel.getElementAt(0);
                    Panel.this.userListModel.removeElementAt(0);
                    String s;
                    int size2;
                    int n;
                    for (s = element, size2 = Panel.this.languageListModel.getSize(), n = 0; n < size2 && s.compareTo((String)Panel.this.languageListModel.getElementAt(n)) <= 0; ++n) {}
                    Panel.this.languageListModel.insertElementAt(element, n);
                }
                Panel.this.updateButtons();
            }
        }
        
        protected class DownLanguageButtonAction extends AbstractAction
        {
            public void actionPerformed(final ActionEvent actionEvent) {
                final int selectedIndex = Panel.this.userList.getSelectedIndex();
                final Object element = Panel.this.userListModel.getElementAt(selectedIndex);
                Panel.this.userListModel.removeElementAt(selectedIndex);
                Panel.this.userListModel.insertElementAt(element, selectedIndex + 1);
                Panel.this.userList.setSelectedIndex(selectedIndex + 1);
            }
        }
        
        protected class UpLanguageButtonAction extends AbstractAction
        {
            public void actionPerformed(final ActionEvent actionEvent) {
                final int selectedIndex = Panel.this.userList.getSelectedIndex();
                final Object element = Panel.this.userListModel.getElementAt(selectedIndex);
                Panel.this.userListModel.removeElementAt(selectedIndex);
                Panel.this.userListModel.insertElementAt(element, selectedIndex - 1);
                Panel.this.userList.setSelectedIndex(selectedIndex - 1);
            }
        }
        
        protected class RemoveLanguageButtonAction extends AbstractAction
        {
            public void actionPerformed(final ActionEvent actionEvent) {
                final int selectedIndex = Panel.this.userList.getSelectedIndex();
                final Object element = Panel.this.userListModel.getElementAt(selectedIndex);
                Panel.this.userListModel.removeElementAt(selectedIndex);
                String s;
                int size;
                int n;
                for (s = (String)element, size = Panel.this.languageListModel.getSize(), n = 0; n < size && s.compareTo((String)Panel.this.languageListModel.getElementAt(n)) <= 0; ++n) {}
                Panel.this.languageListModel.insertElementAt(element, n);
                Panel.this.languageList.setSelectedValue(element, true);
                Panel.this.updateButtons();
            }
        }
        
        protected class AddLanguageButtonAction extends AbstractAction
        {
            public void actionPerformed(final ActionEvent actionEvent) {
                final int selectedIndex = Panel.this.languageList.getSelectedIndex();
                final Object element = Panel.this.languageListModel.getElementAt(selectedIndex);
                Panel.this.languageListModel.removeElementAt(selectedIndex);
                Panel.this.userListModel.addElement(element);
                Panel.this.userList.setSelectedValue(element, true);
            }
        }
    }
}
