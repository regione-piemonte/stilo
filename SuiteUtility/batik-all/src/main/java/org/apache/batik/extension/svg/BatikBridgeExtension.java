// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.extension.svg;

import org.w3c.dom.Element;
import org.apache.batik.bridge.Bridge;
import org.apache.batik.bridge.BridgeContext;
import java.util.List;
import java.util.Collections;
import java.util.Arrays;
import java.util.Iterator;
import org.apache.batik.bridge.BridgeExtension;

public class BatikBridgeExtension implements BridgeExtension
{
    public float getPriority() {
        return 1.0f;
    }
    
    public Iterator getImplementedExtensions() {
        return Collections.unmodifiableList((List<?>)Arrays.asList("http://xml.apache.org/batik/ext/poly/1.0", "http://xml.apache.org/batik/ext/star/1.0", "http://xml.apache.org/batik/ext/histogramNormalization/1.0", "http://xml.apache.org/batik/ext/colorSwitch/1.0", "http://xml.apache.org/batik/ext/flowText/1.0")).iterator();
    }
    
    public String getAuthor() {
        return "Thomas DeWeese";
    }
    
    public String getContactAddress() {
        return "deweese@apache.org";
    }
    
    public String getURL() {
        return "http://xml.apache.org/batik";
    }
    
    public String getDescription() {
        return "Example extension to standard SVG shape tags";
    }
    
    public void registerTags(final BridgeContext bridgeContext) {
        bridgeContext.putBridge(new BatikRegularPolygonElementBridge());
        bridgeContext.putBridge(new BatikStarElementBridge());
        bridgeContext.putBridge(new BatikHistogramNormalizationElementBridge());
        bridgeContext.putBridge(new BatikFlowTextElementBridge());
        bridgeContext.putBridge(new ColorSwitchBridge());
    }
    
    public boolean isDynamicElement(final Element element) {
        return false;
    }
}
