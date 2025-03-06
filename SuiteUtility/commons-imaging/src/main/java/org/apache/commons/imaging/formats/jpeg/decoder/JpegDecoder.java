// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.commons.imaging.formats.jpeg.decoder;

import org.apache.commons.imaging.common.bytesource.ByteSource;
import java.util.Arrays;
import java.awt.image.DataBuffer;
import java.awt.image.WritableRaster;
import java.awt.image.ColorModel;
import java.util.Hashtable;
import java.util.Properties;
import java.awt.Point;
import java.awt.image.Raster;
import java.awt.image.DirectColorModel;
import java.io.InputStream;
import org.apache.commons.imaging.common.BinaryFunctions;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import org.apache.commons.imaging.ImageReadException;
import java.awt.image.BufferedImage;
import org.apache.commons.imaging.formats.jpeg.segments.SosSegment;
import org.apache.commons.imaging.formats.jpeg.segments.SofnSegment;
import org.apache.commons.imaging.formats.jpeg.segments.DhtSegment;
import org.apache.commons.imaging.formats.jpeg.segments.DqtSegment;
import org.apache.commons.imaging.formats.jpeg.JpegUtils;
import org.apache.commons.imaging.common.BinaryFileParser;

public class JpegDecoder extends BinaryFileParser implements JpegUtils.Visitor
{
    private final DqtSegment.QuantizationTable[] quantizationTables;
    private final DhtSegment.HuffmanTable[] huffmanDCTables;
    private final DhtSegment.HuffmanTable[] huffmanACTables;
    private SofnSegment sofnSegment;
    private SosSegment sosSegment;
    private final float[][] scaledQuantizationTables;
    private BufferedImage image;
    private ImageReadException imageReadException;
    private IOException ioException;
    private final int[] zz;
    private final int[] blockInt;
    private final float[] block;
    
    public JpegDecoder() {
        this.quantizationTables = new DqtSegment.QuantizationTable[4];
        this.huffmanDCTables = new DhtSegment.HuffmanTable[4];
        this.huffmanACTables = new DhtSegment.HuffmanTable[4];
        this.scaledQuantizationTables = new float[4][];
        this.zz = new int[64];
        this.blockInt = new int[64];
        this.block = new float[64];
    }
    
    @Override
    public boolean beginSOS() {
        return true;
    }
    
    @Override
    public void visitSOS(final int marker, final byte[] markerBytes, final byte[] imageData) {
        final ByteArrayInputStream is = new ByteArrayInputStream(imageData);
        try {
            final int segmentLength = BinaryFunctions.read2Bytes("segmentLength", is, "Not a Valid JPEG File", this.getByteOrder());
            final byte[] sosSegmentBytes = BinaryFunctions.readBytes("SosSegment", is, segmentLength - 2, "Not a Valid JPEG File");
            this.sosSegment = new SosSegment(marker, sosSegmentBytes);
            int hMax = 0;
            int vMax = 0;
            for (int i = 0; i < this.sofnSegment.numberOfComponents; ++i) {
                hMax = Math.max(hMax, this.sofnSegment.getComponents(i).horizontalSamplingFactor);
                vMax = Math.max(vMax, this.sofnSegment.getComponents(i).verticalSamplingFactor);
            }
            final int hSize = 8 * hMax;
            final int vSize = 8 * vMax;
            final JpegInputStream bitInputStream = new JpegInputStream(is);
            final int xMCUs = (this.sofnSegment.width + hSize - 1) / hSize;
            final int yMCUs = (this.sofnSegment.height + vSize - 1) / vSize;
            final Block[] mcu = this.allocateMCUMemory();
            final Block[] scaledMCU = new Block[mcu.length];
            for (int j = 0; j < scaledMCU.length; ++j) {
                scaledMCU[j] = new Block(hSize, vSize);
            }
            final int[] preds = new int[this.sofnSegment.numberOfComponents];
            ColorModel colorModel;
            WritableRaster raster;
            if (this.sofnSegment.numberOfComponents == 3) {
                colorModel = new DirectColorModel(24, 16711680, 65280, 255);
                raster = Raster.createPackedRaster(3, this.sofnSegment.width, this.sofnSegment.height, new int[] { 16711680, 65280, 255 }, null);
            }
            else {
                if (this.sofnSegment.numberOfComponents != 1) {
                    throw new ImageReadException(this.sofnSegment.numberOfComponents + " components are invalid or unsupported");
                }
                colorModel = new DirectColorModel(24, 16711680, 65280, 255);
                raster = Raster.createPackedRaster(3, this.sofnSegment.width, this.sofnSegment.height, new int[] { 16711680, 65280, 255 }, null);
            }
            final DataBuffer dataBuffer = raster.getDataBuffer();
            for (int y1 = 0; y1 < vSize * yMCUs; y1 += vSize) {
                for (int x1 = 0; x1 < hSize * xMCUs; x1 += hSize) {
                    this.readMCU(bitInputStream, preds, mcu);
                    this.rescaleMCU(mcu, hSize, vSize, scaledMCU);
                    int srcRowOffset = 0;
                    int dstRowOffset = y1 * this.sofnSegment.width + x1;
                    for (int y2 = 0; y2 < vSize && y1 + y2 < this.sofnSegment.height; ++y2) {
                        for (int x2 = 0; x2 < hSize && x1 + x2 < this.sofnSegment.width; ++x2) {
                            if (scaledMCU.length == 3) {
                                final int Y = scaledMCU[0].samples[srcRowOffset + x2];
                                final int Cb = scaledMCU[1].samples[srcRowOffset + x2];
                                final int Cr = scaledMCU[2].samples[srcRowOffset + x2];
                                final int rgb = YCbCrConverter.convertYCbCrToRGB(Y, Cb, Cr);
                                dataBuffer.setElem(dstRowOffset + x2, rgb);
                            }
                            else {
                                if (mcu.length != 1) {
                                    throw new ImageReadException("Unsupported JPEG with " + mcu.length + " components");
                                }
                                final int Y = scaledMCU[0].samples[srcRowOffset + x2];
                                dataBuffer.setElem(dstRowOffset + x2, Y << 16 | Y << 8 | Y);
                            }
                        }
                        srcRowOffset += hSize;
                        dstRowOffset += this.sofnSegment.width;
                    }
                }
            }
            this.image = new BufferedImage(colorModel, raster, colorModel.isAlphaPremultiplied(), new Properties());
        }
        catch (ImageReadException imageReadEx) {
            this.imageReadException = imageReadEx;
        }
        catch (IOException ioEx) {
            this.ioException = ioEx;
        }
        catch (RuntimeException ex) {
            this.imageReadException = new ImageReadException("Error parsing JPEG", ex);
        }
    }
    
    @Override
    public boolean visitSegment(final int marker, final byte[] markerBytes, final int segmentLength, final byte[] segmentLengthBytes, final byte[] segmentData) throws ImageReadException, IOException {
        final int[] sofnSegments = { 65472, 65473, 65474, 65475, 65477, 65478, 65479, 65481, 65482, 65483, 65485, 65486, 65487 };
        if (Arrays.binarySearch(sofnSegments, marker) >= 0) {
            if (marker != 65472) {
                throw new ImageReadException("Only sequential, baseline JPEGs are supported at the moment");
            }
            this.sofnSegment = new SofnSegment(marker, segmentData);
        }
        else if (marker == 65499) {
            final DqtSegment dqtSegment = new DqtSegment(marker, segmentData);
            for (int i = 0; i < dqtSegment.quantizationTables.size(); ++i) {
                final DqtSegment.QuantizationTable table = dqtSegment.quantizationTables.get(i);
                if (0 > table.destinationIdentifier || table.destinationIdentifier >= this.quantizationTables.length) {
                    throw new ImageReadException("Invalid quantization table identifier " + table.destinationIdentifier);
                }
                this.quantizationTables[table.destinationIdentifier] = table;
                final int[] quantizationMatrixInt = new int[64];
                ZigZag.zigZagToBlock(table.getElements(), quantizationMatrixInt);
                final float[] quantizationMatrixFloat = new float[64];
                for (int j = 0; j < 64; ++j) {
                    quantizationMatrixFloat[j] = (float)quantizationMatrixInt[j];
                }
                Dct.scaleDequantizationMatrix(quantizationMatrixFloat);
                this.scaledQuantizationTables[table.destinationIdentifier] = quantizationMatrixFloat;
            }
        }
        else if (marker == 65476) {
            final DhtSegment dhtSegment = new DhtSegment(marker, segmentData);
            for (int i = 0; i < dhtSegment.huffmanTables.size(); ++i) {
                final DhtSegment.HuffmanTable table2 = dhtSegment.huffmanTables.get(i);
                DhtSegment.HuffmanTable[] tables;
                if (table2.tableClass == 0) {
                    tables = this.huffmanDCTables;
                }
                else {
                    if (table2.tableClass != 1) {
                        throw new ImageReadException("Invalid huffman table class " + table2.tableClass);
                    }
                    tables = this.huffmanACTables;
                }
                if (0 > table2.destinationIdentifier || table2.destinationIdentifier >= tables.length) {
                    throw new ImageReadException("Invalid huffman table identifier " + table2.destinationIdentifier);
                }
                tables[table2.destinationIdentifier] = table2;
            }
        }
        return true;
    }
    
    private void rescaleMCU(final Block[] dataUnits, final int hSize, final int vSize, final Block[] ret) {
        for (int i = 0; i < dataUnits.length; ++i) {
            final Block dataUnit = dataUnits[i];
            if (dataUnit.width == hSize && dataUnit.height == vSize) {
                System.arraycopy(dataUnit.samples, 0, ret[i].samples, 0, hSize * vSize);
            }
            else {
                final int hScale = hSize / dataUnit.width;
                final int vScale = vSize / dataUnit.height;
                if (hScale == 2 && vScale == 2) {
                    int srcRowOffset = 0;
                    int dstRowOffset = 0;
                    for (int y = 0; y < dataUnit.height; ++y) {
                        for (int x = 0; x < hSize; ++x) {
                            final int sample = dataUnit.samples[srcRowOffset + (x >> 1)];
                            ret[i].samples[dstRowOffset + x] = sample;
                            ret[i].samples[dstRowOffset + hSize + x] = sample;
                        }
                        srcRowOffset += dataUnit.width;
                        dstRowOffset += 2 * hSize;
                    }
                }
                else {
                    int dstRowOffset2 = 0;
                    for (int y2 = 0; y2 < vSize; ++y2) {
                        for (int x2 = 0; x2 < hSize; ++x2) {
                            ret[i].samples[dstRowOffset2 + x2] = dataUnit.samples[y2 / vScale * dataUnit.width + x2 / hScale];
                        }
                        dstRowOffset2 += hSize;
                    }
                }
            }
        }
    }
    
    private Block[] allocateMCUMemory() throws ImageReadException {
        final Block[] mcu = new Block[this.sosSegment.numberOfComponents];
        for (int i = 0; i < this.sosSegment.numberOfComponents; ++i) {
            final SosSegment.Component scanComponent = this.sosSegment.getComponents(i);
            SofnSegment.Component frameComponent = null;
            for (int j = 0; j < this.sofnSegment.numberOfComponents; ++j) {
                if (this.sofnSegment.getComponents(j).componentIdentifier == scanComponent.scanComponentSelector) {
                    frameComponent = this.sofnSegment.getComponents(j);
                    break;
                }
            }
            if (frameComponent == null) {
                throw new ImageReadException("Invalid component");
            }
            final Block fullBlock = new Block(8 * frameComponent.horizontalSamplingFactor, 8 * frameComponent.verticalSamplingFactor);
            mcu[i] = fullBlock;
        }
        return mcu;
    }
    
    private void readMCU(final JpegInputStream is, final int[] preds, final Block[] mcu) throws IOException, ImageReadException {
        for (int i = 0; i < this.sosSegment.numberOfComponents; ++i) {
            final SosSegment.Component scanComponent = this.sosSegment.getComponents(i);
            SofnSegment.Component frameComponent = null;
            for (int j = 0; j < this.sofnSegment.numberOfComponents; ++j) {
                if (this.sofnSegment.getComponents(j).componentIdentifier == scanComponent.scanComponentSelector) {
                    frameComponent = this.sofnSegment.getComponents(j);
                    break;
                }
            }
            if (frameComponent == null) {
                throw new ImageReadException("Invalid component");
            }
            final Block fullBlock = mcu[i];
            for (int y = 0; y < frameComponent.verticalSamplingFactor; ++y) {
                for (int x = 0; x < frameComponent.horizontalSamplingFactor; ++x) {
                    Arrays.fill(this.zz, 0);
                    final int t = this.decode(is, this.huffmanDCTables[scanComponent.dcCodingTableSelector]);
                    int diff = this.receive(t, is);
                    diff = this.extend(diff, t);
                    preds[i] = (this.zz[0] = preds[i] + diff);
                    int k = 1;
                    while (true) {
                        final int rs = this.decode(is, this.huffmanACTables[scanComponent.acCodingTableSelector]);
                        final int ssss = rs & 0xF;
                        final int r;
                        final int rrrr = r = rs >> 4;
                        if (ssss == 0) {
                            if (r != 15) {
                                break;
                            }
                            k += 16;
                        }
                        else {
                            k += r;
                            this.zz[k] = this.receive(ssss, is);
                            this.zz[k] = this.extend(this.zz[k], ssss);
                            if (k == 63) {
                                break;
                            }
                            ++k;
                        }
                    }
                    final int shift = 1 << this.sofnSegment.precision - 1;
                    final int max = (1 << this.sofnSegment.precision) - 1;
                    final float[] scaledQuantizationTable = this.scaledQuantizationTables[frameComponent.quantTabDestSelector];
                    ZigZag.zigZagToBlock(this.zz, this.blockInt);
                    for (int l = 0; l < 64; ++l) {
                        this.block[l] = this.blockInt[l] * scaledQuantizationTable[l];
                    }
                    Dct.inverseDCT8x8(this.block);
                    int dstRowOffset = 8 * y * 8 * frameComponent.horizontalSamplingFactor + 8 * x;
                    int srcNext = 0;
                    for (int yy = 0; yy < 8; ++yy) {
                        for (int xx = 0; xx < 8; ++xx) {
                            float sample = this.block[srcNext++];
                            sample += shift;
                            int result;
                            if (sample < 0.0f) {
                                result = 0;
                            }
                            else if (sample > max) {
                                result = max;
                            }
                            else {
                                result = fastRound(sample);
                            }
                            fullBlock.samples[dstRowOffset + xx] = result;
                        }
                        dstRowOffset += 8 * frameComponent.horizontalSamplingFactor;
                    }
                }
            }
        }
    }
    
    private static int fastRound(final float x) {
        return (int)(x + 0.5f);
    }
    
    private int extend(int v, final int t) {
        for (int vt = 1 << t - 1; v < vt; vt = (-1 << t) + 1, v += vt) {}
        return v;
    }
    
    private int receive(final int ssss, final JpegInputStream is) throws IOException, ImageReadException {
        int i;
        int v;
        for (i = 0, v = 0; i != ssss; ++i, v = (v << 1) + is.nextBit()) {}
        return v;
    }
    
    private int decode(final JpegInputStream is, final DhtSegment.HuffmanTable huffmanTable) throws IOException, ImageReadException {
        int i;
        int code;
        for (i = 1, code = is.nextBit(); code > huffmanTable.getMaxCode(i); ++i, code = (code << 1 | is.nextBit())) {}
        int j = huffmanTable.getValPtr(i);
        j += code - huffmanTable.getMinCode(i);
        return huffmanTable.getHuffVal(j);
    }
    
    public BufferedImage decode(final ByteSource byteSource) throws IOException, ImageReadException {
        final JpegUtils jpegUtils = new JpegUtils();
        jpegUtils.traverseJFIF(byteSource, this);
        if (this.imageReadException != null) {
            throw this.imageReadException;
        }
        if (this.ioException != null) {
            throw this.ioException;
        }
        return this.image;
    }
}
