// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.xml;

import java.io.PrintWriter;
import java.io.PrintStream;

public class XMLException extends RuntimeException
{
    protected Exception exception;
    
    public XMLException(final String message) {
        super(message);
        this.exception = null;
    }
    
    public XMLException(final Exception exception) {
        this.exception = exception;
    }
    
    public XMLException(final String message, final Exception exception) {
        super(message);
        this.exception = exception;
    }
    
    public String getMessage() {
        final String message = super.getMessage();
        if (message == null && this.exception != null) {
            return this.exception.getMessage();
        }
        return message;
    }
    
    public Exception getException() {
        return this.exception;
    }
    
    public void printStackTrace() {
        if (this.exception == null) {
            super.printStackTrace();
        }
        else {
            synchronized (System.err) {
                System.err.println(this);
                super.printStackTrace();
            }
        }
    }
    
    public void printStackTrace(final PrintStream s) {
        if (this.exception == null) {
            super.printStackTrace(s);
        }
        else {
            synchronized (s) {
                s.println(this);
                super.printStackTrace();
            }
        }
    }
    
    public void printStackTrace(final PrintWriter printWriter) {
        if (this.exception == null) {
            super.printStackTrace(printWriter);
        }
        else {
            synchronized (printWriter) {
                printWriter.println(this);
                super.printStackTrace(printWriter);
            }
        }
    }
}
