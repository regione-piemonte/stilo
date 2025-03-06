// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.bridge.svg12;

import org.apache.batik.util.ParsedURL;
import org.apache.batik.dom.AbstractNode;
import org.apache.batik.dom.util.XLinkSupport;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Attr;
import org.apache.batik.bridge.BridgeException;
import org.apache.batik.bridge.UnitProcessor;
import java.util.Iterator;
import org.w3c.dom.Node;
import org.apache.batik.gvt.svg12.MultiResGraphicsNode;
import java.util.Collection;
import java.awt.Dimension;
import java.util.LinkedList;
import org.apache.batik.bridge.Viewport;
import org.apache.batik.ext.awt.image.renderable.ClipRable;
import java.awt.Shape;
import org.apache.batik.ext.awt.image.renderable.ClipRable8Bit;
import java.awt.geom.Rectangle2D;
import org.apache.batik.bridge.CSSUtilities;
import java.awt.geom.AffineTransform;
import org.apache.batik.gvt.ImageNode;
import org.apache.batik.bridge.SVGUtilities;
import org.apache.batik.gvt.GraphicsNode;
import org.w3c.dom.Element;
import org.apache.batik.bridge.BridgeContext;
import org.apache.batik.bridge.Bridge;
import org.apache.batik.bridge.SVGImageElementBridge;

public class SVGMultiImageElementBridge extends SVGImageElementBridge
{
    public String getNamespaceURI() {
        return "http://www.w3.org/2000/svg";
    }
    
    public String getLocalName() {
        return "multiImage";
    }
    
    public Bridge getInstance() {
        return new SVGMultiImageElementBridge();
    }
    
    public GraphicsNode createGraphicsNode(final BridgeContext bridgeContext, final Element element) {
        if (!SVGUtilities.matchUserAgent(element, bridgeContext.getUserAgent())) {
            return null;
        }
        final ImageNode imageNode = (ImageNode)this.instantiateGraphicsNode();
        if (imageNode == null) {
            return null;
        }
        this.associateSVGContext(bridgeContext, element, imageNode);
        final Rectangle2D imageBounds = getImageBounds(bridgeContext, element);
        final String attribute = element.getAttribute("transform");
        AffineTransform convertTransform;
        if (attribute.length() != 0) {
            convertTransform = SVGUtilities.convertTransform(element, "transform", attribute, bridgeContext);
        }
        else {
            convertTransform = new AffineTransform();
        }
        convertTransform.translate(imageBounds.getX(), imageBounds.getY());
        imageNode.setTransform(convertTransform);
        imageNode.setVisible(CSSUtilities.convertVisibility(element));
        final Rectangle2D.Double double1 = new Rectangle2D.Double(0.0, 0.0, imageBounds.getWidth(), imageBounds.getHeight());
        imageNode.setClip(new ClipRable8Bit(imageNode.getGraphicsNodeRable(true), double1));
        final Rectangle2D convertEnableBackground = CSSUtilities.convertEnableBackground(element);
        if (convertEnableBackground != null) {
            imageNode.setBackgroundEnable(convertEnableBackground);
        }
        bridgeContext.openViewport(element, new MultiImageElementViewport((float)imageBounds.getWidth(), (float)imageBounds.getHeight()));
        final LinkedList<Element> list = new LinkedList<Element>();
        final LinkedList<Dimension> list2 = new LinkedList<Dimension>();
        final LinkedList<Dimension> list3 = new LinkedList<Dimension>();
        for (Node node = element.getFirstChild(); node != null; node = node.getNextSibling()) {
            if (node.getNodeType() == 1) {
                final Element element2 = (Element)node;
                if (this.getNamespaceURI().equals(element2.getNamespaceURI())) {
                    if (element2.getLocalName().equals("subImage")) {
                        this.addInfo(element2, list, list2, list3, imageBounds);
                    }
                    if (element2.getLocalName().equals("subImageRef")) {
                        this.addRefInfo(element2, list, list2, list3, imageBounds);
                    }
                }
            }
        }
        final Dimension[] array = new Dimension[list.size()];
        final Dimension[] array2 = new Dimension[list.size()];
        final Element[] array3 = new Element[list.size()];
        final Iterator<Object> iterator = list2.iterator();
        final Iterator<Object> iterator2 = list3.iterator();
        final Iterator<Object> iterator3 = list.iterator();
        int n = 0;
        while (iterator.hasNext()) {
            final Dimension dimension = iterator.next();
            final Dimension dimension2 = iterator2.next();
            int i = 0;
            if (dimension != null) {
                while (i < n) {
                    if (array[i] != null && dimension.width < array[i].width) {
                        break;
                    }
                    ++i;
                }
            }
            for (int j = n; j > i; --j) {
                array3[j] = array3[j - 1];
                array[j] = array[j - 1];
                array2[j] = array2[j - 1];
            }
            array3[i] = iterator3.next();
            array[i] = dimension;
            array2[i] = dimension2;
            ++n;
        }
        imageNode.setImage(new MultiResGraphicsNode(element, double1, array3, array, array2, bridgeContext));
        return imageNode;
    }
    
    public boolean isComposite() {
        return false;
    }
    
    public void buildGraphicsNode(final BridgeContext bridgeContext, final Element element, final GraphicsNode graphicsNode) {
        this.initializeDynamicSupport(bridgeContext, element, graphicsNode);
        bridgeContext.closeViewport(element);
    }
    
    protected void initializeDynamicSupport(final BridgeContext bridgeContext, final Element element, final GraphicsNode graphicsNode) {
        if (bridgeContext.isInteractive()) {
            bridgeContext.bind(element, ((ImageNode)graphicsNode).getImage());
        }
    }
    
    public void dispose() {
        this.ctx.removeViewport(this.e);
        super.dispose();
    }
    
    protected static Rectangle2D getImageBounds(final BridgeContext bridgeContext, final Element element) {
        final org.apache.batik.parser.UnitProcessor.Context context = UnitProcessor.createContext(bridgeContext, element);
        final String attributeNS = element.getAttributeNS(null, "x");
        float svgHorizontalCoordinateToUserSpace = 0.0f;
        if (attributeNS.length() != 0) {
            svgHorizontalCoordinateToUserSpace = UnitProcessor.svgHorizontalCoordinateToUserSpace(attributeNS, "x", context);
        }
        final String attributeNS2 = element.getAttributeNS(null, "y");
        float svgVerticalCoordinateToUserSpace = 0.0f;
        if (attributeNS2.length() != 0) {
            svgVerticalCoordinateToUserSpace = UnitProcessor.svgVerticalCoordinateToUserSpace(attributeNS2, "y", context);
        }
        final String attributeNS3 = element.getAttributeNS(null, "width");
        if (attributeNS3.length() == 0) {
            throw new BridgeException(bridgeContext, element, "attribute.missing", new Object[] { "width" });
        }
        final float svgHorizontalLengthToUserSpace = UnitProcessor.svgHorizontalLengthToUserSpace(attributeNS3, "width", context);
        final String attributeNS4 = element.getAttributeNS(null, "height");
        if (attributeNS4.length() == 0) {
            throw new BridgeException(bridgeContext, element, "attribute.missing", new Object[] { "height" });
        }
        return new Rectangle2D.Float(svgHorizontalCoordinateToUserSpace, svgVerticalCoordinateToUserSpace, svgHorizontalLengthToUserSpace, UnitProcessor.svgVerticalLengthToUserSpace(attributeNS4, "height", context));
    }
    
    protected void addInfo(final Element element, final Collection collection, final Collection collection2, final Collection collection3, final Rectangle2D rectangle2D) {
        final Element elementNS = element.getOwnerDocument().createElementNS("http://www.w3.org/2000/svg", "g");
        final NamedNodeMap attributes = element.getAttributes();
        for (int length = attributes.getLength(), i = 0; i < length; ++i) {
            final Attr attr = (Attr)attributes.item(i);
            elementNS.setAttributeNS(attr.getNamespaceURI(), attr.getName(), attr.getValue());
        }
        for (Node node = element.getFirstChild(); node != null; node = element.getFirstChild()) {
            elementNS.appendChild(node);
        }
        element.appendChild(elementNS);
        collection.add(elementNS);
        collection2.add(this.getElementMinPixel(element, rectangle2D));
        collection3.add(this.getElementMaxPixel(element, rectangle2D));
    }
    
    protected void addRefInfo(final Element element, final Collection collection, final Collection collection2, final Collection collection3, final Rectangle2D rectangle2D) {
        final String xLinkHref = XLinkSupport.getXLinkHref(element);
        if (xLinkHref.length() == 0) {
            throw new BridgeException(this.ctx, element, "attribute.missing", new Object[] { "xlink:href" });
        }
        final String baseURI = AbstractNode.getBaseURI(element);
        ParsedURL parsedURL;
        if (baseURI == null) {
            parsedURL = new ParsedURL(xLinkHref);
        }
        else {
            parsedURL = new ParsedURL(baseURI, xLinkHref);
        }
        final Element elementNS = element.getOwnerDocument().createElementNS("http://www.w3.org/2000/svg", "image");
        elementNS.setAttributeNS("http://www.w3.org/1999/xlink", "href", parsedURL.toString());
        final NamedNodeMap attributes = element.getAttributes();
        for (int length = attributes.getLength(), i = 0; i < length; ++i) {
            final Attr attr = (Attr)attributes.item(i);
            elementNS.setAttributeNS(attr.getNamespaceURI(), attr.getName(), attr.getValue());
        }
        if (element.getAttribute("x").length() == 0) {
            elementNS.setAttribute("x", "0");
        }
        if (element.getAttribute("y").length() == 0) {
            elementNS.setAttribute("y", "0");
        }
        if (element.getAttribute("width").length() == 0) {
            elementNS.setAttribute("width", "100%");
        }
        if (element.getAttribute("height").length() == 0) {
            elementNS.setAttribute("height", "100%");
        }
        element.appendChild(elementNS);
        collection.add(elementNS);
        collection2.add(this.getElementMinPixel(element, rectangle2D));
        collection3.add(this.getElementMaxPixel(element, rectangle2D));
    }
    
    protected Dimension getElementMinPixel(final Element element, final Rectangle2D rectangle2D) {
        return this.getElementPixelSize(element, "max-pixel-size", rectangle2D);
    }
    
    protected Dimension getElementMaxPixel(final Element element, final Rectangle2D rectangle2D) {
        return this.getElementPixelSize(element, "min-pixel-size", rectangle2D);
    }
    
    protected Dimension getElementPixelSize(final Element element, final String s, final Rectangle2D rectangle2D) {
        final String attribute = element.getAttribute(s);
        if (attribute.length() == 0) {
            return null;
        }
        final Float[] convertSVGNumberOptionalNumber = SVGUtilities.convertSVGNumberOptionalNumber(element, s, attribute, this.ctx);
        if (convertSVGNumberOptionalNumber[0] == null) {
            return null;
        }
        float n = convertSVGNumberOptionalNumber[0];
        if (convertSVGNumberOptionalNumber[1] != null) {
            n = convertSVGNumberOptionalNumber[1];
        }
        return new Dimension((int)(rectangle2D.getWidth() / n + 0.5), (int)(rectangle2D.getHeight() / n + 0.5));
    }
    
    public static class MultiImageElementViewport implements Viewport
    {
        private float width;
        private float height;
        
        public MultiImageElementViewport(final float width, final float height) {
            this.width = width;
            this.height = height;
        }
        
        public float getWidth() {
            return this.width;
        }
        
        public float getHeight() {
            return this.height;
        }
    }
}
