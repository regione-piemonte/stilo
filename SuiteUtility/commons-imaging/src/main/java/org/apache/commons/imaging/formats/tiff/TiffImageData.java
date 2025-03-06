// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.commons.imaging.formats.tiff;

import org.apache.commons.imaging.common.bytesource.ByteSourceFile;
import org.apache.commons.imaging.formats.tiff.datareaders.DataReaderStrips;
import org.apache.commons.imaging.formats.tiff.datareaders.DataReaderTiled;
import org.apache.commons.imaging.ImageReadException;
import java.io.IOException;
import org.apache.commons.imaging.formats.tiff.datareaders.ImageDataReader;
import java.nio.ByteOrder;
import org.apache.commons.imaging.formats.tiff.photometricinterpreters.PhotometricInterpreter;

public abstract class TiffImageData
{
    public abstract TiffElement.DataElement[] getImageData();
    
    public abstract boolean stripsNotTiles();
    
    public abstract ImageDataReader getDataReader(final TiffDirectory p0, final PhotometricInterpreter p1, final int p2, final int[] p3, final int p4, final int p5, final int p6, final int p7, final int p8, final ByteOrder p9) throws IOException, ImageReadException;
    
    public static class Tiles extends TiffImageData
    {
        public final TiffElement.DataElement[] tiles;
        private final int tileWidth;
        private final int tileLength;
        
        public Tiles(final TiffElement.DataElement[] tiles, final int tileWidth, final int tileLength) {
            this.tiles = tiles;
            this.tileWidth = tileWidth;
            this.tileLength = tileLength;
        }
        
        @Override
        public TiffElement.DataElement[] getImageData() {
            return this.tiles;
        }
        
        @Override
        public boolean stripsNotTiles() {
            return false;
        }
        
        @Override
        public ImageDataReader getDataReader(final TiffDirectory directory, final PhotometricInterpreter photometricInterpreter, final int bitsPerPixel, final int[] bitsPerSample, final int predictor, final int samplesPerPixel, final int width, final int height, final int compression, final ByteOrder byteOrder) throws IOException, ImageReadException {
            return new DataReaderTiled(directory, photometricInterpreter, this.tileWidth, this.tileLength, bitsPerPixel, bitsPerSample, predictor, samplesPerPixel, width, height, compression, byteOrder, this);
        }
        
        public int getTileWidth() {
            return this.tileWidth;
        }
        
        public int getTileHeight() {
            return this.tileLength;
        }
    }
    
    public static class Strips extends TiffImageData
    {
        private final TiffElement.DataElement[] strips;
        public final int rowsPerStrip;
        
        public Strips(final TiffElement.DataElement[] strips, final int rowsPerStrip) {
            this.strips = strips;
            this.rowsPerStrip = rowsPerStrip;
        }
        
        @Override
        public TiffElement.DataElement[] getImageData() {
            return this.strips;
        }
        
        public TiffElement.DataElement getImageData(final int offset) {
            return this.strips[offset];
        }
        
        public int getImageDataLength() {
            return this.strips.length;
        }
        
        @Override
        public boolean stripsNotTiles() {
            return true;
        }
        
        @Override
        public ImageDataReader getDataReader(final TiffDirectory directory, final PhotometricInterpreter photometricInterpreter, final int bitsPerPixel, final int[] bitsPerSample, final int predictor, final int samplesPerPixel, final int width, final int height, final int compression, final ByteOrder byteorder) throws IOException, ImageReadException {
            return new DataReaderStrips(directory, photometricInterpreter, bitsPerPixel, bitsPerSample, predictor, samplesPerPixel, width, height, compression, byteorder, this.rowsPerStrip, this);
        }
    }
    
    public static class Data extends DataElement
    {
        public Data(final long offset, final int length, final byte[] data) {
            super(offset, length, data);
        }
        
        @Override
        public String getElementDescription() {
            return "Tiff image data: " + this.getDataLength() + " bytes";
        }
    }
    
    public static class ByteSourceData extends Data
    {
        ByteSourceFile byteSourceFile;
        
        public ByteSourceData(final long offset, final int length, final ByteSourceFile byteSource) {
            super(offset, length, new byte[0]);
            this.byteSourceFile = byteSource;
        }
        
        @Override
        public String getElementDescription() {
            return "Tiff image data: " + this.getDataLength() + " bytes";
        }
        
        @Override
        public byte[] getData() {
            try {
                return this.byteSourceFile.getBlock(this.offset, this.length);
            }
            catch (IOException ioex) {
                return new byte[0];
            }
        }
    }
}
