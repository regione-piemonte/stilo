// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.bridge;

import org.apache.batik.ext.awt.image.spi.ImageTagRegistry;
import org.apache.batik.util.ParsedURL;
import org.w3c.dom.Document;
import org.apache.batik.ext.awt.image.renderable.PadRable8Bit;
import org.apache.batik.ext.awt.image.PadMode;
import org.apache.batik.ext.awt.image.renderable.AffineRable8Bit;
import java.awt.geom.AffineTransform;
import org.w3c.dom.Node;
import org.apache.batik.dom.util.XLinkSupport;
import java.util.Map;
import java.awt.geom.Rectangle2D;
import org.apache.batik.ext.awt.image.renderable.Filter;
import org.apache.batik.gvt.GraphicsNode;
import org.w3c.dom.Element;

public class SVGFeImageElementBridge extends AbstractSVGFilterPrimitiveElementBridge
{
    public String getLocalName() {
        return "feImage";
    }
    
    public Filter createFilter(final BridgeContext bridgeContext, final Element element, final Element element2, final GraphicsNode graphicsNode, final Filter filter, final Rectangle2D rectangle2D, final Map map) {
        final String xLinkHref = XLinkSupport.getXLinkHref(element);
        if (xLinkHref.length() == 0) {
            throw new BridgeException(bridgeContext, element, "attribute.missing", new Object[] { "xlink:href" });
        }
        final Document ownerDocument = element.getOwnerDocument();
        Element element3;
        if (xLinkHref.indexOf(35) != -1) {
            element3 = ownerDocument.createElementNS("http://www.w3.org/2000/svg", "use");
        }
        else {
            element3 = ownerDocument.createElementNS("http://www.w3.org/2000/svg", "image");
        }
        element3.setAttributeNS("http://www.w3.org/1999/xlink", "xlink:href", xLinkHref);
        final Element elementNS = ownerDocument.createElementNS("http://www.w3.org/2000/svg", "g");
        elementNS.appendChild(element3);
        final Element element4 = (Element)element.getParentNode();
        final Rectangle2D baseFilterPrimitiveRegion = SVGUtilities.getBaseFilterPrimitiveRegion(element, element2, graphicsNode, rectangle2D, bridgeContext);
        element3.setAttributeNS(null, "x", String.valueOf(baseFilterPrimitiveRegion.getX()));
        element3.setAttributeNS(null, "y", String.valueOf(baseFilterPrimitiveRegion.getY()));
        element3.setAttributeNS(null, "width", String.valueOf(baseFilterPrimitiveRegion.getWidth()));
        element3.setAttributeNS(null, "height", String.valueOf(baseFilterPrimitiveRegion.getHeight()));
        final Filter graphicsNodeRable = bridgeContext.getGVTBuilder().build(bridgeContext, elementNS).getGraphicsNodeRable(true);
        final String chainableAttributeNS = SVGUtilities.getChainableAttributeNS(element4, null, "primitiveUnits", bridgeContext);
        int coordinateSystem;
        if (chainableAttributeNS.length() == 0) {
            coordinateSystem = 1;
        }
        else {
            coordinateSystem = SVGUtilities.parseCoordinateSystem(element4, "primitiveUnits", chainableAttributeNS, bridgeContext);
        }
        AffineTransform objectBBox = new AffineTransform();
        if (coordinateSystem == 2) {
            objectBBox = SVGUtilities.toObjectBBox(objectBBox, graphicsNode);
        }
        final AffineRable8Bit affineRable8Bit = new AffineRable8Bit(graphicsNodeRable, objectBBox);
        AbstractSVGFilterPrimitiveElementBridge.handleColorInterpolationFilters(affineRable8Bit, element);
        final PadRable8Bit padRable8Bit = new PadRable8Bit(affineRable8Bit, SVGUtilities.convertFilterPrimitiveRegion(element, element2, graphicsNode, rectangle2D, rectangle2D, bridgeContext), PadMode.ZERO_PAD);
        AbstractSVGFilterPrimitiveElementBridge.updateFilterMap(element, padRable8Bit, map);
        return padRable8Bit;
    }
    
    protected static Filter createSVGFeImage(final BridgeContext bridgeContext, final Rectangle2D rectangle2D, final Element element, final boolean b, final Element element2, final GraphicsNode graphicsNode) {
        final Filter graphicsNodeRable = bridgeContext.getGVTBuilder().build(bridgeContext, element).getGraphicsNodeRable(true);
        AffineTransform objectBBox = new AffineTransform();
        if (b) {
            final Element element3 = (Element)element2.getParentNode();
            final String chainableAttributeNS = SVGUtilities.getChainableAttributeNS(element3, null, "primitiveUnits", bridgeContext);
            int coordinateSystem;
            if (chainableAttributeNS.length() == 0) {
                coordinateSystem = 1;
            }
            else {
                coordinateSystem = SVGUtilities.parseCoordinateSystem(element3, "primitiveUnits", chainableAttributeNS, bridgeContext);
            }
            if (coordinateSystem == 2) {
                objectBBox = SVGUtilities.toObjectBBox(objectBBox, graphicsNode);
            }
            final Rectangle2D geometryBounds = graphicsNode.getGeometryBounds();
            objectBBox.preConcatenate(AffineTransform.getTranslateInstance(rectangle2D.getX() - geometryBounds.getX(), rectangle2D.getY() - geometryBounds.getY()));
        }
        else {
            objectBBox.translate(rectangle2D.getX(), rectangle2D.getY());
        }
        return new AffineRable8Bit(graphicsNodeRable, objectBBox);
    }
    
    protected static Filter createRasterFeImage(final BridgeContext bridgeContext, final Rectangle2D rectangle2D, final ParsedURL parsedURL) {
        final Filter url = ImageTagRegistry.getRegistry().readURL(parsedURL);
        final Rectangle2D bounds2D = url.getBounds2D();
        final AffineTransform affineTransform = new AffineTransform();
        affineTransform.translate(rectangle2D.getX(), rectangle2D.getY());
        affineTransform.scale(rectangle2D.getWidth() / (bounds2D.getWidth() - 1.0), rectangle2D.getHeight() / (bounds2D.getHeight() - 1.0));
        affineTransform.translate(-bounds2D.getX(), -bounds2D.getY());
        return new AffineRable8Bit(url, affineTransform);
    }
}
