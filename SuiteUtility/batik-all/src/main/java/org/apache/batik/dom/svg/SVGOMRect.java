// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.dom.svg;

import org.w3c.dom.DOMException;
import org.w3c.dom.svg.SVGRect;

public class SVGOMRect implements SVGRect
{
    protected float x;
    protected float y;
    protected float w;
    protected float h;
    
    public SVGOMRect() {
    }
    
    public SVGOMRect(final float x, final float y, final float w, final float h) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
    }
    
    public float getX() {
        return this.x;
    }
    
    public void setX(final float x) throws DOMException {
        this.x = x;
    }
    
    public float getY() {
        return this.y;
    }
    
    public void setY(final float y) throws DOMException {
        this.y = y;
    }
    
    public float getWidth() {
        return this.w;
    }
    
    public void setWidth(final float w) throws DOMException {
        this.w = w;
    }
    
    public float getHeight() {
        return this.h;
    }
    
    public void setHeight(final float h) throws DOMException {
        this.h = h;
    }
}
