// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.dom.svg;

import org.w3c.dom.svg.SVGAnimatedNumberList;
import org.w3c.dom.svg.SVGAnimatedLengthList;
import org.apache.batik.dom.AbstractDocument;
import org.apache.batik.util.DoublyIndexedTable;
import org.w3c.dom.svg.SVGTextPositioningElement;

public abstract class SVGOMTextPositioningElement extends SVGOMTextContentElement implements SVGTextPositioningElement
{
    protected static DoublyIndexedTable xmlTraitInformation;
    protected SVGOMAnimatedLengthList x;
    protected SVGOMAnimatedLengthList y;
    protected SVGOMAnimatedLengthList dx;
    protected SVGOMAnimatedLengthList dy;
    protected SVGOMAnimatedNumberList rotate;
    
    protected SVGOMTextPositioningElement() {
    }
    
    protected SVGOMTextPositioningElement(final String s, final AbstractDocument abstractDocument) {
        super(s, abstractDocument);
        this.initializeLiveAttributes();
    }
    
    protected void initializeAllLiveAttributes() {
        super.initializeAllLiveAttributes();
        this.initializeLiveAttributes();
    }
    
    private void initializeLiveAttributes() {
        this.x = this.createLiveAnimatedLengthList(null, "x", this.getDefaultXValue(), true, (short)2);
        this.y = this.createLiveAnimatedLengthList(null, "y", this.getDefaultYValue(), true, (short)1);
        this.dx = this.createLiveAnimatedLengthList(null, "dx", "", true, (short)2);
        this.dy = this.createLiveAnimatedLengthList(null, "dy", "", true, (short)1);
        this.rotate = this.createLiveAnimatedNumberList(null, "rotate", "", true);
    }
    
    public SVGAnimatedLengthList getX() {
        return (SVGAnimatedLengthList)this.x;
    }
    
    public SVGAnimatedLengthList getY() {
        return (SVGAnimatedLengthList)this.y;
    }
    
    public SVGAnimatedLengthList getDx() {
        return (SVGAnimatedLengthList)this.dx;
    }
    
    public SVGAnimatedLengthList getDy() {
        return (SVGAnimatedLengthList)this.dy;
    }
    
    public SVGAnimatedNumberList getRotate() {
        return (SVGAnimatedNumberList)this.rotate;
    }
    
    protected String getDefaultXValue() {
        return "";
    }
    
    protected String getDefaultYValue() {
        return "";
    }
    
    protected DoublyIndexedTable getTraitInformationTable() {
        return SVGOMTextPositioningElement.xmlTraitInformation;
    }
    
    static {
        final DoublyIndexedTable xmlTraitInformation = new DoublyIndexedTable(SVGOMTextContentElement.xmlTraitInformation);
        xmlTraitInformation.put(null, "x", new TraitInformation(true, 14, (short)1));
        xmlTraitInformation.put(null, "y", new TraitInformation(true, 14, (short)2));
        xmlTraitInformation.put(null, "dx", new TraitInformation(true, 14, (short)1));
        xmlTraitInformation.put(null, "dy", new TraitInformation(true, 14, (short)2));
        xmlTraitInformation.put(null, "rotate", new TraitInformation(true, 13));
        SVGOMTextPositioningElement.xmlTraitInformation = xmlTraitInformation;
    }
}
