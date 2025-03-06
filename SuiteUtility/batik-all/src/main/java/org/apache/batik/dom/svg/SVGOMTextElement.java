// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.dom.svg;

import org.apache.batik.anim.values.AnimatableMotionPointValue;
import org.apache.batik.anim.values.AnimatableValue;
import org.w3c.dom.Node;
import org.w3c.dom.svg.SVGAnimatedTransformList;
import org.w3c.dom.svg.SVGException;
import org.w3c.dom.svg.SVGMatrix;
import org.w3c.dom.svg.SVGRect;
import org.w3c.dom.Element;
import org.w3c.dom.svg.SVGElement;
import org.apache.batik.dom.AbstractDocument;
import java.awt.geom.AffineTransform;
import org.apache.batik.util.DoublyIndexedTable;
import org.w3c.dom.svg.SVGTextElement;

public class SVGOMTextElement extends SVGOMTextPositioningElement implements SVGTextElement, SVGMotionAnimatableElement
{
    protected static final String X_DEFAULT_VALUE = "0";
    protected static final String Y_DEFAULT_VALUE = "0";
    protected static DoublyIndexedTable xmlTraitInformation;
    protected SVGOMAnimatedTransformList transform;
    protected AffineTransform motionTransform;
    
    protected SVGOMTextElement() {
    }
    
    public SVGOMTextElement(final String s, final AbstractDocument abstractDocument) {
        super(s, abstractDocument);
        this.initializeLiveAttributes();
    }
    
    protected void initializeAllLiveAttributes() {
        super.initializeAllLiveAttributes();
        this.initializeLiveAttributes();
    }
    
    private void initializeLiveAttributes() {
        this.transform = this.createLiveAnimatedTransformList(null, "transform", "");
    }
    
    public String getLocalName() {
        return "text";
    }
    
    public SVGElement getNearestViewportElement() {
        return SVGLocatableSupport.getNearestViewportElement(this);
    }
    
    public SVGElement getFarthestViewportElement() {
        return SVGLocatableSupport.getFarthestViewportElement(this);
    }
    
    public SVGRect getBBox() {
        return SVGLocatableSupport.getBBox(this);
    }
    
    public SVGMatrix getCTM() {
        return SVGLocatableSupport.getCTM(this);
    }
    
    public SVGMatrix getScreenCTM() {
        return SVGLocatableSupport.getScreenCTM(this);
    }
    
    public SVGMatrix getTransformToElement(final SVGElement svgElement) throws SVGException {
        return SVGLocatableSupport.getTransformToElement(this, svgElement);
    }
    
    public SVGAnimatedTransformList getTransform() {
        return (SVGAnimatedTransformList)this.transform;
    }
    
    protected String getDefaultXValue() {
        return "0";
    }
    
    protected String getDefaultYValue() {
        return "0";
    }
    
    protected Node newNode() {
        return new SVGOMTextElement();
    }
    
    protected DoublyIndexedTable getTraitInformationTable() {
        return SVGOMTextElement.xmlTraitInformation;
    }
    
    public AffineTransform getMotionTransform() {
        return this.motionTransform;
    }
    
    public void updateOtherValue(final String s, final AnimatableValue animatableValue) {
        if (s.equals("motion")) {
            if (this.motionTransform == null) {
                this.motionTransform = new AffineTransform();
            }
            if (animatableValue == null) {
                this.motionTransform.setToIdentity();
            }
            else {
                final AnimatableMotionPointValue animatableMotionPointValue = (AnimatableMotionPointValue)animatableValue;
                this.motionTransform.setToTranslation(animatableMotionPointValue.getX(), animatableMotionPointValue.getY());
                this.motionTransform.rotate(animatableMotionPointValue.getAngle());
            }
            ((SVGOMDocument)this.ownerDocument).getAnimatedAttributeListener().otherAnimationChanged(this, s);
        }
        else {
            super.updateOtherValue(s, animatableValue);
        }
    }
    
    static {
        final DoublyIndexedTable xmlTraitInformation = new DoublyIndexedTable(SVGOMTextPositioningElement.xmlTraitInformation);
        xmlTraitInformation.put(null, "transform", new TraitInformation(true, 9));
        SVGOMTextElement.xmlTraitInformation = xmlTraitInformation;
    }
}
