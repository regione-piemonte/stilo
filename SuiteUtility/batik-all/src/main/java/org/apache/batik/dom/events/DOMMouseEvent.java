// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.dom.events;

import org.w3c.dom.views.AbstractView;
import java.util.Iterator;
import java.util.HashSet;
import org.w3c.dom.events.EventTarget;
import org.w3c.dom.events.MouseEvent;

public class DOMMouseEvent extends DOMUIEvent implements MouseEvent
{
    private int screenX;
    private int screenY;
    private int clientX;
    private int clientY;
    private short button;
    private EventTarget relatedTarget;
    protected HashSet modifierKeys;
    
    public DOMMouseEvent() {
        this.modifierKeys = new HashSet();
    }
    
    public int getScreenX() {
        return this.screenX;
    }
    
    public int getScreenY() {
        return this.screenY;
    }
    
    public int getClientX() {
        return this.clientX;
    }
    
    public int getClientY() {
        return this.clientY;
    }
    
    public boolean getCtrlKey() {
        return this.modifierKeys.contains("Control");
    }
    
    public boolean getShiftKey() {
        return this.modifierKeys.contains("Shift");
    }
    
    public boolean getAltKey() {
        return this.modifierKeys.contains("Alt");
    }
    
    public boolean getMetaKey() {
        return this.modifierKeys.contains("Meta");
    }
    
    public short getButton() {
        return this.button;
    }
    
    public EventTarget getRelatedTarget() {
        return this.relatedTarget;
    }
    
    public boolean getModifierState(final String o) {
        return this.modifierKeys.contains(o);
    }
    
    public String getModifiersString() {
        if (this.modifierKeys.isEmpty()) {
            return "";
        }
        final StringBuffer sb = new StringBuffer(this.modifierKeys.size() * 8);
        final Iterator<String> iterator = (Iterator<String>)this.modifierKeys.iterator();
        sb.append(iterator.next());
        while (iterator.hasNext()) {
            sb.append(' ');
            sb.append(iterator.next());
        }
        return sb.toString();
    }
    
    public void initMouseEvent(final String s, final boolean b, final boolean b2, final AbstractView abstractView, final int n, final int screenX, final int screenY, final int clientX, final int clientY, final boolean b3, final boolean b4, final boolean b5, final boolean b6, final short button, final EventTarget relatedTarget) {
        this.initUIEvent(s, b, b2, abstractView, n);
        this.screenX = screenX;
        this.screenY = screenY;
        this.clientX = clientX;
        this.clientY = clientY;
        if (b3) {
            this.modifierKeys.add("Control");
        }
        if (b4) {
            this.modifierKeys.add("Alt");
        }
        if (b5) {
            this.modifierKeys.add("Shift");
        }
        if (b6) {
            this.modifierKeys.add("Meta");
        }
        this.button = button;
        this.relatedTarget = relatedTarget;
    }
    
    public void initMouseEventNS(final String s, final String s2, final boolean b, final boolean b2, final AbstractView abstractView, final int n, final int screenX, final int screenY, final int clientX, final int clientY, final short button, final EventTarget relatedTarget, final String s3) {
        this.initUIEventNS(s, s2, b, b2, abstractView, n);
        this.screenX = screenX;
        this.screenY = screenY;
        this.clientX = clientX;
        this.clientY = clientY;
        this.button = button;
        this.relatedTarget = relatedTarget;
        this.modifierKeys.clear();
        final String[] split = this.split(s3);
        for (int i = 0; i < split.length; ++i) {
            this.modifierKeys.add(split[i]);
        }
    }
}
