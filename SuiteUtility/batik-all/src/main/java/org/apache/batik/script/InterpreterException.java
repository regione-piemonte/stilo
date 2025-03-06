// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.script;

public class InterpreterException extends RuntimeException
{
    private int line;
    private int column;
    private Exception embedded;
    
    public InterpreterException(final String message, final int line, final int column) {
        super(message);
        this.line = -1;
        this.column = -1;
        this.embedded = null;
        this.line = line;
        this.column = column;
    }
    
    public InterpreterException(final Exception embedded, final String s, final int n, final int n2) {
        this(s, n, n2);
        this.embedded = embedded;
    }
    
    public int getLineNumber() {
        return this.line;
    }
    
    public int getColumnNumber() {
        return this.column;
    }
    
    public Exception getException() {
        return this.embedded;
    }
    
    public String getMessage() {
        final String message = super.getMessage();
        if (message != null) {
            return message;
        }
        if (this.embedded != null) {
            return this.embedded.getMessage();
        }
        return null;
    }
}
