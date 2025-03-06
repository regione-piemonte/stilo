// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.dom.svg;

import org.apache.batik.anim.values.AnimatableMotionPointValue;
import org.apache.batik.anim.values.AnimatableValue;
import org.w3c.dom.svg.SVGStringList;
import org.apache.batik.dom.util.XMLSupport;
import org.w3c.dom.svg.SVGAnimatedBoolean;
import org.w3c.dom.svg.SVGAnimatedTransformList;
import org.w3c.dom.svg.SVGException;
import org.w3c.dom.svg.SVGMatrix;
import org.w3c.dom.svg.SVGRect;
import org.w3c.dom.Element;
import org.w3c.dom.svg.SVGElement;
import org.apache.batik.dom.AbstractDocument;
import java.awt.geom.AffineTransform;
import org.apache.batik.util.DoublyIndexedTable;

public abstract class SVGGraphicsElement extends SVGStylableElement implements SVGMotionAnimatableElement
{
    protected static DoublyIndexedTable xmlTraitInformation;
    protected SVGOMAnimatedTransformList transform;
    protected SVGOMAnimatedBoolean externalResourcesRequired;
    protected AffineTransform motionTransform;
    
    protected SVGGraphicsElement() {
    }
    
    protected SVGGraphicsElement(final String s, final AbstractDocument abstractDocument) {
        super(s, abstractDocument);
        this.initializeLiveAttributes();
    }
    
    protected void initializeAllLiveAttributes() {
        super.initializeAllLiveAttributes();
        this.initializeLiveAttributes();
    }
    
    private void initializeLiveAttributes() {
        this.transform = this.createLiveAnimatedTransformList(null, "transform", "");
        this.externalResourcesRequired = this.createLiveAnimatedBoolean(null, "externalResourcesRequired", false);
    }
    
    protected DoublyIndexedTable getTraitInformationTable() {
        return SVGGraphicsElement.xmlTraitInformation;
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
    
    public SVGStringList getRequiredFeatures() {
        return SVGTestsSupport.getRequiredFeatures(this);
    }
    
    public SVGStringList getRequiredExtensions() {
        return SVGTestsSupport.getRequiredExtensions(this);
    }
    
    public SVGStringList getSystemLanguage() {
        return SVGTestsSupport.getSystemLanguage(this);
    }
    
    public boolean hasExtension(final String s) {
        return SVGTestsSupport.hasExtension(this, s);
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
        final DoublyIndexedTable xmlTraitInformation = new DoublyIndexedTable(SVGStylableElement.xmlTraitInformation);
        xmlTraitInformation.put(null, "transform", new TraitInformation(true, 9));
        xmlTraitInformation.put(null, "externalResourcesRequired", new TraitInformation(true, 49));
        SVGGraphicsElement.xmlTraitInformation = xmlTraitInformation;
    }
}
