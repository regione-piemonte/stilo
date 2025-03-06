// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.dom.events;

import org.apache.batik.xml.XMLUtilities;
import java.util.ArrayList;
import org.w3c.dom.views.AbstractView;
import org.w3c.dom.events.UIEvent;

public class DOMUIEvent extends AbstractEvent implements UIEvent
{
    private AbstractView view;
    private int detail;
    
    public AbstractView getView() {
        return this.view;
    }
    
    public int getDetail() {
        return this.detail;
    }
    
    public void initUIEvent(final String s, final boolean b, final boolean b2, final AbstractView view, final int detail) {
        this.initEvent(s, b, b2);
        this.view = view;
        this.detail = detail;
    }
    
    public void initUIEventNS(final String s, final String s2, final boolean b, final boolean b2, final AbstractView view, final int detail) {
        this.initEventNS(s, s2, b, b2);
        this.view = view;
        this.detail = detail;
    }
    
    protected String[] split(final String s) {
        final ArrayList list = new ArrayList<String>(8);
        int i = 0;
        final int length = s.length();
        while (i < length) {
            final char char1 = s.charAt(i++);
            if (XMLUtilities.isXMLSpace(char1)) {
                continue;
            }
            final StringBuffer sb = new StringBuffer();
            sb.append(char1);
            while (i < length) {
                final char char2 = s.charAt(i++);
                if (XMLUtilities.isXMLSpace(char2)) {
                    list.add(sb.toString());
                    break;
                }
                sb.append(char2);
            }
            if (i != length) {
                continue;
            }
            list.add(sb.toString());
        }
        return (String[])list.toArray(new String[list.size()]);
    }
}
