// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.dom.svg;

import org.w3c.dom.Node;
import org.w3c.dom.svg.SVGAnimatedBoolean;
import org.w3c.dom.svg.SVGAnimatedPreserveAspectRatio;
import org.w3c.dom.svg.SVGAnimatedRect;
import org.w3c.dom.Element;
import org.apache.batik.dom.util.XMLSupport;
import org.apache.batik.dom.AbstractDocument;
import org.apache.batik.util.DoublyIndexedTable;
import org.w3c.dom.svg.SVGSymbolElement;

public class SVGOMSymbolElement extends SVGStylableElement implements SVGSymbolElement
{
    protected static DoublyIndexedTable xmlTraitInformation;
    protected SVGOMAnimatedPreserveAspectRatio preserveAspectRatio;
    protected static final AttributeInitializer attributeInitializer;
    protected SVGOMAnimatedBoolean externalResourcesRequired;
    
    protected SVGOMSymbolElement() {
    }
    
    public SVGOMSymbolElement(final String s, final AbstractDocument abstractDocument) {
        super(s, abstractDocument);
        this.initializeLiveAttributes();
    }
    
    protected void initializeAllLiveAttributes() {
        super.initializeAllLiveAttributes();
        this.initializeLiveAttributes();
    }
    
    private void initializeLiveAttributes() {
        this.preserveAspectRatio = this.createLiveAnimatedPreserveAspectRatio();
    }
    
    public String getLocalName() {
        return "symbol";
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
    
    public short getZoomAndPan() {
        return SVGZoomAndPanSupport.getZoomAndPan(this);
    }
    
    public void setZoomAndPan(final short n) {
        SVGZoomAndPanSupport.setZoomAndPan(this, n);
    }
    
    public SVGAnimatedRect getViewBox() {
        throw new UnsupportedOperationException("SVGFitToViewBox.getViewBox is not implemented");
    }
    
    public SVGAnimatedPreserveAspectRatio getPreserveAspectRatio() {
        return (SVGAnimatedPreserveAspectRatio)this.preserveAspectRatio;
    }
    
    public SVGAnimatedBoolean getExternalResourcesRequired() {
        return (SVGAnimatedBoolean)this.externalResourcesRequired;
    }
    
    protected AttributeInitializer getAttributeInitializer() {
        return SVGOMSymbolElement.attributeInitializer;
    }
    
    protected Node newNode() {
        return new SVGOMSymbolElement();
    }
    
    protected DoublyIndexedTable getTraitInformationTable() {
        return SVGOMSymbolElement.xmlTraitInformation;
    }
    
    static {
        final DoublyIndexedTable xmlTraitInformation = new DoublyIndexedTable(SVGStylableElement.xmlTraitInformation);
        xmlTraitInformation.put(null, "externalResourcesRequired", new TraitInformation(true, 49));
        xmlTraitInformation.put(null, "preserveAspectRatio", new TraitInformation(true, 32));
        xmlTraitInformation.put(null, "viewBox", new TraitInformation(true, 13));
        SVGOMSymbolElement.xmlTraitInformation = xmlTraitInformation;
        (attributeInitializer = new AttributeInitializer(1)).addAttribute(null, null, "preserveAspectRatio", "xMidYMid meet");
    }
}
