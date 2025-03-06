// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.apps.rasterizer;

public class SVGConverterException extends Exception
{
    protected String errorCode;
    protected Object[] errorInfo;
    protected boolean isFatal;
    
    public SVGConverterException(final String s) {
        this(s, null, false);
    }
    
    public SVGConverterException(final String s, final Object[] array) {
        this(s, array, false);
    }
    
    public SVGConverterException(final String errorCode, final Object[] errorInfo, final boolean isFatal) {
        this.errorCode = errorCode;
        this.errorInfo = errorInfo;
        this.isFatal = isFatal;
    }
    
    public SVGConverterException(final String s, final boolean b) {
        this(s, null, b);
    }
    
    public boolean isFatal() {
        return this.isFatal;
    }
    
    public String getMessage() {
        return Messages.formatMessage(this.errorCode, this.errorInfo);
    }
    
    public String getErrorCode() {
        return this.errorCode;
    }
}
