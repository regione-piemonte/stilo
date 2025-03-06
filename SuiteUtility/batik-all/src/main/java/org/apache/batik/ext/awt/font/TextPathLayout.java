// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.ext.awt.font;

import java.awt.geom.Point2D;
import java.awt.geom.AffineTransform;
import org.apache.batik.ext.awt.geom.PathLength;
import java.awt.geom.GeneralPath;
import java.awt.Shape;
import java.awt.font.GlyphVector;

public class TextPathLayout
{
    public static final int ALIGN_START = 0;
    public static final int ALIGN_MIDDLE = 1;
    public static final int ALIGN_END = 2;
    public static final int ADJUST_SPACING = 0;
    public static final int ADJUST_GLYPHS = 1;
    
    public static Shape layoutGlyphVector(final GlyphVector glyphVector, final Shape shape, final int n, final float n2, final float n3, final int n4) {
        final GeneralPath generalPath = new GeneralPath();
        final PathLength pathLength = new PathLength(shape);
        final float lengthOfPath = pathLength.lengthOfPath();
        if (glyphVector == null) {
            return generalPath;
        }
        final float n5 = (float)glyphVector.getVisualBounds().getWidth();
        if (shape == null || glyphVector.getNumGlyphs() == 0 || pathLength.lengthOfPath() == 0.0f || n5 == 0.0f) {
            return generalPath;
        }
        final float n6 = n3 / n5;
        float n7 = n2;
        if (n == 2) {
            n7 += lengthOfPath - n3;
        }
        else if (n == 1) {
            n7 += (lengthOfPath - n3) / 2.0f;
        }
        for (int i = 0; i < glyphVector.getNumGlyphs(); ++i) {
            float advance = glyphVector.getGlyphMetrics(i).getAdvance();
            Shape shape2 = glyphVector.getGlyphOutline(i);
            if (n4 == 1) {
                shape2 = AffineTransform.getScaleInstance(n6, 1.0).createTransformedShape(shape2);
                advance *= n6;
            }
            final float n8 = n7 + (float)shape2.getBounds2D().getWidth() / 2.0f;
            final Point2D pointAtLength = pathLength.pointAtLength(n8);
            if (pointAtLength != null) {
                final float angleAtLength = pathLength.angleAtLength(n8);
                final AffineTransform affineTransform = new AffineTransform();
                affineTransform.translate(pointAtLength.getX(), pointAtLength.getY());
                affineTransform.rotate(angleAtLength);
                affineTransform.translate(advance / -2.0f, 0.0);
                generalPath.append(affineTransform.createTransformedShape(shape2), false);
            }
            if (n4 == 0) {
                n7 += advance * n6;
            }
            else {
                n7 += advance;
            }
        }
        return generalPath;
    }
    
    public static Shape layoutGlyphVector(final GlyphVector glyphVector, final Shape shape, final int n) {
        return layoutGlyphVector(glyphVector, shape, n, 0.0f, (float)glyphVector.getVisualBounds().getWidth(), 0);
    }
    
    public static Shape layoutGlyphVector(final GlyphVector glyphVector, final Shape shape) {
        return layoutGlyphVector(glyphVector, shape, 0);
    }
}
