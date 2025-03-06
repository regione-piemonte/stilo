// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.pdfbox.jbig2.decoder.huffman;

import java.io.IOException;
import javax.imageio.stream.ImageInputStream;

abstract class Node
{
    protected abstract long decode(final ImageInputStream p0) throws IOException;
}
