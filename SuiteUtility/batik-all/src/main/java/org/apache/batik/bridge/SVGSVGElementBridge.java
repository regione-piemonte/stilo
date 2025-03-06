// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.bridge;

import java.util.HashSet;
import org.apache.batik.dom.svg.SVGContext;
import java.util.Set;
import java.util.Collection;
import org.apache.batik.gvt.TextNode;
import org.apache.batik.gvt.ShapeNode;
import org.apache.batik.dom.svg.SVGOMElement;
import java.util.ArrayList;
import java.util.List;
import org.apache.batik.gvt.CompositeGraphicsNode;
import org.w3c.dom.Node;
import org.apache.batik.dom.svg.AnimatedLiveAttributeValue;
import org.w3c.dom.svg.SVGRect;
import java.awt.Shape;
import org.apache.batik.dom.svg.LiveAttributeException;
import java.awt.RenderingHints;
import org.apache.batik.ext.awt.image.renderable.ClipRable;
import org.apache.batik.ext.awt.image.renderable.ClipRable8Bit;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Dimension2D;
import java.awt.Dimension;
import java.awt.geom.AffineTransform;
import java.awt.geom.NoninvertibleTransformException;
import org.w3c.dom.svg.SVGAnimatedRect;
import org.apache.batik.dom.svg.SVGOMAnimatedRect;
import org.apache.batik.dom.svg.AbstractSVGAnimatedLength;
import org.apache.batik.dom.svg.SVGOMSVGElement;
import org.w3c.dom.svg.SVGDocument;
import org.w3c.dom.Element;
import org.apache.batik.gvt.CanvasGraphicsNode;
import org.apache.batik.gvt.GraphicsNode;
import org.apache.batik.dom.svg.SVGSVGContext;

public class SVGSVGElementBridge extends SVGGElementBridge implements SVGSVGContext
{
    public String getLocalName() {
        return "svg";
    }
    
    public Bridge getInstance() {
        return new SVGSVGElementBridge();
    }
    
    protected GraphicsNode instantiateGraphicsNode() {
        return new CanvasGraphicsNode();
    }
    
    public GraphicsNode createGraphicsNode(final BridgeContext bridgeContext, final Element element) {
        if (!SVGUtilities.matchUserAgent(element, bridgeContext.getUserAgent())) {
            return null;
        }
        final CanvasGraphicsNode canvasGraphicsNode = (CanvasGraphicsNode)this.instantiateGraphicsNode();
        this.associateSVGContext(bridgeContext, element, canvasGraphicsNode);
        try {
            final SVGDocument svgDocument = (SVGDocument)element.getOwnerDocument();
            final SVGOMSVGElement svgomsvgElement = (SVGOMSVGElement)element;
            final boolean b = svgDocument.getRootElement() == element;
            float checkedValue = 0.0f;
            float checkedValue2 = 0.0f;
            if (!b) {
                checkedValue = ((AbstractSVGAnimatedLength)svgomsvgElement.getX()).getCheckedValue();
                checkedValue2 = ((AbstractSVGAnimatedLength)svgomsvgElement.getY()).getCheckedValue();
            }
            final float checkedValue3 = ((AbstractSVGAnimatedLength)svgomsvgElement.getWidth()).getCheckedValue();
            final float checkedValue4 = ((AbstractSVGAnimatedLength)svgomsvgElement.getHeight()).getCheckedValue();
            canvasGraphicsNode.setVisible(CSSUtilities.convertVisibility(element));
            final SVGOMAnimatedRect svgomAnimatedRect = (SVGOMAnimatedRect)svgomsvgElement.getViewBox();
            final AffineTransform preserveAspectRatioTransform = ViewBox.getPreserveAspectRatioTransform(element, (SVGAnimatedRect)svgomAnimatedRect, svgomsvgElement.getPreserveAspectRatio(), checkedValue3, checkedValue4, bridgeContext);
            float width = checkedValue3;
            float height = checkedValue4;
            try {
                final AffineTransform inverse = preserveAspectRatioTransform.createInverse();
                width = (float)(checkedValue3 * inverse.getScaleX());
                height = (float)(checkedValue4 * inverse.getScaleY());
            }
            catch (NoninvertibleTransformException ex2) {}
            final AffineTransform translateInstance = AffineTransform.getTranslateInstance(checkedValue, checkedValue2);
            if (!b) {
                canvasGraphicsNode.setPositionTransform(translateInstance);
            }
            else if (svgDocument == bridgeContext.getDocument()) {
                bridgeContext.setDocumentSize(new Dimension((int)(checkedValue3 + 0.5f), (int)(checkedValue4 + 0.5f)));
            }
            canvasGraphicsNode.setViewingTransform(preserveAspectRatioTransform);
            Shape pSrc = null;
            if (CSSUtilities.convertOverflow(element)) {
                final float[] convertClip = CSSUtilities.convertClip(element);
                if (convertClip == null) {
                    pSrc = new Rectangle2D.Float(checkedValue, checkedValue2, checkedValue3, checkedValue4);
                }
                else {
                    pSrc = new Rectangle2D.Float(checkedValue + convertClip[3], checkedValue2 + convertClip[0], checkedValue3 - convertClip[1] - convertClip[3], checkedValue4 - convertClip[2] - convertClip[0]);
                }
            }
            if (pSrc != null) {
                try {
                    final AffineTransform affineTransform = new AffineTransform(translateInstance);
                    affineTransform.concatenate(preserveAspectRatioTransform);
                    canvasGraphicsNode.setClip(new ClipRable8Bit(canvasGraphicsNode.getGraphicsNodeRable(true), affineTransform.createInverse().createTransformedShape(pSrc)));
                }
                catch (NoninvertibleTransformException ex3) {}
            }
            final RenderingHints convertColorRendering = CSSUtilities.convertColorRendering(element, null);
            if (convertColorRendering != null) {
                canvasGraphicsNode.setRenderingHints(convertColorRendering);
            }
            final Rectangle2D convertEnableBackground = CSSUtilities.convertEnableBackground(element);
            if (convertEnableBackground != null) {
                canvasGraphicsNode.setBackgroundEnable(convertEnableBackground);
            }
            if (svgomAnimatedRect.isSpecified()) {
                final SVGRect animVal = svgomAnimatedRect.getAnimVal();
                width = animVal.getWidth();
                height = animVal.getHeight();
            }
            bridgeContext.openViewport(element, new SVGSVGElementViewport(width, height));
            return canvasGraphicsNode;
        }
        catch (LiveAttributeException ex) {
            throw new BridgeException(bridgeContext, ex);
        }
    }
    
    public void buildGraphicsNode(final BridgeContext bridgeContext, final Element element, final GraphicsNode graphicsNode) {
        graphicsNode.setComposite(CSSUtilities.convertOpacity(element));
        graphicsNode.setFilter(CSSUtilities.convertFilter(element, graphicsNode, bridgeContext));
        graphicsNode.setMask(CSSUtilities.convertMask(element, graphicsNode, bridgeContext));
        graphicsNode.setPointerEventType(CSSUtilities.convertPointerEvents(element));
        this.initializeDynamicSupport(bridgeContext, element, graphicsNode);
        bridgeContext.closeViewport(element);
    }
    
    public void dispose() {
        this.ctx.removeViewport(this.e);
        super.dispose();
    }
    
    public void handleAnimatedAttributeChanged(final AnimatedLiveAttributeValue animatedLiveAttributeValue) {
        try {
            boolean b = false;
            if (animatedLiveAttributeValue.getNamespaceURI() == null) {
                final String localName = animatedLiveAttributeValue.getLocalName();
                if (localName.equals("width") || localName.equals("height")) {
                    b = true;
                }
                else if (localName.equals("x") || localName.equals("y")) {
                    final SVGDocument svgDocument = (SVGDocument)this.e.getOwnerDocument();
                    final SVGOMSVGElement svgomsvgElement = (SVGOMSVGElement)this.e;
                    if (svgDocument.getRootElement() != this.e) {
                        ((CanvasGraphicsNode)this.node).setPositionTransform(AffineTransform.getTranslateInstance(((AbstractSVGAnimatedLength)svgomsvgElement.getX()).getCheckedValue(), ((AbstractSVGAnimatedLength)svgomsvgElement.getY()).getCheckedValue()));
                        return;
                    }
                }
                else if (localName.equals("viewBox") || localName.equals("preserveAspectRatio")) {
                    final SVGDocument svgDocument2 = (SVGDocument)this.e.getOwnerDocument();
                    final SVGOMSVGElement svgomsvgElement2 = (SVGOMSVGElement)this.e;
                    final boolean b2 = svgDocument2.getRootElement() == this.e;
                    float checkedValue = 0.0f;
                    float checkedValue2 = 0.0f;
                    if (!b2) {
                        checkedValue = ((AbstractSVGAnimatedLength)svgomsvgElement2.getX()).getCheckedValue();
                        checkedValue2 = ((AbstractSVGAnimatedLength)svgomsvgElement2.getY()).getCheckedValue();
                    }
                    final float checkedValue3 = ((AbstractSVGAnimatedLength)svgomsvgElement2.getWidth()).getCheckedValue();
                    final float checkedValue4 = ((AbstractSVGAnimatedLength)svgomsvgElement2.getHeight()).getCheckedValue();
                    final CanvasGraphicsNode canvasGraphicsNode = (CanvasGraphicsNode)this.node;
                    final AffineTransform preserveAspectRatioTransform = ViewBox.getPreserveAspectRatioTransform(this.e, svgomsvgElement2.getViewBox(), svgomsvgElement2.getPreserveAspectRatio(), checkedValue3, checkedValue4, this.ctx);
                    final AffineTransform viewingTransform = canvasGraphicsNode.getViewingTransform();
                    if (preserveAspectRatioTransform.getScaleX() != viewingTransform.getScaleX() || preserveAspectRatioTransform.getScaleY() != viewingTransform.getScaleY() || preserveAspectRatioTransform.getShearX() != viewingTransform.getShearX() || preserveAspectRatioTransform.getShearY() != viewingTransform.getShearY()) {
                        b = true;
                    }
                    else {
                        canvasGraphicsNode.setViewingTransform(preserveAspectRatioTransform);
                        Shape pSrc = null;
                        if (CSSUtilities.convertOverflow(this.e)) {
                            final float[] convertClip = CSSUtilities.convertClip(this.e);
                            if (convertClip == null) {
                                pSrc = new Rectangle2D.Float(checkedValue, checkedValue2, checkedValue3, checkedValue4);
                            }
                            else {
                                pSrc = new Rectangle2D.Float(checkedValue + convertClip[3], checkedValue2 + convertClip[0], checkedValue3 - convertClip[1] - convertClip[3], checkedValue4 - convertClip[2] - convertClip[0]);
                            }
                        }
                        if (pSrc != null) {
                            try {
                                final AffineTransform positionTransform = canvasGraphicsNode.getPositionTransform();
                                AffineTransform affineTransform;
                                if (positionTransform == null) {
                                    affineTransform = new AffineTransform();
                                }
                                else {
                                    affineTransform = new AffineTransform(positionTransform);
                                }
                                affineTransform.concatenate(preserveAspectRatioTransform);
                                canvasGraphicsNode.setClip(new ClipRable8Bit(canvasGraphicsNode.getGraphicsNodeRable(true), affineTransform.createInverse().createTransformedShape(pSrc)));
                            }
                            catch (NoninvertibleTransformException ex2) {}
                        }
                    }
                }
                if (b) {
                    final CompositeGraphicsNode parent = this.node.getParent();
                    parent.remove(this.node);
                    this.disposeTree(this.e, false);
                    this.handleElementAdded(parent, this.e.getParentNode(), this.e);
                    return;
                }
            }
        }
        catch (LiveAttributeException ex) {
            throw new BridgeException(this.ctx, ex);
        }
        super.handleAnimatedAttributeChanged(animatedLiveAttributeValue);
    }
    
    public List getIntersectionList(final SVGRect svgRect, Element element) {
        final ArrayList<SVGOMElement> list = new ArrayList<SVGOMElement>();
        final Rectangle2D.Float float1 = new Rectangle2D.Float(svgRect.getX(), svgRect.getY(), svgRect.getWidth(), svgRect.getHeight());
        final GraphicsNode graphicsNode = this.ctx.getGraphicsNode(this.e);
        if (graphicsNode == null) {
            return list;
        }
        final Rectangle2D sensitiveBounds = graphicsNode.getSensitiveBounds();
        if (sensitiveBounds == null) {
            return list;
        }
        if (!float1.intersects(sensitiveBounds)) {
            return list;
        }
        final Element e = this.e;
        AffineTransform tx = graphicsNode.getGlobalTransform();
        try {
            tx = tx.createInverse();
        }
        catch (NoninvertibleTransformException ex) {}
        Node node;
        for (node = e.getFirstChild(); node != null && !(node instanceof Element); node = node.getNextSibling()) {}
        if (node == null) {
            return list;
        }
        Element element2 = (Element)node;
        Set ancestors = null;
        if (element != null) {
            ancestors = this.getAncestors(element, e);
            if (ancestors == null) {
                element = null;
            }
        }
        while (element2 != null) {
            final String namespaceURI = element2.getNamespaceURI();
            final String localName = element2.getLocalName();
            final boolean b = "http://www.w3.org/2000/svg".equals(namespaceURI) && ("g".equals(localName) || "svg".equals(localName) || "a".equals(localName));
            final GraphicsNode graphicsNode2 = this.ctx.getGraphicsNode(element2);
            if (graphicsNode2 == null) {
                if (ancestors != null && ancestors.contains(element2)) {
                    break;
                }
                element2 = this.getNext(element2, e, element);
            }
            else {
                final AffineTransform globalTransform = graphicsNode2.getGlobalTransform();
                Rectangle2D r = graphicsNode2.getSensitiveBounds();
                globalTransform.preConcatenate(tx);
                if (r != null) {
                    r = globalTransform.createTransformedShape(r).getBounds2D();
                }
                if (r == null || !float1.intersects(r)) {
                    if (ancestors != null && ancestors.contains(element2)) {
                        break;
                    }
                    element2 = this.getNext(element2, e, element);
                }
                else {
                    if (b) {
                        Node node2;
                        for (node2 = element2.getFirstChild(); node2 != null && !(node2 instanceof Element); node2 = node2.getNextSibling()) {}
                        if (node2 != null) {
                            element2 = (Element)node2;
                            continue;
                        }
                    }
                    else {
                        if (element2 == element) {
                            break;
                        }
                        if ("http://www.w3.org/2000/svg".equals(namespaceURI) && "use".equals(localName) && float1.contains(r)) {
                            list.add((SVGOMElement)element2);
                        }
                        if (graphicsNode2 instanceof ShapeNode) {
                            final Shape sensitiveArea = ((ShapeNode)graphicsNode2).getSensitiveArea();
                            if (sensitiveArea != null && globalTransform.createTransformedShape(sensitiveArea).intersects(float1)) {
                                list.add((SVGOMElement)element2);
                            }
                        }
                        else if (graphicsNode2 instanceof TextNode) {
                            final Set textIntersectionSet = ((SVGTextElementBridge)((SVGOMElement)element2).getSVGContext()).getTextIntersectionSet(globalTransform, float1);
                            if (ancestors != null && ancestors.contains(element2)) {
                                this.filterChildren(element2, element, textIntersectionSet, list);
                            }
                            else {
                                list.addAll((Collection<?>)textIntersectionSet);
                            }
                        }
                        else {
                            list.add((SVGOMElement)element2);
                        }
                    }
                    element2 = this.getNext(element2, e, element);
                }
            }
        }
        return list;
    }
    
    public List getEnclosureList(final SVGRect svgRect, Element element) {
        final ArrayList<SVGOMElement> list = new ArrayList<SVGOMElement>();
        final Rectangle2D.Float float1 = new Rectangle2D.Float(svgRect.getX(), svgRect.getY(), svgRect.getWidth(), svgRect.getHeight());
        final GraphicsNode graphicsNode = this.ctx.getGraphicsNode(this.e);
        if (graphicsNode == null) {
            return list;
        }
        final Rectangle2D sensitiveBounds = graphicsNode.getSensitiveBounds();
        if (sensitiveBounds == null) {
            return list;
        }
        if (!float1.intersects(sensitiveBounds)) {
            return list;
        }
        final Element e = this.e;
        AffineTransform tx = graphicsNode.getGlobalTransform();
        try {
            tx = tx.createInverse();
        }
        catch (NoninvertibleTransformException ex) {}
        Node node;
        for (node = e.getFirstChild(); node != null && !(node instanceof Element); node = node.getNextSibling()) {}
        if (node == null) {
            return list;
        }
        Element element2 = (Element)node;
        Set ancestors = null;
        if (element != null) {
            ancestors = this.getAncestors(element, e);
            if (ancestors == null) {
                element = null;
            }
        }
        while (element2 != null) {
            final String namespaceURI = element2.getNamespaceURI();
            final String localName = element2.getLocalName();
            final boolean b = "http://www.w3.org/2000/svg".equals(namespaceURI) && ("g".equals(localName) || "svg".equals(localName) || "a".equals(localName));
            final GraphicsNode graphicsNode2 = this.ctx.getGraphicsNode(element2);
            if (graphicsNode2 == null) {
                if (ancestors != null && ancestors.contains(element2)) {
                    break;
                }
                element2 = this.getNext(element2, e, element);
            }
            else {
                final AffineTransform globalTransform = graphicsNode2.getGlobalTransform();
                Rectangle2D rectangle2D = graphicsNode2.getSensitiveBounds();
                globalTransform.preConcatenate(tx);
                if (rectangle2D != null) {
                    rectangle2D = globalTransform.createTransformedShape(rectangle2D).getBounds2D();
                }
                if (rectangle2D == null || !float1.intersects(rectangle2D)) {
                    if (ancestors != null && ancestors.contains(element2)) {
                        break;
                    }
                    element2 = this.getNext(element2, e, element);
                }
                else {
                    if (b) {
                        Node node2;
                        for (node2 = element2.getFirstChild(); node2 != null && !(node2 instanceof Element); node2 = node2.getNextSibling()) {}
                        if (node2 != null) {
                            element2 = (Element)node2;
                            continue;
                        }
                    }
                    else {
                        if (element2 == element) {
                            break;
                        }
                        if ("http://www.w3.org/2000/svg".equals(namespaceURI) && "use".equals(localName)) {
                            if (float1.contains(rectangle2D)) {
                                list.add((SVGOMElement)element2);
                            }
                        }
                        else if (graphicsNode2 instanceof TextNode) {
                            final Set textEnclosureSet = ((SVGTextElementBridge)((SVGOMElement)element2).getSVGContext()).getTextEnclosureSet(globalTransform, float1);
                            if (ancestors != null && ancestors.contains(element2)) {
                                this.filterChildren(element2, element, textEnclosureSet, list);
                            }
                            else {
                                list.addAll((Collection<?>)textEnclosureSet);
                            }
                        }
                        else if (float1.contains(rectangle2D)) {
                            list.add((SVGOMElement)element2);
                        }
                    }
                    element2 = this.getNext(element2, e, element);
                }
            }
        }
        return list;
    }
    
    public boolean checkIntersection(final Element element, final SVGRect svgRect) {
        final GraphicsNode graphicsNode = this.ctx.getGraphicsNode(this.e);
        if (graphicsNode == null) {
            return false;
        }
        final Rectangle2D.Float float1 = new Rectangle2D.Float(svgRect.getX(), svgRect.getY(), svgRect.getWidth(), svgRect.getHeight());
        AffineTransform tx = graphicsNode.getGlobalTransform();
        try {
            tx = tx.createInverse();
        }
        catch (NoninvertibleTransformException ex) {}
        if (element instanceof SVGOMElement) {
            final SVGContext svgContext = ((SVGOMElement)element).getSVGContext();
            if (svgContext instanceof SVGTextElementBridge || svgContext instanceof SVGTextElementBridge.AbstractTextChildSVGContext) {
                return SVGTextElementBridge.getTextIntersection(this.ctx, element, tx, float1, true);
            }
        }
        Shape sensitiveBounds = null;
        final GraphicsNode graphicsNode2 = this.ctx.getGraphicsNode(element);
        if (graphicsNode2 != null) {
            sensitiveBounds = graphicsNode2.getSensitiveBounds();
        }
        if (sensitiveBounds == null) {
            return false;
        }
        final AffineTransform globalTransform = graphicsNode2.getGlobalTransform();
        globalTransform.preConcatenate(tx);
        if (!float1.intersects(globalTransform.createTransformedShape(sensitiveBounds).getBounds2D())) {
            return false;
        }
        if (!(graphicsNode2 instanceof ShapeNode)) {
            return true;
        }
        final Shape sensitiveArea = ((ShapeNode)graphicsNode2).getSensitiveArea();
        return sensitiveArea != null && globalTransform.createTransformedShape(sensitiveArea).intersects(float1);
    }
    
    public boolean checkEnclosure(final Element element, final SVGRect svgRect) {
        GraphicsNode graphicsNode = this.ctx.getGraphicsNode(element);
        Shape pSrc = null;
        if (element instanceof SVGOMElement) {
            final SVGContext svgContext = ((SVGOMElement)element).getSVGContext();
            if (svgContext instanceof SVGTextElementBridge || svgContext instanceof SVGTextElementBridge.AbstractTextChildSVGContext) {
                pSrc = SVGTextElementBridge.getTextBounds(this.ctx, element, true);
                for (Element element2 = (Element)element.getParentNode(); element2 != null && graphicsNode == null; graphicsNode = this.ctx.getGraphicsNode(element2), element2 = (Element)element2.getParentNode()) {}
            }
            else if (graphicsNode != null) {
                pSrc = graphicsNode.getSensitiveBounds();
            }
        }
        else if (graphicsNode != null) {
            pSrc = graphicsNode.getSensitiveBounds();
        }
        if (pSrc == null) {
            return false;
        }
        final GraphicsNode graphicsNode2 = this.ctx.getGraphicsNode(this.e);
        if (graphicsNode2 == null) {
            return false;
        }
        final Rectangle2D.Float float1 = new Rectangle2D.Float(svgRect.getX(), svgRect.getY(), svgRect.getWidth(), svgRect.getHeight());
        AffineTransform tx = graphicsNode2.getGlobalTransform();
        try {
            tx = tx.createInverse();
        }
        catch (NoninvertibleTransformException ex) {}
        final AffineTransform globalTransform = graphicsNode.getGlobalTransform();
        globalTransform.preConcatenate(tx);
        return float1.contains(globalTransform.createTransformedShape(pSrc).getBounds2D());
    }
    
    public boolean filterChildren(final Element element, final Element element2, final Set set, final List list) {
        for (Node node = element.getFirstChild(); node != null; node = node.getNextSibling()) {
            if (node instanceof Element && this.filterChildren((Element)node, element2, set, list)) {
                return true;
            }
        }
        if (element == element2) {
            return true;
        }
        if (set.contains(element)) {
            list.add(element);
        }
        return false;
    }
    
    protected Set getAncestors(final Element element, final Element element2) {
        final HashSet<Element> set = new HashSet<Element>();
        Element element3 = element;
        do {
            set.add(element3);
            element3 = (Element)element3.getParentNode();
        } while (element3 != null && element3 != element2);
        if (element3 == null) {
            return null;
        }
        return set;
    }
    
    protected Element getNext(Element element, final Element element2, final Element element3) {
        Node node;
        for (node = element.getNextSibling(); node != null; node = node.getNextSibling()) {
            if (node instanceof Element) {
                break;
            }
        }
        while (node == null) {
            element = (Element)element.getParentNode();
            if (element == element3 || element == element2) {
                node = null;
                break;
            }
            for (node = element.getNextSibling(); node != null; node = node.getNextSibling()) {
                if (node instanceof Element) {
                    break;
                }
            }
        }
        return (Element)node;
    }
    
    public void deselectAll() {
        this.ctx.getUserAgent().deselectAll();
    }
    
    public int suspendRedraw(final int n) {
        final UpdateManager updateManager = this.ctx.getUpdateManager();
        if (updateManager != null) {
            return updateManager.addRedrawSuspension(n);
        }
        return -1;
    }
    
    public boolean unsuspendRedraw(final int n) {
        final UpdateManager updateManager = this.ctx.getUpdateManager();
        return updateManager != null && updateManager.releaseRedrawSuspension(n);
    }
    
    public void unsuspendRedrawAll() {
        final UpdateManager updateManager = this.ctx.getUpdateManager();
        if (updateManager != null) {
            updateManager.releaseAllRedrawSuspension();
        }
    }
    
    public void forceRedraw() {
        final UpdateManager updateManager = this.ctx.getUpdateManager();
        if (updateManager != null) {
            updateManager.forceRepaint();
        }
    }
    
    public void pauseAnimations() {
        this.ctx.getAnimationEngine().pause();
    }
    
    public void unpauseAnimations() {
        this.ctx.getAnimationEngine().unpause();
    }
    
    public boolean animationsPaused() {
        return this.ctx.getAnimationEngine().isPaused();
    }
    
    public float getCurrentTime() {
        return this.ctx.getAnimationEngine().getCurrentTime();
    }
    
    public void setCurrentTime(final float currentTime) {
        this.ctx.getAnimationEngine().setCurrentTime(currentTime);
    }
    
    public static class SVGSVGElementViewport implements Viewport
    {
        private float width;
        private float height;
        
        public SVGSVGElementViewport(final float width, final float height) {
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
