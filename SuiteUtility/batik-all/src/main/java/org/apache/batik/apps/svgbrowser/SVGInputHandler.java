// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.apps.svgbrowser;

import java.io.File;
import org.apache.batik.util.ParsedURL;

public class SVGInputHandler implements SquiggleInputHandler
{
    public static final String[] SVG_MIME_TYPES;
    public static final String[] SVG_FILE_EXTENSIONS;
    
    public String[] getHandledMimeTypes() {
        return SVGInputHandler.SVG_MIME_TYPES;
    }
    
    public String[] getHandledExtensions() {
        return SVGInputHandler.SVG_FILE_EXTENSIONS;
    }
    
    public String getDescription() {
        return "";
    }
    
    public void handle(final ParsedURL parsedURL, final JSVGViewerFrame jsvgViewerFrame) {
        jsvgViewerFrame.getJSVGCanvas().loadSVGDocument(parsedURL.toString());
    }
    
    public boolean accept(final File file) {
        return file != null && file.isFile() && this.accept(file.getPath());
    }
    
    public boolean accept(final ParsedURL parsedURL) {
        if (parsedURL == null) {
            return false;
        }
        final String path = parsedURL.getPath();
        return path != null && this.accept(path);
    }
    
    public boolean accept(final String s) {
        if (s == null) {
            return false;
        }
        for (int i = 0; i < SVGInputHandler.SVG_FILE_EXTENSIONS.length; ++i) {
            if (s.endsWith(SVGInputHandler.SVG_FILE_EXTENSIONS[i])) {
                return true;
            }
        }
        return false;
    }
    
    static {
        SVG_MIME_TYPES = new String[] { "image/svg+xml" };
        SVG_FILE_EXTENSIONS = new String[] { ".svg", ".svgz" };
    }
}
