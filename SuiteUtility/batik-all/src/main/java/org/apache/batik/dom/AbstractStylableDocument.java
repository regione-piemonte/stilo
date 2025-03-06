// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.dom;

import org.w3c.dom.css.CSSStyleDeclaration;
import org.w3c.dom.Element;
import org.w3c.dom.stylesheets.StyleSheetList;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.DocumentType;
import org.apache.batik.css.engine.CSSEngine;
import org.w3c.dom.views.AbstractView;
import org.w3c.dom.views.DocumentView;
import org.w3c.dom.css.DocumentCSS;

public abstract class AbstractStylableDocument extends AbstractDocument implements DocumentCSS, DocumentView
{
    protected transient AbstractView defaultView;
    protected transient CSSEngine cssEngine;
    
    protected AbstractStylableDocument() {
    }
    
    protected AbstractStylableDocument(final DocumentType documentType, final DOMImplementation domImplementation) {
        super(documentType, domImplementation);
    }
    
    public void setCSSEngine(final CSSEngine cssEngine) {
        this.cssEngine = cssEngine;
    }
    
    public CSSEngine getCSSEngine() {
        return this.cssEngine;
    }
    
    public StyleSheetList getStyleSheets() {
        throw new RuntimeException(" !!! Not implemented");
    }
    
    public AbstractView getDefaultView() {
        if (this.defaultView == null) {
            this.defaultView = ((ExtensibleDOMImplementation)this.implementation).createViewCSS(this);
        }
        return this.defaultView;
    }
    
    public void clearViewCSS() {
        this.defaultView = null;
        if (this.cssEngine != null) {
            this.cssEngine.dispose();
        }
        this.cssEngine = null;
    }
    
    public CSSStyleDeclaration getOverrideStyle(final Element element, final String s) {
        throw new RuntimeException(" !!! Not implemented");
    }
}
