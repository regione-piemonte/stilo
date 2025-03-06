// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.transcoder.image;

import org.apache.batik.transcoder.keys.StringKey;
import org.apache.batik.transcoder.TranscoderException;
import java.awt.image.SinglePixelPackedSampleModel;
import org.apache.batik.transcoder.TranscoderOutput;
import java.awt.image.BufferedImage;
import org.apache.batik.bridge.UserAgent;
import org.apache.batik.transcoder.TranscodingHints;

public class TIFFTranscoder extends ImageTranscoder
{
    public static final TranscodingHints.Key KEY_FORCE_TRANSPARENT_WHITE;
    public static final TranscodingHints.Key KEY_COMPRESSION_METHOD;
    
    public TIFFTranscoder() {
        this.hints.put(TIFFTranscoder.KEY_FORCE_TRANSPARENT_WHITE, Boolean.FALSE);
    }
    
    public UserAgent getUserAgent() {
        return this.userAgent;
    }
    
    public BufferedImage createImage(final int width, final int height) {
        return new BufferedImage(width, height, 2);
    }
    
    private WriteAdapter getWriteAdapter(final String className) {
        try {
            return (WriteAdapter)Class.forName(className).newInstance();
        }
        catch (ClassNotFoundException ex) {
            return null;
        }
        catch (InstantiationException ex2) {
            return null;
        }
        catch (IllegalAccessException ex3) {
            return null;
        }
    }
    
    public void writeImage(final BufferedImage bufferedImage, final TranscoderOutput transcoderOutput) throws TranscoderException {
        boolean booleanValue = false;
        if (this.hints.containsKey(PNGTranscoder.KEY_FORCE_TRANSPARENT_WHITE)) {
            booleanValue = (boolean)this.hints.get(PNGTranscoder.KEY_FORCE_TRANSPARENT_WHITE);
        }
        if (booleanValue) {
            this.forceTransparentWhite(bufferedImage, (SinglePixelPackedSampleModel)bufferedImage.getSampleModel());
        }
        WriteAdapter writeAdapter = this.getWriteAdapter("org.apache.batik.ext.awt.image.codec.tiff.TIFFTranscoderInternalCodecWriteAdapter");
        if (writeAdapter == null) {
            writeAdapter = this.getWriteAdapter("org.apache.batik.transcoder.image.TIFFTranscoderImageIOWriteAdapter");
        }
        if (writeAdapter == null) {
            throw new TranscoderException("Could not write TIFF file because no WriteAdapter is availble");
        }
        writeAdapter.writeImage(this, bufferedImage, transcoderOutput);
    }
    
    static {
        KEY_FORCE_TRANSPARENT_WHITE = ImageTranscoder.KEY_FORCE_TRANSPARENT_WHITE;
        KEY_COMPRESSION_METHOD = new StringKey();
    }
    
    public interface WriteAdapter
    {
        void writeImage(final TIFFTranscoder p0, final BufferedImage p1, final TranscoderOutput p2) throws TranscoderException;
    }
}
