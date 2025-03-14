// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.parser;

import org.apache.batik.util.io.StringNormalizingReader;
import java.io.InputStream;
import java.io.IOException;
import org.apache.batik.util.io.StreamNormalizingReader;
import java.io.Reader;
import org.apache.batik.util.io.NormalizingReader;

public abstract class AbstractScanner
{
    protected NormalizingReader reader;
    protected int current;
    protected char[] buffer;
    protected int position;
    protected int type;
    protected int previousType;
    protected int start;
    protected int end;
    protected int blankCharacters;
    
    public AbstractScanner(final Reader reader) throws ParseException {
        this.buffer = new char[128];
        try {
            this.reader = new StreamNormalizingReader(reader);
            this.current = this.nextChar();
        }
        catch (IOException ex) {
            throw new ParseException(ex);
        }
    }
    
    public AbstractScanner(final InputStream inputStream, final String s) throws ParseException {
        this.buffer = new char[128];
        try {
            this.reader = new StreamNormalizingReader(inputStream, s);
            this.current = this.nextChar();
        }
        catch (IOException ex) {
            throw new ParseException(ex);
        }
    }
    
    public AbstractScanner(final String s) throws ParseException {
        this.buffer = new char[128];
        try {
            this.reader = new StringNormalizingReader(s);
            this.current = this.nextChar();
        }
        catch (IOException ex) {
            throw new ParseException(ex);
        }
    }
    
    public int getLine() {
        return this.reader.getLine();
    }
    
    public int getColumn() {
        return this.reader.getColumn();
    }
    
    public char[] getBuffer() {
        return this.buffer;
    }
    
    public int getStart() {
        return this.start;
    }
    
    public int getEnd() {
        return this.end;
    }
    
    public void clearBuffer() {
        if (this.position <= 0) {
            this.position = 0;
        }
        else {
            this.buffer[0] = this.buffer[this.position - 1];
            this.position = 1;
        }
    }
    
    public int getType() {
        return this.type;
    }
    
    public String getStringValue() {
        return new String(this.buffer, this.start, this.end - this.start);
    }
    
    public int next() throws ParseException {
        this.blankCharacters = 0;
        this.start = this.position - 1;
        this.previousType = this.type;
        this.nextToken();
        this.end = this.position - this.endGap();
        return this.type;
    }
    
    protected abstract int endGap();
    
    protected abstract void nextToken() throws ParseException;
    
    protected static boolean isEqualIgnoreCase(final int n, final char c) {
        return n != -1 && Character.toLowerCase((char)n) == c;
    }
    
    protected int nextChar() throws IOException {
        this.current = this.reader.read();
        if (this.current == -1) {
            return this.current;
        }
        if (this.position == this.buffer.length) {
            final char[] buffer = new char[1 + this.position + this.position / 2];
            System.arraycopy(this.buffer, 0, buffer, 0, this.position);
            this.buffer = buffer;
        }
        return this.buffer[this.position++] = (char)this.current;
    }
}
