// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.ext.awt.image.codec.jpeg;

import java.awt.image.ColorModel;
import com.sun.image.codec.jpeg.JPEGImageDecoder;
import org.apache.batik.ext.awt.image.spi.ImageTagRegistry;
import org.apache.batik.ext.awt.image.renderable.RedRable;
import java.util.Hashtable;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import org.apache.batik.ext.awt.image.rendered.CachableRed;
import org.apache.batik.ext.awt.image.rendered.FormatRed;
import org.apache.batik.ext.awt.image.rendered.Any2sRGBRed;
import java.awt.image.RenderedImage;
import org.apache.batik.ext.awt.image.GraphicsUtil;
import java.awt.geom.Rectangle2D;
import com.sun.image.codec.jpeg.TruncatedFileException;
import java.io.IOException;
import com.sun.image.codec.jpeg.JPEGCodec;
import org.apache.batik.ext.awt.image.renderable.DeferRable;
import org.apache.batik.ext.awt.image.renderable.Filter;
import org.apache.batik.util.ParsedURL;
import java.io.InputStream;
import org.apache.batik.ext.awt.image.spi.MagicNumberRegistryEntry;

public class JPEGRegistryEntry extends MagicNumberRegistryEntry
{
    static final byte[] sigJPEG;
    static final String[] exts;
    static final String[] mimeTypes;
    static final MagicNumber[] magicNumbers;
    
    public JPEGRegistryEntry() {
        super("JPEG", JPEGRegistryEntry.exts, JPEGRegistryEntry.mimeTypes, JPEGRegistryEntry.magicNumbers);
    }
    
    public Filter handleStream(final InputStream inputStream, final ParsedURL parsedURL, final boolean b) {
        final DeferRable deferRable = new DeferRable();
        String s;
        Object[] array;
        if (parsedURL != null) {
            s = "url.format.unreadable";
            array = new Object[] { "JPEG", parsedURL };
        }
        else {
            s = "stream.format.unreadable";
            array = new Object[] { "JPEG" };
        }
        new Thread() {
            public void run() {
                Filter source;
                try {
                    final JPEGImageDecoder jpegDecoder = JPEGCodec.createJPEGDecoder(inputStream);
                    BufferedImage bufferedImage;
                    try {
                        bufferedImage = jpegDecoder.decodeAsBufferedImage();
                    }
                    catch (TruncatedFileException ex) {
                        bufferedImage = ex.getBufferedImage();
                        if (bufferedImage == null) {
                            throw new IOException("JPEG File was truncated");
                        }
                    }
                    deferRable.setBounds(new Rectangle2D.Double(0.0, 0.0, bufferedImage.getWidth(), bufferedImage.getHeight()));
                    final FormatRed formatRed = new FormatRed(new Any2sRGBRed(GraphicsUtil.wrap(bufferedImage)), GraphicsUtil.sRGB_Unpre);
                    final WritableRaster raster = (WritableRaster)formatRed.getData();
                    final ColorModel colorModel = formatRed.getColorModel();
                    source = new RedRable(GraphicsUtil.wrap(new BufferedImage(colorModel, raster, colorModel.isAlphaPremultiplied(), null)));
                }
                catch (IOException ex2) {
                    source = ImageTagRegistry.getBrokenLinkImage(JPEGRegistryEntry.this, s, array);
                }
                catch (ThreadDeath threadDeath) {
                    deferRable.setSource(ImageTagRegistry.getBrokenLinkImage(JPEGRegistryEntry.this, s, array));
                    throw threadDeath;
                }
                catch (Throwable t) {
                    source = ImageTagRegistry.getBrokenLinkImage(JPEGRegistryEntry.this, s, array);
                }
                deferRable.setSource(source);
            }
        }.start();
        return deferRable;
    }
    
    static {
        sigJPEG = new byte[] { -1, -40, -1 };
        exts = new String[] { "jpeg", "jpg" };
        mimeTypes = new String[] { "image/jpeg", "image/jpg" };
        magicNumbers = new MagicNumber[] { new MagicNumber(0, JPEGRegistryEntry.sigJPEG) };
    }
}
