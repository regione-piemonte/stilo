// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.transcoder.keys;

import java.awt.Paint;
import org.apache.batik.transcoder.TranscodingHints;

public class PaintKey extends TranscodingHints.Key
{
    public boolean isCompatibleValue(final Object o) {
        return o instanceof Paint;
    }
}
