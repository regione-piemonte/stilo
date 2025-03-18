/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.gvt.flow;

import org.apache.batik.gvt.TextPainter;
import org.apache.batik.gvt.TextNode;

public class FlowTextNode extends TextNode
{
    public FlowTextNode() {
        this.textPainter = FlowTextPainter.getInstance();
    }
    
    public void setTextPainter(final TextPainter textPainter) {
        if (textPainter == null) {
            this.textPainter = FlowTextPainter.getInstance();
        }
        else {
            this.textPainter = textPainter;
        }
    }
}
