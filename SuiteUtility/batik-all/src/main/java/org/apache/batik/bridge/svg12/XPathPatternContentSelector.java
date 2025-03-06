// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.bridge.svg12;

import java.util.ArrayList;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import javax.xml.transform.TransformerException;
import org.apache.batik.dom.AbstractDocument;
import org.apache.xml.utils.PrefixResolver;
import javax.xml.transform.SourceLocator;
import org.w3c.dom.Element;
import org.apache.batik.dom.svg12.XBLOMContentElement;
import org.apache.xpath.XPathContext;
import org.apache.xpath.XPath;

public class XPathPatternContentSelector extends AbstractContentSelector
{
    protected NSPrefixResolver prefixResolver;
    protected XPath xpath;
    protected XPathContext context;
    protected SelectedNodes selectedContent;
    protected String expression;
    
    public XPathPatternContentSelector(final ContentManager contentManager, final XBLOMContentElement xblomContentElement, final Element element, final String expression) {
        super(contentManager, xblomContentElement, element);
        this.prefixResolver = new NSPrefixResolver();
        this.expression = expression;
        this.parse();
    }
    
    protected void parse() {
        this.context = new XPathContext();
        try {
            this.xpath = new XPath(this.expression, (SourceLocator)null, (PrefixResolver)this.prefixResolver, 1);
        }
        catch (TransformerException ex) {
            throw ((AbstractDocument)this.contentElement.getOwnerDocument()).createXPathException((short)51, "xpath.invalid.expression", new Object[] { this.expression, ex.getMessage() });
        }
    }
    
    public NodeList getSelectedContent() {
        if (this.selectedContent == null) {
            this.selectedContent = new SelectedNodes();
        }
        return this.selectedContent;
    }
    
    boolean update() {
        if (this.selectedContent == null) {
            this.selectedContent = new SelectedNodes();
            return true;
        }
        this.parse();
        return this.selectedContent.update();
    }
    
    protected class NSPrefixResolver implements PrefixResolver
    {
        public String getBaseIdentifier() {
            return null;
        }
        
        public String getNamespaceForPrefix(final String s) {
            return XPathPatternContentSelector.this.contentElement.lookupNamespaceURI(s);
        }
        
        public String getNamespaceForPrefix(final String s, final Node node) {
            return XPathPatternContentSelector.this.contentElement.lookupNamespaceURI(s);
        }
        
        public boolean handlesNullPrefixes() {
            return false;
        }
    }
    
    protected class SelectedNodes implements NodeList
    {
        protected ArrayList nodes;
        
        public SelectedNodes() {
            this.nodes = new ArrayList(10);
            this.update();
        }
        
        protected boolean update() {
            final ArrayList list = (ArrayList)this.nodes.clone();
            this.nodes.clear();
            for (Node node = XPathPatternContentSelector.this.boundElement.getFirstChild(); node != null; node = node.getNextSibling()) {
                this.update(node);
            }
            final int size = this.nodes.size();
            if (list.size() != size) {
                return true;
            }
            for (int i = 0; i < size; ++i) {
                if (list.get(i) != this.nodes.get(i)) {
                    return true;
                }
            }
            return false;
        }
        
        protected boolean descendantSelected(Node node) {
            for (node = node.getFirstChild(); node != null; node = node.getNextSibling()) {
                if (XPathPatternContentSelector.this.isSelected(node) || this.descendantSelected(node)) {
                    return true;
                }
            }
            return false;
        }
        
        protected void update(Node e) {
            if (!XPathPatternContentSelector.this.isSelected(e)) {
                try {
                    if (XPathPatternContentSelector.this.xpath.execute(XPathPatternContentSelector.this.context, e, (PrefixResolver)XPathPatternContentSelector.this.prefixResolver).num() != Double.NEGATIVE_INFINITY) {
                        if (!this.descendantSelected(e)) {
                            this.nodes.add(e);
                        }
                    }
                    else {
                        for (e = e.getFirstChild(); e != null; e = e.getNextSibling()) {
                            this.update(e);
                        }
                    }
                }
                catch (TransformerException ex) {
                    throw ((AbstractDocument)XPathPatternContentSelector.this.contentElement.getOwnerDocument()).createXPathException((short)51, "xpath.error", new Object[] { XPathPatternContentSelector.this.expression, ex.getMessage() });
                }
            }
        }
        
        public Node item(final int index) {
            if (index < 0 || index >= this.nodes.size()) {
                return null;
            }
            return this.nodes.get(index);
        }
        
        public int getLength() {
            return this.nodes.size();
        }
    }
}
