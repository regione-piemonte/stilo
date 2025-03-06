// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.dom.svg;

import org.w3c.dom.svg.SVGStringList;
import org.w3c.dom.svg.SVGAnimatedBoolean;
import org.w3c.dom.svg.SVGAnimatedPreserveAspectRatio;
import org.w3c.dom.svg.SVGAnimatedRect;
import org.apache.batik.dom.util.XMLSupport;
import org.w3c.dom.css.DocumentCSS;
import org.w3c.dom.stylesheets.DocumentStyle;
import org.w3c.dom.stylesheets.StyleSheetList;
import org.w3c.dom.events.DocumentEvent;
import org.w3c.dom.events.Event;
import org.w3c.dom.css.ViewCSS;
import org.w3c.dom.css.CSSStyleDeclaration;
import org.w3c.dom.views.DocumentView;
import org.w3c.dom.svg.SVGException;
import org.w3c.dom.Node;
import org.w3c.dom.svg.SVGTransform;
import org.w3c.dom.svg.SVGAngle;
import org.w3c.dom.svg.SVGLength;
import org.w3c.dom.svg.SVGNumber;
import org.apache.batik.dom.util.ListNodeList;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.svg.SVGElement;
import org.w3c.dom.svg.SVGMatrix;
import org.w3c.dom.svg.SVGPoint;
import java.awt.geom.AffineTransform;
import org.w3c.dom.svg.SVGViewSpec;
import org.w3c.dom.DOMException;
import org.w3c.dom.svg.SVGRect;
import org.w3c.dom.svg.SVGAnimatedLength;
import org.apache.batik.dom.AbstractDocument;
import org.apache.batik.util.DoublyIndexedTable;
import org.w3c.dom.svg.SVGSVGElement;

public class SVGOMSVGElement extends SVGStylableElement implements SVGSVGElement
{
    protected static DoublyIndexedTable xmlTraitInformation;
    protected static final AttributeInitializer attributeInitializer;
    protected SVGOMAnimatedLength x;
    protected SVGOMAnimatedLength y;
    protected SVGOMAnimatedLength width;
    protected SVGOMAnimatedLength height;
    protected SVGOMAnimatedBoolean externalResourcesRequired;
    protected SVGOMAnimatedPreserveAspectRatio preserveAspectRatio;
    protected SVGOMAnimatedRect viewBox;
    
    protected SVGOMSVGElement() {
    }
    
    public SVGOMSVGElement(final String s, final AbstractDocument abstractDocument) {
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
        this.width = this.createLiveAnimatedLength(null, "width", "100%", (short)2, true);
        this.height = this.createLiveAnimatedLength(null, "height", "100%", (short)1, true);
        this.externalResourcesRequired = this.createLiveAnimatedBoolean(null, "externalResourcesRequired", false);
        this.preserveAspectRatio = this.createLiveAnimatedPreserveAspectRatio();
        this.viewBox = this.createLiveAnimatedRect(null, "viewBox", null);
    }
    
    public String getLocalName() {
        return "svg";
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
    
    public String getContentScriptType() {
        return this.getAttributeNS(null, "contentScriptType");
    }
    
    public void setContentScriptType(final String s) {
        this.setAttributeNS(null, "contentScriptType", s);
    }
    
    public String getContentStyleType() {
        return this.getAttributeNS(null, "contentStyleType");
    }
    
    public void setContentStyleType(final String s) {
        this.setAttributeNS(null, "contentStyleType", s);
    }
    
    public SVGRect getViewport() {
        final SVGContext svgContext = this.getSVGContext();
        return (SVGRect)new SVGOMRect(0.0f, 0.0f, svgContext.getViewportWidth(), svgContext.getViewportHeight());
    }
    
    public float getPixelUnitToMillimeterX() {
        return this.getSVGContext().getPixelUnitToMillimeter();
    }
    
    public float getPixelUnitToMillimeterY() {
        return this.getSVGContext().getPixelUnitToMillimeter();
    }
    
    public float getScreenPixelToMillimeterX() {
        return this.getSVGContext().getPixelUnitToMillimeter();
    }
    
    public float getScreenPixelToMillimeterY() {
        return this.getSVGContext().getPixelUnitToMillimeter();
    }
    
    public boolean getUseCurrentView() {
        throw new UnsupportedOperationException("SVGSVGElement.getUseCurrentView is not implemented");
    }
    
    public void setUseCurrentView(final boolean b) throws DOMException {
        throw new UnsupportedOperationException("SVGSVGElement.setUseCurrentView is not implemented");
    }
    
    public SVGViewSpec getCurrentView() {
        throw new UnsupportedOperationException("SVGSVGElement.getCurrentView is not implemented");
    }
    
    public float getCurrentScale() {
        final AffineTransform screenTransform = this.getSVGContext().getScreenTransform();
        if (screenTransform != null) {
            return (float)Math.sqrt(screenTransform.getDeterminant());
        }
        return 1.0f;
    }
    
    public void setCurrentScale(final float n) throws DOMException {
        final SVGContext svgContext = this.getSVGContext();
        final AffineTransform screenTransform = svgContext.getScreenTransform();
        float n2 = 1.0f;
        if (screenTransform != null) {
            n2 = (float)Math.sqrt(screenTransform.getDeterminant());
        }
        final float n3 = n / n2;
        svgContext.setScreenTransform(new AffineTransform(screenTransform.getScaleX() * n3, screenTransform.getShearY() * n3, screenTransform.getShearX() * n3, screenTransform.getScaleY() * n3, screenTransform.getTranslateX(), screenTransform.getTranslateY()));
    }
    
    public SVGPoint getCurrentTranslate() {
        return (SVGPoint)new SVGPoint() {
            protected AffineTransform getScreenTransform() {
                return SVGOMSVGElement.this.getSVGContext().getScreenTransform();
            }
            
            public float getX() {
                return (float)this.getScreenTransform().getTranslateX();
            }
            
            public float getY() {
                return (float)this.getScreenTransform().getTranslateY();
            }
            
            public void setX(final float n) {
                final SVGContext svgContext = SVGOMSVGElement.this.getSVGContext();
                final AffineTransform screenTransform = svgContext.getScreenTransform();
                svgContext.setScreenTransform(new AffineTransform(screenTransform.getScaleX(), screenTransform.getShearY(), screenTransform.getShearX(), screenTransform.getScaleY(), n, screenTransform.getTranslateY()));
            }
            
            public void setY(final float n) {
                final SVGContext svgContext = SVGOMSVGElement.this.getSVGContext();
                final AffineTransform screenTransform = svgContext.getScreenTransform();
                svgContext.setScreenTransform(new AffineTransform(screenTransform.getScaleX(), screenTransform.getShearY(), screenTransform.getShearX(), screenTransform.getScaleY(), screenTransform.getTranslateX(), n));
            }
            
            public SVGPoint matrixTransform(final SVGMatrix svgMatrix) {
                final AffineTransform screenTransform = this.getScreenTransform();
                final float n = (float)screenTransform.getTranslateX();
                final float n2 = (float)screenTransform.getTranslateY();
                return (SVGPoint)new SVGOMPoint(svgMatrix.getA() * n + svgMatrix.getC() * n2 + svgMatrix.getE(), svgMatrix.getB() * n + svgMatrix.getD() * n2 + svgMatrix.getF());
            }
        };
    }
    
    public int suspendRedraw(int n) {
        if (n > 60000) {
            n = 60000;
        }
        else if (n < 0) {
            n = 0;
        }
        return ((SVGSVGContext)this.getSVGContext()).suspendRedraw(n);
    }
    
    public void unsuspendRedraw(final int value) throws DOMException {
        if (!((SVGSVGContext)this.getSVGContext()).unsuspendRedraw(value)) {
            throw this.createDOMException((short)8, "invalid.suspend.handle", new Object[] { new Integer(value) });
        }
    }
    
    public void unsuspendRedrawAll() {
        ((SVGSVGContext)this.getSVGContext()).unsuspendRedrawAll();
    }
    
    public void forceRedraw() {
        ((SVGSVGContext)this.getSVGContext()).forceRedraw();
    }
    
    public void pauseAnimations() {
        ((SVGSVGContext)this.getSVGContext()).pauseAnimations();
    }
    
    public void unpauseAnimations() {
        ((SVGSVGContext)this.getSVGContext()).unpauseAnimations();
    }
    
    public boolean animationsPaused() {
        return ((SVGSVGContext)this.getSVGContext()).animationsPaused();
    }
    
    public float getCurrentTime() {
        return ((SVGSVGContext)this.getSVGContext()).getCurrentTime();
    }
    
    public void setCurrentTime(final float currentTime) {
        ((SVGSVGContext)this.getSVGContext()).setCurrentTime(currentTime);
    }
    
    public NodeList getIntersectionList(final SVGRect svgRect, final SVGElement svgElement) {
        return new ListNodeList(((SVGSVGContext)this.getSVGContext()).getIntersectionList(svgRect, (Element)svgElement));
    }
    
    public NodeList getEnclosureList(final SVGRect svgRect, final SVGElement svgElement) {
        return new ListNodeList(((SVGSVGContext)this.getSVGContext()).getEnclosureList(svgRect, (Element)svgElement));
    }
    
    public boolean checkIntersection(final SVGElement svgElement, final SVGRect svgRect) {
        return ((SVGSVGContext)this.getSVGContext()).checkIntersection((Element)svgElement, svgRect);
    }
    
    public boolean checkEnclosure(final SVGElement svgElement, final SVGRect svgRect) {
        return ((SVGSVGContext)this.getSVGContext()).checkEnclosure((Element)svgElement, svgRect);
    }
    
    public void deselectAll() {
        ((SVGSVGContext)this.getSVGContext()).deselectAll();
    }
    
    public SVGNumber createSVGNumber() {
        return (SVGNumber)new SVGNumber() {
            protected float value;
            
            public float getValue() {
                return this.value;
            }
            
            public void setValue(final float value) {
                this.value = value;
            }
        };
    }
    
    public SVGLength createSVGLength() {
        return (SVGLength)new SVGOMLength(this);
    }
    
    public SVGAngle createSVGAngle() {
        return (SVGAngle)new SVGOMAngle();
    }
    
    public SVGPoint createSVGPoint() {
        return (SVGPoint)new SVGOMPoint(0.0f, 0.0f);
    }
    
    public SVGMatrix createSVGMatrix() {
        return (SVGMatrix)new AbstractSVGMatrix() {
            protected AffineTransform at = new AffineTransform();
            
            protected AffineTransform getAffineTransform() {
                return this.at;
            }
        };
    }
    
    public SVGRect createSVGRect() {
        return (SVGRect)new SVGOMRect(0.0f, 0.0f, 0.0f, 0.0f);
    }
    
    public SVGTransform createSVGTransform() {
        final SVGOMTransform svgomTransform = new SVGOMTransform();
        svgomTransform.setType((short)1);
        return (SVGTransform)svgomTransform;
    }
    
    public SVGTransform createSVGTransformFromMatrix(final SVGMatrix matrix) {
        final SVGOMTransform svgomTransform = new SVGOMTransform();
        svgomTransform.setMatrix(matrix);
        return (SVGTransform)svgomTransform;
    }
    
    public Element getElementById(final String s) {
        return this.ownerDocument.getChildElementById(this, s);
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
    
    public DocumentView getDocument() {
        return (DocumentView)this.getOwnerDocument();
    }
    
    public CSSStyleDeclaration getComputedStyle(final Element element, final String s) {
        return ((ViewCSS)((DocumentView)this.getOwnerDocument()).getDefaultView()).getComputedStyle(element, s);
    }
    
    public Event createEvent(final String s) throws DOMException {
        return ((DocumentEvent)this.getOwnerDocument()).createEvent(s);
    }
    
    public boolean canDispatch(final String s, final String s2) throws DOMException {
        return ((AbstractDocument)this.getOwnerDocument()).canDispatch(s, s2);
    }
    
    public StyleSheetList getStyleSheets() {
        return ((DocumentStyle)this.getOwnerDocument()).getStyleSheets();
    }
    
    public CSSStyleDeclaration getOverrideStyle(final Element element, final String s) {
        return ((DocumentCSS)this.getOwnerDocument()).getOverrideStyle(element, s);
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
    
    public short getZoomAndPan() {
        return SVGZoomAndPanSupport.getZoomAndPan(this);
    }
    
    public void setZoomAndPan(final short n) {
        SVGZoomAndPanSupport.setZoomAndPan(this, n);
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
    
    protected AttributeInitializer getAttributeInitializer() {
        return SVGOMSVGElement.attributeInitializer;
    }
    
    protected Node newNode() {
        return new SVGOMSVGElement();
    }
    
    protected DoublyIndexedTable getTraitInformationTable() {
        return SVGOMSVGElement.xmlTraitInformation;
    }
    
    static {
        final DoublyIndexedTable xmlTraitInformation = new DoublyIndexedTable(SVGStylableElement.xmlTraitInformation);
        xmlTraitInformation.put(null, "x", new TraitInformation(true, 3, (short)1));
        xmlTraitInformation.put(null, "y", new TraitInformation(true, 3, (short)2));
        xmlTraitInformation.put(null, "width", new TraitInformation(true, 3, (short)1));
        xmlTraitInformation.put(null, "height", new TraitInformation(true, 3, (short)2));
        xmlTraitInformation.put(null, "preserveAspectRatio", new TraitInformation(true, 32));
        xmlTraitInformation.put(null, "viewBox", new TraitInformation(true, 50));
        xmlTraitInformation.put(null, "externalResourcesRequired", new TraitInformation(true, 49));
        SVGOMSVGElement.xmlTraitInformation = xmlTraitInformation;
        (attributeInitializer = new AttributeInitializer(7)).addAttribute("http://www.w3.org/2000/xmlns/", null, "xmlns", "http://www.w3.org/2000/svg");
        SVGOMSVGElement.attributeInitializer.addAttribute("http://www.w3.org/2000/xmlns/", "xmlns", "xlink", "http://www.w3.org/1999/xlink");
        SVGOMSVGElement.attributeInitializer.addAttribute(null, null, "preserveAspectRatio", "xMidYMid meet");
        SVGOMSVGElement.attributeInitializer.addAttribute(null, null, "zoomAndPan", "magnify");
        SVGOMSVGElement.attributeInitializer.addAttribute(null, null, "version", "1.0");
        SVGOMSVGElement.attributeInitializer.addAttribute(null, null, "contentScriptType", "text/ecmascript");
        SVGOMSVGElement.attributeInitializer.addAttribute(null, null, "contentStyleType", "text/css");
    }
}
