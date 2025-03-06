// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.i18n;

import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class LocalizableSupport implements Localizable
{
    protected LocaleGroup localeGroup;
    protected String bundleName;
    protected ClassLoader classLoader;
    protected Locale locale;
    protected Locale usedLocale;
    List resourceBundles;
    Class lastResourceClass;
    Class cls;
    
    public LocalizableSupport(final String s, final Class clazz) {
        this(s, clazz, null);
    }
    
    public LocalizableSupport(final String bundleName, final Class cls, final ClassLoader classLoader) {
        this.localeGroup = LocaleGroup.DEFAULT;
        this.resourceBundles = new ArrayList();
        this.bundleName = bundleName;
        this.cls = cls;
        this.classLoader = classLoader;
    }
    
    public LocalizableSupport(final String s) {
        this(s, (ClassLoader)null);
    }
    
    public LocalizableSupport(final String bundleName, final ClassLoader classLoader) {
        this.localeGroup = LocaleGroup.DEFAULT;
        this.resourceBundles = new ArrayList();
        this.bundleName = bundleName;
        this.classLoader = classLoader;
    }
    
    public void setLocale(final Locale locale) {
        if (this.locale != locale) {
            this.locale = locale;
            this.resourceBundles.clear();
            this.lastResourceClass = null;
        }
    }
    
    public Locale getLocale() {
        return this.locale;
    }
    
    public void setLocaleGroup(final LocaleGroup localeGroup) {
        this.localeGroup = localeGroup;
    }
    
    public LocaleGroup getLocaleGroup() {
        return this.localeGroup;
    }
    
    public void setDefaultLocale(final Locale locale) {
        this.localeGroup.setLocale(locale);
    }
    
    public Locale getDefaultLocale() {
        return this.localeGroup.getLocale();
    }
    
    public String formatMessage(final String s, final Object[] arguments) {
        return MessageFormat.format(this.getString(s), arguments);
    }
    
    protected Locale getCurrentLocale() {
        if (this.locale != null) {
            return this.locale;
        }
        final Locale locale = this.localeGroup.getLocale();
        if (locale != null) {
            return locale;
        }
        return Locale.getDefault();
    }
    
    protected boolean setUsedLocale() {
        final Locale currentLocale = this.getCurrentLocale();
        if (this.usedLocale == currentLocale) {
            return false;
        }
        this.usedLocale = currentLocale;
        this.resourceBundles.clear();
        this.lastResourceClass = null;
        return true;
    }
    
    public ResourceBundle getResourceBundle() {
        return this.getResourceBundle(0);
    }
    
    protected boolean hasNextResourceBundle(final int n) {
        return n == 0 || n < this.resourceBundles.size() || (this.lastResourceClass != null && this.lastResourceClass != Object.class);
    }
    
    protected ResourceBundle lookupResourceBundle(final String s, final Class clazz) {
        ClassLoader classLoader = this.classLoader;
        ResourceBundle resourceBundle = null;
        if (classLoader != null) {
            try {
                resourceBundle = ResourceBundle.getBundle(s, this.usedLocale, classLoader);
            }
            catch (MissingResourceException ex) {}
            if (resourceBundle != null) {
                return resourceBundle;
            }
        }
        if (clazz != null) {
            try {
                classLoader = clazz.getClassLoader();
            }
            catch (SecurityException ex2) {}
        }
        if (classLoader == null) {
            classLoader = this.getClass().getClassLoader();
        }
        try {
            resourceBundle = ResourceBundle.getBundle(s, this.usedLocale, classLoader);
        }
        catch (MissingResourceException ex3) {}
        return resourceBundle;
    }
    
    protected ResourceBundle getResourceBundle(final int i) {
        this.setUsedLocale();
        if (this.cls == null) {
            if (this.resourceBundles.size() == 0) {
                this.resourceBundles.add(this.lookupResourceBundle(this.bundleName, null));
            }
            return this.resourceBundles.get(0);
        }
        while (i >= this.resourceBundles.size()) {
            if (this.lastResourceClass == Object.class) {
                return null;
            }
            if (this.lastResourceClass == null) {
                this.lastResourceClass = this.cls;
            }
            else {
                this.lastResourceClass = this.lastResourceClass.getSuperclass();
            }
            final Class lastResourceClass = this.lastResourceClass;
            this.resourceBundles.add(this.lookupResourceBundle(lastResourceClass.getPackage().getName() + "." + this.bundleName, lastResourceClass));
        }
        return this.resourceBundles.get(i);
    }
    
    public String getString(final String str) throws MissingResourceException {
        this.setUsedLocale();
        for (int n = 0; this.hasNextResourceBundle(n); ++n) {
            final ResourceBundle resourceBundle = this.getResourceBundle(n);
            if (resourceBundle != null) {
                try {
                    final String string = resourceBundle.getString(str);
                    if (string != null) {
                        return string;
                    }
                }
                catch (MissingResourceException ex) {}
            }
        }
        throw new MissingResourceException("Unable to find resource: " + str, (this.cls != null) ? this.cls.toString() : this.bundleName, str);
    }
    
    public int getInteger(final String key) throws MissingResourceException {
        final String string = this.getString(key);
        try {
            return Integer.parseInt(string);
        }
        catch (NumberFormatException ex) {
            throw new MissingResourceException("Malformed integer", this.bundleName, key);
        }
    }
    
    public int getCharacter(final String key) throws MissingResourceException {
        final String string = this.getString(key);
        if (string == null || string.length() == 0) {
            throw new MissingResourceException("Malformed character", this.bundleName, key);
        }
        return string.charAt(0);
    }
}
