// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.util.io;

import java.util.HashMap;
import org.apache.batik.util.EncodingUtilities;
import java.io.Reader;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

public class StreamNormalizingReader extends NormalizingReader
{
    protected CharDecoder charDecoder;
    protected int nextChar;
    protected int line;
    protected int column;
    protected static final Map charDecoderFactories;
    
    public StreamNormalizingReader(final InputStream inputStream) throws IOException {
        this(inputStream, null);
    }
    
    public StreamNormalizingReader(final InputStream inputStream, String s) throws IOException {
        this.nextChar = -1;
        this.line = 1;
        if (s == null) {
            s = "ISO-8859-1";
        }
        this.charDecoder = this.createCharDecoder(inputStream, s);
    }
    
    public StreamNormalizingReader(final Reader reader) throws IOException {
        this.nextChar = -1;
        this.line = 1;
        this.charDecoder = new GenericDecoder(reader);
    }
    
    protected StreamNormalizingReader() {
        this.nextChar = -1;
        this.line = 1;
    }
    
    public int read() throws IOException {
        final int nextChar = this.nextChar;
        if (nextChar != -1) {
            this.nextChar = -1;
            if (nextChar == 13) {
                this.column = 0;
                ++this.line;
            }
            else {
                ++this.column;
            }
            return nextChar;
        }
        final int char1 = this.charDecoder.readChar();
        switch (char1) {
            case 13: {
                this.column = 0;
                ++this.line;
                final int char2 = this.charDecoder.readChar();
                if (char2 == 10) {
                    return 10;
                }
                this.nextChar = char2;
                return 10;
            }
            case 10: {
                this.column = 0;
                ++this.line;
                break;
            }
        }
        return char1;
    }
    
    public int getLine() {
        return this.line;
    }
    
    public int getColumn() {
        return this.column;
    }
    
    public void close() throws IOException {
        this.charDecoder.dispose();
        this.charDecoder = null;
    }
    
    protected CharDecoder createCharDecoder(final InputStream inputStream, final String s) throws IOException {
        final CharDecoderFactory charDecoderFactory = StreamNormalizingReader.charDecoderFactories.get(s.toUpperCase());
        if (charDecoderFactory != null) {
            return charDecoderFactory.createCharDecoder(inputStream);
        }
        String javaEncoding = EncodingUtilities.javaEncoding(s);
        if (javaEncoding == null) {
            javaEncoding = s;
        }
        return new GenericDecoder(inputStream, javaEncoding);
    }
    
    static {
        charDecoderFactories = new HashMap(11);
        final ASCIIDecoderFactory asciiDecoderFactory = new ASCIIDecoderFactory();
        StreamNormalizingReader.charDecoderFactories.put("ASCII", asciiDecoderFactory);
        StreamNormalizingReader.charDecoderFactories.put("US-ASCII", asciiDecoderFactory);
        StreamNormalizingReader.charDecoderFactories.put("ISO-8859-1", new ISO_8859_1DecoderFactory());
        StreamNormalizingReader.charDecoderFactories.put("UTF-8", new UTF8DecoderFactory());
        StreamNormalizingReader.charDecoderFactories.put("UTF-16", new UTF16DecoderFactory());
    }
    
    protected static class UTF16DecoderFactory implements CharDecoderFactory
    {
        public CharDecoder createCharDecoder(final InputStream inputStream) throws IOException {
            return new UTF16Decoder(inputStream);
        }
    }
    
    protected interface CharDecoderFactory
    {
        CharDecoder createCharDecoder(final InputStream p0) throws IOException;
    }
    
    protected static class UTF8DecoderFactory implements CharDecoderFactory
    {
        public CharDecoder createCharDecoder(final InputStream inputStream) throws IOException {
            return new UTF8Decoder(inputStream);
        }
    }
    
    protected static class ISO_8859_1DecoderFactory implements CharDecoderFactory
    {
        public CharDecoder createCharDecoder(final InputStream inputStream) throws IOException {
            return new ISO_8859_1Decoder(inputStream);
        }
    }
    
    protected static class ASCIIDecoderFactory implements CharDecoderFactory
    {
        public CharDecoder createCharDecoder(final InputStream inputStream) throws IOException {
            return new ASCIIDecoder(inputStream);
        }
    }
}
