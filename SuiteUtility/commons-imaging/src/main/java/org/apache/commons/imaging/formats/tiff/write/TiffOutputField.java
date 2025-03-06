// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.commons.imaging.formats.tiff.write;

import java.util.Arrays;
import java.io.IOException;
import org.apache.commons.imaging.common.BinaryOutputStream;
import org.apache.commons.imaging.ImageWriteException;
import java.nio.ByteOrder;
import org.apache.commons.imaging.formats.tiff.fieldtypes.FieldType;
import org.apache.commons.imaging.formats.tiff.taginfos.TagInfo;

public class TiffOutputField
{
    public final int tag;
    public final TagInfo tagInfo;
    public final FieldType fieldType;
    public final int count;
    private byte[] bytes;
    private final TiffOutputItem.Value separateValueItem;
    private int sortHint;
    private static final String NEWLINE;
    
    public TiffOutputField(final TagInfo tagInfo, final FieldType tagtype, final int count, final byte[] bytes) {
        this(tagInfo.tag, tagInfo, tagtype, count, bytes);
    }
    
    public TiffOutputField(final int tag, final TagInfo tagInfo, final FieldType fieldType, final int count, final byte[] bytes) {
        this.sortHint = -1;
        this.tag = tag;
        this.tagInfo = tagInfo;
        this.fieldType = fieldType;
        this.count = count;
        this.bytes = bytes;
        if (this.isLocalValue()) {
            this.separateValueItem = null;
        }
        else {
            final String name = "Field Seperate value (" + tagInfo.getDescription() + ")";
            this.separateValueItem = new TiffOutputItem.Value(name, bytes);
        }
    }
    
    protected static TiffOutputField createOffsetField(final TagInfo tagInfo, final ByteOrder byteOrder) throws ImageWriteException {
        return new TiffOutputField(tagInfo, FieldType.LONG, 1, FieldType.LONG.writeData(0, byteOrder));
    }
    
    protected void writeField(final BinaryOutputStream bos) throws IOException, ImageWriteException {
        bos.write2Bytes(this.tag);
        bos.write2Bytes(this.fieldType.getType());
        bos.write4Bytes(this.count);
        if (this.isLocalValue()) {
            if (this.separateValueItem != null) {
                throw new ImageWriteException("Unexpected separate value item.");
            }
            if (this.bytes.length > 4) {
                throw new ImageWriteException("Local value has invalid length: " + this.bytes.length);
            }
            bos.write(this.bytes);
            for (int remainder = 4 - this.bytes.length, i = 0; i < remainder; ++i) {
                bos.write(0);
            }
        }
        else {
            if (this.separateValueItem == null) {
                throw new ImageWriteException("Missing separate value item.");
            }
            bos.write4Bytes((int)this.separateValueItem.getOffset());
        }
    }
    
    protected TiffOutputItem getSeperateValue() {
        return this.separateValueItem;
    }
    
    protected final boolean isLocalValue() {
        return this.bytes.length <= 4;
    }
    
    public boolean bytesEqual(final byte[] data) {
        return Arrays.equals(this.bytes, data);
    }
    
    protected void setData(final byte[] bytes) throws ImageWriteException {
        if (this.bytes.length != bytes.length) {
            throw new ImageWriteException("Cannot change size of value.");
        }
        this.bytes = bytes;
        if (this.separateValueItem != null) {
            this.separateValueItem.updateValue(bytes);
        }
    }
    
    @Override
    public String toString() {
        return this.toString(null);
    }
    
    public String toString(String prefix) {
        if (prefix == null) {
            prefix = "";
        }
        final StringBuilder result = new StringBuilder();
        result.append(prefix);
        result.append(this.tagInfo);
        result.append(TiffOutputField.NEWLINE);
        result.append(prefix);
        result.append("count: ");
        result.append(this.count);
        result.append(TiffOutputField.NEWLINE);
        result.append(prefix);
        result.append(this.fieldType);
        result.append(TiffOutputField.NEWLINE);
        return result.toString();
    }
    
    public int getSortHint() {
        return this.sortHint;
    }
    
    public void setSortHint(final int sortHint) {
        this.sortHint = sortHint;
    }
    
    static {
        NEWLINE = System.getProperty("line.separator");
    }
}
