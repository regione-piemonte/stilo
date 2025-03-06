// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.bridge.svg12;

import org.w3c.dom.events.Event;
import org.apache.batik.css.engine.value.svg12.LineHeightValue;
import org.apache.batik.css.engine.value.ComputedValue;
import org.apache.batik.css.engine.value.Value;
import org.apache.batik.css.engine.value.svg12.SVG12ValueConstants;
import org.apache.batik.css.engine.value.ValueConstants;
import org.apache.batik.css.engine.CSSEngine;
import org.apache.batik.bridge.UserAgent;
import org.apache.batik.bridge.TextUtilities;
import org.apache.batik.dom.util.XLinkSupport;
import org.apache.batik.bridge.SVGAElementBridge;
import org.apache.batik.bridge.CursorManager;
import org.apache.batik.dom.events.NodeEventTarget;
import java.awt.font.TextAttribute;
import org.apache.batik.gvt.text.TextPath;
import org.apache.batik.dom.util.XMLSupport;
import java.awt.geom.AffineTransform;
import java.awt.Shape;
import org.apache.batik.gvt.flow.RegionInfo;
import java.util.Iterator;
import org.apache.batik.gvt.flow.BlockInfo;
import java.util.LinkedList;
import java.util.ArrayList;
import java.awt.Color;
import org.apache.batik.gvt.text.TextPaintInfo;
import java.util.List;
import org.apache.batik.gvt.flow.TextLineBreaks;
import java.text.AttributedString;
import org.apache.batik.dom.svg.SVGContext;
import org.apache.batik.bridge.SVGTextElementBridge;
import org.apache.batik.dom.svg.SVGOMElement;
import org.apache.batik.bridge.GVTBuilder;
import org.w3c.dom.events.EventListener;
import org.apache.batik.dom.AbstractNode;
import org.apache.batik.dom.svg12.XBLEventSupport;
import org.apache.batik.dom.svg12.SVGOMFlowRegionElement;
import java.util.HashMap;
import java.awt.geom.Point2D;
import org.w3c.dom.Node;
import org.apache.batik.gvt.flow.FlowTextNode;
import java.awt.RenderingHints;
import org.apache.batik.bridge.CSSUtilities;
import org.apache.batik.gvt.CompositeGraphicsNode;
import org.apache.batik.bridge.SVGUtilities;
import org.apache.batik.gvt.GraphicsNode;
import org.w3c.dom.Element;
import org.apache.batik.bridge.BridgeContext;
import org.apache.batik.bridge.Bridge;
import org.apache.batik.gvt.TextNode;
import java.util.Map;
import org.apache.batik.gvt.text.GVTAttributedCharacterIterator;
import java.text.AttributedCharacterIterator;

public class SVGFlowRootElementBridge extends SVG12TextElementBridge
{
    public static final AttributedCharacterIterator.Attribute FLOW_PARAGRAPH;
    public static final AttributedCharacterIterator.Attribute FLOW_EMPTY_PARAGRAPH;
    public static final AttributedCharacterIterator.Attribute FLOW_LINE_BREAK;
    public static final AttributedCharacterIterator.Attribute FLOW_REGIONS;
    public static final AttributedCharacterIterator.Attribute LINE_HEIGHT;
    public static final GVTAttributedCharacterIterator.TextAttribute TEXTPATH;
    public static final GVTAttributedCharacterIterator.TextAttribute ANCHOR_TYPE;
    public static final GVTAttributedCharacterIterator.TextAttribute LETTER_SPACING;
    public static final GVTAttributedCharacterIterator.TextAttribute WORD_SPACING;
    public static final GVTAttributedCharacterIterator.TextAttribute KERNING;
    protected Map flowRegionNodes;
    protected TextNode textNode;
    protected RegionChangeListener regionChangeListener;
    protected int startLen;
    int marginTopIndex;
    int marginRightIndex;
    int marginBottomIndex;
    int marginLeftIndex;
    int indentIndex;
    int textAlignIndex;
    int lineHeightIndex;
    
    protected TextNode getTextNode() {
        return this.textNode;
    }
    
    public SVGFlowRootElementBridge() {
        this.marginTopIndex = -1;
        this.marginRightIndex = -1;
        this.marginBottomIndex = -1;
        this.marginLeftIndex = -1;
        this.indentIndex = -1;
        this.textAlignIndex = -1;
        this.lineHeightIndex = -1;
    }
    
    public String getNamespaceURI() {
        return "http://www.w3.org/2000/svg";
    }
    
    public String getLocalName() {
        return "flowRoot";
    }
    
    public Bridge getInstance() {
        return new SVGFlowRootElementBridge();
    }
    
    public boolean isComposite() {
        return false;
    }
    
    public GraphicsNode createGraphicsNode(final BridgeContext bridgeContext, final Element element) {
        if (!SVGUtilities.matchUserAgent(element, bridgeContext.getUserAgent())) {
            return null;
        }
        final CompositeGraphicsNode compositeGraphicsNode = new CompositeGraphicsNode();
        final String attributeNS = element.getAttributeNS(null, "transform");
        if (attributeNS.length() != 0) {
            compositeGraphicsNode.setTransform(SVGUtilities.convertTransform(element, "transform", attributeNS, bridgeContext));
        }
        compositeGraphicsNode.setVisible(CSSUtilities.convertVisibility(element));
        final RenderingHints convertTextRendering = CSSUtilities.convertTextRendering(element, CSSUtilities.convertColorRendering(element, null));
        if (convertTextRendering != null) {
            compositeGraphicsNode.setRenderingHints(convertTextRendering);
        }
        compositeGraphicsNode.add(new CompositeGraphicsNode());
        final FlowTextNode textNode = (FlowTextNode)this.instantiateGraphicsNode();
        textNode.setLocation(this.getLocation(bridgeContext, element));
        if (bridgeContext.getTextPainter() != null) {
            textNode.setTextPainter(bridgeContext.getTextPainter());
        }
        compositeGraphicsNode.add(this.textNode = textNode);
        this.associateSVGContext(bridgeContext, element, compositeGraphicsNode);
        for (Node node = this.getFirstChild(element); node != null; node = this.getNextSibling(node)) {
            if (node.getNodeType() == 1) {
                this.addContextToChild(bridgeContext, (Element)node);
            }
        }
        return compositeGraphicsNode;
    }
    
    protected GraphicsNode instantiateGraphicsNode() {
        return new FlowTextNode();
    }
    
    protected Point2D getLocation(final BridgeContext bridgeContext, final Element element) {
        return new Point2D.Float(0.0f, 0.0f);
    }
    
    protected boolean isTextElement(final Element element) {
        if (!"http://www.w3.org/2000/svg".equals(element.getNamespaceURI())) {
            return false;
        }
        final String localName = element.getLocalName();
        return localName.equals("flowDiv") || localName.equals("flowLine") || localName.equals("flowPara") || localName.equals("flowRegionBreak") || localName.equals("flowSpan");
    }
    
    protected boolean isTextChild(final Element element) {
        if (!"http://www.w3.org/2000/svg".equals(element.getNamespaceURI())) {
            return false;
        }
        final String localName = element.getLocalName();
        return localName.equals("a") || localName.equals("flowLine") || localName.equals("flowPara") || localName.equals("flowRegionBreak") || localName.equals("flowSpan");
    }
    
    public void buildGraphicsNode(final BridgeContext bridgeContext, final Element element, final GraphicsNode graphicsNode) {
        final CompositeGraphicsNode compositeGraphicsNode = (CompositeGraphicsNode)graphicsNode;
        final boolean b = !bridgeContext.isDynamic();
        if (b) {
            this.flowRegionNodes = new HashMap();
        }
        else {
            this.regionChangeListener = new RegionChangeListener();
        }
        final CompositeGraphicsNode compositeGraphicsNode2 = (CompositeGraphicsNode)compositeGraphicsNode.get(0);
        final GVTBuilder gvtBuilder = bridgeContext.getGVTBuilder();
        for (Node node = this.getFirstChild(element); node != null; node = this.getNextSibling(node)) {
            if (node instanceof SVGOMFlowRegionElement) {
                for (Node node2 = this.getFirstChild(node); node2 != null; node2 = this.getNextSibling(node2)) {
                    if (node2.getNodeType() == 1) {
                        final GraphicsNode build = gvtBuilder.build(bridgeContext, (Element)node2);
                        if (build != null) {
                            compositeGraphicsNode2.add(build);
                            if (b) {
                                this.flowRegionNodes.put(node2, build);
                            }
                        }
                    }
                }
                if (!b) {
                    ((XBLEventSupport)((AbstractNode)node).initializeEventSupport()).addImplementationEventListenerNS("http://www.w3.org/2000/svg", "shapechange", this.regionChangeListener, false);
                }
            }
        }
        super.buildGraphicsNode(bridgeContext, element, (GraphicsNode)compositeGraphicsNode.get(1));
        this.flowRegionNodes = null;
    }
    
    protected void computeLaidoutText(final BridgeContext bridgeContext, final Element element, final GraphicsNode graphicsNode) {
        super.computeLaidoutText(bridgeContext, this.getFlowDivElement(element), graphicsNode);
    }
    
    protected void addContextToChild(final BridgeContext bridgeContext, final Element element) {
        if ("http://www.w3.org/2000/svg".equals(element.getNamespaceURI())) {
            final String localName = element.getLocalName();
            if (localName.equals("flowDiv") || localName.equals("flowLine") || localName.equals("flowPara") || localName.equals("flowSpan")) {
                ((SVGOMElement)element).setSVGContext(new FlowContentBridge(bridgeContext, this, element));
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
            final String localName = element.getLocalName();
            if (localName.equals("flowDiv") || localName.equals("flowLine") || localName.equals("flowPara") || localName.equals("flowSpan")) {
                ((AbstractTextChildBridgeUpdateHandler)((SVGOMElement)element).getSVGContext()).dispose();
            }
        }
        for (Node node = this.getFirstChild(element); node != null; node = this.getNextSibling(node)) {
            if (node.getNodeType() == 1) {
                this.removeContextFromChild(bridgeContext, (Element)node);
            }
        }
    }
    
    protected AttributedString buildAttributedString(final BridgeContext bridgeContext, final Element element) {
        if (element == null) {
            return null;
        }
        final List regions = this.getRegions(bridgeContext, element);
        final AttributedString flowDiv = this.getFlowDiv(bridgeContext, element);
        if (flowDiv == null) {
            return flowDiv;
        }
        flowDiv.addAttribute(SVGFlowRootElementBridge.FLOW_REGIONS, regions, 0, 1);
        TextLineBreaks.findLineBrk(flowDiv);
        return flowDiv;
    }
    
    protected void dumpACIWord(final AttributedString attributedString) {
        if (attributedString == null) {
            return;
        }
        final StringBuffer sb = new StringBuffer();
        final StringBuffer sb2 = new StringBuffer();
        final AttributedCharacterIterator iterator = attributedString.getIterator();
        final AttributedCharacterIterator.Attribute word_LIMIT = TextLineBreaks.WORD_LIMIT;
        for (char c = iterator.current(); c != '\uffff'; c = iterator.next()) {
            sb.append(c).append(' ').append(' ');
            final int intValue = (int)iterator.getAttribute(word_LIMIT);
            sb2.append(intValue).append(' ');
            if (intValue < 10) {
                sb2.append(' ');
            }
        }
        System.out.println(sb.toString());
        System.out.println(sb2.toString());
    }
    
    protected Element getFlowDivElement(final Element element) {
        if (!element.getNamespaceURI().equals("http://www.w3.org/2000/svg")) {
            return null;
        }
        final String localName = element.getLocalName();
        if (localName.equals("flowDiv")) {
            return element;
        }
        if (!localName.equals("flowRoot")) {
            return null;
        }
        for (Node node = this.getFirstChild(element); node != null; node = this.getNextSibling(node)) {
            if (node.getNodeType() == 1) {
                if ("http://www.w3.org/2000/svg".equals(node.getNamespaceURI())) {
                    final Element element2 = (Element)node;
                    if (element2.getLocalName().equals("flowDiv")) {
                        return element2;
                    }
                }
            }
        }
        return null;
    }
    
    protected AttributedString getFlowDiv(final BridgeContext bridgeContext, final Element element) {
        final Element flowDivElement = this.getFlowDivElement(element);
        if (flowDivElement == null) {
            return null;
        }
        return this.gatherFlowPara(bridgeContext, flowDivElement);
    }
    
    protected AttributedString gatherFlowPara(final BridgeContext bridgeContext, final Element key) {
        final TextPaintInfo value = new TextPaintInfo();
        value.visible = true;
        value.fillPaint = Color.black;
        this.elemTPI.put(key, value);
        final AttributedStringBuffer attributedStringBuffer = new AttributedStringBuffer();
        final ArrayList<Integer> list = new ArrayList<Integer>();
        final ArrayList list2 = new ArrayList<Object>();
        final ArrayList<Integer> list3 = new ArrayList<Integer>();
        for (Node node = this.getFirstChild(key); node != null; node = this.getNextSibling(node)) {
            if (node.getNodeType() == 1) {
                if (this.getNamespaceURI().equals(node.getNamespaceURI())) {
                    final Element element = (Element)node;
                    final String localName = element.getLocalName();
                    if (localName.equals("flowPara")) {
                        this.fillAttributedStringBuffer(bridgeContext, element, true, null, null, attributedStringBuffer, list3);
                        list2.add(element);
                        list.add(new Integer(attributedStringBuffer.length()));
                    }
                    else if (localName.equals("flowRegionBreak")) {
                        this.fillAttributedStringBuffer(bridgeContext, element, true, null, null, attributedStringBuffer, list3);
                        list2.add(element);
                        list.add(new Integer(attributedStringBuffer.length()));
                    }
                }
            }
        }
        value.startChar = 0;
        value.endChar = attributedStringBuffer.length() - 1;
        final AttributedString attributedString = attributedStringBuffer.toAttributedString();
        if (attributedString == null) {
            return null;
        }
        int beginIndex = 0;
        for (final int intValue : list3) {
            if (intValue == beginIndex) {
                continue;
            }
            attributedString.addAttribute(SVGFlowRootElementBridge.FLOW_LINE_BREAK, new Object(), beginIndex, intValue);
            beginIndex = intValue;
        }
        int n = 0;
        List<BlockInfo> value2 = null;
        int intValue2;
        for (int i = 0; i < list2.size(); ++i, n = intValue2) {
            final Element element2 = list2.get(i);
            intValue2 = list.get(i);
            if (n == intValue2) {
                if (value2 == null) {
                    value2 = new LinkedList<BlockInfo>();
                }
                value2.add(this.makeBlockInfo(bridgeContext, element2));
            }
            else {
                attributedString.addAttribute(SVGFlowRootElementBridge.FLOW_PARAGRAPH, this.makeBlockInfo(bridgeContext, element2), n, intValue2);
                if (value2 != null) {
                    attributedString.addAttribute(SVGFlowRootElementBridge.FLOW_EMPTY_PARAGRAPH, value2, n, intValue2);
                    value2 = null;
                }
            }
        }
        return attributedString;
    }
    
    protected List getRegions(final BridgeContext bridgeContext, Element element) {
        element = (Element)element.getParentNode();
        final LinkedList list = new LinkedList();
        for (Node node = this.getFirstChild(element); node != null; node = this.getNextSibling(node)) {
            if (node.getNodeType() == 1) {
                if ("http://www.w3.org/2000/svg".equals(node.getNamespaceURI())) {
                    final Element element2 = (Element)node;
                    if ("flowRegion".equals(element2.getLocalName())) {
                        this.gatherRegionInfo(bridgeContext, element2, 0.0f, list);
                    }
                }
            }
        }
        return list;
    }
    
    protected void gatherRegionInfo(final BridgeContext bridgeContext, final Element element, final float n, final List list) {
        final boolean b = !bridgeContext.isDynamic();
        for (Node node = this.getFirstChild(element); node != null; node = this.getNextSibling(node)) {
            if (node.getNodeType() == 1) {
                final GraphicsNode graphicsNode = b ? this.flowRegionNodes.get(node) : bridgeContext.getGraphicsNode(node);
                Shape pSrc = graphicsNode.getOutline();
                if (pSrc != null) {
                    final AffineTransform transform = graphicsNode.getTransform();
                    if (transform != null) {
                        pSrc = transform.createTransformedShape(pSrc);
                    }
                    list.add(new RegionInfo(pSrc, n));
                }
            }
        }
    }
    
    protected void fillAttributedStringBuffer(final BridgeContext bridgeContext, final Element key, final boolean b, final Integer n, Map attributeMap, final AttributedStringBuffer attributedStringBuffer, final List list) {
        if (!SVGUtilities.matchUserAgent(key, bridgeContext.getUserAgent()) || !CSSUtilities.convertDisplay(key)) {
            return;
        }
        final boolean equals = XMLSupport.getXMLSpace(key).equals("preserve");
        final int length = attributedStringBuffer.length();
        if (b) {
            final int length2 = attributedStringBuffer.length();
            this.startLen = length2;
            this.endLimit = length2;
        }
        if (equals) {
            this.endLimit = this.startLen;
        }
        final HashMap hashMap = (attributeMap == null) ? new HashMap<Object, Object>() : new HashMap<Object, Object>((Map<? extends K, ? extends V>)attributeMap);
        attributeMap = (Map<? extends K, ? extends Integer>)this.getAttributeMap(bridgeContext, key, null, n, hashMap);
        final Object value = hashMap.get(TextAttribute.BIDI_EMBEDDING);
        Integer n2 = n;
        if (value != null) {
            n2 = (Integer)value;
        }
        if (list.size() != 0) {
            list.get(list.size() - 1);
        }
        for (Node node = this.getFirstChild(key); node != null; node = this.getNextSibling(node)) {
            boolean b2;
            if (equals) {
                b2 = false;
            }
            else {
                final int length3 = attributedStringBuffer.length();
                if (length3 == this.startLen) {
                    b2 = true;
                }
                else {
                    b2 = (attributedStringBuffer.getLastChar() == 32);
                    final int n3 = list.size() - 1;
                    if (!b2 && n3 >= 0 && list.get(n3) == length3) {
                        b2 = true;
                    }
                }
            }
            switch (node.getNodeType()) {
                case 1: {
                    if (!"http://www.w3.org/2000/svg".equals(node.getNamespaceURI())) {
                        break;
                    }
                    final Element key2 = (Element)node;
                    final String localName = node.getLocalName();
                    if (localName.equals("flowLine")) {
                        final int length4 = attributedStringBuffer.length();
                        this.fillAttributedStringBuffer(bridgeContext, key2, false, n2, attributeMap, attributedStringBuffer, list);
                        final int length5 = attributedStringBuffer.length();
                        list.add(new Integer(length5));
                        if (length4 != length5) {
                            attributeMap = null;
                            break;
                        }
                        break;
                    }
                    else if (localName.equals("flowSpan") || localName.equals("altGlyph")) {
                        final int length6 = attributedStringBuffer.length();
                        this.fillAttributedStringBuffer(bridgeContext, key2, false, n2, attributeMap, attributedStringBuffer, list);
                        if (attributedStringBuffer.length() != length6) {
                            attributeMap = null;
                            break;
                        }
                        break;
                    }
                    else if (localName.equals("a")) {
                        if (bridgeContext.isInteractive()) {
                            final NodeEventTarget nodeEventTarget = (NodeEventTarget)key2;
                            final UserAgent userAgent = bridgeContext.getUserAgent();
                            final SVGAElementBridge.CursorHolder cursorHolder = new SVGAElementBridge.CursorHolder(CursorManager.DEFAULT_CURSOR);
                            nodeEventTarget.addEventListenerNS("http://www.w3.org/2001/xml-events", "click", new SVGAElementBridge.AnchorListener(userAgent, cursorHolder), false, null);
                            nodeEventTarget.addEventListenerNS("http://www.w3.org/2001/xml-events", "mouseover", new SVGAElementBridge.CursorMouseOverListener(userAgent, cursorHolder), false, null);
                            nodeEventTarget.addEventListenerNS("http://www.w3.org/2001/xml-events", "mouseout", new SVGAElementBridge.CursorMouseOutListener(userAgent, cursorHolder), false, null);
                        }
                        final int length7 = attributedStringBuffer.length();
                        this.fillAttributedStringBuffer(bridgeContext, key2, false, n2, attributeMap, attributedStringBuffer, list);
                        if (attributedStringBuffer.length() != length7) {
                            attributeMap = null;
                            break;
                        }
                        break;
                    }
                    else {
                        if (!localName.equals("tref")) {
                            break;
                        }
                        final String normalizeString = this.normalizeString(TextUtilities.getElementContent(bridgeContext.getReferencedElement((Element)node, XLinkSupport.getXLinkHref((Element)node))), equals, b2);
                        if (normalizeString.length() != 0) {
                            final int length8 = attributedStringBuffer.length();
                            final HashMap hashMap2 = new HashMap();
                            this.getAttributeMap(bridgeContext, key2, null, n, hashMap2);
                            attributedStringBuffer.append(normalizeString, hashMap2);
                            final int endChar = attributedStringBuffer.length() - 1;
                            final TextPaintInfo textPaintInfo = this.elemTPI.get(key2);
                            textPaintInfo.startChar = length8;
                            textPaintInfo.endChar = endChar;
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
                int i = list.size() - 1;
                final int length9 = attributedStringBuffer.length();
                if (i >= 0 && list.get(i) >= length9) {
                    list.set(i, new Integer(length9 - 1));
                    --i;
                    while (i >= 0) {
                        if (list.get(i) < length9 - 1) {
                            break;
                        }
                        list.remove(i);
                        --i;
                    }
                }
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
    
    protected Map getAttributeMap(final BridgeContext bridgeContext, final Element element, final TextPath textPath, final Integer n, final Map map) {
        final Map attributeMap = super.getAttributeMap(bridgeContext, element, textPath, n, map);
        map.put(SVGFlowRootElementBridge.LINE_HEIGHT, new Float(this.getLineHeight(bridgeContext, element, TextUtilities.convertFontSize(element))));
        return attributeMap;
    }
    
    protected void checkMap(final Map map) {
        if (map.containsKey(SVGFlowRootElementBridge.TEXTPATH)) {
            return;
        }
        if (map.containsKey(SVGFlowRootElementBridge.ANCHOR_TYPE)) {
            return;
        }
        if (map.containsKey(SVGFlowRootElementBridge.LETTER_SPACING)) {
            return;
        }
        if (map.containsKey(SVGFlowRootElementBridge.WORD_SPACING)) {
            return;
        }
        if (map.containsKey(SVGFlowRootElementBridge.KERNING)) {
            return;
        }
    }
    
    protected void initCSSPropertyIndexes(final Element element) {
        final CSSEngine cssEngine = CSSUtilities.getCSSEngine(element);
        this.marginTopIndex = cssEngine.getPropertyIndex("margin-top");
        this.marginRightIndex = cssEngine.getPropertyIndex("margin-right");
        this.marginBottomIndex = cssEngine.getPropertyIndex("margin-bottom");
        this.marginLeftIndex = cssEngine.getPropertyIndex("margin-left");
        this.indentIndex = cssEngine.getPropertyIndex("indent");
        this.textAlignIndex = cssEngine.getPropertyIndex("text-align");
        this.lineHeightIndex = cssEngine.getPropertyIndex("line-height");
    }
    
    public BlockInfo makeBlockInfo(final BridgeContext bridgeContext, final Element element) {
        if (this.marginTopIndex == -1) {
            this.initCSSPropertyIndexes(element);
        }
        final float floatValue = CSSUtilities.getComputedStyle(element, this.marginTopIndex).getFloatValue();
        final float floatValue2 = CSSUtilities.getComputedStyle(element, this.marginRightIndex).getFloatValue();
        final float floatValue3 = CSSUtilities.getComputedStyle(element, this.marginBottomIndex).getFloatValue();
        final float floatValue4 = CSSUtilities.getComputedStyle(element, this.marginLeftIndex).getFloatValue();
        final float floatValue5 = CSSUtilities.getComputedStyle(element, this.indentIndex).getFloatValue();
        Value value = CSSUtilities.getComputedStyle(element, this.textAlignIndex);
        if (value == ValueConstants.INHERIT_VALUE) {
            if (CSSUtilities.getComputedStyle(element, 11) == ValueConstants.LTR_VALUE) {
                value = SVG12ValueConstants.START_VALUE;
            }
            else {
                value = SVG12ValueConstants.END_VALUE;
            }
        }
        int n;
        if (value == SVG12ValueConstants.START_VALUE) {
            n = 0;
        }
        else if (value == SVG12ValueConstants.MIDDLE_VALUE) {
            n = 1;
        }
        else if (value == SVG12ValueConstants.END_VALUE) {
            n = 2;
        }
        else {
            n = 3;
        }
        final HashMap<Object, Float> hashMap = (HashMap<Object, Float>)new HashMap<Object, Object>(20);
        return new BlockInfo(floatValue, floatValue2, floatValue3, floatValue4, floatValue5, n, this.getLineHeight(bridgeContext, element, (float)hashMap.get(TextAttribute.SIZE)), this.getFontList(bridgeContext, element, hashMap), hashMap, element.getLocalName().equals("flowRegionBreak"));
    }
    
    protected float getLineHeight(final BridgeContext bridgeContext, final Element element, final float n) {
        if (this.lineHeightIndex == -1) {
            this.initCSSPropertyIndexes(element);
        }
        Value value = CSSUtilities.getComputedStyle(element, this.lineHeightIndex);
        if (value == ValueConstants.INHERIT_VALUE || value == SVG12ValueConstants.NORMAL_VALUE) {
            return n * 1.1f;
        }
        float floatValue = value.getFloatValue();
        if (value instanceof ComputedValue) {
            value = ((ComputedValue)value).getComputedValue();
        }
        if (value instanceof LineHeightValue && ((LineHeightValue)value).getFontSizeRelative()) {
            floatValue *= n;
        }
        return floatValue;
    }
    
    static {
        FLOW_PARAGRAPH = GVTAttributedCharacterIterator.TextAttribute.FLOW_PARAGRAPH;
        FLOW_EMPTY_PARAGRAPH = GVTAttributedCharacterIterator.TextAttribute.FLOW_EMPTY_PARAGRAPH;
        FLOW_LINE_BREAK = GVTAttributedCharacterIterator.TextAttribute.FLOW_LINE_BREAK;
        FLOW_REGIONS = GVTAttributedCharacterIterator.TextAttribute.FLOW_REGIONS;
        LINE_HEIGHT = GVTAttributedCharacterIterator.TextAttribute.LINE_HEIGHT;
        TEXTPATH = GVTAttributedCharacterIterator.TextAttribute.TEXTPATH;
        ANCHOR_TYPE = GVTAttributedCharacterIterator.TextAttribute.ANCHOR_TYPE;
        LETTER_SPACING = GVTAttributedCharacterIterator.TextAttribute.LETTER_SPACING;
        WORD_SPACING = GVTAttributedCharacterIterator.TextAttribute.WORD_SPACING;
        KERNING = GVTAttributedCharacterIterator.TextAttribute.KERNING;
    }
    
    protected class RegionChangeListener implements EventListener
    {
        public void handleEvent(final Event event) {
            SVGFlowRootElementBridge.this.laidoutText = null;
            SVGFlowRootElementBridge.this.computeLaidoutText(SVGFlowRootElementBridge.this.ctx, SVGFlowRootElementBridge.this.e, SVGFlowRootElementBridge.this.getTextNode());
        }
    }
    
    protected class FlowContentBridge extends AbstractTextChildTextContent
    {
        public FlowContentBridge(final BridgeContext bridgeContext, final SVGTextElementBridge svgTextElementBridge, final Element element) {
            super(bridgeContext, svgTextElementBridge, element);
        }
    }
}
