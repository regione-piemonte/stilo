// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.bridge;

import org.apache.batik.util.HaltingThread;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import org.apache.batik.gvt.RootGraphicsNode;
import org.apache.batik.gvt.CompositeGraphicsNode;
import org.apache.batik.gvt.GraphicsNode;
import org.w3c.dom.Document;
import org.apache.batik.util.SVGConstants;

public class GVTBuilder implements SVGConstants
{
    public GraphicsNode build(final BridgeContext bridgeContext, final Document document) {
        bridgeContext.setDocument(document);
        bridgeContext.initializeDocument(document);
        bridgeContext.setGVTBuilder(this);
        final DocumentBridge documentBridge = bridgeContext.getDocumentBridge();
        RootGraphicsNode graphicsNode = null;
        try {
            graphicsNode = documentBridge.createGraphicsNode(bridgeContext, document);
            final Element documentElement = document.getDocumentElement();
            final Bridge bridge = bridgeContext.getBridge(documentElement);
            if (bridge == null || !(bridge instanceof GraphicsNodeBridge)) {
                return null;
            }
            final GraphicsNodeBridge graphicsNodeBridge = (GraphicsNodeBridge)bridge;
            final GraphicsNode graphicsNode2 = graphicsNodeBridge.createGraphicsNode(bridgeContext, documentElement);
            if (graphicsNode2 == null) {
                return null;
            }
            graphicsNode.getChildren().add(graphicsNode2);
            this.buildComposite(bridgeContext, documentElement, (CompositeGraphicsNode)graphicsNode2);
            graphicsNodeBridge.buildGraphicsNode(bridgeContext, documentElement, graphicsNode2);
            documentBridge.buildGraphicsNode(bridgeContext, document, graphicsNode);
        }
        catch (BridgeException ex) {
            ex.setGraphicsNode(graphicsNode);
            throw ex;
        }
        if (bridgeContext.isInteractive()) {
            bridgeContext.addUIEventListeners(document);
            bridgeContext.addGVTListener(document);
        }
        if (bridgeContext.isDynamic()) {
            bridgeContext.addDOMListeners();
        }
        return graphicsNode;
    }
    
    public GraphicsNode build(final BridgeContext bridgeContext, final Element element) {
        final Bridge bridge = bridgeContext.getBridge(element);
        if (bridge instanceof GenericBridge) {
            ((GenericBridge)bridge).handleElement(bridgeContext, element);
            this.handleGenericBridges(bridgeContext, element);
            return null;
        }
        if (bridge == null || !(bridge instanceof GraphicsNodeBridge)) {
            this.handleGenericBridges(bridgeContext, element);
            return null;
        }
        final GraphicsNodeBridge graphicsNodeBridge = (GraphicsNodeBridge)bridge;
        if (!graphicsNodeBridge.getDisplay(element)) {
            this.handleGenericBridges(bridgeContext, element);
            return null;
        }
        final GraphicsNode graphicsNode = graphicsNodeBridge.createGraphicsNode(bridgeContext, element);
        if (graphicsNode != null) {
            if (graphicsNodeBridge.isComposite()) {
                this.buildComposite(bridgeContext, element, (CompositeGraphicsNode)graphicsNode);
            }
            else {
                this.handleGenericBridges(bridgeContext, element);
            }
            graphicsNodeBridge.buildGraphicsNode(bridgeContext, element, graphicsNode);
        }
        if (bridgeContext.isDynamic()) {}
        return graphicsNode;
    }
    
    protected void buildComposite(final BridgeContext bridgeContext, final Element element, final CompositeGraphicsNode compositeGraphicsNode) {
        for (Node node = element.getFirstChild(); node != null; node = node.getNextSibling()) {
            if (node.getNodeType() == 1) {
                this.buildGraphicsNode(bridgeContext, (Element)node, compositeGraphicsNode);
            }
        }
    }
    
    protected void buildGraphicsNode(final BridgeContext bridgeContext, final Element element, final CompositeGraphicsNode compositeGraphicsNode) {
        if (HaltingThread.hasBeenHalted()) {
            throw new InterruptedBridgeException();
        }
        final Bridge bridge = bridgeContext.getBridge(element);
        if (bridge instanceof GenericBridge) {
            ((GenericBridge)bridge).handleElement(bridgeContext, element);
            this.handleGenericBridges(bridgeContext, element);
            return;
        }
        if (bridge == null || !(bridge instanceof GraphicsNodeBridge)) {
            this.handleGenericBridges(bridgeContext, element);
            return;
        }
        if (!CSSUtilities.convertDisplay(element)) {
            this.handleGenericBridges(bridgeContext, element);
            return;
        }
        final GraphicsNodeBridge graphicsNodeBridge = (GraphicsNodeBridge)bridge;
        try {
            final GraphicsNode graphicsNode = graphicsNodeBridge.createGraphicsNode(bridgeContext, element);
            if (graphicsNode != null) {
                compositeGraphicsNode.getChildren().add(graphicsNode);
                if (graphicsNodeBridge.isComposite()) {
                    this.buildComposite(bridgeContext, element, (CompositeGraphicsNode)graphicsNode);
                }
                else {
                    this.handleGenericBridges(bridgeContext, element);
                }
                graphicsNodeBridge.buildGraphicsNode(bridgeContext, element, graphicsNode);
            }
            else {
                this.handleGenericBridges(bridgeContext, element);
            }
        }
        catch (BridgeException ex) {
            final GraphicsNode graphicsNode2 = ex.getGraphicsNode();
            if (graphicsNode2 != null) {
                compositeGraphicsNode.getChildren().add(graphicsNode2);
                graphicsNodeBridge.buildGraphicsNode(bridgeContext, element, graphicsNode2);
                ex.setGraphicsNode(null);
            }
            throw ex;
        }
    }
    
    protected void handleGenericBridges(final BridgeContext bridgeContext, final Element element) {
        for (Node node = element.getFirstChild(); node != null; node = node.getNextSibling()) {
            if (node instanceof Element) {
                final Element element2 = (Element)node;
                final Bridge bridge = bridgeContext.getBridge(element2);
                if (bridge instanceof GenericBridge) {
                    ((GenericBridge)bridge).handleElement(bridgeContext, element2);
                }
                this.handleGenericBridges(bridgeContext, element2);
            }
        }
    }
}
