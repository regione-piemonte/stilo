// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.bridge;

import org.w3c.dom.events.EventTarget;
import org.apache.batik.script.ScriptEventWrapper;
import org.w3c.dom.events.EventListener;
import org.w3c.dom.events.Event;
import org.apache.batik.dom.events.NodeEventTarget;
import org.w3c.dom.events.DocumentEvent;
import org.apache.batik.dom.events.AbstractEvent;
import java.io.Reader;
import org.w3c.dom.NodeList;
import org.apache.batik.script.InterpreterException;
import java.io.IOException;
import java.io.StringReader;
import java.io.InputStream;
import java.io.PushbackInputStream;
import java.io.UnsupportedEncodingException;
import java.io.InputStreamReader;
import org.w3c.dom.svg.EventListenerInitializer;
import org.apache.batik.script.ScriptHandler;
import java.util.jar.Manifest;
import java.net.MalformedURLException;
import java.net.URL;
import org.apache.batik.dom.util.XLinkSupport;
import org.apache.batik.dom.AbstractElement;
import org.w3c.dom.svg.SVGSVGElement;
import org.apache.batik.script.Window;
import org.w3c.dom.svg.SVGDocument;
import java.util.HashSet;
import org.w3c.dom.Node;
import java.util.Iterator;
import java.util.List;
import org.w3c.dom.Element;
import org.apache.batik.script.Interpreter;
import java.util.Set;
import org.apache.batik.util.ParsedURL;
import org.w3c.dom.Document;

public class BaseScriptingEnvironment
{
    public static final String INLINE_SCRIPT_DESCRIPTION = "BaseScriptingEnvironment.constant.inline.script.description";
    public static final String EVENT_SCRIPT_DESCRIPTION = "BaseScriptingEnvironment.constant.event.script.description";
    protected static final String EVENT_NAME = "event";
    protected static final String ALTERNATE_EVENT_NAME = "evt";
    protected static final String APPLICATION_ECMASCRIPT = "application/ecmascript";
    protected BridgeContext bridgeContext;
    protected UserAgent userAgent;
    protected Document document;
    protected ParsedURL docPURL;
    protected Set languages;
    protected Interpreter interpreter;
    
    public static boolean isDynamicDocument(final BridgeContext bridgeContext, final Document document) {
        final Element documentElement = document.getDocumentElement();
        return documentElement != null && "http://www.w3.org/2000/svg".equals(documentElement.getNamespaceURI()) && (documentElement.getAttributeNS(null, "onabort").length() > 0 || documentElement.getAttributeNS(null, "onerror").length() > 0 || documentElement.getAttributeNS(null, "onresize").length() > 0 || documentElement.getAttributeNS(null, "onunload").length() > 0 || documentElement.getAttributeNS(null, "onscroll").length() > 0 || documentElement.getAttributeNS(null, "onzoom").length() > 0 || isDynamicElement(bridgeContext, document.getDocumentElement()));
    }
    
    public static boolean isDynamicElement(final BridgeContext bridgeContext, final Element element) {
        return isDynamicElement(element, bridgeContext, bridgeContext.getBridgeExtensions(element.getOwnerDocument()));
    }
    
    public static boolean isDynamicElement(final Element element, final BridgeContext bridgeContext, final List list) {
        final Iterator<BridgeExtension> iterator = list.iterator();
        while (iterator.hasNext()) {
            if (iterator.next().isDynamicElement(element)) {
                return true;
            }
        }
        if ("http://www.w3.org/2000/svg".equals(element.getNamespaceURI())) {
            if (element.getAttributeNS(null, "onkeyup").length() > 0) {
                return true;
            }
            if (element.getAttributeNS(null, "onkeydown").length() > 0) {
                return true;
            }
            if (element.getAttributeNS(null, "onkeypress").length() > 0) {
                return true;
            }
            if (element.getAttributeNS(null, "onload").length() > 0) {
                return true;
            }
            if (element.getAttributeNS(null, "onerror").length() > 0) {
                return true;
            }
            if (element.getAttributeNS(null, "onactivate").length() > 0) {
                return true;
            }
            if (element.getAttributeNS(null, "onclick").length() > 0) {
                return true;
            }
            if (element.getAttributeNS(null, "onfocusin").length() > 0) {
                return true;
            }
            if (element.getAttributeNS(null, "onfocusout").length() > 0) {
                return true;
            }
            if (element.getAttributeNS(null, "onmousedown").length() > 0) {
                return true;
            }
            if (element.getAttributeNS(null, "onmousemove").length() > 0) {
                return true;
            }
            if (element.getAttributeNS(null, "onmouseout").length() > 0) {
                return true;
            }
            if (element.getAttributeNS(null, "onmouseover").length() > 0) {
                return true;
            }
            if (element.getAttributeNS(null, "onmouseup").length() > 0) {
                return true;
            }
        }
        for (Node node = element.getFirstChild(); node != null; node = node.getNextSibling()) {
            if (node.getNodeType() == 1 && isDynamicElement(bridgeContext, (Element)node)) {
                return true;
            }
        }
        return false;
    }
    
    public BaseScriptingEnvironment(final BridgeContext bridgeContext) {
        this.languages = new HashSet();
        this.bridgeContext = bridgeContext;
        this.document = bridgeContext.getDocument();
        this.docPURL = new ParsedURL(((SVGDocument)this.document).getURL());
        this.userAgent = this.bridgeContext.getUserAgent();
    }
    
    public org.apache.batik.script.Window createWindow(final Interpreter interpreter, final String s) {
        return new Window(interpreter, s);
    }
    
    public org.apache.batik.script.Window createWindow() {
        return this.createWindow(null, null);
    }
    
    public Interpreter getInterpreter() {
        if (this.interpreter != null) {
            return this.interpreter;
        }
        return this.getInterpreter(((SVGSVGElement)this.document.getDocumentElement()).getContentScriptType());
    }
    
    public Interpreter getInterpreter(final String s) {
        this.interpreter = this.bridgeContext.getInterpreter(s);
        if (this.interpreter != null) {
            if (!this.languages.contains(s)) {
                this.languages.add(s);
                this.initializeEnvironment(this.interpreter, s);
            }
            return this.interpreter;
        }
        if (this.languages.contains(s)) {
            return null;
        }
        this.languages.add(s);
        return null;
    }
    
    public void initializeEnvironment(final Interpreter interpreter, final String s) {
        interpreter.bindObject("window", this.createWindow(interpreter, s));
    }
    
    public void loadScripts() {
        org.apache.batik.script.Window window = null;
        final NodeList elementsByTagNameNS = this.document.getElementsByTagNameNS("http://www.w3.org/2000/svg", "script");
        final int length = elementsByTagNameNS.getLength();
        if (length == 0) {
            return;
        }
        for (int i = 0; i < length; ++i) {
            final AbstractElement abstractElement = (AbstractElement)elementsByTagNameNS.item(i);
            String attributeNS = abstractElement.getAttributeNS(null, "type");
            if (attributeNS.length() == 0) {
                attributeNS = "text/ecmascript";
            }
            if (attributeNS.equals("application/java-archive")) {
                try {
                    final ParsedURL parsedURL = new ParsedURL(abstractElement.getBaseURI(), XLinkSupport.getXLinkHref(abstractElement));
                    this.checkCompatibleScriptURL(attributeNS, parsedURL);
                    URL url = null;
                    try {
                        url = new URL(this.docPURL.toString());
                    }
                    catch (MalformedURLException ex4) {}
                    final DocumentJarClassLoader documentJarClassLoader = new DocumentJarClassLoader(new URL(parsedURL.toString()), url);
                    final URL resource = documentJarClassLoader.findResource("META-INF/MANIFEST.MF");
                    if (resource != null) {
                        final Manifest manifest = new Manifest(resource.openStream());
                        final String value = manifest.getMainAttributes().getValue("Script-Handler");
                        if (value != null) {
                            final ScriptHandler scriptHandler = (ScriptHandler)documentJarClassLoader.loadClass(value).newInstance();
                            if (window == null) {
                                window = this.createWindow();
                            }
                            scriptHandler.run(this.document, window);
                        }
                        final String value2 = manifest.getMainAttributes().getValue("SVG-Handler-Class");
                        if (value2 != null) {
                            final EventListenerInitializer eventListenerInitializer = (EventListenerInitializer)documentJarClassLoader.loadClass(value2).newInstance();
                            if (window == null) {
                                window = this.createWindow();
                            }
                            eventListenerInitializer.initializeEventListeners((SVGDocument)this.document);
                        }
                    }
                }
                catch (Exception ex) {
                    if (this.userAgent != null) {
                        this.userAgent.displayError(ex);
                    }
                }
            }
            else {
                final Interpreter interpreter = this.getInterpreter(attributeNS);
                if (interpreter != null) {
                    try {
                        final String xLinkHref = XLinkSupport.getXLinkHref(abstractElement);
                        Reader reader = null;
                        String formatMessage;
                        if (xLinkHref.length() > 0) {
                            formatMessage = xLinkHref;
                            final ParsedURL parsedURL2 = new ParsedURL(abstractElement.getBaseURI(), xLinkHref);
                            this.checkCompatibleScriptURL(attributeNS, parsedURL2);
                            final InputStream openStream = parsedURL2.openStream();
                            final String contentTypeMediaType = parsedURL2.getContentTypeMediaType();
                            String contentTypeCharset = parsedURL2.getContentTypeCharset();
                            if (contentTypeCharset != null) {
                                try {
                                    reader = new InputStreamReader(openStream, contentTypeCharset);
                                }
                                catch (UnsupportedEncodingException ex5) {
                                    contentTypeCharset = null;
                                }
                            }
                            if (reader == null) {
                                if ("application/ecmascript".equals(contentTypeMediaType)) {
                                    if (parsedURL2.hasContentTypeParameter("version")) {
                                        continue;
                                    }
                                    final PushbackInputStream in = new PushbackInputStream(openStream, 8);
                                    final byte[] array = new byte[4];
                                    final int read = in.read(array);
                                    if (read > 0) {
                                        in.unread(array, 0, read);
                                        if (read >= 2) {
                                            if (array[0] == -1 && array[1] == -2) {
                                                if (read >= 4 && array[2] == 0 && array[3] == 0) {
                                                    contentTypeCharset = "UTF32-LE";
                                                    in.skip(4L);
                                                }
                                                else {
                                                    contentTypeCharset = "UTF-16LE";
                                                    in.skip(2L);
                                                }
                                            }
                                            else if (array[0] == -2 && array[1] == -1) {
                                                contentTypeCharset = "UTF-16BE";
                                                in.skip(2L);
                                            }
                                            else if (read >= 3 && array[0] == -17 && array[1] == -69 && array[2] == -65) {
                                                contentTypeCharset = "UTF-8";
                                                in.skip(3L);
                                            }
                                            else if (read >= 4 && array[0] == 0 && array[1] == 0 && array[2] == -2 && array[3] == -1) {
                                                contentTypeCharset = "UTF-32BE";
                                                in.skip(4L);
                                            }
                                        }
                                        if (contentTypeCharset == null) {
                                            contentTypeCharset = "UTF-8";
                                        }
                                    }
                                    reader = new InputStreamReader(in, contentTypeCharset);
                                }
                                else {
                                    reader = new InputStreamReader(openStream);
                                }
                            }
                        }
                        else {
                            this.checkCompatibleScriptURL(attributeNS, this.docPURL);
                            formatMessage = Messages.formatMessage("BaseScriptingEnvironment.constant.inline.script.description", new Object[] { ((SVGDocument)abstractElement.getOwnerDocument()).getURL(), "<" + abstractElement.getNodeName() + ">", new Integer(this.bridgeContext.getDocumentLoader().getLineNumber(abstractElement)) });
                            Node node = abstractElement.getFirstChild();
                            if (node == null) {
                                continue;
                            }
                            final StringBuffer sb = new StringBuffer();
                            while (node != null) {
                                if (node.getNodeType() == 4 || node.getNodeType() == 3) {
                                    sb.append(node.getNodeValue());
                                }
                                node = node.getNextSibling();
                            }
                            reader = new StringReader(sb.toString());
                        }
                        interpreter.evaluate(reader, formatMessage);
                    }
                    catch (IOException ex2) {
                        if (this.userAgent != null) {
                            this.userAgent.displayError(ex2);
                        }
                        return;
                    }
                    catch (InterpreterException obj) {
                        System.err.println("InterpExcept: " + obj);
                        this.handleInterpreterException(obj);
                        return;
                    }
                    catch (SecurityException ex3) {
                        if (this.userAgent != null) {
                            this.userAgent.displayError(ex3);
                        }
                    }
                }
            }
        }
    }
    
    protected void checkCompatibleScriptURL(final String s, final ParsedURL parsedURL) {
        this.userAgent.checkLoadScript(s, parsedURL, this.docPURL);
    }
    
    public void dispatchSVGLoadEvent() {
        final SVGSVGElement svgsvgElement = (SVGSVGElement)this.document.getDocumentElement();
        final String contentScriptType = svgsvgElement.getContentScriptType();
        this.bridgeContext.getAnimationEngine().start(System.currentTimeMillis());
        this.dispatchSVGLoad((Element)svgsvgElement, true, contentScriptType);
    }
    
    protected void dispatchSVGLoad(final Element element, final boolean b, final String s) {
        for (Node node = element.getFirstChild(); node != null; node = node.getNextSibling()) {
            if (node.getNodeType() == 1) {
                this.dispatchSVGLoad((Element)node, b, s);
            }
        }
        final AbstractEvent abstractEvent = (AbstractEvent)((DocumentEvent)element.getOwnerDocument()).createEvent("SVGEvents");
        String s2;
        if (this.bridgeContext.isSVG12()) {
            s2 = "load";
        }
        else {
            s2 = "SVGLoad";
        }
        abstractEvent.initEventNS("http://www.w3.org/2001/xml-events", s2, false, false);
        final NodeEventTarget nodeEventTarget = (NodeEventTarget)element;
        final String attributeNS = element.getAttributeNS(null, "onload");
        if (attributeNS.length() == 0) {
            nodeEventTarget.dispatchEvent(abstractEvent);
            return;
        }
        final Interpreter interpreter = this.getInterpreter();
        if (interpreter == null) {
            nodeEventTarget.dispatchEvent(abstractEvent);
            return;
        }
        if (b) {
            this.checkCompatibleScriptURL(s, this.docPURL);
        }
        final EventListener eventListener = new EventListener() {
            private final /* synthetic */ String val$desc = Messages.formatMessage("BaseScriptingEnvironment.constant.event.script.description", new Object[] { ((SVGDocument)element.getOwnerDocument()).getURL(), "onload", new Integer(BaseScriptingEnvironment.this.bridgeContext.getDocumentLoader().getLineNumber(element)) });
            
            public void handleEvent(final Event event) {
                try {
                    Object eventObject;
                    if (event instanceof ScriptEventWrapper) {
                        eventObject = ((ScriptEventWrapper)event).getEventObject();
                    }
                    else {
                        eventObject = event;
                    }
                    interpreter.bindObject("event", eventObject);
                    interpreter.bindObject("evt", eventObject);
                    interpreter.evaluate(new StringReader(attributeNS), this.val$desc);
                }
                catch (IOException ex2) {}
                catch (InterpreterException ex) {
                    BaseScriptingEnvironment.this.handleInterpreterException(ex);
                }
            }
        };
        nodeEventTarget.addEventListenerNS("http://www.w3.org/2001/xml-events", s2, eventListener, false, null);
        nodeEventTarget.dispatchEvent(abstractEvent);
        nodeEventTarget.removeEventListenerNS("http://www.w3.org/2001/xml-events", s2, eventListener, false);
    }
    
    protected void dispatchSVGZoomEvent() {
        if (this.bridgeContext.isSVG12()) {
            this.dispatchSVGDocEvent("zoom");
        }
        else {
            this.dispatchSVGDocEvent("SVGZoom");
        }
    }
    
    protected void dispatchSVGScrollEvent() {
        if (this.bridgeContext.isSVG12()) {
            this.dispatchSVGDocEvent("scroll");
        }
        else {
            this.dispatchSVGDocEvent("SVGScroll");
        }
    }
    
    protected void dispatchSVGResizeEvent() {
        if (this.bridgeContext.isSVG12()) {
            this.dispatchSVGDocEvent("resize");
        }
        else {
            this.dispatchSVGDocEvent("SVGResize");
        }
    }
    
    protected void dispatchSVGDocEvent(final String s) {
        final SVGSVGElement svgsvgElement = (SVGSVGElement)this.document.getDocumentElement();
        final AbstractEvent abstractEvent = (AbstractEvent)((DocumentEvent)this.document).createEvent("SVGEvents");
        abstractEvent.initEventNS("http://www.w3.org/2001/xml-events", s, false, false);
        ((EventTarget)svgsvgElement).dispatchEvent(abstractEvent);
    }
    
    protected void handleInterpreterException(final InterpreterException ex) {
        if (this.userAgent != null) {
            final Exception exception = ex.getException();
            this.userAgent.displayError((exception == null) ? ex : exception);
        }
    }
    
    protected void handleSecurityException(final SecurityException ex) {
        if (this.userAgent != null) {
            this.userAgent.displayError(ex);
        }
    }
    
    protected class Window implements org.apache.batik.script.Window
    {
        protected Interpreter interpreter;
        protected String language;
        
        public Window(final Interpreter interpreter, final String language) {
            this.interpreter = interpreter;
            this.language = language;
        }
        
        public Object setInterval(final String s, final long n) {
            return null;
        }
        
        public Object setInterval(final Runnable runnable, final long n) {
            return null;
        }
        
        public void clearInterval(final Object o) {
        }
        
        public Object setTimeout(final String s, final long n) {
            return null;
        }
        
        public Object setTimeout(final Runnable runnable, final long n) {
            return null;
        }
        
        public void clearTimeout(final Object o) {
        }
        
        public Node parseXML(final String s, final Document document) {
            return null;
        }
        
        public void getURL(final String s, final URLResponseHandler urlResponseHandler) {
            this.getURL(s, urlResponseHandler, "UTF8");
        }
        
        public void getURL(final String s, final URLResponseHandler urlResponseHandler, final String s2) {
        }
        
        public void postURL(final String s, final String s2, final URLResponseHandler urlResponseHandler) {
            this.postURL(s, s2, urlResponseHandler, "text/plain", null);
        }
        
        public void postURL(final String s, final String s2, final URLResponseHandler urlResponseHandler, final String s3) {
            this.postURL(s, s2, urlResponseHandler, s3, null);
        }
        
        public void postURL(final String s, final String s2, final URLResponseHandler urlResponseHandler, final String s3, final String s4) {
        }
        
        public void alert(final String s) {
        }
        
        public boolean confirm(final String s) {
            return false;
        }
        
        public String prompt(final String s) {
            return null;
        }
        
        public String prompt(final String s, final String s2) {
            return null;
        }
        
        public BridgeContext getBridgeContext() {
            return BaseScriptingEnvironment.this.bridgeContext;
        }
        
        public Interpreter getInterpreter() {
            return this.interpreter;
        }
    }
}
