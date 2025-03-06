// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.commons.imaging.formats.tiff.write;

import org.apache.commons.imaging.ImageWriteException;
import java.util.Iterator;
import org.apache.commons.imaging.formats.tiff.fieldtypes.FieldType;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.nio.ByteOrder;

class TiffOutputSummary
{
    public final ByteOrder byteOrder;
    public final TiffOutputDirectory rootDirectory;
    public final Map<Integer, TiffOutputDirectory> directoryTypeMap;
    private final List<OffsetItem> offsetItems;
    private final List<ImageDataOffsets> imageDataItems;
    
    TiffOutputSummary(final ByteOrder byteOrder, final TiffOutputDirectory rootDirectory, final Map<Integer, TiffOutputDirectory> directoryTypeMap) {
        this.offsetItems = new ArrayList<OffsetItem>();
        this.imageDataItems = new ArrayList<ImageDataOffsets>();
        this.byteOrder = byteOrder;
        this.rootDirectory = rootDirectory;
        this.directoryTypeMap = directoryTypeMap;
    }
    
    public void add(final TiffOutputItem item, final TiffOutputField itemOffsetField) {
        this.offsetItems.add(new OffsetItem(item, itemOffsetField));
    }
    
    public void updateOffsets(final ByteOrder byteOrder) throws ImageWriteException {
        for (final OffsetItem offset : this.offsetItems) {
            final byte[] value = FieldType.LONG.writeData((int)offset.item.getOffset(), byteOrder);
            offset.itemOffsetField.setData(value);
        }
        for (final ImageDataOffsets imageDataInfo : this.imageDataItems) {
            for (int j = 0; j < imageDataInfo.outputItems.length; ++j) {
                final TiffOutputItem item = imageDataInfo.outputItems[j];
                imageDataInfo.imageDataOffsets[j] = (int)item.getOffset();
            }
            imageDataInfo.imageDataOffsetsField.setData(FieldType.LONG.writeData(imageDataInfo.imageDataOffsets, byteOrder));
        }
    }
    
    public void addTiffImageData(final ImageDataOffsets imageDataInfo) {
        this.imageDataItems.add(imageDataInfo);
    }
    
    private static class OffsetItem
    {
        public final TiffOutputItem item;
        public final TiffOutputField itemOffsetField;
        
        OffsetItem(final TiffOutputItem item, final TiffOutputField itemOffsetField) {
            this.itemOffsetField = itemOffsetField;
            this.item = item;
        }
    }
}
