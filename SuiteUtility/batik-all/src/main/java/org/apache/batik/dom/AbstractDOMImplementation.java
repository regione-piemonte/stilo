// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.dom;

import org.apache.batik.dom.events.EventSupport;
import org.apache.batik.dom.events.DocumentEventSupport;
import org.apache.batik.dom.util.HashTable;
import java.io.Serializable;
import org.w3c.dom.DOMImplementation;

public abstract class AbstractDOMImplementation implements DOMImplementation, Serializable
{
    protected final HashTable features;
    
    protected void registerFeature(final String s, final Object o) {
        this.features.put(s.toLowerCase(), o);
    }
    
    protected AbstractDOMImplementation() {
        this.features = new HashTable();
        this.registerFeature("Core", new String[] { "2.0", "3.0" });
        this.registerFeature("XML", new String[] { "1.0", "2.0", "3.0" });
        this.registerFeature("Events", new String[] { "2.0", "3.0" });
        this.registerFeature("UIEvents", new String[] { "2.0", "3.0" });
        this.registerFeature("MouseEvents", new String[] { "2.0", "3.0" });
        this.registerFeature("TextEvents", "3.0");
        this.registerFeature("KeyboardEvents", "3.0");
        this.registerFeature("MutationEvents", new String[] { "2.0", "3.0" });
        this.registerFeature("MutationNameEvents", "3.0");
        this.registerFeature("Traversal", "2.0");
        this.registerFeature("XPath", "3.0");
    }
    
    public boolean hasFeature(String substring, final String s) {
        if (substring == null || substring.length() == 0) {
            return false;
        }
        if (substring.charAt(0) == '+') {
            substring = substring.substring(1);
        }
        final Object value = this.features.get(substring.toLowerCase());
        if (value == null) {
            return false;
        }
        if (s == null || s.length() == 0) {
            return true;
        }
        if (value instanceof String) {
            return s.equals(value);
        }
        final String[] array = (String[])value;
        for (int i = 0; i < array.length; ++i) {
            if (s.equals(array[i])) {
                return true;
            }
        }
        return false;
    }
    
    public Object getFeature(final String s, final String s2) {
        if (this.hasFeature(s, s2)) {
            return this;
        }
        return null;
    }
    
    public DocumentEventSupport createDocumentEventSupport() {
        return new DocumentEventSupport();
    }
    
    public EventSupport createEventSupport(final AbstractNode abstractNode) {
        return new EventSupport(abstractNode);
    }
}
