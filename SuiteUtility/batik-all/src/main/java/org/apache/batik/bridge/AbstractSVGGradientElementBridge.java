// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.bridge;

import org.w3c.dom.Node;
import org.apache.batik.dom.AbstractNode;
import org.apache.batik.dom.util.XLinkSupport;
import org.apache.batik.util.ParsedURL;
import java.util.LinkedList;
import java.util.Iterator;
import java.util.List;
import java.awt.geom.AffineTransform;
import org.apache.batik.ext.awt.MultipleGradientPaint;
import java.awt.Color;
import java.awt.Paint;
import org.apache.batik.gvt.GraphicsNode;
import org.w3c.dom.Element;

public abstract class AbstractSVGGradientElementBridge extends AnimatableGenericSVGBridge implements PaintBridge, ErrorConstants
{
    protected AbstractSVGGradientElementBridge() {
    }
    
    public Paint createPaint(final BridgeContext bridgeContext, final Element element, final Element element2, final GraphicsNode graphicsNode, final float n) {
        final List stop = extractStop(element, n, bridgeContext);
        if (stop == null) {
            return null;
        }
        final int size = stop.size();
        if (size == 1) {
            return stop.get(0).color;
        }
        final float[] array = new float[size];
        final Color[] array2 = new Color[size];
        final Iterator<Stop> iterator = stop.iterator();
        int n2 = 0;
        while (iterator.hasNext()) {
            final Stop stop2 = iterator.next();
            array[n2] = stop2.offset;
            array2[n2] = stop2.color;
            ++n2;
        }
        MultipleGradientPaint.CycleMethodEnum cycleMethodEnum = MultipleGradientPaint.NO_CYCLE;
        final String chainableAttributeNS = SVGUtilities.getChainableAttributeNS(element, null, "spreadMethod", bridgeContext);
        if (chainableAttributeNS.length() != 0) {
            cycleMethodEnum = convertSpreadMethod(element, chainableAttributeNS, bridgeContext);
        }
        final MultipleGradientPaint.ColorSpaceEnum convertColorInterpolation = CSSUtilities.convertColorInterpolation(element);
        final String chainableAttributeNS2 = SVGUtilities.getChainableAttributeNS(element, null, "gradientTransform", bridgeContext);
        AffineTransform convertTransform;
        if (chainableAttributeNS2.length() != 0) {
            convertTransform = SVGUtilities.convertTransform(element, "gradientTransform", chainableAttributeNS2, bridgeContext);
        }
        else {
            convertTransform = new AffineTransform();
        }
        return this.buildGradient(element, element2, graphicsNode, cycleMethodEnum, convertColorInterpolation, convertTransform, array2, array, bridgeContext);
    }
    
    protected abstract Paint buildGradient(final Element p0, final Element p1, final GraphicsNode p2, final MultipleGradientPaint.CycleMethodEnum p3, final MultipleGradientPaint.ColorSpaceEnum p4, final AffineTransform p5, final Color[] p6, final float[] p7, final BridgeContext p8);
    
    protected static MultipleGradientPaint.CycleMethodEnum convertSpreadMethod(final Element element, final String anObject, final BridgeContext bridgeContext) {
        if ("repeat".equals(anObject)) {
            return MultipleGradientPaint.REPEAT;
        }
        if ("reflect".equals(anObject)) {
            return MultipleGradientPaint.REFLECT;
        }
        if ("pad".equals(anObject)) {
            return MultipleGradientPaint.NO_CYCLE;
        }
        throw new BridgeException(bridgeContext, element, "attribute.malformed", new Object[] { "spreadMethod", anObject });
    }
    
    protected static List extractStop(Element referencedElement, final float n, final BridgeContext bridgeContext) {
        final LinkedList<ParsedURL> list = new LinkedList<ParsedURL>();
        while (true) {
            final List localStop = extractLocalStop(referencedElement, n, bridgeContext);
            if (localStop != null) {
                return localStop;
            }
            final String xLinkHref = XLinkSupport.getXLinkHref(referencedElement);
            if (xLinkHref.length() == 0) {
                return null;
            }
            final ParsedURL parsedURL = new ParsedURL(((AbstractNode)referencedElement).getBaseURI(), xLinkHref);
            if (contains(list, parsedURL)) {
                throw new BridgeException(bridgeContext, referencedElement, "xlink.href.circularDependencies", new Object[] { xLinkHref });
            }
            list.add(parsedURL);
            referencedElement = bridgeContext.getReferencedElement(referencedElement, xLinkHref);
        }
    }
    
    protected static List extractLocalStop(final Element element, final float n, final BridgeContext bridgeContext) {
        LinkedList<Stop> list = null;
        Stop stop = null;
        for (Node node = element.getFirstChild(); node != null; node = node.getNextSibling()) {
            if (node.getNodeType() == 1) {
                final Element element2 = (Element)node;
                final Bridge bridge = bridgeContext.getBridge(element2);
                if (bridge != null) {
                    if (bridge instanceof SVGStopElementBridge) {
                        final Stop stop2 = ((SVGStopElementBridge)bridge).createStop(bridgeContext, element, element2, n);
                        if (list == null) {
                            list = new LinkedList<Stop>();
                        }
                        if (stop != null && stop2.offset < stop.offset) {
                            stop2.offset = stop.offset;
                        }
                        list.add(stop2);
                        stop = stop2;
                    }
                }
            }
        }
        return list;
    }
    
    private static boolean contains(final List list, final ParsedURL parsedURL) {
        final Iterator<Object> iterator = list.iterator();
        while (iterator.hasNext()) {
            if (parsedURL.equals(iterator.next())) {
                return true;
            }
        }
        return false;
    }
    
    public static class SVGStopElementBridge extends AnimatableGenericSVGBridge implements Bridge
    {
        public String getLocalName() {
            return "stop";
        }
        
        public Stop createStop(final BridgeContext bridgeContext, final Element element, final Element element2, final float n) {
            final String attributeNS = element2.getAttributeNS(null, "offset");
            if (attributeNS.length() == 0) {
                throw new BridgeException(bridgeContext, element2, "attribute.missing", new Object[] { "offset" });
            }
            float convertRatio;
            try {
                convertRatio = SVGUtilities.convertRatio(attributeNS);
            }
            catch (NumberFormatException ex) {
                throw new BridgeException(bridgeContext, element2, ex, "attribute.malformed", new Object[] { "offset", attributeNS, ex });
            }
            return new Stop(CSSUtilities.convertStopColor(element2, n, bridgeContext), convertRatio);
        }
    }
    
    public static class Stop
    {
        public Color color;
        public float offset;
        
        public Stop(final Color color, final float offset) {
            this.color = color;
            this.offset = offset;
        }
    }
}
