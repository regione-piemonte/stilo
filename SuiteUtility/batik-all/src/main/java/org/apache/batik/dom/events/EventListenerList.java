// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.dom.events;

import org.w3c.dom.events.EventListener;
import org.apache.batik.dom.util.HashTable;
import org.apache.batik.dom.util.IntTable;

public class EventListenerList
{
    protected int n;
    protected Entry head;
    protected IntTable counts;
    protected Entry[] listeners;
    protected HashTable listenersNS;
    
    public EventListenerList() {
        this.counts = new IntTable();
        this.listenersNS = new HashTable();
    }
    
    public void addListener(final String s, final Object o, final EventListener eventListener) {
        for (Entry entry = this.head; entry != null; entry = entry.next) {
            if (((s != null && s.equals(entry.namespaceURI)) || (s == null && entry.namespaceURI == null)) && entry.listener == eventListener) {
                return;
            }
        }
        this.head = new Entry(eventListener, s, o, this.head);
        this.counts.inc(s);
        ++this.n;
        this.listeners = null;
        this.listenersNS.remove(s);
    }
    
    public void removeListener(final String s, final EventListener eventListener) {
        if (this.head == null) {
            return;
        }
        if (this.head != null && ((s != null && s.equals(this.head.namespaceURI)) || (s == null && this.head.namespaceURI == null)) && eventListener == this.head.listener) {
            this.head = this.head.next;
        }
        else {
            Entry head = this.head;
            Entry entry;
            for (entry = this.head.next; entry != null; entry = entry.next) {
                if (((s != null && s.equals(entry.namespaceURI)) || (s == null && entry.namespaceURI == null)) && entry.listener == eventListener) {
                    head.next = entry.next;
                    break;
                }
                head = entry;
            }
            if (entry == null) {
                return;
            }
        }
        this.counts.dec(s);
        --this.n;
        this.listeners = null;
        this.listenersNS.remove(s);
    }
    
    public Entry[] getEventListeners() {
        if (this.listeners != null) {
            return this.listeners;
        }
        this.listeners = new Entry[this.n];
        int n = 0;
        for (Entry entry = this.head; entry != null; entry = entry.next) {
            this.listeners[n++] = entry;
        }
        return this.listeners;
    }
    
    public Entry[] getEventListeners(final String s) {
        if (s == null) {
            return this.getEventListeners();
        }
        final Entry[] array = (Entry[])this.listenersNS.get(s);
        if (array != null) {
            return array;
        }
        final int value = this.counts.get(s);
        if (value == 0) {
            return null;
        }
        final Entry[] array2 = new Entry[value];
        this.listenersNS.put(s, array2);
        int i = 0;
        Entry entry = this.head;
        while (i < value) {
            if (s.equals(entry.namespaceURI)) {
                array2[i++] = entry;
            }
            entry = entry.next;
        }
        return array2;
    }
    
    public boolean hasEventListener(final String s) {
        if (s == null) {
            return this.n != 0;
        }
        return this.counts.get(s) != 0;
    }
    
    public int size() {
        return this.n;
    }
    
    public class Entry
    {
        protected EventListener listener;
        protected String namespaceURI;
        protected Object group;
        protected boolean mark;
        protected Entry next;
        
        public Entry(final EventListener listener, final String namespaceURI, final Object group, final Entry next) {
            this.listener = listener;
            this.namespaceURI = namespaceURI;
            this.group = group;
            this.next = next;
        }
        
        public EventListener getListener() {
            return this.listener;
        }
        
        public Object getGroup() {
            return this.group;
        }
        
        public String getNamespaceURI() {
            return this.namespaceURI;
        }
    }
}
