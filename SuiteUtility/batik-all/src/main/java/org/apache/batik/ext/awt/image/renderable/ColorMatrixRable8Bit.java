// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.ext.awt.image.renderable;

import org.apache.batik.ext.awt.image.rendered.ColorMatrixRed;
import java.awt.image.RenderedImage;
import java.awt.image.renderable.RenderContext;
import java.util.Map;

public class ColorMatrixRable8Bit extends AbstractColorInterpolationRable implements ColorMatrixRable
{
    private static float[][] MATRIX_LUMINANCE_TO_ALPHA;
    private int type;
    private float[][] matrix;
    
    public void setSource(final Filter filter) {
        this.init(filter, null);
    }
    
    public Filter getSource() {
        return this.getSources().get(0);
    }
    
    public int getType() {
        return this.type;
    }
    
    public float[][] getMatrix() {
        return this.matrix;
    }
    
    private ColorMatrixRable8Bit() {
    }
    
    public static ColorMatrixRable buildMatrix(final float[][] array) {
        if (array == null) {
            throw new IllegalArgumentException();
        }
        if (array.length != 4) {
            throw new IllegalArgumentException();
        }
        final float[][] matrix = new float[4][];
        for (int i = 0; i < 4; ++i) {
            final float[] array2 = array[i];
            if (array2 == null) {
                throw new IllegalArgumentException();
            }
            if (array2.length != 5) {
                throw new IllegalArgumentException();
            }
            matrix[i] = new float[5];
            for (int j = 0; j < 5; ++j) {
                matrix[i][j] = array2[j];
            }
        }
        final ColorMatrixRable8Bit colorMatrixRable8Bit = new ColorMatrixRable8Bit();
        colorMatrixRable8Bit.type = 0;
        colorMatrixRable8Bit.matrix = matrix;
        return colorMatrixRable8Bit;
    }
    
    public static ColorMatrixRable buildSaturate(final float n) {
        final ColorMatrixRable8Bit colorMatrixRable8Bit = new ColorMatrixRable8Bit();
        colorMatrixRable8Bit.type = 1;
        colorMatrixRable8Bit.matrix = new float[][] { { 0.213f + 0.787f * n, 0.715f - 0.715f * n, 0.072f - 0.072f * n, 0.0f, 0.0f }, { 0.213f - 0.213f * n, 0.715f + 0.285f * n, 0.072f - 0.072f * n, 0.0f, 0.0f }, { 0.213f - 0.213f * n, 0.715f - 0.715f * n, 0.072f + 0.928f * n, 0.0f, 0.0f }, { 0.0f, 0.0f, 0.0f, 1.0f, 0.0f } };
        return colorMatrixRable8Bit;
    }
    
    public static ColorMatrixRable buildHueRotate(final float n) {
        final ColorMatrixRable8Bit colorMatrixRable8Bit = new ColorMatrixRable8Bit();
        colorMatrixRable8Bit.type = 2;
        final float n2 = (float)Math.cos(n);
        final float n3 = (float)Math.sin(n);
        colorMatrixRable8Bit.matrix = new float[][] { { 0.213f + n2 * 0.787f - n3 * 0.213f, 0.715f - n2 * 0.715f - n3 * 0.715f, 0.072f - n2 * 0.072f + n3 * 0.928f, 0.0f, 0.0f }, { 0.213f - n2 * 0.212f + n3 * 0.143f, 0.715f + n2 * 0.285f + n3 * 0.14f, 0.072f - n2 * 0.072f - n3 * 0.283f, 0.0f, 0.0f }, { 0.213f - n2 * 0.213f - n3 * 0.787f, 0.715f - n2 * 0.715f + n3 * 0.715f, 0.072f + n2 * 0.928f + n3 * 0.072f, 0.0f, 0.0f }, { 0.0f, 0.0f, 0.0f, 1.0f, 0.0f } };
        return colorMatrixRable8Bit;
    }
    
    public static ColorMatrixRable buildLuminanceToAlpha() {
        final ColorMatrixRable8Bit colorMatrixRable8Bit = new ColorMatrixRable8Bit();
        colorMatrixRable8Bit.type = 3;
        colorMatrixRable8Bit.matrix = ColorMatrixRable8Bit.MATRIX_LUMINANCE_TO_ALPHA;
        return colorMatrixRable8Bit;
    }
    
    public RenderedImage createRendering(final RenderContext renderContext) {
        final RenderedImage rendering = this.getSource().createRendering(renderContext);
        if (rendering == null) {
            return null;
        }
        return new ColorMatrixRed(this.convertSourceCS(rendering), this.matrix);
    }
    
    static {
        ColorMatrixRable8Bit.MATRIX_LUMINANCE_TO_ALPHA = new float[][] { { 0.0f, 0.0f, 0.0f, 0.0f, 0.0f }, { 0.0f, 0.0f, 0.0f, 0.0f, 0.0f }, { 0.0f, 0.0f, 0.0f, 0.0f, 0.0f }, { 0.2125f, 0.7154f, 0.0721f, 0.0f, 0.0f } };
    }
}
