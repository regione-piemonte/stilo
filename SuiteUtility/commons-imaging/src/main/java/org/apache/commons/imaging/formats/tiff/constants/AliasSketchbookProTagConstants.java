// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.commons.imaging.formats.tiff.constants;

import java.util.Collections;
import java.util.Arrays;
import org.apache.commons.imaging.formats.tiff.taginfos.TagInfo;
import java.util.List;
import org.apache.commons.imaging.formats.tiff.taginfos.TagInfoAscii;

public final class AliasSketchbookProTagConstants
{
    public static final TagInfoAscii EXIF_TAG_ALIAS_LAYER_METADATA;
    public static final List<TagInfo> ALL_ALIAS_SKETCHBOOK_PRO_TAGS;
    
    private AliasSketchbookProTagConstants() {
    }
    
    static {
        EXIF_TAG_ALIAS_LAYER_METADATA = new TagInfoAscii("Alias Layer Metadata", 50784, -1, TiffDirectoryType.EXIF_DIRECTORY_UNKNOWN);
        ALL_ALIAS_SKETCHBOOK_PRO_TAGS = Collections.unmodifiableList((List<? extends TagInfo>)Arrays.asList(AliasSketchbookProTagConstants.EXIF_TAG_ALIAS_LAYER_METADATA));
    }
}
