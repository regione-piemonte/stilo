// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.bridge;

import java.awt.Shape;
import java.awt.geom.GeneralPath;
import org.apache.batik.parser.ParseException;
import org.apache.batik.parser.PathHandler;
import org.apache.batik.parser.PathParser;
import org.apache.batik.parser.AWTPathProducer;
import org.apache.batik.dom.util.XLinkSupport;
import org.apache.batik.gvt.text.TextPath;
import org.w3c.dom.Element;

public class SVGTextPathElementBridge extends AnimatableGenericSVGBridge implements ErrorConstants
{
    public String getLocalName() {
        return "textPath";
    }
    
    public void handleElement(final BridgeContext bridgeContext, final Element element) {
    }
    
    public TextPath createTextPath(final BridgeContext bridgeContext, final Element element) {
        final String xLinkHref = XLinkSupport.getXLinkHref(element);
        final Element referencedElement = bridgeContext.getReferencedElement(element, xLinkHref);
        if (referencedElement == null || !"http://www.w3.org/2000/svg".equals(referencedElement.getNamespaceURI()) || !referencedElement.getLocalName().equals("path")) {
            throw new BridgeException(bridgeContext, element, "uri.badTarget", new Object[] { xLinkHref });
        }
        final String attributeNS = referencedElement.getAttributeNS(null, "d");
        if (attributeNS.length() != 0) {
            final AWTPathProducer pathHandler = new AWTPathProducer();
            pathHandler.setWindingRule(CSSUtilities.convertFillRule(referencedElement));
            Shape shape = null;
            Label_0203: {
                try {
                    final PathParser pathParser = new PathParser();
                    pathParser.setPathHandler(pathHandler);
                    pathParser.parse(attributeNS);
                    break Label_0203;
                }
                catch (ParseException ex) {
                    throw new BridgeException(bridgeContext, referencedElement, ex, "attribute.malformed", new Object[] { "d" });
                }
                finally {
                    shape = pathHandler.getShape();
                }
                throw new BridgeException(bridgeContext, referencedElement, "attribute.missing", new Object[] { "d" });
            }
            final String attributeNS2 = referencedElement.getAttributeNS(null, "transform");
            if (attributeNS2.length() != 0) {
                shape = SVGUtilities.convertTransform(referencedElement, "transform", attributeNS2, bridgeContext).createTransformedShape(shape);
            }
            final TextPath textPath = new TextPath(new GeneralPath(shape));
            final String attributeNS3 = element.getAttributeNS(null, "startOffset");
            if (attributeNS3.length() > 0) {
                final int index = attributeNS3.indexOf(37);
                float svgOtherLengthToUserSpace;
                if (index != -1) {
                    final float lengthOfPath = textPath.lengthOfPath();
                    final String substring = attributeNS3.substring(0, index);
                    float convertSVGNumber;
                    try {
                        convertSVGNumber = SVGUtilities.convertSVGNumber(substring);
                    }
                    catch (NumberFormatException ex2) {
                        convertSVGNumber = -1.0f;
                    }
                    if (convertSVGNumber < 0.0f) {
                        throw new BridgeException(bridgeContext, element, "attribute.malformed", new Object[] { "startOffset", attributeNS3 });
                    }
                    svgOtherLengthToUserSpace = (float)(convertSVGNumber * lengthOfPath / 100.0);
                }
                else {
                    svgOtherLengthToUserSpace = UnitProcessor.svgOtherLengthToUserSpace(attributeNS3, "startOffset", UnitProcessor.createContext(bridgeContext, element));
                }
                textPath.setStartOffset(svgOtherLengthToUserSpace);
            }
            return textPath;
        }
        throw new BridgeException(bridgeContext, referencedElement, "attribute.missing", new Object[] { "d" });
    }
}
