// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.commons.imaging.formats.png.chunks;

import java.io.IOException;
import java.io.InputStream;
import java.util.zip.InflaterInputStream;
import java.io.ByteArrayInputStream;
import java.util.logging.Level;
import java.nio.charset.StandardCharsets;
import org.apache.commons.imaging.ImageReadException;
import org.apache.commons.imaging.common.BinaryFunctions;
import java.util.logging.Logger;

public class PngChunkIccp extends PngChunk
{
    private static final Logger LOGGER;
    public final String profileName;
    public final int compressionMethod;
    private final byte[] compressedProfile;
    private final byte[] uncompressedProfile;
    
    public byte[] getUncompressedProfile() {
        return this.uncompressedProfile;
    }
    
    public PngChunkIccp(final int length, final int chunkType, final int crc, final byte[] bytes) throws ImageReadException, IOException {
        super(length, chunkType, crc, bytes);
        final int index = BinaryFunctions.findNull(bytes);
        if (index < 0) {
            throw new ImageReadException("PngChunkIccp: No Profile Name");
        }
        final byte[] nameBytes = new byte[index];
        System.arraycopy(bytes, 0, nameBytes, 0, index);
        this.profileName = new String(nameBytes, StandardCharsets.ISO_8859_1);
        this.compressionMethod = bytes[index + 1];
        final int compressedProfileLength = bytes.length - (index + 1 + 1);
        System.arraycopy(bytes, index + 1 + 1, this.compressedProfile = new byte[compressedProfileLength], 0, compressedProfileLength);
        if (PngChunkIccp.LOGGER.isLoggable(Level.FINEST)) {
            PngChunkIccp.LOGGER.finest("ProfileName: " + this.profileName);
            PngChunkIccp.LOGGER.finest("ProfileName.length(): " + this.profileName.length());
            PngChunkIccp.LOGGER.finest("CompressionMethod: " + this.compressionMethod);
            PngChunkIccp.LOGGER.finest("CompressedProfileLength: " + compressedProfileLength);
            PngChunkIccp.LOGGER.finest("bytes.length: " + bytes.length);
        }
        this.uncompressedProfile = BinaryFunctions.getStreamBytes(new InflaterInputStream(new ByteArrayInputStream(this.compressedProfile)));
        if (PngChunkIccp.LOGGER.isLoggable(Level.FINEST)) {
            PngChunkIccp.LOGGER.finest("UncompressedProfile: " + Integer.toString(bytes.length));
        }
    }
    
    static {
        LOGGER = Logger.getLogger(PngChunkIccp.class.getName());
    }
}
