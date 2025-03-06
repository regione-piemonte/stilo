// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.ext.awt.image.rendered;

import org.apache.batik.util.DoublyLinkedList;

public class LRUCache
{
    private DoublyLinkedList free;
    private DoublyLinkedList used;
    private int maxSize;
    
    public LRUCache(int i) {
        this.free = null;
        this.used = null;
        this.maxSize = 0;
        if (i <= 0) {
            i = 1;
        }
        this.maxSize = i;
        this.free = new DoublyLinkedList();
        this.used = new DoublyLinkedList();
        while (i > 0) {
            this.free.add(new LRUNode());
            --i;
        }
    }
    
    public int getUsed() {
        return this.used.getSize();
    }
    
    public synchronized void setSize(final int maxSize) {
        if (this.maxSize < maxSize) {
            for (int i = this.maxSize; i < maxSize; ++i) {
                this.free.add(new LRUNode());
            }
        }
        else if (this.maxSize > maxSize) {
            for (int j = this.used.getSize(); j > maxSize; --j) {
                final LRUNode lruNode = (LRUNode)this.used.getTail();
                this.used.remove(lruNode);
                lruNode.setObj(null);
            }
        }
        this.maxSize = maxSize;
    }
    
    public synchronized void flush() {
        while (this.used.getSize() > 0) {
            final LRUNode lruNode = (LRUNode)this.used.pop();
            lruNode.setObj(null);
            this.free.add(lruNode);
        }
    }
    
    public synchronized void remove(final LRUObj lruObj) {
        final LRUNode lruGet = lruObj.lruGet();
        if (lruGet == null) {
            return;
        }
        this.used.remove(lruGet);
        lruGet.setObj(null);
        this.free.add(lruGet);
    }
    
    public synchronized void touch(final LRUObj lruObj) {
        final LRUNode lruGet = lruObj.lruGet();
        if (lruGet == null) {
            return;
        }
        this.used.touch(lruGet);
    }
    
    public synchronized void add(final LRUObj lruObj) {
        final LRUNode lruGet = lruObj.lruGet();
        if (lruGet != null) {
            this.used.touch(lruGet);
            return;
        }
        if (this.free.getSize() > 0) {
            final LRUNode lruNode = (LRUNode)this.free.pop();
            lruNode.setObj(lruObj);
            this.used.add(lruNode);
        }
        else {
            final LRUNode lruNode2 = (LRUNode)this.used.getTail();
            lruNode2.setObj(lruObj);
            this.used.touch(lruNode2);
        }
    }
    
    protected synchronized void print() {
        System.out.println("In Use: " + this.used.getSize() + " Free: " + this.free.getSize());
        LRUNode lruNode = (LRUNode)this.used.getHead();
        if (lruNode == null) {
            return;
        }
        do {
            System.out.println(lruNode.getObj());
            lruNode = (LRUNode)lruNode.getNext();
        } while (lruNode != this.used.getHead());
    }
    
    public class LRUNode extends DoublyLinkedList.Node
    {
        private LRUObj obj;
        
        public LRUNode() {
            this.obj = null;
        }
        
        public LRUObj getObj() {
            return this.obj;
        }
        
        protected void setObj(final LRUObj obj) {
            if (this.obj != null) {
                this.obj.lruRemove();
            }
            this.obj = obj;
            if (this.obj != null) {
                this.obj.lruSet(this);
            }
        }
    }
    
    public interface LRUObj
    {
        void lruSet(final LRUNode p0);
        
        LRUNode lruGet();
        
        void lruRemove();
    }
}
