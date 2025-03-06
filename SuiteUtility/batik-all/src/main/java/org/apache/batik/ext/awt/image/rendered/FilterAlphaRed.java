// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.ext.awt.image.rendered;

import java.awt.image.Raster;
import java.awt.image.WritableRaster;
import org.apache.batik.ext.awt.ColorSpaceHintKey;
import java.util.Map;

public class FilterAlphaRed extends AbstractRed
{
    public FilterAlphaRed(final CachableRed cachableRed) {
        super(cachableRed, cachableRed.getBounds(), cachableRed.getColorModel(), cachableRed.getSampleModel(), cachableRed.getTileGridXOffset(), cachableRed.getTileGridYOffset(), null);
        this.props.put("org.apache.batik.gvt.filter.Colorspace", ColorSpaceHintKey.VALUE_COLORSPACE_ALPHA);
    }
    
    public WritableRaster copyData(final WritableRaster writableRaster) {
        final CachableRed cachableRed = this.getSources().get(0);
        if (cachableRed.getSampleModel().getNumBands() == 1) {
            return cachableRed.copyData(writableRaster);
        }
        PadRed.ZeroRecter.zeroRect(writableRaster);
        final Raster data = cachableRed.getData(writableRaster.getBounds());
        AbstractRed.copyBand(data, data.getNumBands() - 1, writableRaster, writableRaster.getNumBands() - 1);
        return writableRaster;
    }
}
