// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.bridge.svg12;

import org.w3c.dom.Node;
import org.w3c.dom.events.MutationEvent;
import org.apache.batik.gvt.CompositeGraphicsNode;
import org.apache.batik.bridge.SVGUtilities;
import org.apache.batik.gvt.GraphicsNode;
import org.w3c.dom.Element;
import org.apache.batik.bridge.BridgeContext;
import org.apache.batik.bridge.Bridge;
import org.apache.batik.bridge.AbstractGraphicsNodeBridge;

public class XBLShadowTreeElementBridge extends AbstractGraphicsNodeBridge
{
    public String getLocalName() {
        return "shadowTree";
    }
    
    public String getNamespaceURI() {
        return "http://www.w3.org/2004/xbl";
    }
    
    public Bridge getInstance() {
        return new XBLShadowTreeElementBridge();
    }
    
    public GraphicsNode createGraphicsNode(final BridgeContext bridgeContext, final Element element) {
        if (!SVGUtilities.matchUserAgent(element, bridgeContext.getUserAgent())) {
            return null;
        }
        final CompositeGraphicsNode compositeGraphicsNode = new CompositeGraphicsNode();
        this.associateSVGContext(bridgeContext, element, compositeGraphicsNode);
        return compositeGraphicsNode;
    }
    
    protected GraphicsNode instantiateGraphicsNode() {
        return null;
    }
    
    public void buildGraphicsNode(final BridgeContext bridgeContext, final Element element, final GraphicsNode graphicsNode) {
        this.initializeDynamicSupport(bridgeContext, element, graphicsNode);
    }
    
    public boolean getDisplay(final Element element) {
        return true;
    }
    
    public boolean isComposite() {
        return true;
    }
    
    public void handleDOMNodeInsertedEvent(final MutationEvent mutationEvent) {
        if (mutationEvent.getTarget() instanceof Element) {
            this.handleElementAdded((CompositeGraphicsNode)this.node, this.e, (Element)mutationEvent.getTarget());
        }
    }
    
    public void handleElementAdded(final CompositeGraphicsNode compositeGraphicsNode, final Node node, final Element element) {
        final GraphicsNode build = this.ctx.getGVTBuilder().build(this.ctx, element);
        if (build == null) {
            return;
        }
        int index = -1;
        for (Node node2 = element.getPreviousSibling(); node2 != null; node2 = node2.getPreviousSibling()) {
            if (node2.getNodeType() == 1) {
                GraphicsNode graphicsNode;
                for (graphicsNode = this.ctx.getGraphicsNode(node2); graphicsNode != null && graphicsNode.getParent() != compositeGraphicsNode; graphicsNode = graphicsNode.getParent()) {}
                if (graphicsNode != null) {
                    index = compositeGraphicsNode.indexOf(graphicsNode);
                    if (index != -1) {
                        break;
                    }
                }
            }
        }
        ++index;
        compositeGraphicsNode.add(index, build);
    }
}
