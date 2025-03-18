/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
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
