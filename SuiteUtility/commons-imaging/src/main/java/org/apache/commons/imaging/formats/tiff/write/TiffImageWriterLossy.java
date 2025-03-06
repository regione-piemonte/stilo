// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.commons.imaging.formats.tiff.write;

import java.util.Iterator;
import org.apache.commons.imaging.ImageWriteException;
import java.io.IOException;
import java.util.List;
import org.apache.commons.imaging.common.BinaryOutputStream;
import java.io.OutputStream;
import java.nio.ByteOrder;

public class TiffImageWriterLossy extends TiffImageWriterBase
{
    public TiffImageWriterLossy() {
    }
    
    public TiffImageWriterLossy(final ByteOrder byteOrder) {
        super(byteOrder);
    }
    
    @Override
    public void write(final OutputStream os, final TiffOutputSet outputSet) throws IOException, ImageWriteException {
        final TiffOutputSummary outputSummary = this.validateDirectories(outputSet);
        final List<TiffOutputItem> outputItems = outputSet.getOutputItems(outputSummary);
        this.updateOffsetsStep(outputItems);
        outputSummary.updateOffsets(this.byteOrder);
        final BinaryOutputStream bos = new BinaryOutputStream(os, this.byteOrder);
        this.writeStep(bos, outputItems);
    }
    
    private void updateOffsetsStep(final List<TiffOutputItem> outputItems) {
        int offset = 8;
        for (final TiffOutputItem outputItem : outputItems) {
            outputItem.setOffset(offset);
            final int itemLength = outputItem.getItemLength();
            offset += itemLength;
            final int remainder = TiffImageWriterBase.imageDataPaddingLength(itemLength);
            offset += remainder;
        }
    }
    
    private void writeStep(final BinaryOutputStream bos, final List<TiffOutputItem> outputItems) throws IOException, ImageWriteException {
        this.writeImageFileHeader(bos);
        for (final TiffOutputItem outputItem : outputItems) {
            outputItem.writeItem(bos);
            final int length = outputItem.getItemLength();
            for (int remainder = TiffImageWriterBase.imageDataPaddingLength(length), j = 0; j < remainder; ++j) {
                bos.write(0);
            }
        }
    }
}
