// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.bridge;

import org.apache.batik.dom.util.DocumentDescriptor;
import org.apache.batik.util.CleanerThread;
import org.w3c.dom.svg.SVGDocument;
import org.w3c.dom.Element;
import java.io.InputStream;
import java.io.IOException;
import org.w3c.dom.Document;
import org.apache.batik.dom.svg.SAXSVGDocumentFactory;
import java.util.HashMap;
import org.apache.batik.dom.svg.SVGDocumentFactory;

public class DocumentLoader
{
    protected SVGDocumentFactory documentFactory;
    protected HashMap cacheMap;
    protected UserAgent userAgent;
    
    protected DocumentLoader() {
        this.cacheMap = new HashMap();
    }
    
    public DocumentLoader(final UserAgent userAgent) {
        this.cacheMap = new HashMap();
        this.userAgent = userAgent;
        (this.documentFactory = new SAXSVGDocumentFactory(userAgent.getXMLParserClassName(), true)).setValidating(userAgent.isXMLParserValidating());
    }
    
    public Document checkCache(String substring) {
        int lastIndex = substring.lastIndexOf(47);
        if (lastIndex == -1) {
            lastIndex = 0;
        }
        final int index = substring.indexOf(35, lastIndex);
        if (index != -1) {
            substring = substring.substring(0, index);
        }
        final DocumentState documentState;
        synchronized (this.cacheMap) {
            documentState = this.cacheMap.get(substring);
        }
        if (documentState != null) {
            return documentState.getDocument();
        }
        return null;
    }
    
    public Document loadDocument(final String key) throws IOException {
        final Document checkCache = this.checkCache(key);
        if (checkCache != null) {
            return checkCache;
        }
        final DocumentState value = new DocumentState(key, (Document)this.documentFactory.createSVGDocument(key), this.documentFactory.getDocumentDescriptor());
        synchronized (this.cacheMap) {
            this.cacheMap.put(key, value);
        }
        return value.getDocument();
    }
    
    public Document loadDocument(final String key, final InputStream inputStream) throws IOException {
        final Document checkCache = this.checkCache(key);
        if (checkCache != null) {
            return checkCache;
        }
        final DocumentState value = new DocumentState(key, (Document)this.documentFactory.createSVGDocument(key, inputStream), this.documentFactory.getDocumentDescriptor());
        synchronized (this.cacheMap) {
            this.cacheMap.put(key, value);
        }
        return value.getDocument();
    }
    
    public UserAgent getUserAgent() {
        return this.userAgent;
    }
    
    public void dispose() {
        synchronized (this.cacheMap) {
            this.cacheMap.clear();
        }
    }
    
    public int getLineNumber(final Element element) {
        final String url = ((SVGDocument)element.getOwnerDocument()).getURL();
        final DocumentState documentState;
        synchronized (this.cacheMap) {
            documentState = this.cacheMap.get(url);
        }
        if (documentState == null) {
            return -1;
        }
        return documentState.desc.getLocationLine(element);
    }
    
    private class DocumentState extends CleanerThread.SoftReferenceCleared
    {
        private String uri;
        private DocumentDescriptor desc;
        
        public DocumentState(final String uri, final Document document, final DocumentDescriptor desc) {
            super(document);
            this.uri = uri;
            this.desc = desc;
        }
        
        public void cleared() {
            synchronized (DocumentLoader.this.cacheMap) {
                DocumentLoader.this.cacheMap.remove(this.uri);
            }
        }
        
        public DocumentDescriptor getDocumentDescriptor() {
            return this.desc;
        }
        
        public String getURI() {
            return this.uri;
        }
        
        public Document getDocument() {
            return this.get();
        }
    }
}
