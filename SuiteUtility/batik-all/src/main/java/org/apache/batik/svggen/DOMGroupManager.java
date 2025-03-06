// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.svggen;

import java.util.HashMap;
import org.apache.batik.ext.awt.g2d.TransformStackElement;
import java.util.Iterator;
import java.util.Map;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import org.apache.batik.ext.awt.g2d.GraphicContext;

public class DOMGroupManager implements SVGSyntax, ErrorConstants
{
    public static final short DRAW = 1;
    public static final short FILL = 16;
    protected GraphicContext gc;
    protected DOMTreeManager domTreeManager;
    protected SVGGraphicContext groupGC;
    protected Element currentGroup;
    
    public DOMGroupManager(final GraphicContext gc, final DOMTreeManager domTreeManager) {
        if (gc == null) {
            throw new SVGGraphics2DRuntimeException("gc should not be null");
        }
        if (domTreeManager == null) {
            throw new SVGGraphics2DRuntimeException("domTreeManager should not be null");
        }
        this.gc = gc;
        this.domTreeManager = domTreeManager;
        this.recycleCurrentGroup();
        this.groupGC = domTreeManager.gcConverter.toSVG(gc);
    }
    
    void recycleCurrentGroup() {
        this.currentGroup = this.domTreeManager.getDOMFactory().createElementNS("http://www.w3.org/2000/svg", "g");
    }
    
    public void addElement(final Element element) {
        this.addElement(element, (short)17);
    }
    
    public void addElement(final Element element, final short n) {
        if (!this.currentGroup.hasChildNodes()) {
            this.currentGroup.appendChild(element);
            this.groupGC = this.domTreeManager.gcConverter.toSVG(this.gc);
            final SVGGraphicContext processDeltaGC = processDeltaGC(this.groupGC, this.domTreeManager.defaultGC);
            this.domTreeManager.getStyleHandler().setStyle(this.currentGroup, processDeltaGC.getGroupContext(), this.domTreeManager.getGeneratorContext());
            if ((n & 0x1) == 0x0) {
                processDeltaGC.getGraphicElementContext().put("stroke", "none");
            }
            if ((n & 0x10) == 0x0) {
                processDeltaGC.getGraphicElementContext().put("fill", "none");
            }
            this.domTreeManager.getStyleHandler().setStyle(element, processDeltaGC.getGraphicElementContext(), this.domTreeManager.getGeneratorContext());
            this.setTransform(this.currentGroup, processDeltaGC.getTransformStack());
            this.domTreeManager.appendGroup(this.currentGroup, this);
        }
        else if (this.gc.isTransformStackValid()) {
            final SVGGraphicContext processDeltaGC2 = processDeltaGC(this.domTreeManager.gcConverter.toSVG(this.gc), this.groupGC);
            this.trimContextForElement(processDeltaGC2, element);
            if (this.countOverrides(processDeltaGC2) <= this.domTreeManager.maxGCOverrides) {
                this.currentGroup.appendChild(element);
                if ((n & 0x1) == 0x0) {
                    processDeltaGC2.getContext().put("stroke", "none");
                }
                if ((n & 0x10) == 0x0) {
                    processDeltaGC2.getContext().put("fill", "none");
                }
                this.domTreeManager.getStyleHandler().setStyle(element, processDeltaGC2.getContext(), this.domTreeManager.getGeneratorContext());
                this.setTransform(element, processDeltaGC2.getTransformStack());
            }
            else {
                this.currentGroup = this.domTreeManager.getDOMFactory().createElementNS("http://www.w3.org/2000/svg", "g");
                this.addElement(element, n);
            }
        }
        else {
            this.currentGroup = this.domTreeManager.getDOMFactory().createElementNS("http://www.w3.org/2000/svg", "g");
            this.gc.validateTransformStack();
            this.addElement(element, n);
        }
    }
    
    protected int countOverrides(final SVGGraphicContext svgGraphicContext) {
        return svgGraphicContext.getGroupContext().size();
    }
    
    protected void trimContextForElement(final SVGGraphicContext svgGraphicContext, final Element element) {
        final String tagName = element.getTagName();
        final Map groupContext = svgGraphicContext.getGroupContext();
        if (tagName != null) {
            for (final String s : groupContext.keySet()) {
                final SVGAttribute value = SVGAttributeMap.get(s);
                if (value != null && !value.appliesTo(tagName)) {
                    groupContext.remove(s);
                }
            }
        }
    }
    
    protected void setTransform(final Element element, final TransformStackElement[] array) {
        final String trim = this.domTreeManager.gcConverter.toSVG(array).trim();
        if (trim.length() > 0) {
            element.setAttributeNS(null, "transform", trim);
        }
    }
    
    static SVGGraphicContext processDeltaGC(final SVGGraphicContext svgGraphicContext, final SVGGraphicContext svgGraphicContext2) {
        final Map processDeltaMap = processDeltaMap(svgGraphicContext.getGroupContext(), svgGraphicContext2.getGroupContext());
        final Map graphicElementContext = svgGraphicContext.getGraphicElementContext();
        final TransformStackElement[] transformStack = svgGraphicContext.getTransformStack();
        final TransformStackElement[] transformStack2 = svgGraphicContext2.getTransformStack();
        final int n = transformStack.length - transformStack2.length;
        final TransformStackElement[] array = new TransformStackElement[n];
        System.arraycopy(transformStack, transformStack2.length, array, 0, n);
        return new SVGGraphicContext(processDeltaMap, graphicElementContext, array);
    }
    
    static Map processDeltaMap(final Map map, final Map map2) {
        final HashMap<String, String> hashMap = new HashMap<String, String>();
        for (final String s : map.keySet()) {
            final String s2 = map.get(s);
            if (!s2.equals(map2.get(s))) {
                hashMap.put(s, s2);
            }
        }
        return hashMap;
    }
}
