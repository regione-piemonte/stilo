// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.ext.awt.image.rendered;

import org.apache.batik.ext.awt.image.GraphicsUtil;
import java.awt.image.DataBufferInt;
import java.awt.image.SinglePixelPackedSampleModel;
import java.awt.image.SampleModel;
import java.awt.image.WritableRaster;
import java.util.Map;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import org.apache.batik.ext.awt.image.PadMode;

public class PadRed extends AbstractRed
{
    static final boolean DEBUG = false;
    PadMode padMode;
    RenderingHints hints;
    
    public PadRed(final CachableRed cachableRed, final Rectangle rectangle, final PadMode padMode, final RenderingHints hints) {
        super(cachableRed, rectangle, cachableRed.getColorModel(), fixSampleModel(cachableRed, rectangle), rectangle.x, rectangle.y, null);
        this.padMode = padMode;
        this.hints = hints;
    }
    
    public WritableRaster copyData(final WritableRaster writableRaster) {
        final CachableRed cachableRed = this.getSources().get(0);
        final Rectangle bounds = cachableRed.getBounds();
        final Rectangle bounds2 = writableRaster.getBounds();
        if (bounds2.intersects(bounds)) {
            final Rectangle intersection = bounds2.intersection(bounds);
            cachableRed.copyData(writableRaster.createWritableChild(intersection.x, intersection.y, intersection.width, intersection.height, intersection.x, intersection.y, null));
        }
        if (this.padMode == PadMode.ZERO_PAD) {
            this.handleZero(writableRaster);
        }
        else if (this.padMode == PadMode.REPLICATE) {
            this.handleReplicate(writableRaster);
        }
        else if (this.padMode == PadMode.WRAP) {
            this.handleWrap(writableRaster);
        }
        return writableRaster;
    }
    
    protected void handleZero(final WritableRaster writableRaster) {
        final Rectangle bounds = this.getSources().get(0).getBounds();
        final Rectangle bounds2 = writableRaster.getBounds();
        final ZeroRecter zeroRecter = ZeroRecter.getZeroRecter(writableRaster);
        final Rectangle rectangle = new Rectangle(bounds2.x, bounds2.y, bounds2.width, bounds2.height);
        final Rectangle rectangle2 = new Rectangle(bounds2.x, bounds2.y, bounds2.width, bounds2.height);
        if (rectangle.x < bounds.x) {
            int width = bounds.x - rectangle.x;
            if (width > rectangle.width) {
                width = rectangle.width;
            }
            rectangle2.width = width;
            zeroRecter.zeroRect(rectangle2);
            final Rectangle rectangle3 = rectangle;
            rectangle3.x += width;
            final Rectangle rectangle4 = rectangle;
            rectangle4.width -= width;
        }
        if (rectangle.y < bounds.y) {
            int height = bounds.y - rectangle.y;
            if (height > rectangle.height) {
                height = rectangle.height;
            }
            rectangle2.x = rectangle.x;
            rectangle2.y = rectangle.y;
            rectangle2.width = rectangle.width;
            rectangle2.height = height;
            zeroRecter.zeroRect(rectangle2);
            final Rectangle rectangle5 = rectangle;
            rectangle5.y += height;
            final Rectangle rectangle6 = rectangle;
            rectangle6.height -= height;
        }
        if (rectangle.y + rectangle.height > bounds.y + bounds.height) {
            int height2 = rectangle.y + rectangle.height - (bounds.y + bounds.height);
            if (height2 > rectangle.height) {
                height2 = rectangle.height;
            }
            final int y = rectangle.y + rectangle.height - height2;
            rectangle2.x = rectangle.x;
            rectangle2.y = y;
            rectangle2.width = rectangle.width;
            rectangle2.height = height2;
            zeroRecter.zeroRect(rectangle2);
            final Rectangle rectangle7 = rectangle;
            rectangle7.height -= height2;
        }
        if (rectangle.x + rectangle.width > bounds.x + bounds.width) {
            int width2 = rectangle.x + rectangle.width - (bounds.x + bounds.width);
            if (width2 > rectangle.width) {
                width2 = rectangle.width;
            }
            rectangle2.x = rectangle.x + rectangle.width - width2;
            rectangle2.y = rectangle.y;
            rectangle2.width = width2;
            rectangle2.height = rectangle.height;
            zeroRecter.zeroRect(rectangle2);
            final Rectangle rectangle8 = rectangle;
            rectangle8.width -= width2;
        }
    }
    
    protected void handleReplicate(final WritableRaster writableRaster) {
        final CachableRed cachableRed = this.getSources().get(0);
        final Rectangle bounds = cachableRed.getBounds();
        final Rectangle bounds2 = writableRaster.getBounds();
        final int x = bounds2.x;
        final int y = bounds2.y;
        final int width = bounds2.width;
        final int height = bounds2.height;
        final int n = (bounds.x > x) ? bounds.x : x;
        final int n2 = (bounds.x + bounds.width - 1 < x + width - 1) ? (bounds.x + bounds.width - 1) : (x + width - 1);
        final int n3 = (bounds.y > y) ? bounds.y : y;
        final int n4 = (bounds.y + bounds.height - 1 < y + height - 1) ? (bounds.y + bounds.height - 1) : (y + height - 1);
        int x2 = n;
        int width2 = n2 - n + 1;
        int y2 = n3;
        int height2 = n4 - n3 + 1;
        if (width2 < 0) {
            x2 = 0;
            width2 = 0;
        }
        if (height2 < 0) {
            y2 = 0;
            height2 = 0;
        }
        final Rectangle rectangle = new Rectangle(x2, y2, width2, height2);
        if (y < bounds.y) {
            int width3 = rectangle.width;
            int childMinX = rectangle.x;
            int x3 = rectangle.x;
            int i = y;
            if (x + width - 1 <= bounds.x) {
                width3 = 1;
                childMinX = bounds.x;
                x3 = x + width - 1;
            }
            else if (x >= bounds.x + bounds.width) {
                width3 = 1;
                childMinX = bounds.x + bounds.width - 1;
                x3 = x;
            }
            cachableRed.copyData(writableRaster.createWritableChild(x3, i, width3, 1, childMinX, bounds.y, null));
            ++i;
            int y3 = bounds.y;
            if (y + height < y3) {
                y3 = y + height;
            }
            if (i < y3) {
                final int[] pixels = writableRaster.getPixels(x3, i - 1, width3, 1, (int[])null);
                while (i < bounds.y) {
                    writableRaster.setPixels(x3, i, width3, 1, pixels);
                    ++i;
                }
            }
        }
        if (y + height > bounds.y + bounds.height) {
            int width4 = rectangle.width;
            int childMinX2 = rectangle.x;
            final int childMinY = bounds.y + bounds.height - 1;
            int x4 = rectangle.x;
            int j = bounds.y + bounds.height;
            if (j < y) {
                j = y;
            }
            if (x + width <= bounds.x) {
                width4 = 1;
                childMinX2 = bounds.x;
                x4 = x + width - 1;
            }
            else if (x >= bounds.x + bounds.width) {
                width4 = 1;
                childMinX2 = bounds.x + bounds.width - 1;
                x4 = x;
            }
            cachableRed.copyData(writableRaster.createWritableChild(x4, j, width4, 1, childMinX2, childMinY, null));
            ++j;
            final int n5 = y + height;
            if (j < n5) {
                final int[] pixels2 = writableRaster.getPixels(x4, j - 1, width4, 1, (int[])null);
                while (j < n5) {
                    writableRaster.setPixels(x4, j, width4, 1, pixels2);
                    ++j;
                }
            }
        }
        if (x < bounds.x) {
            int x5 = bounds.x;
            if (x + width <= bounds.x) {
                x5 = x + width - 1;
            }
            int k = x;
            final int[] pixels3 = writableRaster.getPixels(x5, y, 1, height, (int[])null);
            while (k < x5) {
                writableRaster.setPixels(k, y, 1, height, pixels3);
                ++k;
            }
        }
        if (x + width > bounds.x + bounds.width) {
            int x6 = bounds.x + bounds.width - 1;
            if (x >= bounds.x + bounds.width) {
                x6 = x;
            }
            int l = x6 + 1;
            final int n6 = x + width - 1;
            final int[] pixels4 = writableRaster.getPixels(x6, y, 1, height, (int[])null);
            while (l < n6) {
                writableRaster.setPixels(l, y, 1, height, pixels4);
                ++l;
            }
        }
    }
    
    protected void handleWrap(final WritableRaster writableRaster) {
        this.handleZero(writableRaster);
    }
    
    protected static SampleModel fixSampleModel(final CachableRed cachableRed, final Rectangle rectangle) {
        final int defaultTileSize = AbstractTiledRed.getDefaultTileSize();
        final SampleModel sampleModel = cachableRed.getSampleModel();
        int n = sampleModel.getWidth();
        if (n < defaultTileSize) {
            n = defaultTileSize;
        }
        if (n > rectangle.width) {
            n = rectangle.width;
        }
        int n2 = sampleModel.getHeight();
        if (n2 < defaultTileSize) {
            n2 = defaultTileSize;
        }
        if (n2 > rectangle.height) {
            n2 = rectangle.height;
        }
        return sampleModel.createCompatibleSampleModel(n, n2);
    }
    
    protected static class ZeroRecter_INT_PACK extends ZeroRecter
    {
        final int base;
        final int scanStride;
        final int[] pixels;
        final int[] zeros;
        final int x0;
        final int y0;
        
        public ZeroRecter_INT_PACK(final WritableRaster writableRaster) {
            super(writableRaster);
            final SinglePixelPackedSampleModel singlePixelPackedSampleModel = (SinglePixelPackedSampleModel)writableRaster.getSampleModel();
            this.scanStride = singlePixelPackedSampleModel.getScanlineStride();
            final DataBufferInt dataBufferInt = (DataBufferInt)writableRaster.getDataBuffer();
            this.x0 = writableRaster.getMinY();
            this.y0 = writableRaster.getMinX();
            this.base = dataBufferInt.getOffset() + singlePixelPackedSampleModel.getOffset(this.x0 - writableRaster.getSampleModelTranslateX(), this.y0 - writableRaster.getSampleModelTranslateY());
            this.pixels = dataBufferInt.getBankData()[0];
            if (writableRaster.getWidth() > 10) {
                this.zeros = new int[writableRaster.getWidth()];
            }
            else {
                this.zeros = null;
            }
        }
        
        public void zeroRect(final Rectangle rectangle) {
            final int n = this.base + (rectangle.x - this.x0) + (rectangle.y - this.y0) * this.scanStride;
            if (rectangle.width > 10) {
                for (int i = 0; i < rectangle.height; ++i) {
                    System.arraycopy(this.zeros, 0, this.pixels, n + i * this.scanStride, rectangle.width);
                }
            }
            else {
                int j = n;
                int n2 = j + rectangle.width;
                final int n3 = this.scanStride - rectangle.width;
                for (int k = 0; k < rectangle.height; ++k) {
                    while (j < n2) {
                        this.pixels[j++] = 0;
                    }
                    j += n3;
                    n2 += this.scanStride;
                }
            }
        }
    }
    
    protected static class ZeroRecter
    {
        WritableRaster wr;
        int bands;
        static int[] zeros;
        
        public ZeroRecter(final WritableRaster wr) {
            this.wr = wr;
            this.bands = wr.getSampleModel().getNumBands();
        }
        
        public void zeroRect(final Rectangle rectangle) {
            synchronized (this) {
                if (ZeroRecter.zeros == null || ZeroRecter.zeros.length < rectangle.width * this.bands) {
                    ZeroRecter.zeros = new int[rectangle.width * this.bands];
                }
            }
            for (int i = 0; i < rectangle.height; ++i) {
                this.wr.setPixels(rectangle.x, rectangle.y + i, rectangle.width, 1, ZeroRecter.zeros);
            }
        }
        
        public static ZeroRecter getZeroRecter(final WritableRaster writableRaster) {
            if (GraphicsUtil.is_INT_PACK_Data(writableRaster.getSampleModel(), false)) {
                return new ZeroRecter_INT_PACK(writableRaster);
            }
            return new ZeroRecter(writableRaster);
        }
        
        public static void zeroRect(final WritableRaster writableRaster) {
            getZeroRecter(writableRaster).zeroRect(writableRaster.getBounds());
        }
        
        static {
            ZeroRecter.zeros = null;
        }
    }
}
