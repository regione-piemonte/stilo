// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.script.rhino;

import java.util.Locale;
import java.io.Writer;
import org.w3c.dom.events.EventTarget;
import org.mozilla.javascript.Function;
import java.util.Iterator;
import java.security.AccessController;
import java.io.StringReader;
import java.security.PrivilegedAction;
import org.mozilla.javascript.Script;
import org.apache.batik.bridge.InterruptedBridgeException;
import org.mozilla.javascript.JavaScriptException;
import org.apache.batik.script.InterpreterException;
import org.mozilla.javascript.WrappedException;
import java.io.IOException;
import java.io.Reader;
import java.security.AccessControlContext;
import org.mozilla.javascript.ClassCache;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ContextAction;
import java.net.URL;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.ContextFactory;
import org.mozilla.javascript.SecurityController;
import org.mozilla.javascript.ClassShutter;
import org.mozilla.javascript.WrapFactory;
import java.util.LinkedList;
import org.mozilla.javascript.ScriptableObject;
import org.apache.batik.script.Window;
import java.util.List;
import org.apache.batik.script.Interpreter;

public class RhinoInterpreter implements Interpreter
{
    protected static String[] TO_BE_IMPORTED;
    private static final int MAX_CACHED_SCRIPTS = 32;
    public static final String SOURCE_NAME_SVG = "<SVG>";
    public static final String BIND_NAME_WINDOW = "window";
    protected static List contexts;
    protected Window window;
    protected ScriptableObject globalObject;
    protected LinkedList compiledScripts;
    protected WrapFactory wrapFactory;
    protected ClassShutter classShutter;
    protected RhinoClassLoader rhinoClassLoader;
    protected SecurityController securityController;
    protected ContextFactory contextFactory;
    protected Context defaultContext;
    
    public RhinoInterpreter(final URL url) {
        this.globalObject = null;
        this.compiledScripts = new LinkedList();
        this.wrapFactory = new BatikWrapFactory(this);
        this.classShutter = (ClassShutter)new RhinoClassShutter();
        this.securityController = new BatikSecurityController();
        this.contextFactory = new Factory();
        try {
            this.rhinoClassLoader = new RhinoClassLoader(url, this.getClass().getClassLoader());
        }
        catch (SecurityException ex) {
            this.rhinoClassLoader = null;
        }
        this.contextFactory.call((ContextAction)new ContextAction() {
            public Object run(final Context context) {
                RhinoInterpreter.this.defineGlobalWrapperClass((Scriptable)context.initStandardObjects((ScriptableObject)null, false));
                RhinoInterpreter.this.globalObject = RhinoInterpreter.this.createGlobalObject(context);
                ClassCache.get((Scriptable)RhinoInterpreter.this.globalObject).setCachingEnabled(RhinoInterpreter.this.rhinoClassLoader != null);
                final StringBuffer sb = new StringBuffer("importPackage(Packages.");
                for (int i = 0; i < RhinoInterpreter.TO_BE_IMPORTED.length - 1; ++i) {
                    sb.append(RhinoInterpreter.TO_BE_IMPORTED[i]);
                    sb.append(");importPackage(Packages.");
                }
                sb.append(RhinoInterpreter.TO_BE_IMPORTED[RhinoInterpreter.TO_BE_IMPORTED.length - 1]);
                sb.append(')');
                context.evaluateString((Scriptable)RhinoInterpreter.this.globalObject, sb.toString(), (String)null, 0, (Object)RhinoInterpreter.this.rhinoClassLoader);
                return null;
            }
        });
    }
    
    public Window getWindow() {
        return this.window;
    }
    
    public ContextFactory getContextFactory() {
        return this.contextFactory;
    }
    
    protected void defineGlobalWrapperClass(final Scriptable scriptable) {
        try {
            ScriptableObject.defineClass(scriptable, WindowWrapper.class);
        }
        catch (Exception ex) {}
    }
    
    protected ScriptableObject createGlobalObject(final Context context) {
        return (ScriptableObject)new WindowWrapper(context);
    }
    
    public AccessControlContext getAccessControlContext() {
        return this.rhinoClassLoader.getAccessControlContext();
    }
    
    protected ScriptableObject getGlobalObject() {
        return this.globalObject;
    }
    
    public Object evaluate(final Reader reader) throws IOException {
        return this.evaluate(reader, "<SVG>");
    }
    
    public Object evaluate(final Reader reader, final String s) throws IOException {
        final ContextAction contextAction = (ContextAction)new ContextAction() {
            public Object run(final Context context) {
                try {
                    return context.evaluateReader((Scriptable)RhinoInterpreter.this.globalObject, reader, s, 1, (Object)RhinoInterpreter.this.rhinoClassLoader);
                }
                catch (IOException ex) {
                    throw new WrappedException((Throwable)ex);
                }
            }
        };
        try {
            return this.contextFactory.call((ContextAction)contextAction);
        }
        catch (JavaScriptException ex) {
            final Object value = ex.getValue();
            final Exception ex2 = (value instanceof Exception) ? ((Exception)value) : ex;
            throw new InterpreterException(ex2, ex2.getMessage(), -1, -1);
        }
        catch (WrappedException ex3) {
            final Throwable wrappedException = ex3.getWrappedException();
            if (wrappedException instanceof Exception) {
                throw new InterpreterException((Exception)wrappedException, wrappedException.getMessage(), -1, -1);
            }
            throw new InterpreterException(wrappedException.getMessage(), -1, -1);
        }
        catch (InterruptedBridgeException ex4) {
            throw ex4;
        }
        catch (RuntimeException ex5) {
            throw new InterpreterException(ex5, ex5.getMessage(), -1, -1);
        }
    }
    
    public Object evaluate(final String s) {
        final ContextAction contextAction = (ContextAction)new ContextAction() {
            private final /* synthetic */ RhinoInterpreter this$0;
            
            public Object run(final Context context) {
                Script script = null;
                Entry e = null;
                final Iterator iterator = RhinoInterpreter.this.compiledScripts.iterator();
                while (iterator.hasNext()) {
                    if ((e = iterator.next()).str.equals(s)) {
                        script = e.script;
                        iterator.remove();
                        break;
                    }
                }
                if (script == null) {
                    script = AccessController.doPrivileged((PrivilegedAction<Script>)new PrivilegedAction() {
                        private final /* synthetic */ RhinoInterpreter$3 this$1 = this$1;
                        
                        public Object run() {
                            try {
                                return context.compileReader((Reader)new StringReader(s), "<SVG>", 1, (Object)this.this$1.this$0.rhinoClassLoader);
                            }
                            catch (IOException ex) {
                                throw new Error(ex.getMessage());
                            }
                        }
                    });
                    if (RhinoInterpreter.this.compiledScripts.size() + 1 > 32) {
                        RhinoInterpreter.this.compiledScripts.removeFirst();
                    }
                    RhinoInterpreter.this.compiledScripts.addLast(new Entry(s, script));
                }
                else {
                    RhinoInterpreter.this.compiledScripts.addLast(e);
                }
                return script.exec(context, (Scriptable)RhinoInterpreter.this.globalObject);
            }
        };
        try {
            return this.contextFactory.call((ContextAction)contextAction);
        }
        catch (InterpreterException ex) {
            throw ex;
        }
        catch (JavaScriptException ex2) {
            final Object value = ex2.getValue();
            final Exception ex3 = (value instanceof Exception) ? ((Exception)value) : ex2;
            throw new InterpreterException(ex3, ex3.getMessage(), -1, -1);
        }
        catch (WrappedException ex4) {
            final Throwable wrappedException = ex4.getWrappedException();
            if (wrappedException instanceof Exception) {
                throw new InterpreterException((Exception)wrappedException, wrappedException.getMessage(), -1, -1);
            }
            throw new InterpreterException(wrappedException.getMessage(), -1, -1);
        }
        catch (RuntimeException ex5) {
            throw new InterpreterException(ex5, ex5.getMessage(), -1, -1);
        }
    }
    
    public void dispose() {
        if (this.rhinoClassLoader != null) {
            ClassCache.get((Scriptable)this.globalObject).setCachingEnabled(false);
        }
    }
    
    public void bindObject(final String s, final Object o) {
        this.contextFactory.call((ContextAction)new ContextAction() {
            public Object run(final Context context) {
                Object o = o;
                if (s.equals("window") && o instanceof Window) {
                    ((WindowWrapper)RhinoInterpreter.this.globalObject).window = (Window)o;
                    RhinoInterpreter.this.window = (Window)o;
                    o = RhinoInterpreter.this.globalObject;
                }
                RhinoInterpreter.this.globalObject.put(s, (Scriptable)RhinoInterpreter.this.globalObject, (Object)Context.toObject(o, (Scriptable)RhinoInterpreter.this.globalObject));
                return null;
            }
        });
    }
    
    void callHandler(final Function function, final Object o) {
        this.contextFactory.call((ContextAction)new ContextAction() {
            public Object run(final Context context) {
                function.call(context, (Scriptable)RhinoInterpreter.this.globalObject, (Scriptable)RhinoInterpreter.this.globalObject, new Object[] { Context.toObject(o, (Scriptable)RhinoInterpreter.this.globalObject) });
                return null;
            }
        });
    }
    
    void callMethod(final ScriptableObject scriptableObject, final String s, final ArgumentsBuilder argumentsBuilder) {
        this.contextFactory.call((ContextAction)new ContextAction() {
            public Object run(final Context context) {
                ScriptableObject.callMethod((Scriptable)scriptableObject, s, argumentsBuilder.buildArguments());
                return null;
            }
        });
    }
    
    void callHandler(final Function function, final Object[] array) {
        this.contextFactory.call((ContextAction)new ContextAction() {
            public Object run(final Context context) {
                function.call(context, (Scriptable)RhinoInterpreter.this.globalObject, (Scriptable)RhinoInterpreter.this.globalObject, array);
                return null;
            }
        });
    }
    
    void callHandler(final Function function, final ArgumentsBuilder argumentsBuilder) {
        this.contextFactory.call((ContextAction)new ContextAction() {
            public Object run(final Context context) {
                function.call(context, function.getParentScope(), (Scriptable)RhinoInterpreter.this.globalObject, argumentsBuilder.buildArguments());
                return null;
            }
        });
    }
    
    Object call(final ContextAction contextAction) {
        return this.contextFactory.call(contextAction);
    }
    
    Scriptable buildEventTargetWrapper(final EventTarget eventTarget) {
        return (Scriptable)new EventTargetWrapper((Scriptable)this.globalObject, eventTarget, this);
    }
    
    public void setOut(final Writer writer) {
    }
    
    public Locale getLocale() {
        return null;
    }
    
    public void setLocale(final Locale locale) {
    }
    
    public String formatMessage(final String s, final Object[] array) {
        return null;
    }
    
    static {
        RhinoInterpreter.TO_BE_IMPORTED = new String[] { "java.lang", "org.w3c.dom", "org.w3c.dom.css", "org.w3c.dom.events", "org.w3c.dom.smil", "org.w3c.dom.stylesheets", "org.w3c.dom.svg", "org.w3c.dom.views", "org.w3c.dom.xpath" };
        RhinoInterpreter.contexts = new LinkedList();
    }
    
    protected class Factory extends ContextFactory
    {
        protected Context makeContext() {
            final Context context = super.makeContext();
            context.setWrapFactory(RhinoInterpreter.this.wrapFactory);
            context.setSecurityController(RhinoInterpreter.this.securityController);
            context.setClassShutter(RhinoInterpreter.this.classShutter);
            if (RhinoInterpreter.this.rhinoClassLoader == null) {
                context.setOptimizationLevel(-1);
            }
            return context;
        }
    }
    
    protected static class Entry
    {
        public String str;
        public Script script;
        
        public Entry(final String str, final Script script) {
            this.str = str;
            this.script = script;
        }
    }
    
    public interface ArgumentsBuilder
    {
        Object[] buildArguments();
    }
}
