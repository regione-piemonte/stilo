// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.bridge;

import org.w3c.dom.Node;
import org.apache.batik.gvt.GraphicsNode;
import java.awt.geom.Point2D;
import org.apache.batik.ext.awt.image.renderable.ClipRable;
import java.awt.Shape;
import org.apache.batik.ext.awt.image.renderable.ClipRable8Bit;
import java.awt.geom.Rectangle2D;
import java.awt.geom.AffineTransform;
import org.apache.batik.gvt.CompositeGraphicsNode;
import org.apache.batik.gvt.Marker;
import org.w3c.dom.Element;

public class SVGMarkerElementBridge extends AnimatableGenericSVGBridge implements MarkerBridge, ErrorConstants
{
    protected SVGMarkerElementBridge() {
    }
    
    public String getLocalName() {
        return "marker";
    }
    
    public Marker createMarker(final BridgeContext bridgeContext, final Element element, final Element element2) {
        final GVTBuilder gvtBuilder = bridgeContext.getGVTBuilder();
        CompositeGraphicsNode compositeGraphicsNode = new CompositeGraphicsNode();
        boolean b = false;
        for (Node node = element.getFirstChild(); node != null; node = node.getNextSibling()) {
            if (node.getNodeType() == 1) {
                final GraphicsNode build = gvtBuilder.build(bridgeContext, (Element)node);
                if (build != null) {
                    b = true;
                    compositeGraphicsNode.getChildren().add(build);
                }
            }
        }
        if (!b) {
            return null;
        }
        final org.apache.batik.parser.UnitProcessor.Context context = UnitProcessor.createContext(bridgeContext, element2);
        float svgHorizontalLengthToUserSpace = 3.0f;
        final String attributeNS = element.getAttributeNS(null, "markerWidth");
        if (attributeNS.length() != 0) {
            svgHorizontalLengthToUserSpace = UnitProcessor.svgHorizontalLengthToUserSpace(attributeNS, "markerWidth", context);
        }
        if (svgHorizontalLengthToUserSpace == 0.0f) {
            return null;
        }
        float svgVerticalLengthToUserSpace = 3.0f;
        final String attributeNS2 = element.getAttributeNS(null, "markerHeight");
        if (attributeNS2.length() != 0) {
            svgVerticalLengthToUserSpace = UnitProcessor.svgVerticalLengthToUserSpace(attributeNS2, "markerHeight", context);
        }
        if (svgVerticalLengthToUserSpace == 0.0f) {
            return null;
        }
        final String attributeNS3 = element.getAttributeNS(null, "orient");
        double n;
        if (attributeNS3.length() == 0) {
            n = 0.0;
        }
        else if ("auto".equals(attributeNS3)) {
            n = Double.NaN;
        }
        else {
            try {
                n = SVGUtilities.convertSVGNumber(attributeNS3);
            }
            catch (NumberFormatException ex) {
                throw new BridgeException(bridgeContext, element, ex, "attribute.malformed", new Object[] { "orient", attributeNS3 });
            }
        }
        final float floatValue = CSSUtilities.getComputedStyle(element2, 52).getFloatValue();
        final String attributeNS4 = element.getAttributeNS(null, "markerUnits");
        int markerCoordinateSystem;
        if (attributeNS4.length() == 0) {
            markerCoordinateSystem = 3;
        }
        else {
            markerCoordinateSystem = SVGUtilities.parseMarkerCoordinateSystem(element, "markerUnits", attributeNS4, bridgeContext);
        }
        AffineTransform transform;
        if (markerCoordinateSystem == 3) {
            transform = new AffineTransform();
            transform.scale(floatValue, floatValue);
        }
        else {
            transform = new AffineTransform();
        }
        final AffineTransform preserveAspectRatioTransform = ViewBox.getPreserveAspectRatioTransform(element, svgHorizontalLengthToUserSpace, svgVerticalLengthToUserSpace, bridgeContext);
        if (preserveAspectRatioTransform == null) {
            return null;
        }
        transform.concatenate(preserveAspectRatioTransform);
        compositeGraphicsNode.setTransform(transform);
        if (CSSUtilities.convertOverflow(element)) {
            final float[] convertClip = CSSUtilities.convertClip(element);
            Rectangle2D.Float float1;
            if (convertClip == null) {
                float1 = new Rectangle2D.Float(0.0f, 0.0f, floatValue * svgHorizontalLengthToUserSpace, floatValue * svgVerticalLengthToUserSpace);
            }
            else {
                float1 = new Rectangle2D.Float(convertClip[3], convertClip[0], floatValue * svgHorizontalLengthToUserSpace - convertClip[1] - convertClip[3], floatValue * svgVerticalLengthToUserSpace - convertClip[2] - convertClip[0]);
            }
            final CompositeGraphicsNode compositeGraphicsNode2 = new CompositeGraphicsNode();
            compositeGraphicsNode2.getChildren().add(compositeGraphicsNode);
            compositeGraphicsNode2.setClip(new ClipRable8Bit(compositeGraphicsNode2.getGraphicsNodeRable(true), float1));
            compositeGraphicsNode = compositeGraphicsNode2;
        }
        float svgHorizontalCoordinateToUserSpace = 0.0f;
        final String attributeNS5 = element.getAttributeNS(null, "refX");
        if (attributeNS5.length() != 0) {
            svgHorizontalCoordinateToUserSpace = UnitProcessor.svgHorizontalCoordinateToUserSpace(attributeNS5, "refX", context);
        }
        float svgVerticalCoordinateToUserSpace = 0.0f;
        final String attributeNS6 = element.getAttributeNS(null, "refY");
        if (attributeNS6.length() != 0) {
            svgVerticalCoordinateToUserSpace = UnitProcessor.svgVerticalCoordinateToUserSpace(attributeNS6, "refY", context);
        }
        final float[] array = { svgHorizontalCoordinateToUserSpace, svgVerticalCoordinateToUserSpace };
        transform.transform(array, 0, array, 0, 1);
        return new Marker(compositeGraphicsNode, new Point2D.Float(array[0], array[1]), n);
    }
}
