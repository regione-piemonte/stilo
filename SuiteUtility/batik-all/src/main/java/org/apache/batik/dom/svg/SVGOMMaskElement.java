// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.dom.svg;

import org.w3c.dom.Node;
import org.w3c.dom.svg.SVGAnimatedLength;
import org.w3c.dom.svg.SVGAnimatedEnumeration;
import org.apache.batik.dom.AbstractDocument;
import org.apache.batik.util.DoublyIndexedTable;
import org.w3c.dom.svg.SVGMaskElement;

public class SVGOMMaskElement extends SVGGraphicsElement implements SVGMaskElement
{
    protected static DoublyIndexedTable xmlTraitInformation;
    protected static final String[] UNITS_VALUES;
    protected SVGOMAnimatedLength x;
    protected SVGOMAnimatedLength y;
    protected SVGOMAnimatedLength width;
    protected SVGOMAnimatedLength height;
    protected SVGOMAnimatedEnumeration maskUnits;
    protected SVGOMAnimatedEnumeration maskContentUnits;
    
    protected SVGOMMaskElement() {
    }
    
    public SVGOMMaskElement(final String s, final AbstractDocument abstractDocument) {
        super(s, abstractDocument);
        this.initializeLiveAttributes();
    }
    
    protected void initializeAllLiveAttributes() {
        super.initializeAllLiveAttributes();
        this.initializeLiveAttributes();
    }
    
    private void initializeLiveAttributes() {
        this.x = this.createLiveAnimatedLength(null, "x", "-10%", (short)2, false);
        this.y = this.createLiveAnimatedLength(null, "y", "-10%", (short)1, false);
        this.width = this.createLiveAnimatedLength(null, "width", "120%", (short)2, true);
        this.height = this.createLiveAnimatedLength(null, "height", "120%", (short)1, true);
        this.maskUnits = this.createLiveAnimatedEnumeration(null, "maskUnits", SVGOMMaskElement.UNITS_VALUES, (short)2);
        this.maskContentUnits = this.createLiveAnimatedEnumeration(null, "maskContentUnits", SVGOMMaskElement.UNITS_VALUES, (short)1);
    }
    
    public String getLocalName() {
        return "mask";
    }
    
    public SVGAnimatedEnumeration getMaskUnits() {
        return (SVGAnimatedEnumeration)this.maskUnits;
    }
    
    public SVGAnimatedEnumeration getMaskContentUnits() {
        return (SVGAnimatedEnumeration)this.maskContentUnits;
    }
    
    public SVGAnimatedLength getX() {
        return (SVGAnimatedLength)this.x;
    }
    
    public SVGAnimatedLength getY() {
        return (SVGAnimatedLength)this.y;
    }
    
    public SVGAnimatedLength getWidth() {
        return (SVGAnimatedLength)this.width;
    }
    
    public SVGAnimatedLength getHeight() {
        return (SVGAnimatedLength)this.height;
    }
    
    protected Node newNode() {
        return new SVGOMMaskElement();
    }
    
    protected DoublyIndexedTable getTraitInformationTable() {
        return SVGOMMaskElement.xmlTraitInformation;
    }
    
    static {
        final DoublyIndexedTable xmlTraitInformation = new DoublyIndexedTable(SVGGraphicsElement.xmlTraitInformation);
        xmlTraitInformation.put(null, "x", new TraitInformation(true, 3, (short)1));
        xmlTraitInformation.put(null, "y", new TraitInformation(true, 3, (short)2));
        xmlTraitInformation.put(null, "width", new TraitInformation(true, 3, (short)1));
        xmlTraitInformation.put(null, "height", new TraitInformation(true, 3, (short)2));
        xmlTraitInformation.put(null, "maskUnits", new TraitInformation(true, 15));
        xmlTraitInformation.put(null, "maskContentUnits", new TraitInformation(true, 15));
        SVGOMMaskElement.xmlTraitInformation = xmlTraitInformation;
        UNITS_VALUES = new String[] { "", "userSpaceOnUse", "objectBoundingBox" };
    }
}
