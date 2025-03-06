// 
// Decompiled by Procyon v0.5.36
// 

package com.sun.comm;

import java.io.IOException;
import java.io.InputStream;

class Win32SerialInputStream extends InputStream
{
    private Win32SerialPort port;
    
    Win32SerialInputStream(final Win32SerialPort port) {
        this.port = port;
    }
    
    public int read() throws IOException {
        return this.port.read();
    }
    
    public int read(final byte[] array) throws IOException {
        return this.port.read(array, 0, array.length);
    }
    
    public int read(final byte[] array, final int n, final int n2) throws IOException {
        return this.port.read(array, n, n2);
    }
    
    public int available() throws IOException {
        return this.port.available();
    }
}
