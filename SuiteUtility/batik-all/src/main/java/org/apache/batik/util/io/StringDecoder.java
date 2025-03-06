// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.util.io;

import java.io.IOException;

public class StringDecoder implements CharDecoder
{
    protected String string;
    protected int length;
    protected int next;
    
    public StringDecoder(final String string) {
        this.string = string;
        this.length = string.length();
    }
    
    public int readChar() throws IOException {
        if (this.next == this.length) {
            return -1;
        }
        return this.string.charAt(this.next++);
    }
    
    public void dispose() throws IOException {
        this.string = null;
    }
}
