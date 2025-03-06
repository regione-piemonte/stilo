// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.transcoder;

import org.apache.batik.transcoder.keys.DOMImplementationKey;
import org.apache.batik.transcoder.keys.BooleanKey;
import org.apache.batik.transcoder.keys.StringKey;
import org.apache.batik.dom.util.SAXDocumentFactory;
import org.apache.batik.dom.util.DocumentFactory;
import org.w3c.dom.Document;
import java.io.IOException;
import org.w3c.dom.DOMException;
import org.apache.batik.util.XMLResourceDescriptor;
import org.w3c.dom.DOMImplementation;

public abstract class XMLAbstractTranscoder extends AbstractTranscoder
{
    public static final TranscodingHints.Key KEY_XML_PARSER_CLASSNAME;
    public static final TranscodingHints.Key KEY_XML_PARSER_VALIDATING;
    public static final TranscodingHints.Key KEY_DOCUMENT_ELEMENT;
    public static final TranscodingHints.Key KEY_DOCUMENT_ELEMENT_NAMESPACE_URI;
    public static final TranscodingHints.Key KEY_DOM_IMPLEMENTATION;
    
    protected XMLAbstractTranscoder() {
        this.hints.put(XMLAbstractTranscoder.KEY_XML_PARSER_VALIDATING, Boolean.FALSE);
    }
    
    public void transcode(final TranscoderInput transcoderInput, final TranscoderOutput transcoderOutput) throws TranscoderException {
        Document document = null;
        final String uri = transcoderInput.getURI();
        if (transcoderInput.getDocument() != null) {
            document = transcoderInput.getDocument();
        }
        else {
            String xmlParserClassName = (String)this.hints.get(XMLAbstractTranscoder.KEY_XML_PARSER_CLASSNAME);
            final String s = (String)this.hints.get(XMLAbstractTranscoder.KEY_DOCUMENT_ELEMENT_NAMESPACE_URI);
            final String s2 = (String)this.hints.get(XMLAbstractTranscoder.KEY_DOCUMENT_ELEMENT);
            final DOMImplementation domImplementation = (DOMImplementation)this.hints.get(XMLAbstractTranscoder.KEY_DOM_IMPLEMENTATION);
            if (xmlParserClassName == null) {
                xmlParserClassName = XMLResourceDescriptor.getXMLParserClassName();
            }
            if (domImplementation == null) {
                this.handler.fatalError(new TranscoderException("Unspecified transcoding hints: KEY_DOM_IMPLEMENTATION"));
                return;
            }
            if (s == null) {
                this.handler.fatalError(new TranscoderException("Unspecified transcoding hints: KEY_DOCUMENT_ELEMENT_NAMESPACE_URI"));
                return;
            }
            if (s2 == null) {
                this.handler.fatalError(new TranscoderException("Unspecified transcoding hints: KEY_DOCUMENT_ELEMENT"));
                return;
            }
            final DocumentFactory documentFactory = this.createDocumentFactory(domImplementation, xmlParserClassName);
            documentFactory.setValidating((boolean)this.hints.get(XMLAbstractTranscoder.KEY_XML_PARSER_VALIDATING));
            try {
                if (transcoderInput.getInputStream() != null) {
                    document = documentFactory.createDocument(s, s2, transcoderInput.getURI(), transcoderInput.getInputStream());
                }
                else if (transcoderInput.getReader() != null) {
                    document = documentFactory.createDocument(s, s2, transcoderInput.getURI(), transcoderInput.getReader());
                }
                else if (transcoderInput.getXMLReader() != null) {
                    document = documentFactory.createDocument(s, s2, transcoderInput.getURI(), transcoderInput.getXMLReader());
                }
                else if (uri != null) {
                    document = documentFactory.createDocument(s, s2, uri);
                }
            }
            catch (DOMException ex) {
                this.handler.fatalError(new TranscoderException(ex));
            }
            catch (IOException ex2) {
                this.handler.fatalError(new TranscoderException(ex2));
            }
        }
        if (document != null) {
            try {
                this.transcode(document, uri, transcoderOutput);
            }
            catch (TranscoderException ex3) {
                this.handler.fatalError(ex3);
            }
        }
    }
    
    protected DocumentFactory createDocumentFactory(final DOMImplementation domImplementation, final String s) {
        return new SAXDocumentFactory(domImplementation, s);
    }
    
    protected abstract void transcode(final Document p0, final String p1, final TranscoderOutput p2) throws TranscoderException;
    
    static {
        KEY_XML_PARSER_CLASSNAME = new StringKey();
        KEY_XML_PARSER_VALIDATING = new BooleanKey();
        KEY_DOCUMENT_ELEMENT = new StringKey();
        KEY_DOCUMENT_ELEMENT_NAMESPACE_URI = new StringKey();
        KEY_DOM_IMPLEMENTATION = new DOMImplementationKey();
    }
}
