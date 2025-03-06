// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.script.rhino;

import org.apache.batik.script.rhino.svg12.SVG12RhinoInterpreter;
import org.apache.batik.script.Interpreter;
import java.net.URL;
import org.apache.batik.script.InterpreterFactory;

public class RhinoInterpreterFactory implements InterpreterFactory
{
    private static final String[] RHINO_MIMETYPES;
    
    public String[] getMimeTypes() {
        return RhinoInterpreterFactory.RHINO_MIMETYPES;
    }
    
    public Interpreter createInterpreter(final URL url, final boolean b) {
        if (b) {
            return new SVG12RhinoInterpreter(url);
        }
        return new RhinoInterpreter(url);
    }
    
    static {
        RHINO_MIMETYPES = new String[] { "application/ecmascript", "application/javascript", "text/ecmascript", "text/javascript" };
    }
}
