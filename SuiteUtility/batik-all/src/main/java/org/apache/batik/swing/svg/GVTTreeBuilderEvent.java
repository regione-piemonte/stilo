/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.swing.svg;

import org.apache.batik.gvt.GraphicsNode;
import java.util.EventObject;

public class GVTTreeBuilderEvent extends EventObject
{
    protected GraphicsNode gvtRoot;
    
    public GVTTreeBuilderEvent(final Object source, final GraphicsNode gvtRoot) {
        super(source);
        this.gvtRoot = gvtRoot;
    }
    
    public GraphicsNode getGVTRoot() {
        return this.gvtRoot;
    }
}
