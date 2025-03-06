// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.util;

import java.util.MissingResourceException;
import java.util.Locale;
import org.apache.batik.util.resources.ResourceManager;
import org.apache.batik.i18n.LocalizableSupport;

public class Messages
{
    protected static final String RESOURCES = "org.apache.batik.util.resources.Messages";
    protected static LocalizableSupport localizableSupport;
    protected static ResourceManager resourceManager;
    
    protected Messages() {
    }
    
    public static void setLocale(final Locale locale) {
        Messages.localizableSupport.setLocale(locale);
        Messages.resourceManager = new ResourceManager(Messages.localizableSupport.getResourceBundle());
    }
    
    public static Locale getLocale() {
        return Messages.localizableSupport.getLocale();
    }
    
    public static String formatMessage(final String s, final Object[] array) throws MissingResourceException {
        return Messages.localizableSupport.formatMessage(s, array);
    }
    
    public static String getString(final String s) throws MissingResourceException {
        return Messages.resourceManager.getString(s);
    }
    
    public static int getInteger(final String s) throws MissingResourceException {
        return Messages.resourceManager.getInteger(s);
    }
    
    public static int getCharacter(final String s) throws MissingResourceException {
        return Messages.resourceManager.getCharacter(s);
    }
    
    static {
        Messages.localizableSupport = new LocalizableSupport("org.apache.batik.util.resources.Messages", Messages.class.getClassLoader());
        Messages.resourceManager = new ResourceManager(Messages.localizableSupport.getResourceBundle());
    }
}
