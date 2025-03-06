// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.svggen;

import java.io.IOException;

public class SVGGraphics2DIOException extends IOException
{
    private IOException embedded;
    
    public SVGGraphics2DIOException(final String s) {
        this(s, null);
    }
    
    public SVGGraphics2DIOException(final IOException ex) {
        this(null, ex);
    }
    
    public SVGGraphics2DIOException(final String message, final IOException embedded) {
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
    
    public IOException getException() {
        return this.embedded;
    }
}
