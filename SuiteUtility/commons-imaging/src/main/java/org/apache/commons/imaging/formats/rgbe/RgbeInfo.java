// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.commons.imaging.formats.rgbe;

import org.apache.commons.imaging.common.ByteConversions;
import java.nio.ByteOrder;
import org.apache.commons.imaging.common.BinaryFunctions;
import java.util.regex.Matcher;
import org.apache.commons.imaging.ImageReadException;
import org.apache.commons.imaging.common.ImageMetadata;
import java.io.IOException;
import org.apache.commons.imaging.common.bytesource.ByteSource;
import org.apache.commons.imaging.common.GenericImageMetadata;
import java.io.InputStream;
import java.util.regex.Pattern;
import java.io.Closeable;

class RgbeInfo implements Closeable
{
    private static final byte[] HEADER;
    private static final Pattern RESOLUTION_STRING;
    private final InputStream in;
    private GenericImageMetadata metadata;
    private int width;
    private int height;
    private static final byte[] TWO_TWO;
    
    RgbeInfo(final ByteSource byteSource) throws IOException {
        this.width = -1;
        this.height = -1;
        this.in = byteSource.getInputStream();
    }
    
    ImageMetadata getMetadata() throws IOException, ImageReadException {
        if (null == this.metadata) {
            this.readMetadata();
        }
        return this.metadata;
    }
    
    int getWidth() throws IOException, ImageReadException {
        if (-1 == this.width) {
            this.readDimensions();
        }
        return this.width;
    }
    
    int getHeight() throws IOException, ImageReadException {
        if (-1 == this.height) {
            this.readDimensions();
        }
        return this.height;
    }
    
    @Override
    public void close() throws IOException {
        this.in.close();
    }
    
    private void readDimensions() throws IOException, ImageReadException {
        this.getMetadata();
        final InfoHeaderReader reader = new InfoHeaderReader(this.in);
        final String resolution = reader.readNextLine();
        final Matcher matcher = RgbeInfo.RESOLUTION_STRING.matcher(resolution);
        if (!matcher.matches()) {
            throw new ImageReadException("Invalid HDR resolution string. Only \"-Y N +X M\" is supported. Found \"" + resolution + "\"");
        }
        this.height = Integer.parseInt(matcher.group(1));
        this.width = Integer.parseInt(matcher.group(2));
    }
    
    private void readMetadata() throws IOException, ImageReadException {
        BinaryFunctions.readAndVerifyBytes(this.in, RgbeInfo.HEADER, "Not a valid HDR: Incorrect Header");
        final InfoHeaderReader reader = new InfoHeaderReader(this.in);
        if (reader.readNextLine().length() != 0) {
            throw new ImageReadException("Not a valid HDR: Incorrect Header");
        }
        this.metadata = new GenericImageMetadata();
        for (String info = reader.readNextLine(); info.length() != 0; info = reader.readNextLine()) {
            final int equals = info.indexOf(61);
            if (equals > 0) {
                final String variable = info.substring(0, equals);
                final String value = info.substring(equals + 1);
                if ("FORMAT".equals(value) && !"32-bit_rle_rgbe".equals(value)) {
                    throw new ImageReadException("Only 32-bit_rle_rgbe images are supported, trying to read " + value);
                }
                this.metadata.add(variable, value);
            }
            else {
                this.metadata.add("<command>", info);
            }
        }
    }
    
    public float[][] getPixelData() throws IOException, ImageReadException {
        final int ht = this.getHeight();
        final int wd = this.getWidth();
        if (wd >= 32768) {
            throw new ImageReadException("Scan lines must be less than 32768 bytes long");
        }
        final byte[] scanLineBytes = ByteConversions.toBytes((short)wd, ByteOrder.BIG_ENDIAN);
        final byte[] rgbe = new byte[wd * 4];
        final float[][] out = new float[3][wd * ht];
        for (int i = 0; i < ht; ++i) {
            BinaryFunctions.readAndVerifyBytes(this.in, RgbeInfo.TWO_TWO, "Scan line " + i + " expected to start with 0x2 0x2");
            BinaryFunctions.readAndVerifyBytes(this.in, scanLineBytes, "Scan line " + i + " length expected");
            decompress(this.in, rgbe);
            for (int channel = 0; channel < 3; ++channel) {
                final int channelOffset = channel * wd;
                final int eOffset = 3 * wd;
                for (int p = 0; p < wd; ++p) {
                    final int mantissa = rgbe[p + eOffset] & 0xFF;
                    final int pos = p + i * wd;
                    if (0 == mantissa) {
                        out[channel][pos] = 0.0f;
                    }
                    else {
                        final float mult = (float)Math.pow(2.0, mantissa - 136);
                        out[channel][pos] = ((rgbe[p + channelOffset] & 0xFF) + 0.5f) * mult;
                    }
                }
            }
        }
        return out;
    }
    
    private static void decompress(final InputStream in, final byte[] out) throws IOException {
        int position = 0;
        final int total = out.length;
        while (position < total) {
            final int n = in.read();
            if (n > 128) {
                final int value = in.read();
                for (int i = 0; i < (n & 0x7F); ++i) {
                    out[position++] = (byte)value;
                }
            }
            else {
                for (int j = 0; j < n; ++j) {
                    out[position++] = (byte)in.read();
                }
            }
        }
    }
    
    static {
        HEADER = new byte[] { 35, 63, 82, 65, 68, 73, 65, 78, 67, 69 };
        RESOLUTION_STRING = Pattern.compile("-Y (\\d+) \\+X (\\d+)");
        TWO_TWO = new byte[] { 2, 2 };
    }
}
