// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.util.resources;

import java.util.StringTokenizer;
import java.util.ArrayList;
import java.util.List;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class ResourceManager
{
    protected ResourceBundle bundle;
    
    public ResourceManager(final ResourceBundle bundle) {
        this.bundle = bundle;
    }
    
    public String getString(final String key) throws MissingResourceException {
        return this.bundle.getString(key);
    }
    
    public List getStringList(final String s) throws MissingResourceException {
        return this.getStringList(s, " \t\n\r\f", false);
    }
    
    public List getStringList(final String s, final String s2) throws MissingResourceException {
        return this.getStringList(s, s2, false);
    }
    
    public List getStringList(final String s, final String delim, final boolean returnDelims) throws MissingResourceException {
        final ArrayList<String> list = new ArrayList<String>();
        final StringTokenizer stringTokenizer = new StringTokenizer(this.getString(s), delim, returnDelims);
        while (stringTokenizer.hasMoreTokens()) {
            list.add(stringTokenizer.nextToken());
        }
        return list;
    }
    
    public boolean getBoolean(final String s) throws MissingResourceException, ResourceFormatException {
        final String string = this.getString(s);
        if (string.equals("true")) {
            return true;
        }
        if (string.equals("false")) {
            return false;
        }
        throw new ResourceFormatException("Malformed boolean", this.bundle.getClass().getName(), s);
    }
    
    public int getInteger(final String s) throws MissingResourceException, ResourceFormatException {
        final String string = this.getString(s);
        try {
            return Integer.parseInt(string);
        }
        catch (NumberFormatException ex) {
            throw new ResourceFormatException("Malformed integer", this.bundle.getClass().getName(), s);
        }
    }
    
    public int getCharacter(final String s) throws MissingResourceException, ResourceFormatException {
        final String string = this.getString(s);
        if (string == null || string.length() == 0) {
            throw new ResourceFormatException("Malformed character", this.bundle.getClass().getName(), s);
        }
        return string.charAt(0);
    }
}
