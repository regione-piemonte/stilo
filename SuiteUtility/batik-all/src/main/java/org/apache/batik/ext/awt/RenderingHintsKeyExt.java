// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.ext.awt;

import java.awt.RenderingHints;

public final class RenderingHintsKeyExt
{
    public static final int KEY_BASE;
    public static final RenderingHints.Key KEY_TRANSCODING;
    public static final String VALUE_TRANSCODING_PRINTING = "Printing";
    public static final String VALUE_TRANSCODING_VECTOR = "Vector";
    public static final RenderingHints.Key KEY_AREA_OF_INTEREST;
    public static final RenderingHints.Key KEY_BUFFERED_IMAGE;
    public static final RenderingHints.Key KEY_COLORSPACE;
    public static final RenderingHints.Key KEY_AVOID_TILE_PAINTING;
    public static final Object VALUE_AVOID_TILE_PAINTING_ON;
    public static final Object VALUE_AVOID_TILE_PAINTING_OFF;
    public static final Object VALUE_AVOID_TILE_PAINTING_DEFAULT;
    
    private RenderingHintsKeyExt() {
    }
    
    static {
        VALUE_AVOID_TILE_PAINTING_ON = new Object();
        VALUE_AVOID_TILE_PAINTING_OFF = new Object();
        VALUE_AVOID_TILE_PAINTING_DEFAULT = new Object();
        int key_BASE = 10100;
        TranscodingHintKey key_TRANSCODING;
        AreaOfInterestHintKey key_AREA_OF_INTEREST;
        BufferedImageHintKey key_BUFFERED_IMAGE;
        ColorSpaceHintKey key_COLORSPACE;
        AvoidTilingHintKey key_AVOID_TILE_PAINTING;
        while (true) {
            int n = key_BASE;
            try {
                key_TRANSCODING = new TranscodingHintKey(n++);
                key_AREA_OF_INTEREST = new AreaOfInterestHintKey(n++);
                key_BUFFERED_IMAGE = new BufferedImageHintKey(n++);
                key_COLORSPACE = new ColorSpaceHintKey(n++);
                key_AVOID_TILE_PAINTING = new AvoidTilingHintKey(n++);
            }
            catch (Exception ex) {
                System.err.println("You have loaded the Batik jar files more than once\nin the same JVM this is likely a problem with the\nway you are loading the Batik jar files.");
                key_BASE = (int)(Math.random() * 2000000.0);
                continue;
            }
            break;
        }
        KEY_BASE = key_BASE;
        KEY_TRANSCODING = key_TRANSCODING;
        KEY_AREA_OF_INTEREST = key_AREA_OF_INTEREST;
        KEY_BUFFERED_IMAGE = key_BUFFERED_IMAGE;
        KEY_COLORSPACE = key_COLORSPACE;
        KEY_AVOID_TILE_PAINTING = key_AVOID_TILE_PAINTING;
    }
}
