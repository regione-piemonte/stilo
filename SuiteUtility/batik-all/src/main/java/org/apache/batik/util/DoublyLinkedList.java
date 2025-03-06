// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.util;

public class DoublyLinkedList
{
    private Node head;
    private int size;
    
    public DoublyLinkedList() {
        this.head = null;
        this.size = 0;
    }
    
    public synchronized int getSize() {
        return this.size;
    }
    
    public synchronized void empty() {
        while (this.size > 0) {
            this.pop();
        }
    }
    
    public Node getHead() {
        return this.head;
    }
    
    public Node getTail() {
        return this.head.getPrev();
    }
    
    public void touch(final Node head) {
        if (head == null) {
            return;
        }
        head.insertBefore(this.head);
        this.head = head;
    }
    
    public void add(int i, final Node head) {
        if (head == null) {
            return;
        }
        if (i == 0) {
            head.insertBefore(this.head);
            this.head = head;
        }
        else if (i == this.size) {
            head.insertBefore(this.head);
        }
        else {
            Node node = this.head;
            while (i != 0) {
                node = node.getNext();
                --i;
            }
            head.insertBefore(node);
        }
        ++this.size;
    }
    
    public void add(final Node head) {
        if (head == null) {
            return;
        }
        head.insertBefore(this.head);
        this.head = head;
        ++this.size;
    }
    
    public void remove(final Node node) {
        if (node == null) {
            return;
        }
        if (node == this.head) {
            if (this.head.getNext() == this.head) {
                this.head = null;
            }
            else {
                this.head = this.head.getNext();
            }
        }
        node.unlink();
        --this.size;
    }
    
    public Node pop() {
        if (this.head == null) {
            return null;
        }
        final Node head = this.head;
        this.remove(head);
        return head;
    }
    
    public Node unpush() {
        if (this.head == null) {
            return null;
        }
        final Node tail = this.getTail();
        this.remove(tail);
        return tail;
    }
    
    public void push(final Node head) {
        head.insertBefore(this.head);
        if (this.head == null) {
            this.head = head;
        }
        ++this.size;
    }
    
    public void unpop(final Node head) {
        head.insertBefore(this.head);
        this.head = head;
        ++this.size;
    }
    
    public static class Node
    {
        private Node next;
        private Node prev;
        
        public Node() {
            this.next = null;
            this.prev = null;
        }
        
        public final Node getNext() {
            return this.next;
        }
        
        public final Node getPrev() {
            return this.prev;
        }
        
        protected final void setNext(final Node next) {
            this.next = next;
        }
        
        protected final void setPrev(final Node prev) {
            this.prev = prev;
        }
        
        protected final void unlink() {
            if (this.getNext() != null) {
                this.getNext().setPrev(this.getPrev());
            }
            if (this.getPrev() != null) {
                this.getPrev().setNext(this.getNext());
            }
            this.setNext(null);
            this.setPrev(null);
        }
        
        protected final void insertBefore(final Node next) {
            if (this == next) {
                return;
            }
            if (this.getPrev() != null) {
                this.unlink();
            }
            if (next == null) {
                this.setNext(this);
                this.setPrev(this);
            }
            else {
                this.setNext(next);
                this.setPrev(next.getPrev());
                next.setPrev(this);
                if (this.getPrev() != null) {
                    this.getPrev().setNext(this);
                }
            }
        }
    }
}
