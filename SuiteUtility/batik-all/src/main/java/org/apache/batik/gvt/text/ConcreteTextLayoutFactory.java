// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.gvt.text;

import java.awt.font.FontRenderContext;
import java.awt.geom.Point2D;
import java.text.AttributedCharacterIterator;

public class ConcreteTextLayoutFactory implements TextLayoutFactory
{
    public TextSpanLayout createTextLayout(final AttributedCharacterIterator attributedCharacterIterator, final int[] array, final Point2D point2D, final FontRenderContext fontRenderContext) {
        return new GlyphLayout(attributedCharacterIterator, array, point2D, fontRenderContext);
    }
}
