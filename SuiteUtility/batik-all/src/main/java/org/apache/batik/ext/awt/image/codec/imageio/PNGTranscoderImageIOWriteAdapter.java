// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.ext.awt.image.codec.imageio;

import java.io.OutputStream;
import org.apache.batik.ext.awt.image.spi.ImageWriter;
import org.apache.batik.transcoder.TranscodingHints;
import java.io.IOException;
import org.apache.batik.transcoder.TranscoderException;
import java.awt.image.RenderedImage;
import org.apache.batik.ext.awt.image.spi.ImageWriterParams;
import org.apache.batik.ext.awt.image.spi.ImageWriterRegistry;
import org.apache.batik.ext.awt.image.rendered.IndexImage;
import org.apache.batik.transcoder.TranscoderOutput;
import java.awt.image.BufferedImage;
import org.apache.batik.transcoder.image.PNGTranscoder;

public class PNGTranscoderImageIOWriteAdapter implements PNGTranscoder.WriteAdapter
{
    public void writeImage(final PNGTranscoder pngTranscoder, BufferedImage indexedImage, final TranscoderOutput transcoderOutput) throws TranscoderException {
        final TranscodingHints transcodingHints = pngTranscoder.getTranscodingHints();
        if (transcodingHints.containsKey(PNGTranscoder.KEY_INDEXED)) {
            final int intValue = (int)transcodingHints.get(PNGTranscoder.KEY_INDEXED);
            if (intValue == 1 || intValue == 2 || intValue == 4 || intValue == 8) {
                indexedImage = IndexImage.getIndexedImage(indexedImage, 1 << intValue);
            }
        }
        final ImageWriter writer = ImageWriterRegistry.getInstance().getWriterFor("image/png");
        final ImageWriterParams imageWriterParams = new ImageWriterParams();
        imageWriterParams.setResolution((int)(25.4 / pngTranscoder.getUserAgent().getPixelUnitToMillimeter() + 0.5));
        try {
            final OutputStream outputStream = transcoderOutput.getOutputStream();
            writer.writeImage(indexedImage, outputStream, imageWriterParams);
            outputStream.flush();
        }
        catch (IOException ex) {
            throw new TranscoderException(ex);
        }
    }
}
