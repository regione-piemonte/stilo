// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.pdfbox.jbig2.decoder.huffman;

import org.apache.pdfbox.jbig2.io.SubInputStream;
import java.util.List;
import java.util.ArrayList;
import java.io.IOException;
import org.apache.pdfbox.jbig2.segments.Table;

public class EncodedTable extends HuffmanTable
{
    private Table table;
    
    public EncodedTable(final Table table) throws IOException {
        this.table = table;
        this.parseTable();
    }
    
    public void parseTable() throws IOException {
        final SubInputStream subInputStream = this.table.getSubInputStream();
        final ArrayList<Code> list = new ArrayList<Code>();
        int n2;
        for (int i = this.table.getHtLow(); i < this.table.getHtHigh(); i += 1 << n2) {
            final int n = (int)subInputStream.readBits(this.table.getHtPS());
            n2 = (int)subInputStream.readBits(this.table.getHtRS());
            list.add(new Code(n, n2, i, false));
        }
        list.add(new Code((int)subInputStream.readBits(this.table.getHtPS()), 32, this.table.getHtLow() - 1, true));
        list.add(new Code((int)subInputStream.readBits(this.table.getHtPS()), 32, this.table.getHtHigh(), false));
        if (this.table.getHtOOB() == 1) {
            list.add(new Code((int)subInputStream.readBits(this.table.getHtPS()), -1, -1, false));
        }
        this.initTree(list);
    }
}
