// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.dom.traversal;

import org.apache.batik.dom.AbstractNode;
import org.w3c.dom.traversal.NodeFilter;
import org.w3c.dom.Node;
import org.w3c.dom.traversal.TreeWalker;

public class DOMTreeWalker implements TreeWalker
{
    protected Node root;
    protected int whatToShow;
    protected NodeFilter filter;
    protected boolean expandEntityReferences;
    protected Node currentNode;
    
    public DOMTreeWalker(final Node root, final int whatToShow, final NodeFilter filter, final boolean expandEntityReferences) {
        this.root = root;
        this.whatToShow = whatToShow;
        this.filter = filter;
        this.expandEntityReferences = expandEntityReferences;
        this.currentNode = this.root;
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
    
    public Node getCurrentNode() {
        return this.currentNode;
    }
    
    public void setCurrentNode(final Node currentNode) {
        if (currentNode == null) {
            throw ((AbstractNode)this.root).createDOMException((short)9, "null.current.node", null);
        }
        this.currentNode = currentNode;
    }
    
    public Node parentNode() {
        final Node parentNode = this.parentNode(this.currentNode);
        if (parentNode != null) {
            this.currentNode = parentNode;
        }
        return parentNode;
    }
    
    public Node firstChild() {
        final Node firstChild = this.firstChild(this.currentNode);
        if (firstChild != null) {
            this.currentNode = firstChild;
        }
        return firstChild;
    }
    
    public Node lastChild() {
        final Node lastChild = this.lastChild(this.currentNode);
        if (lastChild != null) {
            this.currentNode = lastChild;
        }
        return lastChild;
    }
    
    public Node previousSibling() {
        final Node previousSibling = this.previousSibling(this.currentNode, this.root);
        if (previousSibling != null) {
            this.currentNode = previousSibling;
        }
        return previousSibling;
    }
    
    public Node nextSibling() {
        final Node nextSibling = this.nextSibling(this.currentNode, this.root);
        if (nextSibling != null) {
            this.currentNode = nextSibling;
        }
        return nextSibling;
    }
    
    public Node previousNode() {
        final Node previousSibling = this.previousSibling(this.currentNode, this.root);
        if (previousSibling == null) {
            final Node parentNode = this.parentNode(this.currentNode);
            if (parentNode != null) {
                this.currentNode = parentNode;
            }
            return parentNode;
        }
        Node lastChild2;
        for (Node lastChild = lastChild2 = this.lastChild(previousSibling); lastChild != null; lastChild = this.lastChild(lastChild2)) {
            lastChild2 = lastChild;
        }
        return this.currentNode = ((lastChild2 != null) ? lastChild2 : previousSibling);
    }
    
    public Node nextNode() {
        final Node firstChild;
        if ((firstChild = this.firstChild(this.currentNode)) != null) {
            return this.currentNode = firstChild;
        }
        final Node nextSibling;
        if ((nextSibling = this.nextSibling(this.currentNode, this.root)) != null) {
            return this.currentNode = nextSibling;
        }
        Node node = this.currentNode;
        Node nextSibling2;
        do {
            node = this.parentNode(node);
            if (node == null) {
                return null;
            }
        } while ((nextSibling2 = this.nextSibling(node, this.root)) == null);
        return this.currentNode = nextSibling2;
    }
    
    protected Node parentNode(final Node node) {
        if (node == this.root) {
            return null;
        }
        Node parentNode = node;
        do {
            parentNode = parentNode.getParentNode();
            if (parentNode == null) {
                return null;
            }
        } while ((this.whatToShow & 1 << parentNode.getNodeType() - 1) == 0x0 || (this.filter != null && this.filter.acceptNode(parentNode) != 1));
        return parentNode;
    }
    
    protected Node firstChild(final Node node) {
        if (node.getNodeType() == 5 && !this.expandEntityReferences) {
            return null;
        }
        final Node firstChild = node.getFirstChild();
        if (firstChild == null) {
            return null;
        }
        switch (this.acceptNode(firstChild)) {
            case 1: {
                return firstChild;
            }
            case 3: {
                final Node firstChild2 = this.firstChild(firstChild);
                if (firstChild2 != null) {
                    return firstChild2;
                }
                break;
            }
        }
        return this.nextSibling(firstChild, node);
    }
    
    protected Node lastChild(final Node node) {
        if (node.getNodeType() == 5 && !this.expandEntityReferences) {
            return null;
        }
        final Node lastChild = node.getLastChild();
        if (lastChild == null) {
            return null;
        }
        switch (this.acceptNode(lastChild)) {
            case 1: {
                return lastChild;
            }
            case 3: {
                final Node lastChild2 = this.lastChild(lastChild);
                if (lastChild2 != null) {
                    return lastChild2;
                }
                break;
            }
        }
        return this.previousSibling(lastChild, node);
    }
    
    protected Node previousSibling(Node node, final Node node2) {
        while (node != node2) {
            final Node previousSibling = node.getPreviousSibling();
            if (previousSibling == null) {
                final Node parentNode = node.getParentNode();
                if (parentNode == null || parentNode == node2) {
                    return null;
                }
                if (this.acceptNode(parentNode) != 3) {
                    return null;
                }
                node = parentNode;
            }
            else {
                switch (this.acceptNode(previousSibling)) {
                    case 1: {
                        return previousSibling;
                    }
                    case 3: {
                        final Node lastChild = this.lastChild(previousSibling);
                        if (lastChild != null) {
                            return lastChild;
                        }
                        break;
                    }
                }
                node = previousSibling;
            }
        }
        return null;
    }
    
    protected Node nextSibling(Node node, final Node node2) {
        while (node != node2) {
            final Node nextSibling = node.getNextSibling();
            if (nextSibling == null) {
                final Node parentNode = node.getParentNode();
                if (parentNode == null || parentNode == node2) {
                    return null;
                }
                if (this.acceptNode(parentNode) != 3) {
                    return null;
                }
                node = parentNode;
            }
            else {
                switch (this.acceptNode(nextSibling)) {
                    case 1: {
                        return nextSibling;
                    }
                    case 3: {
                        final Node firstChild = this.firstChild(nextSibling);
                        if (firstChild != null) {
                            return firstChild;
                        }
                        break;
                    }
                }
                node = nextSibling;
            }
        }
        return null;
    }
    
    protected short acceptNode(final Node node) {
        if ((this.whatToShow & 1 << node.getNodeType() - 1) == 0x0) {
            return 3;
        }
        if (this.filter == null) {
            return 1;
        }
        return this.filter.acceptNode(node);
    }
}
