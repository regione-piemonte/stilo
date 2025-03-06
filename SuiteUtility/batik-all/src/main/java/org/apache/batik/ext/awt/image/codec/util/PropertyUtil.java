// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.ext.awt.image.codec.util;

import java.util.MissingResourceException;
import org.apache.batik.i18n.LocalizableSupport;

public class PropertyUtil
{
    protected static final String RESOURCES = "org.apache.batik.bridge.resources.properties";
    protected static LocalizableSupport localizableSupport;
    
    public static String getString(final String s) {
        try {
            return PropertyUtil.localizableSupport.formatMessage(s, null);
        }
        catch (MissingResourceException ex) {
            return s;
        }
    }
    
    static {
        PropertyUtil.localizableSupport = new LocalizableSupport("org.apache.batik.bridge.resources.properties", PropertyUtil.class.getClassLoader());
    }
}
