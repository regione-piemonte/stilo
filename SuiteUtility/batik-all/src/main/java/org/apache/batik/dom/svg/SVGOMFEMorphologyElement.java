// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.dom.svg;

import org.w3c.dom.Node;
import org.w3c.dom.svg.SVGAnimatedNumber;
import org.w3c.dom.svg.SVGAnimatedEnumeration;
import org.w3c.dom.svg.SVGAnimatedString;
import org.apache.batik.dom.AbstractDocument;
import org.apache.batik.util.DoublyIndexedTable;
import org.w3c.dom.svg.SVGFEMorphologyElement;

public class SVGOMFEMorphologyElement extends SVGOMFilterPrimitiveStandardAttributes implements SVGFEMorphologyElement
{
    protected static DoublyIndexedTable xmlTraitInformation;
    protected static final String[] OPERATOR_VALUES;
    protected SVGOMAnimatedString in;
    protected SVGOMAnimatedEnumeration operator;
    
    protected SVGOMFEMorphologyElement() {
    }
    
    public SVGOMFEMorphologyElement(final String s, final AbstractDocument abstractDocument) {
        super(s, abstractDocument);
        this.initializeLiveAttributes();
    }
    
    protected void initializeAllLiveAttributes() {
        super.initializeAllLiveAttributes();
        this.initializeLiveAttributes();
    }
    
    private void initializeLiveAttributes() {
        this.in = this.createLiveAnimatedString(null, "in");
        this.operator = this.createLiveAnimatedEnumeration(null, "operator", SVGOMFEMorphologyElement.OPERATOR_VALUES, (short)1);
    }
    
    public String getLocalName() {
        return "feMorphology";
    }
    
    public SVGAnimatedString getIn1() {
        return (SVGAnimatedString)this.in;
    }
    
    public SVGAnimatedEnumeration getOperator() {
        return (SVGAnimatedEnumeration)this.operator;
    }
    
    public SVGAnimatedNumber getRadiusX() {
        throw new UnsupportedOperationException("SVGFEMorphologyElement.getRadiusX is not implemented");
    }
    
    public SVGAnimatedNumber getRadiusY() {
        throw new UnsupportedOperationException("SVGFEMorphologyElement.getRadiusY is not implemented");
    }
    
    protected Node newNode() {
        return new SVGOMFEMorphologyElement();
    }
    
    protected DoublyIndexedTable getTraitInformationTable() {
        return SVGOMFEMorphologyElement.xmlTraitInformation;
    }
    
    static {
        final DoublyIndexedTable xmlTraitInformation = new DoublyIndexedTable(SVGOMFilterPrimitiveStandardAttributes.xmlTraitInformation);
        xmlTraitInformation.put(null, "in", new TraitInformation(true, 16));
        xmlTraitInformation.put(null, "operator", new TraitInformation(true, 15));
        xmlTraitInformation.put(null, "radius", new TraitInformation(true, 4));
        SVGOMFEMorphologyElement.xmlTraitInformation = xmlTraitInformation;
        OPERATOR_VALUES = new String[] { "", "erode", "dilate" };
    }
}
