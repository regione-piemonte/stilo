// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.pdfbox.jbig2.err;

public class InvalidHeaderValueException extends JBIG2Exception
{
    private static final long serialVersionUID = -5534202639860867867L;
    
    public InvalidHeaderValueException() {
    }
    
    public InvalidHeaderValueException(final String s) {
        super(s);
    }
    
    public InvalidHeaderValueException(final Throwable t) {
        super(t);
    }
    
    public InvalidHeaderValueException(final String s, final Throwable t) {
        super(s, t);
    }
}
