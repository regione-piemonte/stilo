// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.commons.imaging.formats.jpeg.iptc;

import java.util.Iterator;
import java.util.Collection;
import java.util.ArrayList;
import java.util.List;

public class PhotoshopApp13Data
{
    private final List<IptcRecord> records;
    private final List<IptcBlock> rawBlocks;
    
    public PhotoshopApp13Data(final List<IptcRecord> records, final List<IptcBlock> rawBlocks) {
        this.rawBlocks = rawBlocks;
        this.records = records;
    }
    
    public List<IptcRecord> getRecords() {
        return new ArrayList<IptcRecord>(this.records);
    }
    
    public List<IptcBlock> getRawBlocks() {
        return new ArrayList<IptcBlock>(this.rawBlocks);
    }
    
    public List<IptcBlock> getNonIptcBlocks() {
        final List<IptcBlock> result = new ArrayList<IptcBlock>();
        for (final IptcBlock block : this.rawBlocks) {
            if (!block.isIPTCBlock()) {
                result.add(block);
            }
        }
        return result;
    }
}
