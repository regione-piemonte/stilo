// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.svggen;

import java.awt.Graphics2D;
import org.w3c.dom.Element;
import java.awt.geom.Rectangle2D;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import java.awt.image.RenderedImage;
import java.awt.image.ImageObserver;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.TexturePaint;
import org.apache.batik.ext.awt.g2d.GraphicContext;

public class SVGTexturePaint extends AbstractSVGConverter
{
    public SVGTexturePaint(final SVGGeneratorContext svgGeneratorContext) {
        super(svgGeneratorContext);
    }
    
    public SVGDescriptor toSVG(final GraphicContext graphicContext) {
        return this.toSVG((TexturePaint)graphicContext.getPaint());
    }
    
    public SVGPaintDescriptor toSVG(final TexturePaint texturePaint) {
        SVGPaintDescriptor svgPaintDescriptor = this.descMap.get(texturePaint);
        final Document domFactory = this.generatorContext.domFactory;
        if (svgPaintDescriptor == null) {
            final Rectangle2D anchorRect = texturePaint.getAnchorRect();
            final Element elementNS = domFactory.createElementNS("http://www.w3.org/2000/svg", "pattern");
            elementNS.setAttributeNS(null, "patternUnits", "userSpaceOnUse");
            elementNS.setAttributeNS(null, "x", this.doubleString(anchorRect.getX()));
            elementNS.setAttributeNS(null, "y", this.doubleString(anchorRect.getY()));
            elementNS.setAttributeNS(null, "width", this.doubleString(anchorRect.getWidth()));
            elementNS.setAttributeNS(null, "height", this.doubleString(anchorRect.getHeight()));
            BufferedImage image = texturePaint.getImage();
            if (image.getWidth() > 0 && image.getHeight() > 0 && (image.getWidth() != anchorRect.getWidth() || image.getHeight() != anchorRect.getHeight()) && anchorRect.getWidth() > 0.0 && anchorRect.getHeight() > 0.0) {
                final double n = anchorRect.getWidth() / image.getWidth();
                final double n2 = anchorRect.getHeight() / image.getHeight();
                final BufferedImage bufferedImage = new BufferedImage((int)(n * image.getWidth()), (int)(n2 * image.getHeight()), 2);
                final Graphics2D graphics = bufferedImage.createGraphics();
                graphics.scale(n, n2);
                graphics.drawImage(image, 0, 0, null);
                graphics.dispose();
                image = bufferedImage;
            }
            final Element element = this.generatorContext.genericImageHandler.createElement(this.generatorContext);
            this.generatorContext.genericImageHandler.handleImage((RenderedImage)image, element, 0, 0, image.getWidth(), image.getHeight(), this.generatorContext);
            elementNS.appendChild(element);
            elementNS.setAttributeNS(null, "id", this.generatorContext.idGenerator.generateID("pattern"));
            svgPaintDescriptor = new SVGPaintDescriptor("url(#" + elementNS.getAttributeNS(null, "id") + ")", "1", elementNS);
            this.descMap.put(texturePaint, svgPaintDescriptor);
            this.defSet.add(elementNS);
        }
        return svgPaintDescriptor;
    }
}
