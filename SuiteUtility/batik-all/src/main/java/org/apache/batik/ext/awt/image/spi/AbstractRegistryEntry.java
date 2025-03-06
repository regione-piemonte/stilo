// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.ext.awt.image.spi;

import java.util.Collections;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractRegistryEntry implements RegistryEntry, ErrorConstants
{
    String name;
    float priority;
    List exts;
    List mimeTypes;
    
    public AbstractRegistryEntry(final String name, final float priority, final String[] array, final String[] array2) {
        this.name = name;
        this.priority = priority;
        this.exts = new ArrayList(array.length);
        for (int i = 0; i < array.length; ++i) {
            this.exts.add(array[i]);
        }
        this.exts = Collections.unmodifiableList((List<?>)this.exts);
        this.mimeTypes = new ArrayList(array2.length);
        for (int j = 0; j < array2.length; ++j) {
            this.mimeTypes.add(array2[j]);
        }
        this.mimeTypes = Collections.unmodifiableList((List<?>)this.mimeTypes);
    }
    
    public AbstractRegistryEntry(final String name, final float priority, final String s, final String s2) {
        this.name = name;
        this.priority = priority;
        (this.exts = new ArrayList(1)).add(s);
        this.exts = Collections.unmodifiableList((List<?>)this.exts);
        (this.mimeTypes = new ArrayList(1)).add(s2);
        this.mimeTypes = Collections.unmodifiableList((List<?>)this.mimeTypes);
    }
    
    public String getFormatName() {
        return this.name;
    }
    
    public List getStandardExtensions() {
        return this.exts;
    }
    
    public List getMimeTypes() {
        return this.mimeTypes;
    }
    
    public float getPriority() {
        return this.priority;
    }
}
