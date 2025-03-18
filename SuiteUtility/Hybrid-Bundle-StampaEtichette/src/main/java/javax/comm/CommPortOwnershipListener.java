/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
// Decompiled by Procyon v0.5.36
// 

package javax.comm;

import java.util.EventListener;

public interface CommPortOwnershipListener extends EventListener
{
    public static final int PORT_OWNED = 1;
    public static final int PORT_UNOWNED = 2;
    public static final int PORT_OWNERSHIP_REQUESTED = 3;
    
    void ownershipChange(final int p0);
}
