// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.commons.imaging.formats.tiff.constants;

import java.util.Collections;
import java.util.Arrays;
import org.apache.commons.imaging.formats.tiff.taginfos.TagInfo;
import java.util.List;
import org.apache.commons.imaging.formats.tiff.taginfos.TagInfoAscii;
import org.apache.commons.imaging.formats.tiff.taginfos.TagInfoShort;
import org.apache.commons.imaging.formats.tiff.taginfos.TagInfoLong;
import org.apache.commons.imaging.formats.tiff.taginfos.TagInfoBytes;
import org.apache.commons.imaging.formats.tiff.taginfos.TagInfoLongOrIFD;

public final class AdobePageMaker6TagConstants
{
    public static final TagInfoLongOrIFD TIFF_TAG_SUB_IFD;
    public static final TagInfoBytes TIFF_TAG_CLIP_PATH;
    public static final TagInfoLong TIFF_TAG_XCLIP_PATH_UNITS;
    public static final TagInfoLong TIFF_TAG_YCLIP_PATH_UNITS;
    public static final TagInfoShort TIFF_TAG_INDEXED;
    public static final int INDEXED_VALUE_NOT_INDEXED = 0;
    public static final int INDEXED_VALUE_INDEXED = 1;
    public static final TagInfoShort TIFF_TAG_OPIPROXY;
    public static final int OPIPROXY_VALUE_HIGHER_RESOLUTION_IMAGE_DOES_NOT_EXIST = 0;
    public static final int OPIPROXY_VALUE_HIGHER_RESOLUTION_IMAGE_EXISTS = 1;
    public static final TagInfoAscii TIFF_TAG_IMAGE_ID;
    public static final List<TagInfo> ALL_ADOBE_PAGEMAKER_6_TAGS;
    
    private AdobePageMaker6TagConstants() {
    }
    
    static {
        TIFF_TAG_SUB_IFD = new TagInfoLongOrIFD("SubIFDs", 330, -1, TiffDirectoryType.EXIF_DIRECTORY_UNKNOWN, true);
        TIFF_TAG_CLIP_PATH = new TagInfoBytes("ClipPath", 343, -1, TiffDirectoryType.EXIF_DIRECTORY_UNKNOWN);
        TIFF_TAG_XCLIP_PATH_UNITS = new TagInfoLong("XClipPathUnits", 344, TiffDirectoryType.EXIF_DIRECTORY_UNKNOWN);
        TIFF_TAG_YCLIP_PATH_UNITS = new TagInfoLong("YClipPathUnits", 345, TiffDirectoryType.EXIF_DIRECTORY_UNKNOWN);
        TIFF_TAG_INDEXED = new TagInfoShort("Indexed", 346, TiffDirectoryType.EXIF_DIRECTORY_UNKNOWN);
        TIFF_TAG_OPIPROXY = new TagInfoShort("OPIProxy", 351, TiffDirectoryType.EXIF_DIRECTORY_UNKNOWN);
        TIFF_TAG_IMAGE_ID = new TagInfoAscii("ImageID", 32781, -1, TiffDirectoryType.EXIF_DIRECTORY_UNKNOWN);
        ALL_ADOBE_PAGEMAKER_6_TAGS = Collections.unmodifiableList((List<? extends TagInfo>)Arrays.asList(AdobePageMaker6TagConstants.TIFF_TAG_SUB_IFD, AdobePageMaker6TagConstants.TIFF_TAG_CLIP_PATH, AdobePageMaker6TagConstants.TIFF_TAG_XCLIP_PATH_UNITS, AdobePageMaker6TagConstants.TIFF_TAG_YCLIP_PATH_UNITS, AdobePageMaker6TagConstants.TIFF_TAG_INDEXED, AdobePageMaker6TagConstants.TIFF_TAG_OPIPROXY, AdobePageMaker6TagConstants.TIFF_TAG_IMAGE_ID));
    }
}
