// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.pdfbox.jbig2.util;

import java.util.Iterator;
import java.util.List;
import org.apache.pdfbox.jbig2.image.Bitmaps;
import org.apache.pdfbox.jbig2.TestImage;
import org.apache.pdfbox.jbig2.Bitmap;

class DictionaryViewer
{
    public static void show(final Bitmap bitmap) {
        new TestImage(Bitmaps.asBufferedImage(bitmap));
    }
    
    public static void show(final List<Bitmap> list) {
        int n = 0;
        int height = 0;
        for (final Bitmap bitmap : list) {
            n += bitmap.getWidth();
            if (bitmap.getHeight() > height) {
                height = bitmap.getHeight();
            }
        }
        final Bitmap bitmap2 = new Bitmap(n, height);
        int n2 = 0;
        for (final Bitmap bitmap3 : list) {
            Bitmaps.blit(bitmap3, bitmap2, n2, 0, CombinationOperator.REPLACE);
            n2 += bitmap3.getWidth();
        }
        new TestImage(Bitmaps.asBufferedImage(bitmap2));
    }
}
