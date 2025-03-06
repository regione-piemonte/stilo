// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.bridge;

import org.apache.batik.css.engine.CSSEngine;
import org.apache.batik.gvt.CanvasGraphicsNode;
import org.w3c.dom.svg.SVGFitToViewBox;
import java.awt.Shape;
import org.apache.batik.ext.awt.geom.SegmentList;
import org.apache.batik.dom.svg.AnimatedLiveAttributeValue;
import org.apache.batik.css.engine.SVGCSSEngine;
import org.apache.batik.css.engine.CSSEngineEvent;
import org.w3c.dom.events.Event;
import org.w3c.dom.events.EventTarget;
import org.w3c.dom.events.DocumentEvent;
import org.apache.batik.dom.events.AbstractEvent;
import org.w3c.dom.events.MutationEvent;
import org.w3c.dom.Node;
import org.apache.batik.dom.svg.SVGOMElement;
import org.apache.batik.dom.svg.LiveAttributeException;
import org.apache.batik.dom.svg.SVGMotionAnimatableElement;
import org.apache.batik.dom.svg.AbstractSVGTransformList;
import org.apache.batik.dom.svg.SVGOMAnimatedTransformList;
import java.awt.geom.AffineTransform;
import org.w3c.dom.svg.SVGTransformable;
import org.w3c.dom.Element;
import java.awt.geom.Rectangle2D;
import java.lang.ref.SoftReference;
import org.apache.batik.parser.UnitProcessor;
import org.apache.batik.gvt.GraphicsNode;
import org.apache.batik.dom.svg.SVGContext;

public abstract class AbstractGraphicsNodeBridge extends AnimatableSVGBridge implements SVGContext, BridgeUpdateHandler, GraphicsNodeBridge, ErrorConstants
{
    protected GraphicsNode node;
    protected boolean isSVG12;
    protected UnitProcessor.Context unitContext;
    protected SoftReference bboxShape;
    protected Rectangle2D bbox;
    
    protected AbstractGraphicsNodeBridge() {
        this.bboxShape = null;
        this.bbox = null;
    }
    
    public GraphicsNode createGraphicsNode(final BridgeContext bridgeContext, final Element element) {
        if (!SVGUtilities.matchUserAgent(element, bridgeContext.getUserAgent())) {
            return null;
        }
        final GraphicsNode instantiateGraphicsNode = this.instantiateGraphicsNode();
        this.setTransform(instantiateGraphicsNode, element, bridgeContext);
        instantiateGraphicsNode.setVisible(CSSUtilities.convertVisibility(element));
        this.associateSVGContext(bridgeContext, element, instantiateGraphicsNode);
        return instantiateGraphicsNode;
    }
    
    protected abstract GraphicsNode instantiateGraphicsNode();
    
    public void buildGraphicsNode(final BridgeContext bridgeContext, final Element element, final GraphicsNode graphicsNode) {
        graphicsNode.setComposite(CSSUtilities.convertOpacity(element));
        graphicsNode.setFilter(CSSUtilities.convertFilter(element, graphicsNode, bridgeContext));
        graphicsNode.setMask(CSSUtilities.convertMask(element, graphicsNode, bridgeContext));
        graphicsNode.setClip(CSSUtilities.convertClipPath(element, graphicsNode, bridgeContext));
        graphicsNode.setPointerEventType(CSSUtilities.convertPointerEvents(element));
        this.initializeDynamicSupport(bridgeContext, element, graphicsNode);
    }
    
    public boolean getDisplay(final Element element) {
        return CSSUtilities.convertDisplay(element);
    }
    
    protected AffineTransform computeTransform(final SVGTransformable svgTransformable, final BridgeContext bridgeContext) {
        try {
            final AffineTransform affineTransform = new AffineTransform();
            final SVGOMAnimatedTransformList list = (SVGOMAnimatedTransformList)svgTransformable.getTransform();
            if (list.isSpecified()) {
                list.check();
                affineTransform.concatenate(((AbstractSVGTransformList)svgTransformable.getTransform().getAnimVal()).getAffineTransform());
            }
            if (this.e instanceof SVGMotionAnimatableElement) {
                final AffineTransform motionTransform = ((SVGMotionAnimatableElement)this.e).getMotionTransform();
                if (motionTransform != null) {
                    affineTransform.concatenate(motionTransform);
                }
            }
            return affineTransform;
        }
        catch (LiveAttributeException ex) {
            throw new BridgeException(bridgeContext, ex);
        }
    }
    
    protected void setTransform(final GraphicsNode graphicsNode, final Element element, final BridgeContext bridgeContext) {
        graphicsNode.setTransform(this.computeTransform((SVGTransformable)element, bridgeContext));
    }
    
    protected void associateSVGContext(final BridgeContext ctx, final Element e, final GraphicsNode node) {
        this.e = e;
        this.node = node;
        this.ctx = ctx;
        this.unitContext = org.apache.batik.bridge.UnitProcessor.createContext(ctx, e);
        this.isSVG12 = ctx.isSVG12();
        ((SVGOMElement)e).setSVGContext(this);
    }
    
    protected void initializeDynamicSupport(final BridgeContext bridgeContext, final Element element, final GraphicsNode graphicsNode) {
        if (bridgeContext.isInteractive()) {
            bridgeContext.bind(element, graphicsNode);
        }
    }
    
    public void handleDOMAttrModifiedEvent(final MutationEvent mutationEvent) {
    }
    
    protected void handleGeometryChanged() {
        this.node.setFilter(CSSUtilities.convertFilter(this.e, this.node, this.ctx));
        this.node.setMask(CSSUtilities.convertMask(this.e, this.node, this.ctx));
        this.node.setClip(CSSUtilities.convertClipPath(this.e, this.node, this.ctx));
        if (this.isSVG12) {
            if (!"use".equals(this.e.getLocalName())) {
                this.fireShapeChangeEvent();
            }
            this.fireBBoxChangeEvent();
        }
    }
    
    protected void fireShapeChangeEvent() {
        final AbstractEvent abstractEvent = (AbstractEvent)((DocumentEvent)this.e.getOwnerDocument()).createEvent("SVGEvents");
        abstractEvent.initEventNS("http://www.w3.org/2000/svg", "shapechange", true, false);
        try {
            ((EventTarget)this.e).dispatchEvent(abstractEvent);
        }
        catch (RuntimeException ex) {
            this.ctx.getUserAgent().displayError(ex);
        }
    }
    
    public void handleDOMNodeInsertedEvent(final MutationEvent mutationEvent) {
        if (mutationEvent.getTarget() instanceof Element) {
            final Element element = (Element)mutationEvent.getTarget();
            final Bridge bridge = this.ctx.getBridge(element);
            if (bridge instanceof GenericBridge) {
                ((GenericBridge)bridge).handleElement(this.ctx, element);
            }
        }
    }
    
    public void handleDOMNodeRemovedEvent(final MutationEvent mutationEvent) {
        final Node parentNode = this.e.getParentNode();
        if (parentNode instanceof SVGOMElement) {
            final SVGContext svgContext = ((SVGOMElement)parentNode).getSVGContext();
            if (svgContext instanceof SVGSwitchElementBridge) {
                ((SVGSwitchElementBridge)svgContext).handleChildElementRemoved(this.e);
                return;
            }
        }
        this.node.getParent().remove(this.node);
        this.disposeTree(this.e);
    }
    
    public void handleDOMCharacterDataModified(final MutationEvent mutationEvent) {
    }
    
    public void dispose() {
        ((SVGOMElement)this.e).setSVGContext(null);
        this.ctx.unbind(this.e);
        this.bboxShape = null;
    }
    
    protected void disposeTree(final Node node) {
        this.disposeTree(node, true);
    }
    
    protected void disposeTree(final Node node, final boolean b) {
        if (node instanceof SVGOMElement) {
            final SVGOMElement svgomElement = (SVGOMElement)node;
            final SVGContext svgContext = svgomElement.getSVGContext();
            if (svgContext instanceof BridgeUpdateHandler) {
                final BridgeUpdateHandler bridgeUpdateHandler = (BridgeUpdateHandler)svgContext;
                if (b) {
                    svgomElement.setSVGContext(null);
                }
                bridgeUpdateHandler.dispose();
            }
        }
        for (Node node2 = node.getFirstChild(); node2 != null; node2 = node2.getNextSibling()) {
            this.disposeTree(node2, b);
        }
    }
    
    public void handleCSSEngineEvent(final CSSEngineEvent cssEngineEvent) {
        try {
            final SVGCSSEngine svgcssEngine = (SVGCSSEngine)cssEngineEvent.getSource();
            final int[] properties = cssEngineEvent.getProperties();
            for (int i = 0; i < properties.length; ++i) {
                final int n = properties[i];
                this.handleCSSPropertyChanged(n);
                this.fireBaseAttributeListeners(svgcssEngine.getPropertyName(n));
            }
        }
        catch (Exception ex) {
            this.ctx.getUserAgent().displayError(ex);
        }
    }
    
    protected void handleCSSPropertyChanged(final int n) {
        switch (n) {
            case 57: {
                this.node.setVisible(CSSUtilities.convertVisibility(this.e));
                break;
            }
            case 38: {
                this.node.setComposite(CSSUtilities.convertOpacity(this.e));
                break;
            }
            case 18: {
                this.node.setFilter(CSSUtilities.convertFilter(this.e, this.node, this.ctx));
                break;
            }
            case 37: {
                this.node.setMask(CSSUtilities.convertMask(this.e, this.node, this.ctx));
                break;
            }
            case 3: {
                this.node.setClip(CSSUtilities.convertClipPath(this.e, this.node, this.ctx));
                break;
            }
            case 40: {
                this.node.setPointerEventType(CSSUtilities.convertPointerEvents(this.e));
                break;
            }
            case 12: {
                if (!this.getDisplay(this.e)) {
                    this.node.getParent().remove(this.node);
                    this.disposeTree(this.e, false);
                    break;
                }
                break;
            }
        }
    }
    
    public void handleAnimatedAttributeChanged(final AnimatedLiveAttributeValue animatedLiveAttributeValue) {
        if (animatedLiveAttributeValue.getNamespaceURI() == null && animatedLiveAttributeValue.getLocalName().equals("transform")) {
            this.setTransform(this.node, this.e, this.ctx);
            this.handleGeometryChanged();
        }
    }
    
    public void handleOtherAnimationChanged(final String s) {
        if (s.equals("motion")) {
            this.setTransform(this.node, this.e, this.ctx);
            this.handleGeometryChanged();
        }
    }
    
    protected void checkBBoxChange() {
        if (this.e != null) {
            this.fireBBoxChangeEvent();
        }
    }
    
    protected void fireBBoxChangeEvent() {
        final AbstractEvent abstractEvent = (AbstractEvent)((DocumentEvent)this.e.getOwnerDocument()).createEvent("SVGEvents");
        abstractEvent.initEventNS("http://www.w3.org/2000/svg", "RenderedBBoxChange", true, false);
        try {
            ((EventTarget)this.e).dispatchEvent(abstractEvent);
        }
        catch (RuntimeException ex) {
            this.ctx.getUserAgent().displayError(ex);
        }
    }
    
    public float getPixelUnitToMillimeter() {
        return this.ctx.getUserAgent().getPixelUnitToMillimeter();
    }
    
    public float getPixelToMM() {
        return this.getPixelUnitToMillimeter();
    }
    
    public Rectangle2D getBBox() {
        if (this.node == null) {
            return null;
        }
        final Shape outline = this.node.getOutline();
        if (this.bboxShape != null && outline == this.bboxShape.get()) {
            return this.bbox;
        }
        this.bboxShape = new SoftReference(outline);
        this.bbox = null;
        if (outline == null) {
            return this.bbox;
        }
        return this.bbox = new SegmentList(outline).getBounds2D();
    }
    
    public AffineTransform getCTM() {
        GraphicsNode graphicsNode = this.node;
        final AffineTransform affineTransform = new AffineTransform();
        Element element = this.e;
        while (element != null) {
            if (element instanceof SVGFitToViewBox) {
                AffineTransform tx;
                if (graphicsNode instanceof CanvasGraphicsNode) {
                    tx = ((CanvasGraphicsNode)graphicsNode).getViewingTransform();
                }
                else {
                    tx = graphicsNode.getTransform();
                }
                if (tx != null) {
                    affineTransform.preConcatenate(tx);
                    break;
                }
                break;
            }
            else {
                final AffineTransform transform = graphicsNode.getTransform();
                if (transform != null) {
                    affineTransform.preConcatenate(transform);
                }
                element = CSSEngine.getParentCSSStylableElement(element);
                graphicsNode = graphicsNode.getParent();
            }
        }
        return affineTransform;
    }
    
    public AffineTransform getScreenTransform() {
        return this.ctx.getUserAgent().getTransform();
    }
    
    public void setScreenTransform(final AffineTransform transform) {
        this.ctx.getUserAgent().setTransform(transform);
    }
    
    public AffineTransform getGlobalTransform() {
        return this.node.getGlobalTransform();
    }
    
    public float getViewportWidth() {
        return this.ctx.getBlockWidth(this.e);
    }
    
    public float getViewportHeight() {
        return this.ctx.getBlockHeight(this.e);
    }
    
    public float getFontSize() {
        return CSSUtilities.getComputedStyle(this.e, 22).getFloatValue();
    }
}
