// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.bridge;

import org.w3c.dom.Node;
import org.w3c.dom.events.MutationEvent;
import java.awt.geom.Rectangle2D;
import java.awt.RenderingHints;
import org.apache.batik.gvt.CompositeGraphicsNode;
import org.apache.batik.gvt.GraphicsNode;
import org.w3c.dom.Element;

public class SVGGElementBridge extends AbstractGraphicsNodeBridge
{
    public String getLocalName() {
        return "g";
    }
    
    public Bridge getInstance() {
        return new SVGGElementBridge();
    }
    
    public GraphicsNode createGraphicsNode(final BridgeContext bridgeContext, final Element element) {
        final CompositeGraphicsNode compositeGraphicsNode = (CompositeGraphicsNode)super.createGraphicsNode(bridgeContext, element);
        if (compositeGraphicsNode == null) {
            return null;
        }
        this.associateSVGContext(bridgeContext, element, compositeGraphicsNode);
        final RenderingHints convertColorRendering = CSSUtilities.convertColorRendering(element, null);
        if (convertColorRendering != null) {
            compositeGraphicsNode.setRenderingHints(convertColorRendering);
        }
        final Rectangle2D convertEnableBackground = CSSUtilities.convertEnableBackground(element);
        if (convertEnableBackground != null) {
            compositeGraphicsNode.setBackgroundEnable(convertEnableBackground);
        }
        return compositeGraphicsNode;
    }
    
    protected GraphicsNode instantiateGraphicsNode() {
        return new CompositeGraphicsNode();
    }
    
    public boolean isComposite() {
        return true;
    }
    
    public void handleDOMNodeInsertedEvent(final MutationEvent mutationEvent) {
        if (mutationEvent.getTarget() instanceof Element) {
            this.handleElementAdded((CompositeGraphicsNode)this.node, this.e, (Element)mutationEvent.getTarget());
        }
        else {
            super.handleDOMNodeInsertedEvent(mutationEvent);
        }
    }
    
    protected void handleElementAdded(final CompositeGraphicsNode compositeGraphicsNode, final Node node, final Element element) {
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
