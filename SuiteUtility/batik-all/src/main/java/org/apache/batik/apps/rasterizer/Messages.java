// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.apps.rasterizer;

import java.util.MissingResourceException;
import java.util.Locale;
import org.apache.batik.i18n.LocalizableSupport;

public class Messages
{
    protected static final String RESOURCES = "org.apache.batik.apps.rasterizer.resources.Messages";
    protected static LocalizableSupport localizableSupport;
    
    protected Messages() {
    }
    
    public static void setLocale(final Locale locale) {
        Messages.localizableSupport.setLocale(locale);
    }
    
    public static Locale getLocale() {
        return Messages.localizableSupport.getLocale();
    }
    
    public static String formatMessage(final String s, final Object[] array) throws MissingResourceException {
        return Messages.localizableSupport.formatMessage(s, array);
    }
    
    public static String get(final String s) throws MissingResourceException {
        return formatMessage(s, null);
    }
    
    public static String get(final String s, final String s2) {
        String value = s2;
        try {
            value = get(s);
        }
        catch (MissingResourceException ex) {}
        return value;
    }
    
    static {
        Messages.localizableSupport = new LocalizableSupport("org.apache.batik.apps.rasterizer.resources.Messages", Messages.class.getClassLoader());
    }
}
