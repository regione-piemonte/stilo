// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.commons.imaging.formats.tiff.constants;

import java.util.Collections;
import java.util.Arrays;
import org.apache.commons.imaging.formats.tiff.taginfos.TagInfo;
import java.util.List;
import org.apache.commons.imaging.formats.tiff.taginfos.TagInfoShort;

public final class Tiff4TagConstants
{
    public static final TagInfoShort TIFF_TAG_COLOR_RESPONSE_UNIT;
    public static final int COLOR_RESPONSE_UNIT_VALUE_0_1 = 1;
    public static final int COLOR_RESPONSE_UNIT_VALUE_0_01 = 2;
    public static final int COLOR_RESPONSE_UNIT_VALUE_0_001 = 3;
    public static final int COLOR_RESPONSE_UNIT_VALUE_0_0001 = 4;
    public static final int COLOR_RESPONSE_UNIT_VALUE_0_00001 = 5;
    public static final List<TagInfo> ALL_TIFF_4_TAGS;
    
    private Tiff4TagConstants() {
    }
    
    static {
        TIFF_TAG_COLOR_RESPONSE_UNIT = new TagInfoShort("ColorResponseUnit", 300, TiffDirectoryType.TIFF_DIRECTORY_ROOT);
        ALL_TIFF_4_TAGS = Collections.unmodifiableList((List<? extends TagInfo>)Arrays.asList(Tiff4TagConstants.TIFF_TAG_COLOR_RESPONSE_UNIT));
    }
}
