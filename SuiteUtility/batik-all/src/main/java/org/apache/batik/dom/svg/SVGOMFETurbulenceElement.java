// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.dom.svg;

import org.w3c.dom.Node;
import org.w3c.dom.svg.SVGAnimatedEnumeration;
import org.w3c.dom.svg.SVGAnimatedInteger;
import org.w3c.dom.svg.SVGAnimatedNumber;
import org.apache.batik.dom.AbstractDocument;
import org.apache.batik.util.DoublyIndexedTable;
import org.w3c.dom.svg.SVGFETurbulenceElement;

public class SVGOMFETurbulenceElement extends SVGOMFilterPrimitiveStandardAttributes implements SVGFETurbulenceElement
{
    protected static DoublyIndexedTable xmlTraitInformation;
    protected static final String[] STITCH_TILES_VALUES;
    protected static final String[] TYPE_VALUES;
    protected SVGOMAnimatedInteger numOctaves;
    protected SVGOMAnimatedNumber seed;
    protected SVGOMAnimatedEnumeration stitchTiles;
    protected SVGOMAnimatedEnumeration type;
    
    protected SVGOMFETurbulenceElement() {
    }
    
    public SVGOMFETurbulenceElement(final String s, final AbstractDocument abstractDocument) {
        super(s, abstractDocument);
        this.initializeLiveAttributes();
    }
    
    protected void initializeAllLiveAttributes() {
        super.initializeAllLiveAttributes();
        this.initializeLiveAttributes();
    }
    
    private void initializeLiveAttributes() {
        this.numOctaves = this.createLiveAnimatedInteger(null, "numOctaves", 1);
        this.seed = this.createLiveAnimatedNumber(null, "seed", 0.0f);
        this.stitchTiles = this.createLiveAnimatedEnumeration(null, "stitchTiles", SVGOMFETurbulenceElement.STITCH_TILES_VALUES, (short)2);
        this.type = this.createLiveAnimatedEnumeration(null, "type", SVGOMFETurbulenceElement.TYPE_VALUES, (short)2);
    }
    
    public String getLocalName() {
        return "feTurbulence";
    }
    
    public SVGAnimatedNumber getBaseFrequencyX() {
        throw new UnsupportedOperationException("SVGFETurbulenceElement.getBaseFrequencyX is not implemented");
    }
    
    public SVGAnimatedNumber getBaseFrequencyY() {
        throw new UnsupportedOperationException("SVGFETurbulenceElement.getBaseFrequencyY is not implemented");
    }
    
    public SVGAnimatedInteger getNumOctaves() {
        return (SVGAnimatedInteger)this.numOctaves;
    }
    
    public SVGAnimatedNumber getSeed() {
        return (SVGAnimatedNumber)this.seed;
    }
    
    public SVGAnimatedEnumeration getStitchTiles() {
        return (SVGAnimatedEnumeration)this.stitchTiles;
    }
    
    public SVGAnimatedEnumeration getType() {
        return (SVGAnimatedEnumeration)this.type;
    }
    
    protected Node newNode() {
        return new SVGOMFETurbulenceElement();
    }
    
    protected DoublyIndexedTable getTraitInformationTable() {
        return SVGOMFETurbulenceElement.xmlTraitInformation;
    }
    
    static {
        final DoublyIndexedTable xmlTraitInformation = new DoublyIndexedTable(SVGOMFilterPrimitiveStandardAttributes.xmlTraitInformation);
        xmlTraitInformation.put(null, "baseFrequency", new TraitInformation(true, 4));
        xmlTraitInformation.put(null, "numOctaves", new TraitInformation(true, 1));
        xmlTraitInformation.put(null, "seed", new TraitInformation(true, 2));
        xmlTraitInformation.put(null, "stitchTiles", new TraitInformation(true, 15));
        xmlTraitInformation.put(null, "type", new TraitInformation(true, 15));
        SVGOMFETurbulenceElement.xmlTraitInformation = xmlTraitInformation;
        STITCH_TILES_VALUES = new String[] { "", "stitch", "noStitch" };
        TYPE_VALUES = new String[] { "", "fractalNoise", "turbulence" };
    }
}
