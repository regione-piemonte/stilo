// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.commons.imaging.formats.tiff.taginfos;

import org.apache.commons.imaging.formats.tiff.fieldtypes.FieldType;
import org.apache.commons.imaging.formats.tiff.constants.TiffDirectoryType;

public class TagInfoUnknowns extends TagInfoBytes
{
    public TagInfoUnknowns(final String name, final int tag, final int length, final TiffDirectoryType exifDirectory) {
        super(name, tag, FieldType.ANY, length, exifDirectory);
    }
}
