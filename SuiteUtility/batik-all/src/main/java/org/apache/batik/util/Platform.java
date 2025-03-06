// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.util;

import java.lang.reflect.InvocationTargetException;
import java.awt.Frame;

public abstract class Platform
{
    public static boolean isOSX;
    public static boolean isJRE13;
    
    public static void unmaximize(final Frame frame) {
        if (!Platform.isJRE13) {
            try {
                Frame.class.getMethod("setExtendedState", Integer.TYPE).invoke(frame, new Integer((int)Frame.class.getMethod("getExtendedState", (Class[])null).invoke(frame, (Object[])null) & 0xFFFFFFF9));
            }
            catch (InvocationTargetException ex) {}
            catch (NoSuchMethodException ex2) {}
            catch (IllegalAccessException ex3) {}
        }
    }
    
    static {
        Platform.isOSX = System.getProperty("os.name").equals("Mac OS X");
        Platform.isJRE13 = System.getProperty("java.version").startsWith("1.3");
    }
}
