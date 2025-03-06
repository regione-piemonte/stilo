// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.dom.svg;

import org.w3c.dom.Node;
import org.w3c.dom.svg.SVGAnimatedEnumeration;
import org.apache.batik.dom.AbstractDocument;
import org.apache.batik.util.DoublyIndexedTable;
import org.w3c.dom.svg.SVGClipPathElement;

public class SVGOMClipPathElement extends SVGGraphicsElement implements SVGClipPathElement
{
    protected static DoublyIndexedTable xmlTraitInformation;
    protected static final String[] CLIP_PATH_UNITS_VALUES;
    protected SVGOMAnimatedEnumeration clipPathUnits;
    
    protected SVGOMClipPathElement() {
    }
    
    public SVGOMClipPathElement(final String s, final AbstractDocument abstractDocument) {
        super(s, abstractDocument);
        this.initializeLiveAttributes();
    }
    
    protected void initializeAllLiveAttributes() {
        super.initializeAllLiveAttributes();
        this.initializeLiveAttributes();
    }
    
    private void initializeLiveAttributes() {
        this.clipPathUnits = this.createLiveAnimatedEnumeration(null, "clipPathUnits", SVGOMClipPathElement.CLIP_PATH_UNITS_VALUES, (short)1);
    }
    
    public String getLocalName() {
        return "clipPath";
    }
    
    public SVGAnimatedEnumeration getClipPathUnits() {
        return (SVGAnimatedEnumeration)this.clipPathUnits;
    }
    
    protected Node newNode() {
        return new SVGOMClipPathElement();
    }
    
    protected DoublyIndexedTable getTraitInformationTable() {
        return SVGOMClipPathElement.xmlTraitInformation;
    }
    
    static {
        final DoublyIndexedTable xmlTraitInformation = new DoublyIndexedTable(SVGGraphicsElement.xmlTraitInformation);
        xmlTraitInformation.put(null, "clipPathUnits", new TraitInformation(true, 15));
        SVGOMClipPathElement.xmlTraitInformation = xmlTraitInformation;
        CLIP_PATH_UNITS_VALUES = new String[] { "", "userSpaceOnUse", "objectBoundingBox" };
    }
}
