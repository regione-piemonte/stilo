// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.commons.imaging.formats.tiff.datareaders;

import java.awt.image.BufferedImage;
import java.awt.Rectangle;
import org.apache.commons.imaging.formats.tiff.TiffElement;
import java.io.IOException;
import org.apache.commons.imaging.ImageReadException;
import java.io.InputStream;
import java.io.ByteArrayInputStream;
import org.apache.commons.imaging.formats.tiff.photometricinterpreters.PhotometricInterpreterRgb;
import org.apache.commons.imaging.common.ImageBuilder;
import org.apache.commons.imaging.formats.tiff.photometricinterpreters.PhotometricInterpreter;
import org.apache.commons.imaging.formats.tiff.TiffDirectory;
import org.apache.commons.imaging.formats.tiff.TiffImageData;
import java.nio.ByteOrder;

public final class DataReaderTiled extends ImageDataReader
{
    private final int tileWidth;
    private final int tileLength;
    private final int bitsPerPixel;
    private final int compression;
    private final ByteOrder byteOrder;
    private final TiffImageData.Tiles imageData;
    
    public DataReaderTiled(final TiffDirectory directory, final PhotometricInterpreter photometricInterpreter, final int tileWidth, final int tileLength, final int bitsPerPixel, final int[] bitsPerSample, final int predictor, final int samplesPerPixel, final int width, final int height, final int compression, final ByteOrder byteOrder, final TiffImageData.Tiles imageData) {
        super(directory, photometricInterpreter, bitsPerSample, predictor, samplesPerPixel, width, height);
        this.tileWidth = tileWidth;
        this.tileLength = tileLength;
        this.bitsPerPixel = bitsPerPixel;
        this.compression = compression;
        this.imageData = imageData;
        this.byteOrder = byteOrder;
    }
    
    private void interpretTile(final ImageBuilder imageBuilder, final byte[] bytes, final int startX, final int startY, final int xLimit, final int yLimit) throws ImageReadException, IOException {
        final boolean allSamplesAreOneByte = this.isHomogenous(8);
        if (this.predictor != 2 && this.bitsPerPixel == 24 && allSamplesAreOneByte) {
            int k = 0;
            final int i0 = startY;
            int i2 = startY + this.tileLength;
            if (i2 > yLimit) {
                i2 = yLimit;
            }
            final int j0 = startX;
            int j2 = startX + this.tileWidth;
            if (j2 > xLimit) {
                j2 = xLimit;
            }
            if (this.photometricInterpreter instanceof PhotometricInterpreterRgb) {
                for (int l = i0; l < i2; ++l) {
                    k = (l - i0) * this.tileWidth * 3;
                    for (int m = j0; m < j2; ++m, k += 3) {
                        final int rgb = 0xFF000000 | (bytes[k] << 8 | (bytes[k + 1] & 0xFF)) << 8 | (bytes[k + 2] & 0xFF);
                        imageBuilder.setRGB(m, l, rgb);
                    }
                }
            }
            else {
                final int[] samples = new int[3];
                for (int i3 = i0; i3 < i2; ++i3) {
                    k = (i3 - i0) * this.tileWidth * 3;
                    for (int j3 = j0; j3 < j2; ++j3) {
                        samples[0] = (bytes[k++] & 0xFF);
                        samples[1] = (bytes[k++] & 0xFF);
                        samples[2] = (bytes[k++] & 0xFF);
                        this.photometricInterpreter.interpretPixel(imageBuilder, samples, j3, i3);
                    }
                }
            }
            return;
        }
        try (final BitInputStream bis = new BitInputStream(new ByteArrayInputStream(bytes), this.byteOrder)) {
            final int pixelsPerTile = this.tileWidth * this.tileLength;
            int tileX = 0;
            int tileY = 0;
            int[] samples = new int[this.bitsPerSampleLength];
            this.resetPredictor();
            for (int i3 = 0; i3 < pixelsPerTile; ++i3) {
                final int x = tileX + startX;
                final int y = tileY + startY;
                this.getSamplesAsBytes(bis, samples);
                if (x < xLimit && y < yLimit) {
                    samples = this.applyPredictor(samples);
                    this.photometricInterpreter.interpretPixel(imageBuilder, samples, x, y);
                }
                if (++tileX >= this.tileWidth) {
                    tileX = 0;
                    this.resetPredictor();
                    ++tileY;
                    bis.flushCache();
                    if (tileY >= this.tileLength) {
                        break;
                    }
                }
            }
        }
    }
    
    @Override
    public void readImageData(final ImageBuilder imageBuilder) throws ImageReadException, IOException {
        final int bitsPerRow = this.tileWidth * this.bitsPerPixel;
        final int bytesPerRow = (bitsPerRow + 7) / 8;
        final int bytesPerTile = bytesPerRow * this.tileLength;
        int x = 0;
        int y = 0;
        for (final TiffElement.DataElement tile2 : this.imageData.tiles) {
            final byte[] compressed = tile2.getData();
            final byte[] decompressed = this.decompress(compressed, this.compression, bytesPerTile, this.tileWidth, this.tileLength);
            this.interpretTile(imageBuilder, decompressed, x, y, this.width, this.height);
            x += this.tileWidth;
            if (x >= this.width) {
                x = 0;
                y += this.tileLength;
                if (y >= this.height) {
                    break;
                }
            }
        }
    }
    
    @Override
    public BufferedImage readImageData(final Rectangle subImage) throws ImageReadException, IOException {
        final int bitsPerRow = this.tileWidth * this.bitsPerPixel;
        final int bytesPerRow = (bitsPerRow + 7) / 8;
        final int bytesPerTile = bytesPerRow * this.tileLength;
        int x = 0;
        int y = 0;
        final int col0 = subImage.x / this.tileWidth;
        final int col2 = (subImage.x + subImage.width - 1) / this.tileWidth;
        final int row0 = subImage.y / this.tileLength;
        final int row2 = (subImage.y + subImage.height - 1) / this.tileLength;
        final int nCol = col2 - col0 + 1;
        final int nRow = row2 - row0 + 1;
        final int workingWidth = nCol * this.tileWidth;
        final int workingHeight = nRow * this.tileLength;
        final int nColumnsOfTiles = (this.width + this.tileWidth - 1) / this.tileWidth;
        final int x2 = col0 * this.tileWidth;
        final int y2 = row0 * this.tileLength;
        final ImageBuilder workingBuilder = new ImageBuilder(workingWidth, workingHeight, false);
        for (int iRow = row0; iRow <= row2; ++iRow) {
            for (int iCol = col0; iCol <= col2; ++iCol) {
                final int tile = iRow * nColumnsOfTiles + iCol;
                final byte[] compressed = this.imageData.tiles[tile].getData();
                final byte[] decompressed = this.decompress(compressed, this.compression, bytesPerTile, this.tileWidth, this.tileLength);
                x = iCol * this.tileWidth - x2;
                y = iRow * this.tileLength - y2;
                this.interpretTile(workingBuilder, decompressed, x, y, workingWidth, workingHeight);
            }
        }
        if (subImage.x == x2 && subImage.y == y2 && subImage.width == workingWidth && subImage.height == workingHeight) {
            return workingBuilder.getBufferedImage();
        }
        return workingBuilder.getSubimage(subImage.x - x2, subImage.y - y2, subImage.width, subImage.height);
    }
}
