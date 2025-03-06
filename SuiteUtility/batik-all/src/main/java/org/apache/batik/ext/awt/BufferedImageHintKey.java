// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.ext.awt;

import java.awt.image.BufferedImage;
import java.lang.ref.Reference;
import java.awt.RenderingHints;

final class BufferedImageHintKey extends RenderingHints.Key
{
    BufferedImageHintKey(final int privatekey) {
        super(privatekey);
    }
    
    public boolean isCompatibleValue(Object value) {
        if (value == null) {
            return true;
        }
        if (!(value instanceof Reference)) {
            return false;
        }
        value = ((Reference)value).get();
        return value == null || value instanceof BufferedImage;
    }
}
