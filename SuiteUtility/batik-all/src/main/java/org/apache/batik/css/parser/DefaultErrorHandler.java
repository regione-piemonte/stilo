// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.css.parser;

import org.w3c.css.sac.CSSParseException;
import org.w3c.css.sac.ErrorHandler;

public class DefaultErrorHandler implements ErrorHandler
{
    public static final ErrorHandler INSTANCE;
    
    protected DefaultErrorHandler() {
    }
    
    public void warning(final CSSParseException ex) {
    }
    
    public void error(final CSSParseException ex) {
    }
    
    public void fatalError(final CSSParseException ex) {
        throw ex;
    }
    
    static {
        INSTANCE = (ErrorHandler)new DefaultErrorHandler();
    }
}
