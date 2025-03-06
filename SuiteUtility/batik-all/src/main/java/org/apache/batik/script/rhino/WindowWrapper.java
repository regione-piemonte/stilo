// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.script.rhino;

import java.security.AccessControlContext;
import org.mozilla.javascript.ScriptableObject;
import org.mozilla.javascript.NativeObject;
import java.security.AccessController;
import org.w3c.dom.Document;
import java.security.PrivilegedAction;
import org.mozilla.javascript.Function;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.Context;
import org.apache.batik.script.Window;
import org.mozilla.javascript.ImporterTopLevel;

public class WindowWrapper extends ImporterTopLevel
{
    private static final Object[] EMPTY_ARGUMENTS;
    protected RhinoInterpreter interpreter;
    protected Window window;
    
    public WindowWrapper(final Context context) {
        super(context);
        this.defineFunctionProperties(new String[] { "setInterval", "setTimeout", "clearInterval", "clearTimeout", "parseXML", "getURL", "postURL", "alert", "confirm", "prompt" }, WindowWrapper.class, 2);
    }
    
    public String getClassName() {
        return "Window";
    }
    
    public String toString() {
        return "[object Window]";
    }
    
    public static Object setInterval(final Context context, final Scriptable scriptable, final Object[] array, final Function function) {
        final int length = array.length;
        final Window window = ((WindowWrapper)scriptable).window;
        if (length < 2) {
            throw Context.reportRuntimeError("invalid argument count");
        }
        final long longValue = (long)Context.jsToJava(array[1], (Class)Long.TYPE);
        if (array[0] instanceof Function) {
            return Context.toObject(window.setInterval(new FunctionWrapper((RhinoInterpreter)window.getInterpreter(), (Function)array[0], WindowWrapper.EMPTY_ARGUMENTS), longValue), scriptable);
        }
        return Context.toObject(window.setInterval((String)Context.jsToJava(array[0], String.class), longValue), scriptable);
    }
    
    public static Object setTimeout(final Context context, final Scriptable scriptable, final Object[] array, final Function function) {
        final int length = array.length;
        final Window window = ((WindowWrapper)scriptable).window;
        if (length < 2) {
            throw Context.reportRuntimeError("invalid argument count");
        }
        final long longValue = (long)Context.jsToJava(array[1], (Class)Long.TYPE);
        if (array[0] instanceof Function) {
            return Context.toObject(window.setTimeout(new FunctionWrapper((RhinoInterpreter)window.getInterpreter(), (Function)array[0], WindowWrapper.EMPTY_ARGUMENTS), longValue), scriptable);
        }
        return Context.toObject(window.setTimeout((String)Context.jsToJava(array[0], String.class), longValue), scriptable);
    }
    
    public static void clearInterval(final Context context, final Scriptable scriptable, final Object[] array, final Function function) {
        final int length = array.length;
        final Window window = ((WindowWrapper)scriptable).window;
        if (length >= 1) {
            window.clearInterval(Context.jsToJava(array[0], Object.class));
        }
    }
    
    public static void clearTimeout(final Context context, final Scriptable scriptable, final Object[] array, final Function function) {
        final int length = array.length;
        final Window window = ((WindowWrapper)scriptable).window;
        if (length >= 1) {
            window.clearTimeout(Context.jsToJava(array[0], Object.class));
        }
    }
    
    public static Object parseXML(final Context context, final Scriptable scriptable, final Object[] array, final Function function) {
        final int length = array.length;
        final Window window = ((WindowWrapper)scriptable).window;
        if (length < 2) {
            throw Context.reportRuntimeError("invalid argument count");
        }
        return Context.toObject(AccessController.doPrivileged((PrivilegedAction<Object>)new PrivilegedAction() {
            public Object run() {
                return window.parseXML((String)Context.jsToJava(array[0], String.class), (Document)Context.jsToJava(array[1], Document.class));
            }
        }, ((RhinoInterpreter)window.getInterpreter()).getAccessControlContext()), scriptable);
    }
    
    public static void getURL(final Context context, final Scriptable scriptable, final Object[] array, final Function function) {
        final int length = array.length;
        final WindowWrapper windowWrapper = (WindowWrapper)scriptable;
        final Window window = windowWrapper.window;
        if (length < 2) {
            throw Context.reportRuntimeError("invalid argument count");
        }
        final RhinoInterpreter rhinoInterpreter = (RhinoInterpreter)window.getInterpreter();
        final String s = (String)Context.jsToJava(array[0], String.class);
        Window.URLResponseHandler urlResponseHandler;
        if (array[1] instanceof Function) {
            urlResponseHandler = new GetURLFunctionWrapper(rhinoInterpreter, (Function)array[1], windowWrapper);
        }
        else {
            urlResponseHandler = new GetURLObjectWrapper(rhinoInterpreter, (ScriptableObject)array[1], windowWrapper);
        }
        final Window.URLResponseHandler urlResponseHandler2 = urlResponseHandler;
        final AccessControlContext accessControlContext = ((RhinoInterpreter)window.getInterpreter()).getAccessControlContext();
        if (length == 2) {
            AccessController.doPrivileged((PrivilegedAction<Object>)new PrivilegedAction() {
                public Object run() {
                    window.getURL(s, urlResponseHandler2);
                    return null;
                }
            }, accessControlContext);
        }
        else {
            AccessController.doPrivileged((PrivilegedAction<Object>)new PrivilegedAction() {
                public Object run() {
                    window.getURL(s, urlResponseHandler2, (String)Context.jsToJava(array[2], String.class));
                    return null;
                }
            }, accessControlContext);
        }
    }
    
    public static void postURL(final Context context, final Scriptable scriptable, final Object[] array, final Function function) {
        final int length = array.length;
        final WindowWrapper windowWrapper = (WindowWrapper)scriptable;
        final Window window = windowWrapper.window;
        if (length < 3) {
            throw Context.reportRuntimeError("invalid argument count");
        }
        final RhinoInterpreter rhinoInterpreter = (RhinoInterpreter)window.getInterpreter();
        final String s = (String)Context.jsToJava(array[0], String.class);
        final String s2 = (String)Context.jsToJava(array[1], String.class);
        Window.URLResponseHandler urlResponseHandler;
        if (array[2] instanceof Function) {
            urlResponseHandler = new GetURLFunctionWrapper(rhinoInterpreter, (Function)array[2], windowWrapper);
        }
        else {
            urlResponseHandler = new GetURLObjectWrapper(rhinoInterpreter, (ScriptableObject)array[2], windowWrapper);
        }
        final Window.URLResponseHandler urlResponseHandler2 = urlResponseHandler;
        final AccessControlContext accessControlContext = rhinoInterpreter.getAccessControlContext();
        switch (length) {
            case 3: {
                AccessController.doPrivileged((PrivilegedAction<Object>)new PrivilegedAction() {
                    public Object run() {
                        window.postURL(s, s2, urlResponseHandler2);
                        return null;
                    }
                }, accessControlContext);
                break;
            }
            case 4: {
                AccessController.doPrivileged((PrivilegedAction<Object>)new PrivilegedAction() {
                    public Object run() {
                        window.postURL(s, s2, urlResponseHandler2, (String)Context.jsToJava(array[3], String.class));
                        return null;
                    }
                }, accessControlContext);
                break;
            }
            default: {
                AccessController.doPrivileged((PrivilegedAction<Object>)new PrivilegedAction() {
                    public Object run() {
                        window.postURL(s, s2, urlResponseHandler2, (String)Context.jsToJava(array[3], String.class), (String)Context.jsToJava(array[4], String.class));
                        return null;
                    }
                }, accessControlContext);
                break;
            }
        }
    }
    
    public static void alert(final Context context, final Scriptable scriptable, final Object[] array, final Function function) {
        final int length = array.length;
        final Window window = ((WindowWrapper)scriptable).window;
        if (length >= 1) {
            window.alert((String)Context.jsToJava(array[0], String.class));
        }
    }
    
    public static Object confirm(final Context context, final Scriptable scriptable, final Object[] array, final Function function) {
        final int length = array.length;
        final Window window = ((WindowWrapper)scriptable).window;
        if (length < 1) {
            return Context.toObject((Object)Boolean.FALSE, scriptable);
        }
        if (window.confirm((String)Context.jsToJava(array[0], String.class))) {
            return Context.toObject((Object)Boolean.TRUE, scriptable);
        }
        return Context.toObject((Object)Boolean.FALSE, scriptable);
    }
    
    public static Object prompt(final Context context, final Scriptable scriptable, final Object[] array, final Function function) {
        final Window window = ((WindowWrapper)scriptable).window;
        String s = null;
        switch (array.length) {
            case 0: {
                s = "";
                break;
            }
            case 1: {
                s = window.prompt((String)Context.jsToJava(array[0], String.class));
                break;
            }
            default: {
                s = window.prompt((String)Context.jsToJava(array[0], String.class), (String)Context.jsToJava(array[1], String.class));
                break;
            }
        }
        if (s == null) {
            return null;
        }
        return Context.toObject((Object)s, scriptable);
    }
    
    static {
        EMPTY_ARGUMENTS = new Object[0];
    }
    
    static class GetURLDoneArgBuilder implements RhinoInterpreter.ArgumentsBuilder
    {
        boolean success;
        String mime;
        String content;
        WindowWrapper windowWrapper;
        
        public GetURLDoneArgBuilder(final boolean success, final String mime, final String content, final WindowWrapper windowWrapper) {
            this.success = success;
            this.mime = mime;
            this.content = content;
            this.windowWrapper = windowWrapper;
        }
        
        public Object[] buildArguments() {
            final NativeObject nativeObject = new NativeObject();
            ((ScriptableObject)nativeObject).put("success", (Scriptable)nativeObject, (Object)(this.success ? Boolean.TRUE : Boolean.FALSE));
            if (this.mime != null) {
                ((ScriptableObject)nativeObject).put("contentType", (Scriptable)nativeObject, (Object)Context.toObject((Object)this.mime, (Scriptable)this.windowWrapper));
            }
            if (this.content != null) {
                ((ScriptableObject)nativeObject).put("content", (Scriptable)nativeObject, (Object)Context.toObject((Object)this.content, (Scriptable)this.windowWrapper));
            }
            return new Object[] { nativeObject };
        }
    }
    
    private static class GetURLObjectWrapper implements Window.URLResponseHandler
    {
        private RhinoInterpreter interpreter;
        private ScriptableObject object;
        private WindowWrapper windowWrapper;
        private static final String COMPLETE = "operationComplete";
        
        public GetURLObjectWrapper(final RhinoInterpreter interpreter, final ScriptableObject object, final WindowWrapper windowWrapper) {
            this.interpreter = interpreter;
            this.object = object;
            this.windowWrapper = windowWrapper;
        }
        
        public void getURLDone(final boolean b, final String s, final String s2) {
            this.interpreter.callMethod(this.object, "operationComplete", new GetURLDoneArgBuilder(b, s, s2, this.windowWrapper));
        }
    }
    
    protected static class GetURLFunctionWrapper implements Window.URLResponseHandler
    {
        protected RhinoInterpreter interpreter;
        protected Function function;
        protected WindowWrapper windowWrapper;
        
        public GetURLFunctionWrapper(final RhinoInterpreter interpreter, final Function function, final WindowWrapper windowWrapper) {
            this.interpreter = interpreter;
            this.function = function;
            this.windowWrapper = windowWrapper;
        }
        
        public void getURLDone(final boolean b, final String s, final String s2) {
            this.interpreter.callHandler(this.function, new GetURLDoneArgBuilder(b, s, s2, this.windowWrapper));
        }
    }
    
    protected static class FunctionWrapper implements Runnable
    {
        protected RhinoInterpreter interpreter;
        protected Function function;
        protected Object[] arguments;
        
        public FunctionWrapper(final RhinoInterpreter interpreter, final Function function, final Object[] arguments) {
            this.interpreter = interpreter;
            this.function = function;
            this.arguments = arguments;
        }
        
        public void run() {
            this.interpreter.callHandler(this.function, this.arguments);
        }
    }
}
