// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.dom.svg;

import org.w3c.dom.Node;
import org.w3c.dom.svg.SVGAnimatedBoolean;
import org.w3c.dom.DOMException;
import org.apache.batik.dom.AbstractDocument;
import org.apache.batik.util.DoublyIndexedTable;
import org.w3c.dom.svg.SVGScriptElement;

public class SVGOMScriptElement extends SVGOMURIReferenceElement implements SVGScriptElement
{
    protected static DoublyIndexedTable xmlTraitInformation;
    protected static final AttributeInitializer attributeInitializer;
    protected SVGOMAnimatedBoolean externalResourcesRequired;
    
    protected SVGOMScriptElement() {
    }
    
    public SVGOMScriptElement(final String s, final AbstractDocument abstractDocument) {
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
        return "script";
    }
    
    public String getType() {
        return this.getAttributeNS(null, "type");
    }
    
    public void setType(final String s) throws DOMException {
        this.setAttributeNS(null, "type", s);
    }
    
    public SVGAnimatedBoolean getExternalResourcesRequired() {
        return (SVGAnimatedBoolean)this.externalResourcesRequired;
    }
    
    protected AttributeInitializer getAttributeInitializer() {
        return SVGOMScriptElement.attributeInitializer;
    }
    
    protected Node newNode() {
        return new SVGOMScriptElement();
    }
    
    protected DoublyIndexedTable getTraitInformationTable() {
        return SVGOMScriptElement.xmlTraitInformation;
    }
    
    static {
        final DoublyIndexedTable xmlTraitInformation = new DoublyIndexedTable(SVGOMURIReferenceElement.xmlTraitInformation);
        xmlTraitInformation.put(null, "externalResourcesRequired", new TraitInformation(true, 49));
        SVGOMScriptElement.xmlTraitInformation = xmlTraitInformation;
        (attributeInitializer = new AttributeInitializer(1)).addAttribute("http://www.w3.org/2000/xmlns/", null, "xmlns:xlink", "http://www.w3.org/1999/xlink");
        SVGOMScriptElement.attributeInitializer.addAttribute("http://www.w3.org/1999/xlink", "xlink", "type", "simple");
        SVGOMScriptElement.attributeInitializer.addAttribute("http://www.w3.org/1999/xlink", "xlink", "show", "other");
        SVGOMScriptElement.attributeInitializer.addAttribute("http://www.w3.org/1999/xlink", "xlink", "actuate", "onLoad");
    }
}
