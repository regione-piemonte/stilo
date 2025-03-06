// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.gvt;

import org.apache.batik.gvt.event.GraphicsNodeChangeListener;
import java.util.LinkedList;
import java.util.List;

public class RootGraphicsNode extends CompositeGraphicsNode
{
    List treeGraphicsNodeChangeListeners;
    
    public RootGraphicsNode() {
        this.treeGraphicsNodeChangeListeners = null;
    }
    
    public RootGraphicsNode getRoot() {
        return this;
    }
    
    public List getTreeGraphicsNodeChangeListeners() {
        if (this.treeGraphicsNodeChangeListeners == null) {
            this.treeGraphicsNodeChangeListeners = new LinkedList();
        }
        return this.treeGraphicsNodeChangeListeners;
    }
    
    public void addTreeGraphicsNodeChangeListener(final GraphicsNodeChangeListener graphicsNodeChangeListener) {
        this.getTreeGraphicsNodeChangeListeners().add(graphicsNodeChangeListener);
    }
    
    public void removeTreeGraphicsNodeChangeListener(final GraphicsNodeChangeListener graphicsNodeChangeListener) {
        this.getTreeGraphicsNodeChangeListeners().remove(graphicsNodeChangeListener);
    }
}
