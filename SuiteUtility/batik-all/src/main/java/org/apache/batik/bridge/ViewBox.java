// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.bridge;

import org.apache.batik.parser.AWTTransformProducer;
import java.util.StringTokenizer;
import org.w3c.dom.svg.SVGRect;
import org.apache.batik.dom.svg.SVGOMAnimatedRect;
import org.w3c.dom.svg.SVGAnimatedRect;
import org.w3c.dom.svg.SVGPreserveAspectRatio;
import org.apache.batik.dom.svg.LiveAttributeException;
import org.w3c.dom.svg.SVGAnimatedPreserveAspectRatio;
import org.w3c.dom.Node;
import org.apache.batik.parser.ParseException;
import org.apache.batik.parser.PreserveAspectRatioHandler;
import org.apache.batik.parser.PreserveAspectRatioParser;
import org.apache.batik.parser.FragmentIdentifierHandler;
import org.apache.batik.parser.FragmentIdentifierParser;
import java.awt.geom.AffineTransform;
import org.w3c.dom.Element;
import org.apache.batik.util.SVGConstants;

public abstract class ViewBox implements SVGConstants, ErrorConstants
{
    protected ViewBox() {
    }
    
    public static AffineTransform getViewTransform(final String s, final Element element, final float n, final float n2, final BridgeContext bridgeContext) {
        if (s == null || s.length() == 0) {
            return getPreserveAspectRatioTransform(element, n, n2, bridgeContext);
        }
        final ViewHandler fragmentIdentifierHandler = new ViewHandler();
        final FragmentIdentifierParser fragmentIdentifierParser = new FragmentIdentifierParser();
        fragmentIdentifierParser.setFragmentIdentifierHandler(fragmentIdentifierHandler);
        fragmentIdentifierParser.parse(s);
        Element element2 = element;
        if (fragmentIdentifierHandler.hasId) {
            element2 = element.getOwnerDocument().getElementById(fragmentIdentifierHandler.id);
        }
        if (element2 == null) {
            throw new BridgeException(bridgeContext, element, "uri.malformed", new Object[] { s });
        }
        if (!element2.getNamespaceURI().equals("http://www.w3.org/2000/svg") || !element2.getLocalName().equals("view")) {
            element2 = getClosestAncestorSVGElement(element);
        }
        float[] array;
        if (fragmentIdentifierHandler.hasViewBox) {
            array = fragmentIdentifierHandler.viewBox;
        }
        else {
            array = parseViewBoxAttribute(element2, element2.getAttributeNS(null, "viewBox"), bridgeContext);
        }
        short n3;
        boolean b;
        if (fragmentIdentifierHandler.hasPreserveAspectRatio) {
            n3 = fragmentIdentifierHandler.align;
            b = fragmentIdentifierHandler.meet;
        }
        else {
            final String attributeNS = element2.getAttributeNS(null, "preserveAspectRatio");
            final PreserveAspectRatioParser preserveAspectRatioParser = new PreserveAspectRatioParser();
            final ViewHandler preserveAspectRatioHandler = new ViewHandler();
            preserveAspectRatioParser.setPreserveAspectRatioHandler(preserveAspectRatioHandler);
            try {
                preserveAspectRatioParser.parse(attributeNS);
            }
            catch (ParseException ex) {
                throw new BridgeException(bridgeContext, element2, ex, "attribute.malformed", new Object[] { "preserveAspectRatio", attributeNS, ex });
            }
            n3 = preserveAspectRatioHandler.align;
            b = preserveAspectRatioHandler.meet;
        }
        final AffineTransform preserveAspectRatioTransform = getPreserveAspectRatioTransform(array, n3, b, n, n2);
        if (fragmentIdentifierHandler.hasTransform) {
            preserveAspectRatioTransform.concatenate(fragmentIdentifierHandler.getAffineTransform());
        }
        return preserveAspectRatioTransform;
    }
    
    private static Element getClosestAncestorSVGElement(final Element element) {
        for (Node parentNode = element; parentNode != null && parentNode.getNodeType() == 1; parentNode = parentNode.getParentNode()) {
            final Element element2 = (Element)parentNode;
            if (element2.getNamespaceURI().equals("http://www.w3.org/2000/svg") && element2.getLocalName().equals("svg")) {
                return element2;
            }
        }
        return null;
    }
    
    public static AffineTransform getPreserveAspectRatioTransform(final Element element, final float n, final float n2) {
        return getPreserveAspectRatioTransform(element, n, n2, null);
    }
    
    public static AffineTransform getPreserveAspectRatioTransform(final Element element, final float n, final float n2, final BridgeContext bridgeContext) {
        return getPreserveAspectRatioTransform(element, element.getAttributeNS(null, "viewBox"), element.getAttributeNS(null, "preserveAspectRatio"), n, n2, bridgeContext);
    }
    
    public static AffineTransform getPreserveAspectRatioTransform(final Element element, final String s, final String s2, final float n, final float n2, final BridgeContext bridgeContext) {
        if (s.length() == 0) {
            return new AffineTransform();
        }
        final float[] viewBoxAttribute = parseViewBoxAttribute(element, s, bridgeContext);
        final PreserveAspectRatioParser preserveAspectRatioParser = new PreserveAspectRatioParser();
        final ViewHandler preserveAspectRatioHandler = new ViewHandler();
        preserveAspectRatioParser.setPreserveAspectRatioHandler(preserveAspectRatioHandler);
        try {
            preserveAspectRatioParser.parse(s2);
        }
        catch (ParseException ex) {
            throw new BridgeException(bridgeContext, element, ex, "attribute.malformed", new Object[] { "preserveAspectRatio", s2, ex });
        }
        return getPreserveAspectRatioTransform(viewBoxAttribute, preserveAspectRatioHandler.align, preserveAspectRatioHandler.meet, n, n2);
    }
    
    public static AffineTransform getPreserveAspectRatioTransform(final Element element, final float[] array, final float n, final float n2, final BridgeContext bridgeContext) {
        final String attributeNS = element.getAttributeNS(null, "preserveAspectRatio");
        final PreserveAspectRatioParser preserveAspectRatioParser = new PreserveAspectRatioParser();
        final ViewHandler preserveAspectRatioHandler = new ViewHandler();
        preserveAspectRatioParser.setPreserveAspectRatioHandler(preserveAspectRatioHandler);
        try {
            preserveAspectRatioParser.parse(attributeNS);
        }
        catch (ParseException ex) {
            throw new BridgeException(bridgeContext, element, ex, "attribute.malformed", new Object[] { "preserveAspectRatio", attributeNS, ex });
        }
        return getPreserveAspectRatioTransform(array, preserveAspectRatioHandler.align, preserveAspectRatioHandler.meet, n, n2);
    }
    
    public static AffineTransform getPreserveAspectRatioTransform(final Element element, final float[] array, final float n, final float n2, final SVGAnimatedPreserveAspectRatio svgAnimatedPreserveAspectRatio, final BridgeContext bridgeContext) {
        try {
            final SVGPreserveAspectRatio animVal = svgAnimatedPreserveAspectRatio.getAnimVal();
            return getPreserveAspectRatioTransform(array, animVal.getAlign(), animVal.getMeetOrSlice() == 1, n, n2);
        }
        catch (LiveAttributeException ex) {
            throw new BridgeException(bridgeContext, ex);
        }
    }
    
    public static AffineTransform getPreserveAspectRatioTransform(final Element element, final SVGAnimatedRect svgAnimatedRect, final SVGAnimatedPreserveAspectRatio svgAnimatedPreserveAspectRatio, final float n, final float n2, final BridgeContext bridgeContext) {
        if (!((SVGOMAnimatedRect)svgAnimatedRect).isSpecified()) {
            return new AffineTransform();
        }
        final SVGRect animVal = svgAnimatedRect.getAnimVal();
        return getPreserveAspectRatioTransform(element, new float[] { animVal.getX(), animVal.getY(), animVal.getWidth(), animVal.getHeight() }, n, n2, svgAnimatedPreserveAspectRatio, bridgeContext);
    }
    
    public static float[] parseViewBoxAttribute(final Element element, final String str, final BridgeContext bridgeContext) {
        if (str.length() == 0) {
            return null;
        }
        int n = 0;
        final float[] array = new float[4];
        final StringTokenizer stringTokenizer = new StringTokenizer(str, " ,");
        try {
            while (n < 4 && stringTokenizer.hasMoreTokens()) {
                array[n] = Float.parseFloat(stringTokenizer.nextToken());
                ++n;
            }
        }
        catch (NumberFormatException ex) {
            throw new BridgeException(bridgeContext, element, ex, "attribute.malformed", new Object[] { "viewBox", str, ex });
        }
        if (n != 4) {
            throw new BridgeException(bridgeContext, element, "attribute.malformed", new Object[] { "viewBox", str });
        }
        if (array[2] < 0.0f || array[3] < 0.0f) {
            throw new BridgeException(bridgeContext, element, "attribute.malformed", new Object[] { "viewBox", str });
        }
        if (array[2] == 0.0f || array[3] == 0.0f) {
            return null;
        }
        return array;
    }
    
    public static AffineTransform getPreserveAspectRatioTransform(final float[] array, final short n, final boolean b, final float n2, final float n3) {
        if (array == null) {
            return new AffineTransform();
        }
        final AffineTransform affineTransform = new AffineTransform();
        final float n4 = array[2] / array[3];
        final float n5 = n2 / n3;
        if (n == 1) {
            affineTransform.scale(n2 / array[2], n3 / array[3]);
            affineTransform.translate(-array[0], -array[1]);
        }
        else if ((n4 < n5 && b) || (n4 >= n5 && !b)) {
            final float n6 = n3 / array[3];
            affineTransform.scale(n6, n6);
            switch (n) {
                case 2:
                case 5:
                case 8: {
                    affineTransform.translate(-array[0], -array[1]);
                    break;
                }
                case 3:
                case 6:
                case 9: {
                    affineTransform.translate(-array[0] - (array[2] - n2 * array[3] / n3) / 2.0f, -array[1]);
                    break;
                }
                default: {
                    affineTransform.translate(-array[0] - (array[2] - n2 * array[3] / n3), -array[1]);
                    break;
                }
            }
        }
        else {
            final float n7 = n2 / array[2];
            affineTransform.scale(n7, n7);
            switch (n) {
                case 2:
                case 3:
                case 4: {
                    affineTransform.translate(-array[0], -array[1]);
                    break;
                }
                case 5:
                case 6:
                case 7: {
                    affineTransform.translate(-array[0], -array[1] - (array[3] - n3 * array[2] / n2) / 2.0f);
                    break;
                }
                default: {
                    affineTransform.translate(-array[0], -array[1] - (array[3] - n3 * array[2] / n2));
                    break;
                }
            }
        }
        return affineTransform;
    }
    
    protected static class ViewHandler extends AWTTransformProducer implements FragmentIdentifierHandler
    {
        public boolean hasTransform;
        public boolean hasId;
        public boolean hasViewBox;
        public boolean hasViewTargetParams;
        public boolean hasZoomAndPanParams;
        public String id;
        public float[] viewBox;
        public String viewTargetParams;
        public boolean isMagnify;
        public boolean hasPreserveAspectRatio;
        public short align;
        public boolean meet;
        
        protected ViewHandler() {
            this.meet = true;
        }
        
        public void endTransformList() throws ParseException {
            super.endTransformList();
            this.hasTransform = true;
        }
        
        public void startFragmentIdentifier() throws ParseException {
        }
        
        public void idReference(final String id) throws ParseException {
            this.id = id;
            this.hasId = true;
        }
        
        public void viewBox(final float n, final float n2, final float n3, final float n4) throws ParseException {
            this.hasViewBox = true;
            (this.viewBox = new float[4])[0] = n;
            this.viewBox[1] = n2;
            this.viewBox[2] = n3;
            this.viewBox[3] = n4;
        }
        
        public void startViewTarget() throws ParseException {
        }
        
        public void viewTarget(final String viewTargetParams) throws ParseException {
            this.viewTargetParams = viewTargetParams;
            this.hasViewTargetParams = true;
        }
        
        public void endViewTarget() throws ParseException {
        }
        
        public void zoomAndPan(final boolean isMagnify) {
            this.isMagnify = isMagnify;
            this.hasZoomAndPanParams = true;
        }
        
        public void endFragmentIdentifier() throws ParseException {
        }
        
        public void startPreserveAspectRatio() throws ParseException {
        }
        
        public void none() throws ParseException {
            this.align = 1;
        }
        
        public void xMaxYMax() throws ParseException {
            this.align = 10;
        }
        
        public void xMaxYMid() throws ParseException {
            this.align = 7;
        }
        
        public void xMaxYMin() throws ParseException {
            this.align = 4;
        }
        
        public void xMidYMax() throws ParseException {
            this.align = 9;
        }
        
        public void xMidYMid() throws ParseException {
            this.align = 6;
        }
        
        public void xMidYMin() throws ParseException {
            this.align = 3;
        }
        
        public void xMinYMax() throws ParseException {
            this.align = 8;
        }
        
        public void xMinYMid() throws ParseException {
            this.align = 5;
        }
        
        public void xMinYMin() throws ParseException {
            this.align = 2;
        }
        
        public void meet() throws ParseException {
            this.meet = true;
        }
        
        public void slice() throws ParseException {
            this.meet = false;
        }
        
        public void endPreserveAspectRatio() throws ParseException {
            this.hasPreserveAspectRatio = true;
        }
    }
}
