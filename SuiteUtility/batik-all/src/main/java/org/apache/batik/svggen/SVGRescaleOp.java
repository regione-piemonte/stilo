// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.svggen;

import org.w3c.dom.Element;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import java.awt.image.RescaleOp;
import java.awt.Rectangle;
import java.awt.image.BufferedImageOp;

public class SVGRescaleOp extends AbstractSVGFilterConverter
{
    public SVGRescaleOp(final SVGGeneratorContext svgGeneratorContext) {
        super(svgGeneratorContext);
    }
    
    public SVGFilterDescriptor toSVG(final BufferedImageOp bufferedImageOp, final Rectangle rectangle) {
        if (bufferedImageOp instanceof RescaleOp) {
            return this.toSVG((RescaleOp)bufferedImageOp);
        }
        return null;
    }
    
    public SVGFilterDescriptor toSVG(final RescaleOp rescaleOp) {
        SVGFilterDescriptor svgFilterDescriptor = this.descMap.get(rescaleOp);
        final Document domFactory = this.generatorContext.domFactory;
        if (svgFilterDescriptor == null) {
            final Element elementNS = domFactory.createElementNS("http://www.w3.org/2000/svg", "filter");
            final Element elementNS2 = domFactory.createElementNS("http://www.w3.org/2000/svg", "feComponentTransfer");
            final float[] offsets = rescaleOp.getOffsets(null);
            final float[] scaleFactors = rescaleOp.getScaleFactors(null);
            if (offsets.length != scaleFactors.length) {
                throw new SVGGraphics2DRuntimeException("RescapeOp offsets and scaleFactor array length do not match");
            }
            if (offsets.length != 1 && offsets.length != 3 && offsets.length != 4) {
                throw new SVGGraphics2DRuntimeException("BufferedImage RescaleOp should have 1, 3 or 4 scale factors");
            }
            final Element elementNS3 = domFactory.createElementNS("http://www.w3.org/2000/svg", "feFuncR");
            final Element elementNS4 = domFactory.createElementNS("http://www.w3.org/2000/svg", "feFuncG");
            final Element elementNS5 = domFactory.createElementNS("http://www.w3.org/2000/svg", "feFuncB");
            Element elementNS6 = null;
            final String s = "linear";
            if (offsets.length == 1) {
                final String doubleString = this.doubleString(scaleFactors[0]);
                final String doubleString2 = this.doubleString(offsets[0]);
                elementNS3.setAttributeNS(null, "type", s);
                elementNS4.setAttributeNS(null, "type", s);
                elementNS5.setAttributeNS(null, "type", s);
                elementNS3.setAttributeNS(null, "slope", doubleString);
                elementNS4.setAttributeNS(null, "slope", doubleString);
                elementNS5.setAttributeNS(null, "slope", doubleString);
                elementNS3.setAttributeNS(null, "intercept", doubleString2);
                elementNS4.setAttributeNS(null, "intercept", doubleString2);
                elementNS5.setAttributeNS(null, "intercept", doubleString2);
            }
            else if (offsets.length >= 3) {
                elementNS3.setAttributeNS(null, "type", s);
                elementNS4.setAttributeNS(null, "type", s);
                elementNS5.setAttributeNS(null, "type", s);
                elementNS3.setAttributeNS(null, "slope", this.doubleString(scaleFactors[0]));
                elementNS4.setAttributeNS(null, "slope", this.doubleString(scaleFactors[1]));
                elementNS5.setAttributeNS(null, "slope", this.doubleString(scaleFactors[2]));
                elementNS3.setAttributeNS(null, "intercept", this.doubleString(offsets[0]));
                elementNS4.setAttributeNS(null, "intercept", this.doubleString(offsets[1]));
                elementNS5.setAttributeNS(null, "intercept", this.doubleString(offsets[2]));
                if (offsets.length == 4) {
                    elementNS6 = domFactory.createElementNS("http://www.w3.org/2000/svg", "feFuncA");
                    elementNS6.setAttributeNS(null, "type", s);
                    elementNS6.setAttributeNS(null, "slope", this.doubleString(scaleFactors[3]));
                    elementNS6.setAttributeNS(null, "intercept", this.doubleString(offsets[3]));
                }
            }
            elementNS2.appendChild(elementNS3);
            elementNS2.appendChild(elementNS4);
            elementNS2.appendChild(elementNS5);
            if (elementNS6 != null) {
                elementNS2.appendChild(elementNS6);
            }
            elementNS.appendChild(elementNS2);
            elementNS.setAttributeNS(null, "id", this.generatorContext.idGenerator.generateID("componentTransfer"));
            svgFilterDescriptor = new SVGFilterDescriptor("url(#" + elementNS.getAttributeNS(null, "id") + ")", elementNS);
            this.defSet.add(elementNS);
            this.descMap.put(rescaleOp, svgFilterDescriptor);
        }
        return svgFilterDescriptor;
    }
}
