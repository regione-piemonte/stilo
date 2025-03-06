// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.pdfbox.jbig2.util.log;

public class JDKLoggerBridge implements LoggerBridge
{
    @Override
    public Logger getLogger(final Class<?> clazz) {
        return new JDKLogger(java.util.logging.Logger.getLogger(clazz.getName()));
    }
}
