// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.bridge.svg12;

import org.w3c.dom.NodeList;
import org.apache.batik.bridge.GVTBuilder;
import org.w3c.dom.Node;
import org.apache.batik.dom.AbstractDocument;
import org.apache.batik.dom.svg12.XBLOMContentElement;
import org.apache.batik.gvt.CompositeGraphicsNode;
import org.apache.batik.gvt.GraphicsNode;
import org.w3c.dom.Element;
import org.apache.batik.bridge.BridgeContext;
import org.apache.batik.bridge.Bridge;
import org.apache.batik.bridge.AbstractGraphicsNodeBridge;

public class XBLContentElementBridge extends AbstractGraphicsNodeBridge
{
    protected ContentChangedListener contentChangedListener;
    protected ContentManager contentManager;
    
    public String getLocalName() {
        return "content";
    }
    
    public String getNamespaceURI() {
        return "http://www.w3.org/2004/xbl";
    }
    
    public Bridge getInstance() {
        return new XBLContentElementBridge();
    }
    
    public GraphicsNode createGraphicsNode(final BridgeContext bridgeContext, final Element element) {
        return this.buildCompositeGraphicsNode(bridgeContext, element, null);
    }
    
    public CompositeGraphicsNode buildCompositeGraphicsNode(final BridgeContext bridgeContext, final Element element, CompositeGraphicsNode compositeGraphicsNode) {
        final XBLOMContentElement xblomContentElement = (XBLOMContentElement)element;
        this.contentManager = ((DefaultXBLManager)((AbstractDocument)element.getOwnerDocument()).getXBLManager()).getContentManager(element);
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
        final NodeList selectedContent = this.contentManager.getSelectedContent(xblomContentElement);
        if (selectedContent != null) {
            for (int j = 0; j < selectedContent.getLength(); ++j) {
                final Node item = selectedContent.item(j);
                if (item.getNodeType() == 1) {
                    final GraphicsNode build = gvtBuilder.build(bridgeContext, (Element)item);
                    if (build != null) {
                        compositeGraphicsNode.add(build);
                    }
                }
            }
        }
        if (bridgeContext.isDynamic() && this.contentChangedListener == null) {
            this.contentChangedListener = new ContentChangedListener();
            this.contentManager.addContentSelectionChangedListener(xblomContentElement, this.contentChangedListener);
        }
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
        return false;
    }
    
    public void dispose() {
        super.dispose();
        if (this.contentChangedListener != null) {
            this.contentManager.removeContentSelectionChangedListener((XBLOMContentElement)this.e, this.contentChangedListener);
        }
    }
    
    protected class ContentChangedListener implements ContentSelectionChangedListener
    {
        public void contentSelectionChanged(final ContentSelectionChangedEvent contentSelectionChangedEvent) {
            XBLContentElementBridge.this.buildCompositeGraphicsNode(XBLContentElementBridge.this.ctx, XBLContentElementBridge.this.e, (CompositeGraphicsNode)XBLContentElementBridge.this.node);
        }
    }
}
