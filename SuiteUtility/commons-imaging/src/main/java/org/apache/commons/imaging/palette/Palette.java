// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.commons.imaging.palette;

import org.apache.commons.imaging.ImageWriteException;

public interface Palette
{
    int getPaletteIndex(final int p0) throws ImageWriteException;
    
    int getEntry(final int p0);
    
    int length();
}
