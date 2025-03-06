// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.commons.imaging;

import java.awt.image.BufferedImage;
import org.apache.commons.imaging.icc.IccProfileInfo;
import java.awt.color.ICC_Profile;
import org.apache.commons.imaging.icc.IccProfileParser;
import java.awt.color.ICC_ColorSpace;
import java.awt.color.ColorSpace;
import java.util.logging.Logger;

public class ImageDump
{
    private static final Logger LOGGER;
    
    private String colorSpaceTypeToName(final ColorSpace cs) {
        switch (cs.getType()) {
            case 9: {
                return "TYPE_CMYK";
            }
            case 5: {
                return "TYPE_RGB";
            }
            case 1000: {
                return "CS_sRGB";
            }
            case 1003: {
                return "CS_GRAY";
            }
            case 1001: {
                return "CS_CIEXYZ";
            }
            case 1004: {
                return "CS_LINEAR_RGB";
            }
            case 1002: {
                return "CS_PYCC";
            }
            default: {
                return "unknown";
            }
        }
    }
    
    public void dumpColorSpace(final String prefix, final ColorSpace cs) {
        ImageDump.LOGGER.fine(prefix + ": type: " + cs.getType() + " (" + this.colorSpaceTypeToName(cs) + ")");
        if (!(cs instanceof ICC_ColorSpace)) {
            ImageDump.LOGGER.fine(prefix + ": Unknown ColorSpace: " + cs.getClass().getName());
            return;
        }
        final ICC_ColorSpace iccColorSpace = (ICC_ColorSpace)cs;
        final ICC_Profile iccProfile = iccColorSpace.getProfile();
        final byte[] bytes = iccProfile.getData();
        final IccProfileParser parser = new IccProfileParser();
        final IccProfileInfo info = parser.getICCProfileInfo(bytes);
        info.dump(prefix);
    }
    
    public void dump(final BufferedImage src) {
        this.dump("", src);
    }
    
    public void dump(final String prefix, final BufferedImage src) {
        ImageDump.LOGGER.fine(prefix + ": dump");
        this.dumpColorSpace(prefix, src.getColorModel().getColorSpace());
        this.dumpBIProps(prefix, src);
    }
    
    public void dumpBIProps(final String prefix, final BufferedImage src) {
        final String[] keys = src.getPropertyNames();
        if (keys == null) {
            ImageDump.LOGGER.fine(prefix + ": no props");
            return;
        }
        for (final String key : keys) {
            ImageDump.LOGGER.fine(prefix + ": " + key + ": " + src.getProperty(key));
        }
    }
    
    static {
        LOGGER = Logger.getLogger(ImageDump.class.getName());
    }
}
