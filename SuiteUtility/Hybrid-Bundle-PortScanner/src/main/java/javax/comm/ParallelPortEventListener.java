/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
// Decompiled by Procyon v0.5.36
// 

package javax.comm;

import java.util.EventListener;

public interface ParallelPortEventListener extends EventListener
{
    void parallelEvent(final ParallelPortEvent p0);
}
