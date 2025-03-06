// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.css.engine.value.svg;

import org.w3c.dom.DOMException;
import org.apache.batik.css.engine.value.AbstractValue;

public class ICCColor extends AbstractValue
{
    protected String colorProfile;
    protected int count;
    protected float[] colors;
    
    public ICCColor(final String colorProfile) {
        this.colors = new float[5];
        this.colorProfile = colorProfile;
    }
    
    public short getCssValueType() {
        return 3;
    }
    
    public String getColorProfile() throws DOMException {
        return this.colorProfile;
    }
    
    public int getNumberOfColors() throws DOMException {
        return this.count;
    }
    
    public float getColor(final int n) throws DOMException {
        return this.colors[n];
    }
    
    public String getCssText() {
        final StringBuffer sb = new StringBuffer(this.count * 8);
        sb.append("icc-color(");
        sb.append(this.colorProfile);
        for (int i = 0; i < this.count; ++i) {
            sb.append(", ");
            sb.append(this.colors[i]);
        }
        sb.append(')');
        return sb.toString();
    }
    
    public void append(final float n) {
        if (this.count == this.colors.length) {
            final float[] colors = new float[this.count * 2];
            System.arraycopy(this.colors, 0, colors, 0, this.count);
            this.colors = colors;
        }
        this.colors[this.count++] = n;
    }
}
