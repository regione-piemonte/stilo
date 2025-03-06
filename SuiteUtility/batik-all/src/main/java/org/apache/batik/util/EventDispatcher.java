// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.util;

import java.lang.reflect.InvocationTargetException;
import java.awt.EventQueue;
import java.util.List;

public class EventDispatcher
{
    public static void fireEvent(final Dispatcher dispatcher, final List list, final Object o, final boolean b) {
        if (b && !EventQueue.isDispatchThread()) {
            final Runnable runnable = new Runnable() {
                public void run() {
                    EventDispatcher.fireEvent(dispatcher, list, o, b);
                }
            };
            try {
                EventQueue.invokeAndWait(runnable);
            }
            catch (InvocationTargetException ex) {
                ex.printStackTrace();
            }
            catch (InterruptedException ex2) {}
            catch (ThreadDeath threadDeath) {
                throw threadDeath;
            }
            catch (Throwable t) {
                t.printStackTrace();
            }
            return;
        }
        Object[] array = null;
        Throwable t2 = null;
        int n = 10;
        while (--n != 0) {
            try {
                synchronized (list) {
                    if (list.size() == 0) {
                        return;
                    }
                    array = list.toArray();
                    break;
                }
            }
            catch (Throwable t3) {
                t2 = t3;
                continue;
            }
            break;
        }
        if (array == null) {
            if (t2 != null) {
                t2.printStackTrace();
            }
            return;
        }
        dispatchEvent(dispatcher, array, o);
    }
    
    protected static void dispatchEvent(final Dispatcher dispatcher, final Object[] array, final Object o) {
        ThreadDeath threadDeath = null;
        try {
            for (int i = 0; i < array.length; ++i) {
                try {
                    final Object o2;
                    synchronized (array) {
                        o2 = array[i];
                        if (o2 == null) {
                            continue;
                        }
                        array[i] = null;
                    }
                    dispatcher.dispatch(o2, o);
                }
                catch (ThreadDeath o2) {
                    threadDeath = (ThreadDeath)o2;
                }
                catch (Throwable o2) {
                    ((Throwable)o2).printStackTrace();
                }
            }
        }
        catch (ThreadDeath threadDeath2) {
            threadDeath = threadDeath2;
        }
        catch (Throwable t) {
            if (array[array.length - 1] != null) {
                dispatchEvent(dispatcher, array, o);
            }
            t.printStackTrace();
        }
        if (threadDeath != null) {
            throw threadDeath;
        }
    }
    
    public interface Dispatcher
    {
        void dispatch(final Object p0, final Object p1);
    }
}
