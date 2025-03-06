// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.transcoder;

public class TranscoderException extends Exception
{
    protected Exception ex;
    
    public TranscoderException(final String s) {
        this(s, null);
    }
    
    public TranscoderException(final Exception ex) {
        this(null, ex);
    }
    
    public TranscoderException(final String message, final Exception ex) {
        super(message);
        this.ex = ex;
    }
    
    public String getMessage() {
        String str = super.getMessage();
        if (this.ex != null) {
            str = str + "\nEnclosed Exception:\n" + this.ex.getMessage();
        }
        return str;
    }
    
    public Exception getException() {
        return this.ex;
    }
}
