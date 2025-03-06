// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.bridge;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;
import java.awt.Shape;
import org.apache.batik.gvt.GraphicsNode;
import java.util.List;
import java.awt.geom.Point2D;
import java.util.StringTokenizer;
import java.util.ArrayList;
import org.w3c.dom.Node;
import org.w3c.dom.Attr;
import org.apache.batik.gvt.CompositeGraphicsNode;
import org.apache.batik.parser.ParseException;
import org.apache.batik.parser.PathHandler;
import org.apache.batik.parser.PathParser;
import org.apache.batik.parser.AWTPathProducer;
import java.awt.geom.AffineTransform;
import org.apache.batik.gvt.font.Glyph;
import org.apache.batik.gvt.text.TextPaintInfo;
import org.apache.batik.gvt.font.GVTFontFace;
import org.w3c.dom.Element;

public class SVGGlyphElementBridge extends AbstractSVGBridge implements ErrorConstants
{
    protected SVGGlyphElementBridge() {
    }
    
    public String getLocalName() {
        return "glyph";
    }
    
    public Glyph createGlyph(final BridgeContext bridgeContext, final Element element, final Element element2, final int n, final float n2, final GVTFontFace gvtFontFace, final TextPaintInfo textPaintInfo) {
        final float n3 = n2 / gvtFontFace.getUnitsPerEm();
        final AffineTransform scaleInstance = AffineTransform.getScaleInstance(n3, -n3);
        final String attributeNS = element.getAttributeNS(null, "d");
        Shape transformedShape = null;
        if (attributeNS.length() != 0) {
            final AWTPathProducer pathHandler = new AWTPathProducer();
            pathHandler.setWindingRule(CSSUtilities.convertFillRule(element2));
            try {
                final PathParser pathParser = new PathParser();
                pathParser.setPathHandler(pathHandler);
                pathParser.parse(attributeNS);
            }
            catch (ParseException ex) {
                throw new BridgeException(bridgeContext, element, ex, "attribute.malformed", new Object[] { "d" });
            }
            finally {
                transformedShape = scaleInstance.createTransformedShape(pathHandler.getShape());
            }
        }
        final NodeList childNodes = element.getChildNodes();
        final int length = childNodes.getLength();
        int n4 = 0;
        for (int i = 0; i < length; ++i) {
            if (childNodes.item(i).getNodeType() == 1) {
                ++n4;
            }
        }
        CompositeGraphicsNode compositeGraphicsNode = null;
        if (n4 > 0) {
            final GVTBuilder gvtBuilder = bridgeContext.getGVTBuilder();
            compositeGraphicsNode = new CompositeGraphicsNode();
            final Element element3 = (Element)element.getParentNode().cloneNode(false);
            final NamedNodeMap attributes = element.getParentNode().getAttributes();
            for (int length2 = attributes.getLength(), j = 0; j < length2; ++j) {
                element3.setAttributeNode((Attr)attributes.item(j));
            }
            final Element element4 = (Element)element.cloneNode(true);
            element3.appendChild(element4);
            element2.appendChild(element3);
            final CompositeGraphicsNode compositeGraphicsNode2 = new CompositeGraphicsNode();
            compositeGraphicsNode2.setTransform(scaleInstance);
            final NodeList childNodes2 = element4.getChildNodes();
            for (int length3 = childNodes2.getLength(), k = 0; k < length3; ++k) {
                final Node item = childNodes2.item(k);
                if (item.getNodeType() == 1) {
                    compositeGraphicsNode2.add(gvtBuilder.build(bridgeContext, (Element)item));
                }
            }
            compositeGraphicsNode.add(compositeGraphicsNode2);
            element2.removeChild(element3);
        }
        final String attributeNS2 = element.getAttributeNS(null, "unicode");
        final String attributeNS3 = element.getAttributeNS(null, "glyph-name");
        final ArrayList<String> list = new ArrayList<String>();
        final StringTokenizer stringTokenizer = new StringTokenizer(attributeNS3, " ,");
        while (stringTokenizer.hasMoreTokens()) {
            list.add(stringTokenizer.nextToken());
        }
        final String attributeNS4 = element.getAttributeNS(null, "orientation");
        final String attributeNS5 = element.getAttributeNS(null, "arabic-form");
        final String attributeNS6 = element.getAttributeNS(null, "lang");
        final Element element5 = (Element)element.getParentNode();
        String s = element.getAttributeNS(null, "horiz-adv-x");
        if (s.length() == 0) {
            s = element5.getAttributeNS(null, "horiz-adv-x");
            if (s.length() == 0) {
                throw new BridgeException(bridgeContext, element5, "attribute.missing", new Object[] { "horiz-adv-x" });
            }
        }
        float n5;
        try {
            n5 = SVGUtilities.convertSVGNumber(s) * n3;
        }
        catch (NumberFormatException ex2) {
            throw new BridgeException(bridgeContext, element, ex2, "attribute.malformed", new Object[] { "horiz-adv-x", s });
        }
        String s2 = element.getAttributeNS(null, "vert-adv-y");
        if (s2.length() == 0) {
            s2 = element5.getAttributeNS(null, "vert-adv-y");
            if (s2.length() == 0) {
                s2 = String.valueOf(gvtFontFace.getUnitsPerEm());
            }
        }
        float n6;
        try {
            n6 = SVGUtilities.convertSVGNumber(s2) * n3;
        }
        catch (NumberFormatException ex3) {
            throw new BridgeException(bridgeContext, element, ex3, "attribute.malformed", new Object[] { "vert-adv-y", s2 });
        }
        String s3 = element.getAttributeNS(null, "vert-origin-x");
        if (s3.length() == 0) {
            s3 = element5.getAttributeNS(null, "vert-origin-x");
            if (s3.length() == 0) {
                s3 = Float.toString(n5 / 2.0f);
            }
        }
        float x;
        try {
            x = SVGUtilities.convertSVGNumber(s3) * n3;
        }
        catch (NumberFormatException ex4) {
            throw new BridgeException(bridgeContext, element, ex4, "attribute.malformed", new Object[] { "vert-origin-x", s3 });
        }
        String s4 = element.getAttributeNS(null, "vert-origin-y");
        if (s4.length() == 0) {
            s4 = element5.getAttributeNS(null, "vert-origin-y");
            if (s4.length() == 0) {
                s4 = String.valueOf(gvtFontFace.getAscent());
            }
        }
        float y;
        try {
            y = SVGUtilities.convertSVGNumber(s4) * -n3;
        }
        catch (NumberFormatException ex5) {
            throw new BridgeException(bridgeContext, element, ex5, "attribute.malformed", new Object[] { "vert-origin-y", s4 });
        }
        final Point2D.Float float1 = new Point2D.Float(x, y);
        String attributeNS7 = element5.getAttributeNS(null, "horiz-origin-x");
        if (attributeNS7.length() == 0) {
            attributeNS7 = "0";
        }
        float x2;
        try {
            x2 = SVGUtilities.convertSVGNumber(attributeNS7) * n3;
        }
        catch (NumberFormatException ex6) {
            throw new BridgeException(bridgeContext, element5, ex6, "attribute.malformed", new Object[] { "horiz-origin-x", attributeNS7 });
        }
        String attributeNS8 = element5.getAttributeNS(null, "horiz-origin-y");
        if (attributeNS8.length() == 0) {
            attributeNS8 = "0";
        }
        float y2;
        try {
            y2 = SVGUtilities.convertSVGNumber(attributeNS8) * -n3;
        }
        catch (NumberFormatException ex7) {
            throw new BridgeException(bridgeContext, element, ex7, "attribute.malformed", new Object[] { "horiz-origin-y", attributeNS8 });
        }
        return new Glyph(attributeNS2, list, attributeNS4, attributeNS5, attributeNS6, new Point2D.Float(x2, y2), float1, n5, n6, n, textPaintInfo, transformedShape, compositeGraphicsNode);
    }
}
