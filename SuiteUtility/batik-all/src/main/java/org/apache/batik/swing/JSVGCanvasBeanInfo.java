// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.swing;

import java.awt.Image;
import java.beans.SimpleBeanInfo;

public class JSVGCanvasBeanInfo extends SimpleBeanInfo
{
    protected Image iconColor16x16;
    protected Image iconMono16x16;
    protected Image iconColor32x32;
    protected Image iconMono32x32;
    
    public JSVGCanvasBeanInfo() {
        this.iconColor16x16 = this.loadImage("resources/batikColor16x16.gif");
        this.iconMono16x16 = this.loadImage("resources/batikMono16x16.gif");
        this.iconColor32x32 = this.loadImage("resources/batikColor32x32.gif");
        this.iconMono32x32 = this.loadImage("resources/batikMono32x32.gif");
    }
    
    public Image getIcon(final int n) {
        switch (n) {
            case 1: {
                return this.iconColor16x16;
            }
            case 3: {
                return this.iconMono16x16;
            }
            case 2: {
                return this.iconColor32x32;
            }
            case 4: {
                return this.iconMono32x32;
            }
            default: {
                return null;
            }
        }
    }
}
