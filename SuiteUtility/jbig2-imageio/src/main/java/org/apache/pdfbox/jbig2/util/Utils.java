// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.pdfbox.jbig2.util;

import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;

public class Utils
{
    private static final int BIG_ENOUGH_INT = 16384;
    private static final double BIG_ENOUGH_FLOOR = 16384.0;
    private static final double BIG_ENOUGH_ROUND = 16384.5;
    
    public static Rectangle enlargeRectToGrid(final Rectangle2D rectangle2D) {
        final int floor = floor(rectangle2D.getMinX());
        final int floor2 = floor(rectangle2D.getMinY());
        return new Rectangle(floor, floor2, ceil(rectangle2D.getMaxX()) - floor, ceil(rectangle2D.getMaxY()) - floor2);
    }
    
    public static Rectangle2D dilateRect(final Rectangle2D rectangle2D, final double n, final double n2) {
        return new Rectangle2D.Double(rectangle2D.getX() - n, rectangle2D.getY() - n2, rectangle2D.getWidth() + 2.0 * n, rectangle2D.getHeight() + 2.0 * n2);
    }
    
    public static double clamp(final double a, final double b, final double a2) {
        return Math.min(a2, Math.max(a, b));
    }
    
    public static int floor(final double n) {
        return (int)(n + 16384.0) - 16384;
    }
    
    public static int round(final double n) {
        return (int)(n + 16384.5) - 16384;
    }
    
    public static int ceil(final double n) {
        return 16384 - (int)(16384.0 - n);
    }
}
