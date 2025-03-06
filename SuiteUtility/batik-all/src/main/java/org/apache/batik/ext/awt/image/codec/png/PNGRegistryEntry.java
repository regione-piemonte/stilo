// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.ext.awt.image.codec.png;

import java.awt.image.ColorModel;
import java.io.IOException;
import org.apache.batik.ext.awt.image.spi.ImageTagRegistry;
import org.apache.batik.ext.awt.image.renderable.RedRable;
import java.awt.image.RenderedImage;
import java.util.Hashtable;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import org.apache.batik.ext.awt.image.rendered.FormatRed;
import org.apache.batik.ext.awt.image.GraphicsUtil;
import org.apache.batik.ext.awt.image.rendered.CachableRed;
import org.apache.batik.ext.awt.image.rendered.Any2sRGBRed;
import java.awt.geom.Rectangle2D;
import org.apache.batik.ext.awt.image.renderable.DeferRable;
import org.apache.batik.ext.awt.image.renderable.Filter;
import org.apache.batik.util.ParsedURL;
import java.io.InputStream;
import org.apache.batik.ext.awt.image.spi.MagicNumberRegistryEntry;

public class PNGRegistryEntry extends MagicNumberRegistryEntry
{
    static final byte[] signature;
    
    public PNGRegistryEntry() {
        super("PNG", "png", "image/png", 0, PNGRegistryEntry.signature);
    }
    
    public Filter handleStream(final InputStream inputStream, final ParsedURL parsedURL, final boolean b) {
        final DeferRable deferRable = new DeferRable();
        String s;
        Object[] array;
        if (parsedURL != null) {
            s = "url.format.unreadable";
            array = new Object[] { "PNG", parsedURL };
        }
        else {
            s = "stream.format.unreadable";
            array = new Object[] { "PNG" };
        }
        new Thread() {
            public void run() {
                Filter source;
                try {
                    final PNGDecodeParam pngDecodeParam = new PNGDecodeParam();
                    pngDecodeParam.setExpandPalette(true);
                    if (b) {
                        pngDecodeParam.setPerformGammaCorrection(false);
                    }
                    else {
                        pngDecodeParam.setPerformGammaCorrection(true);
                        pngDecodeParam.setDisplayExponent(2.2f);
                    }
                    final PNGRed pngRed = new PNGRed(inputStream, pngDecodeParam);
                    deferRable.setBounds(new Rectangle2D.Double(0.0, 0.0, pngRed.getWidth(), pngRed.getHeight()));
                    final FormatRed formatRed = new FormatRed(new Any2sRGBRed(pngRed), GraphicsUtil.sRGB_Unpre);
                    final WritableRaster raster = (WritableRaster)formatRed.getData();
                    final ColorModel colorModel = formatRed.getColorModel();
                    source = new RedRable(GraphicsUtil.wrap(new BufferedImage(colorModel, raster, colorModel.isAlphaPremultiplied(), null)));
                }
                catch (IOException ex) {
                    source = ImageTagRegistry.getBrokenLinkImage(PNGRegistryEntry.this, s, array);
                }
                catch (ThreadDeath threadDeath) {
                    deferRable.setSource(ImageTagRegistry.getBrokenLinkImage(PNGRegistryEntry.this, s, array));
                    throw threadDeath;
                }
                catch (Throwable t) {
                    source = ImageTagRegistry.getBrokenLinkImage(PNGRegistryEntry.this, s, array);
                }
                deferRable.setSource(source);
            }
        }.start();
        return deferRable;
    }
    
    static {
        signature = new byte[] { -119, 80, 78, 71, 13, 10, 26, 10 };
    }
}
