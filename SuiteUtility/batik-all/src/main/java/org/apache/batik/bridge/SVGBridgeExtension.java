// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.bridge;

import org.w3c.dom.Element;
import java.util.Collections;
import java.util.Iterator;

public class SVGBridgeExtension implements BridgeExtension
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
        return "The required SVG 1.0 tags";
    }
    
    public void registerTags(final BridgeContext bridgeContext) {
        bridgeContext.putBridge(new SVGAElementBridge());
        bridgeContext.putBridge(new SVGAltGlyphElementBridge());
        bridgeContext.putBridge(new SVGCircleElementBridge());
        bridgeContext.putBridge(new SVGClipPathElementBridge());
        bridgeContext.putBridge(new SVGColorProfileElementBridge());
        bridgeContext.putBridge(new SVGDescElementBridge());
        bridgeContext.putBridge(new SVGEllipseElementBridge());
        bridgeContext.putBridge(new SVGFeBlendElementBridge());
        bridgeContext.putBridge(new SVGFeColorMatrixElementBridge());
        bridgeContext.putBridge(new SVGFeComponentTransferElementBridge());
        bridgeContext.putBridge(new SVGFeCompositeElementBridge());
        bridgeContext.putBridge(new SVGFeComponentTransferElementBridge.SVGFeFuncAElementBridge());
        bridgeContext.putBridge(new SVGFeComponentTransferElementBridge.SVGFeFuncRElementBridge());
        bridgeContext.putBridge(new SVGFeComponentTransferElementBridge.SVGFeFuncGElementBridge());
        bridgeContext.putBridge(new SVGFeComponentTransferElementBridge.SVGFeFuncBElementBridge());
        bridgeContext.putBridge(new SVGFeConvolveMatrixElementBridge());
        bridgeContext.putBridge(new SVGFeDiffuseLightingElementBridge());
        bridgeContext.putBridge(new SVGFeDisplacementMapElementBridge());
        bridgeContext.putBridge(new AbstractSVGLightingElementBridge.SVGFeDistantLightElementBridge());
        bridgeContext.putBridge(new SVGFeFloodElementBridge());
        bridgeContext.putBridge(new SVGFeGaussianBlurElementBridge());
        bridgeContext.putBridge(new SVGFeImageElementBridge());
        bridgeContext.putBridge(new SVGFeMergeElementBridge());
        bridgeContext.putBridge(new SVGFeMergeElementBridge.SVGFeMergeNodeElementBridge());
        bridgeContext.putBridge(new SVGFeMorphologyElementBridge());
        bridgeContext.putBridge(new SVGFeOffsetElementBridge());
        bridgeContext.putBridge(new AbstractSVGLightingElementBridge.SVGFePointLightElementBridge());
        bridgeContext.putBridge(new SVGFeSpecularLightingElementBridge());
        bridgeContext.putBridge(new AbstractSVGLightingElementBridge.SVGFeSpotLightElementBridge());
        bridgeContext.putBridge(new SVGFeTileElementBridge());
        bridgeContext.putBridge(new SVGFeTurbulenceElementBridge());
        bridgeContext.putBridge(new SVGFontElementBridge());
        bridgeContext.putBridge(new SVGFontFaceElementBridge());
        bridgeContext.putBridge(new SVGFilterElementBridge());
        bridgeContext.putBridge(new SVGGElementBridge());
        bridgeContext.putBridge(new SVGGlyphElementBridge());
        bridgeContext.putBridge(new SVGHKernElementBridge());
        bridgeContext.putBridge(new SVGImageElementBridge());
        bridgeContext.putBridge(new SVGLineElementBridge());
        bridgeContext.putBridge(new SVGLinearGradientElementBridge());
        bridgeContext.putBridge(new SVGMarkerElementBridge());
        bridgeContext.putBridge(new SVGMaskElementBridge());
        bridgeContext.putBridge(new SVGMissingGlyphElementBridge());
        bridgeContext.putBridge(new SVGPathElementBridge());
        bridgeContext.putBridge(new SVGPatternElementBridge());
        bridgeContext.putBridge(new SVGPolylineElementBridge());
        bridgeContext.putBridge(new SVGPolygonElementBridge());
        bridgeContext.putBridge(new SVGRadialGradientElementBridge());
        bridgeContext.putBridge(new SVGRectElementBridge());
        bridgeContext.putBridge(new AbstractSVGGradientElementBridge.SVGStopElementBridge());
        bridgeContext.putBridge(new SVGSVGElementBridge());
        bridgeContext.putBridge(new SVGSwitchElementBridge());
        bridgeContext.putBridge(new SVGTextElementBridge());
        bridgeContext.putBridge(new SVGTextPathElementBridge());
        bridgeContext.putBridge(new SVGTitleElementBridge());
        bridgeContext.putBridge(new SVGUseElementBridge());
        bridgeContext.putBridge(new SVGVKernElementBridge());
        bridgeContext.putBridge(new SVGSetElementBridge());
        bridgeContext.putBridge(new SVGAnimateElementBridge());
        bridgeContext.putBridge(new SVGAnimateColorElementBridge());
        bridgeContext.putBridge(new SVGAnimateTransformElementBridge());
        bridgeContext.putBridge(new SVGAnimateMotionElementBridge());
    }
    
    public boolean isDynamicElement(final Element element) {
        if (!"http://www.w3.org/2000/svg".equals(element.getNamespaceURI())) {
            return false;
        }
        final String localName = element.getLocalName();
        return localName.equals("script") || localName.startsWith("animate") || localName.equals("set");
    }
}
