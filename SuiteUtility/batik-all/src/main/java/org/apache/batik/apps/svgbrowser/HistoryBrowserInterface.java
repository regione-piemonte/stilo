// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.apps.svgbrowser;

import org.apache.batik.dom.util.DOMUtilities;
import org.w3c.dom.Element;
import java.util.ArrayList;
import org.w3c.dom.Node;

public class HistoryBrowserInterface
{
    private static final String ATTRIBUTE_ADDED_COMMAND = "Attribute added: ";
    private static final String ATTRIBUTE_REMOVED_COMMAND = "Attribute removed: ";
    private static final String ATTRIBUTE_MODIFIED_COMMAND = "Attribute modified: ";
    private static final String NODE_INSERTED_COMMAND = "Node inserted: ";
    private static final String NODE_REMOVED_COMMAND = "Node removed: ";
    private static final String CHAR_DATA_MODIFIED_COMMAND = "Node value changed: ";
    private static final String OUTER_EDIT_COMMAND = "Document changed outside DOM Viewer";
    private static final String COMPOUND_TREE_NODE_DROP = "Node moved";
    private static final String REMOVE_SELECTED_NODES = "Nodes removed";
    protected HistoryBrowser historyBrowser;
    protected AbstractCompoundCommand currentCompoundCommand;
    
    public HistoryBrowserInterface(final HistoryBrowser.CommandController commandController) {
        this.historyBrowser = new HistoryBrowser(commandController);
    }
    
    public void setCommmandController(final HistoryBrowser.CommandController commandController) {
        this.historyBrowser.setCommandController(commandController);
    }
    
    public CompoundUpdateCommand createCompoundUpdateCommand(final String s) {
        return new CompoundUpdateCommand(s);
    }
    
    public CompoundUpdateCommand createNodeChangedCommand(final Node node) {
        return new CompoundUpdateCommand(this.getNodeChangedCommandName(node));
    }
    
    public CompoundUpdateCommand createNodesDroppedCommand(final ArrayList list) {
        return new CompoundUpdateCommand("Node moved");
    }
    
    public CompoundUpdateCommand createRemoveSelectedTreeNodesCommand(final ArrayList list) {
        return new CompoundUpdateCommand("Nodes removed");
    }
    
    public void performCompoundUpdateCommand(final UndoableCommand undoableCommand) {
        this.historyBrowser.addCommand(undoableCommand);
    }
    
    public HistoryBrowser getHistoryBrowser() {
        return this.historyBrowser;
    }
    
    public void nodeInserted(final Node node, final Node node2, final Node node3) {
        this.historyBrowser.addCommand(this.createNodeInsertedCommand(node, node2, node3));
    }
    
    public NodeInsertedCommand createNodeInsertedCommand(final Node node, final Node node2, final Node node3) {
        return new NodeInsertedCommand("Node inserted: " + this.getBracketedNodeName(node3), node, node2, node3);
    }
    
    public void nodeRemoved(final Node node, final Node node2, final Node node3) {
        this.historyBrowser.addCommand(this.createNodeRemovedCommand(node, node2, node3));
    }
    
    public NodeRemovedCommand createNodeRemovedCommand(final Node node, final Node node2, final Node node3) {
        return new NodeRemovedCommand("Node removed: " + this.getBracketedNodeName(node3), node, node2, node3);
    }
    
    public void attributeAdded(final Element element, final String s, final String s2, final String s3) {
        this.historyBrowser.addCommand(this.createAttributeAddedCommand(element, s, s2, s3));
    }
    
    public AttributeAddedCommand createAttributeAddedCommand(final Element element, final String s, final String s2, final String s3) {
        return new AttributeAddedCommand("Attribute added: " + this.getBracketedNodeName(element), element, s, s2, s3);
    }
    
    public void attributeRemoved(final Element element, final String s, final String s2, final String s3) {
        this.historyBrowser.addCommand(this.createAttributeRemovedCommand(element, s, s2, s3));
    }
    
    public AttributeRemovedCommand createAttributeRemovedCommand(final Element element, final String s, final String s2, final String s3) {
        return new AttributeRemovedCommand("Attribute removed: " + this.getBracketedNodeName(element), element, s, s2, s3);
    }
    
    public void attributeModified(final Element element, final String s, final String s2, final String s3, final String s4) {
        this.historyBrowser.addCommand(this.createAttributeModifiedCommand(element, s, s2, s3, s4));
    }
    
    public AttributeModifiedCommand createAttributeModifiedCommand(final Element element, final String s, final String s2, final String s3, final String s4) {
        return new AttributeModifiedCommand("Attribute modified: " + this.getBracketedNodeName(element), element, s, s2, s3, s4);
    }
    
    public void charDataModified(final Node node, final String s, final String s2) {
        this.historyBrowser.addCommand(this.createCharDataModifiedCommand(node, s, s2));
    }
    
    public CharDataModifiedCommand createCharDataModifiedCommand(final Node node, final String s, final String s2) {
        return new CharDataModifiedCommand("Node value changed: " + this.getBracketedNodeName(node), node, s, s2);
    }
    
    public void appendChild(final Node node, final Node node2) {
        this.historyBrowser.addCommand(this.createAppendChildCommand(node, node2));
    }
    
    public AppendChildCommand createAppendChildCommand(final Node node, final Node node2) {
        return new AppendChildCommand(this.getAppendChildCommandName(node, node2), node, node2);
    }
    
    public void insertChildBefore(final Node node, final Node node2, final Node node3) {
        if (node2 == null) {
            this.historyBrowser.addCommand(this.createAppendChildCommand(node, node3));
        }
        else {
            this.historyBrowser.addCommand(this.createInsertNodeBeforeCommand(node, node2, node3));
        }
    }
    
    public UndoableCommand createInsertChildCommand(final Node node, final Node node2, final Node node3) {
        if (node2 == null) {
            return this.createAppendChildCommand(node, node3);
        }
        return this.createInsertNodeBeforeCommand(node, node2, node3);
    }
    
    public InsertNodeBeforeCommand createInsertNodeBeforeCommand(final Node node, final Node node2, final Node node3) {
        return new InsertNodeBeforeCommand(this.getInsertBeforeCommandName(node, node3, node2), node, node2, node3);
    }
    
    public void replaceChild(final Node node, final Node node2, final Node node3) {
    }
    
    public void removeChild(final Node node, final Node node2) {
        this.historyBrowser.addCommand(this.createRemoveChildCommand(node, node2));
    }
    
    public RemoveChildCommand createRemoveChildCommand(final Node node, final Node node2) {
        return new RemoveChildCommand(this.getRemoveChildCommandName(node, node2), node, node2);
    }
    
    public void setNodeValue(final Node node, final String s) {
        this.historyBrowser.addCommand(this.createChangeNodeValueCommand(node, s));
    }
    
    public ChangeNodeValueCommand createChangeNodeValueCommand(final Node node, final String s) {
        return new ChangeNodeValueCommand(this.getChangeNodeValueCommandName(node, s), node, s);
    }
    
    public AbstractCompoundCommand getCurrentCompoundCommand() {
        if (this.currentCompoundCommand == null) {
            this.currentCompoundCommand = this.createCompoundUpdateCommand("Document changed outside DOM Viewer");
        }
        return this.currentCompoundCommand;
    }
    
    public void addToCurrentCompoundCommand(final AbstractUndoableCommand abstractUndoableCommand) {
        this.getCurrentCompoundCommand().addCommand(abstractUndoableCommand);
        this.historyBrowser.fireDoCompoundEdit(new HistoryBrowser.HistoryBrowserEvent(this.getCurrentCompoundCommand()));
    }
    
    public void performCurrentCompoundCommand() {
        if (this.getCurrentCompoundCommand().getCommandNumber() > 0) {
            this.historyBrowser.addCommand(this.getCurrentCompoundCommand());
            this.historyBrowser.fireCompoundEditPerformed(new HistoryBrowser.HistoryBrowserEvent(this.currentCompoundCommand));
            this.currentCompoundCommand = null;
        }
    }
    
    private String getNodeAsString(final Node node) {
        String attributeNS = "";
        if (node.getNodeType() == 1) {
            attributeNS = ((Element)node).getAttributeNS(null, "id");
        }
        if (attributeNS.length() != 0) {
            return node.getNodeName() + " \"" + attributeNS + "\"";
        }
        return node.getNodeName();
    }
    
    private String getBracketedNodeName(final Node node) {
        return "(" + this.getNodeAsString(node) + ")";
    }
    
    private String getAppendChildCommandName(final Node node, final Node node2) {
        return "Append " + this.getNodeAsString(node2) + " to " + this.getNodeAsString(node);
    }
    
    private String getInsertBeforeCommandName(final Node node, final Node node2, final Node node3) {
        return "Insert " + this.getNodeAsString(node2) + " to " + this.getNodeAsString(node) + " before " + this.getNodeAsString(node3);
    }
    
    private String getRemoveChildCommandName(final Node node, final Node node2) {
        return "Remove " + this.getNodeAsString(node2) + " from " + this.getNodeAsString(node);
    }
    
    private String getChangeNodeValueCommandName(final Node node, final String str) {
        return "Change " + this.getNodeAsString(node) + " value to " + str;
    }
    
    private String getNodeChangedCommandName(final Node node) {
        return "Node " + this.getNodeAsString(node) + " changed";
    }
    
    public static class ChangeNodeValueCommand extends AbstractUndoableCommand
    {
        protected Node contextNode;
        protected String newValue;
        
        public ChangeNodeValueCommand(final String name, final Node contextNode, final String newValue) {
            this.setName(name);
            this.contextNode = contextNode;
            this.newValue = newValue;
        }
        
        public void execute() {
            final String nodeValue = this.contextNode.getNodeValue();
            this.contextNode.setNodeValue(this.newValue);
            this.newValue = nodeValue;
        }
        
        public void undo() {
            this.execute();
        }
        
        public void redo() {
            this.execute();
        }
        
        public boolean shouldExecute() {
            return this.contextNode != null;
        }
    }
    
    public static class RemoveChildCommand extends AbstractUndoableCommand
    {
        protected Node parentNode;
        protected Node childNode;
        protected int indexInChildrenArray;
        
        public RemoveChildCommand(final String name, final Node parentNode, final Node childNode) {
            this.setName(name);
            this.parentNode = parentNode;
            this.childNode = childNode;
        }
        
        public void execute() {
            this.indexInChildrenArray = DOMUtilities.getChildIndex(this.childNode, this.parentNode);
            this.parentNode.removeChild(this.childNode);
        }
        
        public void undo() {
            this.parentNode.insertBefore(this.childNode, this.parentNode.getChildNodes().item(this.indexInChildrenArray));
        }
        
        public void redo() {
            this.parentNode.removeChild(this.childNode);
        }
        
        public boolean shouldExecute() {
            return this.parentNode != null && this.childNode != null;
        }
    }
    
    public static class ReplaceChildCommand extends AbstractUndoableCommand
    {
        protected Node oldParent;
        protected Node oldNextSibling;
        protected Node newNextSibling;
        protected Node parent;
        protected Node child;
        
        public ReplaceChildCommand(final String name, final Node parent, final Node newNextSibling, final Node child) {
            this.setName(name);
            this.oldParent = child.getParentNode();
            this.oldNextSibling = child.getNextSibling();
            this.parent = parent;
            this.child = child;
            this.newNextSibling = newNextSibling;
        }
        
        public void execute() {
            if (this.newNextSibling != null) {
                this.parent.insertBefore(this.child, this.newNextSibling);
            }
            else {
                this.parent.appendChild(this.child);
            }
        }
        
        public void undo() {
            if (this.oldParent != null) {
                this.oldParent.insertBefore(this.child, this.oldNextSibling);
            }
            else {
                this.parent.removeChild(this.child);
            }
        }
        
        public void redo() {
            this.execute();
        }
        
        public boolean shouldExecute() {
            return this.parent != null && this.child != null;
        }
    }
    
    public static class InsertNodeBeforeCommand extends AbstractUndoableCommand
    {
        protected Node oldParent;
        protected Node oldNextSibling;
        protected Node newNextSibling;
        protected Node parent;
        protected Node child;
        
        public InsertNodeBeforeCommand(final String name, final Node parent, final Node newNextSibling, final Node child) {
            this.setName(name);
            this.oldParent = child.getParentNode();
            this.oldNextSibling = child.getNextSibling();
            this.parent = parent;
            this.child = child;
            this.newNextSibling = newNextSibling;
        }
        
        public void execute() {
            if (this.newNextSibling != null) {
                this.parent.insertBefore(this.child, this.newNextSibling);
            }
            else {
                this.parent.appendChild(this.child);
            }
        }
        
        public void undo() {
            if (this.oldParent != null) {
                this.oldParent.insertBefore(this.child, this.oldNextSibling);
            }
            else {
                this.parent.removeChild(this.child);
            }
        }
        
        public void redo() {
            this.execute();
        }
        
        public boolean shouldExecute() {
            return this.parent != null && this.child != null;
        }
    }
    
    public static class AppendChildCommand extends AbstractUndoableCommand
    {
        protected Node oldParentNode;
        protected Node oldNextSibling;
        protected Node parentNode;
        protected Node childNode;
        
        public AppendChildCommand(final String name, final Node parentNode, final Node childNode) {
            this.setName(name);
            this.oldParentNode = childNode.getParentNode();
            this.oldNextSibling = childNode.getNextSibling();
            this.parentNode = parentNode;
            this.childNode = childNode;
        }
        
        public void execute() {
            this.parentNode.appendChild(this.childNode);
        }
        
        public void undo() {
            if (this.oldParentNode != null) {
                this.oldParentNode.insertBefore(this.childNode, this.oldNextSibling);
            }
            else {
                this.parentNode.removeChild(this.childNode);
            }
        }
        
        public void redo() {
            this.execute();
        }
        
        public boolean shouldExecute() {
            return this.parentNode != null && this.childNode != null;
        }
    }
    
    public static class CharDataModifiedCommand extends AbstractUndoableCommand
    {
        protected Node contextNode;
        protected String oldValue;
        protected String newValue;
        
        public CharDataModifiedCommand(final String name, final Node contextNode, final String oldValue, final String newValue) {
            this.setName(name);
            this.contextNode = contextNode;
            this.oldValue = oldValue;
            this.newValue = newValue;
        }
        
        public void execute() {
        }
        
        public void undo() {
            this.contextNode.setNodeValue(this.oldValue);
        }
        
        public void redo() {
            this.contextNode.setNodeValue(this.newValue);
        }
        
        public boolean shouldExecute() {
            return this.contextNode != null;
        }
    }
    
    public static class AttributeModifiedCommand extends AbstractUndoableCommand
    {
        protected Element contextElement;
        protected String attributeName;
        protected String prevAttributeValue;
        protected String newAttributeValue;
        protected String namespaceURI;
        
        public AttributeModifiedCommand(final String name, final Element contextElement, final String attributeName, final String prevAttributeValue, final String newAttributeValue, final String namespaceURI) {
            this.setName(name);
            this.contextElement = contextElement;
            this.attributeName = attributeName;
            this.prevAttributeValue = prevAttributeValue;
            this.newAttributeValue = newAttributeValue;
            this.namespaceURI = namespaceURI;
        }
        
        public void execute() {
        }
        
        public void undo() {
            this.contextElement.setAttributeNS(this.namespaceURI, this.attributeName, this.prevAttributeValue);
        }
        
        public void redo() {
            this.contextElement.setAttributeNS(this.namespaceURI, this.attributeName, this.newAttributeValue);
        }
        
        public boolean shouldExecute() {
            return this.contextElement != null && this.attributeName.length() != 0;
        }
    }
    
    public static class AttributeRemovedCommand extends AbstractUndoableCommand
    {
        protected Element contextElement;
        protected String attributeName;
        protected String prevValue;
        protected String namespaceURI;
        
        public AttributeRemovedCommand(final String name, final Element contextElement, final String attributeName, final String prevValue, final String namespaceURI) {
            this.setName(name);
            this.contextElement = contextElement;
            this.attributeName = attributeName;
            this.prevValue = prevValue;
            this.namespaceURI = namespaceURI;
        }
        
        public void execute() {
        }
        
        public void undo() {
            this.contextElement.setAttributeNS(this.namespaceURI, this.attributeName, this.prevValue);
        }
        
        public void redo() {
            this.contextElement.removeAttributeNS(this.namespaceURI, this.attributeName);
        }
        
        public boolean shouldExecute() {
            return this.contextElement != null && this.attributeName.length() != 0;
        }
    }
    
    public static class AttributeAddedCommand extends AbstractUndoableCommand
    {
        protected Element contextElement;
        protected String attributeName;
        protected String newValue;
        protected String namespaceURI;
        
        public AttributeAddedCommand(final String name, final Element contextElement, final String attributeName, final String newValue, final String namespaceURI) {
            this.setName(name);
            this.contextElement = contextElement;
            this.attributeName = attributeName;
            this.newValue = newValue;
            this.namespaceURI = namespaceURI;
        }
        
        public void execute() {
        }
        
        public void undo() {
            this.contextElement.removeAttributeNS(this.namespaceURI, this.attributeName);
        }
        
        public void redo() {
            this.contextElement.setAttributeNS(this.namespaceURI, this.attributeName, this.newValue);
        }
        
        public boolean shouldExecute() {
            return this.contextElement != null && this.attributeName.length() != 0;
        }
    }
    
    public static class NodeRemovedCommand extends AbstractUndoableCommand
    {
        protected Node oldSibling;
        protected Node oldParent;
        protected Node contextNode;
        
        public NodeRemovedCommand(final String name, final Node oldParent, final Node oldSibling, final Node contextNode) {
            this.setName(name);
            this.oldParent = oldParent;
            this.contextNode = contextNode;
            this.oldSibling = oldSibling;
        }
        
        public void execute() {
        }
        
        public void undo() {
            if (this.oldSibling != null) {
                this.oldParent.insertBefore(this.contextNode, this.oldSibling);
            }
            else {
                this.oldParent.appendChild(this.contextNode);
            }
        }
        
        public void redo() {
            this.oldParent.removeChild(this.contextNode);
        }
        
        public boolean shouldExecute() {
            return this.oldParent != null && this.contextNode != null;
        }
    }
    
    public static class NodeInsertedCommand extends AbstractUndoableCommand
    {
        protected Node newSibling;
        protected Node newParent;
        protected Node contextNode;
        
        public NodeInsertedCommand(final String name, final Node newParent, final Node newSibling, final Node contextNode) {
            this.setName(name);
            this.newParent = newParent;
            this.contextNode = contextNode;
            this.newSibling = newSibling;
        }
        
        public void execute() {
        }
        
        public void undo() {
            this.newParent.removeChild(this.contextNode);
        }
        
        public void redo() {
            if (this.newSibling != null) {
                this.newParent.insertBefore(this.contextNode, this.newSibling);
            }
            else {
                this.newParent.appendChild(this.contextNode);
            }
        }
        
        public boolean shouldExecute() {
            return this.newParent != null && this.contextNode != null;
        }
    }
    
    public static class CompoundUpdateCommand extends AbstractCompoundCommand
    {
        public CompoundUpdateCommand(final String name) {
            this.setName(name);
        }
    }
}
