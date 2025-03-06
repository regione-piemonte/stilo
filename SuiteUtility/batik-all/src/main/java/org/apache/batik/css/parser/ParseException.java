// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.css.parser;

public class ParseException extends RuntimeException
{
    protected Exception exception;
    protected int lineNumber;
    protected int columnNumber;
    
    public ParseException(final String message, final int lineNumber, final int columnNumber) {
        super(message);
        this.exception = null;
        this.lineNumber = lineNumber;
        this.columnNumber = columnNumber;
    }
    
    public ParseException(final Exception exception) {
        this.exception = exception;
        this.lineNumber = -1;
        this.columnNumber = -1;
    }
    
    public ParseException(final String message, final Exception exception) {
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
    
    public int getLineNumber() {
        return this.lineNumber;
    }
    
    public int getColumnNumber() {
        return this.columnNumber;
    }
}
