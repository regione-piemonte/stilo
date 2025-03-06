// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.commons.imaging.formats.tiff.taginfos;

import org.apache.commons.imaging.formats.tiff.fieldtypes.FieldType;
import org.apache.commons.imaging.formats.tiff.constants.TiffDirectoryType;

public class TagInfoAsciiOrRational extends TagInfo
{
    public TagInfoAsciiOrRational(final String name, final int tag, final int length, final TiffDirectoryType directoryType) {
        super(name, tag, FieldType.ASCII_OR_RATIONAL, length, directoryType, false);
    }
}
