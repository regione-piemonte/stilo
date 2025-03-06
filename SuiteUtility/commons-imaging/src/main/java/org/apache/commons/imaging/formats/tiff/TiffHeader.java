// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.commons.imaging.formats.tiff;

import java.nio.ByteOrder;

public class TiffHeader extends TiffElement
{
    public final ByteOrder byteOrder;
    public final int tiffVersion;
    public final long offsetToFirstIFD;
    
    public TiffHeader(final ByteOrder byteOrder, final int tiffVersion, final long offsetToFirstIFD) {
        super(0L, 8);
        this.byteOrder = byteOrder;
        this.tiffVersion = tiffVersion;
        this.offsetToFirstIFD = offsetToFirstIFD;
    }
    
    @Override
    public String getElementDescription() {
        return "TIFF Header";
    }
}
