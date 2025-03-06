// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.ext.awt.image.rendered;

import java.awt.image.Raster;
import java.awt.image.WritableRaster;
import java.awt.image.LookupTable;
import java.awt.image.ByteLookupTable;
import java.util.Map;
import org.apache.batik.ext.awt.image.GraphicsUtil;
import java.awt.RenderingHints;
import org.apache.batik.ext.awt.image.TransferFunction;
import java.awt.image.LookupOp;

public class ComponentTransferRed extends AbstractRed
{
    LookupOp operation;
    
    public ComponentTransferRed(final CachableRed cachableRed, final TransferFunction[] array, final RenderingHints renderingHints) {
        super(cachableRed, cachableRed.getBounds(), GraphicsUtil.coerceColorModel(cachableRed.getColorModel(), false), cachableRed.getSampleModel(), null);
        this.operation = new LookupOp((LookupTable)new ByteLookupTable(0, new byte[][] { array[1].getLookupTable(), array[2].getLookupTable(), array[3].getLookupTable(), array[0].getLookupTable() }), renderingHints) {};
    }
    
    public WritableRaster copyData(WritableRaster copyData) {
        final CachableRed cachableRed = this.getSources().get(0);
        copyData = cachableRed.copyData(copyData);
        GraphicsUtil.coerceData(copyData, cachableRed.getColorModel(), false);
        final WritableRaster writableTranslatedChild = copyData.createWritableTranslatedChild(0, 0);
        this.operation.filter(writableTranslatedChild, writableTranslatedChild);
        return copyData;
    }
}
