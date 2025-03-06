// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.dom.svg;

import org.w3c.dom.Node;
import org.w3c.dom.svg.SVGAnimatedBoolean;
import org.w3c.dom.svg.SVGAnimatedNumber;
import org.w3c.dom.svg.SVGAnimatedInteger;
import org.w3c.dom.svg.SVGAnimatedNumberList;
import org.w3c.dom.svg.SVGAnimatedEnumeration;
import org.w3c.dom.svg.SVGAnimatedString;
import org.apache.batik.dom.AbstractDocument;
import org.apache.batik.util.DoublyIndexedTable;
import org.w3c.dom.svg.SVGFEConvolveMatrixElement;

public class SVGOMFEConvolveMatrixElement extends SVGOMFilterPrimitiveStandardAttributes implements SVGFEConvolveMatrixElement
{
    protected static DoublyIndexedTable xmlTraitInformation;
    protected static final String[] EDGE_MODE_VALUES;
    protected SVGOMAnimatedString in;
    protected SVGOMAnimatedEnumeration edgeMode;
    protected SVGOMAnimatedNumber bias;
    protected SVGOMAnimatedBoolean preserveAlpha;
    
    protected SVGOMFEConvolveMatrixElement() {
    }
    
    public SVGOMFEConvolveMatrixElement(final String s, final AbstractDocument abstractDocument) {
        super(s, abstractDocument);
        this.initializeLiveAttributes();
    }
    
    protected void initializeAllLiveAttributes() {
        super.initializeAllLiveAttributes();
        this.initializeLiveAttributes();
    }
    
    private void initializeLiveAttributes() {
        this.in = this.createLiveAnimatedString(null, "in");
        this.edgeMode = this.createLiveAnimatedEnumeration(null, "edgeMode", SVGOMFEConvolveMatrixElement.EDGE_MODE_VALUES, (short)1);
        this.bias = this.createLiveAnimatedNumber(null, "bias", 0.0f);
        this.preserveAlpha = this.createLiveAnimatedBoolean(null, "preserveAlpha", false);
    }
    
    public String getLocalName() {
        return "feConvolveMatrix";
    }
    
    public SVGAnimatedString getIn1() {
        return (SVGAnimatedString)this.in;
    }
    
    public SVGAnimatedEnumeration getEdgeMode() {
        return (SVGAnimatedEnumeration)this.edgeMode;
    }
    
    public SVGAnimatedNumberList getKernelMatrix() {
        throw new UnsupportedOperationException("SVGFEConvolveMatrixElement.getKernelMatrix is not implemented");
    }
    
    public SVGAnimatedInteger getOrderX() {
        throw new UnsupportedOperationException("SVGFEConvolveMatrixElement.getOrderX is not implemented");
    }
    
    public SVGAnimatedInteger getOrderY() {
        throw new UnsupportedOperationException("SVGFEConvolveMatrixElement.getOrderY is not implemented");
    }
    
    public SVGAnimatedInteger getTargetX() {
        throw new UnsupportedOperationException("SVGFEConvolveMatrixElement.getTargetX is not implemented");
    }
    
    public SVGAnimatedInteger getTargetY() {
        throw new UnsupportedOperationException("SVGFEConvolveMatrixElement.getTargetY is not implemented");
    }
    
    public SVGAnimatedNumber getDivisor() {
        throw new UnsupportedOperationException("SVGFEConvolveMatrixElement.getDivisor is not implemented");
    }
    
    public SVGAnimatedNumber getBias() {
        return (SVGAnimatedNumber)this.bias;
    }
    
    public SVGAnimatedNumber getKernelUnitLengthX() {
        throw new UnsupportedOperationException("SVGFEConvolveMatrixElement.getKernelUnitLengthX is not implemented");
    }
    
    public SVGAnimatedNumber getKernelUnitLengthY() {
        throw new UnsupportedOperationException("SVGFEConvolveMatrixElement.getKernelUnitLengthY is not implemented");
    }
    
    public SVGAnimatedBoolean getPreserveAlpha() {
        return (SVGAnimatedBoolean)this.preserveAlpha;
    }
    
    protected Node newNode() {
        return new SVGOMFEConvolveMatrixElement();
    }
    
    protected DoublyIndexedTable getTraitInformationTable() {
        return SVGOMFEConvolveMatrixElement.xmlTraitInformation;
    }
    
    static {
        final DoublyIndexedTable xmlTraitInformation = new DoublyIndexedTable(SVGOMFilterPrimitiveStandardAttributes.xmlTraitInformation);
        xmlTraitInformation.put(null, "in", new TraitInformation(true, 16));
        xmlTraitInformation.put(null, "order", new TraitInformation(true, 4));
        xmlTraitInformation.put(null, "kernelUnitLength", new TraitInformation(true, 4));
        xmlTraitInformation.put(null, "kernelMatrix", new TraitInformation(true, 13));
        xmlTraitInformation.put(null, "divisor", new TraitInformation(true, 2));
        xmlTraitInformation.put(null, "bias", new TraitInformation(true, 2));
        xmlTraitInformation.put(null, "targetX", new TraitInformation(true, 1));
        xmlTraitInformation.put(null, "targetY", new TraitInformation(true, 1));
        xmlTraitInformation.put(null, "edgeMode", new TraitInformation(true, 15));
        xmlTraitInformation.put(null, "preserveAlpha", new TraitInformation(true, 49));
        SVGOMFEConvolveMatrixElement.xmlTraitInformation = xmlTraitInformation;
        EDGE_MODE_VALUES = new String[] { "", "duplicate", "wrap", "none" };
    }
}
