// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.dom.svg;

import org.w3c.dom.Node;
import org.w3c.dom.svg.SVGAnimatedNumber;
import org.w3c.dom.svg.SVGAnimatedString;
import org.apache.batik.dom.AbstractDocument;
import org.apache.batik.util.DoublyIndexedTable;
import org.w3c.dom.svg.SVGFEOffsetElement;

public class SVGOMFEOffsetElement extends SVGOMFilterPrimitiveStandardAttributes implements SVGFEOffsetElement
{
    protected static DoublyIndexedTable xmlTraitInformation;
    protected SVGOMAnimatedString in;
    protected SVGOMAnimatedNumber dx;
    protected SVGOMAnimatedNumber dy;
    
    protected SVGOMFEOffsetElement() {
    }
    
    public SVGOMFEOffsetElement(final String s, final AbstractDocument abstractDocument) {
        super(s, abstractDocument);
        this.initializeLiveAttributes();
    }
    
    protected void initializeAllLiveAttributes() {
        super.initializeAllLiveAttributes();
        this.initializeLiveAttributes();
    }
    
    private void initializeLiveAttributes() {
        this.in = this.createLiveAnimatedString(null, "in");
        this.dx = this.createLiveAnimatedNumber(null, "dx", 0.0f);
        this.dy = this.createLiveAnimatedNumber(null, "dy", 0.0f);
    }
    
    public String getLocalName() {
        return "feOffset";
    }
    
    public SVGAnimatedString getIn1() {
        return (SVGAnimatedString)this.in;
    }
    
    public SVGAnimatedNumber getDx() {
        return (SVGAnimatedNumber)this.dx;
    }
    
    public SVGAnimatedNumber getDy() {
        return (SVGAnimatedNumber)this.dy;
    }
    
    protected Node newNode() {
        return new SVGOMFEOffsetElement();
    }
    
    protected DoublyIndexedTable getTraitInformationTable() {
        return SVGOMFEOffsetElement.xmlTraitInformation;
    }
    
    static {
        final DoublyIndexedTable xmlTraitInformation = new DoublyIndexedTable(SVGOMFilterPrimitiveStandardAttributes.xmlTraitInformation);
        xmlTraitInformation.put(null, "in", new TraitInformation(true, 16));
        xmlTraitInformation.put(null, "dx", new TraitInformation(true, 2));
        xmlTraitInformation.put(null, "dy", new TraitInformation(true, 2));
        SVGOMFEOffsetElement.xmlTraitInformation = xmlTraitInformation;
    }
}
