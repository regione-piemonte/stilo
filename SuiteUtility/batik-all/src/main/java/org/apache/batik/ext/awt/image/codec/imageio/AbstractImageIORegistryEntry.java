// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.ext.awt.image.codec.imageio;

import java.awt.image.ColorModel;
import java.util.Iterator;
import java.io.IOException;
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
import javax.imageio.ImageReader;
import javax.imageio.ImageIO;
import org.apache.batik.ext.awt.image.renderable.DeferRable;
import org.apache.batik.ext.awt.image.renderable.Filter;
import org.apache.batik.util.ParsedURL;
import java.io.InputStream;
import org.apache.batik.ext.awt.image.spi.MagicNumberRegistryEntry;

public abstract class AbstractImageIORegistryEntry extends MagicNumberRegistryEntry
{
    public AbstractImageIORegistryEntry(final String s, final String[] array, final String[] array2, final MagicNumber[] array3) {
        super(s, 1100.0f, array, array2, array3);
    }
    
    public AbstractImageIORegistryEntry(final String s, final String s2, final String s3, final int n, final byte[] array) {
        super(s, 1100.0f, s2, s3, n, array);
    }
    
    public Filter handleStream(final InputStream inputStream, final ParsedURL parsedURL, final boolean b) {
        final DeferRable deferRable = new DeferRable();
        String s;
        Object[] array;
        if (parsedURL != null) {
            s = "url.format.unreadable";
            array = new Object[] { this.getFormatName(), parsedURL };
        }
        else {
            s = "stream.format.unreadable";
            array = new Object[] { this.getFormatName() };
        }
        new Thread() {
            public void run() {
                Filter source;
                try {
                    final Iterator<ImageReader> imageReadersByMIMEType = ImageIO.getImageReadersByMIMEType(AbstractImageIORegistryEntry.this.getMimeTypes().get(0).toString());
                    if (!imageReadersByMIMEType.hasNext()) {
                        throw new UnsupportedOperationException("No image reader for " + AbstractImageIORegistryEntry.this.getFormatName() + " available!");
                    }
                    final ImageReader imageReader = imageReadersByMIMEType.next();
                    imageReader.setInput(ImageIO.createImageInputStream(inputStream), true);
                    final int imageIndex = 0;
                    deferRable.setBounds(new Rectangle2D.Double(0.0, 0.0, imageReader.getWidth(imageIndex), imageReader.getHeight(imageIndex)));
                    final FormatRed formatRed = new FormatRed(new Any2sRGBRed(GraphicsUtil.wrap(imageReader.read(imageIndex))), GraphicsUtil.sRGB_Unpre);
                    final WritableRaster raster = (WritableRaster)formatRed.getData();
                    final ColorModel colorModel = formatRed.getColorModel();
                    source = new RedRable(GraphicsUtil.wrap(new BufferedImage(colorModel, raster, colorModel.isAlphaPremultiplied(), null)));
                }
                catch (IOException ex) {
                    source = ImageTagRegistry.getBrokenLinkImage(AbstractImageIORegistryEntry.this, s, array);
                }
                catch (ThreadDeath threadDeath) {
                    deferRable.setSource(ImageTagRegistry.getBrokenLinkImage(AbstractImageIORegistryEntry.this, s, array));
                    throw threadDeath;
                }
                catch (Throwable t) {
                    source = ImageTagRegistry.getBrokenLinkImage(AbstractImageIORegistryEntry.this, s, array);
                }
                deferRable.setSource(source);
            }
        }.start();
        return deferRable;
    }
}
