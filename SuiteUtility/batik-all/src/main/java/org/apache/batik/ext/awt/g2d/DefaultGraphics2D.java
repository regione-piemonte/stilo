// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.ext.awt.g2d;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Font;
import java.awt.GraphicsConfiguration;
import java.text.AttributedCharacterIterator;
import java.awt.image.renderable.RenderableImage;
import java.awt.geom.AffineTransform;
import java.awt.image.RenderedImage;
import java.awt.Shape;
import java.awt.image.ImageObserver;
import java.awt.Image;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.Graphics2D;

public class DefaultGraphics2D extends AbstractGraphics2D
{
    private Graphics2D fmg;
    
    public DefaultGraphics2D(final boolean b) {
        super(b);
        this.fmg = new BufferedImage(1, 1, 2).createGraphics();
    }
    
    public DefaultGraphics2D(final DefaultGraphics2D defaultGraphics2D) {
        super(defaultGraphics2D);
        this.fmg = new BufferedImage(1, 1, 2).createGraphics();
    }
    
    public Graphics create() {
        return new DefaultGraphics2D(this);
    }
    
    public boolean drawImage(final Image image, final int n, final int n2, final ImageObserver imageObserver) {
        System.err.println("drawImage");
        return true;
    }
    
    public boolean drawImage(final Image image, final int n, final int n2, final int n3, final int n4, final ImageObserver imageObserver) {
        System.out.println("drawImage");
        return true;
    }
    
    public void dispose() {
        System.out.println("dispose");
    }
    
    public void draw(final Shape shape) {
        System.out.println("draw(Shape)");
    }
    
    public void drawRenderedImage(final RenderedImage renderedImage, final AffineTransform affineTransform) {
        System.out.println("drawRenderedImage");
    }
    
    public void drawRenderableImage(final RenderableImage renderableImage, final AffineTransform affineTransform) {
        System.out.println("drawRenderableImage");
    }
    
    public void drawString(final String s, final float n, final float n2) {
        System.out.println("drawString(String)");
    }
    
    public void drawString(final AttributedCharacterIterator attributedCharacterIterator, final float n, final float n2) {
        System.err.println("drawString(AttributedCharacterIterator)");
    }
    
    public void fill(final Shape shape) {
        System.err.println("fill");
    }
    
    public GraphicsConfiguration getDeviceConfiguration() {
        System.out.println("getDeviceConviguration");
        return null;
    }
    
    public FontMetrics getFontMetrics(final Font font) {
        return this.fmg.getFontMetrics(font);
    }
    
    public void setXORMode(final Color color) {
        System.out.println("setXORMode");
    }
    
    public void copyArea(final int n, final int n2, final int n3, final int n4, final int n5, final int n6) {
        System.out.println("copyArea");
    }
}
