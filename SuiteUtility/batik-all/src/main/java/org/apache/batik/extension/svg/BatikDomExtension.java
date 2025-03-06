// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.extension.svg;

import org.apache.batik.dom.AbstractDocument;
import org.w3c.dom.Element;
import org.w3c.dom.Document;
import org.apache.batik.dom.ExtensibleDOMImplementation;
import org.apache.batik.dom.DomExtension;

public class BatikDomExtension implements DomExtension, BatikExtConstants
{
    public float getPriority() {
        return 1.0f;
    }
    
    public String getAuthor() {
        return "Thomas DeWeese";
    }
    
    public String getContactAddress() {
        return "deweese@apache.org";
    }
    
    public String getURL() {
        return "http://xml.apache.org/batik";
    }
    
    public String getDescription() {
        return "Example extension to standard SVG shape tags";
    }
    
    public void registerTags(final ExtensibleDOMImplementation extensibleDOMImplementation) {
        extensibleDOMImplementation.registerCustomElementFactory("http://xml.apache.org/batik/ext", "regularPolygon", new BatikRegularPolygonElementFactory());
        extensibleDOMImplementation.registerCustomElementFactory("http://xml.apache.org/batik/ext", "star", new BatikStarElementFactory());
        extensibleDOMImplementation.registerCustomElementFactory("http://xml.apache.org/batik/ext", "histogramNormalization", new BatikHistogramNormalizationElementFactory());
        extensibleDOMImplementation.registerCustomElementFactory("http://xml.apache.org/batik/ext", "colorSwitch", new ColorSwitchElementFactory());
        extensibleDOMImplementation.registerCustomElementFactory("http://xml.apache.org/batik/ext", "flowText", new FlowTextElementFactory());
        extensibleDOMImplementation.registerCustomElementFactory("http://xml.apache.org/batik/ext", "flowDiv", new FlowDivElementFactory());
        extensibleDOMImplementation.registerCustomElementFactory("http://xml.apache.org/batik/ext", "flowPara", new FlowParaElementFactory());
        extensibleDOMImplementation.registerCustomElementFactory("http://xml.apache.org/batik/ext", "flowRegionBreak", new FlowRegionBreakElementFactory());
        extensibleDOMImplementation.registerCustomElementFactory("http://xml.apache.org/batik/ext", "flowRegion", new FlowRegionElementFactory());
        extensibleDOMImplementation.registerCustomElementFactory("http://xml.apache.org/batik/ext", "flowLine", new FlowLineElementFactory());
        extensibleDOMImplementation.registerCustomElementFactory("http://xml.apache.org/batik/ext", "flowSpan", new FlowSpanElementFactory());
    }
    
    protected static class FlowSpanElementFactory implements ExtensibleDOMImplementation.ElementFactory
    {
        public FlowSpanElementFactory() {
        }
        
        public Element create(final String s, final Document document) {
            return new FlowSpanElement(s, (AbstractDocument)document);
        }
    }
    
    protected static class FlowLineElementFactory implements ExtensibleDOMImplementation.ElementFactory
    {
        public FlowLineElementFactory() {
        }
        
        public Element create(final String s, final Document document) {
            return new FlowLineElement(s, (AbstractDocument)document);
        }
    }
    
    protected static class FlowRegionElementFactory implements ExtensibleDOMImplementation.ElementFactory
    {
        public FlowRegionElementFactory() {
        }
        
        public Element create(final String s, final Document document) {
            return new FlowRegionElement(s, (AbstractDocument)document);
        }
    }
    
    protected static class FlowRegionBreakElementFactory implements ExtensibleDOMImplementation.ElementFactory
    {
        public FlowRegionBreakElementFactory() {
        }
        
        public Element create(final String s, final Document document) {
            return new FlowRegionBreakElement(s, (AbstractDocument)document);
        }
    }
    
    protected static class FlowParaElementFactory implements ExtensibleDOMImplementation.ElementFactory
    {
        public FlowParaElementFactory() {
        }
        
        public Element create(final String s, final Document document) {
            return new FlowParaElement(s, (AbstractDocument)document);
        }
    }
    
    protected static class FlowDivElementFactory implements ExtensibleDOMImplementation.ElementFactory
    {
        public FlowDivElementFactory() {
        }
        
        public Element create(final String s, final Document document) {
            return new FlowDivElement(s, (AbstractDocument)document);
        }
    }
    
    protected static class FlowTextElementFactory implements ExtensibleDOMImplementation.ElementFactory
    {
        public FlowTextElementFactory() {
        }
        
        public Element create(final String s, final Document document) {
            return new FlowTextElement(s, (AbstractDocument)document);
        }
    }
    
    protected static class ColorSwitchElementFactory implements ExtensibleDOMImplementation.ElementFactory
    {
        public ColorSwitchElementFactory() {
        }
        
        public Element create(final String s, final Document document) {
            return new ColorSwitchElement(s, (AbstractDocument)document);
        }
    }
    
    protected static class BatikHistogramNormalizationElementFactory implements ExtensibleDOMImplementation.ElementFactory
    {
        public BatikHistogramNormalizationElementFactory() {
        }
        
        public Element create(final String s, final Document document) {
            return new BatikHistogramNormalizationElement(s, (AbstractDocument)document);
        }
    }
    
    protected static class BatikStarElementFactory implements ExtensibleDOMImplementation.ElementFactory
    {
        public BatikStarElementFactory() {
        }
        
        public Element create(final String s, final Document document) {
            return new BatikStarElement(s, (AbstractDocument)document);
        }
    }
    
    protected static class BatikRegularPolygonElementFactory implements ExtensibleDOMImplementation.ElementFactory
    {
        public BatikRegularPolygonElementFactory() {
        }
        
        public Element create(final String s, final Document document) {
            return new BatikRegularPolygonElement(s, (AbstractDocument)document);
        }
    }
}
