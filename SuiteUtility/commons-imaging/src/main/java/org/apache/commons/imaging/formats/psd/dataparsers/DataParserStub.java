// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.commons.imaging.formats.psd.dataparsers;

import org.apache.commons.imaging.formats.psd.PsdImageContents;

public class DataParserStub extends DataParser
{
    @Override
    protected int getRGB(final int[][][] data, final int x, final int y, final PsdImageContents imageContents) {
        return 0;
    }
    
    @Override
    public int getBasicChannelsCount() {
        return 1;
    }
}
