// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.dom.svg;

import org.w3c.dom.Node;
import org.w3c.dom.svg.SVGAnimatedNumber;
import org.w3c.dom.svg.SVGAnimatedString;
import org.apache.batik.dom.AbstractDocument;
import org.apache.batik.util.DoublyIndexedTable;
import org.w3c.dom.svg.SVGFESpecularLightingElement;

public class SVGOMFESpecularLightingElement extends SVGOMFilterPrimitiveStandardAttributes implements SVGFESpecularLightingElement
{
    protected static DoublyIndexedTable xmlTraitInformation;
    protected SVGOMAnimatedString in;
    protected SVGOMAnimatedNumber surfaceScale;
    protected SVGOMAnimatedNumber specularConstant;
    protected SVGOMAnimatedNumber specularExponent;
    
    protected SVGOMFESpecularLightingElement() {
    }
    
    public SVGOMFESpecularLightingElement(final String s, final AbstractDocument abstractDocument) {
        super(s, abstractDocument);
        this.initializeLiveAttributes();
    }
    
    protected void initializeAllLiveAttributes() {
        super.initializeAllLiveAttributes();
        this.initializeLiveAttributes();
    }
    
    private void initializeLiveAttributes() {
        this.in = this.createLiveAnimatedString(null, "in");
        this.surfaceScale = this.createLiveAnimatedNumber(null, "surfaceScale", 1.0f);
        this.specularConstant = this.createLiveAnimatedNumber(null, "specularConstant", 1.0f);
        this.specularExponent = this.createLiveAnimatedNumber(null, "specularExponent", 1.0f);
    }
    
    public String getLocalName() {
        return "feSpecularLighting";
    }
    
    public SVGAnimatedString getIn1() {
        return (SVGAnimatedString)this.in;
    }
    
    public SVGAnimatedNumber getSurfaceScale() {
        return (SVGAnimatedNumber)this.surfaceScale;
    }
    
    public SVGAnimatedNumber getSpecularConstant() {
        return (SVGAnimatedNumber)this.specularConstant;
    }
    
    public SVGAnimatedNumber getSpecularExponent() {
        return (SVGAnimatedNumber)this.specularExponent;
    }
    
    protected Node newNode() {
        return new SVGOMFESpecularLightingElement();
    }
    
    protected DoublyIndexedTable getTraitInformationTable() {
        return SVGOMFESpecularLightingElement.xmlTraitInformation;
    }
    
    static {
        final DoublyIndexedTable xmlTraitInformation = new DoublyIndexedTable(SVGOMFilterPrimitiveStandardAttributes.xmlTraitInformation);
        xmlTraitInformation.put(null, "in", new TraitInformation(true, 16));
        xmlTraitInformation.put(null, "surfaceScale", new TraitInformation(true, 2));
        xmlTraitInformation.put(null, "specularConstant", new TraitInformation(true, 2));
        xmlTraitInformation.put(null, "specularExponent", new TraitInformation(true, 2));
        SVGOMFESpecularLightingElement.xmlTraitInformation = xmlTraitInformation;
    }
}
