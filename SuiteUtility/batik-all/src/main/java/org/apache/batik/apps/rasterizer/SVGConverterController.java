// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.apps.rasterizer;

import java.io.File;
import java.util.List;
import java.util.Map;
import org.apache.batik.transcoder.Transcoder;

public interface SVGConverterController
{
    boolean proceedWithComputedTask(final Transcoder p0, final Map p1, final List p2, final List p3);
    
    boolean proceedWithSourceTranscoding(final SVGConverterSource p0, final File p1);
    
    boolean proceedOnSourceTranscodingFailure(final SVGConverterSource p0, final File p1, final String p2);
    
    void onSourceTranscodingSuccess(final SVGConverterSource p0, final File p1);
}
