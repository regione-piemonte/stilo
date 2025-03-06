// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.dom.svg;

import org.w3c.dom.Node;
import org.w3c.dom.svg.SVGAnimatedNumberList;
import org.w3c.dom.svg.SVGAnimatedEnumeration;
import org.w3c.dom.svg.SVGAnimatedString;
import org.apache.batik.dom.AbstractDocument;
import org.apache.batik.util.DoublyIndexedTable;
import org.w3c.dom.svg.SVGFEColorMatrixElement;

public class SVGOMFEColorMatrixElement extends SVGOMFilterPrimitiveStandardAttributes implements SVGFEColorMatrixElement
{
    protected static DoublyIndexedTable xmlTraitInformation;
    protected static final String[] TYPE_VALUES;
    protected SVGOMAnimatedString in;
    protected SVGOMAnimatedEnumeration type;
    
    protected SVGOMFEColorMatrixElement() {
    }
    
    public SVGOMFEColorMatrixElement(final String s, final AbstractDocument abstractDocument) {
        super(s, abstractDocument);
        this.initializeLiveAttributes();
    }
    
    protected void initializeAllLiveAttributes() {
        super.initializeAllLiveAttributes();
        this.initializeLiveAttributes();
    }
    
    private void initializeLiveAttributes() {
        this.in = this.createLiveAnimatedString(null, "in");
        this.type = this.createLiveAnimatedEnumeration(null, "type", SVGOMFEColorMatrixElement.TYPE_VALUES, (short)1);
    }
    
    public String getLocalName() {
        return "feColorMatrix";
    }
    
    public SVGAnimatedString getIn1() {
        return (SVGAnimatedString)this.in;
    }
    
    public SVGAnimatedEnumeration getType() {
        return (SVGAnimatedEnumeration)this.type;
    }
    
    public SVGAnimatedNumberList getValues() {
        throw new UnsupportedOperationException("SVGFEColorMatrixElement.getValues is not implemented");
    }
    
    protected Node newNode() {
        return new SVGOMFEColorMatrixElement();
    }
    
    protected DoublyIndexedTable getTraitInformationTable() {
        return SVGOMFEColorMatrixElement.xmlTraitInformation;
    }
    
    static {
        final DoublyIndexedTable xmlTraitInformation = new DoublyIndexedTable(SVGOMFilterPrimitiveStandardAttributes.xmlTraitInformation);
        xmlTraitInformation.put(null, "in", new TraitInformation(true, 16));
        xmlTraitInformation.put(null, "type", new TraitInformation(true, 15));
        xmlTraitInformation.put(null, "values", new TraitInformation(true, 13));
        SVGOMFEColorMatrixElement.xmlTraitInformation = xmlTraitInformation;
        TYPE_VALUES = new String[] { "", "matrix", "saturate", "hueRotate", "luminanceToAlpha" };
    }
}
