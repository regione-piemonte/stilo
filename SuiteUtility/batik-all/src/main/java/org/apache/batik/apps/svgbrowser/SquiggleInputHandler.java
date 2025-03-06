// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.apps.svgbrowser;

import org.apache.batik.util.ParsedURL;
import java.io.File;

public interface SquiggleInputHandler
{
    String[] getHandledMimeTypes();
    
    String[] getHandledExtensions();
    
    String getDescription();
    
    boolean accept(final File p0);
    
    boolean accept(final ParsedURL p0);
    
    void handle(final ParsedURL p0, final JSVGViewerFrame p1) throws Exception;
}
