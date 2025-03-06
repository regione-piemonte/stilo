// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.extension;

import org.apache.batik.dom.svg.TraitInformation;
import org.apache.batik.dom.svg.SVGTestsSupport;
import org.w3c.dom.svg.SVGStringList;
import org.apache.batik.dom.util.XMLSupport;
import org.w3c.dom.svg.SVGAnimatedBoolean;
import org.w3c.dom.svg.SVGAnimatedTransformList;
import org.w3c.dom.svg.SVGException;
import org.w3c.dom.svg.SVGMatrix;
import org.w3c.dom.svg.SVGRect;
import org.w3c.dom.Element;
import org.apache.batik.dom.svg.SVGLocatableSupport;
import org.w3c.dom.svg.SVGElement;
import org.apache.batik.dom.AbstractDocument;
import org.apache.batik.dom.svg.SVGOMAnimatedBoolean;
import org.apache.batik.dom.svg.SVGOMAnimatedTransformList;
import org.apache.batik.util.DoublyIndexedTable;
import org.w3c.dom.svg.SVGTransformable;

public abstract class GraphicsExtensionElement extends StylableExtensionElement implements SVGTransformable
{
    protected static DoublyIndexedTable xmlTraitInformation;
    protected SVGOMAnimatedTransformList transform;
    protected SVGOMAnimatedBoolean externalResourcesRequired;
    
    protected GraphicsExtensionElement() {
        this.transform = this.createLiveAnimatedTransformList(null, "transform", "");
        this.externalResourcesRequired = this.createLiveAnimatedBoolean(null, "externalResourcesRequired", false);
    }
    
    protected GraphicsExtensionElement(final String s, final AbstractDocument abstractDocument) {
        super(s, abstractDocument);
        this.transform = this.createLiveAnimatedTransformList(null, "transform", "");
        this.externalResourcesRequired = this.createLiveAnimatedBoolean(null, "externalResourcesRequired", false);
    }
    
    public SVGElement getNearestViewportElement() {
        return SVGLocatableSupport.getNearestViewportElement(this);
    }
    
    public SVGElement getFarthestViewportElement() {
        return SVGLocatableSupport.getFarthestViewportElement(this);
    }
    
    public SVGRect getBBox() {
        return SVGLocatableSupport.getBBox(this);
    }
    
    public SVGMatrix getCTM() {
        return SVGLocatableSupport.getCTM(this);
    }
    
    public SVGMatrix getScreenCTM() {
        return SVGLocatableSupport.getScreenCTM(this);
    }
    
    public SVGMatrix getTransformToElement(final SVGElement svgElement) throws SVGException {
        return SVGLocatableSupport.getTransformToElement(this, svgElement);
    }
    
    public SVGAnimatedTransformList getTransform() {
        return (SVGAnimatedTransformList)this.transform;
    }
    
    public SVGAnimatedBoolean getExternalResourcesRequired() {
        return (SVGAnimatedBoolean)this.externalResourcesRequired;
    }
    
    public String getXMLlang() {
        return XMLSupport.getXMLLang(this);
    }
    
    public void setXMLlang(final String s) {
        this.setAttributeNS("http://www.w3.org/XML/1998/namespace", "xml:lang", s);
    }
    
    public String getXMLspace() {
        return XMLSupport.getXMLSpace(this);
    }
    
    public void setXMLspace(final String s) {
        this.setAttributeNS("http://www.w3.org/XML/1998/namespace", "xml:space", s);
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
    
    static {
        final DoublyIndexedTable xmlTraitInformation = new DoublyIndexedTable(StylableExtensionElement.xmlTraitInformation);
        xmlTraitInformation.put(null, "transform", new TraitInformation(true, 9));
        xmlTraitInformation.put(null, "externalResourcesRequired", new TraitInformation(true, 49));
        xmlTraitInformation.put(null, "requiredExtensions", new TraitInformation(false, 33));
        xmlTraitInformation.put(null, "requiredFeatures", new TraitInformation(false, 33));
        xmlTraitInformation.put(null, "systemLanguage", new TraitInformation(false, 46));
        GraphicsExtensionElement.xmlTraitInformation = xmlTraitInformation;
    }
}
