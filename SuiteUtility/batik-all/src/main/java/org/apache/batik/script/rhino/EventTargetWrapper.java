// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.script.rhino;

import org.mozilla.javascript.ScriptableObject;
import org.mozilla.javascript.ContextAction;
import org.apache.batik.script.ScriptEventWrapper;
import org.w3c.dom.events.Event;
import org.mozilla.javascript.NativeObject;
import org.apache.batik.dom.AbstractNode;
import org.mozilla.javascript.Undefined;
import org.w3c.dom.events.EventListener;
import java.lang.ref.SoftReference;
import org.mozilla.javascript.Context;
import java.util.Map;
import org.mozilla.javascript.Function;
import org.w3c.dom.events.EventTarget;
import org.mozilla.javascript.Scriptable;
import java.util.WeakHashMap;
import org.mozilla.javascript.NativeJavaObject;

class EventTargetWrapper extends NativeJavaObject
{
    protected static WeakHashMap mapOfListenerMap;
    public static final String ADD_NAME = "addEventListener";
    public static final String ADDNS_NAME = "addEventListenerNS";
    public static final String REMOVE_NAME = "removeEventListener";
    public static final String REMOVENS_NAME = "removeEventListenerNS";
    protected RhinoInterpreter interpreter;
    
    EventTargetWrapper(final Scriptable scriptable, final EventTarget eventTarget, final RhinoInterpreter interpreter) {
        super(scriptable, (Object)eventTarget, (Class)null);
        this.interpreter = interpreter;
    }
    
    public Object get(final String s, final Scriptable scriptable) {
        Object value = super.get(s, scriptable);
        if (s.equals("addEventListener")) {
            value = new FunctionAddProxy(this.interpreter, (Function)value, this.initMap());
        }
        else if (s.equals("removeEventListener")) {
            value = new FunctionRemoveProxy((Function)value, this.initMap());
        }
        else if (s.equals("addEventListenerNS")) {
            value = new FunctionAddNSProxy(this.interpreter, (Function)value, this.initMap());
        }
        else if (s.equals("removeEventListenerNS")) {
            value = new FunctionRemoveNSProxy((Function)value, this.initMap());
        }
        return value;
    }
    
    public Map initMap() {
        if (EventTargetWrapper.mapOfListenerMap == null) {
            EventTargetWrapper.mapOfListenerMap = new WeakHashMap(10);
        }
        Map map;
        if ((map = EventTargetWrapper.mapOfListenerMap.get(this.unwrap())) == null) {
            EventTargetWrapper.mapOfListenerMap.put(this.unwrap(), map = new WeakHashMap(2));
        }
        return map;
    }
    
    static class FunctionRemoveNSProxy extends FunctionProxy
    {
        protected Map listenerMap;
        
        FunctionRemoveNSProxy(final Function function, final Map listenerMap) {
            super(function);
            this.listenerMap = listenerMap;
        }
        
        public Object call(final Context context, final Scriptable scriptable, final Scriptable scriptable2, final Object[] array) {
            final NativeJavaObject nativeJavaObject = (NativeJavaObject)scriptable2;
            if (array[2] instanceof Function) {
                final SoftReference<EventListener> softReference = this.listenerMap.get(array[2]);
                if (softReference == null) {
                    return Undefined.instance;
                }
                final EventListener eventListener = softReference.get();
                if (eventListener == null) {
                    return Undefined.instance;
                }
                final Class[] array2 = { String.class, String.class, Function.class, Boolean.TYPE };
                for (int i = 0; i < array.length; ++i) {
                    array[i] = Context.jsToJava(array[i], array2[i]);
                }
                ((AbstractNode)nativeJavaObject.unwrap()).removeEventListenerNS((String)array[0], (String)array[1], eventListener, (boolean)array[3]);
                return Undefined.instance;
            }
            else {
                if (!(array[2] instanceof NativeObject)) {
                    return this.delegate.call(context, scriptable, scriptable2, array);
                }
                final SoftReference<EventListener> softReference2 = this.listenerMap.get(array[2]);
                if (softReference2 == null) {
                    return Undefined.instance;
                }
                final EventListener eventListener2 = softReference2.get();
                if (eventListener2 == null) {
                    return Undefined.instance;
                }
                final Class[] array3 = { String.class, String.class, Scriptable.class, Boolean.TYPE };
                for (int j = 0; j < array.length; ++j) {
                    array[j] = Context.jsToJava(array[j], array3[j]);
                }
                ((AbstractNode)nativeJavaObject.unwrap()).removeEventListenerNS((String)array[0], (String)array[1], eventListener2, (boolean)array[3]);
                return Undefined.instance;
            }
        }
    }
    
    abstract static class FunctionProxy implements Function
    {
        protected Function delegate;
        
        public FunctionProxy(final Function delegate) {
            this.delegate = delegate;
        }
        
        public Scriptable construct(final Context context, final Scriptable scriptable, final Object[] array) {
            return this.delegate.construct(context, scriptable, array);
        }
        
        public String getClassName() {
            return this.delegate.getClassName();
        }
        
        public Object get(final String s, final Scriptable scriptable) {
            return this.delegate.get(s, scriptable);
        }
        
        public Object get(final int n, final Scriptable scriptable) {
            return this.delegate.get(n, scriptable);
        }
        
        public boolean has(final String s, final Scriptable scriptable) {
            return this.delegate.has(s, scriptable);
        }
        
        public boolean has(final int n, final Scriptable scriptable) {
            return this.delegate.has(n, scriptable);
        }
        
        public void put(final String s, final Scriptable scriptable, final Object o) {
            this.delegate.put(s, scriptable, o);
        }
        
        public void put(final int n, final Scriptable scriptable, final Object o) {
            this.delegate.put(n, scriptable, o);
        }
        
        public void delete(final String s) {
            this.delegate.delete(s);
        }
        
        public void delete(final int n) {
            this.delegate.delete(n);
        }
        
        public Scriptable getPrototype() {
            return this.delegate.getPrototype();
        }
        
        public void setPrototype(final Scriptable prototype) {
            this.delegate.setPrototype(prototype);
        }
        
        public Scriptable getParentScope() {
            return this.delegate.getParentScope();
        }
        
        public void setParentScope(final Scriptable parentScope) {
            this.delegate.setParentScope(parentScope);
        }
        
        public Object[] getIds() {
            return this.delegate.getIds();
        }
        
        public Object getDefaultValue(final Class clazz) {
            return this.delegate.getDefaultValue(clazz);
        }
        
        public boolean hasInstance(final Scriptable scriptable) {
            return this.delegate.hasInstance(scriptable);
        }
    }
    
    static class FunctionAddNSProxy extends FunctionProxy
    {
        protected Map listenerMap;
        protected RhinoInterpreter interpreter;
        
        FunctionAddNSProxy(final RhinoInterpreter interpreter, final Function function, final Map listenerMap) {
            super(function);
            this.listenerMap = listenerMap;
            this.interpreter = interpreter;
        }
        
        public Object call(final Context context, final Scriptable scriptable, final Scriptable scriptable2, final Object[] array) {
            final NativeJavaObject nativeJavaObject = (NativeJavaObject)scriptable2;
            if (array[2] instanceof Function) {
                final FunctionEventListener referent = new FunctionEventListener((Function)array[2], this.interpreter);
                this.listenerMap.put(array[2], new SoftReference<FunctionEventListener>(referent));
                final Class[] array2 = { String.class, String.class, Function.class, Boolean.TYPE, Object.class };
                for (int i = 0; i < array.length; ++i) {
                    array[i] = Context.jsToJava(array[i], array2[i]);
                }
                ((AbstractNode)nativeJavaObject.unwrap()).addEventListenerNS((String)array[0], (String)array[1], referent, (boolean)array[3], array[4]);
                return Undefined.instance;
            }
            if (array[2] instanceof NativeObject) {
                final HandleEventListener referent2 = new HandleEventListener((Scriptable)array[2], this.interpreter);
                this.listenerMap.put(array[2], new SoftReference<HandleEventListener>(referent2));
                final Class[] array3 = { String.class, String.class, Scriptable.class, Boolean.TYPE, Object.class };
                for (int j = 0; j < array.length; ++j) {
                    array[j] = Context.jsToJava(array[j], array3[j]);
                }
                ((AbstractNode)nativeJavaObject.unwrap()).addEventListenerNS((String)array[0], (String)array[1], referent2, (boolean)array[3], array[4]);
                return Undefined.instance;
            }
            return this.delegate.call(context, scriptable, scriptable2, array);
        }
    }
    
    static class FunctionEventListener implements EventListener
    {
        protected Function function;
        protected RhinoInterpreter interpreter;
        
        FunctionEventListener(final Function function, final RhinoInterpreter interpreter) {
            this.function = function;
            this.interpreter = interpreter;
        }
        
        public void handleEvent(final Event event) {
            Object eventObject;
            if (event instanceof ScriptEventWrapper) {
                eventObject = ((ScriptEventWrapper)event).getEventObject();
            }
            else {
                eventObject = event;
            }
            this.interpreter.callHandler(this.function, eventObject);
        }
    }
    
    static class HandleEventListener implements EventListener
    {
        public static final String HANDLE_EVENT = "handleEvent";
        public Scriptable scriptable;
        public Object[] array;
        public RhinoInterpreter interpreter;
        
        HandleEventListener(final Scriptable scriptable, final RhinoInterpreter interpreter) {
            this.array = new Object[1];
            this.scriptable = scriptable;
            this.interpreter = interpreter;
        }
        
        public void handleEvent(final Event event) {
            if (event instanceof ScriptEventWrapper) {
                this.array[0] = ((ScriptEventWrapper)event).getEventObject();
            }
            else {
                this.array[0] = event;
            }
            this.interpreter.call((ContextAction)new ContextAction() {
                private final /* synthetic */ HandleEventListener this$0 = this$0;
                
                public Object run(final Context context) {
                    ScriptableObject.callMethod(this.this$0.scriptable, "handleEvent", this.this$0.array);
                    return null;
                }
            });
        }
    }
    
    static class FunctionRemoveProxy extends FunctionProxy
    {
        public Map listenerMap;
        
        FunctionRemoveProxy(final Function function, final Map listenerMap) {
            super(function);
            this.listenerMap = listenerMap;
        }
        
        public Object call(final Context context, final Scriptable scriptable, final Scriptable scriptable2, final Object[] array) {
            final NativeJavaObject nativeJavaObject = (NativeJavaObject)scriptable2;
            if (array[1] instanceof Function) {
                final SoftReference<EventListener> softReference = this.listenerMap.get(array[1]);
                if (softReference == null) {
                    return Undefined.instance;
                }
                final EventListener eventListener = softReference.get();
                if (eventListener == null) {
                    return Undefined.instance;
                }
                final Class[] array2 = { String.class, Function.class, Boolean.TYPE };
                for (int i = 0; i < array.length; ++i) {
                    array[i] = Context.jsToJava(array[i], array2[i]);
                }
                ((EventTarget)nativeJavaObject.unwrap()).removeEventListener((String)array[0], eventListener, (boolean)array[2]);
                return Undefined.instance;
            }
            else {
                if (!(array[1] instanceof NativeObject)) {
                    return this.delegate.call(context, scriptable, scriptable2, array);
                }
                final SoftReference<EventListener> softReference2 = this.listenerMap.get(array[1]);
                if (softReference2 == null) {
                    return Undefined.instance;
                }
                final EventListener eventListener2 = softReference2.get();
                if (eventListener2 == null) {
                    return Undefined.instance;
                }
                final Class[] array3 = { String.class, Scriptable.class, Boolean.TYPE };
                for (int j = 0; j < array.length; ++j) {
                    array[j] = Context.jsToJava(array[j], array3[j]);
                }
                ((EventTarget)nativeJavaObject.unwrap()).removeEventListener((String)array[0], eventListener2, (boolean)array[2]);
                return Undefined.instance;
            }
        }
    }
    
    static class FunctionAddProxy extends FunctionProxy
    {
        protected Map listenerMap;
        protected RhinoInterpreter interpreter;
        
        FunctionAddProxy(final RhinoInterpreter interpreter, final Function function, final Map listenerMap) {
            super(function);
            this.listenerMap = listenerMap;
            this.interpreter = interpreter;
        }
        
        public Object call(final Context context, final Scriptable scriptable, final Scriptable scriptable2, final Object[] array) {
            final NativeJavaObject nativeJavaObject = (NativeJavaObject)scriptable2;
            if (array[1] instanceof Function) {
                EventListener referent = null;
                final SoftReference<EventListener> softReference = this.listenerMap.get(array[1]);
                if (softReference != null) {
                    referent = softReference.get();
                }
                if (referent == null) {
                    referent = new FunctionEventListener((Function)array[1], this.interpreter);
                    this.listenerMap.put(array[1], new SoftReference<EventListener>(referent));
                }
                final Class[] array2 = { String.class, Function.class, Boolean.TYPE };
                for (int i = 0; i < array.length; ++i) {
                    array[i] = Context.jsToJava(array[i], array2[i]);
                }
                ((EventTarget)nativeJavaObject.unwrap()).addEventListener((String)array[0], referent, (boolean)array[2]);
                return Undefined.instance;
            }
            if (array[1] instanceof NativeObject) {
                EventListener referent2 = null;
                final SoftReference<EventListener> softReference2 = this.listenerMap.get(array[1]);
                if (softReference2 != null) {
                    referent2 = softReference2.get();
                }
                if (referent2 == null) {
                    referent2 = new HandleEventListener((Scriptable)array[1], this.interpreter);
                    this.listenerMap.put(array[1], new SoftReference<EventListener>(referent2));
                }
                final Class[] array3 = { String.class, Scriptable.class, Boolean.TYPE };
                for (int j = 0; j < array.length; ++j) {
                    array[j] = Context.jsToJava(array[j], array3[j]);
                }
                ((EventTarget)nativeJavaObject.unwrap()).addEventListener((String)array[0], referent2, (boolean)array[2]);
                return Undefined.instance;
            }
            return this.delegate.call(context, scriptable, scriptable2, array);
        }
    }
}
