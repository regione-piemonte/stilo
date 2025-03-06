// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.apps.svgbrowser;

import org.w3c.dom.Node;
import org.w3c.dom.Document;
import org.apache.batik.swing.gvt.Overlay;

public interface DOMViewerController
{
    void performUpdate(final Runnable p0);
    
    ElementOverlayManager createSelectionManager();
    
    void removeSelectionOverlay(final Overlay p0);
    
    Document getDocument();
    
    void selectNode(final Node p0);
    
    boolean canEdit();
}
