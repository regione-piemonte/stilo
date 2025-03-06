// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.dom.svg;

import org.w3c.dom.Node;
import org.w3c.dom.svg.SVGAnimatedString;
import org.w3c.dom.svg.SVGAnimatedEnumeration;
import org.w3c.dom.svg.SVGAnimatedLength;
import org.apache.batik.dom.AbstractDocument;
import org.apache.batik.util.DoublyIndexedTable;
import org.w3c.dom.svg.SVGTextPathElement;

public class SVGOMTextPathElement extends SVGOMTextContentElement implements SVGTextPathElement
{
    protected static DoublyIndexedTable xmlTraitInformation;
    protected static final AttributeInitializer attributeInitializer;
    protected static final String[] METHOD_VALUES;
    protected static final String[] SPACING_VALUES;
    protected SVGOMAnimatedEnumeration method;
    protected SVGOMAnimatedEnumeration spacing;
    protected SVGOMAnimatedLength startOffset;
    protected SVGOMAnimatedString href;
    
    protected SVGOMTextPathElement() {
    }
    
    public SVGOMTextPathElement(final String s, final AbstractDocument abstractDocument) {
        super(s, abstractDocument);
        this.initializeLiveAttributes();
    }
    
    protected void initializeAllLiveAttributes() {
        super.initializeAllLiveAttributes();
        this.initializeLiveAttributes();
    }
    
    private void initializeLiveAttributes() {
        this.method = this.createLiveAnimatedEnumeration(null, "method", SVGOMTextPathElement.METHOD_VALUES, (short)1);
        this.spacing = this.createLiveAnimatedEnumeration(null, "spacing", SVGOMTextPathElement.SPACING_VALUES, (short)2);
        this.startOffset = this.createLiveAnimatedLength(null, "startOffset", "0", (short)0, true);
        this.href = this.createLiveAnimatedString("http://www.w3.org/1999/xlink", "href");
    }
    
    public String getLocalName() {
        return "textPath";
    }
    
    public SVGAnimatedLength getStartOffset() {
        return (SVGAnimatedLength)this.startOffset;
    }
    
    public SVGAnimatedEnumeration getMethod() {
        return (SVGAnimatedEnumeration)this.method;
    }
    
    public SVGAnimatedEnumeration getSpacing() {
        return (SVGAnimatedEnumeration)this.spacing;
    }
    
    public SVGAnimatedString getHref() {
        return (SVGAnimatedString)this.href;
    }
    
    protected AttributeInitializer getAttributeInitializer() {
        return SVGOMTextPathElement.attributeInitializer;
    }
    
    protected Node newNode() {
        return new SVGOMTextPathElement();
    }
    
    protected DoublyIndexedTable getTraitInformationTable() {
        return SVGOMTextPathElement.xmlTraitInformation;
    }
    
    static {
        final DoublyIndexedTable xmlTraitInformation = new DoublyIndexedTable(SVGOMTextContentElement.xmlTraitInformation);
        xmlTraitInformation.put(null, "method", new TraitInformation(true, 15));
        xmlTraitInformation.put(null, "spacing", new TraitInformation(true, 15));
        xmlTraitInformation.put(null, "startOffset", new TraitInformation(true, 3));
        xmlTraitInformation.put("http://www.w3.org/1999/xlink", "href", new TraitInformation(true, 10));
        SVGOMTextPathElement.xmlTraitInformation = xmlTraitInformation;
        (attributeInitializer = new AttributeInitializer(4)).addAttribute("http://www.w3.org/2000/xmlns/", null, "xmlns:xlink", "http://www.w3.org/1999/xlink");
        SVGOMTextPathElement.attributeInitializer.addAttribute("http://www.w3.org/1999/xlink", "xlink", "type", "simple");
        SVGOMTextPathElement.attributeInitializer.addAttribute("http://www.w3.org/1999/xlink", "xlink", "show", "other");
        SVGOMTextPathElement.attributeInitializer.addAttribute("http://www.w3.org/1999/xlink", "xlink", "actuate", "onLoad");
        METHOD_VALUES = new String[] { "", "align", "stretch" };
        SPACING_VALUES = new String[] { "", "auto", "exact" };
    }
}
