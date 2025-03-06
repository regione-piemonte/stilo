// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.dom.traversal;

import org.w3c.dom.traversal.NodeFilter;
import org.w3c.dom.Node;
import org.apache.batik.dom.AbstractDocument;
import org.w3c.dom.traversal.NodeIterator;

public class DOMNodeIterator implements NodeIterator
{
    protected static final short INITIAL = 0;
    protected static final short INVALID = 1;
    protected static final short FORWARD = 2;
    protected static final short BACKWARD = 3;
    protected AbstractDocument document;
    protected Node root;
    protected int whatToShow;
    protected NodeFilter filter;
    protected boolean expandEntityReferences;
    protected short state;
    protected Node referenceNode;
    
    public DOMNodeIterator(final AbstractDocument document, final Node root, final int whatToShow, final NodeFilter filter, final boolean expandEntityReferences) {
        this.document = document;
        this.root = root;
        this.whatToShow = whatToShow;
        this.filter = filter;
        this.expandEntityReferences = expandEntityReferences;
        this.referenceNode = this.root;
    }
    
    public Node getRoot() {
        return this.root;
    }
    
    public int getWhatToShow() {
        return this.whatToShow;
    }
    
    public NodeFilter getFilter() {
        return this.filter;
    }
    
    public boolean getExpandEntityReferences() {
        return this.expandEntityReferences;
    }
    
    public Node nextNode() {
        switch (this.state) {
            case 1: {
                throw this.document.createDOMException((short)11, "detached.iterator", null);
            }
            case 0:
            case 3: {
                this.state = 2;
                return this.referenceNode;
            }
            default: {
                do {
                    this.unfilteredNextNode();
                    if (this.referenceNode == null) {
                        return null;
                    }
                } while ((this.whatToShow & 1 << this.referenceNode.getNodeType() - 1) == 0x0 || (this.filter != null && this.filter.acceptNode(this.referenceNode) != 1));
                return this.referenceNode;
            }
        }
    }
    
    public Node previousNode() {
        switch (this.state) {
            case 1: {
                throw this.document.createDOMException((short)11, "detached.iterator", null);
            }
            case 0:
            case 2: {
                this.state = 3;
                return this.referenceNode;
            }
            default: {
                do {
                    this.unfilteredPreviousNode();
                    if (this.referenceNode == null) {
                        return this.referenceNode;
                    }
                } while ((this.whatToShow & 1 << this.referenceNode.getNodeType() - 1) == 0x0 || (this.filter != null && this.filter.acceptNode(this.referenceNode) != 1));
                return this.referenceNode;
            }
        }
    }
    
    public void detach() {
        this.state = 1;
        this.document.detachNodeIterator(this);
    }
    
    public void nodeToBeRemoved(final Node node) {
        if (this.state == 1) {
            return;
        }
        Node node2;
        for (node2 = this.referenceNode; node2 != null && node2 != this.root && node2 != node; node2 = node2.getParentNode()) {}
        if (node2 == null || node2 == this.root) {
            return;
        }
        if (this.state == 3) {
            if (node2.getNodeType() != 5 || this.expandEntityReferences) {
                final Node firstChild = node2.getFirstChild();
                if (firstChild != null) {
                    this.referenceNode = firstChild;
                    return;
                }
            }
            final Node nextSibling = node2.getNextSibling();
            if (nextSibling != null) {
                this.referenceNode = nextSibling;
                return;
            }
            Node parentNode = node2;
            while ((parentNode = parentNode.getParentNode()) != null && parentNode != this.root) {
                final Node nextSibling2 = parentNode.getNextSibling();
                if (nextSibling2 != null) {
                    this.referenceNode = nextSibling2;
                    return;
                }
            }
            this.referenceNode = null;
        }
        else {
            Node previousSibling = node2.getPreviousSibling();
            if (previousSibling == null) {
                this.referenceNode = node2.getParentNode();
                return;
            }
            if (previousSibling.getNodeType() != 5 || this.expandEntityReferences) {
                Node lastChild;
                while ((lastChild = previousSibling.getLastChild()) != null) {
                    previousSibling = lastChild;
                }
            }
            this.referenceNode = previousSibling;
        }
    }
    
    protected void unfilteredNextNode() {
        if (this.referenceNode == null) {
            return;
        }
        if (this.referenceNode.getNodeType() != 5 || this.expandEntityReferences) {
            final Node firstChild = this.referenceNode.getFirstChild();
            if (firstChild != null) {
                this.referenceNode = firstChild;
                return;
            }
        }
        final Node nextSibling = this.referenceNode.getNextSibling();
        if (nextSibling != null) {
            this.referenceNode = nextSibling;
            return;
        }
        Node node = this.referenceNode;
        while ((node = node.getParentNode()) != null && node != this.root) {
            final Node nextSibling2 = node.getNextSibling();
            if (nextSibling2 != null) {
                this.referenceNode = nextSibling2;
                return;
            }
        }
        this.referenceNode = null;
    }
    
    protected void unfilteredPreviousNode() {
        if (this.referenceNode == null) {
            return;
        }
        if (this.referenceNode == this.root) {
            this.referenceNode = null;
            return;
        }
        Node previousSibling = this.referenceNode.getPreviousSibling();
        if (previousSibling == null) {
            this.referenceNode = this.referenceNode.getParentNode();
            return;
        }
        if (previousSibling.getNodeType() != 5 || this.expandEntityReferences) {
            Node lastChild;
            while ((lastChild = previousSibling.getLastChild()) != null) {
                previousSibling = lastChild;
            }
        }
        this.referenceNode = previousSibling;
    }
}
