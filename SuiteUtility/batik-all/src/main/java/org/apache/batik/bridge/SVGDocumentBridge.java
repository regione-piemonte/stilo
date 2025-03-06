// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.bridge;

import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import org.apache.batik.dom.svg.AnimatedLiveAttributeValue;
import org.apache.batik.css.engine.CSSEngineEvent;
import org.w3c.dom.Element;
import org.w3c.dom.events.MutationEvent;
import org.apache.batik.gvt.GraphicsNode;
import org.w3c.dom.Node;
import org.apache.batik.dom.svg.SVGOMDocument;
import org.apache.batik.gvt.RootGraphicsNode;
import org.w3c.dom.Document;
import org.apache.batik.dom.svg.SVGContext;

public class SVGDocumentBridge implements DocumentBridge, BridgeUpdateHandler, SVGContext
{
    protected Document document;
    protected RootGraphicsNode node;
    protected BridgeContext ctx;
    
    public String getNamespaceURI() {
        return null;
    }
    
    public String getLocalName() {
        return null;
    }
    
    public Bridge getInstance() {
        return new SVGDocumentBridge();
    }
    
    public RootGraphicsNode createGraphicsNode(final BridgeContext ctx, final Document document) {
        final RootGraphicsNode node = new RootGraphicsNode();
        this.document = document;
        this.node = node;
        this.ctx = ctx;
        ((SVGOMDocument)document).setSVGContext(this);
        return node;
    }
    
    public void buildGraphicsNode(final BridgeContext bridgeContext, final Document document, final RootGraphicsNode rootGraphicsNode) {
        if (bridgeContext.isDynamic()) {
            bridgeContext.bind(document, rootGraphicsNode);
        }
    }
    
    public void handleDOMAttrModifiedEvent(final MutationEvent mutationEvent) {
    }
    
    public void handleDOMNodeInsertedEvent(final MutationEvent mutationEvent) {
        if (mutationEvent.getTarget() instanceof Element) {
            final GraphicsNode build = this.ctx.getGVTBuilder().build(this.ctx, (Element)mutationEvent.getTarget());
            if (build == null) {
                return;
            }
            this.node.add(build);
        }
    }
    
    public void handleDOMNodeRemovedEvent(final MutationEvent mutationEvent) {
    }
    
    public void handleDOMCharacterDataModified(final MutationEvent mutationEvent) {
    }
    
    public void handleCSSEngineEvent(final CSSEngineEvent cssEngineEvent) {
    }
    
    public void handleAnimatedAttributeChanged(final AnimatedLiveAttributeValue animatedLiveAttributeValue) {
    }
    
    public void handleOtherAnimationChanged(final String s) {
    }
    
    public void dispose() {
        ((SVGOMDocument)this.document).setSVGContext(null);
        this.ctx.unbind(this.document);
    }
    
    public float getPixelUnitToMillimeter() {
        return this.ctx.getUserAgent().getPixelUnitToMillimeter();
    }
    
    public float getPixelToMM() {
        return this.getPixelUnitToMillimeter();
    }
    
    public Rectangle2D getBBox() {
        return null;
    }
    
    public AffineTransform getScreenTransform() {
        return this.ctx.getUserAgent().getTransform();
    }
    
    public void setScreenTransform(final AffineTransform transform) {
        this.ctx.getUserAgent().setTransform(transform);
    }
    
    public AffineTransform getCTM() {
        return null;
    }
    
    public AffineTransform getGlobalTransform() {
        return null;
    }
    
    public float getViewportWidth() {
        return 0.0f;
    }
    
    public float getViewportHeight() {
        return 0.0f;
    }
    
    public float getFontSize() {
        return 0.0f;
    }
}
