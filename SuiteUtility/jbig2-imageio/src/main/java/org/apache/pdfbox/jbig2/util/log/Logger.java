// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.pdfbox.jbig2.util.log;

public interface Logger
{
    void debug(final String p0);
    
    void debug(final String p0, final Throwable p1);
    
    void info(final String p0);
    
    void info(final String p0, final Throwable p1);
    
    void warn(final String p0);
    
    void warn(final String p0, final Throwable p1);
    
    void fatal(final String p0);
    
    void fatal(final String p0, final Throwable p1);
    
    void error(final String p0);
    
    void error(final String p0, final Throwable p1);
    
    boolean isDebugEnabled();
    
    boolean isInfoEnabled();
    
    boolean isWarnEnabled();
    
    boolean isFatalEnabled();
    
    boolean isErrorEnabled();
}
