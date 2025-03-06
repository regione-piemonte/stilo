// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.ext.awt.image.codec.png;

import java.io.OutputStream;
import org.apache.batik.transcoder.TranscodingHints;
import java.io.IOException;
import org.apache.batik.transcoder.TranscoderException;
import java.awt.image.RenderedImage;
import org.apache.batik.ext.awt.image.rendered.IndexImage;
import org.apache.batik.transcoder.TranscoderOutput;
import java.awt.image.BufferedImage;
import org.apache.batik.transcoder.image.PNGTranscoder;

public class PNGTranscoderInternalCodecWriteAdapter implements PNGTranscoder.WriteAdapter
{
    public void writeImage(final PNGTranscoder pngTranscoder, BufferedImage indexedImage, final TranscoderOutput transcoderOutput) throws TranscoderException {
        final TranscodingHints transcodingHints = pngTranscoder.getTranscodingHints();
        if (transcodingHints.containsKey(PNGTranscoder.KEY_INDEXED)) {
            final int intValue = (int)transcodingHints.get(PNGTranscoder.KEY_INDEXED);
            if (intValue == 1 || intValue == 2 || intValue == 4 || intValue == 8) {
                indexedImage = IndexImage.getIndexedImage(indexedImage, 1 << intValue);
            }
        }
        final PNGEncodeParam defaultEncodeParam = PNGEncodeParam.getDefaultEncodeParam(indexedImage);
        if (defaultEncodeParam instanceof PNGEncodeParam.RGB) {
            ((PNGEncodeParam.RGB)defaultEncodeParam).setBackgroundRGB(new int[] { 255, 255, 255 });
        }
        if (transcodingHints.containsKey(PNGTranscoder.KEY_GAMMA)) {
            final float floatValue = (float)transcodingHints.get(PNGTranscoder.KEY_GAMMA);
            if (floatValue > 0.0f) {
                defaultEncodeParam.setGamma(floatValue);
            }
            defaultEncodeParam.setChromaticity(PNGTranscoder.DEFAULT_CHROMA);
        }
        else {
            defaultEncodeParam.setSRGBIntent(0);
        }
        final int n = (int)(1000.0f / pngTranscoder.getUserAgent().getPixelUnitToMillimeter() + 0.5);
        defaultEncodeParam.setPhysicalDimension(n, n, 1);
        try {
            final OutputStream outputStream = transcoderOutput.getOutputStream();
            new PNGImageEncoder(outputStream, defaultEncodeParam).encode(indexedImage);
            outputStream.flush();
        }
        catch (IOException ex) {
            throw new TranscoderException(ex);
        }
    }
}
