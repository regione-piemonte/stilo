// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.ext.awt;

import java.awt.RenderingHints;

final class TranscodingHintKey extends RenderingHints.Key
{
    TranscodingHintKey(final int privatekey) {
        super(privatekey);
    }
    
    public boolean isCompatibleValue(final Object o) {
        boolean b = true;
        if (o != null && !(o instanceof String)) {
            b = false;
        }
        return b;
    }
}
