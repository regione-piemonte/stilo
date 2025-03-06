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
import org.w3c.dom.svg.SVGFECompositeElement;

public class SVGOMFECompositeElement extends SVGOMFilterPrimitiveStandardAttributes implements SVGFECompositeElement
{
    protected static DoublyIndexedTable xmlTraitInformation;
    protected static final String[] OPERATOR_VALUES;
    protected SVGOMAnimatedString in;
    protected SVGOMAnimatedString in2;
    protected SVGOMAnimatedEnumeration operator;
    protected SVGOMAnimatedNumber k1;
    protected SVGOMAnimatedNumber k2;
    protected SVGOMAnimatedNumber k3;
    protected SVGOMAnimatedNumber k4;
    
    protected SVGOMFECompositeElement() {
    }
    
    public SVGOMFECompositeElement(final String s, final AbstractDocument abstractDocument) {
        super(s, abstractDocument);
        this.initializeLiveAttributes();
    }
    
    protected void initializeAllLiveAttributes() {
        super.initializeAllLiveAttributes();
        this.initializeLiveAttributes();
    }
    
    private void initializeLiveAttributes() {
        this.in = this.createLiveAnimatedString(null, "in");
        this.in2 = this.createLiveAnimatedString(null, "in2");
        this.operator = this.createLiveAnimatedEnumeration(null, "operator", SVGOMFECompositeElement.OPERATOR_VALUES, (short)1);
        this.k1 = this.createLiveAnimatedNumber(null, "k1", 0.0f);
        this.k2 = this.createLiveAnimatedNumber(null, "k2", 0.0f);
        this.k3 = this.createLiveAnimatedNumber(null, "k3", 0.0f);
        this.k4 = this.createLiveAnimatedNumber(null, "k4", 0.0f);
    }
    
    public String getLocalName() {
        return "feComposite";
    }
    
    public SVGAnimatedString getIn1() {
        return (SVGAnimatedString)this.in;
    }
    
    public SVGAnimatedString getIn2() {
        return (SVGAnimatedString)this.in2;
    }
    
    public SVGAnimatedEnumeration getOperator() {
        return (SVGAnimatedEnumeration)this.operator;
    }
    
    public SVGAnimatedNumber getK1() {
        return (SVGAnimatedNumber)this.k1;
    }
    
    public SVGAnimatedNumber getK2() {
        return (SVGAnimatedNumber)this.k2;
    }
    
    public SVGAnimatedNumber getK3() {
        return (SVGAnimatedNumber)this.k3;
    }
    
    public SVGAnimatedNumber getK4() {
        return (SVGAnimatedNumber)this.k4;
    }
    
    protected Node newNode() {
        return new SVGOMFECompositeElement();
    }
    
    protected DoublyIndexedTable getTraitInformationTable() {
        return SVGOMFECompositeElement.xmlTraitInformation;
    }
    
    static {
        final DoublyIndexedTable xmlTraitInformation = new DoublyIndexedTable(SVGOMFilterPrimitiveStandardAttributes.xmlTraitInformation);
        xmlTraitInformation.put(null, "in", new TraitInformation(true, 16));
        xmlTraitInformation.put(null, "in2", new TraitInformation(true, 16));
        xmlTraitInformation.put(null, "operator", new TraitInformation(true, 15));
        xmlTraitInformation.put(null, "k1", new TraitInformation(true, 2));
        xmlTraitInformation.put(null, "k2", new TraitInformation(true, 2));
        xmlTraitInformation.put(null, "k3", new TraitInformation(true, 2));
        xmlTraitInformation.put(null, "k4", new TraitInformation(true, 2));
        SVGOMFECompositeElement.xmlTraitInformation = xmlTraitInformation;
        OPERATOR_VALUES = new String[] { "", "over", "in", "out", "atop", "xor", "arithmetic" };
    }
}
