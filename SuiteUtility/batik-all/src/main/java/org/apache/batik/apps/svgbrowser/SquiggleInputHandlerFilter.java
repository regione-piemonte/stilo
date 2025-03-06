// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.apps.svgbrowser;

import java.io.File;
import javax.swing.filechooser.FileFilter;

public class SquiggleInputHandlerFilter extends FileFilter
{
    protected SquiggleInputHandler handler;
    
    public SquiggleInputHandlerFilter(final SquiggleInputHandler handler) {
        this.handler = handler;
    }
    
    public boolean accept(final File file) {
        return file.isDirectory() || this.handler.accept(file);
    }
    
    public String getDescription() {
        final StringBuffer sb = new StringBuffer();
        final String[] handledExtensions = this.handler.getHandledExtensions();
        final int n = (handledExtensions != null) ? handledExtensions.length : 0;
        for (int i = 0; i < n; ++i) {
            if (i > 0) {
                sb.append(", ");
            }
            sb.append(handledExtensions[i]);
        }
        if (n > 0) {
            sb.append(' ');
        }
        sb.append(this.handler.getDescription());
        return sb.toString();
    }
}
