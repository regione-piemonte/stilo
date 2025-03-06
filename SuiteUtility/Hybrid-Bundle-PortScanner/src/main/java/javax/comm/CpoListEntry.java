// 
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
