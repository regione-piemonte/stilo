// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.ext.awt.image.spi;

import java.io.IOException;
import java.io.StreamCorruptedException;
import java.io.InputStream;

public abstract class MagicNumberRegistryEntry extends AbstractRegistryEntry implements StreamRegistryEntry
{
    public static final float PRIORITY = 1000.0f;
    MagicNumber[] magicNumbers;
    
    public MagicNumberRegistryEntry(final String s, final float n, final String s2, final String s3, final int n2, final byte[] array) {
        super(s, n, s2, s3);
        (this.magicNumbers = new MagicNumber[1])[0] = new MagicNumber(n2, array);
    }
    
    public MagicNumberRegistryEntry(final String s, final String s2, final String s3, final int n, final byte[] array) {
        this(s, 1000.0f, s2, s3, n, array);
    }
    
    public MagicNumberRegistryEntry(final String s, final float n, final String s2, final String s3, final MagicNumber[] magicNumbers) {
        super(s, n, s2, s3);
        this.magicNumbers = magicNumbers;
    }
    
    public MagicNumberRegistryEntry(final String s, final String s2, final String s3, final MagicNumber[] array) {
        this(s, 1000.0f, s2, s3, array);
    }
    
    public MagicNumberRegistryEntry(final String s, final float n, final String[] array, final String[] array2, final int n2, final byte[] array3) {
        super(s, n, array, array2);
        (this.magicNumbers = new MagicNumber[1])[0] = new MagicNumber(n2, array3);
    }
    
    public MagicNumberRegistryEntry(final String s, final String[] array, final String[] array2, final int n, final byte[] array3) {
        this(s, 1000.0f, array, array2, n, array3);
    }
    
    public MagicNumberRegistryEntry(final String s, final float n, final String[] array, final String[] array2, final MagicNumber[] magicNumbers) {
        super(s, n, array, array2);
        this.magicNumbers = magicNumbers;
    }
    
    public MagicNumberRegistryEntry(final String s, final String[] array, final String[] array2, final MagicNumber[] array3) {
        this(s, 1000.0f, array, array2, array3);
    }
    
    public MagicNumberRegistryEntry(final String s, final String[] array, final String[] array2, final MagicNumber[] magicNumbers, final float n) {
        super(s, n, array, array2);
        this.magicNumbers = magicNumbers;
    }
    
    public int getReadlimit() {
        int n = 0;
        for (int i = 0; i < this.magicNumbers.length; ++i) {
            final int readlimit = this.magicNumbers[i].getReadlimit();
            if (readlimit > n) {
                n = readlimit;
            }
        }
        return n;
    }
    
    public boolean isCompatibleStream(final InputStream inputStream) throws StreamCorruptedException {
        for (int i = 0; i < this.magicNumbers.length; ++i) {
            if (this.magicNumbers[i].isMatch(inputStream)) {
                return true;
            }
        }
        return false;
    }
    
    public static class MagicNumber
    {
        int offset;
        byte[] magicNumber;
        byte[] buffer;
        
        public MagicNumber(final int offset, final byte[] array) {
            this.offset = offset;
            this.magicNumber = array.clone();
            this.buffer = new byte[array.length];
        }
        
        int getReadlimit() {
            return this.offset + this.magicNumber.length;
        }
        
        boolean isMatch(final InputStream inputStream) throws StreamCorruptedException {
            int i = 0;
            inputStream.mark(this.getReadlimit());
            try {
                while (i < this.offset) {
                    final int n = (int)inputStream.skip(this.offset - i);
                    if (n == -1) {
                        return false;
                    }
                    i += n;
                }
                int read;
                for (int j = 0; j < this.buffer.length; j += read) {
                    read = inputStream.read(this.buffer, j, this.buffer.length - j);
                    if (read == -1) {
                        return false;
                    }
                }
                for (int k = 0; k < this.magicNumber.length; ++k) {
                    if (this.magicNumber[k] != this.buffer[k]) {
                        return false;
                    }
                }
            }
            catch (IOException ex2) {
                return false;
            }
            finally {
                try {
                    inputStream.reset();
                }
                catch (IOException ex) {
                    throw new StreamCorruptedException(ex.getMessage());
                }
            }
            return true;
        }
    }
}
