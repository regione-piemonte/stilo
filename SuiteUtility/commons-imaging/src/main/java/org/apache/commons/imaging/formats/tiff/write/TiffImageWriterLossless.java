// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.commons.imaging.formats.tiff.write;

import java.util.Arrays;
import org.apache.commons.imaging.common.BinaryOutputStream;
import org.apache.commons.imaging.formats.tiff.taginfos.TagInfo;
import org.apache.commons.imaging.formats.tiff.constants.ExifTagConstants;
import java.util.HashMap;
import java.io.OutputStream;
import java.io.IOException;
import org.apache.commons.imaging.formats.tiff.TiffImageData;
import org.apache.commons.imaging.formats.tiff.JpegImageData;
import java.util.Iterator;
import org.apache.commons.imaging.formats.tiff.TiffContents;
import org.apache.commons.imaging.common.bytesource.ByteSource;
import org.apache.commons.imaging.ImageReadException;
import org.apache.commons.imaging.ImageWriteException;
import java.util.Collection;
import java.util.Collections;
import org.apache.commons.imaging.formats.tiff.TiffField;
import org.apache.commons.imaging.formats.tiff.TiffDirectory;
import java.util.ArrayList;
import org.apache.commons.imaging.formats.tiff.TiffReader;
import org.apache.commons.imaging.FormatCompliance;
import org.apache.commons.imaging.common.bytesource.ByteSourceArray;
import java.util.List;
import java.util.Map;
import java.nio.ByteOrder;
import org.apache.commons.imaging.formats.tiff.TiffElement;
import java.util.Comparator;

public class TiffImageWriterLossless extends TiffImageWriterBase
{
    private final byte[] exifBytes;
    private static final Comparator<TiffElement> ELEMENT_SIZE_COMPARATOR;
    private static final Comparator<TiffOutputItem> ITEM_SIZE_COMPARATOR;
    
    public TiffImageWriterLossless(final byte[] exifBytes) {
        this.exifBytes = exifBytes;
    }
    
    public TiffImageWriterLossless(final ByteOrder byteOrder, final byte[] exifBytes) {
        super(byteOrder);
        this.exifBytes = exifBytes;
    }
    
    private List<TiffElement> analyzeOldTiff(final Map<Integer, TiffOutputField> frozenFields) throws ImageWriteException, IOException {
        try {
            final ByteSource byteSource = new ByteSourceArray(this.exifBytes);
            final Map<String, Object> params = null;
            final FormatCompliance formatCompliance = FormatCompliance.getDefault();
            final TiffContents contents = new TiffReader(false).readContents(byteSource, params, formatCompliance);
            final List<TiffElement> elements = new ArrayList<TiffElement>();
            final List<TiffDirectory> directories = contents.directories;
            for (final TiffDirectory directory : directories) {
                elements.add(directory);
                for (final TiffField field : directory.getDirectoryEntries()) {
                    final TiffElement oversizeValue = field.getOversizeValueElement();
                    if (oversizeValue != null) {
                        final TiffOutputField frozenField = frozenFields.get(field.getTag());
                        if (frozenField != null && frozenField.getSeperateValue() != null && frozenField.bytesEqual(field.getByteArrayValue())) {
                            frozenField.getSeperateValue().setOffset(field.getOffset());
                        }
                        else {
                            elements.add(oversizeValue);
                        }
                    }
                }
                final JpegImageData jpegImageData = directory.getJpegImageData();
                if (jpegImageData != null) {
                    elements.add(jpegImageData);
                }
                final TiffImageData tiffImageData = directory.getTiffImageData();
                if (tiffImageData != null) {
                    final TiffElement.DataElement[] data = tiffImageData.getImageData();
                    Collections.addAll(elements, data);
                }
            }
            Collections.sort(elements, TiffElement.COMPARATOR);
            final List<TiffElement> rewritableElements = new ArrayList<TiffElement>();
            final int TOLERANCE = 3;
            TiffElement start = null;
            long index = -1L;
            for (final TiffElement element : elements) {
                final long lastElementByte = element.offset + element.length;
                if (start == null) {
                    start = element;
                    index = lastElementByte;
                }
                else if (element.offset - index > 3L) {
                    rewritableElements.add(new TiffElement.Stub(start.offset, (int)(index - start.offset)));
                    start = element;
                    index = lastElementByte;
                }
                else {
                    index = lastElementByte;
                }
            }
            if (null != start) {
                rewritableElements.add(new TiffElement.Stub(start.offset, (int)(index - start.offset)));
            }
            return rewritableElements;
        }
        catch (ImageReadException e) {
            throw new ImageWriteException(e.getMessage(), e);
        }
    }
    
    @Override
    public void write(final OutputStream os, final TiffOutputSet outputSet) throws IOException, ImageWriteException {
        final Map<Integer, TiffOutputField> frozenFields = new HashMap<Integer, TiffOutputField>();
        final TiffOutputField makerNoteField = outputSet.findField(ExifTagConstants.EXIF_TAG_MAKER_NOTE);
        if (makerNoteField != null && makerNoteField.getSeperateValue() != null) {
            frozenFields.put(ExifTagConstants.EXIF_TAG_MAKER_NOTE.tag, makerNoteField);
        }
        final List<TiffElement> analysis = this.analyzeOldTiff(frozenFields);
        final int oldLength = this.exifBytes.length;
        if (analysis.isEmpty()) {
            throw new ImageWriteException("Couldn't analyze old tiff data.");
        }
        if (analysis.size() == 1) {
            final TiffElement onlyElement = analysis.get(0);
            if (onlyElement.offset == 8L && onlyElement.offset + onlyElement.length + 8L == oldLength) {
                new TiffImageWriterLossy(this.byteOrder).write(os, outputSet);
                return;
            }
        }
        final Map<Long, TiffOutputField> frozenFieldOffsets = new HashMap<Long, TiffOutputField>();
        for (final Map.Entry<Integer, TiffOutputField> entry : frozenFields.entrySet()) {
            final TiffOutputField frozenField = entry.getValue();
            if (frozenField.getSeperateValue().getOffset() != -1L) {
                frozenFieldOffsets.put(frozenField.getSeperateValue().getOffset(), frozenField);
            }
        }
        final TiffOutputSummary outputSummary = this.validateDirectories(outputSet);
        final List<TiffOutputItem> allOutputItems = outputSet.getOutputItems(outputSummary);
        final List<TiffOutputItem> outputItems = new ArrayList<TiffOutputItem>();
        for (final TiffOutputItem outputItem : allOutputItems) {
            if (!frozenFieldOffsets.containsKey(outputItem.getOffset())) {
                outputItems.add(outputItem);
            }
        }
        final long outputLength = this.updateOffsetsStep(analysis, outputItems);
        outputSummary.updateOffsets(this.byteOrder);
        this.writeStep(os, outputSet, analysis, outputItems, outputLength);
    }
    
    private long updateOffsetsStep(final List<TiffElement> analysis, final List<TiffOutputItem> outputItems) {
        long overflowIndex = this.exifBytes.length;
        final List<TiffElement> unusedElements = new ArrayList<TiffElement>(analysis);
        Collections.sort(unusedElements, TiffElement.COMPARATOR);
        Collections.reverse(unusedElements);
        while (!unusedElements.isEmpty()) {
            final TiffElement element = unusedElements.get(0);
            final long elementEnd = element.offset + element.length;
            if (elementEnd != overflowIndex) {
                break;
            }
            overflowIndex -= element.length;
            unusedElements.remove(0);
        }
        Collections.sort(unusedElements, TiffImageWriterLossless.ELEMENT_SIZE_COMPARATOR);
        Collections.reverse(unusedElements);
        final List<TiffOutputItem> unplacedItems = new ArrayList<TiffOutputItem>(outputItems);
        Collections.sort(unplacedItems, TiffImageWriterLossless.ITEM_SIZE_COMPARATOR);
        Collections.reverse(unplacedItems);
        while (!unplacedItems.isEmpty()) {
            final TiffOutputItem outputItem = unplacedItems.remove(0);
            final int outputItemLength = outputItem.getItemLength();
            TiffElement bestFit = null;
            for (final TiffElement element2 : unusedElements) {
                if (element2.length < outputItemLength) {
                    break;
                }
                bestFit = element2;
            }
            if (null == bestFit) {
                if ((overflowIndex & 0x1L) != 0x0L) {
                    ++overflowIndex;
                }
                outputItem.setOffset(overflowIndex);
                overflowIndex += outputItemLength;
            }
            else {
                long offset = bestFit.offset;
                if ((offset & 0x1L) != 0x0L) {
                    ++offset;
                }
                outputItem.setOffset(offset);
                unusedElements.remove(bestFit);
                if (bestFit.length <= outputItemLength) {
                    continue;
                }
                final long excessOffset = bestFit.offset + outputItemLength;
                final int excessLength = bestFit.length - outputItemLength;
                unusedElements.add(new TiffElement.Stub(excessOffset, excessLength));
                Collections.sort(unusedElements, TiffImageWriterLossless.ELEMENT_SIZE_COMPARATOR);
                Collections.reverse(unusedElements);
            }
        }
        return overflowIndex;
    }
    
    private void writeStep(final OutputStream os, final TiffOutputSet outputSet, final List<TiffElement> analysis, final List<TiffOutputItem> outputItems, final long outputLength) throws IOException, ImageWriteException {
        final TiffOutputDirectory rootDirectory = outputSet.getRootDirectory();
        final byte[] output = new byte[(int)outputLength];
        System.arraycopy(this.exifBytes, 0, output, 0, Math.min(this.exifBytes.length, output.length));
        final BufferOutputStream headerStream = new BufferOutputStream(output, 0);
        final BinaryOutputStream headerBinaryStream = new BinaryOutputStream(headerStream, this.byteOrder);
        this.writeImageFileHeader(headerBinaryStream, rootDirectory.getOffset());
        for (final TiffElement element : analysis) {
            Arrays.fill(output, (int)element.offset, (int)Math.min(element.offset + element.length, output.length), (byte)0);
        }
        for (final TiffOutputItem outputItem : outputItems) {
            try (final BinaryOutputStream bos = new BinaryOutputStream(new BufferOutputStream(output, (int)outputItem.getOffset()), this.byteOrder)) {
                outputItem.writeItem(bos);
            }
        }
        os.write(output);
    }
    
    static {
        ELEMENT_SIZE_COMPARATOR = new Comparator<TiffElement>() {
            @Override
            public int compare(final TiffElement e1, final TiffElement e2) {
                return e1.length - e2.length;
            }
        };
        ITEM_SIZE_COMPARATOR = new Comparator<TiffOutputItem>() {
            @Override
            public int compare(final TiffOutputItem e1, final TiffOutputItem e2) {
                return e1.getItemLength() - e2.getItemLength();
            }
        };
    }
    
    private static class BufferOutputStream extends OutputStream
    {
        private final byte[] buffer;
        private int index;
        
        BufferOutputStream(final byte[] buffer, final int index) {
            this.buffer = buffer;
            this.index = index;
        }
        
        @Override
        public void write(final int b) throws IOException {
            if (this.index >= this.buffer.length) {
                throw new IOException("Buffer overflow.");
            }
            this.buffer[this.index++] = (byte)b;
        }
        
        @Override
        public void write(final byte[] b, final int off, final int len) throws IOException {
            if (this.index + len > this.buffer.length) {
                throw new IOException("Buffer overflow.");
            }
            System.arraycopy(b, off, this.buffer, this.index, len);
            this.index += len;
        }
    }
}
