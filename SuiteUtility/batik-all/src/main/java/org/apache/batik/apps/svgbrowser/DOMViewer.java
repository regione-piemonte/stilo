// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.apps.svgbrowser;

import javax.swing.AbstractAction;
import org.w3c.dom.events.Event;
import java.awt.event.MouseEvent;
import javax.swing.JPopupMenu;
import java.awt.event.MouseAdapter;
import java.awt.Frame;
import org.w3c.dom.DocumentFragment;
import java.awt.event.ActionEvent;
import javax.swing.table.TableModel;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.tree.DefaultTreeCellRenderer;
import org.w3c.dom.css.CSSStyleDeclaration;
import javax.swing.table.AbstractTableModel;
import java.util.Iterator;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Collections;
import java.util.Comparator;
import java.util.ArrayList;
import javax.swing.JMenuItem;
import javax.swing.JMenu;
import javax.swing.tree.TreePath;
import java.util.Enumeration;
import javax.swing.JTree;
import org.w3c.dom.NodeList;
import org.apache.batik.bridge.svg12.ContentManager;
import org.apache.batik.dom.xbl.XBLManager;
import org.apache.batik.bridge.svg12.DefaultXBLManager;
import org.apache.batik.dom.AbstractDocument;
import org.apache.batik.dom.svg12.XBLOMContentElement;
import org.apache.batik.dom.xbl.NodeXBL;
import org.w3c.dom.Text;
import javax.swing.tree.MutableTreeNode;
import org.w3c.dom.events.MutationEvent;
import java.lang.reflect.InvocationTargetException;
import javax.swing.SwingUtilities;
import org.w3c.dom.events.EventTarget;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.JButton;
import java.awt.event.MouseListener;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.TreeCellRenderer;
import javax.swing.tree.TreeNode;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.Dimension;
import org.apache.batik.util.gui.DropDownComponent;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JToolBar;
import java.awt.event.FocusListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusAdapter;
import javax.swing.border.Border;
import javax.swing.BorderFactory;
import javax.swing.JScrollPane;
import java.awt.GridBagLayout;
import java.net.URL;
import org.w3c.dom.NamedNodeMap;
import org.apache.batik.dom.util.DOMUtilities;
import org.apache.batik.dom.svg.SVGOMDocument;
import org.apache.batik.dom.util.SAXDocumentFactory;
import org.apache.batik.util.XMLResourceDescriptor;
import org.w3c.dom.Attr;
import javax.swing.JTextArea;
import java.awt.GridBagConstraints;
import javax.swing.JTable;
import javax.swing.JSplitPane;
import org.w3c.dom.events.EventListener;
import org.w3c.dom.Element;
import java.util.Locale;
import org.apache.batik.util.gui.resource.MissingListenerException;
import javax.swing.Action;
import org.w3c.dom.Node;
import org.w3c.dom.Document;
import org.w3c.dom.css.ViewCSS;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.JCheckBox;
import java.awt.LayoutManager;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Component;
import java.util.HashMap;
import javax.swing.JToggleButton;
import org.apache.batik.util.gui.resource.ButtonFactory;
import java.util.Map;
import org.apache.batik.util.resources.ResourceManager;
import java.util.ResourceBundle;
import org.apache.batik.util.gui.resource.ActionMap;
import javax.swing.JFrame;

public class DOMViewer extends JFrame implements ActionMap
{
    protected static final String RESOURCE = "org.apache.batik.apps.svgbrowser.resources.DOMViewerMessages";
    protected static ResourceBundle bundle;
    protected static ResourceManager resources;
    protected Map listeners;
    protected ButtonFactory buttonFactory;
    protected Panel panel;
    protected boolean showWhitespace;
    protected boolean isCapturingClickEnabled;
    protected DOMViewerController domViewerController;
    protected ElementOverlayManager elementOverlayManager;
    protected boolean isElementOverlayEnabled;
    protected HistoryBrowserInterface historyBrowserInterface;
    protected boolean canEdit;
    protected JToggleButton overlayButton;
    
    public DOMViewer(final DOMViewerController domViewerController) {
        super(DOMViewer.resources.getString("Frame.title"));
        this.listeners = new HashMap();
        this.showWhitespace = true;
        this.canEdit = true;
        this.setSize(DOMViewer.resources.getInteger("Frame.width"), DOMViewer.resources.getInteger("Frame.height"));
        this.domViewerController = domViewerController;
        this.elementOverlayManager = this.domViewerController.createSelectionManager();
        if (this.elementOverlayManager != null) {
            this.elementOverlayManager.setController(new DOMViewerElementOverlayController());
        }
        this.historyBrowserInterface = new HistoryBrowserInterface(new HistoryBrowser.DocumentCommandController(domViewerController));
        this.listeners.put("CloseButtonAction", new CloseButtonAction());
        this.listeners.put("UndoButtonAction", new UndoButtonAction());
        this.listeners.put("RedoButtonAction", new RedoButtonAction());
        this.listeners.put("CapturingClickButtonAction", new CapturingClickButtonAction());
        this.listeners.put("OverlayButtonAction", new OverlayButtonAction());
        this.panel = new Panel();
        this.getContentPane().add(this.panel);
        final JPanel comp = new JPanel(new BorderLayout());
        final JCheckBox comp2 = new JCheckBox(DOMViewer.resources.getString("ShowWhitespaceCheckbox.text"));
        comp2.setSelected(this.showWhitespace);
        comp2.addItemListener(new ItemListener() {
            public void itemStateChanged(final ItemEvent itemEvent) {
                DOMViewer.this.setShowWhitespace(itemEvent.getStateChange() == 1);
            }
        });
        comp.add(comp2, "West");
        comp.add(this.getButtonFactory().createJButton("CloseButton"), "East");
        this.getContentPane().add(comp, "South");
        final Document document = this.domViewerController.getDocument();
        if (document != null) {
            this.panel.setDocument(document, null);
        }
    }
    
    public void setShowWhitespace(final boolean showWhitespace) {
        this.showWhitespace = showWhitespace;
        if (this.panel.document != null) {
            this.panel.setDocument(this.panel.document);
        }
    }
    
    public void setDocument(final Document document) {
        this.panel.setDocument(document);
    }
    
    public void setDocument(final Document document, final ViewCSS viewCSS) {
        this.panel.setDocument(document, viewCSS);
    }
    
    public boolean canEdit() {
        return this.domViewerController.canEdit() && this.canEdit;
    }
    
    public void setEditable(final boolean canEdit) {
        this.canEdit = canEdit;
    }
    
    public void selectNode(final Node node) {
        this.panel.selectNode(node);
    }
    
    public void resetHistory() {
        this.historyBrowserInterface.getHistoryBrowser().resetHistory();
    }
    
    private ButtonFactory getButtonFactory() {
        if (this.buttonFactory == null) {
            this.buttonFactory = new ButtonFactory(DOMViewer.bundle, this);
        }
        return this.buttonFactory;
    }
    
    public Action getAction(final String s) throws MissingListenerException {
        return this.listeners.get(s);
    }
    
    private void addChangesToHistory() {
        this.historyBrowserInterface.performCurrentCompoundCommand();
    }
    
    protected void toggleOverlay() {
        if (!(this.isElementOverlayEnabled = this.overlayButton.isSelected())) {
            this.overlayButton.setToolTipText(DOMViewer.resources.getString("OverlayButton.tooltip"));
        }
        else {
            this.overlayButton.setToolTipText(DOMViewer.resources.getString("OverlayButton.disableText"));
        }
        if (this.elementOverlayManager != null) {
            this.elementOverlayManager.setOverlayEnabled(this.isElementOverlayEnabled);
            this.elementOverlayManager.repaint();
        }
    }
    
    static {
        DOMViewer.bundle = ResourceBundle.getBundle("org.apache.batik.apps.svgbrowser.resources.DOMViewerMessages", Locale.getDefault());
        DOMViewer.resources = new ResourceManager(DOMViewer.bundle);
    }
    
    protected static class ContentNodeInfo extends NodeInfo
    {
        public ContentNodeInfo(final Node node) {
            super(node);
        }
        
        public String toString() {
            return "selected content";
        }
    }
    
    protected static class NodeInfo
    {
        protected Node node;
        
        public NodeInfo(final Node node) {
            this.node = node;
        }
        
        public Node getNode() {
            return this.node;
        }
        
        public String toString() {
            if (this.node instanceof Element) {
                final String attribute = ((Element)this.node).getAttribute("id");
                if (attribute.length() != 0) {
                    return this.node.getNodeName() + " \"" + attribute + "\"";
                }
            }
            return this.node.getNodeName();
        }
    }
    
    protected static class ShadowNodeInfo extends NodeInfo
    {
        public ShadowNodeInfo(final Node node) {
            super(node);
        }
        
        public String toString() {
            return "shadow tree";
        }
    }
    
    public class Panel extends JPanel
    {
        public static final String NODE_INSERTED = "DOMNodeInserted";
        public static final String NODE_REMOVED = "DOMNodeRemoved";
        public static final String ATTRIBUTE_MODIFIED = "DOMAttrModified";
        public static final String CHAR_DATA_MODIFIED = "DOMCharacterDataModified";
        protected Document document;
        protected EventListener nodeInsertion;
        protected EventListener nodeRemoval;
        protected EventListener attrModification;
        protected EventListener charDataModification;
        protected EventListener capturingListener;
        protected ViewCSS viewCSS;
        protected DOMDocumentTree tree;
        protected JSplitPane splitPane;
        protected JPanel rightPanel;
        protected JTable propertiesTable;
        protected NodePickerPanel attributePanel;
        protected GridBagConstraints attributePanelLayout;
        protected GridBagConstraints propertiesTableLayout;
        protected JPanel elementPanel;
        protected CharacterPanel characterDataPanel;
        protected JTextArea documentInfo;
        protected JPanel documentInfoPanel;
        private final /* synthetic */ DOMViewer this$0;
        
        public Panel() {
            super(new BorderLayout());
            this.rightPanel = new JPanel(new BorderLayout());
            this.propertiesTable = new JTable();
            (this.attributePanel = new NodePickerPanel(new DOMViewerNodePickerController())).addListener(new NodePickerPanel.NodePickerAdapter() {
                private final /* synthetic */ Panel this$1 = this$1;
                
                public void updateElement(final NodePickerPanel.NodePickerEvent nodePickerEvent) {
                    final String result = nodePickerEvent.getResult();
                    final Element element = (Element)nodePickerEvent.getContextNode();
                    final Element wrapAndParse = this.wrapAndParse(result, element);
                    DOMViewer.this.addChangesToHistory();
                    final HistoryBrowserInterface.CompoundUpdateCommand nodeChangedCommand = this.this$1.this$0.historyBrowserInterface.createNodeChangedCommand(wrapAndParse);
                    final Node parentNode = element.getParentNode();
                    final Node nextSibling = element.getNextSibling();
                    nodeChangedCommand.addCommand(this.this$1.this$0.historyBrowserInterface.createRemoveChildCommand(parentNode, element));
                    nodeChangedCommand.addCommand(this.this$1.this$0.historyBrowserInterface.createInsertChildCommand(parentNode, nextSibling, wrapAndParse));
                    this.this$1.this$0.historyBrowserInterface.performCompoundUpdateCommand(nodeChangedCommand);
                    this.this$1.attributePanel.setPreviewElement(wrapAndParse);
                }
                
                public void addNewElement(final NodePickerPanel.NodePickerEvent nodePickerEvent) {
                    final String result = nodePickerEvent.getResult();
                    final Element element = (Element)nodePickerEvent.getContextNode();
                    final Element wrapAndParse = this.wrapAndParse(result, element);
                    DOMViewer.this.addChangesToHistory();
                    this.this$1.this$0.historyBrowserInterface.appendChild(element, wrapAndParse);
                    this.this$1.attributePanel.setPreviewElement(wrapAndParse);
                }
                
                private Element wrapAndParse(final String s, final Node node) {
                    final HashMap hashMap = new HashMap<String, String>();
                    int n = 0;
                    for (Node parentNode = node; parentNode != null; parentNode = parentNode.getParentNode()) {
                        final NamedNodeMap attributes = parentNode.getAttributes();
                        for (int n2 = 0; attributes != null && n2 < attributes.getLength(); ++n2) {
                            final Attr attr = (Attr)attributes.item(n2);
                            final String prefix = attr.getPrefix();
                            final String localName = attr.getLocalName();
                            final String value = attr.getValue();
                            if (prefix != null && prefix.equals("xmlns")) {
                                final String string = "xmlns:" + localName;
                                if (!hashMap.containsKey(string)) {
                                    hashMap.put(string, value);
                                }
                            }
                            if ((n != 0 || parentNode == this.this$1.document.getDocumentElement()) && attr.getNodeName().equals("xmlns") && !hashMap.containsKey("xmlns")) {
                                hashMap.put("xmlns", attr.getNodeValue());
                            }
                        }
                        ++n;
                    }
                    final Document document = this.this$1.this$0.panel.document;
                    final SAXDocumentFactory saxDocumentFactory = new SAXDocumentFactory(document.getImplementation(), XMLResourceDescriptor.getXMLParserClassName());
                    URL urlObject = null;
                    if (document instanceof SVGOMDocument) {
                        urlObject = ((SVGOMDocument)document).getURLObject();
                    }
                    return (Element)DOMUtilities.parseXML(s, document, (urlObject == null) ? "" : urlObject.toString(), hashMap, "svg", saxDocumentFactory).getFirstChild();
                }
                
                private void selectNewNode(final Element element) {
                    this.this$1.this$0.domViewerController.performUpdate(new Runnable() {
                        private final /* synthetic */ DOMViewer$2 this$2 = this$2;
                        
                        public void run() {
                            this.this$2.this$1.selectNode(element);
                        }
                    });
                }
            });
            this.attributePanelLayout = new GridBagConstraints();
            this.attributePanelLayout.gridx = 1;
            this.attributePanelLayout.gridy = 1;
            this.attributePanelLayout.gridheight = 2;
            this.attributePanelLayout.weightx = 1.0;
            this.attributePanelLayout.weighty = 1.0;
            this.attributePanelLayout.fill = 1;
            this.propertiesTableLayout = new GridBagConstraints();
            this.propertiesTableLayout.gridx = 1;
            this.propertiesTableLayout.gridy = 3;
            this.propertiesTableLayout.weightx = 1.0;
            this.propertiesTableLayout.weighty = 1.0;
            this.propertiesTableLayout.fill = 1;
            this.elementPanel = new JPanel(new GridBagLayout());
            final JScrollPane comp = new JScrollPane();
            comp.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(2, 0, 2, 2), BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(), DOMViewer.resources.getString("CSSValuesPanel.title")), BorderFactory.createLoweredBevelBorder())));
            comp.getViewport().add(this.propertiesTable);
            this.elementPanel.add(this.attributePanel, this.attributePanelLayout);
            this.elementPanel.add(comp, this.propertiesTableLayout);
            (this.characterDataPanel = new CharacterPanel(new BorderLayout())).setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(2, 0, 2, 2), BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(), DOMViewer.resources.getString("CDataPanel.title")), BorderFactory.createLoweredBevelBorder())));
            final JScrollPane comp2 = new JScrollPane();
            final JTextArea textArea = new JTextArea();
            this.characterDataPanel.setTextArea(textArea);
            comp2.getViewport().add(textArea);
            this.characterDataPanel.add(comp2);
            textArea.setEditable(true);
            textArea.addFocusListener(new FocusAdapter() {
                private final /* synthetic */ Panel this$1 = this$1;
                
                public void focusLost(final FocusEvent focusEvent) {
                    if (this.this$1.this$0.canEdit()) {
                        final Node node = this.this$1.characterDataPanel.getNode();
                        final String text = this.this$1.characterDataPanel.getTextArea().getText();
                        switch (node.getNodeType()) {
                            case 3:
                            case 4:
                            case 8: {
                                DOMViewer.this.addChangesToHistory();
                                this.this$1.this$0.historyBrowserInterface.setNodeValue(node, text);
                                break;
                            }
                        }
                    }
                }
            });
            this.documentInfo = new JTextArea();
            (this.documentInfoPanel = new JPanel(new BorderLayout())).setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(2, 0, 2, 2), BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(), DOMViewer.resources.getString("DocumentInfoPanel.title")), BorderFactory.createLoweredBevelBorder())));
            final JScrollPane comp3 = new JScrollPane();
            comp3.getViewport().add(this.documentInfo);
            this.documentInfoPanel.add(comp3);
            this.documentInfo.setEditable(false);
            this.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(), DOMViewer.resources.getString("DOMViewerPanel.title")));
            final JToolBar comp4 = new JToolBar(DOMViewer.resources.getString("DOMViewerToolbar.name"));
            comp4.setFloatable(false);
            final JButton jToolbarButton = DOMViewer.this.getButtonFactory().createJToolbarButton("UndoButton");
            jToolbarButton.setDisabledIcon(new ImageIcon(this.getClass().getResource(DOMViewer.resources.getString("UndoButton.disabledIcon"))));
            final DropDownComponent comp5 = new DropDownComponent(jToolbarButton);
            comp5.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 2));
            comp5.setMaximumSize(new Dimension(44, 25));
            comp5.setPreferredSize(new Dimension(44, 25));
            comp4.add(comp5);
            comp5.getPopupMenu().setModel(new DropDownHistoryModel.UndoPopUpMenuModel(comp5.getPopupMenu(), DOMViewer.this.historyBrowserInterface));
            final JButton jToolbarButton2 = DOMViewer.this.getButtonFactory().createJToolbarButton("RedoButton");
            jToolbarButton2.setDisabledIcon(new ImageIcon(this.getClass().getResource(DOMViewer.resources.getString("RedoButton.disabledIcon"))));
            final DropDownComponent comp6 = new DropDownComponent(jToolbarButton2);
            comp6.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 2));
            comp6.setMaximumSize(new Dimension(44, 25));
            comp6.setPreferredSize(new Dimension(44, 25));
            comp4.add(comp6);
            comp6.getPopupMenu().setModel(new DropDownHistoryModel.RedoPopUpMenuModel(comp6.getPopupMenu(), DOMViewer.this.historyBrowserInterface));
            final JToggleButton jToolbarToggleButton = DOMViewer.this.getButtonFactory().createJToolbarToggleButton("CapturingClickButton");
            jToolbarToggleButton.setEnabled(true);
            jToolbarToggleButton.setPreferredSize(new Dimension(32, 25));
            comp4.add(jToolbarToggleButton);
            (DOMViewer.this.overlayButton = DOMViewer.this.getButtonFactory().createJToolbarToggleButton("OverlayButton")).setEnabled(true);
            DOMViewer.this.overlayButton.setPreferredSize(new Dimension(32, 25));
            comp4.add(DOMViewer.this.overlayButton);
            this.add(comp4, "North");
            (this.tree = new DOMDocumentTree(new DefaultMutableTreeNode(DOMViewer.resources.getString("EmptyDocument.text")), new DOMViewerDOMDocumentTreeController())).setCellRenderer(new NodeRenderer());
            this.tree.putClientProperty("JTree.lineStyle", "Angled");
            this.tree.addListener(new DOMDocumentTree.DOMDocumentTreeAdapter() {
                private final /* synthetic */ Panel this$1 = this$1;
                
                public void dropCompleted(final DOMDocumentTree.DOMDocumentTreeEvent domDocumentTreeEvent) {
                    final DOMDocumentTree.DropCompletedInfo dropCompletedInfo = (DOMDocumentTree.DropCompletedInfo)domDocumentTreeEvent.getSource();
                    DOMViewer.this.addChangesToHistory();
                    final HistoryBrowserInterface.CompoundUpdateCommand nodesDroppedCommand = this.this$1.this$0.historyBrowserInterface.createNodesDroppedCommand(dropCompletedInfo.getChildren());
                    for (int size = dropCompletedInfo.getChildren().size(), i = 0; i < size; ++i) {
                        final Node node = dropCompletedInfo.getChildren().get(i);
                        if (!DOMUtilities.isAnyNodeAncestorOf(dropCompletedInfo.getChildren(), node)) {
                            nodesDroppedCommand.addCommand(this.this$1.this$0.historyBrowserInterface.createInsertChildCommand(dropCompletedInfo.getParent(), dropCompletedInfo.getSibling(), node));
                        }
                    }
                    this.this$1.this$0.historyBrowserInterface.performCompoundUpdateCommand(nodesDroppedCommand);
                }
            });
            this.tree.addTreeSelectionListener(new DOMTreeSelectionListener());
            this.tree.addMouseListener(new TreePopUpListener());
            final JScrollPane newLeftComponent = new JScrollPane();
            newLeftComponent.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(2, 2, 2, 0), BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(), DOMViewer.resources.getString("DOMViewer.title")), BorderFactory.createLoweredBevelBorder())));
            newLeftComponent.getViewport().add(this.tree);
            (this.splitPane = new JSplitPane(1, true, newLeftComponent, this.rightPanel)).setDividerLocation(DOMViewer.resources.getInteger("SplitPane.dividerLocation"));
            this.add(this.splitPane);
        }
        
        public void setDocument(final Document document) {
            this.setDocument(document, null);
        }
        
        public void setDocument(final Document document, final ViewCSS viewCSS) {
            if (this.document != null) {
                if (this.document != document) {
                    this.removeDomMutationListeners(this.document);
                    this.addDomMutationListeners(document);
                    this.removeCapturingListener(this.document);
                    this.addCapturingListener(document);
                }
            }
            else {
                this.addDomMutationListeners(document);
                this.addCapturingListener(document);
            }
            DOMViewer.this.resetHistory();
            this.document = document;
            this.viewCSS = viewCSS;
            ((DefaultTreeModel)this.tree.getModel()).setRoot(this.createTree(document, DOMViewer.this.showWhitespace));
            if (this.rightPanel.getComponentCount() != 0) {
                this.rightPanel.remove(0);
                this.splitPane.revalidate();
                this.splitPane.repaint();
            }
        }
        
        protected void addDomMutationListeners(final Document document) {
            final EventTarget eventTarget = (EventTarget)document;
            eventTarget.addEventListener("DOMNodeInserted", this.nodeInsertion = new NodeInsertionHandler(), true);
            eventTarget.addEventListener("DOMNodeRemoved", this.nodeRemoval = new NodeRemovalHandler(), true);
            eventTarget.addEventListener("DOMAttrModified", this.attrModification = new AttributeModificationHandler(), true);
            eventTarget.addEventListener("DOMCharacterDataModified", this.charDataModification = new CharDataModificationHandler(), true);
        }
        
        protected void removeDomMutationListeners(final Document document) {
            if (document != null) {
                final EventTarget eventTarget = (EventTarget)document;
                eventTarget.removeEventListener("DOMNodeInserted", this.nodeInsertion, true);
                eventTarget.removeEventListener("DOMNodeRemoved", this.nodeRemoval, true);
                eventTarget.removeEventListener("DOMAttrModified", this.attrModification, true);
                eventTarget.removeEventListener("DOMCharacterDataModified", this.charDataModification, true);
            }
        }
        
        protected void addCapturingListener(final Document document) {
            ((EventTarget)document.getDocumentElement()).addEventListener("click", this.capturingListener = new CapturingClickHandler(), true);
        }
        
        protected void removeCapturingListener(final Document document) {
            if (document != null) {
                ((EventTarget)document.getDocumentElement()).removeEventListener("click", this.capturingListener, true);
            }
        }
        
        protected void refreshGUI(final Runnable doRun) {
            if (DOMViewer.this.canEdit()) {
                try {
                    SwingUtilities.invokeAndWait(doRun);
                }
                catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
                catch (InvocationTargetException ex2) {
                    ex2.printStackTrace();
                }
            }
        }
        
        protected void registerNodeInserted(final MutationEvent mutationEvent) {
            final Node node = (Node)mutationEvent.getTarget();
            DOMViewer.this.historyBrowserInterface.addToCurrentCompoundCommand(DOMViewer.this.historyBrowserInterface.createNodeInsertedCommand(node.getParentNode(), node.getNextSibling(), node));
        }
        
        protected void registerNodeRemoved(final MutationEvent mutationEvent) {
            final Node node = (Node)mutationEvent.getTarget();
            DOMViewer.this.historyBrowserInterface.addToCurrentCompoundCommand(DOMViewer.this.historyBrowserInterface.createNodeRemovedCommand(mutationEvent.getRelatedNode(), node.getNextSibling(), node));
        }
        
        protected void registerAttributeAdded(final MutationEvent mutationEvent) {
            DOMViewer.this.historyBrowserInterface.addToCurrentCompoundCommand(DOMViewer.this.historyBrowserInterface.createAttributeAddedCommand((Element)mutationEvent.getTarget(), mutationEvent.getAttrName(), mutationEvent.getNewValue(), null));
        }
        
        protected void registerAttributeRemoved(final MutationEvent mutationEvent) {
            DOMViewer.this.historyBrowserInterface.addToCurrentCompoundCommand(DOMViewer.this.historyBrowserInterface.createAttributeRemovedCommand((Element)mutationEvent.getTarget(), mutationEvent.getAttrName(), mutationEvent.getPrevValue(), null));
        }
        
        protected void registerAttributeModified(final MutationEvent mutationEvent) {
            DOMViewer.this.historyBrowserInterface.addToCurrentCompoundCommand(DOMViewer.this.historyBrowserInterface.createAttributeModifiedCommand((Element)mutationEvent.getTarget(), mutationEvent.getAttrName(), mutationEvent.getPrevValue(), mutationEvent.getNewValue(), null));
        }
        
        protected void registerAttributeChanged(final MutationEvent mutationEvent) {
            switch (mutationEvent.getAttrChange()) {
                case 2: {
                    this.registerAttributeAdded(mutationEvent);
                    break;
                }
                case 3: {
                    this.registerAttributeRemoved(mutationEvent);
                    break;
                }
                case 1: {
                    this.registerAttributeModified(mutationEvent);
                    break;
                }
                default: {
                    this.registerAttributeModified(mutationEvent);
                    break;
                }
            }
        }
        
        protected void registerCharDataModified(final MutationEvent mutationEvent) {
            DOMViewer.this.historyBrowserInterface.addToCurrentCompoundCommand(DOMViewer.this.historyBrowserInterface.createCharDataModifiedCommand((Node)mutationEvent.getTarget(), mutationEvent.getPrevValue(), mutationEvent.getNewValue()));
        }
        
        protected boolean shouldRegisterDocumentChange() {
            return DOMViewer.this.canEdit() && DOMViewer.this.historyBrowserInterface.getHistoryBrowser().getState() == 4;
        }
        
        protected void registerDocumentChange(final MutationEvent mutationEvent) {
            if (this.shouldRegisterDocumentChange()) {
                final String type = mutationEvent.getType();
                if (type.equals("DOMNodeInserted")) {
                    this.registerNodeInserted(mutationEvent);
                }
                else if (type.equals("DOMNodeRemoved")) {
                    this.registerNodeRemoved(mutationEvent);
                }
                else if (type.equals("DOMAttrModified")) {
                    this.registerAttributeChanged(mutationEvent);
                }
                else if (type.equals("DOMCharacterDataModified")) {
                    this.registerCharDataModified(mutationEvent);
                }
            }
        }
        
        protected MutableTreeNode createTree(final Node node, final boolean b) {
            final DefaultMutableTreeNode defaultMutableTreeNode = new DefaultMutableTreeNode(new NodeInfo(node));
            for (Node node2 = node.getFirstChild(); node2 != null; node2 = node2.getNextSibling()) {
                if (b || !(node2 instanceof Text) || node2.getNodeValue().trim().length() != 0) {
                    defaultMutableTreeNode.add(this.createTree(node2, b));
                }
            }
            if (node instanceof NodeXBL) {
                final Element xblShadowTree = ((NodeXBL)node).getXblShadowTree();
                if (xblShadowTree != null) {
                    final DefaultMutableTreeNode newChild = new DefaultMutableTreeNode(new ShadowNodeInfo(xblShadowTree));
                    newChild.add(this.createTree(xblShadowTree, b));
                    defaultMutableTreeNode.add(newChild);
                }
            }
            if (node instanceof XBLOMContentElement) {
                final XBLManager xblManager = ((AbstractDocument)node.getOwnerDocument()).getXBLManager();
                if (xblManager instanceof DefaultXBLManager) {
                    final DefaultMutableTreeNode newChild2 = new DefaultMutableTreeNode(new ContentNodeInfo(node));
                    final ContentManager contentManager = ((DefaultXBLManager)xblManager).getContentManager(node);
                    if (contentManager != null) {
                        final NodeList selectedContent = contentManager.getSelectedContent((XBLOMContentElement)node);
                        for (int i = 0; i < selectedContent.getLength(); ++i) {
                            newChild2.add(this.createTree(selectedContent.item(i), b));
                        }
                        defaultMutableTreeNode.add(newChild2);
                    }
                }
            }
            return defaultMutableTreeNode;
        }
        
        protected DefaultMutableTreeNode findNode(final JTree tree, final Node node) {
            final Enumeration<TreeNode> breadthFirstEnumeration = ((DefaultMutableTreeNode)tree.getModel().getRoot()).breadthFirstEnumeration();
            while (breadthFirstEnumeration.hasMoreElements()) {
                final DefaultMutableTreeNode defaultMutableTreeNode = breadthFirstEnumeration.nextElement();
                if (((NodeInfo)defaultMutableTreeNode.getUserObject()).getNode() == node) {
                    return defaultMutableTreeNode;
                }
            }
            return null;
        }
        
        public void selectNode(final Node node) {
            SwingUtilities.invokeLater(new Runnable() {
                private final /* synthetic */ Panel this$1 = this$1;
                
                public void run() {
                    final DefaultMutableTreeNode node = this.this$1.findNode(this.this$1.tree, node);
                    if (node != null) {
                        final TreePath treePath = new TreePath(node.getPath());
                        this.this$1.tree.setSelectionPath(treePath);
                        this.this$1.tree.scrollPathToVisible(treePath);
                    }
                }
            });
        }
        
        protected JMenu createTemplatesMenu(final String s) {
            final NodeTemplates nodeTemplates = new NodeTemplates();
            final JMenu menu = new JMenu(s);
            final HashMap<String, JMenu> hashMap = new HashMap<String, JMenu>();
            final ArrayList categories = nodeTemplates.getCategories();
            for (int size = categories.size(), i = 0; i < size; ++i) {
                final String string = categories.get(i).toString();
                final JMenu menu2 = new JMenu(string);
                menu.add(menu2);
                hashMap.put(string, menu2);
            }
            final ArrayList list = new ArrayList<NodeTemplates.NodeTemplateDescriptor>(nodeTemplates.getNodeTemplatesMap().values());
            Collections.sort((List<E>)list, new Comparator() {
                public int compare(final Object o, final Object o2) {
                    return ((NodeTemplates.NodeTemplateDescriptor)o).getName().compareTo(((NodeTemplates.NodeTemplateDescriptor)o2).getName());
                }
            });
            for (final NodeTemplates.NodeTemplateDescriptor nodeTemplateDescriptor : list) {
                final String xmlValue = nodeTemplateDescriptor.getXmlValue();
                final short type = nodeTemplateDescriptor.getType();
                final String category = nodeTemplateDescriptor.getCategory();
                final JMenuItem menuItem = new JMenuItem(nodeTemplateDescriptor.getName());
                menuItem.addActionListener(new NodeTemplateParser(xmlValue, type));
                hashMap.get(category).add(menuItem);
            }
            return menu;
        }
        
        protected class NodeCSSValuesModel extends AbstractTableModel
        {
            protected Node node;
            protected CSSStyleDeclaration style;
            protected List propertyNames;
            
            public NodeCSSValuesModel(final Node node) {
                this.node = node;
                if (Panel.this.viewCSS != null) {
                    this.style = Panel.this.viewCSS.getComputedStyle((Element)node, null);
                    this.propertyNames = new ArrayList();
                    if (this.style != null) {
                        for (int i = 0; i < this.style.getLength(); ++i) {
                            this.propertyNames.add(this.style.item(i));
                        }
                        Collections.sort((List<Comparable>)this.propertyNames);
                    }
                }
            }
            
            public String getColumnName(final int n) {
                if (n == 0) {
                    return DOMViewer.resources.getString("CSSValuesTable.column1");
                }
                return DOMViewer.resources.getString("CSSValuesTable.column2");
            }
            
            public int getColumnCount() {
                return 2;
            }
            
            public int getRowCount() {
                if (this.style == null) {
                    return 0;
                }
                return this.style.getLength();
            }
            
            public boolean isCellEditable(final int n, final int n2) {
                return false;
            }
            
            public Object getValueAt(final int n, final int n2) {
                final String s = this.propertyNames.get(n);
                if (n2 == 0) {
                    return s;
                }
                return this.style.getPropertyValue(s);
            }
        }
        
        protected class NodeRenderer extends DefaultTreeCellRenderer
        {
            protected ImageIcon elementIcon;
            protected ImageIcon commentIcon;
            protected ImageIcon piIcon;
            protected ImageIcon textIcon;
            
            public NodeRenderer() {
                this.elementIcon = new ImageIcon(this.getClass().getResource(DOMViewer.resources.getString("Element.icon")));
                this.commentIcon = new ImageIcon(this.getClass().getResource(DOMViewer.resources.getString("Comment.icon")));
                this.piIcon = new ImageIcon(this.getClass().getResource(DOMViewer.resources.getString("PI.icon")));
                this.textIcon = new ImageIcon(this.getClass().getResource(DOMViewer.resources.getString("Text.icon")));
            }
            
            public Component getTreeCellRendererComponent(final JTree tree, final Object value, final boolean sel, final boolean expanded, final boolean leaf, final int row, final boolean hasFocus) {
                super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);
                switch (this.getNodeType(value)) {
                    case 1: {
                        this.setIcon(this.elementIcon);
                        break;
                    }
                    case 8: {
                        this.setIcon(this.commentIcon);
                        break;
                    }
                    case 7: {
                        this.setIcon(this.piIcon);
                        break;
                    }
                    case 3:
                    case 4: {
                        this.setIcon(this.textIcon);
                        break;
                    }
                }
                return this;
            }
            
            protected short getNodeType(final Object o) {
                final Object userObject = ((DefaultMutableTreeNode)o).getUserObject();
                if (userObject instanceof NodeInfo) {
                    return ((NodeInfo)userObject).getNode().getNodeType();
                }
                return -1;
            }
        }
        
        protected class DOMTreeSelectionListener implements TreeSelectionListener
        {
            public void valueChanged(final TreeSelectionEvent treeSelectionEvent) {
                if (DOMViewer.this.elementOverlayManager != null) {
                    this.handleElementSelection(treeSelectionEvent);
                }
                final DefaultMutableTreeNode defaultMutableTreeNode = (DefaultMutableTreeNode)Panel.this.tree.getLastSelectedPathComponent();
                if (defaultMutableTreeNode == null) {
                    return;
                }
                if (Panel.this.rightPanel.getComponentCount() != 0) {
                    Panel.this.rightPanel.remove(0);
                }
                final Object userObject = defaultMutableTreeNode.getUserObject();
                if (userObject instanceof NodeInfo) {
                    final Node node = ((NodeInfo)userObject).getNode();
                    switch (node.getNodeType()) {
                        case 9: {
                            Panel.this.documentInfo.setText(this.createDocumentText((Document)node));
                            Panel.this.rightPanel.add(Panel.this.documentInfoPanel);
                            break;
                        }
                        case 1: {
                            Panel.this.propertiesTable.setModel(new NodeCSSValuesModel(node));
                            Panel.this.attributePanel.promptForChanges();
                            Panel.this.attributePanel.setPreviewElement((Element)node);
                            Panel.this.rightPanel.add(Panel.this.elementPanel);
                            break;
                        }
                        case 3:
                        case 4:
                        case 8: {
                            Panel.this.characterDataPanel.setNode(node);
                            Panel.this.characterDataPanel.getTextArea().setText(node.getNodeValue());
                            Panel.this.rightPanel.add(Panel.this.characterDataPanel);
                            break;
                        }
                    }
                }
                Panel.this.splitPane.revalidate();
                Panel.this.splitPane.repaint();
            }
            
            protected String createDocumentText(final Document document) {
                final StringBuffer sb = new StringBuffer();
                sb.append("Nodes: ");
                sb.append(this.nodeCount(document));
                return sb.toString();
            }
            
            protected int nodeCount(final Node node) {
                int n = 1;
                for (Node node2 = node.getFirstChild(); node2 != null; node2 = node2.getNextSibling()) {
                    n += this.nodeCount(node2);
                }
                return n;
            }
            
            protected void handleElementSelection(final TreeSelectionEvent treeSelectionEvent) {
                final TreePath[] paths = treeSelectionEvent.getPaths();
                for (int i = 0; i < paths.length; ++i) {
                    final TreePath path = paths[i];
                    final Object userObject = ((DefaultMutableTreeNode)path.getLastPathComponent()).getUserObject();
                    if (userObject instanceof NodeInfo) {
                        final Node node = ((NodeInfo)userObject).getNode();
                        if (node.getNodeType() == 1) {
                            if (treeSelectionEvent.isAddedPath(path)) {
                                DOMViewer.this.elementOverlayManager.addElement((Element)node);
                            }
                            else {
                                DOMViewer.this.elementOverlayManager.removeElement((Element)node);
                            }
                        }
                    }
                }
                DOMViewer.this.elementOverlayManager.repaint();
            }
        }
        
        protected class CharacterPanel extends JPanel
        {
            protected Node node;
            protected JTextArea textArea;
            
            public CharacterPanel(final BorderLayout layout) {
                super(layout);
                this.textArea = new JTextArea();
            }
            
            public JTextArea getTextArea() {
                return this.textArea;
            }
            
            public void setTextArea(final JTextArea textArea) {
                this.textArea = textArea;
            }
            
            public Node getNode() {
                return this.node;
            }
            
            public void setNode(final Node node) {
                this.node = node;
            }
        }
        
        protected class TreeNodeRemover implements ActionListener
        {
            public void actionPerformed(final ActionEvent actionEvent) {
                DOMViewer.this.addChangesToHistory();
                final HistoryBrowserInterface.CompoundUpdateCommand removeSelectedTreeNodesCommand = DOMViewer.this.historyBrowserInterface.createRemoveSelectedTreeNodesCommand(null);
                final TreePath[] selectionPaths = Panel.this.tree.getSelectionPaths();
                for (int n = 0; selectionPaths != null && n < selectionPaths.length; ++n) {
                    final NodeInfo nodeInfo = (NodeInfo)((DefaultMutableTreeNode)selectionPaths[n].getLastPathComponent()).getUserObject();
                    if (DOMUtilities.isParentOf(nodeInfo.getNode(), nodeInfo.getNode().getParentNode())) {
                        removeSelectedTreeNodesCommand.addCommand(DOMViewer.this.historyBrowserInterface.createRemoveChildCommand(nodeInfo.getNode().getParentNode(), nodeInfo.getNode()));
                    }
                }
                DOMViewer.this.historyBrowserInterface.performCompoundUpdateCommand(removeSelectedTreeNodesCommand);
            }
        }
        
        protected class NodeTemplateParser implements ActionListener
        {
            protected String toParse;
            protected short nodeType;
            
            public NodeTemplateParser(final String toParse, final short nodeType) {
                this.toParse = toParse;
                this.nodeType = nodeType;
            }
            
            public void actionPerformed(final ActionEvent actionEvent) {
                Node node = null;
                switch (this.nodeType) {
                    case 1: {
                        URL urlObject = null;
                        if (Panel.this.document instanceof SVGOMDocument) {
                            urlObject = ((SVGOMDocument)Panel.this.document).getURLObject();
                        }
                        final String s = (urlObject == null) ? "" : urlObject.toString();
                        final HashMap<String, String> hashMap = new HashMap<String, String>();
                        hashMap.put("xmlns", "http://www.w3.org/2000/svg");
                        hashMap.put("xmlns:xlink", "http://www.w3.org/1999/xlink");
                        node = ((DocumentFragment)DOMUtilities.parseXML(this.toParse, Panel.this.document, s, hashMap, "svg", new SAXDocumentFactory(Panel.this.document.getImplementation(), XMLResourceDescriptor.getXMLParserClassName()))).getFirstChild();
                        break;
                    }
                    case 3: {
                        node = Panel.this.document.createTextNode(this.toParse);
                        break;
                    }
                    case 8: {
                        node = Panel.this.document.createComment(this.toParse);
                        break;
                    }
                    case 4: {
                        node = Panel.this.document.createCDATASection(this.toParse);
                        break;
                    }
                }
                final TreePath[] selectionPaths = Panel.this.tree.getSelectionPaths();
                if (selectionPaths != null) {
                    final NodeInfo nodeInfo = (NodeInfo)((DefaultMutableTreeNode)selectionPaths[selectionPaths.length - 1].getLastPathComponent()).getUserObject();
                    DOMViewer.this.addChangesToHistory();
                    DOMViewer.this.historyBrowserInterface.appendChild(nodeInfo.getNode(), node);
                }
            }
        }
        
        protected class TreeNodeAdder implements ActionListener
        {
            public void actionPerformed(final ActionEvent actionEvent) {
                final NodePickerPanel.NameEditorDialog nameEditorDialog = new NodePickerPanel.NameEditorDialog(DOMViewer.this);
                nameEditorDialog.setLocationRelativeTo(DOMViewer.this);
                if (nameEditorDialog.showDialog() == 0) {
                    final Element elementNS = Panel.this.document.createElementNS("http://www.w3.org/2000/svg", nameEditorDialog.getResults());
                    if (Panel.this.rightPanel.getComponentCount() != 0) {
                        Panel.this.rightPanel.remove(0);
                    }
                    Panel.this.rightPanel.add(Panel.this.elementPanel);
                    final TreePath[] selectionPaths = Panel.this.tree.getSelectionPaths();
                    if (selectionPaths != null) {
                        Panel.this.attributePanel.enterAddNewElementMode(elementNS, ((NodeInfo)((DefaultMutableTreeNode)selectionPaths[selectionPaths.length - 1].getLastPathComponent()).getUserObject()).getNode());
                    }
                }
            }
        }
        
        protected class TreePopUpListener extends MouseAdapter
        {
            protected JPopupMenu treePopupMenu;
            
            public TreePopUpListener() {
                (this.treePopupMenu = new JPopupMenu()).add(Panel.this.createTemplatesMenu(DOMViewer.resources.getString("ContextMenuItem.insertNewNode")));
                final JMenuItem menuItem = new JMenuItem(DOMViewer.resources.getString("ContextMenuItem.createNewElement"));
                this.treePopupMenu.add(menuItem);
                menuItem.addActionListener(new TreeNodeAdder());
                final JMenuItem menuItem2 = new JMenuItem(DOMViewer.resources.getString("ContextMenuItem.removeSelection"));
                menuItem2.addActionListener(new TreeNodeRemover());
                this.treePopupMenu.add(menuItem2);
            }
            
            public void mouseReleased(final MouseEvent mouseEvent) {
                if (mouseEvent.isPopupTrigger() && mouseEvent.getClickCount() == 1 && Panel.this.tree.getSelectionPaths() != null) {
                    this.showPopUp(mouseEvent);
                }
            }
            
            public void mousePressed(final MouseEvent mouseEvent) {
                final JTree tree = (JTree)mouseEvent.getSource();
                final TreePath pathForLocation = tree.getPathForLocation(mouseEvent.getX(), mouseEvent.getY());
                if (!mouseEvent.isControlDown() && !mouseEvent.isShiftDown()) {
                    tree.setSelectionPath(pathForLocation);
                }
                else {
                    tree.addSelectionPath(pathForLocation);
                }
                if (mouseEvent.isPopupTrigger() && mouseEvent.getClickCount() == 1) {
                    this.showPopUp(mouseEvent);
                }
            }
            
            private void showPopUp(final MouseEvent mouseEvent) {
                if (DOMViewer.this.canEdit()) {
                    final TreePath pathForLocation = Panel.this.tree.getPathForLocation(mouseEvent.getX(), mouseEvent.getY());
                    if (pathForLocation != null && pathForLocation.getPathCount() > 1) {
                        this.treePopupMenu.show((Component)mouseEvent.getSource(), mouseEvent.getX(), mouseEvent.getY());
                    }
                }
            }
        }
        
        protected class CapturingClickHandler implements EventListener
        {
            public void handleEvent(final Event event) {
                if (DOMViewer.this.isCapturingClickEnabled) {
                    Panel.this.selectNode((Node)event.getTarget());
                }
            }
        }
        
        protected class CharDataModificationHandler implements EventListener
        {
            private final /* synthetic */ Panel this$1;
            
            public void handleEvent(final Event event) {
                Panel.this.refreshGUI(new Runnable() {
                    private final /* synthetic */ CharDataModificationHandler this$2 = this$2;
                    
                    public void run() {
                        final MutationEvent mutationEvent = (MutationEvent)event;
                        final Node node = (Node)mutationEvent.getTarget();
                        if (this.this$2.this$1.characterDataPanel.getNode() == node) {
                            this.this$2.this$1.characterDataPanel.getTextArea().setText(node.getNodeValue());
                            this.this$2.this$1.attributePanel.updateOnDocumentChange(mutationEvent.getType(), node);
                        }
                    }
                });
                if (Panel.this.characterDataPanel.getNode() == event.getTarget()) {
                    Panel.this.registerDocumentChange((MutationEvent)event);
                }
            }
        }
        
        protected class AttributeModificationHandler implements EventListener
        {
            private final /* synthetic */ Panel this$1;
            
            public void handleEvent(final Event event) {
                Panel.this.refreshGUI(new Runnable() {
                    private final /* synthetic */ AttributeModificationHandler this$2 = this$2;
                    
                    public void run() {
                        final MutationEvent mutationEvent = (MutationEvent)event;
                        final Element element = (Element)mutationEvent.getTarget();
                        ((DefaultTreeModel)this.this$2.this$1.tree.getModel()).nodeChanged(this.this$2.this$1.findNode(this.this$2.this$1.tree, element));
                        this.this$2.this$1.attributePanel.updateOnDocumentChange(mutationEvent.getType(), element);
                    }
                });
                Panel.this.registerDocumentChange((MutationEvent)event);
            }
        }
        
        protected class NodeRemovalHandler implements EventListener
        {
            private final /* synthetic */ Panel this$1;
            
            public void handleEvent(final Event event) {
                Panel.this.refreshGUI(new Runnable() {
                    private final /* synthetic */ NodeRemovalHandler this$2 = this$2;
                    
                    public void run() {
                        final MutationEvent mutationEvent = (MutationEvent)event;
                        final Node node = (Node)mutationEvent.getTarget();
                        final DefaultMutableTreeNode node2 = this.this$2.this$1.findNode(this.this$2.this$1.tree, node);
                        final DefaultTreeModel defaultTreeModel = (DefaultTreeModel)this.this$2.this$1.tree.getModel();
                        if (node2 != null) {
                            defaultTreeModel.removeNodeFromParent(node2);
                        }
                        this.this$2.this$1.attributePanel.updateOnDocumentChange(mutationEvent.getType(), node);
                    }
                });
                Panel.this.registerDocumentChange((MutationEvent)event);
            }
        }
        
        protected class NodeInsertionHandler implements EventListener
        {
            private final /* synthetic */ Panel this$1;
            
            public void handleEvent(final Event event) {
                Panel.this.refreshGUI(new Runnable() {
                    private final /* synthetic */ NodeInsertionHandler this$2 = this$2;
                    
                    public void run() {
                        final MutationEvent mutationEvent = (MutationEvent)event;
                        final Node node = (Node)mutationEvent.getTarget();
                        final DefaultMutableTreeNode node2 = this.this$2.this$1.findNode(this.this$2.this$1.tree, node.getParentNode());
                        final DefaultMutableTreeNode newChild = (DefaultMutableTreeNode)this.this$2.this$1.createTree(node, this.this$2.this$1.this$0.showWhitespace);
                        final DefaultTreeModel defaultTreeModel = (DefaultTreeModel)this.this$2.this$1.tree.getModel();
                        final int indexToInsert = this.this$2.findIndexToInsert(node2, (DefaultMutableTreeNode)this.this$2.this$1.createTree(node.getParentNode(), this.this$2.this$1.this$0.showWhitespace));
                        if (indexToInsert != -1) {
                            defaultTreeModel.insertNodeInto(newChild, node2, indexToInsert);
                        }
                        this.this$2.this$1.attributePanel.updateOnDocumentChange(mutationEvent.getType(), node);
                    }
                });
                Panel.this.registerDocumentChange((MutationEvent)event);
            }
            
            protected int findIndexToInsert(final DefaultMutableTreeNode defaultMutableTreeNode, final DefaultMutableTreeNode defaultMutableTreeNode2) {
                final int n = -1;
                if (defaultMutableTreeNode == null || defaultMutableTreeNode2 == null) {
                    return n;
                }
                final Enumeration<TreeNode> children = defaultMutableTreeNode.children();
                final Enumeration<TreeNode> children2 = defaultMutableTreeNode2.children();
                int n2 = 0;
                while (children.hasMoreElements()) {
                    if (((NodeInfo)children.nextElement().getUserObject()).getNode() != ((NodeInfo)children2.nextElement().getUserObject()).getNode()) {
                        return n2;
                    }
                    ++n2;
                }
                return n2;
            }
        }
    }
    
    protected class DOMViewerNodePickerController implements NodePickerController
    {
        public boolean isEditable() {
            return DOMViewer.this.canEdit();
        }
        
        public boolean canEdit(final Element element) {
            if (DOMViewer.this.panel == null || DOMViewer.this.panel.document != null) {}
            return true;
        }
    }
    
    protected class DOMViewerDOMDocumentTreeController implements DOMDocumentTreeController
    {
        public boolean isDNDSupported() {
            return DOMViewer.this.canEdit();
        }
    }
    
    protected class DOMViewerElementOverlayController implements ElementOverlayController
    {
        public boolean isOverlayEnabled() {
            return DOMViewer.this.canEdit() && DOMViewer.this.isElementOverlayEnabled;
        }
    }
    
    protected class OverlayButtonAction extends AbstractAction
    {
        public void actionPerformed(final ActionEvent actionEvent) {
            DOMViewer.this.toggleOverlay();
        }
    }
    
    protected class CapturingClickButtonAction extends AbstractAction
    {
        public void actionPerformed(final ActionEvent actionEvent) {
            final JToggleButton toggleButton = (JToggleButton)actionEvent.getSource();
            if (!(DOMViewer.this.isCapturingClickEnabled = toggleButton.isSelected())) {
                toggleButton.setToolTipText(DOMViewer.resources.getString("CapturingClickButton.tooltip"));
            }
            else {
                toggleButton.setToolTipText(DOMViewer.resources.getString("CapturingClickButton.disableText"));
            }
        }
    }
    
    protected class RedoButtonAction extends AbstractAction
    {
        public void actionPerformed(final ActionEvent actionEvent) {
            DOMViewer.this.addChangesToHistory();
            DOMViewer.this.historyBrowserInterface.getHistoryBrowser().redo();
        }
    }
    
    protected class UndoButtonAction extends AbstractAction
    {
        public void actionPerformed(final ActionEvent actionEvent) {
            DOMViewer.this.addChangesToHistory();
            DOMViewer.this.historyBrowserInterface.getHistoryBrowser().undo();
        }
    }
    
    protected class CloseButtonAction extends AbstractAction
    {
        public void actionPerformed(final ActionEvent actionEvent) {
            if (DOMViewer.this.panel.attributePanel.panelHiding()) {
                DOMViewer.this.panel.tree.setSelectionRow(0);
                DOMViewer.this.dispose();
            }
        }
    }
}
