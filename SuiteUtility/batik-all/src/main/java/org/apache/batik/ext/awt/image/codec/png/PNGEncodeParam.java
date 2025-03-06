// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.ext.awt.image.codec.png;

import org.apache.batik.ext.awt.image.codec.util.PropertyUtil;
import java.awt.image.IndexColorModel;
import java.awt.image.RenderedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import org.apache.batik.ext.awt.image.codec.util.ImageEncodeParam;

public abstract class PNGEncodeParam implements ImageEncodeParam
{
    public static final int INTENT_PERCEPTUAL = 0;
    public static final int INTENT_RELATIVE = 1;
    public static final int INTENT_SATURATION = 2;
    public static final int INTENT_ABSOLUTE = 3;
    public static final int PNG_FILTER_NONE = 0;
    public static final int PNG_FILTER_SUB = 1;
    public static final int PNG_FILTER_UP = 2;
    public static final int PNG_FILTER_AVERAGE = 3;
    public static final int PNG_FILTER_PAETH = 4;
    protected int bitDepth;
    protected boolean bitDepthSet;
    private boolean useInterlacing;
    private float[] chromaticity;
    private boolean chromaticitySet;
    private float gamma;
    private boolean gammaSet;
    private int[] paletteHistogram;
    private boolean paletteHistogramSet;
    private byte[] ICCProfileData;
    private boolean ICCProfileDataSet;
    private int[] physicalDimension;
    private boolean physicalDimensionSet;
    private PNGSuggestedPaletteEntry[] suggestedPalette;
    private boolean suggestedPaletteSet;
    private int[] significantBits;
    private boolean significantBitsSet;
    private int SRGBIntent;
    private boolean SRGBIntentSet;
    private String[] text;
    private boolean textSet;
    private Date modificationTime;
    private boolean modificationTimeSet;
    boolean transparencySet;
    private String[] zText;
    private boolean zTextSet;
    List chunkType;
    List chunkData;
    
    public PNGEncodeParam() {
        this.bitDepthSet = false;
        this.useInterlacing = false;
        this.chromaticity = null;
        this.chromaticitySet = false;
        this.gammaSet = false;
        this.paletteHistogram = null;
        this.paletteHistogramSet = false;
        this.ICCProfileData = null;
        this.ICCProfileDataSet = false;
        this.physicalDimension = null;
        this.physicalDimensionSet = false;
        this.suggestedPalette = null;
        this.suggestedPaletteSet = false;
        this.significantBits = null;
        this.significantBitsSet = false;
        this.SRGBIntentSet = false;
        this.text = null;
        this.textSet = false;
        this.modificationTimeSet = false;
        this.transparencySet = false;
        this.zText = null;
        this.zTextSet = false;
        this.chunkType = new ArrayList();
        this.chunkData = new ArrayList();
    }
    
    public static PNGEncodeParam getDefaultEncodeParam(final RenderedImage renderedImage) {
        if (renderedImage.getColorModel() instanceof IndexColorModel) {
            return new Palette();
        }
        final int numBands = renderedImage.getSampleModel().getNumBands();
        if (numBands == 1 || numBands == 2) {
            return new Gray();
        }
        return new RGB();
    }
    
    public abstract void setBitDepth(final int p0);
    
    public int getBitDepth() {
        if (!this.bitDepthSet) {
            throw new IllegalStateException(PropertyUtil.getString("PNGEncodeParam11"));
        }
        return this.bitDepth;
    }
    
    public void unsetBitDepth() {
        this.bitDepthSet = false;
    }
    
    public void setInterlacing(final boolean useInterlacing) {
        this.useInterlacing = useInterlacing;
    }
    
    public boolean getInterlacing() {
        return this.useInterlacing;
    }
    
    public void unsetBackground() {
        throw new RuntimeException(PropertyUtil.getString("PNGEncodeParam23"));
    }
    
    public boolean isBackgroundSet() {
        throw new RuntimeException(PropertyUtil.getString("PNGEncodeParam24"));
    }
    
    public void setChromaticity(final float[] array) {
        if (array.length != 8) {
            throw new IllegalArgumentException();
        }
        this.chromaticity = array.clone();
        this.chromaticitySet = true;
    }
    
    public void setChromaticity(final float n, final float n2, final float n3, final float n4, final float n5, final float n6, final float n7, final float n8) {
        this.setChromaticity(new float[] { n, n2, n3, n4, n5, n6, n7, n8 });
    }
    
    public float[] getChromaticity() {
        if (!this.chromaticitySet) {
            throw new IllegalStateException(PropertyUtil.getString("PNGEncodeParam12"));
        }
        return this.chromaticity.clone();
    }
    
    public void unsetChromaticity() {
        this.chromaticity = null;
        this.chromaticitySet = false;
    }
    
    public boolean isChromaticitySet() {
        return this.chromaticitySet;
    }
    
    public void setGamma(final float gamma) {
        this.gamma = gamma;
        this.gammaSet = true;
    }
    
    public float getGamma() {
        if (!this.gammaSet) {
            throw new IllegalStateException(PropertyUtil.getString("PNGEncodeParam13"));
        }
        return this.gamma;
    }
    
    public void unsetGamma() {
        this.gammaSet = false;
    }
    
    public boolean isGammaSet() {
        return this.gammaSet;
    }
    
    public void setPaletteHistogram(final int[] array) {
        this.paletteHistogram = array.clone();
        this.paletteHistogramSet = true;
    }
    
    public int[] getPaletteHistogram() {
        if (!this.paletteHistogramSet) {
            throw new IllegalStateException(PropertyUtil.getString("PNGEncodeParam14"));
        }
        return this.paletteHistogram;
    }
    
    public void unsetPaletteHistogram() {
        this.paletteHistogram = null;
        this.paletteHistogramSet = false;
    }
    
    public boolean isPaletteHistogramSet() {
        return this.paletteHistogramSet;
    }
    
    public void setICCProfileData(final byte[] array) {
        this.ICCProfileData = array.clone();
        this.ICCProfileDataSet = true;
    }
    
    public byte[] getICCProfileData() {
        if (!this.ICCProfileDataSet) {
            throw new IllegalStateException(PropertyUtil.getString("PNGEncodeParam15"));
        }
        return this.ICCProfileData.clone();
    }
    
    public void unsetICCProfileData() {
        this.ICCProfileData = null;
        this.ICCProfileDataSet = false;
    }
    
    public boolean isICCProfileDataSet() {
        return this.ICCProfileDataSet;
    }
    
    public void setPhysicalDimension(final int[] array) {
        this.physicalDimension = array.clone();
        this.physicalDimensionSet = true;
    }
    
    public void setPhysicalDimension(final int n, final int n2, final int n3) {
        this.setPhysicalDimension(new int[] { n, n2, n3 });
    }
    
    public int[] getPhysicalDimension() {
        if (!this.physicalDimensionSet) {
            throw new IllegalStateException(PropertyUtil.getString("PNGEncodeParam16"));
        }
        return this.physicalDimension.clone();
    }
    
    public void unsetPhysicalDimension() {
        this.physicalDimension = null;
        this.physicalDimensionSet = false;
    }
    
    public boolean isPhysicalDimensionSet() {
        return this.physicalDimensionSet;
    }
    
    public void setSuggestedPalette(final PNGSuggestedPaletteEntry[] array) {
        this.suggestedPalette = array.clone();
        this.suggestedPaletteSet = true;
    }
    
    public PNGSuggestedPaletteEntry[] getSuggestedPalette() {
        if (!this.suggestedPaletteSet) {
            throw new IllegalStateException(PropertyUtil.getString("PNGEncodeParam17"));
        }
        return this.suggestedPalette.clone();
    }
    
    public void unsetSuggestedPalette() {
        this.suggestedPalette = null;
        this.suggestedPaletteSet = false;
    }
    
    public boolean isSuggestedPaletteSet() {
        return this.suggestedPaletteSet;
    }
    
    public void setSignificantBits(final int[] array) {
        this.significantBits = array.clone();
        this.significantBitsSet = true;
    }
    
    public int[] getSignificantBits() {
        if (!this.significantBitsSet) {
            throw new IllegalStateException(PropertyUtil.getString("PNGEncodeParam18"));
        }
        return this.significantBits.clone();
    }
    
    public void unsetSignificantBits() {
        this.significantBits = null;
        this.significantBitsSet = false;
    }
    
    public boolean isSignificantBitsSet() {
        return this.significantBitsSet;
    }
    
    public void setSRGBIntent(final int srgbIntent) {
        this.SRGBIntent = srgbIntent;
        this.SRGBIntentSet = true;
    }
    
    public int getSRGBIntent() {
        if (!this.SRGBIntentSet) {
            throw new IllegalStateException(PropertyUtil.getString("PNGEncodeParam19"));
        }
        return this.SRGBIntent;
    }
    
    public void unsetSRGBIntent() {
        this.SRGBIntentSet = false;
    }
    
    public boolean isSRGBIntentSet() {
        return this.SRGBIntentSet;
    }
    
    public void setText(final String[] text) {
        this.text = text;
        this.textSet = true;
    }
    
    public String[] getText() {
        if (!this.textSet) {
            throw new IllegalStateException(PropertyUtil.getString("PNGEncodeParam20"));
        }
        return this.text;
    }
    
    public void unsetText() {
        this.text = null;
        this.textSet = false;
    }
    
    public boolean isTextSet() {
        return this.textSet;
    }
    
    public void setModificationTime(final Date modificationTime) {
        this.modificationTime = modificationTime;
        this.modificationTimeSet = true;
    }
    
    public Date getModificationTime() {
        if (!this.modificationTimeSet) {
            throw new IllegalStateException(PropertyUtil.getString("PNGEncodeParam21"));
        }
        return this.modificationTime;
    }
    
    public void unsetModificationTime() {
        this.modificationTime = null;
        this.modificationTimeSet = false;
    }
    
    public boolean isModificationTimeSet() {
        return this.modificationTimeSet;
    }
    
    public void unsetTransparency() {
        this.transparencySet = false;
    }
    
    public boolean isTransparencySet() {
        return this.transparencySet;
    }
    
    public void setCompressedText(final String[] zText) {
        this.zText = zText;
        this.zTextSet = true;
    }
    
    public String[] getCompressedText() {
        if (!this.zTextSet) {
            throw new IllegalStateException(PropertyUtil.getString("PNGEncodeParam22"));
        }
        return this.zText;
    }
    
    public void unsetCompressedText() {
        this.zText = null;
        this.zTextSet = false;
    }
    
    public boolean isCompressedTextSet() {
        return this.zTextSet;
    }
    
    public synchronized void addPrivateChunk(final String s, final byte[] array) {
        this.chunkType.add(s);
        this.chunkData.add(array.clone());
    }
    
    public synchronized int getNumPrivateChunks() {
        return this.chunkType.size();
    }
    
    public synchronized String getPrivateChunkType(final int n) {
        return this.chunkType.get(n);
    }
    
    public synchronized byte[] getPrivateChunkData(final int n) {
        return this.chunkData.get(n);
    }
    
    public synchronized void removeUnsafeToCopyPrivateChunks() {
        final ArrayList<String> chunkType = new ArrayList<String>();
        final ArrayList<byte[]> chunkData = new ArrayList<byte[]>();
        for (int numPrivateChunks = this.getNumPrivateChunks(), i = 0; i < numPrivateChunks; ++i) {
            final String privateChunkType = this.getPrivateChunkType(i);
            final char char1 = privateChunkType.charAt(3);
            if (char1 >= 'a' && char1 <= 'z') {
                chunkType.add(privateChunkType);
                chunkData.add(this.getPrivateChunkData(i));
            }
        }
        this.chunkType = chunkType;
        this.chunkData = chunkData;
    }
    
    public synchronized void removeAllPrivateChunks() {
        this.chunkType = new ArrayList();
        this.chunkData = new ArrayList();
    }
    
    private static final int abs(final int n) {
        return (n < 0) ? (-n) : n;
    }
    
    public static final int paethPredictor(final int n, final int n2, final int n3) {
        final int n4 = n + n2 - n3;
        final int abs = abs(n4 - n);
        final int abs2 = abs(n4 - n2);
        final int abs3 = abs(n4 - n3);
        if (abs <= abs2 && abs <= abs3) {
            return n;
        }
        if (abs2 <= abs3) {
            return n2;
        }
        return n3;
    }
    
    public int filterRow(final byte[] array, final byte[] array2, final byte[][] array3, final int n, final int n2) {
        final int[] array4 = { 0, 0, 0, 0, 0 };
        for (int i = n2; i < n + n2; ++i) {
            final int n3 = array[i] & 0xFF;
            final int n4 = array[i - n2] & 0xFF;
            final int n5 = array2[i] & 0xFF;
            final int n6 = array2[i - n2] & 0xFF;
            final int[] array5 = array4;
            final int n7 = 0;
            array5[n7] += n3;
            final int n8 = n3 - n4;
            array3[1][i] = (byte)n8;
            final int[] array6 = array4;
            final int n9 = 1;
            array6[n9] += ((n8 > 0) ? n8 : (-n8));
            final int n10 = n3 - n5;
            array3[2][i] = (byte)n10;
            final int[] array7 = array4;
            final int n11 = 2;
            array7[n11] += ((n10 >= 0) ? n10 : (-n10));
            final int n12 = n3 - (n4 + n5 >> 1);
            array3[3][i] = (byte)n12;
            final int[] array8 = array4;
            final int n13 = 3;
            array8[n13] += ((n12 >= 0) ? n12 : (-n12));
            final int n14 = n5 - n6;
            final int n15 = n4 - n6;
            int n16;
            if (n14 < 0) {
                if (n15 < 0) {
                    if (n14 >= n15) {
                        n16 = n3 - n4;
                    }
                    else {
                        n16 = n3 - n5;
                    }
                }
                else {
                    final int n17 = n14 + n15;
                    final int n18 = -n14;
                    if (n18 <= n15) {
                        if (n18 <= n17) {
                            n16 = n3 - n4;
                        }
                        else {
                            n16 = n3 - n6;
                        }
                    }
                    else if (n15 <= -n17) {
                        n16 = n3 - n5;
                    }
                    else {
                        n16 = n3 - n6;
                    }
                }
            }
            else if (n15 < 0) {
                final int n19 = -n15;
                if (n14 <= n19) {
                    final int n20 = n19 - n14;
                    if (n14 <= n20) {
                        n16 = n3 - n4;
                    }
                    else if (n19 == n20) {
                        n16 = n3 - n5;
                    }
                    else {
                        n16 = n3 - n6;
                    }
                }
                else if (n19 <= n14 - n19) {
                    n16 = n3 - n5;
                }
                else {
                    n16 = n3 - n6;
                }
            }
            else if (n14 <= n15) {
                n16 = n3 - n4;
            }
            else {
                n16 = n3 - n5;
            }
            array3[4][i] = (byte)n16;
            final int[] array9 = array4;
            final int n21 = 4;
            array9[n21] += ((n16 >= 0) ? n16 : (-n16));
        }
        int n22 = 0;
        int n23 = array4[0];
        for (int j = 1; j < 5; ++j) {
            if (array4[j] < n23) {
                n23 = array4[j];
                n22 = j;
            }
        }
        if (n22 == 0) {
            System.arraycopy(array, n2, array3[0], n2, n);
        }
        return n22;
    }
    
    public static class RGB extends PNGEncodeParam
    {
        private boolean backgroundSet;
        private int[] backgroundRGB;
        private int[] transparency;
        
        public RGB() {
            this.backgroundSet = false;
        }
        
        public void unsetBackground() {
            this.backgroundSet = false;
        }
        
        public boolean isBackgroundSet() {
            return this.backgroundSet;
        }
        
        public void setBitDepth(final int bitDepth) {
            if (bitDepth != 8 && bitDepth != 16) {
                throw new RuntimeException();
            }
            this.bitDepth = bitDepth;
            this.bitDepthSet = true;
        }
        
        public void setBackgroundRGB(final int[] backgroundRGB) {
            if (backgroundRGB.length != 3) {
                throw new RuntimeException();
            }
            this.backgroundRGB = backgroundRGB;
            this.backgroundSet = true;
        }
        
        public int[] getBackgroundRGB() {
            if (!this.backgroundSet) {
                throw new IllegalStateException(PropertyUtil.getString("PNGEncodeParam9"));
            }
            return this.backgroundRGB;
        }
        
        public void setTransparentRGB(final int[] array) {
            this.transparency = array.clone();
            this.transparencySet = true;
        }
        
        public int[] getTransparentRGB() {
            if (!this.transparencySet) {
                throw new IllegalStateException(PropertyUtil.getString("PNGEncodeParam10"));
            }
            return this.transparency.clone();
        }
    }
    
    public static class Gray extends PNGEncodeParam
    {
        private boolean backgroundSet;
        private int backgroundPaletteGray;
        private int[] transparency;
        private int bitShift;
        private boolean bitShiftSet;
        
        public Gray() {
            this.backgroundSet = false;
            this.bitShiftSet = false;
        }
        
        public void unsetBackground() {
            this.backgroundSet = false;
        }
        
        public boolean isBackgroundSet() {
            return this.backgroundSet;
        }
        
        public void setBitDepth(final int bitDepth) {
            if (bitDepth != 1 && bitDepth != 2 && bitDepth != 4 && bitDepth != 8 && bitDepth != 16) {
                throw new IllegalArgumentException();
            }
            this.bitDepth = bitDepth;
            this.bitDepthSet = true;
        }
        
        public void setBackgroundGray(final int backgroundPaletteGray) {
            this.backgroundPaletteGray = backgroundPaletteGray;
            this.backgroundSet = true;
        }
        
        public int getBackgroundGray() {
            if (!this.backgroundSet) {
                throw new IllegalStateException(PropertyUtil.getString("PNGEncodeParam6"));
            }
            return this.backgroundPaletteGray;
        }
        
        public void setTransparentGray(final int n) {
            (this.transparency = new int[1])[0] = n;
            this.transparencySet = true;
        }
        
        public int getTransparentGray() {
            if (!this.transparencySet) {
                throw new IllegalStateException(PropertyUtil.getString("PNGEncodeParam7"));
            }
            return this.transparency[0];
        }
        
        public void setBitShift(final int bitShift) {
            if (bitShift < 0) {
                throw new RuntimeException();
            }
            this.bitShift = bitShift;
            this.bitShiftSet = true;
        }
        
        public int getBitShift() {
            if (!this.bitShiftSet) {
                throw new IllegalStateException(PropertyUtil.getString("PNGEncodeParam8"));
            }
            return this.bitShift;
        }
        
        public void unsetBitShift() {
            this.bitShiftSet = false;
        }
        
        public boolean isBitShiftSet() {
            return this.bitShiftSet;
        }
        
        public boolean isBitDepthSet() {
            return this.bitDepthSet;
        }
    }
    
    public static class Palette extends PNGEncodeParam
    {
        private boolean backgroundSet;
        private int[] palette;
        private boolean paletteSet;
        private int backgroundPaletteIndex;
        private int[] transparency;
        
        public Palette() {
            this.backgroundSet = false;
            this.palette = null;
            this.paletteSet = false;
        }
        
        public void unsetBackground() {
            this.backgroundSet = false;
        }
        
        public boolean isBackgroundSet() {
            return this.backgroundSet;
        }
        
        public void setBitDepth(final int bitDepth) {
            if (bitDepth != 1 && bitDepth != 2 && bitDepth != 4 && bitDepth != 8) {
                throw new IllegalArgumentException(PropertyUtil.getString("PNGEncodeParam2"));
            }
            this.bitDepth = bitDepth;
            this.bitDepthSet = true;
        }
        
        public void setPalette(final int[] array) {
            if (array.length < 3 || array.length > 768) {
                throw new IllegalArgumentException(PropertyUtil.getString("PNGEncodeParam0"));
            }
            if (array.length % 3 != 0) {
                throw new IllegalArgumentException(PropertyUtil.getString("PNGEncodeParam1"));
            }
            this.palette = array.clone();
            this.paletteSet = true;
        }
        
        public int[] getPalette() {
            if (!this.paletteSet) {
                throw new IllegalStateException(PropertyUtil.getString("PNGEncodeParam3"));
            }
            return this.palette.clone();
        }
        
        public void unsetPalette() {
            this.palette = null;
            this.paletteSet = false;
        }
        
        public boolean isPaletteSet() {
            return this.paletteSet;
        }
        
        public void setBackgroundPaletteIndex(final int backgroundPaletteIndex) {
            this.backgroundPaletteIndex = backgroundPaletteIndex;
            this.backgroundSet = true;
        }
        
        public int getBackgroundPaletteIndex() {
            if (!this.backgroundSet) {
                throw new IllegalStateException(PropertyUtil.getString("PNGEncodeParam4"));
            }
            return this.backgroundPaletteIndex;
        }
        
        public void setPaletteTransparency(final byte[] array) {
            this.transparency = new int[array.length];
            for (int i = 0; i < array.length; ++i) {
                this.transparency[i] = (array[i] & 0xFF);
            }
            this.transparencySet = true;
        }
        
        public byte[] getPaletteTransparency() {
            if (!this.transparencySet) {
                throw new IllegalStateException(PropertyUtil.getString("PNGEncodeParam5"));
            }
            final byte[] array = new byte[this.transparency.length];
            for (int i = 0; i < array.length; ++i) {
                array[i] = (byte)this.transparency[i];
            }
            return array;
        }
    }
}
