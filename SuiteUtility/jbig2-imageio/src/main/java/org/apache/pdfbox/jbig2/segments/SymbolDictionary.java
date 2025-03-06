// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.pdfbox.jbig2.segments;

import org.apache.pdfbox.jbig2.decoder.huffman.EncodedTable;
import java.util.Collection;
import org.apache.pdfbox.jbig2.Region;
import org.apache.pdfbox.jbig2.image.Bitmaps;
import java.awt.Rectangle;
import javax.imageio.stream.ImageInputStream;
import org.apache.pdfbox.jbig2.decoder.huffman.StandardTables;
import org.apache.pdfbox.jbig2.err.IntegerMaxValueException;
import org.apache.pdfbox.jbig2.err.InvalidHeaderValueException;
import java.io.IOException;
import org.apache.pdfbox.jbig2.util.log.LoggerFactory;
import org.apache.pdfbox.jbig2.decoder.arithmetic.CX;
import org.apache.pdfbox.jbig2.decoder.arithmetic.ArithmeticIntegerDecoder;
import org.apache.pdfbox.jbig2.decoder.arithmetic.ArithmeticDecoder;
import org.apache.pdfbox.jbig2.decoder.huffman.HuffmanTable;
import org.apache.pdfbox.jbig2.Bitmap;
import java.util.ArrayList;
import org.apache.pdfbox.jbig2.SegmentHeader;
import org.apache.pdfbox.jbig2.io.SubInputStream;
import org.apache.pdfbox.jbig2.util.log.Logger;
import org.apache.pdfbox.jbig2.Dictionary;

public class SymbolDictionary implements Dictionary
{
    private final Logger log;
    private SubInputStream subInputStream;
    private short sdrTemplate;
    private byte sdTemplate;
    private boolean isCodingContextRetained;
    private boolean isCodingContextUsed;
    private short sdHuffAggInstanceSelection;
    private short sdHuffBMSizeSelection;
    private short sdHuffDecodeWidthSelection;
    private short sdHuffDecodeHeightSelection;
    private boolean useRefinementAggregation;
    private boolean isHuffmanEncoded;
    private short[] sdATX;
    private short[] sdATY;
    private short[] sdrATX;
    private short[] sdrATY;
    private int amountOfExportSymbolss;
    private int amountOfNewSymbols;
    private SegmentHeader segmentHeader;
    private int amountOfImportedSymbolss;
    private ArrayList<Bitmap> importSymbols;
    private int amountOfDecodedSymbols;
    private Bitmap[] newSymbols;
    private HuffmanTable dhTable;
    private HuffmanTable dwTable;
    private HuffmanTable bmSizeTable;
    private HuffmanTable aggInstTable;
    private ArrayList<Bitmap> exportSymbols;
    private ArrayList<Bitmap> sbSymbols;
    private ArithmeticDecoder arithmeticDecoder;
    private ArithmeticIntegerDecoder iDecoder;
    private TextRegion textRegion;
    private GenericRegion genericRegion;
    private GenericRefinementRegion genericRefinementRegion;
    private CX cx;
    private CX cxIADH;
    private CX cxIADW;
    private CX cxIAAI;
    private CX cxIAEX;
    private CX cxIARDX;
    private CX cxIARDY;
    private CX cxIADT;
    protected CX cxIAID;
    private int sbSymCodeLen;
    
    public SymbolDictionary() {
        this.log = LoggerFactory.getLogger(SymbolDictionary.class);
    }
    
    public SymbolDictionary(final SubInputStream subInputStream, final SegmentHeader segmentHeader) throws IOException {
        this.log = LoggerFactory.getLogger(SymbolDictionary.class);
        this.subInputStream = subInputStream;
        this.segmentHeader = segmentHeader;
    }
    
    private void parseHeader() throws IOException, InvalidHeaderValueException, IntegerMaxValueException {
        this.readRegionFlags();
        this.setAtPixels();
        this.setRefinementAtPixels();
        this.readAmountOfExportedSymbols();
        this.readAmountOfNewSymbols();
        this.setInSyms();
        if (this.isCodingContextUsed) {
            final SegmentHeader[] rtSegments = this.segmentHeader.getRtSegments();
            int i = rtSegments.length - 1;
            while (i >= 0) {
                if (rtSegments[i].getSegmentType() == 0) {
                    final SymbolDictionary retainedCodingContexts = (SymbolDictionary)rtSegments[i].getSegmentData();
                    if (retainedCodingContexts.isCodingContextRetained) {
                        this.setRetainedCodingContexts(retainedCodingContexts);
                        break;
                    }
                    break;
                }
                else {
                    --i;
                }
            }
        }
        this.checkInput();
    }
    
    private void readRegionFlags() throws IOException {
        this.subInputStream.readBits(3);
        this.sdrTemplate = (short)this.subInputStream.readBit();
        this.sdTemplate = (byte)(this.subInputStream.readBits(2) & 0xFL);
        if (this.subInputStream.readBit() == 1) {
            this.isCodingContextRetained = true;
        }
        if (this.subInputStream.readBit() == 1) {
            this.isCodingContextUsed = true;
        }
        this.sdHuffAggInstanceSelection = (short)this.subInputStream.readBit();
        this.sdHuffBMSizeSelection = (short)this.subInputStream.readBit();
        this.sdHuffDecodeWidthSelection = (short)(this.subInputStream.readBits(2) & 0xFL);
        this.sdHuffDecodeHeightSelection = (short)(this.subInputStream.readBits(2) & 0xFL);
        if (this.subInputStream.readBit() == 1) {
            this.useRefinementAggregation = true;
        }
        if (this.subInputStream.readBit() == 1) {
            this.isHuffmanEncoded = true;
        }
    }
    
    private void setAtPixels() throws IOException {
        if (!this.isHuffmanEncoded) {
            if (this.sdTemplate == 0) {
                this.readAtPixels(4);
            }
            else {
                this.readAtPixels(1);
            }
        }
    }
    
    private void setRefinementAtPixels() throws IOException {
        if (this.useRefinementAggregation && this.sdrTemplate == 0) {
            this.readRefinementAtPixels(2);
        }
    }
    
    private void readAtPixels(final int n) throws IOException {
        this.sdATX = new short[n];
        this.sdATY = new short[n];
        for (int i = 0; i < n; ++i) {
            this.sdATX[i] = this.subInputStream.readByte();
            this.sdATY[i] = this.subInputStream.readByte();
        }
    }
    
    private void readRefinementAtPixels(final int n) throws IOException {
        this.sdrATX = new short[n];
        this.sdrATY = new short[n];
        for (int i = 0; i < n; ++i) {
            this.sdrATX[i] = this.subInputStream.readByte();
            this.sdrATY[i] = this.subInputStream.readByte();
        }
    }
    
    private void readAmountOfExportedSymbols() throws IOException {
        this.amountOfExportSymbolss = (int)this.subInputStream.readBits(32);
    }
    
    private void readAmountOfNewSymbols() throws IOException {
        this.amountOfNewSymbols = (int)this.subInputStream.readBits(32);
    }
    
    private void setInSyms() throws IOException, InvalidHeaderValueException, IntegerMaxValueException {
        if (this.segmentHeader.getRtSegments() != null) {
            this.retrieveImportSymbols();
        }
        else {
            this.importSymbols = new ArrayList<Bitmap>();
        }
    }
    
    private void setRetainedCodingContexts(final SymbolDictionary symbolDictionary) {
        this.arithmeticDecoder = symbolDictionary.arithmeticDecoder;
        this.isHuffmanEncoded = symbolDictionary.isHuffmanEncoded;
        this.useRefinementAggregation = symbolDictionary.useRefinementAggregation;
        this.sdTemplate = symbolDictionary.sdTemplate;
        this.sdrTemplate = symbolDictionary.sdrTemplate;
        this.sdATX = symbolDictionary.sdATX;
        this.sdATY = symbolDictionary.sdATY;
        this.sdrATX = symbolDictionary.sdrATX;
        this.sdrATY = symbolDictionary.sdrATY;
        this.cx = symbolDictionary.cx;
    }
    
    private void checkInput() throws InvalidHeaderValueException {
        if (this.sdHuffDecodeHeightSelection == 2) {
            this.log.info("sdHuffDecodeHeightSelection = " + this.sdHuffDecodeHeightSelection + " (value not permitted)");
        }
        if (this.sdHuffDecodeWidthSelection == 2) {
            this.log.info("sdHuffDecodeWidthSelection = " + this.sdHuffDecodeWidthSelection + " (value not permitted)");
        }
        if (this.isHuffmanEncoded) {
            if (this.sdTemplate != 0) {
                this.log.info("sdTemplate = " + this.sdTemplate + " (should be 0)");
                this.sdTemplate = 0;
            }
            if (!this.useRefinementAggregation) {
                if (this.isCodingContextRetained) {
                    this.log.info("isCodingContextRetained = " + this.isCodingContextRetained + " (should be 0)");
                    this.isCodingContextRetained = false;
                }
                if (this.isCodingContextUsed) {
                    this.log.info("isCodingContextUsed = " + this.isCodingContextUsed + " (should be 0)");
                    this.isCodingContextUsed = false;
                }
            }
        }
        else {
            if (this.sdHuffBMSizeSelection != 0) {
                this.log.info("sdHuffBMSizeSelection should be 0");
                this.sdHuffBMSizeSelection = 0;
            }
            if (this.sdHuffDecodeWidthSelection != 0) {
                this.log.info("sdHuffDecodeWidthSelection should be 0");
                this.sdHuffDecodeWidthSelection = 0;
            }
            if (this.sdHuffDecodeHeightSelection != 0) {
                this.log.info("sdHuffDecodeHeightSelection should be 0");
                this.sdHuffDecodeHeightSelection = 0;
            }
        }
        if (!this.useRefinementAggregation && this.sdrTemplate != 0) {
            this.log.info("sdrTemplate = " + this.sdrTemplate + " (should be 0)");
            this.sdrTemplate = 0;
        }
        if ((!this.isHuffmanEncoded || !this.useRefinementAggregation) && this.sdHuffAggInstanceSelection != 0) {
            this.log.info("sdHuffAggInstanceSelection = " + this.sdHuffAggInstanceSelection + " (should be 0)");
            this.sdHuffAggInstanceSelection = 0;
        }
    }
    
    @Override
    public ArrayList<Bitmap> getDictionary() throws IOException, IntegerMaxValueException, InvalidHeaderValueException {
        System.currentTimeMillis();
        if (null == this.exportSymbols) {
            if (this.useRefinementAggregation) {
                this.sbSymCodeLen = this.getSbSymCodeLen();
            }
            if (!this.isHuffmanEncoded) {
                this.setCodingStatistics();
            }
            this.newSymbols = new Bitmap[this.amountOfNewSymbols];
            int[] array = null;
            if (this.isHuffmanEncoded && !this.useRefinementAggregation) {
                array = new int[this.amountOfNewSymbols];
            }
            this.setSymbolsArray();
            int n = 0;
            this.amountOfDecodedSymbols = 0;
            while (this.amountOfDecodedSymbols < this.amountOfNewSymbols) {
                n += (int)this.decodeHeightClassDeltaHeight();
                int n2 = 0;
                int n3 = 0;
                final int amountOfDecodedSymbols = this.amountOfDecodedSymbols;
                while (true) {
                    final long decodeDifferenceWidth = this.decodeDifferenceWidth();
                    if (decodeDifferenceWidth == Long.MAX_VALUE || this.amountOfDecodedSymbols >= this.amountOfNewSymbols) {
                        break;
                    }
                    n2 += (int)decodeDifferenceWidth;
                    n3 += n2;
                    if (!this.isHuffmanEncoded || this.useRefinementAggregation) {
                        if (!this.useRefinementAggregation) {
                            this.decodeDirectlyThroughGenericRegion(n2, n);
                        }
                        else {
                            this.decodeAggregate(n2, n);
                        }
                    }
                    else if (this.isHuffmanEncoded && !this.useRefinementAggregation) {
                        array[this.amountOfDecodedSymbols] = n2;
                    }
                    ++this.amountOfDecodedSymbols;
                }
                if (this.isHuffmanEncoded && !this.useRefinementAggregation) {
                    long n4;
                    if (this.sdHuffBMSizeSelection == 0) {
                        n4 = StandardTables.getTable(1).decode(this.subInputStream);
                    }
                    else {
                        n4 = this.huffDecodeBmSize();
                    }
                    this.subInputStream.skipBits();
                    final Bitmap decodeHeightClassCollectiveBitmap = this.decodeHeightClassCollectiveBitmap(n4, n, n3);
                    this.subInputStream.skipBits();
                    this.decodeHeightClassBitmap(decodeHeightClassCollectiveBitmap, amountOfDecodedSymbols, n, array);
                }
            }
            this.setExportedSymbols(this.getToExportFlags());
        }
        return this.exportSymbols;
    }
    
    private void setCodingStatistics() throws IOException {
        if (this.cxIADT == null) {
            this.cxIADT = new CX(512, 1);
        }
        if (this.cxIADH == null) {
            this.cxIADH = new CX(512, 1);
        }
        if (this.cxIADW == null) {
            this.cxIADW = new CX(512, 1);
        }
        if (this.cxIAAI == null) {
            this.cxIAAI = new CX(512, 1);
        }
        if (this.cxIAEX == null) {
            this.cxIAEX = new CX(512, 1);
        }
        if (this.useRefinementAggregation && this.cxIAID == null) {
            this.cxIAID = new CX(1 << this.sbSymCodeLen, 1);
            this.cxIARDX = new CX(512, 1);
            this.cxIARDY = new CX(512, 1);
        }
        if (this.cx == null) {
            this.cx = new CX(65536, 1);
        }
        if (this.arithmeticDecoder == null) {
            this.arithmeticDecoder = new ArithmeticDecoder(this.subInputStream);
        }
        if (this.iDecoder == null) {
            this.iDecoder = new ArithmeticIntegerDecoder(this.arithmeticDecoder);
        }
    }
    
    private final void decodeHeightClassBitmap(final Bitmap bitmap, final int n, final int height, final int[] array) throws IntegerMaxValueException, InvalidHeaderValueException, IOException {
        for (int i = n; i < this.amountOfDecodedSymbols; ++i) {
            int x = 0;
            for (int j = n; j <= i - 1; ++j) {
                x += array[j];
            }
            final Bitmap extract = Bitmaps.extract(new Rectangle(x, 0, array[i], height), bitmap);
            this.newSymbols[i] = extract;
            this.sbSymbols.add(extract);
        }
    }
    
    private final void decodeAggregate(final int n, final int n2) throws IOException, InvalidHeaderValueException, IntegerMaxValueException {
        long n3;
        if (this.isHuffmanEncoded) {
            this.log.info("Refinement or aggregate-coded symbols may couse problems with huffman decoding!");
            n3 = this.huffDecodeRefAggNInst();
        }
        else {
            n3 = this.iDecoder.decode(this.cxIAAI);
        }
        if (n3 > 1L) {
            this.decodeThroughTextRegion(n, n2, n3);
        }
        else if (n3 == 1L) {
            this.decodeRefinedSymbol(n, n2);
        }
    }
    
    private final long huffDecodeRefAggNInst() throws IOException, InvalidHeaderValueException {
        if (this.sdHuffAggInstanceSelection == 0) {
            return StandardTables.getTable(1).decode(this.subInputStream);
        }
        if (this.sdHuffAggInstanceSelection == 1) {
            if (this.aggInstTable == null) {
                int n = 0;
                if (this.sdHuffDecodeHeightSelection == 3) {
                    ++n;
                }
                if (this.sdHuffDecodeWidthSelection == 3) {
                    ++n;
                }
                if (this.sdHuffBMSizeSelection == 3) {
                    ++n;
                }
                this.aggInstTable = this.getUserTable(n);
            }
            return this.aggInstTable.decode(this.subInputStream);
        }
        return 0L;
    }
    
    private final void decodeThroughTextRegion(final int n, final int n2, final long n3) throws IOException, IntegerMaxValueException, InvalidHeaderValueException {
        if (this.textRegion == null) {
            (this.textRegion = new TextRegion(this.subInputStream, null)).setContexts(this.cx, new CX(512, 1), new CX(512, 1), new CX(512, 1), new CX(512, 1), this.cxIAID, new CX(512, 1), new CX(512, 1), new CX(512, 1), new CX(512, 1));
        }
        this.setSymbolsArray();
        this.textRegion.setParameters(this.arithmeticDecoder, this.iDecoder, this.isHuffmanEncoded, true, n, n2, n3, 1, this.amountOfImportedSymbolss + this.amountOfDecodedSymbols, (short)0, (short)0, (short)0, (short)1, (short)0, (short)0, (short)0, (short)0, (short)0, (short)0, (short)0, (short)0, (short)0, this.sdrTemplate, this.sdrATX, this.sdrATY, this.sbSymbols, this.sbSymCodeLen);
        this.addSymbol(this.textRegion);
    }
    
    private final void decodeRefinedSymbol(final int n, final int n2) throws IOException, InvalidHeaderValueException, IntegerMaxValueException {
        int decodeIAID;
        int n3;
        int n4;
        if (this.isHuffmanEncoded) {
            decodeIAID = (int)this.subInputStream.readBits(this.sbSymCodeLen);
            n3 = (int)StandardTables.getTable(15).decode(this.subInputStream);
            n4 = (int)StandardTables.getTable(15).decode(this.subInputStream);
            StandardTables.getTable(1).decode(this.subInputStream);
            this.subInputStream.skipBits();
        }
        else {
            decodeIAID = this.iDecoder.decodeIAID(this.cxIAID, this.sbSymCodeLen);
            n3 = (int)this.iDecoder.decode(this.cxIARDX);
            n4 = (int)this.iDecoder.decode(this.cxIARDY);
        }
        this.setSymbolsArray();
        this.decodeNewSymbols(n, n2, this.sbSymbols.get(decodeIAID), n3, n4);
        if (this.isHuffmanEncoded) {
            this.subInputStream.skipBits();
        }
    }
    
    private final void decodeNewSymbols(final int n, final int n2, final Bitmap bitmap, final int n3, final int n4) throws IOException, InvalidHeaderValueException, IntegerMaxValueException {
        if (this.genericRefinementRegion == null) {
            this.genericRefinementRegion = new GenericRefinementRegion(this.subInputStream);
            if (this.arithmeticDecoder == null) {
                this.arithmeticDecoder = new ArithmeticDecoder(this.subInputStream);
            }
            if (this.cx == null) {
                this.cx = new CX(65536, 1);
            }
        }
        this.genericRefinementRegion.setParameters(this.cx, this.arithmeticDecoder, this.sdrTemplate, n, n2, bitmap, n3, n4, false, this.sdrATX, this.sdrATY);
        this.addSymbol(this.genericRefinementRegion);
    }
    
    private final void decodeDirectlyThroughGenericRegion(final int n, final int n2) throws IOException, IntegerMaxValueException, InvalidHeaderValueException {
        if (this.genericRegion == null) {
            this.genericRegion = new GenericRegion(this.subInputStream);
        }
        this.genericRegion.setParameters(false, this.sdTemplate, false, false, this.sdATX, this.sdATY, n, n2, this.cx, this.arithmeticDecoder);
        this.addSymbol(this.genericRegion);
    }
    
    private final void addSymbol(final Region region) throws IntegerMaxValueException, InvalidHeaderValueException, IOException {
        final Bitmap regionBitmap = region.getRegionBitmap();
        this.newSymbols[this.amountOfDecodedSymbols] = regionBitmap;
        this.sbSymbols.add(regionBitmap);
    }
    
    private final long decodeDifferenceWidth() throws IOException, InvalidHeaderValueException {
        if (!this.isHuffmanEncoded) {
            return this.iDecoder.decode(this.cxIADW);
        }
        switch (this.sdHuffDecodeWidthSelection) {
            case 0: {
                return StandardTables.getTable(2).decode(this.subInputStream);
            }
            case 1: {
                return StandardTables.getTable(3).decode(this.subInputStream);
            }
            case 3: {
                if (this.dwTable == null) {
                    int n = 0;
                    if (this.sdHuffDecodeHeightSelection == 3) {
                        ++n;
                    }
                    this.dwTable = this.getUserTable(n);
                }
                return this.dwTable.decode(this.subInputStream);
            }
            default: {
                return 0L;
            }
        }
    }
    
    private final long decodeHeightClassDeltaHeight() throws IOException, InvalidHeaderValueException {
        if (this.isHuffmanEncoded) {
            return this.decodeHeightClassDeltaHeightWithHuffman();
        }
        return this.iDecoder.decode(this.cxIADH);
    }
    
    private final long decodeHeightClassDeltaHeightWithHuffman() throws IOException, InvalidHeaderValueException {
        switch (this.sdHuffDecodeHeightSelection) {
            case 0: {
                return StandardTables.getTable(4).decode(this.subInputStream);
            }
            case 1: {
                return StandardTables.getTable(5).decode(this.subInputStream);
            }
            case 3: {
                if (this.dhTable == null) {
                    this.dhTable = this.getUserTable(0);
                }
                return this.dhTable.decode(this.subInputStream);
            }
            default: {
                return 0L;
            }
        }
    }
    
    private final Bitmap decodeHeightClassCollectiveBitmap(final long n, final int n2, final int n3) throws IOException {
        if (n == 0L) {
            final Bitmap bitmap = new Bitmap(n3, n2);
            for (int i = 0; i < bitmap.getByteArray().length; ++i) {
                bitmap.setByte(i, this.subInputStream.readByte());
            }
            return bitmap;
        }
        if (this.genericRegion == null) {
            this.genericRegion = new GenericRegion(this.subInputStream);
        }
        this.genericRegion.setParameters(true, this.subInputStream.getStreamPosition(), n, n2, n3);
        return this.genericRegion.getRegionBitmap();
    }
    
    private void setExportedSymbols(final int[] array) {
        this.exportSymbols = new ArrayList<Bitmap>(this.amountOfExportSymbolss);
        for (int i = 0; i < this.amountOfImportedSymbolss + this.amountOfNewSymbols; ++i) {
            if (array[i] == 1) {
                if (i < this.amountOfImportedSymbolss) {
                    this.exportSymbols.add(this.importSymbols.get(i));
                }
                else {
                    this.exportSymbols.add(this.newSymbols[i - this.amountOfImportedSymbolss]);
                }
            }
        }
    }
    
    private int[] getToExportFlags() throws IOException, InvalidHeaderValueException {
        boolean b = false;
        final int[] array = new int[this.amountOfImportedSymbolss + this.amountOfNewSymbols];
        long n;
        for (int i = 0; i < this.amountOfImportedSymbolss + this.amountOfNewSymbols; i += (int)n) {
            if (this.isHuffmanEncoded) {
                n = StandardTables.getTable(1).decode(this.subInputStream);
            }
            else {
                n = this.iDecoder.decode(this.cxIAEX);
            }
            if (n != 0L) {
                for (int n2 = i; n2 < i + n; ++n2) {
                    array[n2] = (b ? 1 : 0);
                }
            }
            b = !b;
        }
        return array;
    }
    
    private final long huffDecodeBmSize() throws IOException, InvalidHeaderValueException {
        if (this.bmSizeTable == null) {
            int n = 0;
            if (this.sdHuffDecodeHeightSelection == 3) {
                ++n;
            }
            if (this.sdHuffDecodeWidthSelection == 3) {
                ++n;
            }
            this.bmSizeTable = this.getUserTable(n);
        }
        return this.bmSizeTable.decode(this.subInputStream);
    }
    
    private int getSbSymCodeLen() throws IOException {
        if (this.isHuffmanEncoded) {
            return Math.max((int)Math.ceil(Math.log(this.amountOfImportedSymbolss + this.amountOfNewSymbols) / Math.log(2.0)), 1);
        }
        return (int)Math.ceil(Math.log(this.amountOfImportedSymbolss + this.amountOfNewSymbols) / Math.log(2.0));
    }
    
    private final void setSymbolsArray() throws IOException, InvalidHeaderValueException, IntegerMaxValueException {
        if (this.importSymbols == null) {
            this.retrieveImportSymbols();
        }
        if (this.sbSymbols == null) {
            (this.sbSymbols = new ArrayList<Bitmap>()).addAll(this.importSymbols);
        }
    }
    
    private void retrieveImportSymbols() throws IOException, InvalidHeaderValueException, IntegerMaxValueException {
        this.importSymbols = new ArrayList<Bitmap>();
        for (final SegmentHeader segmentHeader : this.segmentHeader.getRtSegments()) {
            if (segmentHeader.getSegmentType() == 0) {
                final SymbolDictionary symbolDictionary = (SymbolDictionary)segmentHeader.getSegmentData();
                this.importSymbols.addAll(symbolDictionary.getDictionary());
                this.amountOfImportedSymbolss += symbolDictionary.amountOfExportSymbolss;
            }
        }
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
    
    @Override
    public void init(final SegmentHeader segmentHeader, final SubInputStream subInputStream) throws InvalidHeaderValueException, IntegerMaxValueException, IOException {
        this.subInputStream = subInputStream;
        this.segmentHeader = segmentHeader;
        this.parseHeader();
    }
}
