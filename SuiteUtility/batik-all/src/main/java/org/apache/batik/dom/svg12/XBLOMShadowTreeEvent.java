// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.dom.svg12;

import org.apache.batik.dom.xbl.XBLShadowTreeElement;
import org.apache.batik.dom.xbl.ShadowTreeEvent;
import org.apache.batik.dom.events.AbstractEvent;

public class XBLOMShadowTreeEvent extends AbstractEvent implements ShadowTreeEvent
{
    protected XBLShadowTreeElement xblShadowTree;
    
    public XBLShadowTreeElement getXblShadowTree() {
        return this.xblShadowTree;
    }
    
    public void initShadowTreeEvent(final String s, final boolean b, final boolean b2, final XBLShadowTreeElement xblShadowTree) {
        this.initEvent(s, b, b2);
        this.xblShadowTree = xblShadowTree;
    }
    
    public void initShadowTreeEventNS(final String s, final String s2, final boolean b, final boolean b2, final XBLShadowTreeElement xblShadowTree) {
        this.initEventNS(s, s2, b, b2);
        this.xblShadowTree = xblShadowTree;
    }
}
