// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.pdfbox.jbig2.decoder.huffman;

import java.io.IOException;
import javax.imageio.stream.ImageInputStream;
import java.util.Iterator;
import java.util.List;

public abstract class HuffmanTable
{
    private InternalNode rootNode;
    
    public HuffmanTable() {
        this.rootNode = new InternalNode();
    }
    
    public void initTree(final List<Code> list) {
        this.preprocessCodes(list);
        final Iterator<Code> iterator = list.iterator();
        while (iterator.hasNext()) {
            this.rootNode.append(iterator.next());
        }
    }
    
    public long decode(final ImageInputStream imageInputStream) throws IOException {
        return this.rootNode.decode(imageInputStream);
    }
    
    @Override
    public String toString() {
        return this.rootNode + "\n";
    }
    
    public static String codeTableToString(final List<Code> list) {
        final StringBuilder sb = new StringBuilder();
        final Iterator<Code> iterator = list.iterator();
        while (iterator.hasNext()) {
            sb.append(iterator.next().toString()).append("\n");
        }
        return sb.toString();
    }
    
    private void preprocessCodes(final List<Code> list) {
        int max = 0;
        final Iterator<Code> iterator = list.iterator();
        while (iterator.hasNext()) {
            max = Math.max(max, iterator.next().prefixLength);
        }
        final int[] array = new int[max + 1];
        for (final Code code : list) {
            final int[] array2 = array;
            final int prefixLength = code.prefixLength;
            ++array2[prefixLength];
        }
        final int[] array3 = new int[array.length + 1];
        array[0] = 0;
        for (int i = 1; i <= array.length; ++i) {
            array3[i] = array3[i - 1] + array[i - 1] << 1;
            int code2 = array3[i];
            for (final Code code3 : list) {
                if (code3.prefixLength == i) {
                    code3.code = code2;
                    ++code2;
                }
            }
        }
    }
    
    public static class Code
    {
        final int prefixLength;
        final int rangeLength;
        final int rangeLow;
        final boolean isLowerRange;
        int code;
        
        public Code(final int prefixLength, final int rangeLength, final int rangeLow, final boolean isLowerRange) {
            this.code = -1;
            this.prefixLength = prefixLength;
            this.rangeLength = rangeLength;
            this.rangeLow = rangeLow;
            this.isLowerRange = isLowerRange;
        }
        
        @Override
        public String toString() {
            return ((this.code != -1) ? ValueNode.bitPattern(this.code, this.prefixLength) : "?") + "/" + this.prefixLength + "/" + this.rangeLength + "/" + this.rangeLow;
        }
    }
}
