// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.swing.svg;

public interface SVGDocumentLoaderListener
{
    void documentLoadingStarted(final SVGDocumentLoaderEvent p0);
    
    void documentLoadingCompleted(final SVGDocumentLoaderEvent p0);
    
    void documentLoadingCancelled(final SVGDocumentLoaderEvent p0);
    
    void documentLoadingFailed(final SVGDocumentLoaderEvent p0);
}
