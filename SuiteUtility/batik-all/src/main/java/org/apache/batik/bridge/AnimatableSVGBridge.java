// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.bridge;

import java.util.Iterator;
import org.apache.batik.dom.anim.AnimationTarget;
import java.util.LinkedList;
import org.apache.batik.dom.anim.AnimationTargetListener;
import java.util.HashMap;
import org.w3c.dom.Element;
import org.apache.batik.dom.svg.SVGAnimationTargetContext;

public abstract class AnimatableSVGBridge extends AbstractSVGBridge implements SVGAnimationTargetContext
{
    protected Element e;
    protected BridgeContext ctx;
    protected HashMap targetListeners;
    
    public void addTargetListener(final String s, final AnimationTargetListener e) {
        if (this.targetListeners == null) {
            this.targetListeners = new HashMap();
        }
        LinkedList<AnimationTargetListener> value = this.targetListeners.get(s);
        if (value == null) {
            value = new LinkedList<AnimationTargetListener>();
            this.targetListeners.put(s, value);
        }
        value.add(e);
    }
    
    public void removeTargetListener(final String key, final AnimationTargetListener o) {
        this.targetListeners.get(key).remove(o);
    }
    
    protected void fireBaseAttributeListeners(final String key) {
        if (this.targetListeners != null) {
            final LinkedList<AnimationTargetListener> list = this.targetListeners.get(key);
            if (list != null) {
                final Iterator<Object> iterator = list.iterator();
                while (iterator.hasNext()) {
                    iterator.next().baseValueChanged((AnimationTarget)this.e, null, key, true);
                }
            }
        }
    }
}
