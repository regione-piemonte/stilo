// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.dom.svg;

import org.w3c.dom.Node;
import org.w3c.dom.svg.SVGAnimatedNumber;
import org.w3c.dom.svg.SVGAnimatedString;
import org.apache.batik.dom.AbstractDocument;
import org.apache.batik.util.DoublyIndexedTable;
import org.w3c.dom.svg.SVGFEDiffuseLightingElement;

public class SVGOMFEDiffuseLightingElement extends SVGOMFilterPrimitiveStandardAttributes implements SVGFEDiffuseLightingElement
{
    protected static DoublyIndexedTable xmlTraitInformation;
    protected SVGOMAnimatedString in;
    protected SVGOMAnimatedNumber surfaceScale;
    protected SVGOMAnimatedNumber diffuseConstant;
    
    protected SVGOMFEDiffuseLightingElement() {
    }
    
    public SVGOMFEDiffuseLightingElement(final String s, final AbstractDocument abstractDocument) {
        super(s, abstractDocument);
        this.initializeLiveAttributes();
    }
    
    protected void initializeAllLiveAttributes() {
        super.initializeAllLiveAttributes();
        this.initializeLiveAttributes();
    }
    
    private void initializeLiveAttributes() {
        this.in = this.createLiveAnimatedString(null, "in");
        this.surfaceScale = this.createLiveAnimatedNumber(null, "surfaceScale", 1.0f);
        this.diffuseConstant = this.createLiveAnimatedNumber(null, "diffuseConstant", 1.0f);
    }
    
    public String getLocalName() {
        return "feDiffuseLighting";
    }
    
    public SVGAnimatedString getIn1() {
        return (SVGAnimatedString)this.in;
    }
    
    public SVGAnimatedNumber getSurfaceScale() {
        return (SVGAnimatedNumber)this.surfaceScale;
    }
    
    public SVGAnimatedNumber getDiffuseConstant() {
        return (SVGAnimatedNumber)this.diffuseConstant;
    }
    
    public SVGAnimatedNumber getKernelUnitLengthX() {
        throw new UnsupportedOperationException("SVGFEDiffuseLightingElement.getKernelUnitLengthX is not implemented");
    }
    
    public SVGAnimatedNumber getKernelUnitLengthY() {
        throw new UnsupportedOperationException("SVGFEDiffuseLightingElement.getKernelUnitLengthY is not implemented");
    }
    
    protected Node newNode() {
        return new SVGOMFEDiffuseLightingElement();
    }
    
    protected DoublyIndexedTable getTraitInformationTable() {
        return SVGOMFEDiffuseLightingElement.xmlTraitInformation;
    }
    
    static {
        final DoublyIndexedTable xmlTraitInformation = new DoublyIndexedTable(SVGOMFilterPrimitiveStandardAttributes.xmlTraitInformation);
        xmlTraitInformation.put(null, "in", new TraitInformation(true, 16));
        xmlTraitInformation.put(null, "in2", new TraitInformation(true, 16));
        xmlTraitInformation.put(null, "mode", new TraitInformation(true, 15));
        SVGOMFEDiffuseLightingElement.xmlTraitInformation = xmlTraitInformation;
    }
}
