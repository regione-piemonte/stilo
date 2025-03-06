// 
// Decompiled by Procyon v0.5.36
// 

package com.sun.comm;

import javax.comm.UnsupportedCommOperationException;
import java.io.InputStream;
import java.util.TooManyListenersException;
import javax.comm.ParallelPortEventListener;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.File;
import javax.comm.ParallelPort;

class Win32ParallelPort extends ParallelPort
{
    private File prfile;
    private OutputStream outstream;
    private boolean closed;
    
    Win32ParallelPort(final String s) throws IOException {
        this.closed = false;
        super.name = s;
        try {
            this.prfile = new File(s);
        }
        catch (NullPointerException obj) {
            throw new IOException(String.valueOf(obj) + " while opening port");
        }
        this.outstream = new FileOutputStream(this.prfile);
    }
    
    public void addEventListener(final ParallelPortEventListener parallelPortEventListener) throws TooManyListenersException {
        if (this.closed) {
            throw new IllegalStateException("Port Closed");
        }
    }
    
    public void removeEventListener() {
        if (this.closed) {
            throw new IllegalStateException("Port Closed");
        }
    }
    
    public void notifyOnError(final boolean b) {
        if (this.closed) {
            throw new IllegalStateException("Port Closed");
        }
    }
    
    public void notifyOnBuffer(final boolean b) {
        if (this.closed) {
            throw new IllegalStateException("Port Closed");
        }
    }
    
    public int getOutputBufferFree() {
        if (this.closed) {
            throw new IllegalStateException("Port Closed");
        }
        return 512;
    }
    
    public boolean isPaperOut() {
        if (this.closed) {
            throw new IllegalStateException("Port Closed");
        }
        return false;
    }
    
    public boolean isPrinterBusy() {
        if (this.closed) {
            throw new IllegalStateException("Port Closed");
        }
        return false;
    }
    
    public boolean isPrinterSelected() {
        if (this.closed) {
            throw new IllegalStateException("Port Closed");
        }
        return false;
    }
    
    public boolean isPrinterTimedOut() {
        if (this.closed) {
            throw new IllegalStateException("Port Closed");
        }
        return false;
    }
    
    public boolean isPrinterError() {
        if (this.closed) {
            throw new IllegalStateException("Port Closed");
        }
        return false;
    }
    
    public void restart() {
        if (this.closed) {
            throw new IllegalStateException("Port Closed");
        }
    }
    
    public void suspend() {
        if (this.closed) {
            throw new IllegalStateException("Port Closed");
        }
    }
    
    public int getMode() {
        if (this.closed) {
            throw new IllegalStateException("Port Closed");
        }
        return 1;
    }
    
    public InputStream getInputStream() throws IOException {
        throw new IOException("Unsupported operation. Output only mode");
    }
    
    public OutputStream getOutputStream() throws IOException {
        if (this.closed) {
            throw new IllegalStateException("Port Closed");
        }
        return this.outstream;
    }
    
    public void enableReceiveThreshold(final int n) throws UnsupportedCommOperationException {
        if (this.closed) {
            throw new IllegalStateException("Port Closed");
        }
    }
    
    public void disableReceiveThreshold() {
        if (this.closed) {
            throw new IllegalStateException("Port Closed");
        }
    }
    
    public boolean isReceiveThresholdEnabled() {
        if (this.closed) {
            throw new IllegalStateException("Port Closed");
        }
        return false;
    }
    
    public int getReceiveThreshold() {
        if (this.closed) {
            throw new IllegalStateException("Port Closed");
        }
        return 1;
    }
    
    public void enableReceiveTimeout(final int n) throws UnsupportedCommOperationException {
        if (this.closed) {
            throw new IllegalStateException("Port Closed");
        }
    }
    
    public void disableReceiveTimeout() {
        if (this.closed) {
            throw new IllegalStateException("Port Closed");
        }
    }
    
    public boolean isReceiveTimeoutEnabled() {
        if (this.closed) {
            throw new IllegalStateException("Port Closed");
        }
        return false;
    }
    
    public int getReceiveTimeout() {
        if (this.closed) {
            throw new IllegalStateException("Port Closed");
        }
        return 0;
    }
    
    public void enableReceiveFraming(final int n) throws UnsupportedCommOperationException {
        if (this.closed) {
            throw new IllegalStateException("Port Closed");
        }
    }
    
    public void disableReceiveFraming() {
        if (this.closed) {
            throw new IllegalStateException("Port Closed");
        }
    }
    
    public int getReceiveFramingByte() {
        if (this.closed) {
            throw new IllegalStateException("Port Closed");
        }
        return 0;
    }
    
    public boolean isReceiveFramingEnabled() {
        if (this.closed) {
            throw new IllegalStateException("Port Closed");
        }
        return false;
    }
    
    public void setInputBufferSize(final int n) {
        if (this.closed) {
            throw new IllegalStateException("Port Closed");
        }
    }
    
    public int getInputBufferSize() {
        if (this.closed) {
            throw new IllegalStateException("Port Closed");
        }
        return 1024;
    }
    
    public void setOutputBufferSize(final int n) {
        if (this.closed) {
            throw new IllegalStateException("Port Closed");
        }
    }
    
    public int getOutputBufferSize() {
        if (this.closed) {
            throw new IllegalStateException("Port Closed");
        }
        return 1024;
    }
    
    public int setMode(final int n) throws UnsupportedCommOperationException {
        if (this.closed) {
            throw new IllegalStateException("Port Closed");
        }
        if (n != 1) {
            throw new UnsupportedCommOperationException();
        }
        return 1;
    }
    
    public void close() {
        if (this.closed) {
            throw new IllegalStateException("Port Closed");
        }
        try {
            this.outstream.close();
        }
        catch (IOException ex) {}
        this.closed = true;
        super.close();
    }
}
