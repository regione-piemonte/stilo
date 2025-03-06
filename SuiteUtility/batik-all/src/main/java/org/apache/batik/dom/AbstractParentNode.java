// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.dom;

import java.io.Serializable;
import org.w3c.dom.events.Event;
import org.apache.batik.dom.events.DOMMutationEvent;
import org.w3c.dom.DOMException;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public abstract class AbstractParentNode extends AbstractNode
{
    protected ChildNodes childNodes;
    
    public NodeList getChildNodes() {
        return (this.childNodes == null) ? (this.childNodes = new ChildNodes()) : this.childNodes;
    }
    
    public Node getFirstChild() {
        return (this.childNodes == null) ? null : this.childNodes.firstChild;
    }
    
    public Node getLastChild() {
        return (this.childNodes == null) ? null : this.childNodes.lastChild;
    }
    
    public Node insertBefore(final Node node, final Node node2) throws DOMException {
        if (node2 != null && (this.childNodes == null || node2.getParentNode() != this)) {
            throw this.createDOMException((short)8, "child.missing", new Object[] { new Integer(node2.getNodeType()), node2.getNodeName() });
        }
        this.checkAndRemove(node, false);
        if (node.getNodeType() == 11) {
            Node nextSibling;
            for (Node firstChild = node.getFirstChild(); firstChild != null; firstChild = nextSibling) {
                nextSibling = firstChild.getNextSibling();
                this.insertBefore(firstChild, node2);
            }
            return node;
        }
        if (this.childNodes == null) {
            this.childNodes = new ChildNodes();
        }
        final ExtendedNode insert = this.childNodes.insert((ExtendedNode)node, (ExtendedNode)node2);
        insert.setParentNode(this);
        this.nodeAdded(insert);
        this.fireDOMNodeInsertedEvent(insert);
        this.fireDOMSubtreeModifiedEvent();
        return insert;
    }
    
    public Node replaceChild(final Node node, final Node node2) throws DOMException {
        if (this.childNodes == null || node2.getParentNode() != this) {
            throw this.createDOMException((short)8, "child.missing", new Object[] { new Integer(node2.getNodeType()), node2.getNodeName() });
        }
        this.checkAndRemove(node, true);
        if (node.getNodeType() != 11) {
            this.fireDOMNodeRemovedEvent(node2);
            this.getCurrentDocument().nodeToBeRemoved(node2);
            this.nodeToBeRemoved(node2);
            final ExtendedNode extendedNode = (ExtendedNode)node;
            final ExtendedNode replace = this.childNodes.replace(extendedNode, (ExtendedNode)node2);
            extendedNode.setParentNode(this);
            replace.setParentNode(null);
            this.nodeAdded(extendedNode);
            this.fireDOMNodeInsertedEvent(extendedNode);
            this.fireDOMSubtreeModifiedEvent();
            return extendedNode;
        }
        final Node lastChild = node.getLastChild();
        if (lastChild == null) {
            return node;
        }
        final Node previousSibling = lastChild.getPreviousSibling();
        this.replaceChild(lastChild, node2);
        Node node3 = lastChild;
        Node previousSibling2;
        for (Node node4 = previousSibling; node4 != null; node4 = previousSibling2) {
            previousSibling2 = node4.getPreviousSibling();
            this.insertBefore(node4, node3);
            node3 = node4;
        }
        return node;
    }
    
    public Node removeChild(final Node node) throws DOMException {
        if (this.childNodes == null || node.getParentNode() != this) {
            throw this.createDOMException((short)8, "child.missing", new Object[] { new Integer(node.getNodeType()), node.getNodeName() });
        }
        if (this.isReadonly()) {
            throw this.createDOMException((short)7, "readonly.node", new Object[] { new Integer(this.getNodeType()), this.getNodeName() });
        }
        this.fireDOMNodeRemovedEvent(node);
        this.getCurrentDocument().nodeToBeRemoved(node);
        this.nodeToBeRemoved(node);
        final ExtendedNode remove = this.childNodes.remove((ExtendedNode)node);
        remove.setParentNode(null);
        this.fireDOMSubtreeModifiedEvent();
        return remove;
    }
    
    public Node appendChild(final Node node) throws DOMException {
        this.checkAndRemove(node, false);
        if (node.getNodeType() == 11) {
            Node nextSibling;
            for (Node firstChild = node.getFirstChild(); firstChild != null; firstChild = nextSibling) {
                nextSibling = firstChild.getNextSibling();
                this.appendChild(firstChild);
            }
            return node;
        }
        if (this.childNodes == null) {
            this.childNodes = new ChildNodes();
        }
        final ExtendedNode append = this.childNodes.append((ExtendedNode)node);
        append.setParentNode(this);
        this.nodeAdded(append);
        this.fireDOMNodeInsertedEvent(append);
        this.fireDOMSubtreeModifiedEvent();
        return append;
    }
    
    public boolean hasChildNodes() {
        return this.childNodes != null && this.childNodes.getLength() != 0;
    }
    
    public void normalize() {
        Node firstChild = this.getFirstChild();
        if (firstChild != null) {
            firstChild.normalize();
            Node node = firstChild.getNextSibling();
            while (node != null) {
                if (firstChild.getNodeType() == 3 && node.getNodeType() == 3) {
                    ((AbstractText)firstChild).setNodeValue(firstChild.getNodeValue() + node.getNodeValue());
                    this.removeChild(node);
                    node = firstChild.getNextSibling();
                }
                else {
                    node.normalize();
                    firstChild = node;
                    node = node.getNextSibling();
                }
            }
        }
    }
    
    public NodeList getElementsByTagName(final String s) {
        if (s == null) {
            return AbstractParentNode.EMPTY_NODE_LIST;
        }
        final AbstractDocument currentDocument = this.getCurrentDocument();
        ElementsByTagName elementsByTagName = currentDocument.getElementsByTagName(this, s);
        if (elementsByTagName == null) {
            elementsByTagName = new ElementsByTagName(s);
            currentDocument.putElementsByTagName(this, s, elementsByTagName);
        }
        return elementsByTagName;
    }
    
    public NodeList getElementsByTagNameNS(String s, final String s2) {
        if (s2 == null) {
            return AbstractParentNode.EMPTY_NODE_LIST;
        }
        if (s != null && s.length() == 0) {
            s = null;
        }
        final AbstractDocument currentDocument = this.getCurrentDocument();
        ElementsByTagNameNS elementsByTagNameNS = currentDocument.getElementsByTagNameNS(this, s, s2);
        if (elementsByTagNameNS == null) {
            elementsByTagNameNS = new ElementsByTagNameNS(s, s2);
            currentDocument.putElementsByTagNameNS(this, s, s2, elementsByTagNameNS);
        }
        return elementsByTagNameNS;
    }
    
    public String getTextContent() {
        final StringBuffer sb = new StringBuffer();
        for (Node node = this.getFirstChild(); node != null; node = node.getNextSibling()) {
            switch (node.getNodeType()) {
                case 7:
                case 8: {
                    break;
                }
                default: {
                    sb.append(((AbstractNode)node).getTextContent());
                    break;
                }
            }
        }
        return sb.toString();
    }
    
    public void fireDOMNodeInsertedIntoDocumentEvent() {
        if (this.getCurrentDocument().getEventsEnabled()) {
            super.fireDOMNodeInsertedIntoDocumentEvent();
            for (Node node = this.getFirstChild(); node != null; node = node.getNextSibling()) {
                ((AbstractNode)node).fireDOMNodeInsertedIntoDocumentEvent();
            }
        }
    }
    
    public void fireDOMNodeRemovedFromDocumentEvent() {
        if (this.getCurrentDocument().getEventsEnabled()) {
            super.fireDOMNodeRemovedFromDocumentEvent();
            for (Node node = this.getFirstChild(); node != null; node = node.getNextSibling()) {
                ((AbstractNode)node).fireDOMNodeRemovedFromDocumentEvent();
            }
        }
    }
    
    protected void nodeAdded(final Node node) {
    }
    
    protected void nodeToBeRemoved(final Node node) {
    }
    
    protected Node deepExport(final Node node, final AbstractDocument abstractDocument) {
        super.deepExport(node, abstractDocument);
        for (Node node2 = this.getFirstChild(); node2 != null; node2 = node2.getNextSibling()) {
            node.appendChild(((AbstractNode)node2).deepExport(node2.cloneNode(false), abstractDocument));
        }
        return node;
    }
    
    protected Node deepCopyInto(final Node node) {
        super.deepCopyInto(node);
        for (Node node2 = this.getFirstChild(); node2 != null; node2 = node2.getNextSibling()) {
            node.appendChild(node2.cloneNode(true));
        }
        return node;
    }
    
    protected void fireDOMSubtreeModifiedEvent() {
        final AbstractDocument currentDocument = this.getCurrentDocument();
        if (currentDocument.getEventsEnabled()) {
            final DOMMutationEvent domMutationEvent = (DOMMutationEvent)currentDocument.createEvent("MutationEvents");
            domMutationEvent.initMutationEventNS("http://www.w3.org/2001/xml-events", "DOMSubtreeModified", true, false, null, null, null, null, (short)1);
            this.dispatchEvent(domMutationEvent);
        }
    }
    
    protected void fireDOMNodeInsertedEvent(final Node node) {
        final AbstractDocument currentDocument = this.getCurrentDocument();
        if (currentDocument.getEventsEnabled()) {
            final DOMMutationEvent domMutationEvent = (DOMMutationEvent)currentDocument.createEvent("MutationEvents");
            domMutationEvent.initMutationEventNS("http://www.w3.org/2001/xml-events", "DOMNodeInserted", true, false, this, null, null, null, (short)2);
            final AbstractNode abstractNode = (AbstractNode)node;
            abstractNode.dispatchEvent(domMutationEvent);
            abstractNode.fireDOMNodeInsertedIntoDocumentEvent();
        }
    }
    
    protected void fireDOMNodeRemovedEvent(final Node node) {
        final AbstractDocument currentDocument = this.getCurrentDocument();
        if (currentDocument.getEventsEnabled()) {
            final DOMMutationEvent domMutationEvent = (DOMMutationEvent)currentDocument.createEvent("MutationEvents");
            domMutationEvent.initMutationEventNS("http://www.w3.org/2001/xml-events", "DOMNodeRemoved", true, false, this, null, null, null, (short)3);
            final AbstractNode abstractNode = (AbstractNode)node;
            abstractNode.dispatchEvent(domMutationEvent);
            abstractNode.fireDOMNodeRemovedFromDocumentEvent();
        }
    }
    
    protected void checkAndRemove(final Node node, final boolean b) {
        this.checkChildType(node, b);
        if (this.isReadonly()) {
            throw this.createDOMException((short)7, "readonly.node", new Object[] { new Integer(this.getNodeType()), this.getNodeName() });
        }
        if (node.getOwnerDocument() != this.getCurrentDocument()) {
            throw this.createDOMException((short)4, "node.from.wrong.document", new Object[] { new Integer(this.getNodeType()), this.getNodeName() });
        }
        if (this == node) {
            throw this.createDOMException((short)3, "add.self", new Object[] { this.getNodeName() });
        }
        final Node parentNode = node.getParentNode();
        if (parentNode == null) {
            return;
        }
        for (Node parentNode2 = this; parentNode2 != null; parentNode2 = parentNode2.getParentNode()) {
            if (parentNode2 == node) {
                throw this.createDOMException((short)3, "add.ancestor", new Object[] { new Integer(this.getNodeType()), this.getNodeName() });
            }
        }
        parentNode.removeChild(node);
    }
    
    protected class ChildNodes implements NodeList, Serializable
    {
        protected ExtendedNode firstChild;
        protected ExtendedNode lastChild;
        protected int children;
        protected int elementChildren;
        
        public ChildNodes() {
        }
        
        public Node item(final int n) {
            if (n < 0 || n >= this.children) {
                return null;
            }
            if (n < this.children >> 1) {
                Node node = this.firstChild;
                for (int i = 0; i < n; ++i) {
                    node = node.getNextSibling();
                }
                return node;
            }
            Node node2 = this.lastChild;
            for (int j = this.children - 1; j > n; --j) {
                node2 = node2.getPreviousSibling();
            }
            return node2;
        }
        
        public int getLength() {
            return this.children;
        }
        
        public ExtendedNode append(final ExtendedNode lastChild) {
            if (this.lastChild == null) {
                this.firstChild = lastChild;
            }
            else {
                this.lastChild.setNextSibling(lastChild);
                lastChild.setPreviousSibling(this.lastChild);
            }
            this.lastChild = lastChild;
            ++this.children;
            if (lastChild.getNodeType() == 1) {
                ++this.elementChildren;
            }
            return lastChild;
        }
        
        public ExtendedNode insert(final ExtendedNode extendedNode, final ExtendedNode extendedNode2) {
            if (extendedNode2 == null) {
                return this.append(extendedNode);
            }
            if (extendedNode2 == this.firstChild) {
                this.firstChild.setPreviousSibling(extendedNode);
                extendedNode.setNextSibling(this.firstChild);
                this.firstChild = extendedNode;
                ++this.children;
                if (extendedNode.getNodeType() == 1) {
                    ++this.elementChildren;
                }
                return extendedNode;
            }
            if (extendedNode2 == this.lastChild) {
                final ExtendedNode previousSibling = (ExtendedNode)extendedNode2.getPreviousSibling();
                previousSibling.setNextSibling(extendedNode);
                extendedNode2.setPreviousSibling(extendedNode);
                extendedNode.setNextSibling(extendedNode2);
                extendedNode.setPreviousSibling(previousSibling);
                ++this.children;
                if (extendedNode.getNodeType() == 1) {
                    ++this.elementChildren;
                }
                return extendedNode;
            }
            final ExtendedNode previousSibling2 = (ExtendedNode)extendedNode2.getPreviousSibling();
            if (previousSibling2.getNextSibling() == extendedNode2 && previousSibling2.getParentNode() == extendedNode2.getParentNode()) {
                previousSibling2.setNextSibling(extendedNode);
                extendedNode.setPreviousSibling(previousSibling2);
                extendedNode.setNextSibling(extendedNode2);
                extendedNode2.setPreviousSibling(extendedNode);
                ++this.children;
                if (extendedNode.getNodeType() == 1) {
                    ++this.elementChildren;
                }
                return extendedNode;
            }
            throw AbstractParentNode.this.createDOMException((short)8, "child.missing", new Object[] { new Integer(extendedNode2.getNodeType()), extendedNode2.getNodeName() });
        }
        
        public ExtendedNode replace(final ExtendedNode previousSibling, final ExtendedNode extendedNode) {
            if (extendedNode == this.firstChild) {
                final ExtendedNode nextSibling = (ExtendedNode)this.firstChild.getNextSibling();
                previousSibling.setNextSibling(nextSibling);
                if (extendedNode == this.lastChild) {
                    this.lastChild = previousSibling;
                }
                else {
                    nextSibling.setPreviousSibling(previousSibling);
                }
                this.firstChild.setNextSibling(null);
                this.firstChild = previousSibling;
                if (extendedNode.getNodeType() == 1) {
                    --this.elementChildren;
                }
                if (previousSibling.getNodeType() == 1) {
                    ++this.elementChildren;
                }
                return extendedNode;
            }
            if (extendedNode == this.lastChild) {
                final ExtendedNode previousSibling2 = (ExtendedNode)this.lastChild.getPreviousSibling();
                previousSibling.setPreviousSibling(previousSibling2);
                previousSibling2.setNextSibling(previousSibling);
                this.lastChild.setPreviousSibling(null);
                this.lastChild = previousSibling;
                if (extendedNode.getNodeType() == 1) {
                    --this.elementChildren;
                }
                if (previousSibling.getNodeType() == 1) {
                    ++this.elementChildren;
                }
                return extendedNode;
            }
            final ExtendedNode previousSibling3 = (ExtendedNode)extendedNode.getPreviousSibling();
            final ExtendedNode nextSibling2 = (ExtendedNode)extendedNode.getNextSibling();
            if (previousSibling3.getNextSibling() == extendedNode && nextSibling2.getPreviousSibling() == extendedNode && previousSibling3.getParentNode() == extendedNode.getParentNode() && nextSibling2.getParentNode() == extendedNode.getParentNode()) {
                previousSibling3.setNextSibling(previousSibling);
                previousSibling.setPreviousSibling(previousSibling3);
                previousSibling.setNextSibling(nextSibling2);
                nextSibling2.setPreviousSibling(previousSibling);
                extendedNode.setPreviousSibling(null);
                extendedNode.setNextSibling(null);
                if (extendedNode.getNodeType() == 1) {
                    --this.elementChildren;
                }
                if (previousSibling.getNodeType() == 1) {
                    ++this.elementChildren;
                }
                return extendedNode;
            }
            throw AbstractParentNode.this.createDOMException((short)8, "child.missing", new Object[] { new Integer(extendedNode.getNodeType()), extendedNode.getNodeName() });
        }
        
        public ExtendedNode remove(final ExtendedNode extendedNode) {
            if (extendedNode == this.firstChild) {
                if (extendedNode == this.lastChild) {
                    this.firstChild = null;
                    this.lastChild = null;
                    --this.children;
                    if (extendedNode.getNodeType() == 1) {
                        --this.elementChildren;
                    }
                    return extendedNode;
                }
                (this.firstChild = (ExtendedNode)this.firstChild.getNextSibling()).setPreviousSibling(null);
                extendedNode.setNextSibling(null);
                if (extendedNode.getNodeType() == 1) {
                    --this.elementChildren;
                }
                --this.children;
                return extendedNode;
            }
            else {
                if (extendedNode == this.lastChild) {
                    (this.lastChild = (ExtendedNode)this.lastChild.getPreviousSibling()).setNextSibling(null);
                    extendedNode.setPreviousSibling(null);
                    --this.children;
                    if (extendedNode.getNodeType() == 1) {
                        --this.elementChildren;
                    }
                    return extendedNode;
                }
                final ExtendedNode previousSibling = (ExtendedNode)extendedNode.getPreviousSibling();
                final ExtendedNode nextSibling = (ExtendedNode)extendedNode.getNextSibling();
                if (previousSibling.getNextSibling() == extendedNode && nextSibling.getPreviousSibling() == extendedNode && previousSibling.getParentNode() == extendedNode.getParentNode() && nextSibling.getParentNode() == extendedNode.getParentNode()) {
                    previousSibling.setNextSibling(nextSibling);
                    nextSibling.setPreviousSibling(previousSibling);
                    extendedNode.setPreviousSibling(null);
                    extendedNode.setNextSibling(null);
                    --this.children;
                    if (extendedNode.getNodeType() == 1) {
                        --this.elementChildren;
                    }
                    return extendedNode;
                }
                throw AbstractParentNode.this.createDOMException((short)8, "child.missing", new Object[] { new Integer(extendedNode.getNodeType()), extendedNode.getNodeName() });
            }
        }
    }
    
    protected class ElementsByTagNameNS implements NodeList
    {
        protected Node[] table;
        protected int size;
        protected String namespaceURI;
        protected String localName;
        
        public ElementsByTagNameNS(final String namespaceURI, final String localName) {
            this.size = -1;
            this.namespaceURI = namespaceURI;
            this.localName = localName;
        }
        
        public Node item(final int n) {
            if (this.size == -1) {
                this.initialize();
            }
            if (this.table == null || n < 0 || n > this.size) {
                return null;
            }
            return this.table[n];
        }
        
        public int getLength() {
            if (this.size == -1) {
                this.initialize();
            }
            return this.size;
        }
        
        public void invalidate() {
            this.size = -1;
        }
        
        protected void append(final Node node) {
            if (this.table == null) {
                this.table = new Node[11];
            }
            else if (this.size == this.table.length - 1) {
                final Node[] table = new Node[this.table.length * 2 + 1];
                System.arraycopy(this.table, 0, table, 0, this.size);
                this.table = table;
            }
            this.table[this.size++] = node;
        }
        
        protected void initialize() {
            this.size = 0;
            for (Node node = AbstractParentNode.this.getFirstChild(); node != null; node = node.getNextSibling()) {
                this.initialize(node);
            }
        }
        
        private void initialize(final Node node) {
            if (node.getNodeType() == 1) {
                final String anObject = (node.getNamespaceURI() == null) ? node.getNodeName() : node.getLocalName();
                if (this.nsMatch(this.namespaceURI, node.getNamespaceURI()) && (this.localName.equals("*") || this.localName.equals(anObject))) {
                    this.append(node);
                }
            }
            for (Node node2 = node.getFirstChild(); node2 != null; node2 = node2.getNextSibling()) {
                this.initialize(node2);
            }
        }
        
        private boolean nsMatch(final String s, final String anObject) {
            return (s == null && anObject == null) || (s != null && anObject != null && (s.equals("*") || s.equals(anObject)));
        }
    }
    
    protected class ElementsByTagName implements NodeList
    {
        protected Node[] table;
        protected int size;
        protected String name;
        
        public ElementsByTagName(final String name) {
            this.size = -1;
            this.name = name;
        }
        
        public Node item(final int n) {
            if (this.size == -1) {
                this.initialize();
            }
            if (this.table == null || n < 0 || n >= this.size) {
                return null;
            }
            return this.table[n];
        }
        
        public int getLength() {
            if (this.size == -1) {
                this.initialize();
            }
            return this.size;
        }
        
        public void invalidate() {
            this.size = -1;
        }
        
        protected void append(final Node node) {
            if (this.table == null) {
                this.table = new Node[11];
            }
            else if (this.size == this.table.length - 1) {
                final Node[] table = new Node[this.table.length * 2 + 1];
                System.arraycopy(this.table, 0, table, 0, this.size);
                this.table = table;
            }
            this.table[this.size++] = node;
        }
        
        protected void initialize() {
            this.size = 0;
            for (Node node = AbstractParentNode.this.getFirstChild(); node != null; node = node.getNextSibling()) {
                this.initialize(node);
            }
        }
        
        private void initialize(final Node node) {
            if (node.getNodeType() == 1) {
                final String nodeName = node.getNodeName();
                if (this.name.equals("*") || this.name.equals(nodeName)) {
                    this.append(node);
                }
            }
            for (Node node2 = node.getFirstChild(); node2 != null; node2 = node2.getNextSibling()) {
                this.initialize(node2);
            }
        }
    }
}
