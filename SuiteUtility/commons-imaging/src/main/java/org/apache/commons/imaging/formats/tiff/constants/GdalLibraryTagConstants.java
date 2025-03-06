// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.commons.imaging.formats.tiff.constants;

import java.util.Collections;
import java.util.Arrays;
import org.apache.commons.imaging.formats.tiff.taginfos.TagInfo;
import java.util.List;
import org.apache.commons.imaging.formats.tiff.taginfos.TagInfoAscii;

public final class GdalLibraryTagConstants
{
    public static final TagInfoAscii EXIF_TAG_GDAL_METADATA;
    public static final TagInfoAscii EXIF_TAG_GDAL_NO_DATA;
    public static final List<TagInfo> ALL_GDAL_LIBRARY_TAGS;
    
    private GdalLibraryTagConstants() {
    }
    
    static {
        EXIF_TAG_GDAL_METADATA = new TagInfoAscii("GDALMetadata", 42112, -1, TiffDirectoryType.EXIF_DIRECTORY_UNKNOWN);
        EXIF_TAG_GDAL_NO_DATA = new TagInfoAscii("GDALNoData", 42113, -1, TiffDirectoryType.EXIF_DIRECTORY_UNKNOWN);
        ALL_GDAL_LIBRARY_TAGS = Collections.unmodifiableList((List<? extends TagInfo>)Arrays.asList(GdalLibraryTagConstants.EXIF_TAG_GDAL_METADATA, GdalLibraryTagConstants.EXIF_TAG_GDAL_NO_DATA));
    }
}
