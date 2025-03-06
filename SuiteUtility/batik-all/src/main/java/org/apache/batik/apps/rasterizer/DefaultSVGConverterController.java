// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.apps.rasterizer;

import java.io.File;
import java.util.List;
import java.util.Map;
import org.apache.batik.transcoder.Transcoder;

public class DefaultSVGConverterController implements SVGConverterController
{
    public boolean proceedWithComputedTask(final Transcoder transcoder, final Map map, final List list, final List list2) {
        return true;
    }
    
    public boolean proceedWithSourceTranscoding(final SVGConverterSource svgConverterSource, final File file) {
        System.out.println("About to transcoder source of type: " + svgConverterSource.getClass().getName());
        return true;
    }
    
    public boolean proceedOnSourceTranscodingFailure(final SVGConverterSource svgConverterSource, final File file, final String s) {
        return true;
    }
    
    public void onSourceTranscodingSuccess(final SVGConverterSource svgConverterSource, final File file) {
    }
}
