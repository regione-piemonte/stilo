// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.transcoder.image;

import org.apache.batik.transcoder.keys.IntegerKey;
import org.apache.batik.transcoder.keys.FloatKey;
import java.awt.image.SinglePixelPackedSampleModel;
import org.apache.batik.transcoder.TranscoderException;
import org.apache.batik.transcoder.image.resources.Messages;
import org.apache.batik.transcoder.TranscoderOutput;
import java.awt.image.BufferedImage;
import org.apache.batik.bridge.UserAgent;
import org.apache.batik.transcoder.TranscodingHints;

public class PNGTranscoder extends ImageTranscoder
{
    public static final TranscodingHints.Key KEY_GAMMA;
    public static final float[] DEFAULT_CHROMA;
    public static final TranscodingHints.Key KEY_INDEXED;
    
    public PNGTranscoder() {
        this.hints.put(PNGTranscoder.KEY_FORCE_TRANSPARENT_WHITE, Boolean.FALSE);
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
        if (transcoderOutput.getOutputStream() == null) {
            throw new TranscoderException(Messages.formatMessage("png.badoutput", null));
        }
        boolean booleanValue = false;
        if (this.hints.containsKey(PNGTranscoder.KEY_FORCE_TRANSPARENT_WHITE)) {
            booleanValue = (boolean)this.hints.get(PNGTranscoder.KEY_FORCE_TRANSPARENT_WHITE);
        }
        if (booleanValue) {
            this.forceTransparentWhite(bufferedImage, (SinglePixelPackedSampleModel)bufferedImage.getSampleModel());
        }
        WriteAdapter writeAdapter = this.getWriteAdapter("org.apache.batik.ext.awt.image.codec.png.PNGTranscoderInternalCodecWriteAdapter");
        if (writeAdapter == null) {
            writeAdapter = this.getWriteAdapter("org.apache.batik.transcoder.image.PNGTranscoderImageIOWriteAdapter");
        }
        if (writeAdapter == null) {
            throw new TranscoderException("Could not write PNG file because no WriteAdapter is availble");
        }
        writeAdapter.writeImage(this, bufferedImage, transcoderOutput);
    }
    
    static {
        KEY_GAMMA = new FloatKey();
        DEFAULT_CHROMA = new float[] { 0.3127f, 0.329f, 0.64f, 0.33f, 0.3f, 0.6f, 0.15f, 0.06f };
        KEY_INDEXED = new IntegerKey();
    }
    
    public interface WriteAdapter
    {
        void writeImage(final PNGTranscoder p0, final BufferedImage p1, final TranscoderOutput p2) throws TranscoderException;
    }
}
