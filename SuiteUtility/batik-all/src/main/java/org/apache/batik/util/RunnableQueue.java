// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.util;

import java.util.NoSuchElementException;
import java.util.Iterator;

public class RunnableQueue implements Runnable
{
    public static final RunnableQueueState RUNNING;
    public static final RunnableQueueState SUSPENDING;
    public static final RunnableQueueState SUSPENDED;
    protected volatile RunnableQueueState state;
    protected final Object stateLock;
    protected boolean wasResumed;
    private final DoublyLinkedList list;
    protected int preemptCount;
    protected RunHandler runHandler;
    protected volatile HaltingThread runnableQueueThread;
    private IdleRunnable idleRunnable;
    private long idleRunnableWaitTime;
    private static volatile int threadCount;
    
    public RunnableQueue() {
        this.stateLock = new Object();
        this.list = new DoublyLinkedList();
    }
    
    public static RunnableQueue createRunnableQueue() {
        final RunnableQueue runnableQueue = new RunnableQueue();
        synchronized (runnableQueue) {
            final HaltingThread haltingThread = new HaltingThread(runnableQueue, "RunnableQueue-" + RunnableQueue.threadCount++);
            haltingThread.setDaemon(true);
            haltingThread.start();
            while (runnableQueue.getThread() == null) {
                try {
                    runnableQueue.wait();
                }
                catch (InterruptedException ex) {}
            }
        }
        return runnableQueue;
    }
    
    public void run() {
        Object o = this;
        synchronized (this) {
            this.runnableQueueThread = (HaltingThread)Thread.currentThread();
            this.notify();
        }
        try {
            while (!HaltingThread.hasBeenHalted()) {
                boolean b = false;
                boolean b2 = false;
                synchronized (this.stateLock) {
                    if (this.state != RunnableQueue.RUNNING) {
                        this.state = RunnableQueue.SUSPENDED;
                        b = true;
                    }
                }
                if (b) {
                    this.executionSuspended();
                }
                synchronized (this.stateLock) {
                    while (this.state != RunnableQueue.RUNNING) {
                        this.state = RunnableQueue.SUSPENDED;
                        this.stateLock.notifyAll();
                        try {
                            this.stateLock.wait();
                        }
                        catch (InterruptedException ex) {}
                    }
                    if (this.wasResumed) {
                        this.wasResumed = false;
                        b2 = true;
                    }
                }
                if (b2) {
                    this.executionResumed();
                }
                Runnable runnable = null;
                synchronized (this.list) {
                    if (this.state == RunnableQueue.SUSPENDING) {
                        continue;
                    }
                    o = this.list.pop();
                    if (this.preemptCount != 0) {
                        --this.preemptCount;
                    }
                    Label_0334: {
                        if (o == null) {
                            if (this.idleRunnable != null) {
                                final long waitTime = this.idleRunnable.getWaitTime();
                                this.idleRunnableWaitTime = waitTime;
                                if (waitTime < System.currentTimeMillis()) {
                                    runnable = this.idleRunnable;
                                    break Label_0334;
                                }
                            }
                            try {
                                if (this.idleRunnable != null && this.idleRunnableWaitTime != Long.MAX_VALUE) {
                                    final long n = this.idleRunnableWaitTime - System.currentTimeMillis();
                                    if (n <= 0L) {
                                        continue;
                                    }
                                    this.list.wait(n);
                                }
                                else {
                                    this.list.wait();
                                }
                            }
                            catch (InterruptedException ex2) {}
                            continue;
                        }
                        runnable = ((Link)o).runnable;
                    }
                }
                this.runnableStart(runnable);
                try {
                    runnable.run();
                }
                catch (ThreadDeath threadDeath) {
                    throw threadDeath;
                }
                catch (Throwable t) {
                    t.printStackTrace();
                }
                if (o != null) {
                    ((Link)o).unlock();
                }
                this.runnableInvoked(runnable);
            }
        }
        finally {
            synchronized (this) {
                this.runnableQueueThread = null;
            }
        }
    }
    
    public HaltingThread getThread() {
        return this.runnableQueueThread;
    }
    
    public void invokeLater(final Runnable runnable) {
        if (this.runnableQueueThread == null) {
            throw new IllegalStateException("RunnableQueue not started or has exited");
        }
        synchronized (this.list) {
            this.list.push(new Link(runnable));
            this.list.notify();
        }
    }
    
    public void invokeAndWait(final Runnable runnable) throws InterruptedException {
        if (this.runnableQueueThread == null) {
            throw new IllegalStateException("RunnableQueue not started or has exited");
        }
        if (this.runnableQueueThread == Thread.currentThread()) {
            throw new IllegalStateException("Cannot be called from the RunnableQueue thread");
        }
        final LockableLink lockableLink = new LockableLink(runnable);
        synchronized (this.list) {
            this.list.push(lockableLink);
            this.list.notify();
        }
        lockableLink.lock();
    }
    
    public void preemptLater(final Runnable runnable) {
        if (this.runnableQueueThread == null) {
            throw new IllegalStateException("RunnableQueue not started or has exited");
        }
        synchronized (this.list) {
            this.list.add(this.preemptCount, new Link(runnable));
            ++this.preemptCount;
            this.list.notify();
        }
    }
    
    public void preemptAndWait(final Runnable runnable) throws InterruptedException {
        if (this.runnableQueueThread == null) {
            throw new IllegalStateException("RunnableQueue not started or has exited");
        }
        if (this.runnableQueueThread == Thread.currentThread()) {
            throw new IllegalStateException("Cannot be called from the RunnableQueue thread");
        }
        final LockableLink lockableLink = new LockableLink(runnable);
        synchronized (this.list) {
            this.list.add(this.preemptCount, lockableLink);
            ++this.preemptCount;
            this.list.notify();
        }
        lockableLink.lock();
    }
    
    public RunnableQueueState getQueueState() {
        synchronized (this.stateLock) {
            return this.state;
        }
    }
    
    public void suspendExecution(final boolean b) {
        if (this.runnableQueueThread == null) {
            throw new IllegalStateException("RunnableQueue not started or has exited");
        }
        synchronized (this.stateLock) {
            this.wasResumed = false;
            if (this.state == RunnableQueue.SUSPENDED) {
                this.stateLock.notifyAll();
                return;
            }
            if (this.state == RunnableQueue.RUNNING) {
                this.state = RunnableQueue.SUSPENDING;
                synchronized (this.list) {
                    this.list.notify();
                }
            }
            if (b) {
                while (this.state == RunnableQueue.SUSPENDING) {
                    try {
                        this.stateLock.wait();
                    }
                    catch (InterruptedException ex) {}
                }
            }
        }
    }
    
    public void resumeExecution() {
        if (this.runnableQueueThread == null) {
            throw new IllegalStateException("RunnableQueue not started or has exited");
        }
        synchronized (this.stateLock) {
            this.wasResumed = true;
            if (this.state != RunnableQueue.RUNNING) {
                this.state = RunnableQueue.RUNNING;
                this.stateLock.notifyAll();
            }
        }
    }
    
    public Object getIteratorLock() {
        return this.list;
    }
    
    public Iterator iterator() {
        return new Iterator() {
            Link head = (Link)RunnableQueue.this.list.getHead();
            Link link;
            
            public boolean hasNext() {
                return this.head != null && (this.link == null || this.link != this.head);
            }
            
            public Object next() {
                if (this.head == null || this.head == this.link) {
                    throw new NoSuchElementException();
                }
                if (this.link == null) {
                    this.link = (Link)this.head.getNext();
                    return this.head.runnable;
                }
                final Runnable access$100 = this.link.runnable;
                this.link = (Link)this.link.getNext();
                return access$100;
            }
            
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }
    
    public synchronized void setRunHandler(final RunHandler runHandler) {
        this.runHandler = runHandler;
    }
    
    public synchronized RunHandler getRunHandler() {
        return this.runHandler;
    }
    
    public void setIdleRunnable(final IdleRunnable idleRunnable) {
        synchronized (this.list) {
            this.idleRunnable = idleRunnable;
            this.idleRunnableWaitTime = 0L;
            this.list.notify();
        }
    }
    
    protected synchronized void executionSuspended() {
        if (this.runHandler != null) {
            this.runHandler.executionSuspended(this);
        }
    }
    
    protected synchronized void executionResumed() {
        if (this.runHandler != null) {
            this.runHandler.executionResumed(this);
        }
    }
    
    protected synchronized void runnableStart(final Runnable runnable) {
        if (this.runHandler != null) {
            this.runHandler.runnableStart(this, runnable);
        }
    }
    
    protected synchronized void runnableInvoked(final Runnable runnable) {
        if (this.runHandler != null) {
            this.runHandler.runnableInvoked(this, runnable);
        }
    }
    
    static {
        RUNNING = new RunnableQueueState("Running");
        SUSPENDING = new RunnableQueueState("Suspending");
        SUSPENDED = new RunnableQueueState("Suspended");
    }
    
    protected static class LockableLink extends Link
    {
        private volatile boolean locked;
        
        public LockableLink(final Runnable runnable) {
            super(runnable);
        }
        
        public boolean isLocked() {
            return this.locked;
        }
        
        public synchronized void lock() throws InterruptedException {
            this.locked = true;
            this.notify();
            this.wait();
        }
        
        public synchronized void unlock() {
            while (!this.locked) {
                try {
                    this.wait();
                }
                catch (InterruptedException ex) {}
            }
            this.locked = false;
            this.notify();
        }
    }
    
    protected static class Link extends DoublyLinkedList.Node
    {
        private final Runnable runnable;
        
        public Link(final Runnable runnable) {
            this.runnable = runnable;
        }
        
        public void unlock() {
        }
    }
    
    public static class RunHandlerAdapter implements RunHandler
    {
        public void runnableStart(final RunnableQueue runnableQueue, final Runnable runnable) {
        }
        
        public void runnableInvoked(final RunnableQueue runnableQueue, final Runnable runnable) {
        }
        
        public void executionSuspended(final RunnableQueue runnableQueue) {
        }
        
        public void executionResumed(final RunnableQueue runnableQueue) {
        }
    }
    
    public interface RunHandler
    {
        void runnableStart(final RunnableQueue p0, final Runnable p1);
        
        void runnableInvoked(final RunnableQueue p0, final Runnable p1);
        
        void executionSuspended(final RunnableQueue p0);
        
        void executionResumed(final RunnableQueue p0);
    }
    
    public interface IdleRunnable extends Runnable
    {
        long getWaitTime();
    }
    
    public static final class RunnableQueueState
    {
        private final String value;
        
        private RunnableQueueState(final String value) {
            this.value = value;
        }
        
        public String getValue() {
            return this.value;
        }
        
        public String toString() {
            return "[RunnableQueueState: " + this.value + ']';
        }
    }
}
