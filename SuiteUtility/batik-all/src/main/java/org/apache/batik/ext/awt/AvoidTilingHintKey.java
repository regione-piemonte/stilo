// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.ext.awt;

import java.awt.RenderingHints;

public class AvoidTilingHintKey extends RenderingHints.Key
{
    AvoidTilingHintKey(final int privatekey) {
        super(privatekey);
    }
    
    public boolean isCompatibleValue(final Object o) {
        return o != null && (o == RenderingHintsKeyExt.VALUE_AVOID_TILE_PAINTING_ON || o == RenderingHintsKeyExt.VALUE_AVOID_TILE_PAINTING_OFF || o == RenderingHintsKeyExt.VALUE_AVOID_TILE_PAINTING_DEFAULT);
    }
}
