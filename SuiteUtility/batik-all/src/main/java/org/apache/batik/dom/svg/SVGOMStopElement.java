// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.dom.svg;

import org.w3c.dom.Node;
import org.w3c.dom.svg.SVGAnimatedNumber;
import org.apache.batik.dom.AbstractDocument;
import org.apache.batik.util.DoublyIndexedTable;
import org.w3c.dom.svg.SVGStopElement;

public class SVGOMStopElement extends SVGStylableElement implements SVGStopElement
{
    protected static DoublyIndexedTable xmlTraitInformation;
    protected SVGOMAnimatedNumber offset;
    
    protected SVGOMStopElement() {
    }
    
    public SVGOMStopElement(final String s, final AbstractDocument abstractDocument) {
        super(s, abstractDocument);
        this.initializeLiveAttributes();
    }
    
    protected void initializeAllLiveAttributes() {
        super.initializeAllLiveAttributes();
        this.initializeLiveAttributes();
    }
    
    private void initializeLiveAttributes() {
        this.offset = this.createLiveAnimatedNumber(null, "offset", 0.0f, true);
    }
    
    public String getLocalName() {
        return "stop";
    }
    
    public SVGAnimatedNumber getOffset() {
        return (SVGAnimatedNumber)this.offset;
    }
    
    protected Node newNode() {
        return new SVGOMStopElement();
    }
    
    protected DoublyIndexedTable getTraitInformationTable() {
        return SVGOMStopElement.xmlTraitInformation;
    }
    
    static {
        final DoublyIndexedTable xmlTraitInformation = new DoublyIndexedTable(SVGStylableElement.xmlTraitInformation);
        xmlTraitInformation.put(null, "offset", new TraitInformation(true, 47));
        SVGOMStopElement.xmlTraitInformation = xmlTraitInformation;
    }
}
