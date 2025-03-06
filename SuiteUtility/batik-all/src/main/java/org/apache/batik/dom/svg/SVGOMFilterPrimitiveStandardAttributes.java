// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.dom.svg;

import org.w3c.dom.svg.SVGAnimatedString;
import org.w3c.dom.svg.SVGAnimatedLength;
import org.apache.batik.dom.AbstractDocument;
import org.apache.batik.util.DoublyIndexedTable;
import org.w3c.dom.svg.SVGFilterPrimitiveStandardAttributes;

public abstract class SVGOMFilterPrimitiveStandardAttributes extends SVGStylableElement implements SVGFilterPrimitiveStandardAttributes
{
    protected static DoublyIndexedTable xmlTraitInformation;
    protected SVGOMAnimatedLength x;
    protected SVGOMAnimatedLength y;
    protected SVGOMAnimatedLength width;
    protected SVGOMAnimatedLength height;
    protected SVGOMAnimatedString result;
    
    protected SVGOMFilterPrimitiveStandardAttributes() {
    }
    
    protected SVGOMFilterPrimitiveStandardAttributes(final String s, final AbstractDocument abstractDocument) {
        super(s, abstractDocument);
        this.initializeLiveAttributes();
    }
    
    protected void initializeAllLiveAttributes() {
        super.initializeAllLiveAttributes();
        this.initializeLiveAttributes();
    }
    
    private void initializeLiveAttributes() {
        this.x = this.createLiveAnimatedLength(null, "x", "0%", (short)2, false);
        this.y = this.createLiveAnimatedLength(null, "y", "0%", (short)1, false);
        this.width = this.createLiveAnimatedLength(null, "width", "100%", (short)2, true);
        this.height = this.createLiveAnimatedLength(null, "height", "100%", (short)1, true);
        this.result = this.createLiveAnimatedString(null, "result");
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
    
    public SVGAnimatedString getResult() {
        return (SVGAnimatedString)this.result;
    }
    
    protected DoublyIndexedTable getTraitInformationTable() {
        return SVGOMFilterPrimitiveStandardAttributes.xmlTraitInformation;
    }
    
    static {
        final DoublyIndexedTable xmlTraitInformation = new DoublyIndexedTable(SVGStylableElement.xmlTraitInformation);
        xmlTraitInformation.put(null, "x", new TraitInformation(true, 3, (short)1));
        xmlTraitInformation.put(null, "y", new TraitInformation(true, 3, (short)2));
        xmlTraitInformation.put(null, "width", new TraitInformation(true, 3, (short)1));
        xmlTraitInformation.put(null, "height", new TraitInformation(true, 3, (short)2));
        xmlTraitInformation.put(null, "result", new TraitInformation(true, 16));
        SVGOMFilterPrimitiveStandardAttributes.xmlTraitInformation = xmlTraitInformation;
    }
}
