// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.pdfbox.jbig2.image;

import java.awt.image.WritableRaster;
import org.apache.pdfbox.jbig2.Bitmap;
import org.apache.pdfbox.jbig2.util.Utils;
import java.awt.Rectangle;

class Resizer
{
    private static final double EPSILON = 1.0E-7;
    private int weightBits;
    private int weightOne;
    private int[] bitsPerChannel;
    private static final int[] NO_SHIFT;
    private int[] finalShift;
    static final boolean debug = false;
    private final boolean coerce = true;
    private final Order order;
    private final boolean trimZeros = true;
    private final Mapping mappingX;
    private final Mapping mappingY;
    
    private static boolean isInteger(final double n) {
        return Math.abs(n - Math.floor(n + 0.5)) < 1.0E-7;
    }
    
    public Resizer(final double n) {
        this(n, n);
    }
    
    public Resizer(final double n, final double n2) {
        this.weightBits = 14;
        this.weightOne = 1 << this.weightBits;
        this.bitsPerChannel = new int[] { 8, 8, 8 };
        this.finalShift = new int[] { 2 * this.weightBits - this.bitsPerChannel[0], 2 * this.weightBits - this.bitsPerChannel[1], 2 * this.weightBits - this.bitsPerChannel[2] };
        this.order = Order.AUTO;
        this.mappingX = new Mapping(n);
        this.mappingY = new Mapping(n2);
    }
    
    private Weighttab[] createXWeights(final Rectangle rectangle, final Rectangle rectangle2, final ParameterizedFilter parameterizedFilter) {
        final int x = rectangle.x;
        final int n = rectangle.x + rectangle.width;
        final int x2 = rectangle2.x;
        final int n2 = rectangle2.x + rectangle2.width;
        final Weighttab[] array = new Weighttab[rectangle2.width];
        for (int i = x2; i < n2; ++i) {
            array[i - x2] = new Weighttab(parameterizedFilter, this.weightOne, this.mappingX.mapPixelCenter(i), x, n - 1, true);
        }
        return array;
    }
    
    private ParameterizedFilter simplifyFilter(final ParameterizedFilter parameterizedFilter, final double n, final double n2) {
        if (parameterizedFilter.support <= 0.5 || (parameterizedFilter.filter.cardinal && isInteger(1.0 / parameterizedFilter.scale) && isInteger(1.0 / (n * parameterizedFilter.scale)) && isInteger((n2 / n - 0.5) / parameterizedFilter.scale))) {
            return new ParameterizedFilter(new Filter.Point(), 1.0, 0.5, 1);
        }
        return parameterizedFilter;
    }
    
    private void resizeXfirst(final Object o, final Rectangle rectangle, final Object o2, final Rectangle rectangle2, final ParameterizedFilter parameterizedFilter, final ParameterizedFilter parameterizedFilter2) {
        final Scanline scanline = createScanline(o, o2, rectangle.width);
        final Scanline scanline2 = createScanline(o, o2, rectangle2.width);
        final Weighttab[] xWeights = this.createXWeights(rectangle, rectangle2, parameterizedFilter);
        final int n = parameterizedFilter2.width + 2;
        final Scanline[] array = new Scanline[n];
        for (int i = 0; i < n; ++i) {
            array[i] = createScanline(o, o2, rectangle2.width);
            array[i].y = -1;
        }
        final int y = rectangle.y;
        final int n2 = rectangle.y + rectangle.height;
        final int y2 = rectangle2.y;
        final int n3 = rectangle2.y + rectangle2.height;
        int j = -1;
        for (int k = y2; k < n3; ++k) {
            final Weighttab weighttab = new Weighttab(parameterizedFilter2, this.weightOne, this.mappingY.mapPixelCenter(k), y, n2 - 1, true);
            scanline2.clear();
            for (int l = weighttab.i0; l <= weighttab.i1; ++l) {
                final Scanline scanline3 = array[l % n];
                if (scanline3.y != l) {
                    scanline3.y = l;
                    if (y + l <= j) {
                        throw new AssertionError((Object)("Backtracking from line " + j + " to " + (y + l)));
                    }
                    scanline.fetch(rectangle.x, y + l);
                    j = y + l;
                    scanline.filter(Resizer.NO_SHIFT, this.bitsPerChannel, xWeights, scanline3);
                }
                scanline3.accumulate(weighttab.weights[l - weighttab.i0], scanline2);
            }
            scanline2.shift(this.finalShift);
            scanline2.store(rectangle2.x, k);
        }
    }
    
    private void resizeYfirst(final Object o, final Rectangle rectangle, final Object o2, final Rectangle rectangle2, final ParameterizedFilter parameterizedFilter, final ParameterizedFilter parameterizedFilter2) {
        final Scanline scanline = createScanline(o, o2, rectangle2.width);
        final Scanline scanline2 = createScanline(o, o2, rectangle.width);
        final Weighttab[] xWeights = this.createXWeights(rectangle, rectangle2, parameterizedFilter);
        final int n = parameterizedFilter2.width + 2;
        final Scanline[] array = new Scanline[n];
        for (int i = 0; i < n; ++i) {
            array[i] = createScanline(o, o2, rectangle.width);
            array[i].y = -1;
        }
        final int y = rectangle.y;
        final int n2 = rectangle.y + rectangle.height;
        final int y2 = rectangle2.y;
        final int n3 = rectangle2.y + rectangle2.height;
        int j = -1;
        for (int k = y2; k < n3; ++k) {
            final Weighttab weighttab = new Weighttab(parameterizedFilter2, this.weightOne, this.mappingY.mapPixelCenter(k), y, n2 - 1, true);
            scanline2.clear();
            for (int l = weighttab.i0; l <= weighttab.i1; ++l) {
                final Scanline scanline3 = array[l % n];
                if (scanline3.y != l) {
                    scanline3.y = l;
                    if (y + l <= j) {
                        throw new AssertionError((Object)("Backtracking from line " + j + " to " + (y + l)));
                    }
                    scanline3.fetch(rectangle.x, y + l);
                    j = y + l;
                }
                scanline3.accumulate(weighttab.weights[l - weighttab.i0], scanline2);
            }
            scanline2.filter(this.bitsPerChannel, this.finalShift, xWeights, scanline);
            scanline.store(rectangle2.x, k);
        }
    }
    
    public void resize(final Object o, final Rectangle rectangle, final Object o2, Rectangle intersection, final Filter filter, final Filter filter2) {
        final ParameterizedFilter parameterizedFilter = new ParameterizedFilter(filter, this.mappingX.scale);
        final ParameterizedFilter parameterizedFilter2 = new ParameterizedFilter(filter2, this.mappingY.scale);
        final Rectangle r = new Rectangle();
        r.setFrameFromDiagonal(Utils.ceil(this.mappingX.srcToDst(rectangle.x - parameterizedFilter.support) + 1.0E-7), Utils.ceil(this.mappingY.srcToDst(rectangle.y - parameterizedFilter2.support) + 1.0E-7), Utils.floor(this.mappingX.srcToDst(rectangle.x + rectangle.width + parameterizedFilter.support) - 1.0E-7), Utils.floor(this.mappingY.srcToDst(rectangle.y + rectangle.height + parameterizedFilter2.support) - 1.0E-7));
        if (intersection.x < r.x || intersection.getMaxX() > r.getMaxX() || intersection.y < r.y || intersection.getMaxY() > r.getMaxY()) {
            intersection = intersection.intersection(r);
        }
        if (rectangle.isEmpty() || intersection.width <= 0 || intersection.height <= 0) {
            return;
        }
        final ParameterizedFilter parameterizedFilter3 = parameterizedFilter;
        final double scale = this.mappingX.scale;
        this.mappingX.getClass();
        final ParameterizedFilter simplifyFilter = this.simplifyFilter(parameterizedFilter3, scale, 0.5);
        final ParameterizedFilter parameterizedFilter4 = parameterizedFilter2;
        final double scale2 = this.mappingY.scale;
        this.mappingY.getClass();
        final ParameterizedFilter simplifyFilter2 = this.simplifyFilter(parameterizedFilter4, scale2, 0.5);
        if ((this.order != Order.AUTO) ? (this.order == Order.XY) : (intersection.width * (rectangle.height * simplifyFilter.width + intersection.height * simplifyFilter2.width) < intersection.height * (intersection.width * simplifyFilter.width + rectangle.width * simplifyFilter2.width))) {
            this.resizeXfirst(o, rectangle, o2, intersection, simplifyFilter, simplifyFilter2);
        }
        else {
            this.resizeYfirst(o, rectangle, o2, intersection, simplifyFilter, simplifyFilter2);
        }
    }
    
    private static Scanline createScanline(final Object o, final Object o2, final int n) {
        if (o == null) {
            throw new IllegalArgumentException("src must not be null");
        }
        if (!(o instanceof Bitmap)) {
            throw new IllegalArgumentException("src must be from type " + Bitmap.class.getName());
        }
        if (o2 == null) {
            throw new IllegalArgumentException("dst must not be null");
        }
        if (!(o2 instanceof WritableRaster)) {
            throw new IllegalArgumentException("dst must be from type " + WritableRaster.class.getName());
        }
        return new BitmapScanline((Bitmap)o, (WritableRaster)o2, n);
    }
    
    static {
        NO_SHIFT = new int[16];
    }
    
    static final class Mapping
    {
        final double scale;
        final double offset = 0.5;
        private final double a0;
        private final double b0;
        
        Mapping(final double a0, final double n, final double b0, final double n2) {
            this.a0 = a0;
            this.b0 = b0;
            this.scale = n2 / n;
            if (this.scale <= 0.0) {
                throw new IllegalArgumentException("Negative scales are not allowed");
            }
        }
        
        Mapping(final double scale) {
            this.scale = scale;
            final double n = 0.0;
            this.b0 = n;
            this.a0 = n;
        }
        
        double mapPixelCenter(final int n) {
            return (n + 0.5 - this.b0) / this.scale + this.a0;
        }
        
        double dstToSrc(final double n) {
            return (n - this.b0) / this.scale + this.a0;
        }
        
        double srcToDst(final double n) {
            return (n - this.a0) * this.scale + this.b0;
        }
    }
    
    private enum Order
    {
        AUTO, 
        XY, 
        YX;
    }
}
