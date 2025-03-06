// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.ext.awt.image.codec.tiff;

import java.io.OutputStream;
import org.apache.batik.transcoder.TranscodingHints;
import java.io.IOException;
import org.apache.batik.transcoder.TranscoderException;
import java.awt.image.SampleModel;
import org.apache.batik.ext.awt.image.rendered.FormatRed;
import java.awt.image.PixelInterleavedSampleModel;
import java.awt.image.RenderedImage;
import org.apache.batik.ext.awt.image.GraphicsUtil;
import org.apache.batik.ext.awt.image.codec.util.ImageEncodeParam;
import java.awt.image.SinglePixelPackedSampleModel;
import org.apache.batik.transcoder.TranscoderOutput;
import java.awt.image.BufferedImage;
import org.apache.batik.transcoder.image.TIFFTranscoder;

public class TIFFTranscoderInternalCodecWriteAdapter implements TIFFTranscoder.WriteAdapter
{
    public void writeImage(final TIFFTranscoder tiffTranscoder, final BufferedImage bufferedImage, final TranscoderOutput transcoderOutput) throws TranscoderException {
        final TranscodingHints transcodingHints = tiffTranscoder.getTranscodingHints();
        final TIFFEncodeParam tiffEncodeParam = new TIFFEncodeParam();
        final long[] array = { (long)(int)(100000.0f / tiffTranscoder.getUserAgent().getPixelUnitToMillimeter() + 0.5), 10000 };
        tiffEncodeParam.setExtraFields(new TIFFField[] { new TIFFField(296, 3, 1, new char[] { '\u0003' }), new TIFFField(282, 5, 1, new long[][] { array }), new TIFFField(283, 5, 1, new long[][] { array }) });
        if (transcodingHints.containsKey(TIFFTranscoder.KEY_COMPRESSION_METHOD)) {
            final String s = (String)transcodingHints.get(TIFFTranscoder.KEY_COMPRESSION_METHOD);
            if ("packbits".equals(s)) {
                tiffEncodeParam.setCompression(32773);
            }
            else if ("deflate".equals(s)) {
                tiffEncodeParam.setCompression(32946);
            }
        }
        try {
            final int width = bufferedImage.getWidth();
            final int height = bufferedImage.getHeight();
            final SinglePixelPackedSampleModel singlePixelPackedSampleModel = (SinglePixelPackedSampleModel)bufferedImage.getSampleModel();
            final OutputStream outputStream = transcoderOutput.getOutputStream();
            final TIFFImageEncoder tiffImageEncoder = new TIFFImageEncoder(outputStream, tiffEncodeParam);
            final int numBands = singlePixelPackedSampleModel.getNumBands();
            final int[] bandOffsets = new int[numBands];
            for (int i = 0; i < numBands; ++i) {
                bandOffsets[i] = i;
            }
            tiffImageEncoder.encode(new FormatRed(GraphicsUtil.wrap(bufferedImage), new PixelInterleavedSampleModel(0, width, height, numBands, width * numBands, bandOffsets)));
            outputStream.flush();
        }
        catch (IOException ex) {
            throw new TranscoderException(ex);
        }
    }
}
