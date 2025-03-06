// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.dom.svg;

import org.w3c.dom.svg.SVGAnimatedString;
import org.apache.batik.dom.AbstractDocument;
import org.apache.batik.util.DoublyIndexedTable;

public abstract class SVGURIReferenceGraphicsElement extends SVGGraphicsElement
{
    protected static DoublyIndexedTable xmlTraitInformation;
    protected SVGOMAnimatedString href;
    
    protected SVGURIReferenceGraphicsElement() {
    }
    
    protected SVGURIReferenceGraphicsElement(final String s, final AbstractDocument abstractDocument) {
        super(s, abstractDocument);
        this.initializeLiveAttributes();
    }
    
    protected void initializeAllLiveAttributes() {
        super.initializeAllLiveAttributes();
        this.initializeLiveAttributes();
    }
    
    private void initializeLiveAttributes() {
        this.href = this.createLiveAnimatedString("http://www.w3.org/1999/xlink", "href");
    }
    
    public SVGAnimatedString getHref() {
        return (SVGAnimatedString)this.href;
    }
    
    protected DoublyIndexedTable getTraitInformationTable() {
        return SVGURIReferenceGraphicsElement.xmlTraitInformation;
    }
    
    static {
        final DoublyIndexedTable xmlTraitInformation = new DoublyIndexedTable(SVGGraphicsElement.xmlTraitInformation);
        xmlTraitInformation.put("http://www.w3.org/1999/xlink", "href", new TraitInformation(true, 10));
        SVGURIReferenceGraphicsElement.xmlTraitInformation = xmlTraitInformation;
    }
}
