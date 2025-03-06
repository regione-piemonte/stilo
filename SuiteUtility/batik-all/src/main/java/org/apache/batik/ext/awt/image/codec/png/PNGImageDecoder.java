// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.ext.awt.image.codec.png;

import java.io.IOException;
import org.apache.batik.ext.awt.image.codec.util.PropertyUtil;
import java.awt.image.RenderedImage;
import org.apache.batik.ext.awt.image.codec.util.ImageDecodeParam;
import java.io.InputStream;
import org.apache.batik.ext.awt.image.codec.util.ImageDecoderImpl;

public class PNGImageDecoder extends ImageDecoderImpl
{
    public PNGImageDecoder(final InputStream inputStream, final PNGDecodeParam pngDecodeParam) {
        super(inputStream, pngDecodeParam);
    }
    
    public RenderedImage decodeAsRenderedImage(final int n) throws IOException {
        if (n != 0) {
            throw new IOException(PropertyUtil.getString("PNGImageDecoder19"));
        }
        return new PNGImage(this.input, (PNGDecodeParam)this.param);
    }
}
