// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.transcoder.svg2svg;

import org.apache.batik.transcoder.keys.StringKey;
import org.apache.batik.transcoder.keys.IntegerKey;
import org.apache.batik.transcoder.keys.BooleanKey;
import org.apache.batik.transcoder.TranscoderException;
import org.w3c.dom.Document;
import java.io.Reader;
import java.io.StringReader;
import java.io.IOException;
import java.io.Writer;
import org.apache.batik.dom.util.DOMUtilities;
import java.io.StringWriter;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscodingHints;
import org.apache.batik.transcoder.ErrorHandler;
import org.apache.batik.transcoder.AbstractTranscoder;

public class SVGTranscoder extends AbstractTranscoder
{
    public static final ErrorHandler DEFAULT_ERROR_HANDLER;
    public static final TranscodingHints.Key KEY_NEWLINE;
    public static final NewlineValue VALUE_NEWLINE_CR;
    public static final NewlineValue VALUE_NEWLINE_CR_LF;
    public static final NewlineValue VALUE_NEWLINE_LF;
    public static final TranscodingHints.Key KEY_FORMAT;
    public static final Boolean VALUE_FORMAT_ON;
    public static final Boolean VALUE_FORMAT_OFF;
    public static final TranscodingHints.Key KEY_TABULATION_WIDTH;
    public static final TranscodingHints.Key KEY_DOCUMENT_WIDTH;
    public static final TranscodingHints.Key KEY_DOCTYPE;
    public static final DoctypeValue VALUE_DOCTYPE_CHANGE;
    public static final DoctypeValue VALUE_DOCTYPE_REMOVE;
    public static final DoctypeValue VALUE_DOCTYPE_KEEP_UNCHANGED;
    public static final TranscodingHints.Key KEY_PUBLIC_ID;
    public static final TranscodingHints.Key KEY_SYSTEM_ID;
    public static final TranscodingHints.Key KEY_XML_DECLARATION;
    
    public SVGTranscoder() {
        this.setErrorHandler(SVGTranscoder.DEFAULT_ERROR_HANDLER);
    }
    
    public void transcode(final TranscoderInput transcoderInput, final TranscoderOutput transcoderOutput) throws TranscoderException {
        Reader reader = transcoderInput.getReader();
        final Writer writer = transcoderOutput.getWriter();
        if (reader == null) {
            final Document document = transcoderInput.getDocument();
            if (document == null) {
                throw new Error("Reader or Document expected");
            }
            final StringWriter stringWriter = new StringWriter(1024);
            try {
                DOMUtilities.writeDocument(document, stringWriter);
            }
            catch (IOException ex) {
                throw new Error("IO:" + ex.getMessage());
            }
            reader = new StringReader(stringWriter.toString());
        }
        if (writer == null) {
            throw new Error("Writer expected");
        }
        this.prettyPrint(reader, writer);
    }
    
    protected void prettyPrint(final Reader reader, final Writer writer) throws TranscoderException {
        try {
            final PrettyPrinter prettyPrinter = new PrettyPrinter();
            final NewlineValue newlineValue = (NewlineValue)this.hints.get(SVGTranscoder.KEY_NEWLINE);
            if (newlineValue != null) {
                prettyPrinter.setNewline(newlineValue.getValue());
            }
            final Boolean b = (Boolean)this.hints.get(SVGTranscoder.KEY_FORMAT);
            if (b != null) {
                prettyPrinter.setFormat(b);
            }
            final Integer n = (Integer)this.hints.get(SVGTranscoder.KEY_TABULATION_WIDTH);
            if (n != null) {
                prettyPrinter.setTabulationWidth(n);
            }
            final Integer n2 = (Integer)this.hints.get(SVGTranscoder.KEY_DOCUMENT_WIDTH);
            if (n2 != null) {
                prettyPrinter.setDocumentWidth(n2);
            }
            final DoctypeValue doctypeValue = (DoctypeValue)this.hints.get(SVGTranscoder.KEY_DOCTYPE);
            if (doctypeValue != null) {
                prettyPrinter.setDoctypeOption(doctypeValue.getValue());
            }
            final String publicId = (String)this.hints.get(SVGTranscoder.KEY_PUBLIC_ID);
            if (publicId != null) {
                prettyPrinter.setPublicId(publicId);
            }
            final String systemId = (String)this.hints.get(SVGTranscoder.KEY_SYSTEM_ID);
            if (systemId != null) {
                prettyPrinter.setSystemId(systemId);
            }
            final String xmlDeclaration = (String)this.hints.get(SVGTranscoder.KEY_XML_DECLARATION);
            if (xmlDeclaration != null) {
                prettyPrinter.setXMLDeclaration(xmlDeclaration);
            }
            prettyPrinter.print(reader, writer);
            writer.flush();
        }
        catch (IOException ex) {
            this.getErrorHandler().fatalError(new TranscoderException(ex.getMessage()));
        }
    }
    
    static {
        DEFAULT_ERROR_HANDLER = new ErrorHandler() {
            public void error(final TranscoderException ex) throws TranscoderException {
                throw ex;
            }
            
            public void fatalError(final TranscoderException ex) throws TranscoderException {
                throw ex;
            }
            
            public void warning(final TranscoderException ex) throws TranscoderException {
            }
        };
        KEY_NEWLINE = new NewlineKey();
        VALUE_NEWLINE_CR = new NewlineValue("\r");
        VALUE_NEWLINE_CR_LF = new NewlineValue("\r\n");
        VALUE_NEWLINE_LF = new NewlineValue("\n");
        KEY_FORMAT = new BooleanKey();
        VALUE_FORMAT_ON = Boolean.TRUE;
        VALUE_FORMAT_OFF = Boolean.FALSE;
        KEY_TABULATION_WIDTH = new IntegerKey();
        KEY_DOCUMENT_WIDTH = new IntegerKey();
        KEY_DOCTYPE = new DoctypeKey();
        VALUE_DOCTYPE_CHANGE = new DoctypeValue(0);
        VALUE_DOCTYPE_REMOVE = new DoctypeValue(1);
        VALUE_DOCTYPE_KEEP_UNCHANGED = new DoctypeValue(2);
        KEY_PUBLIC_ID = new StringKey();
        KEY_SYSTEM_ID = new StringKey();
        KEY_XML_DECLARATION = new StringKey();
    }
    
    protected static class DoctypeValue
    {
        final int value;
        
        protected DoctypeValue(final int value) {
            this.value = value;
        }
        
        public int getValue() {
            return this.value;
        }
    }
    
    protected static class DoctypeKey extends TranscodingHints.Key
    {
        public boolean isCompatibleValue(final Object o) {
            return o instanceof DoctypeValue;
        }
    }
    
    protected static class NewlineValue
    {
        protected final String value;
        
        protected NewlineValue(final String value) {
            this.value = value;
        }
        
        public String getValue() {
            return this.value;
        }
    }
    
    protected static class NewlineKey extends TranscodingHints.Key
    {
        public boolean isCompatibleValue(final Object o) {
            return o instanceof NewlineValue;
        }
    }
}
