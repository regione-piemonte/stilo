// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.dom.svg;

import org.w3c.dom.Node;
import org.w3c.dom.svg.SVGAnimatedBoolean;
import org.w3c.dom.svg.SVGAnimatedString;
import org.w3c.dom.svg.SVGAnimatedEnumeration;
import org.w3c.dom.svg.SVGAnimatedTransformList;
import org.apache.batik.dom.AbstractDocument;
import org.apache.batik.util.DoublyIndexedTable;
import org.w3c.dom.svg.SVGGradientElement;

public abstract class SVGOMGradientElement extends SVGStylableElement implements SVGGradientElement
{
    protected static DoublyIndexedTable xmlTraitInformation;
    protected static final AttributeInitializer attributeInitializer;
    protected static final String[] UNITS_VALUES;
    protected static final String[] SPREAD_METHOD_VALUES;
    protected SVGOMAnimatedEnumeration gradientUnits;
    protected SVGOMAnimatedEnumeration spreadMethod;
    protected SVGOMAnimatedString href;
    protected SVGOMAnimatedBoolean externalResourcesRequired;
    
    protected SVGOMGradientElement() {
    }
    
    protected SVGOMGradientElement(final String s, final AbstractDocument abstractDocument) {
        super(s, abstractDocument);
        this.initializeLiveAttributes();
    }
    
    protected void initializeAllLiveAttributes() {
        super.initializeAllLiveAttributes();
        this.initializeLiveAttributes();
    }
    
    private void initializeLiveAttributes() {
        this.gradientUnits = this.createLiveAnimatedEnumeration(null, "gradientUnits", SVGOMGradientElement.UNITS_VALUES, (short)2);
        this.spreadMethod = this.createLiveAnimatedEnumeration(null, "spreadMethod", SVGOMGradientElement.SPREAD_METHOD_VALUES, (short)1);
        this.href = this.createLiveAnimatedString("http://www.w3.org/1999/xlink", "href");
        this.externalResourcesRequired = this.createLiveAnimatedBoolean(null, "externalResourcesRequired", false);
    }
    
    public SVGAnimatedTransformList getGradientTransform() {
        throw new UnsupportedOperationException("SVGGradientElement.getGradientTransform is not implemented");
    }
    
    public SVGAnimatedEnumeration getGradientUnits() {
        return (SVGAnimatedEnumeration)this.gradientUnits;
    }
    
    public SVGAnimatedEnumeration getSpreadMethod() {
        return (SVGAnimatedEnumeration)this.spreadMethod;
    }
    
    public SVGAnimatedString getHref() {
        return (SVGAnimatedString)this.href;
    }
    
    public SVGAnimatedBoolean getExternalResourcesRequired() {
        return (SVGAnimatedBoolean)this.externalResourcesRequired;
    }
    
    protected AttributeInitializer getAttributeInitializer() {
        return SVGOMGradientElement.attributeInitializer;
    }
    
    protected Node newNode() {
        return new SVGOMAElement();
    }
    
    protected DoublyIndexedTable getTraitInformationTable() {
        return SVGOMGradientElement.xmlTraitInformation;
    }
    
    static {
        final DoublyIndexedTable xmlTraitInformation = new DoublyIndexedTable(SVGStylableElement.xmlTraitInformation);
        xmlTraitInformation.put(null, "gradientUnits", new TraitInformation(true, 15));
        xmlTraitInformation.put(null, "spreadMethod", new TraitInformation(true, 15));
        xmlTraitInformation.put(null, "gradientTransform", new TraitInformation(true, 9));
        xmlTraitInformation.put(null, "externalResourcesRequired", new TraitInformation(true, 49));
        xmlTraitInformation.put("http://www.w3.org/1999/xlink", "href", new TraitInformation(true, 10));
        SVGOMGradientElement.xmlTraitInformation = xmlTraitInformation;
        (attributeInitializer = new AttributeInitializer(4)).addAttribute("http://www.w3.org/2000/xmlns/", null, "xmlns:xlink", "http://www.w3.org/1999/xlink");
        SVGOMGradientElement.attributeInitializer.addAttribute("http://www.w3.org/1999/xlink", "xlink", "type", "simple");
        SVGOMGradientElement.attributeInitializer.addAttribute("http://www.w3.org/1999/xlink", "xlink", "show", "other");
        SVGOMGradientElement.attributeInitializer.addAttribute("http://www.w3.org/1999/xlink", "xlink", "actuate", "onLoad");
        UNITS_VALUES = new String[] { "", "userSpaceOnUse", "objectBoundingBox" };
        SPREAD_METHOD_VALUES = new String[] { "", "pad", "reflect", "repeat" };
    }
}
