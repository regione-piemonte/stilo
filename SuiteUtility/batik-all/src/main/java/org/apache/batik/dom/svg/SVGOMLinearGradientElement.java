// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.dom.svg;

import org.w3c.dom.Node;
import org.w3c.dom.svg.SVGAnimatedLength;
import org.apache.batik.dom.AbstractDocument;
import org.apache.batik.util.DoublyIndexedTable;
import org.w3c.dom.svg.SVGLinearGradientElement;

public class SVGOMLinearGradientElement extends SVGOMGradientElement implements SVGLinearGradientElement
{
    protected static DoublyIndexedTable xmlTraitInformation;
    protected SVGOMAnimatedLength x1;
    protected SVGOMAnimatedLength y1;
    protected SVGOMAnimatedLength x2;
    protected SVGOMAnimatedLength y2;
    
    protected SVGOMLinearGradientElement() {
    }
    
    public SVGOMLinearGradientElement(final String s, final AbstractDocument abstractDocument) {
        super(s, abstractDocument);
        this.initializeLiveAttributes();
    }
    
    protected void initializeAllLiveAttributes() {
        super.initializeAllLiveAttributes();
        this.initializeLiveAttributes();
    }
    
    private void initializeLiveAttributes() {
        this.x1 = this.createLiveAnimatedLength(null, "x1", "0%", (short)2, false);
        this.y1 = this.createLiveAnimatedLength(null, "y1", "0%", (short)1, false);
        this.x2 = this.createLiveAnimatedLength(null, "x2", "100%", (short)2, false);
        this.y2 = this.createLiveAnimatedLength(null, "y2", "0%", (short)1, false);
    }
    
    public String getLocalName() {
        return "linearGradient";
    }
    
    public SVGAnimatedLength getX1() {
        return (SVGAnimatedLength)this.x1;
    }
    
    public SVGAnimatedLength getY1() {
        return (SVGAnimatedLength)this.y1;
    }
    
    public SVGAnimatedLength getX2() {
        return (SVGAnimatedLength)this.x2;
    }
    
    public SVGAnimatedLength getY2() {
        return (SVGAnimatedLength)this.y2;
    }
    
    protected Node newNode() {
        return new SVGOMLinearGradientElement();
    }
    
    protected DoublyIndexedTable getTraitInformationTable() {
        return SVGOMLinearGradientElement.xmlTraitInformation;
    }
    
    static {
        final DoublyIndexedTable xmlTraitInformation = new DoublyIndexedTable(SVGOMGradientElement.xmlTraitInformation);
        xmlTraitInformation.put(null, "x", new TraitInformation(true, 3, (short)1));
        xmlTraitInformation.put(null, "y", new TraitInformation(true, 3, (short)2));
        xmlTraitInformation.put(null, "width", new TraitInformation(true, 3, (short)1));
        xmlTraitInformation.put(null, "height", new TraitInformation(true, 3, (short)2));
        SVGOMLinearGradientElement.xmlTraitInformation = xmlTraitInformation;
    }
}
