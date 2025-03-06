// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.dom.svg;

import org.w3c.dom.Node;
import org.w3c.dom.svg.SVGAnimatedEnumeration;
import org.w3c.dom.svg.SVGAnimatedNumber;
import org.w3c.dom.svg.SVGAnimatedString;
import org.apache.batik.dom.AbstractDocument;
import org.apache.batik.util.DoublyIndexedTable;
import org.w3c.dom.svg.SVGFEDisplacementMapElement;

public class SVGOMFEDisplacementMapElement extends SVGOMFilterPrimitiveStandardAttributes implements SVGFEDisplacementMapElement
{
    protected static DoublyIndexedTable xmlTraitInformation;
    protected static final String[] CHANNEL_SELECTOR_VALUES;
    protected SVGOMAnimatedString in;
    protected SVGOMAnimatedString in2;
    protected SVGOMAnimatedNumber scale;
    protected SVGOMAnimatedEnumeration xChannelSelector;
    protected SVGOMAnimatedEnumeration yChannelSelector;
    
    protected SVGOMFEDisplacementMapElement() {
    }
    
    public SVGOMFEDisplacementMapElement(final String s, final AbstractDocument abstractDocument) {
        super(s, abstractDocument);
        this.initializeLiveAttributes();
    }
    
    protected void initializeAllLiveAttributes() {
        super.initializeAllLiveAttributes();
        this.initializeLiveAttributes();
    }
    
    private void initializeLiveAttributes() {
        this.in = this.createLiveAnimatedString(null, "in");
        this.in2 = this.createLiveAnimatedString(null, "in2");
        this.scale = this.createLiveAnimatedNumber(null, "scale", 0.0f);
        this.xChannelSelector = this.createLiveAnimatedEnumeration(null, "xChannelSelector", SVGOMFEDisplacementMapElement.CHANNEL_SELECTOR_VALUES, (short)4);
        this.yChannelSelector = this.createLiveAnimatedEnumeration(null, "yChannelSelector", SVGOMFEDisplacementMapElement.CHANNEL_SELECTOR_VALUES, (short)4);
    }
    
    public String getLocalName() {
        return "feDisplacementMap";
    }
    
    public SVGAnimatedString getIn1() {
        return (SVGAnimatedString)this.in;
    }
    
    public SVGAnimatedString getIn2() {
        return (SVGAnimatedString)this.in2;
    }
    
    public SVGAnimatedNumber getScale() {
        return (SVGAnimatedNumber)this.scale;
    }
    
    public SVGAnimatedEnumeration getXChannelSelector() {
        return (SVGAnimatedEnumeration)this.xChannelSelector;
    }
    
    public SVGAnimatedEnumeration getYChannelSelector() {
        return (SVGAnimatedEnumeration)this.yChannelSelector;
    }
    
    protected Node newNode() {
        return new SVGOMFEDisplacementMapElement();
    }
    
    protected DoublyIndexedTable getTraitInformationTable() {
        return SVGOMFEDisplacementMapElement.xmlTraitInformation;
    }
    
    static {
        final DoublyIndexedTable xmlTraitInformation = new DoublyIndexedTable(SVGOMFilterPrimitiveStandardAttributes.xmlTraitInformation);
        xmlTraitInformation.put(null, "in", new TraitInformation(true, 16));
        xmlTraitInformation.put(null, "in2", new TraitInformation(true, 16));
        xmlTraitInformation.put(null, "scale", new TraitInformation(true, 2));
        xmlTraitInformation.put(null, "xChannelSelector", new TraitInformation(true, 15));
        xmlTraitInformation.put(null, "yChannelSelector", new TraitInformation(true, 15));
        SVGOMFEDisplacementMapElement.xmlTraitInformation = xmlTraitInformation;
        CHANNEL_SELECTOR_VALUES = new String[] { "", "R", "G", "B", "A" };
    }
}
