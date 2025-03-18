/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.gvt.event;

import java.util.EventListener;

public interface GraphicsNodeChangeListener extends EventListener
{
    void changeStarted(final GraphicsNodeChangeEvent p0);
    
    void changeCompleted(final GraphicsNodeChangeEvent p0);
}
