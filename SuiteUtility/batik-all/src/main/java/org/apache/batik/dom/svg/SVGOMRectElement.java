// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.dom.svg;

import org.apache.batik.anim.values.AnimatableValue;
import org.w3c.dom.Node;
import org.w3c.dom.svg.SVGAnimatedLength;
import org.w3c.dom.Attr;
import org.apache.batik.dom.AbstractDocument;
import org.apache.batik.util.DoublyIndexedTable;
import org.w3c.dom.svg.SVGRectElement;

public class SVGOMRectElement extends SVGGraphicsElement implements SVGRectElement
{
    protected static DoublyIndexedTable xmlTraitInformation;
    protected SVGOMAnimatedLength x;
    protected SVGOMAnimatedLength y;
    protected AbstractSVGAnimatedLength rx;
    protected AbstractSVGAnimatedLength ry;
    protected SVGOMAnimatedLength width;
    protected SVGOMAnimatedLength height;
    
    protected SVGOMRectElement() {
    }
    
    public SVGOMRectElement(final String s, final AbstractDocument abstractDocument) {
        super(s, abstractDocument);
        this.initializeLiveAttributes();
    }
    
    protected void initializeAllLiveAttributes() {
        super.initializeAllLiveAttributes();
        this.initializeLiveAttributes();
    }
    
    private void initializeLiveAttributes() {
        this.x = this.createLiveAnimatedLength(null, "x", "0", (short)2, false);
        this.y = this.createLiveAnimatedLength(null, "y", "0", (short)1, false);
        this.width = this.createLiveAnimatedLength(null, "width", null, (short)2, true);
        this.height = this.createLiveAnimatedLength(null, "height", null, (short)1, true);
        this.rx = new AbstractSVGAnimatedLength((AbstractElement)this, (String)null, "rx", (short)2, true) {
            protected String getDefaultValue() {
                final Attr attributeNodeNS = SVGOMRectElement.this.getAttributeNodeNS(null, "ry");
                if (attributeNodeNS == null) {
                    return "0";
                }
                return attributeNodeNS.getValue();
            }
            
            protected void attrChanged() {
                super.attrChanged();
                final AbstractSVGAnimatedLength abstractSVGAnimatedLength = (AbstractSVGAnimatedLength)SVGOMRectElement.this.getRy();
                if (this.isSpecified() && !abstractSVGAnimatedLength.isSpecified()) {
                    abstractSVGAnimatedLength.attrChanged();
                }
            }
        };
        this.ry = new AbstractSVGAnimatedLength((AbstractElement)this, (String)null, "ry", (short)1, true) {
            protected String getDefaultValue() {
                final Attr attributeNodeNS = SVGOMRectElement.this.getAttributeNodeNS(null, "rx");
                if (attributeNodeNS == null) {
                    return "0";
                }
                return attributeNodeNS.getValue();
            }
            
            protected void attrChanged() {
                super.attrChanged();
                final AbstractSVGAnimatedLength abstractSVGAnimatedLength = (AbstractSVGAnimatedLength)SVGOMRectElement.this.getRx();
                if (this.isSpecified() && !abstractSVGAnimatedLength.isSpecified()) {
                    abstractSVGAnimatedLength.attrChanged();
                }
            }
        };
        this.liveAttributeValues.put(null, "rx", this.rx);
        this.liveAttributeValues.put(null, "ry", this.ry);
        final AnimatedAttributeListener animatedAttributeListener = ((SVGOMDocument)this.ownerDocument).getAnimatedAttributeListener();
        this.rx.addAnimatedAttributeListener(animatedAttributeListener);
        this.ry.addAnimatedAttributeListener(animatedAttributeListener);
    }
    
    public String getLocalName() {
        return "rect";
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
    
    public SVGAnimatedLength getRx() {
        return (SVGAnimatedLength)this.rx;
    }
    
    public SVGAnimatedLength getRy() {
        return (SVGAnimatedLength)this.ry;
    }
    
    protected Node newNode() {
        return new SVGOMRectElement();
    }
    
    protected DoublyIndexedTable getTraitInformationTable() {
        return SVGOMRectElement.xmlTraitInformation;
    }
    
    public void updateAttributeValue(final String s, final String s2, final AnimatableValue animatableValue) {
        if (s == null) {
            if (s2.equals("rx")) {
                super.updateAttributeValue(s, s2, animatableValue);
                if (!((AbstractSVGAnimatedLength)this.getRy()).isSpecified()) {
                    super.updateAttributeValue(s, "ry", animatableValue);
                }
                return;
            }
            if (s2.equals("ry")) {
                super.updateAttributeValue(s, s2, animatableValue);
                if (!((AbstractSVGAnimatedLength)this.getRx()).isSpecified()) {
                    super.updateAttributeValue(s, "rx", animatableValue);
                }
                return;
            }
        }
        super.updateAttributeValue(s, s2, animatableValue);
    }
    
    static {
        final DoublyIndexedTable xmlTraitInformation = new DoublyIndexedTable(SVGGraphicsElement.xmlTraitInformation);
        xmlTraitInformation.put(null, "x", new TraitInformation(true, 3, (short)1));
        xmlTraitInformation.put(null, "y", new TraitInformation(true, 3, (short)2));
        xmlTraitInformation.put(null, "rx", new TraitInformation(true, 3, (short)1));
        xmlTraitInformation.put(null, "ry", new TraitInformation(true, 3, (short)2));
        xmlTraitInformation.put(null, "width", new TraitInformation(true, 3, (short)1));
        xmlTraitInformation.put(null, "height", new TraitInformation(true, 3, (short)2));
        SVGOMRectElement.xmlTraitInformation = xmlTraitInformation;
    }
}
