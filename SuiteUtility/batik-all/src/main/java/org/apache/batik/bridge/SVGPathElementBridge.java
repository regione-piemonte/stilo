// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.bridge;

import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;
import org.apache.batik.dom.svg.AnimatedLiveAttributeValue;
import org.w3c.dom.svg.SVGPathSegList;
import org.apache.batik.dom.svg.SVGOMAnimatedPathData;
import org.apache.batik.dom.svg.LiveAttributeException;
import org.apache.batik.parser.PathHandler;
import org.apache.batik.dom.svg.SVGAnimatedPathDataSupport;
import org.apache.batik.parser.AWTPathProducer;
import org.apache.batik.dom.svg.SVGOMPathElement;
import org.apache.batik.gvt.ShapeNode;
import org.w3c.dom.Element;
import org.apache.batik.ext.awt.geom.PathLength;
import java.awt.Shape;
import org.apache.batik.dom.svg.SVGPathContext;

public class SVGPathElementBridge extends SVGDecoratedShapeElementBridge implements SVGPathContext
{
    protected static final Shape DEFAULT_SHAPE;
    protected Shape pathLengthShape;
    protected PathLength pathLength;
    
    public String getLocalName() {
        return "path";
    }
    
    public Bridge getInstance() {
        return new SVGPathElementBridge();
    }
    
    protected void buildShape(final BridgeContext bridgeContext, final Element element, final ShapeNode shapeNode) {
        final SVGOMPathElement svgomPathElement = (SVGOMPathElement)element;
        final AWTPathProducer awtPathProducer = new AWTPathProducer();
        try {
            final SVGOMAnimatedPathData animatedPathData = svgomPathElement.getAnimatedPathData();
            animatedPathData.check();
            final SVGPathSegList animatedPathSegList = animatedPathData.getAnimatedPathSegList();
            awtPathProducer.setWindingRule(CSSUtilities.convertFillRule(element));
            SVGAnimatedPathDataSupport.handlePathSegList(animatedPathSegList, awtPathProducer);
        }
        catch (LiveAttributeException ex) {
            throw new BridgeException(bridgeContext, ex);
        }
        finally {
            shapeNode.setShape(awtPathProducer.getShape());
        }
    }
    
    public void handleAnimatedAttributeChanged(final AnimatedLiveAttributeValue animatedLiveAttributeValue) {
        if (animatedLiveAttributeValue.getNamespaceURI() == null && animatedLiveAttributeValue.getLocalName().equals("d")) {
            this.buildShape(this.ctx, this.e, (ShapeNode)this.node);
            this.handleGeometryChanged();
        }
        else {
            super.handleAnimatedAttributeChanged(animatedLiveAttributeValue);
        }
    }
    
    protected void handleCSSPropertyChanged(final int n) {
        switch (n) {
            case 17: {
                this.buildShape(this.ctx, this.e, (ShapeNode)this.node);
                this.handleGeometryChanged();
                break;
            }
            default: {
                super.handleCSSPropertyChanged(n);
                break;
            }
        }
    }
    
    protected PathLength getPathLengthObj() {
        final Shape shape = ((ShapeNode)this.node).getShape();
        if (this.pathLengthShape != shape) {
            this.pathLength = new PathLength(shape);
            this.pathLengthShape = shape;
        }
        return this.pathLength;
    }
    
    public float getTotalLength() {
        return this.getPathLengthObj().lengthOfPath();
    }
    
    public Point2D getPointAtLength(final float n) {
        return this.getPathLengthObj().pointAtLength(n);
    }
    
    public int getPathSegAtLength(final float n) {
        return this.getPathLengthObj().segmentAtLength(n);
    }
    
    static {
        DEFAULT_SHAPE = new GeneralPath();
    }
}
