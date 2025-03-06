// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.apps.svgbrowser;

import java.awt.event.FocusEvent;
import java.awt.event.FocusAdapter;
import javax.swing.event.DocumentEvent;
import javax.swing.event.TableModelEvent;
import javax.swing.table.DefaultTableModel;
import java.util.EventListener;
import java.util.EventObject;
import java.awt.BorderLayout;
import org.apache.batik.util.gui.xmleditor.XMLTextEditor;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import java.awt.Container;
import java.awt.Frame;
import javax.swing.JTextField;
import javax.swing.JDialog;
import java.util.Locale;
import org.apache.batik.util.gui.resource.MissingListenerException;
import javax.swing.Action;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import java.io.IOException;
import javax.xml.parsers.ParserConfigurationException;
import java.io.Reader;
import org.xml.sax.InputSource;
import java.io.StringReader;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.ErrorHandler;
import javax.xml.parsers.DocumentBuilderFactory;
import java.util.Vector;
import org.apache.batik.dom.AbstractNode;
import org.w3c.dom.Attr;
import org.w3c.dom.NamedNodeMap;
import org.apache.batik.dom.util.DOMUtilities;
import javax.swing.event.DocumentListener;
import javax.swing.table.TableModel;
import java.awt.event.FocusListener;
import java.awt.Component;
import java.awt.Insets;
import java.awt.GridBagConstraints;
import java.util.HashMap;
import java.awt.LayoutManager;
import java.awt.GridBagLayout;
import java.util.Map;
import javax.swing.event.EventListenerList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import javax.swing.JLabel;
import javax.swing.JButton;
import org.apache.batik.util.gui.resource.ButtonFactory;
import javax.swing.JScrollPane;
import javax.swing.event.TableModelListener;
import javax.swing.JTable;
import org.apache.batik.util.resources.ResourceManager;
import java.util.ResourceBundle;
import org.apache.batik.util.gui.resource.ActionMap;
import javax.swing.JPanel;

public class NodePickerPanel extends JPanel implements ActionMap
{
    private static final int VIEW_MODE = 1;
    private static final int EDIT_MODE = 2;
    private static final int ADD_NEW_ELEMENT = 3;
    private static final String RESOURCES = "org.apache.batik.apps.svgbrowser.resources.NodePickerPanelMessages";
    private static ResourceBundle bundle;
    private static ResourceManager resources;
    private JTable attributesTable;
    private TableModelListener tableModelListener;
    private JScrollPane attributePane;
    private JPanel attributesPanel;
    private ButtonFactory buttonFactory;
    private JButton addButton;
    private JButton removeButton;
    private JLabel attributesLabel;
    private JButton applyButton;
    private JButton resetButton;
    private JPanel choosePanel;
    private SVGInputPanel svgInputPanel;
    private JLabel isWellFormedLabel;
    private JLabel svgInputPanelNameLabel;
    private boolean shouldProcessUpdate;
    private Element previewElement;
    private Element clonedElement;
    private Node parentElement;
    private int mode;
    private boolean isDirty;
    private EventListenerList eventListeners;
    private NodePickerController controller;
    private Map listeners;
    
    public NodePickerPanel(final NodePickerController controller) {
        super(new GridBagLayout());
        this.shouldProcessUpdate = true;
        this.eventListeners = new EventListenerList();
        this.listeners = new HashMap(10);
        this.controller = controller;
        this.initialize();
    }
    
    private void initialize() {
        this.addButtonActions();
        final GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = 18;
        gridBagConstraints.fill = 0;
        gridBagConstraints.insets = new Insets(5, 5, 0, 5);
        (this.attributesLabel = new JLabel()).setText(NodePickerPanel.resources.getString("AttributesTable.name"));
        this.add(this.attributesLabel, gridBagConstraints);
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 0.3;
        gridBagConstraints.fill = 1;
        gridBagConstraints.anchor = 10;
        gridBagConstraints.insets = new Insets(0, 0, 0, 5);
        this.add(this.getAttributesPanel(), gridBagConstraints);
        gridBagConstraints.weightx = 0.0;
        gridBagConstraints.weighty = 0.0;
        gridBagConstraints.gridwidth = 1;
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = 18;
        gridBagConstraints.fill = 0;
        gridBagConstraints.insets = new Insets(0, 5, 0, 5);
        (this.svgInputPanelNameLabel = new JLabel()).setText(NodePickerPanel.resources.getString("InputPanelLabel.name"));
        this.add(this.svgInputPanelNameLabel, gridBagConstraints);
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.fill = 1;
        gridBagConstraints.anchor = 10;
        gridBagConstraints.insets = new Insets(0, 5, 0, 10);
        this.add(this.getSvgInputPanel(), gridBagConstraints);
        gridBagConstraints.weightx = 0.0;
        gridBagConstraints.weighty = 0.0;
        gridBagConstraints.gridwidth = 1;
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.anchor = 18;
        gridBagConstraints.fill = 0;
        gridBagConstraints.insets = new Insets(5, 5, 0, 5);
        (this.isWellFormedLabel = new JLabel()).setText(NodePickerPanel.resources.getString("IsWellFormedLabel.wellFormed"));
        this.add(this.isWellFormedLabel, gridBagConstraints);
        gridBagConstraints.weightx = 0.0;
        gridBagConstraints.weighty = 0.0;
        gridBagConstraints.gridwidth = 1;
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.anchor = 13;
        gridBagConstraints.insets = new Insets(0, 0, 0, 5);
        this.add(this.getChoosePanel(), gridBagConstraints);
        this.enterViewMode();
    }
    
    private ButtonFactory getButtonFactory() {
        if (this.buttonFactory == null) {
            this.buttonFactory = new ButtonFactory(NodePickerPanel.bundle, this);
        }
        return this.buttonFactory;
    }
    
    private void addButtonActions() {
        this.listeners.put("ApplyButtonAction", new ApplyButtonAction());
        this.listeners.put("ResetButtonAction", new ResetButtonAction());
        this.listeners.put("AddButtonAction", new AddButtonAction());
        this.listeners.put("RemoveButtonAction", new RemoveButtonAction());
    }
    
    private JButton getAddButton() {
        if (this.addButton == null) {
            (this.addButton = this.getButtonFactory().createJButton("AddButton")).addFocusListener(new NodePickerEditListener());
        }
        return this.addButton;
    }
    
    private JButton getRemoveButton() {
        if (this.removeButton == null) {
            (this.removeButton = this.getButtonFactory().createJButton("RemoveButton")).addFocusListener(new NodePickerEditListener());
        }
        return this.removeButton;
    }
    
    private JButton getApplyButton() {
        if (this.applyButton == null) {
            this.applyButton = this.getButtonFactory().createJButton("ApplyButton");
        }
        return this.applyButton;
    }
    
    private JButton getResetButton() {
        if (this.resetButton == null) {
            this.resetButton = this.getButtonFactory().createJButton("ResetButton");
        }
        return this.resetButton;
    }
    
    private JPanel getAttributesPanel() {
        if (this.attributesPanel == null) {
            this.attributesPanel = new JPanel(new GridBagLayout());
            final GridBagConstraints constraints = new GridBagConstraints();
            constraints.gridx = 1;
            constraints.gridy = 1;
            constraints.fill = 1;
            constraints.anchor = 10;
            constraints.weightx = 4.0;
            constraints.weighty = 1.0;
            constraints.gridheight = 5;
            constraints.gridwidth = 2;
            constraints.insets = new Insets(5, 5, 5, 0);
            final GridBagConstraints constraints2 = new GridBagConstraints();
            constraints2.gridx = 3;
            constraints2.gridy = 1;
            constraints2.fill = 2;
            constraints2.anchor = 11;
            constraints2.insets = new Insets(5, 20, 0, 5);
            constraints2.weightx = 1.0;
            final GridBagConstraints constraints3 = new GridBagConstraints();
            constraints3.gridx = 3;
            constraints3.gridy = 3;
            constraints3.fill = 2;
            constraints3.anchor = 11;
            constraints3.insets = new Insets(5, 20, 0, 5);
            constraints3.weightx = 1.0;
            (this.attributesTable = new JTable()).setModel(new AttributesTableModel(10, 2));
            this.tableModelListener = new AttributesTableModelListener();
            this.attributesTable.getModel().addTableModelListener(this.tableModelListener);
            this.attributesTable.addFocusListener(new NodePickerEditListener());
            this.attributePane = new JScrollPane();
            this.attributePane.getViewport().add(this.attributesTable);
            this.attributesPanel.add(this.attributePane, constraints);
            this.attributesPanel.add(this.getAddButton(), constraints2);
            this.attributesPanel.add(this.getRemoveButton(), constraints3);
        }
        return this.attributesPanel;
    }
    
    private SVGInputPanel getSvgInputPanel() {
        if (this.svgInputPanel == null) {
            this.svgInputPanel = new SVGInputPanel();
            this.svgInputPanel.getNodeXmlArea().getDocument().addDocumentListener(new XMLAreaListener());
            this.svgInputPanel.getNodeXmlArea().addFocusListener(new NodePickerEditListener());
        }
        return this.svgInputPanel;
    }
    
    private JPanel getChoosePanel() {
        if (this.choosePanel == null) {
            this.choosePanel = new JPanel(new GridBagLayout());
            final GridBagConstraints constraints = new GridBagConstraints();
            constraints.gridx = 1;
            constraints.gridy = 1;
            constraints.weightx = 0.5;
            constraints.anchor = 17;
            constraints.fill = 2;
            constraints.insets = new Insets(5, 5, 5, 5);
            final GridBagConstraints constraints2 = new GridBagConstraints();
            constraints2.gridx = 2;
            constraints2.gridy = 1;
            constraints2.weightx = 0.5;
            constraints2.anchor = 13;
            constraints2.fill = 2;
            constraints2.insets = new Insets(5, 5, 5, 5);
            this.choosePanel.add(this.getApplyButton(), constraints);
            this.choosePanel.add(this.getResetButton(), constraints2);
        }
        return this.choosePanel;
    }
    
    public String getResults() {
        return this.getSvgInputPanel().getNodeXmlArea().getText();
    }
    
    private void updateViewAfterSvgInput(final Element element, final Element element2) {
        if (element != null) {
            this.isWellFormedLabel.setText(NodePickerPanel.resources.getString("IsWellFormedLabel.wellFormed"));
            this.getApplyButton().setEnabled(true);
            this.attributesTable.setEnabled(true);
            this.updateElementAttributes(element2, element);
            this.shouldProcessUpdate = false;
            this.updateAttributesTable(element2);
            this.shouldProcessUpdate = true;
        }
        else {
            this.isWellFormedLabel.setText(NodePickerPanel.resources.getString("IsWellFormedLabel.notWellFormed"));
            this.getApplyButton().setEnabled(false);
            this.attributesTable.setEnabled(false);
        }
    }
    
    private void updateElementAttributes(final Element element, final Element element2) {
        this.removeAttributes(element);
        final NamedNodeMap attributes = element2.getAttributes();
        for (int i = attributes.getLength() - 1; i >= 0; --i) {
            final Node item = attributes.item(i);
            final String nodeName = item.getNodeName();
            element.setAttributeNS(this.getNamespaceURI(DOMUtilities.getPrefix(nodeName)), nodeName, item.getNodeValue());
        }
    }
    
    private void updateElementAttributes(final Element element, final AttributesTableModel attributesTableModel) {
        this.removeAttributes(element);
        for (int i = 0; i < attributesTableModel.getRowCount(); ++i) {
            final String s = (String)attributesTableModel.getAttrNameAt(i);
            final String s2 = (String)attributesTableModel.getAttrValueAt(i);
            if (s != null && s.length() > 0) {
                String namespaceURI;
                if (s.equals("xmlns")) {
                    namespaceURI = "http://www.w3.org/2000/xmlns/";
                }
                else {
                    namespaceURI = this.getNamespaceURI(DOMUtilities.getPrefix(s));
                }
                if (s2 != null) {
                    element.setAttributeNS(namespaceURI, s, s2);
                }
                else {
                    element.setAttributeNS(namespaceURI, s, "");
                }
            }
        }
    }
    
    private void removeAttributes(final Element element) {
        final NamedNodeMap attributes = element.getAttributes();
        for (int i = attributes.getLength() - 1; i >= 0; --i) {
            element.removeAttributeNode((Attr)attributes.item(i));
        }
    }
    
    private String getNamespaceURI(final String s) {
        String s2 = null;
        if (s != null) {
            if (s.equals("xmlns")) {
                s2 = "http://www.w3.org/2000/xmlns/";
            }
            else if (this.mode == 2) {
                s2 = ((AbstractNode)this.previewElement).lookupNamespaceURI(s);
            }
            else if (this.mode == 3) {
                s2 = ((AbstractNode)this.parentElement).lookupNamespaceURI(s);
            }
        }
        return s2;
    }
    
    private void updateAttributesTable(final Element element) {
        final NamedNodeMap attributes = element.getAttributes();
        final AttributesTableModel attributesTableModel = (AttributesTableModel)this.attributesTable.getModel();
        for (int i = attributesTableModel.getRowCount() - 1; i >= 0; --i) {
            final String s = (String)attributesTableModel.getValueAt(i, 0);
            String attributeNS = "";
            if (s != null) {
                attributeNS = element.getAttributeNS(null, s);
            }
            if (s == null || attributeNS.length() == 0) {
                attributesTableModel.removeRow(i);
            }
            if (attributeNS.length() > 0) {
                attributesTableModel.setValueAt(attributeNS, i, 1);
            }
        }
        for (int j = 0; j < attributes.getLength(); ++j) {
            final Node item = attributes.item(j);
            final String nodeName = item.getNodeName();
            final String nodeValue = item.getNodeValue();
            if (attributesTableModel.getValueForName(nodeName) == null) {
                final Vector<String> rowData = new Vector<String>();
                rowData.add(nodeName);
                rowData.add(nodeValue);
                attributesTableModel.addRow(rowData);
            }
        }
    }
    
    private void updateNodeXmlArea(final Node node) {
        this.getSvgInputPanel().getNodeXmlArea().setText(DOMUtilities.getXML(node));
    }
    
    private Element getPreviewElement() {
        return this.previewElement;
    }
    
    public void setPreviewElement(final Element previewElement) {
        if (this.previewElement != previewElement && this.isDirty && !this.promptForChanges()) {
            return;
        }
        this.previewElement = previewElement;
        this.enterViewMode();
        this.updateNodeXmlArea(previewElement);
        this.updateAttributesTable(previewElement);
    }
    
    boolean panelHiding() {
        return !this.isDirty || this.promptForChanges();
    }
    
    private int getMode() {
        return this.mode;
    }
    
    public void enterViewMode() {
        if (this.mode != 1) {
            this.mode = 1;
            this.getApplyButton().setEnabled(false);
            this.getResetButton().setEnabled(false);
            this.getRemoveButton().setEnabled(true);
            this.getAddButton().setEnabled(true);
            this.isWellFormedLabel.setText(NodePickerPanel.resources.getString("IsWellFormedLabel.wellFormed"));
        }
    }
    
    public void enterEditMode() {
        if (this.mode != 2) {
            this.mode = 2;
            this.clonedElement = (Element)this.previewElement.cloneNode(true);
            this.getApplyButton().setEnabled(true);
            this.getResetButton().setEnabled(true);
        }
    }
    
    public void enterAddNewElementMode(final Element previewElement, final Node parentElement) {
        if (this.mode != 3) {
            this.mode = 3;
            this.previewElement = previewElement;
            this.clonedElement = (Element)previewElement.cloneNode(true);
            this.parentElement = parentElement;
            this.updateNodeXmlArea(previewElement);
            this.getApplyButton().setEnabled(true);
            this.getResetButton().setEnabled(true);
        }
    }
    
    public void updateOnDocumentChange(final String s, final Node node) {
        if (this.mode == 1 && this.isShowing() && this.shouldUpdate(s, node, this.getPreviewElement())) {
            this.setPreviewElement(this.getPreviewElement());
        }
    }
    
    private boolean shouldUpdate(final String s, final Node node, final Node node2) {
        if (s.equals("DOMNodeInserted")) {
            if (DOMUtilities.isAncestorOf(node2, node)) {
                return true;
            }
        }
        else if (s.equals("DOMNodeRemoved")) {
            if (DOMUtilities.isAncestorOf(node2, node)) {
                return true;
            }
        }
        else if (s.equals("DOMAttrModified")) {
            if (DOMUtilities.isAncestorOf(node2, node) || node2 == node) {
                return true;
            }
        }
        else if (s.equals("DOMCharDataModified") && DOMUtilities.isAncestorOf(node2, node)) {
            return true;
        }
        return false;
    }
    
    private Element parseXml(final String s) {
        Document parse = null;
        final DocumentBuilderFactory instance = DocumentBuilderFactory.newInstance();
        try {
            final DocumentBuilder documentBuilder = instance.newDocumentBuilder();
            documentBuilder.setErrorHandler(new ErrorHandler() {
                public void error(final SAXParseException ex) throws SAXException {
                }
                
                public void fatalError(final SAXParseException ex) throws SAXException {
                }
                
                public void warning(final SAXParseException ex) throws SAXException {
                }
            });
            parse = documentBuilder.parse(new InputSource(new StringReader(s)));
        }
        catch (ParserConfigurationException ex) {}
        catch (SAXException ex2) {}
        catch (IOException ex3) {}
        if (parse != null) {
            return parse.getDocumentElement();
        }
        return null;
    }
    
    public void setEditable(final boolean b) {
        this.getSvgInputPanel().getNodeXmlArea().setEditable(b);
        this.getResetButton().setEnabled(b);
        this.getApplyButton().setEnabled(b);
        this.getAddButton().setEnabled(b);
        this.getRemoveButton().setEnabled(b);
        this.attributesTable.setEnabled(b);
    }
    
    private boolean isANodePickerComponent(final Component comp) {
        return SwingUtilities.getAncestorOfClass(NodePickerPanel.class, comp) != null;
    }
    
    public boolean promptForChanges() {
        if (this.getApplyButton().isEnabled() && this.isElementModified()) {
            final int showConfirmDialog = JOptionPane.showConfirmDialog(this.getSvgInputPanel(), NodePickerPanel.resources.getString("ConfirmDialog.message"));
            if (showConfirmDialog == 0) {
                this.getApplyButton().doClick();
            }
            else {
                if (showConfirmDialog == 2) {
                    return false;
                }
                this.getResetButton().doClick();
            }
        }
        else {
            this.getResetButton().doClick();
        }
        this.isDirty = false;
        return true;
    }
    
    private boolean isElementModified() {
        if (this.getMode() == 2) {
            return !DOMUtilities.getXML(this.previewElement).equals(this.getSvgInputPanel().getNodeXmlArea().getText());
        }
        return this.getMode() == 3;
    }
    
    public Action getAction(final String s) throws MissingListenerException {
        return this.listeners.get(s);
    }
    
    public void fireUpdateElement(final NodePickerEvent nodePickerEvent) {
        final Object[] listenerList = this.eventListeners.getListenerList();
        for (int length = listenerList.length, i = 0; i < length; i += 2) {
            if (listenerList[i] == NodePickerListener.class) {
                ((NodePickerListener)listenerList[i + 1]).updateElement(nodePickerEvent);
            }
        }
    }
    
    public void fireAddNewElement(final NodePickerEvent nodePickerEvent) {
        final Object[] listenerList = this.eventListeners.getListenerList();
        for (int length = listenerList.length, i = 0; i < length; i += 2) {
            if (listenerList[i] == NodePickerListener.class) {
                ((NodePickerListener)listenerList[i + 1]).addNewElement(nodePickerEvent);
            }
        }
    }
    
    public void addListener(final NodePickerListener l) {
        this.eventListeners.add(NodePickerListener.class, l);
    }
    
    static {
        NodePickerPanel.bundle = ResourceBundle.getBundle("org.apache.batik.apps.svgbrowser.resources.NodePickerPanelMessages", Locale.getDefault());
        NodePickerPanel.resources = new ResourceManager(NodePickerPanel.bundle);
    }
    
    public static class NameEditorDialog extends JDialog implements ActionMap
    {
        public static final int OK_OPTION = 0;
        public static final int CANCEL_OPTION = 1;
        protected static final String RESOURCES = "org.apache.batik.apps.svgbrowser.resources.NameEditorDialogMessages";
        protected static ResourceBundle bundle;
        protected static ResourceManager resources;
        protected int returnCode;
        protected JPanel mainPanel;
        protected ButtonFactory buttonFactory;
        protected JLabel nodeNameLabel;
        protected JTextField nodeNameField;
        protected JButton okButton;
        protected JButton cancelButton;
        protected Map listeners;
        
        public NameEditorDialog(final Frame owner) {
            super(owner, true);
            this.listeners = new HashMap(10);
            this.setResizable(false);
            this.setModal(true);
            this.initialize();
        }
        
        protected void initialize() {
            this.setSize(NameEditorDialog.resources.getInteger("Dialog.width"), NameEditorDialog.resources.getInteger("Dialog.height"));
            this.setTitle(NameEditorDialog.resources.getString("Dialog.title"));
            this.addButtonActions();
            this.setContentPane(this.getMainPanel());
        }
        
        protected ButtonFactory getButtonFactory() {
            if (this.buttonFactory == null) {
                this.buttonFactory = new ButtonFactory(NameEditorDialog.bundle, this);
            }
            return this.buttonFactory;
        }
        
        protected void addButtonActions() {
            this.listeners.put("OKButtonAction", new OKButtonAction());
            this.listeners.put("CancelButtonAction", new CancelButtonAction());
        }
        
        public int showDialog() {
            this.setVisible(true);
            return this.returnCode;
        }
        
        protected JButton getOkButton() {
            if (this.okButton == null) {
                this.okButton = this.getButtonFactory().createJButton("OKButton");
                this.getRootPane().setDefaultButton(this.okButton);
            }
            return this.okButton;
        }
        
        protected JButton getCancelButton() {
            if (this.cancelButton == null) {
                this.cancelButton = this.getButtonFactory().createJButton("CancelButton");
            }
            return this.cancelButton;
        }
        
        protected JPanel getMainPanel() {
            if (this.mainPanel == null) {
                this.mainPanel = new JPanel(new GridBagLayout());
                final GridBagConstraints gridBagConstraints = new GridBagConstraints();
                gridBagConstraints.gridx = 1;
                gridBagConstraints.gridy = 1;
                gridBagConstraints.fill = 0;
                gridBagConstraints.insets = new Insets(5, 5, 5, 5);
                this.mainPanel.add(this.getNodeNameLabel(), gridBagConstraints);
                gridBagConstraints.gridx = 2;
                gridBagConstraints.weightx = 1.0;
                gridBagConstraints.weighty = 1.0;
                gridBagConstraints.fill = 2;
                gridBagConstraints.anchor = 10;
                this.mainPanel.add(this.getNodeNameField(), gridBagConstraints);
                gridBagConstraints.gridx = 1;
                gridBagConstraints.gridy = 2;
                gridBagConstraints.weightx = 0.0;
                gridBagConstraints.weighty = 0.0;
                gridBagConstraints.anchor = 13;
                gridBagConstraints.fill = 2;
                this.mainPanel.add(this.getOkButton(), gridBagConstraints);
                gridBagConstraints.gridx = 2;
                gridBagConstraints.gridy = 2;
                gridBagConstraints.anchor = 13;
                this.mainPanel.add(this.getCancelButton(), gridBagConstraints);
            }
            return this.mainPanel;
        }
        
        public JLabel getNodeNameLabel() {
            if (this.nodeNameLabel == null) {
                (this.nodeNameLabel = new JLabel()).setText(NameEditorDialog.resources.getString("Dialog.label"));
            }
            return this.nodeNameLabel;
        }
        
        protected JTextField getNodeNameField() {
            if (this.nodeNameField == null) {
                this.nodeNameField = new JTextField();
            }
            return this.nodeNameField;
        }
        
        public String getResults() {
            return this.nodeNameField.getText();
        }
        
        public Action getAction(final String s) throws MissingListenerException {
            return this.listeners.get(s);
        }
        
        static {
            NameEditorDialog.bundle = ResourceBundle.getBundle("org.apache.batik.apps.svgbrowser.resources.NameEditorDialogMessages", Locale.getDefault());
            NameEditorDialog.resources = new ResourceManager(NameEditorDialog.bundle);
        }
        
        protected class CancelButtonAction extends AbstractAction
        {
            public void actionPerformed(final ActionEvent actionEvent) {
                NameEditorDialog.this.returnCode = 1;
                NameEditorDialog.this.dispose();
            }
        }
        
        protected class OKButtonAction extends AbstractAction
        {
            public void actionPerformed(final ActionEvent actionEvent) {
                NameEditorDialog.this.returnCode = 0;
                NameEditorDialog.this.dispose();
            }
        }
    }
    
    protected class SVGInputPanel extends JPanel
    {
        protected XMLTextEditor nodeXmlArea;
        
        public SVGInputPanel() {
            super(new BorderLayout());
            this.add(new JScrollPane(this.getNodeXmlArea()));
        }
        
        protected XMLTextEditor getNodeXmlArea() {
            if (this.nodeXmlArea == null) {
                (this.nodeXmlArea = new XMLTextEditor()).setEditable(true);
            }
            return this.nodeXmlArea;
        }
    }
    
    public static class NodePickerAdapter implements NodePickerListener
    {
        public void addNewElement(final NodePickerEvent nodePickerEvent) {
        }
        
        public void updateElement(final NodePickerEvent nodePickerEvent) {
        }
    }
    
    public static class NodePickerEvent extends EventObject
    {
        public static final int EDIT_ELEMENT = 1;
        public static final int ADD_NEW_ELEMENT = 2;
        private int type;
        private String result;
        private Node contextNode;
        
        public NodePickerEvent(final Object source, final String result, final Node contextNode, final int n) {
            super(source);
            this.result = result;
            this.contextNode = contextNode;
        }
        
        public String getResult() {
            return this.result;
        }
        
        public Node getContextNode() {
            return this.contextNode;
        }
        
        public int getType() {
            return this.type;
        }
    }
    
    public interface NodePickerListener extends EventListener
    {
        void updateElement(final NodePickerEvent p0);
        
        void addNewElement(final NodePickerEvent p0);
    }
    
    public static class AttributesTableModel extends DefaultTableModel
    {
        public AttributesTableModel(final int rowCount, final int columnCount) {
            super(rowCount, columnCount);
        }
        
        public String getColumnName(final int n) {
            if (n == 0) {
                return NodePickerPanel.resources.getString("AttributesTable.column1");
            }
            return NodePickerPanel.resources.getString("AttributesTable.column2");
        }
        
        public Object getValueForName(final Object obj) {
            for (int i = 0; i < this.getRowCount(); ++i) {
                if (this.getValueAt(i, 0) != null && this.getValueAt(i, 0).equals(obj)) {
                    return this.getValueAt(i, 1);
                }
            }
            return null;
        }
        
        public Object getAttrNameAt(final int row) {
            return this.getValueAt(row, 0);
        }
        
        public Object getAttrValueAt(final int row) {
            return this.getValueAt(row, 1);
        }
        
        public int getRow(final Object obj) {
            for (int i = 0; i < this.getRowCount(); ++i) {
                if (this.getValueAt(i, 0) != null && this.getValueAt(i, 0).equals(obj)) {
                    return i;
                }
            }
            return -1;
        }
    }
    
    protected class RemoveButtonAction extends AbstractAction
    {
        public void actionPerformed(final ActionEvent actionEvent) {
            if (NodePickerPanel.this.getMode() == 1) {
                NodePickerPanel.this.enterEditMode();
            }
            Element element = NodePickerPanel.this.clonedElement;
            if (NodePickerPanel.this.getMode() == 3) {
                element = NodePickerPanel.this.previewElement;
            }
            final DefaultTableModel defaultTableModel = (DefaultTableModel)NodePickerPanel.this.attributesTable.getModel();
            final int[] selectedRows = NodePickerPanel.this.attributesTable.getSelectedRows();
            for (int i = 0; i < selectedRows.length; ++i) {
                final String s = (String)defaultTableModel.getValueAt(selectedRows[i], 0);
                if (s != null) {
                    element.removeAttributeNS(NodePickerPanel.this.getNamespaceURI(DOMUtilities.getPrefix(s)), DOMUtilities.getLocalName(s));
                }
            }
            NodePickerPanel.this.shouldProcessUpdate = false;
            NodePickerPanel.this.updateAttributesTable(element);
            NodePickerPanel.this.shouldProcessUpdate = true;
            NodePickerPanel.this.updateNodeXmlArea(element);
        }
    }
    
    protected class AddButtonAction extends AbstractAction
    {
        public void actionPerformed(final ActionEvent actionEvent) {
            if (NodePickerPanel.this.getMode() == 1) {
                NodePickerPanel.this.enterEditMode();
            }
            final DefaultTableModel defaultTableModel = (DefaultTableModel)NodePickerPanel.this.attributesTable.getModel();
            NodePickerPanel.this.shouldProcessUpdate = false;
            defaultTableModel.addRow((Vector<?>)null);
            NodePickerPanel.this.shouldProcessUpdate = true;
        }
    }
    
    protected class ResetButtonAction extends AbstractAction
    {
        public void actionPerformed(final ActionEvent actionEvent) {
            NodePickerPanel.this.isDirty = false;
            NodePickerPanel.this.setPreviewElement(NodePickerPanel.this.getPreviewElement());
        }
    }
    
    protected class ApplyButtonAction extends AbstractAction
    {
        public void actionPerformed(final ActionEvent actionEvent) {
            NodePickerPanel.this.isDirty = false;
            final String results = NodePickerPanel.this.getResults();
            if (NodePickerPanel.this.getMode() == 2) {
                NodePickerPanel.this.fireUpdateElement(new NodePickerEvent(NodePickerPanel.this, results, NodePickerPanel.this.previewElement, 1));
            }
            else if (NodePickerPanel.this.getMode() == 3) {
                NodePickerPanel.this.fireAddNewElement(new NodePickerEvent(NodePickerPanel.this, results, NodePickerPanel.this.parentElement, 2));
            }
            NodePickerPanel.this.enterViewMode();
        }
    }
    
    protected class AttributesTableModelListener implements TableModelListener
    {
        public void tableChanged(final TableModelEvent tableModelEvent) {
            if (tableModelEvent.getType() == 0 && NodePickerPanel.this.shouldProcessUpdate) {
                this.updateNodePicker(tableModelEvent);
            }
        }
        
        private void updateNodePicker(final TableModelEvent tableModelEvent) {
            if (NodePickerPanel.this.getMode() == 2) {
                NodePickerPanel.this.updateElementAttributes(NodePickerPanel.this.clonedElement, (AttributesTableModel)tableModelEvent.getSource());
                NodePickerPanel.this.updateNodeXmlArea(NodePickerPanel.this.clonedElement);
            }
            else if (NodePickerPanel.this.getMode() == 3) {
                NodePickerPanel.this.updateElementAttributes(NodePickerPanel.this.previewElement, (AttributesTableModel)tableModelEvent.getSource());
                NodePickerPanel.this.updateNodeXmlArea(NodePickerPanel.this.previewElement);
            }
        }
    }
    
    protected class XMLAreaListener implements DocumentListener
    {
        public void changedUpdate(final DocumentEvent documentEvent) {
            NodePickerPanel.this.isDirty = NodePickerPanel.this.isElementModified();
        }
        
        public void insertUpdate(final DocumentEvent documentEvent) {
            this.updateNodePicker(documentEvent);
            NodePickerPanel.this.isDirty = NodePickerPanel.this.isElementModified();
        }
        
        public void removeUpdate(final DocumentEvent documentEvent) {
            this.updateNodePicker(documentEvent);
            NodePickerPanel.this.isDirty = NodePickerPanel.this.isElementModified();
        }
        
        private void updateNodePicker(final DocumentEvent documentEvent) {
            if (NodePickerPanel.this.getMode() == 2) {
                NodePickerPanel.this.updateViewAfterSvgInput(NodePickerPanel.this.parseXml(NodePickerPanel.this.svgInputPanel.getNodeXmlArea().getText()), NodePickerPanel.this.clonedElement);
            }
            else if (NodePickerPanel.this.getMode() == 3) {
                NodePickerPanel.this.updateViewAfterSvgInput(NodePickerPanel.this.parseXml(NodePickerPanel.this.svgInputPanel.getNodeXmlArea().getText()), NodePickerPanel.this.previewElement);
            }
        }
    }
    
    protected class NodePickerEditListener extends FocusAdapter
    {
        public void focusGained(final FocusEvent focusEvent) {
            if (NodePickerPanel.this.getMode() == 1) {
                NodePickerPanel.this.enterEditMode();
            }
            NodePickerPanel.this.setEditable(NodePickerPanel.this.controller.isEditable() && NodePickerPanel.this.controller.canEdit(NodePickerPanel.this.previewElement));
            NodePickerPanel.this.isDirty = NodePickerPanel.this.isElementModified();
        }
    }
}
