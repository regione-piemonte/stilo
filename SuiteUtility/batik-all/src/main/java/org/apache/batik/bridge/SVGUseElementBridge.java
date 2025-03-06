// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.bridge;

import java.awt.Cursor;
import org.w3c.dom.events.Event;
import org.apache.batik.dom.svg.AnimatedLiveAttributeValue;
import org.apache.batik.dom.svg.AbstractSVGAnimatedLength;
import org.w3c.dom.svg.SVGUseElement;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.events.EventTarget;
import org.apache.batik.dom.events.NodeEventTarget;
import org.w3c.dom.events.EventListener;
import java.awt.RenderingHints;
import org.w3c.dom.svg.SVGTransformable;
import org.apache.batik.dom.AbstractDocument;
import org.apache.batik.dom.svg.SVGOMUseShadowRoot;
import org.apache.batik.dom.svg.LiveAttributeException;
import org.apache.batik.dom.svg.SVGOMAnimatedLength;
import org.w3c.dom.Attr;
import org.w3c.dom.Node;
import org.apache.batik.dom.svg.SVGOMDocument;
import org.apache.batik.dom.svg.SVGOMUseElement;
import org.apache.batik.gvt.CompositeGraphicsNode;
import org.apache.batik.gvt.GraphicsNode;
import org.w3c.dom.Element;

public class SVGUseElementBridge extends AbstractGraphicsNodeBridge
{
    protected ReferencedElementMutationListener l;
    protected BridgeContext subCtx;
    
    public String getLocalName() {
        return "use";
    }
    
    public Bridge getInstance() {
        return new SVGUseElementBridge();
    }
    
    public GraphicsNode createGraphicsNode(final BridgeContext bridgeContext, final Element element) {
        if (!SVGUtilities.matchUserAgent(element, bridgeContext.getUserAgent())) {
            return null;
        }
        final CompositeGraphicsNode buildCompositeGraphicsNode = this.buildCompositeGraphicsNode(bridgeContext, element, null);
        this.associateSVGContext(bridgeContext, element, buildCompositeGraphicsNode);
        return buildCompositeGraphicsNode;
    }
    
    public CompositeGraphicsNode buildCompositeGraphicsNode(final BridgeContext bridgeContext, final Element element, CompositeGraphicsNode compositeGraphicsNode) {
        final SVGOMUseElement svgomUseElement = (SVGOMUseElement)element;
        final String animVal = svgomUseElement.getHref().getAnimVal();
        if (animVal.length() == 0) {
            throw new BridgeException(bridgeContext, element, "attribute.missing", new Object[] { "xlink:href" });
        }
        final Element referencedElement = bridgeContext.getReferencedElement(element, animVal);
        final SVGOMDocument svgomDocument = (SVGOMDocument)element.getOwnerDocument();
        final SVGOMDocument svgomDocument2 = (SVGOMDocument)referencedElement.getOwnerDocument();
        final boolean b = svgomDocument2 == svgomDocument;
        BridgeContext subCtx = bridgeContext;
        this.subCtx = null;
        if (!b) {
            this.subCtx = (BridgeContext)svgomDocument2.getCSSEngine().getCSSContext();
            subCtx = this.subCtx;
        }
        Element element2 = (Element)svgomDocument.importNode(referencedElement, true, true);
        if ("symbol".equals(element2.getLocalName())) {
            final Element elementNS = svgomDocument.createElementNS("http://www.w3.org/2000/svg", "svg");
            final NamedNodeMap attributes = element2.getAttributes();
            for (int length = attributes.getLength(), i = 0; i < length; ++i) {
                final Attr attr = (Attr)attributes.item(i);
                elementNS.setAttributeNS(attr.getNamespaceURI(), attr.getName(), attr.getValue());
            }
            for (Node node = element2.getFirstChild(); node != null; node = element2.getFirstChild()) {
                elementNS.appendChild(node);
            }
            element2 = elementNS;
        }
        if ("svg".equals(element2.getLocalName())) {
            try {
                final SVGOMAnimatedLength svgomAnimatedLength = (SVGOMAnimatedLength)svgomUseElement.getWidth();
                if (svgomAnimatedLength.isSpecified()) {
                    element2.setAttributeNS(null, "width", svgomAnimatedLength.getAnimVal().getValueAsString());
                }
                final SVGOMAnimatedLength svgomAnimatedLength2 = (SVGOMAnimatedLength)svgomUseElement.getHeight();
                if (svgomAnimatedLength2.isSpecified()) {
                    element2.setAttributeNS(null, "height", svgomAnimatedLength2.getAnimVal().getValueAsString());
                }
            }
            catch (LiveAttributeException ex) {
                throw new BridgeException(bridgeContext, ex);
            }
        }
        final SVGOMUseShadowRoot useShadowTree = new SVGOMUseShadowRoot(svgomDocument, element, b);
        useShadowTree.appendChild(element2);
        if (compositeGraphicsNode == null) {
            compositeGraphicsNode = new CompositeGraphicsNode();
            this.associateSVGContext(bridgeContext, element, this.node);
        }
        else {
            for (int size = compositeGraphicsNode.size(), j = 0; j < size; ++j) {
                compositeGraphicsNode.remove(0);
            }
        }
        final Node cssFirstChild = svgomUseElement.getCSSFirstChild();
        if (cssFirstChild != null) {
            this.disposeTree(cssFirstChild);
        }
        svgomUseElement.setUseShadowTree(useShadowTree);
        final Element element3 = element2;
        CSSUtilities.computeStyleAndURIs(referencedElement, element2, animVal);
        compositeGraphicsNode.getChildren().add(bridgeContext.getGVTBuilder().build(bridgeContext, element3));
        compositeGraphicsNode.setTransform(this.computeTransform((SVGTransformable)element, bridgeContext));
        compositeGraphicsNode.setVisible(CSSUtilities.convertVisibility(element));
        final RenderingHints convertColorRendering = CSSUtilities.convertColorRendering(element, null);
        if (convertColorRendering != null) {
            compositeGraphicsNode.setRenderingHints(convertColorRendering);
        }
        final Rectangle2D convertEnableBackground = CSSUtilities.convertEnableBackground(element);
        if (convertEnableBackground != null) {
            compositeGraphicsNode.setBackgroundEnable(convertEnableBackground);
        }
        if (this.l != null) {
            final NodeEventTarget target = this.l.target;
            target.removeEventListenerNS("http://www.w3.org/2001/xml-events", "DOMAttrModified", this.l, true);
            target.removeEventListenerNS("http://www.w3.org/2001/xml-events", "DOMNodeInserted", this.l, true);
            target.removeEventListenerNS("http://www.w3.org/2001/xml-events", "DOMNodeRemoved", this.l, true);
            target.removeEventListenerNS("http://www.w3.org/2001/xml-events", "DOMCharacterDataModified", this.l, true);
            this.l = null;
        }
        if (b && bridgeContext.isDynamic()) {
            this.l = new ReferencedElementMutationListener();
            final NodeEventTarget target2 = (NodeEventTarget)referencedElement;
            (this.l.target = target2).addEventListenerNS("http://www.w3.org/2001/xml-events", "DOMAttrModified", this.l, true, null);
            subCtx.storeEventListenerNS(target2, "http://www.w3.org/2001/xml-events", "DOMAttrModified", this.l, true);
            target2.addEventListenerNS("http://www.w3.org/2001/xml-events", "DOMNodeInserted", this.l, true, null);
            subCtx.storeEventListenerNS(target2, "http://www.w3.org/2001/xml-events", "DOMNodeInserted", this.l, true);
            target2.addEventListenerNS("http://www.w3.org/2001/xml-events", "DOMNodeRemoved", this.l, true, null);
            subCtx.storeEventListenerNS(target2, "http://www.w3.org/2001/xml-events", "DOMNodeRemoved", this.l, true);
            target2.addEventListenerNS("http://www.w3.org/2001/xml-events", "DOMCharacterDataModified", this.l, true, null);
            subCtx.storeEventListenerNS(target2, "http://www.w3.org/2001/xml-events", "DOMCharacterDataModified", this.l, true);
        }
        return compositeGraphicsNode;
    }
    
    public void dispose() {
        if (this.l != null) {
            final NodeEventTarget target = this.l.target;
            target.removeEventListenerNS("http://www.w3.org/2001/xml-events", "DOMAttrModified", this.l, true);
            target.removeEventListenerNS("http://www.w3.org/2001/xml-events", "DOMNodeInserted", this.l, true);
            target.removeEventListenerNS("http://www.w3.org/2001/xml-events", "DOMNodeRemoved", this.l, true);
            target.removeEventListenerNS("http://www.w3.org/2001/xml-events", "DOMCharacterDataModified", this.l, true);
            this.l = null;
        }
        final SVGOMUseElement svgomUseElement = (SVGOMUseElement)this.e;
        if (svgomUseElement != null && svgomUseElement.getCSSFirstChild() != null) {
            this.disposeTree(svgomUseElement.getCSSFirstChild());
        }
        super.dispose();
        this.subCtx = null;
    }
    
    protected AffineTransform computeTransform(final SVGTransformable svgTransformable, final BridgeContext bridgeContext) {
        final AffineTransform computeTransform = super.computeTransform(svgTransformable, bridgeContext);
        final SVGUseElement svgUseElement = (SVGUseElement)svgTransformable;
        try {
            final AffineTransform translateInstance = AffineTransform.getTranslateInstance(((AbstractSVGAnimatedLength)svgUseElement.getX()).getCheckedValue(), ((AbstractSVGAnimatedLength)svgUseElement.getY()).getCheckedValue());
            translateInstance.preConcatenate(computeTransform);
            return translateInstance;
        }
        catch (LiveAttributeException ex) {
            throw new BridgeException(bridgeContext, ex);
        }
    }
    
    protected GraphicsNode instantiateGraphicsNode() {
        return null;
    }
    
    public boolean isComposite() {
        return false;
    }
    
    public void buildGraphicsNode(final BridgeContext bridgeContext, final Element element, final GraphicsNode graphicsNode) {
        super.buildGraphicsNode(bridgeContext, element, graphicsNode);
        if (bridgeContext.isInteractive()) {
            final NodeEventTarget nodeEventTarget = (NodeEventTarget)element;
            final CursorMouseOverListener cursorMouseOverListener = new CursorMouseOverListener(bridgeContext);
            nodeEventTarget.addEventListenerNS("http://www.w3.org/2001/xml-events", "mouseover", cursorMouseOverListener, false, null);
            bridgeContext.storeEventListenerNS(nodeEventTarget, "http://www.w3.org/2001/xml-events", "mouseover", cursorMouseOverListener, false);
        }
    }
    
    public void handleAnimatedAttributeChanged(final AnimatedLiveAttributeValue animatedLiveAttributeValue) {
        try {
            final String namespaceURI = animatedLiveAttributeValue.getNamespaceURI();
            final String localName = animatedLiveAttributeValue.getLocalName();
            if (namespaceURI == null && (localName.equals("x") || localName.equals("y") || localName.equals("transform"))) {
                this.node.setTransform(this.computeTransform((SVGTransformable)this.e, this.ctx));
                this.handleGeometryChanged();
            }
            else if ((namespaceURI == null && (localName.equals("width") || localName.equals("height"))) || (namespaceURI.equals("http://www.w3.org/1999/xlink") && localName.equals("href"))) {
                this.buildCompositeGraphicsNode(this.ctx, this.e, (CompositeGraphicsNode)this.node);
            }
        }
        catch (LiveAttributeException ex) {
            throw new BridgeException(this.ctx, ex);
        }
        super.handleAnimatedAttributeChanged(animatedLiveAttributeValue);
    }
    
    protected class ReferencedElementMutationListener implements EventListener
    {
        protected NodeEventTarget target;
        
        public void handleEvent(final Event event) {
            SVGUseElementBridge.this.buildCompositeGraphicsNode(SVGUseElementBridge.this.ctx, SVGUseElementBridge.this.e, (CompositeGraphicsNode)SVGUseElementBridge.this.node);
        }
    }
    
    public static class CursorMouseOverListener implements EventListener
    {
        protected BridgeContext ctx;
        
        public CursorMouseOverListener(final BridgeContext ctx) {
            this.ctx = ctx;
        }
        
        public void handleEvent(final Event event) {
            final Element element = (Element)event.getCurrentTarget();
            if (!CSSUtilities.isAutoCursor(element)) {
                final Cursor convertCursor = CSSUtilities.convertCursor(element, this.ctx);
                if (convertCursor != null) {
                    this.ctx.getUserAgent().setSVGCursor(convertCursor);
                }
            }
        }
    }
}
