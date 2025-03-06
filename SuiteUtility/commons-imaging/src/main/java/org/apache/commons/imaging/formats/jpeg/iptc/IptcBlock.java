// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.commons.imaging.formats.jpeg.iptc;

public class IptcBlock
{
    public final int blockType;
    final byte[] blockNameBytes;
    final byte[] blockData;
    
    public IptcBlock(final int blockType, final byte[] blockNameBytes, final byte[] blockData) {
        this.blockData = blockData;
        this.blockNameBytes = blockNameBytes;
        this.blockType = blockType;
    }
    
    public boolean isIPTCBlock() {
        return this.blockType == 1028;
    }
}
