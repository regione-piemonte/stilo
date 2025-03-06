// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.commons.imaging.formats.jpeg.xmp;

import java.nio.charset.StandardCharsets;
import org.apache.commons.imaging.ImageReadException;
import org.apache.commons.imaging.common.BinaryFunctions;
import org.apache.commons.imaging.formats.jpeg.JpegConstants;
import java.nio.ByteOrder;
import org.apache.commons.imaging.common.BinaryFileParser;

public class JpegXmpParser extends BinaryFileParser
{
    public JpegXmpParser() {
        this.setByteOrder(ByteOrder.BIG_ENDIAN);
    }
    
    public boolean isXmpJpegSegment(final byte[] segmentData) {
        return BinaryFunctions.startsWith(segmentData, JpegConstants.XMP_IDENTIFIER);
    }
    
    public String parseXmpJpegSegment(final byte[] segmentData) throws ImageReadException {
        if (!this.isXmpJpegSegment(segmentData)) {
            throw new ImageReadException("Invalid JPEG XMP Segment.");
        }
        final int index = JpegConstants.XMP_IDENTIFIER.size();
        return new String(segmentData, index, segmentData.length - index, StandardCharsets.UTF_8);
    }
}
