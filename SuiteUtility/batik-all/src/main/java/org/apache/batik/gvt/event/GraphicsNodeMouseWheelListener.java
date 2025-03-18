/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.gvt.event;

import java.util.EventListener;

public interface GraphicsNodeMouseWheelListener extends EventListener
{
    void mouseWheelMoved(final GraphicsNodeMouseWheelEvent p0);
}
