// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.extension.svg;

import org.apache.batik.gvt.text.TextSpanLayout;
import java.awt.font.FontRenderContext;
import java.awt.geom.Point2D;
import java.text.AttributedCharacterIterator;
import org.apache.batik.gvt.text.TextLayoutFactory;

public class FlowExtTextLayoutFactory implements TextLayoutFactory
{
    public TextSpanLayout createTextLayout(final AttributedCharacterIterator attributedCharacterIterator, final int[] array, final Point2D point2D, final FontRenderContext fontRenderContext) {
        return new FlowExtGlyphLayout(attributedCharacterIterator, array, point2D, fontRenderContext);
    }
}
