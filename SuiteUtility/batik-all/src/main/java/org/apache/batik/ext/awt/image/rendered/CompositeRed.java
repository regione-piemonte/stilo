// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.ext.awt.image.rendered;

import java.awt.image.DirectColorModel;
import java.awt.Graphics2D;
import java.util.Hashtable;
import java.awt.image.BufferedImage;
import org.apache.batik.ext.awt.image.GraphicsUtil;
import java.awt.Point;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;
import java.awt.image.SampleModel;
import java.awt.Rectangle;
import java.util.Iterator;
import java.awt.image.ColorModel;
import java.util.Map;
import org.apache.batik.ext.awt.image.PadMode;
import java.util.ArrayList;
import java.awt.RenderingHints;
import org.apache.batik.ext.awt.image.SVGComposite;
import java.util.List;
import java.awt.CompositeContext;
import org.apache.batik.ext.awt.image.CompositeRule;

public class CompositeRed extends AbstractRed
{
    CompositeRule rule;
    CompositeContext[] contexts;
    
    public CompositeRed(List list, final CompositeRule rule) {
        final CachableRed cachableRed = list.get(0);
        final ColorModel fixColorModel = fixColorModel(cachableRed);
        this.rule = rule;
        final SVGComposite svgComposite = new SVGComposite(rule);
        this.contexts = new CompositeContext[list.size()];
        int n = 0;
        final Iterator<CachableRed> iterator = list.iterator();
        Rectangle intersection = null;
        while (iterator.hasNext()) {
            final CachableRed cachableRed2 = iterator.next();
            this.contexts[n++] = svgComposite.createContext(cachableRed2.getColorModel(), fixColorModel, null);
            final Rectangle bounds = cachableRed2.getBounds();
            if (intersection == null) {
                intersection = bounds;
            }
            else {
                switch (rule.getRule()) {
                    case 2: {
                        if (intersection.intersects(bounds)) {
                            intersection = intersection.intersection(bounds);
                            continue;
                        }
                        intersection.width = 0;
                        intersection.height = 0;
                        continue;
                    }
                    case 3: {
                        intersection = bounds;
                        continue;
                    }
                    default: {
                        intersection.add(bounds);
                        continue;
                    }
                }
            }
        }
        if (intersection == null) {
            throw new IllegalArgumentException("Composite Operation Must have some source!");
        }
        if (rule.getRule() == 6) {
            final ArrayList list2 = new ArrayList<CachableRed>(list.size());
            for (CachableRed cachableRed3 : list) {
                final Rectangle bounds2 = cachableRed3.getBounds();
                if (bounds2.x != intersection.x || bounds2.y != intersection.y || bounds2.width != intersection.width || bounds2.height != intersection.height) {
                    cachableRed3 = new PadRed(cachableRed3, intersection, PadMode.ZERO_PAD, null);
                }
                list2.add(cachableRed3);
            }
            list = (List<CachableRed>)list2;
        }
        final SampleModel fixSampleModel = fixSampleModel(cachableRed, fixColorModel, intersection);
        final int defaultTileSize = AbstractTiledRed.getDefaultTileSize();
        this.init(list, intersection, fixColorModel, fixSampleModel, defaultTileSize * (int)Math.floor(intersection.x / defaultTileSize), defaultTileSize * (int)Math.floor(intersection.y / defaultTileSize), null);
    }
    
    public WritableRaster copyData(final WritableRaster writableRaster) {
        this.genRect(writableRaster);
        return writableRaster;
    }
    
    public Raster getTile(final int n, final int n2) {
        final WritableRaster writableRaster = Raster.createWritableRaster(this.sm, new Point(this.tileGridXOff + n * this.tileWidth, this.tileGridYOff + n2 * this.tileHeight));
        this.genRect(writableRaster);
        return writableRaster;
    }
    
    public void emptyRect(final WritableRaster writableRaster) {
        PadRed.ZeroRecter.getZeroRecter(writableRaster).zeroRect(new Rectangle(writableRaster.getMinX(), writableRaster.getMinY(), writableRaster.getWidth(), writableRaster.getHeight()));
    }
    
    public void genRect(final WritableRaster writableRaster) {
        final Rectangle bounds = writableRaster.getBounds();
        int n = 0;
        final Iterator<CachableRed> iterator = (Iterator<CachableRed>)this.srcs.iterator();
        int n2 = 1;
        while (iterator.hasNext()) {
            final CachableRed cachableRed = iterator.next();
            if (n2 != 0) {
                final Rectangle bounds2 = cachableRed.getBounds();
                if (bounds.x < bounds2.x || bounds.y < bounds2.y || bounds.x + bounds.width > bounds2.x + bounds2.width || bounds.y + bounds.height > bounds2.y + bounds2.height) {
                    this.emptyRect(writableRaster);
                }
                cachableRed.copyData(writableRaster);
                if (!cachableRed.getColorModel().isAlphaPremultiplied()) {
                    GraphicsUtil.coerceData(writableRaster, cachableRed.getColorModel(), true);
                }
                n2 = 0;
            }
            else {
                final Rectangle bounds3 = cachableRed.getBounds();
                if (bounds3.intersects(bounds)) {
                    final Rectangle intersection = bounds3.intersection(bounds);
                    final Raster data = cachableRed.getData(intersection);
                    final WritableRaster writableChild = writableRaster.createWritableChild(intersection.x, intersection.y, intersection.width, intersection.height, intersection.x, intersection.y, null);
                    this.contexts[n].compose(data, writableChild, writableChild);
                }
            }
            ++n;
        }
    }
    
    public void genRect_OVER(final WritableRaster writableRaster) {
        final Rectangle bounds = writableRaster.getBounds();
        final ColorModel colorModel = this.getColorModel();
        final Graphics2D graphics = GraphicsUtil.createGraphics(new BufferedImage(colorModel, writableRaster.createWritableTranslatedChild(0, 0), colorModel.isAlphaPremultiplied(), null));
        graphics.translate(-bounds.x, -bounds.y);
        final Iterator<CachableRed> iterator = (Iterator<CachableRed>)this.srcs.iterator();
        int n = 1;
        while (iterator.hasNext()) {
            final CachableRed cachableRed = iterator.next();
            if (n != 0) {
                final Rectangle bounds2 = cachableRed.getBounds();
                if (bounds.x < bounds2.x || bounds.y < bounds2.y || bounds.x + bounds.width > bounds2.x + bounds2.width || bounds.y + bounds.height > bounds2.y + bounds2.height) {
                    this.emptyRect(writableRaster);
                }
                cachableRed.copyData(writableRaster);
                GraphicsUtil.coerceData(writableRaster, cachableRed.getColorModel(), colorModel.isAlphaPremultiplied());
                n = 0;
            }
            else {
                GraphicsUtil.drawImage(graphics, cachableRed);
            }
        }
    }
    
    protected static SampleModel fixSampleModel(final CachableRed cachableRed, final ColorModel colorModel, final Rectangle rectangle) {
        final int defaultTileSize = AbstractTiledRed.getDefaultTileSize();
        final int n = defaultTileSize * (int)Math.floor(rectangle.x / defaultTileSize);
        final int n2 = defaultTileSize * (int)Math.floor(rectangle.y / defaultTileSize);
        final int n3 = rectangle.x + rectangle.width - n;
        final int n4 = rectangle.y + rectangle.height - n2;
        final SampleModel sampleModel = cachableRed.getSampleModel();
        int width = sampleModel.getWidth();
        if (width < defaultTileSize) {
            width = defaultTileSize;
        }
        if (width > n3) {
            width = n3;
        }
        int height = sampleModel.getHeight();
        if (height < defaultTileSize) {
            height = defaultTileSize;
        }
        if (height > n4) {
            height = n4;
        }
        if (width <= 0 || height <= 0) {
            width = 1;
            height = 1;
        }
        return colorModel.createCompatibleSampleModel(width, height);
    }
    
    protected static ColorModel fixColorModel(final CachableRed cachableRed) {
        ColorModel colorModel = cachableRed.getColorModel();
        if (colorModel.hasAlpha()) {
            if (!colorModel.isAlphaPremultiplied()) {
                colorModel = GraphicsUtil.coerceColorModel(colorModel, true);
            }
            return colorModel;
        }
        final int n = cachableRed.getSampleModel().getNumBands() + 1;
        if (n > 4) {
            throw new IllegalArgumentException("CompositeRed can only handle up to three band images");
        }
        final int[] array = new int[4];
        for (int i = 0; i < n - 1; ++i) {
            array[i] = 16711680 >> 8 * i;
        }
        array[3] = 255 << 8 * (n - 1);
        return new DirectColorModel(colorModel.getColorSpace(), 8 * n, array[0], array[1], array[2], array[3], true, 3);
    }
}
