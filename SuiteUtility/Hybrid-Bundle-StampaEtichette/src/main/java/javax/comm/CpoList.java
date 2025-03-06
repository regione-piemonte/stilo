// 
// Decompiled by Procyon v0.5.36
// 

package javax.comm;

class CpoList
{
    CpoListEntry listHead;
    
    synchronized void add(final CommPortOwnershipListener commPortOwnershipListener) {
        for (CpoListEntry cpoListEntry = this.listHead; cpoListEntry != null; cpoListEntry = cpoListEntry.next) {
            if (cpoListEntry.listener == commPortOwnershipListener) {
                return;
            }
        }
        final CpoListEntry listHead = new CpoListEntry(commPortOwnershipListener);
        listHead.next = this.listHead;
        this.listHead = listHead;
    }
    
    synchronized void remove(final CommPortOwnershipListener commPortOwnershipListener) {
        CpoListEntry cpoListEntry = null;
        for (CpoListEntry cpoListEntry2 = this.listHead; cpoListEntry2 != null; cpoListEntry2 = cpoListEntry2.next) {
            if (cpoListEntry2.listener == commPortOwnershipListener) {
                if (cpoListEntry != null) {
                    cpoListEntry.next = cpoListEntry2.next;
                }
                else {
                    this.listHead = cpoListEntry2.next;
                }
                cpoListEntry2.listener = null;
                cpoListEntry2.next = null;
                return;
            }
            cpoListEntry = cpoListEntry2;
        }
    }
    
    synchronized CpoList clonelist() {
        CpoListEntry cpoListEntry = null;
        for (CpoListEntry cpoListEntry2 = this.listHead; cpoListEntry2 != null; cpoListEntry2 = cpoListEntry2.next) {
            final CpoListEntry cpoListEntry3 = new CpoListEntry(cpoListEntry2.listener);
            cpoListEntry3.next = cpoListEntry;
            cpoListEntry = cpoListEntry3;
        }
        final CpoList list = new CpoList();
        list.listHead = cpoListEntry;
        return list;
    }
    
    synchronized boolean isEmpty() {
        return this.listHead == null;
    }
    
    synchronized void fireOwnershipEvent(final int n) {
        for (CpoListEntry cpoListEntry = this.listHead; cpoListEntry != null; cpoListEntry = cpoListEntry.next) {
            cpoListEntry.listener.ownershipChange(n);
        }
    }
    
    synchronized void dump() {
        for (CpoListEntry cpoListEntry = this.listHead; cpoListEntry != null; cpoListEntry = cpoListEntry.next) {
            System.err.println("    CpoListEntry - " + cpoListEntry.listener.toString());
        }
    }
}
