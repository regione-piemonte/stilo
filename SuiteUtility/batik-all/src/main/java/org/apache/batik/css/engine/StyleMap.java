// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.css.engine;

import org.apache.batik.css.engine.value.Value;

public class StyleMap
{
    public static final short IMPORTANT_MASK = 1;
    public static final short COMPUTED_MASK = 2;
    public static final short NULL_CASCADED_MASK = 4;
    public static final short INHERITED_MASK = 8;
    public static final short LINE_HEIGHT_RELATIVE_MASK = 16;
    public static final short FONT_SIZE_RELATIVE_MASK = 32;
    public static final short COLOR_RELATIVE_MASK = 64;
    public static final short PARENT_RELATIVE_MASK = 128;
    public static final short BLOCK_WIDTH_RELATIVE_MASK = 256;
    public static final short BLOCK_HEIGHT_RELATIVE_MASK = 512;
    public static final short BOX_RELATIVE_MASK = 1024;
    public static final short ORIGIN_MASK = -8192;
    public static final short USER_AGENT_ORIGIN = 0;
    public static final short USER_ORIGIN = 8192;
    public static final short NON_CSS_ORIGIN = 16384;
    public static final short AUTHOR_ORIGIN = 24576;
    public static final short INLINE_AUTHOR_ORIGIN = Short.MIN_VALUE;
    public static final short OVERRIDE_ORIGIN = -24576;
    protected Value[] values;
    protected short[] masks;
    protected boolean fixedCascadedValues;
    
    public StyleMap(final int n) {
        this.values = new Value[n];
        this.masks = new short[n];
    }
    
    public boolean hasFixedCascadedValues() {
        return this.fixedCascadedValues;
    }
    
    public void setFixedCascadedStyle(final boolean fixedCascadedValues) {
        this.fixedCascadedValues = fixedCascadedValues;
    }
    
    public Value getValue(final int n) {
        return this.values[n];
    }
    
    public short getMask(final int n) {
        return this.masks[n];
    }
    
    public boolean isImportant(final int n) {
        return (this.masks[n] & 0x1) != 0x0;
    }
    
    public boolean isComputed(final int n) {
        return (this.masks[n] & 0x2) != 0x0;
    }
    
    public boolean isNullCascaded(final int n) {
        return (this.masks[n] & 0x4) != 0x0;
    }
    
    public boolean isInherited(final int n) {
        return (this.masks[n] & 0x8) != 0x0;
    }
    
    public short getOrigin(final int n) {
        return (short)(this.masks[n] & 0xFFFFE000);
    }
    
    public boolean isColorRelative(final int n) {
        return (this.masks[n] & 0x40) != 0x0;
    }
    
    public boolean isParentRelative(final int n) {
        return (this.masks[n] & 0x80) != 0x0;
    }
    
    public boolean isLineHeightRelative(final int n) {
        return (this.masks[n] & 0x10) != 0x0;
    }
    
    public boolean isFontSizeRelative(final int n) {
        return (this.masks[n] & 0x20) != 0x0;
    }
    
    public boolean isBlockWidthRelative(final int n) {
        return (this.masks[n] & 0x100) != 0x0;
    }
    
    public boolean isBlockHeightRelative(final int n) {
        return (this.masks[n] & 0x200) != 0x0;
    }
    
    public void putValue(final int n, final Value value) {
        this.values[n] = value;
    }
    
    public void putMask(final int n, final short n2) {
        this.masks[n] = n2;
    }
    
    public void putImportant(final int n, final boolean b) {
        if (b) {
            final short[] masks = this.masks;
            masks[n] |= 0x1;
        }
        else {
            final short[] masks2 = this.masks;
            masks2[n] &= 0xFFFFFFFE;
        }
    }
    
    public void putOrigin(final int n, final short n2) {
        final short[] masks = this.masks;
        masks[n] &= 0x1FFF;
        final short[] masks2 = this.masks;
        masks2[n] |= (short)(n2 & 0xFFFFE000);
    }
    
    public void putComputed(final int n, final boolean b) {
        if (b) {
            final short[] masks = this.masks;
            masks[n] |= 0x2;
        }
        else {
            final short[] masks2 = this.masks;
            masks2[n] &= 0xFFFFFFFD;
        }
    }
    
    public void putNullCascaded(final int n, final boolean b) {
        if (b) {
            final short[] masks = this.masks;
            masks[n] |= 0x4;
        }
        else {
            final short[] masks2 = this.masks;
            masks2[n] &= 0xFFFFFFFB;
        }
    }
    
    public void putInherited(final int n, final boolean b) {
        if (b) {
            final short[] masks = this.masks;
            masks[n] |= 0x8;
        }
        else {
            final short[] masks2 = this.masks;
            masks2[n] &= 0xFFFFFFF7;
        }
    }
    
    public void putColorRelative(final int n, final boolean b) {
        if (b) {
            final short[] masks = this.masks;
            masks[n] |= 0x40;
        }
        else {
            final short[] masks2 = this.masks;
            masks2[n] &= 0xFFFFFFBF;
        }
    }
    
    public void putParentRelative(final int n, final boolean b) {
        if (b) {
            final short[] masks = this.masks;
            masks[n] |= 0x80;
        }
        else {
            final short[] masks2 = this.masks;
            masks2[n] &= 0xFFFFFF7F;
        }
    }
    
    public void putLineHeightRelative(final int n, final boolean b) {
        if (b) {
            final short[] masks = this.masks;
            masks[n] |= 0x10;
        }
        else {
            final short[] masks2 = this.masks;
            masks2[n] &= 0xFFFFFFEF;
        }
    }
    
    public void putFontSizeRelative(final int n, final boolean b) {
        if (b) {
            final short[] masks = this.masks;
            masks[n] |= 0x20;
        }
        else {
            final short[] masks2 = this.masks;
            masks2[n] &= 0xFFFFFFDF;
        }
    }
    
    public void putBlockWidthRelative(final int n, final boolean b) {
        if (b) {
            final short[] masks = this.masks;
            masks[n] |= 0x100;
        }
        else {
            final short[] masks2 = this.masks;
            masks2[n] &= 0xFFFFFEFF;
        }
    }
    
    public void putBlockHeightRelative(final int n, final boolean b) {
        if (b) {
            final short[] masks = this.masks;
            masks[n] |= 0x200;
        }
        else {
            final short[] masks2 = this.masks;
            masks2[n] &= 0xFFFFFDFF;
        }
    }
    
    public String toString(final CSSEngine cssEngine) {
        final int length = this.values.length;
        final StringBuffer sb = new StringBuffer(length * 8);
        for (int i = 0; i < length; ++i) {
            final Value obj = this.values[i];
            if (obj != null) {
                sb.append(cssEngine.getPropertyName(i));
                sb.append(": ");
                sb.append(obj);
                if (this.isImportant(i)) {
                    sb.append(" !important");
                }
                sb.append(";\n");
            }
        }
        return sb.toString();
    }
}
