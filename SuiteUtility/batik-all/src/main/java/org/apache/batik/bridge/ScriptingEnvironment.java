// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.bridge;

import java.io.OutputStream;
import java.net.URLConnection;
import java.io.OutputStreamWriter;
import java.util.zip.GZIPOutputStream;
import java.util.zip.DeflaterOutputStream;
import java.io.InputStream;
import java.io.BufferedReader;
import java.io.UnsupportedEncodingException;
import java.io.InputStreamReader;
import org.apache.batik.util.EncodingUtilities;
import org.apache.batik.util.ParsedURL;
import java.net.URL;
import org.w3c.dom.DOMImplementation;
import org.apache.batik.dom.GenericDOMImplementation;
import org.apache.batik.dom.util.SAXDocumentFactory;
import org.apache.batik.dom.util.DOMUtilities;
import org.apache.batik.dom.svg.SVGOMDocument;
import org.apache.batik.dom.svg.SAXSVGDocumentFactory;
import org.apache.batik.util.XMLResourceDescriptor;
import org.w3c.dom.Document;
import java.util.TimerTask;
import org.w3c.dom.events.MutationEvent;
import org.w3c.dom.svg.SVGDocument;
import org.w3c.dom.Element;
import org.apache.batik.script.InterpreterException;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import org.apache.batik.script.ScriptEventWrapper;
import org.w3c.dom.events.Event;
import org.apache.batik.script.Window;
import org.apache.batik.script.Interpreter;
import org.apache.batik.dom.events.NodeEventTarget;
import org.w3c.dom.Node;
import java.util.HashMap;
import java.util.Map;
import org.w3c.dom.events.EventListener;
import org.apache.batik.util.RunnableQueue;
import java.util.Timer;

public class ScriptingEnvironment extends BaseScriptingEnvironment
{
    public static final String[] SVG_EVENT_ATTRS;
    public static final String[] SVG_DOM_EVENT;
    protected Timer timer;
    protected UpdateManager updateManager;
    protected RunnableQueue updateRunnableQueue;
    protected EventListener domNodeInsertedListener;
    protected EventListener domNodeRemovedListener;
    protected EventListener domAttrModifiedListener;
    protected EventListener svgAbortListener;
    protected EventListener svgErrorListener;
    protected EventListener svgResizeListener;
    protected EventListener svgScrollListener;
    protected EventListener svgUnloadListener;
    protected EventListener svgZoomListener;
    protected EventListener beginListener;
    protected EventListener endListener;
    protected EventListener repeatListener;
    protected EventListener focusinListener;
    protected EventListener focusoutListener;
    protected EventListener activateListener;
    protected EventListener clickListener;
    protected EventListener mousedownListener;
    protected EventListener mouseupListener;
    protected EventListener mouseoverListener;
    protected EventListener mouseoutListener;
    protected EventListener mousemoveListener;
    protected EventListener keypressListener;
    protected EventListener keydownListener;
    protected EventListener keyupListener;
    protected EventListener[] listeners;
    Map attrToDOMEvent;
    Map attrToListener;
    
    public ScriptingEnvironment(final BridgeContext bridgeContext) {
        super(bridgeContext);
        this.timer = new Timer(true);
        this.svgAbortListener = new ScriptingEventListener("onabort");
        this.svgErrorListener = new ScriptingEventListener("onerror");
        this.svgResizeListener = new ScriptingEventListener("onresize");
        this.svgScrollListener = new ScriptingEventListener("onscroll");
        this.svgUnloadListener = new ScriptingEventListener("onunload");
        this.svgZoomListener = new ScriptingEventListener("onzoom");
        this.beginListener = new ScriptingEventListener("onbegin");
        this.endListener = new ScriptingEventListener("onend");
        this.repeatListener = new ScriptingEventListener("onrepeat");
        this.focusinListener = new ScriptingEventListener("onfocusin");
        this.focusoutListener = new ScriptingEventListener("onfocusout");
        this.activateListener = new ScriptingEventListener("onactivate");
        this.clickListener = new ScriptingEventListener("onclick");
        this.mousedownListener = new ScriptingEventListener("onmousedown");
        this.mouseupListener = new ScriptingEventListener("onmouseup");
        this.mouseoverListener = new ScriptingEventListener("onmouseover");
        this.mouseoutListener = new ScriptingEventListener("onmouseout");
        this.mousemoveListener = new ScriptingEventListener("onmousemove");
        this.keypressListener = new ScriptingEventListener("onkeypress");
        this.keydownListener = new ScriptingEventListener("onkeydown");
        this.keyupListener = new ScriptingEventListener("onkeyup");
        this.listeners = new EventListener[] { this.svgAbortListener, this.svgErrorListener, this.svgResizeListener, this.svgScrollListener, this.svgUnloadListener, this.svgZoomListener, this.beginListener, this.endListener, this.repeatListener, this.focusinListener, this.focusoutListener, this.activateListener, this.clickListener, this.mousedownListener, this.mouseupListener, this.mouseoverListener, this.mouseoutListener, this.mousemoveListener, this.keypressListener, this.keydownListener, this.keyupListener };
        this.attrToDOMEvent = new HashMap(ScriptingEnvironment.SVG_EVENT_ATTRS.length);
        this.attrToListener = new HashMap(ScriptingEnvironment.SVG_EVENT_ATTRS.length);
        for (int i = 0; i < ScriptingEnvironment.SVG_EVENT_ATTRS.length; ++i) {
            this.attrToDOMEvent.put(ScriptingEnvironment.SVG_EVENT_ATTRS[i], ScriptingEnvironment.SVG_DOM_EVENT[i]);
            this.attrToListener.put(ScriptingEnvironment.SVG_EVENT_ATTRS[i], this.listeners[i]);
        }
        this.updateManager = bridgeContext.getUpdateManager();
        this.updateRunnableQueue = this.updateManager.getUpdateRunnableQueue();
        this.addScriptingListeners(this.document.getDocumentElement());
        this.addDocumentListeners();
    }
    
    protected void addDocumentListeners() {
        this.domNodeInsertedListener = new DOMNodeInsertedListener();
        this.domNodeRemovedListener = new DOMNodeRemovedListener();
        this.domAttrModifiedListener = new DOMAttrModifiedListener();
        final NodeEventTarget nodeEventTarget = (NodeEventTarget)this.document;
        nodeEventTarget.addEventListenerNS("http://www.w3.org/2001/xml-events", "DOMNodeInserted", this.domNodeInsertedListener, false, null);
        nodeEventTarget.addEventListenerNS("http://www.w3.org/2001/xml-events", "DOMNodeRemoved", this.domNodeRemovedListener, false, null);
        nodeEventTarget.addEventListenerNS("http://www.w3.org/2001/xml-events", "DOMAttrModified", this.domAttrModifiedListener, false, null);
    }
    
    protected void removeDocumentListeners() {
        final NodeEventTarget nodeEventTarget = (NodeEventTarget)this.document;
        nodeEventTarget.removeEventListenerNS("http://www.w3.org/2001/xml-events", "DOMNodeInserted", this.domNodeInsertedListener, false);
        nodeEventTarget.removeEventListenerNS("http://www.w3.org/2001/xml-events", "DOMNodeRemoved", this.domNodeRemovedListener, false);
        nodeEventTarget.removeEventListenerNS("http://www.w3.org/2001/xml-events", "DOMAttrModified", this.domAttrModifiedListener, false);
    }
    
    public org.apache.batik.script.Window createWindow(final Interpreter interpreter, final String s) {
        return new Window(interpreter, s);
    }
    
    public void runEventHandler(final String s, final Event event, final String s2, final String s3) {
        final Interpreter interpreter = this.getInterpreter(s2);
        if (interpreter == null) {
            return;
        }
        try {
            this.checkCompatibleScriptURL(s2, this.docPURL);
            Object eventObject;
            if (event instanceof ScriptEventWrapper) {
                eventObject = ((ScriptEventWrapper)event).getEventObject();
            }
            else {
                eventObject = event;
            }
            interpreter.bindObject("event", eventObject);
            interpreter.bindObject("evt", eventObject);
            interpreter.evaluate(new StringReader(s), s3);
        }
        catch (IOException ex3) {}
        catch (InterpreterException ex) {
            this.handleInterpreterException(ex);
        }
        catch (SecurityException ex2) {
            this.handleSecurityException(ex2);
        }
    }
    
    public void interrupt() {
        this.timer.cancel();
        this.removeScriptingListeners(this.document.getDocumentElement());
        this.removeDocumentListeners();
    }
    
    public void addScriptingListeners(final Node node) {
        if (node.getNodeType() == 1) {
            this.addScriptingListenersOn((Element)node);
        }
        for (Node node2 = node.getFirstChild(); node2 != null; node2 = node2.getNextSibling()) {
            this.addScriptingListeners(node2);
        }
    }
    
    protected void addScriptingListenersOn(final Element element) {
        final NodeEventTarget nodeEventTarget = (NodeEventTarget)element;
        if ("http://www.w3.org/2000/svg".equals(element.getNamespaceURI())) {
            if ("svg".equals(element.getLocalName())) {
                if (element.hasAttributeNS(null, "onabort")) {
                    nodeEventTarget.addEventListenerNS("http://www.w3.org/2001/xml-events", "SVGAbort", this.svgAbortListener, false, null);
                }
                if (element.hasAttributeNS(null, "onerror")) {
                    nodeEventTarget.addEventListenerNS("http://www.w3.org/2001/xml-events", "SVGError", this.svgErrorListener, false, null);
                }
                if (element.hasAttributeNS(null, "onresize")) {
                    nodeEventTarget.addEventListenerNS("http://www.w3.org/2001/xml-events", "SVGResize", this.svgResizeListener, false, null);
                }
                if (element.hasAttributeNS(null, "onscroll")) {
                    nodeEventTarget.addEventListenerNS("http://www.w3.org/2001/xml-events", "SVGScroll", this.svgScrollListener, false, null);
                }
                if (element.hasAttributeNS(null, "onunload")) {
                    nodeEventTarget.addEventListenerNS("http://www.w3.org/2001/xml-events", "SVGUnload", this.svgUnloadListener, false, null);
                }
                if (element.hasAttributeNS(null, "onzoom")) {
                    nodeEventTarget.addEventListenerNS("http://www.w3.org/2001/xml-events", "SVGZoom", this.svgZoomListener, false, null);
                }
            }
            else {
                final String localName = element.getLocalName();
                if (localName.equals("set") || localName.startsWith("animate")) {
                    if (element.hasAttributeNS(null, "onbegin")) {
                        nodeEventTarget.addEventListenerNS("http://www.w3.org/2001/xml-events", "beginEvent", this.beginListener, false, null);
                    }
                    if (element.hasAttributeNS(null, "onend")) {
                        nodeEventTarget.addEventListenerNS("http://www.w3.org/2001/xml-events", "endEvent", this.endListener, false, null);
                    }
                    if (element.hasAttributeNS(null, "onrepeat")) {
                        nodeEventTarget.addEventListenerNS("http://www.w3.org/2001/xml-events", "repeatEvent", this.repeatListener, false, null);
                    }
                    return;
                }
            }
        }
        if (element.hasAttributeNS(null, "onfocusin")) {
            nodeEventTarget.addEventListenerNS("http://www.w3.org/2001/xml-events", "DOMFocusIn", this.focusinListener, false, null);
        }
        if (element.hasAttributeNS(null, "onfocusout")) {
            nodeEventTarget.addEventListenerNS("http://www.w3.org/2001/xml-events", "DOMFocusOut", this.focusoutListener, false, null);
        }
        if (element.hasAttributeNS(null, "onactivate")) {
            nodeEventTarget.addEventListenerNS("http://www.w3.org/2001/xml-events", "DOMActivate", this.activateListener, false, null);
        }
        if (element.hasAttributeNS(null, "onclick")) {
            nodeEventTarget.addEventListenerNS("http://www.w3.org/2001/xml-events", "click", this.clickListener, false, null);
        }
        if (element.hasAttributeNS(null, "onmousedown")) {
            nodeEventTarget.addEventListenerNS("http://www.w3.org/2001/xml-events", "mousedown", this.mousedownListener, false, null);
        }
        if (element.hasAttributeNS(null, "onmouseup")) {
            nodeEventTarget.addEventListenerNS("http://www.w3.org/2001/xml-events", "mouseup", this.mouseupListener, false, null);
        }
        if (element.hasAttributeNS(null, "onmouseover")) {
            nodeEventTarget.addEventListenerNS("http://www.w3.org/2001/xml-events", "mouseover", this.mouseoverListener, false, null);
        }
        if (element.hasAttributeNS(null, "onmouseout")) {
            nodeEventTarget.addEventListenerNS("http://www.w3.org/2001/xml-events", "mouseout", this.mouseoutListener, false, null);
        }
        if (element.hasAttributeNS(null, "onmousemove")) {
            nodeEventTarget.addEventListenerNS("http://www.w3.org/2001/xml-events", "mousemove", this.mousemoveListener, false, null);
        }
        if (element.hasAttributeNS(null, "onkeypress")) {
            nodeEventTarget.addEventListenerNS("http://www.w3.org/2001/xml-events", "keypress", this.keypressListener, false, null);
        }
        if (element.hasAttributeNS(null, "onkeydown")) {
            nodeEventTarget.addEventListenerNS("http://www.w3.org/2001/xml-events", "keydown", this.keydownListener, false, null);
        }
        if (element.hasAttributeNS(null, "onkeyup")) {
            nodeEventTarget.addEventListenerNS("http://www.w3.org/2001/xml-events", "keyup", this.keyupListener, false, null);
        }
    }
    
    protected void removeScriptingListeners(final Node node) {
        if (node.getNodeType() == 1) {
            this.removeScriptingListenersOn((Element)node);
        }
        for (Node node2 = node.getFirstChild(); node2 != null; node2 = node2.getNextSibling()) {
            this.removeScriptingListeners(node2);
        }
    }
    
    protected void removeScriptingListenersOn(final Element element) {
        final NodeEventTarget nodeEventTarget = (NodeEventTarget)element;
        if ("http://www.w3.org/2000/svg".equals(element.getNamespaceURI())) {
            if ("svg".equals(element.getLocalName())) {
                nodeEventTarget.removeEventListenerNS("http://www.w3.org/2001/xml-events", "SVGAbort", this.svgAbortListener, false);
                nodeEventTarget.removeEventListenerNS("http://www.w3.org/2001/xml-events", "SVGError", this.svgErrorListener, false);
                nodeEventTarget.removeEventListenerNS("http://www.w3.org/2001/xml-events", "SVGResize", this.svgResizeListener, false);
                nodeEventTarget.removeEventListenerNS("http://www.w3.org/2001/xml-events", "SVGScroll", this.svgScrollListener, false);
                nodeEventTarget.removeEventListenerNS("http://www.w3.org/2001/xml-events", "SVGUnload", this.svgUnloadListener, false);
                nodeEventTarget.removeEventListenerNS("http://www.w3.org/2001/xml-events", "SVGZoom", this.svgZoomListener, false);
            }
            else {
                final String localName = element.getLocalName();
                if (localName.equals("set") || localName.startsWith("animate")) {
                    nodeEventTarget.removeEventListenerNS("http://www.w3.org/2001/xml-events", "beginEvent", this.beginListener, false);
                    nodeEventTarget.removeEventListenerNS("http://www.w3.org/2001/xml-events", "endEvent", this.endListener, false);
                    nodeEventTarget.removeEventListenerNS("http://www.w3.org/2001/xml-events", "repeatEvent", this.repeatListener, false);
                    return;
                }
            }
        }
        nodeEventTarget.removeEventListenerNS("http://www.w3.org/2001/xml-events", "DOMFocusIn", this.focusinListener, false);
        nodeEventTarget.removeEventListenerNS("http://www.w3.org/2001/xml-events", "DOMFocusOut", this.focusoutListener, false);
        nodeEventTarget.removeEventListenerNS("http://www.w3.org/2001/xml-events", "DOMActivate", this.activateListener, false);
        nodeEventTarget.removeEventListenerNS("http://www.w3.org/2001/xml-events", "click", this.clickListener, false);
        nodeEventTarget.removeEventListenerNS("http://www.w3.org/2001/xml-events", "mousedown", this.mousedownListener, false);
        nodeEventTarget.removeEventListenerNS("http://www.w3.org/2001/xml-events", "mouseup", this.mouseupListener, false);
        nodeEventTarget.removeEventListenerNS("http://www.w3.org/2001/xml-events", "mouseover", this.mouseoverListener, false);
        nodeEventTarget.removeEventListenerNS("http://www.w3.org/2001/xml-events", "mouseout", this.mouseoutListener, false);
        nodeEventTarget.removeEventListenerNS("http://www.w3.org/2001/xml-events", "mousemove", this.mousemoveListener, false);
        nodeEventTarget.removeEventListenerNS("http://www.w3.org/2001/xml-events", "keypress", this.keypressListener, false);
        nodeEventTarget.removeEventListenerNS("http://www.w3.org/2001/xml-events", "keydown", this.keydownListener, false);
        nodeEventTarget.removeEventListenerNS("http://www.w3.org/2001/xml-events", "keyup", this.keyupListener, false);
    }
    
    protected void updateScriptingListeners(final Element element, final String s) {
        final String s2 = this.attrToDOMEvent.get(s);
        if (s2 == null) {
            return;
        }
        final EventListener eventListener = this.attrToListener.get(s);
        final NodeEventTarget nodeEventTarget = (NodeEventTarget)element;
        if (element.hasAttributeNS(null, s)) {
            nodeEventTarget.addEventListenerNS("http://www.w3.org/2001/xml-events", s2, eventListener, false, null);
        }
        else {
            nodeEventTarget.removeEventListenerNS("http://www.w3.org/2001/xml-events", s2, eventListener, false);
        }
    }
    
    static {
        SVG_EVENT_ATTRS = new String[] { "onabort", "onerror", "onresize", "onscroll", "onunload", "onzoom", "onbegin", "onend", "onrepeat", "onfocusin", "onfocusout", "onactivate", "onclick", "onmousedown", "onmouseup", "onmouseover", "onmouseout", "onmousemove", "onkeypress", "onkeydown", "onkeyup" };
        SVG_DOM_EVENT = new String[] { "SVGAbort", "SVGError", "SVGResize", "SVGScroll", "SVGUnload", "SVGZoom", "beginEvent", "endEvent", "repeatEvent", "DOMFocusIn", "DOMFocusOut", "DOMActivate", "click", "mousedown", "mouseup", "mouseover", "mouseout", "mousemove", "keypress", "keydown", "keyup" };
    }
    
    protected class ScriptingEventListener implements EventListener
    {
        protected String attribute;
        
        public ScriptingEventListener(final String attribute) {
            this.attribute = attribute;
        }
        
        public void handleEvent(final Event event) {
            final Element element = (Element)event.getCurrentTarget();
            final String attributeNS = element.getAttributeNS(null, this.attribute);
            if (attributeNS.length() == 0) {
                return;
            }
            final String formatMessage = Messages.formatMessage("BaseScriptingEnvironment.constant.event.script.description", new Object[] { ((SVGDocument)element.getOwnerDocument()).getURL(), this.attribute, new Integer(ScriptingEnvironment.this.bridgeContext.getDocumentLoader().getLineNumber(element)) });
            Element parentElement;
            for (parentElement = element; parentElement != null && (!"http://www.w3.org/2000/svg".equals(parentElement.getNamespaceURI()) || !"svg".equals(parentElement.getLocalName())); parentElement = SVGUtilities.getParentElement(parentElement)) {}
            if (parentElement == null) {
                return;
            }
            ScriptingEnvironment.this.runEventHandler(attributeNS, event, parentElement.getAttributeNS(null, "contentScriptType"), formatMessage);
        }
    }
    
    protected class DOMAttrModifiedListener implements EventListener
    {
        public void handleEvent(final Event event) {
            final MutationEvent mutationEvent = (MutationEvent)event;
            if (mutationEvent.getAttrChange() != 1) {
                ScriptingEnvironment.this.updateScriptingListeners((Element)mutationEvent.getTarget(), mutationEvent.getAttrName());
            }
        }
    }
    
    protected class DOMNodeRemovedListener implements EventListener
    {
        public void handleEvent(final Event event) {
            ScriptingEnvironment.this.removeScriptingListeners((Node)event.getTarget());
        }
    }
    
    protected class DOMNodeInsertedListener implements EventListener
    {
        public void handleEvent(final Event event) {
            ScriptingEnvironment.this.addScriptingListeners((Node)event.getTarget());
        }
    }
    
    protected class Window implements org.apache.batik.script.Window
    {
        protected Interpreter interpreter;
        protected String language;
        static final String DEFLATE = "deflate";
        static final String GZIP = "gzip";
        static final String UTF_8 = "UTF-8";
        private final /* synthetic */ ScriptingEnvironment this$0;
        
        public Window(final Interpreter interpreter, final String language) {
            this.interpreter = interpreter;
            this.language = language;
        }
        
        public Object setInterval(final String s, final long n) {
            final TimerTask task = new TimerTask() {
                EvaluateIntervalRunnable eir = this.this$1.this$0.new EvaluateIntervalRunnable(s, this.this$1.interpreter);
                private final /* synthetic */ Window this$1 = this$1;
                
                public void run() {
                    synchronized (this.eir) {
                        if (this.eir.count > 1) {
                            return;
                        }
                        final EvaluateIntervalRunnable eir = this.eir;
                        ++eir.count;
                    }
                    synchronized (this.this$1.this$0.updateRunnableQueue.getIteratorLock()) {
                        if (this.this$1.this$0.updateRunnableQueue.getThread() == null) {
                            this.cancel();
                            return;
                        }
                        this.this$1.this$0.updateRunnableQueue.invokeLater(this.eir);
                    }
                    synchronized (this.eir) {
                        if (this.eir.error) {
                            this.cancel();
                        }
                    }
                }
            };
            ScriptingEnvironment.this.timer.schedule(task, n, n);
            return task;
        }
        
        public Object setInterval(final Runnable runnable, final long n) {
            final TimerTask task = new TimerTask() {
                EvaluateRunnableRunnable eihr = this.this$1.this$0.new EvaluateRunnableRunnable(runnable);
                private final /* synthetic */ Window this$1 = this$1;
                
                public void run() {
                    synchronized (this.eihr) {
                        if (this.eihr.count > 1) {
                            return;
                        }
                        final EvaluateRunnableRunnable eihr = this.eihr;
                        ++eihr.count;
                    }
                    this.this$1.this$0.updateRunnableQueue.invokeLater(this.eihr);
                    synchronized (this.eihr) {
                        if (this.eihr.error) {
                            this.cancel();
                        }
                    }
                }
            };
            ScriptingEnvironment.this.timer.schedule(task, n, n);
            return task;
        }
        
        public void clearInterval(final Object o) {
            if (o == null) {
                return;
            }
            ((TimerTask)o).cancel();
        }
        
        public Object setTimeout(final String s, final long delay) {
            final TimerTask task = new TimerTask() {
                private final /* synthetic */ Window this$1 = this$1;
                
                public void run() {
                    this.this$1.this$0.updateRunnableQueue.invokeLater(this.this$1.this$0.new EvaluateRunnable(s, this.this$1.interpreter));
                }
            };
            ScriptingEnvironment.this.timer.schedule(task, delay);
            return task;
        }
        
        public Object setTimeout(final Runnable runnable, final long delay) {
            final TimerTask task = new TimerTask() {
                private final /* synthetic */ Window this$1 = this$1;
                
                public void run() {
                    this.this$1.this$0.updateRunnableQueue.invokeLater(new Runnable() {
                        private final /* synthetic */ ScriptingEnvironment$4 this$2 = this$2;
                        
                        public void run() {
                            try {
                                runnable.run();
                            }
                            catch (Exception ex) {
                                if (this.this$2.this$1.this$0.userAgent != null) {
                                    this.this$2.this$1.this$0.userAgent.displayError(ex);
                                }
                            }
                        }
                    });
                }
            };
            ScriptingEnvironment.this.timer.schedule(task, delay);
            return task;
        }
        
        public void clearTimeout(final Object o) {
            if (o == null) {
                return;
            }
            ((TimerTask)o).cancel();
        }
        
        public Node parseXML(final String s, final Document document) {
            final SAXSVGDocumentFactory saxsvgDocumentFactory = new SAXSVGDocumentFactory(XMLResourceDescriptor.getXMLParserClassName());
            URL url = null;
            if (document instanceof SVGOMDocument) {
                url = ((SVGOMDocument)document).getURLObject();
            }
            if (url == null) {
                url = ((SVGOMDocument)ScriptingEnvironment.this.bridgeContext.getDocument()).getURLObject();
            }
            final String s2 = (url == null) ? "" : url.toString();
            final Node xml = DOMUtilities.parseXML(s, document, s2, null, null, saxsvgDocumentFactory);
            if (xml != null) {
                return xml;
            }
            if (document instanceof SVGOMDocument) {
                final HashMap<String, String> hashMap = new HashMap<String, String>();
                hashMap.put("xmlns", "http://www.w3.org/2000/xmlns/");
                hashMap.put("xmlns:xlink", "http://www.w3.org/1999/xlink");
                final Node xml2 = DOMUtilities.parseXML(s, document, s2, hashMap, "svg", saxsvgDocumentFactory);
                if (xml2 != null) {
                    return xml2;
                }
            }
            SAXDocumentFactory saxDocumentFactory;
            if (document != null) {
                saxDocumentFactory = new SAXDocumentFactory(document.getImplementation(), XMLResourceDescriptor.getXMLParserClassName());
            }
            else {
                saxDocumentFactory = new SAXDocumentFactory(new GenericDOMImplementation(), XMLResourceDescriptor.getXMLParserClassName());
            }
            return DOMUtilities.parseXML(s, document, s2, null, null, saxDocumentFactory);
        }
        
        public void getURL(final String s, final URLResponseHandler urlResponseHandler) {
            this.getURL(s, urlResponseHandler, null);
        }
        
        public void getURL(final String s, final URLResponseHandler urlResponseHandler, final String s2) {
            final Thread thread = new Thread() {
                private final /* synthetic */ Window this$1 = this$1;
                
                public void run() {
                    try {
                        final ParsedURL parsedURL = new ParsedURL(((SVGOMDocument)this.this$1.this$0.document).getParsedURL(), s);
                        String charsetName = null;
                        if (s2 != null) {
                            final String javaEncoding = EncodingUtilities.javaEncoding(s2);
                            charsetName = ((javaEncoding == null) ? s2 : javaEncoding);
                        }
                        final InputStream openStream = parsedURL.openStream();
                        InputStreamReader in;
                        if (charsetName == null) {
                            in = new InputStreamReader(openStream);
                        }
                        else {
                            try {
                                in = new InputStreamReader(openStream, charsetName);
                            }
                            catch (UnsupportedEncodingException ex2) {
                                in = new InputStreamReader(openStream);
                            }
                        }
                        final BufferedReader bufferedReader = new BufferedReader(in);
                        final StringBuffer sb = new StringBuffer();
                        final char[] str = new char[4096];
                        int read;
                        while ((read = bufferedReader.read(str, 0, str.length)) != -1) {
                            sb.append(str, 0, read);
                        }
                        bufferedReader.close();
                        this.this$1.this$0.updateRunnableQueue.invokeLater(new Runnable() {
                            private final /* synthetic */ ScriptingEnvironment$6 this$2 = this$2;
                            
                            public void run() {
                                try {
                                    urlResponseHandler.getURLDone(true, parsedURL.getContentType(), sb.toString());
                                }
                                catch (Exception ex) {
                                    if (this.this$2.this$1.this$0.userAgent != null) {
                                        this.this$2.this$1.this$0.userAgent.displayError(ex);
                                    }
                                }
                            }
                        });
                    }
                    catch (Exception ex) {
                        if (ex instanceof SecurityException) {
                            this.this$1.this$0.userAgent.displayError(ex);
                        }
                        this.this$1.this$0.updateRunnableQueue.invokeLater(new Runnable() {
                            private final /* synthetic */ ScriptingEnvironment$6 this$2 = this$2;
                            
                            public void run() {
                                try {
                                    urlResponseHandler.getURLDone(false, null, null);
                                }
                                catch (Exception ex) {
                                    if (this.this$2.this$1.this$0.userAgent != null) {
                                        this.this$2.this$1.this$0.userAgent.displayError(ex);
                                    }
                                }
                            }
                        });
                    }
                }
            };
            thread.setPriority(1);
            thread.start();
        }
        
        public void postURL(final String s, final String s2, final URLResponseHandler urlResponseHandler) {
            this.postURL(s, s2, urlResponseHandler, "text/plain", null);
        }
        
        public void postURL(final String s, final String s2, final URLResponseHandler urlResponseHandler, final String s3) {
            this.postURL(s, s2, urlResponseHandler, s3, null);
        }
        
        public void postURL(final String s, final String s2, final URLResponseHandler urlResponseHandler, final String s3, final String s4) {
            final Thread thread = new Thread() {
                private final /* synthetic */ Window this$1 = this$1;
                
                public void run() {
                    try {
                        final String documentURI = ((SVGOMDocument)this.this$1.this$0.document).getDocumentURI();
                        URL url;
                        if (documentURI == null) {
                            url = new URL(s);
                        }
                        else {
                            url = new URL(new URL(documentURI), s);
                        }
                        final URLConnection openConnection = url.openConnection();
                        openConnection.setDoOutput(true);
                        openConnection.setDoInput(true);
                        openConnection.setUseCaches(false);
                        openConnection.setRequestProperty("Content-Type", s3);
                        OutputStream outputStream = openConnection.getOutputStream();
                        String javaEncoding = null;
                        String s = s4;
                        if (s != null) {
                            if (s.startsWith("deflate")) {
                                outputStream = new DeflaterOutputStream(outputStream);
                                if (s.length() > "deflate".length()) {
                                    s = s.substring("deflate".length() + 1);
                                }
                                else {
                                    s = "";
                                }
                                openConnection.setRequestProperty("Content-Encoding", "deflate");
                            }
                            if (s.startsWith("gzip")) {
                                outputStream = new GZIPOutputStream(outputStream);
                                if (s.length() > "gzip".length()) {
                                    s = s.substring("gzip".length() + 1);
                                }
                                else {
                                    s = "";
                                }
                                openConnection.setRequestProperty("Content-Encoding", "deflate");
                            }
                            if (s.length() != 0) {
                                javaEncoding = EncodingUtilities.javaEncoding(s);
                                if (javaEncoding == null) {
                                    javaEncoding = "UTF-8";
                                }
                            }
                            else {
                                javaEncoding = "UTF-8";
                            }
                        }
                        OutputStreamWriter outputStreamWriter;
                        if (javaEncoding == null) {
                            outputStreamWriter = new OutputStreamWriter(outputStream);
                        }
                        else {
                            outputStreamWriter = new OutputStreamWriter(outputStream, javaEncoding);
                        }
                        outputStreamWriter.write(s2);
                        outputStreamWriter.flush();
                        outputStreamWriter.close();
                        outputStream.close();
                        final InputStream inputStream = openConnection.getInputStream();
                        final String charsetName = "UTF-8";
                        InputStreamReader in;
                        if (charsetName == null) {
                            in = new InputStreamReader(inputStream);
                        }
                        else {
                            in = new InputStreamReader(inputStream, charsetName);
                        }
                        final BufferedReader bufferedReader = new BufferedReader(in);
                        final StringBuffer sb = new StringBuffer();
                        final char[] str = new char[4096];
                        int read;
                        while ((read = bufferedReader.read(str, 0, str.length)) != -1) {
                            sb.append(str, 0, read);
                        }
                        bufferedReader.close();
                        this.this$1.this$0.updateRunnableQueue.invokeLater(new Runnable() {
                            private final /* synthetic */ ScriptingEnvironment$9 this$2 = this$2;
                            
                            public void run() {
                                try {
                                    urlResponseHandler.getURLDone(true, openConnection.getContentType(), sb.toString());
                                }
                                catch (Exception ex) {
                                    if (this.this$2.this$1.this$0.userAgent != null) {
                                        this.this$2.this$1.this$0.userAgent.displayError(ex);
                                    }
                                }
                            }
                        });
                    }
                    catch (Exception ex) {
                        if (ex instanceof SecurityException) {
                            this.this$1.this$0.userAgent.displayError(ex);
                        }
                        this.this$1.this$0.updateRunnableQueue.invokeLater(new Runnable() {
                            private final /* synthetic */ ScriptingEnvironment$9 this$2 = this$2;
                            
                            public void run() {
                                try {
                                    urlResponseHandler.getURLDone(false, null, null);
                                }
                                catch (Exception ex) {
                                    if (this.this$2.this$1.this$0.userAgent != null) {
                                        this.this$2.this$1.this$0.userAgent.displayError(ex);
                                    }
                                }
                            }
                        });
                    }
                }
            };
            thread.setPriority(1);
            thread.start();
        }
        
        public void alert(final String s) {
            if (ScriptingEnvironment.this.userAgent != null) {
                ScriptingEnvironment.this.userAgent.showAlert(s);
            }
        }
        
        public boolean confirm(final String s) {
            return ScriptingEnvironment.this.userAgent != null && ScriptingEnvironment.this.userAgent.showConfirm(s);
        }
        
        public String prompt(final String s) {
            if (ScriptingEnvironment.this.userAgent != null) {
                return ScriptingEnvironment.this.userAgent.showPrompt(s);
            }
            return null;
        }
        
        public String prompt(final String s, final String s2) {
            if (ScriptingEnvironment.this.userAgent != null) {
                return ScriptingEnvironment.this.userAgent.showPrompt(s, s2);
            }
            return null;
        }
        
        public BridgeContext getBridgeContext() {
            return ScriptingEnvironment.this.bridgeContext;
        }
        
        public Interpreter getInterpreter() {
            return this.interpreter;
        }
    }
    
    protected class EvaluateRunnableRunnable implements Runnable
    {
        public int count;
        public boolean error;
        protected Runnable runnable;
        
        public EvaluateRunnableRunnable(final Runnable runnable) {
            this.runnable = runnable;
        }
        
        public void run() {
            synchronized (this) {
                if (this.error) {
                    return;
                }
                --this.count;
            }
            try {
                this.runnable.run();
            }
            catch (Exception ex) {
                if (ScriptingEnvironment.this.userAgent != null) {
                    ScriptingEnvironment.this.userAgent.displayError(ex);
                }
                else {
                    ex.printStackTrace();
                }
                synchronized (this) {
                    this.error = true;
                }
            }
        }
    }
    
    protected class EvaluateIntervalRunnable implements Runnable
    {
        public int count;
        public boolean error;
        protected Interpreter interpreter;
        protected String script;
        
        public EvaluateIntervalRunnable(final String script, final Interpreter interpreter) {
            this.interpreter = interpreter;
            this.script = script;
        }
        
        public void run() {
            synchronized (this) {
                if (this.error) {
                    return;
                }
                --this.count;
            }
            try {
                this.interpreter.evaluate(this.script);
            }
            catch (InterpreterException ex) {
                ScriptingEnvironment.this.handleInterpreterException(ex);
                synchronized (this) {
                    this.error = true;
                }
            }
            catch (Exception ex2) {
                if (ScriptingEnvironment.this.userAgent != null) {
                    ScriptingEnvironment.this.userAgent.displayError(ex2);
                }
                else {
                    ex2.printStackTrace();
                }
                synchronized (this) {
                    this.error = true;
                }
            }
        }
    }
    
    protected class EvaluateRunnable implements Runnable
    {
        protected Interpreter interpreter;
        protected String script;
        
        public EvaluateRunnable(final String script, final Interpreter interpreter) {
            this.interpreter = interpreter;
            this.script = script;
        }
        
        public void run() {
            try {
                this.interpreter.evaluate(this.script);
            }
            catch (InterpreterException ex) {
                ScriptingEnvironment.this.handleInterpreterException(ex);
            }
        }
    }
}
