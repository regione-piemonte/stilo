/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.swing.gvt;

public interface GVTTreeRendererListener
{
    void gvtRenderingPrepare(final GVTTreeRendererEvent p0);
    
    void gvtRenderingStarted(final GVTTreeRendererEvent p0);
    
    void gvtRenderingCompleted(final GVTTreeRendererEvent p0);
    
    void gvtRenderingCancelled(final GVTTreeRendererEvent p0);
    
    void gvtRenderingFailed(final GVTTreeRendererEvent p0);
}
