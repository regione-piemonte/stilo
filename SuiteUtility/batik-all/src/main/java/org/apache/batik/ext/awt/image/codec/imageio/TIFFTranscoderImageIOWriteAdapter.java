// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.ext.awt.image.codec.imageio;

import java.io.OutputStream;
import org.apache.batik.ext.awt.image.spi.ImageWriter;
import org.apache.batik.transcoder.TranscodingHints;
import java.io.IOException;
import org.apache.batik.transcoder.TranscoderException;
import java.awt.image.SampleModel;
import org.apache.batik.ext.awt.image.rendered.FormatRed;
import java.awt.image.PixelInterleavedSampleModel;
import java.awt.image.RenderedImage;
import org.apache.batik.ext.awt.image.GraphicsUtil;
import java.awt.image.SinglePixelPackedSampleModel;
import org.apache.batik.ext.awt.image.spi.ImageWriterParams;
import org.apache.batik.ext.awt.image.spi.ImageWriterRegistry;
import org.apache.batik.transcoder.TranscoderOutput;
import java.awt.image.BufferedImage;
import org.apache.batik.transcoder.image.TIFFTranscoder;

public class TIFFTranscoderImageIOWriteAdapter implements TIFFTranscoder.WriteAdapter
{
    public void writeImage(final TIFFTranscoder tiffTranscoder, final BufferedImage bufferedImage, final TranscoderOutput transcoderOutput) throws TranscoderException {
        final TranscodingHints transcodingHints = tiffTranscoder.getTranscodingHints();
        final ImageWriter writer = ImageWriterRegistry.getInstance().getWriterFor("image/tiff");
        final ImageWriterParams imageWriterParams = new ImageWriterParams();
        imageWriterParams.setResolution((int)(25.4 / tiffTranscoder.getUserAgent().getPixelUnitToMillimeter() + 0.5));
        if (transcodingHints.containsKey(TIFFTranscoder.KEY_COMPRESSION_METHOD)) {
            final String s = (String)transcodingHints.get(TIFFTranscoder.KEY_COMPRESSION_METHOD);
            if ("packbits".equals(s)) {
                imageWriterParams.setCompressionMethod("PackBits");
            }
            else if ("deflate".equals(s)) {
                imageWriterParams.setCompressionMethod("Deflate");
            }
            else if ("lzw".equals(s)) {
                imageWriterParams.setCompressionMethod("LZW");
            }
            else if ("jpeg".equals(s)) {
                imageWriterParams.setCompressionMethod("JPEG");
            }
        }
        try {
            final OutputStream outputStream = transcoderOutput.getOutputStream();
            final int width = bufferedImage.getWidth();
            final int height = bufferedImage.getHeight();
            final int numBands = ((SinglePixelPackedSampleModel)bufferedImage.getSampleModel()).getNumBands();
            final int[] bandOffsets = new int[numBands];
            for (int i = 0; i < numBands; ++i) {
                bandOffsets[i] = i;
            }
            writer.writeImage(new FormatRed(GraphicsUtil.wrap(bufferedImage), new PixelInterleavedSampleModel(0, width, height, numBands, width * numBands, bandOffsets)), outputStream, imageWriterParams);
            outputStream.flush();
        }
        catch (IOException ex) {
            throw new TranscoderException(ex);
        }
    }
}
