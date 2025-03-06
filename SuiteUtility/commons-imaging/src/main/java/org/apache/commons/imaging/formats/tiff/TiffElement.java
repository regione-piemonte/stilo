// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.commons.imaging.formats.tiff;

import java.util.Comparator;

public abstract class TiffElement
{
    public final long offset;
    public final int length;
    public static final Comparator<TiffElement> COMPARATOR;
    
    public TiffElement(final long offset, final int length) {
        this.offset = offset;
        this.length = length;
    }
    
    public abstract String getElementDescription();
    
    static {
        COMPARATOR = new Comparator<TiffElement>() {
            @Override
            public int compare(final TiffElement e1, final TiffElement e2) {
                if (e1.offset < e2.offset) {
                    return -1;
                }
                if (e1.offset > e2.offset) {
                    return 1;
                }
                return 0;
            }
        };
    }
    
    public abstract static class DataElement extends TiffElement
    {
        private final byte[] data;
        
        public DataElement(final long offset, final int length, final byte[] data) {
            super(offset, length);
            this.data = data;
        }
        
        public byte[] getData() {
            return this.data;
        }
        
        public int getDataLength() {
            return this.data.length;
        }
    }
    
    public static final class Stub extends TiffElement
    {
        public Stub(final long offset, final int length) {
            super(offset, length);
        }
        
        @Override
        public String getElementDescription() {
            return "Element, offset: " + this.offset + ", length: " + this.length + ", last: " + (this.offset + this.length);
        }
    }
}
