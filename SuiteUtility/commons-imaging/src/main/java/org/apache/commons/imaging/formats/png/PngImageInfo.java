// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.commons.imaging.formats.png;

import java.util.Collection;
import java.util.ArrayList;
import org.apache.commons.imaging.ImageFormat;
import java.util.List;
import org.apache.commons.imaging.ImageInfo;

public class PngImageInfo extends ImageInfo
{
    private final List<PngText> textChunks;
    private final PhysicalScale physicalScale;
    
    PngImageInfo(final String formatDetails, final int bitsPerPixel, final List<String> comments, final ImageFormat format, final String formatName, final int height, final String mimeType, final int numberOfImages, final int physicalHeightDpi, final float physicalHeightInch, final int physicalWidthDpi, final float physicalWidthInch, final int width, final boolean progressive, final boolean transparent, final boolean usesPalette, final ColorType colorType, final CompressionAlgorithm compressionAlgorithm, final List<PngText> textChunks, final PhysicalScale physicalScale) {
        super(formatDetails, bitsPerPixel, comments, format, formatName, height, mimeType, numberOfImages, physicalHeightDpi, physicalHeightInch, physicalWidthDpi, physicalWidthInch, width, progressive, transparent, usesPalette, colorType, compressionAlgorithm);
        this.textChunks = textChunks;
        this.physicalScale = physicalScale;
    }
    
    public List<PngText> getTextChunks() {
        return new ArrayList<PngText>(this.textChunks);
    }
    
    public PhysicalScale getPhysicalScale() {
        return this.physicalScale;
    }
}
