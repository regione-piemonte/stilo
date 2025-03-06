// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.dom.svg;

import org.w3c.dom.Node;
import org.w3c.dom.Element;
import org.apache.batik.dom.util.XMLSupport;
import org.w3c.dom.svg.SVGAnimatedBoolean;
import org.w3c.dom.svg.SVGAnimatedPreserveAspectRatio;
import org.w3c.dom.svg.SVGAnimatedRect;
import org.w3c.dom.svg.SVGAngle;
import org.w3c.dom.svg.SVGAnimatedAngle;
import org.w3c.dom.svg.SVGAnimatedEnumeration;
import org.w3c.dom.svg.SVGAnimatedLength;
import org.apache.batik.dom.AbstractDocument;
import org.apache.batik.util.DoublyIndexedTable;
import org.w3c.dom.svg.SVGMarkerElement;

public class SVGOMMarkerElement extends SVGStylableElement implements SVGMarkerElement
{
    protected static DoublyIndexedTable xmlTraitInformation;
    protected static final AttributeInitializer attributeInitializer;
    protected static final String[] UNITS_VALUES;
    protected static final String[] ORIENT_TYPE_VALUES;
    protected SVGOMAnimatedLength refX;
    protected SVGOMAnimatedLength refY;
    protected SVGOMAnimatedLength markerWidth;
    protected SVGOMAnimatedLength markerHeight;
    protected SVGOMAnimatedMarkerOrientValue orient;
    protected SVGOMAnimatedEnumeration markerUnits;
    protected SVGOMAnimatedPreserveAspectRatio preserveAspectRatio;
    protected SVGOMAnimatedRect viewBox;
    protected SVGOMAnimatedBoolean externalResourcesRequired;
    
    protected SVGOMMarkerElement() {
    }
    
    public SVGOMMarkerElement(final String s, final AbstractDocument abstractDocument) {
        super(s, abstractDocument);
        this.initializeLiveAttributes();
    }
    
    protected void initializeAllLiveAttributes() {
        super.initializeAllLiveAttributes();
        this.initializeLiveAttributes();
    }
    
    private void initializeLiveAttributes() {
        this.refX = this.createLiveAnimatedLength(null, "refX", "0", (short)2, false);
        this.refY = this.createLiveAnimatedLength(null, "refY", "0", (short)1, false);
        this.markerWidth = this.createLiveAnimatedLength(null, "markerWidth", "3", (short)2, true);
        this.markerHeight = this.createLiveAnimatedLength(null, "markerHeight", "3", (short)1, true);
        this.orient = this.createLiveAnimatedMarkerOrientValue(null, "orient");
        this.markerUnits = this.createLiveAnimatedEnumeration(null, "markerUnits", SVGOMMarkerElement.UNITS_VALUES, (short)2);
        this.preserveAspectRatio = this.createLiveAnimatedPreserveAspectRatio();
        this.viewBox = this.createLiveAnimatedRect(null, "viewBox", null);
        this.externalResourcesRequired = this.createLiveAnimatedBoolean(null, "externalResourcesRequired", false);
    }
    
    public String getLocalName() {
        return "marker";
    }
    
    public SVGAnimatedLength getRefX() {
        return (SVGAnimatedLength)this.refX;
    }
    
    public SVGAnimatedLength getRefY() {
        return (SVGAnimatedLength)this.refY;
    }
    
    public SVGAnimatedEnumeration getMarkerUnits() {
        return (SVGAnimatedEnumeration)this.markerUnits;
    }
    
    public SVGAnimatedLength getMarkerWidth() {
        return (SVGAnimatedLength)this.markerWidth;
    }
    
    public SVGAnimatedLength getMarkerHeight() {
        return (SVGAnimatedLength)this.markerHeight;
    }
    
    public SVGAnimatedEnumeration getOrientType() {
        return this.orient.getAnimatedEnumeration();
    }
    
    public SVGAnimatedAngle getOrientAngle() {
        return this.orient.getAnimatedAngle();
    }
    
    public void setOrientToAuto() {
        this.setAttributeNS(null, "orient", "auto");
    }
    
    public void setOrientToAngle(final SVGAngle svgAngle) {
        this.setAttributeNS(null, "orient", svgAngle.getValueAsString());
    }
    
    public SVGAnimatedRect getViewBox() {
        return (SVGAnimatedRect)this.viewBox;
    }
    
    public SVGAnimatedPreserveAspectRatio getPreserveAspectRatio() {
        return (SVGAnimatedPreserveAspectRatio)this.preserveAspectRatio;
    }
    
    public SVGAnimatedBoolean getExternalResourcesRequired() {
        return (SVGAnimatedBoolean)this.externalResourcesRequired;
    }
    
    public String getXMLlang() {
        return XMLSupport.getXMLLang(this);
    }
    
    public void setXMLlang(final String s) {
        this.setAttributeNS("http://www.w3.org/XML/1998/namespace", "xml:lang", s);
    }
    
    public String getXMLspace() {
        return XMLSupport.getXMLSpace(this);
    }
    
    public void setXMLspace(final String s) {
        this.setAttributeNS("http://www.w3.org/XML/1998/namespace", "xml:space", s);
    }
    
    protected AttributeInitializer getAttributeInitializer() {
        return SVGOMMarkerElement.attributeInitializer;
    }
    
    protected Node newNode() {
        return new SVGOMMarkerElement();
    }
    
    protected DoublyIndexedTable getTraitInformationTable() {
        return SVGOMMarkerElement.xmlTraitInformation;
    }
    
    static {
        final DoublyIndexedTable xmlTraitInformation = new DoublyIndexedTable(SVGStylableElement.xmlTraitInformation);
        xmlTraitInformation.put(null, "refX", new TraitInformation(true, 3, (short)1));
        xmlTraitInformation.put(null, "refY", new TraitInformation(true, 3, (short)2));
        xmlTraitInformation.put(null, "markerWidth", new TraitInformation(true, 3, (short)1));
        xmlTraitInformation.put(null, "markerHeight", new TraitInformation(true, 3, (short)2));
        xmlTraitInformation.put(null, "markerUnits", new TraitInformation(true, 15));
        xmlTraitInformation.put(null, "orient", new TraitInformation(true, 15));
        xmlTraitInformation.put(null, "preserveAspectRatio", new TraitInformation(true, 32));
        xmlTraitInformation.put(null, "externalResourcesRequired", new TraitInformation(true, 49));
        SVGOMMarkerElement.xmlTraitInformation = xmlTraitInformation;
        (attributeInitializer = new AttributeInitializer(1)).addAttribute(null, null, "preserveAspectRatio", "xMidYMid meet");
        UNITS_VALUES = new String[] { "", "userSpaceOnUse", "stroke-width" };
        ORIENT_TYPE_VALUES = new String[] { "", "auto", "" };
    }
}
