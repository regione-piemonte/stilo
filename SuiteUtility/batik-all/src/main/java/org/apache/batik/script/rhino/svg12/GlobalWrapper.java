// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.script.rhino.svg12;

import org.w3c.dom.events.EventTarget;
import org.mozilla.javascript.NativeJavaObject;
import org.apache.batik.dom.svg12.SVGGlobal;
import org.mozilla.javascript.Function;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.Context;
import org.apache.batik.script.rhino.WindowWrapper;

public class GlobalWrapper extends WindowWrapper
{
    public GlobalWrapper(final Context context) {
        super(context);
        this.defineFunctionProperties(new String[] { "startMouseCapture", "stopMouseCapture" }, GlobalWrapper.class, 2);
    }
    
    public String getClassName() {
        return "SVGGlobal";
    }
    
    public String toString() {
        return "[object SVGGlobal]";
    }
    
    public static void startMouseCapture(final Context context, final Scriptable scriptable, final Object[] array, final Function function) {
        final int length = array.length;
        final SVGGlobal svgGlobal = (SVGGlobal)((GlobalWrapper)scriptable).window;
        if (length >= 3) {
            EventTarget eventTarget = null;
            if (array[0] instanceof NativeJavaObject) {
                final Object unwrap = ((NativeJavaObject)array[0]).unwrap();
                if (unwrap instanceof EventTarget) {
                    eventTarget = (EventTarget)unwrap;
                }
            }
            if (eventTarget == null) {
                throw Context.reportRuntimeError("First argument to startMouseCapture must be an EventTarget");
            }
            svgGlobal.startMouseCapture(eventTarget, Context.toBoolean(array[1]), Context.toBoolean(array[2]));
        }
    }
    
    public static void stopMouseCapture(final Context context, final Scriptable scriptable, final Object[] array, final Function function) {
        ((SVGGlobal)((GlobalWrapper)scriptable).window).stopMouseCapture();
    }
}
