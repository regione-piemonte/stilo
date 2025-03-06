// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.ext.awt.image.codec.tiff;

import java.io.IOException;
import org.apache.batik.ext.awt.image.spi.ImageTagRegistry;
import org.apache.batik.ext.awt.image.renderable.RedRable;
import org.apache.batik.ext.awt.image.rendered.CachableRed;
import org.apache.batik.ext.awt.image.rendered.Any2sRGBRed;
import org.apache.batik.ext.awt.image.codec.util.SeekableStream;
import org.apache.batik.ext.awt.image.renderable.DeferRable;
import org.apache.batik.ext.awt.image.renderable.Filter;
import org.apache.batik.util.ParsedURL;
import java.io.InputStream;
import org.apache.batik.ext.awt.image.spi.MagicNumberRegistryEntry;

public class TIFFRegistryEntry extends MagicNumberRegistryEntry
{
    static final byte[] sig1;
    static final byte[] sig2;
    static MagicNumber[] magicNumbers;
    static final String[] exts;
    static final String[] mimeTypes;
    
    public TIFFRegistryEntry() {
        super("TIFF", TIFFRegistryEntry.exts, TIFFRegistryEntry.mimeTypes, TIFFRegistryEntry.magicNumbers);
    }
    
    public Filter handleStream(final InputStream inputStream, final ParsedURL parsedURL, final boolean b) {
        final DeferRable deferRable = new DeferRable();
        String s;
        Object[] array;
        if (parsedURL != null) {
            s = "url.format.unreadable";
            array = new Object[] { "TIFF", parsedURL };
        }
        else {
            s = "stream.format.unreadable";
            array = new Object[] { "TIFF" };
        }
        new Thread() {
            public void run() {
                Filter source;
                try {
                    source = new RedRable(new Any2sRGBRed(new TIFFImage(SeekableStream.wrapInputStream(inputStream, true), new TIFFDecodeParam(), 0)));
                }
                catch (IOException ex) {
                    source = ImageTagRegistry.getBrokenLinkImage(TIFFRegistryEntry.this, s, array);
                }
                catch (ThreadDeath threadDeath) {
                    deferRable.setSource(ImageTagRegistry.getBrokenLinkImage(TIFFRegistryEntry.this, s, array));
                    throw threadDeath;
                }
                catch (Throwable t) {
                    source = ImageTagRegistry.getBrokenLinkImage(TIFFRegistryEntry.this, s, array);
                }
                deferRable.setSource(source);
            }
        }.start();
        return deferRable;
    }
    
    static {
        sig1 = new byte[] { 73, 73, 42, 0 };
        sig2 = new byte[] { 77, 77, 0, 42 };
        TIFFRegistryEntry.magicNumbers = new MagicNumber[] { new MagicNumber(0, TIFFRegistryEntry.sig1), new MagicNumber(0, TIFFRegistryEntry.sig2) };
        exts = new String[] { "tiff", "tif" };
        mimeTypes = new String[] { "image/tiff", "image/tif" };
    }
}
