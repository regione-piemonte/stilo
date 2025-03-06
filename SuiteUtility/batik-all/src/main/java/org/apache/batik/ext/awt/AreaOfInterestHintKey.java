// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.ext.awt;

import java.awt.Shape;
import java.awt.RenderingHints;

final class AreaOfInterestHintKey extends RenderingHints.Key
{
    AreaOfInterestHintKey(final int privatekey) {
        super(privatekey);
    }
    
    public boolean isCompatibleValue(final Object o) {
        boolean b = true;
        if (o != null && !(o instanceof Shape)) {
            b = false;
        }
        return b;
    }
}
