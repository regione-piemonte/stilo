// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.dom.svg;

import org.w3c.dom.Node;
import org.w3c.dom.svg.SVGAnimatedNumber;
import org.apache.batik.dom.AbstractDocument;
import org.apache.batik.util.DoublyIndexedTable;
import org.w3c.dom.svg.SVGFEPointLightElement;

public class SVGOMFEPointLightElement extends SVGOMElement implements SVGFEPointLightElement
{
    protected static DoublyIndexedTable xmlTraitInformation;
    protected SVGOMAnimatedNumber x;
    protected SVGOMAnimatedNumber y;
    protected SVGOMAnimatedNumber z;
    
    protected SVGOMFEPointLightElement() {
    }
    
    public SVGOMFEPointLightElement(final String s, final AbstractDocument abstractDocument) {
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
    }
    
    public String getLocalName() {
        return "fePointLight";
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
    
    protected Node newNode() {
        return new SVGOMFEPointLightElement();
    }
    
    protected DoublyIndexedTable getTraitInformationTable() {
        return SVGOMFEPointLightElement.xmlTraitInformation;
    }
    
    static {
        final DoublyIndexedTable xmlTraitInformation = new DoublyIndexedTable(SVGOMElement.xmlTraitInformation);
        xmlTraitInformation.put(null, "x", new TraitInformation(true, 2));
        xmlTraitInformation.put(null, "y", new TraitInformation(true, 2));
        xmlTraitInformation.put(null, "z", new TraitInformation(true, 2));
        SVGOMFEPointLightElement.xmlTraitInformation = xmlTraitInformation;
    }
}
