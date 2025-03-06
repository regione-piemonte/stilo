// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.bridge.svg12;

import org.w3c.dom.Element;
import org.apache.batik.bridge.Bridge;
import org.apache.batik.bridge.BridgeContext;
import java.util.Collections;
import java.util.Iterator;
import org.apache.batik.bridge.SVGBridgeExtension;

public class SVG12BridgeExtension extends SVGBridgeExtension
{
    public float getPriority() {
        return 0.0f;
    }
    
    public Iterator getImplementedExtensions() {
        return Collections.EMPTY_LIST.iterator();
    }
    
    public String getAuthor() {
        return "The Apache Batik Team.";
    }
    
    public String getContactAddress() {
        return "batik-dev@xmlgraphics.apache.org";
    }
    
    public String getURL() {
        return "http://xml.apache.org/batik";
    }
    
    public String getDescription() {
        return "The required SVG 1.2 tags";
    }
    
    public void registerTags(final BridgeContext bridgeContext) {
        super.registerTags(bridgeContext);
        bridgeContext.putBridge(new SVGFlowRootElementBridge());
        bridgeContext.putBridge(new SVGMultiImageElementBridge());
        bridgeContext.putBridge(new SVGSolidColorElementBridge());
        bridgeContext.putBridge(new SVG12TextElementBridge());
        bridgeContext.putBridge(new XBLShadowTreeElementBridge());
        bridgeContext.putBridge(new XBLContentElementBridge());
        bridgeContext.setDefaultBridge(new BindableElementBridge());
        bridgeContext.putReservedNamespaceURI(null);
        bridgeContext.putReservedNamespaceURI("http://www.w3.org/2000/svg");
        bridgeContext.putReservedNamespaceURI("http://www.w3.org/2004/xbl");
    }
    
    public boolean isDynamicElement(final Element element) {
        final String namespaceURI = element.getNamespaceURI();
        if ("http://www.w3.org/2004/xbl".equals(namespaceURI)) {
            return true;
        }
        if (!"http://www.w3.org/2000/svg".equals(namespaceURI)) {
            return false;
        }
        final String localName = element.getLocalName();
        return localName.equals("script") || localName.equals("handler") || localName.startsWith("animate") || localName.equals("set");
    }
}
