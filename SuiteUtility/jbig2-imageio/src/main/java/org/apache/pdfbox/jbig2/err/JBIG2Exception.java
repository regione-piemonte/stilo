// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.pdfbox.jbig2.err;

public class JBIG2Exception extends Exception
{
    private static final long serialVersionUID = 5063673874564442169L;
    
    public JBIG2Exception() {
    }
    
    public JBIG2Exception(final String message) {
        super(message);
    }
    
    public JBIG2Exception(final Throwable cause) {
        super(cause);
    }
    
    public JBIG2Exception(final String message, final Throwable cause) {
        super(message, cause);
    }
}
