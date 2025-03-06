// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.gvt.renderer;

import java.awt.font.TextLayout;
import java.awt.Graphics2D;
import org.apache.batik.gvt.TextNode;
import java.awt.geom.Point2D;
import java.text.AttributedCharacterIterator;

public abstract class ConcreteTextPainter extends BasicTextPainter
{
    public void paint(final AttributedCharacterIterator text, final Point2D point2D, final TextNode.Anchor anchor, final Graphics2D g2) {
        final TextLayout textLayout = new TextLayout(text, this.fontRenderContext);
        final float advance = textLayout.getAdvance();
        float n = 0.0f;
        switch (anchor.getType()) {
            case 1: {
                n = -advance / 2.0f;
                break;
            }
            case 2: {
                n = -advance;
                break;
            }
        }
        textLayout.draw(g2, (float)(point2D.getX() + n), (float)point2D.getY());
    }
}
