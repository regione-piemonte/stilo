// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.commons.imaging.formats.tiff.constants;

import java.util.Collections;
import java.util.Arrays;
import org.apache.commons.imaging.formats.tiff.taginfos.TagInfo;
import java.util.List;
import org.apache.commons.imaging.formats.tiff.taginfos.TagInfoAscii;
import org.apache.commons.imaging.formats.tiff.taginfos.TagInfoLong;
import org.apache.commons.imaging.formats.tiff.taginfos.TagInfoRational;
import org.apache.commons.imaging.formats.tiff.taginfos.TagInfoRationals;
import org.apache.commons.imaging.formats.tiff.taginfos.TagInfoSShorts;
import org.apache.commons.imaging.formats.tiff.taginfos.TagInfoShort;
import org.apache.commons.imaging.formats.tiff.taginfos.TagInfoUndefineds;
import org.apache.commons.imaging.formats.tiff.taginfos.TagInfoAsciiOrRational;
import org.apache.commons.imaging.formats.tiff.taginfos.TagInfoBytes;
import org.apache.commons.imaging.formats.tiff.taginfos.TagInfoShorts;

public final class TiffEpTagConstants
{
    public static final TagInfoShorts EXIF_TAG_CFAREPEAT_PATTERN_DIM;
    public static final TagInfoBytes EXIF_TAG_CFAPATTERN_2;
    public static final TagInfoAsciiOrRational EXIF_TAG_BATTERY_LEVEL;
    public static final TagInfoUndefineds EXIF_TAG_INTER_COLOR_PROFILE;
    public static final TagInfoShort EXIF_TAG_INTERLACE;
    public static final TagInfoSShorts EXIF_TAG_TIME_ZONE_OFFSET;
    public static final TagInfoShort EXIF_TAG_SELF_TIMER_MODE;
    public static final TagInfoRationals EXIF_TAG_FLASH_ENERGY;
    public static final TagInfoUndefineds EXIF_TAG_SPATIAL_FREQUENCY_RESPONSE_1;
    public static final TagInfoUndefineds EXIF_TAG_NOISE_1;
    public static final TagInfoRational EXIF_TAG_FOCAL_PLANE_XRESOLUTION;
    public static final TagInfoRational EXIF_TAG_FOCAL_PLANE_YRESOLUTION;
    public static final TagInfoShort EXIF_TAG_FOCAL_PLANE_RESOLUTION_UNIT;
    public static final int FOCAL_PLANE_RESOLUTION_UNIT_VALUE_NONE = 1;
    public static final int FOCAL_PLANE_RESOLUTION_UNIT_VALUE_INCHES = 2;
    public static final int FOCAL_PLANE_RESOLUTION_UNIT_VALUE_CM = 3;
    public static final int FOCAL_PLANE_RESOLUTION_UNIT_VALUE_MM = 4;
    public static final int FOCAL_PLANE_RESOLUTION_UNIT_VALUE_UM = 5;
    public static final TagInfoLong EXIF_TAG_IMAGE_NUMBER_EXIF_IFD;
    public static final TagInfoAscii EXIF_TAG_SECURITY_CLASSIFICATION_EXIF_IFD;
    public static final TagInfoAscii EXIF_TAG_IMAGE_HISTORY_EXIF_IFD;
    public static final TagInfoRationals EXIF_TAG_EXPOSURE_INDEX;
    public static final TagInfoBytes EXIF_TAG_TIFF_EPSTANDARD_ID_1;
    public static final TagInfoShort EXIF_TAG_SENSING_METHOD;
    public static final int SENSING_METHOD_VALUE_MONOCHROME_AREA = 1;
    public static final int SENSING_METHOD_VALUE_ONE_CHIP_COLOR_AREA = 2;
    public static final int SENSING_METHOD_VALUE_TWO_CHIP_COLOR_AREA = 3;
    public static final int SENSING_METHOD_VALUE_THREE_CHIP_COLOR_AREA = 4;
    public static final int SENSING_METHOD_VALUE_COLOR_SEQUENTIAL_AREA = 5;
    public static final int SENSING_METHOD_VALUE_MONOCHROME_LINEAR = 6;
    public static final int SENSING_METHOD_VALUE_TRILINEAR = 7;
    public static final int SENSING_METHOD_VALUE_COLOR_SEQUENTIAL_LINEAR = 8;
    public static final List<TagInfo> ALL_TIFF_EP_TAGS;
    
    private TiffEpTagConstants() {
    }
    
    static {
        EXIF_TAG_CFAREPEAT_PATTERN_DIM = new TagInfoShorts("CFARepeatPatternDim", 33421, 2, TiffDirectoryType.EXIF_DIRECTORY_UNKNOWN);
        EXIF_TAG_CFAPATTERN_2 = new TagInfoBytes("CFAPattern2", 33422, -1, TiffDirectoryType.EXIF_DIRECTORY_UNKNOWN);
        EXIF_TAG_BATTERY_LEVEL = new TagInfoAsciiOrRational("BatteryLevel", 33423, -1, TiffDirectoryType.TIFF_DIRECTORY_ROOT);
        EXIF_TAG_INTER_COLOR_PROFILE = new TagInfoUndefineds("InterColorProfile", 34675, -1, TiffDirectoryType.EXIF_DIRECTORY_UNKNOWN);
        EXIF_TAG_INTERLACE = new TagInfoShort("Interlace", 34857, TiffDirectoryType.EXIF_DIRECTORY_UNKNOWN);
        EXIF_TAG_TIME_ZONE_OFFSET = new TagInfoSShorts("TimeZoneOffset", 34858, -1, TiffDirectoryType.EXIF_DIRECTORY_EXIF_IFD);
        EXIF_TAG_SELF_TIMER_MODE = new TagInfoShort("SelfTimerMode", 34859, TiffDirectoryType.EXIF_DIRECTORY_EXIF_IFD);
        EXIF_TAG_FLASH_ENERGY = new TagInfoRationals("FlashEnergy", 37387, -1, TiffDirectoryType.EXIF_DIRECTORY_UNKNOWN);
        EXIF_TAG_SPATIAL_FREQUENCY_RESPONSE_1 = new TagInfoUndefineds("SpatialFrequencyResponse", 37388, -1, TiffDirectoryType.EXIF_DIRECTORY_UNKNOWN);
        EXIF_TAG_NOISE_1 = new TagInfoUndefineds("Noise", 37389, -1, TiffDirectoryType.EXIF_DIRECTORY_UNKNOWN);
        EXIF_TAG_FOCAL_PLANE_XRESOLUTION = new TagInfoRational("FocalPlaneXResolution", 37390, TiffDirectoryType.EXIF_DIRECTORY_UNKNOWN);
        EXIF_TAG_FOCAL_PLANE_YRESOLUTION = new TagInfoRational("FocalPlaneYResolution", 37391, TiffDirectoryType.EXIF_DIRECTORY_UNKNOWN);
        EXIF_TAG_FOCAL_PLANE_RESOLUTION_UNIT = new TagInfoShort("FocalPlaneResolutionUnit", 37392, TiffDirectoryType.EXIF_DIRECTORY_UNKNOWN);
        EXIF_TAG_IMAGE_NUMBER_EXIF_IFD = new TagInfoLong("ImageNumber", 37393, TiffDirectoryType.EXIF_DIRECTORY_EXIF_IFD);
        EXIF_TAG_SECURITY_CLASSIFICATION_EXIF_IFD = new TagInfoAscii("SecurityClassification", 37394, -1, TiffDirectoryType.EXIF_DIRECTORY_EXIF_IFD);
        EXIF_TAG_IMAGE_HISTORY_EXIF_IFD = new TagInfoAscii("ImageHistory", 37395, -1, TiffDirectoryType.EXIF_DIRECTORY_EXIF_IFD);
        EXIF_TAG_EXPOSURE_INDEX = new TagInfoRationals("ExposureIndex", 37397, -1, TiffDirectoryType.EXIF_DIRECTORY_UNKNOWN);
        EXIF_TAG_TIFF_EPSTANDARD_ID_1 = new TagInfoBytes("TIFF/EPStandardID", 37398, 4, TiffDirectoryType.EXIF_DIRECTORY_UNKNOWN);
        EXIF_TAG_SENSING_METHOD = new TagInfoShort("SensingMethod", 37399, TiffDirectoryType.EXIF_DIRECTORY_UNKNOWN);
        ALL_TIFF_EP_TAGS = Collections.unmodifiableList((List<? extends TagInfo>)Arrays.asList(TiffEpTagConstants.EXIF_TAG_CFAREPEAT_PATTERN_DIM, TiffEpTagConstants.EXIF_TAG_CFAPATTERN_2, TiffEpTagConstants.EXIF_TAG_BATTERY_LEVEL, TiffEpTagConstants.EXIF_TAG_INTER_COLOR_PROFILE, TiffEpTagConstants.EXIF_TAG_INTERLACE, TiffEpTagConstants.EXIF_TAG_TIME_ZONE_OFFSET, TiffEpTagConstants.EXIF_TAG_SELF_TIMER_MODE, TiffEpTagConstants.EXIF_TAG_FLASH_ENERGY, TiffEpTagConstants.EXIF_TAG_SPATIAL_FREQUENCY_RESPONSE_1, TiffEpTagConstants.EXIF_TAG_NOISE_1, TiffEpTagConstants.EXIF_TAG_FOCAL_PLANE_XRESOLUTION, TiffEpTagConstants.EXIF_TAG_FOCAL_PLANE_YRESOLUTION, TiffEpTagConstants.EXIF_TAG_FOCAL_PLANE_RESOLUTION_UNIT, TiffEpTagConstants.EXIF_TAG_IMAGE_NUMBER_EXIF_IFD, TiffEpTagConstants.EXIF_TAG_SECURITY_CLASSIFICATION_EXIF_IFD, TiffEpTagConstants.EXIF_TAG_IMAGE_HISTORY_EXIF_IFD, TiffEpTagConstants.EXIF_TAG_EXPOSURE_INDEX, TiffEpTagConstants.EXIF_TAG_TIFF_EPSTANDARD_ID_1, TiffEpTagConstants.EXIF_TAG_SENSING_METHOD));
    }
}
