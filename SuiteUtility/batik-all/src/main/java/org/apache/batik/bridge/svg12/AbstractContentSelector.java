// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.bridge.svg12;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import java.util.HashMap;
import org.w3c.dom.Element;
import org.apache.batik.dom.svg12.XBLOMContentElement;

public abstract class AbstractContentSelector
{
    protected ContentManager contentManager;
    protected XBLOMContentElement contentElement;
    protected Element boundElement;
    protected static HashMap selectorFactories;
    
    public AbstractContentSelector(final ContentManager contentManager, final XBLOMContentElement contentElement, final Element boundElement) {
        this.contentManager = contentManager;
        this.contentElement = contentElement;
        this.boundElement = boundElement;
    }
    
    public abstract NodeList getSelectedContent();
    
    abstract boolean update();
    
    protected boolean isSelected(final Node node) {
        return this.contentManager.getContentElement(node) != null;
    }
    
    public static AbstractContentSelector createSelector(final String s, final ContentManager contentManager, final XBLOMContentElement xblomContentElement, final Element element, final String s2) {
        final ContentSelectorFactory contentSelectorFactory = AbstractContentSelector.selectorFactories.get(s);
        if (contentSelectorFactory == null) {
            throw new RuntimeException("Invalid XBL content selector language '" + s + "'");
        }
        return contentSelectorFactory.createSelector(contentManager, xblomContentElement, element, s2);
    }
    
    static {
        AbstractContentSelector.selectorFactories = new HashMap();
        final XPathPatternContentSelectorFactory xPathPatternContentSelectorFactory = new XPathPatternContentSelectorFactory();
        final XPathSubsetContentSelectorFactory value = new XPathSubsetContentSelectorFactory();
        AbstractContentSelector.selectorFactories.put(null, xPathPatternContentSelectorFactory);
        AbstractContentSelector.selectorFactories.put("XPathPattern", xPathPatternContentSelectorFactory);
        AbstractContentSelector.selectorFactories.put("XPathSubset", value);
    }
    
    protected static class XPathPatternContentSelectorFactory implements ContentSelectorFactory
    {
        public AbstractContentSelector createSelector(final ContentManager contentManager, final XBLOMContentElement xblomContentElement, final Element element, final String s) {
            return new XPathPatternContentSelector(contentManager, xblomContentElement, element, s);
        }
    }
    
    protected static class XPathSubsetContentSelectorFactory implements ContentSelectorFactory
    {
        public AbstractContentSelector createSelector(final ContentManager contentManager, final XBLOMContentElement xblomContentElement, final Element element, final String s) {
            return new XPathSubsetContentSelector(contentManager, xblomContentElement, element, s);
        }
    }
    
    protected interface ContentSelectorFactory
    {
        AbstractContentSelector createSelector(final ContentManager p0, final XBLOMContentElement p1, final Element p2, final String p3);
    }
}
