// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.ext.awt.image.rendered;

import java.awt.image.WritableRaster;
import java.awt.Graphics2D;
import org.apache.batik.ext.awt.image.GraphicsUtil;
import java.util.Hashtable;
import java.awt.image.ColorModel;
import java.awt.image.SampleModel;
import java.awt.image.Raster;
import java.awt.Point;
import java.awt.image.MultiPixelPackedSampleModel;
import java.awt.image.ImageObserver;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.IndexColorModel;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.List;
import java.awt.image.BufferedImage;

public class IndexImage
{
    static byte[][] computeRGB(final int n, final Cube[] array) {
        final byte[] array2 = new byte[n];
        final byte[] array3 = new byte[n];
        final byte[] array4 = new byte[n];
        byte[] averageColorRGB = new byte[3];
        for (int i = 0; i < n; ++i) {
            averageColorRGB = array[i].averageColorRGB(averageColorRGB);
            array2[i] = averageColorRGB[0];
            array3[i] = averageColorRGB[1];
            array4[i] = averageColorRGB[2];
        }
        return new byte[][] { array2, array3, array4 };
    }
    
    static void logRGB(final byte[] array, final byte[] array2, final byte[] array3) {
        final StringBuffer obj = new StringBuffer(100);
        final int length = array.length;
        for (int i = 0; i < length; ++i) {
            obj.append("(" + (array[i] + 128) + ',' + (array2[i] + 128) + ',' + (array3[i] + 128) + "),");
        }
        System.out.println("RGB:" + length + (Object)obj);
    }
    
    static List[] createColorList(final BufferedImage bufferedImage) {
        final int width = bufferedImage.getWidth();
        final int height = bufferedImage.getHeight();
        final ArrayList[] array = new ArrayList[4096];
        for (int i = 0; i < width; ++i) {
        Label_0182:
            for (int j = 0; j < height; ++j) {
                final int n = bufferedImage.getRGB(i, j) & 0xFFFFFF;
                final int n2 = (n & 0xF00000) >>> 12 | (n & 0xF000) >>> 8 | (n & 0xF0) >>> 4;
                final ArrayList list = array[n2];
                if (list == null) {
                    final ArrayList<Counter> list2 = new ArrayList<Counter>();
                    list2.add(new Counter(n));
                    array[n2] = list2;
                }
                else {
                    final Iterator<Counter> iterator = (Iterator<Counter>)list.iterator();
                    while (iterator.hasNext()) {
                        if (iterator.next().add(n)) {
                            continue Label_0182;
                        }
                    }
                    list.add(new Counter(n));
                }
            }
        }
        return array;
    }
    
    static Counter[][] convertColorList(final List[] array) {
        final Counter[] array2 = new Counter[0];
        final Counter[][] array3 = new Counter[4096][];
        for (int i = 0; i < array.length; ++i) {
            final List list = array[i];
            if (list == null) {
                array3[i] = array2;
            }
            else {
                array3[i] = list.toArray(new Counter[list.size()]);
                array[i] = null;
            }
        }
        return array3;
    }
    
    public static BufferedImage getIndexedImage(final BufferedImage bufferedImage, final int n) {
        final int width = bufferedImage.getWidth();
        final int height = bufferedImage.getHeight();
        final Counter[][] convertColorList = convertColorList(createColorList(bufferedImage));
        int i = 1;
        int n2 = 0;
        final Cube[] array = new Cube[n];
        array[0] = new Cube(convertColorList, width * height);
        while (i < n) {
            while (array[n2].isDone() && ++n2 != i) {}
            if (n2 == i) {
                break;
            }
            Cube cube = array[n2];
            Cube split = cube.split();
            if (split == null) {
                continue;
            }
            if (split.count > cube.count) {
                final Cube cube2 = cube;
                cube = split;
                split = cube2;
            }
            int n3 = n2;
            for (int count = cube.count, n4 = n2 + 1; n4 < i && array[n4].count >= count; ++n4) {
                array[n3++] = array[n4];
            }
            array[n3++] = cube;
            for (int count2 = split.count; n3 < i && array[n3].count >= count2; ++n3) {}
            for (int j = i; j > n3; --j) {
                array[j] = array[j - 1];
            }
            array[n3++] = split;
            ++i;
        }
        final byte[][] computeRGB = computeRGB(i, array);
        final BufferedImage bufferedImage2 = new BufferedImage(width, height, 13, new IndexColorModel(8, i, computeRGB[0], computeRGB[1], computeRGB[2]));
        final Graphics2D graphics = bufferedImage2.createGraphics();
        graphics.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE);
        graphics.drawImage(bufferedImage, 0, 0, null);
        graphics.dispose();
        int n5;
        for (n5 = 1; n5 <= 8 && 1 << n5 < i; ++n5) {}
        if (n5 > 4) {
            return bufferedImage2;
        }
        if (n5 == 3) {
            n5 = 4;
        }
        final IndexColorModel cm = new IndexColorModel(n5, i, computeRGB[0], computeRGB[1], computeRGB[2]);
        final WritableRaster writableRaster = Raster.createWritableRaster(new MultiPixelPackedSampleModel(0, width, height, n5), new Point(0, 0));
        final BufferedImage bufferedImage3 = bufferedImage2;
        final BufferedImage bufferedImage4 = new BufferedImage(cm, writableRaster, bufferedImage3.isAlphaPremultiplied(), null);
        GraphicsUtil.copyData(bufferedImage3, bufferedImage4);
        return bufferedImage4;
    }
    
    private static class Cube
    {
        static final byte[] RGB_BLACK;
        int[] min;
        int[] max;
        boolean done;
        final Counter[][] colors;
        int count;
        static final int RED = 0;
        static final int GRN = 1;
        static final int BLU = 2;
        
        Cube(final Counter[][] colors, final int count) {
            this.min = new int[] { 0, 0, 0 };
            this.max = new int[] { 255, 255, 255 };
            this.done = false;
            this.count = 0;
            this.colors = colors;
            this.count = count;
        }
        
        public boolean isDone() {
            return this.done;
        }
        
        private boolean contains(final int[] array) {
            final int n = array[0];
            final int n2 = array[1];
            final int n3 = array[2];
            return this.min[0] <= n && n <= this.max[0] && this.min[1] <= n2 && n2 <= this.max[1] && this.min[2] <= n3 && n3 <= this.max[2];
        }
        
        Cube split() {
            final int n = this.max[0] - this.min[0] + 1;
            final int n2 = this.max[1] - this.min[1] + 1;
            final int n3 = this.max[2] - this.min[2] + 1;
            int n4;
            int n5;
            int n6;
            if (n >= n2) {
                if (n >= n3) {
                    n4 = 0;
                    n5 = 1;
                    n6 = 2;
                }
                else {
                    n4 = 2;
                    n5 = 0;
                    n6 = 1;
                }
            }
            else if (n2 >= n3) {
                n4 = 1;
                n5 = 0;
                n6 = 2;
            }
            else {
                n4 = 2;
                n5 = 1;
                n6 = 0;
            }
            final Cube splitChannel = this.splitChannel(n4, n5, n6);
            if (splitChannel != null) {
                return splitChannel;
            }
            final Cube splitChannel2 = this.splitChannel(n5, n4, n6);
            if (splitChannel2 != null) {
                return splitChannel2;
            }
            final Cube splitChannel3 = this.splitChannel(n6, n4, n5);
            if (splitChannel3 != null) {
                return splitChannel3;
            }
            this.done = true;
            return null;
        }
        
        private void normalize(final int n, final int[] array) {
            if (this.count == 0) {
                return;
            }
            final int n2 = this.min[n];
            final int n3 = this.max[n];
            int n4 = -1;
            int n5 = -1;
            for (int i = n2; i <= n3; ++i) {
                if (array[i] != 0) {
                    n4 = i;
                    break;
                }
            }
            for (int j = n3; j >= n2; --j) {
                if (array[j] != 0) {
                    n5 = j;
                    break;
                }
            }
            final boolean b = n4 != -1 && n2 != n4;
            final boolean b2 = n5 != -1 && n3 != n5;
            if (b) {
                this.min[n] = n4;
            }
            if (b2) {
                this.max[n] = n5;
            }
        }
        
        Cube splitChannel(final int n, final int n2, final int n3) {
            if (this.min[n] == this.max[n]) {
                return null;
            }
            if (this.count == 0) {
                return null;
            }
            final int n4 = this.count / 2;
            final int[] computeCounts = this.computeCounts(n, n2, n3);
            int n5 = 0;
            int n6 = -1;
            int n7 = this.min[n];
            int n8 = this.max[n];
            for (int i = this.min[n]; i <= this.max[n]; ++i) {
                final int n9 = computeCounts[i];
                if (n9 == 0) {
                    if (n5 == 0 && i < this.max[n]) {
                        this.min[n] = i + 1;
                    }
                }
                else if (n5 + n9 < n4) {
                    n6 = i;
                    n5 += n9;
                }
                else if (n4 - n5 <= n5 + n9 - n4) {
                    if (n6 != -1) {
                        n7 = n6;
                        n8 = i;
                        break;
                    }
                    if (n9 == this.count) {
                        this.max[n] = i;
                        return null;
                    }
                    n7 = i;
                    n8 = i + 1;
                    n5 += n9;
                    break;
                }
                else {
                    if (i != this.max[n]) {
                        n5 += n9;
                        n7 = i;
                        n8 = i + 1;
                        break;
                    }
                    if (n9 == this.count) {
                        return null;
                    }
                    n7 = n6;
                    n8 = i;
                    break;
                }
            }
            final Cube cube = new Cube(this.colors, n5);
            this.count -= n5;
            cube.min[n] = this.min[n];
            cube.max[n] = n7;
            this.min[n] = n8;
            cube.min[n2] = this.min[n2];
            cube.max[n2] = this.max[n2];
            cube.min[n3] = this.min[n3];
            cube.max[n3] = this.max[n3];
            this.normalize(n, computeCounts);
            cube.normalize(n, computeCounts);
            return cube;
        }
        
        private int[] computeCounts(final int n, final int n2, final int n3) {
            final int n4 = (2 - n) * 4;
            final int n5 = (2 - n2) * 4;
            final int n6 = (2 - n3) * 4;
            final int n7 = this.count / 2;
            final int[] array = new int[256];
            int n8 = 0;
            final int n9 = this.min[0];
            final int n10 = this.min[1];
            final int n11 = this.min[2];
            final int n12 = this.max[0];
            final int n13 = this.max[1];
            final int n14 = this.max[2];
            final int[] array2 = { n9 >> 4, n10 >> 4, n11 >> 4 };
            final int[] array3 = { n12 >> 4, n13 >> 4, n14 >> 4 };
            int[] rgb = { 0, 0, 0 };
            for (int i = array2[n]; i <= array3[n]; ++i) {
                final int n15 = i << n4;
                for (int j = array2[n2]; j <= array3[n2]; ++j) {
                    final int n16 = n15 | j << n5;
                    for (int k = array2[n3]; k <= array3[n3]; ++k) {
                        final Counter[] array4 = this.colors[n16 | k << n6];
                        for (int l = 0; l < array4.length; ++l) {
                            final Counter counter = array4[l];
                            rgb = counter.getRgb(rgb);
                            if (this.contains(rgb)) {
                                final int[] array5 = array;
                                final int n17 = rgb[n];
                                array5[n17] += counter.count;
                                n8 += counter.count;
                            }
                        }
                    }
                }
            }
            return array;
        }
        
        public String toString() {
            return "Cube: [" + this.min[0] + '-' + this.max[0] + "] [" + this.min[1] + '-' + this.max[1] + "] [" + this.min[2] + '-' + this.max[2] + "] n:" + this.count;
        }
        
        public int averageColor() {
            if (this.count == 0) {
                return 0;
            }
            final byte[] averageColorRGB = this.averageColorRGB(null);
            return (averageColorRGB[0] << 16 & 0xFF0000) | (averageColorRGB[1] << 8 & 0xFF00) | (averageColorRGB[2] & 0xFF);
        }
        
        public byte[] averageColorRGB(final byte[] array) {
            if (this.count == 0) {
                return Cube.RGB_BLACK;
            }
            float n = 0.0f;
            float n2 = 0.0f;
            float n3 = 0.0f;
            final int n4 = this.min[0];
            final int n5 = this.min[1];
            final int n6 = this.min[2];
            final int n7 = this.max[0];
            final int n8 = this.max[1];
            final int n9 = this.max[2];
            final int[] array2 = { n4 >> 4, n5 >> 4, n6 >> 4 };
            final int[] array3 = { n7 >> 4, n8 >> 4, n9 >> 4 };
            int[] rgb = new int[3];
            for (int i = array2[0]; i <= array3[0]; ++i) {
                final int n10 = i << 8;
                for (int j = array2[1]; j <= array3[1]; ++j) {
                    final int n11 = n10 | j << 4;
                    for (int k = array2[2]; k <= array3[2]; ++k) {
                        final Counter[] array4 = this.colors[n11 | k];
                        for (int l = 0; l < array4.length; ++l) {
                            final Counter counter = array4[l];
                            rgb = counter.getRgb(rgb);
                            if (this.contains(rgb)) {
                                final float n12 = counter.count / (float)this.count;
                                n += rgb[0] * n12;
                                n2 += rgb[1] * n12;
                                n3 += rgb[2] * n12;
                            }
                        }
                    }
                }
            }
            final byte[] array5 = (array == null) ? new byte[3] : array;
            array5[0] = (byte)(n + 0.5f);
            array5[1] = (byte)(n2 + 0.5f);
            array5[2] = (byte)(n3 + 0.5f);
            return array5;
        }
        
        static {
            RGB_BLACK = new byte[] { 0, 0, 0 };
        }
    }
    
    private static class Counter
    {
        final int val;
        int count;
        
        Counter(final int val) {
            this.count = 1;
            this.val = val;
        }
        
        boolean add(final int n) {
            if (this.val != n) {
                return false;
            }
            ++this.count;
            return true;
        }
        
        int[] getRgb(final int[] array) {
            array[0] = (this.val & 0xFF0000) >> 16;
            array[1] = (this.val & 0xFF00) >> 8;
            array[2] = (this.val & 0xFF);
            return array;
        }
    }
}
