// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.dom.svg;

import org.w3c.dom.Node;
import org.w3c.dom.svg.SVGAnimatedNumber;
import org.apache.batik.dom.AbstractDocument;
import org.apache.batik.util.DoublyIndexedTable;
import org.w3c.dom.svg.SVGFESpotLightElement;

public class SVGOMFESpotLightElement extends SVGOMElement implements SVGFESpotLightElement
{
    protected static DoublyIndexedTable xmlTraitInformation;
    protected SVGOMAnimatedNumber x;
    protected SVGOMAnimatedNumber y;
    protected SVGOMAnimatedNumber z;
    protected SVGOMAnimatedNumber pointsAtX;
    protected SVGOMAnimatedNumber pointsAtY;
    protected SVGOMAnimatedNumber pointsAtZ;
    protected SVGOMAnimatedNumber specularExponent;
    protected SVGOMAnimatedNumber limitingConeAngle;
    
    protected SVGOMFESpotLightElement() {
    }
    
    public SVGOMFESpotLightElement(final String s, final AbstractDocument abstractDocument) {
        super(s, abstractDocument);
        this.initializeLiveAttributes();
    }
    
    protected void initializeAllLiveAttributes() {
        super.initializeAllLiveAttributes();
        this.initializeLiveAttributes();
    }
    
    private void initializeLiveAttributes() {
        this.x = this.createLiveAnimatedNumber(null, "x", 0.0f);
        this.y = this.createLiveAnimatedNumber(null, "y", 0.0f);
        this.z = this.createLiveAnimatedNumber(null, "z", 0.0f);
        this.pointsAtX = this.createLiveAnimatedNumber(null, "pointsAtX", 0.0f);
        this.pointsAtY = this.createLiveAnimatedNumber(null, "pointsAtY", 0.0f);
        this.pointsAtZ = this.createLiveAnimatedNumber(null, "pointsAtZ", 0.0f);
        this.specularExponent = this.createLiveAnimatedNumber(null, "specularExponent", 1.0f);
        this.limitingConeAngle = this.createLiveAnimatedNumber(null, "limitingConeAngle", 0.0f);
    }
    
    public String getLocalName() {
        return "feSpotLight";
    }
    
    public SVGAnimatedNumber getX() {
        return (SVGAnimatedNumber)this.x;
    }
    
    public SVGAnimatedNumber getY() {
        return (SVGAnimatedNumber)this.y;
    }
    
    public SVGAnimatedNumber getZ() {
        return (SVGAnimatedNumber)this.z;
    }
    
    public SVGAnimatedNumber getPointsAtX() {
        return (SVGAnimatedNumber)this.pointsAtX;
    }
    
    public SVGAnimatedNumber getPointsAtY() {
        return (SVGAnimatedNumber)this.pointsAtY;
    }
    
    public SVGAnimatedNumber getPointsAtZ() {
        return (SVGAnimatedNumber)this.pointsAtZ;
    }
    
    public SVGAnimatedNumber getSpecularExponent() {
        return (SVGAnimatedNumber)this.specularExponent;
    }
    
    public SVGAnimatedNumber getLimitingConeAngle() {
        return (SVGAnimatedNumber)this.limitingConeAngle;
    }
    
    protected Node newNode() {
        return new SVGOMFESpotLightElement();
    }
    
    protected DoublyIndexedTable getTraitInformationTable() {
        return SVGOMFESpotLightElement.xmlTraitInformation;
    }
    
    static {
        final DoublyIndexedTable xmlTraitInformation = new DoublyIndexedTable(SVGOMElement.xmlTraitInformation);
        xmlTraitInformation.put(null, "x", new TraitInformation(true, 2));
        xmlTraitInformation.put(null, "y", new TraitInformation(true, 2));
        xmlTraitInformation.put(null, "z", new TraitInformation(true, 2));
        xmlTraitInformation.put(null, "pointsAtX", new TraitInformation(true, 2));
        xmlTraitInformation.put(null, "pointsAtY", new TraitInformation(true, 2));
        xmlTraitInformation.put(null, "pointsAtZ", new TraitInformation(true, 2));
        xmlTraitInformation.put(null, "specularExponent", new TraitInformation(true, 2));
        xmlTraitInformation.put(null, "limitingConeAngle", new TraitInformation(true, 2));
        SVGOMFESpotLightElement.xmlTraitInformation = xmlTraitInformation;
    }
}
