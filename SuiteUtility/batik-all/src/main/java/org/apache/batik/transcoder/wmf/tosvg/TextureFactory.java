// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.transcoder.wmf.tosvg;

import java.awt.Graphics2D;
import java.awt.TexturePaint;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.Color;
import java.awt.Paint;
import java.util.HashMap;
import java.util.Map;

public class TextureFactory
{
    private static TextureFactory fac;
    private Map textures;
    private static final int SIZE = 10;
    private float scale;
    
    private TextureFactory(final float n) {
        this.textures = new HashMap(1);
        this.scale = 1.0f;
    }
    
    public static TextureFactory getInstance() {
        if (TextureFactory.fac == null) {
            TextureFactory.fac = new TextureFactory(1.0f);
        }
        return TextureFactory.fac;
    }
    
    public static TextureFactory getInstance(final float n) {
        if (TextureFactory.fac == null) {
            TextureFactory.fac = new TextureFactory(n);
        }
        return TextureFactory.fac;
    }
    
    public void reset() {
        this.textures.clear();
    }
    
    public Paint getTexture(final int value) {
        final Integer n = new Integer(value);
        if (this.textures.containsKey(n)) {
            return (Paint)this.textures.get(n);
        }
        final Paint texture = this.createTexture(value, null, null);
        if (texture != null) {
            this.textures.put(n, texture);
        }
        return texture;
    }
    
    public Paint getTexture(final int n, final Color color) {
        final ColoredTexture coloredTexture = new ColoredTexture(n, color, null);
        if (this.textures.containsKey(coloredTexture)) {
            return (Paint)this.textures.get(coloredTexture);
        }
        final Paint texture = this.createTexture(n, color, null);
        if (texture != null) {
            this.textures.put(coloredTexture, texture);
        }
        return texture;
    }
    
    public Paint getTexture(final int n, final Color color, final Color color2) {
        final ColoredTexture coloredTexture = new ColoredTexture(n, color, color2);
        if (this.textures.containsKey(coloredTexture)) {
            return (Paint)this.textures.get(coloredTexture);
        }
        final Paint texture = this.createTexture(n, color, color2);
        if (texture != null) {
            this.textures.put(coloredTexture, texture);
        }
        return texture;
    }
    
    private Paint createTexture(final int n, final Color color, final Color color2) {
        final BufferedImage txtr = new BufferedImage(10, 10, 2);
        final Graphics2D graphics = txtr.createGraphics();
        final Rectangle2D.Float anchor = new Rectangle2D.Float(0.0f, 0.0f, 10.0f, 10.0f);
        Paint paint = null;
        boolean b = false;
        if (color2 != null) {
            graphics.setColor(color2);
            graphics.fillRect(0, 0, 10, 10);
        }
        if (color == null) {
            graphics.setColor(Color.black);
        }
        else {
            graphics.setColor(color);
        }
        if (n == 1) {
            for (int i = 0; i < 5; ++i) {
                graphics.drawLine(i * 10, 0, i * 10, 10);
            }
            b = true;
        }
        else if (n == 0) {
            for (int j = 0; j < 5; ++j) {
                graphics.drawLine(0, j * 10, 10, j * 10);
            }
            b = true;
        }
        else if (n == 3) {
            for (int k = 0; k < 5; ++k) {
                graphics.drawLine(0, k * 10, k * 10, 0);
            }
            b = true;
        }
        else if (n == 2) {
            for (int l = 0; l < 5; ++l) {
                graphics.drawLine(0, l * 10, 10 - l * 10, 10);
            }
            b = true;
        }
        else if (n == 5) {
            for (int n2 = 0; n2 < 5; ++n2) {
                graphics.drawLine(0, n2 * 10, n2 * 10, 0);
                graphics.drawLine(0, n2 * 10, 10 - n2 * 10, 10);
            }
            b = true;
        }
        else if (n == 4) {
            for (int n3 = 0; n3 < 5; ++n3) {
                graphics.drawLine(n3 * 10, 0, n3 * 10, 10);
                graphics.drawLine(0, n3 * 10, 10, n3 * 10);
            }
            b = true;
        }
        txtr.flush();
        if (b) {
            paint = new TexturePaint(txtr, anchor);
        }
        return paint;
    }
    
    static {
        TextureFactory.fac = null;
    }
    
    private class ColoredTexture
    {
        final int textureId;
        final Color foreground;
        final Color background;
        
        ColoredTexture(final int textureId, final Color foreground, final Color background) {
            this.textureId = textureId;
            this.foreground = foreground;
            this.background = background;
        }
    }
}
