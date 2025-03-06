// 
// Decompiled by Procyon v0.5.36
// 

package javax.comm;

class OwnershipEventThread extends Thread
{
    CommPortIdentifier portId;
    
    OwnershipEventThread(final CommPortIdentifier portId) {
        this.portId = portId;
    }
    
    public void run() {
        while (!this.portId.cpoList.isEmpty()) {
            this.portId.ownershipThreadWaiter();
        }
        this.portId.oeThread = null;
    }
}
