// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.bridge;

import org.apache.batik.ext.awt.image.renderable.Filter;
import org.w3c.dom.Node;
import java.awt.geom.Rectangle2D;
import org.apache.batik.gvt.filter.MaskRable8Bit;
import java.awt.geom.AffineTransform;
import org.apache.batik.gvt.CompositeGraphicsNode;
import org.apache.batik.gvt.filter.Mask;
import org.apache.batik.gvt.GraphicsNode;
import org.w3c.dom.Element;

public class SVGMaskElementBridge extends AnimatableGenericSVGBridge implements MaskBridge
{
    public String getLocalName() {
        return "mask";
    }
    
    public Mask createMask(final BridgeContext bridgeContext, final Element element, final Element element2, final GraphicsNode graphicsNode) {
        final Rectangle2D convertMaskRegion = SVGUtilities.convertMaskRegion(element, element2, graphicsNode, bridgeContext);
        final GVTBuilder gvtBuilder = bridgeContext.getGVTBuilder();
        final CompositeGraphicsNode compositeGraphicsNode = new CompositeGraphicsNode();
        final CompositeGraphicsNode compositeGraphicsNode2 = new CompositeGraphicsNode();
        compositeGraphicsNode.getChildren().add(compositeGraphicsNode2);
        boolean b = false;
        for (Node node = element.getFirstChild(); node != null; node = node.getNextSibling()) {
            if (node.getNodeType() == 1) {
                final GraphicsNode build = gvtBuilder.build(bridgeContext, (Element)node);
                if (build != null) {
                    b = true;
                    compositeGraphicsNode2.getChildren().add(build);
                }
            }
        }
        if (!b) {
            return null;
        }
        final String attributeNS = element.getAttributeNS(null, "transform");
        AffineTransform transform;
        if (attributeNS.length() != 0) {
            transform = SVGUtilities.convertTransform(element, "transform", attributeNS, bridgeContext);
        }
        else {
            transform = new AffineTransform();
        }
        final String attributeNS2 = element.getAttributeNS(null, "maskContentUnits");
        int coordinateSystem;
        if (attributeNS2.length() == 0) {
            coordinateSystem = 1;
        }
        else {
            coordinateSystem = SVGUtilities.parseCoordinateSystem(element, "maskContentUnits", attributeNS2, bridgeContext);
        }
        if (coordinateSystem == 2) {
            transform = SVGUtilities.toObjectBBox(transform, graphicsNode);
        }
        compositeGraphicsNode2.setTransform(transform);
        Filter filter = graphicsNode.getFilter();
        if (filter == null) {
            filter = graphicsNode.getGraphicsNodeRable(true);
        }
        return new MaskRable8Bit(filter, compositeGraphicsNode, convertMaskRegion);
    }
}
