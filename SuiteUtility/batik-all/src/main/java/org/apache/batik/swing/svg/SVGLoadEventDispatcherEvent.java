// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.swing.svg;

import org.apache.batik.gvt.GraphicsNode;
import java.util.EventObject;

public class SVGLoadEventDispatcherEvent extends EventObject
{
    protected GraphicsNode gvtRoot;
    
    public SVGLoadEventDispatcherEvent(final Object source, final GraphicsNode gvtRoot) {
        super(source);
        this.gvtRoot = gvtRoot;
    }
    
    public GraphicsNode getGVTRoot() {
        return this.gvtRoot;
    }
}
