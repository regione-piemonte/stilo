// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.bridge;

import org.apache.batik.dom.svg.IdContainer;
import org.apache.batik.dom.svg.SVGOMUseShadowRoot;
import org.w3c.dom.Element;
import org.apache.batik.dom.svg.SVGOMAnimationElement;
import org.apache.batik.anim.timing.TimedElement;
import org.w3c.dom.events.Event;
import org.w3c.dom.views.AbstractView;
import org.w3c.dom.Node;
import org.w3c.dom.events.DocumentEvent;
import org.apache.batik.dom.events.DOMTimeEvent;
import java.util.Calendar;
import org.w3c.dom.events.EventTarget;

public abstract class AnimationSupport
{
    public static void fireTimeEvent(final EventTarget eventTarget, final String s, final Calendar calendar, final int n) {
        final DOMTimeEvent domTimeEvent = (DOMTimeEvent)((DocumentEvent)((Node)eventTarget).getOwnerDocument()).createEvent("TimeEvent");
        domTimeEvent.initTimeEventNS("http://www.w3.org/2001/xml-events", s, null, n);
        domTimeEvent.setTimestamp(calendar.getTime().getTime());
        eventTarget.dispatchEvent(domTimeEvent);
    }
    
    public static TimedElement getTimedElementById(final String s, final Node node) {
        final Element elementById = getElementById(s, node);
        if (elementById instanceof SVGOMAnimationElement) {
            return ((SVGAnimationElementBridge)((SVGOMAnimationElement)elementById).getSVGContext()).getTimedElement();
        }
        return null;
    }
    
    public static EventTarget getEventTargetById(final String s, final Node node) {
        return (EventTarget)getElementById(s, node);
    }
    
    protected static Element getElementById(final String s, Node node) {
        Node node2 = node.getParentNode();
        while (node2 != null) {
            node = node2;
            if (node instanceof SVGOMUseShadowRoot) {
                node2 = ((SVGOMUseShadowRoot)node).getCSSParentNode();
            }
            else {
                node2 = node.getParentNode();
            }
        }
        if (node instanceof IdContainer) {
            return ((IdContainer)node).getElementById(s);
        }
        return null;
    }
}
