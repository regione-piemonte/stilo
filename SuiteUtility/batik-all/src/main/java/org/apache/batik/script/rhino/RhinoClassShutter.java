// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.script.rhino;

import org.mozilla.javascript.ClassShutter;

public class RhinoClassShutter implements ClassShutter
{
    public boolean visibleToScripts(final String s) {
        if (s.startsWith("org.mozilla.javascript")) {
            return false;
        }
        if (s.startsWith("org.apache.batik.")) {
            final String substring = s.substring(17);
            if (substring.startsWith("script")) {
                return false;
            }
            if (substring.startsWith("apps")) {
                return false;
            }
            if (substring.startsWith("bridge.")) {
                if (substring.indexOf(".BaseScriptingEnvironment") != -1) {
                    return false;
                }
                if (substring.indexOf(".ScriptingEnvironment") != -1) {
                    return false;
                }
            }
        }
        return true;
    }
}
