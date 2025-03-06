// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.transcoder.image;

import org.apache.batik.ext.awt.image.spi.ImageWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.awt.image.RenderedImage;
import org.apache.batik.ext.awt.image.spi.ImageWriterParams;
import org.apache.batik.ext.awt.image.spi.ImageWriterRegistry;
import org.apache.batik.transcoder.TranscoderException;
import org.apache.batik.transcoder.image.resources.Messages;
import org.apache.batik.transcoder.TranscoderOutput;
import java.awt.image.BufferedImage;
import java.awt.Color;
import org.apache.batik.transcoder.TranscodingHints;

public class JPEGTranscoder extends ImageTranscoder
{
    public static final TranscodingHints.Key KEY_QUALITY;
    
    public JPEGTranscoder() {
        this.hints.put(ImageTranscoder.KEY_BACKGROUND_COLOR, Color.white);
    }
    
    public BufferedImage createImage(final int width, final int height) {
        return new BufferedImage(width, height, 1);
    }
    
    public void writeImage(final BufferedImage bufferedImage, final TranscoderOutput transcoderOutput) throws TranscoderException {
        final OutputStreamWrapper outputStreamWrapper = new OutputStreamWrapper(transcoderOutput.getOutputStream());
        if (outputStreamWrapper == null) {
            throw new TranscoderException(Messages.formatMessage("jpeg.badoutput", null));
        }
        try {
            float floatValue;
            if (this.hints.containsKey(JPEGTranscoder.KEY_QUALITY)) {
                floatValue = (float)this.hints.get(JPEGTranscoder.KEY_QUALITY);
            }
            else {
                this.handler.error(new TranscoderException(Messages.formatMessage("jpeg.unspecifiedQuality", null)));
                floatValue = 0.75f;
            }
            final ImageWriter writer = ImageWriterRegistry.getInstance().getWriterFor("image/jpeg");
            final ImageWriterParams imageWriterParams = new ImageWriterParams();
            imageWriterParams.setJPEGQuality(floatValue, true);
            imageWriterParams.setResolution((int)(25.4 / this.userAgent.getPixelUnitToMillimeter() + 0.5));
            writer.writeImage(bufferedImage, outputStreamWrapper, imageWriterParams);
            outputStreamWrapper.flush();
        }
        catch (IOException ex) {
            throw new TranscoderException(ex);
        }
    }
    
    static {
        KEY_QUALITY = new QualityKey();
    }
    
    private static class OutputStreamWrapper extends OutputStream
    {
        OutputStream os;
        
        OutputStreamWrapper(final OutputStream os) {
            this.os = os;
        }
        
        public void close() throws IOException {
            if (this.os == null) {
                return;
            }
            try {
                this.os.close();
            }
            catch (IOException ex) {
                this.os = null;
            }
        }
        
        public void flush() throws IOException {
            if (this.os == null) {
                return;
            }
            try {
                this.os.flush();
            }
            catch (IOException ex) {
                this.os = null;
            }
        }
        
        public void write(final byte[] b) throws IOException {
            if (this.os == null) {
                return;
            }
            try {
                this.os.write(b);
            }
            catch (IOException ex) {
                this.os = null;
            }
        }
        
        public void write(final byte[] b, final int off, final int len) throws IOException {
            if (this.os == null) {
                return;
            }
            try {
                this.os.write(b, off, len);
            }
            catch (IOException ex) {
                this.os = null;
            }
        }
        
        public void write(final int n) throws IOException {
            if (this.os == null) {
                return;
            }
            try {
                this.os.write(n);
            }
            catch (IOException ex) {
                this.os = null;
            }
        }
    }
    
    private static class QualityKey extends TranscodingHints.Key
    {
        public boolean isCompatibleValue(final Object o) {
            if (o instanceof Float) {
                final float floatValue = (float)o;
                return floatValue > 0.0f && floatValue <= 1.0f;
            }
            return false;
        }
    }
}
