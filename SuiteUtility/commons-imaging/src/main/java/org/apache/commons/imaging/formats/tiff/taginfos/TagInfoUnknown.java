// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.commons.imaging.formats.tiff.taginfos;

import org.apache.commons.imaging.formats.tiff.fieldtypes.FieldType;
import org.apache.commons.imaging.formats.tiff.constants.TiffDirectoryType;

public final class TagInfoUnknown extends TagInfoByte
{
    public TagInfoUnknown(final String name, final int tag, final TiffDirectoryType exifDirectory) {
        super(name, tag, FieldType.ANY, exifDirectory);
    }
}
