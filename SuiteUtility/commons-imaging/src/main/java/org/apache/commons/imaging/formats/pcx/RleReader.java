// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.commons.imaging.formats.pcx;

import java.io.IOException;
import org.apache.commons.imaging.ImageReadException;
import org.apache.commons.imaging.common.BinaryFunctions;
import java.util.Arrays;
import java.io.InputStream;

class RleReader
{
    private final boolean isCompressed;
    private int count;
    private byte sample;
    
    RleReader(final boolean isCompressed) {
        this.isCompressed = isCompressed;
    }
    
    void read(final InputStream is, final byte[] samples) throws IOException, ImageReadException {
        if (this.isCompressed) {
            final int prefill = Math.min(this.count, samples.length);
            Arrays.fill(samples, 0, prefill, this.sample);
            this.count -= prefill;
            int samplesToAdd;
            for (int bytesRead = prefill; bytesRead < samples.length; bytesRead += samplesToAdd, this.count -= samplesToAdd) {
                final byte b = BinaryFunctions.readByte("RleByte", is, "Error reading image data");
                if ((b & 0xC0) == 0xC0) {
                    this.count = (b & 0x3F);
                    this.sample = BinaryFunctions.readByte("RleValue", is, "Error reading image data");
                }
                else {
                    this.count = 1;
                    this.sample = b;
                }
                samplesToAdd = Math.min(this.count, samples.length - bytesRead);
                Arrays.fill(samples, bytesRead, bytesRead + samplesToAdd, this.sample);
            }
        }
        else {
            int r;
            for (int bytesRead = 0; bytesRead < samples.length; bytesRead += r) {
                r = is.read(samples, bytesRead, samples.length - bytesRead);
                if (r < 0) {
                    throw new ImageReadException("Premature end of file reading image data");
                }
            }
        }
    }
}
