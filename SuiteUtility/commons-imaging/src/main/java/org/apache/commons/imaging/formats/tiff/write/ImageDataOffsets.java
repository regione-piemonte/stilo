// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.commons.imaging.formats.tiff.write;

import org.apache.commons.imaging.formats.tiff.TiffElement;

class ImageDataOffsets
{
    final int[] imageDataOffsets;
    final TiffOutputField imageDataOffsetsField;
    final TiffOutputItem[] outputItems;
    
    ImageDataOffsets(final TiffElement.DataElement[] imageData, final int[] imageDataOffsets, final TiffOutputField imageDataOffsetsField) {
        this.imageDataOffsets = imageDataOffsets;
        this.imageDataOffsetsField = imageDataOffsetsField;
        this.outputItems = new TiffOutputItem[imageData.length];
        for (int i = 0; i < imageData.length; ++i) {
            final TiffOutputItem item = new TiffOutputItem.Value("TIFF image data", imageData[i].getData());
            this.outputItems[i] = item;
        }
    }
}
