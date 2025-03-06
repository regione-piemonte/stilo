// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.bridge;

import org.apache.batik.parser.ClockHandler;
import org.apache.batik.parser.ClockParser;
import org.apache.batik.parser.ParseException;
import org.apache.batik.parser.AWTTransformProducer;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import org.apache.batik.gvt.GraphicsNode;
import java.awt.geom.Point2D;
import org.apache.batik.parser.UnitProcessor;
import java.util.Iterator;
import java.io.IOException;
import org.w3c.dom.svg.SVGDocument;
import org.apache.batik.util.ParsedURL;
import org.apache.batik.dom.AbstractNode;
import org.apache.batik.dom.util.XLinkSupport;
import java.util.LinkedList;
import java.util.StringTokenizer;
import org.apache.batik.dom.util.XMLSupport;
import org.w3c.dom.svg.SVGLangSpace;
import org.w3c.dom.svg.SVGElement;
import org.w3c.dom.svg.SVGNumberList;
import org.w3c.dom.Node;
import org.apache.batik.css.engine.CSSEngine;
import org.w3c.dom.Element;
import org.apache.batik.util.SVGConstants;

public abstract class SVGUtilities implements SVGConstants, ErrorConstants
{
    public static final short USER_SPACE_ON_USE = 1;
    public static final short OBJECT_BOUNDING_BOX = 2;
    public static final short STROKE_WIDTH = 3;
    
    protected SVGUtilities() {
    }
    
    public static Element getParentElement(final Element element) {
        Node node;
        for (node = CSSEngine.getCSSParentNode(element); node != null && node.getNodeType() != 1; node = CSSEngine.getCSSParentNode(node)) {}
        return (Element)node;
    }
    
    public static float[] convertSVGNumberList(final SVGNumberList list) {
        final int numberOfItems = list.getNumberOfItems();
        if (numberOfItems == 0) {
            return null;
        }
        final float[] array = new float[numberOfItems];
        for (int i = 0; i < numberOfItems; ++i) {
            array[i] = list.getItem(i).getValue();
        }
        return array;
    }
    
    public static float convertSVGNumber(final String s) {
        return Float.parseFloat(s);
    }
    
    public static int convertSVGInteger(final String s) {
        return Integer.parseInt(s);
    }
    
    public static float convertRatio(String substring) {
        float n = 1.0f;
        if (substring.endsWith("%")) {
            substring = substring.substring(0, substring.length() - 1);
            n = 100.0f;
        }
        float n2 = Float.parseFloat(substring) / n;
        if (n2 < 0.0f) {
            n2 = 0.0f;
        }
        else if (n2 > 1.0f) {
            n2 = 1.0f;
        }
        return n2;
    }
    
    public static String getDescription(final SVGElement svgElement) {
        String string = "";
        boolean equals = false;
        final Node firstChild = svgElement.getFirstChild();
        if (firstChild != null && firstChild.getNodeType() == 1 && ((firstChild.getPrefix() == null) ? firstChild.getNodeName() : firstChild.getLocalName()).equals("desc")) {
            equals = ((SVGLangSpace)firstChild).getXMLspace().equals("preserve");
            for (Node node = firstChild.getFirstChild(); node != null; node = node.getNextSibling()) {
                if (node.getNodeType() == 3) {
                    string += node.getNodeValue();
                }
            }
        }
        return equals ? XMLSupport.preserveXMLSpace(string) : XMLSupport.defaultXMLSpace(string);
    }
    
    public static boolean matchUserAgent(final Element element, final UserAgent userAgent) {
        Label_0074: {
            if (element.hasAttributeNS(null, "systemLanguage")) {
                final String attributeNS = element.getAttributeNS(null, "systemLanguage");
                if (attributeNS.length() == 0) {
                    return false;
                }
                final StringTokenizer stringTokenizer = new StringTokenizer(attributeNS, ", ");
                while (stringTokenizer.hasMoreTokens()) {
                    if (matchUserLanguage(stringTokenizer.nextToken(), userAgent.getLanguages())) {
                        break Label_0074;
                    }
                }
                return false;
            }
        }
        if (element.hasAttributeNS(null, "requiredFeatures")) {
            final String attributeNS2 = element.getAttributeNS(null, "requiredFeatures");
            if (attributeNS2.length() == 0) {
                return false;
            }
            final StringTokenizer stringTokenizer2 = new StringTokenizer(attributeNS2, " ");
            while (stringTokenizer2.hasMoreTokens()) {
                if (!userAgent.hasFeature(stringTokenizer2.nextToken())) {
                    return false;
                }
            }
        }
        if (element.hasAttributeNS(null, "requiredExtensions")) {
            final String attributeNS3 = element.getAttributeNS(null, "requiredExtensions");
            if (attributeNS3.length() == 0) {
                return false;
            }
            final StringTokenizer stringTokenizer3 = new StringTokenizer(attributeNS3, " ");
            while (stringTokenizer3.hasMoreTokens()) {
                if (!userAgent.supportExtension(stringTokenizer3.nextToken())) {
                    return false;
                }
            }
        }
        return true;
    }
    
    protected static boolean matchUserLanguage(final String s, final String str) {
        final StringTokenizer stringTokenizer = new StringTokenizer(str, ", ");
        while (stringTokenizer.hasMoreTokens()) {
            final String nextToken = stringTokenizer.nextToken();
            if (s.startsWith(nextToken)) {
                return s.length() <= nextToken.length() || s.charAt(nextToken.length()) == '-';
            }
        }
        return false;
    }
    
    public static String getChainableAttributeNS(final Element element, final String s, final String s2, final BridgeContext bridgeContext) {
        final DocumentLoader documentLoader = bridgeContext.getDocumentLoader();
        Element element2 = element;
        final LinkedList<ParsedURL> list = (LinkedList<ParsedURL>)new LinkedList<Object>();
        while (true) {
            final String attributeNS = element2.getAttributeNS(s, s2);
            if (attributeNS.length() > 0) {
                return attributeNS;
            }
            final String xLinkHref = XLinkSupport.getXLinkHref(element2);
            if (xLinkHref.length() == 0) {
                return "";
            }
            final ParsedURL parsedURL = new ParsedURL(((AbstractNode)element2).getBaseURI(), xLinkHref);
            final Iterator<Object> iterator = list.iterator();
            while (iterator.hasNext()) {
                if (parsedURL.equals(iterator.next())) {
                    throw new BridgeException(bridgeContext, element2, "xlink.href.circularDependencies", new Object[] { xLinkHref });
                }
            }
            try {
                element2 = bridgeContext.createURIResolver((SVGDocument)element2.getOwnerDocument(), documentLoader).getElement(parsedURL.toString(), element2);
                list.add(parsedURL);
            }
            catch (IOException ex) {
                throw new BridgeException(bridgeContext, element2, ex, "uri.io", new Object[] { xLinkHref });
            }
            catch (SecurityException ex2) {
                throw new BridgeException(bridgeContext, element2, ex2, "uri.unsecure", new Object[] { xLinkHref });
            }
        }
    }
    
    public static Point2D convertPoint(final String s, final String s2, final String s3, final String s4, final short n, final UnitProcessor.Context context) {
        float x = 0.0f;
        float y = 0.0f;
        switch (n) {
            case 2: {
                x = org.apache.batik.bridge.UnitProcessor.svgHorizontalCoordinateToObjectBoundingBox(s, s2, context);
                y = org.apache.batik.bridge.UnitProcessor.svgVerticalCoordinateToObjectBoundingBox(s3, s4, context);
                break;
            }
            case 1: {
                x = org.apache.batik.bridge.UnitProcessor.svgHorizontalCoordinateToUserSpace(s, s2, context);
                y = org.apache.batik.bridge.UnitProcessor.svgVerticalCoordinateToUserSpace(s3, s4, context);
                break;
            }
            default: {
                throw new IllegalArgumentException("Invalid unit type");
            }
        }
        return new Point2D.Float(x, y);
    }
    
    public static float convertLength(final String s, final String s2, final short n, final UnitProcessor.Context context) {
        switch (n) {
            case 2: {
                return org.apache.batik.bridge.UnitProcessor.svgOtherLengthToObjectBoundingBox(s, s2, context);
            }
            case 1: {
                return org.apache.batik.bridge.UnitProcessor.svgOtherLengthToUserSpace(s, s2, context);
            }
            default: {
                throw new IllegalArgumentException("Invalid unit type");
            }
        }
    }
    
    public static Rectangle2D convertMaskRegion(final Element element, final Element element2, final GraphicsNode graphicsNode, final BridgeContext bridgeContext) {
        String attributeNS = element.getAttributeNS(null, "x");
        if (attributeNS.length() == 0) {
            attributeNS = "-10%";
        }
        String attributeNS2 = element.getAttributeNS(null, "y");
        if (attributeNS2.length() == 0) {
            attributeNS2 = "-10%";
        }
        String attributeNS3 = element.getAttributeNS(null, "width");
        if (attributeNS3.length() == 0) {
            attributeNS3 = "120%";
        }
        String attributeNS4 = element.getAttributeNS(null, "height");
        if (attributeNS4.length() == 0) {
            attributeNS4 = "120%";
        }
        final String attributeNS5 = element.getAttributeNS(null, "maskUnits");
        short coordinateSystem;
        if (attributeNS5.length() == 0) {
            coordinateSystem = 2;
        }
        else {
            coordinateSystem = parseCoordinateSystem(element, "maskUnits", attributeNS5, bridgeContext);
        }
        return convertRegion(attributeNS, attributeNS2, attributeNS3, attributeNS4, coordinateSystem, graphicsNode, org.apache.batik.bridge.UnitProcessor.createContext(bridgeContext, element2));
    }
    
    public static Rectangle2D convertPatternRegion(final Element element, final Element element2, final GraphicsNode graphicsNode, final BridgeContext bridgeContext) {
        String chainableAttributeNS = getChainableAttributeNS(element, null, "x", bridgeContext);
        if (chainableAttributeNS.length() == 0) {
            chainableAttributeNS = "0";
        }
        String chainableAttributeNS2 = getChainableAttributeNS(element, null, "y", bridgeContext);
        if (chainableAttributeNS2.length() == 0) {
            chainableAttributeNS2 = "0";
        }
        final String chainableAttributeNS3 = getChainableAttributeNS(element, null, "width", bridgeContext);
        if (chainableAttributeNS3.length() == 0) {
            throw new BridgeException(bridgeContext, element, "attribute.missing", new Object[] { "width" });
        }
        final String chainableAttributeNS4 = getChainableAttributeNS(element, null, "height", bridgeContext);
        if (chainableAttributeNS4.length() == 0) {
            throw new BridgeException(bridgeContext, element, "attribute.missing", new Object[] { "height" });
        }
        final String chainableAttributeNS5 = getChainableAttributeNS(element, null, "patternUnits", bridgeContext);
        short coordinateSystem;
        if (chainableAttributeNS5.length() == 0) {
            coordinateSystem = 2;
        }
        else {
            coordinateSystem = parseCoordinateSystem(element, "patternUnits", chainableAttributeNS5, bridgeContext);
        }
        return convertRegion(chainableAttributeNS, chainableAttributeNS2, chainableAttributeNS3, chainableAttributeNS4, coordinateSystem, graphicsNode, org.apache.batik.bridge.UnitProcessor.createContext(bridgeContext, element2));
    }
    
    public static float[] convertFilterRes(final Element element, final BridgeContext bridgeContext) {
        final float[] array = new float[2];
        final String chainableAttributeNS = getChainableAttributeNS(element, null, "filterRes", bridgeContext);
        final Float[] convertSVGNumberOptionalNumber = convertSVGNumberOptionalNumber(element, "filterRes", chainableAttributeNS, bridgeContext);
        if (array[0] < 0.0f || array[1] < 0.0f) {
            throw new BridgeException(bridgeContext, element, "attribute.malformed", new Object[] { "filterRes", chainableAttributeNS });
        }
        if (convertSVGNumberOptionalNumber[0] == null) {
            array[0] = -1.0f;
        }
        else {
            array[0] = convertSVGNumberOptionalNumber[0];
            if (array[0] < 0.0f) {
                throw new BridgeException(bridgeContext, element, "attribute.malformed", new Object[] { "filterRes", chainableAttributeNS });
            }
        }
        if (convertSVGNumberOptionalNumber[1] == null) {
            array[1] = array[0];
        }
        else {
            array[1] = convertSVGNumberOptionalNumber[1];
            if (array[1] < 0.0f) {
                throw new BridgeException(bridgeContext, element, "attribute.malformed", new Object[] { "filterRes", chainableAttributeNS });
            }
        }
        return array;
    }
    
    public static Float[] convertSVGNumberOptionalNumber(final Element element, final String s, final String str, final BridgeContext bridgeContext) {
        final Float[] array = new Float[2];
        if (str.length() == 0) {
            return array;
        }
        try {
            final StringTokenizer stringTokenizer = new StringTokenizer(str, " ");
            array[0] = new Float(Float.parseFloat(stringTokenizer.nextToken()));
            if (stringTokenizer.hasMoreTokens()) {
                array[1] = new Float(Float.parseFloat(stringTokenizer.nextToken()));
            }
            if (stringTokenizer.hasMoreTokens()) {
                throw new BridgeException(bridgeContext, element, "attribute.malformed", new Object[] { s, str });
            }
        }
        catch (NumberFormatException ex) {
            throw new BridgeException(bridgeContext, element, ex, "attribute.malformed", new Object[] { s, str, ex });
        }
        return array;
    }
    
    public static Rectangle2D convertFilterChainRegion(final Element element, final Element element2, final GraphicsNode graphicsNode, final BridgeContext bridgeContext) {
        String chainableAttributeNS = getChainableAttributeNS(element, null, "x", bridgeContext);
        if (chainableAttributeNS.length() == 0) {
            chainableAttributeNS = "-10%";
        }
        String chainableAttributeNS2 = getChainableAttributeNS(element, null, "y", bridgeContext);
        if (chainableAttributeNS2.length() == 0) {
            chainableAttributeNS2 = "-10%";
        }
        String chainableAttributeNS3 = getChainableAttributeNS(element, null, "width", bridgeContext);
        if (chainableAttributeNS3.length() == 0) {
            chainableAttributeNS3 = "120%";
        }
        String chainableAttributeNS4 = getChainableAttributeNS(element, null, "height", bridgeContext);
        if (chainableAttributeNS4.length() == 0) {
            chainableAttributeNS4 = "120%";
        }
        final String chainableAttributeNS5 = getChainableAttributeNS(element, null, "filterUnits", bridgeContext);
        short coordinateSystem;
        if (chainableAttributeNS5.length() == 0) {
            coordinateSystem = 2;
        }
        else {
            coordinateSystem = parseCoordinateSystem(element, "filterUnits", chainableAttributeNS5, bridgeContext);
        }
        final UnitProcessor.Context context = org.apache.batik.bridge.UnitProcessor.createContext(bridgeContext, element2);
        final Rectangle2D convertRegion = convertRegion(chainableAttributeNS, chainableAttributeNS2, chainableAttributeNS3, chainableAttributeNS4, coordinateSystem, graphicsNode, context);
        final String chainableAttributeNS6 = getChainableAttributeNS(element, null, "filterMarginsUnits", bridgeContext);
        short coordinateSystem2;
        if (chainableAttributeNS6.length() == 0) {
            coordinateSystem2 = 1;
        }
        else {
            coordinateSystem2 = parseCoordinateSystem(element, "filterMarginsUnits", chainableAttributeNS6, bridgeContext);
        }
        String attributeNS = element.getAttributeNS(null, "mx");
        if (attributeNS.length() == 0) {
            attributeNS = "0";
        }
        String attributeNS2 = element.getAttributeNS(null, "my");
        if (attributeNS2.length() == 0) {
            attributeNS2 = "0";
        }
        String attributeNS3 = element.getAttributeNS(null, "mw");
        if (attributeNS3.length() == 0) {
            attributeNS3 = "0";
        }
        String attributeNS4 = element.getAttributeNS(null, "mh");
        if (attributeNS4.length() == 0) {
            attributeNS4 = "0";
        }
        return extendRegion(attributeNS, attributeNS2, attributeNS3, attributeNS4, coordinateSystem2, graphicsNode, convertRegion, context);
    }
    
    protected static Rectangle2D extendRegion(final String s, final String s2, final String s3, final String s4, final short n, final GraphicsNode graphicsNode, final Rectangle2D rectangle2D, final UnitProcessor.Context context) {
        float svgHorizontalCoordinateToUserSpace = 0.0f;
        float svgVerticalCoordinateToUserSpace = 0.0f;
        float svgHorizontalCoordinateToUserSpace2 = 0.0f;
        float svgVerticalCoordinateToUserSpace2 = 0.0f;
        switch (n) {
            case 1: {
                svgHorizontalCoordinateToUserSpace = org.apache.batik.bridge.UnitProcessor.svgHorizontalCoordinateToUserSpace(s, "mx", context);
                svgVerticalCoordinateToUserSpace = org.apache.batik.bridge.UnitProcessor.svgVerticalCoordinateToUserSpace(s2, "my", context);
                svgHorizontalCoordinateToUserSpace2 = org.apache.batik.bridge.UnitProcessor.svgHorizontalCoordinateToUserSpace(s3, "mw", context);
                svgVerticalCoordinateToUserSpace2 = org.apache.batik.bridge.UnitProcessor.svgVerticalCoordinateToUserSpace(s4, "mh", context);
                break;
            }
            case 2: {
                final Rectangle2D geometryBounds = graphicsNode.getGeometryBounds();
                if (geometryBounds == null) {
                    svgVerticalCoordinateToUserSpace = (svgHorizontalCoordinateToUserSpace = (svgHorizontalCoordinateToUserSpace2 = (svgVerticalCoordinateToUserSpace2 = 0.0f)));
                    break;
                }
                svgHorizontalCoordinateToUserSpace = (float)(org.apache.batik.bridge.UnitProcessor.svgHorizontalCoordinateToObjectBoundingBox(s, "mx", context) * geometryBounds.getWidth());
                svgVerticalCoordinateToUserSpace = (float)(org.apache.batik.bridge.UnitProcessor.svgVerticalCoordinateToObjectBoundingBox(s2, "my", context) * geometryBounds.getHeight());
                svgHorizontalCoordinateToUserSpace2 = (float)(org.apache.batik.bridge.UnitProcessor.svgHorizontalCoordinateToObjectBoundingBox(s3, "mw", context) * geometryBounds.getWidth());
                svgVerticalCoordinateToUserSpace2 = (float)(org.apache.batik.bridge.UnitProcessor.svgVerticalCoordinateToObjectBoundingBox(s4, "mh", context) * geometryBounds.getHeight());
                break;
            }
            default: {
                throw new IllegalArgumentException("Invalid unit type");
            }
        }
        rectangle2D.setRect(rectangle2D.getX() + svgHorizontalCoordinateToUserSpace, rectangle2D.getY() + svgVerticalCoordinateToUserSpace, rectangle2D.getWidth() + svgHorizontalCoordinateToUserSpace2, rectangle2D.getHeight() + svgVerticalCoordinateToUserSpace2);
        return rectangle2D;
    }
    
    public static Rectangle2D getBaseFilterPrimitiveRegion(final Element element, final Element element2, final GraphicsNode graphicsNode, final Rectangle2D rectangle2D, final BridgeContext bridgeContext) {
        final UnitProcessor.Context context = org.apache.batik.bridge.UnitProcessor.createContext(bridgeContext, element2);
        double x = rectangle2D.getX();
        final String attributeNS = element.getAttributeNS(null, "x");
        if (attributeNS.length() != 0) {
            x = org.apache.batik.bridge.UnitProcessor.svgHorizontalCoordinateToUserSpace(attributeNS, "x", context);
        }
        double y = rectangle2D.getY();
        final String attributeNS2 = element.getAttributeNS(null, "y");
        if (attributeNS2.length() != 0) {
            y = org.apache.batik.bridge.UnitProcessor.svgVerticalCoordinateToUserSpace(attributeNS2, "y", context);
        }
        double width = rectangle2D.getWidth();
        final String attributeNS3 = element.getAttributeNS(null, "width");
        if (attributeNS3.length() != 0) {
            width = org.apache.batik.bridge.UnitProcessor.svgHorizontalLengthToUserSpace(attributeNS3, "width", context);
        }
        double height = rectangle2D.getHeight();
        final String attributeNS4 = element.getAttributeNS(null, "height");
        if (attributeNS4.length() != 0) {
            height = org.apache.batik.bridge.UnitProcessor.svgVerticalLengthToUserSpace(attributeNS4, "height", context);
        }
        return new Rectangle2D.Double(x, y, width, height);
    }
    
    public static Rectangle2D convertFilterPrimitiveRegion(final Element element, final Element element2, final Element element3, final GraphicsNode graphicsNode, final Rectangle2D rectangle2D, final Rectangle2D src2, final BridgeContext bridgeContext) {
        String chainableAttributeNS = "";
        if (element2 != null) {
            chainableAttributeNS = getChainableAttributeNS(element2, null, "primitiveUnits", bridgeContext);
        }
        int coordinateSystem;
        if (chainableAttributeNS.length() == 0) {
            coordinateSystem = 1;
        }
        else {
            coordinateSystem = parseCoordinateSystem(element2, "filterUnits", chainableAttributeNS, bridgeContext);
        }
        String attributeNS = "";
        String attributeNS2 = "";
        String attributeNS3 = "";
        String attributeNS4 = "";
        if (element != null) {
            attributeNS = element.getAttributeNS(null, "x");
            attributeNS2 = element.getAttributeNS(null, "y");
            attributeNS3 = element.getAttributeNS(null, "width");
            attributeNS4 = element.getAttributeNS(null, "height");
        }
        double x = rectangle2D.getX();
        double y = rectangle2D.getY();
        double width = rectangle2D.getWidth();
        double height = rectangle2D.getHeight();
        final UnitProcessor.Context context = org.apache.batik.bridge.UnitProcessor.createContext(bridgeContext, element3);
        switch (coordinateSystem) {
            case 2: {
                final Rectangle2D geometryBounds = graphicsNode.getGeometryBounds();
                if (geometryBounds == null) {
                    break;
                }
                if (attributeNS.length() != 0) {
                    x = geometryBounds.getX() + org.apache.batik.bridge.UnitProcessor.svgHorizontalCoordinateToObjectBoundingBox(attributeNS, "x", context) * geometryBounds.getWidth();
                }
                if (attributeNS2.length() != 0) {
                    y = geometryBounds.getY() + org.apache.batik.bridge.UnitProcessor.svgVerticalCoordinateToObjectBoundingBox(attributeNS2, "y", context) * geometryBounds.getHeight();
                }
                if (attributeNS3.length() != 0) {
                    width = org.apache.batik.bridge.UnitProcessor.svgHorizontalLengthToObjectBoundingBox(attributeNS3, "width", context) * geometryBounds.getWidth();
                }
                if (attributeNS4.length() != 0) {
                    height = org.apache.batik.bridge.UnitProcessor.svgVerticalLengthToObjectBoundingBox(attributeNS4, "height", context) * geometryBounds.getHeight();
                    break;
                }
                break;
            }
            case 1: {
                if (attributeNS.length() != 0) {
                    x = org.apache.batik.bridge.UnitProcessor.svgHorizontalCoordinateToUserSpace(attributeNS, "x", context);
                }
                if (attributeNS2.length() != 0) {
                    y = org.apache.batik.bridge.UnitProcessor.svgVerticalCoordinateToUserSpace(attributeNS2, "y", context);
                }
                if (attributeNS3.length() != 0) {
                    width = org.apache.batik.bridge.UnitProcessor.svgHorizontalLengthToUserSpace(attributeNS3, "width", context);
                }
                if (attributeNS4.length() != 0) {
                    height = org.apache.batik.bridge.UnitProcessor.svgVerticalLengthToUserSpace(attributeNS4, "height", context);
                    break;
                }
                break;
            }
            default: {
                throw new Error("invalid unitsType:" + coordinateSystem);
            }
        }
        final Rectangle2D.Double double1 = new Rectangle2D.Double(x, y, width, height);
        String chainableAttributeNS2 = "";
        if (element2 != null) {
            chainableAttributeNS2 = getChainableAttributeNS(element2, null, "filterPrimitiveMarginsUnits", bridgeContext);
        }
        short coordinateSystem2;
        if (chainableAttributeNS2.length() == 0) {
            coordinateSystem2 = 1;
        }
        else {
            coordinateSystem2 = parseCoordinateSystem(element2, "filterPrimitiveMarginsUnits", chainableAttributeNS2, bridgeContext);
        }
        String attributeNS5 = "";
        String attributeNS6 = "";
        String attributeNS7 = "";
        String attributeNS8 = "";
        if (element != null) {
            attributeNS5 = element.getAttributeNS(null, "mx");
            attributeNS6 = element.getAttributeNS(null, "my");
            attributeNS7 = element.getAttributeNS(null, "mw");
            attributeNS8 = element.getAttributeNS(null, "mh");
        }
        if (attributeNS5.length() == 0) {
            attributeNS5 = "0";
        }
        if (attributeNS6.length() == 0) {
            attributeNS6 = "0";
        }
        if (attributeNS7.length() == 0) {
            attributeNS7 = "0";
        }
        if (attributeNS8.length() == 0) {
            attributeNS8 = "0";
        }
        final Rectangle2D extendRegion = extendRegion(attributeNS5, attributeNS6, attributeNS7, attributeNS8, coordinateSystem2, graphicsNode, double1, context);
        Rectangle2D.intersect(extendRegion, src2, extendRegion);
        return extendRegion;
    }
    
    public static Rectangle2D convertFilterPrimitiveRegion(final Element element, final Element element2, final GraphicsNode graphicsNode, final Rectangle2D rectangle2D, final Rectangle2D rectangle2D2, final BridgeContext bridgeContext) {
        final Node parentNode = element.getParentNode();
        Element element3 = null;
        if (parentNode != null && parentNode.getNodeType() == 1) {
            element3 = (Element)parentNode;
        }
        return convertFilterPrimitiveRegion(element, element3, element2, graphicsNode, rectangle2D, rectangle2D2, bridgeContext);
    }
    
    public static short parseCoordinateSystem(final Element element, final String s, final String s2, final BridgeContext bridgeContext) {
        if ("userSpaceOnUse".equals(s2)) {
            return 1;
        }
        if ("objectBoundingBox".equals(s2)) {
            return 2;
        }
        throw new BridgeException(bridgeContext, element, "attribute.malformed", new Object[] { s, s2 });
    }
    
    public static short parseMarkerCoordinateSystem(final Element element, final String s, final String s2, final BridgeContext bridgeContext) {
        if ("userSpaceOnUse".equals(s2)) {
            return 1;
        }
        if ("strokeWidth".equals(s2)) {
            return 3;
        }
        throw new BridgeException(bridgeContext, element, "attribute.malformed", new Object[] { s, s2 });
    }
    
    protected static Rectangle2D convertRegion(final String s, final String s2, final String s3, final String s4, final short i, final GraphicsNode graphicsNode, final UnitProcessor.Context context) {
        double x = 0.0;
        double y = 0.0;
        double w = 0.0;
        double h = 0.0;
        switch (i) {
            case 2: {
                final double n = org.apache.batik.bridge.UnitProcessor.svgHorizontalCoordinateToObjectBoundingBox(s, "x", context);
                final double n2 = org.apache.batik.bridge.UnitProcessor.svgVerticalCoordinateToObjectBoundingBox(s2, "y", context);
                final double n3 = org.apache.batik.bridge.UnitProcessor.svgHorizontalLengthToObjectBoundingBox(s3, "width", context);
                final double n4 = org.apache.batik.bridge.UnitProcessor.svgVerticalLengthToObjectBoundingBox(s4, "height", context);
                final Rectangle2D geometryBounds = graphicsNode.getGeometryBounds();
                if (geometryBounds != null) {
                    x = geometryBounds.getX() + n * geometryBounds.getWidth();
                    y = geometryBounds.getY() + n2 * geometryBounds.getHeight();
                    w = n3 * geometryBounds.getWidth();
                    h = n4 * geometryBounds.getHeight();
                    break;
                }
                y = (x = (w = (h = 0.0)));
                break;
            }
            case 1: {
                x = org.apache.batik.bridge.UnitProcessor.svgHorizontalCoordinateToUserSpace(s, "x", context);
                y = org.apache.batik.bridge.UnitProcessor.svgVerticalCoordinateToUserSpace(s2, "y", context);
                w = org.apache.batik.bridge.UnitProcessor.svgHorizontalLengthToUserSpace(s3, "width", context);
                h = org.apache.batik.bridge.UnitProcessor.svgVerticalLengthToUserSpace(s4, "height", context);
                break;
            }
            default: {
                throw new Error("invalid unitsType:" + i);
            }
        }
        return new Rectangle2D.Double(x, y, w, h);
    }
    
    public static AffineTransform convertTransform(final Element element, final String s, final String s2, final BridgeContext bridgeContext) {
        try {
            return AWTTransformProducer.createAffineTransform(s2);
        }
        catch (ParseException ex) {
            throw new BridgeException(bridgeContext, element, ex, "attribute.malformed", new Object[] { s, s2, ex });
        }
    }
    
    public static AffineTransform toObjectBBox(final AffineTransform tx, final GraphicsNode graphicsNode) {
        final AffineTransform affineTransform = new AffineTransform();
        final Rectangle2D geometryBounds = graphicsNode.getGeometryBounds();
        if (geometryBounds != null) {
            affineTransform.translate(geometryBounds.getX(), geometryBounds.getY());
            affineTransform.scale(geometryBounds.getWidth(), geometryBounds.getHeight());
        }
        affineTransform.concatenate(tx);
        return affineTransform;
    }
    
    public static Rectangle2D toObjectBBox(final Rectangle2D rectangle2D, final GraphicsNode graphicsNode) {
        final Rectangle2D geometryBounds = graphicsNode.getGeometryBounds();
        if (geometryBounds != null) {
            return new Rectangle2D.Double(geometryBounds.getX() + rectangle2D.getX() * geometryBounds.getWidth(), geometryBounds.getY() + rectangle2D.getY() * geometryBounds.getHeight(), rectangle2D.getWidth() * geometryBounds.getWidth(), rectangle2D.getHeight() * geometryBounds.getHeight());
        }
        return new Rectangle2D.Double();
    }
    
    public static float convertSnapshotTime(final Element element, final BridgeContext bridgeContext) {
        if (!element.hasAttributeNS(null, "snapshotTime")) {
            return 0.0f;
        }
        final String attributeNS = element.getAttributeNS(null, "snapshotTime");
        if (attributeNS.equals("none")) {
            return 0.0f;
        }
        final ClockParser clockParser = new ClockParser(false);
        final Handler clockHandler = new Handler();
        clockParser.setClockHandler(clockHandler);
        try {
            clockParser.parse(attributeNS);
        }
        catch (ParseException ex) {
            throw new BridgeException(null, element, ex, "attribute.malformed", new Object[] { "snapshotTime", attributeNS, ex });
        }
        return clockHandler.time;
    }
    
    class Handler implements ClockHandler
    {
        float time;
        
        public void clockValue(final float time) {
            this.time = time;
        }
    }
}
