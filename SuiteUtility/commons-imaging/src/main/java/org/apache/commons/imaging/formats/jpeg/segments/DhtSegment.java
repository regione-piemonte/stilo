// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.commons.imaging.formats.jpeg.segments;

import java.util.Collections;
import org.apache.commons.imaging.common.BinaryFunctions;
import java.util.ArrayList;
import java.io.IOException;
import java.io.InputStream;
import java.io.ByteArrayInputStream;
import java.util.List;

public class DhtSegment extends Segment
{
    public final List<HuffmanTable> huffmanTables;
    
    public DhtSegment(final int marker, final byte[] segmentData) throws IOException {
        this(marker, segmentData.length, new ByteArrayInputStream(segmentData));
    }
    
    public DhtSegment(final int marker, int length, final InputStream is) throws IOException {
        super(marker, length);
        final ArrayList<HuffmanTable> huffmanTables = new ArrayList<HuffmanTable>();
        while (length > 0) {
            final int tableClassAndDestinationId = 0xFF & BinaryFunctions.readByte("TableClassAndDestinationId", is, "Not a Valid JPEG File");
            --length;
            final int tableClass = tableClassAndDestinationId >> 4 & 0xF;
            final int destinationIdentifier = tableClassAndDestinationId & 0xF;
            final int[] bits = new int[17];
            int bitsSum = 0;
            for (int i = 1; i < bits.length; ++i) {
                bits[i] = (0xFF & BinaryFunctions.readByte("Li", is, "Not a Valid JPEG File"));
                --length;
                bitsSum += bits[i];
            }
            final int[] huffVal = new int[bitsSum];
            for (int j = 0; j < bitsSum; ++j) {
                huffVal[j] = (0xFF & BinaryFunctions.readByte("Vij", is, "Not a Valid JPEG File"));
                --length;
            }
            huffmanTables.add(new HuffmanTable(tableClass, destinationIdentifier, bits, huffVal));
        }
        this.huffmanTables = Collections.unmodifiableList((List<? extends HuffmanTable>)huffmanTables);
    }
    
    @Override
    public String getDescription() {
        return "DHT (" + this.getSegmentType() + ")";
    }
    
    public static class HuffmanTable
    {
        public final int tableClass;
        public final int destinationIdentifier;
        private final int[] huffVal;
        private final int[] huffSize;
        private final int[] huffCode;
        private final int[] minCode;
        private final int[] maxCode;
        private final int[] valPtr;
        
        HuffmanTable(final int tableClass, final int destinationIdentifier, final int[] bits, final int[] huffVal) {
            this.huffSize = new int[4096];
            this.minCode = new int[17];
            this.maxCode = new int[17];
            this.valPtr = new int[17];
            this.tableClass = tableClass;
            this.destinationIdentifier = destinationIdentifier;
            this.huffVal = huffVal;
            int k = 0;
            int i = 1;
            int j = 1;
            int lastK = -1;
            while (true) {
                if (j > bits[i]) {
                    ++i;
                    j = 1;
                    if (i > 16) {
                        break;
                    }
                    continue;
                }
                else {
                    this.huffSize[k] = i;
                    ++k;
                    ++j;
                }
            }
            this.huffSize[k] = 0;
            lastK = k;
            k = 0;
            int code = 0;
            int si = this.huffSize[0];
            this.huffCode = new int[lastK];
            while (true) {
                while (k < lastK) {
                    this.huffCode[k] = code;
                    ++code;
                    ++k;
                    if (this.huffSize[k] == si) {
                        continue;
                    }
                    if (this.huffSize[k] == 0) {
                        i = 0;
                        j = 0;
                        while (++i <= 16) {
                            if (bits[i] == 0) {
                                this.maxCode[i] = -1;
                            }
                            else {
                                this.valPtr[i] = j;
                                this.minCode[i] = this.huffCode[j];
                                j += bits[i] - 1;
                                this.maxCode[i] = this.huffCode[j];
                                ++j;
                            }
                        }
                        return;
                    }
                    do {
                        code <<= 1;
                        ++si;
                    } while (this.huffSize[k] != si);
                }
                continue;
            }
        }
        
        public int getHuffVal(final int i) {
            return this.huffVal[i];
        }
        
        public int getMinCode(final int i) {
            return this.minCode[i];
        }
        
        public int getMaxCode(final int i) {
            return this.maxCode[i];
        }
        
        public int getValPtr(final int i) {
            return this.valPtr[i];
        }
    }
}
