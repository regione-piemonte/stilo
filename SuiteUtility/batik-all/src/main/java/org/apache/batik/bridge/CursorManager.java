// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.bridge;

import org.apache.batik.util.SoftReferenceCache;
import org.apache.batik.util.Platform;
import java.awt.image.WritableRaster;
import java.awt.image.ColorModel;
import java.awt.image.SampleModel;
import java.util.Hashtable;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import org.apache.batik.gvt.GraphicsNode;
import org.w3c.dom.svg.SVGSVGElement;
import java.awt.Dimension;
import java.awt.geom.Rectangle2D;
import org.apache.batik.ext.awt.image.renderable.PadRable8Bit;
import org.apache.batik.ext.awt.image.PadMode;
import java.awt.geom.AffineTransform;
import org.apache.batik.ext.awt.image.spi.BrokenLinkProvider;
import org.apache.batik.ext.awt.image.spi.ImageTagRegistry;
import org.apache.batik.ext.awt.image.renderable.AffineRable8Bit;
import org.w3c.dom.Document;
import org.w3c.dom.svg.SVGDocument;
import java.awt.image.RenderedImage;
import java.awt.Rectangle;
import org.apache.batik.ext.awt.image.renderable.Filter;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.geom.Point2D;
import org.apache.batik.util.ParsedURL;
import org.w3c.dom.Node;
import org.apache.batik.dom.AbstractNode;
import org.apache.batik.dom.util.XLinkSupport;
import org.apache.batik.css.engine.value.Value;
import org.w3c.dom.Element;
import java.awt.Cursor;
import java.util.Map;
import org.apache.batik.util.SVGConstants;

public class CursorManager implements SVGConstants, ErrorConstants
{
    protected static Map cursorMap;
    public static final Cursor DEFAULT_CURSOR;
    public static final Cursor ANCHOR_CURSOR;
    public static final Cursor TEXT_CURSOR;
    public static final int DEFAULT_PREFERRED_WIDTH = 32;
    public static final int DEFAULT_PREFERRED_HEIGHT = 32;
    protected BridgeContext ctx;
    protected CursorCache cursorCache;
    
    public CursorManager(final BridgeContext ctx) {
        this.cursorCache = new CursorCache();
        this.ctx = ctx;
    }
    
    public static Cursor getPredefinedCursor(final String s) {
        return CursorManager.cursorMap.get(s);
    }
    
    public Cursor convertCursor(final Element element) {
        final Value computedStyle = CSSUtilities.getComputedStyle(element, 10);
        final String s = "auto";
        if (computedStyle != null) {
            if (computedStyle.getCssValueType() == 1 && computedStyle.getPrimitiveType() == 21) {
                return this.convertBuiltInCursor(element, computedStyle.getStringValue());
            }
            if (computedStyle.getCssValueType() == 2) {
                final int length = computedStyle.getLength();
                if (length == 1) {
                    final Value item = computedStyle.item(0);
                    if (item.getPrimitiveType() == 21) {
                        return this.convertBuiltInCursor(element, item.getStringValue());
                    }
                }
                else if (length > 1) {
                    return this.convertSVGCursor(element, computedStyle);
                }
            }
        }
        return this.convertBuiltInCursor(element, s);
    }
    
    public Cursor convertBuiltInCursor(final Element element, final String s) {
        Cursor cursor;
        if (s.charAt(0) == 'a') {
            if ("http://www.w3.org/2000/svg".equals(element.getNamespaceURI())) {
                final String localName = element.getLocalName();
                if ("a".equals(localName)) {
                    cursor = CursorManager.ANCHOR_CURSOR;
                }
                else if ("text".equals(localName) || "tspan".equals(localName) || "tref".equals(localName)) {
                    cursor = CursorManager.TEXT_CURSOR;
                }
                else {
                    if ("image".equals(localName)) {
                        return null;
                    }
                    cursor = CursorManager.DEFAULT_CURSOR;
                }
            }
            else {
                cursor = CursorManager.DEFAULT_CURSOR;
            }
        }
        else {
            cursor = getPredefinedCursor(s);
        }
        return cursor;
    }
    
    public Cursor convertSVGCursor(final Element element, final Value value) {
        final int length = value.getLength();
        Element referencedElement = null;
        for (int i = 0; i < length - 1; ++i) {
            final Value item = value.item(i);
            if (item.getPrimitiveType() == 20) {
                final String stringValue = item.getStringValue();
                try {
                    referencedElement = this.ctx.getReferencedElement(element, stringValue);
                }
                catch (BridgeException ex) {
                    if (!"uri.badTarget".equals(ex.getCode())) {
                        throw ex;
                    }
                }
                if (referencedElement != null && "http://www.w3.org/2000/svg".equals(referencedElement.getNamespaceURI()) && "cursor".equals(referencedElement.getLocalName())) {
                    final Cursor convertSVGCursorElement = this.convertSVGCursorElement(referencedElement);
                    if (convertSVGCursorElement != null) {
                        return convertSVGCursorElement;
                    }
                }
            }
        }
        final Value item2 = value.item(length - 1);
        String stringValue2 = "auto";
        if (item2.getPrimitiveType() == 21) {
            stringValue2 = item2.getStringValue();
        }
        return this.convertBuiltInCursor(element, stringValue2);
    }
    
    public Cursor convertSVGCursorElement(final Element element) {
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
        final org.apache.batik.parser.UnitProcessor.Context context = UnitProcessor.createContext(this.ctx, element);
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
        final CursorDescriptor cursorDescriptor = new CursorDescriptor(parsedURL, svgHorizontalCoordinateToUserSpace, svgVerticalCoordinateToUserSpace);
        final Cursor cursor = this.cursorCache.getCursor(cursorDescriptor);
        if (cursor != null) {
            return cursor;
        }
        final Point2D.Float float1 = new Point2D.Float(svgHorizontalCoordinateToUserSpace, svgVerticalCoordinateToUserSpace);
        final Filter cursorHrefToFilter = this.cursorHrefToFilter(element, parsedURL, float1);
        if (cursorHrefToFilter == null) {
            this.cursorCache.clearCursor(cursorDescriptor);
            return null;
        }
        final Rectangle bounds = cursorHrefToFilter.getBounds2D().getBounds();
        final RenderedImage scaledRendering = cursorHrefToFilter.createScaledRendering(bounds.width, bounds.height, null);
        Image renderedImageToImage;
        if (scaledRendering instanceof Image) {
            renderedImageToImage = (Image)scaledRendering;
        }
        else {
            renderedImageToImage = this.renderedImageToImage(scaledRendering);
        }
        float1.x = ((float1.x < 0.0f) ? 0.0f : float1.x);
        float1.y = ((float1.y < 0.0f) ? 0.0f : float1.y);
        float1.x = ((float1.x > bounds.width - 1) ? ((float)(bounds.width - 1)) : float1.x);
        float1.y = ((float1.y > bounds.height - 1) ? ((float)(bounds.height - 1)) : float1.y);
        final Cursor customCursor = Toolkit.getDefaultToolkit().createCustomCursor(renderedImageToImage, new Point(Math.round(float1.x), Math.round(float1.y)), parsedURL.toString());
        this.cursorCache.putCursor(cursorDescriptor, customCursor);
        return customCursor;
    }
    
    protected Filter cursorHrefToFilter(final Element element, final ParsedURL parsedURL, final Point2D point2D) {
        AffineRable8Bit affineRable8Bit = null;
        final String string = parsedURL.toString();
        Dimension dimension = null;
        final URIResolver uriResolver = this.ctx.createURIResolver((SVGDocument)element.getOwnerDocument(), this.ctx.getDocumentLoader());
        try {
            final Node node = uriResolver.getNode(string, element);
            if (node.getNodeType() != 9) {
                throw new BridgeException(this.ctx, element, "uri.image.invalid", new Object[] { string });
            }
            final SVGDocument svgDocument = (SVGDocument)node;
            this.ctx.initializeDocument((Document)svgDocument);
            final SVGSVGElement rootElement = svgDocument.getRootElement();
            final GraphicsNode build = this.ctx.getGVTBuilder().build(this.ctx, (Element)rootElement);
            float svgHorizontalLengthToUserSpace = 32.0f;
            float svgVerticalLengthToUserSpace = 32.0f;
            final org.apache.batik.parser.UnitProcessor.Context context = UnitProcessor.createContext(this.ctx, (Element)rootElement);
            final String attribute = ((Element)rootElement).getAttribute("width");
            if (attribute.length() != 0) {
                svgHorizontalLengthToUserSpace = UnitProcessor.svgHorizontalLengthToUserSpace(attribute, "width", context);
            }
            final String attribute2 = ((Element)rootElement).getAttribute("height");
            if (attribute2.length() != 0) {
                svgVerticalLengthToUserSpace = UnitProcessor.svgVerticalLengthToUserSpace(attribute2, "height", context);
            }
            dimension = Toolkit.getDefaultToolkit().getBestCursorSize(Math.round(svgHorizontalLengthToUserSpace), Math.round(svgVerticalLengthToUserSpace));
            affineRable8Bit = new AffineRable8Bit(build.getGraphicsNodeRable(true), ViewBox.getPreserveAspectRatioTransform((Element)rootElement, (float)dimension.width, (float)dimension.height, this.ctx));
        }
        catch (BridgeException ex) {
            throw ex;
        }
        catch (SecurityException ex2) {
            throw new BridgeException(this.ctx, element, ex2, "uri.unsecure", new Object[] { string });
        }
        catch (Exception ex3) {}
        if (affineRable8Bit == null) {
            final Filter url = ImageTagRegistry.getRegistry().readURL(parsedURL);
            if (url == null) {
                return null;
            }
            if (BrokenLinkProvider.hasBrokenLinkProperty(url)) {
                return null;
            }
            final Rectangle bounds = url.getBounds2D().getBounds();
            dimension = Toolkit.getDefaultToolkit().getBestCursorSize(bounds.width, bounds.height);
            if (bounds == null || bounds.width <= 0 || bounds.height <= 0) {
                return null;
            }
            AffineTransform preserveAspectRatioTransform = new AffineTransform();
            if (bounds.width > dimension.width || bounds.height > dimension.height) {
                preserveAspectRatioTransform = ViewBox.getPreserveAspectRatioTransform(new float[] { 0.0f, 0.0f, (float)bounds.width, (float)bounds.height }, (short)2, true, (float)dimension.width, (float)dimension.height);
            }
            affineRable8Bit = new AffineRable8Bit(url, preserveAspectRatioTransform);
        }
        affineRable8Bit.getAffine().transform(point2D, point2D);
        return new PadRable8Bit(affineRable8Bit, new Rectangle(0, 0, dimension.width, dimension.height), PadMode.ZERO_PAD);
    }
    
    protected Image renderedImageToImage(final RenderedImage renderedImage) {
        final int minX = renderedImage.getMinX();
        final int minY = renderedImage.getMinY();
        final SampleModel sampleModel = renderedImage.getSampleModel();
        final ColorModel colorModel = renderedImage.getColorModel();
        final WritableRaster writableRaster = Raster.createWritableRaster(sampleModel, new Point(minX, minY));
        renderedImage.copyData(writableRaster);
        return new BufferedImage(colorModel, writableRaster, colorModel.isAlphaPremultiplied(), null);
    }
    
    static {
        DEFAULT_CURSOR = Cursor.getPredefinedCursor(0);
        ANCHOR_CURSOR = Cursor.getPredefinedCursor(12);
        TEXT_CURSOR = Cursor.getPredefinedCursor(2);
        final Toolkit defaultToolkit = Toolkit.getDefaultToolkit();
        (CursorManager.cursorMap = new Hashtable()).put("crosshair", Cursor.getPredefinedCursor(1));
        CursorManager.cursorMap.put("default", Cursor.getPredefinedCursor(0));
        CursorManager.cursorMap.put("pointer", Cursor.getPredefinedCursor(12));
        CursorManager.cursorMap.put("e-resize", Cursor.getPredefinedCursor(11));
        CursorManager.cursorMap.put("ne-resize", Cursor.getPredefinedCursor(7));
        CursorManager.cursorMap.put("nw-resize", Cursor.getPredefinedCursor(6));
        CursorManager.cursorMap.put("n-resize", Cursor.getPredefinedCursor(8));
        CursorManager.cursorMap.put("se-resize", Cursor.getPredefinedCursor(5));
        CursorManager.cursorMap.put("sw-resize", Cursor.getPredefinedCursor(4));
        CursorManager.cursorMap.put("s-resize", Cursor.getPredefinedCursor(9));
        CursorManager.cursorMap.put("w-resize", Cursor.getPredefinedCursor(10));
        CursorManager.cursorMap.put("text", Cursor.getPredefinedCursor(2));
        CursorManager.cursorMap.put("wait", Cursor.getPredefinedCursor(3));
        Cursor cursor = Cursor.getPredefinedCursor(13);
        if (Platform.isOSX) {
            try {
                cursor = defaultToolkit.createCustomCursor(defaultToolkit.createImage(CursorManager.class.getResource("resources/move.gif")), new Point(11, 11), "move");
            }
            catch (Exception ex) {}
        }
        CursorManager.cursorMap.put("move", cursor);
        Cursor cursor2;
        try {
            cursor2 = defaultToolkit.createCustomCursor(defaultToolkit.createImage(CursorManager.class.getResource("resources/help.gif")), new Point(1, 3), "help");
        }
        catch (Exception ex2) {
            cursor2 = Cursor.getPredefinedCursor(12);
        }
        CursorManager.cursorMap.put("help", cursor2);
    }
    
    static class CursorCache extends SoftReferenceCache
    {
        public CursorCache() {
        }
        
        public Cursor getCursor(final CursorDescriptor cursorDescriptor) {
            return (Cursor)this.requestImpl(cursorDescriptor);
        }
        
        public void putCursor(final CursorDescriptor cursorDescriptor, final Cursor cursor) {
            this.putImpl(cursorDescriptor, cursor);
        }
        
        public void clearCursor(final CursorDescriptor cursorDescriptor) {
            this.clearImpl(cursorDescriptor);
        }
    }
    
    static class CursorDescriptor
    {
        ParsedURL purl;
        float x;
        float y;
        String desc;
        
        public CursorDescriptor(final ParsedURL purl, final float n, final float n2) {
            if (purl == null) {
                throw new IllegalArgumentException();
            }
            this.purl = purl;
            this.x = n;
            this.y = n2;
            this.desc = this.getClass().getName() + "\n\t:[" + this.purl + "]\n\t:[" + n + "]:[" + n2 + "]";
        }
        
        public boolean equals(final Object o) {
            if (o == null || !(o instanceof CursorDescriptor)) {
                return false;
            }
            final CursorDescriptor cursorDescriptor = (CursorDescriptor)o;
            return this.purl.equals(cursorDescriptor.purl) && this.x == cursorDescriptor.x && this.y == cursorDescriptor.y;
        }
        
        public String toString() {
            return this.desc;
        }
        
        public int hashCode() {
            return this.desc.hashCode();
        }
    }
}
