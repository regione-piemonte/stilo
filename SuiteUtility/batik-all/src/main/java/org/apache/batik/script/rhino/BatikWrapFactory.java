// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.script.rhino;

import org.w3c.dom.events.EventTarget;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.WrapFactory;

class BatikWrapFactory extends WrapFactory
{
    private RhinoInterpreter interpreter;
    
    public BatikWrapFactory(final RhinoInterpreter interpreter) {
        this.interpreter = interpreter;
        this.setJavaPrimitiveWrap(false);
    }
    
    public Object wrap(final Context context, final Scriptable scriptable, final Object o, final Class clazz) {
        if (o instanceof EventTarget) {
            return this.interpreter.buildEventTargetWrapper((EventTarget)o);
        }
        return super.wrap(context, scriptable, o, clazz);
    }
}
