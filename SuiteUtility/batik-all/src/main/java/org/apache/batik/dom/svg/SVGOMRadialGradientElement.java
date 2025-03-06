// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.dom.svg;

import org.w3c.dom.Node;
import org.w3c.dom.svg.SVGAnimatedLength;
import org.w3c.dom.Attr;
import org.apache.batik.dom.AbstractDocument;
import org.apache.batik.util.DoublyIndexedTable;
import org.w3c.dom.svg.SVGRadialGradientElement;

public class SVGOMRadialGradientElement extends SVGOMGradientElement implements SVGRadialGradientElement
{
    protected static DoublyIndexedTable xmlTraitInformation;
    protected SVGOMAnimatedLength cx;
    protected SVGOMAnimatedLength cy;
    protected AbstractSVGAnimatedLength fx;
    protected AbstractSVGAnimatedLength fy;
    protected SVGOMAnimatedLength r;
    
    protected SVGOMRadialGradientElement() {
    }
    
    public SVGOMRadialGradientElement(final String s, final AbstractDocument abstractDocument) {
        super(s, abstractDocument);
        this.initializeLiveAttributes();
    }
    
    protected void initializeAllLiveAttributes() {
        super.initializeAllLiveAttributes();
        this.initializeLiveAttributes();
    }
    
    private void initializeLiveAttributes() {
        this.cx = this.createLiveAnimatedLength(null, "cx", "50%", (short)2, false);
        this.cy = this.createLiveAnimatedLength(null, "cy", "50%", (short)1, false);
        this.r = this.createLiveAnimatedLength(null, "r", "50%", (short)0, false);
        this.fx = new AbstractSVGAnimatedLength((AbstractElement)this, (String)null, "fx", (short)2, false) {
            protected String getDefaultValue() {
                final Attr attributeNodeNS = SVGOMRadialGradientElement.this.getAttributeNodeNS(null, "cx");
                if (attributeNodeNS == null) {
                    return "50%";
                }
                return attributeNodeNS.getValue();
            }
        };
        this.fy = new AbstractSVGAnimatedLength((AbstractElement)this, (String)null, "fy", (short)1, false) {
            protected String getDefaultValue() {
                final Attr attributeNodeNS = SVGOMRadialGradientElement.this.getAttributeNodeNS(null, "cy");
                if (attributeNodeNS == null) {
                    return "50%";
                }
                return attributeNodeNS.getValue();
            }
        };
        this.liveAttributeValues.put(null, "fx", this.fx);
        this.liveAttributeValues.put(null, "fy", this.fy);
        final AnimatedAttributeListener animatedAttributeListener = ((SVGOMDocument)this.ownerDocument).getAnimatedAttributeListener();
        this.fx.addAnimatedAttributeListener(animatedAttributeListener);
        this.fy.addAnimatedAttributeListener(animatedAttributeListener);
    }
    
    public String getLocalName() {
        return "radialGradient";
    }
    
    public SVGAnimatedLength getCx() {
        return (SVGAnimatedLength)this.cx;
    }
    
    public SVGAnimatedLength getCy() {
        return (SVGAnimatedLength)this.cy;
    }
    
    public SVGAnimatedLength getR() {
        return (SVGAnimatedLength)this.r;
    }
    
    public SVGAnimatedLength getFx() {
        return (SVGAnimatedLength)this.fx;
    }
    
    public SVGAnimatedLength getFy() {
        return (SVGAnimatedLength)this.fy;
    }
    
    protected Node newNode() {
        return new SVGOMRadialGradientElement();
    }
    
    protected DoublyIndexedTable getTraitInformationTable() {
        return SVGOMRadialGradientElement.xmlTraitInformation;
    }
    
    static {
        final DoublyIndexedTable xmlTraitInformation = new DoublyIndexedTable(SVGOMGradientElement.xmlTraitInformation);
        xmlTraitInformation.put(null, "cx", new TraitInformation(true, 3, (short)1));
        xmlTraitInformation.put(null, "cy", new TraitInformation(true, 3, (short)2));
        xmlTraitInformation.put(null, "fx", new TraitInformation(true, 3, (short)1));
        xmlTraitInformation.put(null, "fy", new TraitInformation(true, 3, (short)2));
        xmlTraitInformation.put(null, "r", new TraitInformation(true, 3, (short)3));
        SVGOMRadialGradientElement.xmlTraitInformation = xmlTraitInformation;
    }
}
