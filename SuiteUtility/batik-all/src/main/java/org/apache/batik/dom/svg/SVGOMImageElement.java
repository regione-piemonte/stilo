// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.dom.svg;

import org.w3c.dom.Node;
import org.w3c.dom.svg.SVGAnimatedPreserveAspectRatio;
import org.w3c.dom.svg.SVGAnimatedLength;
import org.apache.batik.dom.AbstractDocument;
import org.apache.batik.util.DoublyIndexedTable;
import org.w3c.dom.svg.SVGImageElement;

public class SVGOMImageElement extends SVGURIReferenceGraphicsElement implements SVGImageElement
{
    protected static DoublyIndexedTable xmlTraitInformation;
    protected static final AttributeInitializer attributeInitializer;
    protected SVGOMAnimatedLength x;
    protected SVGOMAnimatedLength y;
    protected SVGOMAnimatedLength width;
    protected SVGOMAnimatedLength height;
    protected SVGOMAnimatedPreserveAspectRatio preserveAspectRatio;
    
    protected SVGOMImageElement() {
    }
    
    public SVGOMImageElement(final String s, final AbstractDocument abstractDocument) {
        super(s, abstractDocument);
        this.initializeLiveAttributes();
    }
    
    protected void initializeAllLiveAttributes() {
        super.initializeAllLiveAttributes();
        this.initializeLiveAttributes();
    }
    
    private void initializeLiveAttributes() {
        this.x = this.createLiveAnimatedLength(null, "x", "0", (short)2, false);
        this.y = this.createLiveAnimatedLength(null, "y", "0", (short)1, false);
        this.width = this.createLiveAnimatedLength(null, "width", null, (short)2, true);
        this.height = this.createLiveAnimatedLength(null, "height", null, (short)1, true);
        this.preserveAspectRatio = this.createLiveAnimatedPreserveAspectRatio();
    }
    
    public String getLocalName() {
        return "image";
    }
    
    public SVGAnimatedLength getX() {
        return (SVGAnimatedLength)this.x;
    }
    
    public SVGAnimatedLength getY() {
        return (SVGAnimatedLength)this.y;
    }
    
    public SVGAnimatedLength getWidth() {
        return (SVGAnimatedLength)this.width;
    }
    
    public SVGAnimatedLength getHeight() {
        return (SVGAnimatedLength)this.height;
    }
    
    public SVGAnimatedPreserveAspectRatio getPreserveAspectRatio() {
        return (SVGAnimatedPreserveAspectRatio)this.preserveAspectRatio;
    }
    
    protected AttributeInitializer getAttributeInitializer() {
        return SVGOMImageElement.attributeInitializer;
    }
    
    protected Node newNode() {
        return new SVGOMImageElement();
    }
    
    protected DoublyIndexedTable getTraitInformationTable() {
        return SVGOMImageElement.xmlTraitInformation;
    }
    
    static {
        final DoublyIndexedTable xmlTraitInformation = new DoublyIndexedTable(SVGURIReferenceGraphicsElement.xmlTraitInformation);
        xmlTraitInformation.put(null, "x", new TraitInformation(true, 3, (short)1));
        xmlTraitInformation.put(null, "y", new TraitInformation(true, 3, (short)2));
        xmlTraitInformation.put(null, "width", new TraitInformation(true, 3, (short)1));
        xmlTraitInformation.put(null, "height", new TraitInformation(true, 3, (short)2));
        SVGOMImageElement.xmlTraitInformation = xmlTraitInformation;
        (attributeInitializer = new AttributeInitializer(5)).addAttribute(null, null, "preserveAspectRatio", "xMidYMid meet");
        SVGOMImageElement.attributeInitializer.addAttribute("http://www.w3.org/2000/xmlns/", null, "xmlns:xlink", "http://www.w3.org/1999/xlink");
        SVGOMImageElement.attributeInitializer.addAttribute("http://www.w3.org/1999/xlink", "xlink", "type", "simple");
        SVGOMImageElement.attributeInitializer.addAttribute("http://www.w3.org/1999/xlink", "xlink", "show", "embed");
        SVGOMImageElement.attributeInitializer.addAttribute("http://www.w3.org/1999/xlink", "xlink", "actuate", "onLoad");
    }
}
