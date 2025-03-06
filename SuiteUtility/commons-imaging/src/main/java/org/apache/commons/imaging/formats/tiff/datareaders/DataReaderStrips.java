// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.commons.imaging.formats.tiff.datareaders;

import java.awt.image.BufferedImage;
import java.awt.Rectangle;
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

public final class DataReaderStrips extends ImageDataReader
{
    private final int bitsPerPixel;
    private final int compression;
    private final int rowsPerStrip;
    private final ByteOrder byteOrder;
    private int x;
    private int y;
    private final TiffImageData.Strips imageData;
    
    public DataReaderStrips(final TiffDirectory directory, final PhotometricInterpreter photometricInterpreter, final int bitsPerPixel, final int[] bitsPerSample, final int predictor, final int samplesPerPixel, final int width, final int height, final int compression, final ByteOrder byteOrder, final int rowsPerStrip, final TiffImageData.Strips imageData) {
        super(directory, photometricInterpreter, bitsPerSample, predictor, samplesPerPixel, width, height);
        this.bitsPerPixel = bitsPerPixel;
        this.compression = compression;
        this.rowsPerStrip = rowsPerStrip;
        this.imageData = imageData;
        this.byteOrder = byteOrder;
    }
    
    private void interpretStrip(final ImageBuilder imageBuilder, final byte[] bytes, final int pixelsPerStrip, final int yLimit) throws ImageReadException, IOException {
        if (this.y >= yLimit) {
            return;
        }
        final boolean allSamplesAreOneByte = this.isHomogenous(8);
        if (this.predictor != 2 && this.bitsPerPixel == 8 && allSamplesAreOneByte) {
            int k = 0;
            int nRows = pixelsPerStrip / this.width;
            if (this.y + nRows > yLimit) {
                nRows = yLimit - this.y;
            }
            final int i0 = this.y;
            final int i2 = this.y + nRows;
            this.x = 0;
            this.y += nRows;
            final int[] samples = { 0 };
            for (int j = i0; j < i2; ++j) {
                for (int l = 0; l < this.width; ++l) {
                    samples[0] = (bytes[k++] & 0xFF);
                    this.photometricInterpreter.interpretPixel(imageBuilder, samples, l, j);
                }
            }
            return;
        }
        if (this.predictor != 2 && this.bitsPerPixel == 24 && allSamplesAreOneByte) {
            int k = 0;
            int nRows = pixelsPerStrip / this.width;
            if (this.y + nRows > yLimit) {
                nRows = yLimit - this.y;
            }
            final int i0 = this.y;
            final int i2 = this.y + nRows;
            this.x = 0;
            this.y += nRows;
            if (this.photometricInterpreter instanceof PhotometricInterpreterRgb) {
                for (int m = i0; m < i2; ++m) {
                    for (int j2 = 0; j2 < this.width; ++j2, k += 3) {
                        final int rgb = 0xFF000000 | (bytes[k] << 8 | (bytes[k + 1] & 0xFF)) << 8 | (bytes[k + 2] & 0xFF);
                        imageBuilder.setRGB(j2, m, rgb);
                    }
                }
            }
            else {
                final int[] samples = new int[3];
                for (int j = i0; j < i2; ++j) {
                    for (int l = 0; l < this.width; ++l) {
                        samples[0] = (bytes[k++] & 0xFF);
                        samples[1] = (bytes[k++] & 0xFF);
                        samples[2] = (bytes[k++] & 0xFF);
                        this.photometricInterpreter.interpretPixel(imageBuilder, samples, l, j);
                    }
                }
            }
            return;
        }
        try (final BitInputStream bis = new BitInputStream(new ByteArrayInputStream(bytes), this.byteOrder)) {
            int[] samples2 = new int[this.bitsPerSampleLength];
            this.resetPredictor();
            for (int i3 = 0; i3 < pixelsPerStrip; ++i3) {
                this.getSamplesAsBytes(bis, samples2);
                if (this.x < this.width) {
                    samples2 = this.applyPredictor(samples2);
                    this.photometricInterpreter.interpretPixel(imageBuilder, samples2, this.x, this.y);
                }
                ++this.x;
                if (this.x >= this.width) {
                    this.x = 0;
                    this.resetPredictor();
                    ++this.y;
                    bis.flushCache();
                    if (this.y >= yLimit) {
                        break;
                    }
                }
            }
        }
    }
    
    @Override
    public void readImageData(final ImageBuilder imageBuilder) throws ImageReadException, IOException {
        for (int strip = 0; strip < this.imageData.getImageDataLength(); ++strip) {
            final long rowsPerStripLong = 0xFFFFFFFFL & (long)this.rowsPerStrip;
            final long rowsRemaining = this.height - strip * rowsPerStripLong;
            final long rowsInThisStrip = Math.min(rowsRemaining, rowsPerStripLong);
            final long bytesPerRow = (this.bitsPerPixel * this.width + 7) / 8;
            final long bytesPerStrip = rowsInThisStrip * bytesPerRow;
            final long pixelsPerStrip = rowsInThisStrip * this.width;
            final byte[] compressed = this.imageData.getImageData(strip).getData();
            final byte[] decompressed = this.decompress(compressed, this.compression, (int)bytesPerStrip, this.width, (int)rowsInThisStrip);
            this.interpretStrip(imageBuilder, decompressed, (int)pixelsPerStrip, this.height);
        }
    }
    
    @Override
    public BufferedImage readImageData(final Rectangle subImage) throws ImageReadException, IOException {
        final int strip0 = subImage.y / this.rowsPerStrip;
        final int strip2 = (subImage.y + subImage.height - 1) / this.rowsPerStrip;
        final int workingHeight = (strip2 - strip0 + 1) * this.rowsPerStrip;
        final int y0 = strip0 * this.rowsPerStrip;
        final int yLimit = subImage.y - y0 + subImage.height;
        final ImageBuilder workingBuilder = new ImageBuilder(this.width, workingHeight, false);
        for (int strip3 = strip0; strip3 <= strip2; ++strip3) {
            final long rowsPerStripLong = 0xFFFFFFFFL & (long)this.rowsPerStrip;
            final long rowsRemaining = this.height - strip3 * rowsPerStripLong;
            final long rowsInThisStrip = Math.min(rowsRemaining, rowsPerStripLong);
            final long bytesPerRow = (this.bitsPerPixel * this.width + 7) / 8;
            final long bytesPerStrip = rowsInThisStrip * bytesPerRow;
            final long pixelsPerStrip = rowsInThisStrip * this.width;
            final byte[] compressed = this.imageData.getImageData(strip3).getData();
            final byte[] decompressed = this.decompress(compressed, this.compression, (int)bytesPerStrip, this.width, (int)rowsInThisStrip);
            this.interpretStrip(workingBuilder, decompressed, (int)pixelsPerStrip, yLimit);
        }
        if (subImage.x == 0 && subImage.y == y0 && subImage.width == this.width && subImage.height == workingHeight) {
            return workingBuilder.getBufferedImage();
        }
        return workingBuilder.getSubimage(subImage.x, subImage.y - y0, subImage.width, subImage.height);
    }
}
