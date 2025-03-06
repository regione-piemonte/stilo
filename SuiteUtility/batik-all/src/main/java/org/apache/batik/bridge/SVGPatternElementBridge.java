// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.bridge;

import java.awt.Graphics2D;
import java.awt.Shape;
import org.apache.batik.gvt.AbstractGraphicsNode;
import java.util.Iterator;
import org.apache.batik.gvt.CompositeGraphicsNode;
import java.util.List;
import org.apache.batik.dom.svg.SVGOMDocument;
import org.apache.batik.dom.util.XLinkSupport;
import org.apache.batik.util.ParsedURL;
import java.util.LinkedList;
import java.awt.geom.Rectangle2D;
import org.apache.batik.gvt.PatternPaint;
import org.apache.batik.ext.awt.image.renderable.Filter;
import org.apache.batik.ext.awt.image.renderable.ComponentTransferRable8Bit;
import org.apache.batik.ext.awt.image.ConcreteComponentTransferFunction;
import java.awt.geom.AffineTransform;
import org.w3c.dom.Node;
import org.apache.batik.gvt.RootGraphicsNode;
import java.awt.Paint;
import org.apache.batik.gvt.GraphicsNode;
import org.w3c.dom.Element;

public class SVGPatternElementBridge extends AnimatableGenericSVGBridge implements PaintBridge, ErrorConstants
{
    public String getLocalName() {
        return "pattern";
    }
    
    public Paint createPaint(final BridgeContext bridgeContext, final Element element, final Element element2, final GraphicsNode graphicsNode, final float n) {
        RootGraphicsNode patternContent = (RootGraphicsNode)bridgeContext.getElementData(element);
        if (patternContent == null) {
            patternContent = extractPatternContent(element, bridgeContext);
            bridgeContext.setElementData(element, patternContent);
        }
        if (patternContent == null) {
            return null;
        }
        final Rectangle2D convertPatternRegion = SVGUtilities.convertPatternRegion(element, element2, graphicsNode, bridgeContext);
        final String chainableAttributeNS = SVGUtilities.getChainableAttributeNS(element, null, "patternTransform", bridgeContext);
        AffineTransform convertTransform;
        if (chainableAttributeNS.length() != 0) {
            convertTransform = SVGUtilities.convertTransform(element, "patternTransform", chainableAttributeNS, bridgeContext);
        }
        else {
            convertTransform = new AffineTransform();
        }
        final boolean convertOverflow = CSSUtilities.convertOverflow(element);
        final String chainableAttributeNS2 = SVGUtilities.getChainableAttributeNS(element, null, "patternContentUnits", bridgeContext);
        int coordinateSystem;
        if (chainableAttributeNS2.length() == 0) {
            coordinateSystem = 1;
        }
        else {
            coordinateSystem = SVGUtilities.parseCoordinateSystem(element, "patternContentUnits", chainableAttributeNS2, bridgeContext);
        }
        final AffineTransform transform = new AffineTransform();
        transform.translate(convertPatternRegion.getX(), convertPatternRegion.getY());
        final String chainableAttributeNS3 = SVGUtilities.getChainableAttributeNS(element, null, "viewBox", bridgeContext);
        if (chainableAttributeNS3.length() > 0) {
            transform.concatenate(ViewBox.getPreserveAspectRatioTransform(element, chainableAttributeNS3, SVGUtilities.getChainableAttributeNS(element, null, "preserveAspectRatio", bridgeContext), (float)convertPatternRegion.getWidth(), (float)convertPatternRegion.getHeight(), bridgeContext));
        }
        else if (coordinateSystem == 2) {
            final AffineTransform tx = new AffineTransform();
            final Rectangle2D geometryBounds = graphicsNode.getGeometryBounds();
            tx.translate(geometryBounds.getX(), geometryBounds.getY());
            tx.scale(geometryBounds.getWidth(), geometryBounds.getHeight());
            transform.concatenate(tx);
        }
        final PatternGraphicsNode patternGraphicsNode = new PatternGraphicsNode(patternContent);
        patternGraphicsNode.setTransform(transform);
        if (n != 1.0f) {
            patternGraphicsNode.setFilter(new ComponentTransferRable8Bit(patternGraphicsNode.getGraphicsNodeRable(true), ConcreteComponentTransferFunction.getLinearTransfer(n, 0.0f), ConcreteComponentTransferFunction.getIdentityTransfer(), ConcreteComponentTransferFunction.getIdentityTransfer(), ConcreteComponentTransferFunction.getIdentityTransfer()));
        }
        return new PatternPaint(patternGraphicsNode, convertPatternRegion, !convertOverflow, convertTransform);
    }
    
    protected static RootGraphicsNode extractPatternContent(Element referencedElement, final BridgeContext bridgeContext) {
        final LinkedList<ParsedURL> list = new LinkedList<ParsedURL>();
        while (true) {
            final RootGraphicsNode localPatternContent = extractLocalPatternContent(referencedElement, bridgeContext);
            if (localPatternContent != null) {
                return localPatternContent;
            }
            final String xLinkHref = XLinkSupport.getXLinkHref(referencedElement);
            if (xLinkHref.length() == 0) {
                return null;
            }
            final ParsedURL parsedURL = new ParsedURL(((SVGOMDocument)referencedElement.getOwnerDocument()).getURL(), xLinkHref);
            if (!parsedURL.complete()) {
                throw new BridgeException(bridgeContext, referencedElement, "uri.malformed", new Object[] { xLinkHref });
            }
            if (contains(list, parsedURL)) {
                throw new BridgeException(bridgeContext, referencedElement, "xlink.href.circularDependencies", new Object[] { xLinkHref });
            }
            list.add(parsedURL);
            referencedElement = bridgeContext.getReferencedElement(referencedElement, xLinkHref);
        }
    }
    
    protected static RootGraphicsNode extractLocalPatternContent(final Element element, final BridgeContext bridgeContext) {
        final GVTBuilder gvtBuilder = bridgeContext.getGVTBuilder();
        CompositeGraphicsNode compositeGraphicsNode = null;
        for (Node node = element.getFirstChild(); node != null; node = node.getNextSibling()) {
            if (node.getNodeType() == 1) {
                final GraphicsNode build = gvtBuilder.build(bridgeContext, (Element)node);
                if (build != null) {
                    if (compositeGraphicsNode == null) {
                        compositeGraphicsNode = new RootGraphicsNode();
                    }
                    compositeGraphicsNode.getChildren().add(build);
                }
            }
        }
        return (RootGraphicsNode)compositeGraphicsNode;
    }
    
    private static boolean contains(final List list, final ParsedURL parsedURL) {
        final Iterator<Object> iterator = list.iterator();
        while (iterator.hasNext()) {
            if (parsedURL.equals(iterator.next())) {
                return true;
            }
        }
        return false;
    }
    
    public static class PatternGraphicsNode extends AbstractGraphicsNode
    {
        GraphicsNode pcn;
        Rectangle2D pBounds;
        Rectangle2D gBounds;
        Rectangle2D sBounds;
        Shape oShape;
        
        public PatternGraphicsNode(final GraphicsNode pcn) {
            this.pcn = pcn;
        }
        
        public void primitivePaint(final Graphics2D graphics2D) {
            this.pcn.paint(graphics2D);
        }
        
        public Rectangle2D getPrimitiveBounds() {
            if (this.pBounds != null) {
                return this.pBounds;
            }
            return this.pBounds = this.pcn.getTransformedBounds(GraphicsNode.IDENTITY);
        }
        
        public Rectangle2D getGeometryBounds() {
            if (this.gBounds != null) {
                return this.gBounds;
            }
            return this.gBounds = this.pcn.getTransformedGeometryBounds(GraphicsNode.IDENTITY);
        }
        
        public Rectangle2D getSensitiveBounds() {
            if (this.sBounds != null) {
                return this.sBounds;
            }
            return this.sBounds = this.pcn.getTransformedSensitiveBounds(GraphicsNode.IDENTITY);
        }
        
        public Shape getOutline() {
            if (this.oShape != null) {
                return this.oShape;
            }
            this.oShape = this.pcn.getOutline();
            final AffineTransform transform = this.pcn.getTransform();
            if (transform != null) {
                this.oShape = transform.createTransformedShape(this.oShape);
            }
            return this.oShape;
        }
        
        protected void invalidateGeometryCache() {
            this.pBounds = null;
            this.gBounds = null;
            this.sBounds = null;
            this.oShape = null;
            super.invalidateGeometryCache();
        }
    }
}
