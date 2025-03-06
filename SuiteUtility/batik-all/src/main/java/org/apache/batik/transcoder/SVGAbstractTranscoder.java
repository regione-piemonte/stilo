// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.transcoder;

import java.util.StringTokenizer;
import java.util.LinkedList;
import org.apache.batik.bridge.RelaxedScriptSecurity;
import org.apache.batik.bridge.DefaultScriptSecurity;
import org.apache.batik.bridge.NoLoadScriptSecurity;
import org.apache.batik.bridge.ScriptSecurity;
import java.awt.Dimension;
import java.awt.geom.Dimension2D;
import org.apache.batik.bridge.UserAgentAdapter;
import org.apache.batik.transcoder.keys.BooleanKey;
import org.apache.batik.transcoder.keys.FloatKey;
import org.apache.batik.transcoder.keys.StringKey;
import org.apache.batik.transcoder.keys.Rectangle2DKey;
import org.apache.batik.transcoder.keys.LengthKey;
import org.apache.batik.bridge.svg12.SVG12BridgeContext;
import java.util.List;
import org.apache.batik.gvt.CompositeGraphicsNode;
import org.apache.batik.gvt.CanvasGraphicsNode;
import org.w3c.dom.svg.SVGSVGElement;
import org.apache.batik.bridge.ViewBox;
import org.apache.batik.bridge.BridgeException;
import org.w3c.dom.Element;
import org.apache.batik.bridge.SVGUtilities;
import org.apache.batik.bridge.BaseScriptingEnvironment;
import org.apache.batik.util.ParsedURL;
import org.apache.batik.dom.svg.SVGOMDocument;
import org.apache.batik.dom.util.DOMUtilities;
import org.w3c.dom.Document;
import org.apache.batik.dom.svg.SAXSVGDocumentFactory;
import org.apache.batik.dom.util.DocumentFactory;
import org.w3c.dom.DOMImplementation;
import org.apache.batik.dom.svg.SVGDOMImplementation;
import org.apache.batik.bridge.UserAgent;
import org.apache.batik.bridge.GVTBuilder;
import org.apache.batik.bridge.BridgeContext;
import org.apache.batik.gvt.GraphicsNode;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;

public abstract class SVGAbstractTranscoder extends XMLAbstractTranscoder
{
    public static final String DEFAULT_DEFAULT_FONT_FAMILY = "Arial, Helvetica, sans-serif";
    protected Rectangle2D curAOI;
    protected AffineTransform curTxf;
    protected GraphicsNode root;
    protected BridgeContext ctx;
    protected GVTBuilder builder;
    protected float width;
    protected float height;
    protected UserAgent userAgent;
    public static final TranscodingHints.Key KEY_WIDTH;
    public static final TranscodingHints.Key KEY_HEIGHT;
    public static final TranscodingHints.Key KEY_MAX_WIDTH;
    public static final TranscodingHints.Key KEY_MAX_HEIGHT;
    public static final TranscodingHints.Key KEY_AOI;
    public static final TranscodingHints.Key KEY_LANGUAGE;
    public static final TranscodingHints.Key KEY_MEDIA;
    public static final TranscodingHints.Key KEY_DEFAULT_FONT_FAMILY;
    public static final TranscodingHints.Key KEY_ALTERNATE_STYLESHEET;
    public static final TranscodingHints.Key KEY_USER_STYLESHEET_URI;
    public static final TranscodingHints.Key KEY_PIXEL_UNIT_TO_MILLIMETER;
    public static final TranscodingHints.Key KEY_PIXEL_TO_MM;
    public static final TranscodingHints.Key KEY_EXECUTE_ONLOAD;
    public static final TranscodingHints.Key KEY_SNAPSHOT_TIME;
    public static final TranscodingHints.Key KEY_ALLOWED_SCRIPT_TYPES;
    public static final String DEFAULT_ALLOWED_SCRIPT_TYPES = "text/ecmascript, application/ecmascript, text/javascript, application/javascript, application/java-archive";
    public static final TranscodingHints.Key KEY_CONSTRAIN_SCRIPT_ORIGIN;
    
    protected SVGAbstractTranscoder() {
        this.width = 400.0f;
        this.height = 400.0f;
        this.userAgent = this.createUserAgent();
        this.hints.put(SVGAbstractTranscoder.KEY_DOCUMENT_ELEMENT_NAMESPACE_URI, "http://www.w3.org/2000/svg");
        this.hints.put(SVGAbstractTranscoder.KEY_DOCUMENT_ELEMENT, "svg");
        this.hints.put(SVGAbstractTranscoder.KEY_DOM_IMPLEMENTATION, SVGDOMImplementation.getDOMImplementation());
        this.hints.put(SVGAbstractTranscoder.KEY_MEDIA, "screen");
        this.hints.put(SVGAbstractTranscoder.KEY_DEFAULT_FONT_FAMILY, "Arial, Helvetica, sans-serif");
        this.hints.put(SVGAbstractTranscoder.KEY_EXECUTE_ONLOAD, Boolean.FALSE);
        this.hints.put(SVGAbstractTranscoder.KEY_ALLOWED_SCRIPT_TYPES, "text/ecmascript, application/ecmascript, text/javascript, application/javascript, application/java-archive");
    }
    
    protected UserAgent createUserAgent() {
        return new SVGAbstractTranscoderUserAgent();
    }
    
    protected DocumentFactory createDocumentFactory(final DOMImplementation domImplementation, final String s) {
        return new SAXSVGDocumentFactory(s);
    }
    
    public void transcode(final TranscoderInput transcoderInput, final TranscoderOutput transcoderOutput) throws TranscoderException {
        super.transcode(transcoderInput, transcoderOutput);
        if (this.ctx != null) {
            this.ctx.dispose();
        }
    }
    
    protected void transcode(Document deepCloneDocument, final String s, final TranscoderOutput transcoderOutput) throws TranscoderException {
        if (deepCloneDocument != null && !(deepCloneDocument.getImplementation() instanceof SVGDOMImplementation)) {
            deepCloneDocument = DOMUtilities.deepCloneDocument(deepCloneDocument, (DOMImplementation)this.hints.get(SVGAbstractTranscoder.KEY_DOM_IMPLEMENTATION));
            if (s != null) {
                ((SVGOMDocument)deepCloneDocument).setParsedURL(new ParsedURL(s));
            }
        }
        if (this.hints.containsKey(SVGAbstractTranscoder.KEY_WIDTH)) {
            this.width = (float)this.hints.get(SVGAbstractTranscoder.KEY_WIDTH);
        }
        if (this.hints.containsKey(SVGAbstractTranscoder.KEY_HEIGHT)) {
            this.height = (float)this.hints.get(SVGAbstractTranscoder.KEY_HEIGHT);
        }
        final SVGOMDocument svgomDocument = (SVGOMDocument)deepCloneDocument;
        final SVGSVGElement rootElement = svgomDocument.getRootElement();
        this.ctx = this.createBridgeContext(svgomDocument);
        this.builder = new GVTBuilder();
        final boolean b = this.hints.containsKey(SVGAbstractTranscoder.KEY_EXECUTE_ONLOAD) && (boolean)this.hints.get(SVGAbstractTranscoder.KEY_EXECUTE_ONLOAD);
        GraphicsNode build;
        try {
            if (b) {
                this.ctx.setDynamicState(2);
            }
            build = this.builder.build(this.ctx, svgomDocument);
            if (this.ctx.isDynamic()) {
                final BaseScriptingEnvironment baseScriptingEnvironment = new BaseScriptingEnvironment(this.ctx);
                baseScriptingEnvironment.loadScripts();
                baseScriptingEnvironment.dispatchSVGLoadEvent();
                if (this.hints.containsKey(SVGAbstractTranscoder.KEY_SNAPSHOT_TIME)) {
                    this.ctx.getAnimationEngine().setCurrentTime((float)this.hints.get(SVGAbstractTranscoder.KEY_SNAPSHOT_TIME));
                }
                else if (this.ctx.isSVG12()) {
                    this.ctx.getAnimationEngine().setCurrentTime(SVGUtilities.convertSnapshotTime((Element)rootElement, null));
                }
            }
        }
        catch (BridgeException ex) {
            ex.printStackTrace();
            throw new TranscoderException(ex);
        }
        final float n = (float)this.ctx.getDocumentSize().getWidth();
        final float n2 = (float)this.ctx.getDocumentSize().getHeight();
        this.setImageSize(n, n2);
        AffineTransform affineTransform;
        if (this.hints.containsKey(SVGAbstractTranscoder.KEY_AOI)) {
            final Rectangle2D curAOI = (Rectangle2D)this.hints.get(SVGAbstractTranscoder.KEY_AOI);
            affineTransform = new AffineTransform();
            final double min = Math.min(this.width / curAOI.getWidth(), this.height / curAOI.getHeight());
            affineTransform.scale(min, min);
            affineTransform.translate(-curAOI.getX() + (this.width / min - curAOI.getWidth()) / 2.0, -curAOI.getY() + (this.height / min - curAOI.getHeight()) / 2.0);
            this.curAOI = curAOI;
        }
        else {
            final String ref = new ParsedURL(s).getRef();
            final String attributeNS = rootElement.getAttributeNS((String)null, "viewBox");
            if (ref != null && ref.length() != 0) {
                affineTransform = ViewBox.getViewTransform(ref, (Element)rootElement, this.width, this.height, this.ctx);
            }
            else if (attributeNS != null && attributeNS.length() != 0) {
                affineTransform = ViewBox.getPreserveAspectRatioTransform((Element)rootElement, attributeNS, rootElement.getAttributeNS((String)null, "preserveAspectRatio"), this.width, this.height, this.ctx);
            }
            else {
                final float min2 = Math.min(this.width / n, this.height / n2);
                affineTransform = AffineTransform.getScaleInstance(min2, min2);
            }
            this.curAOI = new Rectangle2D.Float(0.0f, 0.0f, this.width, this.height);
        }
        final CanvasGraphicsNode canvasGraphicsNode = this.getCanvasGraphicsNode(build);
        if (canvasGraphicsNode != null) {
            canvasGraphicsNode.setViewingTransform(affineTransform);
            this.curTxf = new AffineTransform();
        }
        else {
            this.curTxf = affineTransform;
        }
        this.root = build;
    }
    
    protected CanvasGraphicsNode getCanvasGraphicsNode(GraphicsNode graphicsNode) {
        if (!(graphicsNode instanceof CompositeGraphicsNode)) {
            return null;
        }
        final List children = ((CompositeGraphicsNode)graphicsNode).getChildren();
        if (children.size() == 0) {
            return null;
        }
        graphicsNode = children.get(0);
        if (!(graphicsNode instanceof CanvasGraphicsNode)) {
            return null;
        }
        return (CanvasGraphicsNode)graphicsNode;
    }
    
    protected BridgeContext createBridgeContext(final SVGOMDocument svgomDocument) {
        return this.createBridgeContext(svgomDocument.isSVG12() ? "1.2" : "1.x");
    }
    
    protected BridgeContext createBridgeContext() {
        return this.createBridgeContext("1.x");
    }
    
    protected BridgeContext createBridgeContext(final String anObject) {
        if ("1.2".equals(anObject)) {
            return new SVG12BridgeContext(this.userAgent);
        }
        return new BridgeContext(this.userAgent);
    }
    
    protected void setImageSize(final float width, final float height) {
        float floatValue = -1.0f;
        if (this.hints.containsKey(SVGAbstractTranscoder.KEY_WIDTH)) {
            floatValue = (float)this.hints.get(SVGAbstractTranscoder.KEY_WIDTH);
        }
        float floatValue2 = -1.0f;
        if (this.hints.containsKey(SVGAbstractTranscoder.KEY_HEIGHT)) {
            floatValue2 = (float)this.hints.get(SVGAbstractTranscoder.KEY_HEIGHT);
        }
        if (floatValue > 0.0f && floatValue2 > 0.0f) {
            this.width = floatValue;
            this.height = floatValue2;
        }
        else if (floatValue2 > 0.0f) {
            this.width = width * floatValue2 / height;
            this.height = floatValue2;
        }
        else if (floatValue > 0.0f) {
            this.width = floatValue;
            this.height = height * floatValue / width;
        }
        else {
            this.width = width;
            this.height = height;
        }
        float floatValue3 = -1.0f;
        if (this.hints.containsKey(SVGAbstractTranscoder.KEY_MAX_WIDTH)) {
            floatValue3 = (float)this.hints.get(SVGAbstractTranscoder.KEY_MAX_WIDTH);
        }
        float floatValue4 = -1.0f;
        if (this.hints.containsKey(SVGAbstractTranscoder.KEY_MAX_HEIGHT)) {
            floatValue4 = (float)this.hints.get(SVGAbstractTranscoder.KEY_MAX_HEIGHT);
        }
        if (floatValue4 > 0.0f && this.height > floatValue4) {
            this.width = width * floatValue4 / height;
            this.height = floatValue4;
        }
        if (floatValue3 > 0.0f && this.width > floatValue3) {
            this.width = floatValue3;
            this.height = height * floatValue3 / width;
        }
    }
    
    static {
        KEY_WIDTH = new LengthKey();
        KEY_HEIGHT = new LengthKey();
        KEY_MAX_WIDTH = new LengthKey();
        KEY_MAX_HEIGHT = new LengthKey();
        KEY_AOI = new Rectangle2DKey();
        KEY_LANGUAGE = new StringKey();
        KEY_MEDIA = new StringKey();
        KEY_DEFAULT_FONT_FAMILY = new StringKey();
        KEY_ALTERNATE_STYLESHEET = new StringKey();
        KEY_USER_STYLESHEET_URI = new StringKey();
        KEY_PIXEL_UNIT_TO_MILLIMETER = new FloatKey();
        KEY_PIXEL_TO_MM = SVGAbstractTranscoder.KEY_PIXEL_UNIT_TO_MILLIMETER;
        KEY_EXECUTE_ONLOAD = new BooleanKey();
        KEY_SNAPSHOT_TIME = new FloatKey();
        KEY_ALLOWED_SCRIPT_TYPES = new StringKey();
        KEY_CONSTRAIN_SCRIPT_ORIGIN = new BooleanKey();
    }
    
    protected class SVGAbstractTranscoderUserAgent extends UserAgentAdapter
    {
        protected List scripts;
        
        public SVGAbstractTranscoderUserAgent() {
            this.addStdFeatures();
        }
        
        public AffineTransform getTransform() {
            return SVGAbstractTranscoder.this.curTxf;
        }
        
        public void setTransform(final AffineTransform curTxf) {
            SVGAbstractTranscoder.this.curTxf = curTxf;
        }
        
        public Dimension2D getViewportSize() {
            return new Dimension((int)SVGAbstractTranscoder.this.width, (int)SVGAbstractTranscoder.this.height);
        }
        
        public void displayError(final String s) {
            try {
                SVGAbstractTranscoder.this.handler.error(new TranscoderException(s));
            }
            catch (TranscoderException ex) {
                throw new RuntimeException(ex.getMessage());
            }
        }
        
        public void displayError(final Exception ex) {
            try {
                ex.printStackTrace();
                SVGAbstractTranscoder.this.handler.error(new TranscoderException(ex));
            }
            catch (TranscoderException ex2) {
                throw new RuntimeException(ex2.getMessage());
            }
        }
        
        public void displayMessage(final String s) {
            try {
                SVGAbstractTranscoder.this.handler.warning(new TranscoderException(s));
            }
            catch (TranscoderException ex) {
                throw new RuntimeException(ex.getMessage());
            }
        }
        
        public float getPixelUnitToMillimeter() {
            final Object value = SVGAbstractTranscoder.this.hints.get(SVGAbstractTranscoder.KEY_PIXEL_UNIT_TO_MILLIMETER);
            if (value != null) {
                return (float)value;
            }
            return super.getPixelUnitToMillimeter();
        }
        
        public String getLanguages() {
            if (SVGAbstractTranscoder.this.hints.containsKey(SVGAbstractTranscoder.KEY_LANGUAGE)) {
                return (String)SVGAbstractTranscoder.this.hints.get(SVGAbstractTranscoder.KEY_LANGUAGE);
            }
            return super.getLanguages();
        }
        
        public String getMedia() {
            final String s = (String)SVGAbstractTranscoder.this.hints.get(SVGAbstractTranscoder.KEY_MEDIA);
            if (s != null) {
                return s;
            }
            return super.getMedia();
        }
        
        public String getDefaultFontFamily() {
            final String s = (String)SVGAbstractTranscoder.this.hints.get(SVGAbstractTranscoder.KEY_DEFAULT_FONT_FAMILY);
            if (s != null) {
                return s;
            }
            return super.getDefaultFontFamily();
        }
        
        public String getAlternateStyleSheet() {
            final String s = (String)SVGAbstractTranscoder.this.hints.get(SVGAbstractTranscoder.KEY_ALTERNATE_STYLESHEET);
            if (s != null) {
                return s;
            }
            return super.getAlternateStyleSheet();
        }
        
        public String getUserStyleSheetURI() {
            final String s = (String)SVGAbstractTranscoder.this.hints.get(SVGAbstractTranscoder.KEY_USER_STYLESHEET_URI);
            if (s != null) {
                return s;
            }
            return super.getUserStyleSheetURI();
        }
        
        public String getXMLParserClassName() {
            final String s = (String)SVGAbstractTranscoder.this.hints.get(XMLAbstractTranscoder.KEY_XML_PARSER_CLASSNAME);
            if (s != null) {
                return s;
            }
            return super.getXMLParserClassName();
        }
        
        public boolean isXMLParserValidating() {
            final Boolean b = (Boolean)SVGAbstractTranscoder.this.hints.get(XMLAbstractTranscoder.KEY_XML_PARSER_VALIDATING);
            if (b != null) {
                return b;
            }
            return super.isXMLParserValidating();
        }
        
        public ScriptSecurity getScriptSecurity(final String s, final ParsedURL parsedURL, final ParsedURL parsedURL2) {
            if (this.scripts == null) {
                this.computeAllowedScripts();
            }
            if (!this.scripts.contains(s)) {
                return new NoLoadScriptSecurity(s);
            }
            boolean booleanValue = true;
            if (SVGAbstractTranscoder.this.hints.containsKey(SVGAbstractTranscoder.KEY_CONSTRAIN_SCRIPT_ORIGIN)) {
                booleanValue = (boolean)SVGAbstractTranscoder.this.hints.get(SVGAbstractTranscoder.KEY_CONSTRAIN_SCRIPT_ORIGIN);
            }
            if (booleanValue) {
                return new DefaultScriptSecurity(s, parsedURL, parsedURL2);
            }
            return new RelaxedScriptSecurity(s, parsedURL, parsedURL2);
        }
        
        protected void computeAllowedScripts() {
            this.scripts = new LinkedList();
            if (!SVGAbstractTranscoder.this.hints.containsKey(SVGAbstractTranscoder.KEY_ALLOWED_SCRIPT_TYPES)) {
                return;
            }
            final StringTokenizer stringTokenizer = new StringTokenizer((String)SVGAbstractTranscoder.this.hints.get(SVGAbstractTranscoder.KEY_ALLOWED_SCRIPT_TYPES), ",");
            while (stringTokenizer.hasMoreTokens()) {
                this.scripts.add(stringTokenizer.nextToken());
            }
        }
    }
}
