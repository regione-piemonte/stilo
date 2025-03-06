// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.commons.imaging.formats.gif;

import java.util.List;

class GifImageContents
{
    final GifHeaderInfo gifHeaderInfo;
    final List<GifBlock> blocks;
    final byte[] globalColorTable;
    
    GifImageContents(final GifHeaderInfo gifHeaderInfo, final byte[] globalColorTable, final List<GifBlock> blocks) {
        this.gifHeaderInfo = gifHeaderInfo;
        this.globalColorTable = globalColorTable;
        this.blocks = blocks;
    }
}
