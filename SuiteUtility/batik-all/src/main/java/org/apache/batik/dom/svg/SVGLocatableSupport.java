// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.dom.svg;

import org.w3c.dom.svg.SVGException;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.geom.AffineTransform;
import org.w3c.dom.svg.SVGMatrix;
import org.w3c.dom.DOMException;
import org.w3c.dom.svg.SVGRect;
import org.w3c.dom.svg.SVGFitToViewBox;
import org.apache.batik.css.engine.CSSEngine;
import org.w3c.dom.svg.SVGElement;
import org.w3c.dom.Element;

public class SVGLocatableSupport
{
    public static SVGElement getNearestViewportElement(final Element element) {
        Element parentCSSStylableElement = element;
        while (parentCSSStylableElement != null) {
            parentCSSStylableElement = CSSEngine.getParentCSSStylableElement(parentCSSStylableElement);
            if (parentCSSStylableElement instanceof SVGFitToViewBox) {
                break;
            }
        }
        return (SVGElement)parentCSSStylableElement;
    }
    
    public static SVGElement getFarthestViewportElement(final Element element) {
        return (SVGElement)element.getOwnerDocument().getDocumentElement();
    }
    
    public static SVGRect getBBox(final Element element) {
        final SVGOMElement svgomElement = (SVGOMElement)element;
        final SVGContext svgContext = svgomElement.getSVGContext();
        if (svgContext == null) {
            return null;
        }
        if (svgContext.getBBox() == null) {
            return null;
        }
        return (SVGRect)new SVGRect() {
            public float getX() {
                return (float)svgomElement.getSVGContext().getBBox().getX();
            }
            
            public void setX(final float n) throws DOMException {
                throw svgomElement.createDOMException((short)7, "readonly.rect", null);
            }
            
            public float getY() {
                return (float)svgomElement.getSVGContext().getBBox().getY();
            }
            
            public void setY(final float n) throws DOMException {
                throw svgomElement.createDOMException((short)7, "readonly.rect", null);
            }
            
            public float getWidth() {
                return (float)svgomElement.getSVGContext().getBBox().getWidth();
            }
            
            public void setWidth(final float n) throws DOMException {
                throw svgomElement.createDOMException((short)7, "readonly.rect", null);
            }
            
            public float getHeight() {
                return (float)svgomElement.getSVGContext().getBBox().getHeight();
            }
            
            public void setHeight(final float n) throws DOMException {
                throw svgomElement.createDOMException((short)7, "readonly.rect", null);
            }
        };
    }
    
    public static SVGMatrix getCTM(final Element element) {
        return (SVGMatrix)new AbstractSVGMatrix() {
            private final /* synthetic */ SVGOMElement val$svgelt = (SVGOMElement)element;
            
            protected AffineTransform getAffineTransform() {
                return this.val$svgelt.getSVGContext().getCTM();
            }
        };
    }
    
    public static SVGMatrix getScreenCTM(final Element element) {
        return (SVGMatrix)new AbstractSVGMatrix() {
            private final /* synthetic */ SVGOMElement val$svgelt = (SVGOMElement)element;
            
            protected AffineTransform getAffineTransform() {
                final SVGContext svgContext = this.val$svgelt.getSVGContext();
                final AffineTransform globalTransform = svgContext.getGlobalTransform();
                final AffineTransform screenTransform = svgContext.getScreenTransform();
                if (screenTransform != null) {
                    globalTransform.preConcatenate(screenTransform);
                }
                return globalTransform;
            }
        };
    }
    
    public static SVGMatrix getTransformToElement(final Element element, final SVGElement svgElement) throws SVGException {
        return (SVGMatrix)new AbstractSVGMatrix() {
            private final /* synthetic */ SVGOMElement val$currentElt = (SVGOMElement)element;
            private final /* synthetic */ SVGOMElement val$targetElt = (SVGOMElement)svgElement;
            
            protected AffineTransform getAffineTransform() {
                AffineTransform globalTransform = this.val$currentElt.getSVGContext().getGlobalTransform();
                if (globalTransform == null) {
                    globalTransform = new AffineTransform();
                }
                AffineTransform globalTransform2 = this.val$targetElt.getSVGContext().getGlobalTransform();
                if (globalTransform2 == null) {
                    globalTransform2 = new AffineTransform();
                }
                final AffineTransform affineTransform = new AffineTransform(globalTransform);
                try {
                    affineTransform.preConcatenate(globalTransform2.createInverse());
                    return affineTransform;
                }
                catch (NoninvertibleTransformException ex) {
                    throw this.val$currentElt.createSVGException((short)2, "noninvertiblematrix", null);
                }
            }
        };
    }
}
