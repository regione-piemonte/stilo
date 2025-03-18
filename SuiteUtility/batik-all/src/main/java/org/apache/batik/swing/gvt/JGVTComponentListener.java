/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.swing.gvt;

import java.awt.event.ComponentEvent;

public interface JGVTComponentListener
{
    public static final int COMPONENT_TRANSFORM_CHANGED = 1337;
    
    void componentTransformChanged(final ComponentEvent p0);
}
