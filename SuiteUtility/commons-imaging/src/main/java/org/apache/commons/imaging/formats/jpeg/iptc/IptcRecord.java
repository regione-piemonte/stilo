// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.commons.imaging.formats.jpeg.iptc;

import java.util.Comparator;

public class IptcRecord
{
    public final IptcType iptcType;
    private final String value;
    public static final Comparator<IptcRecord> COMPARATOR;
    
    public IptcRecord(final IptcType iptcType, final String value) {
        this.iptcType = iptcType;
        this.value = value;
    }
    
    public String getValue() {
        return this.value;
    }
    
    public String getIptcTypeName() {
        return this.iptcType.getName();
    }
    
    static {
        COMPARATOR = new Comparator<IptcRecord>() {
            @Override
            public int compare(final IptcRecord e1, final IptcRecord e2) {
                return e1.iptcType.getType() - e2.iptcType.getType();
            }
        };
    }
}
