// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.commons.imaging.formats.tiff.constants;

import java.util.Collections;
import java.util.Arrays;
import org.apache.commons.imaging.formats.tiff.taginfos.TagInfo;
import java.util.List;
import org.apache.commons.imaging.formats.tiff.taginfos.TagInfoBytes;

public final class WangTagConstants
{
    public static final TagInfoBytes EXIF_TAG_WANG_ANNOTATION;
    public static final List<TagInfo> ALL_WANG_TAGS;
    
    private WangTagConstants() {
    }
    
    static {
        EXIF_TAG_WANG_ANNOTATION = new TagInfoBytes("WangAnnotation", 32932, -1, TiffDirectoryType.EXIF_DIRECTORY_UNKNOWN);
        ALL_WANG_TAGS = Collections.unmodifiableList((List<? extends TagInfo>)Arrays.asList(WangTagConstants.EXIF_TAG_WANG_ANNOTATION));
    }
}
