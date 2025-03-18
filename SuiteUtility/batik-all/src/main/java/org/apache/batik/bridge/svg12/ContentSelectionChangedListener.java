/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.bridge.svg12;

import java.util.EventListener;

public interface ContentSelectionChangedListener extends EventListener
{
    void contentSelectionChanged(final ContentSelectionChangedEvent p0);
}
