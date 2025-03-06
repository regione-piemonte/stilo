// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.commons.imaging.formats.jpeg;

import org.apache.commons.imaging.internal.Debug;
import java.util.Iterator;
import java.util.List;
import org.apache.commons.imaging.formats.jpeg.iptc.IptcTypes;
import java.util.Comparator;
import java.util.Collections;
import org.apache.commons.imaging.formats.jpeg.iptc.IptcRecord;
import org.apache.commons.imaging.formats.jpeg.iptc.PhotoshopApp13Data;
import org.apache.commons.imaging.common.GenericImageMetadata;

public class JpegPhotoshopMetadata extends GenericImageMetadata
{
    public final PhotoshopApp13Data photoshopApp13Data;
    
    public JpegPhotoshopMetadata(final PhotoshopApp13Data photoshopApp13Data) {
        this.photoshopApp13Data = photoshopApp13Data;
        final List<IptcRecord> records = photoshopApp13Data.getRecords();
        Collections.sort(records, IptcRecord.COMPARATOR);
        for (final IptcRecord element : records) {
            if (element.iptcType != IptcTypes.RECORD_VERSION) {
                this.add(element.getIptcTypeName(), element.getValue());
            }
        }
    }
    
    public void dump() {
        Debug.debug(this.toString());
    }
}
