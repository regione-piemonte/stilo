// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.bridge.svg12;

import org.w3c.dom.events.MutationEvent;
import org.apache.batik.bridge.GVTBuilder;
import org.apache.batik.bridge.ScriptingEnvironment;
import org.apache.batik.bridge.UpdateManager;
import org.w3c.dom.Node;
import org.apache.batik.dom.svg12.BindableElement;
import org.apache.batik.gvt.CompositeGraphicsNode;
import org.apache.batik.bridge.SVGUtilities;
import org.apache.batik.gvt.GraphicsNode;
import org.w3c.dom.Element;
import org.apache.batik.bridge.BridgeContext;
import org.apache.batik.bridge.Bridge;
import org.apache.batik.bridge.AbstractGraphicsNodeBridge;

public class BindableElementBridge extends AbstractGraphicsNodeBridge implements SVG12BridgeUpdateHandler
{
    public String getNamespaceURI() {
        return "*";
    }
    
    public String getLocalName() {
        return "*";
    }
    
    public Bridge getInstance() {
        return new BindableElementBridge();
    }
    
    public GraphicsNode createGraphicsNode(final BridgeContext bridgeContext, final Element element) {
        if (!SVGUtilities.matchUserAgent(element, bridgeContext.getUserAgent())) {
            return null;
        }
        return this.buildCompositeGraphicsNode(bridgeContext, element, null);
    }
    
    public CompositeGraphicsNode buildCompositeGraphicsNode(final BridgeContext bridgeContext, final Element element, CompositeGraphicsNode compositeGraphicsNode) {
        final Element xblShadowTree = ((BindableElement)element).getXblShadowTree();
        final UpdateManager updateManager = bridgeContext.getUpdateManager();
        final ScriptingEnvironment scriptingEnvironment = (updateManager == null) ? null : updateManager.getScriptingEnvironment();
        if (scriptingEnvironment != null && xblShadowTree != null) {
            scriptingEnvironment.addScriptingListeners(xblShadowTree);
        }
        if (compositeGraphicsNode == null) {
            compositeGraphicsNode = new CompositeGraphicsNode();
            this.associateSVGContext(bridgeContext, element, compositeGraphicsNode);
        }
        else {
            for (int size = compositeGraphicsNode.size(), i = 0; i < size; ++i) {
                compositeGraphicsNode.remove(0);
            }
        }
        final GVTBuilder gvtBuilder = bridgeContext.getGVTBuilder();
        if (xblShadowTree != null) {
            final GraphicsNode build = gvtBuilder.build(bridgeContext, xblShadowTree);
            if (build != null) {
                compositeGraphicsNode.add(build);
            }
        }
        else {
            for (Node node = element.getFirstChild(); node != null; node = node.getNextSibling()) {
                if (node.getNodeType() == 1) {
                    final GraphicsNode build2 = gvtBuilder.build(bridgeContext, (Element)node);
                    if (build2 != null) {
                        compositeGraphicsNode.add(build2);
                    }
                }
            }
        }
        return compositeGraphicsNode;
    }
    
    public void dispose() {
        final BindableElement bindableElement = (BindableElement)this.e;
        if (bindableElement != null && bindableElement.getCSSFirstChild() != null) {
            this.disposeTree(bindableElement.getCSSFirstChild());
        }
        super.dispose();
    }
    
    protected GraphicsNode instantiateGraphicsNode() {
        return null;
    }
    
    public boolean isComposite() {
        return false;
    }
    
    public void buildGraphicsNode(final BridgeContext bridgeContext, final Element element, final GraphicsNode graphicsNode) {
        this.initializeDynamicSupport(bridgeContext, element, graphicsNode);
    }
    
    public void handleDOMNodeInsertedEvent(final MutationEvent mutationEvent) {
        if (((BindableElement)this.e).getXblShadowTree() == null && mutationEvent.getTarget() instanceof Element) {
            this.handleElementAdded((CompositeGraphicsNode)this.node, this.e, (Element)mutationEvent.getTarget());
        }
    }
    
    public void handleBindingEvent(final Element element, final Element element2) {
        final CompositeGraphicsNode parent = this.node.getParent();
        parent.remove(this.node);
        this.disposeTree(this.e);
        this.handleElementAdded(parent, this.e.getParentNode(), this.e);
    }
    
    public void handleContentSelectionChangedEvent(final ContentSelectionChangedEvent contentSelectionChangedEvent) {
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
