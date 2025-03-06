// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.util.io;

import java.io.IOException;

public class StringNormalizingReader extends NormalizingReader
{
    protected String string;
    protected int length;
    protected int next;
    protected int line;
    protected int column;
    
    public StringNormalizingReader(final String string) {
        this.line = 1;
        this.string = string;
        this.length = string.length();
    }
    
    public int read() throws IOException {
        final int n = (this.length == this.next) ? -1 : this.string.charAt(this.next++);
        if (n <= 13) {
            switch (n) {
                case 13: {
                    this.column = 0;
                    ++this.line;
                    if (((this.length == this.next) ? -1 : this.string.charAt(this.next)) == 10) {
                        ++this.next;
                    }
                    return 10;
                }
                case 10: {
                    this.column = 0;
                    ++this.line;
                    break;
                }
            }
        }
        return n;
    }
    
    public int getLine() {
        return this.line;
    }
    
    public int getColumn() {
        return this.column;
    }
    
    public void close() throws IOException {
        this.string = null;
    }
}
