// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.dom.events;

import org.apache.batik.dom.AbstractDocument;
import java.util.Iterator;
import java.util.List;
import org.w3c.dom.events.EventException;
import java.util.Collection;
import java.util.HashSet;
import org.w3c.dom.events.EventTarget;
import org.w3c.dom.events.Event;
import org.w3c.dom.events.EventListener;
import org.apache.batik.dom.AbstractNode;
import org.apache.batik.dom.util.HashTable;

public class EventSupport
{
    protected HashTable capturingListeners;
    protected HashTable bubblingListeners;
    protected AbstractNode node;
    
    public EventSupport(final AbstractNode node) {
        this.node = node;
    }
    
    public void addEventListener(final String s, final EventListener eventListener, final boolean b) {
        this.addEventListenerNS(null, s, eventListener, b, null);
    }
    
    public void addEventListenerNS(final String s, final String s2, final EventListener eventListener, final boolean b, final Object o) {
        HashTable hashTable;
        if (b) {
            if (this.capturingListeners == null) {
                this.capturingListeners = new HashTable();
            }
            hashTable = this.capturingListeners;
        }
        else {
            if (this.bubblingListeners == null) {
                this.bubblingListeners = new HashTable();
            }
            hashTable = this.bubblingListeners;
        }
        EventListenerList list = (EventListenerList)hashTable.get(s2);
        if (list == null) {
            list = new EventListenerList();
            hashTable.put(s2, list);
        }
        list.addListener(s, o, eventListener);
    }
    
    public void removeEventListener(final String s, final EventListener eventListener, final boolean b) {
        this.removeEventListenerNS(null, s, eventListener, b);
    }
    
    public void removeEventListenerNS(final String s, final String s2, final EventListener eventListener, final boolean b) {
        HashTable hashTable;
        if (b) {
            hashTable = this.capturingListeners;
        }
        else {
            hashTable = this.bubblingListeners;
        }
        if (hashTable == null) {
            return;
        }
        final EventListenerList list = (EventListenerList)hashTable.get(s2);
        if (list != null) {
            list.removeListener(s, eventListener);
            if (list.size() == 0) {
                hashTable.remove(s2);
            }
        }
    }
    
    public void moveEventListeners(final EventSupport eventSupport) {
        eventSupport.capturingListeners = this.capturingListeners;
        eventSupport.bubblingListeners = this.bubblingListeners;
        this.capturingListeners = null;
        this.bubblingListeners = null;
    }
    
    public boolean dispatchEvent(final NodeEventTarget nodeEventTarget, final Event event) throws EventException {
        if (event == null) {
            return false;
        }
        if (!(event instanceof AbstractEvent)) {
            throw this.createEventException((short)9, "unsupported.event", new Object[0]);
        }
        final AbstractEvent abstractEvent = (AbstractEvent)event;
        final String type = abstractEvent.getType();
        if (type == null || type.length() == 0) {
            throw this.createEventException((short)0, "unspecified.event", new Object[0]);
        }
        abstractEvent.setTarget(nodeEventTarget);
        abstractEvent.stopPropagation(false);
        abstractEvent.stopImmediatePropagation(false);
        abstractEvent.preventDefault(false);
        final NodeEventTarget[] ancestors = this.getAncestors(nodeEventTarget);
        abstractEvent.setEventPhase((short)1);
        final HashSet set = new HashSet();
        final HashSet c = new HashSet();
        for (int i = 0; i < ancestors.length; ++i) {
            final NodeEventTarget currentTarget = ancestors[i];
            abstractEvent.setCurrentTarget(currentTarget);
            this.fireEventListeners(currentTarget, abstractEvent, true, set, c);
            set.addAll(c);
            c.clear();
        }
        abstractEvent.setEventPhase((short)2);
        abstractEvent.setCurrentTarget(nodeEventTarget);
        this.fireEventListeners(nodeEventTarget, abstractEvent, false, set, c);
        set.addAll(c);
        c.clear();
        if (abstractEvent.getBubbles()) {
            abstractEvent.setEventPhase((short)3);
            for (int j = ancestors.length - 1; j >= 0; --j) {
                final NodeEventTarget currentTarget2 = ancestors[j];
                abstractEvent.setCurrentTarget(currentTarget2);
                this.fireEventListeners(currentTarget2, abstractEvent, false, set, c);
                set.addAll(c);
                c.clear();
            }
        }
        if (!abstractEvent.getDefaultPrevented()) {
            this.runDefaultActions(abstractEvent);
        }
        return abstractEvent.getDefaultPrevented();
    }
    
    protected void runDefaultActions(final AbstractEvent abstractEvent) {
        final List defaultActions = abstractEvent.getDefaultActions();
        if (defaultActions != null) {
            final Iterator<Runnable> iterator = defaultActions.iterator();
            while (iterator.hasNext()) {
                iterator.next().run();
            }
        }
    }
    
    protected void fireEventListeners(final NodeEventTarget nodeEventTarget, final AbstractEvent abstractEvent, final EventListenerList.Entry[] array, final HashSet set, final HashSet set2) {
        if (array == null) {
            return;
        }
        final String namespaceURI = abstractEvent.getNamespaceURI();
        for (int i = 0; i < array.length; ++i) {
            try {
                final String namespaceURI2 = array[i].getNamespaceURI();
                if (namespaceURI2 == null || namespaceURI == null || namespaceURI2.equals(namespaceURI)) {
                    final Object group = array[i].getGroup();
                    if (set == null || !set.contains(group)) {
                        array[i].getListener().handleEvent(abstractEvent);
                        if (abstractEvent.getStopImmediatePropagation()) {
                            if (set != null) {
                                set.add(group);
                            }
                            abstractEvent.stopImmediatePropagation(false);
                        }
                        else if (abstractEvent.getStopPropagation()) {
                            if (set2 != null) {
                                set2.add(group);
                            }
                            abstractEvent.stopPropagation(false);
                        }
                    }
                }
            }
            catch (ThreadDeath threadDeath) {
                throw threadDeath;
            }
            catch (Throwable t) {
                t.printStackTrace();
            }
        }
    }
    
    protected void fireEventListeners(final NodeEventTarget nodeEventTarget, final AbstractEvent abstractEvent, final boolean b, final HashSet set, final HashSet set2) {
        final String type = abstractEvent.getType();
        final EventSupport eventSupport = nodeEventTarget.getEventSupport();
        if (eventSupport == null) {
            return;
        }
        final EventListenerList eventListeners = eventSupport.getEventListeners(type, b);
        if (eventListeners == null) {
            return;
        }
        this.fireEventListeners(nodeEventTarget, abstractEvent, eventListeners.getEventListeners(), set, set2);
    }
    
    protected NodeEventTarget[] getAncestors(NodeEventTarget nodeEventTarget) {
        nodeEventTarget = nodeEventTarget.getParentNodeEventTarget();
        int n = 0;
        for (NodeEventTarget parentNodeEventTarget = nodeEventTarget; parentNodeEventTarget != null; parentNodeEventTarget = parentNodeEventTarget.getParentNodeEventTarget(), ++n) {}
        final NodeEventTarget[] array = new NodeEventTarget[n];
        for (int i = n - 1; i >= 0; --i, nodeEventTarget = nodeEventTarget.getParentNodeEventTarget()) {
            array[i] = nodeEventTarget;
        }
        return array;
    }
    
    public boolean hasEventListenerNS(final String s, final String s2) {
        if (this.capturingListeners != null) {
            final EventListenerList list = (EventListenerList)this.capturingListeners.get(s2);
            if (list != null && list.hasEventListener(s)) {
                return true;
            }
        }
        if (this.bubblingListeners != null) {
            final EventListenerList list2 = (EventListenerList)this.capturingListeners.get(s2);
            if (list2 != null) {
                return list2.hasEventListener(s);
            }
        }
        return false;
    }
    
    public EventListenerList getEventListeners(final String s, final boolean b) {
        final HashTable hashTable = b ? this.capturingListeners : this.bubblingListeners;
        if (hashTable == null) {
            return null;
        }
        return (EventListenerList)hashTable.get(s);
    }
    
    protected EventException createEventException(final short n, final String s, final Object[] array) {
        try {
            return new EventException(n, ((AbstractDocument)this.node.getOwnerDocument()).formatMessage(s, array));
        }
        catch (Exception ex) {
            return new EventException(n, s);
        }
    }
    
    protected void setTarget(final AbstractEvent abstractEvent, final NodeEventTarget target) {
        abstractEvent.setTarget(target);
    }
    
    protected void stopPropagation(final AbstractEvent abstractEvent, final boolean b) {
        abstractEvent.stopPropagation(b);
    }
    
    protected void stopImmediatePropagation(final AbstractEvent abstractEvent, final boolean b) {
        abstractEvent.stopImmediatePropagation(b);
    }
    
    protected void preventDefault(final AbstractEvent abstractEvent, final boolean b) {
        abstractEvent.preventDefault(b);
    }
    
    protected void setCurrentTarget(final AbstractEvent abstractEvent, final NodeEventTarget currentTarget) {
        abstractEvent.setCurrentTarget(currentTarget);
    }
    
    protected void setEventPhase(final AbstractEvent abstractEvent, final short eventPhase) {
        abstractEvent.setEventPhase(eventPhase);
    }
    
    public static Event getUltimateOriginalEvent(final Event event) {
        AbstractEvent abstractEvent = (AbstractEvent)event;
        while (true) {
            final AbstractEvent abstractEvent2 = (AbstractEvent)abstractEvent.getOriginalEvent();
            if (abstractEvent2 == null) {
                break;
            }
            abstractEvent = abstractEvent2;
        }
        return abstractEvent;
    }
}
