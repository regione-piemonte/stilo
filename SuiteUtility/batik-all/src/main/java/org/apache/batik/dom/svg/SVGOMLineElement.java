// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.dom.svg;

import org.w3c.dom.Node;
import org.w3c.dom.svg.SVGAnimatedLength;
import org.apache.batik.dom.AbstractDocument;
import org.apache.batik.util.DoublyIndexedTable;
import org.w3c.dom.svg.SVGLineElement;

public class SVGOMLineElement extends SVGGraphicsElement implements SVGLineElement
{
    protected static DoublyIndexedTable xmlTraitInformation;
    protected SVGOMAnimatedLength x1;
    protected SVGOMAnimatedLength y1;
    protected SVGOMAnimatedLength x2;
    protected SVGOMAnimatedLength y2;
    
    protected SVGOMLineElement() {
    }
    
    public SVGOMLineElement(final String s, final AbstractDocument abstractDocument) {
        super(s, abstractDocument);
        this.initializeLiveAttributes();
    }
    
    protected void initializeAllLiveAttributes() {
        super.initializeAllLiveAttributes();
        this.initializeLiveAttributes();
    }
    
    private void initializeLiveAttributes() {
        this.x1 = this.createLiveAnimatedLength(null, "x1", "0", (short)2, false);
        this.y1 = this.createLiveAnimatedLength(null, "y1", "0", (short)1, false);
        this.x2 = this.createLiveAnimatedLength(null, "x2", "0", (short)2, false);
        this.y2 = this.createLiveAnimatedLength(null, "y2", "0", (short)1, false);
    }
    
    public String getLocalName() {
        return "line";
    }
    
    public SVGAnimatedLength getX1() {
        return (SVGAnimatedLength)this.x1;
    }
    
    public SVGAnimatedLength getY1() {
        return (SVGAnimatedLength)this.y1;
    }
    
    public SVGAnimatedLength getX2() {
        return (SVGAnimatedLength)this.x2;
    }
    
    public SVGAnimatedLength getY2() {
        return (SVGAnimatedLength)this.y2;
    }
    
    protected Node newNode() {
        return new SVGOMLineElement();
    }
    
    protected DoublyIndexedTable getTraitInformationTable() {
        return SVGOMLineElement.xmlTraitInformation;
    }
    
    static {
        final DoublyIndexedTable xmlTraitInformation = new DoublyIndexedTable(SVGGraphicsElement.xmlTraitInformation);
        xmlTraitInformation.put(null, "x1", new TraitInformation(true, 3, (short)1));
        xmlTraitInformation.put(null, "y1", new TraitInformation(true, 3, (short)2));
        xmlTraitInformation.put(null, "x2", new TraitInformation(true, 3, (short)1));
        xmlTraitInformation.put(null, "y2", new TraitInformation(true, 3, (short)2));
        SVGOMLineElement.xmlTraitInformation = xmlTraitInformation;
    }
}
