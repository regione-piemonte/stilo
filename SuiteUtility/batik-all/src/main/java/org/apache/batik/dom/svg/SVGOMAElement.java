// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.dom.svg;

import org.w3c.dom.Node;
import org.w3c.dom.svg.SVGAnimatedString;
import org.apache.batik.dom.AbstractDocument;
import org.apache.batik.util.DoublyIndexedTable;
import org.w3c.dom.svg.SVGAElement;

public class SVGOMAElement extends SVGURIReferenceGraphicsElement implements SVGAElement
{
    protected static final AttributeInitializer attributeInitializer;
    protected static DoublyIndexedTable xmlTraitInformation;
    protected SVGOMAnimatedString target;
    
    protected SVGOMAElement() {
    }
    
    public SVGOMAElement(final String s, final AbstractDocument abstractDocument) {
        super(s, abstractDocument);
        this.initializeLiveAttributes();
    }
    
    protected void initializeAllLiveAttributes() {
        super.initializeAllLiveAttributes();
        this.initializeLiveAttributes();
    }
    
    private void initializeLiveAttributes() {
        this.target = this.createLiveAnimatedString(null, "target");
    }
    
    public String getLocalName() {
        return "a";
    }
    
    public SVGAnimatedString getTarget() {
        return (SVGAnimatedString)this.target;
    }
    
    protected AttributeInitializer getAttributeInitializer() {
        return SVGOMAElement.attributeInitializer;
    }
    
    protected Node newNode() {
        return new SVGOMAElement();
    }
    
    protected DoublyIndexedTable getTraitInformationTable() {
        return SVGOMAElement.xmlTraitInformation;
    }
    
    static {
        (attributeInitializer = new AttributeInitializer(4)).addAttribute("http://www.w3.org/2000/xmlns/", null, "xmlns:xlink", "http://www.w3.org/1999/xlink");
        SVGOMAElement.attributeInitializer.addAttribute("http://www.w3.org/1999/xlink", "xlink", "type", "simple");
        SVGOMAElement.attributeInitializer.addAttribute("http://www.w3.org/1999/xlink", "xlink", "show", "replace");
        SVGOMAElement.attributeInitializer.addAttribute("http://www.w3.org/1999/xlink", "xlink", "actuate", "onRequest");
        final DoublyIndexedTable xmlTraitInformation = new DoublyIndexedTable(SVGURIReferenceGraphicsElement.xmlTraitInformation);
        xmlTraitInformation.put(null, "target", new TraitInformation(true, 16));
        SVGOMAElement.xmlTraitInformation = xmlTraitInformation;
    }
}
