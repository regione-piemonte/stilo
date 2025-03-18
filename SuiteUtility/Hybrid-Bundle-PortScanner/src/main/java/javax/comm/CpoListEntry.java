/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
// Decompiled by Procyon v0.5.36
// 

package javax.comm;

class CpoListEntry
{
    CpoListEntry next;
    CommPortOwnershipListener listener;
    
    CpoListEntry(final CommPortOwnershipListener listener) {
        this.listener = listener;
        this.next = null;
    }
}
