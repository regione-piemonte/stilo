// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.commons.imaging.formats.gif;

class ImageDescriptor extends GifBlock
{
    final int imageLeftPosition;
    final int imageTopPosition;
    final int imageWidth;
    final int imageHeight;
    final byte packedFields;
    final boolean localColorTableFlag;
    final boolean interlaceFlag;
    final boolean sortFlag;
    final byte sizeOfLocalColorTable;
    final byte[] localColorTable;
    final byte[] imageData;
    
    ImageDescriptor(final int blockCode, final int imageLeftPosition, final int imageTopPosition, final int imageWidth, final int imageHeight, final byte packedFields, final boolean localColorTableFlag, final boolean interlaceFlag, final boolean sortFlag, final byte sizeofLocalColorTable, final byte[] localColorTable, final byte[] imageData) {
        super(blockCode);
        this.imageLeftPosition = imageLeftPosition;
        this.imageTopPosition = imageTopPosition;
        this.imageWidth = imageWidth;
        this.imageHeight = imageHeight;
        this.packedFields = packedFields;
        this.localColorTableFlag = localColorTableFlag;
        this.interlaceFlag = interlaceFlag;
        this.sortFlag = sortFlag;
        this.sizeOfLocalColorTable = sizeofLocalColorTable;
        this.localColorTable = localColorTable;
        this.imageData = imageData;
    }
}
