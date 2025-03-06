// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.ext.awt.image.spi;

import java.awt.Graphics2D;
import java.util.Map;
import java.util.HashMap;
import java.awt.image.BufferedImage;
import java.awt.geom.Rectangle2D;
import java.awt.image.ImageObserver;
import java.awt.image.RenderedImage;
import java.awt.Image;
import org.apache.batik.ext.awt.image.renderable.RedRable;
import org.apache.batik.ext.awt.image.GraphicsUtil;
import java.awt.Toolkit;
import org.apache.batik.ext.awt.image.renderable.DeferRable;
import org.apache.batik.ext.awt.image.renderable.Filter;
import java.net.MalformedURLException;
import java.net.URL;
import org.apache.batik.util.ParsedURL;

public class JDKRegistryEntry extends AbstractRegistryEntry implements URLRegistryEntry
{
    public static final float PRIORITY = 1000000.0f;
    
    public JDKRegistryEntry() {
        super("JDK", 1000000.0f, new String[0], new String[] { "image/gif" });
    }
    
    public boolean isCompatibleURL(final ParsedURL parsedURL) {
        try {
            new URL(parsedURL.toString());
        }
        catch (MalformedURLException ex) {
            return false;
        }
        return true;
    }
    
    public Filter handleURL(final ParsedURL parsedURL, final boolean b) {
        URL url;
        try {
            url = new URL(parsedURL.toString());
        }
        catch (MalformedURLException ex) {
            return null;
        }
        final DeferRable deferRable = new DeferRable();
        String s;
        Object[] array;
        if (parsedURL != null) {
            s = "url.format.unreadable";
            array = new Object[] { "JDK", url };
        }
        else {
            s = "stream.format.unreadable";
            array = new Object[] { "JDK" };
        }
        new Thread() {
            public void run() {
                Filter brokenLinkImage = null;
                try {
                    final Image image = Toolkit.getDefaultToolkit().createImage(url);
                    if (image != null) {
                        final RenderedImage loadImage = JDKRegistryEntry.this.loadImage(image, deferRable);
                        if (loadImage != null) {
                            brokenLinkImage = new RedRable(GraphicsUtil.wrap(loadImage));
                        }
                    }
                }
                catch (ThreadDeath threadDeath) {
                    deferRable.setSource(ImageTagRegistry.getBrokenLinkImage(JDKRegistryEntry.this, s, array));
                    throw threadDeath;
                }
                catch (Throwable t) {}
                if (brokenLinkImage == null) {
                    brokenLinkImage = ImageTagRegistry.getBrokenLinkImage(JDKRegistryEntry.this, s, array);
                }
                deferRable.setSource(brokenLinkImage);
            }
        }.start();
        return deferRable;
    }
    
    public RenderedImage loadImage(final Image image, final DeferRable deferRable) {
        if (image instanceof RenderedImage) {
            return (RenderedImage)image;
        }
        final MyImgObs myImgObs = new MyImgObs();
        Toolkit.getDefaultToolkit().prepareImage(image, -1, -1, myImgObs);
        myImgObs.waitTilWidthHeightDone();
        if (myImgObs.imageError) {
            return null;
        }
        final int width = myImgObs.width;
        final int height = myImgObs.height;
        deferRable.setBounds(new Rectangle2D.Double(0.0, 0.0, width, height));
        final BufferedImage bufferedImage = new BufferedImage(width, height, 2);
        final Graphics2D graphics = bufferedImage.createGraphics();
        myImgObs.waitTilImageDone();
        if (myImgObs.imageError) {
            return null;
        }
        deferRable.setProperties(new HashMap());
        graphics.drawImage(image, 0, 0, null);
        graphics.dispose();
        return bufferedImage;
    }
    
    public static class MyImgObs implements ImageObserver
    {
        boolean widthDone;
        boolean heightDone;
        boolean imageDone;
        int width;
        int height;
        boolean imageError;
        int IMG_BITS;
        
        public MyImgObs() {
            this.widthDone = false;
            this.heightDone = false;
            this.imageDone = false;
            this.width = -1;
            this.height = -1;
            this.imageError = false;
            this.IMG_BITS = 224;
        }
        
        public void clear() {
            this.width = -1;
            this.height = -1;
            this.widthDone = false;
            this.heightDone = false;
            this.imageDone = false;
        }
        
        public boolean imageUpdate(final Image image, final int n, final int n2, final int n3, final int n4, final int n5) {
            synchronized (this) {
                boolean b = false;
                if ((n & 0x1) != 0x0) {
                    this.width = n4;
                }
                if ((n & 0x2) != 0x0) {
                    this.height = n5;
                }
                if ((n & 0x20) != 0x0) {
                    this.width = n4;
                    this.height = n5;
                }
                if ((n & this.IMG_BITS) != 0x0) {
                    if (!this.widthDone || !this.heightDone || !this.imageDone) {
                        this.widthDone = true;
                        this.heightDone = true;
                        this.imageDone = true;
                        b = true;
                    }
                    if ((n & 0x40) != 0x0) {
                        this.imageError = true;
                    }
                }
                if (!this.widthDone && this.width != -1) {
                    b = true;
                    this.widthDone = true;
                }
                if (!this.heightDone && this.height != -1) {
                    b = true;
                    this.heightDone = true;
                }
                if (b) {
                    this.notifyAll();
                }
            }
            return true;
        }
        
        public synchronized void waitTilWidthHeightDone() {
            while (true) {
                if (this.widthDone) {
                    if (this.heightDone) {
                        break;
                    }
                }
                try {
                    this.wait();
                }
                catch (InterruptedException ex) {}
            }
        }
        
        public synchronized void waitTilWidthDone() {
            while (!this.widthDone) {
                try {
                    this.wait();
                }
                catch (InterruptedException ex) {}
            }
        }
        
        public synchronized void waitTilHeightDone() {
            while (!this.heightDone) {
                try {
                    this.wait();
                }
                catch (InterruptedException ex) {}
            }
        }
        
        public synchronized void waitTilImageDone() {
            while (!this.imageDone) {
                try {
                    this.wait();
                }
                catch (InterruptedException ex) {}
            }
        }
    }
}
