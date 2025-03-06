// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.util.io;

import java.io.IOException;
import java.io.Reader;

public abstract class NormalizingReader extends Reader
{
    public int read(final char[] array, final int n, final int n2) throws IOException {
        if (n2 == 0) {
            return 0;
        }
        int n3 = this.read();
        if (n3 == -1) {
            return -1;
        }
        int n4 = 0;
        do {
            array[n4 + n] = (char)n3;
            ++n4;
            n3 = this.read();
        } while (n3 != -1 && n4 < n2);
        return n4;
    }
    
    public abstract int getLine();
    
    public abstract int getColumn();
}
