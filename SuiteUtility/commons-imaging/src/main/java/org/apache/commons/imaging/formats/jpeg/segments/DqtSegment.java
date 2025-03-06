// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.commons.imaging.formats.jpeg.segments;

import org.apache.commons.imaging.common.BinaryFunctions;
import java.util.ArrayList;
import java.io.IOException;
import org.apache.commons.imaging.ImageReadException;
import java.io.InputStream;
import java.io.ByteArrayInputStream;
import java.util.List;

public class DqtSegment extends Segment
{
    public final List<QuantizationTable> quantizationTables;
    
    public DqtSegment(final int marker, final byte[] segmentData) throws ImageReadException, IOException {
        this(marker, segmentData.length, new ByteArrayInputStream(segmentData));
    }
    
    public DqtSegment(final int marker, int length, final InputStream is) throws ImageReadException, IOException {
        super(marker, length);
        this.quantizationTables = new ArrayList<QuantizationTable>();
        while (length > 0) {
            final int precisionAndDestination = BinaryFunctions.readByte("QuantizationTablePrecisionAndDestination", is, "Not a Valid JPEG File");
            --length;
            final int precision = precisionAndDestination >> 4 & 0xF;
            final int destinationIdentifier = precisionAndDestination & 0xF;
            final int[] elements = new int[64];
            for (int i = 0; i < 64; ++i) {
                if (precision == 0) {
                    elements[i] = (0xFF & BinaryFunctions.readByte("QuantizationTableElement", is, "Not a Valid JPEG File"));
                    --length;
                }
                else {
                    if (precision != 1) {
                        throw new ImageReadException("Quantization table precision '" + precision + "' is invalid");
                    }
                    elements[i] = BinaryFunctions.read2Bytes("QuantizationTableElement", is, "Not a Valid JPEG File", this.getByteOrder());
                    length -= 2;
                }
            }
            this.quantizationTables.add(new QuantizationTable(precision, destinationIdentifier, elements));
        }
    }
    
    @Override
    public String getDescription() {
        return "DQT (" + this.getSegmentType() + ")";
    }
    
    public static class QuantizationTable
    {
        public final int precision;
        public final int destinationIdentifier;
        private final int[] elements;
        
        public QuantizationTable(final int precision, final int destinationIdentifier, final int[] elements) {
            this.precision = precision;
            this.destinationIdentifier = destinationIdentifier;
            this.elements = elements;
        }
        
        public int[] getElements() {
            return this.elements;
        }
    }
}
