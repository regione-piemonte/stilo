// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.anim.values;

import java.text.DecimalFormatSymbols;
import java.util.Locale;
import org.apache.batik.dom.anim.AnimationTarget;
import java.text.DecimalFormat;

public abstract class AnimatableValue
{
    protected static DecimalFormat decimalFormat;
    protected AnimationTarget target;
    protected boolean hasChanged;
    
    protected AnimatableValue(final AnimationTarget target) {
        this.hasChanged = true;
        this.target = target;
    }
    
    public static String formatNumber(final float n) {
        return AnimatableValue.decimalFormat.format(n);
    }
    
    public abstract AnimatableValue interpolate(final AnimatableValue p0, final AnimatableValue p1, final float p2, final AnimatableValue p3, final int p4);
    
    public abstract boolean canPace();
    
    public abstract float distanceTo(final AnimatableValue p0);
    
    public abstract AnimatableValue getZeroValue();
    
    public String getCssText() {
        return null;
    }
    
    public boolean hasChanged() {
        final boolean hasChanged = this.hasChanged;
        this.hasChanged = false;
        return hasChanged;
    }
    
    public String toStringRep() {
        return this.getCssText();
    }
    
    public String toString() {
        return this.getClass().getName() + "[" + this.toStringRep() + "]";
    }
    
    static {
        AnimatableValue.decimalFormat = new DecimalFormat("0.0###########################################################", new DecimalFormatSymbols(Locale.ENGLISH));
    }
}
