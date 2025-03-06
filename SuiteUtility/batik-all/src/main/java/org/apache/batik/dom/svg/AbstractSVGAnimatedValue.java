// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.dom.svg;

import java.util.Iterator;
import org.w3c.dom.Element;
import org.apache.batik.anim.values.AnimatableValue;
import java.util.LinkedList;

public abstract class AbstractSVGAnimatedValue implements AnimatedLiveAttributeValue
{
    protected AbstractElement element;
    protected String namespaceURI;
    protected String localName;
    protected boolean hasAnimVal;
    protected LinkedList listeners;
    
    public AbstractSVGAnimatedValue(final AbstractElement element, final String namespaceURI, final String localName) {
        this.listeners = new LinkedList();
        this.element = element;
        this.namespaceURI = namespaceURI;
        this.localName = localName;
    }
    
    public String getNamespaceURI() {
        return this.namespaceURI;
    }
    
    public String getLocalName() {
        return this.localName;
    }
    
    public boolean isSpecified() {
        return this.hasAnimVal || this.element.hasAttributeNS(this.namespaceURI, this.localName);
    }
    
    protected abstract void updateAnimatedValue(final AnimatableValue p0);
    
    public void addAnimatedAttributeListener(final AnimatedAttributeListener animatedAttributeListener) {
        if (!this.listeners.contains(animatedAttributeListener)) {
            this.listeners.add(animatedAttributeListener);
        }
    }
    
    public void removeAnimatedAttributeListener(final AnimatedAttributeListener o) {
        this.listeners.remove(o);
    }
    
    protected void fireBaseAttributeListeners() {
        if (this.element instanceof SVGOMElement) {
            ((SVGOMElement)this.element).fireBaseAttributeListeners(this.namespaceURI, this.localName);
        }
    }
    
    protected void fireAnimatedAttributeListeners() {
        final Iterator iterator = this.listeners.iterator();
        while (iterator.hasNext()) {
            iterator.next().animatedAttributeChanged(this.element, this);
        }
    }
}
