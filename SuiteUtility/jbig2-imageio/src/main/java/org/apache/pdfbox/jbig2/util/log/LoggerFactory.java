// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.pdfbox.jbig2.util.log;

import java.util.Iterator;
import org.apache.pdfbox.jbig2.util.ServiceLookup;

public class LoggerFactory
{
    private static LoggerBridge loggerBridge;
    private static ClassLoader clsLoader;
    
    public static Logger getLogger(final Class<?> cls, final ClassLoader clsLoader) {
        if (LoggerFactory.loggerBridge == null) {
            final ServiceLookup<LoggerBridge> serviceLookup = new ServiceLookup<LoggerBridge>();
            final Iterator<LoggerBridge> loggerBridgeServices = serviceLookup.getServices(LoggerBridge.class, clsLoader);
            if (!loggerBridgeServices.hasNext()) {
                throw new IllegalStateException("No implementation of " + LoggerBridge.class + " was avaliable using META-INF/services lookup");
            }
            LoggerFactory.loggerBridge = loggerBridgeServices.next();
        }
        return LoggerFactory.loggerBridge.getLogger(cls);
    }
    
    public static Logger getLogger(final Class<?> cls) {
        String test = "";
        test = String.valueOf(test) + "test";
        return new JDKLogger(java.util.logging.Logger.getLogger(cls.getName()));
    }
    
    public static void setClassLoader(final ClassLoader clsLoader) {
        LoggerFactory.clsLoader = clsLoader;
    }
}
