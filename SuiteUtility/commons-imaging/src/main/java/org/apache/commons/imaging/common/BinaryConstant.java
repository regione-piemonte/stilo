// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.commons.imaging.common;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;

public class BinaryConstant
{
    private final byte[] value;
    
    public BinaryConstant(final byte[] value) {
        this.value = value.clone();
    }
    
    public BinaryConstant clone() throws CloneNotSupportedException {
        return (BinaryConstant)super.clone();
    }
    
    @Override
    public boolean equals(final Object obj) {
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof BinaryConstant)) {
            return false;
        }
        final BinaryConstant other = (BinaryConstant)obj;
        return this.equals(other.value);
    }
    
    public boolean equals(final byte[] bytes) {
        return Arrays.equals(this.value, bytes);
    }
    
    public boolean equals(final byte[] bytes, final int offset, final int length) {
        if (this.value.length != length) {
            return false;
        }
        for (int i = 0; i < length; ++i) {
            if (this.value[i] != bytes[offset + i]) {
                return false;
            }
        }
        return true;
    }
    
    @Override
    public int hashCode() {
        return Arrays.hashCode(this.value);
    }
    
    public byte get(final int i) {
        return this.value[i];
    }
    
    public int size() {
        return this.value.length;
    }
    
    public byte[] toByteArray() {
        return this.value.clone();
    }
    
    public void writeTo(final OutputStream os) throws IOException {
        for (final byte element : this.value) {
            os.write(element);
        }
    }
}
