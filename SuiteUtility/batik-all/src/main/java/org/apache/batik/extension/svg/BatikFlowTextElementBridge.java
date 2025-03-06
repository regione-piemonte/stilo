// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.extension.svg;

import org.apache.batik.bridge.UserAgent;
import org.apache.batik.bridge.TextUtilities;
import org.apache.batik.dom.util.XLinkSupport;
import org.w3c.dom.events.EventListener;
import org.apache.batik.bridge.SVGAElementBridge;
import org.apache.batik.bridge.CursorManager;
import org.apache.batik.dom.events.NodeEventTarget;
import java.awt.font.TextAttribute;
import org.apache.batik.gvt.text.TextPath;
import java.util.HashMap;
import org.apache.batik.dom.util.XMLSupport;
import org.apache.batik.bridge.CSSUtilities;
import org.apache.batik.bridge.SVGUtilities;
import org.apache.batik.bridge.BridgeException;
import org.apache.batik.bridge.UnitProcessor;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.ArrayList;
import java.awt.Color;
import org.apache.batik.gvt.text.TextPaintInfo;
import org.apache.batik.gvt.TextNode;
import java.util.List;
import java.text.AttributedString;
import org.w3c.dom.Node;
import org.apache.batik.dom.svg.SVGContext;
import org.apache.batik.dom.svg.SVGOMElement;
import java.awt.geom.Point2D;
import org.w3c.dom.Element;
import org.apache.batik.bridge.BridgeContext;
import org.apache.batik.gvt.GraphicsNode;
import org.apache.batik.bridge.Bridge;
import org.apache.batik.gvt.text.GVTAttributedCharacterIterator;
import java.text.AttributedCharacterIterator;
import org.apache.batik.bridge.SVGTextElementBridge;

public class BatikFlowTextElementBridge extends SVGTextElementBridge implements BatikExtConstants
{
    public static final AttributedCharacterIterator.Attribute FLOW_PARAGRAPH;
    public static final AttributedCharacterIterator.Attribute FLOW_EMPTY_PARAGRAPH;
    public static final AttributedCharacterIterator.Attribute FLOW_LINE_BREAK;
    public static final AttributedCharacterIterator.Attribute FLOW_REGIONS;
    public static final AttributedCharacterIterator.Attribute PREFORMATTED;
    protected static final GVTAttributedCharacterIterator.TextAttribute TEXTPATH;
    protected static final GVTAttributedCharacterIterator.TextAttribute ANCHOR_TYPE;
    protected static final GVTAttributedCharacterIterator.TextAttribute LETTER_SPACING;
    protected static final GVTAttributedCharacterIterator.TextAttribute WORD_SPACING;
    protected static final GVTAttributedCharacterIterator.TextAttribute KERNING;
    
    public String getNamespaceURI() {
        return "http://xml.apache.org/batik/ext";
    }
    
    public String getLocalName() {
        return "flowText";
    }
    
    public Bridge getInstance() {
        return new BatikFlowTextElementBridge();
    }
    
    public boolean isComposite() {
        return false;
    }
    
    protected GraphicsNode instantiateGraphicsNode() {
        return new FlowExtTextNode();
    }
    
    protected Point2D getLocation(final BridgeContext bridgeContext, final Element element) {
        return new Point2D.Float(0.0f, 0.0f);
    }
    
    protected void addContextToChild(final BridgeContext bridgeContext, final Element element) {
        if (this.getNamespaceURI().equals(element.getNamespaceURI())) {
            final String localName = element.getLocalName();
            if (localName.equals("flowPara") || localName.equals("flowRegionBreak") || localName.equals("flowLine") || localName.equals("flowSpan") || localName.equals("a") || localName.equals("tref")) {
                ((SVGOMElement)element).setSVGContext(new BatikFlowContentBridge(bridgeContext, this, element));
            }
        }
        for (Node node = this.getFirstChild(element); node != null; node = this.getNextSibling(node)) {
            if (node.getNodeType() == 1) {
                this.addContextToChild(bridgeContext, (Element)node);
            }
        }
    }
    
    protected AttributedString buildAttributedString(final BridgeContext bridgeContext, final Element element) {
        final List regions = this.getRegions(bridgeContext, element);
        final AttributedString flowDiv = this.getFlowDiv(bridgeContext, element);
        if (flowDiv == null) {
            return flowDiv;
        }
        flowDiv.addAttribute(BatikFlowTextElementBridge.FLOW_REGIONS, regions, 0, 1);
        return flowDiv;
    }
    
    protected void addGlyphPositionAttributes(final AttributedString attributedString, final Element element, final BridgeContext bridgeContext) {
        if (element.getNodeType() != 1) {
            return;
        }
        final String namespaceURI = element.getNamespaceURI();
        if (!namespaceURI.equals(this.getNamespaceURI()) && !namespaceURI.equals("http://www.w3.org/2000/svg")) {
            return;
        }
        if (element.getLocalName() != "flowText") {
            super.addGlyphPositionAttributes(attributedString, element, bridgeContext);
            return;
        }
        for (Node node = element.getFirstChild(); node != null; node = node.getNextSibling()) {
            if (node.getNodeType() == 1) {
                final String namespaceURI2 = node.getNamespaceURI();
                if (this.getNamespaceURI().equals(namespaceURI2) || "http://www.w3.org/2000/svg".equals(namespaceURI2)) {
                    final Element element2 = (Element)node;
                    if (element2.getLocalName().equals("flowDiv")) {
                        super.addGlyphPositionAttributes(attributedString, element2, bridgeContext);
                        return;
                    }
                }
            }
        }
    }
    
    protected void addChildGlyphPositionAttributes(final AttributedString attributedString, final Element element, final BridgeContext bridgeContext) {
        for (Node node = element.getFirstChild(); node != null; node = node.getNextSibling()) {
            if (node.getNodeType() == 1) {
                final String namespaceURI = node.getNamespaceURI();
                if (this.getNamespaceURI().equals(namespaceURI) || "http://www.w3.org/2000/svg".equals(namespaceURI)) {
                    final String localName = node.getLocalName();
                    if (localName.equals("flowPara") || localName.equals("flowRegionBreak") || localName.equals("flowLine") || localName.equals("flowSpan") || localName.equals("a") || localName.equals("tref")) {
                        this.addGlyphPositionAttributes(attributedString, (Element)node, bridgeContext);
                    }
                }
            }
        }
    }
    
    protected void addPaintAttributes(final AttributedString attributedString, final Element element, final TextNode textNode, final TextPaintInfo textPaintInfo, final BridgeContext bridgeContext) {
        if (element.getNodeType() != 1) {
            return;
        }
        final String namespaceURI = element.getNamespaceURI();
        if (!namespaceURI.equals(this.getNamespaceURI()) && !namespaceURI.equals("http://www.w3.org/2000/svg")) {
            return;
        }
        if (element.getLocalName() != "flowText") {
            super.addPaintAttributes(attributedString, element, textNode, textPaintInfo, bridgeContext);
            return;
        }
        for (Node node = element.getFirstChild(); node != null; node = node.getNextSibling()) {
            if (node.getNodeType() == 1) {
                if (this.getNamespaceURI().equals(node.getNamespaceURI())) {
                    final Element element2 = (Element)node;
                    if (element2.getLocalName().equals("flowDiv")) {
                        super.addPaintAttributes(attributedString, element2, textNode, textPaintInfo, bridgeContext);
                        return;
                    }
                }
            }
        }
    }
    
    protected void addChildPaintAttributes(final AttributedString attributedString, final Element element, final TextNode textNode, final TextPaintInfo textPaintInfo, final BridgeContext bridgeContext) {
        for (Node node = element.getFirstChild(); node != null; node = node.getNextSibling()) {
            if (node.getNodeType() == 1) {
                final String namespaceURI = node.getNamespaceURI();
                if (this.getNamespaceURI().equals(namespaceURI) || "http://www.w3.org/2000/svg".equals(namespaceURI)) {
                    final String localName = node.getLocalName();
                    if (localName.equals("flowPara") || localName.equals("flowRegionBreak") || localName.equals("flowLine") || localName.equals("flowSpan") || localName.equals("a") || localName.equals("tref")) {
                        final Element element2 = (Element)node;
                        this.addPaintAttributes(attributedString, element2, textNode, this.getTextPaintInfo(element2, textNode, textPaintInfo, bridgeContext), bridgeContext);
                    }
                }
            }
        }
    }
    
    protected AttributedString getFlowDiv(final BridgeContext bridgeContext, final Element element) {
        for (Node node = element.getFirstChild(); node != null; node = node.getNextSibling()) {
            if (node.getNodeType() == 1) {
                if (this.getNamespaceURI().equals(node.getNamespaceURI())) {
                    final Element element2 = (Element)node;
                    if (node.getLocalName().equals("flowDiv")) {
                        return this.gatherFlowPara(bridgeContext, element2);
                    }
                }
            }
        }
        return null;
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
        for (Node node = key.getFirstChild(); node != null; node = node.getNextSibling()) {
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
        int beginIndex = 0;
        for (final int intValue : list3) {
            if (intValue == beginIndex) {
                continue;
            }
            attributedString.addAttribute(BatikFlowTextElementBridge.FLOW_LINE_BREAK, new Object(), beginIndex, intValue);
            beginIndex = intValue;
        }
        int n = 0;
        List<MarginInfo> value2 = null;
        int intValue2;
        for (int i = 0; i < list2.size(); ++i, n = intValue2) {
            final Element element2 = list2.get(i);
            intValue2 = list.get(i);
            if (n == intValue2) {
                if (value2 == null) {
                    value2 = new LinkedList<MarginInfo>();
                }
                value2.add(this.makeMarginInfo(element2));
            }
            else {
                attributedString.addAttribute(BatikFlowTextElementBridge.FLOW_PARAGRAPH, this.makeMarginInfo(element2), n, intValue2);
                if (value2 != null) {
                    attributedString.addAttribute(BatikFlowTextElementBridge.FLOW_EMPTY_PARAGRAPH, value2, n, intValue2);
                    value2 = null;
                }
            }
        }
        return attributedString;
    }
    
    protected List getRegions(final BridgeContext bridgeContext, final Element element) {
        final LinkedList list = new LinkedList();
        for (Node node = element.getFirstChild(); node != null; node = node.getNextSibling()) {
            if (node.getNodeType() == 1) {
                if (this.getNamespaceURI().equals(node.getNamespaceURI())) {
                    final Element element2 = (Element)node;
                    if ("flowRegion".equals(element2.getLocalName())) {
                        float n = 0.0f;
                        final String attribute = element2.getAttribute("vertical-align");
                        if (attribute != null && attribute.length() > 0) {
                            if ("top".equals(attribute)) {
                                n = 0.0f;
                            }
                            else if ("middle".equals(attribute)) {
                                n = 0.5f;
                            }
                            else if ("bottom".equals(attribute)) {
                                n = 1.0f;
                            }
                        }
                        this.gatherRegionInfo(bridgeContext, element2, n, list);
                    }
                }
            }
        }
        return list;
    }
    
    protected void gatherRegionInfo(final BridgeContext bridgeContext, final Element element, final float n, final List list) {
        for (Node node = element.getFirstChild(); node != null; node = node.getNextSibling()) {
            if (node.getNodeType() == 1) {
                if (this.getNamespaceURI().equals(node.getNamespaceURI())) {
                    final Element element2 = (Element)node;
                    if (node.getLocalName().equals("rect")) {
                        final RegionInfo buildRegion = this.buildRegion(UnitProcessor.createContext(bridgeContext, element2), element2, n);
                        if (buildRegion != null) {
                            list.add(buildRegion);
                        }
                    }
                }
            }
        }
    }
    
    protected RegionInfo buildRegion(final org.apache.batik.parser.UnitProcessor.Context context, final Element element, final float n) {
        final String attribute = element.getAttribute("x");
        float svgHorizontalCoordinateToUserSpace = 0.0f;
        if (attribute.length() != 0) {
            svgHorizontalCoordinateToUserSpace = UnitProcessor.svgHorizontalCoordinateToUserSpace(attribute, "x", context);
        }
        final String attribute2 = element.getAttribute("y");
        float svgVerticalCoordinateToUserSpace = 0.0f;
        if (attribute2.length() != 0) {
            svgVerticalCoordinateToUserSpace = UnitProcessor.svgVerticalCoordinateToUserSpace(attribute2, "y", context);
        }
        final String attribute3 = element.getAttribute("width");
        if (attribute3.length() == 0) {
            throw new BridgeException(this.ctx, element, "attribute.missing", new Object[] { "width", attribute3 });
        }
        final float svgHorizontalLengthToUserSpace = UnitProcessor.svgHorizontalLengthToUserSpace(attribute3, "width", context);
        if (svgHorizontalLengthToUserSpace == 0.0f) {
            return null;
        }
        final String attribute4 = element.getAttribute("height");
        if (attribute4.length() == 0) {
            throw new BridgeException(this.ctx, element, "attribute.missing", new Object[] { "height", attribute4 });
        }
        final float svgVerticalLengthToUserSpace = UnitProcessor.svgVerticalLengthToUserSpace(attribute4, "height", context);
        if (svgVerticalLengthToUserSpace == 0.0f) {
            return null;
        }
        return new RegionInfo(svgHorizontalCoordinateToUserSpace, svgVerticalCoordinateToUserSpace, svgHorizontalLengthToUserSpace, svgVerticalLengthToUserSpace, n);
    }
    
    protected void fillAttributedStringBuffer(final BridgeContext bridgeContext, final Element key, final boolean b, final Integer n, Map attributeMap, final AttributedStringBuffer attributedStringBuffer, final List list) {
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
        attributeMap = (Map<? extends K, ? extends Integer>)this.getAttributeMap(bridgeContext, key, null, n, hashMap);
        final Object value = hashMap.get(TextAttribute.BIDI_EMBEDDING);
        Integer n2 = n;
        if (value != null) {
            n2 = (Integer)value;
        }
        for (Node node = key.getFirstChild(); node != null; node = node.getNextSibling()) {
            final boolean b2 = !equals && (attributedStringBuffer.length() == 0 || attributedStringBuffer.getLastChar() == 32);
            switch (node.getNodeType()) {
                case 1: {
                    if (!this.getNamespaceURI().equals(node.getNamespaceURI()) && !"http://www.w3.org/2000/svg".equals(node.getNamespaceURI())) {
                        break;
                    }
                    final Element key2 = (Element)node;
                    final String localName = node.getLocalName();
                    if (localName.equals("flowLine")) {
                        final int length2 = attributedStringBuffer.length();
                        this.fillAttributedStringBuffer(bridgeContext, key2, false, n2, attributeMap, attributedStringBuffer, list);
                        list.add(new Integer(attributedStringBuffer.length()));
                        if (attributedStringBuffer.length() != length2) {
                            attributeMap = null;
                            break;
                        }
                        break;
                    }
                    else if (localName.equals("flowSpan") || localName.equals("altGlyph")) {
                        final int length3 = attributedStringBuffer.length();
                        this.fillAttributedStringBuffer(bridgeContext, key2, false, n2, attributeMap, attributedStringBuffer, list);
                        if (attributedStringBuffer.length() != length3) {
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
                        final int length4 = attributedStringBuffer.length();
                        this.fillAttributedStringBuffer(bridgeContext, key2, false, n2, attributeMap, attributedStringBuffer, list);
                        if (attributedStringBuffer.length() != length4) {
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
                            final int length5 = attributedStringBuffer.length();
                            final HashMap hashMap2 = (attributeMap == null) ? new HashMap() : new HashMap(attributeMap);
                            this.getAttributeMap(bridgeContext, key2, null, n, hashMap2);
                            attributedStringBuffer.append(normalizeString, hashMap2);
                            final int endChar = attributedStringBuffer.length() - 1;
                            final TextPaintInfo textPaintInfo = this.elemTPI.get(key2);
                            textPaintInfo.startChar = length5;
                            textPaintInfo.endChar = endChar;
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
    
    protected Map getAttributeMap(final BridgeContext bridgeContext, final Element element, final TextPath textPath, final Integer n, final Map map) {
        final Map attributeMap = super.getAttributeMap(bridgeContext, element, textPath, n, map);
        final String attribute = element.getAttribute("preformatted");
        if (attribute.length() != 0 && attribute.equals("true")) {
            map.put(BatikFlowTextElementBridge.PREFORMATTED, Boolean.TRUE);
        }
        return attributeMap;
    }
    
    protected void checkMap(final Map map) {
        if (map.containsKey(BatikFlowTextElementBridge.TEXTPATH)) {
            return;
        }
        if (map.containsKey(BatikFlowTextElementBridge.ANCHOR_TYPE)) {
            return;
        }
        if (map.containsKey(BatikFlowTextElementBridge.LETTER_SPACING)) {
            return;
        }
        if (map.containsKey(BatikFlowTextElementBridge.WORD_SPACING)) {
            return;
        }
        if (map.containsKey(BatikFlowTextElementBridge.KERNING)) {
            return;
        }
    }
    
    public MarginInfo makeMarginInfo(final Element element) {
        float float1 = 0.0f;
        float float2 = 0.0f;
        float float3 = 0.0f;
        float n = 0.0f;
        final String attribute = element.getAttribute("margin");
        try {
            if (attribute.length() != 0) {
                float2 = (float1 = (float3 = (n = Float.parseFloat(attribute))));
            }
        }
        catch (NumberFormatException ex) {}
        final String attribute2 = element.getAttribute("top-margin");
        try {
            if (attribute2.length() != 0) {
                float1 = Float.parseFloat(attribute2);
            }
        }
        catch (NumberFormatException ex2) {}
        final String attribute3 = element.getAttribute("right-margin");
        try {
            if (attribute3.length() != 0) {
                float2 = Float.parseFloat(attribute3);
            }
        }
        catch (NumberFormatException ex3) {}
        final String attribute4 = element.getAttribute("bottom-margin");
        try {
            if (attribute4.length() != 0) {
                float3 = Float.parseFloat(attribute4);
            }
        }
        catch (NumberFormatException ex4) {}
        final String attribute5 = element.getAttribute("left-margin");
        try {
            if (attribute5.length() != 0) {
                n = Float.parseFloat(attribute5);
            }
        }
        catch (NumberFormatException ex5) {}
        float float4 = 0.0f;
        final String attribute6 = element.getAttribute("indent");
        try {
            if (attribute6.length() != 0) {
                float4 = Float.parseFloat(attribute6);
            }
        }
        catch (NumberFormatException ex6) {}
        int n2 = 0;
        final String attribute7 = element.getAttribute("justification");
        try {
            if (attribute7.length() != 0) {
                if ("start".equals(attribute7)) {
                    n2 = 0;
                }
                else if ("middle".equals(attribute7)) {
                    n2 = 1;
                }
                else if ("end".equals(attribute7)) {
                    n2 = 2;
                }
                else if ("full".equals(attribute7)) {
                    n2 = 3;
                }
            }
        }
        catch (NumberFormatException ex7) {}
        return new MarginInfo(float1, float2, float3, n, float4, n2, element.getLocalName().equals("flowRegionBreak"));
    }
    
    static {
        FLOW_PARAGRAPH = GVTAttributedCharacterIterator.TextAttribute.FLOW_PARAGRAPH;
        FLOW_EMPTY_PARAGRAPH = GVTAttributedCharacterIterator.TextAttribute.FLOW_EMPTY_PARAGRAPH;
        FLOW_LINE_BREAK = GVTAttributedCharacterIterator.TextAttribute.FLOW_LINE_BREAK;
        FLOW_REGIONS = GVTAttributedCharacterIterator.TextAttribute.FLOW_REGIONS;
        PREFORMATTED = GVTAttributedCharacterIterator.TextAttribute.PREFORMATTED;
        TEXTPATH = GVTAttributedCharacterIterator.TextAttribute.TEXTPATH;
        ANCHOR_TYPE = GVTAttributedCharacterIterator.TextAttribute.ANCHOR_TYPE;
        LETTER_SPACING = GVTAttributedCharacterIterator.TextAttribute.LETTER_SPACING;
        WORD_SPACING = GVTAttributedCharacterIterator.TextAttribute.WORD_SPACING;
        KERNING = GVTAttributedCharacterIterator.TextAttribute.KERNING;
    }
    
    protected class BatikFlowContentBridge extends AbstractTextChildTextContent
    {
        public BatikFlowContentBridge(final BridgeContext bridgeContext, final SVGTextElementBridge svgTextElementBridge, final Element element) {
            super(bridgeContext, svgTextElementBridge, element);
        }
    }
    
    public static class LineBreakInfo
    {
        int breakIdx;
        float lineAdvAdj;
        boolean relative;
        
        public LineBreakInfo(final int breakIdx, final float lineAdvAdj, final boolean relative) {
            this.breakIdx = breakIdx;
            this.lineAdvAdj = lineAdvAdj;
            this.relative = relative;
        }
        
        public int getBreakIdx() {
            return this.breakIdx;
        }
        
        public boolean isRelative() {
            return this.relative;
        }
        
        public float getLineAdvAdj() {
            return this.lineAdvAdj;
        }
    }
}
