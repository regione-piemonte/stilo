// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.script.rhino;

import org.mozilla.javascript.WrappedException;
import java.security.PrivilegedExceptionAction;
import java.security.AccessControlContext;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.Callable;
import org.mozilla.javascript.Context;
import java.security.AccessController;
import org.mozilla.javascript.GeneratedClassLoader;
import org.mozilla.javascript.SecurityController;

public class BatikSecurityController extends SecurityController
{
    public GeneratedClassLoader createClassLoader(final ClassLoader classLoader, final Object o) {
        if (o instanceof RhinoClassLoader) {
            return (GeneratedClassLoader)o;
        }
        throw new SecurityException("Script() objects are not supported");
    }
    
    public Object getDynamicSecurityDomain(final Object o) {
        final RhinoClassLoader rhinoClassLoader = (RhinoClassLoader)o;
        if (rhinoClassLoader != null) {
            return rhinoClassLoader;
        }
        return AccessController.getContext();
    }
    
    public Object callWithDomain(final Object o, final Context context, final Callable callable, final Scriptable scriptable, final Scriptable scriptable2, final Object[] array) {
        AccessControlContext rhinoAccessControlContext;
        if (o instanceof AccessControlContext) {
            rhinoAccessControlContext = (AccessControlContext)o;
        }
        else {
            rhinoAccessControlContext = ((RhinoClassLoader)o).rhinoAccessControlContext;
        }
        final PrivilegedExceptionAction action = new PrivilegedExceptionAction() {
            public Object run() {
                return callable.call(context, scriptable, scriptable2, array);
            }
        };
        try {
            return AccessController.doPrivileged((PrivilegedExceptionAction<Object>)action, rhinoAccessControlContext);
        }
        catch (Exception ex) {
            throw new WrappedException((Throwable)ex);
        }
    }
}
