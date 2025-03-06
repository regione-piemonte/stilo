// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.css.engine;

import org.w3c.css.sac.CSSException;
import org.w3c.dom.events.MutationEvent;
import org.w3c.dom.events.Event;
import org.w3c.dom.Attr;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import org.w3c.css.sac.InputSource;
import org.w3c.css.sac.SelectorList;
import org.apache.batik.css.engine.sac.ExtendedSelector;
import org.apache.batik.css.engine.value.ComputedValue;
import org.apache.batik.css.engine.value.InheritValue;
import org.apache.batik.css.engine.value.Value;
import org.w3c.dom.NamedNodeMap;
import org.w3c.css.sac.DocumentHandler;
import org.w3c.css.sac.ConditionFactory;
import org.apache.batik.css.engine.sac.CSSSelectorFactory;
import org.w3c.css.sac.LexicalUnit;
import java.util.ArrayList;
import org.w3c.dom.DOMException;
import org.w3c.dom.events.EventTarget;
import java.util.HashSet;
import java.util.Collections;
import java.util.LinkedList;
import org.w3c.dom.Element;
import org.apache.batik.css.engine.sac.CSSConditionFactory;
import org.w3c.dom.Node;
import org.w3c.dom.events.EventListener;
import java.util.Set;
import java.util.List;
import org.w3c.css.sac.SACMediaList;
import org.apache.batik.css.parser.ExtendedParser;
import org.apache.batik.css.engine.value.ShorthandManager;
import org.apache.batik.css.engine.value.ValueManager;
import org.apache.batik.util.ParsedURL;
import org.w3c.dom.Document;

public abstract class CSSEngine
{
    protected CSSEngineUserAgent userAgent;
    protected CSSContext cssContext;
    protected Document document;
    protected ParsedURL documentURI;
    protected boolean isCSSNavigableDocument;
    protected StringIntMap indexes;
    protected StringIntMap shorthandIndexes;
    protected ValueManager[] valueManagers;
    protected ShorthandManager[] shorthandManagers;
    protected ExtendedParser parser;
    protected String[] pseudoElementNames;
    protected int fontSizeIndex;
    protected int lineHeightIndex;
    protected int colorIndex;
    protected StyleSheet userAgentStyleSheet;
    protected StyleSheet userStyleSheet;
    protected SACMediaList media;
    protected List styleSheetNodes;
    protected List fontFaces;
    protected String styleNamespaceURI;
    protected String styleLocalName;
    protected String classNamespaceURI;
    protected String classLocalName;
    protected Set nonCSSPresentationalHints;
    protected String nonCSSPresentationalHintsNamespaceURI;
    protected StyleDeclarationDocumentHandler styleDeclarationDocumentHandler;
    protected StyleDeclarationUpdateHandler styleDeclarationUpdateHandler;
    protected StyleSheetDocumentHandler styleSheetDocumentHandler;
    protected StyleDeclarationBuilder styleDeclarationBuilder;
    protected CSSStylableElement element;
    protected ParsedURL cssBaseURI;
    protected String alternateStyleSheet;
    protected CSSNavigableDocumentHandler cssNavigableDocumentListener;
    protected EventListener domAttrModifiedListener;
    protected EventListener domNodeInsertedListener;
    protected EventListener domNodeRemovedListener;
    protected EventListener domSubtreeModifiedListener;
    protected EventListener domCharacterDataModifiedListener;
    protected boolean styleSheetRemoved;
    protected Node removedStylableElementSibling;
    protected List listeners;
    protected Set selectorAttributes;
    protected final int[] ALL_PROPERTIES;
    protected CSSConditionFactory cssConditionFactory;
    protected static final CSSEngineListener[] LISTENER_ARRAY;
    
    public static Node getCSSParentNode(final Node node) {
        if (node instanceof CSSNavigableNode) {
            return ((CSSNavigableNode)node).getCSSParentNode();
        }
        return node.getParentNode();
    }
    
    protected static Node getCSSFirstChild(final Node node) {
        if (node instanceof CSSNavigableNode) {
            return ((CSSNavigableNode)node).getCSSFirstChild();
        }
        return node.getFirstChild();
    }
    
    protected static Node getCSSNextSibling(final Node node) {
        if (node instanceof CSSNavigableNode) {
            return ((CSSNavigableNode)node).getCSSNextSibling();
        }
        return node.getNextSibling();
    }
    
    protected static Node getCSSPreviousSibling(final Node node) {
        if (node instanceof CSSNavigableNode) {
            return ((CSSNavigableNode)node).getCSSPreviousSibling();
        }
        return node.getPreviousSibling();
    }
    
    public static CSSStylableElement getParentCSSStylableElement(final Element element) {
        for (Node node = getCSSParentNode(element); node != null; node = getCSSParentNode(node)) {
            if (node instanceof CSSStylableElement) {
                return (CSSStylableElement)node;
            }
        }
        return null;
    }
    
    protected CSSEngine(final Document document, final ParsedURL documentURI, final ExtendedParser parser, final ValueManager[] valueManagers, final ShorthandManager[] shorthandManagers, final String[] pseudoElementNames, final String styleNamespaceURI, final String styleLocalName, final String classNamespaceURI, final String classLocalName, final boolean b, final String nonCSSPresentationalHintsNamespaceURI, final CSSContext cssContext) {
        this.fontSizeIndex = -1;
        this.lineHeightIndex = -1;
        this.colorIndex = -1;
        this.fontFaces = new LinkedList();
        this.styleDeclarationDocumentHandler = new StyleDeclarationDocumentHandler();
        this.styleSheetDocumentHandler = new StyleSheetDocumentHandler();
        this.styleDeclarationBuilder = new StyleDeclarationBuilder();
        this.listeners = Collections.synchronizedList(new LinkedList<Object>());
        this.document = document;
        this.documentURI = documentURI;
        this.parser = parser;
        this.pseudoElementNames = pseudoElementNames;
        this.styleNamespaceURI = styleNamespaceURI;
        this.styleLocalName = styleLocalName;
        this.classNamespaceURI = classNamespaceURI;
        this.classLocalName = classLocalName;
        this.cssContext = cssContext;
        this.isCSSNavigableDocument = (document instanceof CSSNavigableDocument);
        this.cssConditionFactory = new CSSConditionFactory(classNamespaceURI, classLocalName, null, "id");
        final int length = valueManagers.length;
        this.indexes = new StringIntMap(length);
        this.valueManagers = valueManagers;
        for (int i = length - 1; i >= 0; --i) {
            final String propertyName = valueManagers[i].getPropertyName();
            this.indexes.put(propertyName, i);
            if (this.fontSizeIndex == -1 && propertyName.equals("font-size")) {
                this.fontSizeIndex = i;
            }
            if (this.lineHeightIndex == -1 && propertyName.equals("line-height")) {
                this.lineHeightIndex = i;
            }
            if (this.colorIndex == -1 && propertyName.equals("color")) {
                this.colorIndex = i;
            }
        }
        final int length2 = shorthandManagers.length;
        this.shorthandIndexes = new StringIntMap(length2);
        this.shorthandManagers = shorthandManagers;
        for (int j = length2 - 1; j >= 0; --j) {
            this.shorthandIndexes.put(shorthandManagers[j].getPropertyName(), j);
        }
        if (b) {
            this.nonCSSPresentationalHints = new HashSet(valueManagers.length + shorthandManagers.length);
            this.nonCSSPresentationalHintsNamespaceURI = nonCSSPresentationalHintsNamespaceURI;
            for (int length3 = valueManagers.length, k = 0; k < length3; ++k) {
                this.nonCSSPresentationalHints.add(valueManagers[k].getPropertyName());
            }
            for (int length4 = shorthandManagers.length, l = 0; l < length4; ++l) {
                this.nonCSSPresentationalHints.add(shorthandManagers[l].getPropertyName());
            }
        }
        if (this.cssContext.isDynamic() && this.document instanceof EventTarget) {
            this.addEventListeners((EventTarget)this.document);
            this.styleDeclarationUpdateHandler = new StyleDeclarationUpdateHandler();
        }
        this.ALL_PROPERTIES = new int[this.getNumberOfProperties()];
        for (int n = this.getNumberOfProperties() - 1; n >= 0; --n) {
            this.ALL_PROPERTIES[n] = n;
        }
    }
    
    protected void addEventListeners(final EventTarget eventTarget) {
        if (this.isCSSNavigableDocument) {
            this.cssNavigableDocumentListener = new CSSNavigableDocumentHandler();
            ((CSSNavigableDocument)eventTarget).addCSSNavigableDocumentListener(this.cssNavigableDocumentListener);
        }
        else {
            eventTarget.addEventListener("DOMAttrModified", this.domAttrModifiedListener = new DOMAttrModifiedListener(), false);
            eventTarget.addEventListener("DOMNodeInserted", this.domNodeInsertedListener = new DOMNodeInsertedListener(), false);
            eventTarget.addEventListener("DOMNodeRemoved", this.domNodeRemovedListener = new DOMNodeRemovedListener(), false);
            eventTarget.addEventListener("DOMSubtreeModified", this.domSubtreeModifiedListener = new DOMSubtreeModifiedListener(), false);
            eventTarget.addEventListener("DOMCharacterDataModified", this.domCharacterDataModifiedListener = new DOMCharacterDataModifiedListener(), false);
        }
    }
    
    protected void removeEventListeners(final EventTarget eventTarget) {
        if (this.isCSSNavigableDocument) {
            ((CSSNavigableDocument)eventTarget).removeCSSNavigableDocumentListener(this.cssNavigableDocumentListener);
        }
        else {
            eventTarget.removeEventListener("DOMAttrModified", this.domAttrModifiedListener, false);
            eventTarget.removeEventListener("DOMNodeInserted", this.domNodeInsertedListener, false);
            eventTarget.removeEventListener("DOMNodeRemoved", this.domNodeRemovedListener, false);
            eventTarget.removeEventListener("DOMSubtreeModified", this.domSubtreeModifiedListener, false);
            eventTarget.removeEventListener("DOMCharacterDataModified", this.domCharacterDataModifiedListener, false);
        }
    }
    
    public void dispose() {
        this.setCSSEngineUserAgent(null);
        this.disposeStyleMaps(this.document.getDocumentElement());
        if (this.document instanceof EventTarget) {
            this.removeEventListeners((EventTarget)this.document);
        }
    }
    
    protected void disposeStyleMaps(final Node node) {
        if (node instanceof CSSStylableElement) {
            ((CSSStylableElement)node).setComputedStyleMap(null, null);
        }
        for (Node node2 = getCSSFirstChild(node); node2 != null; node2 = getCSSNextSibling(node2)) {
            if (node2.getNodeType() == 1) {
                this.disposeStyleMaps(node2);
            }
        }
    }
    
    public CSSContext getCSSContext() {
        return this.cssContext;
    }
    
    public Document getDocument() {
        return this.document;
    }
    
    public int getFontSizeIndex() {
        return this.fontSizeIndex;
    }
    
    public int getLineHeightIndex() {
        return this.lineHeightIndex;
    }
    
    public int getColorIndex() {
        return this.colorIndex;
    }
    
    public int getNumberOfProperties() {
        return this.valueManagers.length;
    }
    
    public int getPropertyIndex(final String s) {
        return this.indexes.get(s);
    }
    
    public int getShorthandIndex(final String s) {
        return this.shorthandIndexes.get(s);
    }
    
    public String getPropertyName(final int n) {
        return this.valueManagers[n].getPropertyName();
    }
    
    public void setCSSEngineUserAgent(final CSSEngineUserAgent userAgent) {
        this.userAgent = userAgent;
    }
    
    public CSSEngineUserAgent getCSSEngineUserAgent() {
        return this.userAgent;
    }
    
    public void setUserAgentStyleSheet(final StyleSheet userAgentStyleSheet) {
        this.userAgentStyleSheet = userAgentStyleSheet;
    }
    
    public void setUserStyleSheet(final StyleSheet userStyleSheet) {
        this.userStyleSheet = userStyleSheet;
    }
    
    public ValueManager[] getValueManagers() {
        return this.valueManagers;
    }
    
    public ShorthandManager[] getShorthandManagers() {
        return this.shorthandManagers;
    }
    
    public List getFontFaces() {
        return this.fontFaces;
    }
    
    public void setMedia(final String s) {
        try {
            this.media = this.parser.parseMedia(s);
        }
        catch (Exception ex) {
            String message = ex.getMessage();
            if (message == null) {
                message = "";
            }
            throw new DOMException((short)12, Messages.formatMessage("media.error", new Object[] { s, message }));
        }
    }
    
    public void setAlternateStyleSheet(final String alternateStyleSheet) {
        this.alternateStyleSheet = alternateStyleSheet;
    }
    
    public void importCascadedStyleMaps(final Element element, final CSSEngine cssEngine, final Element element2) {
        if (element instanceof CSSStylableElement) {
            final CSSStylableElement cssStylableElement = (CSSStylableElement)element;
            final CSSStylableElement cssStylableElement2 = (CSSStylableElement)element2;
            final StyleMap cascadedStyleMap = cssEngine.getCascadedStyleMap(cssStylableElement, null);
            cascadedStyleMap.setFixedCascadedStyle(true);
            cssStylableElement2.setComputedStyleMap(null, cascadedStyleMap);
            if (this.pseudoElementNames != null) {
                for (int length = this.pseudoElementNames.length, i = 0; i < length; ++i) {
                    final String s = this.pseudoElementNames[i];
                    cssStylableElement2.setComputedStyleMap(s, cssEngine.getCascadedStyleMap(cssStylableElement, s));
                }
            }
        }
        for (Node node = getCSSFirstChild(element2), node2 = getCSSFirstChild(element); node != null; node = getCSSNextSibling(node), node2 = getCSSNextSibling(node2)) {
            if (node2.getNodeType() == 1) {
                this.importCascadedStyleMaps((Element)node2, cssEngine, (Element)node);
            }
        }
    }
    
    public ParsedURL getCSSBaseURI() {
        if (this.cssBaseURI == null) {
            this.cssBaseURI = this.element.getCSSBase();
        }
        return this.cssBaseURI;
    }
    
    public StyleMap getCascadedStyleMap(final CSSStylableElement element, final String s) {
        final StyleMap styleMap = new StyleMap(this.getNumberOfProperties());
        if (this.userAgentStyleSheet != null) {
            final ArrayList list = new ArrayList();
            this.addMatchingRules(list, this.userAgentStyleSheet, element, s);
            this.addRules(element, s, styleMap, list, (short)0);
        }
        if (this.userStyleSheet != null) {
            final ArrayList list2 = new ArrayList();
            this.addMatchingRules(list2, this.userStyleSheet, element, s);
            this.addRules(element, s, styleMap, list2, (short)8192);
        }
        this.element = element;
        try {
            if (this.nonCSSPresentationalHints != null) {
                final ShorthandManager.PropertyHandler propertyHandler = new ShorthandManager.PropertyHandler() {
                    public void property(final String s, final LexicalUnit lexicalUnit, final boolean b) {
                        final int propertyIndex = CSSEngine.this.getPropertyIndex(s);
                        if (propertyIndex != -1) {
                            CSSEngine.this.putAuthorProperty(styleMap, propertyIndex, CSSEngine.this.valueManagers[propertyIndex].createValue(lexicalUnit, CSSEngine.this), b, (short)16384);
                            return;
                        }
                        final int shorthandIndex = CSSEngine.this.getShorthandIndex(s);
                        if (shorthandIndex == -1) {
                            return;
                        }
                        CSSEngine.this.shorthandManagers[shorthandIndex].setValues(CSSEngine.this, this, lexicalUnit, b);
                    }
                };
                final NamedNodeMap attributes = element.getAttributes();
                for (int length = attributes.getLength(), i = 0; i < length; ++i) {
                    final Node item = attributes.item(i);
                    final String nodeName = item.getNodeName();
                    if (this.nonCSSPresentationalHints.contains(nodeName)) {
                        final String nodeValue = item.getNodeValue();
                        try {
                            propertyHandler.property(nodeName, this.parser.parsePropertyValue(item.getNodeValue()), false);
                        }
                        catch (Exception obj) {
                            System.err.println("\n***** CSSEngine: exception property.syntax.error:" + obj);
                            System.err.println("\nAttrValue:" + nodeValue);
                            System.err.println("\nException:" + obj.getClass().getName());
                            obj.printStackTrace(System.err);
                            System.err.println("\n***** CSSEngine: exception....");
                            String message = obj.getMessage();
                            if (message == null) {
                                message = "";
                            }
                            final DOMException ex = new DOMException((short)12, Messages.formatMessage("property.syntax.error.at", new Object[] { (this.documentURI == null) ? "<unknown>" : this.documentURI.toString(), nodeName, item.getNodeValue(), message }));
                            if (this.userAgent == null) {
                                throw ex;
                            }
                            this.userAgent.displayError(ex);
                        }
                    }
                }
            }
            final List styleSheetNodes = this.cssContext.getCSSEngineForElement(element).getStyleSheetNodes();
            final int size = styleSheetNodes.size();
            if (size > 0) {
                final ArrayList list3 = new ArrayList();
                for (int j = 0; j < size; ++j) {
                    final StyleSheet cssStyleSheet = styleSheetNodes.get(j).getCSSStyleSheet();
                    if (cssStyleSheet != null && (!cssStyleSheet.isAlternate() || cssStyleSheet.getTitle() == null || cssStyleSheet.getTitle().equals(this.alternateStyleSheet)) && this.mediaMatch(cssStyleSheet.getMedia())) {
                        this.addMatchingRules(list3, cssStyleSheet, element, s);
                    }
                }
                this.addRules(element, s, styleMap, list3, (short)24576);
            }
            if (this.styleLocalName != null) {
                final String attributeNS = element.getAttributeNS(this.styleNamespaceURI, this.styleLocalName);
                if (attributeNS.length() > 0) {
                    try {
                        this.parser.setSelectorFactory(CSSSelectorFactory.INSTANCE);
                        this.parser.setConditionFactory((ConditionFactory)this.cssConditionFactory);
                        this.styleDeclarationDocumentHandler.styleMap = styleMap;
                        this.parser.setDocumentHandler((DocumentHandler)this.styleDeclarationDocumentHandler);
                        this.parser.parseStyleDeclaration(attributeNS);
                        this.styleDeclarationDocumentHandler.styleMap = null;
                    }
                    catch (Exception ex2) {
                        String s2 = ex2.getMessage();
                        if (s2 == null) {
                            s2 = ex2.getClass().getName();
                        }
                        final DOMException ex3 = new DOMException((short)12, Messages.formatMessage("style.syntax.error.at", new Object[] { (this.documentURI == null) ? "<unknown>" : this.documentURI.toString(), this.styleLocalName, attributeNS, s2 }));
                        if (this.userAgent == null) {
                            throw ex3;
                        }
                        this.userAgent.displayError(ex3);
                    }
                }
            }
            final StyleDeclarationProvider overrideStyleDeclarationProvider = element.getOverrideStyleDeclarationProvider();
            if (overrideStyleDeclarationProvider != null) {
                final StyleDeclaration styleDeclaration = overrideStyleDeclarationProvider.getStyleDeclaration();
                if (styleDeclaration != null) {
                    for (int size2 = styleDeclaration.size(), k = 0; k < size2; ++k) {
                        final int index = styleDeclaration.getIndex(k);
                        final Value value = styleDeclaration.getValue(k);
                        final boolean priority = styleDeclaration.getPriority(k);
                        if (!styleMap.isImportant(index) || priority) {
                            styleMap.putValue(index, value);
                            styleMap.putImportant(index, priority);
                            styleMap.putOrigin(index, (short)(-24576));
                        }
                    }
                }
            }
        }
        finally {
            this.element = null;
            this.cssBaseURI = null;
        }
        return styleMap;
    }
    
    public Value getComputedStyle(final CSSStylableElement cssStylableElement, final String s, final int n) {
        StyleMap styleMap = cssStylableElement.getComputedStyleMap(s);
        if (styleMap == null) {
            styleMap = this.getCascadedStyleMap(cssStylableElement, s);
            cssStylableElement.setComputedStyleMap(s, styleMap);
        }
        final Value value = styleMap.getValue(n);
        if (styleMap.isComputed(n)) {
            return value;
        }
        Value defaultValue = value;
        final ValueManager valueManager = this.valueManagers[n];
        final CSSStylableElement parentCSSStylableElement = getParentCSSStylableElement(cssStylableElement);
        if (value == null) {
            if (parentCSSStylableElement == null || !valueManager.isInheritedProperty()) {
                defaultValue = valueManager.getDefaultValue();
            }
        }
        else if (parentCSSStylableElement != null && value == InheritValue.INSTANCE) {
            defaultValue = null;
        }
        Value computedValue;
        if (defaultValue == null) {
            computedValue = this.getComputedStyle(parentCSSStylableElement, null, n);
            styleMap.putParentRelative(n, true);
            styleMap.putInherited(n, true);
        }
        else {
            computedValue = valueManager.computeValue(cssStylableElement, s, this, n, styleMap, defaultValue);
        }
        if (value == null) {
            styleMap.putValue(n, computedValue);
            styleMap.putNullCascaded(n, true);
        }
        else if (computedValue != value) {
            final ComputedValue computedValue2 = new ComputedValue(value);
            computedValue2.setComputedValue(computedValue);
            styleMap.putValue(n, computedValue2);
            computedValue = computedValue2;
        }
        styleMap.putComputed(n, true);
        return computedValue;
    }
    
    public List getStyleSheetNodes() {
        if (this.styleSheetNodes == null) {
            this.styleSheetNodes = new ArrayList();
            this.selectorAttributes = new HashSet();
            this.findStyleSheetNodes(this.document);
            for (int size = this.styleSheetNodes.size(), i = 0; i < size; ++i) {
                final StyleSheet cssStyleSheet = this.styleSheetNodes.get(i).getCSSStyleSheet();
                if (cssStyleSheet != null) {
                    this.findSelectorAttributes(this.selectorAttributes, cssStyleSheet);
                }
            }
        }
        return this.styleSheetNodes;
    }
    
    protected void findStyleSheetNodes(final Node node) {
        if (node instanceof CSSStyleSheetNode) {
            this.styleSheetNodes.add(node);
        }
        for (Node node2 = getCSSFirstChild(node); node2 != null; node2 = getCSSNextSibling(node2)) {
            this.findStyleSheetNodes(node2);
        }
    }
    
    protected void findSelectorAttributes(final Set set, final StyleSheet styleSheet) {
        for (int size = styleSheet.getSize(), i = 0; i < size; ++i) {
            final Rule rule = styleSheet.getRule(i);
            switch (rule.getType()) {
                case 0: {
                    final SelectorList selectorList = ((StyleRule)rule).getSelectorList();
                    for (int length = selectorList.getLength(), j = 0; j < length; ++j) {
                        ((ExtendedSelector)selectorList.item(j)).fillAttributeSet(set);
                    }
                    break;
                }
                case 1:
                case 2: {
                    final MediaRule mediaRule = (MediaRule)rule;
                    if (this.mediaMatch(mediaRule.getMediaList())) {
                        this.findSelectorAttributes(set, mediaRule);
                        break;
                    }
                    break;
                }
            }
        }
    }
    
    public void setMainProperties(final CSSStylableElement element, final MainPropertyReceiver mainPropertyReceiver, final String s, final String s2, final boolean b) {
        try {
            this.element = element;
            new ShorthandManager.PropertyHandler() {
                public void property(final String s, final LexicalUnit lexicalUnit, final boolean b) {
                    final int propertyIndex = CSSEngine.this.getPropertyIndex(s);
                    if (propertyIndex != -1) {
                        mainPropertyReceiver.setMainProperty(s, CSSEngine.this.valueManagers[propertyIndex].createValue(lexicalUnit, CSSEngine.this), b);
                        return;
                    }
                    final int shorthandIndex = CSSEngine.this.getShorthandIndex(s);
                    if (shorthandIndex == -1) {
                        return;
                    }
                    CSSEngine.this.shorthandManagers[shorthandIndex].setValues(CSSEngine.this, this, lexicalUnit, b);
                }
            }.property(s, this.parser.parsePropertyValue(s2), b);
        }
        catch (Exception ex) {
            String message = ex.getMessage();
            if (message == null) {
                message = "";
            }
            final DOMException ex2 = new DOMException((short)12, Messages.formatMessage("property.syntax.error.at", new Object[] { (this.documentURI == null) ? "<unknown>" : this.documentURI.toString(), s, s2, message }));
            if (this.userAgent == null) {
                throw ex2;
            }
            this.userAgent.displayError(ex2);
        }
        finally {
            this.element = null;
            this.cssBaseURI = null;
        }
    }
    
    public Value parsePropertyValue(final CSSStylableElement element, final String s, final String s2) {
        final int propertyIndex = this.getPropertyIndex(s);
        if (propertyIndex == -1) {
            return null;
        }
        final ValueManager valueManager = this.valueManagers[propertyIndex];
        try {
            this.element = element;
            return valueManager.createValue(this.parser.parsePropertyValue(s2), this);
        }
        catch (Exception ex) {
            String message = ex.getMessage();
            if (message == null) {
                message = "";
            }
            final DOMException ex2 = new DOMException((short)12, Messages.formatMessage("property.syntax.error.at", new Object[] { (this.documentURI == null) ? "<unknown>" : this.documentURI.toString(), s, s2, message }));
            if (this.userAgent == null) {
                throw ex2;
            }
            this.userAgent.displayError(ex2);
        }
        finally {
            this.element = null;
            this.cssBaseURI = null;
        }
        return valueManager.getDefaultValue();
    }
    
    public StyleDeclaration parseStyleDeclaration(final CSSStylableElement element, final String s) {
        this.styleDeclarationBuilder.styleDeclaration = new StyleDeclaration();
        try {
            this.element = element;
            this.parser.setSelectorFactory(CSSSelectorFactory.INSTANCE);
            this.parser.setConditionFactory((ConditionFactory)this.cssConditionFactory);
            this.parser.setDocumentHandler((DocumentHandler)this.styleDeclarationBuilder);
            this.parser.parseStyleDeclaration(s);
        }
        catch (Exception ex) {
            String message = ex.getMessage();
            if (message == null) {
                message = "";
            }
            final DOMException ex2 = new DOMException((short)12, Messages.formatMessage("syntax.error.at", new Object[] { (this.documentURI == null) ? "<unknown>" : this.documentURI.toString(), message }));
            if (this.userAgent == null) {
                throw ex2;
            }
            this.userAgent.displayError(ex2);
        }
        finally {
            this.element = null;
            this.cssBaseURI = null;
        }
        return this.styleDeclarationBuilder.styleDeclaration;
    }
    
    public StyleSheet parseStyleSheet(final ParsedURL parsedURL, final String s) throws DOMException {
        final StyleSheet styleSheet = new StyleSheet();
        try {
            styleSheet.setMedia(this.parser.parseMedia(s));
        }
        catch (Exception ex) {
            String message = ex.getMessage();
            if (message == null) {
                message = "";
            }
            final DOMException ex2 = new DOMException((short)12, Messages.formatMessage("syntax.error.at", new Object[] { (this.documentURI == null) ? "<unknown>" : this.documentURI.toString(), message }));
            if (this.userAgent == null) {
                throw ex2;
            }
            this.userAgent.displayError(ex2);
            return styleSheet;
        }
        this.parseStyleSheet(styleSheet, parsedURL);
        return styleSheet;
    }
    
    public StyleSheet parseStyleSheet(final InputSource inputSource, final ParsedURL parsedURL, final String s) throws DOMException {
        final StyleSheet styleSheet = new StyleSheet();
        try {
            styleSheet.setMedia(this.parser.parseMedia(s));
            this.parseStyleSheet(styleSheet, inputSource, parsedURL);
        }
        catch (Exception ex) {
            String message = ex.getMessage();
            if (message == null) {
                message = "";
            }
            final DOMException ex2 = new DOMException((short)12, Messages.formatMessage("syntax.error.at", new Object[] { (this.documentURI == null) ? "<unknown>" : this.documentURI.toString(), message }));
            if (this.userAgent == null) {
                throw ex2;
            }
            this.userAgent.displayError(ex2);
        }
        return styleSheet;
    }
    
    public void parseStyleSheet(final StyleSheet styleSheet, final ParsedURL parsedURL) throws DOMException {
        if (parsedURL != null) {
            try {
                this.cssContext.checkLoadExternalResource(parsedURL, this.documentURI);
                this.parseStyleSheet(styleSheet, new InputSource(parsedURL.toString()), parsedURL);
            }
            catch (SecurityException ex) {
                throw ex;
            }
            catch (Exception ex2) {
                String s = ex2.getMessage();
                if (s == null) {
                    s = ex2.getClass().getName();
                }
                final DOMException ex3 = new DOMException((short)12, Messages.formatMessage("syntax.error.at", new Object[] { parsedURL.toString(), s }));
                if (this.userAgent == null) {
                    throw ex3;
                }
                this.userAgent.displayError(ex3);
            }
            return;
        }
        final DOMException ex4 = new DOMException((short)12, Messages.formatMessage("syntax.error.at", new Object[] { "Null Document reference", "" }));
        if (this.userAgent == null) {
            throw ex4;
        }
        this.userAgent.displayError(ex4);
    }
    
    public StyleSheet parseStyleSheet(final String s, final ParsedURL parsedURL, final String s2) throws DOMException {
        final StyleSheet styleSheet = new StyleSheet();
        try {
            styleSheet.setMedia(this.parser.parseMedia(s2));
        }
        catch (Exception ex) {
            String message = ex.getMessage();
            if (message == null) {
                message = "";
            }
            final DOMException ex2 = new DOMException((short)12, Messages.formatMessage("syntax.error.at", new Object[] { (this.documentURI == null) ? "<unknown>" : this.documentURI.toString(), message }));
            if (this.userAgent == null) {
                throw ex2;
            }
            this.userAgent.displayError(ex2);
            return styleSheet;
        }
        this.parseStyleSheet(styleSheet, s, parsedURL);
        return styleSheet;
    }
    
    public void parseStyleSheet(final StyleSheet styleSheet, final String s, final ParsedURL parsedURL) throws DOMException {
        try {
            this.parseStyleSheet(styleSheet, new InputSource((Reader)new StringReader(s)), parsedURL);
        }
        catch (Exception ex) {
            String message = ex.getMessage();
            if (message == null) {
                message = "";
            }
            final DOMException ex2 = new DOMException((short)12, Messages.formatMessage("stylesheet.syntax.error", new Object[] { parsedURL.toString(), s, message }));
            if (this.userAgent == null) {
                throw ex2;
            }
            this.userAgent.displayError(ex2);
        }
    }
    
    protected void parseStyleSheet(final StyleSheet styleSheet, final InputSource inputSource, final ParsedURL cssBaseURI) throws IOException {
        this.parser.setSelectorFactory(CSSSelectorFactory.INSTANCE);
        this.parser.setConditionFactory((ConditionFactory)this.cssConditionFactory);
        try {
            this.cssBaseURI = cssBaseURI;
            this.styleSheetDocumentHandler.styleSheet = styleSheet;
            this.parser.setDocumentHandler((DocumentHandler)this.styleSheetDocumentHandler);
            this.parser.parseStyleSheet(inputSource);
            for (int size = styleSheet.getSize(), i = 0; i < size; ++i) {
                final Rule rule = styleSheet.getRule(i);
                if (rule.getType() != 2) {
                    break;
                }
                final ImportRule importRule = (ImportRule)rule;
                this.parseStyleSheet(importRule, importRule.getURI());
            }
        }
        finally {
            this.cssBaseURI = null;
        }
    }
    
    protected void putAuthorProperty(final StyleMap styleMap, final int n, final Value value, final boolean b, final short n2) {
        final Value value2 = styleMap.getValue(n);
        final short origin = styleMap.getOrigin(n);
        final boolean important = styleMap.isImportant(n);
        int n3 = (value2 == null) ? 1 : 0;
        if (n3 == 0) {
            switch (origin) {
                case 8192: {
                    n3 = (important ? 0 : 1);
                    break;
                }
                case 24576: {
                    n3 = ((!important || b) ? 1 : 0);
                    break;
                }
                case -24576: {
                    n3 = 0;
                    break;
                }
                default: {
                    n3 = 1;
                    break;
                }
            }
        }
        if (n3 != 0) {
            styleMap.putValue(n, value);
            styleMap.putImportant(n, b);
            styleMap.putOrigin(n, n2);
        }
    }
    
    protected void addMatchingRules(final List list, final StyleSheet styleSheet, final Element element, final String s) {
        for (int size = styleSheet.getSize(), i = 0; i < size; ++i) {
            final Rule rule = styleSheet.getRule(i);
            switch (rule.getType()) {
                case 0: {
                    final StyleRule styleRule = (StyleRule)rule;
                    final SelectorList selectorList = styleRule.getSelectorList();
                    for (int length = selectorList.getLength(), j = 0; j < length; ++j) {
                        if (((ExtendedSelector)selectorList.item(j)).match(element, s)) {
                            list.add(styleRule);
                        }
                    }
                    break;
                }
                case 1:
                case 2: {
                    final MediaRule mediaRule = (MediaRule)rule;
                    if (this.mediaMatch(mediaRule.getMediaList())) {
                        this.addMatchingRules(list, mediaRule, element, s);
                        break;
                    }
                    break;
                }
            }
        }
    }
    
    protected void addRules(final Element element, final String s, final StyleMap styleMap, final ArrayList list, final short n) {
        this.sortRules(list, element, s);
        final int size = list.size();
        if (n == 24576) {
            for (int i = 0; i < size; ++i) {
                final StyleDeclaration styleDeclaration = list.get(i).getStyleDeclaration();
                for (int size2 = styleDeclaration.size(), j = 0; j < size2; ++j) {
                    this.putAuthorProperty(styleMap, styleDeclaration.getIndex(j), styleDeclaration.getValue(j), styleDeclaration.getPriority(j), n);
                }
            }
        }
        else {
            for (int k = 0; k < size; ++k) {
                final StyleDeclaration styleDeclaration2 = list.get(k).getStyleDeclaration();
                for (int size3 = styleDeclaration2.size(), l = 0; l < size3; ++l) {
                    final int index = styleDeclaration2.getIndex(l);
                    styleMap.putValue(index, styleDeclaration2.getValue(l));
                    styleMap.putImportant(index, styleDeclaration2.getPriority(l));
                    styleMap.putOrigin(index, n);
                }
            }
        }
    }
    
    protected void sortRules(final ArrayList list, final Element element, final String s) {
        final int size = list.size();
        final int[] array = new int[size];
        for (int i = 0; i < size; ++i) {
            final SelectorList selectorList = list.get(i).getSelectorList();
            int n = 0;
            for (int length = selectorList.getLength(), j = 0; j < length; ++j) {
                final ExtendedSelector extendedSelector = (ExtendedSelector)selectorList.item(j);
                if (extendedSelector.match(element, s)) {
                    final int specificity = extendedSelector.getSpecificity();
                    if (specificity > n) {
                        n = specificity;
                    }
                }
            }
            array[i] = n;
        }
        for (int k = 1; k < size; ++k) {
            final StyleRule value = list.get(k);
            int n2;
            int index;
            for (n2 = array[k], index = k - 1; index >= 0 && array[index] > n2; --index) {
                list.set(index + 1, list.get(index));
                array[index + 1] = array[index];
            }
            list.set(index + 1, value);
            array[index + 1] = n2;
        }
    }
    
    protected boolean mediaMatch(final SACMediaList list) {
        if (this.media == null || list == null || this.media.getLength() == 0 || list.getLength() == 0) {
            return true;
        }
        for (int i = 0; i < list.getLength(); ++i) {
            if (list.item(i).equalsIgnoreCase("all")) {
                return true;
            }
            for (int j = 0; j < this.media.getLength(); ++j) {
                if (this.media.item(j).equalsIgnoreCase("all") || list.item(i).equalsIgnoreCase(this.media.item(j))) {
                    return true;
                }
            }
        }
        return false;
    }
    
    public void addCSSEngineListener(final CSSEngineListener cssEngineListener) {
        this.listeners.add(cssEngineListener);
    }
    
    public void removeCSSEngineListener(final CSSEngineListener cssEngineListener) {
        this.listeners.remove(cssEngineListener);
    }
    
    protected void firePropertiesChangedEvent(final Element element, final int[] array) {
        final CSSEngineListener[] array2 = this.listeners.toArray(CSSEngine.LISTENER_ARRAY);
        final int length = array2.length;
        if (length > 0) {
            final CSSEngineEvent cssEngineEvent = new CSSEngineEvent(this, element, array);
            for (int i = 0; i < length; ++i) {
                array2[i].propertiesChanged(cssEngineEvent);
            }
        }
    }
    
    protected void inlineStyleAttributeUpdated(final CSSStylableElement element, final StyleMap styleMap, final short n, final String s, final String s2) {
        final boolean[] updatedProperties = this.styleDeclarationUpdateHandler.updatedProperties;
        for (int i = this.getNumberOfProperties() - 1; i >= 0; --i) {
            updatedProperties[i] = false;
        }
        switch (n) {
            case 1:
            case 2: {
                if (s2.length() > 0) {
                    this.element = element;
                    try {
                        this.parser.setSelectorFactory(CSSSelectorFactory.INSTANCE);
                        this.parser.setConditionFactory((ConditionFactory)this.cssConditionFactory);
                        this.styleDeclarationUpdateHandler.styleMap = styleMap;
                        this.parser.setDocumentHandler((DocumentHandler)this.styleDeclarationUpdateHandler);
                        this.parser.parseStyleDeclaration(s2);
                        this.styleDeclarationUpdateHandler.styleMap = null;
                    }
                    catch (Exception ex) {
                        String message = ex.getMessage();
                        if (message == null) {
                            message = "";
                        }
                        final DOMException ex2 = new DOMException((short)12, Messages.formatMessage("style.syntax.error.at", new Object[] { (this.documentURI == null) ? "<unknown>" : this.documentURI.toString(), this.styleLocalName, s2, message }));
                        if (this.userAgent == null) {
                            throw ex2;
                        }
                        this.userAgent.displayError(ex2);
                    }
                    finally {
                        this.element = null;
                        this.cssBaseURI = null;
                    }
                }
            }
            case 3: {
                boolean b = false;
                if (s != null && s.length() > 0) {
                    for (int j = this.getNumberOfProperties() - 1; j >= 0; --j) {
                        if (styleMap.isComputed(j) && !updatedProperties[j] && styleMap.getOrigin(j) >= -32768) {
                            b = true;
                            updatedProperties[j] = true;
                        }
                    }
                }
                if (b) {
                    this.invalidateProperties(element, null, updatedProperties, true);
                }
                else {
                    int n2 = 0;
                    final boolean b2 = this.fontSizeIndex != -1 && updatedProperties[this.fontSizeIndex];
                    final boolean b3 = this.lineHeightIndex != -1 && updatedProperties[this.lineHeightIndex];
                    final boolean b4 = this.colorIndex != -1 && updatedProperties[this.colorIndex];
                    for (int k = this.getNumberOfProperties() - 1; k >= 0; --k) {
                        if (updatedProperties[k]) {
                            ++n2;
                        }
                        else if ((b2 && styleMap.isFontSizeRelative(k)) || (b3 && styleMap.isLineHeightRelative(k)) || (b4 && styleMap.isColorRelative(k))) {
                            updatedProperties[k] = true;
                            clearComputedValue(styleMap, k);
                            ++n2;
                        }
                    }
                    if (n2 > 0) {
                        final int[] array = new int[n2];
                        int n3 = 0;
                        for (int l = this.getNumberOfProperties() - 1; l >= 0; --l) {
                            if (updatedProperties[l]) {
                                array[n3++] = l;
                            }
                        }
                        this.invalidateProperties(element, array, null, true);
                    }
                }
            }
            default: {
                throw new IllegalStateException("Invalid attrChangeType");
            }
        }
    }
    
    private static void clearComputedValue(final StyleMap styleMap, final int n) {
        if (styleMap.isNullCascaded(n)) {
            styleMap.putValue(n, null);
        }
        else {
            final Value value = styleMap.getValue(n);
            if (value instanceof ComputedValue) {
                styleMap.putValue(n, ((ComputedValue)value).getCascadedValue());
            }
        }
        styleMap.putComputed(n, false);
    }
    
    protected void invalidateProperties(final Node node, final int[] array, final boolean[] array2, final boolean b) {
        if (!(node instanceof CSSStylableElement)) {
            return;
        }
        final CSSStylableElement cssStylableElement = (CSSStylableElement)node;
        final StyleMap computedStyleMap = cssStylableElement.getComputedStyleMap(null);
        if (computedStyleMap == null) {
            return;
        }
        final boolean[] array3 = new boolean[this.getNumberOfProperties()];
        if (array2 != null) {
            System.arraycopy(array2, 0, array3, 0, array2.length);
        }
        if (array != null) {
            for (int i = 0; i < array.length; ++i) {
                array3[array[i]] = true;
            }
        }
        int n = 0;
        if (!b) {
            for (int j = 0; j < array3.length; ++j) {
                if (array3[j]) {
                    ++n;
                }
            }
        }
        else {
            final StyleMap cascadedStyleMap = this.getCascadedStyleMap(cssStylableElement, null);
            cssStylableElement.setComputedStyleMap(null, cascadedStyleMap);
            for (int k = 0; k < array3.length; ++k) {
                if (array3[k]) {
                    ++n;
                }
                else {
                    final Value value = cascadedStyleMap.getValue(k);
                    Value obj = null;
                    if (!computedStyleMap.isNullCascaded(k)) {
                        obj = computedStyleMap.getValue(k);
                        if (obj instanceof ComputedValue) {
                            obj = ((ComputedValue)obj).getCascadedValue();
                        }
                    }
                    if (value != obj) {
                        if (value != null && obj != null) {
                            if (value.equals(obj)) {
                                continue;
                            }
                            final String cssText = obj.getCssText();
                            final String cssText2 = value.getCssText();
                            if (cssText2 == cssText) {
                                continue;
                            }
                            if (cssText2 != null && cssText2.equals(cssText)) {
                                continue;
                            }
                        }
                        ++n;
                        array3[k] = true;
                    }
                }
            }
        }
        int[] array4 = null;
        if (n != 0) {
            array4 = new int[n];
            int n2 = 0;
            for (int l = 0; l < array3.length; ++l) {
                if (array3[l]) {
                    array4[n2++] = l;
                }
            }
        }
        this.propagateChanges(cssStylableElement, array4, b);
    }
    
    protected void propagateChanges(final Node node, int[] array, final boolean b) {
        if (!(node instanceof CSSStylableElement)) {
            return;
        }
        final CSSStylableElement cssStylableElement = (CSSStylableElement)node;
        final StyleMap computedStyleMap = cssStylableElement.getComputedStyleMap(null);
        if (computedStyleMap != null) {
            final boolean[] updatedProperties = this.styleDeclarationUpdateHandler.updatedProperties;
            for (int i = this.getNumberOfProperties() - 1; i >= 0; --i) {
                updatedProperties[i] = false;
            }
            if (array != null) {
                for (int j = array.length - 1; j >= 0; --j) {
                    updatedProperties[array[j]] = true;
                }
            }
            final boolean b2 = this.fontSizeIndex != -1 && updatedProperties[this.fontSizeIndex];
            final boolean b3 = this.lineHeightIndex != -1 && updatedProperties[this.lineHeightIndex];
            final boolean b4 = this.colorIndex != -1 && updatedProperties[this.colorIndex];
            int n = 0;
            for (int k = this.getNumberOfProperties() - 1; k >= 0; --k) {
                if (updatedProperties[k]) {
                    ++n;
                }
                else if ((b2 && computedStyleMap.isFontSizeRelative(k)) || (b3 && computedStyleMap.isLineHeightRelative(k)) || (b4 && computedStyleMap.isColorRelative(k))) {
                    updatedProperties[k] = true;
                    clearComputedValue(computedStyleMap, k);
                    ++n;
                }
            }
            if (n == 0) {
                array = null;
            }
            else {
                array = new int[n];
                int n2 = 0;
                for (int l = this.getNumberOfProperties() - 1; l >= 0; --l) {
                    if (updatedProperties[l]) {
                        array[n2++] = l;
                    }
                }
                this.firePropertiesChangedEvent(cssStylableElement, array);
            }
        }
        int[] array2;
        if ((array2 = array) != null) {
            int n3 = 0;
            for (int n4 = 0; n4 < array.length; ++n4) {
                if (this.valueManagers[array[n4]].isInheritedProperty()) {
                    ++n3;
                }
                else {
                    array[n4] = -1;
                }
            }
            if (n3 == 0) {
                array2 = null;
            }
            else {
                array2 = new int[n3];
                int n5 = 0;
                for (int n6 = 0; n6 < array.length; ++n6) {
                    if (array[n6] != -1) {
                        array2[n5++] = array[n6];
                    }
                }
            }
        }
        for (Node node2 = getCSSFirstChild(node); node2 != null; node2 = getCSSNextSibling(node2)) {
            if (node2.getNodeType() == 1) {
                this.invalidateProperties(node2, array2, null, b);
            }
        }
    }
    
    protected void nonCSSPresentationalHintUpdated(final CSSStylableElement element, final StyleMap styleMap, final String s, final short n, final String s2) {
        final int propertyIndex = this.getPropertyIndex(s);
        if (styleMap.isImportant(propertyIndex)) {
            return;
        }
        if (styleMap.getOrigin(propertyIndex) >= 24576) {
            return;
        }
        switch (n) {
            case 1:
            case 2: {
                this.element = element;
                try {
                    final Value value = this.valueManagers[propertyIndex].createValue(this.parser.parsePropertyValue(s2), this);
                    styleMap.putMask(propertyIndex, (short)0);
                    styleMap.putValue(propertyIndex, value);
                    styleMap.putOrigin(propertyIndex, (short)16384);
                    break;
                }
                catch (Exception ex) {
                    String message = ex.getMessage();
                    if (message == null) {
                        message = "";
                    }
                    final DOMException ex2 = new DOMException((short)12, Messages.formatMessage("property.syntax.error.at", new Object[] { (this.documentURI == null) ? "<unknown>" : this.documentURI.toString(), s, s2, message }));
                    if (this.userAgent == null) {
                        throw ex2;
                    }
                    this.userAgent.displayError(ex2);
                    break;
                }
                finally {
                    this.element = null;
                    this.cssBaseURI = null;
                }
            }
            case 3: {
                this.invalidateProperties(element, new int[] { propertyIndex }, null, true);
                return;
            }
        }
        final boolean[] updatedProperties = this.styleDeclarationUpdateHandler.updatedProperties;
        for (int i = this.getNumberOfProperties() - 1; i >= 0; --i) {
            updatedProperties[i] = false;
        }
        updatedProperties[propertyIndex] = true;
        final boolean b = propertyIndex == this.fontSizeIndex;
        final boolean b2 = propertyIndex == this.lineHeightIndex;
        final boolean b3 = propertyIndex == this.colorIndex;
        int n2 = 0;
        for (int j = this.getNumberOfProperties() - 1; j >= 0; --j) {
            if (updatedProperties[j]) {
                ++n2;
            }
            else if ((b && styleMap.isFontSizeRelative(j)) || (b2 && styleMap.isLineHeightRelative(j)) || (b3 && styleMap.isColorRelative(j))) {
                updatedProperties[j] = true;
                clearComputedValue(styleMap, j);
                ++n2;
            }
        }
        final int[] array = new int[n2];
        int n3 = 0;
        for (int k = this.getNumberOfProperties() - 1; k >= 0; --k) {
            if (updatedProperties[k]) {
                array[n3++] = k;
            }
        }
        this.invalidateProperties(element, array, null, true);
    }
    
    protected boolean hasStyleSheetNode(Node node) {
        if (node instanceof CSSStyleSheetNode) {
            return true;
        }
        for (node = getCSSFirstChild(node); node != null; node = getCSSNextSibling(node)) {
            if (this.hasStyleSheetNode(node)) {
                return true;
            }
        }
        return false;
    }
    
    protected void handleAttrModified(final Element element, final Attr attr, final short n, final String anObject, final String s) {
        if (!(element instanceof CSSStylableElement)) {
            return;
        }
        if (s.equals(anObject)) {
            return;
        }
        final String namespaceURI = attr.getNamespaceURI();
        final String s2 = (namespaceURI == null) ? attr.getNodeName() : attr.getLocalName();
        final CSSStylableElement cssStylableElement = (CSSStylableElement)element;
        final StyleMap computedStyleMap = cssStylableElement.getComputedStyleMap(null);
        if (computedStyleMap != null) {
            if ((namespaceURI == this.styleNamespaceURI || (namespaceURI != null && namespaceURI.equals(this.styleNamespaceURI))) && s2.equals(this.styleLocalName)) {
                this.inlineStyleAttributeUpdated(cssStylableElement, computedStyleMap, n, anObject, s);
                return;
            }
            if (this.nonCSSPresentationalHints != null && (namespaceURI == this.nonCSSPresentationalHintsNamespaceURI || (namespaceURI != null && namespaceURI.equals(this.nonCSSPresentationalHintsNamespaceURI))) && this.nonCSSPresentationalHints.contains(s2)) {
                this.nonCSSPresentationalHintUpdated(cssStylableElement, computedStyleMap, s2, n, s);
                return;
            }
        }
        if (this.selectorAttributes != null && this.selectorAttributes.contains(s2)) {
            this.invalidateProperties(cssStylableElement, null, null, true);
            for (Node node = getCSSNextSibling(cssStylableElement); node != null; node = getCSSNextSibling(node)) {
                this.invalidateProperties(node, null, null, true);
            }
        }
    }
    
    protected void handleNodeInserted(Node node) {
        if (this.hasStyleSheetNode(node)) {
            this.styleSheetNodes = null;
            this.invalidateProperties(this.document.getDocumentElement(), null, null, true);
        }
        else if (node instanceof CSSStylableElement) {
            for (node = getCSSNextSibling(node); node != null; node = getCSSNextSibling(node)) {
                this.invalidateProperties(node, null, null, true);
            }
        }
    }
    
    protected void handleNodeRemoved(final Node node) {
        if (this.hasStyleSheetNode(node)) {
            this.styleSheetRemoved = true;
        }
        else if (node instanceof CSSStylableElement) {
            this.removedStylableElementSibling = getCSSNextSibling(node);
        }
        this.disposeStyleMaps(node);
    }
    
    protected void handleSubtreeModified(final Node node) {
        if (this.styleSheetRemoved) {
            this.styleSheetRemoved = false;
            this.styleSheetNodes = null;
            this.invalidateProperties(this.document.getDocumentElement(), null, null, true);
        }
        else if (this.removedStylableElementSibling != null) {
            for (Node node2 = this.removedStylableElementSibling; node2 != null; node2 = getCSSNextSibling(node2)) {
                this.invalidateProperties(node2, null, null, true);
            }
            this.removedStylableElementSibling = null;
        }
    }
    
    protected void handleCharacterDataModified(final Node node) {
        if (getCSSParentNode(node) instanceof CSSStyleSheetNode) {
            this.styleSheetNodes = null;
            this.invalidateProperties(this.document.getDocumentElement(), null, null, true);
        }
    }
    
    static {
        LISTENER_ARRAY = new CSSEngineListener[0];
    }
    
    protected class DOMAttrModifiedListener implements EventListener
    {
        public void handleEvent(final Event event) {
            final MutationEvent mutationEvent = (MutationEvent)event;
            CSSEngine.this.handleAttrModified((Element)event.getTarget(), (Attr)mutationEvent.getRelatedNode(), mutationEvent.getAttrChange(), mutationEvent.getPrevValue(), mutationEvent.getNewValue());
        }
    }
    
    protected class DOMCharacterDataModifiedListener implements EventListener
    {
        public void handleEvent(final Event event) {
            CSSEngine.this.handleCharacterDataModified((Node)event.getTarget());
        }
    }
    
    protected class DOMSubtreeModifiedListener implements EventListener
    {
        public void handleEvent(final Event event) {
            CSSEngine.this.handleSubtreeModified((Node)event.getTarget());
        }
    }
    
    protected class DOMNodeRemovedListener implements EventListener
    {
        public void handleEvent(final Event event) {
            CSSEngine.this.handleNodeRemoved((Node)event.getTarget());
        }
    }
    
    protected class DOMNodeInsertedListener implements EventListener
    {
        public void handleEvent(final Event event) {
            CSSEngine.this.handleNodeInserted((Node)event.getTarget());
        }
    }
    
    protected class CSSNavigableDocumentHandler implements CSSNavigableDocumentListener, MainPropertyReceiver
    {
        protected boolean[] mainPropertiesChanged;
        protected StyleDeclaration declaration;
        
        public void nodeInserted(final Node node) {
            CSSEngine.this.handleNodeInserted(node);
        }
        
        public void nodeToBeRemoved(final Node node) {
            CSSEngine.this.handleNodeRemoved(node);
        }
        
        public void subtreeModified(final Node node) {
            CSSEngine.this.handleSubtreeModified(node);
        }
        
        public void characterDataModified(final Node node) {
            CSSEngine.this.handleCharacterDataModified(node);
        }
        
        public void attrModified(final Element element, final Attr attr, final short n, final String s, final String s2) {
            CSSEngine.this.handleAttrModified(element, attr, n, s, s2);
        }
        
        public void overrideStyleTextChanged(final CSSStylableElement cssStylableElement, final String s) {
            final StyleDeclarationProvider overrideStyleDeclarationProvider = cssStylableElement.getOverrideStyleDeclarationProvider();
            final StyleDeclaration styleDeclaration = overrideStyleDeclarationProvider.getStyleDeclaration();
            final int size = styleDeclaration.size();
            final boolean[] array = new boolean[CSSEngine.this.getNumberOfProperties()];
            for (int i = 0; i < size; ++i) {
                array[styleDeclaration.getIndex(i)] = true;
            }
            final StyleDeclaration styleDeclaration2 = CSSEngine.this.parseStyleDeclaration(cssStylableElement, s);
            overrideStyleDeclarationProvider.setStyleDeclaration(styleDeclaration2);
            for (int size2 = styleDeclaration2.size(), j = 0; j < size2; ++j) {
                array[styleDeclaration2.getIndex(j)] = true;
            }
            CSSEngine.this.invalidateProperties(cssStylableElement, null, array, true);
        }
        
        public void overrideStylePropertyRemoved(final CSSStylableElement cssStylableElement, final String s) {
            final StyleDeclaration styleDeclaration = cssStylableElement.getOverrideStyleDeclarationProvider().getStyleDeclaration();
            final int propertyIndex = CSSEngine.this.getPropertyIndex(s);
            final int size = styleDeclaration.size();
            int i = 0;
            while (i < size) {
                if (propertyIndex == styleDeclaration.getIndex(i)) {
                    styleDeclaration.remove(i);
                    final StyleMap computedStyleMap = cssStylableElement.getComputedStyleMap(null);
                    if (computedStyleMap != null && computedStyleMap.getOrigin(propertyIndex) == -24576) {
                        CSSEngine.this.invalidateProperties(cssStylableElement, new int[] { propertyIndex }, null, true);
                        break;
                    }
                    break;
                }
                else {
                    ++i;
                }
            }
        }
        
        public void overrideStylePropertyChanged(final CSSStylableElement cssStylableElement, final String s, final String s2, final String s3) {
            final boolean b = s3 != null && s3.length() != 0;
            this.declaration = cssStylableElement.getOverrideStyleDeclarationProvider().getStyleDeclaration();
            CSSEngine.this.setMainProperties(cssStylableElement, this, s, s2, b);
            this.declaration = null;
            CSSEngine.this.invalidateProperties(cssStylableElement, null, this.mainPropertiesChanged, true);
        }
        
        public void setMainProperty(final String s, final Value value, final boolean b) {
            final int propertyIndex = CSSEngine.this.getPropertyIndex(s);
            if (propertyIndex == -1) {
                return;
            }
            int n;
            for (n = 0; n < this.declaration.size() && propertyIndex != this.declaration.getIndex(n); ++n) {}
            if (n < this.declaration.size()) {
                this.declaration.put(n, value, propertyIndex, b);
            }
            else {
                this.declaration.append(value, propertyIndex, b);
            }
        }
    }
    
    public interface MainPropertyReceiver
    {
        void setMainProperty(final String p0, final Value p1, final boolean p2);
    }
    
    protected class StyleDeclarationUpdateHandler extends DocumentAdapter implements ShorthandManager.PropertyHandler
    {
        public StyleMap styleMap;
        public boolean[] updatedProperties;
        
        protected StyleDeclarationUpdateHandler() {
            this.updatedProperties = new boolean[CSSEngine.this.getNumberOfProperties()];
        }
        
        public void property(final String s, final LexicalUnit lexicalUnit, final boolean b) throws CSSException {
            final int propertyIndex = CSSEngine.this.getPropertyIndex(s);
            if (propertyIndex == -1) {
                final int shorthandIndex = CSSEngine.this.getShorthandIndex(s);
                if (shorthandIndex == -1) {
                    return;
                }
                CSSEngine.this.shorthandManagers[shorthandIndex].setValues(CSSEngine.this, this, lexicalUnit, b);
            }
            else {
                if (this.styleMap.isImportant(propertyIndex)) {
                    return;
                }
                this.updatedProperties[propertyIndex] = true;
                final Value value = CSSEngine.this.valueManagers[propertyIndex].createValue(lexicalUnit, CSSEngine.this);
                this.styleMap.putMask(propertyIndex, (short)0);
                this.styleMap.putValue(propertyIndex, value);
                this.styleMap.putOrigin(propertyIndex, (short)(-32768));
            }
        }
    }
    
    protected static class DocumentAdapter implements DocumentHandler
    {
        public void startDocument(final InputSource inputSource) {
            this.throwUnsupportedEx();
        }
        
        public void endDocument(final InputSource inputSource) {
            this.throwUnsupportedEx();
        }
        
        public void comment(final String s) {
        }
        
        public void ignorableAtRule(final String s) {
            this.throwUnsupportedEx();
        }
        
        public void namespaceDeclaration(final String s, final String s2) {
            this.throwUnsupportedEx();
        }
        
        public void importStyle(final String s, final SACMediaList list, final String s2) {
            this.throwUnsupportedEx();
        }
        
        public void startMedia(final SACMediaList list) {
            this.throwUnsupportedEx();
        }
        
        public void endMedia(final SACMediaList list) {
            this.throwUnsupportedEx();
        }
        
        public void startPage(final String s, final String s2) {
            this.throwUnsupportedEx();
        }
        
        public void endPage(final String s, final String s2) {
            this.throwUnsupportedEx();
        }
        
        public void startFontFace() {
            this.throwUnsupportedEx();
        }
        
        public void endFontFace() {
            this.throwUnsupportedEx();
        }
        
        public void startSelector(final SelectorList list) {
            this.throwUnsupportedEx();
        }
        
        public void endSelector(final SelectorList list) {
            this.throwUnsupportedEx();
        }
        
        public void property(final String s, final LexicalUnit lexicalUnit, final boolean b) {
            this.throwUnsupportedEx();
        }
        
        private void throwUnsupportedEx() {
            throw new UnsupportedOperationException("you try to use an empty method in Adapter-class");
        }
    }
    
    protected class StyleSheetDocumentHandler extends DocumentAdapter implements ShorthandManager.PropertyHandler
    {
        public StyleSheet styleSheet;
        protected StyleRule styleRule;
        protected StyleDeclaration styleDeclaration;
        
        public void startDocument(final InputSource inputSource) throws CSSException {
        }
        
        public void endDocument(final InputSource inputSource) throws CSSException {
        }
        
        public void ignorableAtRule(final String s) throws CSSException {
        }
        
        public void importStyle(final String s, final SACMediaList mediaList, final String s2) throws CSSException {
            final ImportRule importRule = new ImportRule();
            importRule.setMediaList(mediaList);
            importRule.setParent(this.styleSheet);
            final ParsedURL cssBaseURI = CSSEngine.this.getCSSBaseURI();
            ParsedURL uri;
            if (cssBaseURI == null) {
                uri = new ParsedURL(s);
            }
            else {
                uri = new ParsedURL(cssBaseURI, s);
            }
            importRule.setURI(uri);
            this.styleSheet.append(importRule);
        }
        
        public void startMedia(final SACMediaList mediaList) throws CSSException {
            final MediaRule styleSheet = new MediaRule();
            styleSheet.setMediaList(mediaList);
            styleSheet.setParent(this.styleSheet);
            this.styleSheet.append(styleSheet);
            this.styleSheet = styleSheet;
        }
        
        public void endMedia(final SACMediaList list) throws CSSException {
            this.styleSheet = this.styleSheet.getParent();
        }
        
        public void startPage(final String s, final String s2) throws CSSException {
        }
        
        public void endPage(final String s, final String s2) throws CSSException {
        }
        
        public void startFontFace() throws CSSException {
            this.styleDeclaration = new StyleDeclaration();
        }
        
        public void endFontFace() throws CSSException {
            final StyleMap styleMap = new StyleMap(CSSEngine.this.getNumberOfProperties());
            for (int size = this.styleDeclaration.size(), i = 0; i < size; ++i) {
                final int index = this.styleDeclaration.getIndex(i);
                styleMap.putValue(index, this.styleDeclaration.getValue(i));
                styleMap.putImportant(index, this.styleDeclaration.getPriority(i));
                styleMap.putOrigin(index, (short)24576);
            }
            this.styleDeclaration = null;
            if (styleMap.getValue(CSSEngine.this.getPropertyIndex("font-family")) == null) {
                return;
            }
            CSSEngine.this.fontFaces.add(new FontFaceRule(styleMap, CSSEngine.this.getCSSBaseURI()));
        }
        
        public void startSelector(final SelectorList selectorList) throws CSSException {
            (this.styleRule = new StyleRule()).setSelectorList(selectorList);
            this.styleDeclaration = new StyleDeclaration();
            this.styleRule.setStyleDeclaration(this.styleDeclaration);
            this.styleSheet.append(this.styleRule);
        }
        
        public void endSelector(final SelectorList list) throws CSSException {
            this.styleRule = null;
            this.styleDeclaration = null;
        }
        
        public void property(final String s, final LexicalUnit lexicalUnit, final boolean b) throws CSSException {
            final int propertyIndex = CSSEngine.this.getPropertyIndex(s);
            if (propertyIndex == -1) {
                final int shorthandIndex = CSSEngine.this.getShorthandIndex(s);
                if (shorthandIndex == -1) {
                    return;
                }
                CSSEngine.this.shorthandManagers[shorthandIndex].setValues(CSSEngine.this, this, lexicalUnit, b);
            }
            else {
                this.styleDeclaration.append(CSSEngine.this.valueManagers[propertyIndex].createValue(lexicalUnit, CSSEngine.this), propertyIndex, b);
            }
        }
    }
    
    protected class StyleDeclarationBuilder extends DocumentAdapter implements ShorthandManager.PropertyHandler
    {
        public StyleDeclaration styleDeclaration;
        
        public void property(final String s, final LexicalUnit lexicalUnit, final boolean b) throws CSSException {
            final int propertyIndex = CSSEngine.this.getPropertyIndex(s);
            if (propertyIndex == -1) {
                final int shorthandIndex = CSSEngine.this.getShorthandIndex(s);
                if (shorthandIndex == -1) {
                    return;
                }
                CSSEngine.this.shorthandManagers[shorthandIndex].setValues(CSSEngine.this, this, lexicalUnit, b);
            }
            else {
                this.styleDeclaration.append(CSSEngine.this.valueManagers[propertyIndex].createValue(lexicalUnit, CSSEngine.this), propertyIndex, b);
            }
        }
    }
    
    protected class StyleDeclarationDocumentHandler extends DocumentAdapter implements ShorthandManager.PropertyHandler
    {
        public StyleMap styleMap;
        
        public void property(final String s, final LexicalUnit lexicalUnit, final boolean b) throws CSSException {
            final int propertyIndex = CSSEngine.this.getPropertyIndex(s);
            if (propertyIndex == -1) {
                final int shorthandIndex = CSSEngine.this.getShorthandIndex(s);
                if (shorthandIndex == -1) {
                    return;
                }
                CSSEngine.this.shorthandManagers[shorthandIndex].setValues(CSSEngine.this, this, lexicalUnit, b);
            }
            else {
                CSSEngine.this.putAuthorProperty(this.styleMap, propertyIndex, CSSEngine.this.valueManagers[propertyIndex].createValue(lexicalUnit, CSSEngine.this), b, (short)(-32768));
            }
        }
    }
}
