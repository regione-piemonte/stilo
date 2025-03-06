// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.dom.svg;

import org.apache.batik.dom.events.DOMTimeEvent;
import org.w3c.dom.events.Event;
import org.apache.batik.dom.events.DocumentEventSupport;
import org.apache.batik.dom.util.DOMUtilities;
import org.w3c.dom.Element;
import org.apache.batik.dom.AbstractDocument;
import org.w3c.dom.stylesheets.StyleSheet;
import org.w3c.dom.css.CSSStyleDeclaration;
import org.w3c.dom.css.CSSStyleSheet;
import org.w3c.dom.DOMException;
import org.w3c.dom.Node;
import org.apache.batik.dom.GenericDocumentType;
import org.w3c.dom.DocumentType;
import org.apache.batik.css.dom.CSSOMSVGViewCSS;
import org.w3c.dom.css.ViewCSS;
import java.net.URL;
import org.w3c.css.sac.InputSource;
import org.apache.batik.util.ParsedURL;
import org.w3c.dom.Document;
import org.apache.batik.css.engine.SVGCSSEngine;
import org.apache.batik.css.engine.CSSEngine;
import org.apache.batik.css.engine.value.ShorthandManager;
import org.apache.batik.css.engine.value.ValueManager;
import org.apache.batik.css.parser.ExtendedParser;
import org.apache.batik.css.engine.CSSContext;
import org.apache.batik.dom.AbstractStylableDocument;
import org.apache.batik.i18n.LocalizableSupport;
import org.w3c.dom.DOMImplementation;
import org.apache.batik.dom.util.HashTable;
import org.apache.batik.dom.util.CSSStyleDeclarationFactory;
import org.apache.batik.dom.ExtensibleDOMImplementation;

public class SVGDOMImplementation extends ExtensibleDOMImplementation implements CSSStyleDeclarationFactory
{
    public static final String SVG_NAMESPACE_URI = "http://www.w3.org/2000/svg";
    protected static final String RESOURCES = "org.apache.batik.dom.svg.resources.Messages";
    protected HashTable factories;
    protected static HashTable svg11Factories;
    protected static final DOMImplementation DOM_IMPLEMENTATION;
    
    public static DOMImplementation getDOMImplementation() {
        return SVGDOMImplementation.DOM_IMPLEMENTATION;
    }
    
    public SVGDOMImplementation() {
        this.factories = SVGDOMImplementation.svg11Factories;
        this.registerFeature("CSS", "2.0");
        this.registerFeature("StyleSheets", "2.0");
        this.registerFeature("SVG", new String[] { "1.0", "1.1" });
        this.registerFeature("SVGEvents", new String[] { "1.0", "1.1" });
    }
    
    protected void initLocalizable() {
        this.localizableSupport = new LocalizableSupport("org.apache.batik.dom.svg.resources.Messages", this.getClass().getClassLoader());
    }
    
    public CSSEngine createCSSEngine(final AbstractStylableDocument abstractStylableDocument, final CSSContext cssContext, final ExtendedParser extendedParser, final ValueManager[] array, final ShorthandManager[] array2) {
        final SVGCSSEngine svgcssEngine = new SVGCSSEngine(abstractStylableDocument, ((SVGOMDocument)abstractStylableDocument).getParsedURL(), extendedParser, array, array2, cssContext);
        final URL resource = this.getClass().getResource("resources/UserAgentStyleSheet.css");
        if (resource != null) {
            final ParsedURL parsedURL = new ParsedURL(resource);
            svgcssEngine.setUserAgentStyleSheet(svgcssEngine.parseStyleSheet(new InputSource(parsedURL.toString()), parsedURL, "all"));
        }
        return svgcssEngine;
    }
    
    public ViewCSS createViewCSS(final AbstractStylableDocument abstractStylableDocument) {
        return new CSSOMSVGViewCSS(abstractStylableDocument.getCSSEngine());
    }
    
    public DocumentType createDocumentType(final String s, final String s2, final String s3) {
        return new GenericDocumentType(s, s2, s3);
    }
    
    public Document createDocument(final String s, final String s2, final DocumentType documentType) throws DOMException {
        final SVGOMDocument svgomDocument = new SVGOMDocument(documentType, this);
        if (s2 != null) {
            svgomDocument.appendChild(svgomDocument.createElementNS(s, s2));
        }
        return svgomDocument;
    }
    
    public CSSStyleSheet createCSSStyleSheet(final String s, final String s2) {
        throw new UnsupportedOperationException("DOMImplementationCSS.createCSSStyleSheet is not implemented");
    }
    
    public CSSStyleDeclaration createCSSStyleDeclaration() {
        throw new UnsupportedOperationException("CSSStyleDeclarationFactory.createCSSStyleDeclaration is not implemented");
    }
    
    public StyleSheet createStyleSheet(final Node node, final HashTable hashTable) {
        throw new UnsupportedOperationException("StyleSheetFactory.createStyleSheet is not implemented");
    }
    
    public CSSStyleSheet getUserAgentStyleSheet() {
        throw new UnsupportedOperationException("StyleSheetFactory.getUserAgentStyleSheet is not implemented");
    }
    
    public Element createElementNS(final AbstractDocument abstractDocument, final String anObject, final String s) {
        if (!"http://www.w3.org/2000/svg".equals(anObject)) {
            return super.createElementNS(abstractDocument, anObject, s);
        }
        final ElementFactory elementFactory = (ElementFactory)this.factories.get(DOMUtilities.getLocalName(s));
        if (elementFactory != null) {
            return elementFactory.create(DOMUtilities.getPrefix(s), abstractDocument);
        }
        throw abstractDocument.createDOMException((short)8, "invalid.element", new Object[] { anObject, s });
    }
    
    public DocumentEventSupport createDocumentEventSupport() {
        final DocumentEventSupport documentEventSupport = new DocumentEventSupport();
        documentEventSupport.registerEventFactory("SVGEvents", new DocumentEventSupport.EventFactory() {
            public Event createEvent() {
                return new SVGOMEvent();
            }
        });
        documentEventSupport.registerEventFactory("TimeEvent", new DocumentEventSupport.EventFactory() {
            public Event createEvent() {
                return new DOMTimeEvent();
            }
        });
        return documentEventSupport;
    }
    
    static {
        (SVGDOMImplementation.svg11Factories = new HashTable()).put("a", new AElementFactory());
        SVGDOMImplementation.svg11Factories.put("altGlyph", new AltGlyphElementFactory());
        SVGDOMImplementation.svg11Factories.put("altGlyphDef", new AltGlyphDefElementFactory());
        SVGDOMImplementation.svg11Factories.put("altGlyphItem", new AltGlyphItemElementFactory());
        SVGDOMImplementation.svg11Factories.put("animate", new AnimateElementFactory());
        SVGDOMImplementation.svg11Factories.put("animateColor", new AnimateColorElementFactory());
        SVGDOMImplementation.svg11Factories.put("animateMotion", new AnimateMotionElementFactory());
        SVGDOMImplementation.svg11Factories.put("animateTransform", new AnimateTransformElementFactory());
        SVGDOMImplementation.svg11Factories.put("circle", new CircleElementFactory());
        SVGDOMImplementation.svg11Factories.put("clipPath", new ClipPathElementFactory());
        SVGDOMImplementation.svg11Factories.put("color-profile", new ColorProfileElementFactory());
        SVGDOMImplementation.svg11Factories.put("cursor", new CursorElementFactory());
        SVGDOMImplementation.svg11Factories.put("definition-src", new DefinitionSrcElementFactory());
        SVGDOMImplementation.svg11Factories.put("defs", new DefsElementFactory());
        SVGDOMImplementation.svg11Factories.put("desc", new DescElementFactory());
        SVGDOMImplementation.svg11Factories.put("ellipse", new EllipseElementFactory());
        SVGDOMImplementation.svg11Factories.put("feBlend", new FeBlendElementFactory());
        SVGDOMImplementation.svg11Factories.put("feColorMatrix", new FeColorMatrixElementFactory());
        SVGDOMImplementation.svg11Factories.put("feComponentTransfer", new FeComponentTransferElementFactory());
        SVGDOMImplementation.svg11Factories.put("feComposite", new FeCompositeElementFactory());
        SVGDOMImplementation.svg11Factories.put("feConvolveMatrix", new FeConvolveMatrixElementFactory());
        SVGDOMImplementation.svg11Factories.put("feDiffuseLighting", new FeDiffuseLightingElementFactory());
        SVGDOMImplementation.svg11Factories.put("feDisplacementMap", new FeDisplacementMapElementFactory());
        SVGDOMImplementation.svg11Factories.put("feDistantLight", new FeDistantLightElementFactory());
        SVGDOMImplementation.svg11Factories.put("feFlood", new FeFloodElementFactory());
        SVGDOMImplementation.svg11Factories.put("feFuncA", new FeFuncAElementFactory());
        SVGDOMImplementation.svg11Factories.put("feFuncR", new FeFuncRElementFactory());
        SVGDOMImplementation.svg11Factories.put("feFuncG", new FeFuncGElementFactory());
        SVGDOMImplementation.svg11Factories.put("feFuncB", new FeFuncBElementFactory());
        SVGDOMImplementation.svg11Factories.put("feGaussianBlur", new FeGaussianBlurElementFactory());
        SVGDOMImplementation.svg11Factories.put("feImage", new FeImageElementFactory());
        SVGDOMImplementation.svg11Factories.put("feMerge", new FeMergeElementFactory());
        SVGDOMImplementation.svg11Factories.put("feMergeNode", new FeMergeNodeElementFactory());
        SVGDOMImplementation.svg11Factories.put("feMorphology", new FeMorphologyElementFactory());
        SVGDOMImplementation.svg11Factories.put("feOffset", new FeOffsetElementFactory());
        SVGDOMImplementation.svg11Factories.put("fePointLight", new FePointLightElementFactory());
        SVGDOMImplementation.svg11Factories.put("feSpecularLighting", new FeSpecularLightingElementFactory());
        SVGDOMImplementation.svg11Factories.put("feSpotLight", new FeSpotLightElementFactory());
        SVGDOMImplementation.svg11Factories.put("feTile", new FeTileElementFactory());
        SVGDOMImplementation.svg11Factories.put("feTurbulence", new FeTurbulenceElementFactory());
        SVGDOMImplementation.svg11Factories.put("filter", new FilterElementFactory());
        SVGDOMImplementation.svg11Factories.put("font", new FontElementFactory());
        SVGDOMImplementation.svg11Factories.put("font-face", new FontFaceElementFactory());
        SVGDOMImplementation.svg11Factories.put("font-face-format", new FontFaceFormatElementFactory());
        SVGDOMImplementation.svg11Factories.put("font-face-name", new FontFaceNameElementFactory());
        SVGDOMImplementation.svg11Factories.put("font-face-src", new FontFaceSrcElementFactory());
        SVGDOMImplementation.svg11Factories.put("font-face-uri", new FontFaceUriElementFactory());
        SVGDOMImplementation.svg11Factories.put("foreignObject", new ForeignObjectElementFactory());
        SVGDOMImplementation.svg11Factories.put("g", new GElementFactory());
        SVGDOMImplementation.svg11Factories.put("glyph", new GlyphElementFactory());
        SVGDOMImplementation.svg11Factories.put("glyphRef", new GlyphRefElementFactory());
        SVGDOMImplementation.svg11Factories.put("hkern", new HkernElementFactory());
        SVGDOMImplementation.svg11Factories.put("image", new ImageElementFactory());
        SVGDOMImplementation.svg11Factories.put("line", new LineElementFactory());
        SVGDOMImplementation.svg11Factories.put("linearGradient", new LinearGradientElementFactory());
        SVGDOMImplementation.svg11Factories.put("marker", new MarkerElementFactory());
        SVGDOMImplementation.svg11Factories.put("mask", new MaskElementFactory());
        SVGDOMImplementation.svg11Factories.put("metadata", new MetadataElementFactory());
        SVGDOMImplementation.svg11Factories.put("missing-glyph", new MissingGlyphElementFactory());
        SVGDOMImplementation.svg11Factories.put("mpath", new MpathElementFactory());
        SVGDOMImplementation.svg11Factories.put("path", new PathElementFactory());
        SVGDOMImplementation.svg11Factories.put("pattern", new PatternElementFactory());
        SVGDOMImplementation.svg11Factories.put("polygon", new PolygonElementFactory());
        SVGDOMImplementation.svg11Factories.put("polyline", new PolylineElementFactory());
        SVGDOMImplementation.svg11Factories.put("radialGradient", new RadialGradientElementFactory());
        SVGDOMImplementation.svg11Factories.put("rect", new RectElementFactory());
        SVGDOMImplementation.svg11Factories.put("set", new SetElementFactory());
        SVGDOMImplementation.svg11Factories.put("script", new ScriptElementFactory());
        SVGDOMImplementation.svg11Factories.put("stop", new StopElementFactory());
        SVGDOMImplementation.svg11Factories.put("style", new StyleElementFactory());
        SVGDOMImplementation.svg11Factories.put("svg", new SvgElementFactory());
        SVGDOMImplementation.svg11Factories.put("switch", new SwitchElementFactory());
        SVGDOMImplementation.svg11Factories.put("symbol", new SymbolElementFactory());
        SVGDOMImplementation.svg11Factories.put("text", new TextElementFactory());
        SVGDOMImplementation.svg11Factories.put("textPath", new TextPathElementFactory());
        SVGDOMImplementation.svg11Factories.put("title", new TitleElementFactory());
        SVGDOMImplementation.svg11Factories.put("tref", new TrefElementFactory());
        SVGDOMImplementation.svg11Factories.put("tspan", new TspanElementFactory());
        SVGDOMImplementation.svg11Factories.put("use", new UseElementFactory());
        SVGDOMImplementation.svg11Factories.put("view", new ViewElementFactory());
        SVGDOMImplementation.svg11Factories.put("vkern", new VkernElementFactory());
        DOM_IMPLEMENTATION = new SVGDOMImplementation();
    }
    
    protected static class VkernElementFactory implements ElementFactory
    {
        public VkernElementFactory() {
        }
        
        public Element create(final String s, final Document document) {
            return new SVGOMVKernElement(s, (AbstractDocument)document);
        }
    }
    
    protected static class ViewElementFactory implements ElementFactory
    {
        public ViewElementFactory() {
        }
        
        public Element create(final String s, final Document document) {
            return new SVGOMViewElement(s, (AbstractDocument)document);
        }
    }
    
    protected static class UseElementFactory implements ElementFactory
    {
        public UseElementFactory() {
        }
        
        public Element create(final String s, final Document document) {
            return new SVGOMUseElement(s, (AbstractDocument)document);
        }
    }
    
    protected static class TspanElementFactory implements ElementFactory
    {
        public TspanElementFactory() {
        }
        
        public Element create(final String s, final Document document) {
            return new SVGOMTSpanElement(s, (AbstractDocument)document);
        }
    }
    
    protected static class TrefElementFactory implements ElementFactory
    {
        public TrefElementFactory() {
        }
        
        public Element create(final String s, final Document document) {
            return new SVGOMTRefElement(s, (AbstractDocument)document);
        }
    }
    
    protected static class TitleElementFactory implements ElementFactory
    {
        public TitleElementFactory() {
        }
        
        public Element create(final String s, final Document document) {
            return new SVGOMTitleElement(s, (AbstractDocument)document);
        }
    }
    
    protected static class TextPathElementFactory implements ElementFactory
    {
        public TextPathElementFactory() {
        }
        
        public Element create(final String s, final Document document) {
            return new SVGOMTextPathElement(s, (AbstractDocument)document);
        }
    }
    
    protected static class TextElementFactory implements ElementFactory
    {
        public TextElementFactory() {
        }
        
        public Element create(final String s, final Document document) {
            return new SVGOMTextElement(s, (AbstractDocument)document);
        }
    }
    
    protected static class SymbolElementFactory implements ElementFactory
    {
        public SymbolElementFactory() {
        }
        
        public Element create(final String s, final Document document) {
            return new SVGOMSymbolElement(s, (AbstractDocument)document);
        }
    }
    
    protected static class SwitchElementFactory implements ElementFactory
    {
        public SwitchElementFactory() {
        }
        
        public Element create(final String s, final Document document) {
            return new SVGOMSwitchElement(s, (AbstractDocument)document);
        }
    }
    
    protected static class SvgElementFactory implements ElementFactory
    {
        public SvgElementFactory() {
        }
        
        public Element create(final String s, final Document document) {
            return new SVGOMSVGElement(s, (AbstractDocument)document);
        }
    }
    
    protected static class StyleElementFactory implements ElementFactory
    {
        public StyleElementFactory() {
        }
        
        public Element create(final String s, final Document document) {
            return new SVGOMStyleElement(s, (AbstractDocument)document);
        }
    }
    
    protected static class StopElementFactory implements ElementFactory
    {
        public StopElementFactory() {
        }
        
        public Element create(final String s, final Document document) {
            return new SVGOMStopElement(s, (AbstractDocument)document);
        }
    }
    
    protected static class SetElementFactory implements ElementFactory
    {
        public SetElementFactory() {
        }
        
        public Element create(final String s, final Document document) {
            return new SVGOMSetElement(s, (AbstractDocument)document);
        }
    }
    
    protected static class ScriptElementFactory implements ElementFactory
    {
        public ScriptElementFactory() {
        }
        
        public Element create(final String s, final Document document) {
            return new SVGOMScriptElement(s, (AbstractDocument)document);
        }
    }
    
    protected static class RectElementFactory implements ElementFactory
    {
        public RectElementFactory() {
        }
        
        public Element create(final String s, final Document document) {
            return new SVGOMRectElement(s, (AbstractDocument)document);
        }
    }
    
    protected static class RadialGradientElementFactory implements ElementFactory
    {
        public RadialGradientElementFactory() {
        }
        
        public Element create(final String s, final Document document) {
            return new SVGOMRadialGradientElement(s, (AbstractDocument)document);
        }
    }
    
    protected static class PolylineElementFactory implements ElementFactory
    {
        public PolylineElementFactory() {
        }
        
        public Element create(final String s, final Document document) {
            return new SVGOMPolylineElement(s, (AbstractDocument)document);
        }
    }
    
    protected static class PolygonElementFactory implements ElementFactory
    {
        public PolygonElementFactory() {
        }
        
        public Element create(final String s, final Document document) {
            return new SVGOMPolygonElement(s, (AbstractDocument)document);
        }
    }
    
    protected static class PatternElementFactory implements ElementFactory
    {
        public PatternElementFactory() {
        }
        
        public Element create(final String s, final Document document) {
            return new SVGOMPatternElement(s, (AbstractDocument)document);
        }
    }
    
    protected static class PathElementFactory implements ElementFactory
    {
        public PathElementFactory() {
        }
        
        public Element create(final String s, final Document document) {
            return new SVGOMPathElement(s, (AbstractDocument)document);
        }
    }
    
    protected static class MpathElementFactory implements ElementFactory
    {
        public MpathElementFactory() {
        }
        
        public Element create(final String s, final Document document) {
            return new SVGOMMPathElement(s, (AbstractDocument)document);
        }
    }
    
    protected static class MissingGlyphElementFactory implements ElementFactory
    {
        public MissingGlyphElementFactory() {
        }
        
        public Element create(final String s, final Document document) {
            return new SVGOMMissingGlyphElement(s, (AbstractDocument)document);
        }
    }
    
    protected static class MetadataElementFactory implements ElementFactory
    {
        public MetadataElementFactory() {
        }
        
        public Element create(final String s, final Document document) {
            return new SVGOMMetadataElement(s, (AbstractDocument)document);
        }
    }
    
    protected static class MaskElementFactory implements ElementFactory
    {
        public MaskElementFactory() {
        }
        
        public Element create(final String s, final Document document) {
            return new SVGOMMaskElement(s, (AbstractDocument)document);
        }
    }
    
    protected static class MarkerElementFactory implements ElementFactory
    {
        public MarkerElementFactory() {
        }
        
        public Element create(final String s, final Document document) {
            return new SVGOMMarkerElement(s, (AbstractDocument)document);
        }
    }
    
    protected static class LinearGradientElementFactory implements ElementFactory
    {
        public LinearGradientElementFactory() {
        }
        
        public Element create(final String s, final Document document) {
            return new SVGOMLinearGradientElement(s, (AbstractDocument)document);
        }
    }
    
    protected static class LineElementFactory implements ElementFactory
    {
        public LineElementFactory() {
        }
        
        public Element create(final String s, final Document document) {
            return new SVGOMLineElement(s, (AbstractDocument)document);
        }
    }
    
    protected static class ImageElementFactory implements ElementFactory
    {
        public ImageElementFactory() {
        }
        
        public Element create(final String s, final Document document) {
            return new SVGOMImageElement(s, (AbstractDocument)document);
        }
    }
    
    protected static class HkernElementFactory implements ElementFactory
    {
        public HkernElementFactory() {
        }
        
        public Element create(final String s, final Document document) {
            return new SVGOMHKernElement(s, (AbstractDocument)document);
        }
    }
    
    protected static class GlyphRefElementFactory implements ElementFactory
    {
        public GlyphRefElementFactory() {
        }
        
        public Element create(final String s, final Document document) {
            return new SVGOMGlyphRefElement(s, (AbstractDocument)document);
        }
    }
    
    protected static class GlyphElementFactory implements ElementFactory
    {
        public GlyphElementFactory() {
        }
        
        public Element create(final String s, final Document document) {
            return new SVGOMGlyphElement(s, (AbstractDocument)document);
        }
    }
    
    protected static class GElementFactory implements ElementFactory
    {
        public GElementFactory() {
        }
        
        public Element create(final String s, final Document document) {
            return new SVGOMGElement(s, (AbstractDocument)document);
        }
    }
    
    protected static class ForeignObjectElementFactory implements ElementFactory
    {
        public ForeignObjectElementFactory() {
        }
        
        public Element create(final String s, final Document document) {
            return new SVGOMForeignObjectElement(s, (AbstractDocument)document);
        }
    }
    
    protected static class FontFaceUriElementFactory implements ElementFactory
    {
        public FontFaceUriElementFactory() {
        }
        
        public Element create(final String s, final Document document) {
            return new SVGOMFontFaceUriElement(s, (AbstractDocument)document);
        }
    }
    
    protected static class FontFaceSrcElementFactory implements ElementFactory
    {
        public FontFaceSrcElementFactory() {
        }
        
        public Element create(final String s, final Document document) {
            return new SVGOMFontFaceSrcElement(s, (AbstractDocument)document);
        }
    }
    
    protected static class FontFaceNameElementFactory implements ElementFactory
    {
        public FontFaceNameElementFactory() {
        }
        
        public Element create(final String s, final Document document) {
            return new SVGOMFontFaceNameElement(s, (AbstractDocument)document);
        }
    }
    
    protected static class FontFaceFormatElementFactory implements ElementFactory
    {
        public FontFaceFormatElementFactory() {
        }
        
        public Element create(final String s, final Document document) {
            return new SVGOMFontFaceFormatElement(s, (AbstractDocument)document);
        }
    }
    
    protected static class FontFaceElementFactory implements ElementFactory
    {
        public FontFaceElementFactory() {
        }
        
        public Element create(final String s, final Document document) {
            return new SVGOMFontFaceElement(s, (AbstractDocument)document);
        }
    }
    
    protected static class FontElementFactory implements ElementFactory
    {
        public FontElementFactory() {
        }
        
        public Element create(final String s, final Document document) {
            return new SVGOMFontElement(s, (AbstractDocument)document);
        }
    }
    
    protected static class FilterElementFactory implements ElementFactory
    {
        public FilterElementFactory() {
        }
        
        public Element create(final String s, final Document document) {
            return new SVGOMFilterElement(s, (AbstractDocument)document);
        }
    }
    
    protected static class FeTurbulenceElementFactory implements ElementFactory
    {
        public FeTurbulenceElementFactory() {
        }
        
        public Element create(final String s, final Document document) {
            return new SVGOMFETurbulenceElement(s, (AbstractDocument)document);
        }
    }
    
    protected static class FeTileElementFactory implements ElementFactory
    {
        public FeTileElementFactory() {
        }
        
        public Element create(final String s, final Document document) {
            return new SVGOMFETileElement(s, (AbstractDocument)document);
        }
    }
    
    protected static class FeSpotLightElementFactory implements ElementFactory
    {
        public FeSpotLightElementFactory() {
        }
        
        public Element create(final String s, final Document document) {
            return new SVGOMFESpotLightElement(s, (AbstractDocument)document);
        }
    }
    
    protected static class FeSpecularLightingElementFactory implements ElementFactory
    {
        public FeSpecularLightingElementFactory() {
        }
        
        public Element create(final String s, final Document document) {
            return new SVGOMFESpecularLightingElement(s, (AbstractDocument)document);
        }
    }
    
    protected static class FePointLightElementFactory implements ElementFactory
    {
        public FePointLightElementFactory() {
        }
        
        public Element create(final String s, final Document document) {
            return new SVGOMFEPointLightElement(s, (AbstractDocument)document);
        }
    }
    
    protected static class FeOffsetElementFactory implements ElementFactory
    {
        public FeOffsetElementFactory() {
        }
        
        public Element create(final String s, final Document document) {
            return new SVGOMFEOffsetElement(s, (AbstractDocument)document);
        }
    }
    
    protected static class FeMorphologyElementFactory implements ElementFactory
    {
        public FeMorphologyElementFactory() {
        }
        
        public Element create(final String s, final Document document) {
            return new SVGOMFEMorphologyElement(s, (AbstractDocument)document);
        }
    }
    
    protected static class FeMergeNodeElementFactory implements ElementFactory
    {
        public FeMergeNodeElementFactory() {
        }
        
        public Element create(final String s, final Document document) {
            return new SVGOMFEMergeNodeElement(s, (AbstractDocument)document);
        }
    }
    
    protected static class FeMergeElementFactory implements ElementFactory
    {
        public FeMergeElementFactory() {
        }
        
        public Element create(final String s, final Document document) {
            return new SVGOMFEMergeElement(s, (AbstractDocument)document);
        }
    }
    
    protected static class FeImageElementFactory implements ElementFactory
    {
        public FeImageElementFactory() {
        }
        
        public Element create(final String s, final Document document) {
            return new SVGOMFEImageElement(s, (AbstractDocument)document);
        }
    }
    
    protected static class FeGaussianBlurElementFactory implements ElementFactory
    {
        public FeGaussianBlurElementFactory() {
        }
        
        public Element create(final String s, final Document document) {
            return new SVGOMFEGaussianBlurElement(s, (AbstractDocument)document);
        }
    }
    
    protected static class FeFuncBElementFactory implements ElementFactory
    {
        public FeFuncBElementFactory() {
        }
        
        public Element create(final String s, final Document document) {
            return new SVGOMFEFuncBElement(s, (AbstractDocument)document);
        }
    }
    
    protected static class FeFuncGElementFactory implements ElementFactory
    {
        public FeFuncGElementFactory() {
        }
        
        public Element create(final String s, final Document document) {
            return new SVGOMFEFuncGElement(s, (AbstractDocument)document);
        }
    }
    
    protected static class FeFuncRElementFactory implements ElementFactory
    {
        public FeFuncRElementFactory() {
        }
        
        public Element create(final String s, final Document document) {
            return new SVGOMFEFuncRElement(s, (AbstractDocument)document);
        }
    }
    
    protected static class FeFuncAElementFactory implements ElementFactory
    {
        public FeFuncAElementFactory() {
        }
        
        public Element create(final String s, final Document document) {
            return new SVGOMFEFuncAElement(s, (AbstractDocument)document);
        }
    }
    
    protected static class FeFloodElementFactory implements ElementFactory
    {
        public FeFloodElementFactory() {
        }
        
        public Element create(final String s, final Document document) {
            return new SVGOMFEFloodElement(s, (AbstractDocument)document);
        }
    }
    
    protected static class FeDistantLightElementFactory implements ElementFactory
    {
        public FeDistantLightElementFactory() {
        }
        
        public Element create(final String s, final Document document) {
            return new SVGOMFEDistantLightElement(s, (AbstractDocument)document);
        }
    }
    
    protected static class FeDisplacementMapElementFactory implements ElementFactory
    {
        public FeDisplacementMapElementFactory() {
        }
        
        public Element create(final String s, final Document document) {
            return new SVGOMFEDisplacementMapElement(s, (AbstractDocument)document);
        }
    }
    
    protected static class FeDiffuseLightingElementFactory implements ElementFactory
    {
        public FeDiffuseLightingElementFactory() {
        }
        
        public Element create(final String s, final Document document) {
            return new SVGOMFEDiffuseLightingElement(s, (AbstractDocument)document);
        }
    }
    
    protected static class FeConvolveMatrixElementFactory implements ElementFactory
    {
        public FeConvolveMatrixElementFactory() {
        }
        
        public Element create(final String s, final Document document) {
            return new SVGOMFEConvolveMatrixElement(s, (AbstractDocument)document);
        }
    }
    
    protected static class FeCompositeElementFactory implements ElementFactory
    {
        public FeCompositeElementFactory() {
        }
        
        public Element create(final String s, final Document document) {
            return new SVGOMFECompositeElement(s, (AbstractDocument)document);
        }
    }
    
    protected static class FeComponentTransferElementFactory implements ElementFactory
    {
        public FeComponentTransferElementFactory() {
        }
        
        public Element create(final String s, final Document document) {
            return new SVGOMFEComponentTransferElement(s, (AbstractDocument)document);
        }
    }
    
    protected static class FeColorMatrixElementFactory implements ElementFactory
    {
        public FeColorMatrixElementFactory() {
        }
        
        public Element create(final String s, final Document document) {
            return new SVGOMFEColorMatrixElement(s, (AbstractDocument)document);
        }
    }
    
    protected static class FeBlendElementFactory implements ElementFactory
    {
        public FeBlendElementFactory() {
        }
        
        public Element create(final String s, final Document document) {
            return new SVGOMFEBlendElement(s, (AbstractDocument)document);
        }
    }
    
    protected static class EllipseElementFactory implements ElementFactory
    {
        public EllipseElementFactory() {
        }
        
        public Element create(final String s, final Document document) {
            return new SVGOMEllipseElement(s, (AbstractDocument)document);
        }
    }
    
    protected static class DescElementFactory implements ElementFactory
    {
        public DescElementFactory() {
        }
        
        public Element create(final String s, final Document document) {
            return new SVGOMDescElement(s, (AbstractDocument)document);
        }
    }
    
    protected static class DefsElementFactory implements ElementFactory
    {
        public DefsElementFactory() {
        }
        
        public Element create(final String s, final Document document) {
            return new SVGOMDefsElement(s, (AbstractDocument)document);
        }
    }
    
    protected static class DefinitionSrcElementFactory implements ElementFactory
    {
        public DefinitionSrcElementFactory() {
        }
        
        public Element create(final String s, final Document document) {
            return new SVGOMDefinitionSrcElement(s, (AbstractDocument)document);
        }
    }
    
    protected static class CursorElementFactory implements ElementFactory
    {
        public CursorElementFactory() {
        }
        
        public Element create(final String s, final Document document) {
            return new SVGOMCursorElement(s, (AbstractDocument)document);
        }
    }
    
    protected static class ColorProfileElementFactory implements ElementFactory
    {
        public ColorProfileElementFactory() {
        }
        
        public Element create(final String s, final Document document) {
            return new SVGOMColorProfileElement(s, (AbstractDocument)document);
        }
    }
    
    protected static class ClipPathElementFactory implements ElementFactory
    {
        public ClipPathElementFactory() {
        }
        
        public Element create(final String s, final Document document) {
            return new SVGOMClipPathElement(s, (AbstractDocument)document);
        }
    }
    
    protected static class CircleElementFactory implements ElementFactory
    {
        public CircleElementFactory() {
        }
        
        public Element create(final String s, final Document document) {
            return new SVGOMCircleElement(s, (AbstractDocument)document);
        }
    }
    
    protected static class AnimateTransformElementFactory implements ElementFactory
    {
        public AnimateTransformElementFactory() {
        }
        
        public Element create(final String s, final Document document) {
            return new SVGOMAnimateTransformElement(s, (AbstractDocument)document);
        }
    }
    
    protected static class AnimateMotionElementFactory implements ElementFactory
    {
        public AnimateMotionElementFactory() {
        }
        
        public Element create(final String s, final Document document) {
            return new SVGOMAnimateMotionElement(s, (AbstractDocument)document);
        }
    }
    
    protected static class AnimateColorElementFactory implements ElementFactory
    {
        public AnimateColorElementFactory() {
        }
        
        public Element create(final String s, final Document document) {
            return new SVGOMAnimateColorElement(s, (AbstractDocument)document);
        }
    }
    
    protected static class AnimateElementFactory implements ElementFactory
    {
        public AnimateElementFactory() {
        }
        
        public Element create(final String s, final Document document) {
            return new SVGOMAnimateElement(s, (AbstractDocument)document);
        }
    }
    
    protected static class AltGlyphItemElementFactory implements ElementFactory
    {
        public AltGlyphItemElementFactory() {
        }
        
        public Element create(final String s, final Document document) {
            return new SVGOMAltGlyphItemElement(s, (AbstractDocument)document);
        }
    }
    
    protected static class AltGlyphDefElementFactory implements ElementFactory
    {
        public AltGlyphDefElementFactory() {
        }
        
        public Element create(final String s, final Document document) {
            return new SVGOMAltGlyphDefElement(s, (AbstractDocument)document);
        }
    }
    
    protected static class AltGlyphElementFactory implements ElementFactory
    {
        public AltGlyphElementFactory() {
        }
        
        public Element create(final String s, final Document document) {
            return new SVGOMAltGlyphElement(s, (AbstractDocument)document);
        }
    }
    
    protected static class AElementFactory implements ElementFactory
    {
        public AElementFactory() {
        }
        
        public Element create(final String s, final Document document) {
            return new SVGOMAElement(s, (AbstractDocument)document);
        }
    }
}
