// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.bridge.svg12;

import org.apache.batik.css.engine.value.svg.ICCColor;
import java.awt.Color;
import org.apache.batik.css.engine.value.Value;
import org.apache.batik.bridge.BridgeException;
import org.apache.batik.dom.svg.SVGOMDocument;
import org.apache.batik.dom.util.XLinkSupport;
import org.apache.batik.bridge.PaintServer;
import org.apache.batik.css.engine.CSSStylableElement;
import org.apache.batik.bridge.CSSUtilities;
import org.apache.batik.util.ParsedURL;
import java.util.HashMap;
import java.awt.Paint;
import org.apache.batik.gvt.GraphicsNode;
import org.w3c.dom.Element;
import org.apache.batik.bridge.BridgeContext;
import org.apache.batik.bridge.PaintBridge;
import org.apache.batik.bridge.AnimatableGenericSVGBridge;

public class SVGSolidColorElementBridge extends AnimatableGenericSVGBridge implements PaintBridge
{
    public String getNamespaceURI() {
        return "http://www.w3.org/2000/svg";
    }
    
    public String getLocalName() {
        return "solidColor";
    }
    
    public Paint createPaint(final BridgeContext bridgeContext, final Element element, final Element element2, final GraphicsNode graphicsNode, float opacity) {
        opacity = extractOpacity(element, opacity, bridgeContext);
        return extractColor(element, opacity, bridgeContext);
    }
    
    protected static float extractOpacity(Element referencedElement, final float n, final BridgeContext bridgeContext) {
        final HashMap hashMap = new HashMap<ParsedURL, ParsedURL>();
        final int propertyIndex = CSSUtilities.getCSSEngine(referencedElement).getPropertyIndex("solid-opacity");
        while (true) {
            final Value computedStyle = CSSUtilities.getComputedStyle(referencedElement, propertyIndex);
            if (!((CSSStylableElement)referencedElement).getComputedStyleMap(null).isNullCascaded(propertyIndex)) {
                return n * PaintServer.convertOpacity(computedStyle);
            }
            final String xLinkHref = XLinkSupport.getXLinkHref(referencedElement);
            if (xLinkHref.length() == 0) {
                return n;
            }
            final ParsedURL parsedURL = new ParsedURL(((SVGOMDocument)referencedElement.getOwnerDocument()).getURL(), xLinkHref);
            if (hashMap.containsKey(parsedURL)) {
                throw new BridgeException(bridgeContext, referencedElement, "xlink.href.circularDependencies", new Object[] { xLinkHref });
            }
            hashMap.put(parsedURL, parsedURL);
            referencedElement = bridgeContext.getReferencedElement(referencedElement, xLinkHref);
        }
    }
    
    protected static Color extractColor(Element referencedElement, final float a, final BridgeContext bridgeContext) {
        final HashMap hashMap = new HashMap<ParsedURL, ParsedURL>();
        final int propertyIndex = CSSUtilities.getCSSEngine(referencedElement).getPropertyIndex("solid-color");
        while (true) {
            final Value computedStyle = CSSUtilities.getComputedStyle(referencedElement, propertyIndex);
            if (!((CSSStylableElement)referencedElement).getComputedStyleMap(null).isNullCascaded(propertyIndex)) {
                if (computedStyle.getCssValueType() == 1) {
                    return PaintServer.convertColor(computedStyle, a);
                }
                return PaintServer.convertRGBICCColor(referencedElement, computedStyle.item(0), (ICCColor)computedStyle.item(1), a, bridgeContext);
            }
            else {
                final String xLinkHref = XLinkSupport.getXLinkHref(referencedElement);
                if (xLinkHref.length() == 0) {
                    return new Color(0.0f, 0.0f, 0.0f, a);
                }
                final ParsedURL parsedURL = new ParsedURL(((SVGOMDocument)referencedElement.getOwnerDocument()).getURL(), xLinkHref);
                if (hashMap.containsKey(parsedURL)) {
                    throw new BridgeException(bridgeContext, referencedElement, "xlink.href.circularDependencies", new Object[] { xLinkHref });
                }
                hashMap.put(parsedURL, parsedURL);
                referencedElement = bridgeContext.getReferencedElement(referencedElement, xLinkHref);
            }
        }
    }
}
