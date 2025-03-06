// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.swing.svg;

public interface GVTTreeBuilderListener
{
    void gvtBuildStarted(final GVTTreeBuilderEvent p0);
    
    void gvtBuildCompleted(final GVTTreeBuilderEvent p0);
    
    void gvtBuildCancelled(final GVTTreeBuilderEvent p0);
    
    void gvtBuildFailed(final GVTTreeBuilderEvent p0);
}
