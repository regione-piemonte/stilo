// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.dom.svg12;

import org.apache.batik.dom.xbl.ShadowTreeEvent;
import org.w3c.dom.events.MutationEvent;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.apache.batik.dom.xbl.NodeXBL;
import org.w3c.dom.events.EventException;
import org.w3c.dom.Element;
import java.util.Collection;
import java.util.HashSet;
import org.apache.batik.dom.events.AbstractEvent;
import org.w3c.dom.events.Event;
import org.apache.batik.dom.events.NodeEventTarget;
import org.apache.batik.dom.events.EventListenerList;
import org.w3c.dom.events.EventListener;
import org.apache.batik.dom.AbstractNode;
import org.apache.batik.dom.util.HashTable;
import org.apache.batik.dom.events.EventSupport;

public class XBLEventSupport extends EventSupport
{
    protected HashTable capturingImplementationListeners;
    protected HashTable bubblingImplementationListeners;
    protected static HashTable eventTypeAliases;
    
    public XBLEventSupport(final AbstractNode abstractNode) {
        super(abstractNode);
    }
    
    public void addEventListenerNS(final String s, final String s2, final EventListener eventListener, final boolean b, final Object o) {
        super.addEventListenerNS(s, s2, eventListener, b, o);
        if (s == null || s.equals("http://www.w3.org/2001/xml-events")) {
            final String s3 = (String)XBLEventSupport.eventTypeAliases.get(s2);
            if (s3 != null) {
                super.addEventListenerNS(s, s3, eventListener, b, o);
            }
        }
    }
    
    public void removeEventListenerNS(final String s, final String s2, final EventListener eventListener, final boolean b) {
        super.removeEventListenerNS(s, s2, eventListener, b);
        if (s == null || s.equals("http://www.w3.org/2001/xml-events")) {
            final String s3 = (String)XBLEventSupport.eventTypeAliases.get(s2);
            if (s3 != null) {
                super.removeEventListenerNS(s, s3, eventListener, b);
            }
        }
    }
    
    public void addImplementationEventListenerNS(final String s, final String s2, final EventListener eventListener, final boolean b) {
        HashTable hashTable;
        if (b) {
            if (this.capturingImplementationListeners == null) {
                this.capturingImplementationListeners = new HashTable();
            }
            hashTable = this.capturingImplementationListeners;
        }
        else {
            if (this.bubblingImplementationListeners == null) {
                this.bubblingImplementationListeners = new HashTable();
            }
            hashTable = this.bubblingImplementationListeners;
        }
        EventListenerList list = (EventListenerList)hashTable.get(s2);
        if (list == null) {
            list = new EventListenerList();
            hashTable.put(s2, list);
        }
        list.addListener(s, null, eventListener);
    }
    
    public void removeImplementationEventListenerNS(final String s, final String s2, final EventListener eventListener, final boolean b) {
        final HashTable hashTable = b ? this.capturingImplementationListeners : this.bubblingImplementationListeners;
        if (hashTable == null) {
            return;
        }
        final EventListenerList list = (EventListenerList)hashTable.get(s2);
        if (list == null) {
            return;
        }
        list.removeListener(s, eventListener);
        if (list.size() == 0) {
            hashTable.remove(s2);
        }
    }
    
    public void moveEventListeners(final EventSupport eventSupport) {
        super.moveEventListeners(eventSupport);
        final XBLEventSupport xblEventSupport = (XBLEventSupport)eventSupport;
        xblEventSupport.capturingImplementationListeners = this.capturingImplementationListeners;
        xblEventSupport.bubblingImplementationListeners = this.bubblingImplementationListeners;
        this.capturingImplementationListeners = null;
        this.bubblingImplementationListeners = null;
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
        this.setTarget(abstractEvent, nodeEventTarget);
        this.stopPropagation(abstractEvent, false);
        this.stopImmediatePropagation(abstractEvent, false);
        this.preventDefault(abstractEvent, false);
        final NodeEventTarget[] ancestors = this.getAncestors(nodeEventTarget);
        final int bubbleLimit = abstractEvent.getBubbleLimit();
        int i = 0;
        if (this.isSingleScopeEvent(abstractEvent)) {
            final Element xblBoundElement = ((AbstractNode)nodeEventTarget).getXblBoundElement();
            if (xblBoundElement != null) {
                for (i = ancestors.length; i > 0; --i) {
                    if (((AbstractNode)ancestors[i - 1]).getXblBoundElement() != xblBoundElement) {
                        break;
                    }
                }
            }
        }
        else if (bubbleLimit != 0) {
            i = ancestors.length - bubbleLimit + 1;
            if (i < 0) {
                i = 0;
            }
        }
        final AbstractEvent[] retargettedEvents = this.getRetargettedEvents(nodeEventTarget, ancestors, abstractEvent);
        boolean b = false;
        final HashSet set = new HashSet();
        final HashSet c = new HashSet();
        for (int j = 0; j < i; ++j) {
            final NodeEventTarget nodeEventTarget2 = ancestors[j];
            this.setCurrentTarget(retargettedEvents[j], nodeEventTarget2);
            this.setEventPhase(retargettedEvents[j], (short)1);
            this.fireImplementationEventListeners(nodeEventTarget2, retargettedEvents[j], true);
        }
        for (int k = i; k < ancestors.length; ++k) {
            final NodeEventTarget nodeEventTarget3 = ancestors[k];
            this.setCurrentTarget(retargettedEvents[k], nodeEventTarget3);
            this.setEventPhase(retargettedEvents[k], (short)1);
            this.fireImplementationEventListeners(nodeEventTarget3, retargettedEvents[k], true);
            this.fireEventListeners(nodeEventTarget3, retargettedEvents[k], true, set, c);
            this.fireHandlerGroupEventListeners(nodeEventTarget3, retargettedEvents[k], true, set, c);
            b = (b || retargettedEvents[k].getDefaultPrevented());
            set.addAll(c);
            c.clear();
        }
        this.setEventPhase(abstractEvent, (short)2);
        this.setCurrentTarget(abstractEvent, nodeEventTarget);
        this.fireImplementationEventListeners(nodeEventTarget, abstractEvent, false);
        this.fireEventListeners(nodeEventTarget, abstractEvent, false, set, c);
        this.fireHandlerGroupEventListeners(this.node, abstractEvent, false, set, c);
        set.addAll(c);
        c.clear();
        boolean b2 = b || abstractEvent.getDefaultPrevented();
        if (abstractEvent.getBubbles()) {
            for (int l = ancestors.length - 1; l >= i; --l) {
                final NodeEventTarget nodeEventTarget4 = ancestors[l];
                this.setCurrentTarget(retargettedEvents[l], nodeEventTarget4);
                this.setEventPhase(retargettedEvents[l], (short)3);
                this.fireImplementationEventListeners(nodeEventTarget4, retargettedEvents[l], false);
                this.fireEventListeners(nodeEventTarget4, retargettedEvents[l], false, set, c);
                this.fireHandlerGroupEventListeners(nodeEventTarget4, retargettedEvents[l], false, set, c);
                b2 = (b2 || retargettedEvents[l].getDefaultPrevented());
                set.addAll(c);
                c.clear();
            }
            for (int n = i - 1; n >= 0; --n) {
                final NodeEventTarget nodeEventTarget5 = ancestors[n];
                this.setCurrentTarget(retargettedEvents[n], nodeEventTarget5);
                this.setEventPhase(retargettedEvents[n], (short)3);
                this.fireImplementationEventListeners(nodeEventTarget5, retargettedEvents[n], false);
                b2 = (b2 || retargettedEvents[n].getDefaultPrevented());
            }
        }
        if (!b2) {
            this.runDefaultActions(abstractEvent);
        }
        return b2;
    }
    
    protected void fireHandlerGroupEventListeners(NodeEventTarget nodeEventTarget, final AbstractEvent abstractEvent, final boolean b, final HashSet set, final HashSet set2) {
        final NodeList xblDefinitions = ((NodeXBL)nodeEventTarget).getXblDefinitions();
        for (int i = 0; i < xblDefinitions.getLength(); ++i) {
            Node node;
            for (node = xblDefinitions.item(i).getFirstChild(); node != null && !(node instanceof XBLOMHandlerGroupElement); node = node.getNextSibling()) {}
            if (node != null) {
                nodeEventTarget = (NodeEventTarget)node;
                final String type = abstractEvent.getType();
                final EventSupport eventSupport = nodeEventTarget.getEventSupport();
                if (eventSupport != null) {
                    final EventListenerList eventListeners = eventSupport.getEventListeners(type, b);
                    if (eventListeners == null) {
                        return;
                    }
                    this.fireEventListeners(nodeEventTarget, abstractEvent, eventListeners.getEventListeners(), set, set2);
                }
            }
        }
    }
    
    protected boolean isSingleScopeEvent(final Event event) {
        return event instanceof MutationEvent || event instanceof ShadowTreeEvent;
    }
    
    protected AbstractEvent[] getRetargettedEvents(final NodeEventTarget nodeEventTarget, final NodeEventTarget[] array, final AbstractEvent abstractEvent) {
        final boolean singleScopeEvent = this.isSingleScopeEvent(abstractEvent);
        final AbstractNode abstractNode = (AbstractNode)nodeEventTarget;
        final AbstractEvent[] array2 = new AbstractEvent[array.length];
        if (array.length > 0) {
            int n = array.length - 1;
            final Element xblBoundElement = abstractNode.getXblBoundElement();
            final AbstractNode abstractNode2 = (AbstractNode)array[n];
            if (!singleScopeEvent && abstractNode2.getXblBoundElement() != xblBoundElement) {
                array2[n] = this.retargetEvent(abstractEvent, array[n]);
            }
            else {
                array2[n] = abstractEvent;
            }
            while (--n >= 0) {
                final Element xblBoundElement2 = ((AbstractNode)array[n + 1]).getXblBoundElement();
                final Element xblBoundElement3 = ((AbstractNode)array[n]).getXblBoundElement();
                if (!singleScopeEvent && xblBoundElement3 != xblBoundElement2) {
                    array2[n] = this.retargetEvent(array2[n + 1], array[n]);
                }
                else {
                    array2[n] = array2[n + 1];
                }
            }
        }
        return array2;
    }
    
    protected AbstractEvent retargetEvent(final AbstractEvent abstractEvent, final NodeEventTarget nodeEventTarget) {
        final AbstractEvent cloneEvent = abstractEvent.cloneEvent();
        this.setTarget(cloneEvent, nodeEventTarget);
        return cloneEvent;
    }
    
    public EventListenerList getImplementationEventListeners(final String s, final boolean b) {
        final HashTable hashTable = b ? this.capturingImplementationListeners : this.bubblingImplementationListeners;
        if (hashTable == null) {
            return null;
        }
        return (EventListenerList)hashTable.get(s);
    }
    
    protected void fireImplementationEventListeners(final NodeEventTarget nodeEventTarget, final AbstractEvent abstractEvent, final boolean b) {
        final String type = abstractEvent.getType();
        final XBLEventSupport xblEventSupport = (XBLEventSupport)nodeEventTarget.getEventSupport();
        if (xblEventSupport == null) {
            return;
        }
        final EventListenerList implementationEventListeners = xblEventSupport.getImplementationEventListeners(type, b);
        if (implementationEventListeners == null) {
            return;
        }
        this.fireEventListeners(nodeEventTarget, abstractEvent, implementationEventListeners.getEventListeners(), null, null);
    }
    
    static {
        (XBLEventSupport.eventTypeAliases = new HashTable()).put("SVGLoad", "load");
        XBLEventSupport.eventTypeAliases.put("SVGUnoad", "unload");
        XBLEventSupport.eventTypeAliases.put("SVGAbort", "abort");
        XBLEventSupport.eventTypeAliases.put("SVGError", "error");
        XBLEventSupport.eventTypeAliases.put("SVGResize", "resize");
        XBLEventSupport.eventTypeAliases.put("SVGScroll", "scroll");
        XBLEventSupport.eventTypeAliases.put("SVGZoom", "zoom");
    }
}
