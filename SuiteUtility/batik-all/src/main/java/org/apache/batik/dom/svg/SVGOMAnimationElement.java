// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.dom.svg;

import org.w3c.dom.Element;
import org.w3c.dom.svg.SVGStringList;
import org.w3c.dom.svg.SVGAnimatedBoolean;
import org.w3c.dom.DOMException;
import org.w3c.dom.svg.SVGElement;
import org.apache.batik.dom.AbstractDocument;
import org.apache.batik.util.DoublyIndexedTable;
import org.w3c.dom.svg.SVGAnimationElement;

public abstract class SVGOMAnimationElement extends SVGOMElement implements SVGAnimationElement
{
    protected static DoublyIndexedTable xmlTraitInformation;
    protected SVGOMAnimatedBoolean externalResourcesRequired;
    
    protected SVGOMAnimationElement() {
    }
    
    protected SVGOMAnimationElement(final String s, final AbstractDocument abstractDocument) {
        super(s, abstractDocument);
        this.initializeLiveAttributes();
    }
    
    protected void initializeAllLiveAttributes() {
        super.initializeAllLiveAttributes();
        this.initializeLiveAttributes();
    }
    
    private void initializeLiveAttributes() {
        this.externalResourcesRequired = this.createLiveAnimatedBoolean(null, "externalResourcesRequired", false);
    }
    
    public SVGElement getTargetElement() {
        return ((SVGAnimationContext)this.getSVGContext()).getTargetElement();
    }
    
    public float getStartTime() {
        return ((SVGAnimationContext)this.getSVGContext()).getStartTime();
    }
    
    public float getCurrentTime() {
        return ((SVGAnimationContext)this.getSVGContext()).getCurrentTime();
    }
    
    public float getSimpleDuration() throws DOMException {
        final float simpleDuration = ((SVGAnimationContext)this.getSVGContext()).getSimpleDuration();
        if (simpleDuration == Float.POSITIVE_INFINITY) {
            throw this.createDOMException((short)9, "animation.dur.indefinite", null);
        }
        return simpleDuration;
    }
    
    public float getHyperlinkBeginTime() {
        return ((SVGAnimationContext)this.getSVGContext()).getHyperlinkBeginTime();
    }
    
    public boolean beginElement() throws DOMException {
        return ((SVGAnimationContext)this.getSVGContext()).beginElement();
    }
    
    public boolean beginElementAt(final float n) throws DOMException {
        return ((SVGAnimationContext)this.getSVGContext()).beginElementAt(n);
    }
    
    public boolean endElement() throws DOMException {
        return ((SVGAnimationContext)this.getSVGContext()).endElement();
    }
    
    public boolean endElementAt(final float n) throws DOMException {
        return ((SVGAnimationContext)this.getSVGContext()).endElementAt(n);
    }
    
    public SVGAnimatedBoolean getExternalResourcesRequired() {
        return (SVGAnimatedBoolean)this.externalResourcesRequired;
    }
    
    public SVGStringList getRequiredFeatures() {
        return SVGTestsSupport.getRequiredFeatures(this);
    }
    
    public SVGStringList getRequiredExtensions() {
        return SVGTestsSupport.getRequiredExtensions(this);
    }
    
    public SVGStringList getSystemLanguage() {
        return SVGTestsSupport.getSystemLanguage(this);
    }
    
    public boolean hasExtension(final String s) {
        return SVGTestsSupport.hasExtension(this, s);
    }
    
    protected DoublyIndexedTable getTraitInformationTable() {
        return SVGOMAnimationElement.xmlTraitInformation;
    }
    
    static {
        final DoublyIndexedTable xmlTraitInformation = new DoublyIndexedTable(SVGOMElement.xmlTraitInformation);
        xmlTraitInformation.put(null, "externalResourcesRequired", new TraitInformation(true, 49));
        SVGOMAnimationElement.xmlTraitInformation = xmlTraitInformation;
    }
}
