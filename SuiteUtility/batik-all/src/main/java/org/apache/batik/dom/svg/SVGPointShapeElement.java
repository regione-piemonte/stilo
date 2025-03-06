// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.dom.svg;

import org.w3c.dom.svg.SVGPointList;
import org.apache.batik.dom.AbstractDocument;
import org.apache.batik.util.DoublyIndexedTable;
import org.w3c.dom.svg.SVGAnimatedPoints;

public abstract class SVGPointShapeElement extends SVGGraphicsElement implements SVGAnimatedPoints
{
    protected static DoublyIndexedTable xmlTraitInformation;
    protected SVGOMAnimatedPoints points;
    
    protected SVGPointShapeElement() {
    }
    
    public SVGPointShapeElement(final String s, final AbstractDocument abstractDocument) {
        super(s, abstractDocument);
        this.initializeLiveAttributes();
    }
    
    protected void initializeAllLiveAttributes() {
        super.initializeAllLiveAttributes();
        this.initializeLiveAttributes();
    }
    
    private void initializeLiveAttributes() {
        this.points = this.createLiveAnimatedPoints(null, "points", "");
    }
    
    public SVGOMAnimatedPoints getSVGOMAnimatedPoints() {
        return this.points;
    }
    
    public SVGPointList getPoints() {
        return this.points.getPoints();
    }
    
    public SVGPointList getAnimatedPoints() {
        return this.points.getAnimatedPoints();
    }
    
    protected DoublyIndexedTable getTraitInformationTable() {
        return SVGPointShapeElement.xmlTraitInformation;
    }
    
    static {
        final DoublyIndexedTable xmlTraitInformation = new DoublyIndexedTable(SVGGraphicsElement.xmlTraitInformation);
        xmlTraitInformation.put(null, "points", new TraitInformation(true, 31));
        SVGPointShapeElement.xmlTraitInformation = xmlTraitInformation;
    }
}
