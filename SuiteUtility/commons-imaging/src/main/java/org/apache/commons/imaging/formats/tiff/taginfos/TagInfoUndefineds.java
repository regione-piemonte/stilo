// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.commons.imaging.formats.tiff.taginfos;

import org.apache.commons.imaging.formats.tiff.fieldtypes.FieldType;
import org.apache.commons.imaging.formats.tiff.constants.TiffDirectoryType;

public class TagInfoUndefineds extends TagInfoBytes
{
    public TagInfoUndefineds(final String name, final int tag, final int length, final TiffDirectoryType directoryType) {
        super(name, tag, FieldType.UNDEFINED, length, directoryType);
    }
}
