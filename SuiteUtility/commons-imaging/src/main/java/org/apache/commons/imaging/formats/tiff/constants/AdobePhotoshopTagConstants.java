// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.commons.imaging.formats.tiff.constants;

import java.util.Collections;
import java.util.Arrays;
import org.apache.commons.imaging.formats.tiff.taginfos.TagInfo;
import java.util.List;
import org.apache.commons.imaging.formats.tiff.taginfos.TagInfoUndefineds;

public final class AdobePhotoshopTagConstants
{
    public static final TagInfoUndefineds EXIF_TAG_JPEGTABLES;
    public static final TagInfoUndefineds EXIF_TAG_IMAGE_SOURCE_DATA;
    public static final List<TagInfo> ALL_ADOBE_PHOTOSHOP_TAGS;
    
    private AdobePhotoshopTagConstants() {
    }
    
    static {
        EXIF_TAG_JPEGTABLES = new TagInfoUndefineds("JPEGTables", 347, -1, TiffDirectoryType.EXIF_DIRECTORY_UNKNOWN);
        EXIF_TAG_IMAGE_SOURCE_DATA = new TagInfoUndefineds("ImageSourceData", 37724, -1, TiffDirectoryType.EXIF_DIRECTORY_IFD0);
        ALL_ADOBE_PHOTOSHOP_TAGS = Collections.unmodifiableList((List<? extends TagInfo>)Arrays.asList(AdobePhotoshopTagConstants.EXIF_TAG_JPEGTABLES, AdobePhotoshopTagConstants.EXIF_TAG_IMAGE_SOURCE_DATA));
    }
}
