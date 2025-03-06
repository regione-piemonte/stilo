// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.apps.rasterizer;

import java.io.IOException;
import java.io.InputStream;

public interface SVGConverterSource
{
    String getName();
    
    InputStream openStream() throws IOException;
    
    boolean isSameAs(final String p0);
    
    boolean isReadable();
    
    String getURI();
}
