// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.svggen;

import java.awt.geom.Line2D;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.awt.geom.GeneralPath;
import org.apache.batik.ext.awt.g2d.GraphicContext;
import java.awt.Shape;

public class SVGClip extends AbstractSVGConverter
{
    public static final Shape ORIGIN;
    public static final SVGClipDescriptor NO_CLIP;
    private SVGShape shapeConverter;
    
    public SVGClip(final SVGGeneratorContext svgGeneratorContext) {
        super(svgGeneratorContext);
        this.shapeConverter = new SVGShape(svgGeneratorContext);
    }
    
    public SVGDescriptor toSVG(final GraphicContext graphicContext) {
        final Shape clip = graphicContext.getClip();
        SVGClipDescriptor svgClipDescriptor;
        if (clip != null) {
            final StringBuffer sb = new StringBuffer("url(");
            final ClipKey clipKey = new ClipKey(new GeneralPath(clip), this.generatorContext);
            svgClipDescriptor = this.descMap.get(clipKey);
            if (svgClipDescriptor == null) {
                final Element clipToSVG = this.clipToSVG(clip);
                if (clipToSVG == null) {
                    svgClipDescriptor = SVGClip.NO_CLIP;
                }
                else {
                    sb.append("#");
                    sb.append(clipToSVG.getAttributeNS(null, "id"));
                    sb.append(")");
                    svgClipDescriptor = new SVGClipDescriptor(sb.toString(), clipToSVG);
                    this.descMap.put(clipKey, svgClipDescriptor);
                    this.defSet.add(clipToSVG);
                }
            }
        }
        else {
            svgClipDescriptor = SVGClip.NO_CLIP;
        }
        return svgClipDescriptor;
    }
    
    private Element clipToSVG(final Shape shape) {
        final Element elementNS = this.generatorContext.domFactory.createElementNS("http://www.w3.org/2000/svg", "clipPath");
        elementNS.setAttributeNS(null, "clipPathUnits", "userSpaceOnUse");
        elementNS.setAttributeNS(null, "id", this.generatorContext.idGenerator.generateID("clipPath"));
        final Element svg = this.shapeConverter.toSVG(shape);
        if (svg != null) {
            elementNS.appendChild(svg);
            return elementNS;
        }
        elementNS.appendChild(this.shapeConverter.toSVG(SVGClip.ORIGIN));
        return elementNS;
    }
    
    static {
        ORIGIN = new Line2D.Float(0.0f, 0.0f, 0.0f, 0.0f);
        NO_CLIP = new SVGClipDescriptor("none", null);
    }
}
