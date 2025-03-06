// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.pdfbox.jbig2.util.log;

import java.util.logging.Level;

public class JDKLogger implements Logger
{
    final java.util.logging.Logger wrappedLogger;
    
    public JDKLogger(final java.util.logging.Logger wrappedLogger) {
        this.wrappedLogger = wrappedLogger;
    }
    
    @Override
    public void debug(final String msg) {
        this.wrappedLogger.log(Level.FINE, msg);
    }
    
    @Override
    public void debug(final String msg, final Throwable thrown) {
        this.wrappedLogger.log(Level.FINE, msg, thrown);
    }
    
    @Override
    public void info(final String msg) {
        this.wrappedLogger.log(Level.INFO, msg);
    }
    
    @Override
    public void info(final String msg, final Throwable thrown) {
        this.wrappedLogger.log(Level.INFO, msg, thrown);
    }
    
    @Override
    public void warn(final String msg) {
        this.wrappedLogger.log(Level.WARNING, msg);
    }
    
    @Override
    public void warn(final String msg, final Throwable thrown) {
        this.wrappedLogger.log(Level.WARNING, msg, thrown);
    }
    
    @Override
    public void fatal(final String msg) {
        this.wrappedLogger.log(Level.SEVERE, msg);
    }
    
    @Override
    public void fatal(final String msg, final Throwable thrown) {
        this.wrappedLogger.log(Level.SEVERE, msg, thrown);
    }
    
    @Override
    public void error(final String msg) {
        this.wrappedLogger.log(Level.SEVERE, msg);
    }
    
    @Override
    public void error(final String msg, final Throwable thrown) {
        this.wrappedLogger.log(Level.SEVERE, msg, thrown);
    }
    
    @Override
    public boolean isDebugEnabled() {
        return this.wrappedLogger.isLoggable(Level.FINE);
    }
    
    @Override
    public boolean isInfoEnabled() {
        return this.wrappedLogger.isLoggable(Level.INFO);
    }
    
    @Override
    public boolean isWarnEnabled() {
        return this.wrappedLogger.isLoggable(Level.WARNING);
    }
    
    @Override
    public boolean isFatalEnabled() {
        return this.wrappedLogger.isLoggable(Level.SEVERE);
    }
    
    @Override
    public boolean isErrorEnabled() {
        return this.wrappedLogger.isLoggable(Level.SEVERE);
    }
}
