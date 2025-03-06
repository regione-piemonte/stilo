// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.dom.svg;

import org.w3c.dom.svg.SVGAnimatedString;
import org.apache.batik.dom.AbstractDocument;
import org.apache.batik.util.DoublyIndexedTable;
import org.w3c.dom.svg.SVGURIReference;

public abstract class SVGOMURIReferenceElement extends SVGOMElement implements SVGURIReference
{
    protected static DoublyIndexedTable xmlTraitInformation;
    protected SVGOMAnimatedString href;
    
    protected SVGOMURIReferenceElement() {
    }
    
    protected SVGOMURIReferenceElement(final String s, final AbstractDocument abstractDocument) {
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
        return SVGOMURIReferenceElement.xmlTraitInformation;
    }
    
    static {
        final DoublyIndexedTable xmlTraitInformation = new DoublyIndexedTable(SVGOMElement.xmlTraitInformation);
        xmlTraitInformation.put("http://www.w3.org/1999/xlink", "href", new TraitInformation(true, 10));
        SVGOMURIReferenceElement.xmlTraitInformation = xmlTraitInformation;
    }
}
