// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.script.rhino.svg12;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.ScriptableObject;
import org.mozilla.javascript.Scriptable;
import java.net.URL;
import org.apache.batik.script.rhino.RhinoInterpreter;

public class SVG12RhinoInterpreter extends RhinoInterpreter
{
    public SVG12RhinoInterpreter(final URL url) {
        super(url);
    }
    
    protected void defineGlobalWrapperClass(final Scriptable scriptable) {
        try {
            ScriptableObject.defineClass(scriptable, GlobalWrapper.class);
        }
        catch (Exception ex) {}
    }
    
    protected ScriptableObject createGlobalObject(final Context context) {
        return (ScriptableObject)new GlobalWrapper(context);
    }
}
