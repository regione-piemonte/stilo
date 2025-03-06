// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.dom.svg;

import org.w3c.dom.Node;
import org.w3c.dom.svg.SVGAnimatedNumber;
import org.apache.batik.dom.AbstractDocument;
import org.apache.batik.util.DoublyIndexedTable;
import org.w3c.dom.svg.SVGFEDistantLightElement;

public class SVGOMFEDistantLightElement extends SVGOMElement implements SVGFEDistantLightElement
{
    protected static DoublyIndexedTable xmlTraitInformation;
    protected SVGOMAnimatedNumber azimuth;
    protected SVGOMAnimatedNumber elevation;
    
    protected SVGOMFEDistantLightElement() {
    }
    
    public SVGOMFEDistantLightElement(final String s, final AbstractDocument abstractDocument) {
        super(s, abstractDocument);
        this.initializeLiveAttributes();
    }
    
    protected void initializeAllLiveAttributes() {
        super.initializeAllLiveAttributes();
        this.initializeLiveAttributes();
    }
    
    private void initializeLiveAttributes() {
        this.azimuth = this.createLiveAnimatedNumber(null, "azimuth", 0.0f);
        this.elevation = this.createLiveAnimatedNumber(null, "elevation", 0.0f);
    }
    
    public String getLocalName() {
        return "feDistantLight";
    }
    
    public SVGAnimatedNumber getAzimuth() {
        return (SVGAnimatedNumber)this.azimuth;
    }
    
    public SVGAnimatedNumber getElevation() {
        return (SVGAnimatedNumber)this.elevation;
    }
    
    protected Node newNode() {
        return new SVGOMFEDistantLightElement();
    }
    
    protected DoublyIndexedTable getTraitInformationTable() {
        return SVGOMFEDistantLightElement.xmlTraitInformation;
    }
    
    static {
        final DoublyIndexedTable xmlTraitInformation = new DoublyIndexedTable(SVGOMElement.xmlTraitInformation);
        xmlTraitInformation.put(null, "azimuth", new TraitInformation(true, 2));
        xmlTraitInformation.put(null, "elevation", new TraitInformation(true, 2));
        SVGOMFEDistantLightElement.xmlTraitInformation = xmlTraitInformation;
    }
}
