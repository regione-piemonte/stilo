// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.util;

import java.util.Collection;
import java.io.DataInputStream;
import java.util.zip.ZipEntry;
import java.io.InputStream;
import java.io.FileInputStream;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.Iterator;
import java.io.IOException;
import java.util.List;
import java.util.Collections;
import java.util.ArrayList;
import java.util.Set;
import java.util.jar.JarFile;
import java.util.HashSet;
import java.util.Map;
import java.util.HashMap;
import java.io.File;

public class ClassFileUtilities
{
    public static final byte CONSTANT_UTF8_INFO = 1;
    public static final byte CONSTANT_INTEGER_INFO = 3;
    public static final byte CONSTANT_FLOAT_INFO = 4;
    public static final byte CONSTANT_LONG_INFO = 5;
    public static final byte CONSTANT_DOUBLE_INFO = 6;
    public static final byte CONSTANT_CLASS_INFO = 7;
    public static final byte CONSTANT_STRING_INFO = 8;
    public static final byte CONSTANT_FIELDREF_INFO = 9;
    public static final byte CONSTANT_METHODREF_INFO = 10;
    public static final byte CONSTANT_INTERFACEMETHODREF_INFO = 11;
    public static final byte CONSTANT_NAMEANDTYPE_INFO = 12;
    
    protected ClassFileUtilities() {
    }
    
    public static void main(final String[] array) {
        boolean b = false;
        if (array.length == 1 && array[0].equals("-f")) {
            b = true;
        }
        else if (array.length != 0) {
            System.err.println("usage: org.apache.batik.util.ClassFileUtilities [-f]");
            System.err.println();
            System.err.println("  -f    list files that cause each jar file dependency");
            System.exit(1);
        }
        final File file = new File(".");
        File file2 = null;
        final String[] list = file.list();
        for (int i = 0; i < list.length; ++i) {
            if (list[i].startsWith("batik-")) {
                file2 = new File(list[i]);
                if (file2.isDirectory()) {
                    break;
                }
                file2 = null;
            }
        }
        if (file2 == null || !file2.isDirectory()) {
            System.out.println("Directory 'batik-xxx' not found in current directory!");
            return;
        }
        try {
            final HashMap<Object, ClassFile> hashMap = (HashMap<Object, ClassFile>)new HashMap<Object, Object>();
            final HashMap<Object, Jar> hashMap2 = new HashMap<Object, Jar>();
            collectJars(file2, hashMap2, hashMap);
            final HashSet<JarFile> set = new HashSet<JarFile>();
            final Iterator<Jar> iterator = hashMap2.values().iterator();
            while (iterator.hasNext()) {
                set.add(iterator.next().jarFile);
            }
            for (final ClassFile classFile : hashMap.values()) {
                final Iterator iterator3 = getClassDependencies(classFile.getInputStream(), set, false).iterator();
                while (iterator3.hasNext()) {
                    final ClassFile classFile2 = hashMap.get(iterator3.next());
                    if (classFile != classFile2 && classFile2 != null) {
                        classFile.deps.add(classFile2);
                    }
                }
            }
            for (final ClassFile classFile3 : hashMap.values()) {
                for (final ClassFile classFile4 : classFile3.deps) {
                    final Jar jar = classFile3.jar;
                    final Jar jar2 = classFile4.jar;
                    if (!classFile3.name.equals(classFile4.name) && jar2 != jar) {
                        if (jar.files.contains(classFile4.name)) {
                            continue;
                        }
                        final Integer n = jar.deps.get(jar2);
                        if (n == null) {
                            jar.deps.put(jar2, new Integer(1));
                        }
                        else {
                            jar.deps.put(jar2, new Integer(n + 1));
                        }
                    }
                }
            }
            final ArrayList<Triple> list2 = (ArrayList<Triple>)new ArrayList<Comparable>(10);
            for (final Jar from : hashMap2.values()) {
                for (final Jar to : from.deps.keySet()) {
                    final Triple triple = new Triple();
                    triple.from = from;
                    triple.to = to;
                    triple.count = from.deps.get(to);
                    list2.add(triple);
                }
            }
            Collections.sort((List<Comparable>)list2);
            for (final Triple triple2 : list2) {
                System.out.println(triple2.count + "," + triple2.from.name + "," + triple2.to.name);
                if (b) {
                    for (final ClassFile classFile5 : triple2.from.files) {
                        for (final ClassFile classFile6 : classFile5.deps) {
                            if (classFile6.jar == triple2.to && !triple2.from.files.contains(classFile6.name)) {
                                System.out.println("\t" + classFile5.name + " --> " + classFile6.name);
                            }
                        }
                    }
                }
            }
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    private static void collectJars(final File file, final Map map, final Map map2) throws IOException {
        final File[] listFiles = file.listFiles();
        for (int i = 0; i < listFiles.length; ++i) {
            if (listFiles[i].getName().endsWith(".jar") && listFiles[i].isFile()) {
                final Jar jar = new Jar();
                jar.name = listFiles[i].getPath();
                jar.file = listFiles[i];
                jar.jarFile = new JarFile(listFiles[i]);
                map.put(jar.name, jar);
                final Enumeration<JarEntry> entries = jar.jarFile.entries();
                while (entries.hasMoreElements()) {
                    final String name = entries.nextElement().getName();
                    if (name.endsWith(".class")) {
                        final ClassFile classFile = new ClassFile();
                        classFile.name = name;
                        classFile.jar = jar;
                        map2.put(jar.name + '!' + classFile.name, classFile);
                        jar.files.add(classFile);
                    }
                }
            }
            else if (listFiles[i].isDirectory()) {
                collectJars(listFiles[i], map, map2);
            }
        }
    }
    
    public static Set getClassDependencies(final String name, final Set set, final boolean b) throws IOException {
        return getClassDependencies(new FileInputStream(name), set, b);
    }
    
    public static Set getClassDependencies(final InputStream inputStream, final Set set, final boolean b) throws IOException {
        final HashSet set2 = new HashSet();
        computeClassDependencies(inputStream, set, new HashSet(), set2, b);
        return set2;
    }
    
    private static void computeClassDependencies(final InputStream inputStream, final Set set, final Set set2, final Set set3, final boolean b) throws IOException {
        for (final String s : getClassDependencies(inputStream)) {
            if (!set2.contains(s)) {
                set2.add(s);
                final Iterator<JarFile> iterator2 = set.iterator();
                while (iterator2.hasNext()) {
                    InputStream inputStream2 = null;
                    String pathname = null;
                    final JarFile next = iterator2.next();
                    if (next instanceof JarFile) {
                        final JarFile jarFile = next;
                        final String string = s + ".class";
                        final ZipEntry entry = jarFile.getEntry(string);
                        if (entry != null) {
                            pathname = jarFile.getName() + '!' + string;
                            inputStream2 = jarFile.getInputStream(entry);
                        }
                    }
                    else {
                        pathname = (String)next + '/' + s + ".class";
                        final File file = new File(pathname);
                        if (file.isFile()) {
                            inputStream2 = new FileInputStream(file);
                        }
                    }
                    if (inputStream2 != null) {
                        set3.add(pathname);
                        if (!b) {
                            continue;
                        }
                        computeClassDependencies(inputStream2, set, set2, set3, b);
                    }
                }
            }
        }
    }
    
    public static Set getClassDependencies(final InputStream in) throws IOException {
        final DataInputStream dataInputStream = new DataInputStream(in);
        if (dataInputStream.readInt() != -889275714) {
            throw new IOException("Invalid classfile");
        }
        dataInputStream.readInt();
        final short short1 = dataInputStream.readShort();
        final String[] array = new String[short1];
        final HashSet<Integer> set = new HashSet<Integer>();
        final HashSet<Integer> set2 = new HashSet<Integer>();
        for (short n = 1; n < short1; ++n) {
            final int i = dataInputStream.readByte() & 0xFF;
            switch (i) {
                case 5:
                case 6: {
                    dataInputStream.readLong();
                    ++n;
                    break;
                }
                case 3:
                case 4:
                case 9:
                case 10:
                case 11: {
                    dataInputStream.readInt();
                    break;
                }
                case 7: {
                    set.add(new Integer(dataInputStream.readShort() & 0xFFFF));
                    break;
                }
                case 8: {
                    dataInputStream.readShort();
                    break;
                }
                case 12: {
                    dataInputStream.readShort();
                    set2.add(new Integer(dataInputStream.readShort() & 0xFFFF));
                    break;
                }
                case 1: {
                    array[n] = dataInputStream.readUTF();
                    break;
                }
                default: {
                    throw new RuntimeException("unexpected data in constant-pool:" + i);
                }
            }
        }
        final HashSet<String> set3 = new HashSet<String>();
        final Iterator<Object> iterator = set.iterator();
        while (iterator.hasNext()) {
            set3.add(array[iterator.next()]);
        }
        final Iterator<Object> iterator2 = set2.iterator();
        while (iterator2.hasNext()) {
            set3.addAll((Collection<?>)getDescriptorClasses(array[iterator2.next()]));
        }
        return set3;
    }
    
    protected static Set getDescriptorClasses(final String s) {
        final HashSet<String> set = new HashSet<String>();
        int index = 0;
        Label_0395: {
            switch (s.charAt(index)) {
                case '(': {
                Label_0181:
                    while (true) {
                        switch (s.charAt(++index)) {
                            case '[': {
                                char char1;
                                do {
                                    char1 = s.charAt(++index);
                                } while (char1 == '[');
                                if (char1 != 'L') {
                                    continue;
                                }
                            }
                            case 'L': {
                                char c = s.charAt(++index);
                                final StringBuffer sb = new StringBuffer();
                                while (c != ';') {
                                    sb.append(c);
                                    c = s.charAt(++index);
                                }
                                set.add(sb.toString());
                                continue;
                            }
                            default: {
                                continue;
                            }
                            case ')': {
                                break Label_0181;
                            }
                        }
                    }
                    switch (s.charAt(++index)) {
                        case '[': {
                            char char2;
                            do {
                                char2 = s.charAt(++index);
                            } while (char2 == '[');
                            if (char2 != 'L') {
                                break Label_0395;
                            }
                        }
                        case 'L': {
                            char c2 = s.charAt(++index);
                            final StringBuffer sb2 = new StringBuffer();
                            while (c2 != ';') {
                                sb2.append(c2);
                                c2 = s.charAt(++index);
                            }
                            set.add(sb2.toString());
                            break Label_0395;
                        }
                        default: {
                            break Label_0395;
                        }
                    }
                    break;
                }
                case '[': {
                    char char3;
                    do {
                        char3 = s.charAt(++index);
                    } while (char3 == '[');
                    if (char3 != 'L') {
                        break;
                    }
                }
                case 'L': {
                    char c3 = s.charAt(++index);
                    final StringBuffer sb3 = new StringBuffer();
                    while (c3 != ';') {
                        sb3.append(c3);
                        c3 = s.charAt(++index);
                    }
                    set.add(sb3.toString());
                    break;
                }
            }
        }
        return set;
    }
    
    protected static class Triple implements Comparable
    {
        public Jar from;
        public Jar to;
        public int count;
        
        public int compareTo(final Object o) {
            return ((Triple)o).count - this.count;
        }
    }
    
    protected static class Jar
    {
        public String name;
        public File file;
        public JarFile jarFile;
        public Map deps;
        public Set files;
        
        protected Jar() {
            this.deps = new HashMap();
            this.files = new HashSet();
        }
    }
    
    protected static class ClassFile
    {
        public String name;
        public List deps;
        public Jar jar;
        
        protected ClassFile() {
            this.deps = new ArrayList(10);
        }
        
        public InputStream getInputStream() throws IOException {
            return this.jar.jarFile.getInputStream(this.jar.jarFile.getEntry(this.name));
        }
    }
}
