// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.extension.svg;

import org.apache.batik.gvt.TextPainter;
import org.apache.batik.gvt.TextNode;

public class FlowExtTextNode extends TextNode
{
    public FlowExtTextNode() {
        this.textPainter = FlowExtTextPainter.getInstance();
    }
    
    public void setTextPainter(final TextPainter textPainter) {
        if (textPainter == null) {
            this.textPainter = FlowExtTextPainter.getInstance();
        }
        else {
            this.textPainter = textPainter;
        }
    }
}
