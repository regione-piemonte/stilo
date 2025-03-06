// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.commons.imaging.formats.tiff;

public class JpegImageData extends DataElement
{
    public JpegImageData(final long offset, final int length, final byte[] data) {
        super(offset, length, data);
    }
    
    @Override
    public String getElementDescription() {
        return "Jpeg image data: " + this.getDataLength() + " bytes";
    }
}
