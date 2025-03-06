// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.dom.svg;

import org.w3c.dom.Node;
import org.w3c.dom.svg.SVGAnimatedLength;
import org.apache.batik.dom.AbstractDocument;
import org.apache.batik.util.DoublyIndexedTable;
import org.w3c.dom.svg.SVGEllipseElement;

public class SVGOMEllipseElement extends SVGGraphicsElement implements SVGEllipseElement
{
    protected static DoublyIndexedTable xmlTraitInformation;
    protected SVGOMAnimatedLength cx;
    protected SVGOMAnimatedLength cy;
    protected SVGOMAnimatedLength rx;
    protected SVGOMAnimatedLength ry;
    
    protected SVGOMEllipseElement() {
    }
    
    public SVGOMEllipseElement(final String s, final AbstractDocument abstractDocument) {
        super(s, abstractDocument);
        this.initializeLiveAttributes();
    }
    
    protected void initializeAllLiveAttributes() {
        super.initializeAllLiveAttributes();
        this.initializeLiveAttributes();
    }
    
    private void initializeLiveAttributes() {
        this.cx = this.createLiveAnimatedLength(null, "cx", "0", (short)2, false);
        this.cy = this.createLiveAnimatedLength(null, "cy", "0", (short)1, false);
        this.rx = this.createLiveAnimatedLength(null, "rx", null, (short)2, true);
        this.ry = this.createLiveAnimatedLength(null, "ry", null, (short)1, true);
    }
    
    public String getLocalName() {
        return "ellipse";
    }
    
    public SVGAnimatedLength getCx() {
        return (SVGAnimatedLength)this.cx;
    }
    
    public SVGAnimatedLength getCy() {
        return (SVGAnimatedLength)this.cy;
    }
    
    public SVGAnimatedLength getRx() {
        return (SVGAnimatedLength)this.rx;
    }
    
    public SVGAnimatedLength getRy() {
        return (SVGAnimatedLength)this.ry;
    }
    
    protected Node newNode() {
        return new SVGOMEllipseElement();
    }
    
    protected DoublyIndexedTable getTraitInformationTable() {
        return SVGOMEllipseElement.xmlTraitInformation;
    }
    
    static {
        final DoublyIndexedTable xmlTraitInformation = new DoublyIndexedTable(SVGGraphicsElement.xmlTraitInformation);
        xmlTraitInformation.put(null, "cx", new TraitInformation(true, 3, (short)1));
        xmlTraitInformation.put(null, "cy", new TraitInformation(true, 3, (short)2));
        xmlTraitInformation.put(null, "rx", new TraitInformation(true, 3, (short)1));
        xmlTraitInformation.put(null, "ry", new TraitInformation(true, 3, (short)2));
        SVGOMEllipseElement.xmlTraitInformation = xmlTraitInformation;
    }
}
