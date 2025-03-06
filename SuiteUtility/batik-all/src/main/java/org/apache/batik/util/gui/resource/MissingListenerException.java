// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.util.gui.resource;

public class MissingListenerException extends RuntimeException
{
    private String className;
    private String key;
    
    public MissingListenerException(final String message, final String className, final String key) {
        super(message);
        this.className = className;
        this.key = key;
    }
    
    public String getClassName() {
        return this.className;
    }
    
    public String getKey() {
        return this.key;
    }
    
    public String toString() {
        return super.toString() + " (" + this.getKey() + ", bundle: " + this.getClassName() + ")";
    }
}
