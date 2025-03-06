// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.ext.awt.image.spi;

import java.awt.Graphics2D;
import org.apache.batik.ext.awt.image.renderable.RedRable;
import java.awt.image.RenderedImage;
import org.apache.batik.ext.awt.image.GraphicsUtil;
import java.util.Hashtable;
import java.awt.image.BufferedImage;
import org.apache.batik.i18n.LocalizableSupport;
import java.awt.Color;
import org.apache.batik.ext.awt.image.renderable.Filter;

public class DefaultBrokenLinkProvider extends BrokenLinkProvider
{
    static Filter brokenLinkImg;
    static final String MESSAGE_RSRC = "resources.Messages";
    static final Color BROKEN_LINK_COLOR;
    static /* synthetic */ Class class$org$apache$batik$ext$awt$image$spi$DefaultBrokenLinkProvider;
    
    public static String formatMessage(final Object o, final String s, final Object[] array) {
        ClassLoader classLoader = null;
        try {
            classLoader = DefaultBrokenLinkProvider.class.getClassLoader();
            classLoader = o.getClass().getClassLoader();
        }
        catch (SecurityException ex) {}
        return new LocalizableSupport("resources.Messages", o.getClass(), classLoader).formatMessage(s, array);
    }
    
    public Filter getBrokenLinkImage(final Object o, final String s, final Object[] array) {
        Class class$;
        Class class$org$apache$batik$ext$awt$image$spi$DefaultBrokenLinkProvider;
        if (DefaultBrokenLinkProvider.class$org$apache$batik$ext$awt$image$spi$DefaultBrokenLinkProvider == null) {
            class$org$apache$batik$ext$awt$image$spi$DefaultBrokenLinkProvider = (DefaultBrokenLinkProvider.class$org$apache$batik$ext$awt$image$spi$DefaultBrokenLinkProvider = (class$ = class$("org.apache.batik.ext.awt.image.spi.DefaultBrokenLinkProvider")));
        }
        else {
            class$ = (class$org$apache$batik$ext$awt$image$spi$DefaultBrokenLinkProvider = DefaultBrokenLinkProvider.class$org$apache$batik$ext$awt$image$spi$DefaultBrokenLinkProvider);
        }
        final Class clazz = class$org$apache$batik$ext$awt$image$spi$DefaultBrokenLinkProvider;
        synchronized (class$) {
            if (DefaultBrokenLinkProvider.brokenLinkImg != null) {
                return DefaultBrokenLinkProvider.brokenLinkImg;
            }
            final BufferedImage bufferedImage = new BufferedImage(100, 100, 2);
            final Hashtable<String, String> properties = new Hashtable<String, String>();
            properties.put("org.apache.batik.BrokenLinkImage", formatMessage(o, s, array));
            final BufferedImage bufferedImage2 = new BufferedImage(bufferedImage.getColorModel(), bufferedImage.getRaster(), bufferedImage.isAlphaPremultiplied(), properties);
            final Graphics2D graphics = bufferedImage2.createGraphics();
            graphics.setColor(DefaultBrokenLinkProvider.BROKEN_LINK_COLOR);
            graphics.fillRect(0, 0, 100, 100);
            graphics.setColor(Color.black);
            graphics.drawRect(2, 2, 96, 96);
            graphics.drawString("Broken Image", 6, 50);
            graphics.dispose();
            return DefaultBrokenLinkProvider.brokenLinkImg = new RedRable(GraphicsUtil.wrap(bufferedImage2));
        }
    }
    
    static /* synthetic */ Class class$(final String className) {
        try {
            return Class.forName(className);
        }
        catch (ClassNotFoundException ex) {
            throw new NoClassDefFoundError(ex.getMessage());
        }
    }
    
    static {
        DefaultBrokenLinkProvider.brokenLinkImg = null;
        BROKEN_LINK_COLOR = new Color(255, 255, 255, 190);
    }
}
