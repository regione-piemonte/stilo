// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.util;

import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.lang.ref.PhantomReference;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;

public class CleanerThread extends Thread
{
    static volatile ReferenceQueue queue;
    static CleanerThread thread;
    static /* synthetic */ Class class$org$apache$batik$util$CleanerThread;
    
    public static ReferenceQueue getReferenceQueue() {
        if (CleanerThread.queue == null) {
            Class class$;
            Class class$org$apache$batik$util$CleanerThread;
            if (CleanerThread.class$org$apache$batik$util$CleanerThread == null) {
                class$org$apache$batik$util$CleanerThread = (CleanerThread.class$org$apache$batik$util$CleanerThread = (class$ = class$("org.apache.batik.util.CleanerThread")));
            }
            else {
                class$ = (class$org$apache$batik$util$CleanerThread = CleanerThread.class$org$apache$batik$util$CleanerThread);
            }
            final Class clazz = class$org$apache$batik$util$CleanerThread;
            synchronized (class$) {
                CleanerThread.queue = new ReferenceQueue();
                CleanerThread.thread = new CleanerThread();
            }
        }
        return CleanerThread.queue;
    }
    
    protected CleanerThread() {
        super("Batik CleanerThread");
        this.setDaemon(true);
        this.start();
    }
    
    public void run() {
        while (true) {
            try {
                while (true) {
                    Reference remove;
                    try {
                        remove = CleanerThread.queue.remove();
                    }
                    catch (InterruptedException ex) {
                        continue;
                    }
                    if (remove instanceof ReferenceCleared) {
                        ((ReferenceCleared)remove).cleared();
                    }
                }
            }
            catch (ThreadDeath threadDeath) {
                throw threadDeath;
            }
            catch (Throwable t) {
                t.printStackTrace();
                continue;
            }
            break;
        }
    }
    
    static /* synthetic */ Class class$(final String className) {
        try {
            return Class.forName(className);
        }
        catch (ClassNotFoundException ex) {
            throw new NoClassDefFoundError(ex.getMessage());
        }
    }
    
    static {
        CleanerThread.queue = null;
        CleanerThread.thread = null;
    }
    
    public abstract static class PhantomReferenceCleared extends PhantomReference implements ReferenceCleared
    {
        public PhantomReferenceCleared(final Object referent) {
            super(referent, CleanerThread.getReferenceQueue());
        }
    }
    
    public interface ReferenceCleared
    {
        void cleared();
    }
    
    public abstract static class WeakReferenceCleared extends WeakReference implements ReferenceCleared
    {
        public WeakReferenceCleared(final Object referent) {
            super(referent, CleanerThread.getReferenceQueue());
        }
    }
    
    public abstract static class SoftReferenceCleared extends SoftReference implements ReferenceCleared
    {
        public SoftReferenceCleared(final Object referent) {
            super(referent, CleanerThread.getReferenceQueue());
        }
    }
}
