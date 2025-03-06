// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.bridge;

import org.w3c.dom.events.Event;
import org.apache.batik.gvt.text.TextSpanLayout;
import java.util.HashSet;
import java.util.Set;
import org.apache.batik.gvt.text.TextHit;
import org.apache.batik.gvt.renderer.StrokingTextPainter;
import org.apache.batik.gvt.text.Mark;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;
import org.apache.batik.gvt.font.GVTGlyphVector;
import java.awt.Shape;
import java.awt.geom.GeneralPath;
import java.awt.geom.Rectangle2D;
import org.apache.batik.css.engine.value.ListValue;
import java.awt.AlphaComposite;
import org.apache.batik.css.engine.StyleMap;
import org.apache.batik.css.engine.CSSStylableElement;
import org.apache.batik.dom.svg.SVGOMAnimatedEnumeration;
import org.apache.batik.dom.svg.AbstractSVGAnimatedLength;
import java.awt.Color;
import org.w3c.dom.svg.SVGTextContentElement;
import org.apache.batik.gvt.font.GVTFontFamily;
import org.apache.batik.css.engine.value.Value;
import org.apache.batik.gvt.font.FontFamilyResolver;
import org.apache.batik.gvt.font.UnresolvedFontFamily;
import org.apache.batik.gvt.font.GVTFont;
import java.util.ArrayList;
import java.lang.ref.SoftReference;
import java.util.List;
import org.w3c.dom.svg.SVGNumberList;
import org.apache.batik.gvt.text.GVTAttributedCharacterIterator;
import org.apache.batik.dom.svg.SVGOMAnimatedNumberList;
import org.w3c.dom.svg.SVGTextPositioningElement;
import java.util.Iterator;
import org.apache.batik.dom.util.XLinkSupport;
import java.awt.font.TextAttribute;
import java.util.HashMap;
import org.apache.batik.dom.util.XMLSupport;
import java.util.Map;
import org.apache.batik.gvt.text.TextPath;
import org.apache.batik.css.engine.CSSEngineEvent;
import org.apache.batik.dom.svg.AnimatedLiveAttributeValue;
import org.apache.batik.gvt.text.TextPaintInfo;
import org.w3c.dom.events.MutationEvent;
import org.apache.batik.dom.svg.SVGContext;
import org.apache.batik.dom.svg.SVGOMElement;
import org.w3c.dom.events.EventTarget;
import org.w3c.dom.events.EventListener;
import org.apache.batik.dom.events.NodeEventTarget;
import org.w3c.dom.svg.SVGLengthList;
import org.apache.batik.dom.svg.LiveAttributeException;
import org.apache.batik.dom.svg.SVGOMAnimatedLengthList;
import org.apache.batik.dom.svg.SVGOMTextPositioningElement;
import java.awt.geom.Point2D;
import java.awt.RenderingHints;
import org.w3c.dom.Node;
import org.apache.batik.gvt.GraphicsNode;
import org.apache.batik.gvt.TextNode;
import org.w3c.dom.Element;
import java.util.WeakHashMap;
import java.text.AttributedString;
import java.text.AttributedCharacterIterator;
import org.apache.batik.dom.svg.SVGTextContent;

public class SVGTextElementBridge extends AbstractGraphicsNodeBridge implements SVGTextContent
{
    protected static final Integer ZERO;
    public static final AttributedCharacterIterator.Attribute TEXT_COMPOUND_DELIMITER;
    public static final AttributedCharacterIterator.Attribute TEXT_COMPOUND_ID;
    public static final AttributedCharacterIterator.Attribute PAINT_INFO;
    public static final AttributedCharacterIterator.Attribute ALT_GLYPH_HANDLER;
    public static final AttributedCharacterIterator.Attribute TEXTPATH;
    public static final AttributedCharacterIterator.Attribute ANCHOR_TYPE;
    public static final AttributedCharacterIterator.Attribute GVT_FONT_FAMILIES;
    public static final AttributedCharacterIterator.Attribute GVT_FONTS;
    public static final AttributedCharacterIterator.Attribute BASELINE_SHIFT;
    protected AttributedString laidoutText;
    protected WeakHashMap elemTPI;
    protected boolean usingComplexSVGFont;
    protected DOMChildNodeRemovedEventListener childNodeRemovedEventListener;
    protected DOMSubtreeModifiedEventListener subtreeModifiedEventListener;
    private boolean hasNewACI;
    private Element cssProceedElement;
    protected int endLimit;
    
    public SVGTextElementBridge() {
        this.elemTPI = new WeakHashMap();
        this.usingComplexSVGFont = false;
    }
    
    public String getLocalName() {
        return "text";
    }
    
    public Bridge getInstance() {
        return new SVGTextElementBridge();
    }
    
    protected TextNode getTextNode() {
        return (TextNode)this.node;
    }
    
    public GraphicsNode createGraphicsNode(final BridgeContext bridgeContext, final Element element) {
        final TextNode textNode = (TextNode)super.createGraphicsNode(bridgeContext, element);
        if (textNode == null) {
            return null;
        }
        this.associateSVGContext(bridgeContext, element, textNode);
        for (Node node = this.getFirstChild(element); node != null; node = this.getNextSibling(node)) {
            if (node.getNodeType() == 1) {
                this.addContextToChild(bridgeContext, (Element)node);
            }
        }
        if (bridgeContext.getTextPainter() != null) {
            textNode.setTextPainter(bridgeContext.getTextPainter());
        }
        final RenderingHints convertTextRendering = CSSUtilities.convertTextRendering(element, CSSUtilities.convertColorRendering(element, null));
        if (convertTextRendering != null) {
            textNode.setRenderingHints(convertTextRendering);
        }
        textNode.setLocation(this.getLocation(bridgeContext, element));
        return textNode;
    }
    
    protected GraphicsNode instantiateGraphicsNode() {
        return new TextNode();
    }
    
    protected Point2D getLocation(final BridgeContext bridgeContext, final Element element) {
        try {
            final SVGOMTextPositioningElement svgomTextPositioningElement = (SVGOMTextPositioningElement)element;
            final SVGOMAnimatedLengthList list = (SVGOMAnimatedLengthList)svgomTextPositioningElement.getX();
            list.check();
            final SVGLengthList animVal = list.getAnimVal();
            float value = 0.0f;
            if (animVal.getNumberOfItems() > 0) {
                value = animVal.getItem(0).getValue();
            }
            final SVGOMAnimatedLengthList list2 = (SVGOMAnimatedLengthList)svgomTextPositioningElement.getY();
            list2.check();
            final SVGLengthList animVal2 = list2.getAnimVal();
            float value2 = 0.0f;
            if (animVal2.getNumberOfItems() > 0) {
                value2 = animVal2.getItem(0).getValue();
            }
            return new Point2D.Float(value, value2);
        }
        catch (LiveAttributeException ex) {
            throw new BridgeException(bridgeContext, ex);
        }
    }
    
    protected boolean isTextElement(final Element element) {
        if (!"http://www.w3.org/2000/svg".equals(element.getNamespaceURI())) {
            return false;
        }
        final String localName = element.getLocalName();
        return localName.equals("text") || localName.equals("tspan") || localName.equals("altGlyph") || localName.equals("a") || localName.equals("textPath") || localName.equals("tref");
    }
    
    protected boolean isTextChild(final Element element) {
        if (!"http://www.w3.org/2000/svg".equals(element.getNamespaceURI())) {
            return false;
        }
        final String localName = element.getLocalName();
        return localName.equals("tspan") || localName.equals("altGlyph") || localName.equals("a") || localName.equals("textPath") || localName.equals("tref");
    }
    
    public void buildGraphicsNode(final BridgeContext bridgeContext, final Element element, final GraphicsNode graphicsNode) {
        element.normalize();
        this.computeLaidoutText(bridgeContext, element, graphicsNode);
        graphicsNode.setComposite(CSSUtilities.convertOpacity(element));
        graphicsNode.setFilter(CSSUtilities.convertFilter(element, graphicsNode, bridgeContext));
        graphicsNode.setMask(CSSUtilities.convertMask(element, graphicsNode, bridgeContext));
        graphicsNode.setClip(CSSUtilities.convertClipPath(element, graphicsNode, bridgeContext));
        graphicsNode.setPointerEventType(CSSUtilities.convertPointerEvents(element));
        this.initializeDynamicSupport(bridgeContext, element, graphicsNode);
        if (!bridgeContext.isDynamic()) {
            this.elemTPI.clear();
        }
    }
    
    public boolean isComposite() {
        return false;
    }
    
    protected Node getFirstChild(final Node node) {
        return node.getFirstChild();
    }
    
    protected Node getNextSibling(final Node node) {
        return node.getNextSibling();
    }
    
    protected Node getParentNode(final Node node) {
        return node.getParentNode();
    }
    
    protected void initializeDynamicSupport(final BridgeContext bridgeContext, final Element element, final GraphicsNode graphicsNode) {
        super.initializeDynamicSupport(bridgeContext, element, graphicsNode);
        if (bridgeContext.isDynamic()) {
            this.addTextEventListeners(bridgeContext, (NodeEventTarget)element);
        }
    }
    
    protected void addTextEventListeners(final BridgeContext bridgeContext, final NodeEventTarget nodeEventTarget) {
        if (this.childNodeRemovedEventListener == null) {
            this.childNodeRemovedEventListener = new DOMChildNodeRemovedEventListener();
        }
        if (this.subtreeModifiedEventListener == null) {
            this.subtreeModifiedEventListener = new DOMSubtreeModifiedEventListener();
        }
        nodeEventTarget.addEventListenerNS("http://www.w3.org/2001/xml-events", "DOMNodeRemoved", this.childNodeRemovedEventListener, true, null);
        bridgeContext.storeEventListenerNS(nodeEventTarget, "http://www.w3.org/2001/xml-events", "DOMNodeRemoved", this.childNodeRemovedEventListener, true);
        nodeEventTarget.addEventListenerNS("http://www.w3.org/2001/xml-events", "DOMSubtreeModified", this.subtreeModifiedEventListener, false, null);
        bridgeContext.storeEventListenerNS(nodeEventTarget, "http://www.w3.org/2001/xml-events", "DOMSubtreeModified", this.subtreeModifiedEventListener, false);
    }
    
    protected void removeTextEventListeners(final BridgeContext bridgeContext, final NodeEventTarget nodeEventTarget) {
        nodeEventTarget.removeEventListenerNS("http://www.w3.org/2001/xml-events", "DOMNodeRemoved", this.childNodeRemovedEventListener, true);
        nodeEventTarget.removeEventListenerNS("http://www.w3.org/2001/xml-events", "DOMSubtreeModified", this.subtreeModifiedEventListener, false);
    }
    
    public void dispose() {
        this.removeTextEventListeners(this.ctx, (NodeEventTarget)this.e);
        super.dispose();
    }
    
    protected void addContextToChild(final BridgeContext bridgeContext, final Element element) {
        if ("http://www.w3.org/2000/svg".equals(element.getNamespaceURI())) {
            if (element.getLocalName().equals("tspan")) {
                ((SVGOMElement)element).setSVGContext(new TspanBridge(bridgeContext, this, element));
            }
            else if (element.getLocalName().equals("textPath")) {
                ((SVGOMElement)element).setSVGContext(new TextPathBridge(bridgeContext, this, element));
            }
            else if (element.getLocalName().equals("tref")) {
                ((SVGOMElement)element).setSVGContext(new TRefBridge(bridgeContext, this, element));
            }
        }
        for (Node node = this.getFirstChild(element); node != null; node = this.getNextSibling(node)) {
            if (node.getNodeType() == 1) {
                this.addContextToChild(bridgeContext, (Element)node);
            }
        }
    }
    
    protected void removeContextFromChild(final BridgeContext bridgeContext, final Element element) {
        if ("http://www.w3.org/2000/svg".equals(element.getNamespaceURI())) {
            if (element.getLocalName().equals("tspan")) {
                ((AbstractTextChildBridgeUpdateHandler)((SVGOMElement)element).getSVGContext()).dispose();
            }
            else if (element.getLocalName().equals("textPath")) {
                ((AbstractTextChildBridgeUpdateHandler)((SVGOMElement)element).getSVGContext()).dispose();
            }
            else if (element.getLocalName().equals("tref")) {
                ((AbstractTextChildBridgeUpdateHandler)((SVGOMElement)element).getSVGContext()).dispose();
            }
        }
        for (Node node = this.getFirstChild(element); node != null; node = this.getNextSibling(node)) {
            if (node.getNodeType() == 1) {
                this.removeContextFromChild(bridgeContext, (Element)node);
            }
        }
    }
    
    public void handleDOMNodeInsertedEvent(final MutationEvent mutationEvent) {
        final Node node = (Node)mutationEvent.getTarget();
        switch (node.getNodeType()) {
            case 3:
            case 4: {
                this.laidoutText = null;
                break;
            }
            case 1: {
                final Element element = (Element)node;
                if (this.isTextChild(element)) {
                    this.addContextToChild(this.ctx, element);
                    this.laidoutText = null;
                    break;
                }
                break;
            }
        }
        if (this.laidoutText == null) {
            this.computeLaidoutText(this.ctx, this.e, this.getTextNode());
        }
    }
    
    public void handleDOMChildNodeRemovedEvent(final MutationEvent mutationEvent) {
        final Node node = (Node)mutationEvent.getTarget();
        switch (node.getNodeType()) {
            case 3:
            case 4: {
                if (this.isParentDisplayed(node)) {
                    this.laidoutText = null;
                    break;
                }
                break;
            }
            case 1: {
                final Element element = (Element)node;
                if (this.isTextChild(element)) {
                    this.laidoutText = null;
                    this.removeContextFromChild(this.ctx, element);
                    break;
                }
                break;
            }
        }
    }
    
    public void handleDOMSubtreeModifiedEvent(final MutationEvent mutationEvent) {
        if (this.laidoutText == null) {
            this.computeLaidoutText(this.ctx, this.e, this.getTextNode());
        }
    }
    
    public void handleDOMCharacterDataModified(final MutationEvent mutationEvent) {
        if (this.isParentDisplayed((Node)mutationEvent.getTarget())) {
            this.laidoutText = null;
        }
    }
    
    protected boolean isParentDisplayed(final Node node) {
        return this.isTextElement((Element)this.getParentNode(node));
    }
    
    protected void computeLaidoutText(final BridgeContext bridgeContext, final Element element, final GraphicsNode graphicsNode) {
        final TextNode textNode = (TextNode)graphicsNode;
        this.elemTPI.clear();
        final AttributedString buildAttributedString = this.buildAttributedString(bridgeContext, element);
        if (buildAttributedString == null) {
            textNode.setAttributedCharacterIterator(null);
            return;
        }
        this.addGlyphPositionAttributes(buildAttributedString, element, bridgeContext);
        if (bridgeContext.isDynamic()) {
            this.laidoutText = new AttributedString(buildAttributedString.getIterator());
        }
        textNode.setAttributedCharacterIterator(buildAttributedString.getIterator());
        final TextPaintInfo textPaintInfo = new TextPaintInfo();
        this.setBaseTextPaintInfo(textPaintInfo, element, graphicsNode, bridgeContext);
        this.setDecorationTextPaintInfo(textPaintInfo, element);
        this.addPaintAttributes(buildAttributedString, element, textNode, textPaintInfo, bridgeContext);
        if (this.usingComplexSVGFont) {
            textNode.setAttributedCharacterIterator(buildAttributedString.getIterator());
        }
        if (bridgeContext.isDynamic()) {
            this.checkBBoxChange();
        }
    }
    
    public void handleAnimatedAttributeChanged(final AnimatedLiveAttributeValue animatedLiveAttributeValue) {
        if (animatedLiveAttributeValue.getNamespaceURI() == null) {
            final String localName = animatedLiveAttributeValue.getLocalName();
            if (localName.equals("x") || localName.equals("y") || localName.equals("dx") || localName.equals("dy") || localName.equals("rotate") || localName.equals("textLength") || localName.equals("lengthAdjust")) {
                final char char1 = localName.charAt(0);
                if (char1 == 'x' || char1 == 'y') {
                    this.getTextNode().setLocation(this.getLocation(this.ctx, this.e));
                }
                this.computeLaidoutText(this.ctx, this.e, this.getTextNode());
                return;
            }
        }
        super.handleAnimatedAttributeChanged(animatedLiveAttributeValue);
    }
    
    public void handleCSSEngineEvent(final CSSEngineEvent cssEngineEvent) {
        this.hasNewACI = false;
        final int[] properties = cssEngineEvent.getProperties();
        for (int i = 0; i < properties.length; ++i) {
            switch (properties[i]) {
                case 1:
                case 11:
                case 12:
                case 21:
                case 22:
                case 24:
                case 25:
                case 27:
                case 28:
                case 29:
                case 31:
                case 32:
                case 53:
                case 56:
                case 58:
                case 59: {
                    if (!this.hasNewACI) {
                        this.hasNewACI = true;
                        this.computeLaidoutText(this.ctx, this.e, this.getTextNode());
                        break;
                    }
                    break;
                }
            }
        }
        this.cssProceedElement = cssEngineEvent.getElement();
        super.handleCSSEngineEvent(cssEngineEvent);
        this.cssProceedElement = null;
    }
    
    protected void handleCSSPropertyChanged(final int n) {
        switch (n) {
            case 15:
            case 16:
            case 45:
            case 46:
            case 47:
            case 48:
            case 49:
            case 50:
            case 51:
            case 52:
            case 54: {
                this.rebuildACI();
                break;
            }
            case 57: {
                this.rebuildACI();
                super.handleCSSPropertyChanged(n);
                break;
            }
            case 55: {
                final RenderingHints convertTextRendering = CSSUtilities.convertTextRendering(this.e, this.node.getRenderingHints());
                if (convertTextRendering != null) {
                    this.node.setRenderingHints(convertTextRendering);
                    break;
                }
                break;
            }
            case 9: {
                final RenderingHints convertColorRendering = CSSUtilities.convertColorRendering(this.e, this.node.getRenderingHints());
                if (convertColorRendering != null) {
                    this.node.setRenderingHints(convertColorRendering);
                    break;
                }
                break;
            }
            default: {
                super.handleCSSPropertyChanged(n);
                break;
            }
        }
    }
    
    protected void rebuildACI() {
        if (this.hasNewACI) {
            return;
        }
        final TextNode textNode = this.getTextNode();
        if (textNode.getAttributedCharacterIterator() == null) {
            return;
        }
        TextPaintInfo textPaintInfo;
        TextPaintInfo textPaintInfo2;
        if (this.cssProceedElement == this.e) {
            textPaintInfo = new TextPaintInfo();
            this.setBaseTextPaintInfo(textPaintInfo, this.e, this.node, this.ctx);
            this.setDecorationTextPaintInfo(textPaintInfo, this.e);
            textPaintInfo2 = this.elemTPI.get(this.e);
        }
        else {
            textPaintInfo = this.getTextPaintInfo(this.cssProceedElement, textNode, this.getParentTextPaintInfo(this.cssProceedElement), this.ctx);
            textPaintInfo2 = this.elemTPI.get(this.cssProceedElement);
        }
        if (textPaintInfo2 == null) {
            return;
        }
        textNode.swapTextPaintInfo(textPaintInfo, textPaintInfo2);
        if (this.usingComplexSVGFont) {
            textNode.setAttributedCharacterIterator(textNode.getAttributedCharacterIterator());
        }
    }
    
    int getElementStartIndex(final Element key) {
        final TextPaintInfo textPaintInfo = this.elemTPI.get(key);
        if (textPaintInfo == null) {
            return -1;
        }
        return textPaintInfo.startChar;
    }
    
    int getElementEndIndex(final Element key) {
        final TextPaintInfo textPaintInfo = this.elemTPI.get(key);
        if (textPaintInfo == null) {
            return -1;
        }
        return textPaintInfo.endChar;
    }
    
    protected AttributedString buildAttributedString(final BridgeContext bridgeContext, final Element element) {
        final AttributedStringBuffer attributedStringBuffer = new AttributedStringBuffer();
        this.fillAttributedStringBuffer(bridgeContext, element, true, null, null, null, attributedStringBuffer);
        return attributedStringBuffer.toAttributedString();
    }
    
    protected void fillAttributedStringBuffer(final BridgeContext bridgeContext, final Element key, final boolean b, final TextPath textPath, final Integer n, Map attributeMap, final AttributedStringBuffer attributedStringBuffer) {
        if (!SVGUtilities.matchUserAgent(key, bridgeContext.getUserAgent()) || !CSSUtilities.convertDisplay(key)) {
            return;
        }
        final boolean equals = XMLSupport.getXMLSpace(key).equals("preserve");
        final int length = attributedStringBuffer.length();
        if (b) {
            this.endLimit = 0;
        }
        if (equals) {
            this.endLimit = attributedStringBuffer.length();
        }
        final HashMap hashMap = (attributeMap == null) ? new HashMap<Object, Object>() : new HashMap<Object, Object>((Map<? extends K, ? extends V>)attributeMap);
        attributeMap = (Map<? extends K, ? extends Integer>)this.getAttributeMap(bridgeContext, key, textPath, n, hashMap);
        final Object value = hashMap.get(TextAttribute.BIDI_EMBEDDING);
        Integer n2 = n;
        if (value != null) {
            n2 = (Integer)value;
        }
        for (Node node = this.getFirstChild(key); node != null; node = this.getNextSibling(node)) {
            final boolean b2 = !equals && (attributedStringBuffer.length() == 0 || attributedStringBuffer.getLastChar() == 32);
            switch (node.getNodeType()) {
                case 1: {
                    if (!"http://www.w3.org/2000/svg".equals(node.getNamespaceURI())) {
                        break;
                    }
                    final Element key2 = (Element)node;
                    final String localName = node.getLocalName();
                    if (localName.equals("tspan") || localName.equals("altGlyph")) {
                        final int count = attributedStringBuffer.count;
                        this.fillAttributedStringBuffer(bridgeContext, key2, false, textPath, n2, attributeMap, attributedStringBuffer);
                        if (attributedStringBuffer.count != count) {
                            attributeMap = null;
                            break;
                        }
                        break;
                    }
                    else if (localName.equals("textPath")) {
                        final TextPath textPath2 = ((SVGTextPathElementBridge)bridgeContext.getBridge(key2)).createTextPath(bridgeContext, key2);
                        if (textPath2 == null) {
                            break;
                        }
                        final int count2 = attributedStringBuffer.count;
                        this.fillAttributedStringBuffer(bridgeContext, key2, false, textPath2, n2, attributeMap, attributedStringBuffer);
                        if (attributedStringBuffer.count != count2) {
                            attributeMap = null;
                            break;
                        }
                        break;
                    }
                    else if (localName.equals("tref")) {
                        final String normalizeString = this.normalizeString(TextUtilities.getElementContent(bridgeContext.getReferencedElement((Element)node, XLinkSupport.getXLinkHref((Element)node))), equals, b2);
                        if (normalizeString.length() != 0) {
                            final int length2 = attributedStringBuffer.length();
                            final HashMap hashMap2 = (attributeMap == null) ? new HashMap() : new HashMap(attributeMap);
                            this.getAttributeMap(bridgeContext, key2, textPath, n, hashMap2);
                            attributedStringBuffer.append(normalizeString, hashMap2);
                            final int endChar = attributedStringBuffer.length() - 1;
                            final TextPaintInfo textPaintInfo = this.elemTPI.get(key2);
                            textPaintInfo.startChar = length2;
                            textPaintInfo.endChar = endChar;
                            attributeMap = null;
                            break;
                        }
                        break;
                    }
                    else {
                        if (!localName.equals("a")) {
                            break;
                        }
                        final NodeEventTarget nodeEventTarget = (NodeEventTarget)key2;
                        final SVGAElementBridge.AnchorListener anchorListener = new SVGAElementBridge.AnchorListener(bridgeContext.getUserAgent(), new SVGAElementBridge.CursorHolder(CursorManager.DEFAULT_CURSOR));
                        nodeEventTarget.addEventListenerNS("http://www.w3.org/2001/xml-events", "click", anchorListener, false, null);
                        bridgeContext.storeEventListenerNS(nodeEventTarget, "http://www.w3.org/2001/xml-events", "click", anchorListener, false);
                        final int count3 = attributedStringBuffer.count;
                        this.fillAttributedStringBuffer(bridgeContext, key2, false, textPath, n2, attributeMap, attributedStringBuffer);
                        if (attributedStringBuffer.count != count3) {
                            attributeMap = null;
                            break;
                        }
                        break;
                    }
                    break;
                }
                case 3:
                case 4: {
                    final String normalizeString2 = this.normalizeString(node.getNodeValue(), equals, b2);
                    if (normalizeString2.length() != 0) {
                        attributedStringBuffer.append(normalizeString2, hashMap);
                        if (equals) {
                            this.endLimit = attributedStringBuffer.length();
                        }
                        attributeMap = null;
                        break;
                    }
                    break;
                }
            }
        }
        if (b) {
            boolean b3 = false;
            while (this.endLimit < attributedStringBuffer.length() && attributedStringBuffer.getLastChar() == 32) {
                attributedStringBuffer.stripLast();
                b3 = true;
            }
            if (b3) {
                for (final TextPaintInfo textPaintInfo2 : this.elemTPI.values()) {
                    if (textPaintInfo2.endChar >= attributedStringBuffer.length()) {
                        textPaintInfo2.endChar = attributedStringBuffer.length() - 1;
                        if (textPaintInfo2.startChar <= textPaintInfo2.endChar) {
                            continue;
                        }
                        textPaintInfo2.startChar = textPaintInfo2.endChar;
                    }
                }
            }
        }
        final int endChar2 = attributedStringBuffer.length() - 1;
        final TextPaintInfo textPaintInfo3 = this.elemTPI.get(key);
        textPaintInfo3.startChar = length;
        textPaintInfo3.endChar = endChar2;
    }
    
    protected String normalizeString(final String s, final boolean b, final boolean b2) {
        final StringBuffer sb = new StringBuffer(s.length());
        if (b) {
            for (int i = 0; i < s.length(); ++i) {
                final char char1 = s.charAt(i);
                switch (char1) {
                    case 9:
                    case 10:
                    case 13: {
                        sb.append(' ');
                        break;
                    }
                    default: {
                        sb.append(char1);
                        break;
                    }
                }
            }
            return sb.toString();
        }
        int j = 0;
        Label_0177: {
            if (b2) {
                while (j < s.length()) {
                    switch (s.charAt(j)) {
                        default: {
                            break Label_0177;
                        }
                        case '\t':
                        case '\n':
                        case '\r':
                        case ' ': {
                            ++j;
                            continue;
                        }
                    }
                }
            }
        }
        int n = 0;
        for (int k = j; k < s.length(); ++k) {
            final char char2 = s.charAt(k);
            switch (char2) {
                case 10:
                case 13: {
                    break;
                }
                case 9:
                case 32: {
                    if (n == 0) {
                        sb.append(' ');
                        n = 1;
                        break;
                    }
                    break;
                }
                default: {
                    sb.append(char2);
                    n = 0;
                    break;
                }
            }
        }
        return sb.toString();
    }
    
    protected boolean nodeAncestorOf(final Node node, final Node node2) {
        if (node2 == null || node == null) {
            return false;
        }
        Node node3;
        for (node3 = this.getParentNode(node2); node3 != null && node3 != node; node3 = this.getParentNode(node3)) {}
        return node3 == node;
    }
    
    protected void addGlyphPositionAttributes(final AttributedString attributedString, final Element element, final BridgeContext bridgeContext) {
        if (!SVGUtilities.matchUserAgent(element, bridgeContext.getUserAgent()) || !CSSUtilities.convertDisplay(element)) {
            return;
        }
        if (element.getLocalName().equals("textPath")) {
            this.addChildGlyphPositionAttributes(attributedString, element, bridgeContext);
            return;
        }
        final int elementStartIndex = this.getElementStartIndex(element);
        if (elementStartIndex == -1) {
            return;
        }
        final int elementEndIndex = this.getElementEndIndex(element);
        if (!(element instanceof SVGTextPositioningElement)) {
            this.addChildGlyphPositionAttributes(attributedString, element, bridgeContext);
            return;
        }
        final SVGTextPositioningElement svgTextPositioningElement = (SVGTextPositioningElement)element;
        try {
            final SVGOMAnimatedLengthList list = (SVGOMAnimatedLengthList)svgTextPositioningElement.getX();
            list.check();
            final SVGOMAnimatedLengthList list2 = (SVGOMAnimatedLengthList)svgTextPositioningElement.getY();
            list2.check();
            final SVGOMAnimatedLengthList list3 = (SVGOMAnimatedLengthList)svgTextPositioningElement.getDx();
            list3.check();
            final SVGOMAnimatedLengthList list4 = (SVGOMAnimatedLengthList)svgTextPositioningElement.getDy();
            list4.check();
            final SVGOMAnimatedNumberList list5 = (SVGOMAnimatedNumberList)svgTextPositioningElement.getRotate();
            list5.check();
            final SVGLengthList animVal = list.getAnimVal();
            final SVGLengthList animVal2 = list2.getAnimVal();
            final SVGLengthList animVal3 = list3.getAnimVal();
            final SVGLengthList animVal4 = list4.getAnimVal();
            final SVGNumberList animVal5 = list5.getAnimVal();
            for (int numberOfItems = animVal.getNumberOfItems(), n = 0; n < numberOfItems && elementStartIndex + n <= elementEndIndex; ++n) {
                attributedString.addAttribute(GVTAttributedCharacterIterator.TextAttribute.X, new Float(animVal.getItem(n).getValue()), elementStartIndex + n, elementStartIndex + n + 1);
            }
            for (int numberOfItems2 = animVal2.getNumberOfItems(), n2 = 0; n2 < numberOfItems2 && elementStartIndex + n2 <= elementEndIndex; ++n2) {
                attributedString.addAttribute(GVTAttributedCharacterIterator.TextAttribute.Y, new Float(animVal2.getItem(n2).getValue()), elementStartIndex + n2, elementStartIndex + n2 + 1);
            }
            for (int numberOfItems3 = animVal3.getNumberOfItems(), n3 = 0; n3 < numberOfItems3 && elementStartIndex + n3 <= elementEndIndex; ++n3) {
                attributedString.addAttribute(GVTAttributedCharacterIterator.TextAttribute.DX, new Float(animVal3.getItem(n3).getValue()), elementStartIndex + n3, elementStartIndex + n3 + 1);
            }
            for (int numberOfItems4 = animVal4.getNumberOfItems(), n4 = 0; n4 < numberOfItems4 && elementStartIndex + n4 <= elementEndIndex; ++n4) {
                attributedString.addAttribute(GVTAttributedCharacterIterator.TextAttribute.DY, new Float(animVal4.getItem(n4).getValue()), elementStartIndex + n4, elementStartIndex + n4 + 1);
            }
            final int numberOfItems5 = animVal5.getNumberOfItems();
            if (numberOfItems5 == 1) {
                attributedString.addAttribute(GVTAttributedCharacterIterator.TextAttribute.ROTATION, new Float(Math.toRadians(animVal5.getItem(0).getValue())), elementStartIndex, elementEndIndex + 1);
            }
            else if (numberOfItems5 > 1) {
                for (int n5 = 0; n5 < numberOfItems5 && elementStartIndex + n5 <= elementEndIndex; ++n5) {
                    attributedString.addAttribute(GVTAttributedCharacterIterator.TextAttribute.ROTATION, new Float(Math.toRadians(animVal5.getItem(n5).getValue())), elementStartIndex + n5, elementStartIndex + n5 + 1);
                }
            }
            this.addChildGlyphPositionAttributes(attributedString, element, bridgeContext);
        }
        catch (LiveAttributeException ex) {
            throw new BridgeException(bridgeContext, ex);
        }
    }
    
    protected void addChildGlyphPositionAttributes(final AttributedString attributedString, final Element element, final BridgeContext bridgeContext) {
        for (Node node = this.getFirstChild(element); node != null; node = this.getNextSibling(node)) {
            if (node.getNodeType() == 1) {
                final Element element2 = (Element)node;
                if (this.isTextChild(element2)) {
                    this.addGlyphPositionAttributes(attributedString, element2, bridgeContext);
                }
            }
        }
    }
    
    protected void addPaintAttributes(final AttributedString attributedString, final Element key, final TextNode textNode, final TextPaintInfo textPaintInfo, final BridgeContext bridgeContext) {
        if (!SVGUtilities.matchUserAgent(key, bridgeContext.getUserAgent()) || !CSSUtilities.convertDisplay(key)) {
            return;
        }
        final TextPaintInfo value = this.elemTPI.get(key);
        if (value != null) {
            textNode.swapTextPaintInfo(textPaintInfo, value);
        }
        this.addChildPaintAttributes(attributedString, key, textNode, textPaintInfo, bridgeContext);
    }
    
    protected void addChildPaintAttributes(final AttributedString attributedString, final Element element, final TextNode textNode, final TextPaintInfo textPaintInfo, final BridgeContext bridgeContext) {
        for (Node node = this.getFirstChild(element); node != null; node = this.getNextSibling(node)) {
            if (node.getNodeType() == 1) {
                final Element element2 = (Element)node;
                if (this.isTextChild(element2)) {
                    this.addPaintAttributes(attributedString, element2, textNode, this.getTextPaintInfo(element2, textNode, textPaintInfo, bridgeContext), bridgeContext);
                }
            }
        }
    }
    
    protected List getFontList(final BridgeContext bridgeContext, final Element referent, final Map map) {
        map.put(SVGTextElementBridge.TEXT_COMPOUND_ID, new SoftReference<Element>(referent));
        final Float convertFontSize = TextUtilities.convertFontSize(referent);
        final float floatValue = convertFontSize;
        map.put(TextAttribute.SIZE, convertFontSize);
        map.put(TextAttribute.WIDTH, TextUtilities.convertFontStretch(referent));
        map.put(TextAttribute.POSTURE, TextUtilities.convertFontStyle(referent));
        map.put(TextAttribute.WEIGHT, TextUtilities.convertFontWeight(referent));
        final String cssText = CSSUtilities.getComputedStyle(referent, 27).getCssText();
        final String stringValue = CSSUtilities.getComputedStyle(referent, 25).getStringValue();
        map.put(SVGTextElementBridge.TEXT_COMPOUND_DELIMITER, referent);
        final Value computedStyle = CSSUtilities.getComputedStyle(referent, 21);
        final ArrayList<Element> list = new ArrayList<Element>();
        final ArrayList<GVTFont> list2 = new ArrayList<GVTFont>();
        for (int length = computedStyle.getLength(), i = 0; i < length; ++i) {
            GVTFontFamily gvtFontFamily = SVGFontUtilities.getFontFamily(referent, bridgeContext, computedStyle.item(i).getStringValue(), cssText, stringValue);
            if (gvtFontFamily != null) {
                if (gvtFontFamily instanceof UnresolvedFontFamily) {
                    gvtFontFamily = FontFamilyResolver.resolve((UnresolvedFontFamily)gvtFontFamily);
                    if (gvtFontFamily == null) {
                        continue;
                    }
                }
                list.add((Element)gvtFontFamily);
                if (gvtFontFamily instanceof SVGFontFamily && ((SVGFontFamily)gvtFontFamily).isComplex()) {
                    this.usingComplexSVGFont = true;
                }
                list2.add(gvtFontFamily.deriveFont(floatValue, map));
            }
        }
        map.put(SVGTextElementBridge.GVT_FONT_FAMILIES, list);
        if (!bridgeContext.isDynamic()) {
            map.remove(SVGTextElementBridge.TEXT_COMPOUND_DELIMITER);
        }
        return list2;
    }
    
    protected Map getAttributeMap(final BridgeContext bridgeContext, final Element key, final TextPath textPath, final Integer n, final Map map) {
        SVGTextContentElement svgTextContentElement = null;
        if (key instanceof SVGTextContentElement) {
            svgTextContentElement = (SVGTextContentElement)key;
        }
        Map<GVTAttributedCharacterIterator.TextAttribute, TextPaintInfo> map2 = null;
        if ("http://www.w3.org/2000/svg".equals(key.getNamespaceURI()) && key.getLocalName().equals("altGlyph")) {
            map.put(SVGTextElementBridge.ALT_GLYPH_HANDLER, new SVGAltGlyphHandler(bridgeContext, key));
        }
        final TextPaintInfo value = new TextPaintInfo();
        value.visible = true;
        value.fillPaint = Color.black;
        map.put(SVGTextElementBridge.PAINT_INFO, value);
        this.elemTPI.put(key, value);
        if (textPath != null) {
            map.put(SVGTextElementBridge.TEXTPATH, textPath);
        }
        map.put(SVGTextElementBridge.ANCHOR_TYPE, TextUtilities.convertTextAnchor(key));
        map.put(SVGTextElementBridge.GVT_FONTS, this.getFontList(bridgeContext, key, map));
        final Object convertBaselineShift = TextUtilities.convertBaselineShift(key);
        if (convertBaselineShift != null) {
            map.put(SVGTextElementBridge.BASELINE_SHIFT, convertBaselineShift);
        }
        final String stringValue = CSSUtilities.getComputedStyle(key, 56).getStringValue();
        if (stringValue.charAt(0) == 'n') {
            if (n != null) {
                map.put(TextAttribute.BIDI_EMBEDDING, n);
            }
        }
        else {
            final String stringValue2 = CSSUtilities.getComputedStyle(key, 11).getStringValue();
            int intValue = 0;
            if (n != null) {
                intValue = n;
            }
            if (intValue < 0) {
                intValue = -intValue;
            }
            switch (stringValue2.charAt(0)) {
                case 'l': {
                    map.put(TextAttribute.RUN_DIRECTION, TextAttribute.RUN_DIRECTION_LTR);
                    if ((intValue & 0x1) == 0x1) {
                        ++intValue;
                        break;
                    }
                    intValue += 2;
                    break;
                }
                case 'r': {
                    map.put(TextAttribute.RUN_DIRECTION, TextAttribute.RUN_DIRECTION_RTL);
                    if ((intValue & 0x1) == 0x1) {
                        intValue += 2;
                        break;
                    }
                    ++intValue;
                    break;
                }
            }
            switch (stringValue.charAt(0)) {
                case 'b': {
                    intValue = -intValue;
                    break;
                }
            }
            map.put(TextAttribute.BIDI_EMBEDDING, new Integer(intValue));
        }
        switch (CSSUtilities.getComputedStyle(key, 59).getStringValue().charAt(0)) {
            case 'l': {
                map.put(GVTAttributedCharacterIterator.TextAttribute.WRITING_MODE, GVTAttributedCharacterIterator.TextAttribute.WRITING_MODE_LTR);
                break;
            }
            case 'r': {
                map.put(GVTAttributedCharacterIterator.TextAttribute.WRITING_MODE, GVTAttributedCharacterIterator.TextAttribute.WRITING_MODE_RTL);
                break;
            }
            case 't': {
                map.put(GVTAttributedCharacterIterator.TextAttribute.WRITING_MODE, GVTAttributedCharacterIterator.TextAttribute.WRITING_MODE_TTB);
                break;
            }
        }
        final Value computedStyle = CSSUtilities.getComputedStyle(key, 29);
        final short primitiveType = computedStyle.getPrimitiveType();
        switch (primitiveType) {
            case 21: {
                map.put(GVTAttributedCharacterIterator.TextAttribute.VERTICAL_ORIENTATION, GVTAttributedCharacterIterator.TextAttribute.ORIENTATION_AUTO);
                break;
            }
            case 11: {
                map.put(GVTAttributedCharacterIterator.TextAttribute.VERTICAL_ORIENTATION, GVTAttributedCharacterIterator.TextAttribute.ORIENTATION_ANGLE);
                map.put(GVTAttributedCharacterIterator.TextAttribute.VERTICAL_ORIENTATION_ANGLE, new Float(computedStyle.getFloatValue()));
                break;
            }
            case 12: {
                map.put(GVTAttributedCharacterIterator.TextAttribute.VERTICAL_ORIENTATION, GVTAttributedCharacterIterator.TextAttribute.ORIENTATION_ANGLE);
                map.put(GVTAttributedCharacterIterator.TextAttribute.VERTICAL_ORIENTATION_ANGLE, new Float(Math.toDegrees(computedStyle.getFloatValue())));
                break;
            }
            case 13: {
                map.put(GVTAttributedCharacterIterator.TextAttribute.VERTICAL_ORIENTATION, GVTAttributedCharacterIterator.TextAttribute.ORIENTATION_ANGLE);
                map.put(GVTAttributedCharacterIterator.TextAttribute.VERTICAL_ORIENTATION_ANGLE, new Float(computedStyle.getFloatValue() * 9.0f / 5.0f));
                break;
            }
            default: {
                throw new IllegalStateException("unexpected primitiveType (V):" + primitiveType);
            }
        }
        final Value computedStyle2 = CSSUtilities.getComputedStyle(key, 28);
        final short primitiveType2 = computedStyle2.getPrimitiveType();
        switch (primitiveType2) {
            case 11: {
                map.put(GVTAttributedCharacterIterator.TextAttribute.HORIZONTAL_ORIENTATION_ANGLE, new Float(computedStyle2.getFloatValue()));
                break;
            }
            case 12: {
                map.put(GVTAttributedCharacterIterator.TextAttribute.HORIZONTAL_ORIENTATION_ANGLE, new Float(Math.toDegrees(computedStyle2.getFloatValue())));
                break;
            }
            case 13: {
                map.put(GVTAttributedCharacterIterator.TextAttribute.HORIZONTAL_ORIENTATION_ANGLE, new Float(computedStyle2.getFloatValue() * 9.0f / 5.0f));
                break;
            }
            default: {
                throw new IllegalStateException("unexpected primitiveType (H):" + primitiveType2);
            }
        }
        final Float convertLetterSpacing = TextUtilities.convertLetterSpacing(key);
        if (convertLetterSpacing != null) {
            map.put(GVTAttributedCharacterIterator.TextAttribute.LETTER_SPACING, convertLetterSpacing);
            map.put(GVTAttributedCharacterIterator.TextAttribute.CUSTOM_SPACING, Boolean.TRUE);
        }
        final Float convertWordSpacing = TextUtilities.convertWordSpacing(key);
        if (convertWordSpacing != null) {
            map.put(GVTAttributedCharacterIterator.TextAttribute.WORD_SPACING, convertWordSpacing);
            map.put(GVTAttributedCharacterIterator.TextAttribute.CUSTOM_SPACING, Boolean.TRUE);
        }
        final Float convertKerning = TextUtilities.convertKerning(key);
        if (convertKerning != null) {
            map.put(GVTAttributedCharacterIterator.TextAttribute.KERNING, convertKerning);
            map.put(GVTAttributedCharacterIterator.TextAttribute.CUSTOM_SPACING, Boolean.TRUE);
        }
        if (svgTextContentElement == null) {
            return map2;
        }
        try {
            final AbstractSVGAnimatedLength abstractSVGAnimatedLength = (AbstractSVGAnimatedLength)svgTextContentElement.getTextLength();
            if (abstractSVGAnimatedLength.isSpecified()) {
                if (map2 == null) {
                    map2 = new HashMap<GVTAttributedCharacterIterator.TextAttribute, TextPaintInfo>();
                }
                final Float n2 = new Float(abstractSVGAnimatedLength.getCheckedValue());
                map.put(GVTAttributedCharacterIterator.TextAttribute.BBOX_WIDTH, n2);
                map2.put(GVTAttributedCharacterIterator.TextAttribute.BBOX_WIDTH, (TextPaintInfo)n2);
                if (((SVGOMAnimatedEnumeration)svgTextContentElement.getLengthAdjust()).getCheckedVal() == 2) {
                    map.put(GVTAttributedCharacterIterator.TextAttribute.LENGTH_ADJUST, GVTAttributedCharacterIterator.TextAttribute.ADJUST_ALL);
                    map2.put(GVTAttributedCharacterIterator.TextAttribute.LENGTH_ADJUST, (TextPaintInfo)GVTAttributedCharacterIterator.TextAttribute.ADJUST_ALL);
                }
                else {
                    map.put(GVTAttributedCharacterIterator.TextAttribute.LENGTH_ADJUST, GVTAttributedCharacterIterator.TextAttribute.ADJUST_SPACING);
                    map2.put(GVTAttributedCharacterIterator.TextAttribute.LENGTH_ADJUST, (TextPaintInfo)GVTAttributedCharacterIterator.TextAttribute.ADJUST_SPACING);
                    map.put(GVTAttributedCharacterIterator.TextAttribute.CUSTOM_SPACING, Boolean.TRUE);
                    map2.put(GVTAttributedCharacterIterator.TextAttribute.CUSTOM_SPACING, (TextPaintInfo)Boolean.TRUE);
                }
            }
        }
        catch (LiveAttributeException ex) {
            throw new BridgeException(bridgeContext, ex);
        }
        return map2;
    }
    
    protected TextPaintInfo getParentTextPaintInfo(final Element element) {
        for (Node key = this.getParentNode(element); key != null; key = this.getParentNode(key)) {
            final TextPaintInfo textPaintInfo = this.elemTPI.get(key);
            if (textPaintInfo != null) {
                return textPaintInfo;
            }
        }
        return null;
    }
    
    protected TextPaintInfo getTextPaintInfo(final Element element, final GraphicsNode graphicsNode, final TextPaintInfo textPaintInfo, final BridgeContext bridgeContext) {
        CSSUtilities.getComputedStyle(element, 54);
        final TextPaintInfo textPaintInfo2 = new TextPaintInfo(textPaintInfo);
        final StyleMap computedStyleMap = ((CSSStylableElement)element).getComputedStyleMap(null);
        if (computedStyleMap.isNullCascaded(54) && computedStyleMap.isNullCascaded(15) && computedStyleMap.isNullCascaded(45) && computedStyleMap.isNullCascaded(52) && computedStyleMap.isNullCascaded(38)) {
            return textPaintInfo2;
        }
        this.setBaseTextPaintInfo(textPaintInfo2, element, graphicsNode, bridgeContext);
        if (!computedStyleMap.isNullCascaded(54)) {
            this.setDecorationTextPaintInfo(textPaintInfo2, element);
        }
        return textPaintInfo2;
    }
    
    public void setBaseTextPaintInfo(final TextPaintInfo textPaintInfo, final Element element, final GraphicsNode graphicsNode, final BridgeContext bridgeContext) {
        if (!element.getLocalName().equals("text")) {
            textPaintInfo.composite = CSSUtilities.convertOpacity(element);
        }
        else {
            textPaintInfo.composite = AlphaComposite.SrcOver;
        }
        textPaintInfo.visible = CSSUtilities.convertVisibility(element);
        textPaintInfo.fillPaint = PaintServer.convertFillPaint(element, graphicsNode, bridgeContext);
        textPaintInfo.strokePaint = PaintServer.convertStrokePaint(element, graphicsNode, bridgeContext);
        textPaintInfo.strokeStroke = PaintServer.convertStroke(element);
    }
    
    public void setDecorationTextPaintInfo(final TextPaintInfo textPaintInfo, final Element element) {
        final Value computedStyle = CSSUtilities.getComputedStyle(element, 54);
        switch (computedStyle.getCssValueType()) {
            case 2: {
                final ListValue listValue = (ListValue)computedStyle;
                for (int length = listValue.getLength(), i = 0; i < length; ++i) {
                    switch (listValue.item(i).getStringValue().charAt(0)) {
                        case 'u': {
                            if (textPaintInfo.fillPaint != null) {
                                textPaintInfo.underlinePaint = textPaintInfo.fillPaint;
                            }
                            if (textPaintInfo.strokePaint != null) {
                                textPaintInfo.underlineStrokePaint = textPaintInfo.strokePaint;
                            }
                            if (textPaintInfo.strokeStroke != null) {
                                textPaintInfo.underlineStroke = textPaintInfo.strokeStroke;
                                break;
                            }
                            break;
                        }
                        case 'o': {
                            if (textPaintInfo.fillPaint != null) {
                                textPaintInfo.overlinePaint = textPaintInfo.fillPaint;
                            }
                            if (textPaintInfo.strokePaint != null) {
                                textPaintInfo.overlineStrokePaint = textPaintInfo.strokePaint;
                            }
                            if (textPaintInfo.strokeStroke != null) {
                                textPaintInfo.overlineStroke = textPaintInfo.strokeStroke;
                                break;
                            }
                            break;
                        }
                        case 'l': {
                            if (textPaintInfo.fillPaint != null) {
                                textPaintInfo.strikethroughPaint = textPaintInfo.fillPaint;
                            }
                            if (textPaintInfo.strokePaint != null) {
                                textPaintInfo.strikethroughStrokePaint = textPaintInfo.strokePaint;
                            }
                            if (textPaintInfo.strokeStroke != null) {
                                textPaintInfo.strikethroughStroke = textPaintInfo.strokeStroke;
                                break;
                            }
                            break;
                        }
                    }
                }
                break;
            }
            default: {
                textPaintInfo.underlinePaint = null;
                textPaintInfo.underlineStrokePaint = null;
                textPaintInfo.underlineStroke = null;
                textPaintInfo.overlinePaint = null;
                textPaintInfo.overlineStrokePaint = null;
                textPaintInfo.overlineStroke = null;
                textPaintInfo.strikethroughPaint = null;
                textPaintInfo.strikethroughStrokePaint = null;
                textPaintInfo.strikethroughStroke = null;
                break;
            }
        }
    }
    
    public int getNumberOfChars() {
        return this.getNumberOfChars(this.e);
    }
    
    public Rectangle2D getExtentOfChar(final int n) {
        return this.getExtentOfChar(this.e, n);
    }
    
    public Point2D getStartPositionOfChar(final int n) {
        return this.getStartPositionOfChar(this.e, n);
    }
    
    public Point2D getEndPositionOfChar(final int n) {
        return this.getEndPositionOfChar(this.e, n);
    }
    
    public void selectSubString(final int n, final int n2) {
        this.selectSubString(this.e, n, n2);
    }
    
    public float getRotationOfChar(final int n) {
        return this.getRotationOfChar(this.e, n);
    }
    
    public float getComputedTextLength() {
        return this.getComputedTextLength(this.e);
    }
    
    public float getSubStringLength(final int n, final int n2) {
        return this.getSubStringLength(this.e, n, n2);
    }
    
    public int getCharNumAtPosition(final float n, final float n2) {
        return this.getCharNumAtPosition(this.e, n, n2);
    }
    
    protected int getNumberOfChars(final Element element) {
        if (this.getTextNode().getAttributedCharacterIterator() == null) {
            return 0;
        }
        final int elementStartIndex = this.getElementStartIndex(element);
        if (elementStartIndex == -1) {
            return 0;
        }
        return this.getElementEndIndex(element) - elementStartIndex + 1;
    }
    
    protected Rectangle2D getExtentOfChar(final Element element, final int n) {
        final TextNode textNode = this.getTextNode();
        final AttributedCharacterIterator attributedCharacterIterator = textNode.getAttributedCharacterIterator();
        if (attributedCharacterIterator == null) {
            return null;
        }
        final int elementStartIndex = this.getElementStartIndex(element);
        if (elementStartIndex == -1) {
            return null;
        }
        final CharacterInformation characterInformation = this.getCharacterInformation(this.getTextRuns(textNode), elementStartIndex, n, attributedCharacterIterator);
        if (characterInformation == null) {
            return null;
        }
        final GVTGlyphVector glyphVector = characterInformation.layout.getGlyphVector();
        Shape glyphCellBounds = null;
        if (characterInformation.glyphIndexStart == characterInformation.glyphIndexEnd) {
            if (glyphVector.isGlyphVisible(characterInformation.glyphIndexStart)) {
                glyphCellBounds = glyphVector.getGlyphCellBounds(characterInformation.glyphIndexStart);
            }
        }
        else {
            Path2D path2D = null;
            for (int i = characterInformation.glyphIndexStart; i <= characterInformation.glyphIndexEnd; ++i) {
                if (glyphVector.isGlyphVisible(i)) {
                    final Rectangle2D glyphCellBounds2 = glyphVector.getGlyphCellBounds(i);
                    if (path2D == null) {
                        path2D = new GeneralPath(glyphCellBounds2);
                    }
                    else {
                        path2D.append(glyphCellBounds2, false);
                    }
                }
            }
            glyphCellBounds = path2D;
        }
        if (glyphCellBounds == null) {
            return null;
        }
        return glyphCellBounds.getBounds2D();
    }
    
    protected Point2D getStartPositionOfChar(final Element element, final int n) {
        final TextNode textNode = this.getTextNode();
        final AttributedCharacterIterator attributedCharacterIterator = textNode.getAttributedCharacterIterator();
        if (attributedCharacterIterator == null) {
            return null;
        }
        final int elementStartIndex = this.getElementStartIndex(element);
        if (elementStartIndex == -1) {
            return null;
        }
        final CharacterInformation characterInformation = this.getCharacterInformation(this.getTextRuns(textNode), elementStartIndex, n, attributedCharacterIterator);
        if (characterInformation == null) {
            return null;
        }
        return this.getStartPoint(characterInformation);
    }
    
    protected Point2D getStartPoint(final CharacterInformation characterInformation) {
        final GVTGlyphVector glyphVector = characterInformation.layout.getGlyphVector();
        if (!glyphVector.isGlyphVisible(characterInformation.glyphIndexStart)) {
            return null;
        }
        final Point2D glyphPosition = glyphVector.getGlyphPosition(characterInformation.glyphIndexStart);
        final AffineTransform glyphTransform = glyphVector.getGlyphTransform(characterInformation.glyphIndexStart);
        final Point2D.Float float1 = new Point2D.Float(0.0f, 0.0f);
        if (glyphTransform != null) {
            glyphTransform.transform(float1, float1);
        }
        final Point2D.Float float2 = float1;
        float2.x += (float)glyphPosition.getX();
        final Point2D.Float float3 = float1;
        float3.y += (float)glyphPosition.getY();
        return float1;
    }
    
    protected Point2D getEndPositionOfChar(final Element element, final int n) {
        final TextNode textNode = this.getTextNode();
        final AttributedCharacterIterator attributedCharacterIterator = textNode.getAttributedCharacterIterator();
        if (attributedCharacterIterator == null) {
            return null;
        }
        final int elementStartIndex = this.getElementStartIndex(element);
        if (elementStartIndex == -1) {
            return null;
        }
        final CharacterInformation characterInformation = this.getCharacterInformation(this.getTextRuns(textNode), elementStartIndex, n, attributedCharacterIterator);
        if (characterInformation == null) {
            return null;
        }
        return this.getEndPoint(characterInformation);
    }
    
    protected Point2D getEndPoint(final CharacterInformation characterInformation) {
        final GVTGlyphVector glyphVector = characterInformation.layout.getGlyphVector();
        if (!glyphVector.isGlyphVisible(characterInformation.glyphIndexEnd)) {
            return null;
        }
        final Point2D glyphPosition = glyphVector.getGlyphPosition(characterInformation.glyphIndexEnd);
        final AffineTransform glyphTransform = glyphVector.getGlyphTransform(characterInformation.glyphIndexEnd);
        final Point2D.Float float1 = new Point2D.Float(glyphVector.getGlyphMetrics(characterInformation.glyphIndexEnd).getHorizontalAdvance(), 0.0f);
        if (glyphTransform != null) {
            glyphTransform.transform(float1, float1);
        }
        final Point2D.Float float2 = float1;
        float2.x += (float)glyphPosition.getX();
        final Point2D.Float float3 = float1;
        float3.y += (float)glyphPosition.getY();
        return float1;
    }
    
    protected float getRotationOfChar(final Element element, final int n) {
        final TextNode textNode = this.getTextNode();
        final AttributedCharacterIterator attributedCharacterIterator = textNode.getAttributedCharacterIterator();
        if (attributedCharacterIterator == null) {
            return 0.0f;
        }
        final int elementStartIndex = this.getElementStartIndex(element);
        if (elementStartIndex == -1) {
            return 0.0f;
        }
        final CharacterInformation characterInformation = this.getCharacterInformation(this.getTextRuns(textNode), elementStartIndex, n, attributedCharacterIterator);
        double n2 = 0.0;
        int n3 = 0;
        if (characterInformation != null) {
            final GVTGlyphVector glyphVector = characterInformation.layout.getGlyphVector();
            for (int i = characterInformation.glyphIndexStart; i <= characterInformation.glyphIndexEnd; ++i) {
                if (glyphVector.isGlyphVisible(i)) {
                    ++n3;
                    final AffineTransform glyphTransform = glyphVector.getGlyphTransform(i);
                    if (glyphTransform != null) {
                        final double scaleX = glyphTransform.getScaleX();
                        final double shearX = glyphTransform.getShearX();
                        double atan;
                        if (scaleX == 0.0) {
                            if (shearX > 0.0) {
                                atan = 3.141592653589793;
                            }
                            else {
                                atan = -3.141592653589793;
                            }
                        }
                        else {
                            atan = Math.atan(shearX / scaleX);
                            if (scaleX < 0.0) {
                                atan += 3.141592653589793;
                            }
                        }
                        n2 += Math.toDegrees(-atan) % 360.0 - characterInformation.getComputedOrientationAngle();
                    }
                }
            }
        }
        if (n3 == 0) {
            return 0.0f;
        }
        return (float)(n2 / n3);
    }
    
    protected float getComputedTextLength(final Element element) {
        return this.getSubStringLength(element, 0, this.getNumberOfChars(element));
    }
    
    protected float getSubStringLength(final Element element, final int n, final int n2) {
        if (n2 == 0) {
            return 0.0f;
        }
        float n3 = 0.0f;
        final TextNode textNode = this.getTextNode();
        final AttributedCharacterIterator attributedCharacterIterator = textNode.getAttributedCharacterIterator();
        if (attributedCharacterIterator == null) {
            return -1.0f;
        }
        final int elementStartIndex = this.getElementStartIndex(element);
        if (elementStartIndex == -1) {
            return -1.0f;
        }
        final List textRuns = this.getTextRuns(textNode);
        CharacterInformation characterInformation = this.getCharacterInformation(textRuns, elementStartIndex, n, attributedCharacterIterator);
        CharacterInformation characterInformation2 = null;
        int n4 = characterInformation.characterIndex + 1;
        GVTGlyphVector gvtGlyphVector = characterInformation.layout.getGlyphVector();
        float[] array = characterInformation.layout.getGlyphAdvances();
        boolean[] array2 = new boolean[array.length];
        for (int i = n + 1; i < n + n2; ++i) {
            if (characterInformation.layout.isOnATextPath()) {
                for (int j = characterInformation.glyphIndexStart; j <= characterInformation.glyphIndexEnd; ++j) {
                    if (gvtGlyphVector.isGlyphVisible(j) && !array2[j]) {
                        n3 += array[j + 1] - array[j];
                    }
                    array2[j] = true;
                }
                final CharacterInformation characterInformation3 = this.getCharacterInformation(textRuns, elementStartIndex, i, attributedCharacterIterator);
                if (characterInformation3.layout != characterInformation.layout) {
                    gvtGlyphVector = characterInformation3.layout.getGlyphVector();
                    array = characterInformation3.layout.getGlyphAdvances();
                    array2 = new boolean[array.length];
                    n4 = characterInformation.characterIndex + 1;
                }
                characterInformation = characterInformation3;
            }
            else if (characterInformation.layout.hasCharacterIndex(n4)) {
                ++n4;
            }
            else {
                n3 += this.distanceFirstLastCharacterInRun(characterInformation, this.getCharacterInformation(textRuns, elementStartIndex, i - 1, attributedCharacterIterator));
                characterInformation = this.getCharacterInformation(textRuns, elementStartIndex, i, attributedCharacterIterator);
                n4 = characterInformation.characterIndex + 1;
                gvtGlyphVector = characterInformation.layout.getGlyphVector();
                array = characterInformation.layout.getGlyphAdvances();
                array2 = new boolean[array.length];
                characterInformation2 = null;
            }
        }
        if (characterInformation.layout.isOnATextPath()) {
            for (int k = characterInformation.glyphIndexStart; k <= characterInformation.glyphIndexEnd; ++k) {
                if (gvtGlyphVector.isGlyphVisible(k) && !array2[k]) {
                    n3 += array[k + 1] - array[k];
                }
                array2[k] = true;
            }
        }
        else {
            if (characterInformation2 == null) {
                characterInformation2 = this.getCharacterInformation(textRuns, elementStartIndex, n + n2 - 1, attributedCharacterIterator);
            }
            n3 += this.distanceFirstLastCharacterInRun(characterInformation, characterInformation2);
        }
        return n3;
    }
    
    protected float distanceFirstLastCharacterInRun(final CharacterInformation characterInformation, final CharacterInformation characterInformation2) {
        final float[] glyphAdvances = characterInformation.layout.getGlyphAdvances();
        final int glyphIndexStart = characterInformation.glyphIndexStart;
        final int glyphIndexEnd = characterInformation.glyphIndexEnd;
        final int glyphIndexStart2 = characterInformation2.glyphIndexStart;
        final int glyphIndexEnd2 = characterInformation2.glyphIndexEnd;
        return glyphAdvances[((glyphIndexEnd < glyphIndexEnd2) ? glyphIndexEnd2 : glyphIndexEnd) + 1] - glyphAdvances[(glyphIndexStart < glyphIndexStart2) ? glyphIndexStart : glyphIndexStart2];
    }
    
    protected float distanceBetweenRun(final CharacterInformation characterInformation, final CharacterInformation characterInformation2) {
        final CharacterInformation characterInformation3 = new CharacterInformation();
        characterInformation3.layout = characterInformation.layout;
        characterInformation3.glyphIndexEnd = characterInformation.layout.getGlyphCount() - 1;
        final Point2D endPoint = this.getEndPoint(characterInformation3);
        characterInformation3.layout = characterInformation2.layout;
        characterInformation3.glyphIndexStart = 0;
        final Point2D startPoint = this.getStartPoint(characterInformation3);
        float n;
        if (characterInformation2.isVertical()) {
            n = (float)(startPoint.getY() - endPoint.getY());
        }
        else {
            n = (float)(startPoint.getX() - endPoint.getX());
        }
        return n;
    }
    
    protected void selectSubString(final Element element, final int n, final int n2) {
        final TextNode textNode = this.getTextNode();
        final AttributedCharacterIterator attributedCharacterIterator = textNode.getAttributedCharacterIterator();
        if (attributedCharacterIterator == null) {
            return;
        }
        final int elementStartIndex = this.getElementStartIndex(element);
        if (elementStartIndex == -1) {
            return;
        }
        final List textRuns = this.getTextRuns(textNode);
        final int elementEndIndex = this.getElementEndIndex(element);
        final CharacterInformation characterInformation = this.getCharacterInformation(textRuns, elementStartIndex, n, attributedCharacterIterator);
        final CharacterInformation characterInformation2 = this.getCharacterInformation(textRuns, elementStartIndex, n + n2 - 1, attributedCharacterIterator);
        final Mark markerForChar = textNode.getMarkerForChar(characterInformation.characterIndex, true);
        Mark mark;
        if (characterInformation2 != null && characterInformation2.characterIndex <= elementEndIndex) {
            mark = textNode.getMarkerForChar(characterInformation2.characterIndex, false);
        }
        else {
            mark = textNode.getMarkerForChar(elementEndIndex, false);
        }
        this.ctx.getUserAgent().setTextSelection(markerForChar, mark);
    }
    
    protected int getCharNumAtPosition(final Element element, final float n, final float n2) {
        final TextNode textNode = this.getTextNode();
        if (textNode.getAttributedCharacterIterator() == null) {
            return -1;
        }
        final List textRuns = this.getTextRuns(textNode);
        TextHit hitTestChar = null;
        for (int n3 = textRuns.size() - 1; n3 >= 0 && hitTestChar == null; hitTestChar = textRuns.get(n3).getLayout().hitTestChar(n, n2), --n3) {}
        if (hitTestChar == null) {
            return -1;
        }
        final int elementStartIndex = this.getElementStartIndex(element);
        final int elementEndIndex = this.getElementEndIndex(element);
        final int charIndex = hitTestChar.getCharIndex();
        if (charIndex >= elementStartIndex && charIndex <= elementEndIndex) {
            return charIndex - elementStartIndex;
        }
        return -1;
    }
    
    protected List getTextRuns(final TextNode textNode) {
        if (textNode.getTextRuns() == null) {
            textNode.getPrimitiveBounds();
        }
        return textNode.getTextRuns();
    }
    
    protected CharacterInformation getCharacterInformation(final List list, final int n, final int n2, final AttributedCharacterIterator attributedCharacterIterator) {
        final CharacterInformation characterInformation = new CharacterInformation();
        characterInformation.characterIndex = n + n2;
        for (int i = 0; i < list.size(); ++i) {
            final StrokingTextPainter.TextRun textRun = list.get(i);
            if (textRun.getLayout().hasCharacterIndex(characterInformation.characterIndex)) {
                characterInformation.layout = textRun.getLayout();
                attributedCharacterIterator.setIndex(characterInformation.characterIndex);
                if (attributedCharacterIterator.getAttribute(SVGTextElementBridge.ALT_GLYPH_HANDLER) != null) {
                    characterInformation.glyphIndexStart = 0;
                    characterInformation.glyphIndexEnd = characterInformation.layout.getGlyphCount() - 1;
                }
                else {
                    characterInformation.glyphIndexStart = characterInformation.layout.getGlyphIndex(characterInformation.characterIndex);
                    if (characterInformation.glyphIndexStart == -1) {
                        characterInformation.glyphIndexStart = 0;
                        characterInformation.glyphIndexEnd = characterInformation.layout.getGlyphCount() - 1;
                    }
                    else {
                        characterInformation.glyphIndexEnd = characterInformation.glyphIndexStart;
                    }
                }
                return characterInformation;
            }
        }
        return null;
    }
    
    public Set getTextIntersectionSet(final AffineTransform affineTransform, final Rectangle2D rectangle2D) {
        final HashSet set = new HashSet<Element>();
        final List textRuns = this.getTextNode().getTextRuns();
        if (textRuns == null) {
            return set;
        }
        for (int i = 0; i < textRuns.size(); ++i) {
            final StrokingTextPainter.TextRun textRun = textRuns.get(i);
            final TextSpanLayout layout = textRun.getLayout();
            final AttributedCharacterIterator aci = textRun.getACI();
            aci.first();
            final Element element = ((SoftReference)aci.getAttribute(SVGTextElementBridge.TEXT_COMPOUND_ID)).get();
            if (element != null) {
                if (!set.contains(element)) {
                    if (isTextSensitive(element)) {
                        final Rectangle2D bounds2D = layout.getBounds2D();
                        if (bounds2D == null || rectangle2D.intersects(affineTransform.createTransformedShape(bounds2D).getBounds2D())) {
                            final GVTGlyphVector glyphVector = layout.getGlyphVector();
                            for (int j = 0; j < glyphVector.getNumGlyphs(); ++j) {
                                final Shape glyphLogicalBounds = glyphVector.getGlyphLogicalBounds(j);
                                if (glyphLogicalBounds != null && affineTransform.createTransformedShape(glyphLogicalBounds).getBounds2D().intersects(rectangle2D)) {
                                    set.add(element);
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        }
        return set;
    }
    
    public Set getTextEnclosureSet(final AffineTransform affineTransform, final Rectangle2D rectangle2D) {
        final TextNode textNode = this.getTextNode();
        final HashSet<Element> set = new HashSet<Element>();
        final List textRuns = textNode.getTextRuns();
        if (textRuns == null) {
            return set;
        }
        final HashSet set2 = new HashSet<Element>();
        for (int i = 0; i < textRuns.size(); ++i) {
            final StrokingTextPainter.TextRun textRun = textRuns.get(i);
            final TextSpanLayout layout = textRun.getLayout();
            final AttributedCharacterIterator aci = textRun.getACI();
            aci.first();
            final Element element = ((SoftReference)aci.getAttribute(SVGTextElementBridge.TEXT_COMPOUND_ID)).get();
            if (element != null) {
                if (!set2.contains(element)) {
                    if (!isTextSensitive(element)) {
                        set2.add(element);
                    }
                    else {
                        final Rectangle2D bounds2D = layout.getBounds2D();
                        if (bounds2D != null) {
                            if (rectangle2D.contains(affineTransform.createTransformedShape(bounds2D).getBounds2D())) {
                                set.add(element);
                            }
                            else {
                                set2.add(element);
                                set.remove(element);
                            }
                        }
                    }
                }
            }
        }
        return set;
    }
    
    public static boolean getTextIntersection(final BridgeContext bridgeContext, final Element element, final AffineTransform tx, final Rectangle2D rectangle2D, final boolean b) {
        SVGContext svgContext = null;
        if (element instanceof SVGOMElement) {
            svgContext = ((SVGOMElement)element).getSVGContext();
        }
        if (svgContext == null) {
            return false;
        }
        SVGTextElementBridge textBridge = null;
        if (svgContext instanceof SVGTextElementBridge) {
            textBridge = (SVGTextElementBridge)svgContext;
        }
        else if (svgContext instanceof AbstractTextChildSVGContext) {
            textBridge = ((AbstractTextChildSVGContext)svgContext).getTextBridge();
        }
        if (textBridge == null) {
            return false;
        }
        final TextNode textNode = textBridge.getTextNode();
        final List textRuns = textNode.getTextRuns();
        if (textRuns == null) {
            return false;
        }
        final Element e = textBridge.e;
        final AffineTransform globalTransform = textNode.getGlobalTransform();
        globalTransform.preConcatenate(tx);
        if (!rectangle2D.intersects(globalTransform.createTransformedShape(textNode.getBounds()).getBounds2D())) {
            return false;
        }
        for (int i = 0; i < textRuns.size(); ++i) {
            final StrokingTextPainter.TextRun textRun = textRuns.get(i);
            final TextSpanLayout layout = textRun.getLayout();
            final AttributedCharacterIterator aci = textRun.getACI();
            aci.first();
            final Element element2 = ((SoftReference)aci.getAttribute(SVGTextElementBridge.TEXT_COMPOUND_ID)).get();
            if (element2 != null) {
                if (!b || isTextSensitive(element2)) {
                    Element element3;
                    for (element3 = element2; element3 != null && element3 != e && element3 != element; element3 = (Element)textBridge.getParentNode(element3)) {}
                    if (element3 == element) {
                        final Rectangle2D bounds2D = layout.getBounds2D();
                        if (bounds2D != null) {
                            if (rectangle2D.intersects(globalTransform.createTransformedShape(bounds2D).getBounds2D())) {
                                final GVTGlyphVector glyphVector = layout.getGlyphVector();
                                for (int j = 0; j < glyphVector.getNumGlyphs(); ++j) {
                                    final Shape glyphLogicalBounds = glyphVector.getGlyphLogicalBounds(j);
                                    if (glyphLogicalBounds != null && globalTransform.createTransformedShape(glyphLogicalBounds).getBounds2D().intersects(rectangle2D)) {
                                        return true;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return false;
    }
    
    public static Rectangle2D getTextBounds(final BridgeContext bridgeContext, final Element element, final boolean b) {
        SVGContext svgContext = null;
        if (element instanceof SVGOMElement) {
            svgContext = ((SVGOMElement)element).getSVGContext();
        }
        if (svgContext == null) {
            return null;
        }
        SVGTextElementBridge textBridge = null;
        if (svgContext instanceof SVGTextElementBridge) {
            textBridge = (SVGTextElementBridge)svgContext;
        }
        else if (svgContext instanceof AbstractTextChildSVGContext) {
            textBridge = ((AbstractTextChildSVGContext)svgContext).getTextBridge();
        }
        if (textBridge == null) {
            return null;
        }
        final List textRuns = textBridge.getTextNode().getTextRuns();
        if (textRuns == null) {
            return null;
        }
        final Element e = textBridge.e;
        Rectangle2D rectangle2D = null;
        for (int i = 0; i < textRuns.size(); ++i) {
            final StrokingTextPainter.TextRun textRun = textRuns.get(i);
            final TextSpanLayout layout = textRun.getLayout();
            final AttributedCharacterIterator aci = textRun.getACI();
            aci.first();
            final Element element2 = ((SoftReference)aci.getAttribute(SVGTextElementBridge.TEXT_COMPOUND_ID)).get();
            if (element2 != null) {
                if (!b || isTextSensitive(element2)) {
                    Element element3;
                    for (element3 = element2; element3 != null && element3 != e && element3 != element; element3 = (Element)textBridge.getParentNode(element3)) {}
                    if (element3 == element) {
                        final Rectangle2D bounds2D = layout.getBounds2D();
                        if (bounds2D != null) {
                            if (rectangle2D == null) {
                                rectangle2D = (Rectangle2D)bounds2D.clone();
                            }
                            else {
                                rectangle2D.add(bounds2D);
                            }
                        }
                    }
                }
            }
        }
        return rectangle2D;
    }
    
    public static boolean isTextSensitive(final Element element) {
        switch (CSSUtilities.convertPointerEvents(element)) {
            case 0:
            case 1:
            case 2:
            case 3: {
                return CSSUtilities.convertVisibility(element);
            }
            case 4:
            case 5:
            case 6:
            case 7: {
                return true;
            }
            default: {
                return false;
            }
        }
    }
    
    static {
        ZERO = new Integer(0);
        TEXT_COMPOUND_DELIMITER = GVTAttributedCharacterIterator.TextAttribute.TEXT_COMPOUND_DELIMITER;
        TEXT_COMPOUND_ID = GVTAttributedCharacterIterator.TextAttribute.TEXT_COMPOUND_ID;
        PAINT_INFO = GVTAttributedCharacterIterator.TextAttribute.PAINT_INFO;
        ALT_GLYPH_HANDLER = GVTAttributedCharacterIterator.TextAttribute.ALT_GLYPH_HANDLER;
        TEXTPATH = GVTAttributedCharacterIterator.TextAttribute.TEXTPATH;
        ANCHOR_TYPE = GVTAttributedCharacterIterator.TextAttribute.ANCHOR_TYPE;
        GVT_FONT_FAMILIES = GVTAttributedCharacterIterator.TextAttribute.GVT_FONT_FAMILIES;
        GVT_FONTS = GVTAttributedCharacterIterator.TextAttribute.GVT_FONTS;
        BASELINE_SHIFT = GVTAttributedCharacterIterator.TextAttribute.BASELINE_SHIFT;
    }
    
    protected static class CharacterInformation
    {
        TextSpanLayout layout;
        int glyphIndexStart;
        int glyphIndexEnd;
        int characterIndex;
        
        public boolean isVertical() {
            return this.layout.isVertical();
        }
        
        public double getComputedOrientationAngle() {
            return this.layout.getComputedOrientationAngle(this.characterIndex);
        }
    }
    
    protected class TspanBridge extends AbstractTextChildTextContent
    {
        protected TspanBridge(final BridgeContext bridgeContext, final SVGTextElementBridge svgTextElementBridge, final Element element) {
            super(bridgeContext, svgTextElementBridge, element);
        }
        
        public void handleAnimatedAttributeChanged(final AnimatedLiveAttributeValue animatedLiveAttributeValue) {
            if (animatedLiveAttributeValue.getNamespaceURI() == null) {
                final String localName = animatedLiveAttributeValue.getLocalName();
                if (localName.equals("x") || localName.equals("y") || localName.equals("dx") || localName.equals("dy") || localName.equals("rotate") || localName.equals("textLength") || localName.equals("lengthAdjust")) {
                    this.textBridge.computeLaidoutText(this.ctx, this.textBridge.e, this.textBridge.getTextNode());
                    return;
                }
            }
            super.handleAnimatedAttributeChanged(animatedLiveAttributeValue);
        }
    }
    
    protected class AbstractTextChildTextContent extends AbstractTextChildBridgeUpdateHandler implements SVGTextContent
    {
        protected AbstractTextChildTextContent(final BridgeContext bridgeContext, final SVGTextElementBridge svgTextElementBridge, final Element element) {
            super(bridgeContext, svgTextElementBridge, element);
        }
        
        public int getNumberOfChars() {
            return this.textBridge.getNumberOfChars(this.e);
        }
        
        public Rectangle2D getExtentOfChar(final int n) {
            return this.textBridge.getExtentOfChar(this.e, n);
        }
        
        public Point2D getStartPositionOfChar(final int n) {
            return this.textBridge.getStartPositionOfChar(this.e, n);
        }
        
        public Point2D getEndPositionOfChar(final int n) {
            return this.textBridge.getEndPositionOfChar(this.e, n);
        }
        
        public void selectSubString(final int n, final int n2) {
            this.textBridge.selectSubString(this.e, n, n2);
        }
        
        public float getRotationOfChar(final int n) {
            return this.textBridge.getRotationOfChar(this.e, n);
        }
        
        public float getComputedTextLength() {
            return this.textBridge.getComputedTextLength(this.e);
        }
        
        public float getSubStringLength(final int n, final int n2) {
            return this.textBridge.getSubStringLength(this.e, n, n2);
        }
        
        public int getCharNumAtPosition(final float n, final float n2) {
            return this.textBridge.getCharNumAtPosition(this.e, n, n2);
        }
    }
    
    protected abstract class AbstractTextChildBridgeUpdateHandler extends AbstractTextChildSVGContext implements BridgeUpdateHandler
    {
        protected AbstractTextChildBridgeUpdateHandler(final BridgeContext bridgeContext, final SVGTextElementBridge svgTextElementBridge, final Element element) {
            super(bridgeContext, svgTextElementBridge, element);
        }
        
        public void handleDOMAttrModifiedEvent(final MutationEvent mutationEvent) {
        }
        
        public void handleDOMNodeInsertedEvent(final MutationEvent mutationEvent) {
            this.textBridge.handleDOMNodeInsertedEvent(mutationEvent);
        }
        
        public void handleDOMNodeRemovedEvent(final MutationEvent mutationEvent) {
        }
        
        public void handleDOMCharacterDataModified(final MutationEvent mutationEvent) {
            this.textBridge.handleDOMCharacterDataModified(mutationEvent);
        }
        
        public void handleCSSEngineEvent(final CSSEngineEvent cssEngineEvent) {
            this.textBridge.handleCSSEngineEvent(cssEngineEvent);
        }
        
        public void handleAnimatedAttributeChanged(final AnimatedLiveAttributeValue animatedLiveAttributeValue) {
        }
        
        public void handleOtherAnimationChanged(final String s) {
        }
        
        public void dispose() {
            ((SVGOMElement)this.e).setSVGContext(null);
            SVGTextElementBridge.this.elemTPI.remove(this.e);
        }
    }
    
    public abstract class AbstractTextChildSVGContext extends AnimatableSVGBridge
    {
        protected SVGTextElementBridge textBridge;
        
        public AbstractTextChildSVGContext(final BridgeContext ctx, final SVGTextElementBridge textBridge, final Element e) {
            this.ctx = ctx;
            this.textBridge = textBridge;
            this.e = e;
        }
        
        public String getNamespaceURI() {
            return null;
        }
        
        public String getLocalName() {
            return null;
        }
        
        public Bridge getInstance() {
            return null;
        }
        
        public SVGTextElementBridge getTextBridge() {
            return this.textBridge;
        }
        
        public float getPixelUnitToMillimeter() {
            return this.ctx.getUserAgent().getPixelUnitToMillimeter();
        }
        
        public float getPixelToMM() {
            return this.getPixelUnitToMillimeter();
        }
        
        public Rectangle2D getBBox() {
            return null;
        }
        
        public AffineTransform getCTM() {
            return null;
        }
        
        public AffineTransform getGlobalTransform() {
            return null;
        }
        
        public AffineTransform getScreenTransform() {
            return null;
        }
        
        public void setScreenTransform(final AffineTransform affineTransform) {
        }
        
        public float getViewportWidth() {
            return this.ctx.getBlockWidth(this.e);
        }
        
        public float getViewportHeight() {
            return this.ctx.getBlockHeight(this.e);
        }
        
        public float getFontSize() {
            return CSSUtilities.getComputedStyle(this.e, 22).getFloatValue();
        }
    }
    
    protected class TextPathBridge extends AbstractTextChildTextContent
    {
        protected TextPathBridge(final BridgeContext bridgeContext, final SVGTextElementBridge svgTextElementBridge, final Element element) {
            super(bridgeContext, svgTextElementBridge, element);
        }
    }
    
    protected class TRefBridge extends AbstractTextChildTextContent
    {
        protected TRefBridge(final BridgeContext bridgeContext, final SVGTextElementBridge svgTextElementBridge, final Element element) {
            super(bridgeContext, svgTextElementBridge, element);
        }
        
        public void handleAnimatedAttributeChanged(final AnimatedLiveAttributeValue animatedLiveAttributeValue) {
            if (animatedLiveAttributeValue.getNamespaceURI() == null) {
                final String localName = animatedLiveAttributeValue.getLocalName();
                if (localName.equals("x") || localName.equals("y") || localName.equals("dx") || localName.equals("dy") || localName.equals("rotate") || localName.equals("textLength") || localName.equals("lengthAdjust")) {
                    this.textBridge.computeLaidoutText(this.ctx, this.textBridge.e, this.textBridge.getTextNode());
                    return;
                }
            }
            super.handleAnimatedAttributeChanged(animatedLiveAttributeValue);
        }
    }
    
    protected static class AttributedStringBuffer
    {
        protected List strings;
        protected List attributes;
        protected int count;
        protected int length;
        
        public AttributedStringBuffer() {
            this.strings = new ArrayList();
            this.attributes = new ArrayList();
            this.count = 0;
            this.length = 0;
        }
        
        public boolean isEmpty() {
            return this.count == 0;
        }
        
        public int length() {
            return this.length;
        }
        
        public void append(final String s, final Map map) {
            if (s.length() == 0) {
                return;
            }
            this.strings.add(s);
            this.attributes.add(map);
            ++this.count;
            this.length += s.length();
        }
        
        public int getLastChar() {
            if (this.count == 0) {
                return -1;
            }
            final String s = this.strings.get(this.count - 1);
            return s.charAt(s.length() - 1);
        }
        
        public void stripFirst() {
            final String s = this.strings.get(0);
            if (s.charAt(s.length() - 1) != ' ') {
                return;
            }
            --this.length;
            if (s.length() == 1) {
                this.attributes.remove(0);
                this.strings.remove(0);
                --this.count;
                return;
            }
            this.strings.set(0, s.substring(1));
        }
        
        public void stripLast() {
            final String s = this.strings.get(this.count - 1);
            if (s.charAt(s.length() - 1) != ' ') {
                return;
            }
            --this.length;
            if (s.length() == 1) {
                this.attributes.remove(--this.count);
                this.strings.remove(this.count);
                return;
            }
            this.strings.set(this.count - 1, s.substring(0, s.length() - 1));
        }
        
        public AttributedString toAttributedString() {
            switch (this.count) {
                case 0: {
                    return null;
                }
                case 1: {
                    return new AttributedString(this.strings.get(0), this.attributes.get(0));
                }
                default: {
                    final StringBuffer sb = new StringBuffer(this.strings.size() * 5);
                    final Iterator<String> iterator = this.strings.iterator();
                    while (iterator.hasNext()) {
                        sb.append(iterator.next());
                    }
                    final AttributedString attributedString = new AttributedString(sb.toString());
                    final Iterator<String> iterator2 = this.strings.iterator();
                    final Iterator<Map<AttributedCharacterIterator.Attribute, V>> iterator3 = this.attributes.iterator();
                    int beginIndex = 0;
                    while (iterator2.hasNext()) {
                        final int endIndex = beginIndex + iterator2.next().length();
                        final Map<AttributedCharacterIterator.Attribute, V> map = iterator3.next();
                        final Iterator<AttributedCharacterIterator.Attribute> iterator4 = map.keySet().iterator();
                        final Iterator<V> iterator5 = map.values().iterator();
                        while (iterator4.hasNext()) {
                            attributedString.addAttribute(iterator4.next(), iterator5.next(), beginIndex, endIndex);
                        }
                        beginIndex = endIndex;
                    }
                    return attributedString;
                }
            }
        }
        
        public String toString() {
            switch (this.count) {
                case 0: {
                    return "";
                }
                case 1: {
                    return this.strings.get(0);
                }
                default: {
                    final StringBuffer sb = new StringBuffer(this.strings.size() * 5);
                    final Iterator<String> iterator = this.strings.iterator();
                    while (iterator.hasNext()) {
                        sb.append(iterator.next());
                    }
                    return sb.toString();
                }
            }
        }
    }
    
    protected class DOMSubtreeModifiedEventListener implements EventListener
    {
        public void handleEvent(final Event event) {
            SVGTextElementBridge.this.handleDOMSubtreeModifiedEvent((MutationEvent)event);
        }
    }
    
    protected class DOMChildNodeRemovedEventListener implements EventListener
    {
        public void handleEvent(final Event event) {
            SVGTextElementBridge.this.handleDOMChildNodeRemovedEvent((MutationEvent)event);
        }
    }
}
