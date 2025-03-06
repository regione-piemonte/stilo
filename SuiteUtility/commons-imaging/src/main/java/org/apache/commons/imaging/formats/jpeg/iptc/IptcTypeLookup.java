// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.commons.imaging.formats.jpeg.iptc;

import java.util.HashMap;
import java.util.Map;

public final class IptcTypeLookup
{
    private static final Map<Integer, IptcType> IPTC_TYPE_MAP;
    
    private IptcTypeLookup() {
    }
    
    public static IptcType getIptcType(final int type) {
        if (!IptcTypeLookup.IPTC_TYPE_MAP.containsKey(type)) {
            return IptcTypes.getUnknown(type);
        }
        return IptcTypeLookup.IPTC_TYPE_MAP.get(type);
    }
    
    static {
        IPTC_TYPE_MAP = new HashMap<Integer, IptcType>();
        for (final IptcType iptcType : IptcTypes.values()) {
            IptcTypeLookup.IPTC_TYPE_MAP.put(iptcType.getType(), iptcType);
        }
    }
}
