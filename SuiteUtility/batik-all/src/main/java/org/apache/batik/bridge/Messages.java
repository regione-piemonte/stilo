// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.bridge;

import java.util.MissingResourceException;
import java.util.Locale;
import org.apache.batik.i18n.LocalizableSupport;

public class Messages
{
    protected static final String RESOURCES = "org.apache.batik.bridge.resources.Messages";
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
    
    public static String getMessage(final String s) throws MissingResourceException {
        return formatMessage(s, null);
    }
    
    static {
        Messages.localizableSupport = new LocalizableSupport("org.apache.batik.bridge.resources.Messages", Messages.class.getClassLoader());
    }
}
