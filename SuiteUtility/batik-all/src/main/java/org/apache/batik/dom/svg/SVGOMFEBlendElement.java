// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.dom.svg;

import org.w3c.dom.Node;
import org.w3c.dom.svg.SVGAnimatedEnumeration;
import org.w3c.dom.svg.SVGAnimatedString;
import org.apache.batik.dom.AbstractDocument;
import org.apache.batik.util.DoublyIndexedTable;
import org.w3c.dom.svg.SVGFEBlendElement;

public class SVGOMFEBlendElement extends SVGOMFilterPrimitiveStandardAttributes implements SVGFEBlendElement
{
    protected static DoublyIndexedTable xmlTraitInformation;
    protected static final String[] MODE_VALUES;
    protected SVGOMAnimatedString in;
    protected SVGOMAnimatedString in2;
    protected SVGOMAnimatedEnumeration mode;
    
    protected SVGOMFEBlendElement() {
    }
    
    public SVGOMFEBlendElement(final String s, final AbstractDocument abstractDocument) {
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
        this.mode = this.createLiveAnimatedEnumeration(null, "mode", SVGOMFEBlendElement.MODE_VALUES, (short)1);
    }
    
    public String getLocalName() {
        return "feBlend";
    }
    
    public SVGAnimatedString getIn1() {
        return (SVGAnimatedString)this.in;
    }
    
    public SVGAnimatedString getIn2() {
        return (SVGAnimatedString)this.in2;
    }
    
    public SVGAnimatedEnumeration getMode() {
        return (SVGAnimatedEnumeration)this.mode;
    }
    
    protected Node newNode() {
        return new SVGOMFEBlendElement();
    }
    
    protected DoublyIndexedTable getTraitInformationTable() {
        return SVGOMFEBlendElement.xmlTraitInformation;
    }
    
    static {
        final DoublyIndexedTable xmlTraitInformation = new DoublyIndexedTable(SVGOMFilterPrimitiveStandardAttributes.xmlTraitInformation);
        xmlTraitInformation.put(null, "in", new TraitInformation(true, 16));
        xmlTraitInformation.put(null, "surfaceScale", new TraitInformation(true, 2));
        xmlTraitInformation.put(null, "diffuseConstant", new TraitInformation(true, 2));
        xmlTraitInformation.put(null, "kernelUnitLength", new TraitInformation(true, 4));
        SVGOMFEBlendElement.xmlTraitInformation = xmlTraitInformation;
        MODE_VALUES = new String[] { "", "normal", "multiply", "screen", "darken", "lighten" };
    }
}
