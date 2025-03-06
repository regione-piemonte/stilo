// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.util;

import java.io.Reader;
import java.io.InputStream;
import java.util.Enumeration;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;
import java.util.HashMap;

public class Service
{
    static HashMap providerMap;
    
    public static synchronized Iterator providers(final Class clazz) {
        final String string = "META-INF/services/" + clazz.getName();
        final List list = Service.providerMap.get(string);
        if (list != null) {
            return list.iterator();
        }
        final ArrayList<Object> value = new ArrayList<Object>();
        Service.providerMap.put(string, value);
        ClassLoader classLoader = null;
        try {
            classLoader = clazz.getClassLoader();
        }
        catch (SecurityException ex) {}
        if (classLoader == null) {
            classLoader = Service.class.getClassLoader();
        }
        if (classLoader == null) {
            return value.iterator();
        }
        Enumeration<URL> resources;
        try {
            resources = classLoader.getResources(string);
        }
        catch (IOException ex2) {
            return value.iterator();
        }
        while (resources.hasMoreElements()) {
            InputStream openStream = null;
            Reader in = null;
            BufferedReader bufferedReader = null;
            try {
                openStream = resources.nextElement().openStream();
                in = new InputStreamReader(openStream, "UTF-8");
                bufferedReader = new BufferedReader(in);
                String s = bufferedReader.readLine();
                while (s != null) {
                    try {
                        final int index = s.indexOf(35);
                        if (index != -1) {
                            s = s.substring(0, index);
                        }
                        final String trim = s.trim();
                        if (trim.length() == 0) {
                            s = bufferedReader.readLine();
                            continue;
                        }
                        value.add(classLoader.loadClass(trim).newInstance());
                    }
                    catch (Exception ex3) {}
                    s = bufferedReader.readLine();
                }
            }
            catch (Exception ex4) {}
            catch (LinkageError linkageError) {}
            finally {
                if (openStream != null) {
                    try {
                        openStream.close();
                    }
                    catch (IOException ex5) {}
                }
                if (in != null) {
                    try {
                        in.close();
                    }
                    catch (IOException ex6) {}
                }
                if (bufferedReader == null) {
                    try {
                        bufferedReader.close();
                    }
                    catch (IOException ex7) {}
                }
            }
            break;
        }
        return value.iterator();
    }
    
    static {
        Service.providerMap = new HashMap();
    }
}
