// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.transcoder;

import java.util.Map;

public class TranscoderSupport
{
    static final ErrorHandler defaultErrorHandler;
    protected TranscodingHints hints;
    protected ErrorHandler handler;
    
    public TranscoderSupport() {
        this.hints = new TranscodingHints();
        this.handler = TranscoderSupport.defaultErrorHandler;
    }
    
    public TranscodingHints getTranscodingHints() {
        return new TranscodingHints(this.hints);
    }
    
    public void addTranscodingHint(final TranscodingHints.Key key, final Object o) {
        this.hints.put(key, o);
    }
    
    public void removeTranscodingHint(final TranscodingHints.Key key) {
        this.hints.remove(key);
    }
    
    public void setTranscodingHints(final Map map) {
        this.hints.putAll(map);
    }
    
    public void setTranscodingHints(final TranscodingHints hints) {
        this.hints = hints;
    }
    
    public void setErrorHandler(final ErrorHandler handler) {
        this.handler = handler;
    }
    
    public ErrorHandler getErrorHandler() {
        return this.handler;
    }
    
    static {
        defaultErrorHandler = new DefaultErrorHandler();
    }
}
