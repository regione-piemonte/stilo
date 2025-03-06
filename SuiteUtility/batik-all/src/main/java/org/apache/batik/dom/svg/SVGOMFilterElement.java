// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.dom.svg;

import org.w3c.dom.Node;
import org.w3c.dom.Element;
import org.apache.batik.dom.util.XMLSupport;
import org.w3c.dom.svg.SVGAnimatedBoolean;
import org.w3c.dom.svg.SVGAnimatedString;
import org.w3c.dom.svg.SVGAnimatedInteger;
import org.w3c.dom.svg.SVGAnimatedLength;
import org.w3c.dom.svg.SVGAnimatedEnumeration;
import org.apache.batik.dom.AbstractDocument;
import org.apache.batik.util.DoublyIndexedTable;
import org.w3c.dom.svg.SVGFilterElement;

public class SVGOMFilterElement extends SVGStylableElement implements SVGFilterElement
{
    protected static DoublyIndexedTable xmlTraitInformation;
    protected static final AttributeInitializer attributeInitializer;
    protected static final String[] UNITS_VALUES;
    protected SVGOMAnimatedEnumeration filterUnits;
    protected SVGOMAnimatedEnumeration primitiveUnits;
    protected SVGOMAnimatedLength x;
    protected SVGOMAnimatedLength y;
    protected SVGOMAnimatedLength width;
    protected SVGOMAnimatedLength height;
    protected SVGOMAnimatedString href;
    protected SVGOMAnimatedBoolean externalResourcesRequired;
    
    protected SVGOMFilterElement() {
    }
    
    public SVGOMFilterElement(final String s, final AbstractDocument abstractDocument) {
        super(s, abstractDocument);
        this.initializeLiveAttributes();
    }
    
    protected void initializeAllLiveAttributes() {
        super.initializeAllLiveAttributes();
        this.initializeLiveAttributes();
    }
    
    private void initializeLiveAttributes() {
        this.filterUnits = this.createLiveAnimatedEnumeration(null, "filterUnits", SVGOMFilterElement.UNITS_VALUES, (short)2);
        this.primitiveUnits = this.createLiveAnimatedEnumeration(null, "primitiveUnits", SVGOMFilterElement.UNITS_VALUES, (short)1);
        this.x = this.createLiveAnimatedLength(null, "x", "-10%", (short)2, false);
        this.y = this.createLiveAnimatedLength(null, "y", "-10%", (short)1, false);
        this.width = this.createLiveAnimatedLength(null, "width", "120%", (short)2, true);
        this.height = this.createLiveAnimatedLength(null, "height", "120%", (short)1, true);
        this.href = this.createLiveAnimatedString("http://www.w3.org/1999/xlink", "href");
        this.externalResourcesRequired = this.createLiveAnimatedBoolean(null, "externalResourcesRequired", false);
    }
    
    public String getLocalName() {
        return "filter";
    }
    
    public SVGAnimatedEnumeration getFilterUnits() {
        return (SVGAnimatedEnumeration)this.filterUnits;
    }
    
    public SVGAnimatedEnumeration getPrimitiveUnits() {
        return (SVGAnimatedEnumeration)this.primitiveUnits;
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
    
    public SVGAnimatedInteger getFilterResX() {
        throw new UnsupportedOperationException("SVGFilterElement.getFilterResX is not implemented");
    }
    
    public SVGAnimatedInteger getFilterResY() {
        throw new UnsupportedOperationException("SVGFilterElement.getFilterResY is not implemented");
    }
    
    public void setFilterRes(final int n, final int n2) {
        throw new UnsupportedOperationException("SVGFilterElement.setFilterRes is not implemented");
    }
    
    public SVGAnimatedString getHref() {
        return (SVGAnimatedString)this.href;
    }
    
    public SVGAnimatedBoolean getExternalResourcesRequired() {
        return (SVGAnimatedBoolean)this.externalResourcesRequired;
    }
    
    protected DoublyIndexedTable getTraitInformationTable() {
        return SVGOMFilterElement.xmlTraitInformation;
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
    
    protected AttributeInitializer getAttributeInitializer() {
        return SVGOMFilterElement.attributeInitializer;
    }
    
    protected Node newNode() {
        return new SVGOMFilterElement();
    }
    
    static {
        final DoublyIndexedTable xmlTraitInformation = new DoublyIndexedTable(SVGStylableElement.xmlTraitInformation);
        xmlTraitInformation.put(null, "filterUnits", new TraitInformation(true, 15));
        xmlTraitInformation.put(null, "primitiveUnits", new TraitInformation(true, 15));
        xmlTraitInformation.put(null, "x", new TraitInformation(true, 3, (short)1));
        xmlTraitInformation.put(null, "y", new TraitInformation(true, 3, (short)2));
        xmlTraitInformation.put(null, "width", new TraitInformation(true, 3, (short)1));
        xmlTraitInformation.put(null, "height", new TraitInformation(true, 3, (short)2));
        xmlTraitInformation.put(null, "filterRes", new TraitInformation(true, 4));
        SVGOMFilterElement.xmlTraitInformation = xmlTraitInformation;
        (attributeInitializer = new AttributeInitializer(4)).addAttribute("http://www.w3.org/2000/xmlns/", null, "xmlns:xlink", "http://www.w3.org/1999/xlink");
        SVGOMFilterElement.attributeInitializer.addAttribute("http://www.w3.org/1999/xlink", "xlink", "type", "simple");
        SVGOMFilterElement.attributeInitializer.addAttribute("http://www.w3.org/1999/xlink", "xlink", "show", "other");
        SVGOMFilterElement.attributeInitializer.addAttribute("http://www.w3.org/1999/xlink", "xlink", "actuate", "onLoad");
        UNITS_VALUES = new String[] { "", "userSpaceOnUse", "objectBoundingBox" };
    }
}
