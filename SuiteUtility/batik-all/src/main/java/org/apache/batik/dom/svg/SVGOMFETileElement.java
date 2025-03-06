// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.dom.svg;

import org.w3c.dom.Node;
import org.w3c.dom.svg.SVGAnimatedString;
import org.apache.batik.dom.AbstractDocument;
import org.apache.batik.util.DoublyIndexedTable;
import org.w3c.dom.svg.SVGFETileElement;

public class SVGOMFETileElement extends SVGOMFilterPrimitiveStandardAttributes implements SVGFETileElement
{
    protected static DoublyIndexedTable xmlTraitInformation;
    protected SVGOMAnimatedString in;
    
    protected SVGOMFETileElement() {
    }
    
    public SVGOMFETileElement(final String s, final AbstractDocument abstractDocument) {
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
        return "feTile";
    }
    
    public SVGAnimatedString getIn1() {
        return (SVGAnimatedString)this.in;
    }
    
    protected Node newNode() {
        return new SVGOMFETileElement();
    }
    
    protected DoublyIndexedTable getTraitInformationTable() {
        return SVGOMFETileElement.xmlTraitInformation;
    }
    
    static {
        final DoublyIndexedTable xmlTraitInformation = new DoublyIndexedTable(SVGOMFilterPrimitiveStandardAttributes.xmlTraitInformation);
        xmlTraitInformation.put(null, "in", new TraitInformation(true, 16));
        SVGOMFETileElement.xmlTraitInformation = xmlTraitInformation;
    }
}
