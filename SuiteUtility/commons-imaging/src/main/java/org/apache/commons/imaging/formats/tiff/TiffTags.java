// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.commons.imaging.formats.tiff;

import org.apache.commons.imaging.formats.tiff.constants.TiffDirectoryType;
import java.util.Iterator;
import java.util.HashMap;
import java.util.Collections;
import org.apache.commons.imaging.formats.tiff.constants.WangTagConstants;
import org.apache.commons.imaging.formats.tiff.constants.TiffTagConstants;
import org.apache.commons.imaging.formats.tiff.constants.TiffEpTagConstants;
import org.apache.commons.imaging.formats.tiff.constants.Tiff4TagConstants;
import org.apache.commons.imaging.formats.tiff.constants.Rfc2301TagConstants;
import org.apache.commons.imaging.formats.tiff.constants.OceScanjobTagConstants;
import org.apache.commons.imaging.formats.tiff.constants.MolecularDynamicsGelTagConstants;
import org.apache.commons.imaging.formats.tiff.constants.MicrosoftHdPhotoTagConstants;
import org.apache.commons.imaging.formats.tiff.constants.MicrosoftTagConstants;
import org.apache.commons.imaging.formats.tiff.constants.HylaFaxTagConstants;
import org.apache.commons.imaging.formats.tiff.constants.GpsTagConstants;
import org.apache.commons.imaging.formats.tiff.constants.GdalLibraryTagConstants;
import org.apache.commons.imaging.formats.tiff.constants.GeoTiffTagConstants;
import org.apache.commons.imaging.formats.tiff.constants.ExifTagConstants;
import org.apache.commons.imaging.formats.tiff.constants.DngTagConstants;
import org.apache.commons.imaging.formats.tiff.constants.DcfTagConstants;
import org.apache.commons.imaging.formats.tiff.constants.AliasSketchbookProTagConstants;
import org.apache.commons.imaging.formats.tiff.constants.AdobePhotoshopTagConstants;
import java.util.Collection;
import org.apache.commons.imaging.formats.tiff.constants.AdobePageMaker6TagConstants;
import java.util.ArrayList;
import java.util.Map;
import org.apache.commons.imaging.formats.tiff.taginfos.TagInfo;
import java.util.List;

final class TiffTags
{
    private static final List<TagInfo> ALL_TAGS;
    private static final Map<Integer, List<TagInfo>> ALL_TAG_MAP;
    private static final Map<Integer, Integer> TAG_COUNTS;
    
    private TiffTags() {
    }
    
    private static List<TagInfo> makeMergedTagList() {
        final ArrayList<TagInfo> result = new ArrayList<TagInfo>();
        result.addAll(AdobePageMaker6TagConstants.ALL_ADOBE_PAGEMAKER_6_TAGS);
        result.addAll(AdobePhotoshopTagConstants.ALL_ADOBE_PHOTOSHOP_TAGS);
        result.addAll(AliasSketchbookProTagConstants.ALL_ALIAS_SKETCHBOOK_PRO_TAGS);
        result.addAll(DcfTagConstants.ALL_DCF_TAGS);
        result.addAll(DngTagConstants.ALL_DNG_TAGS);
        result.addAll(ExifTagConstants.ALL_EXIF_TAGS);
        result.addAll(GeoTiffTagConstants.ALL_GEO_TIFF_TAGS);
        result.addAll(GdalLibraryTagConstants.ALL_GDAL_LIBRARY_TAGS);
        result.addAll(GpsTagConstants.ALL_GPS_TAGS);
        result.addAll(HylaFaxTagConstants.ALL_HYLAFAX_TAGS);
        result.addAll(MicrosoftTagConstants.ALL_MICROSOFT_TAGS);
        result.addAll(MicrosoftHdPhotoTagConstants.ALL_MICROSOFT_HD_PHOTO_TAGS);
        result.addAll(MolecularDynamicsGelTagConstants.ALL_MOLECULAR_DYNAMICS_GEL_TAGS);
        result.addAll(OceScanjobTagConstants.ALL_OCE_SCANJOB_TAGS);
        result.addAll(Rfc2301TagConstants.ALL_RFC_2301_TAGS);
        result.addAll(Tiff4TagConstants.ALL_TIFF_4_TAGS);
        result.addAll(TiffEpTagConstants.ALL_TIFF_EP_TAGS);
        result.addAll(TiffTagConstants.ALL_TIFF_TAGS);
        result.addAll(WangTagConstants.ALL_WANG_TAGS);
        return Collections.unmodifiableList((List<? extends TagInfo>)result);
    }
    
    private static Map<Integer, List<TagInfo>> makeTagMap(final List<TagInfo> tags) {
        final Map<Integer, List<TagInfo>> map = new HashMap<Integer, List<TagInfo>>();
        for (final TagInfo tag : tags) {
            List<TagInfo> tagList = map.get(tag.tag);
            if (tagList == null) {
                tagList = new ArrayList<TagInfo>();
                map.put(tag.tag, tagList);
            }
            tagList.add(tag);
        }
        return map;
    }
    
    private static Map<Integer, Integer> countTags(final List<TagInfo> tags) {
        final Map<Integer, Integer> map = new HashMap<Integer, Integer>();
        for (final TagInfo tag : tags) {
            final Integer count = map.get(tag.tag);
            if (count == null) {
                map.put(tag.tag, 1);
            }
            else {
                map.put(tag.tag, count + 1);
            }
        }
        return map;
    }
    
    static Integer getTagCount(final int tag) {
        return TiffTags.TAG_COUNTS.get(tag);
    }
    
    static TagInfo getTag(final int directoryType, final int tag) {
        final List<TagInfo> possibleMatches = TiffTags.ALL_TAG_MAP.get(tag);
        if (null == possibleMatches) {
            return TiffTagConstants.TIFF_TAG_UNKNOWN;
        }
        return getTag(directoryType, possibleMatches);
    }
    
    private static TagInfo getTag(final int directoryType, final List<TagInfo> possibleMatches) {
        if (possibleMatches.size() < 1) {
            return null;
        }
        for (final TagInfo tagInfo : possibleMatches) {
            if (tagInfo.directoryType == TiffDirectoryType.EXIF_DIRECTORY_UNKNOWN) {
                continue;
            }
            if (directoryType == tagInfo.directoryType.directoryType) {
                return tagInfo;
            }
        }
        for (final TagInfo tagInfo : possibleMatches) {
            if (tagInfo.directoryType == TiffDirectoryType.EXIF_DIRECTORY_UNKNOWN) {
                continue;
            }
            if (directoryType >= 0 && tagInfo.directoryType.isImageDirectory()) {
                return tagInfo;
            }
            if (directoryType < 0 && !tagInfo.directoryType.isImageDirectory()) {
                return tagInfo;
            }
        }
        for (final TagInfo tagInfo : possibleMatches) {
            if (tagInfo.directoryType == TiffDirectoryType.EXIF_DIRECTORY_UNKNOWN) {
                return tagInfo;
            }
        }
        return TiffTagConstants.TIFF_TAG_UNKNOWN;
    }
    
    static {
        ALL_TAGS = makeMergedTagList();
        ALL_TAG_MAP = makeTagMap(TiffTags.ALL_TAGS);
        TAG_COUNTS = countTags(TiffTags.ALL_TAGS);
    }
}
