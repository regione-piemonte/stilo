// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.transcoder;

public class DefaultErrorHandler implements ErrorHandler
{
    public void error(final TranscoderException ex) throws TranscoderException {
        System.err.println("ERROR: " + ex.getMessage());
    }
    
    public void fatalError(final TranscoderException ex) throws TranscoderException {
        throw ex;
    }
    
    public void warning(final TranscoderException ex) throws TranscoderException {
        System.err.println("WARNING: " + ex.getMessage());
    }
}
