// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.commons.imaging.formats.bmp;

import java.awt.image.BufferedImage;
import java.io.IOException;
import org.apache.commons.imaging.common.BinaryOutputStream;

interface BmpWriter
{
    int getPaletteSize();
    
    int getBitsPerPixel();
    
    void writePalette(final BinaryOutputStream p0) throws IOException;
    
    byte[] getImageData(final BufferedImage p0);
}
