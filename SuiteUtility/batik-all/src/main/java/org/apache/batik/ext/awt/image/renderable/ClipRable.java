/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.ext.awt.image.renderable;

import java.awt.Shape;

public interface ClipRable extends Filter
{
    void setUseAntialiasedClip(final boolean p0);
    
    boolean getUseAntialiasedClip();
    
    void setSource(final Filter p0);
    
    Filter getSource();
    
    void setClipPath(final Shape p0);
    
    Shape getClipPath();
}
