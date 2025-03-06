// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.pdfbox.jbig2.decoder.huffman;

import java.util.List;

public class FixedSizeTable extends HuffmanTable
{
    public FixedSizeTable(final List<Code> list) {
        this.initTree(list);
    }
}
