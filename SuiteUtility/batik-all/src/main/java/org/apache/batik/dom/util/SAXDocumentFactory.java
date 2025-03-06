// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.dom.util;

import org.w3c.dom.Text;
import java.util.Iterator;
import org.w3c.dom.DocumentType;
import org.xml.sax.SAXNotRecognizedException;
import org.apache.batik.util.HaltingThread;
import org.xml.sax.Attributes;
import java.util.LinkedList;
import org.xml.sax.SAXParseException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.helpers.XMLReaderFactory;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;
import java.io.InterruptedIOException;
import org.xml.sax.EntityResolver;
import org.xml.sax.DTDHandler;
import org.xml.sax.ContentHandler;
import java.io.Reader;
import java.io.InputStream;
import java.io.IOException;
import org.xml.sax.InputSource;
import javax.xml.parsers.SAXParserFactory;
import java.util.List;
import org.xml.sax.ErrorHandler;
import org.xml.sax.Locator;
import org.w3c.dom.Node;
import org.w3c.dom.Document;
import org.xml.sax.XMLReader;
import org.w3c.dom.DOMImplementation;
import org.xml.sax.ext.LexicalHandler;
import org.xml.sax.helpers.DefaultHandler;

public class SAXDocumentFactory extends DefaultHandler implements LexicalHandler, DocumentFactory
{
    protected DOMImplementation implementation;
    protected String parserClassName;
    protected XMLReader parser;
    protected Document document;
    protected DocumentDescriptor documentDescriptor;
    protected boolean createDocumentDescriptor;
    protected Node currentNode;
    protected Locator locator;
    protected StringBuffer stringBuffer;
    protected boolean stringContent;
    protected boolean inDTD;
    protected boolean inCDATA;
    protected boolean inProlog;
    protected boolean isValidating;
    protected boolean isStandalone;
    protected String xmlVersion;
    protected HashTableStack namespaces;
    protected ErrorHandler errorHandler;
    protected List preInfo;
    static SAXParserFactory saxFactory;
    
    public SAXDocumentFactory(final DOMImplementation implementation, final String parserClassName) {
        this.stringBuffer = new StringBuffer();
        this.implementation = implementation;
        this.parserClassName = parserClassName;
    }
    
    public SAXDocumentFactory(final DOMImplementation implementation, final String parserClassName, final boolean createDocumentDescriptor) {
        this.stringBuffer = new StringBuffer();
        this.implementation = implementation;
        this.parserClassName = parserClassName;
        this.createDocumentDescriptor = createDocumentDescriptor;
    }
    
    public Document createDocument(final String s, final String s2, final String systemId) throws IOException {
        return this.createDocument(s, s2, systemId, new InputSource(systemId));
    }
    
    public Document createDocument(final String systemId) throws IOException {
        return this.createDocument(new InputSource(systemId));
    }
    
    public Document createDocument(final String s, final String s2, final String systemId, final InputStream byteStream) throws IOException {
        final InputSource inputSource = new InputSource(byteStream);
        inputSource.setSystemId(systemId);
        return this.createDocument(s, s2, systemId, inputSource);
    }
    
    public Document createDocument(final String systemId, final InputStream byteStream) throws IOException {
        final InputSource inputSource = new InputSource(byteStream);
        inputSource.setSystemId(systemId);
        return this.createDocument(inputSource);
    }
    
    public Document createDocument(final String s, final String s2, final String systemId, final Reader characterStream) throws IOException {
        final InputSource inputSource = new InputSource(characterStream);
        inputSource.setSystemId(systemId);
        return this.createDocument(s, s2, systemId, inputSource);
    }
    
    public Document createDocument(final String s, final String s2, final String s3, final XMLReader xmlReader) throws IOException {
        xmlReader.setContentHandler(this);
        xmlReader.setDTDHandler(this);
        xmlReader.setEntityResolver(this);
        try {
            xmlReader.parse(s3);
        }
        catch (SAXException ex) {
            final Exception exception = ex.getException();
            if (exception != null && exception instanceof InterruptedIOException) {
                throw (InterruptedIOException)exception;
            }
            throw new SAXIOException(ex);
        }
        this.currentNode = null;
        final Document document = this.document;
        this.document = null;
        return document;
    }
    
    public Document createDocument(final String systemId, final Reader characterStream) throws IOException {
        final InputSource inputSource = new InputSource(characterStream);
        inputSource.setSystemId(systemId);
        return this.createDocument(inputSource);
    }
    
    protected Document createDocument(final String s, final String s2, final String s3, final InputSource inputSource) throws IOException {
        final Document document = this.createDocument(inputSource);
        final Element documentElement = document.getDocumentElement();
        String substring = s2;
        String value = s;
        if (s == null) {
            final int index = substring.indexOf(58);
            value = this.namespaces.get((index == -1 || index == substring.length() - 1) ? "" : substring.substring(0, index));
            if (index != -1 && index != substring.length() - 1) {
                substring = substring.substring(index + 1);
            }
        }
        final String namespaceURI = documentElement.getNamespaceURI();
        if (namespaceURI != value && (namespaceURI == null || !namespaceURI.equals(value))) {
            throw new IOException("Root element namespace does not match that requested:\nRequested: " + value + "\n" + "Found: " + namespaceURI);
        }
        if (namespaceURI != null) {
            if (!documentElement.getLocalName().equals(substring)) {
                throw new IOException("Root element does not match that requested:\nRequested: " + substring + "\n" + "Found: " + documentElement.getLocalName());
            }
        }
        else if (!documentElement.getNodeName().equals(substring)) {
            throw new IOException("Root element does not match that requested:\nRequested: " + substring + "\n" + "Found: " + documentElement.getNodeName());
        }
        return document;
    }
    
    protected Document createDocument(final InputSource inputSource) throws IOException {
        try {
            if (this.parserClassName != null) {
                this.parser = XMLReaderFactory.createXMLReader(this.parserClassName);
            }
            else {
                SAXParser saxParser;
                try {
                    saxParser = SAXDocumentFactory.saxFactory.newSAXParser();
                }
                catch (ParserConfigurationException ex) {
                    throw new IOException("Could not create SAXParser: " + ex.getMessage());
                }
                this.parser = saxParser.getXMLReader();
            }
            this.parser.setContentHandler(this);
            this.parser.setDTDHandler(this);
            this.parser.setEntityResolver(this);
            this.parser.setErrorHandler((this.errorHandler == null) ? this : this.errorHandler);
            this.parser.setFeature("http://xml.org/sax/features/namespaces", true);
            this.parser.setFeature("http://xml.org/sax/features/namespace-prefixes", true);
            this.parser.setFeature("http://xml.org/sax/features/validation", this.isValidating);
            this.parser.setProperty("http://xml.org/sax/properties/lexical-handler", this);
            this.parser.parse(inputSource);
        }
        catch (SAXException ex2) {
            final Exception exception = ex2.getException();
            if (exception != null && exception instanceof InterruptedIOException) {
                throw (InterruptedIOException)exception;
            }
            throw new SAXIOException(ex2);
        }
        this.currentNode = null;
        final Document document = this.document;
        this.document = null;
        this.locator = null;
        this.parser = null;
        return document;
    }
    
    public DocumentDescriptor getDocumentDescriptor() {
        return this.documentDescriptor;
    }
    
    public void setDocumentLocator(final Locator locator) {
        this.locator = locator;
    }
    
    public void setValidating(final boolean isValidating) {
        this.isValidating = isValidating;
    }
    
    public boolean isValidating() {
        return this.isValidating;
    }
    
    public void setErrorHandler(final ErrorHandler errorHandler) {
        this.errorHandler = errorHandler;
    }
    
    public DOMImplementation getDOMImplementation(final String s) {
        return this.implementation;
    }
    
    public void fatalError(final SAXParseException ex) throws SAXException {
        throw ex;
    }
    
    public void error(final SAXParseException ex) throws SAXException {
        throw ex;
    }
    
    public void warning(final SAXParseException ex) throws SAXException {
    }
    
    public void startDocument() throws SAXException {
        this.preInfo = new LinkedList();
        (this.namespaces = new HashTableStack()).put("xml", "http://www.w3.org/XML/1998/namespace");
        this.namespaces.put("xmlns", "http://www.w3.org/2000/xmlns/");
        this.namespaces.put("", null);
        this.inDTD = false;
        this.inCDATA = false;
        this.inProlog = true;
        this.currentNode = null;
        this.document = null;
        this.isStandalone = false;
        this.xmlVersion = "1.0";
        this.stringBuffer.setLength(0);
        this.stringContent = false;
        if (this.createDocumentDescriptor) {
            this.documentDescriptor = new DocumentDescriptor();
        }
        else {
            this.documentDescriptor = null;
        }
    }
    
    public void startElement(final String s, final String s2, final String s3, final Attributes attributes) throws SAXException {
        if (HaltingThread.hasBeenHalted()) {
            throw new SAXException(new InterruptedIOException());
        }
        if (this.inProlog) {
            this.inProlog = false;
            try {
                this.isStandalone = this.parser.getFeature("http://xml.org/sax/features/is-standalone");
            }
            catch (SAXNotRecognizedException ex) {}
            try {
                this.xmlVersion = (String)this.parser.getProperty("http://xml.org/sax/properties/document-xml-version");
            }
            catch (SAXNotRecognizedException ex2) {}
        }
        final int length = attributes.getLength();
        this.namespaces.push();
        String value = null;
        for (int i = 0; i < length; ++i) {
            final String qName = attributes.getQName(i);
            final int length2 = qName.length();
            if (length2 >= 5) {
                if (qName.equals("version")) {
                    value = attributes.getValue(i);
                }
                else if (qName.startsWith("xmlns")) {
                    if (length2 == 5) {
                        String value2 = attributes.getValue(i);
                        if (value2.length() == 0) {
                            value2 = null;
                        }
                        this.namespaces.put("", value2);
                    }
                    else if (qName.charAt(5) == ':') {
                        String value3 = attributes.getValue(i);
                        if (value3.length() == 0) {
                            value3 = null;
                        }
                        this.namespaces.put(qName.substring(6), value3);
                    }
                }
            }
        }
        this.appendStringData();
        final int index = s3.indexOf(58);
        final String value4 = this.namespaces.get((index == -1 || index == s3.length() - 1) ? "" : s3.substring(0, index));
        Node elementNS;
        if (this.currentNode == null) {
            this.implementation = this.getDOMImplementation(value);
            this.document = this.implementation.createDocument(value4, s3, null);
            final Iterator<PreInfo> iterator = this.preInfo.iterator();
            elementNS = (this.currentNode = this.document.getDocumentElement());
            while (iterator.hasNext()) {
                this.document.insertBefore(iterator.next().createNode(this.document), elementNS);
            }
            this.preInfo = null;
        }
        else {
            elementNS = this.document.createElementNS(value4, s3);
            this.currentNode.appendChild(elementNS);
            this.currentNode = elementNS;
        }
        if (this.createDocumentDescriptor && this.locator != null) {
            this.documentDescriptor.setLocation((Element)elementNS, this.locator.getLineNumber(), this.locator.getColumnNumber());
        }
        for (int j = 0; j < length; ++j) {
            final String qName2 = attributes.getQName(j);
            if (qName2.equals("xmlns")) {
                ((Element)elementNS).setAttributeNS("http://www.w3.org/2000/xmlns/", qName2, attributes.getValue(j));
            }
            else {
                final int index2 = qName2.indexOf(58);
                ((Element)elementNS).setAttributeNS((index2 == -1) ? null : this.namespaces.get(qName2.substring(0, index2)), qName2, attributes.getValue(j));
            }
        }
    }
    
    public void endElement(final String s, final String s2, final String s3) throws SAXException {
        this.appendStringData();
        if (this.currentNode != null) {
            this.currentNode = this.currentNode.getParentNode();
        }
        this.namespaces.pop();
    }
    
    public void appendStringData() {
        if (!this.stringContent) {
            return;
        }
        final String string = this.stringBuffer.toString();
        this.stringBuffer.setLength(0);
        this.stringContent = false;
        if (this.currentNode == null) {
            if (this.inCDATA) {
                this.preInfo.add(new CDataInfo(string));
            }
            else {
                this.preInfo.add(new TextInfo(string));
            }
        }
        else {
            Text text;
            if (this.inCDATA) {
                text = this.document.createCDATASection(string);
            }
            else {
                text = this.document.createTextNode(string);
            }
            this.currentNode.appendChild(text);
        }
    }
    
    public void characters(final char[] str, final int offset, final int len) throws SAXException {
        this.stringBuffer.append(str, offset, len);
        this.stringContent = true;
    }
    
    public void ignorableWhitespace(final char[] str, final int offset, final int len) throws SAXException {
        this.stringBuffer.append(str, offset, len);
        this.stringContent = true;
    }
    
    public void processingInstruction(final String s, final String s2) throws SAXException {
        if (this.inDTD) {
            return;
        }
        this.appendStringData();
        if (this.currentNode == null) {
            this.preInfo.add(new ProcessingInstructionInfo(s, s2));
        }
        else {
            this.currentNode.appendChild(this.document.createProcessingInstruction(s, s2));
        }
    }
    
    public void startDTD(final String s, final String s2, final String s3) throws SAXException {
        this.appendStringData();
        this.inDTD = true;
    }
    
    public void endDTD() throws SAXException {
        this.inDTD = false;
    }
    
    public void startEntity(final String s) throws SAXException {
    }
    
    public void endEntity(final String s) throws SAXException {
    }
    
    public void startCDATA() throws SAXException {
        this.appendStringData();
        this.inCDATA = true;
        this.stringContent = true;
    }
    
    public void endCDATA() throws SAXException {
        this.appendStringData();
        this.inCDATA = false;
    }
    
    public void comment(final char[] value, final int offset, final int count) throws SAXException {
        if (this.inDTD) {
            return;
        }
        this.appendStringData();
        final String s = new String(value, offset, count);
        if (this.currentNode == null) {
            this.preInfo.add(new CommentInfo(s));
        }
        else {
            this.currentNode.appendChild(this.document.createComment(s));
        }
    }
    
    static {
        SAXDocumentFactory.saxFactory = SAXParserFactory.newInstance();
    }
    
    static class TextInfo implements PreInfo
    {
        public String text;
        
        public TextInfo(final String text) {
            this.text = text;
        }
        
        public Node createNode(final Document document) {
            return document.createTextNode(this.text);
        }
    }
    
    protected interface PreInfo
    {
        Node createNode(final Document p0);
    }
    
    static class CDataInfo implements PreInfo
    {
        public String cdata;
        
        public CDataInfo(final String cdata) {
            this.cdata = cdata;
        }
        
        public Node createNode(final Document document) {
            return document.createCDATASection(this.cdata);
        }
    }
    
    static class CommentInfo implements PreInfo
    {
        public String comment;
        
        public CommentInfo(final String comment) {
            this.comment = comment;
        }
        
        public Node createNode(final Document document) {
            return document.createComment(this.comment);
        }
    }
    
    static class ProcessingInstructionInfo implements PreInfo
    {
        public String target;
        public String data;
        
        public ProcessingInstructionInfo(final String target, final String data) {
            this.target = target;
            this.data = data;
        }
        
        public Node createNode(final Document document) {
            return document.createProcessingInstruction(this.target, this.data);
        }
    }
}
