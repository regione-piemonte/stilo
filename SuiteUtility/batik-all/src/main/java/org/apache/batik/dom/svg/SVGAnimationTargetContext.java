// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.dom.svg;

import org.apache.batik.dom.anim.AnimationTargetListener;

public interface SVGAnimationTargetContext extends SVGContext
{
    void addTargetListener(final String p0, final AnimationTargetListener p1);
    
    void removeTargetListener(final String p0, final AnimationTargetListener p1);
}
