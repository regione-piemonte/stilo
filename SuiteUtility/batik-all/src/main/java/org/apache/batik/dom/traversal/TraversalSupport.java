// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.dom.traversal;

import java.util.Iterator;
import org.w3c.dom.DOMException;
import java.util.LinkedList;
import org.w3c.dom.traversal.NodeIterator;
import org.w3c.dom.traversal.TreeWalker;
import org.w3c.dom.traversal.NodeFilter;
import org.w3c.dom.Node;
import org.apache.batik.dom.AbstractDocument;
import java.util.List;

public class TraversalSupport
{
    protected List iterators;
    
    public static TreeWalker createTreeWalker(final AbstractDocument abstractDocument, final Node node, final int n, final NodeFilter nodeFilter, final boolean b) {
        if (node == null) {
            throw abstractDocument.createDOMException((short)9, "null.root", null);
        }
        return new DOMTreeWalker(node, n, nodeFilter, b);
    }
    
    public NodeIterator createNodeIterator(final AbstractDocument abstractDocument, final Node node, final int n, final NodeFilter nodeFilter, final boolean b) throws DOMException {
        if (node == null) {
            throw abstractDocument.createDOMException((short)9, "null.root", null);
        }
        final DOMNodeIterator domNodeIterator = new DOMNodeIterator(abstractDocument, node, n, nodeFilter, b);
        if (this.iterators == null) {
            this.iterators = new LinkedList();
        }
        this.iterators.add(domNodeIterator);
        return domNodeIterator;
    }
    
    public void nodeToBeRemoved(final Node node) {
        if (this.iterators != null) {
            final Iterator<DOMNodeIterator> iterator = this.iterators.iterator();
            while (iterator.hasNext()) {
                iterator.next().nodeToBeRemoved(node);
            }
        }
    }
    
    public void detachNodeIterator(final NodeIterator nodeIterator) {
        this.iterators.remove(nodeIterator);
    }
}
