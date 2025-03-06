// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.dom.svg;

import org.w3c.dom.Node;
import org.w3c.dom.svg.SVGAnimatedNumber;
import org.w3c.dom.svg.SVGAnimatedString;
import org.apache.batik.dom.AbstractDocument;
import org.apache.batik.util.DoublyIndexedTable;
import org.w3c.dom.svg.SVGFEGaussianBlurElement;

public class SVGOMFEGaussianBlurElement extends SVGOMFilterPrimitiveStandardAttributes implements SVGFEGaussianBlurElement
{
    protected static DoublyIndexedTable xmlTraitInformation;
    protected SVGOMAnimatedString in;
    
    protected SVGOMFEGaussianBlurElement() {
    }
    
    public SVGOMFEGaussianBlurElement(final String s, final AbstractDocument abstractDocument) {
        super(s, abstractDocument);
        this.initializeLiveAttributes();
    }
    
    protected void initializeAllLiveAttributes() {
        super.initializeAllLiveAttributes();
        this.initializeLiveAttributes();
    }
    
    private void initializeLiveAttributes() {
        this.in = this.createLiveAnimatedString(null, "in");
    }
    
    public String getLocalName() {
        return "feGaussianBlur";
    }
    
    public SVGAnimatedString getIn1() {
        return (SVGAnimatedString)this.in;
    }
    
    public SVGAnimatedNumber getStdDeviationX() {
        throw new UnsupportedOperationException("SVGFEGaussianBlurElement.getStdDeviationX is not implemented");
    }
    
    public SVGAnimatedNumber getStdDeviationY() {
        throw new UnsupportedOperationException("SVGFEGaussianBlurElement.getStdDeviationY is not implemented");
    }
    
    public void setStdDeviation(final float f, final float f2) {
        this.setAttributeNS(null, "stdDeviation", Float.toString(f) + " " + Float.toString(f2));
    }
    
    protected Node newNode() {
        return new SVGOMFEGaussianBlurElement();
    }
    
    protected DoublyIndexedTable getTraitInformationTable() {
        return SVGOMFEGaussianBlurElement.xmlTraitInformation;
    }
    
    static {
        final DoublyIndexedTable xmlTraitInformation = new DoublyIndexedTable(SVGOMFilterPrimitiveStandardAttributes.xmlTraitInformation);
        xmlTraitInformation.put(null, "in", new TraitInformation(true, 16));
        xmlTraitInformation.put(null, "stdDeviation", new TraitInformation(true, 4));
        SVGOMFEGaussianBlurElement.xmlTraitInformation = xmlTraitInformation;
    }
}
