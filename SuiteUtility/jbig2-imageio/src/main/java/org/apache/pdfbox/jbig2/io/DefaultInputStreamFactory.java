// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.pdfbox.jbig2.io;

import java.io.IOException;
import javax.imageio.stream.MemoryCacheImageInputStream;
import java.io.File;
import javax.imageio.stream.FileCacheImageInputStream;
import javax.imageio.stream.ImageInputStream;
import java.io.InputStream;

public class DefaultInputStreamFactory implements InputStreamFactory
{
    @Override
    public ImageInputStream getInputStream(final InputStream inputStream) {
        try {
            return new FileCacheImageInputStream(inputStream, null);
        }
        catch (IOException ex) {
            return new MemoryCacheImageInputStream(inputStream);
        }
    }
}
