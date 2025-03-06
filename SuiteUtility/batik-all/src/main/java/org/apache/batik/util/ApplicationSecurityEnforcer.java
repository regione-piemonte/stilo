// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.util;

import java.security.Policy;
import java.net.URL;

public class ApplicationSecurityEnforcer
{
    public static final String EXCEPTION_ALIEN_SECURITY_MANAGER = "ApplicationSecurityEnforcer.message.security.exception.alien.security.manager";
    public static final String EXCEPTION_NO_POLICY_FILE = "ApplicationSecurityEnforcer.message.null.pointer.exception.no.policy.file";
    public static final String PROPERTY_JAVA_SECURITY_POLICY = "java.security.policy";
    public static final String JAR_PROTOCOL = "jar:";
    public static final String JAR_URL_FILE_SEPARATOR = "!/";
    public static final String PROPERTY_APP_DEV_BASE = "app.dev.base";
    public static final String PROPERTY_APP_JAR_BASE = "app.jar.base";
    public static final String APP_MAIN_CLASS_DIR = "classes/";
    protected Class appMainClass;
    protected String securityPolicy;
    protected String appMainClassRelativeURL;
    protected BatikSecurityManager lastSecurityManagerInstalled;
    
    public ApplicationSecurityEnforcer(final Class clazz, final String s, final String s2) {
        this(clazz, s);
    }
    
    public ApplicationSecurityEnforcer(final Class appMainClass, final String securityPolicy) {
        this.appMainClass = appMainClass;
        this.securityPolicy = securityPolicy;
        this.appMainClassRelativeURL = appMainClass.getName().replace('.', '/') + ".class";
    }
    
    public void enforceSecurity(final boolean b) {
        final SecurityManager securityManager = System.getSecurityManager();
        if (securityManager != null && securityManager != this.lastSecurityManagerInstalled) {
            throw new SecurityException(Messages.getString("ApplicationSecurityEnforcer.message.security.exception.alien.security.manager"));
        }
        if (b) {
            System.setSecurityManager(null);
            this.installSecurityManager();
        }
        else if (securityManager != null) {
            System.setSecurityManager(null);
            this.lastSecurityManagerInstalled = null;
        }
    }
    
    public URL getPolicyURL() {
        final URL resource = this.appMainClass.getClassLoader().getResource(this.securityPolicy);
        if (resource == null) {
            throw new NullPointerException(Messages.formatMessage("ApplicationSecurityEnforcer.message.null.pointer.exception.no.policy.file", new Object[] { this.securityPolicy }));
        }
        return resource;
    }
    
    public void installSecurityManager() {
        final Policy policy = Policy.getPolicy();
        final BatikSecurityManager batikSecurityManager = new BatikSecurityManager();
        final ClassLoader classLoader = this.appMainClass.getClassLoader();
        final String property = System.getProperty("java.security.policy");
        if (property == null || property.equals("")) {
            System.setProperty("java.security.policy", this.getPolicyURL().toString());
        }
        final URL resource = classLoader.getResource(this.appMainClassRelativeURL);
        if (resource == null) {
            throw new Error(this.appMainClassRelativeURL);
        }
        final String string = resource.toString();
        if (string.startsWith("jar:")) {
            this.setJarBase(string);
        }
        else {
            this.setDevBase(string);
        }
        System.setSecurityManager(batikSecurityManager);
        this.lastSecurityManagerInstalled = batikSecurityManager;
        policy.refresh();
        if (property == null || property.equals("")) {
            System.setProperty("java.security.policy", "");
        }
    }
    
    private void setJarBase(String substring) {
        if (System.getProperty("app.jar.base") == null) {
            substring = substring.substring("jar:".length());
            final int index = substring.indexOf("!/" + this.appMainClassRelativeURL);
            if (index == -1) {
                throw new Error();
            }
            final String substring2 = substring.substring(0, index);
            final int lastIndex = substring2.lastIndexOf(47);
            String substring3;
            if (lastIndex == -1) {
                substring3 = "";
            }
            else {
                substring3 = substring2.substring(0, lastIndex);
            }
            System.setProperty("app.jar.base", substring3);
        }
    }
    
    private void setDevBase(final String s) {
        if (System.getProperty("app.dev.base") == null) {
            final int index = s.indexOf("classes/" + this.appMainClassRelativeURL);
            if (index == -1) {
                throw new Error();
            }
            System.setProperty("app.dev.base", s.substring(0, index));
        }
    }
}
