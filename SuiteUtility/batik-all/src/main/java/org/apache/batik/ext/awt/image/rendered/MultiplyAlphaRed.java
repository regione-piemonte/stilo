// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.ext.awt.image.rendered;

import java.awt.image.ComponentColorModel;
import java.awt.image.PixelInterleavedSampleModel;
import java.util.ArrayList;
import java.util.List;
import java.awt.image.ColorModel;
import java.awt.image.Raster;
import java.awt.Rectangle;
import java.awt.image.DataBufferInt;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;
import java.awt.image.ComponentSampleModel;
import java.awt.image.SinglePixelPackedSampleModel;
import java.awt.image.SampleModel;
import java.util.Map;

public class MultiplyAlphaRed extends AbstractRed
{
    public MultiplyAlphaRed(final CachableRed cachableRed, final CachableRed cachableRed2) {
        super(makeList(cachableRed, cachableRed2), makeBounds(cachableRed, cachableRed2), fixColorModel(cachableRed), fixSampleModel(cachableRed), cachableRed.getTileGridXOffset(), cachableRed.getTileGridYOffset(), null);
    }
    
    public boolean is_INT_PACK_BYTE_COMP(final SampleModel sampleModel, final SampleModel sampleModel2) {
        if (!(sampleModel instanceof SinglePixelPackedSampleModel)) {
            return false;
        }
        if (!(sampleModel2 instanceof ComponentSampleModel)) {
            return false;
        }
        if (sampleModel.getDataType() != 3) {
            return false;
        }
        if (sampleModel2.getDataType() != 0) {
            return false;
        }
        final int[] bitMasks = ((SinglePixelPackedSampleModel)sampleModel).getBitMasks();
        if (bitMasks.length != 4) {
            return false;
        }
        if (bitMasks[0] != 16711680) {
            return false;
        }
        if (bitMasks[1] != 65280) {
            return false;
        }
        if (bitMasks[2] != 255) {
            return false;
        }
        if (bitMasks[3] != -16777216) {
            return false;
        }
        final ComponentSampleModel componentSampleModel = (ComponentSampleModel)sampleModel2;
        return componentSampleModel.getNumBands() == 1 && componentSampleModel.getPixelStride() == 1;
    }
    
    public WritableRaster INT_PACK_BYTE_COMP_Impl(final WritableRaster writableRaster) {
        final CachableRed cachableRed = this.getSources().get(0);
        final CachableRed cachableRed2 = this.getSources().get(1);
        cachableRed.copyData(writableRaster);
        final Rectangle intersection = writableRaster.getBounds().intersection(cachableRed2.getBounds());
        final Raster data = cachableRed2.getData(intersection);
        final ComponentSampleModel componentSampleModel = (ComponentSampleModel)data.getSampleModel();
        final int scanlineStride = componentSampleModel.getScanlineStride();
        final DataBufferByte dataBufferByte = (DataBufferByte)data.getDataBuffer();
        final int n = dataBufferByte.getOffset() + componentSampleModel.getOffset(intersection.x - data.getSampleModelTranslateX(), intersection.y - data.getSampleModelTranslateY());
        final byte[] array = dataBufferByte.getBankData()[0];
        final SinglePixelPackedSampleModel singlePixelPackedSampleModel = (SinglePixelPackedSampleModel)writableRaster.getSampleModel();
        final int scanlineStride2 = singlePixelPackedSampleModel.getScanlineStride();
        final DataBufferInt dataBufferInt = (DataBufferInt)writableRaster.getDataBuffer();
        final int n2 = dataBufferInt.getOffset() + singlePixelPackedSampleModel.getOffset(intersection.x - writableRaster.getSampleModelTranslateX(), intersection.y - writableRaster.getSampleModelTranslateY());
        final int[] array2 = dataBufferInt.getBankData()[0];
        if (cachableRed.getColorModel().isAlphaPremultiplied()) {
            for (int i = 0; i < intersection.height; ++i) {
                int j = n2 + i * scanlineStride2;
                int n3 = n + i * scanlineStride;
                while (j < j + intersection.width) {
                    final int n4 = array[n3++] & 0xFF;
                    final int n5 = array2[j];
                    array2[j] = (((n5 >>> 24) * n4 & 0xFF00) << 16 | ((n5 >>> 16 & 0xFF) * n4 & 0xFF00) << 8 | ((n5 >>> 8 & 0xFF) * n4 & 0xFF00) | ((n5 & 0xFF) * n4 & 0xFF00) >> 8);
                    ++j;
                }
            }
        }
        else {
            for (int k = 0; k < intersection.height; ++k) {
                int l = n2 + k * scanlineStride2;
                int n6 = n + k * scanlineStride;
                while (l < l + intersection.width) {
                    array2[l] = (((array2[l] >>> 24) * (array[n6++] & 0xFF) & 0xFF00) << 16 | (array2[l] & 0xFFFFFF));
                    ++l;
                }
            }
        }
        return writableRaster;
    }
    
    public WritableRaster copyData(final WritableRaster writableRaster) {
        final CachableRed cachableRed = this.getSources().get(0);
        final CachableRed cachableRed2 = this.getSources().get(1);
        if (this.is_INT_PACK_BYTE_COMP(cachableRed.getSampleModel(), cachableRed2.getSampleModel())) {
            return this.INT_PACK_BYTE_COMP_Impl(writableRaster);
        }
        final ColorModel colorModel = cachableRed.getColorModel();
        if (!colorModel.hasAlpha()) {
            final int[] bandList = new int[writableRaster.getNumBands() - 1];
            for (int i = 0; i < bandList.length; ++i) {
                bandList[i] = i;
            }
            cachableRed.copyData(writableRaster.createWritableChild(writableRaster.getMinX(), writableRaster.getMinY(), writableRaster.getWidth(), writableRaster.getHeight(), writableRaster.getMinX(), writableRaster.getMinY(), bandList));
            final Rectangle intersection = writableRaster.getBounds().intersection(cachableRed2.getBounds());
            cachableRed2.copyData(writableRaster.createWritableChild(intersection.x, intersection.y, intersection.width, intersection.height, intersection.x, intersection.y, new int[] { writableRaster.getNumBands() - 1 }));
            return writableRaster;
        }
        cachableRed.copyData(writableRaster);
        final Rectangle bounds = writableRaster.getBounds();
        if (bounds.intersects(cachableRed2.getBounds())) {
            final Rectangle intersection2 = bounds.intersection(cachableRed2.getBounds());
            int[] array = null;
            int[] array2 = null;
            final Raster data = cachableRed2.getData(intersection2);
            final int width = intersection2.width;
            final int numBands = writableRaster.getSampleModel().getNumBands();
            if (colorModel.isAlphaPremultiplied()) {
                for (int j = intersection2.y; j < intersection2.y + intersection2.height; ++j) {
                    array = writableRaster.getPixels(intersection2.x, j, width, 1, array);
                    array2 = data.getSamples(intersection2.x, j, width, 1, 0, array2);
                    int n = 0;
                    switch (numBands) {
                        case 2: {
                            for (int k = 0; k < array2.length; ++k) {
                                final int n2 = array2[k] & 0xFF;
                                array[n] = (array[n] & 0xFF) * n2 >> 8;
                                ++n;
                                array[n] = (array[n] & 0xFF) * n2 >> 8;
                                ++n;
                            }
                            break;
                        }
                        case 4: {
                            for (int l = 0; l < array2.length; ++l) {
                                final int n3 = array2[l] & 0xFF;
                                array[n] = (array[n] & 0xFF) * n3 >> 8;
                                ++n;
                                array[n] = (array[n] & 0xFF) * n3 >> 8;
                                ++n;
                                array[n] = (array[n] & 0xFF) * n3 >> 8;
                                ++n;
                                array[n] = (array[n] & 0xFF) * n3 >> 8;
                                ++n;
                            }
                            break;
                        }
                        default: {
                            for (int n4 = 0; n4 < array2.length; ++n4) {
                                final int n5 = array2[n4] & 0xFF;
                                for (int n6 = 0; n6 < numBands; ++n6) {
                                    array[n] = (array[n] & 0xFF) * n5 >> 8;
                                    ++n;
                                }
                            }
                            break;
                        }
                    }
                    writableRaster.setPixels(intersection2.x, j, width, 1, array);
                }
            }
            else {
                final int n7 = cachableRed.getSampleModel().getNumBands() - 1;
                for (int y = intersection2.y; y < intersection2.y + intersection2.height; ++y) {
                    array = writableRaster.getSamples(intersection2.x, y, width, 1, n7, array);
                    array2 = data.getSamples(intersection2.x, y, width, 1, 0, array2);
                    for (int n8 = 0; n8 < array.length; ++n8) {
                        array[n8] = (array[n8] & 0xFF) * (array2[n8] & 0xFF) >> 8;
                    }
                    writableRaster.setSamples(intersection2.x, y, width, 1, n7, array);
                }
            }
            return writableRaster;
        }
        return writableRaster;
    }
    
    public static List makeList(final CachableRed cachableRed, final CachableRed cachableRed2) {
        final ArrayList<CachableRed> list = new ArrayList<CachableRed>(2);
        list.add(cachableRed);
        list.add(cachableRed2);
        return list;
    }
    
    public static Rectangle makeBounds(final CachableRed cachableRed, final CachableRed cachableRed2) {
        return cachableRed.getBounds().intersection(cachableRed2.getBounds());
    }
    
    public static SampleModel fixSampleModel(final CachableRed cachableRed) {
        final ColorModel colorModel = cachableRed.getColorModel();
        final SampleModel sampleModel = cachableRed.getSampleModel();
        if (colorModel.hasAlpha()) {
            return sampleModel;
        }
        final int width = sampleModel.getWidth();
        final int height = sampleModel.getHeight();
        final int pixelStride = sampleModel.getNumBands() + 1;
        final int[] bandOffsets = new int[pixelStride];
        for (int i = 0; i < pixelStride; ++i) {
            bandOffsets[i] = i;
        }
        return new PixelInterleavedSampleModel(0, width, height, pixelStride, width * pixelStride, bandOffsets);
    }
    
    public static ColorModel fixColorModel(final CachableRed cachableRed) {
        final ColorModel colorModel = cachableRed.getColorModel();
        if (colorModel.hasAlpha()) {
            return colorModel;
        }
        final int n = cachableRed.getSampleModel().getNumBands() + 1;
        final int[] bits = new int[n];
        for (int i = 0; i < n; ++i) {
            bits[i] = 8;
        }
        return new ComponentColorModel(colorModel.getColorSpace(), bits, true, false, 3, 0);
    }
}
