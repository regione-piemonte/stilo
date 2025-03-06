// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.dom.svg;

import org.w3c.dom.Node;
import org.w3c.dom.svg.SVGAnimatedBoolean;
import org.w3c.dom.svg.SVGAnimatedPreserveAspectRatio;
import org.w3c.dom.svg.SVGAnimatedRect;
import org.w3c.dom.Element;
import org.w3c.dom.svg.SVGStringList;
import org.apache.batik.dom.AbstractDocument;
import org.apache.batik.util.DoublyIndexedTable;
import org.w3c.dom.svg.SVGViewElement;

public class SVGOMViewElement extends SVGOMElement implements SVGViewElement
{
    protected static DoublyIndexedTable xmlTraitInformation;
    protected static final AttributeInitializer attributeInitializer;
    protected SVGOMAnimatedBoolean externalResourcesRequired;
    protected SVGOMAnimatedPreserveAspectRatio preserveAspectRatio;
    
    protected SVGOMViewElement() {
    }
    
    public SVGOMViewElement(final String s, final AbstractDocument abstractDocument) {
        super(s, abstractDocument);
        this.initializeLiveAttributes();
    }
    
    protected void initializeAllLiveAttributes() {
        super.initializeAllLiveAttributes();
        this.initializeLiveAttributes();
    }
    
    private void initializeLiveAttributes() {
        this.externalResourcesRequired = this.createLiveAnimatedBoolean(null, "externalResourcesRequired", false);
        this.preserveAspectRatio = this.createLiveAnimatedPreserveAspectRatio();
    }
    
    public String getLocalName() {
        return "view";
    }
    
    public SVGStringList getViewTarget() {
        throw new UnsupportedOperationException("SVGViewElement.getViewTarget is not implemented");
    }
    
    public short getZoomAndPan() {
        return SVGZoomAndPanSupport.getZoomAndPan(this);
    }
    
    public void setZoomAndPan(final short n) {
        SVGZoomAndPanSupport.setZoomAndPan(this, n);
    }
    
    public SVGAnimatedRect getViewBox() {
        throw new UnsupportedOperationException("SVGFitToViewBox.getViewBox is not implemented");
    }
    
    public SVGAnimatedPreserveAspectRatio getPreserveAspectRatio() {
        return (SVGAnimatedPreserveAspectRatio)this.preserveAspectRatio;
    }
    
    public SVGAnimatedBoolean getExternalResourcesRequired() {
        return (SVGAnimatedBoolean)this.externalResourcesRequired;
    }
    
    protected AttributeInitializer getAttributeInitializer() {
        return SVGOMViewElement.attributeInitializer;
    }
    
    protected Node newNode() {
        return new SVGOMViewElement();
    }
    
    protected DoublyIndexedTable getTraitInformationTable() {
        return SVGOMViewElement.xmlTraitInformation;
    }
    
    static {
        final DoublyIndexedTable xmlTraitInformation = new DoublyIndexedTable(SVGOMElement.xmlTraitInformation);
        xmlTraitInformation.put(null, "preserveAspectRatio", new TraitInformation(true, 32));
        xmlTraitInformation.put(null, "viewBox", new TraitInformation(true, 13));
        xmlTraitInformation.put(null, "externalResourcesRequired", new TraitInformation(true, 49));
        SVGOMViewElement.xmlTraitInformation = xmlTraitInformation;
        (attributeInitializer = new AttributeInitializer(2)).addAttribute(null, null, "preserveAspectRatio", "xMidYMid meet");
        SVGOMViewElement.attributeInitializer.addAttribute(null, null, "zoomAndPan", "magnify");
    }
}
