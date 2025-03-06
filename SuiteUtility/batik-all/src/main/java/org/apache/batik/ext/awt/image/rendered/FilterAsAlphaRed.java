// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.ext.awt.image.rendered;

import java.awt.image.Raster;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;
import org.apache.batik.ext.awt.ColorSpaceHintKey;
import java.util.Map;
import java.awt.image.SampleModel;
import java.awt.image.ColorModel;
import java.awt.image.PixelInterleavedSampleModel;
import java.awt.image.ComponentColorModel;
import java.awt.color.ColorSpace;

public class FilterAsAlphaRed extends AbstractRed
{
    public FilterAsAlphaRed(final CachableRed cachableRed) {
        super(new Any2LumRed(cachableRed), cachableRed.getBounds(), new ComponentColorModel(ColorSpace.getInstance(1003), new int[] { 8 }, false, false, 1, 0), new PixelInterleavedSampleModel(0, cachableRed.getSampleModel().getWidth(), cachableRed.getSampleModel().getHeight(), 1, cachableRed.getSampleModel().getWidth(), new int[] { 0 }), cachableRed.getTileGridXOffset(), cachableRed.getTileGridYOffset(), null);
        this.props.put("org.apache.batik.gvt.filter.Colorspace", ColorSpaceHintKey.VALUE_COLORSPACE_ALPHA);
    }
    
    public WritableRaster copyData(final WritableRaster writableRaster) {
        final CachableRed cachableRed = this.getSources().get(0);
        if (cachableRed.getSampleModel().getNumBands() == 1) {
            return cachableRed.copyData(writableRaster);
        }
        final Raster data = cachableRed.getData(writableRaster.getBounds());
        final PixelInterleavedSampleModel pixelInterleavedSampleModel = (PixelInterleavedSampleModel)data.getSampleModel();
        final DataBufferByte dataBufferByte = (DataBufferByte)data.getDataBuffer();
        final byte[] data2 = dataBufferByte.getData();
        final PixelInterleavedSampleModel pixelInterleavedSampleModel2 = (PixelInterleavedSampleModel)writableRaster.getSampleModel();
        final DataBufferByte dataBufferByte2 = (DataBufferByte)writableRaster.getDataBuffer();
        final byte[] data3 = dataBufferByte2.getData();
        final int n = data.getMinX() - data.getSampleModelTranslateX();
        int n2 = data.getMinY() - data.getSampleModelTranslateY();
        final int n3 = writableRaster.getMinX() - writableRaster.getSampleModelTranslateX();
        final int n4 = n3 + writableRaster.getWidth() - 1;
        int n5 = writableRaster.getMinY() - writableRaster.getSampleModelTranslateY();
        final int pixelStride = pixelInterleavedSampleModel.getPixelStride();
        final int[] bandOffsets = pixelInterleavedSampleModel.getBandOffsets();
        final int n6 = bandOffsets[0];
        final int n7 = bandOffsets[1];
        if (cachableRed.getColorModel().isAlphaPremultiplied()) {
            for (int i = 0; i < data.getHeight(); ++i) {
                final int n8 = dataBufferByte.getOffset() + pixelInterleavedSampleModel.getOffset(n, n2);
                for (int j = dataBufferByte2.getOffset() + pixelInterleavedSampleModel2.getOffset(n3, n5), n9 = dataBufferByte2.getOffset() + pixelInterleavedSampleModel2.getOffset(n4 + 1, n5), n10 = n8 + n6; j < n9; data3[j++] = data2[n10], n10 += pixelStride) {}
                ++n2;
                ++n5;
            }
        }
        else {
            final int n11 = n7 - n6;
            for (int k = 0; k < data.getHeight(); ++k) {
                final int n12 = dataBufferByte.getOffset() + pixelInterleavedSampleModel.getOffset(n, n2);
                for (int l = dataBufferByte2.getOffset() + pixelInterleavedSampleModel2.getOffset(n3, n5), n13 = dataBufferByte2.getOffset() + pixelInterleavedSampleModel2.getOffset(n4 + 1, n5), n14 = n12 + n6; l < n13; data3[l++] = (byte)((data2[n14] & 0xFF) * (data2[n14 + n11] & 0xFF) + 128 >> 8), n14 += pixelStride) {}
                ++n2;
                ++n5;
            }
        }
        return writableRaster;
    }
}
