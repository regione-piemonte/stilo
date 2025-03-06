// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.svggen;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import java.util.Collection;
import java.util.Iterator;
import org.w3c.dom.Node;
import java.util.LinkedList;
import java.util.Collections;
import java.util.ArrayList;
import org.apache.batik.ext.awt.g2d.GraphicContext;
import org.w3c.dom.Element;
import java.util.List;

public class DOMTreeManager implements SVGSyntax, ErrorConstants
{
    int maxGCOverrides;
    protected final List groupManagers;
    protected List genericDefSet;
    SVGGraphicContext defaultGC;
    protected Element topLevelGroup;
    SVGGraphicContextConverter gcConverter;
    protected SVGGeneratorContext generatorContext;
    protected SVGBufferedImageOp filterConverter;
    protected List otherDefs;
    
    public DOMTreeManager(final GraphicContext graphicContext, final SVGGeneratorContext generatorContext, final int maxGCOverrides) {
        this.groupManagers = Collections.synchronizedList(new ArrayList<Object>());
        this.genericDefSet = new LinkedList();
        if (graphicContext == null) {
            throw new SVGGraphics2DRuntimeException("gc should not be null");
        }
        if (maxGCOverrides <= 0) {
            throw new SVGGraphics2DRuntimeException("maxGcOverrides should be greater than zero");
        }
        if (generatorContext == null) {
            throw new SVGGraphics2DRuntimeException("generatorContext should not be null");
        }
        this.generatorContext = generatorContext;
        this.maxGCOverrides = maxGCOverrides;
        this.recycleTopLevelGroup();
        this.defaultGC = this.gcConverter.toSVG(graphicContext);
    }
    
    public void addGroupManager(final DOMGroupManager domGroupManager) {
        if (domGroupManager != null) {
            this.groupManagers.add(domGroupManager);
        }
    }
    
    public void removeGroupManager(final DOMGroupManager domGroupManager) {
        if (domGroupManager != null) {
            this.groupManagers.remove(domGroupManager);
        }
    }
    
    public void appendGroup(final Element element, final DOMGroupManager domGroupManager) {
        this.topLevelGroup.appendChild(element);
        synchronized (this.groupManagers) {
            for (int size = this.groupManagers.size(), i = 0; i < size; ++i) {
                final DOMGroupManager domGroupManager2 = this.groupManagers.get(i);
                if (domGroupManager2 != domGroupManager) {
                    domGroupManager2.recycleCurrentGroup();
                }
            }
        }
    }
    
    protected void recycleTopLevelGroup() {
        this.recycleTopLevelGroup(true);
    }
    
    protected void recycleTopLevelGroup(final boolean b) {
        synchronized (this.groupManagers) {
            for (int size = this.groupManagers.size(), i = 0; i < size; ++i) {
                ((DOMGroupManager)this.groupManagers.get(i)).recycleCurrentGroup();
            }
        }
        this.topLevelGroup = this.generatorContext.domFactory.createElementNS("http://www.w3.org/2000/svg", "g");
        if (b) {
            this.filterConverter = new SVGBufferedImageOp(this.generatorContext);
            this.gcConverter = new SVGGraphicContextConverter(this.generatorContext);
        }
    }
    
    public void setTopLevelGroup(final Element topLevelGroup) {
        if (topLevelGroup == null) {
            throw new SVGGraphics2DRuntimeException("topLevelGroup should not be null");
        }
        if (!"g".equalsIgnoreCase(topLevelGroup.getTagName())) {
            throw new SVGGraphics2DRuntimeException("topLevelGroup should be a group <g>");
        }
        this.recycleTopLevelGroup(false);
        this.topLevelGroup = topLevelGroup;
    }
    
    public Element getRoot() {
        return this.getRoot(null);
    }
    
    public Element getRoot(final Element element) {
        Element elementNS = element;
        if (elementNS == null) {
            elementNS = this.generatorContext.domFactory.createElementNS("http://www.w3.org/2000/svg", "svg");
        }
        if (this.gcConverter.getCompositeConverter().getAlphaCompositeConverter().requiresBackgroundAccess()) {
            elementNS.setAttributeNS(null, "enable-background", "new");
        }
        if (this.generatorContext.generatorComment != null) {
            elementNS.appendChild(this.generatorContext.domFactory.createComment(this.generatorContext.generatorComment));
        }
        this.applyDefaultRenderingStyle(elementNS);
        elementNS.appendChild(this.getGenericDefinitions());
        elementNS.appendChild(this.getTopLevelGroup());
        return elementNS;
    }
    
    public void applyDefaultRenderingStyle(final Element element) {
        this.generatorContext.styleHandler.setStyle(element, this.defaultGC.getGroupContext(), this.generatorContext);
    }
    
    public Element getGenericDefinitions() {
        final Element elementNS = this.generatorContext.domFactory.createElementNS("http://www.w3.org/2000/svg", "defs");
        final Iterator<Element> iterator = this.genericDefSet.iterator();
        while (iterator.hasNext()) {
            elementNS.appendChild(iterator.next());
        }
        elementNS.setAttributeNS(null, "id", "genericDefs");
        return elementNS;
    }
    
    public ExtensionHandler getExtensionHandler() {
        return this.generatorContext.getExtensionHandler();
    }
    
    void setExtensionHandler(final ExtensionHandler extensionHandler) {
        this.generatorContext.setExtensionHandler(extensionHandler);
    }
    
    public List getDefinitionSet() {
        final List definitionSet = this.gcConverter.getDefinitionSet();
        definitionSet.removeAll(this.genericDefSet);
        definitionSet.addAll(this.filterConverter.getDefinitionSet());
        if (this.otherDefs != null) {
            definitionSet.addAll(this.otherDefs);
            this.otherDefs = null;
        }
        this.filterConverter = new SVGBufferedImageOp(this.generatorContext);
        this.gcConverter = new SVGGraphicContextConverter(this.generatorContext);
        return definitionSet;
    }
    
    public void addOtherDef(final Element element) {
        if (this.otherDefs == null) {
            this.otherDefs = new LinkedList();
        }
        this.otherDefs.add(element);
    }
    
    public Element getTopLevelGroup() {
        return this.getTopLevelGroup(true);
    }
    
    public Element getTopLevelGroup(final boolean b) {
        final Element topLevelGroup = this.topLevelGroup;
        if (b) {
            final List definitionSet = this.getDefinitionSet();
            if (definitionSet.size() > 0) {
                Element elementNS = null;
                final NodeList elementsByTagName = topLevelGroup.getElementsByTagName("defs");
                if (elementsByTagName.getLength() > 0) {
                    elementNS = (Element)elementsByTagName.item(0);
                }
                if (elementNS == null) {
                    elementNS = this.generatorContext.domFactory.createElementNS("http://www.w3.org/2000/svg", "defs");
                    elementNS.setAttributeNS(null, "id", this.generatorContext.idGenerator.generateID("defs"));
                    topLevelGroup.insertBefore(elementNS, topLevelGroup.getFirstChild());
                }
                final Iterator<Element> iterator = definitionSet.iterator();
                while (iterator.hasNext()) {
                    elementNS.appendChild(iterator.next());
                }
            }
        }
        this.recycleTopLevelGroup(false);
        return topLevelGroup;
    }
    
    public SVGBufferedImageOp getFilterConverter() {
        return this.filterConverter;
    }
    
    public SVGGraphicContextConverter getGraphicContextConverter() {
        return this.gcConverter;
    }
    
    SVGGeneratorContext getGeneratorContext() {
        return this.generatorContext;
    }
    
    Document getDOMFactory() {
        return this.generatorContext.domFactory;
    }
    
    StyleHandler getStyleHandler() {
        return this.generatorContext.styleHandler;
    }
}
