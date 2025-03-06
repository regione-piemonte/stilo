// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.commons.imaging.formats.psd;

import java.io.IOException;
import java.util.logging.Level;
import java.io.Writer;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.logging.Logger;

public class PsdImageContents
{
    private static final Logger LOGGER;
    public final PsdHeaderInfo header;
    public final int ColorModeDataLength;
    public final int ImageResourcesLength;
    public final int LayerAndMaskDataLength;
    public final int Compression;
    
    public PsdImageContents(final PsdHeaderInfo header, final int ColorModeDataLength, final int ImageResourcesLength, final int LayerAndMaskDataLength, final int Compression) {
        this.header = header;
        this.ColorModeDataLength = ColorModeDataLength;
        this.ImageResourcesLength = ImageResourcesLength;
        this.LayerAndMaskDataLength = LayerAndMaskDataLength;
        this.Compression = Compression;
    }
    
    public void dump() {
        try (final StringWriter sw = new StringWriter();
             final PrintWriter pw = new PrintWriter(sw)) {
            this.dump(pw);
            pw.flush();
            sw.flush();
            PsdImageContents.LOGGER.fine(sw.toString());
        }
        catch (IOException e) {
            PsdImageContents.LOGGER.log(Level.SEVERE, e.getMessage(), e);
        }
    }
    
    public void dump(final PrintWriter pw) {
        pw.println("");
        pw.println("ImageContents");
        pw.println("Compression: " + this.Compression + " (" + Integer.toHexString(this.Compression) + ")");
        pw.println("ColorModeDataLength: " + this.ColorModeDataLength + " (" + Integer.toHexString(this.ColorModeDataLength) + ")");
        pw.println("ImageResourcesLength: " + this.ImageResourcesLength + " (" + Integer.toHexString(this.ImageResourcesLength) + ")");
        pw.println("LayerAndMaskDataLength: " + this.LayerAndMaskDataLength + " (" + Integer.toHexString(this.LayerAndMaskDataLength) + ")");
        pw.println("");
        pw.flush();
    }
    
    static {
        LOGGER = Logger.getLogger(PsdImageContents.class.getName());
    }
}
