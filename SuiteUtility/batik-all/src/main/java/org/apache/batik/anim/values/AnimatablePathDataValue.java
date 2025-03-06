// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.anim.values;

import java.util.Arrays;
import org.apache.batik.dom.anim.AnimationTarget;

public class AnimatablePathDataValue extends AnimatableValue
{
    protected short[] commands;
    protected float[] parameters;
    protected static final char[] PATH_COMMANDS;
    protected static final int[] PATH_PARAMS;
    
    protected AnimatablePathDataValue(final AnimationTarget animationTarget) {
        super(animationTarget);
    }
    
    public AnimatablePathDataValue(final AnimationTarget animationTarget, final short[] commands, final float[] parameters) {
        super(animationTarget);
        this.commands = commands;
        this.parameters = parameters;
    }
    
    public AnimatableValue interpolate(final AnimatableValue animatableValue, final AnimatableValue animatableValue2, final float n, final AnimatableValue animatableValue3, final int n2) {
        final AnimatablePathDataValue animatablePathDataValue = (AnimatablePathDataValue)animatableValue2;
        final AnimatablePathDataValue animatablePathDataValue2 = (AnimatablePathDataValue)animatableValue3;
        final boolean b = animatableValue2 != null;
        final boolean b2 = animatableValue3 != null;
        final boolean b3 = b && animatablePathDataValue.parameters.length == this.parameters.length && Arrays.equals(animatablePathDataValue.commands, this.commands);
        final boolean b4 = b2 && animatablePathDataValue2.parameters.length == this.parameters.length && Arrays.equals(animatablePathDataValue2.commands, this.commands);
        AnimatablePathDataValue animatablePathDataValue3;
        if (!b3 && b && n >= 0.5) {
            animatablePathDataValue3 = animatablePathDataValue;
        }
        else {
            animatablePathDataValue3 = this;
        }
        final int length = animatablePathDataValue3.commands.length;
        final int length2 = animatablePathDataValue3.parameters.length;
        AnimatablePathDataValue animatablePathDataValue4;
        if (animatableValue == null) {
            animatablePathDataValue4 = new AnimatablePathDataValue(this.target);
            animatablePathDataValue4.commands = new short[length];
            animatablePathDataValue4.parameters = new float[length2];
            System.arraycopy(animatablePathDataValue3.commands, 0, animatablePathDataValue4.commands, 0, length);
        }
        else {
            animatablePathDataValue4 = (AnimatablePathDataValue)animatableValue;
            if (animatablePathDataValue4.commands == null || animatablePathDataValue4.commands.length != length) {
                animatablePathDataValue4.commands = new short[length];
                System.arraycopy(animatablePathDataValue3.commands, 0, animatablePathDataValue4.commands, 0, length);
                animatablePathDataValue4.hasChanged = true;
            }
            else if (!Arrays.equals(animatablePathDataValue3.commands, animatablePathDataValue4.commands)) {
                System.arraycopy(animatablePathDataValue3.commands, 0, animatablePathDataValue4.commands, 0, length);
                animatablePathDataValue4.hasChanged = true;
            }
        }
        for (int i = 0; i < length2; ++i) {
            float n3 = animatablePathDataValue3.parameters[i];
            if (b3) {
                n3 += n * (animatablePathDataValue.parameters[i] - n3);
            }
            if (b4) {
                n3 += n2 * animatablePathDataValue2.parameters[i];
            }
            if (animatablePathDataValue4.parameters[i] != n3) {
                animatablePathDataValue4.parameters[i] = n3;
                animatablePathDataValue4.hasChanged = true;
            }
        }
        return animatablePathDataValue4;
    }
    
    public short[] getCommands() {
        return this.commands;
    }
    
    public float[] getParameters() {
        return this.parameters;
    }
    
    public boolean canPace() {
        return false;
    }
    
    public float distanceTo(final AnimatableValue animatableValue) {
        return 0.0f;
    }
    
    public AnimatableValue getZeroValue() {
        final short[] array = new short[this.commands.length];
        System.arraycopy(this.commands, 0, array, 0, this.commands.length);
        return new AnimatablePathDataValue(this.target, array, new float[this.parameters.length]);
    }
    
    public String toStringRep() {
        final StringBuffer sb = new StringBuffer();
        int n = 0;
        for (int i = 0; i < this.commands.length; ++i) {
            sb.append(AnimatablePathDataValue.PATH_COMMANDS[this.commands[i]]);
            for (int j = 0; j < AnimatablePathDataValue.PATH_PARAMS[this.commands[i]]; ++j) {
                sb.append(' ');
                sb.append(this.parameters[n++]);
            }
        }
        return sb.toString();
    }
    
    static {
        PATH_COMMANDS = new char[] { ' ', 'z', 'M', 'm', 'L', 'l', 'C', 'c', 'Q', 'q', 'A', 'a', 'H', 'h', 'V', 'v', 'S', 's', 'T', 't' };
        PATH_PARAMS = new int[] { 0, 0, 2, 2, 2, 2, 6, 6, 4, 4, 7, 7, 1, 1, 1, 1, 4, 4, 2, 2 };
    }
}
