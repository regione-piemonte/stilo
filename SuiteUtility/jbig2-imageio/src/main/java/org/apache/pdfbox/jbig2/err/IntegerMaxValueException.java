// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.pdfbox.jbig2.err;

public class IntegerMaxValueException extends JBIG2Exception
{
    private static final long serialVersionUID = -5534202639860867867L;
    
    public IntegerMaxValueException() {
    }
    
    public IntegerMaxValueException(final String s) {
        super(s);
    }
    
    public IntegerMaxValueException(final Throwable t) {
        super(t);
    }
    
    public IntegerMaxValueException(final String s, final Throwable t) {
        super(s, t);
    }
}
