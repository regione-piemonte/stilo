// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.svggen;

public class SVGGraphics2DRuntimeException extends RuntimeException
{
    private Exception embedded;
    
    public SVGGraphics2DRuntimeException(final String s) {
        this(s, null);
    }
    
    public SVGGraphics2DRuntimeException(final Exception ex) {
        this(null, ex);
    }
    
    public SVGGraphics2DRuntimeException(final String message, final Exception embedded) {
        super(message);
        this.embedded = embedded;
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
    
    public Exception getException() {
        return this.embedded;
    }
}
