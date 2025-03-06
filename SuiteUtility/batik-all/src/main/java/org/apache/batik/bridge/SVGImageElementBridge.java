// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.bridge;

import java.io.BufferedInputStream;
import org.w3c.dom.events.DocumentEvent;
import org.apache.batik.dom.events.DOMMouseEvent;
import org.w3c.dom.events.Event;
import java.awt.color.ICC_Profile;
import java.awt.geom.NoninvertibleTransformException;
import org.apache.batik.ext.awt.image.renderable.ClipRable8Bit;
import org.w3c.dom.svg.SVGAnimatedPreserveAspectRatio;
import org.apache.batik.dom.svg.SVGOMAnimatedPreserveAspectRatio;
import org.w3c.dom.svg.SVGSVGElement;
import org.apache.batik.css.engine.CSSEngine;
import org.w3c.dom.events.EventTarget;
import java.awt.geom.AffineTransform;
import org.apache.batik.ext.awt.image.renderable.ClipRable;
import org.apache.batik.gvt.CanvasGraphicsNode;
import org.apache.batik.gvt.CompositeGraphicsNode;
import org.apache.batik.dom.svg.SVGOMDocument;
import org.apache.batik.ext.awt.image.spi.BrokenLinkProvider;
import org.apache.batik.dom.events.NodeEventTarget;
import org.apache.batik.gvt.RasterImageNode;
import org.apache.batik.dom.svg.LiveAttributeException;
import org.apache.batik.dom.svg.AbstractSVGAnimatedLength;
import org.apache.batik.dom.svg.AnimatedLiveAttributeValue;
import org.apache.batik.dom.svg.SVGContext;
import org.apache.batik.dom.svg.SVGOMElement;
import java.util.Iterator;
import org.apache.batik.util.MimeTypeConstants;
import java.util.Collection;
import java.util.ArrayList;
import org.apache.batik.ext.awt.image.renderable.Filter;
import org.w3c.dom.Document;
import org.apache.batik.ext.awt.color.ICCColorSpaceExt;
import java.awt.geom.Rectangle2D;
import java.io.InterruptedIOException;
import org.apache.batik.util.HaltingThread;
import java.io.InputStream;
import java.io.IOException;
import org.apache.batik.ext.awt.image.spi.ImageTagRegistry;
import java.awt.Shape;
import org.apache.batik.gvt.ShapeNode;
import org.apache.batik.util.ParsedURL;
import org.w3c.dom.Node;
import org.apache.batik.dom.AbstractNode;
import java.awt.RenderingHints;
import org.w3c.dom.svg.SVGImageElement;
import org.apache.batik.gvt.ImageNode;
import org.apache.batik.gvt.GraphicsNode;
import org.w3c.dom.Element;
import org.w3c.dom.events.EventListener;
import org.w3c.dom.svg.SVGDocument;

public class SVGImageElementBridge extends AbstractGraphicsNodeBridge
{
    protected SVGDocument imgDocument;
    protected EventListener listener;
    protected BridgeContext subCtx;
    protected boolean hitCheckChildren;
    static SVGBrokenLinkProvider brokenLinkProvider;
    
    public SVGImageElementBridge() {
        this.listener = null;
        this.subCtx = null;
        this.hitCheckChildren = false;
    }
    
    public String getLocalName() {
        return "image";
    }
    
    public Bridge getInstance() {
        return new SVGImageElementBridge();
    }
    
    public GraphicsNode createGraphicsNode(final BridgeContext bridgeContext, final Element element) {
        final ImageNode imageNode = (ImageNode)super.createGraphicsNode(bridgeContext, element);
        if (imageNode == null) {
            return null;
        }
        this.associateSVGContext(bridgeContext, element, imageNode);
        this.hitCheckChildren = false;
        final GraphicsNode buildImageGraphicsNode = this.buildImageGraphicsNode(bridgeContext, element);
        if (buildImageGraphicsNode == null) {
            throw new BridgeException(bridgeContext, element, "uri.image.invalid", new Object[] { ((SVGImageElement)element).getHref().getAnimVal() });
        }
        imageNode.setImage(buildImageGraphicsNode);
        imageNode.setHitCheckChildren(this.hitCheckChildren);
        final RenderingHints convertColorRendering = CSSUtilities.convertColorRendering(element, CSSUtilities.convertImageRendering(element, null));
        if (convertColorRendering != null) {
            imageNode.setRenderingHints(convertColorRendering);
        }
        return imageNode;
    }
    
    protected GraphicsNode buildImageGraphicsNode(final BridgeContext bridgeContext, final Element element) {
        final String animVal = ((SVGImageElement)element).getHref().getAnimVal();
        if (animVal.length() == 0) {
            throw new BridgeException(bridgeContext, element, "attribute.missing", new Object[] { "xlink:href" });
        }
        if (animVal.indexOf(35) != -1) {
            throw new BridgeException(bridgeContext, element, "attribute.malformed", new Object[] { "xlink:href", animVal });
        }
        final String baseURI = AbstractNode.getBaseURI(element);
        ParsedURL parsedURL;
        if (baseURI == null) {
            parsedURL = new ParsedURL(animVal);
        }
        else {
            parsedURL = new ParsedURL(baseURI, animVal);
        }
        return this.createImageGraphicsNode(bridgeContext, element, parsedURL);
    }
    
    protected GraphicsNode createImageGraphicsNode(final BridgeContext bridgeContext, final Element element, final ParsedURL parsedURL) {
        final Rectangle2D imageBounds = getImageBounds(bridgeContext, element);
        if (imageBounds.getWidth() == 0.0 || imageBounds.getHeight() == 0.0) {
            final ShapeNode shapeNode = new ShapeNode();
            shapeNode.setShape(imageBounds);
            return shapeNode;
        }
        final String url = ((SVGDocument)element.getOwnerDocument()).getURL();
        ParsedURL parsedURL2 = null;
        if (url != null) {
            parsedURL2 = new ParsedURL(url);
        }
        final UserAgent userAgent = bridgeContext.getUserAgent();
        try {
            userAgent.checkLoadExternalResource(parsedURL, parsedURL2);
        }
        catch (SecurityException ex) {
            throw new BridgeException(bridgeContext, element, ex, "uri.unsecure", new Object[] { parsedURL });
        }
        final DocumentLoader documentLoader = bridgeContext.getDocumentLoader();
        final ImageTagRegistry registry = ImageTagRegistry.getRegistry();
        final ICCColorSpaceExt colorSpace = extractColorSpace(element, bridgeContext);
        try {
            final Document checkCache = documentLoader.checkCache(parsedURL.toString());
            if (checkCache != null) {
                this.imgDocument = (SVGDocument)checkCache;
                return this.createSVGImageNode(bridgeContext, element, this.imgDocument);
            }
        }
        catch (BridgeException ex2) {
            throw ex2;
        }
        catch (Exception ex10) {}
        final Filter checkCache2 = registry.checkCache(parsedURL, colorSpace);
        if (checkCache2 != null) {
            return this.createRasterImageNode(bridgeContext, element, checkCache2, parsedURL);
        }
        ProtectedStream protectedStream;
        try {
            protectedStream = this.openStream(element, parsedURL);
        }
        catch (SecurityException ex3) {
            throw new BridgeException(bridgeContext, element, ex3, "uri.unsecure", new Object[] { parsedURL });
        }
        catch (IOException ex4) {
            return this.createBrokenImageNode(bridgeContext, element, parsedURL.toString(), ex4.getLocalizedMessage());
        }
        final Filter url2 = registry.readURL(protectedStream, parsedURL, colorSpace, false, false);
        if (url2 != null) {
            return this.createRasterImageNode(bridgeContext, element, url2, parsedURL);
        }
        try {
            protectedStream.retry();
        }
        catch (IOException ex11) {
            protectedStream.release();
            try {
                protectedStream = this.openStream(element, parsedURL);
            }
            catch (IOException ex5) {
                return this.createBrokenImageNode(bridgeContext, element, parsedURL.toString(), ex5.getLocalizedMessage());
            }
        }
        try {
            this.imgDocument = (SVGDocument)documentLoader.loadDocument(parsedURL.toString(), protectedStream);
            return this.createSVGImageNode(bridgeContext, element, this.imgDocument);
        }
        catch (BridgeException ex6) {
            throw ex6;
        }
        catch (SecurityException ex7) {
            throw new BridgeException(bridgeContext, element, ex7, "uri.unsecure", new Object[] { parsedURL });
        }
        catch (InterruptedIOException ex12) {
            if (HaltingThread.hasBeenHalted()) {
                throw new InterruptedBridgeException();
            }
        }
        catch (InterruptedBridgeException ex8) {
            throw ex8;
        }
        catch (Exception ex13) {}
        try {
            protectedStream.retry();
        }
        catch (IOException ex14) {
            protectedStream.release();
            try {
                protectedStream = this.openStream(element, parsedURL);
            }
            catch (IOException ex9) {
                return this.createBrokenImageNode(bridgeContext, element, parsedURL.toString(), ex9.getLocalizedMessage());
            }
        }
        try {
            final Filter url3 = registry.readURL(protectedStream, parsedURL, colorSpace, true, true);
            if (url3 != null) {
                return this.createRasterImageNode(bridgeContext, element, url3, parsedURL);
            }
        }
        finally {
            protectedStream.release();
        }
        return null;
    }
    
    protected ProtectedStream openStream(final Element element, final ParsedURL parsedURL) throws IOException {
        final ArrayList<Object> list = new ArrayList<Object>(ImageTagRegistry.getRegistry().getRegisteredMimeTypes());
        list.add(MimeTypeConstants.MIME_TYPES_SVG);
        return new ProtectedStream(parsedURL.openStream(list.iterator()));
    }
    
    protected GraphicsNode instantiateGraphicsNode() {
        return new ImageNode();
    }
    
    public boolean isComposite() {
        return false;
    }
    
    protected void initializeDynamicSupport(final BridgeContext ctx, final Element e, final GraphicsNode node) {
        if (!ctx.isInteractive()) {
            return;
        }
        ctx.bind(e, node);
        if (ctx.isDynamic()) {
            this.e = e;
            this.node = node;
            this.ctx = ctx;
            ((SVGOMElement)e).setSVGContext(this);
        }
    }
    
    public void handleAnimatedAttributeChanged(final AnimatedLiveAttributeValue animatedLiveAttributeValue) {
        try {
            final String namespaceURI = animatedLiveAttributeValue.getNamespaceURI();
            final String localName = animatedLiveAttributeValue.getLocalName();
            if (namespaceURI == null) {
                if (localName.equals("x") || localName.equals("y")) {
                    this.updateImageBounds();
                    return;
                }
                if (localName.equals("width") || localName.equals("height")) {
                    final SVGImageElement svgImageElement = (SVGImageElement)this.e;
                    final ImageNode imageNode = (ImageNode)this.node;
                    AbstractSVGAnimatedLength abstractSVGAnimatedLength;
                    if (localName.charAt(0) == 'w') {
                        abstractSVGAnimatedLength = (AbstractSVGAnimatedLength)svgImageElement.getWidth();
                    }
                    else {
                        abstractSVGAnimatedLength = (AbstractSVGAnimatedLength)svgImageElement.getHeight();
                    }
                    if (abstractSVGAnimatedLength.getCheckedValue() == 0.0f || imageNode.getImage() instanceof ShapeNode) {
                        this.rebuildImageNode();
                    }
                    else {
                        this.updateImageBounds();
                    }
                    return;
                }
                if (localName.equals("preserveAspectRatio")) {
                    this.updateImageBounds();
                    return;
                }
            }
            else if (namespaceURI.equals("http://www.w3.org/1999/xlink") && localName.equals("href")) {
                this.rebuildImageNode();
                return;
            }
        }
        catch (LiveAttributeException ex) {
            throw new BridgeException(this.ctx, ex);
        }
        super.handleAnimatedAttributeChanged(animatedLiveAttributeValue);
    }
    
    protected void updateImageBounds() {
        final Rectangle2D imageBounds = getImageBounds(this.ctx, this.e);
        final GraphicsNode image = ((ImageNode)this.node).getImage();
        float[] viewBoxAttribute = null;
        if (image instanceof RasterImageNode) {
            final Rectangle2D imageBounds2 = ((RasterImageNode)image).getImageBounds();
            viewBoxAttribute = new float[] { 0.0f, 0.0f, (float)imageBounds2.getWidth(), (float)imageBounds2.getHeight() };
        }
        else if (this.imgDocument != null) {
            viewBoxAttribute = ViewBox.parseViewBoxAttribute(this.e, ((Element)this.imgDocument.getRootElement()).getAttributeNS((String)null, "viewBox"), this.ctx);
        }
        if (image != null) {
            initializeViewport(this.ctx, this.e, image, viewBoxAttribute, imageBounds);
        }
    }
    
    protected void rebuildImageNode() {
        if (this.imgDocument != null && this.listener != null) {
            final NodeEventTarget nodeEventTarget = (NodeEventTarget)this.imgDocument.getRootElement();
            nodeEventTarget.removeEventListenerNS("http://www.w3.org/2001/xml-events", "click", this.listener, false);
            nodeEventTarget.removeEventListenerNS("http://www.w3.org/2001/xml-events", "keydown", this.listener, false);
            nodeEventTarget.removeEventListenerNS("http://www.w3.org/2001/xml-events", "keypress", this.listener, false);
            nodeEventTarget.removeEventListenerNS("http://www.w3.org/2001/xml-events", "keyup", this.listener, false);
            nodeEventTarget.removeEventListenerNS("http://www.w3.org/2001/xml-events", "mousedown", this.listener, false);
            nodeEventTarget.removeEventListenerNS("http://www.w3.org/2001/xml-events", "mousemove", this.listener, false);
            nodeEventTarget.removeEventListenerNS("http://www.w3.org/2001/xml-events", "mouseout", this.listener, false);
            nodeEventTarget.removeEventListenerNS("http://www.w3.org/2001/xml-events", "mouseover", this.listener, false);
            nodeEventTarget.removeEventListenerNS("http://www.w3.org/2001/xml-events", "mouseup", this.listener, false);
            this.listener = null;
        }
        if (this.imgDocument != null) {
            this.disposeTree((Node)this.imgDocument.getRootElement());
        }
        this.imgDocument = null;
        this.subCtx = null;
        final GraphicsNode buildImageGraphicsNode = this.buildImageGraphicsNode(this.ctx, this.e);
        ((ImageNode)this.node).setImage(buildImageGraphicsNode);
        if (buildImageGraphicsNode == null) {
            throw new BridgeException(this.ctx, this.e, "uri.image.invalid", new Object[] { ((SVGImageElement)this.e).getHref().getAnimVal() });
        }
    }
    
    protected void handleCSSPropertyChanged(final int n) {
        switch (n) {
            case 6:
            case 30: {
                final RenderingHints convertColorRendering = CSSUtilities.convertColorRendering(this.e, CSSUtilities.convertImageRendering(this.e, null));
                if (convertColorRendering != null) {
                    this.node.setRenderingHints(convertColorRendering);
                    break;
                }
                break;
            }
            default: {
                super.handleCSSPropertyChanged(n);
                break;
            }
        }
    }
    
    protected GraphicsNode createRasterImageNode(final BridgeContext bridgeContext, final Element element, final Filter image, final ParsedURL parsedURL) {
        final Rectangle2D imageBounds = getImageBounds(bridgeContext, element);
        if (imageBounds.getWidth() == 0.0 || imageBounds.getHeight() == 0.0) {
            final ShapeNode shapeNode = new ShapeNode();
            shapeNode.setShape(imageBounds);
            return shapeNode;
        }
        if (BrokenLinkProvider.hasBrokenLinkProperty(image)) {
            final Object property = image.getProperty("org.apache.batik.BrokenLinkImage");
            String s = "unknown";
            if (property instanceof String) {
                s = (String)property;
            }
            return this.createSVGImageNode(bridgeContext, element, bridgeContext.getUserAgent().getBrokenLinkDocument(element, parsedURL.toString(), s));
        }
        final RasterImageNode rasterImageNode = new RasterImageNode();
        rasterImageNode.setImage(image);
        final Rectangle2D bounds2D = image.getBounds2D();
        initializeViewport(bridgeContext, element, rasterImageNode, new float[] { 0.0f, 0.0f, (float)bounds2D.getWidth(), (float)bounds2D.getHeight() }, imageBounds);
        return rasterImageNode;
    }
    
    protected GraphicsNode createSVGImageNode(final BridgeContext bridgeContext, final Element element, final SVGDocument svgDocument) {
        final CSSEngine cssEngine = ((SVGOMDocument)svgDocument).getCSSEngine();
        this.subCtx = bridgeContext.createSubBridgeContext((SVGOMDocument)svgDocument);
        final CompositeGraphicsNode compositeGraphicsNode = new CompositeGraphicsNode();
        final Rectangle2D imageBounds = getImageBounds(bridgeContext, element);
        if (imageBounds.getWidth() == 0.0 || imageBounds.getHeight() == 0.0) {
            final ShapeNode shapeNode = new ShapeNode();
            shapeNode.setShape(imageBounds);
            compositeGraphicsNode.getChildren().add(shapeNode);
            return compositeGraphicsNode;
        }
        final Rectangle2D convertEnableBackground = CSSUtilities.convertEnableBackground(element);
        if (convertEnableBackground != null) {
            compositeGraphicsNode.setBackgroundEnable(convertEnableBackground);
        }
        final SVGSVGElement rootElement = svgDocument.getRootElement();
        final CanvasGraphicsNode canvasGraphicsNode = (CanvasGraphicsNode)this.subCtx.getGVTBuilder().build(this.subCtx, (Element)rootElement);
        if (cssEngine == null && bridgeContext.isInteractive()) {
            this.subCtx.addUIEventListeners((Document)svgDocument);
        }
        canvasGraphicsNode.setClip(null);
        canvasGraphicsNode.setViewingTransform(new AffineTransform());
        compositeGraphicsNode.getChildren().add(canvasGraphicsNode);
        initializeViewport(bridgeContext, element, compositeGraphicsNode, ViewBox.parseViewBoxAttribute(element, rootElement.getAttributeNS((String)null, "viewBox"), bridgeContext), imageBounds);
        if (bridgeContext.isInteractive()) {
            this.listener = new ForwardEventListener((Element)rootElement, element);
            final NodeEventTarget nodeEventTarget = (NodeEventTarget)rootElement;
            nodeEventTarget.addEventListenerNS("http://www.w3.org/2001/xml-events", "click", this.listener, false, null);
            this.subCtx.storeEventListenerNS(nodeEventTarget, "http://www.w3.org/2001/xml-events", "click", this.listener, false);
            nodeEventTarget.addEventListenerNS("http://www.w3.org/2001/xml-events", "keydown", this.listener, false, null);
            this.subCtx.storeEventListenerNS(nodeEventTarget, "http://www.w3.org/2001/xml-events", "keydown", this.listener, false);
            nodeEventTarget.addEventListenerNS("http://www.w3.org/2001/xml-events", "keypress", this.listener, false, null);
            this.subCtx.storeEventListenerNS(nodeEventTarget, "http://www.w3.org/2001/xml-events", "keypress", this.listener, false);
            nodeEventTarget.addEventListenerNS("http://www.w3.org/2001/xml-events", "keyup", this.listener, false, null);
            this.subCtx.storeEventListenerNS(nodeEventTarget, "http://www.w3.org/2001/xml-events", "keyup", this.listener, false);
            nodeEventTarget.addEventListenerNS("http://www.w3.org/2001/xml-events", "mousedown", this.listener, false, null);
            this.subCtx.storeEventListenerNS(nodeEventTarget, "http://www.w3.org/2001/xml-events", "mousedown", this.listener, false);
            nodeEventTarget.addEventListenerNS("http://www.w3.org/2001/xml-events", "mousemove", this.listener, false, null);
            this.subCtx.storeEventListenerNS(nodeEventTarget, "http://www.w3.org/2001/xml-events", "mousemove", this.listener, false);
            nodeEventTarget.addEventListenerNS("http://www.w3.org/2001/xml-events", "mouseout", this.listener, false, null);
            this.subCtx.storeEventListenerNS(nodeEventTarget, "http://www.w3.org/2001/xml-events", "mouseout", this.listener, false);
            nodeEventTarget.addEventListenerNS("http://www.w3.org/2001/xml-events", "mouseover", this.listener, false, null);
            this.subCtx.storeEventListenerNS(nodeEventTarget, "http://www.w3.org/2001/xml-events", "mouseover", this.listener, false);
            nodeEventTarget.addEventListenerNS("http://www.w3.org/2001/xml-events", "mouseup", this.listener, false, null);
            this.subCtx.storeEventListenerNS(nodeEventTarget, "http://www.w3.org/2001/xml-events", "mouseup", this.listener, false);
        }
        return compositeGraphicsNode;
    }
    
    public void dispose() {
        if (this.imgDocument != null && this.listener != null) {
            final NodeEventTarget nodeEventTarget = (NodeEventTarget)this.imgDocument.getRootElement();
            nodeEventTarget.removeEventListenerNS("http://www.w3.org/2001/xml-events", "click", this.listener, false);
            nodeEventTarget.removeEventListenerNS("http://www.w3.org/2001/xml-events", "keydown", this.listener, false);
            nodeEventTarget.removeEventListenerNS("http://www.w3.org/2001/xml-events", "keypress", this.listener, false);
            nodeEventTarget.removeEventListenerNS("http://www.w3.org/2001/xml-events", "keyup", this.listener, false);
            nodeEventTarget.removeEventListenerNS("http://www.w3.org/2001/xml-events", "mousedown", this.listener, false);
            nodeEventTarget.removeEventListenerNS("http://www.w3.org/2001/xml-events", "mousemove", this.listener, false);
            nodeEventTarget.removeEventListenerNS("http://www.w3.org/2001/xml-events", "mouseout", this.listener, false);
            nodeEventTarget.removeEventListenerNS("http://www.w3.org/2001/xml-events", "mouseover", this.listener, false);
            nodeEventTarget.removeEventListenerNS("http://www.w3.org/2001/xml-events", "mouseup", this.listener, false);
            this.listener = null;
        }
        if (this.imgDocument != null) {
            this.disposeTree((Node)this.imgDocument.getRootElement());
            this.imgDocument = null;
            this.subCtx = null;
        }
        super.dispose();
    }
    
    protected static void initializeViewport(final BridgeContext bridgeContext, final Element element, final GraphicsNode graphicsNode, final float[] array, final Rectangle2D rectangle2D) {
        final float x = (float)rectangle2D.getX();
        final float y = (float)rectangle2D.getY();
        final float w = (float)rectangle2D.getWidth();
        final float h = (float)rectangle2D.getHeight();
        try {
            final SVGOMAnimatedPreserveAspectRatio svgomAnimatedPreserveAspectRatio = (SVGOMAnimatedPreserveAspectRatio)((SVGImageElement)element).getPreserveAspectRatio();
            svgomAnimatedPreserveAspectRatio.check();
            final AffineTransform preserveAspectRatioTransform = ViewBox.getPreserveAspectRatioTransform(element, array, w, h, (SVGAnimatedPreserveAspectRatio)svgomAnimatedPreserveAspectRatio, bridgeContext);
            preserveAspectRatioTransform.preConcatenate(AffineTransform.getTranslateInstance(x, y));
            graphicsNode.setTransform(preserveAspectRatioTransform);
            Shape pSrc = null;
            if (CSSUtilities.convertOverflow(element)) {
                final float[] convertClip = CSSUtilities.convertClip(element);
                if (convertClip == null) {
                    pSrc = new Rectangle2D.Float(x, y, w, h);
                }
                else {
                    pSrc = new Rectangle2D.Float(x + convertClip[3], y + convertClip[0], w - convertClip[1] - convertClip[3], h - convertClip[2] - convertClip[0]);
                }
            }
            if (pSrc != null) {
                try {
                    graphicsNode.setClip(new ClipRable8Bit(graphicsNode.getGraphicsNodeRable(true), preserveAspectRatioTransform.createInverse().createTransformedShape(pSrc)));
                }
                catch (NoninvertibleTransformException ex2) {}
            }
        }
        catch (LiveAttributeException ex) {
            throw new BridgeException(bridgeContext, ex);
        }
    }
    
    protected static ICCColorSpaceExt extractColorSpace(final Element element, final BridgeContext bridgeContext) {
        final String stringValue = CSSUtilities.getComputedStyle(element, 8).getStringValue();
        ICCColorSpaceExt iccColorSpaceExt = null;
        if ("srgb".equalsIgnoreCase(stringValue)) {
            iccColorSpaceExt = new ICCColorSpaceExt(ICC_Profile.getInstance(1000), 4);
        }
        else if (!"auto".equalsIgnoreCase(stringValue) && !"".equalsIgnoreCase(stringValue)) {
            final SVGColorProfileElementBridge svgColorProfileElementBridge = (SVGColorProfileElementBridge)bridgeContext.getBridge("http://www.w3.org/2000/svg", "color-profile");
            if (svgColorProfileElementBridge != null) {
                iccColorSpaceExt = svgColorProfileElementBridge.createICCColorSpaceExt(bridgeContext, element, stringValue);
            }
        }
        return iccColorSpaceExt;
    }
    
    protected static Rectangle2D getImageBounds(final BridgeContext bridgeContext, final Element element) {
        try {
            final SVGImageElement svgImageElement = (SVGImageElement)element;
            return new Rectangle2D.Float(((AbstractSVGAnimatedLength)svgImageElement.getX()).getCheckedValue(), ((AbstractSVGAnimatedLength)svgImageElement.getY()).getCheckedValue(), ((AbstractSVGAnimatedLength)svgImageElement.getWidth()).getCheckedValue(), ((AbstractSVGAnimatedLength)svgImageElement.getHeight()).getCheckedValue());
        }
        catch (LiveAttributeException ex) {
            throw new BridgeException(bridgeContext, ex);
        }
    }
    
    GraphicsNode createBrokenImageNode(final BridgeContext bridgeContext, final Element element, final String s, final String s2) {
        return this.createSVGImageNode(bridgeContext, element, bridgeContext.getUserAgent().getBrokenLinkDocument(element, s, Messages.formatMessage("uri.image.error", new Object[] { s2 })));
    }
    
    static {
        ImageTagRegistry.setBrokenLinkProvider(SVGImageElementBridge.brokenLinkProvider = new SVGBrokenLinkProvider());
    }
    
    protected static class ForwardEventListener implements EventListener
    {
        protected Element svgElement;
        protected Element imgElement;
        
        public ForwardEventListener(final Element svgElement, final Element imgElement) {
            this.svgElement = svgElement;
            this.imgElement = imgElement;
        }
        
        public void handleEvent(final Event event) {
            final DOMMouseEvent domMouseEvent = (DOMMouseEvent)event;
            final DOMMouseEvent domMouseEvent2 = (DOMMouseEvent)((DocumentEvent)this.imgElement.getOwnerDocument()).createEvent("MouseEvents");
            domMouseEvent2.initMouseEventNS("http://www.w3.org/2001/xml-events", domMouseEvent.getType(), domMouseEvent.getBubbles(), domMouseEvent.getCancelable(), domMouseEvent.getView(), domMouseEvent.getDetail(), domMouseEvent.getScreenX(), domMouseEvent.getScreenY(), domMouseEvent.getClientX(), domMouseEvent.getClientY(), domMouseEvent.getButton(), (EventTarget)this.imgElement, domMouseEvent.getModifiersString());
            ((EventTarget)this.imgElement).dispatchEvent(domMouseEvent2);
        }
    }
    
    public static class ProtectedStream extends BufferedInputStream
    {
        static final int BUFFER_SIZE = 8192;
        
        ProtectedStream(final InputStream in) {
            super(in, 8192);
            super.mark(8192);
        }
        
        ProtectedStream(final InputStream in, final int n) {
            super(in, n);
            super.mark(n);
        }
        
        public boolean markSupported() {
            return false;
        }
        
        public void mark(final int n) {
        }
        
        public void reset() throws IOException {
            throw new IOException("Reset unsupported");
        }
        
        public void retry() throws IOException {
            super.reset();
        }
        
        public void close() throws IOException {
        }
        
        public void release() {
            try {
                super.close();
            }
            catch (IOException ex) {}
        }
    }
}
