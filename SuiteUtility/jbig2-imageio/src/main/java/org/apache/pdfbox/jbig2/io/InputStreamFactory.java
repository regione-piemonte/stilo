// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.pdfbox.jbig2.io;

import java.io.IOException;
import javax.imageio.stream.ImageInputStream;
import java.io.InputStream;

public interface InputStreamFactory
{
    ImageInputStream getInputStream(final InputStream p0) throws IOException;
}
