// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.dom.xbl;

import org.apache.batik.dom.AbstractNode;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;

public class GenericXBLManager implements XBLManager
{
    protected boolean isProcessing;
    
    public void startProcessing() {
        this.isProcessing = true;
    }
    
    public void stopProcessing() {
        this.isProcessing = false;
    }
    
    public boolean isProcessing() {
        return this.isProcessing;
    }
    
    public Node getXblParentNode(final Node node) {
        return node.getParentNode();
    }
    
    public NodeList getXblChildNodes(final Node node) {
        return node.getChildNodes();
    }
    
    public NodeList getXblScopedChildNodes(final Node node) {
        return node.getChildNodes();
    }
    
    public Node getXblFirstChild(final Node node) {
        return node.getFirstChild();
    }
    
    public Node getXblLastChild(final Node node) {
        return node.getLastChild();
    }
    
    public Node getXblPreviousSibling(final Node node) {
        return node.getPreviousSibling();
    }
    
    public Node getXblNextSibling(final Node node) {
        return node.getNextSibling();
    }
    
    public Element getXblFirstElementChild(final Node node) {
        Node node2;
        for (node2 = node.getFirstChild(); node2 != null && node2.getNodeType() != 1; node2 = node2.getNextSibling()) {}
        return (Element)node2;
    }
    
    public Element getXblLastElementChild(final Node node) {
        Node node2;
        for (node2 = node.getLastChild(); node2 != null && node2.getNodeType() != 1; node2 = node2.getPreviousSibling()) {}
        return (Element)node2;
    }
    
    public Element getXblPreviousElementSibling(final Node node) {
        Node previousSibling = node;
        do {
            previousSibling = previousSibling.getPreviousSibling();
        } while (previousSibling != null && previousSibling.getNodeType() != 1);
        return (Element)previousSibling;
    }
    
    public Element getXblNextElementSibling(final Node node) {
        Node nextSibling = node;
        do {
            nextSibling = nextSibling.getNextSibling();
        } while (nextSibling != null && nextSibling.getNodeType() != 1);
        return (Element)nextSibling;
    }
    
    public Element getXblBoundElement(final Node node) {
        return null;
    }
    
    public Element getXblShadowTree(final Node node) {
        return null;
    }
    
    public NodeList getXblDefinitions(final Node node) {
        return AbstractNode.EMPTY_NODE_LIST;
    }
}
