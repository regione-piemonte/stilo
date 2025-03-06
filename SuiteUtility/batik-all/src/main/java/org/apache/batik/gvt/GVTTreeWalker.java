// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.gvt;

import java.util.List;

public class GVTTreeWalker
{
    protected GraphicsNode gvtRoot;
    protected GraphicsNode treeRoot;
    protected GraphicsNode currentNode;
    
    public GVTTreeWalker(final GraphicsNode graphicsNode) {
        this.gvtRoot = graphicsNode.getRoot();
        this.treeRoot = graphicsNode;
        this.currentNode = graphicsNode;
    }
    
    public GraphicsNode getRoot() {
        return this.treeRoot;
    }
    
    public GraphicsNode getGVTRoot() {
        return this.gvtRoot;
    }
    
    public void setCurrentGraphicsNode(final GraphicsNode graphicsNode) {
        if (graphicsNode.getRoot() != this.gvtRoot) {
            throw new IllegalArgumentException("The node " + graphicsNode + " is not part of the document " + this.gvtRoot);
        }
        this.currentNode = graphicsNode;
    }
    
    public GraphicsNode getCurrentGraphicsNode() {
        return this.currentNode;
    }
    
    public GraphicsNode previousGraphicsNode() {
        final GraphicsNode previousGraphicsNode = this.getPreviousGraphicsNode(this.currentNode);
        if (previousGraphicsNode != null) {
            this.currentNode = previousGraphicsNode;
        }
        return previousGraphicsNode;
    }
    
    public GraphicsNode nextGraphicsNode() {
        final GraphicsNode nextGraphicsNode = this.getNextGraphicsNode(this.currentNode);
        if (nextGraphicsNode != null) {
            this.currentNode = nextGraphicsNode;
        }
        return nextGraphicsNode;
    }
    
    public GraphicsNode parentGraphicsNode() {
        if (this.currentNode == this.treeRoot) {
            return null;
        }
        final CompositeGraphicsNode parent = this.currentNode.getParent();
        if (parent != null) {
            this.currentNode = parent;
        }
        return parent;
    }
    
    public GraphicsNode getNextSibling() {
        final GraphicsNode nextSibling = getNextSibling(this.currentNode);
        if (nextSibling != null) {
            this.currentNode = nextSibling;
        }
        return nextSibling;
    }
    
    public GraphicsNode getPreviousSibling() {
        final GraphicsNode previousSibling = getPreviousSibling(this.currentNode);
        if (previousSibling != null) {
            this.currentNode = previousSibling;
        }
        return previousSibling;
    }
    
    public GraphicsNode firstChild() {
        final GraphicsNode firstChild = getFirstChild(this.currentNode);
        if (firstChild != null) {
            this.currentNode = firstChild;
        }
        return firstChild;
    }
    
    public GraphicsNode lastChild() {
        final GraphicsNode lastChild = getLastChild(this.currentNode);
        if (lastChild != null) {
            this.currentNode = lastChild;
        }
        return lastChild;
    }
    
    protected GraphicsNode getNextGraphicsNode(final GraphicsNode graphicsNode) {
        if (graphicsNode == null) {
            return null;
        }
        final GraphicsNode firstChild = getFirstChild(graphicsNode);
        if (firstChild != null) {
            return firstChild;
        }
        final GraphicsNode nextSibling = getNextSibling(graphicsNode);
        if (nextSibling != null) {
            return nextSibling;
        }
        GraphicsNode parent = graphicsNode;
        while ((parent = parent.getParent()) != null && parent != this.treeRoot) {
            final GraphicsNode nextSibling2 = getNextSibling(parent);
            if (nextSibling2 != null) {
                return nextSibling2;
            }
        }
        return null;
    }
    
    protected GraphicsNode getPreviousGraphicsNode(final GraphicsNode graphicsNode) {
        if (graphicsNode == null) {
            return null;
        }
        if (graphicsNode == this.treeRoot) {
            return null;
        }
        GraphicsNode previousSibling = getPreviousSibling(graphicsNode);
        if (previousSibling == null) {
            return graphicsNode.getParent();
        }
        GraphicsNode lastChild;
        while ((lastChild = getLastChild(previousSibling)) != null) {
            previousSibling = lastChild;
        }
        return previousSibling;
    }
    
    protected static GraphicsNode getLastChild(final GraphicsNode graphicsNode) {
        if (!(graphicsNode instanceof CompositeGraphicsNode)) {
            return null;
        }
        final List children = ((CompositeGraphicsNode)graphicsNode).getChildren();
        if (children == null) {
            return null;
        }
        if (children.size() >= 1) {
            return children.get(children.size() - 1);
        }
        return null;
    }
    
    protected static GraphicsNode getPreviousSibling(final GraphicsNode graphicsNode) {
        final CompositeGraphicsNode parent = graphicsNode.getParent();
        if (parent == null) {
            return null;
        }
        final List children = parent.getChildren();
        if (children == null) {
            return null;
        }
        final int index = children.indexOf(graphicsNode);
        if (index - 1 >= 0) {
            return children.get(index - 1);
        }
        return null;
    }
    
    protected static GraphicsNode getFirstChild(final GraphicsNode graphicsNode) {
        if (!(graphicsNode instanceof CompositeGraphicsNode)) {
            return null;
        }
        final List children = ((CompositeGraphicsNode)graphicsNode).getChildren();
        if (children == null) {
            return null;
        }
        if (children.size() >= 1) {
            return children.get(0);
        }
        return null;
    }
    
    protected static GraphicsNode getNextSibling(final GraphicsNode graphicsNode) {
        final CompositeGraphicsNode parent = graphicsNode.getParent();
        if (parent == null) {
            return null;
        }
        final List children = parent.getChildren();
        if (children == null) {
            return null;
        }
        final int index = children.indexOf(graphicsNode);
        if (index + 1 < children.size()) {
            return children.get(index + 1);
        }
        return null;
    }
}
