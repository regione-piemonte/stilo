// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.dom.svg;

import org.w3c.dom.svg.SVGStringList;
import org.apache.batik.dom.util.XMLSupport;
import org.w3c.dom.svg.SVGAnimatedBoolean;
import org.w3c.dom.svg.SVGRect;
import org.w3c.dom.svg.SVGPoint;
import org.w3c.dom.DOMException;
import org.w3c.dom.Element;
import org.w3c.dom.svg.SVGAnimatedEnumeration;
import org.w3c.dom.svg.SVGAnimatedLength;
import org.w3c.dom.svg.SVGLength;
import org.apache.batik.dom.AbstractDocument;
import org.apache.batik.util.DoublyIndexedTable;

public abstract class SVGOMTextContentElement extends SVGStylableElement
{
    protected static DoublyIndexedTable xmlTraitInformation;
    protected static final String[] LENGTH_ADJUST_VALUES;
    protected SVGOMAnimatedBoolean externalResourcesRequired;
    protected AbstractSVGAnimatedLength textLength;
    protected SVGOMAnimatedEnumeration lengthAdjust;
    
    protected SVGOMTextContentElement() {
    }
    
    protected SVGOMTextContentElement(final String s, final AbstractDocument abstractDocument) {
        super(s, abstractDocument);
        this.initializeLiveAttributes();
    }
    
    protected void initializeAllLiveAttributes() {
        super.initializeAllLiveAttributes();
        this.initializeLiveAttributes();
    }
    
    private void initializeLiveAttributes() {
        this.externalResourcesRequired = this.createLiveAnimatedBoolean(null, "externalResourcesRequired", false);
        this.lengthAdjust = this.createLiveAnimatedEnumeration(null, "lengthAdjust", SVGOMTextContentElement.LENGTH_ADJUST_VALUES, (short)1);
        this.textLength = new AbstractSVGAnimatedLength("textLength", (short)2, true) {
            boolean usedDefault;
            
            protected String getDefaultValue() {
                this.usedDefault = true;
                return String.valueOf(SVGOMTextContentElement.this.getComputedTextLength());
            }
            
            public SVGLength getBaseVal() {
                if (this.baseVal == null) {
                    this.baseVal = new SVGTextLength(this.direction);
                }
                return (SVGLength)this.baseVal;
            }
            
            class SVGTextLength extends BaseSVGLength
            {
                private final /* synthetic */ SVGOMTextContentElement$1 this$1 = this;
                
                public SVGTextLength(final short n) {
                    this$1.super(n);
                }
                
                protected void revalidate() {
                    this.this$1.usedDefault = false;
                    super.revalidate();
                    if (this.this$1.usedDefault) {
                        this.valid = false;
                    }
                }
            }
        };
        this.liveAttributeValues.put(null, "textLength", this.textLength);
        this.textLength.addAnimatedAttributeListener(((SVGOMDocument)this.ownerDocument).getAnimatedAttributeListener());
    }
    
    public SVGAnimatedLength getTextLength() {
        return (SVGAnimatedLength)this.textLength;
    }
    
    public SVGAnimatedEnumeration getLengthAdjust() {
        return (SVGAnimatedEnumeration)this.lengthAdjust;
    }
    
    public int getNumberOfChars() {
        return SVGTextContentSupport.getNumberOfChars(this);
    }
    
    public float getComputedTextLength() {
        return SVGTextContentSupport.getComputedTextLength(this);
    }
    
    public float getSubStringLength(final int n, final int n2) throws DOMException {
        return SVGTextContentSupport.getSubStringLength(this, n, n2);
    }
    
    public SVGPoint getStartPositionOfChar(final int n) throws DOMException {
        return SVGTextContentSupport.getStartPositionOfChar(this, n);
    }
    
    public SVGPoint getEndPositionOfChar(final int n) throws DOMException {
        return SVGTextContentSupport.getEndPositionOfChar(this, n);
    }
    
    public SVGRect getExtentOfChar(final int n) throws DOMException {
        return SVGTextContentSupport.getExtentOfChar(this, n);
    }
    
    public float getRotationOfChar(final int n) throws DOMException {
        return SVGTextContentSupport.getRotationOfChar(this, n);
    }
    
    public int getCharNumAtPosition(final SVGPoint svgPoint) {
        return SVGTextContentSupport.getCharNumAtPosition(this, svgPoint.getX(), svgPoint.getY());
    }
    
    public void selectSubString(final int n, final int n2) throws DOMException {
        SVGTextContentSupport.selectSubString(this, n, n2);
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
    
    protected DoublyIndexedTable getTraitInformationTable() {
        return SVGOMTextContentElement.xmlTraitInformation;
    }
    
    static {
        final DoublyIndexedTable xmlTraitInformation = new DoublyIndexedTable(SVGStylableElement.xmlTraitInformation);
        xmlTraitInformation.put(null, "textLength", new TraitInformation(true, 3, (short)3));
        xmlTraitInformation.put(null, "lengthAdjust", new TraitInformation(true, 15));
        xmlTraitInformation.put(null, "externalResourcesRequired", new TraitInformation(true, 49));
        SVGOMTextContentElement.xmlTraitInformation = xmlTraitInformation;
        LENGTH_ADJUST_VALUES = new String[] { "", "spacing", "spacingAndGlyphs" };
    }
}
