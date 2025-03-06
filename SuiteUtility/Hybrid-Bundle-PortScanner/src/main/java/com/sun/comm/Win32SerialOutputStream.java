// 
// Decompiled by Procyon v0.5.36
// 

package com.sun.comm;

import java.io.IOException;
import java.io.OutputStream;

class Win32SerialOutputStream extends OutputStream
{
    private Win32SerialPort port;
    
    Win32SerialOutputStream(final Win32SerialPort port) {
        this.port = port;
    }
    
    public synchronized void write(final int n) throws IOException {
        this.port.write(n);
    }
    
    public synchronized void write(final byte[] array) throws IOException {
        this.port.write(array, 0, array.length);
    }
    
    public synchronized void write(final byte[] array, final int n, final int n2) throws IOException {
        this.port.write(array, n, n2);
    }
}
