// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.pdfbox.jbig2;

import java.awt.Dimension;
import java.awt.Rectangle;
import javax.imageio.ImageReadParam;

public class JBIG2ReadParam extends ImageReadParam
{
    public JBIG2ReadParam() {
        this(1, 1, 0, 0, null, null);
    }
    
    public JBIG2ReadParam(final int n, final int n2, final int subsamplingXOffset, final int subsamplingYOffset, final Rectangle sourceRegion, final Dimension sourceRenderSize) {
        this.canSetSourceRenderSize = true;
        this.sourceRegion = sourceRegion;
        this.sourceRenderSize = sourceRenderSize;
        if (n < 1 || n2 < 1) {
            throw new IllegalArgumentException("Illegal subsampling factor: shall be 1 or greater; but was  sourceXSubsampling=" + n + ", sourceYSubsampling=" + n2);
        }
        this.setSourceSubsampling(n, n2, subsamplingXOffset, subsamplingYOffset);
    }
}
