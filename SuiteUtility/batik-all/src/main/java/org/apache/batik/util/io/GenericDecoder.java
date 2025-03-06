// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.util.io;

import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.InputStream;
import java.io.Reader;

public class GenericDecoder implements CharDecoder
{
    protected Reader reader;
    
    public GenericDecoder(final InputStream in, final String charsetName) throws IOException {
        this.reader = new InputStreamReader(in, charsetName);
        this.reader = new BufferedReader(this.reader);
    }
    
    public GenericDecoder(final Reader reader) {
        this.reader = reader;
        if (!(reader instanceof BufferedReader)) {
            this.reader = new BufferedReader(this.reader);
        }
    }
    
    public int readChar() throws IOException {
        return this.reader.read();
    }
    
    public void dispose() throws IOException {
        this.reader.close();
        this.reader = null;
    }
}
