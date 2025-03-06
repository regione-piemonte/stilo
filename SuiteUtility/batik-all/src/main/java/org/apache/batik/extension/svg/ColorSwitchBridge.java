// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.extension.svg;

import org.apache.batik.bridge.Bridge;
import org.w3c.dom.Node;
import java.awt.Color;
import org.apache.batik.bridge.SVGUtilities;
import java.awt.Paint;
import org.apache.batik.gvt.GraphicsNode;
import org.w3c.dom.Element;
import org.apache.batik.bridge.BridgeContext;
import org.apache.batik.bridge.PaintBridge;
import org.apache.batik.bridge.AbstractSVGBridge;

public class ColorSwitchBridge extends AbstractSVGBridge implements PaintBridge, BatikExtConstants
{
    public String getNamespaceURI() {
        return "http://xml.apache.org/batik/ext";
    }
    
    public String getLocalName() {
        return "colorSwitch";
    }
    
    public Paint createPaint(final BridgeContext bridgeContext, final Element element, final Element element2, final GraphicsNode graphicsNode, final float n) {
        Element element3 = null;
        for (Node node = element.getFirstChild(); node != null; node = node.getNextSibling()) {
            if (node.getNodeType() == 1) {
                final Element element4 = (Element)node;
                if (SVGUtilities.matchUserAgent(element4, bridgeContext.getUserAgent())) {
                    element3 = element4;
                    break;
                }
            }
        }
        if (element3 == null) {
            return Color.black;
        }
        final Bridge bridge = bridgeContext.getBridge(element3);
        if (bridge == null || !(bridge instanceof PaintBridge)) {
            return Color.black;
        }
        return ((PaintBridge)bridge).createPaint(bridgeContext, element3, element2, graphicsNode, n);
    }
}
