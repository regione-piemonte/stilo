// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.pdfbox.jbig2.segments;

import java.util.List;
import org.apache.pdfbox.jbig2.decoder.huffman.EncodedTable;
import java.util.Collection;
import org.apache.pdfbox.jbig2.image.Bitmaps;
import org.apache.pdfbox.jbig2.decoder.huffman.StandardTables;
import java.util.Arrays;
import javax.imageio.stream.ImageInputStream;
import org.apache.pdfbox.jbig2.err.IntegerMaxValueException;
import org.apache.pdfbox.jbig2.err.InvalidHeaderValueException;
import java.io.IOException;
import org.apache.pdfbox.jbig2.util.log.LoggerFactory;
import org.apache.pdfbox.jbig2.decoder.huffman.HuffmanTable;
import org.apache.pdfbox.jbig2.SegmentHeader;
import org.apache.pdfbox.jbig2.decoder.huffman.FixedSizeTable;
import org.apache.pdfbox.jbig2.decoder.arithmetic.CX;
import org.apache.pdfbox.jbig2.decoder.arithmetic.ArithmeticIntegerDecoder;
import org.apache.pdfbox.jbig2.decoder.arithmetic.ArithmeticDecoder;
import java.util.ArrayList;
import org.apache.pdfbox.jbig2.Bitmap;
import org.apache.pdfbox.jbig2.util.CombinationOperator;
import org.apache.pdfbox.jbig2.io.SubInputStream;
import org.apache.pdfbox.jbig2.util.log.Logger;
import org.apache.pdfbox.jbig2.Region;

public class TextRegion implements Region
{
    private final Logger log;
    private SubInputStream subInputStream;
    private RegionSegmentInformation regionInfo;
    private short sbrTemplate;
    private short sbdsOffset;
    private short defaultPixel;
    private CombinationOperator combinationOperator;
    private short isTransposed;
    private short referenceCorner;
    private short logSBStrips;
    private boolean useRefinement;
    private boolean isHuffmanEncoded;
    private short sbHuffRSize;
    private short sbHuffRDY;
    private short sbHuffRDX;
    private short sbHuffRDHeight;
    private short sbHuffRDWidth;
    private short sbHuffDT;
    private short sbHuffDS;
    private short sbHuffFS;
    private short[] sbrATX;
    private short[] sbrATY;
    private long amountOfSymbolInstances;
    private long currentS;
    private int sbStrips;
    private int amountOfSymbols;
    private Bitmap regionBitmap;
    private ArrayList<Bitmap> symbols;
    private ArithmeticDecoder arithmeticDecoder;
    private ArithmeticIntegerDecoder integerDecoder;
    private GenericRefinementRegion genericRefinementRegion;
    private CX cxIADT;
    private CX cxIAFS;
    private CX cxIADS;
    private CX cxIAIT;
    private CX cxIARI;
    private CX cxIARDW;
    private CX cxIARDH;
    private CX cxIAID;
    private CX cxIARDX;
    private CX cxIARDY;
    private CX cx;
    private int symbolCodeLength;
    private FixedSizeTable symbolCodeTable;
    private SegmentHeader segmentHeader;
    private HuffmanTable fsTable;
    private HuffmanTable dsTable;
    private HuffmanTable table;
    private HuffmanTable rdwTable;
    private HuffmanTable rdhTable;
    private HuffmanTable rdxTable;
    private HuffmanTable rdyTable;
    private HuffmanTable rSizeTable;
    
    public TextRegion() {
        this.log = LoggerFactory.getLogger(TextRegion.class);
        this.symbols = new ArrayList<Bitmap>();
    }
    
    public TextRegion(final SubInputStream subInputStream, final SegmentHeader segmentHeader) {
        this.log = LoggerFactory.getLogger(TextRegion.class);
        this.symbols = new ArrayList<Bitmap>();
        this.subInputStream = subInputStream;
        this.regionInfo = new RegionSegmentInformation(subInputStream);
        this.segmentHeader = segmentHeader;
    }
    
    private void parseHeader() throws IOException, InvalidHeaderValueException, IntegerMaxValueException {
        this.regionInfo.parseHeader();
        this.readRegionFlags();
        if (this.isHuffmanEncoded) {
            this.readHuffmanFlags();
        }
        this.readUseRefinement();
        this.readAmountOfSymbolInstances();
        this.getSymbols();
        this.computeSymbolCodeLength();
        this.checkInput();
    }
    
    private void readRegionFlags() throws IOException {
        this.sbrTemplate = (short)this.subInputStream.readBit();
        this.sbdsOffset = (short)this.subInputStream.readBits(5);
        if (this.sbdsOffset > 15) {
            this.sbdsOffset -= 32;
        }
        this.defaultPixel = (short)this.subInputStream.readBit();
        this.combinationOperator = CombinationOperator.translateOperatorCodeToEnum((short)(this.subInputStream.readBits(2) & 0x3L));
        this.isTransposed = (short)this.subInputStream.readBit();
        this.referenceCorner = (short)(this.subInputStream.readBits(2) & 0x3L);
        this.logSBStrips = (short)(this.subInputStream.readBits(2) & 0x3L);
        this.sbStrips = 1 << this.logSBStrips;
        if (this.subInputStream.readBit() == 1) {
            this.useRefinement = true;
        }
        if (this.subInputStream.readBit() == 1) {
            this.isHuffmanEncoded = true;
        }
    }
    
    private void readHuffmanFlags() throws IOException {
        this.subInputStream.readBit();
        this.sbHuffRSize = (short)this.subInputStream.readBit();
        this.sbHuffRDY = (short)(this.subInputStream.readBits(2) & 0xFL);
        this.sbHuffRDX = (short)(this.subInputStream.readBits(2) & 0xFL);
        this.sbHuffRDHeight = (short)(this.subInputStream.readBits(2) & 0xFL);
        this.sbHuffRDWidth = (short)(this.subInputStream.readBits(2) & 0xFL);
        this.sbHuffDT = (short)(this.subInputStream.readBits(2) & 0xFL);
        this.sbHuffDS = (short)(this.subInputStream.readBits(2) & 0xFL);
        this.sbHuffFS = (short)(this.subInputStream.readBits(2) & 0xFL);
    }
    
    private void readUseRefinement() throws IOException {
        if (this.useRefinement && this.sbrTemplate == 0) {
            this.sbrATX = new short[2];
            this.sbrATY = new short[2];
            this.sbrATX[0] = this.subInputStream.readByte();
            this.sbrATY[0] = this.subInputStream.readByte();
            this.sbrATX[1] = this.subInputStream.readByte();
            this.sbrATY[1] = this.subInputStream.readByte();
        }
    }
    
    private void readAmountOfSymbolInstances() throws IOException {
        this.amountOfSymbolInstances = (this.subInputStream.readBits(32) & -1L);
        final long n = this.regionInfo.getBitmapWidth() * (long)this.regionInfo.getBitmapHeight();
        if (n < this.amountOfSymbolInstances) {
            this.log.warn("Limiting number of decoded symbol instances to one per pixel (" + n + " instead of " + this.amountOfSymbolInstances + ")");
            this.amountOfSymbolInstances = n;
        }
    }
    
    private void getSymbols() throws IOException, IntegerMaxValueException, InvalidHeaderValueException {
        if (this.segmentHeader.getRtSegments() != null) {
            this.initSymbols();
        }
    }
    
    private void computeSymbolCodeLength() throws IOException {
        if (this.isHuffmanEncoded) {
            this.symbolIDCodeLengths();
        }
        else {
            this.symbolCodeLength = (int)Math.ceil(Math.log(this.amountOfSymbols) / Math.log(2.0));
        }
    }
    
    private void checkInput() throws InvalidHeaderValueException {
        if (!this.useRefinement && this.sbrTemplate != 0) {
            this.log.info("sbrTemplate should be 0");
            this.sbrTemplate = 0;
        }
        if (this.sbHuffFS == 2 || this.sbHuffRDWidth == 2 || this.sbHuffRDHeight == 2 || this.sbHuffRDX == 2 || this.sbHuffRDY == 2) {
            throw new InvalidHeaderValueException("Huffman flag value of text region segment is not permitted");
        }
        if (!this.useRefinement) {
            if (this.sbHuffRSize != 0) {
                this.log.info("sbHuffRSize should be 0");
                this.sbHuffRSize = 0;
            }
            if (this.sbHuffRDY != 0) {
                this.log.info("sbHuffRDY should be 0");
                this.sbHuffRDY = 0;
            }
            if (this.sbHuffRDX != 0) {
                this.log.info("sbHuffRDX should be 0");
                this.sbHuffRDX = 0;
            }
            if (this.sbHuffRDWidth != 0) {
                this.log.info("sbHuffRDWidth should be 0");
                this.sbHuffRDWidth = 0;
            }
            if (this.sbHuffRDHeight != 0) {
                this.log.info("sbHuffRDHeight should be 0");
                this.sbHuffRDHeight = 0;
            }
        }
    }
    
    @Override
    public Bitmap getRegionBitmap() throws IOException, IntegerMaxValueException, InvalidHeaderValueException {
        if (!this.isHuffmanEncoded) {
            this.setCodingStatistics();
        }
        this.createRegionBitmap();
        this.decodeSymbolInstances();
        return this.regionBitmap;
    }
    
    private void setCodingStatistics() throws IOException {
        if (this.cxIADT == null) {
            this.cxIADT = new CX(512, 1);
        }
        if (this.cxIAFS == null) {
            this.cxIAFS = new CX(512, 1);
        }
        if (this.cxIADS == null) {
            this.cxIADS = new CX(512, 1);
        }
        if (this.cxIAIT == null) {
            this.cxIAIT = new CX(512, 1);
        }
        if (this.cxIARI == null) {
            this.cxIARI = new CX(512, 1);
        }
        if (this.cxIARDW == null) {
            this.cxIARDW = new CX(512, 1);
        }
        if (this.cxIARDH == null) {
            this.cxIARDH = new CX(512, 1);
        }
        if (this.cxIAID == null) {
            this.cxIAID = new CX(1 << this.symbolCodeLength, 1);
        }
        if (this.cxIARDX == null) {
            this.cxIARDX = new CX(512, 1);
        }
        if (this.cxIARDY == null) {
            this.cxIARDY = new CX(512, 1);
        }
        if (this.arithmeticDecoder == null) {
            this.arithmeticDecoder = new ArithmeticDecoder(this.subInputStream);
        }
        if (this.integerDecoder == null) {
            this.integerDecoder = new ArithmeticIntegerDecoder(this.arithmeticDecoder);
        }
    }
    
    private void createRegionBitmap() {
        this.regionBitmap = new Bitmap(this.regionInfo.getBitmapWidth(), this.regionInfo.getBitmapHeight());
        if (this.defaultPixel != 0) {
            Arrays.fill(this.regionBitmap.getByteArray(), (byte)(-1));
        }
    }
    
    private final long decodeStripT() throws IOException, InvalidHeaderValueException {
        long n2;
        if (this.isHuffmanEncoded) {
            if (this.sbHuffDT == 3) {
                if (this.table == null) {
                    int n = 0;
                    if (this.sbHuffFS == 3) {
                        ++n;
                    }
                    if (this.sbHuffDS == 3) {
                        ++n;
                    }
                    this.table = this.getUserTable(n);
                }
                n2 = this.table.decode(this.subInputStream);
            }
            else {
                n2 = StandardTables.getTable(11 + this.sbHuffDT).decode(this.subInputStream);
            }
        }
        else {
            n2 = this.integerDecoder.decode(this.cxIADT);
        }
        return n2 * -this.sbStrips;
    }
    
    private void decodeSymbolInstances() throws IOException, InvalidHeaderValueException, IntegerMaxValueException {
        long decodeStripT = this.decodeStripT();
        long currentS = 0L;
        long n = 0L;
        while (n < this.amountOfSymbolInstances) {
            decodeStripT += this.decodeDT();
            int n2 = 1;
            this.currentS = 0L;
            while (true) {
                if (n2 != 0) {
                    currentS += this.decodeDfS();
                    this.currentS = currentS;
                    n2 = 0;
                }
                else {
                    final long decodeIdS = this.decodeIdS();
                    if (decodeIdS == Long.MAX_VALUE || n >= this.amountOfSymbolInstances) {
                        break;
                    }
                    this.currentS += decodeIdS + this.sbdsOffset;
                }
                this.blit(this.decodeIb(this.decodeRI(), this.decodeID()), decodeStripT + this.decodeCurrentT());
                ++n;
            }
        }
    }
    
    private final long decodeDT() throws IOException {
        long n;
        if (this.isHuffmanEncoded) {
            if (this.sbHuffDT == 3) {
                n = this.table.decode(this.subInputStream);
            }
            else {
                n = StandardTables.getTable(11 + this.sbHuffDT).decode(this.subInputStream);
            }
        }
        else {
            n = this.integerDecoder.decode(this.cxIADT);
        }
        return n * this.sbStrips;
    }
    
    private final long decodeDfS() throws IOException, InvalidHeaderValueException {
        if (!this.isHuffmanEncoded) {
            return this.integerDecoder.decode(this.cxIAFS);
        }
        if (this.sbHuffFS == 3) {
            if (this.fsTable == null) {
                this.fsTable = this.getUserTable(0);
            }
            return this.fsTable.decode(this.subInputStream);
        }
        return StandardTables.getTable(6 + this.sbHuffFS).decode(this.subInputStream);
    }
    
    private final long decodeIdS() throws IOException, InvalidHeaderValueException {
        if (!this.isHuffmanEncoded) {
            return this.integerDecoder.decode(this.cxIADS);
        }
        if (this.sbHuffDS == 3) {
            if (this.dsTable == null) {
                int n = 0;
                if (this.sbHuffFS == 3) {
                    ++n;
                }
                this.dsTable = this.getUserTable(n);
            }
            return this.dsTable.decode(this.subInputStream);
        }
        return StandardTables.getTable(8 + this.sbHuffDS).decode(this.subInputStream);
    }
    
    private final long decodeCurrentT() throws IOException {
        if (this.sbStrips == 1) {
            return 0L;
        }
        if (this.isHuffmanEncoded) {
            return this.subInputStream.readBits(this.logSBStrips);
        }
        return this.integerDecoder.decode(this.cxIAIT);
    }
    
    private final long decodeID() throws IOException {
        if (!this.isHuffmanEncoded) {
            return this.integerDecoder.decodeIAID(this.cxIAID, this.symbolCodeLength);
        }
        if (this.symbolCodeTable == null) {
            return this.subInputStream.readBits(this.symbolCodeLength);
        }
        return this.symbolCodeTable.decode(this.subInputStream);
    }
    
    private final long decodeRI() throws IOException {
        if (!this.useRefinement) {
            return 0L;
        }
        if (this.isHuffmanEncoded) {
            return this.subInputStream.readBit();
        }
        return this.integerDecoder.decode(this.cxIARI);
    }
    
    private final Bitmap decodeIb(final long n, final long n2) throws IOException, InvalidHeaderValueException, IntegerMaxValueException {
        Bitmap regionBitmap;
        if (n == 0L) {
            regionBitmap = this.symbols.get((int)n2);
        }
        else {
            final long decodeRdw = this.decodeRdw();
            final long decodeRdh = this.decodeRdh();
            final long decodeRdx = this.decodeRdx();
            final long decodeRdy = this.decodeRdy();
            if (this.isHuffmanEncoded) {
                this.decodeSymInRefSize();
                this.subInputStream.skipBits();
            }
            final Bitmap bitmap = this.symbols.get((int)n2);
            final int width = bitmap.getWidth();
            final int height = bitmap.getHeight();
            final int n3 = (int)((decodeRdw >> 1) + decodeRdx);
            final int n4 = (int)((decodeRdh >> 1) + decodeRdy);
            if (this.genericRefinementRegion == null) {
                this.genericRefinementRegion = new GenericRefinementRegion(this.subInputStream);
            }
            this.genericRefinementRegion.setParameters(this.cx, this.arithmeticDecoder, this.sbrTemplate, (int)(width + decodeRdw), (int)(height + decodeRdh), bitmap, n3, n4, false, this.sbrATX, this.sbrATY);
            regionBitmap = this.genericRefinementRegion.getRegionBitmap();
            if (this.isHuffmanEncoded) {
                this.subInputStream.skipBits();
            }
        }
        return regionBitmap;
    }
    
    private final long decodeRdw() throws IOException, InvalidHeaderValueException {
        if (!this.isHuffmanEncoded) {
            return this.integerDecoder.decode(this.cxIARDW);
        }
        if (this.sbHuffRDWidth == 3) {
            if (this.rdwTable == null) {
                int n = 0;
                if (this.sbHuffFS == 3) {
                    ++n;
                }
                if (this.sbHuffDS == 3) {
                    ++n;
                }
                if (this.sbHuffDT == 3) {
                    ++n;
                }
                this.rdwTable = this.getUserTable(n);
            }
            return this.rdwTable.decode(this.subInputStream);
        }
        return StandardTables.getTable(14 + this.sbHuffRDWidth).decode(this.subInputStream);
    }
    
    private final long decodeRdh() throws IOException, InvalidHeaderValueException {
        if (!this.isHuffmanEncoded) {
            return this.integerDecoder.decode(this.cxIARDH);
        }
        if (this.sbHuffRDHeight == 3) {
            if (this.rdhTable == null) {
                int n = 0;
                if (this.sbHuffFS == 3) {
                    ++n;
                }
                if (this.sbHuffDS == 3) {
                    ++n;
                }
                if (this.sbHuffDT == 3) {
                    ++n;
                }
                if (this.sbHuffRDWidth == 3) {
                    ++n;
                }
                this.rdhTable = this.getUserTable(n);
            }
            return this.rdhTable.decode(this.subInputStream);
        }
        return StandardTables.getTable(14 + this.sbHuffRDHeight).decode(this.subInputStream);
    }
    
    private final long decodeRdx() throws IOException, InvalidHeaderValueException {
        if (!this.isHuffmanEncoded) {
            return this.integerDecoder.decode(this.cxIARDX);
        }
        if (this.sbHuffRDX == 3) {
            if (this.rdxTable == null) {
                int n = 0;
                if (this.sbHuffFS == 3) {
                    ++n;
                }
                if (this.sbHuffDS == 3) {
                    ++n;
                }
                if (this.sbHuffDT == 3) {
                    ++n;
                }
                if (this.sbHuffRDWidth == 3) {
                    ++n;
                }
                if (this.sbHuffRDHeight == 3) {
                    ++n;
                }
                this.rdxTable = this.getUserTable(n);
            }
            return this.rdxTable.decode(this.subInputStream);
        }
        return StandardTables.getTable(14 + this.sbHuffRDX).decode(this.subInputStream);
    }
    
    private final long decodeRdy() throws IOException, InvalidHeaderValueException {
        if (!this.isHuffmanEncoded) {
            return this.integerDecoder.decode(this.cxIARDY);
        }
        if (this.sbHuffRDY == 3) {
            if (this.rdyTable == null) {
                int n = 0;
                if (this.sbHuffFS == 3) {
                    ++n;
                }
                if (this.sbHuffDS == 3) {
                    ++n;
                }
                if (this.sbHuffDT == 3) {
                    ++n;
                }
                if (this.sbHuffRDWidth == 3) {
                    ++n;
                }
                if (this.sbHuffRDHeight == 3) {
                    ++n;
                }
                if (this.sbHuffRDX == 3) {
                    ++n;
                }
                this.rdyTable = this.getUserTable(n);
            }
            return this.rdyTable.decode(this.subInputStream);
        }
        return StandardTables.getTable(14 + this.sbHuffRDY).decode(this.subInputStream);
    }
    
    private final long decodeSymInRefSize() throws IOException, InvalidHeaderValueException {
        if (this.sbHuffRSize == 0) {
            return StandardTables.getTable(1).decode(this.subInputStream);
        }
        if (this.rSizeTable == null) {
            int n = 0;
            if (this.sbHuffFS == 3) {
                ++n;
            }
            if (this.sbHuffDS == 3) {
                ++n;
            }
            if (this.sbHuffDT == 3) {
                ++n;
            }
            if (this.sbHuffRDWidth == 3) {
                ++n;
            }
            if (this.sbHuffRDHeight == 3) {
                ++n;
            }
            if (this.sbHuffRDX == 3) {
                ++n;
            }
            if (this.sbHuffRDY == 3) {
                ++n;
            }
            this.rSizeTable = this.getUserTable(n);
        }
        return this.rSizeTable.decode(this.subInputStream);
    }
    
    private final void blit(final Bitmap bitmap, long n) {
        if (this.isTransposed == 0 && (this.referenceCorner == 2 || this.referenceCorner == 3)) {
            this.currentS += bitmap.getWidth() - 1;
        }
        else if (this.isTransposed == 1 && (this.referenceCorner == 0 || this.referenceCorner == 2)) {
            this.currentS += bitmap.getHeight() - 1;
        }
        long currentS = this.currentS;
        if (this.isTransposed == 1) {
            final long n2 = n;
            n = currentS;
            currentS = n2;
        }
        if (this.referenceCorner != 1) {
            if (this.referenceCorner == 0) {
                n -= bitmap.getHeight() - 1;
            }
            else if (this.referenceCorner == 2) {
                n -= bitmap.getHeight() - 1;
                currentS -= bitmap.getWidth() - 1;
            }
            else if (this.referenceCorner == 3) {
                currentS -= bitmap.getWidth() - 1;
            }
        }
        Bitmaps.blit(bitmap, this.regionBitmap, (int)currentS, (int)n, this.combinationOperator);
        if (this.isTransposed == 0 && (this.referenceCorner == 0 || this.referenceCorner == 1)) {
            this.currentS += bitmap.getWidth() - 1;
        }
        if (this.isTransposed == 1 && (this.referenceCorner == 1 || this.referenceCorner == 3)) {
            this.currentS += bitmap.getHeight() - 1;
        }
    }
    
    private void initSymbols() throws IOException, IntegerMaxValueException, InvalidHeaderValueException {
        for (final SegmentHeader segmentHeader : this.segmentHeader.getRtSegments()) {
            if (segmentHeader.getSegmentType() == 0) {
                final SymbolDictionary symbolDictionary = (SymbolDictionary)segmentHeader.getSegmentData();
                symbolDictionary.cxIAID = this.cxIAID;
                this.symbols.addAll(symbolDictionary.getDictionary());
            }
        }
        this.amountOfSymbols = this.symbols.size();
    }
    
    private HuffmanTable getUserTable(final int n) throws InvalidHeaderValueException, IOException {
        int n2 = 0;
        for (final SegmentHeader segmentHeader : this.segmentHeader.getRtSegments()) {
            if (segmentHeader.getSegmentType() == 53) {
                if (n2 == n) {
                    return new EncodedTable((Table)segmentHeader.getSegmentData());
                }
                ++n2;
            }
        }
        return null;
    }
    
    private void symbolIDCodeLengths() throws IOException {
        final ArrayList<HuffmanTable.Code> list = new ArrayList<HuffmanTable.Code>();
        for (int i = 0; i < 35; ++i) {
            final int n = (int)(this.subInputStream.readBits(4) & 0xFL);
            if (n > 0) {
                list.add(new HuffmanTable.Code(n, 0, i, false));
            }
        }
        final FixedSizeTable fixedSizeTable = new FixedSizeTable(list);
        long n2 = 0L;
        int j = 0;
        final ArrayList<HuffmanTable.Code> list2 = new ArrayList<HuffmanTable.Code>();
        while (j < this.amountOfSymbols) {
            final long decode = fixedSizeTable.decode(this.subInputStream);
            if (decode < 32L) {
                if (decode > 0L) {
                    list2.add(new HuffmanTable.Code((int)decode, 0, j, false));
                }
                n2 = decode;
                ++j;
            }
            else {
                long n3 = 0L;
                long n4 = 0L;
                if (decode == 32L) {
                    n3 = 3L + this.subInputStream.readBits(2);
                    if (j > 0) {
                        n4 = n2;
                    }
                }
                else if (decode == 33L) {
                    n3 = 3L + this.subInputStream.readBits(3);
                }
                else if (decode == 34L) {
                    n3 = 11L + this.subInputStream.readBits(7);
                }
                for (int n5 = 0; n5 < n3; ++n5) {
                    if (n4 > 0L) {
                        list2.add(new HuffmanTable.Code((int)n4, 0, j, false));
                    }
                    ++j;
                }
            }
        }
        this.subInputStream.skipBits();
        this.symbolCodeTable = new FixedSizeTable(list2);
    }
    
    @Override
    public void init(final SegmentHeader segmentHeader, final SubInputStream subInputStream) throws InvalidHeaderValueException, IntegerMaxValueException, IOException {
        this.segmentHeader = segmentHeader;
        this.subInputStream = subInputStream;
        this.regionInfo = new RegionSegmentInformation(this.subInputStream);
        this.parseHeader();
    }
    
    protected void setContexts(final CX cx, final CX cxIADT, final CX cxIAFS, final CX cxIADS, final CX cxIAIT, final CX cxIAID, final CX cxIARDW, final CX cxIARDH, final CX cxIARDX, final CX cxIARDY) {
        this.cx = cx;
        this.cxIADT = cxIADT;
        this.cxIAFS = cxIAFS;
        this.cxIADS = cxIADS;
        this.cxIAIT = cxIAIT;
        this.cxIAID = cxIAID;
        this.cxIARDW = cxIARDW;
        this.cxIARDH = cxIARDH;
        this.cxIARDX = cxIARDX;
        this.cxIARDY = cxIARDY;
    }
    
    protected void setParameters(final ArithmeticDecoder arithmeticDecoder, final ArithmeticIntegerDecoder integerDecoder, final boolean isHuffmanEncoded, final boolean useRefinement, final int bitmapWidth, final int bitmapHeight, final long amountOfSymbolInstances, final int sbStrips, final int amountOfSymbols, final short defaultPixel, final short n, final short isTransposed, final short referenceCorner, final short sbdsOffset, final short sbHuffFS, final short sbHuffDS, final short sbHuffDT, final short sbHuffRDWidth, final short sbHuffRDHeight, final short sbHuffRDX, final short sbHuffRDY, final short sbHuffRSize, final short sbrTemplate, final short[] sbrATX, final short[] sbrATY, final ArrayList<Bitmap> symbols, final int symbolCodeLength) {
        this.arithmeticDecoder = arithmeticDecoder;
        this.integerDecoder = integerDecoder;
        this.isHuffmanEncoded = isHuffmanEncoded;
        this.useRefinement = useRefinement;
        this.regionInfo.setBitmapWidth(bitmapWidth);
        this.regionInfo.setBitmapHeight(bitmapHeight);
        this.amountOfSymbolInstances = amountOfSymbolInstances;
        this.sbStrips = sbStrips;
        this.amountOfSymbols = amountOfSymbols;
        this.defaultPixel = defaultPixel;
        this.combinationOperator = CombinationOperator.translateOperatorCodeToEnum(n);
        this.isTransposed = isTransposed;
        this.referenceCorner = referenceCorner;
        this.sbdsOffset = sbdsOffset;
        this.sbHuffFS = sbHuffFS;
        this.sbHuffDS = sbHuffDS;
        this.sbHuffDT = sbHuffDT;
        this.sbHuffRDWidth = sbHuffRDWidth;
        this.sbHuffRDHeight = sbHuffRDHeight;
        this.sbHuffRDX = sbHuffRDX;
        this.sbHuffRDY = sbHuffRDY;
        this.sbHuffRSize = sbHuffRSize;
        this.sbrTemplate = sbrTemplate;
        this.sbrATX = sbrATX;
        this.sbrATY = sbrATY;
        this.symbols = symbols;
        this.symbolCodeLength = symbolCodeLength;
    }
    
    @Override
    public RegionSegmentInformation getRegionInfo() {
        return this.regionInfo;
    }
}
