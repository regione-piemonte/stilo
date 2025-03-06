// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.bridge;

import org.apache.batik.ext.awt.image.renderable.Filter;
import org.w3c.dom.Node;
import org.apache.batik.ext.awt.image.renderable.ClipRable8Bit;
import java.awt.RenderingHints;
import org.apache.batik.gvt.ShapeNode;
import java.awt.Shape;
import java.awt.geom.GeneralPath;
import org.apache.batik.dom.svg.SVGOMUseElement;
import java.awt.geom.Area;
import java.awt.geom.AffineTransform;
import org.apache.batik.ext.awt.image.renderable.ClipRable;
import org.apache.batik.gvt.GraphicsNode;
import org.w3c.dom.Element;

public class SVGClipPathElementBridge extends AnimatableGenericSVGBridge implements ClipBridge
{
    public String getLocalName() {
        return "clipPath";
    }
    
    public ClipRable createClip(final BridgeContext bridgeContext, final Element element, final Element element2, final GraphicsNode graphicsNode) {
        final String attributeNS = element.getAttributeNS(null, "transform");
        AffineTransform tx;
        if (attributeNS.length() != 0) {
            tx = SVGUtilities.convertTransform(element, "transform", attributeNS, bridgeContext);
        }
        else {
            tx = new AffineTransform();
        }
        final String attributeNS2 = element.getAttributeNS(null, "clipPathUnits");
        int coordinateSystem;
        if (attributeNS2.length() == 0) {
            coordinateSystem = 1;
        }
        else {
            coordinateSystem = SVGUtilities.parseCoordinateSystem(element, "clipPathUnits", attributeNS2, bridgeContext);
        }
        if (coordinateSystem == 2) {
            tx = SVGUtilities.toObjectBBox(tx, graphicsNode);
        }
        final Area shape = new Area();
        final GVTBuilder gvtBuilder = bridgeContext.getGVTBuilder();
        boolean b = false;
        for (Node node = element.getFirstChild(); node != null; node = node.getNextSibling()) {
            if (node.getNodeType() == 1) {
                Element element3 = (Element)node;
                final GraphicsNode build = gvtBuilder.build(bridgeContext, element3);
                if (build != null) {
                    b = true;
                    if (element3 instanceof SVGOMUseElement) {
                        final Node cssFirstChild = ((SVGOMUseElement)element3).getCSSFirstChild();
                        if (cssFirstChild != null && cssFirstChild.getNodeType() == 1) {
                            element3 = (Element)cssFirstChild;
                        }
                    }
                    final int convertClipRule = CSSUtilities.convertClipRule(element3);
                    final GeneralPath pSrc = new GeneralPath(build.getOutline());
                    pSrc.setWindingRule(convertClipRule);
                    AffineTransform transform = build.getTransform();
                    if (transform == null) {
                        transform = tx;
                    }
                    else {
                        transform.preConcatenate(tx);
                    }
                    Shape transformedShape = transform.createTransformedShape(pSrc);
                    final ShapeNode shapeNode = new ShapeNode();
                    shapeNode.setShape(transformedShape);
                    final ClipRable convertClipPath = CSSUtilities.convertClipPath(element3, shapeNode, bridgeContext);
                    if (convertClipPath != null) {
                        final Area area = new Area(transformedShape);
                        area.subtract(new Area(convertClipPath.getClipPath()));
                        transformedShape = area;
                    }
                    shape.add(new Area(transformedShape));
                }
            }
        }
        if (!b) {
            return null;
        }
        final ShapeNode shapeNode2 = new ShapeNode();
        shapeNode2.setShape(shape);
        final ClipRable convertClipPath2 = CSSUtilities.convertClipPath(element, shapeNode2, bridgeContext);
        if (convertClipPath2 != null) {
            shape.subtract(new Area(convertClipPath2.getClipPath()));
        }
        Filter filter = graphicsNode.getFilter();
        if (filter == null) {
            filter = graphicsNode.getGraphicsNodeRable(true);
        }
        boolean b2 = false;
        final RenderingHints convertShapeRendering = CSSUtilities.convertShapeRendering(element, null);
        if (convertShapeRendering != null) {
            b2 = (convertShapeRendering.get(RenderingHints.KEY_ANTIALIASING) == RenderingHints.VALUE_ANTIALIAS_ON);
        }
        return new ClipRable8Bit(filter, shape, b2);
    }
}
