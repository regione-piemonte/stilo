// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.dom.svg;

import org.w3c.dom.Node;
import org.w3c.dom.svg.SVGAnimatedBoolean;
import org.apache.batik.dom.AbstractDocument;
import org.apache.batik.util.DoublyIndexedTable;
import org.w3c.dom.svg.SVGMPathElement;

public class SVGOMMPathElement extends SVGOMURIReferenceElement implements SVGMPathElement
{
    protected static DoublyIndexedTable xmlTraitInformation;
    protected static final AttributeInitializer attributeInitializer;
    protected SVGOMAnimatedBoolean externalResourcesRequired;
    
    protected SVGOMMPathElement() {
    }
    
    public SVGOMMPathElement(final String s, final AbstractDocument abstractDocument) {
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
    
    public String getLocalName() {
        return "mpath";
    }
    
    public SVGAnimatedBoolean getExternalResourcesRequired() {
        return (SVGAnimatedBoolean)this.externalResourcesRequired;
    }
    
    protected AttributeInitializer getAttributeInitializer() {
        return SVGOMMPathElement.attributeInitializer;
    }
    
    protected Node newNode() {
        return new SVGOMMPathElement();
    }
    
    protected DoublyIndexedTable getTraitInformationTable() {
        return SVGOMMPathElement.xmlTraitInformation;
    }
    
    static {
        final DoublyIndexedTable xmlTraitInformation = new DoublyIndexedTable(SVGOMURIReferenceElement.xmlTraitInformation);
        xmlTraitInformation.put(null, "externalResourcesRequired", new TraitInformation(true, 49));
        SVGOMMPathElement.xmlTraitInformation = xmlTraitInformation;
        (attributeInitializer = new AttributeInitializer(4)).addAttribute("http://www.w3.org/2000/xmlns/", null, "xmlns:xlink", "http://www.w3.org/1999/xlink");
        SVGOMMPathElement.attributeInitializer.addAttribute("http://www.w3.org/1999/xlink", "xlink", "type", "simple");
        SVGOMMPathElement.attributeInitializer.addAttribute("http://www.w3.org/1999/xlink", "xlink", "show", "other");
        SVGOMMPathElement.attributeInitializer.addAttribute("http://www.w3.org/1999/xlink", "xlink", "actuate", "onLoad");
    }
}
