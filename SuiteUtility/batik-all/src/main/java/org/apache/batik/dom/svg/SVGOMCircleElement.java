// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.dom.svg;

import org.w3c.dom.Node;
import org.w3c.dom.svg.SVGAnimatedLength;
import org.apache.batik.dom.AbstractDocument;
import org.apache.batik.util.DoublyIndexedTable;
import org.w3c.dom.svg.SVGCircleElement;

public class SVGOMCircleElement extends SVGGraphicsElement implements SVGCircleElement
{
    protected static DoublyIndexedTable xmlTraitInformation;
    protected SVGOMAnimatedLength cx;
    protected SVGOMAnimatedLength cy;
    protected SVGOMAnimatedLength r;
    
    protected SVGOMCircleElement() {
    }
    
    public SVGOMCircleElement(final String s, final AbstractDocument abstractDocument) {
        super(s, abstractDocument);
        this.initializeLiveAttributes();
    }
    
    protected void initializeAllLiveAttributes() {
        super.initializeAllLiveAttributes();
        this.initializeLiveAttributes();
    }
    
    private void initializeLiveAttributes() {
        this.cx = this.createLiveAnimatedLength(null, "cx", "0", (short)2, false);
        this.cy = this.createLiveAnimatedLength(null, "cy", "0", (short)1, false);
        this.r = this.createLiveAnimatedLength(null, "r", null, (short)0, true);
    }
    
    public String getLocalName() {
        return "circle";
    }
    
    public SVGAnimatedLength getCx() {
        return (SVGAnimatedLength)this.cx;
    }
    
    public SVGAnimatedLength getCy() {
        return (SVGAnimatedLength)this.cy;
    }
    
    public SVGAnimatedLength getR() {
        return (SVGAnimatedLength)this.r;
    }
    
    protected Node newNode() {
        return new SVGOMCircleElement();
    }
    
    protected DoublyIndexedTable getTraitInformationTable() {
        return SVGOMCircleElement.xmlTraitInformation;
    }
    
    static {
        final DoublyIndexedTable xmlTraitInformation = new DoublyIndexedTable(SVGGraphicsElement.xmlTraitInformation);
        xmlTraitInformation.put(null, "cx", new TraitInformation(true, 3, (short)1));
        xmlTraitInformation.put(null, "cy", new TraitInformation(true, 3, (short)2));
        xmlTraitInformation.put(null, "r", new TraitInformation(true, 3, (short)3));
        SVGOMCircleElement.xmlTraitInformation = xmlTraitInformation;
    }
}
