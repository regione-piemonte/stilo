// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.commons.imaging.icc;

import java.io.IOException;
import org.apache.commons.imaging.common.BinaryFunctions;
import java.io.InputStream;
import java.util.logging.Level;
import org.apache.commons.imaging.common.bytesource.ByteSourceFile;
import java.io.File;
import org.apache.commons.imaging.common.bytesource.ByteSource;
import org.apache.commons.imaging.common.bytesource.ByteSourceArray;
import java.awt.color.ICC_Profile;
import java.nio.ByteOrder;
import java.util.logging.Logger;
import org.apache.commons.imaging.common.BinaryFileParser;

public class IccProfileParser extends BinaryFileParser
{
    private static final Logger LOGGER;
    
    public IccProfileParser() {
        this.setByteOrder(ByteOrder.BIG_ENDIAN);
    }
    
    public IccProfileInfo getICCProfileInfo(final ICC_Profile iccProfile) {
        if (iccProfile == null) {
            return null;
        }
        return this.getICCProfileInfo(new ByteSourceArray(iccProfile.getData()));
    }
    
    public IccProfileInfo getICCProfileInfo(final byte[] bytes) {
        if (bytes == null) {
            return null;
        }
        return this.getICCProfileInfo(new ByteSourceArray(bytes));
    }
    
    public IccProfileInfo getICCProfileInfo(final File file) {
        if (file == null) {
            return null;
        }
        return this.getICCProfileInfo(new ByteSourceFile(file));
    }
    
    public IccProfileInfo getICCProfileInfo(final ByteSource byteSource) {
        InputStream is = null;
        try {
            is = byteSource.getInputStream();
            final IccProfileInfo result = this.readICCProfileInfo(is);
            if (result == null) {
                return null;
            }
            is.close();
            is = null;
            for (final IccTag tag : result.getTags()) {
                final byte[] bytes = byteSource.getBlock(tag.offset, tag.length);
                tag.setData(bytes);
            }
            return result;
        }
        catch (Exception e) {
            IccProfileParser.LOGGER.log(Level.SEVERE, e.getMessage(), e);
            try {
                if (is != null) {
                    is.close();
                }
            }
            catch (Exception e) {
                IccProfileParser.LOGGER.log(Level.SEVERE, e.getMessage(), e);
            }
        }
        finally {
            try {
                if (is != null) {
                    is.close();
                }
            }
            catch (Exception e2) {
                IccProfileParser.LOGGER.log(Level.SEVERE, e2.getMessage(), e2);
            }
        }
        return null;
    }
    
    private IccProfileInfo readICCProfileInfo(InputStream is) {
        final CachingInputStream cis = (CachingInputStream)(is = new CachingInputStream(is));
        try {
            final int profileSize = BinaryFunctions.read4Bytes("ProfileSize", is, "Not a Valid ICC Profile", this.getByteOrder());
            final int cmmTypeSignature = BinaryFunctions.read4Bytes("Signature", is, "Not a Valid ICC Profile", this.getByteOrder());
            if (IccProfileParser.LOGGER.isLoggable(Level.FINEST)) {
                BinaryFunctions.printCharQuad("CMMTypeSignature", cmmTypeSignature);
            }
            final int profileVersion = BinaryFunctions.read4Bytes("ProfileVersion", is, "Not a Valid ICC Profile", this.getByteOrder());
            final int profileDeviceClassSignature = BinaryFunctions.read4Bytes("ProfileDeviceClassSignature", is, "Not a Valid ICC Profile", this.getByteOrder());
            if (IccProfileParser.LOGGER.isLoggable(Level.FINEST)) {
                BinaryFunctions.printCharQuad("ProfileDeviceClassSignature", profileDeviceClassSignature);
            }
            final int colorSpace = BinaryFunctions.read4Bytes("ColorSpace", is, "Not a Valid ICC Profile", this.getByteOrder());
            if (IccProfileParser.LOGGER.isLoggable(Level.FINEST)) {
                BinaryFunctions.printCharQuad("ColorSpace", colorSpace);
            }
            final int profileConnectionSpace = BinaryFunctions.read4Bytes("ProfileConnectionSpace", is, "Not a Valid ICC Profile", this.getByteOrder());
            if (IccProfileParser.LOGGER.isLoggable(Level.FINEST)) {
                BinaryFunctions.printCharQuad("ProfileConnectionSpace", profileConnectionSpace);
            }
            BinaryFunctions.skipBytes(is, 12L, "Not a Valid ICC Profile");
            final int profileFileSignature = BinaryFunctions.read4Bytes("ProfileFileSignature", is, "Not a Valid ICC Profile", this.getByteOrder());
            if (IccProfileParser.LOGGER.isLoggable(Level.FINEST)) {
                BinaryFunctions.printCharQuad("ProfileFileSignature", profileFileSignature);
            }
            final int primaryPlatformSignature = BinaryFunctions.read4Bytes("PrimaryPlatformSignature", is, "Not a Valid ICC Profile", this.getByteOrder());
            if (IccProfileParser.LOGGER.isLoggable(Level.FINEST)) {
                BinaryFunctions.printCharQuad("PrimaryPlatformSignature", primaryPlatformSignature);
            }
            final int variousFlags = BinaryFunctions.read4Bytes("VariousFlags", is, "Not a Valid ICC Profile", this.getByteOrder());
            if (IccProfileParser.LOGGER.isLoggable(Level.FINEST)) {
                BinaryFunctions.printCharQuad("VariousFlags", profileFileSignature);
            }
            final int deviceManufacturer = BinaryFunctions.read4Bytes("DeviceManufacturer", is, "Not a Valid ICC Profile", this.getByteOrder());
            if (IccProfileParser.LOGGER.isLoggable(Level.FINEST)) {
                BinaryFunctions.printCharQuad("DeviceManufacturer", deviceManufacturer);
            }
            final int deviceModel = BinaryFunctions.read4Bytes("DeviceModel", is, "Not a Valid ICC Profile", this.getByteOrder());
            if (IccProfileParser.LOGGER.isLoggable(Level.FINEST)) {
                BinaryFunctions.printCharQuad("DeviceModel", deviceModel);
            }
            BinaryFunctions.skipBytes(is, 8L, "Not a Valid ICC Profile");
            final int renderingIntent = BinaryFunctions.read4Bytes("RenderingIntent", is, "Not a Valid ICC Profile", this.getByteOrder());
            if (IccProfileParser.LOGGER.isLoggable(Level.FINEST)) {
                BinaryFunctions.printCharQuad("RenderingIntent", renderingIntent);
            }
            BinaryFunctions.skipBytes(is, 12L, "Not a Valid ICC Profile");
            final int profileCreatorSignature = BinaryFunctions.read4Bytes("ProfileCreatorSignature", is, "Not a Valid ICC Profile", this.getByteOrder());
            if (IccProfileParser.LOGGER.isLoggable(Level.FINEST)) {
                BinaryFunctions.printCharQuad("ProfileCreatorSignature", profileCreatorSignature);
            }
            final byte[] profileId = null;
            BinaryFunctions.skipBytes(is, 16L, "Not a Valid ICC Profile");
            BinaryFunctions.skipBytes(is, 28L, "Not a Valid ICC Profile");
            final int tagCount = BinaryFunctions.read4Bytes("TagCount", is, "Not a Valid ICC Profile", this.getByteOrder());
            final IccTag[] tags = new IccTag[tagCount];
            for (int i = 0; i < tagCount; ++i) {
                final int tagSignature = BinaryFunctions.read4Bytes("TagSignature[" + i + "]", is, "Not a Valid ICC Profile", this.getByteOrder());
                final int offsetToData = BinaryFunctions.read4Bytes("OffsetToData[" + i + "]", is, "Not a Valid ICC Profile", this.getByteOrder());
                final int elementSize = BinaryFunctions.read4Bytes("ElementSize[" + i + "]", is, "Not a Valid ICC Profile", this.getByteOrder());
                final IccTagType fIccTagType = this.getIccTagType(tagSignature);
                final IccTag tag = new IccTag(tagSignature, offsetToData, elementSize, fIccTagType);
                tags[i] = tag;
            }
            while (is.read() >= 0) {}
            final byte[] data = cis.getCache();
            if (data.length < profileSize) {
                throw new IOException("Couldn't read ICC Profile.");
            }
            final IccProfileInfo result = new IccProfileInfo(data, profileSize, cmmTypeSignature, profileVersion, profileDeviceClassSignature, colorSpace, profileConnectionSpace, profileFileSignature, primaryPlatformSignature, variousFlags, deviceManufacturer, deviceModel, renderingIntent, profileCreatorSignature, profileId, tags);
            if (IccProfileParser.LOGGER.isLoggable(Level.FINEST)) {
                IccProfileParser.LOGGER.finest("issRGB: " + result.issRGB());
            }
            return result;
        }
        catch (Exception e) {
            IccProfileParser.LOGGER.log(Level.SEVERE, e.getMessage(), e);
            return null;
        }
    }
    
    private IccTagType getIccTagType(final int quad) {
        for (final IccTagType iccTagType : IccTagTypes.values()) {
            if (iccTagType.getSignature() == quad) {
                return iccTagType;
            }
        }
        return null;
    }
    
    public boolean issRGB(final ICC_Profile iccProfile) throws IOException {
        return this.issRGB(new ByteSourceArray(iccProfile.getData()));
    }
    
    public boolean issRGB(final byte[] bytes) throws IOException {
        return this.issRGB(new ByteSourceArray(bytes));
    }
    
    public boolean issRGB(final File file) throws IOException {
        return this.issRGB(new ByteSourceFile(file));
    }
    
    public boolean issRGB(final ByteSource byteSource) throws IOException {
        try (final InputStream is = byteSource.getInputStream()) {
            BinaryFunctions.read4Bytes("ProfileSize", is, "Not a Valid ICC Profile", this.getByteOrder());
            BinaryFunctions.skipBytes(is, 20L);
            BinaryFunctions.skipBytes(is, 12L, "Not a Valid ICC Profile");
            BinaryFunctions.skipBytes(is, 12L);
            final int deviceManufacturer = BinaryFunctions.read4Bytes("ProfileFileSignature", is, "Not a Valid ICC Profile", this.getByteOrder());
            if (IccProfileParser.LOGGER.isLoggable(Level.FINEST)) {
                BinaryFunctions.printCharQuad("DeviceManufacturer", deviceManufacturer);
            }
            final int deviceModel = BinaryFunctions.read4Bytes("DeviceModel", is, "Not a Valid ICC Profile", this.getByteOrder());
            if (IccProfileParser.LOGGER.isLoggable(Level.FINEST)) {
                BinaryFunctions.printCharQuad("DeviceModel", deviceModel);
            }
            final boolean result = deviceManufacturer == 1229275936 && deviceModel == 1934772034;
            return result;
        }
    }
    
    static {
        LOGGER = Logger.getLogger(IccProfileParser.class.getName());
    }
}
