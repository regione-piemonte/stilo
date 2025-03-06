// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.svggen;

import org.w3c.dom.Element;
import java.awt.image.Kernel;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import java.awt.image.ConvolveOp;
import java.awt.Rectangle;
import java.awt.image.BufferedImageOp;

public class SVGConvolveOp extends AbstractSVGFilterConverter
{
    public SVGConvolveOp(final SVGGeneratorContext svgGeneratorContext) {
        super(svgGeneratorContext);
    }
    
    public SVGFilterDescriptor toSVG(final BufferedImageOp bufferedImageOp, final Rectangle rectangle) {
        if (bufferedImageOp instanceof ConvolveOp) {
            return this.toSVG((ConvolveOp)bufferedImageOp);
        }
        return null;
    }
    
    public SVGFilterDescriptor toSVG(final ConvolveOp convolveOp) {
        SVGFilterDescriptor svgFilterDescriptor = this.descMap.get(convolveOp);
        final Document domFactory = this.generatorContext.domFactory;
        if (svgFilterDescriptor == null) {
            final Kernel kernel = convolveOp.getKernel();
            final Element elementNS = domFactory.createElementNS("http://www.w3.org/2000/svg", "filter");
            final Element elementNS2 = domFactory.createElementNS("http://www.w3.org/2000/svg", "feConvolveMatrix");
            elementNS2.setAttributeNS(null, "order", kernel.getWidth() + " " + kernel.getHeight());
            final float[] kernelData = kernel.getKernelData(null);
            final StringBuffer sb = new StringBuffer(kernelData.length * 8);
            for (int i = 0; i < kernelData.length; ++i) {
                sb.append(this.doubleString(kernelData[i]));
                sb.append(" ");
            }
            elementNS2.setAttributeNS(null, "kernelMatrix", sb.toString().trim());
            elementNS.appendChild(elementNS2);
            elementNS.setAttributeNS(null, "id", this.generatorContext.idGenerator.generateID("convolve"));
            if (convolveOp.getEdgeCondition() == 1) {
                elementNS2.setAttributeNS(null, "edgeMode", "duplicate");
            }
            else {
                elementNS2.setAttributeNS(null, "edgeMode", "none");
            }
            final StringBuffer sb2 = new StringBuffer("url(");
            sb2.append("#");
            sb2.append(elementNS.getAttributeNS(null, "id"));
            sb2.append(")");
            svgFilterDescriptor = new SVGFilterDescriptor(sb2.toString(), elementNS);
            this.defSet.add(elementNS);
            this.descMap.put(convolveOp, svgFilterDescriptor);
        }
        return svgFilterDescriptor;
    }
}
