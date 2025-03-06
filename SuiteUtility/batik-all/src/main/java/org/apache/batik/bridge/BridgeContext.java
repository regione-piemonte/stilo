// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.bridge;

import org.apache.batik.util.CleanerThread;
import org.w3c.dom.events.MouseEvent;
import java.awt.Cursor;
import org.w3c.dom.events.MutationEvent;
import org.apache.batik.dom.AbstractNode;
import org.w3c.dom.events.Event;
import org.apache.batik.gvt.CompositeGraphicsNode;
import org.apache.batik.css.engine.CSSEngineEvent;
import org.apache.batik.dom.svg.AnimatedLiveAttributeValue;
import org.apache.batik.util.Service;
import java.util.ListIterator;
import java.util.Collection;
import org.apache.batik.bridge.svg12.SVG12BridgeExtension;
import org.w3c.dom.svg.SVGSVGElement;
import org.apache.batik.css.engine.CSSStylableElement;
import org.apache.batik.dom.svg.SVGStylableElement;
import org.apache.batik.css.engine.SystemColorSupport;
import org.apache.batik.css.engine.value.Value;
import org.apache.batik.dom.svg.SVGOMElement;
import org.apache.batik.dom.svg.SVGContext;
import org.w3c.dom.events.EventTarget;
import org.apache.batik.dom.events.NodeEventTarget;
import org.apache.batik.gvt.GraphicsNode;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.MalformedURLException;
import org.w3c.dom.svg.SVGDocument;
import java.util.Iterator;
import org.apache.batik.script.Interpreter;
import java.lang.ref.SoftReference;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import org.apache.batik.css.engine.CSSEngine;
import org.apache.batik.util.ParsedURL;
import org.apache.batik.css.engine.CSSEngineUserAgent;
import org.apache.batik.dom.AbstractStylableDocument;
import org.apache.batik.dom.svg.SVGDOMImplementation;
import org.apache.batik.bridge.svg12.SVG12BridgeContext;
import java.lang.ref.WeakReference;
import org.apache.batik.dom.svg.SVGOMDocument;
import java.util.LinkedList;
import java.util.WeakHashMap;
import java.util.HashMap;
import org.apache.batik.dom.svg.AnimatedAttributeListener;
import org.apache.batik.css.engine.CSSEngineListener;
import org.w3c.dom.events.EventListener;
import java.util.HashSet;
import org.apache.batik.dom.xbl.XBLManager;
import org.apache.batik.gvt.TextPainter;
import java.awt.geom.Dimension2D;
import org.apache.batik.script.InterpreterPool;
import java.util.Set;
import java.util.List;
import java.util.Map;
import org.w3c.dom.Document;
import org.apache.batik.css.engine.CSSContext;

public class BridgeContext implements ErrorConstants, CSSContext
{
    protected Document document;
    protected boolean isSVG12;
    protected GVTBuilder gvtBuilder;
    protected Map interpreterMap;
    private Map fontFamilyMap;
    protected Map viewportMap;
    protected List viewportStack;
    protected UserAgent userAgent;
    protected Map elementNodeMap;
    protected Map nodeElementMap;
    protected Map namespaceURIMap;
    protected Bridge defaultBridge;
    protected Set reservedNamespaceSet;
    protected Map elementDataMap;
    protected InterpreterPool interpreterPool;
    protected DocumentLoader documentLoader;
    protected Dimension2D documentSize;
    protected TextPainter textPainter;
    public static final int STATIC = 0;
    public static final int INTERACTIVE = 1;
    public static final int DYNAMIC = 2;
    protected int dynamicStatus;
    protected UpdateManager updateManager;
    protected XBLManager xblManager;
    protected BridgeContext primaryContext;
    protected HashSet childContexts;
    protected SVGAnimationEngine animationEngine;
    protected int animationLimitingMode;
    protected float animationLimitingAmount;
    private static InterpreterPool sharedPool;
    protected Set eventListenerSet;
    protected EventListener domCharacterDataModifiedEventListener;
    protected EventListener domAttrModifiedEventListener;
    protected EventListener domNodeInsertedEventListener;
    protected EventListener domNodeRemovedEventListener;
    protected CSSEngineListener cssPropertiesChangedListener;
    protected AnimatedAttributeListener animatedAttributeListener;
    protected FocusManager focusManager;
    protected CursorManager cursorManager;
    protected List extensions;
    protected static List globalExtensions;
    
    protected BridgeContext() {
        this.interpreterMap = new HashMap(7);
        this.viewportMap = new WeakHashMap();
        this.viewportStack = new LinkedList();
        this.dynamicStatus = 0;
        this.childContexts = new HashSet();
        this.eventListenerSet = new HashSet();
        this.cursorManager = new CursorManager(this);
        this.extensions = null;
    }
    
    public BridgeContext(final UserAgent userAgent) {
        this(userAgent, BridgeContext.sharedPool, new DocumentLoader(userAgent));
    }
    
    public BridgeContext(final UserAgent userAgent, final DocumentLoader documentLoader) {
        this(userAgent, BridgeContext.sharedPool, documentLoader);
    }
    
    public BridgeContext(final UserAgent userAgent, final InterpreterPool interpreterPool, final DocumentLoader documentLoader) {
        this.interpreterMap = new HashMap(7);
        this.viewportMap = new WeakHashMap();
        this.viewportStack = new LinkedList();
        this.dynamicStatus = 0;
        this.childContexts = new HashSet();
        this.eventListenerSet = new HashSet();
        this.cursorManager = new CursorManager(this);
        this.extensions = null;
        this.userAgent = userAgent;
        this.viewportMap.put(userAgent, new UserAgentViewport(userAgent));
        this.interpreterPool = interpreterPool;
        this.documentLoader = documentLoader;
    }
    
    protected void finalize() {
        if (this.primaryContext != null) {
            this.dispose();
        }
    }
    
    public BridgeContext createSubBridgeContext(final SVGOMDocument document) {
        if (document.getCSSEngine() != null) {
            return (BridgeContext)document.getCSSEngine().getCSSContext();
        }
        final BridgeContext bridgeContext = this.createBridgeContext(document);
        bridgeContext.primaryContext = ((this.primaryContext != null) ? this.primaryContext : this);
        bridgeContext.primaryContext.childContexts.add(new WeakReference<BridgeContext>(bridgeContext));
        bridgeContext.dynamicStatus = this.dynamicStatus;
        bridgeContext.setGVTBuilder(this.getGVTBuilder());
        bridgeContext.setTextPainter(this.getTextPainter());
        bridgeContext.setDocument(document);
        bridgeContext.initializeDocument(document);
        if (this.isInteractive()) {
            bridgeContext.addUIEventListeners(document);
        }
        return bridgeContext;
    }
    
    public BridgeContext createBridgeContext(final SVGOMDocument svgomDocument) {
        if (svgomDocument.isSVG12()) {
            return new SVG12BridgeContext(this.getUserAgent(), this.getDocumentLoader());
        }
        return new BridgeContext(this.getUserAgent(), this.getDocumentLoader());
    }
    
    protected void initializeDocument(final Document document) {
        final SVGOMDocument svgomDocument = (SVGOMDocument)document;
        if (svgomDocument.getCSSEngine() == null) {
            final CSSEngine cssEngine = ((SVGDOMImplementation)svgomDocument.getImplementation()).createCSSEngine(svgomDocument, this);
            cssEngine.setCSSEngineUserAgent(new CSSEngineUserAgentWrapper(this.userAgent));
            svgomDocument.setCSSEngine(cssEngine);
            cssEngine.setMedia(this.userAgent.getMedia());
            final String userStyleSheetURI = this.userAgent.getUserStyleSheetURI();
            if (userStyleSheetURI != null) {
                try {
                    cssEngine.setUserAgentStyleSheet(cssEngine.parseStyleSheet(new ParsedURL(userStyleSheetURI), "all"));
                }
                catch (Exception ex) {
                    this.userAgent.displayError(ex);
                }
            }
            cssEngine.setAlternateStyleSheet(this.userAgent.getAlternateStyleSheet());
        }
    }
    
    public CSSEngine getCSSEngineForElement(final Element element) {
        return ((SVGOMDocument)element.getOwnerDocument()).getCSSEngine();
    }
    
    public void setTextPainter(final TextPainter textPainter) {
        this.textPainter = textPainter;
    }
    
    public TextPainter getTextPainter() {
        return this.textPainter;
    }
    
    public Document getDocument() {
        return this.document;
    }
    
    protected void setDocument(final Document document) {
        if (this.document != document) {
            this.fontFamilyMap = null;
        }
        this.document = document;
        this.isSVG12 = ((SVGOMDocument)document).isSVG12();
        this.registerSVGBridges();
    }
    
    public Map getFontFamilyMap() {
        if (this.fontFamilyMap == null) {
            this.fontFamilyMap = new HashMap();
        }
        return this.fontFamilyMap;
    }
    
    protected void setFontFamilyMap(final Map fontFamilyMap) {
        this.fontFamilyMap = fontFamilyMap;
    }
    
    public void setElementData(final Node node, final Object referent) {
        if (this.elementDataMap == null) {
            this.elementDataMap = new WeakHashMap();
        }
        this.elementDataMap.put(node, new SoftReference<Object>(referent));
    }
    
    public Object getElementData(final Node node) {
        if (this.elementDataMap == null) {
            return null;
        }
        final SoftReference<Object> value = this.elementDataMap.get(node);
        if (value == null) {
            return null;
        }
        final Object value2 = value.get();
        if (value2 == null) {
            this.elementDataMap.remove(node);
        }
        return value2;
    }
    
    public UserAgent getUserAgent() {
        return this.userAgent;
    }
    
    protected void setUserAgent(final UserAgent userAgent) {
        this.userAgent = userAgent;
    }
    
    public GVTBuilder getGVTBuilder() {
        return this.gvtBuilder;
    }
    
    protected void setGVTBuilder(final GVTBuilder gvtBuilder) {
        this.gvtBuilder = gvtBuilder;
    }
    
    public InterpreterPool getInterpreterPool() {
        return this.interpreterPool;
    }
    
    public FocusManager getFocusManager() {
        return this.focusManager;
    }
    
    public CursorManager getCursorManager() {
        return this.cursorManager;
    }
    
    protected void setInterpreterPool(final InterpreterPool interpreterPool) {
        this.interpreterPool = interpreterPool;
    }
    
    public Interpreter getInterpreter(final String str) {
        if (this.document == null) {
            throw new RuntimeException("Unknown document");
        }
        Interpreter interpreter = this.interpreterMap.get(str);
        if (interpreter == null) {
            try {
                interpreter = this.interpreterPool.createInterpreter(this.document, str);
                this.interpreterMap.put(str, interpreter);
            }
            catch (Exception ex) {
                if (this.userAgent != null) {
                    this.userAgent.displayError(ex);
                    return null;
                }
            }
        }
        if (interpreter == null && this.userAgent != null) {
            this.userAgent.displayError(new Exception("Unknown language: " + str));
        }
        return interpreter;
    }
    
    public DocumentLoader getDocumentLoader() {
        return this.documentLoader;
    }
    
    protected void setDocumentLoader(final DocumentLoader documentLoader) {
        this.documentLoader = documentLoader;
    }
    
    public Dimension2D getDocumentSize() {
        return this.documentSize;
    }
    
    protected void setDocumentSize(final Dimension2D documentSize) {
        this.documentSize = documentSize;
    }
    
    public boolean isDynamic() {
        return this.dynamicStatus == 2;
    }
    
    public boolean isInteractive() {
        return this.dynamicStatus != 0;
    }
    
    public void setDynamicState(final int dynamicStatus) {
        this.dynamicStatus = dynamicStatus;
    }
    
    public void setDynamic(final boolean b) {
        if (b) {
            this.setDynamicState(2);
        }
        else {
            this.setDynamicState(0);
        }
    }
    
    public void setInteractive(final boolean b) {
        if (b) {
            this.setDynamicState(1);
        }
        else {
            this.setDynamicState(0);
        }
    }
    
    public UpdateManager getUpdateManager() {
        return this.updateManager;
    }
    
    protected void setUpdateManager(final UpdateManager updateManager) {
        this.updateManager = updateManager;
    }
    
    protected void setUpdateManager(final BridgeContext bridgeContext, final UpdateManager updateManager) {
        bridgeContext.setUpdateManager(updateManager);
    }
    
    protected void setXBLManager(final BridgeContext bridgeContext, final XBLManager xblManager) {
        bridgeContext.xblManager = xblManager;
    }
    
    public boolean isSVG12() {
        return this.isSVG12;
    }
    
    public BridgeContext getPrimaryBridgeContext() {
        if (this.primaryContext != null) {
            return this.primaryContext;
        }
        return this;
    }
    
    public BridgeContext[] getChildContexts() {
        final BridgeContext[] array = new BridgeContext[this.childContexts.size()];
        final Iterator<WeakReference<BridgeContext>> iterator = this.childContexts.iterator();
        for (int i = 0; i < array.length; ++i) {
            array[i] = iterator.next().get();
        }
        return array;
    }
    
    public SVGAnimationEngine getAnimationEngine() {
        if (this.animationEngine == null) {
            this.animationEngine = new SVGAnimationEngine(this.document, this);
            this.setAnimationLimitingMode();
        }
        return this.animationEngine;
    }
    
    public URIResolver createURIResolver(final SVGDocument svgDocument, final DocumentLoader documentLoader) {
        return new URIResolver(svgDocument, documentLoader);
    }
    
    public Node getReferencedNode(final Element element, final String s) {
        try {
            final SVGDocument svgDocument = (SVGDocument)element.getOwnerDocument();
            final Node node = this.createURIResolver(svgDocument, this.documentLoader).getNode(s, element);
            if (node == null) {
                throw new BridgeException(this, element, "uri.badTarget", new Object[] { s });
            }
            final SVGOMDocument svgomDocument = (SVGOMDocument)((node.getNodeType() == 9) ? node : node.getOwnerDocument());
            if (svgomDocument != svgDocument) {
                this.createSubBridgeContext(svgomDocument);
            }
            return node;
        }
        catch (MalformedURLException ex) {
            throw new BridgeException(this, element, ex, "uri.malformed", new Object[] { s });
        }
        catch (InterruptedIOException ex4) {
            throw new InterruptedBridgeException();
        }
        catch (IOException ex2) {
            throw new BridgeException(this, element, ex2, "uri.io", new Object[] { s });
        }
        catch (SecurityException ex3) {
            throw new BridgeException(this, element, ex3, "uri.unsecure", new Object[] { s });
        }
    }
    
    public Element getReferencedElement(final Element element, final String s) {
        final Node referencedNode = this.getReferencedNode(element, s);
        if (referencedNode != null && referencedNode.getNodeType() != 1) {
            throw new BridgeException(this, element, "uri.referenceDocument", new Object[] { s });
        }
        return (Element)referencedNode;
    }
    
    public Viewport getViewport(Element element) {
        if (this.viewportStack == null) {
            Viewport viewport;
            for (element = SVGUtilities.getParentElement(element); element != null; element = SVGUtilities.getParentElement(element)) {
                viewport = this.viewportMap.get(element);
                if (viewport != null) {
                    return viewport;
                }
            }
            return this.viewportMap.get(this.userAgent);
        }
        if (this.viewportStack.size() == 0) {
            return this.viewportMap.get(this.userAgent);
        }
        return this.viewportStack.get(0);
    }
    
    public void openViewport(final Element element, final Viewport viewport) {
        this.viewportMap.put(element, viewport);
        if (this.viewportStack == null) {
            this.viewportStack = new LinkedList();
        }
        this.viewportStack.add(0, viewport);
    }
    
    public void removeViewport(final Element element) {
        this.viewportMap.remove(element);
    }
    
    public void closeViewport(final Element element) {
        this.viewportStack.remove(0);
        if (this.viewportStack.size() == 0) {
            this.viewportStack = null;
        }
    }
    
    public void bind(final Node referent, final GraphicsNode referent2) {
        if (this.elementNodeMap == null) {
            this.elementNodeMap = new WeakHashMap();
            this.nodeElementMap = new WeakHashMap();
        }
        this.elementNodeMap.put(referent, new SoftReference<GraphicsNode>(referent2));
        this.nodeElementMap.put(referent2, new SoftReference<Node>(referent));
    }
    
    public void unbind(final Node node) {
        if (this.elementNodeMap == null) {
            return;
        }
        Object o = null;
        final SoftReference<GraphicsNode> softReference = this.elementNodeMap.get(node);
        if (softReference != null) {
            o = softReference.get();
        }
        this.elementNodeMap.remove(node);
        if (o != null) {
            this.nodeElementMap.remove(o);
        }
    }
    
    public GraphicsNode getGraphicsNode(final Node node) {
        if (this.elementNodeMap != null) {
            final SoftReference<GraphicsNode> softReference = this.elementNodeMap.get(node);
            if (softReference != null) {
                return softReference.get();
            }
        }
        return null;
    }
    
    public Element getElement(final GraphicsNode graphicsNode) {
        if (this.nodeElementMap != null) {
            final SoftReference<Node> softReference = this.nodeElementMap.get(graphicsNode);
            if (softReference != null) {
                final Node node = softReference.get();
                if (node.getNodeType() == 1) {
                    return (Element)node;
                }
            }
        }
        return null;
    }
    
    public boolean hasGraphicsNodeBridge(final Element element) {
        if (this.namespaceURIMap == null || element == null) {
            return false;
        }
        final String localName = element.getLocalName();
        final String namespaceURI = element.getNamespaceURI();
        final HashMap hashMap = this.namespaceURIMap.get((namespaceURI == null) ? "" : namespaceURI);
        return hashMap != null && hashMap.get(localName) instanceof GraphicsNodeBridge;
    }
    
    public DocumentBridge getDocumentBridge() {
        return new SVGDocumentBridge();
    }
    
    public Bridge getBridge(final Element element) {
        if (this.namespaceURIMap == null || element == null) {
            return null;
        }
        final String localName = element.getLocalName();
        final String namespaceURI = element.getNamespaceURI();
        return this.getBridge((namespaceURI == null) ? "" : namespaceURI, localName);
    }
    
    public Bridge getBridge(final String s, final String key) {
        Bridge defaultBridge = null;
        if (this.namespaceURIMap != null) {
            final HashMap<K, Bridge> hashMap = this.namespaceURIMap.get(s);
            if (hashMap != null) {
                defaultBridge = hashMap.get(key);
            }
        }
        if (defaultBridge == null && (this.reservedNamespaceSet == null || !this.reservedNamespaceSet.contains(s))) {
            defaultBridge = this.defaultBridge;
        }
        if (this.isDynamic()) {
            return (defaultBridge == null) ? null : defaultBridge.getInstance();
        }
        return defaultBridge;
    }
    
    public void putBridge(String str, final String s, final Bridge value) {
        if (!str.equals(value.getNamespaceURI()) || !s.equals(value.getLocalName())) {
            throw new Error("Invalid Bridge: " + str + "/" + value.getNamespaceURI() + " " + s + "/" + value.getLocalName() + " " + value.getClass());
        }
        if (this.namespaceURIMap == null) {
            this.namespaceURIMap = new HashMap();
        }
        str = ((str == null) ? "" : str);
        HashMap<String, Bridge> hashMap = this.namespaceURIMap.get(str);
        if (hashMap == null) {
            hashMap = new HashMap<String, Bridge>();
            this.namespaceURIMap.put(str, hashMap);
        }
        hashMap.put(s, value);
    }
    
    public void putBridge(final Bridge bridge) {
        this.putBridge(bridge.getNamespaceURI(), bridge.getLocalName(), bridge);
    }
    
    public void removeBridge(String s, final String key) {
        if (this.namespaceURIMap == null) {
            return;
        }
        s = ((s == null) ? "" : s);
        final HashMap hashMap = this.namespaceURIMap.get(s);
        if (hashMap != null) {
            hashMap.remove(key);
            if (hashMap.isEmpty()) {
                this.namespaceURIMap.remove(s);
                if (this.namespaceURIMap.isEmpty()) {
                    this.namespaceURIMap = null;
                }
            }
        }
    }
    
    public void setDefaultBridge(final Bridge defaultBridge) {
        this.defaultBridge = defaultBridge;
    }
    
    public void putReservedNamespaceURI(String s) {
        if (s == null) {
            s = "";
        }
        if (this.reservedNamespaceSet == null) {
            this.reservedNamespaceSet = new HashSet();
        }
        this.reservedNamespaceSet.add(s);
    }
    
    public void removeReservedNamespaceURI(String s) {
        if (s == null) {
            s = "";
        }
        if (this.reservedNamespaceSet != null) {
            this.reservedNamespaceSet.remove(s);
            if (this.reservedNamespaceSet.isEmpty()) {
                this.reservedNamespaceSet = null;
            }
        }
    }
    
    public void addUIEventListeners(final Document document) {
        final NodeEventTarget nodeEventTarget = (NodeEventTarget)document.getDocumentElement();
        final DOMMouseOverEventListener domMouseOverEventListener = new DOMMouseOverEventListener();
        nodeEventTarget.addEventListenerNS("http://www.w3.org/2001/xml-events", "mouseover", domMouseOverEventListener, true, null);
        this.storeEventListenerNS(nodeEventTarget, "http://www.w3.org/2001/xml-events", "mouseover", domMouseOverEventListener, true);
        final DOMMouseOutEventListener domMouseOutEventListener = new DOMMouseOutEventListener();
        nodeEventTarget.addEventListenerNS("http://www.w3.org/2001/xml-events", "mouseout", domMouseOutEventListener, true, null);
        this.storeEventListenerNS(nodeEventTarget, "http://www.w3.org/2001/xml-events", "mouseout", domMouseOutEventListener, true);
    }
    
    public void removeUIEventListeners(final Document document) {
        final EventTarget eventTarget = (EventTarget)document.getDocumentElement();
        synchronized (this.eventListenerSet) {
            for (final EventListenerMememto eventListenerMememto : this.eventListenerSet) {
                final NodeEventTarget target = eventListenerMememto.getTarget();
                if (target == eventTarget) {
                    final EventListener listener = eventListenerMememto.getListener();
                    final boolean useCapture = eventListenerMememto.getUseCapture();
                    final String eventType = eventListenerMememto.getEventType();
                    final boolean namespaced = eventListenerMememto.getNamespaced();
                    if (target == null || listener == null) {
                        continue;
                    }
                    if (eventType == null) {
                        continue;
                    }
                    if (namespaced) {
                        target.removeEventListenerNS(eventListenerMememto.getNamespaceURI(), eventType, listener, useCapture);
                    }
                    else {
                        target.removeEventListener(eventType, listener, useCapture);
                    }
                }
            }
        }
    }
    
    public void addDOMListeners() {
        final SVGOMDocument svgomDocument = (SVGOMDocument)this.document;
        svgomDocument.addEventListenerNS("http://www.w3.org/2001/xml-events", "DOMAttrModified", this.domAttrModifiedEventListener = new DOMAttrModifiedEventListener(), true, null);
        svgomDocument.addEventListenerNS("http://www.w3.org/2001/xml-events", "DOMNodeInserted", this.domNodeInsertedEventListener = new DOMNodeInsertedEventListener(), true, null);
        svgomDocument.addEventListenerNS("http://www.w3.org/2001/xml-events", "DOMNodeRemoved", this.domNodeRemovedEventListener = new DOMNodeRemovedEventListener(), true, null);
        svgomDocument.addEventListenerNS("http://www.w3.org/2001/xml-events", "DOMCharacterDataModified", this.domCharacterDataModifiedEventListener = new DOMCharacterDataModifiedEventListener(), true, null);
        svgomDocument.addAnimatedAttributeListener(this.animatedAttributeListener = new AnimatedAttrListener());
        this.focusManager = new FocusManager(this.document);
        svgomDocument.getCSSEngine().addCSSEngineListener(this.cssPropertiesChangedListener = new CSSPropertiesChangedListener());
    }
    
    protected void removeDOMListeners() {
        final SVGOMDocument svgomDocument = (SVGOMDocument)this.document;
        svgomDocument.removeEventListenerNS("http://www.w3.org/2001/xml-events", "DOMAttrModified", this.domAttrModifiedEventListener, true);
        svgomDocument.removeEventListenerNS("http://www.w3.org/2001/xml-events", "DOMNodeInserted", this.domNodeInsertedEventListener, true);
        svgomDocument.removeEventListenerNS("http://www.w3.org/2001/xml-events", "DOMNodeRemoved", this.domNodeRemovedEventListener, true);
        svgomDocument.removeEventListenerNS("http://www.w3.org/2001/xml-events", "DOMCharacterDataModified", this.domCharacterDataModifiedEventListener, true);
        svgomDocument.removeAnimatedAttributeListener(this.animatedAttributeListener);
        final CSSEngine cssEngine = svgomDocument.getCSSEngine();
        if (cssEngine != null) {
            cssEngine.removeCSSEngineListener(this.cssPropertiesChangedListener);
            cssEngine.dispose();
            svgomDocument.setCSSEngine(null);
        }
    }
    
    protected void storeEventListener(final EventTarget eventTarget, final String s, final EventListener eventListener, final boolean b) {
        synchronized (this.eventListenerSet) {
            this.eventListenerSet.add(new EventListenerMememto(eventTarget, s, eventListener, b, this));
        }
    }
    
    protected void storeEventListenerNS(final EventTarget eventTarget, final String s, final String s2, final EventListener eventListener, final boolean b) {
        synchronized (this.eventListenerSet) {
            this.eventListenerSet.add(new EventListenerMememto(eventTarget, s, s2, eventListener, b, this));
        }
    }
    
    public void addGVTListener(final Document document) {
        BridgeEventSupport.addGVTListener(this, document);
    }
    
    protected void clearChildContexts() {
        this.childContexts.clear();
    }
    
    public void dispose() {
        this.clearChildContexts();
        synchronized (this.eventListenerSet) {
            for (final EventListenerMememto eventListenerMememto : this.eventListenerSet) {
                final NodeEventTarget target = eventListenerMememto.getTarget();
                final EventListener listener = eventListenerMememto.getListener();
                final boolean useCapture = eventListenerMememto.getUseCapture();
                final String eventType = eventListenerMememto.getEventType();
                final boolean namespaced = eventListenerMememto.getNamespaced();
                if (target != null && listener != null) {
                    if (eventType == null) {
                        continue;
                    }
                    if (namespaced) {
                        target.removeEventListenerNS(eventListenerMememto.getNamespaceURI(), eventType, listener, useCapture);
                    }
                    else {
                        target.removeEventListener(eventType, listener, useCapture);
                    }
                }
            }
        }
        if (this.document != null) {
            this.removeDOMListeners();
        }
        if (this.animationEngine != null) {
            this.animationEngine.dispose();
            this.animationEngine = null;
        }
        for (final Interpreter interpreter : this.interpreterMap.values()) {
            if (interpreter != null) {
                interpreter.dispose();
            }
        }
        this.interpreterMap.clear();
        if (this.focusManager != null) {
            this.focusManager.dispose();
        }
        if (this.elementDataMap != null) {
            this.elementDataMap.clear();
        }
        if (this.nodeElementMap != null) {
            this.nodeElementMap.clear();
        }
        if (this.elementNodeMap != null) {
            this.elementNodeMap.clear();
        }
    }
    
    protected static SVGContext getSVGContext(final Node node) {
        if (node instanceof SVGOMElement) {
            return ((SVGOMElement)node).getSVGContext();
        }
        if (node instanceof SVGOMDocument) {
            return ((SVGOMDocument)node).getSVGContext();
        }
        return null;
    }
    
    protected static BridgeUpdateHandler getBridgeUpdateHandler(final Node node) {
        final SVGContext svgContext = getSVGContext(node);
        return (svgContext == null) ? null : ((BridgeUpdateHandler)svgContext);
    }
    
    public Value getSystemColor(final String s) {
        return SystemColorSupport.getSystemColor(s);
    }
    
    public Value getDefaultFontFamily() {
        final SVGOMDocument svgomDocument = (SVGOMDocument)this.document;
        return svgomDocument.getCSSEngine().parsePropertyValue((CSSStylableElement)svgomDocument.getRootElement(), "font-family", this.userAgent.getDefaultFontFamily());
    }
    
    public float getLighterFontWeight(final float n) {
        return this.userAgent.getLighterFontWeight(n);
    }
    
    public float getBolderFontWeight(final float n) {
        return this.userAgent.getBolderFontWeight(n);
    }
    
    public float getPixelUnitToMillimeter() {
        return this.userAgent.getPixelUnitToMillimeter();
    }
    
    public float getPixelToMillimeter() {
        return this.getPixelUnitToMillimeter();
    }
    
    public float getMediumFontSize() {
        return this.userAgent.getMediumFontSize();
    }
    
    public float getBlockWidth(final Element element) {
        return this.getViewport(element).getWidth();
    }
    
    public float getBlockHeight(final Element element) {
        return this.getViewport(element).getHeight();
    }
    
    public void checkLoadExternalResource(final ParsedURL parsedURL, final ParsedURL parsedURL2) throws SecurityException {
        this.userAgent.checkLoadExternalResource(parsedURL, parsedURL2);
    }
    
    public boolean isDynamicDocument(final Document document) {
        return BaseScriptingEnvironment.isDynamicDocument(this, document);
    }
    
    public boolean isInteractiveDocument(final Document document) {
        final SVGSVGElement rootElement = ((SVGDocument)document).getRootElement();
        return "http://www.w3.org/2000/svg".equals(((Node)rootElement).getNamespaceURI()) && this.checkInteractiveElement((Element)rootElement);
    }
    
    public boolean checkInteractiveElement(final Element element) {
        return this.checkInteractiveElement((SVGDocument)element.getOwnerDocument(), element);
    }
    
    public boolean checkInteractiveElement(final SVGDocument svgDocument, final Element element) {
        final String localName = element.getLocalName();
        if ("a".equals(localName)) {
            return true;
        }
        if ("title".equals(localName)) {
            return element.getParentNode() != svgDocument.getRootElement();
        }
        if ("desc".equals(localName)) {
            return element.getParentNode() != svgDocument.getRootElement();
        }
        if ("cursor".equals(localName)) {
            return true;
        }
        if (element.getAttribute("cursor").length() > 0) {
            return true;
        }
        for (Node node = element.getFirstChild(); node != null; node = node.getNextSibling()) {
            if (node.getNodeType() == 1) {
                final Element element2 = (Element)node;
                if ("http://www.w3.org/2000/svg".equals(element2.getNamespaceURI()) && this.checkInteractiveElement(element2)) {
                    return true;
                }
            }
        }
        return false;
    }
    
    public void setAnimationLimitingNone() {
        this.animationLimitingMode = 0;
        if (this.animationEngine != null) {
            this.setAnimationLimitingMode();
        }
    }
    
    public void setAnimationLimitingCPU(final float animationLimitingAmount) {
        this.animationLimitingMode = 1;
        this.animationLimitingAmount = animationLimitingAmount;
        if (this.animationEngine != null) {
            this.setAnimationLimitingMode();
        }
    }
    
    public void setAnimationLimitingFPS(final float animationLimitingAmount) {
        this.animationLimitingMode = 2;
        this.animationLimitingAmount = animationLimitingAmount;
        if (this.animationEngine != null) {
            this.setAnimationLimitingMode();
        }
    }
    
    protected void setAnimationLimitingMode() {
        switch (this.animationLimitingMode) {
            case 0: {
                this.animationEngine.setAnimationLimitingNone();
                break;
            }
            case 1: {
                this.animationEngine.setAnimationLimitingCPU(this.animationLimitingAmount);
                break;
            }
            case 2: {
                this.animationEngine.setAnimationLimitingFPS(this.animationLimitingAmount);
                break;
            }
        }
    }
    
    public void registerSVGBridges() {
        final UserAgent userAgent = this.getUserAgent();
        for (final BridgeExtension bridgeExtension : this.getBridgeExtensions(this.document)) {
            bridgeExtension.registerTags(this);
            userAgent.registerExtension(bridgeExtension);
        }
    }
    
    public List getBridgeExtensions(final Document document) {
        final String attributeNS = ((Element)((SVGOMDocument)document).getRootElement()).getAttributeNS(null, "version");
        SVGBridgeExtension svgBridgeExtension;
        if (attributeNS.length() == 0 || attributeNS.equals("1.0") || attributeNS.equals("1.1")) {
            svgBridgeExtension = new SVGBridgeExtension();
        }
        else {
            svgBridgeExtension = new SVG12BridgeExtension();
        }
        final float priority = svgBridgeExtension.getPriority();
        this.extensions = new LinkedList(getGlobalBridgeExtensions());
        final ListIterator<SVGBridgeExtension> listIterator = (ListIterator<SVGBridgeExtension>)this.extensions.listIterator();
        while (listIterator.hasNext()) {
            if (listIterator.next().getPriority() > priority) {
                listIterator.previous();
                listIterator.add(svgBridgeExtension);
                return this.extensions;
            }
        }
        listIterator.add(svgBridgeExtension);
        return this.extensions;
    }
    
    public static synchronized List getGlobalBridgeExtensions() {
        if (BridgeContext.globalExtensions != null) {
            return BridgeContext.globalExtensions;
        }
        BridgeContext.globalExtensions = new LinkedList();
        final Iterator providers = Service.providers(BridgeExtension.class);
    Label_0046:
        while (providers.hasNext()) {
            final BridgeExtension bridgeExtension = providers.next();
            final float priority = bridgeExtension.getPriority();
            final ListIterator<BridgeExtension> listIterator = (ListIterator<BridgeExtension>)BridgeContext.globalExtensions.listIterator();
            while (listIterator.hasNext()) {
                if (listIterator.next().getPriority() > priority) {
                    listIterator.previous();
                    listIterator.add(bridgeExtension);
                    continue Label_0046;
                }
            }
            listIterator.add(bridgeExtension);
        }
        return BridgeContext.globalExtensions;
    }
    
    static {
        BridgeContext.sharedPool = new InterpreterPool();
        BridgeContext.globalExtensions = null;
    }
    
    public static class CSSEngineUserAgentWrapper implements CSSEngineUserAgent
    {
        UserAgent ua;
        
        CSSEngineUserAgentWrapper(final UserAgent ua) {
            this.ua = ua;
        }
        
        public void displayError(final Exception ex) {
            this.ua.displayError(ex);
        }
        
        public void displayMessage(final String s) {
            this.ua.displayMessage(s);
        }
    }
    
    protected class AnimatedAttrListener implements AnimatedAttributeListener
    {
        public AnimatedAttrListener() {
        }
        
        public void animatedAttributeChanged(final Element element, final AnimatedLiveAttributeValue animatedLiveAttributeValue) {
            final BridgeUpdateHandler bridgeUpdateHandler = BridgeContext.getBridgeUpdateHandler(element);
            if (bridgeUpdateHandler != null) {
                try {
                    bridgeUpdateHandler.handleAnimatedAttributeChanged(animatedLiveAttributeValue);
                }
                catch (Exception ex) {
                    BridgeContext.this.userAgent.displayError(ex);
                }
            }
        }
        
        public void otherAnimationChanged(final Element element, final String s) {
            final BridgeUpdateHandler bridgeUpdateHandler = BridgeContext.getBridgeUpdateHandler(element);
            if (bridgeUpdateHandler != null) {
                try {
                    bridgeUpdateHandler.handleOtherAnimationChanged(s);
                }
                catch (Exception ex) {
                    BridgeContext.this.userAgent.displayError(ex);
                }
            }
        }
    }
    
    protected class CSSPropertiesChangedListener implements CSSEngineListener
    {
        public CSSPropertiesChangedListener() {
        }
        
        public void propertiesChanged(final CSSEngineEvent cssEngineEvent) {
            final Element element = cssEngineEvent.getElement();
            final SVGContext svgContext = BridgeContext.getSVGContext(element);
            if (svgContext == null) {
                final GraphicsNode graphicsNode = BridgeContext.this.getGraphicsNode(element.getParentNode());
                if (graphicsNode == null || !(graphicsNode instanceof CompositeGraphicsNode)) {
                    return;
                }
                final CompositeGraphicsNode compositeGraphicsNode = (CompositeGraphicsNode)graphicsNode;
                final int[] properties = cssEngineEvent.getProperties();
                int i = 0;
                while (i < properties.length) {
                    if (properties[i] == 12) {
                        if (!CSSUtilities.convertDisplay(element)) {
                            break;
                        }
                        final GraphicsNode build = BridgeContext.this.getGVTBuilder().build(BridgeContext.this, element);
                        if (build == null) {
                            break;
                        }
                        int index = -1;
                        for (Node node = element.getPreviousSibling(); node != null; node = node.getPreviousSibling()) {
                            if (node.getNodeType() == 1) {
                                final GraphicsNode graphicsNode2 = BridgeContext.this.getGraphicsNode(node);
                                if (graphicsNode2 != null) {
                                    index = compositeGraphicsNode.indexOf(graphicsNode2);
                                    if (index != -1) {
                                        break;
                                    }
                                }
                            }
                        }
                        ++index;
                        compositeGraphicsNode.add(index, build);
                        break;
                    }
                    else {
                        ++i;
                    }
                }
            }
            if (svgContext != null && svgContext instanceof BridgeUpdateHandler) {
                ((BridgeUpdateHandler)svgContext).handleCSSEngineEvent(cssEngineEvent);
            }
        }
    }
    
    protected class DOMCharacterDataModifiedEventListener implements EventListener
    {
        public DOMCharacterDataModifiedEventListener() {
        }
        
        public void handleEvent(final Event event) {
            Node node;
            for (node = (Node)event.getTarget(); node != null && !(node instanceof SVGOMElement); node = (Node)((AbstractNode)node).getParentNodeEventTarget()) {}
            final BridgeUpdateHandler bridgeUpdateHandler = BridgeContext.getBridgeUpdateHandler(node);
            if (bridgeUpdateHandler != null) {
                try {
                    bridgeUpdateHandler.handleDOMCharacterDataModified((MutationEvent)event);
                }
                catch (Exception ex) {
                    BridgeContext.this.userAgent.displayError(ex);
                }
            }
        }
    }
    
    protected class DOMNodeRemovedEventListener implements EventListener
    {
        public DOMNodeRemovedEventListener() {
        }
        
        public void handleEvent(final Event event) {
            final BridgeUpdateHandler bridgeUpdateHandler = BridgeContext.getBridgeUpdateHandler((Node)event.getTarget());
            if (bridgeUpdateHandler != null) {
                try {
                    bridgeUpdateHandler.handleDOMNodeRemovedEvent((MutationEvent)event);
                }
                catch (Exception ex) {
                    BridgeContext.this.userAgent.displayError(ex);
                }
            }
        }
    }
    
    protected class DOMNodeInsertedEventListener implements EventListener
    {
        public DOMNodeInsertedEventListener() {
        }
        
        public void handleEvent(final Event event) {
            final MutationEvent mutationEvent = (MutationEvent)event;
            final BridgeUpdateHandler bridgeUpdateHandler = BridgeContext.getBridgeUpdateHandler(mutationEvent.getRelatedNode());
            if (bridgeUpdateHandler != null) {
                try {
                    bridgeUpdateHandler.handleDOMNodeInsertedEvent(mutationEvent);
                }
                catch (InterruptedBridgeException ex2) {}
                catch (Exception ex) {
                    BridgeContext.this.userAgent.displayError(ex);
                }
            }
        }
    }
    
    protected class DOMMouseOverEventListener implements EventListener
    {
        public DOMMouseOverEventListener() {
        }
        
        public void handleEvent(final Event event) {
            final Cursor convertCursor = CSSUtilities.convertCursor((Element)event.getTarget(), BridgeContext.this);
            if (convertCursor != null) {
                BridgeContext.this.userAgent.setSVGCursor(convertCursor);
            }
        }
    }
    
    protected class DOMMouseOutEventListener implements EventListener
    {
        public DOMMouseOutEventListener() {
        }
        
        public void handleEvent(final Event event) {
            final Element element = (Element)((MouseEvent)event).getRelatedTarget();
            Cursor svgCursor = CursorManager.DEFAULT_CURSOR;
            if (element != null) {
                svgCursor = CSSUtilities.convertCursor(element, BridgeContext.this);
            }
            if (svgCursor == null) {
                svgCursor = CursorManager.DEFAULT_CURSOR;
            }
            BridgeContext.this.userAgent.setSVGCursor(svgCursor);
        }
    }
    
    protected class DOMAttrModifiedEventListener implements EventListener
    {
        public DOMAttrModifiedEventListener() {
        }
        
        public void handleEvent(final Event event) {
            final BridgeUpdateHandler bridgeUpdateHandler = BridgeContext.getBridgeUpdateHandler((Node)event.getTarget());
            if (bridgeUpdateHandler != null) {
                try {
                    bridgeUpdateHandler.handleDOMAttrModifiedEvent((MutationEvent)event);
                }
                catch (Exception ex) {
                    BridgeContext.this.userAgent.displayError(ex);
                }
            }
        }
    }
    
    protected static class EventListenerMememto
    {
        public SoftReference target;
        public SoftReference listener;
        public boolean useCapture;
        public String namespaceURI;
        public String eventType;
        public boolean namespaced;
        
        public EventListenerMememto(final EventTarget eventTarget, final String eventType, final EventListener eventListener, final boolean useCapture, final BridgeContext bridgeContext) {
            final Set eventListenerSet = bridgeContext.eventListenerSet;
            this.target = new SoftReferenceMememto(eventTarget, this, eventListenerSet);
            this.listener = new SoftReferenceMememto(eventListener, this, eventListenerSet);
            this.eventType = eventType;
            this.useCapture = useCapture;
        }
        
        public EventListenerMememto(final EventTarget eventTarget, final String namespaceURI, final String s, final EventListener eventListener, final boolean b, final BridgeContext bridgeContext) {
            this(eventTarget, s, eventListener, b, bridgeContext);
            this.namespaceURI = namespaceURI;
            this.namespaced = true;
        }
        
        public EventListener getListener() {
            return this.listener.get();
        }
        
        public NodeEventTarget getTarget() {
            return this.target.get();
        }
        
        public boolean getUseCapture() {
            return this.useCapture;
        }
        
        public String getNamespaceURI() {
            return this.namespaceURI;
        }
        
        public String getEventType() {
            return this.eventType;
        }
        
        public boolean getNamespaced() {
            return this.namespaced;
        }
    }
    
    public static class SoftReferenceMememto extends CleanerThread.SoftReferenceCleared
    {
        Object mememto;
        Set set;
        
        SoftReferenceMememto(final Object o, final Object mememto, final Set set) {
            super(o);
            this.mememto = mememto;
            this.set = set;
        }
        
        public void cleared() {
            synchronized (this.set) {
                this.set.remove(this.mememto);
                this.mememto = null;
                this.set = null;
            }
        }
    }
}
