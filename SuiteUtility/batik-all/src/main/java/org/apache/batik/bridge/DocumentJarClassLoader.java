// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.bridge;

import java.util.Enumeration;
import java.security.Permission;
import java.security.Policy;
import java.security.PermissionCollection;
import java.security.cert.Certificate;
import java.net.URL;
import java.security.CodeSource;
import java.net.URLClassLoader;

public class DocumentJarClassLoader extends URLClassLoader
{
    protected CodeSource documentCodeSource;
    
    public DocumentJarClassLoader(final URL url, final URL url2) {
        super(new URL[] { url });
        this.documentCodeSource = null;
        if (url2 != null) {
            this.documentCodeSource = new CodeSource(url2, (Certificate[])null);
        }
    }
    
    protected PermissionCollection getPermissions(final CodeSource codesource) {
        final Policy policy = Policy.getPolicy();
        PermissionCollection permissions = null;
        if (policy != null) {
            permissions = policy.getPermissions(codesource);
        }
        if (this.documentCodeSource != null) {
            final PermissionCollection permissions2 = super.getPermissions(this.documentCodeSource);
            if (permissions != null) {
                final Enumeration<Permission> elements = permissions2.elements();
                while (elements.hasMoreElements()) {
                    permissions.add(elements.nextElement());
                }
            }
            else {
                permissions = permissions2;
            }
        }
        return permissions;
    }
}
