// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.dom.svg12;

import org.apache.batik.dom.events.EventSupport;
import org.apache.batik.dom.AbstractNode;
import org.w3c.dom.events.Event;
import org.apache.batik.dom.events.DocumentEventSupport;
import org.apache.batik.dom.ExtensibleDOMImplementation;
import org.apache.batik.dom.util.DOMUtilities;
import org.apache.batik.dom.GenericElement;
import org.w3c.dom.Element;
import org.apache.batik.dom.AbstractDocument;
import org.w3c.dom.DOMException;
import org.w3c.dom.Node;
import org.w3c.dom.DocumentType;
import java.net.URL;
import org.w3c.css.sac.InputSource;
import org.apache.batik.util.ParsedURL;
import org.w3c.dom.Document;
import org.apache.batik.css.engine.SVG12CSSEngine;
import org.apache.batik.dom.svg.SVGOMDocument;
import org.apache.batik.css.engine.CSSEngine;
import org.apache.batik.css.engine.value.ShorthandManager;
import org.apache.batik.css.engine.value.ValueManager;
import org.apache.batik.css.parser.ExtendedParser;
import org.apache.batik.css.engine.CSSContext;
import org.apache.batik.dom.AbstractStylableDocument;
import org.w3c.dom.DOMImplementation;
import org.apache.batik.dom.util.HashTable;
import org.apache.batik.dom.svg.SVGDOMImplementation;

public class SVG12DOMImplementation extends SVGDOMImplementation
{
    protected static HashTable svg12Factories;
    protected static HashTable xblFactories;
    protected static final DOMImplementation DOM_IMPLEMENTATION;
    
    public SVG12DOMImplementation() {
        this.factories = SVG12DOMImplementation.svg12Factories;
        this.registerFeature("CSS", "2.0");
        this.registerFeature("StyleSheets", "2.0");
        this.registerFeature("SVG", new String[] { "1.0", "1.1", "1.2" });
        this.registerFeature("SVGEvents", new String[] { "1.0", "1.1", "1.2" });
    }
    
    public CSSEngine createCSSEngine(final AbstractStylableDocument abstractStylableDocument, final CSSContext cssContext, final ExtendedParser extendedParser, final ValueManager[] array, final ShorthandManager[] array2) {
        final SVG12CSSEngine svg12CSSEngine = new SVG12CSSEngine(abstractStylableDocument, ((SVGOMDocument)abstractStylableDocument).getParsedURL(), extendedParser, array, array2, cssContext);
        final URL resource = this.getClass().getResource("resources/UserAgentStyleSheet.css");
        if (resource != null) {
            final ParsedURL parsedURL = new ParsedURL(resource);
            svg12CSSEngine.setUserAgentStyleSheet(svg12CSSEngine.parseStyleSheet(new InputSource(parsedURL.toString()), parsedURL, "all"));
        }
        return svg12CSSEngine;
    }
    
    public Document createDocument(final String s, final String s2, final DocumentType documentType) throws DOMException {
        final SVG12OMDocument svg12OMDocument = new SVG12OMDocument(documentType, this);
        svg12OMDocument.setIsSVG12(true);
        if (s2 != null) {
            svg12OMDocument.appendChild(svg12OMDocument.createElementNS(s, s2));
        }
        return svg12OMDocument;
    }
    
    public Element createElementNS(final AbstractDocument abstractDocument, final String s, final String s2) {
        if (s == null) {
            return new GenericElement(s2.intern(), abstractDocument);
        }
        final String localName = DOMUtilities.getLocalName(s2);
        final String prefix = DOMUtilities.getPrefix(s2);
        if ("http://www.w3.org/2000/svg".equals(s)) {
            final ElementFactory elementFactory = (ElementFactory)this.factories.get(localName);
            if (elementFactory != null) {
                return elementFactory.create(prefix, abstractDocument);
            }
        }
        else if ("http://www.w3.org/2004/xbl".equals(s)) {
            final ElementFactory elementFactory2 = (ElementFactory)SVG12DOMImplementation.xblFactories.get(localName);
            if (elementFactory2 != null) {
                return elementFactory2.create(prefix, abstractDocument);
            }
        }
        if (this.customFactories != null) {
            final ElementFactory elementFactory3 = (ElementFactory)this.customFactories.get(s, localName);
            if (elementFactory3 != null) {
                return elementFactory3.create(prefix, abstractDocument);
            }
        }
        return new BindableElement(prefix, abstractDocument, s, localName);
    }
    
    public DocumentEventSupport createDocumentEventSupport() {
        final DocumentEventSupport documentEventSupport = super.createDocumentEventSupport();
        documentEventSupport.registerEventFactory("WheelEvent", new DocumentEventSupport.EventFactory() {
            public Event createEvent() {
                return new SVGOMWheelEvent();
            }
        });
        documentEventSupport.registerEventFactory("ShadowTreeEvent", new DocumentEventSupport.EventFactory() {
            public Event createEvent() {
                return new XBLOMShadowTreeEvent();
            }
        });
        return documentEventSupport;
    }
    
    public EventSupport createEventSupport(final AbstractNode abstractNode) {
        return new XBLEventSupport(abstractNode);
    }
    
    public static DOMImplementation getDOMImplementation() {
        return SVG12DOMImplementation.DOM_IMPLEMENTATION;
    }
    
    static {
        (SVG12DOMImplementation.svg12Factories = new HashTable(SVG12DOMImplementation.svg11Factories)).put("flowDiv", new FlowDivElementFactory());
        SVG12DOMImplementation.svg12Factories.put("flowLine", new FlowLineElementFactory());
        SVG12DOMImplementation.svg12Factories.put("flowPara", new FlowParaElementFactory());
        SVG12DOMImplementation.svg12Factories.put("flowRegionBreak", new FlowRegionBreakElementFactory());
        SVG12DOMImplementation.svg12Factories.put("flowRegion", new FlowRegionElementFactory());
        SVG12DOMImplementation.svg12Factories.put("flowRegionExclude", new FlowRegionExcludeElementFactory());
        SVG12DOMImplementation.svg12Factories.put("flowRoot", new FlowRootElementFactory());
        SVG12DOMImplementation.svg12Factories.put("flowSpan", new FlowSpanElementFactory());
        SVG12DOMImplementation.svg12Factories.put("handler", new HandlerElementFactory());
        SVG12DOMImplementation.svg12Factories.put("multiImage", new MultiImageElementFactory());
        SVG12DOMImplementation.svg12Factories.put("solidColor", new SolidColorElementFactory());
        SVG12DOMImplementation.svg12Factories.put("subImage", new SubImageElementFactory());
        SVG12DOMImplementation.svg12Factories.put("subImageRef", new SubImageRefElementFactory());
        (SVG12DOMImplementation.xblFactories = new HashTable()).put("xbl", new XBLXBLElementFactory());
        SVG12DOMImplementation.xblFactories.put("definition", new XBLDefinitionElementFactory());
        SVG12DOMImplementation.xblFactories.put("template", new XBLTemplateElementFactory());
        SVG12DOMImplementation.xblFactories.put("content", new XBLContentElementFactory());
        SVG12DOMImplementation.xblFactories.put("handlerGroup", new XBLHandlerGroupElementFactory());
        SVG12DOMImplementation.xblFactories.put("import", new XBLImportElementFactory());
        SVG12DOMImplementation.xblFactories.put("shadowTree", new XBLShadowTreeElementFactory());
        DOM_IMPLEMENTATION = new SVG12DOMImplementation();
    }
    
    protected static class XBLShadowTreeElementFactory implements ElementFactory
    {
        public XBLShadowTreeElementFactory() {
        }
        
        public Element create(final String s, final Document document) {
            return new XBLOMShadowTreeElement(s, (AbstractDocument)document);
        }
    }
    
    protected static class XBLImportElementFactory implements ElementFactory
    {
        public XBLImportElementFactory() {
        }
        
        public Element create(final String s, final Document document) {
            return new XBLOMImportElement(s, (AbstractDocument)document);
        }
    }
    
    protected static class XBLHandlerGroupElementFactory implements ElementFactory
    {
        public XBLHandlerGroupElementFactory() {
        }
        
        public Element create(final String s, final Document document) {
            return new XBLOMHandlerGroupElement(s, (AbstractDocument)document);
        }
    }
    
    protected static class XBLContentElementFactory implements ElementFactory
    {
        public XBLContentElementFactory() {
        }
        
        public Element create(final String s, final Document document) {
            return new XBLOMContentElement(s, (AbstractDocument)document);
        }
    }
    
    protected static class XBLTemplateElementFactory implements ElementFactory
    {
        public XBLTemplateElementFactory() {
        }
        
        public Element create(final String s, final Document document) {
            return new XBLOMTemplateElement(s, (AbstractDocument)document);
        }
    }
    
    protected static class XBLDefinitionElementFactory implements ElementFactory
    {
        public XBLDefinitionElementFactory() {
        }
        
        public Element create(final String s, final Document document) {
            return new XBLOMDefinitionElement(s, (AbstractDocument)document);
        }
    }
    
    protected static class XBLXBLElementFactory implements ElementFactory
    {
        public XBLXBLElementFactory() {
        }
        
        public Element create(final String s, final Document document) {
            return new XBLOMXBLElement(s, (AbstractDocument)document);
        }
    }
    
    protected static class SubImageRefElementFactory implements ElementFactory
    {
        public SubImageRefElementFactory() {
        }
        
        public Element create(final String s, final Document document) {
            return new SVGOMSubImageRefElement(s, (AbstractDocument)document);
        }
    }
    
    protected static class SubImageElementFactory implements ElementFactory
    {
        public SubImageElementFactory() {
        }
        
        public Element create(final String s, final Document document) {
            return new SVGOMSubImageElement(s, (AbstractDocument)document);
        }
    }
    
    protected static class SolidColorElementFactory implements ElementFactory
    {
        public SolidColorElementFactory() {
        }
        
        public Element create(final String s, final Document document) {
            return new SVGOMSolidColorElement(s, (AbstractDocument)document);
        }
    }
    
    protected static class MultiImageElementFactory implements ElementFactory
    {
        public MultiImageElementFactory() {
        }
        
        public Element create(final String s, final Document document) {
            return new SVGOMMultiImageElement(s, (AbstractDocument)document);
        }
    }
    
    protected static class HandlerElementFactory implements ElementFactory
    {
        public HandlerElementFactory() {
        }
        
        public Element create(final String s, final Document document) {
            return new SVGOMHandlerElement(s, (AbstractDocument)document);
        }
    }
    
    protected static class FlowSpanElementFactory implements ElementFactory
    {
        public FlowSpanElementFactory() {
        }
        
        public Element create(final String s, final Document document) {
            return new SVGOMFlowSpanElement(s, (AbstractDocument)document);
        }
    }
    
    protected static class FlowRootElementFactory implements ElementFactory
    {
        public FlowRootElementFactory() {
        }
        
        public Element create(final String s, final Document document) {
            return new SVGOMFlowRootElement(s, (AbstractDocument)document);
        }
    }
    
    protected static class FlowRegionExcludeElementFactory implements ElementFactory
    {
        public FlowRegionExcludeElementFactory() {
        }
        
        public Element create(final String s, final Document document) {
            return new SVGOMFlowRegionExcludeElement(s, (AbstractDocument)document);
        }
    }
    
    protected static class FlowRegionElementFactory implements ElementFactory
    {
        public FlowRegionElementFactory() {
        }
        
        public Element create(final String s, final Document document) {
            return new SVGOMFlowRegionElement(s, (AbstractDocument)document);
        }
    }
    
    protected static class FlowRegionBreakElementFactory implements ElementFactory
    {
        public FlowRegionBreakElementFactory() {
        }
        
        public Element create(final String s, final Document document) {
            return new SVGOMFlowRegionBreakElement(s, (AbstractDocument)document);
        }
    }
    
    protected static class FlowParaElementFactory implements ElementFactory
    {
        public FlowParaElementFactory() {
        }
        
        public Element create(final String s, final Document document) {
            return new SVGOMFlowParaElement(s, (AbstractDocument)document);
        }
    }
    
    protected static class FlowLineElementFactory implements ElementFactory
    {
        public FlowLineElementFactory() {
        }
        
        public Element create(final String s, final Document document) {
            return new SVGOMFlowLineElement(s, (AbstractDocument)document);
        }
    }
    
    protected static class FlowDivElementFactory implements ElementFactory
    {
        public FlowDivElementFactory() {
        }
        
        public Element create(final String s, final Document document) {
            return new SVGOMFlowDivElement(s, (AbstractDocument)document);
        }
    }
}
