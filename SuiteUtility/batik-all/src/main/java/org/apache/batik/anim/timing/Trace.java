// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.anim.timing;

public class Trace
{
    private static int level;
    private static boolean enabled;
    
    public static void enter(final Object obj, final String str, final Object[] array) {
        if (Trace.enabled) {
            System.err.print("LOG\t");
            for (int i = 0; i < Trace.level; ++i) {
                System.err.print("  ");
            }
            if (str == null) {
                System.err.print("new " + obj.getClass().getName() + "(");
            }
            else {
                System.err.print(obj + "." + str + "(");
            }
            if (array != null) {
                System.err.print(array[0]);
                for (int j = 1; j < array.length; ++j) {
                    System.err.print(", " + array[j]);
                }
            }
            System.err.println(")");
        }
        ++Trace.level;
    }
    
    public static void exit() {
        --Trace.level;
    }
    
    public static void print(final String x) {
        if (Trace.enabled) {
            System.err.print("LOG\t");
            for (int i = 0; i < Trace.level; ++i) {
                System.err.print("  ");
            }
            System.err.println(x);
        }
    }
    
    static {
        Trace.enabled = false;
    }
}
