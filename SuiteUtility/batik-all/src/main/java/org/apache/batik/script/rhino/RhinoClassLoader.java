// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.script.rhino;

import java.security.Permission;
import java.io.File;
import java.io.FilePermission;
import java.io.IOException;
import java.security.PermissionCollection;
import java.security.ProtectionDomain;
import java.security.cert.Certificate;
import java.security.AccessControlContext;
import java.security.CodeSource;
import java.net.URL;
import org.mozilla.javascript.GeneratedClassLoader;
import java.net.URLClassLoader;

public class RhinoClassLoader extends URLClassLoader implements GeneratedClassLoader
{
    protected URL documentURL;
    protected CodeSource codeSource;
    protected AccessControlContext rhinoAccessControlContext;
    
    public RhinoClassLoader(final URL url, final ClassLoader parent) {
        super((url != null) ? new URL[] { url } : new URL[0], parent);
        this.documentURL = url;
        if (url != null) {
            this.codeSource = new CodeSource(url, (Certificate[])null);
        }
        this.rhinoAccessControlContext = new AccessControlContext(new ProtectionDomain[] { new ProtectionDomain(this.codeSource, this.getPermissions(this.codeSource)) });
    }
    
    static URL[] getURL(final ClassLoader classLoader) {
        if (!(classLoader instanceof RhinoClassLoader)) {
            return new URL[0];
        }
        final URL documentURL = ((RhinoClassLoader)classLoader).documentURL;
        if (documentURL != null) {
            return new URL[] { documentURL };
        }
        return new URL[0];
    }
    
    public Class defineClass(final String name, final byte[] b) {
        return super.defineClass(name, b, 0, b.length, this.codeSource);
    }
    
    public void linkClass(final Class c) {
        super.resolveClass(c);
    }
    
    public AccessControlContext getAccessControlContext() {
        return this.rhinoAccessControlContext;
    }
    
    protected PermissionCollection getPermissions(final CodeSource codesource) {
        PermissionCollection permissions = null;
        if (codesource != null) {
            permissions = super.getPermissions(codesource);
        }
        if (this.documentURL != null && permissions != null) {
            Permission permission;
            try {
                permission = this.documentURL.openConnection().getPermission();
            }
            catch (IOException ex) {
                permission = null;
            }
            if (permission instanceof FilePermission) {
                final String name = permission.getName();
                if (!name.endsWith(File.separator)) {
                    final int lastIndex = name.lastIndexOf(File.separator);
                    if (lastIndex != -1) {
                        permissions.add(new FilePermission(name.substring(0, lastIndex + 1) + "-", "read"));
                    }
                }
            }
        }
        return permissions;
    }
}
