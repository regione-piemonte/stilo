// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.svggen;

import org.w3c.dom.Node;
import org.w3c.dom.Element;
import org.apache.batik.ext.awt.g2d.GraphicContext;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.awt.AlphaComposite;
import java.util.HashMap;
import java.util.Map;

public class SVGAlphaComposite extends AbstractSVGConverter
{
    private Map compositeDefsMap;
    private boolean backgroundAccessRequired;
    
    public SVGAlphaComposite(final SVGGeneratorContext svgGeneratorContext) {
        super(svgGeneratorContext);
        this.compositeDefsMap = new HashMap();
        this.backgroundAccessRequired = false;
        this.compositeDefsMap.put(AlphaComposite.Src, this.compositeToSVG(AlphaComposite.Src));
        this.compositeDefsMap.put(AlphaComposite.SrcIn, this.compositeToSVG(AlphaComposite.SrcIn));
        this.compositeDefsMap.put(AlphaComposite.SrcOut, this.compositeToSVG(AlphaComposite.SrcOut));
        this.compositeDefsMap.put(AlphaComposite.DstIn, this.compositeToSVG(AlphaComposite.DstIn));
        this.compositeDefsMap.put(AlphaComposite.DstOut, this.compositeToSVG(AlphaComposite.DstOut));
        this.compositeDefsMap.put(AlphaComposite.DstOver, this.compositeToSVG(AlphaComposite.DstOver));
        this.compositeDefsMap.put(AlphaComposite.Clear, this.compositeToSVG(AlphaComposite.Clear));
    }
    
    public List getAlphaCompositeFilterSet() {
        return new LinkedList(this.compositeDefsMap.values());
    }
    
    public boolean requiresBackgroundAccess() {
        return this.backgroundAccessRequired;
    }
    
    public SVGDescriptor toSVG(final GraphicContext graphicContext) {
        return this.toSVG((AlphaComposite)graphicContext.getComposite());
    }
    
    public SVGCompositeDescriptor toSVG(final AlphaComposite alphaComposite) {
        SVGCompositeDescriptor svgCompositeDescriptor = this.descMap.get(alphaComposite);
        if (svgCompositeDescriptor == null) {
            final String doubleString = this.doubleString(alphaComposite.getAlpha());
            Element element = null;
            String string;
            if (alphaComposite.getRule() != 3) {
                element = this.compositeDefsMap.get(AlphaComposite.getInstance(alphaComposite.getRule()));
                this.defSet.add(element);
                final StringBuffer sb = new StringBuffer("url(");
                sb.append("#");
                sb.append(element.getAttributeNS(null, "id"));
                sb.append(")");
                string = sb.toString();
            }
            else {
                string = "none";
            }
            svgCompositeDescriptor = new SVGCompositeDescriptor(doubleString, string, element);
            this.descMap.put(alphaComposite, svgCompositeDescriptor);
        }
        if (alphaComposite.getRule() != 3) {
            this.backgroundAccessRequired = true;
        }
        return svgCompositeDescriptor;
    }
    
    private Element compositeToSVG(final AlphaComposite alphaComposite) {
        String s = "0";
        String s2 = null;
        String s3 = null;
        String s4 = null;
        String s5 = null;
        switch (alphaComposite.getRule()) {
            case 1: {
                s2 = "arithmetic";
                s3 = "SourceGraphic";
                s4 = "BackgroundImage";
                s5 = "alphaCompositeClear";
                break;
            }
            case 2: {
                s2 = "arithmetic";
                s3 = "SourceGraphic";
                s4 = "BackgroundImage";
                s5 = "alphaCompositeSrc";
                s = "1";
                break;
            }
            case 5: {
                s2 = "in";
                s3 = "SourceGraphic";
                s4 = "BackgroundImage";
                s5 = "alphaCompositeSrcIn";
                break;
            }
            case 7: {
                s2 = "out";
                s3 = "SourceGraphic";
                s4 = "BackgroundImage";
                s5 = "alphaCompositeSrcOut";
                break;
            }
            case 6: {
                s2 = "in";
                s4 = "SourceGraphic";
                s3 = "BackgroundImage";
                s5 = "alphaCompositeDstIn";
                break;
            }
            case 8: {
                s2 = "out";
                s4 = "SourceGraphic";
                s3 = "BackgroundImage";
                s5 = "alphaCompositeDstOut";
                break;
            }
            case 4: {
                s2 = "over";
                s4 = "SourceGraphic";
                s3 = "BackgroundImage";
                s5 = "alphaCompositeDstOver";
                break;
            }
            default: {
                throw new Error("invalid rule:" + alphaComposite.getRule());
            }
        }
        final Element elementNS = this.generatorContext.domFactory.createElementNS("http://www.w3.org/2000/svg", "filter");
        elementNS.setAttributeNS(null, "id", s5);
        elementNS.setAttributeNS(null, "filterUnits", "objectBoundingBox");
        elementNS.setAttributeNS(null, "x", "0%");
        elementNS.setAttributeNS(null, "y", "0%");
        elementNS.setAttributeNS(null, "width", "100%");
        elementNS.setAttributeNS(null, "height", "100%");
        final Element elementNS2 = this.generatorContext.domFactory.createElementNS("http://www.w3.org/2000/svg", "feComposite");
        elementNS2.setAttributeNS(null, "operator", s2);
        elementNS2.setAttributeNS(null, "in", s3);
        elementNS2.setAttributeNS(null, "in2", s4);
        elementNS2.setAttributeNS(null, "k2", s);
        elementNS2.setAttributeNS(null, "result", "composite");
        final Element elementNS3 = this.generatorContext.domFactory.createElementNS("http://www.w3.org/2000/svg", "feFlood");
        elementNS3.setAttributeNS(null, "flood-color", "white");
        elementNS3.setAttributeNS(null, "flood-opacity", "1");
        elementNS3.setAttributeNS(null, "result", "flood");
        final Element elementNS4 = this.generatorContext.domFactory.createElementNS("http://www.w3.org/2000/svg", "feMerge");
        final Element elementNS5 = this.generatorContext.domFactory.createElementNS("http://www.w3.org/2000/svg", "feMergeNode");
        elementNS5.setAttributeNS(null, "in", "flood");
        final Element elementNS6 = this.generatorContext.domFactory.createElementNS("http://www.w3.org/2000/svg", "feMergeNode");
        elementNS6.setAttributeNS(null, "in", "composite");
        elementNS4.appendChild(elementNS5);
        elementNS4.appendChild(elementNS6);
        elementNS.appendChild(elementNS3);
        elementNS.appendChild(elementNS2);
        elementNS.appendChild(elementNS4);
        return elementNS;
    }
}
