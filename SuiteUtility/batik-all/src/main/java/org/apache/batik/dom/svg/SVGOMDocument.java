// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.dom.svg;

import org.w3c.dom.events.MutationEvent;
import org.apache.batik.dom.events.EventSupport;
import org.w3c.dom.events.Event;
import java.io.IOException;
import java.io.ObjectInputStream;
import org.w3c.dom.css.CSSStyleDeclaration;
import java.util.Iterator;
import org.apache.batik.css.engine.CSSStylableElement;
import org.w3c.dom.events.EventListener;
import org.apache.batik.css.engine.CSSNavigableDocumentListener;
import org.apache.batik.dom.GenericAttrNS;
import org.apache.batik.dom.GenericEntityReference;
import org.w3c.dom.EntityReference;
import org.apache.batik.dom.GenericAttr;
import org.w3c.dom.Attr;
import org.apache.batik.dom.GenericProcessingInstruction;
import org.apache.batik.dom.StyleSheetFactory;
import org.w3c.dom.ProcessingInstruction;
import org.apache.batik.dom.GenericCDATASection;
import org.w3c.dom.CDATASection;
import org.apache.batik.dom.GenericComment;
import org.w3c.dom.Comment;
import org.apache.batik.dom.GenericText;
import org.w3c.dom.Text;
import org.apache.batik.dom.GenericDocumentFragment;
import org.w3c.dom.DocumentFragment;
import org.w3c.dom.DOMException;
import org.apache.batik.dom.AbstractDocument;
import org.apache.batik.dom.GenericElement;
import org.w3c.dom.Element;
import java.net.MalformedURLException;
import java.net.URL;
import org.w3c.dom.svg.SVGSVGElement;
import org.w3c.dom.Node;
import org.apache.batik.dom.util.XMLSupport;
import org.w3c.dom.svg.SVGLangSpace;
import java.util.MissingResourceException;
import java.util.Locale;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.DocumentType;
import java.util.LinkedList;
import java.util.HashMap;
import org.apache.batik.util.ParsedURL;
import org.apache.batik.i18n.LocalizableSupport;
import org.apache.batik.css.engine.CSSNavigableDocument;
import org.apache.batik.util.SVGConstants;
import org.w3c.dom.svg.SVGDocument;
import org.apache.batik.dom.AbstractStylableDocument;

public class SVGOMDocument extends AbstractStylableDocument implements SVGDocument, SVGConstants, CSSNavigableDocument, IdContainer
{
    protected static final String RESOURCES = "org.apache.batik.dom.svg.resources.Messages";
    protected transient LocalizableSupport localizableSupport;
    protected String referrer;
    protected ParsedURL url;
    protected transient boolean readonly;
    protected boolean isSVG12;
    protected HashMap cssNavigableDocumentListeners;
    protected AnimatedAttributeListener mainAnimatedAttributeListener;
    protected LinkedList animatedAttributeListeners;
    protected transient SVGContext svgContext;
    
    protected SVGOMDocument() {
        this.localizableSupport = new LocalizableSupport("org.apache.batik.dom.svg.resources.Messages", this.getClass().getClassLoader());
        this.referrer = "";
        this.cssNavigableDocumentListeners = new HashMap();
        this.mainAnimatedAttributeListener = new AnimAttrListener();
        this.animatedAttributeListeners = new LinkedList();
    }
    
    public SVGOMDocument(final DocumentType documentType, final DOMImplementation domImplementation) {
        super(documentType, domImplementation);
        this.localizableSupport = new LocalizableSupport("org.apache.batik.dom.svg.resources.Messages", this.getClass().getClassLoader());
        this.referrer = "";
        this.cssNavigableDocumentListeners = new HashMap();
        this.mainAnimatedAttributeListener = new AnimAttrListener();
        this.animatedAttributeListeners = new LinkedList();
    }
    
    public void setLocale(final Locale locale) {
        super.setLocale(locale);
        this.localizableSupport.setLocale(locale);
    }
    
    public String formatMessage(final String s, final Object[] array) throws MissingResourceException {
        try {
            return super.formatMessage(s, array);
        }
        catch (MissingResourceException ex) {
            return this.localizableSupport.formatMessage(s, array);
        }
    }
    
    public String getTitle() {
        final StringBuffer sb = new StringBuffer();
        boolean equals = false;
        for (Node node = this.getDocumentElement().getFirstChild(); node != null; node = node.getNextSibling()) {
            final String namespaceURI = node.getNamespaceURI();
            if (namespaceURI != null && namespaceURI.equals("http://www.w3.org/2000/svg") && node.getLocalName().equals("title")) {
                equals = ((SVGLangSpace)node).getXMLspace().equals("preserve");
                for (Node node2 = node.getFirstChild(); node2 != null; node2 = node2.getNextSibling()) {
                    if (node2.getNodeType() == 3) {
                        sb.append(node2.getNodeValue());
                    }
                }
                break;
            }
        }
        final String string = sb.toString();
        return equals ? XMLSupport.preserveXMLSpace(string) : XMLSupport.defaultXMLSpace(string);
    }
    
    public String getReferrer() {
        return this.referrer;
    }
    
    public void setReferrer(final String referrer) {
        this.referrer = referrer;
    }
    
    public String getDomain() {
        return (this.url == null) ? null : this.url.getHost();
    }
    
    public SVGSVGElement getRootElement() {
        return (SVGSVGElement)this.getDocumentElement();
    }
    
    public String getURL() {
        return this.documentURI;
    }
    
    public URL getURLObject() {
        try {
            return new URL(this.documentURI);
        }
        catch (MalformedURLException ex) {
            return null;
        }
    }
    
    public ParsedURL getParsedURL() {
        return this.url;
    }
    
    public void setURLObject(final URL url) {
        this.setParsedURL(new ParsedURL(url));
    }
    
    public void setParsedURL(final ParsedURL url) {
        this.url = url;
        this.documentURI = ((url == null) ? null : url.toString());
    }
    
    public void setDocumentURI(final String documentURI) {
        this.documentURI = documentURI;
        this.url = ((documentURI == null) ? null : new ParsedURL(documentURI));
    }
    
    public Element createElement(final String s) throws DOMException {
        return new GenericElement(s.intern(), this);
    }
    
    public DocumentFragment createDocumentFragment() {
        return new GenericDocumentFragment(this);
    }
    
    public Text createTextNode(final String s) {
        return new GenericText(s, this);
    }
    
    public Comment createComment(final String s) {
        return new GenericComment(s, this);
    }
    
    public CDATASection createCDATASection(final String s) throws DOMException {
        return new GenericCDATASection(s, this);
    }
    
    public ProcessingInstruction createProcessingInstruction(final String anObject, final String s) throws DOMException {
        if ("xml-stylesheet".equals(anObject)) {
            return new SVGStyleSheetProcessingInstruction(s, this, (StyleSheetFactory)this.getImplementation());
        }
        return new GenericProcessingInstruction(anObject, s, this);
    }
    
    public Attr createAttribute(final String s) throws DOMException {
        return new GenericAttr(s.intern(), this);
    }
    
    public EntityReference createEntityReference(final String s) throws DOMException {
        return new GenericEntityReference(s, this);
    }
    
    public Attr createAttributeNS(final String s, final String s2) throws DOMException {
        if (s == null) {
            return new GenericAttr(s2.intern(), this);
        }
        return new GenericAttrNS(s.intern(), s2.intern(), this);
    }
    
    public Element createElementNS(final String s, final String s2) throws DOMException {
        return ((SVGDOMImplementation)this.implementation).createElementNS(this, s, s2);
    }
    
    public boolean isSVG12() {
        return this.isSVG12;
    }
    
    public void setIsSVG12(final boolean isSVG12) {
        this.isSVG12 = isSVG12;
    }
    
    public boolean isId(final Attr attr) {
        if (attr.getNamespaceURI() == null) {
            return "id".equals(attr.getNodeName());
        }
        return attr.getNodeName().equals("xml:id");
    }
    
    public void setSVGContext(final SVGContext svgContext) {
        this.svgContext = svgContext;
    }
    
    public SVGContext getSVGContext() {
        return this.svgContext;
    }
    
    public void addCSSNavigableDocumentListener(final CSSNavigableDocumentListener cssNavigableDocumentListener) {
        if (this.cssNavigableDocumentListeners.containsKey(cssNavigableDocumentListener)) {
            return;
        }
        final DOMNodeInsertedListenerWrapper domNodeInsertedListenerWrapper = new DOMNodeInsertedListenerWrapper(cssNavigableDocumentListener);
        final DOMNodeRemovedListenerWrapper domNodeRemovedListenerWrapper = new DOMNodeRemovedListenerWrapper(cssNavigableDocumentListener);
        final DOMSubtreeModifiedListenerWrapper domSubtreeModifiedListenerWrapper = new DOMSubtreeModifiedListenerWrapper(cssNavigableDocumentListener);
        final DOMCharacterDataModifiedListenerWrapper domCharacterDataModifiedListenerWrapper = new DOMCharacterDataModifiedListenerWrapper(cssNavigableDocumentListener);
        final DOMAttrModifiedListenerWrapper domAttrModifiedListenerWrapper = new DOMAttrModifiedListenerWrapper(cssNavigableDocumentListener);
        this.cssNavigableDocumentListeners.put(cssNavigableDocumentListener, new EventListener[] { domNodeInsertedListenerWrapper, domNodeRemovedListenerWrapper, domSubtreeModifiedListenerWrapper, domCharacterDataModifiedListenerWrapper, domAttrModifiedListenerWrapper });
        this.addEventListenerNS("http://www.w3.org/2001/xml-events", "DOMNodeInserted", domNodeInsertedListenerWrapper, false, null);
        this.addEventListenerNS("http://www.w3.org/2001/xml-events", "DOMNodeRemoved", domNodeRemovedListenerWrapper, false, null);
        this.addEventListenerNS("http://www.w3.org/2001/xml-events", "DOMSubtreeModified", domSubtreeModifiedListenerWrapper, false, null);
        this.addEventListenerNS("http://www.w3.org/2001/xml-events", "DOMCharacterDataModified", domCharacterDataModifiedListenerWrapper, false, null);
        this.addEventListenerNS("http://www.w3.org/2001/xml-events", "DOMAttrModified", domAttrModifiedListenerWrapper, false, null);
    }
    
    public void removeCSSNavigableDocumentListener(final CSSNavigableDocumentListener cssNavigableDocumentListener) {
        final EventListener[] array = this.cssNavigableDocumentListeners.get(cssNavigableDocumentListener);
        if (array == null) {
            return;
        }
        this.removeEventListenerNS("http://www.w3.org/2001/xml-events", "DOMNodeInserted", array[0], false);
        this.removeEventListenerNS("http://www.w3.org/2001/xml-events", "DOMNodeRemoved", array[1], false);
        this.removeEventListenerNS("http://www.w3.org/2001/xml-events", "DOMSubtreeModified", array[2], false);
        this.removeEventListenerNS("http://www.w3.org/2001/xml-events", "DOMCharacterDataModified", array[3], false);
        this.removeEventListenerNS("http://www.w3.org/2001/xml-events", "DOMAttrModified", array[4], false);
        this.cssNavigableDocumentListeners.remove(cssNavigableDocumentListener);
    }
    
    protected AnimatedAttributeListener getAnimatedAttributeListener() {
        return this.mainAnimatedAttributeListener;
    }
    
    protected void overrideStyleTextChanged(final CSSStylableElement cssStylableElement, final String s) {
        final Iterator<CSSNavigableDocumentListener> iterator = this.cssNavigableDocumentListeners.keySet().iterator();
        while (iterator.hasNext()) {
            iterator.next().overrideStyleTextChanged(cssStylableElement, s);
        }
    }
    
    protected void overrideStylePropertyRemoved(final CSSStylableElement cssStylableElement, final String s) {
        final Iterator<CSSNavigableDocumentListener> iterator = this.cssNavigableDocumentListeners.keySet().iterator();
        while (iterator.hasNext()) {
            iterator.next().overrideStylePropertyRemoved(cssStylableElement, s);
        }
    }
    
    protected void overrideStylePropertyChanged(final CSSStylableElement cssStylableElement, final String s, final String s2, final String s3) {
        final Iterator<CSSNavigableDocumentListener> iterator = this.cssNavigableDocumentListeners.keySet().iterator();
        while (iterator.hasNext()) {
            iterator.next().overrideStylePropertyChanged(cssStylableElement, s, s2, s3);
        }
    }
    
    public void addAnimatedAttributeListener(final AnimatedAttributeListener animatedAttributeListener) {
        if (this.animatedAttributeListeners.contains(animatedAttributeListener)) {
            return;
        }
        this.animatedAttributeListeners.add(animatedAttributeListener);
    }
    
    public void removeAnimatedAttributeListener(final AnimatedAttributeListener o) {
        this.animatedAttributeListeners.remove(o);
    }
    
    public CSSStyleDeclaration getOverrideStyle(final Element element, final String s) {
        if (element instanceof SVGStylableElement && s == null) {
            return ((SVGStylableElement)element).getOverrideStyle();
        }
        return null;
    }
    
    public boolean isReadonly() {
        return this.readonly;
    }
    
    public void setReadonly(final boolean readonly) {
        this.readonly = readonly;
    }
    
    protected Node newNode() {
        return new SVGOMDocument();
    }
    
    protected Node copyInto(final Node node) {
        super.copyInto(node);
        final SVGOMDocument svgomDocument = (SVGOMDocument)node;
        svgomDocument.localizableSupport = new LocalizableSupport("org.apache.batik.dom.svg.resources.Messages", this.getClass().getClassLoader());
        svgomDocument.referrer = this.referrer;
        svgomDocument.url = this.url;
        return node;
    }
    
    protected Node deepCopyInto(final Node node) {
        super.deepCopyInto(node);
        final SVGOMDocument svgomDocument = (SVGOMDocument)node;
        svgomDocument.localizableSupport = new LocalizableSupport("org.apache.batik.dom.svg.resources.Messages", this.getClass().getClassLoader());
        svgomDocument.referrer = this.referrer;
        svgomDocument.url = this.url;
        return node;
    }
    
    private void readObject(final ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        this.localizableSupport = new LocalizableSupport("org.apache.batik.dom.svg.resources.Messages", this.getClass().getClassLoader());
    }
    
    protected class AnimAttrListener implements AnimatedAttributeListener
    {
        public void animatedAttributeChanged(final Element element, final AnimatedLiveAttributeValue animatedLiveAttributeValue) {
            final Iterator iterator = SVGOMDocument.this.animatedAttributeListeners.iterator();
            while (iterator.hasNext()) {
                iterator.next().animatedAttributeChanged(element, animatedLiveAttributeValue);
            }
        }
        
        public void otherAnimationChanged(final Element element, final String s) {
            final Iterator iterator = SVGOMDocument.this.animatedAttributeListeners.iterator();
            while (iterator.hasNext()) {
                iterator.next().otherAnimationChanged(element, s);
            }
        }
    }
    
    protected class DOMAttrModifiedListenerWrapper implements EventListener
    {
        protected CSSNavigableDocumentListener listener;
        
        public DOMAttrModifiedListenerWrapper(final CSSNavigableDocumentListener listener) {
            this.listener = listener;
        }
        
        public void handleEvent(Event ultimateOriginalEvent) {
            ultimateOriginalEvent = EventSupport.getUltimateOriginalEvent(ultimateOriginalEvent);
            final MutationEvent mutationEvent = (MutationEvent)ultimateOriginalEvent;
            this.listener.attrModified((Element)ultimateOriginalEvent.getTarget(), (Attr)mutationEvent.getRelatedNode(), mutationEvent.getAttrChange(), mutationEvent.getPrevValue(), mutationEvent.getNewValue());
        }
    }
    
    protected class DOMCharacterDataModifiedListenerWrapper implements EventListener
    {
        protected CSSNavigableDocumentListener listener;
        
        public DOMCharacterDataModifiedListenerWrapper(final CSSNavigableDocumentListener listener) {
            this.listener = listener;
        }
        
        public void handleEvent(Event ultimateOriginalEvent) {
            ultimateOriginalEvent = EventSupport.getUltimateOriginalEvent(ultimateOriginalEvent);
            this.listener.subtreeModified((Node)ultimateOriginalEvent.getTarget());
        }
    }
    
    protected class DOMSubtreeModifiedListenerWrapper implements EventListener
    {
        protected CSSNavigableDocumentListener listener;
        
        public DOMSubtreeModifiedListenerWrapper(final CSSNavigableDocumentListener listener) {
            this.listener = listener;
        }
        
        public void handleEvent(Event ultimateOriginalEvent) {
            ultimateOriginalEvent = EventSupport.getUltimateOriginalEvent(ultimateOriginalEvent);
            this.listener.subtreeModified((Node)ultimateOriginalEvent.getTarget());
        }
    }
    
    protected class DOMNodeRemovedListenerWrapper implements EventListener
    {
        protected CSSNavigableDocumentListener listener;
        
        public DOMNodeRemovedListenerWrapper(final CSSNavigableDocumentListener listener) {
            this.listener = listener;
        }
        
        public void handleEvent(Event ultimateOriginalEvent) {
            ultimateOriginalEvent = EventSupport.getUltimateOriginalEvent(ultimateOriginalEvent);
            this.listener.nodeToBeRemoved((Node)ultimateOriginalEvent.getTarget());
        }
    }
    
    protected class DOMNodeInsertedListenerWrapper implements EventListener
    {
        protected CSSNavigableDocumentListener listener;
        
        public DOMNodeInsertedListenerWrapper(final CSSNavigableDocumentListener listener) {
            this.listener = listener;
        }
        
        public void handleEvent(Event ultimateOriginalEvent) {
            ultimateOriginalEvent = EventSupport.getUltimateOriginalEvent(ultimateOriginalEvent);
            this.listener.nodeInserted((Node)ultimateOriginalEvent.getTarget());
        }
    }
}
