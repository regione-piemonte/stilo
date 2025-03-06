// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.dom.svg;

import org.w3c.dom.svg.SVGAnimatedNumber;
import org.w3c.dom.svg.SVGAnimatedNumberList;
import org.w3c.dom.svg.SVGAnimatedEnumeration;
import org.apache.batik.dom.AbstractDocument;
import org.apache.batik.util.DoublyIndexedTable;
import org.w3c.dom.svg.SVGComponentTransferFunctionElement;

public abstract class SVGOMComponentTransferFunctionElement extends SVGOMElement implements SVGComponentTransferFunctionElement
{
    protected static DoublyIndexedTable xmlTraitInformation;
    protected static final String[] TYPE_VALUES;
    protected SVGOMAnimatedEnumeration type;
    protected SVGOMAnimatedNumberList tableValues;
    protected SVGOMAnimatedNumber slope;
    protected SVGOMAnimatedNumber intercept;
    protected SVGOMAnimatedNumber amplitude;
    protected SVGOMAnimatedNumber exponent;
    protected SVGOMAnimatedNumber offset;
    
    protected SVGOMComponentTransferFunctionElement() {
    }
    
    protected SVGOMComponentTransferFunctionElement(final String s, final AbstractDocument abstractDocument) {
        super(s, abstractDocument);
        this.initializeLiveAttributes();
    }
    
    protected void initializeAllLiveAttributes() {
        super.initializeAllLiveAttributes();
        this.initializeLiveAttributes();
    }
    
    private void initializeLiveAttributes() {
        this.type = this.createLiveAnimatedEnumeration(null, "type", SVGOMComponentTransferFunctionElement.TYPE_VALUES, (short)1);
        this.tableValues = this.createLiveAnimatedNumberList(null, "tableValues", "", false);
        this.slope = this.createLiveAnimatedNumber(null, "slope", 1.0f);
        this.intercept = this.createLiveAnimatedNumber(null, "intercept", 0.0f);
        this.amplitude = this.createLiveAnimatedNumber(null, "amplitude", 1.0f);
        this.exponent = this.createLiveAnimatedNumber(null, "exponent", 1.0f);
        this.offset = this.createLiveAnimatedNumber(null, "exponent", 0.0f);
    }
    
    public SVGAnimatedEnumeration getType() {
        return (SVGAnimatedEnumeration)this.type;
    }
    
    public SVGAnimatedNumberList getTableValues() {
        throw new UnsupportedOperationException("SVGComponentTransferFunctionElement.getTableValues is not implemented");
    }
    
    public SVGAnimatedNumber getSlope() {
        return (SVGAnimatedNumber)this.slope;
    }
    
    public SVGAnimatedNumber getIntercept() {
        return (SVGAnimatedNumber)this.intercept;
    }
    
    public SVGAnimatedNumber getAmplitude() {
        return (SVGAnimatedNumber)this.amplitude;
    }
    
    public SVGAnimatedNumber getExponent() {
        return (SVGAnimatedNumber)this.exponent;
    }
    
    public SVGAnimatedNumber getOffset() {
        return (SVGAnimatedNumber)this.offset;
    }
    
    protected DoublyIndexedTable getTraitInformationTable() {
        return SVGOMComponentTransferFunctionElement.xmlTraitInformation;
    }
    
    static {
        final DoublyIndexedTable xmlTraitInformation = new DoublyIndexedTable(SVGOMElement.xmlTraitInformation);
        xmlTraitInformation.put(null, "type", new TraitInformation(true, 15));
        xmlTraitInformation.put(null, "tableValues", new TraitInformation(true, 13));
        xmlTraitInformation.put(null, "slope", new TraitInformation(true, 2));
        xmlTraitInformation.put(null, "intercept", new TraitInformation(true, 2));
        xmlTraitInformation.put(null, "amplitude", new TraitInformation(true, 2));
        xmlTraitInformation.put(null, "exponent", new TraitInformation(true, 2));
        xmlTraitInformation.put(null, "offset", new TraitInformation(true, 2));
        SVGOMComponentTransferFunctionElement.xmlTraitInformation = xmlTraitInformation;
        TYPE_VALUES = new String[] { "", "identity", "table", "discrete", "linear", "gamma" };
    }
}
