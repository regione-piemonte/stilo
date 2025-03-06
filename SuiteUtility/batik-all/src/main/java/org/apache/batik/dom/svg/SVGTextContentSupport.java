// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.dom.svg;

import java.awt.geom.Point2D;
import org.w3c.dom.svg.SVGPoint;
import java.awt.geom.Rectangle2D;
import org.w3c.dom.DOMException;
import org.w3c.dom.svg.SVGRect;
import org.w3c.dom.Element;

public class SVGTextContentSupport
{
    public static int getNumberOfChars(final Element element) {
        return ((SVGTextContent)((SVGOMElement)element).getSVGContext()).getNumberOfChars();
    }
    
    public static SVGRect getExtentOfChar(final Element element, final int n) {
        final SVGOMElement svgomElement = (SVGOMElement)element;
        if (n < 0 || n >= getNumberOfChars(element)) {
            throw svgomElement.createDOMException((short)1, "", null);
        }
        return (SVGRect)new SVGRect() {
            private final /* synthetic */ SVGTextContent val$context = (SVGTextContent)svgomElement.getSVGContext();
            
            public float getX() {
                return (float)SVGTextContentSupport.getExtent(svgomElement, this.val$context, n).getX();
            }
            
            public void setX(final float n) throws DOMException {
                throw svgomElement.createDOMException((short)7, "readonly.rect", null);
            }
            
            public float getY() {
                return (float)SVGTextContentSupport.getExtent(svgomElement, this.val$context, n).getY();
            }
            
            public void setY(final float n) throws DOMException {
                throw svgomElement.createDOMException((short)7, "readonly.rect", null);
            }
            
            public float getWidth() {
                return (float)SVGTextContentSupport.getExtent(svgomElement, this.val$context, n).getWidth();
            }
            
            public void setWidth(final float n) throws DOMException {
                throw svgomElement.createDOMException((short)7, "readonly.rect", null);
            }
            
            public float getHeight() {
                return (float)SVGTextContentSupport.getExtent(svgomElement, this.val$context, n).getHeight();
            }
            
            public void setHeight(final float n) throws DOMException {
                throw svgomElement.createDOMException((short)7, "readonly.rect", null);
            }
        };
    }
    
    protected static Rectangle2D getExtent(final SVGOMElement svgomElement, final SVGTextContent svgTextContent, final int n) {
        final Rectangle2D extentOfChar = svgTextContent.getExtentOfChar(n);
        if (extentOfChar == null) {
            throw svgomElement.createDOMException((short)1, "", null);
        }
        return extentOfChar;
    }
    
    public static SVGPoint getStartPositionOfChar(final Element element, final int n) throws DOMException {
        final SVGOMElement svgomElement = (SVGOMElement)element;
        if (n < 0 || n >= getNumberOfChars(element)) {
            throw svgomElement.createDOMException((short)1, "", null);
        }
        return (SVGPoint)new SVGTextPoint(svgomElement) {
            private final /* synthetic */ SVGTextContent val$context = (SVGTextContent)svgomElement.getSVGContext();
            
            public float getX() {
                return (float)SVGTextContentSupport.getStartPos(this.svgelt, this.val$context, n).getX();
            }
            
            public float getY() {
                return (float)SVGTextContentSupport.getStartPos(this.svgelt, this.val$context, n).getY();
            }
        };
    }
    
    protected static Point2D getStartPos(final SVGOMElement svgomElement, final SVGTextContent svgTextContent, final int n) {
        final Point2D startPositionOfChar = svgTextContent.getStartPositionOfChar(n);
        if (startPositionOfChar == null) {
            throw svgomElement.createDOMException((short)1, "", null);
        }
        return startPositionOfChar;
    }
    
    public static SVGPoint getEndPositionOfChar(final Element element, final int n) throws DOMException {
        final SVGOMElement svgomElement = (SVGOMElement)element;
        if (n < 0 || n >= getNumberOfChars(element)) {
            throw svgomElement.createDOMException((short)1, "", null);
        }
        return (SVGPoint)new SVGTextPoint(svgomElement) {
            private final /* synthetic */ SVGTextContent val$context = (SVGTextContent)svgomElement.getSVGContext();
            
            public float getX() {
                return (float)SVGTextContentSupport.getEndPos(this.svgelt, this.val$context, n).getX();
            }
            
            public float getY() {
                return (float)SVGTextContentSupport.getEndPos(this.svgelt, this.val$context, n).getY();
            }
        };
    }
    
    protected static Point2D getEndPos(final SVGOMElement svgomElement, final SVGTextContent svgTextContent, final int n) {
        final Point2D endPositionOfChar = svgTextContent.getEndPositionOfChar(n);
        if (endPositionOfChar == null) {
            throw svgomElement.createDOMException((short)1, "", null);
        }
        return endPositionOfChar;
    }
    
    public static void selectSubString(final Element element, final int n, final int n2) {
        final SVGOMElement svgomElement = (SVGOMElement)element;
        if (n < 0 || n >= getNumberOfChars(element)) {
            throw svgomElement.createDOMException((short)1, "", null);
        }
        ((SVGTextContent)svgomElement.getSVGContext()).selectSubString(n, n2);
    }
    
    public static float getRotationOfChar(final Element element, final int n) {
        final SVGOMElement svgomElement = (SVGOMElement)element;
        if (n < 0 || n >= getNumberOfChars(element)) {
            throw svgomElement.createDOMException((short)1, "", null);
        }
        return ((SVGTextContent)svgomElement.getSVGContext()).getRotationOfChar(n);
    }
    
    public static float getComputedTextLength(final Element element) {
        return ((SVGTextContent)((SVGOMElement)element).getSVGContext()).getComputedTextLength();
    }
    
    public static float getSubStringLength(final Element element, final int n, final int n2) {
        final SVGOMElement svgomElement = (SVGOMElement)element;
        if (n < 0 || n >= getNumberOfChars(element)) {
            throw svgomElement.createDOMException((short)1, "", null);
        }
        return ((SVGTextContent)svgomElement.getSVGContext()).getSubStringLength(n, n2);
    }
    
    public static int getCharNumAtPosition(final Element element, final float n, final float n2) throws DOMException {
        return ((SVGTextContent)((SVGOMElement)element).getSVGContext()).getCharNumAtPosition(n, n2);
    }
    
    public static class SVGTextPoint extends SVGOMPoint
    {
        SVGOMElement svgelt;
        
        SVGTextPoint(final SVGOMElement svgelt) {
            this.svgelt = svgelt;
        }
        
        public void setX(final float n) throws DOMException {
            throw this.svgelt.createDOMException((short)7, "readonly.point", null);
        }
        
        public void setY(final float n) throws DOMException {
            throw this.svgelt.createDOMException((short)7, "readonly.point", null);
        }
    }
}
