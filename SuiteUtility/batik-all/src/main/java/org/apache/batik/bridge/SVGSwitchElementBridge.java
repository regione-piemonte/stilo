// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.bridge;

import org.w3c.dom.Node;
import org.apache.batik.gvt.CompositeGraphicsNode;
import org.w3c.dom.svg.SVGTests;
import org.apache.batik.gvt.GraphicsNode;
import org.w3c.dom.Element;

public class SVGSwitchElementBridge extends SVGGElementBridge
{
    protected Element selectedChild;
    
    public String getLocalName() {
        return "switch";
    }
    
    public Bridge getInstance() {
        return new SVGSwitchElementBridge();
    }
    
    public GraphicsNode createGraphicsNode(final BridgeContext bridgeContext, final Element element) {
        Object build = null;
        final GVTBuilder gvtBuilder = bridgeContext.getGVTBuilder();
        this.selectedChild = null;
        for (Node node = element.getFirstChild(); node != null; node = node.getNextSibling()) {
            if (node.getNodeType() == 1) {
                final Element selectedChild = (Element)node;
                if (node instanceof SVGTests && SVGUtilities.matchUserAgent(selectedChild, bridgeContext.getUserAgent())) {
                    this.selectedChild = selectedChild;
                    build = gvtBuilder.build(bridgeContext, selectedChild);
                    break;
                }
            }
        }
        if (build == null) {
            return null;
        }
        final CompositeGraphicsNode compositeGraphicsNode = (CompositeGraphicsNode)super.createGraphicsNode(bridgeContext, element);
        if (compositeGraphicsNode == null) {
            return null;
        }
        compositeGraphicsNode.add(build);
        return compositeGraphicsNode;
    }
    
    public boolean isComposite() {
        return false;
    }
    
    public void dispose() {
        this.selectedChild = null;
        super.dispose();
    }
    
    protected void handleElementAdded(final CompositeGraphicsNode compositeGraphicsNode, final Node node, final Element selectedChild) {
        for (Node node2 = selectedChild.getPreviousSibling(); node2 != null; node2 = node2.getPreviousSibling()) {
            if (node2 == selectedChild) {
                return;
            }
        }
        if (selectedChild instanceof SVGTests && SVGUtilities.matchUserAgent(selectedChild, this.ctx.getUserAgent())) {
            if (this.selectedChild != null) {
                compositeGraphicsNode.remove(0);
                this.disposeTree(this.selectedChild);
            }
            this.selectedChild = selectedChild;
            final GraphicsNode build = this.ctx.getGVTBuilder().build(this.ctx, selectedChild);
            if (build != null) {
                compositeGraphicsNode.add(build);
            }
        }
    }
    
    protected void handleChildElementRemoved(final Element element) {
        final CompositeGraphicsNode compositeGraphicsNode = (CompositeGraphicsNode)this.node;
        if (this.selectedChild == element) {
            compositeGraphicsNode.remove(0);
            this.disposeTree(this.selectedChild);
            this.selectedChild = null;
            Object build = null;
            final GVTBuilder gvtBuilder = this.ctx.getGVTBuilder();
            for (Node node = element.getNextSibling(); node != null; node = node.getNextSibling()) {
                if (node.getNodeType() == 1) {
                    final Element selectedChild = (Element)node;
                    if (node instanceof SVGTests && SVGUtilities.matchUserAgent(selectedChild, this.ctx.getUserAgent())) {
                        build = gvtBuilder.build(this.ctx, selectedChild);
                        this.selectedChild = selectedChild;
                        break;
                    }
                }
            }
            if (build != null) {
                compositeGraphicsNode.add(build);
            }
        }
    }
}
