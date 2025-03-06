// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.swing.svg;

import java.io.File;
import javax.swing.filechooser.FileFilter;

public class SVGFileFilter extends FileFilter
{
    public boolean accept(final File file) {
        boolean b = false;
        if (file != null) {
            if (file.isDirectory()) {
                b = true;
            }
            else {
                final String lowerCase = file.getPath().toLowerCase();
                if (lowerCase.endsWith(".svg") || lowerCase.endsWith(".svgz")) {
                    b = true;
                }
            }
        }
        return b;
    }
    
    public String getDescription() {
        return ".svg, .svgz";
    }
}
