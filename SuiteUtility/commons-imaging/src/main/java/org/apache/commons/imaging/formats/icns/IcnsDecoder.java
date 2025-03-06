// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.commons.imaging.formats.icns;

import java.util.ArrayList;
import java.awt.image.BufferedImage;
import java.util.List;
import org.apache.commons.imaging.ImageReadException;
import org.apache.commons.imaging.common.ImageBuilder;

final class IcnsDecoder
{
    private static final int[] PALETTE_4BPP;
    private static final int[] PALETTE_8BPP;
    
    private IcnsDecoder() {
    }
    
    private static void decode1BPPImage(final IcnsType imageType, final byte[] imageData, final ImageBuilder image) {
        int position = 0;
        int bitsLeft = 0;
        int value = 0;
        for (int y = 0; y < imageType.getHeight(); ++y) {
            for (int x = 0; x < imageType.getWidth(); ++x) {
                if (bitsLeft == 0) {
                    value = (0xFF & imageData[position++]);
                    bitsLeft = 8;
                }
                int argb;
                if ((value & 0x80) != 0x0) {
                    argb = -16777216;
                }
                else {
                    argb = -1;
                }
                value <<= 1;
                --bitsLeft;
                image.setRGB(x, y, argb);
            }
        }
    }
    
    private static void decode4BPPImage(final IcnsType imageType, final byte[] imageData, final ImageBuilder image) {
        int i = 0;
        boolean visited = false;
        for (int y = 0; y < imageType.getHeight(); ++y) {
            for (int x = 0; x < imageType.getWidth(); ++x) {
                int index;
                if (!visited) {
                    index = (0xF & imageData[i] >> 4);
                }
                else {
                    index = (0xF & imageData[i++]);
                }
                visited = !visited;
                image.setRGB(x, y, IcnsDecoder.PALETTE_4BPP[index]);
            }
        }
    }
    
    private static void decode8BPPImage(final IcnsType imageType, final byte[] imageData, final ImageBuilder image) {
        for (int y = 0; y < imageType.getHeight(); ++y) {
            for (int x = 0; x < imageType.getWidth(); ++x) {
                final int index = 0xFF & imageData[y * imageType.getWidth() + x];
                image.setRGB(x, y, IcnsDecoder.PALETTE_8BPP[index]);
            }
        }
    }
    
    private static void decode32BPPImage(final IcnsType imageType, final byte[] imageData, final ImageBuilder image) {
        for (int y = 0; y < imageType.getHeight(); ++y) {
            for (int x = 0; x < imageType.getWidth(); ++x) {
                final int argb = 0xFF000000 | (0xFF & imageData[4 * (y * imageType.getWidth() + x) + 1]) << 16 | (0xFF & imageData[4 * (y * imageType.getWidth() + x) + 2]) << 8 | (0xFF & imageData[4 * (y * imageType.getWidth() + x) + 3]);
                image.setRGB(x, y, argb);
            }
        }
    }
    
    private static void apply1BPPMask(final byte[] maskData, final ImageBuilder image) throws ImageReadException {
        int position = 0;
        int bitsLeft = 0;
        int value = 0;
        final int totalBytes = (image.getWidth() * image.getHeight() + 7) / 8;
        if (maskData.length >= 2 * totalBytes) {
            position = totalBytes;
            for (int y = 0; y < image.getHeight(); ++y) {
                for (int x = 0; x < image.getWidth(); ++x) {
                    if (bitsLeft == 0) {
                        value = (0xFF & maskData[position++]);
                        bitsLeft = 8;
                    }
                    int alpha;
                    if ((value & 0x80) != 0x0) {
                        alpha = 255;
                    }
                    else {
                        alpha = 0;
                    }
                    value <<= 1;
                    --bitsLeft;
                    image.setRGB(x, y, alpha << 24 | (0xFFFFFF & image.getRGB(x, y)));
                }
            }
            return;
        }
        throw new ImageReadException("1 BPP mask underrun parsing ICNS file");
    }
    
    private static void apply8BPPMask(final byte[] maskData, final ImageBuilder image) {
        for (int y = 0; y < image.getHeight(); ++y) {
            for (int x = 0; x < image.getWidth(); ++x) {
                final int alpha = 0xFF & maskData[y * image.getWidth() + x];
                image.setRGB(x, y, alpha << 24 | (0xFFFFFF & image.getRGB(x, y)));
            }
        }
    }
    
    public static List<BufferedImage> decodeAllImages(final IcnsImageParser.IcnsElement[] icnsElements) throws ImageReadException {
        final List<BufferedImage> result = new ArrayList<BufferedImage>();
        for (final IcnsImageParser.IcnsElement imageElement : icnsElements) {
            final IcnsType imageType = IcnsType.findImageType(imageElement.type);
            if (imageType != null) {
                IcnsImageParser.IcnsElement maskElement = null;
                IcnsType maskType;
                if (imageType.hasMask()) {
                    maskType = imageType;
                    maskElement = imageElement;
                }
                else {
                    maskType = IcnsType.find8BPPMaskType(imageType);
                    if (maskType != null) {
                        for (final IcnsImageParser.IcnsElement icnsElement : icnsElements) {
                            if (icnsElement.type == maskType.getType()) {
                                maskElement = icnsElement;
                                break;
                            }
                        }
                    }
                    if (maskElement == null) {
                        maskType = IcnsType.find1BPPMaskType(imageType);
                        if (maskType != null) {
                            for (final IcnsImageParser.IcnsElement icnsElement : icnsElements) {
                                if (icnsElement.type == maskType.getType()) {
                                    maskElement = icnsElement;
                                    break;
                                }
                            }
                        }
                    }
                }
                if (imageType != IcnsType.ICNS_256x256_32BIT_ARGB_IMAGE) {
                    if (imageType != IcnsType.ICNS_512x512_32BIT_ARGB_IMAGE) {
                        final int expectedSize = (imageType.getWidth() * imageType.getHeight() * imageType.getBitsPerPixel() + 7) / 8;
                        byte[] imageData;
                        if (imageElement.data.length < expectedSize) {
                            if (imageType.getBitsPerPixel() != 32) {
                                throw new ImageReadException("Short image data but not a 32 bit compressed type");
                            }
                            imageData = Rle24Compression.decompress(imageType.getWidth(), imageType.getHeight(), imageElement.data);
                        }
                        else {
                            imageData = imageElement.data;
                        }
                        final ImageBuilder imageBuilder = new ImageBuilder(imageType.getWidth(), imageType.getHeight(), true);
                        switch (imageType.getBitsPerPixel()) {
                            case 1: {
                                decode1BPPImage(imageType, imageData, imageBuilder);
                                break;
                            }
                            case 4: {
                                decode4BPPImage(imageType, imageData, imageBuilder);
                                break;
                            }
                            case 8: {
                                decode8BPPImage(imageType, imageData, imageBuilder);
                                break;
                            }
                            case 32: {
                                decode32BPPImage(imageType, imageData, imageBuilder);
                                break;
                            }
                            default: {
                                throw new ImageReadException("Unsupported bit depth " + imageType.getBitsPerPixel());
                            }
                        }
                        if (maskElement != null) {
                            if (maskType.getBitsPerPixel() == 1) {
                                apply1BPPMask(maskElement.data, imageBuilder);
                            }
                            else {
                                if (maskType.getBitsPerPixel() != 8) {
                                    throw new ImageReadException("Unsupport mask bit depth " + maskType.getBitsPerPixel());
                                }
                                apply8BPPMask(maskElement.data, imageBuilder);
                            }
                        }
                        result.add(imageBuilder.getBufferedImage());
                    }
                }
            }
        }
        return result;
    }
    
    static {
        PALETTE_4BPP = new int[] { -1, -199931, -39934, -2291706, -915324, -12189531, -16777004, -16602134, -14698732, -16751599, -11129851, -7311046, -4144960, -8355712, -12566464, -16777216 };
        PALETTE_8BPP = new int[] { -1, -52, -103, -154, -205, -256, -13057, -13108, -13159, -13210, -13261, -13312, -26113, -26164, -26215, -26266, -26317, -26368, -39169, -39220, -39271, -39322, -39373, -39424, -52225, -52276, -52327, -52378, -52429, -52480, -65281, -65332, -65383, -65434, -65485, -65536, -3342337, -3342388, -3342439, -3342490, -3342541, -3342592, -3355393, -3355444, -3355495, -3355546, -3355597, -3355648, -3368449, -3368500, -3368551, -3368602, -3368653, -3368704, -3381505, -3381556, -3381607, -3381658, -3381709, -3381760, -3394561, -3394612, -3394663, -3394714, -3394765, -3394816, -3407617, -3407668, -3407719, -3407770, -3407821, -3407872, -6684673, -6684724, -6684775, -6684826, -6684877, -6684928, -6697729, -6697780, -6697831, -6697882, -6697933, -6697984, -6710785, -6710836, -6710887, -6710938, -6710989, -6711040, -6723841, -6723892, -6723943, -6723994, -6724045, -6724096, -6736897, -6736948, -6736999, -6737050, -6737101, -6737152, -6749953, -6750004, -6750055, -6750106, -6750157, -6750208, -10027009, -10027060, -10027111, -10027162, -10027213, -10027264, -10040065, -10040116, -10040167, -10040218, -10040269, -10040320, -10053121, -10053172, -10053223, -10053274, -10053325, -10053376, -10066177, -10066228, -10066279, -10066330, -10066381, -10066432, -10079233, -10079284, -10079335, -10079386, -10079437, -10079488, -10092289, -10092340, -10092391, -10092442, -10092493, -10092544, -13369345, -13369396, -13369447, -13369498, -13369549, -13369600, -13382401, -13382452, -13382503, -13382554, -13382605, -13382656, -13395457, -13395508, -13395559, -13395610, -13395661, -13395712, -13408513, -13408564, -13408615, -13408666, -13408717, -13408768, -13421569, -13421620, -13421671, -13421722, -13421773, -13421824, -13434625, -13434676, -13434727, -13434778, -13434829, -13434880, -16711681, -16711732, -16711783, -16711834, -16711885, -16711936, -16724737, -16724788, -16724839, -16724890, -16724941, -16724992, -16737793, -16737844, -16737895, -16737946, -16737997, -16738048, -16750849, -16750900, -16750951, -16751002, -16751053, -16751104, -16763905, -16763956, -16764007, -16764058, -16764109, -16764160, -16776961, -16777012, -16777063, -16777114, -16777165, -1179648, -2293760, -4521984, -5636096, -7864320, -8978432, -11206656, -12320768, -14548992, -15663104, -16716288, -16720640, -16729344, -16733696, -16742400, -16746752, -16755456, -16759808, -16768512, -16772864, -16776978, -16776995, -16777029, -16777046, -16777080, -16777097, -16777131, -16777148, -16777182, -16777199, -1118482, -2236963, -4473925, -5592406, -7829368, -8947849, -11184811, -12303292, -14540254, -15658735, -16777216 };
    }
}
