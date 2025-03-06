// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.dom.svg;

import org.w3c.dom.Node;
import org.w3c.dom.Element;
import org.w3c.dom.svg.SVGStringList;
import org.w3c.dom.svg.SVGAnimatedBoolean;
import org.w3c.dom.svg.SVGAnimatedLength;
import org.apache.batik.dom.AbstractDocument;
import org.apache.batik.util.DoublyIndexedTable;
import org.w3c.dom.svg.SVGCursorElement;

public class SVGOMCursorElement extends SVGOMURIReferenceElement implements SVGCursorElement
{
    protected static final AttributeInitializer attributeInitializer;
    protected static DoublyIndexedTable xmlTraitInformation;
    protected SVGOMAnimatedLength x;
    protected SVGOMAnimatedLength y;
    protected SVGOMAnimatedBoolean externalResourcesRequired;
    
    protected SVGOMCursorElement() {
    }
    
    public SVGOMCursorElement(final String s, final AbstractDocument abstractDocument) {
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
        this.externalResourcesRequired = this.createLiveAnimatedBoolean(null, "externalResourcesRequired", false);
    }
    
    public String getLocalName() {
        return "cursor";
    }
    
    public SVGAnimatedLength getX() {
        return (SVGAnimatedLength)this.x;
    }
    
    public SVGAnimatedLength getY() {
        return (SVGAnimatedLength)this.y;
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
    
    protected AttributeInitializer getAttributeInitializer() {
        return SVGOMCursorElement.attributeInitializer;
    }
    
    protected Node newNode() {
        return new SVGOMCursorElement();
    }
    
    protected DoublyIndexedTable getTraitInformationTable() {
        return SVGOMCursorElement.xmlTraitInformation;
    }
    
    static {
        (attributeInitializer = new AttributeInitializer(4)).addAttribute("http://www.w3.org/2000/xmlns/", null, "xmlns:xlink", "http://www.w3.org/1999/xlink");
        SVGOMCursorElement.attributeInitializer.addAttribute("http://www.w3.org/1999/xlink", "xlink", "type", "simple");
        SVGOMCursorElement.attributeInitializer.addAttribute("http://www.w3.org/1999/xlink", "xlink", "show", "other");
        SVGOMCursorElement.attributeInitializer.addAttribute("http://www.w3.org/1999/xlink", "xlink", "actuate", "onLoad");
        final DoublyIndexedTable xmlTraitInformation = new DoublyIndexedTable(SVGOMURIReferenceElement.xmlTraitInformation);
        xmlTraitInformation.put(null, "externalResourcesRequired", new TraitInformation(true, 49));
        xmlTraitInformation.put(null, "x", new TraitInformation(true, 3, (short)1));
        xmlTraitInformation.put(null, "y", new TraitInformation(true, 3, (short)2));
        SVGOMCursorElement.xmlTraitInformation = xmlTraitInformation;
    }
}
