// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.gvt.renderer;

public class ConcreteImageRendererFactory implements ImageRendererFactory
{
    static final boolean onMacOSX;
    
    public Renderer createRenderer() {
        return this.createStaticImageRenderer();
    }
    
    public ImageRenderer createStaticImageRenderer() {
        if (ConcreteImageRendererFactory.onMacOSX) {
            return new MacRenderer();
        }
        return new StaticRenderer();
    }
    
    public ImageRenderer createDynamicImageRenderer() {
        if (ConcreteImageRendererFactory.onMacOSX) {
            return new MacRenderer();
        }
        return new DynamicRenderer();
    }
    
    static {
        onMacOSX = "Mac OS X".equals(System.getProperty("os.name"));
    }
}
