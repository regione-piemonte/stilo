// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.commons.imaging.formats.icns;

import java.nio.charset.StandardCharsets;

enum IcnsType
{
    ICNS_16x12_1BIT_IMAGE_AND_MASK("icm#", 16, 12, 1, true), 
    ICNS_16x12_4BIT_IMAGE("icm4", 16, 12, 4, false), 
    ICNS_16x12_8BIT_IMAGE("icm8", 16, 12, 8, false), 
    ICNS_16x16_8BIT_MASK("s8mk", 16, 16, 8, true), 
    ICNS_16x16_1BIT_IMAGE_AND_MASK("ics#", 16, 16, 1, true), 
    ICNS_16x16_4BIT_IMAGE("ics4", 16, 16, 4, false), 
    ICNS_16x16_8BIT_IMAGE("ics8", 16, 16, 8, false), 
    ICNS_16x16_32BIT_IMAGE("is32", 16, 16, 32, false), 
    ICNS_32x32_8BIT_MASK("l8mk", 32, 32, 8, true), 
    ICNS_32x32_1BIT_IMAGE_AND_MASK("ICN#", 32, 32, 1, true), 
    ICNS_32x32_4BIT_IMAGE("icl4", 32, 32, 4, false), 
    ICNS_32x32_8BIT_IMAGE("icl8", 32, 32, 8, false), 
    ICNS_32x32_32BIT_IMAGE("il32", 32, 32, 32, false), 
    ICNS_48x48_8BIT_MASK("h8mk", 48, 48, 8, true), 
    ICNS_48x48_1BIT_IMAGE_AND_MASK("ich#", 48, 48, 1, true), 
    ICNS_48x48_4BIT_IMAGE("ich4", 48, 48, 4, false), 
    ICNS_48x48_8BIT_IMAGE("ich8", 48, 48, 8, false), 
    ICNS_48x48_32BIT_IMAGE("ih32", 48, 48, 32, false), 
    ICNS_128x128_8BIT_MASK("t8mk", 128, 128, 8, true), 
    ICNS_128x128_32BIT_IMAGE("it32", 128, 128, 32, false), 
    ICNS_256x256_32BIT_ARGB_IMAGE("ic08", 256, 256, 32, false), 
    ICNS_512x512_32BIT_ARGB_IMAGE("ic09", 512, 512, 32, false);
    
    private static final IcnsType[] ALL_IMAGE_TYPES;
    private static final IcnsType[] ALL_MASK_TYPES;
    private final int type;
    private final int width;
    private final int height;
    private final int bitsPerPixel;
    private final boolean hasMask;
    
    private IcnsType(final String type, final int width, final int height, final int bitsPerPixel, final boolean hasMask) {
        this.type = typeAsInt(type);
        this.width = width;
        this.height = height;
        this.bitsPerPixel = bitsPerPixel;
        this.hasMask = hasMask;
    }
    
    public int getType() {
        return this.type;
    }
    
    public int getWidth() {
        return this.width;
    }
    
    public int getHeight() {
        return this.height;
    }
    
    public int getBitsPerPixel() {
        return this.bitsPerPixel;
    }
    
    public boolean hasMask() {
        return this.hasMask;
    }
    
    @Override
    public String toString() {
        return this.getClass().getName() + "[width=" + this.width + ",height=" + this.height + ",bpp=" + this.bitsPerPixel + ",hasMask=" + this.hasMask + "]";
    }
    
    public static IcnsType findAnyType(final int type) {
        for (final IcnsType allImageType : IcnsType.ALL_IMAGE_TYPES) {
            if (allImageType.getType() == type) {
                return allImageType;
            }
        }
        for (final IcnsType allMaskType : IcnsType.ALL_MASK_TYPES) {
            if (allMaskType.getType() == type) {
                return allMaskType;
            }
        }
        return null;
    }
    
    public static IcnsType findImageType(final int type) {
        for (final IcnsType allImageType : IcnsType.ALL_IMAGE_TYPES) {
            if (allImageType.getType() == type) {
                return allImageType;
            }
        }
        return null;
    }
    
    public static IcnsType find8BPPMaskType(final IcnsType imageType) {
        for (final IcnsType allMaskType : IcnsType.ALL_MASK_TYPES) {
            if (allMaskType.getBitsPerPixel() == 8 && allMaskType.getWidth() == imageType.getWidth() && allMaskType.getHeight() == imageType.getHeight()) {
                return allMaskType;
            }
        }
        return null;
    }
    
    public static IcnsType find1BPPMaskType(final IcnsType imageType) {
        for (final IcnsType allMaskType : IcnsType.ALL_MASK_TYPES) {
            if (allMaskType.getBitsPerPixel() == 1 && allMaskType.getWidth() == imageType.getWidth() && allMaskType.getHeight() == imageType.getHeight()) {
                return allMaskType;
            }
        }
        return null;
    }
    
    public static int typeAsInt(final String type) {
        final byte[] bytes = type.getBytes(StandardCharsets.US_ASCII);
        if (bytes.length != 4) {
            throw new IllegalArgumentException("Invalid ICNS type");
        }
        return (0xFF & bytes[0]) << 24 | (0xFF & bytes[1]) << 16 | (0xFF & bytes[2]) << 8 | (0xFF & bytes[3]);
    }
    
    public static String describeType(final int type) {
        final byte[] bytes = { (byte)(0xFF & type >> 24), (byte)(0xFF & type >> 16), (byte)(0xFF & type >> 8), (byte)(0xFF & type) };
        return new String(bytes, StandardCharsets.US_ASCII);
    }
    
    static {
        ALL_IMAGE_TYPES = new IcnsType[] { IcnsType.ICNS_16x12_1BIT_IMAGE_AND_MASK, IcnsType.ICNS_16x12_4BIT_IMAGE, IcnsType.ICNS_16x12_8BIT_IMAGE, IcnsType.ICNS_16x16_1BIT_IMAGE_AND_MASK, IcnsType.ICNS_16x16_4BIT_IMAGE, IcnsType.ICNS_16x16_8BIT_IMAGE, IcnsType.ICNS_16x16_32BIT_IMAGE, IcnsType.ICNS_32x32_1BIT_IMAGE_AND_MASK, IcnsType.ICNS_32x32_4BIT_IMAGE, IcnsType.ICNS_32x32_8BIT_IMAGE, IcnsType.ICNS_32x32_32BIT_IMAGE, IcnsType.ICNS_48x48_1BIT_IMAGE_AND_MASK, IcnsType.ICNS_48x48_4BIT_IMAGE, IcnsType.ICNS_48x48_8BIT_IMAGE, IcnsType.ICNS_48x48_32BIT_IMAGE, IcnsType.ICNS_128x128_32BIT_IMAGE, IcnsType.ICNS_256x256_32BIT_ARGB_IMAGE, IcnsType.ICNS_512x512_32BIT_ARGB_IMAGE };
        ALL_MASK_TYPES = new IcnsType[] { IcnsType.ICNS_16x12_1BIT_IMAGE_AND_MASK, IcnsType.ICNS_16x16_1BIT_IMAGE_AND_MASK, IcnsType.ICNS_16x16_8BIT_MASK, IcnsType.ICNS_32x32_1BIT_IMAGE_AND_MASK, IcnsType.ICNS_32x32_8BIT_MASK, IcnsType.ICNS_48x48_1BIT_IMAGE_AND_MASK, IcnsType.ICNS_48x48_8BIT_MASK, IcnsType.ICNS_128x128_8BIT_MASK };
    }
}
