// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.bridge;

import java.util.Iterator;
import java.util.Collection;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import org.w3c.dom.events.Event;
import org.w3c.dom.events.EventTarget;
import org.w3c.dom.events.DocumentEvent;
import org.apache.batik.dom.events.AbstractEvent;
import org.apache.batik.gvt.RootGraphicsNode;
import java.awt.image.BufferedImage;
import org.apache.batik.gvt.event.GraphicsNodeChangeListener;
import org.apache.batik.gvt.renderer.ImageRenderer;
import org.apache.batik.bridge.svg12.SVG12BridgeContext;
import org.apache.batik.bridge.svg12.DefaultXBLManager;
import org.apache.batik.bridge.svg12.SVG12ScriptingEnvironment;
import org.apache.batik.dom.svg.SVGOMDocument;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import org.apache.batik.util.EventDispatcher;
import java.util.TimerTask;
import java.util.Timer;
import org.apache.batik.gvt.GraphicsNode;
import org.apache.batik.gvt.UpdateTracker;
import java.util.List;
import org.apache.batik.util.RunnableQueue;
import org.w3c.dom.Document;

public class UpdateManager
{
    static final int MIN_REPAINT_TIME;
    protected BridgeContext bridgeContext;
    protected Document document;
    protected RunnableQueue updateRunnableQueue;
    protected RunnableQueue.RunHandler runHandler;
    protected volatile boolean running;
    protected volatile boolean suspendCalled;
    protected List listeners;
    protected ScriptingEnvironment scriptingEnvironment;
    protected RepaintManager repaintManager;
    protected UpdateTracker updateTracker;
    protected GraphicsNode graphicsNode;
    protected boolean started;
    protected BridgeContext[] secondaryBridgeContexts;
    protected ScriptingEnvironment[] secondaryScriptingEnvironments;
    protected int minRepaintTime;
    long outOfDateTime;
    List suspensionList;
    int nextSuspensionIndex;
    long allResumeTime;
    Timer repaintTriggerTimer;
    TimerTask repaintTimerTask;
    static EventDispatcher.Dispatcher startedDispatcher;
    static EventDispatcher.Dispatcher stoppedDispatcher;
    static EventDispatcher.Dispatcher suspendedDispatcher;
    static EventDispatcher.Dispatcher resumedDispatcher;
    static EventDispatcher.Dispatcher updateStartedDispatcher;
    static EventDispatcher.Dispatcher updateCompletedDispatcher;
    static EventDispatcher.Dispatcher updateFailedDispatcher;
    
    public UpdateManager(final BridgeContext bridgeContext, final GraphicsNode graphicsNode, final Document document) {
        this.listeners = Collections.synchronizedList(new LinkedList<Object>());
        this.outOfDateTime = 0L;
        this.suspensionList = new ArrayList();
        this.nextSuspensionIndex = 1;
        this.allResumeTime = -1L;
        this.repaintTriggerTimer = null;
        this.repaintTimerTask = null;
        (this.bridgeContext = bridgeContext).setUpdateManager(this);
        this.document = document;
        this.updateRunnableQueue = RunnableQueue.createRunnableQueue();
        this.runHandler = this.createRunHandler();
        this.updateRunnableQueue.setRunHandler(this.runHandler);
        this.graphicsNode = graphicsNode;
        this.scriptingEnvironment = this.initializeScriptingEnvironment(this.bridgeContext);
        this.secondaryBridgeContexts = bridgeContext.getChildContexts().clone();
        this.secondaryScriptingEnvironments = new ScriptingEnvironment[this.secondaryBridgeContexts.length];
        for (int i = 0; i < this.secondaryBridgeContexts.length; ++i) {
            final BridgeContext bridgeContext2 = this.secondaryBridgeContexts[i];
            if (((SVGOMDocument)bridgeContext2.getDocument()).isSVG12()) {
                bridgeContext2.setUpdateManager(this);
                this.secondaryScriptingEnvironments[i] = this.initializeScriptingEnvironment(bridgeContext2);
            }
        }
        this.minRepaintTime = UpdateManager.MIN_REPAINT_TIME;
    }
    
    public int getMinRepaintTime() {
        return this.minRepaintTime;
    }
    
    public void setMinRepaintTime(final int minRepaintTime) {
        this.minRepaintTime = minRepaintTime;
    }
    
    protected ScriptingEnvironment initializeScriptingEnvironment(final BridgeContext bridgeContext) {
        final SVGOMDocument svgomDocument = (SVGOMDocument)bridgeContext.getDocument();
        ScriptingEnvironment scriptingEnvironment;
        if (svgomDocument.isSVG12()) {
            scriptingEnvironment = new SVG12ScriptingEnvironment(bridgeContext);
            svgomDocument.setXBLManager(bridgeContext.xblManager = new DefaultXBLManager(svgomDocument, bridgeContext));
        }
        else {
            scriptingEnvironment = new ScriptingEnvironment(bridgeContext);
        }
        return scriptingEnvironment;
    }
    
    public synchronized void dispatchSVGLoadEvent() throws InterruptedException {
        this.dispatchSVGLoadEvent(this.bridgeContext, this.scriptingEnvironment);
        for (int i = 0; i < this.secondaryScriptingEnvironments.length; ++i) {
            final BridgeContext bridgeContext = this.secondaryBridgeContexts[i];
            if (((SVGOMDocument)bridgeContext.getDocument()).isSVG12()) {
                this.dispatchSVGLoadEvent(bridgeContext, this.secondaryScriptingEnvironments[i]);
            }
        }
        this.secondaryBridgeContexts = null;
        this.secondaryScriptingEnvironments = null;
    }
    
    protected void dispatchSVGLoadEvent(final BridgeContext bridgeContext, final ScriptingEnvironment scriptingEnvironment) {
        scriptingEnvironment.loadScripts();
        scriptingEnvironment.dispatchSVGLoadEvent();
        if (bridgeContext.isSVG12() && bridgeContext.xblManager != null) {
            final SVG12BridgeContext svg12BridgeContext = (SVG12BridgeContext)bridgeContext;
            svg12BridgeContext.addBindingListener();
            svg12BridgeContext.xblManager.startProcessing();
        }
    }
    
    public void dispatchSVGZoomEvent() throws InterruptedException {
        this.scriptingEnvironment.dispatchSVGZoomEvent();
    }
    
    public void dispatchSVGScrollEvent() throws InterruptedException {
        this.scriptingEnvironment.dispatchSVGScrollEvent();
    }
    
    public void dispatchSVGResizeEvent() throws InterruptedException {
        this.scriptingEnvironment.dispatchSVGResizeEvent();
    }
    
    public void manageUpdates(final ImageRenderer imageRenderer) {
        this.updateRunnableQueue.preemptLater(new Runnable() {
            public void run() {
                synchronized (UpdateManager.this) {
                    UpdateManager.this.running = true;
                    UpdateManager.this.updateTracker = new UpdateTracker();
                    final RootGraphicsNode root = UpdateManager.this.graphicsNode.getRoot();
                    if (root != null) {
                        root.addTreeGraphicsNodeChangeListener(UpdateManager.this.updateTracker);
                    }
                    UpdateManager.this.repaintManager = new RepaintManager(imageRenderer);
                    UpdateManager.this.fireEvent(UpdateManager.startedDispatcher, new UpdateManagerEvent(UpdateManager.this, null, null));
                    UpdateManager.this.started = true;
                }
            }
        });
        this.resume();
    }
    
    public BridgeContext getBridgeContext() {
        return this.bridgeContext;
    }
    
    public RunnableQueue getUpdateRunnableQueue() {
        return this.updateRunnableQueue;
    }
    
    public RepaintManager getRepaintManager() {
        return this.repaintManager;
    }
    
    public UpdateTracker getUpdateTracker() {
        return this.updateTracker;
    }
    
    public Document getDocument() {
        return this.document;
    }
    
    public ScriptingEnvironment getScriptingEnvironment() {
        return this.scriptingEnvironment;
    }
    
    public synchronized boolean isRunning() {
        return this.running;
    }
    
    public synchronized void suspend() {
        if (this.updateRunnableQueue.getQueueState() == RunnableQueue.RUNNING) {
            this.updateRunnableQueue.suspendExecution(false);
        }
        this.suspendCalled = true;
    }
    
    public synchronized void resume() {
        if (this.updateRunnableQueue.getQueueState() != RunnableQueue.RUNNING) {
            this.updateRunnableQueue.resumeExecution();
        }
    }
    
    public void interrupt() {
        final Runnable runnable = new Runnable() {
            public void run() {
                synchronized (UpdateManager.this) {
                    if (UpdateManager.this.started) {
                        UpdateManager.this.dispatchSVGUnLoadEvent();
                    }
                    else {
                        UpdateManager.this.running = false;
                        UpdateManager.this.scriptingEnvironment.interrupt();
                        UpdateManager.this.updateRunnableQueue.getThread().halt();
                    }
                }
            }
        };
        try {
            this.updateRunnableQueue.preemptLater(runnable);
            this.updateRunnableQueue.resumeExecution();
        }
        catch (IllegalStateException ex) {}
    }
    
    public void dispatchSVGUnLoadEvent() {
        if (!this.started) {
            throw new IllegalStateException("UpdateManager not started.");
        }
        this.updateRunnableQueue.preemptLater(new Runnable() {
            public void run() {
                synchronized (UpdateManager.this) {
                    final AbstractEvent abstractEvent = (AbstractEvent)((DocumentEvent)UpdateManager.this.document).createEvent("SVGEvents");
                    String s;
                    if (UpdateManager.this.bridgeContext.isSVG12()) {
                        s = "unload";
                    }
                    else {
                        s = "SVGUnload";
                    }
                    abstractEvent.initEventNS("http://www.w3.org/2001/xml-events", s, false, false);
                    ((EventTarget)UpdateManager.this.document.getDocumentElement()).dispatchEvent(abstractEvent);
                    UpdateManager.this.running = false;
                    UpdateManager.this.scriptingEnvironment.interrupt();
                    UpdateManager.this.updateRunnableQueue.getThread().halt();
                    UpdateManager.this.bridgeContext.dispose();
                    UpdateManager.this.fireEvent(UpdateManager.stoppedDispatcher, new UpdateManagerEvent(UpdateManager.this, null, null));
                }
            }
        });
        this.resume();
    }
    
    public void updateRendering(final AffineTransform affineTransform, final boolean b, final Shape shape, final int n, final int n2) {
        this.repaintManager.setupRenderer(affineTransform, b, shape, n, n2);
        final ArrayList<Shape> list = new ArrayList<Shape>(1);
        list.add(shape);
        this.updateRendering(list, false);
    }
    
    public void updateRendering(final AffineTransform affineTransform, final boolean b, final boolean b2, final Shape shape, final int n, final int n2) {
        this.repaintManager.setupRenderer(affineTransform, b, shape, n, n2);
        final ArrayList<Shape> list = new ArrayList<Shape>(1);
        list.add(shape);
        this.updateRendering(list, b2);
    }
    
    protected void updateRendering(final List list, final boolean b) {
        try {
            this.fireEvent(UpdateManager.updateStartedDispatcher, new UpdateManagerEvent(this, this.repaintManager.getOffScreen(), null));
            this.fireEvent(UpdateManager.updateCompletedDispatcher, new UpdateManagerEvent(this, this.repaintManager.getOffScreen(), new ArrayList(this.repaintManager.updateRendering(list)), b));
        }
        catch (ThreadDeath threadDeath) {
            this.fireEvent(UpdateManager.updateFailedDispatcher, new UpdateManagerEvent(this, null, null));
            throw threadDeath;
        }
        catch (Throwable t) {
            this.fireEvent(UpdateManager.updateFailedDispatcher, new UpdateManagerEvent(this, null, null));
        }
    }
    
    protected void repaint() {
        if (!this.updateTracker.hasChanged()) {
            this.outOfDateTime = 0L;
            return;
        }
        final long currentTimeMillis = System.currentTimeMillis();
        if (currentTimeMillis < this.allResumeTime) {
            this.createRepaintTimer();
            return;
        }
        if (this.allResumeTime > 0L) {
            this.releaseAllRedrawSuspension();
        }
        if (currentTimeMillis - this.outOfDateTime < this.minRepaintTime) {
            synchronized (this.updateRunnableQueue.getIteratorLock()) {
                final Iterator iterator = this.updateRunnableQueue.iterator();
                while (iterator.hasNext()) {
                    if (!(iterator.next() instanceof NoRepaintRunnable)) {
                        return;
                    }
                }
            }
        }
        final List dirtyAreas = this.updateTracker.getDirtyAreas();
        this.updateTracker.clear();
        if (dirtyAreas != null) {
            this.updateRendering(dirtyAreas, false);
        }
        this.outOfDateTime = 0L;
    }
    
    public void forceRepaint() {
        if (!this.updateTracker.hasChanged()) {
            this.outOfDateTime = 0L;
            return;
        }
        final List dirtyAreas = this.updateTracker.getDirtyAreas();
        this.updateTracker.clear();
        if (dirtyAreas != null) {
            this.updateRendering(dirtyAreas, false);
        }
        this.outOfDateTime = 0L;
    }
    
    void createRepaintTimer() {
        if (this.repaintTimerTask != null) {
            return;
        }
        if (this.allResumeTime < 0L) {
            return;
        }
        if (this.repaintTriggerTimer == null) {
            this.repaintTriggerTimer = new Timer(true);
        }
        long delay = this.allResumeTime - System.currentTimeMillis();
        if (delay < 0L) {
            delay = 0L;
        }
        this.repaintTimerTask = new RepaintTimerTask(this);
        this.repaintTriggerTimer.schedule(this.repaintTimerTask, delay);
    }
    
    void resetRepaintTimer() {
        if (this.repaintTimerTask == null) {
            return;
        }
        if (this.allResumeTime < 0L) {
            return;
        }
        if (this.repaintTriggerTimer == null) {
            this.repaintTriggerTimer = new Timer(true);
        }
        long delay = this.allResumeTime - System.currentTimeMillis();
        if (delay < 0L) {
            delay = 0L;
        }
        this.repaintTimerTask = new RepaintTimerTask(this);
        this.repaintTriggerTimer.schedule(this.repaintTimerTask, delay);
    }
    
    int addRedrawSuspension(final int n) {
        final long allResumeTime = System.currentTimeMillis() + n;
        final SuspensionInfo suspensionInfo = new SuspensionInfo(this.nextSuspensionIndex++, allResumeTime);
        if (allResumeTime > this.allResumeTime) {
            this.allResumeTime = allResumeTime;
            this.resetRepaintTimer();
        }
        this.suspensionList.add(suspensionInfo);
        return suspensionInfo.getIndex();
    }
    
    void releaseAllRedrawSuspension() {
        this.suspensionList.clear();
        this.allResumeTime = -1L;
        this.resetRepaintTimer();
    }
    
    boolean releaseRedrawSuspension(final int n) {
        if (n > this.nextSuspensionIndex) {
            return false;
        }
        if (this.suspensionList.size() == 0) {
            return true;
        }
        int i = 0;
        int n2 = this.suspensionList.size() - 1;
        while (i < n2) {
            final int n3 = i + n2 >> 1;
            final int index = this.suspensionList.get(n3).getIndex();
            if (index == n) {
                n2 = (i = n3);
            }
            else if (index < n) {
                i = n3 + 1;
            }
            else {
                n2 = n3 - 1;
            }
        }
        final SuspensionInfo suspensionInfo = this.suspensionList.get(i);
        if (suspensionInfo.getIndex() != n) {
            return true;
        }
        this.suspensionList.remove(i);
        if (this.suspensionList.size() == 0) {
            this.allResumeTime = -1L;
            this.resetRepaintTimer();
        }
        else if (suspensionInfo.getResumeMilli() == this.allResumeTime) {
            this.allResumeTime = this.findNewAllResumeTime();
            this.resetRepaintTimer();
        }
        return true;
    }
    
    long findNewAllResumeTime() {
        long n = -1L;
        final Iterator<SuspensionInfo> iterator = this.suspensionList.iterator();
        while (iterator.hasNext()) {
            final long resumeMilli = iterator.next().getResumeMilli();
            if (resumeMilli > n) {
                n = resumeMilli;
            }
        }
        return n;
    }
    
    public void addUpdateManagerListener(final UpdateManagerListener updateManagerListener) {
        this.listeners.add(updateManagerListener);
    }
    
    public void removeUpdateManagerListener(final UpdateManagerListener updateManagerListener) {
        this.listeners.remove(updateManagerListener);
    }
    
    protected void fireEvent(final EventDispatcher.Dispatcher dispatcher, final Object o) {
        EventDispatcher.fireEvent(dispatcher, this.listeners, o, false);
    }
    
    protected RunnableQueue.RunHandler createRunHandler() {
        return new UpdateManagerRunHander();
    }
    
    static {
        int int1 = 20;
        try {
            int1 = Integer.parseInt(System.getProperty("org.apache.batik.min_repaint_time", "20"));
        }
        catch (SecurityException ex) {}
        catch (NumberFormatException ex2) {}
        finally {
            MIN_REPAINT_TIME = int1;
        }
        UpdateManager.startedDispatcher = new EventDispatcher.Dispatcher() {
            public void dispatch(final Object o, final Object o2) {
                ((UpdateManagerListener)o).managerStarted((UpdateManagerEvent)o2);
            }
        };
        UpdateManager.stoppedDispatcher = new EventDispatcher.Dispatcher() {
            public void dispatch(final Object o, final Object o2) {
                ((UpdateManagerListener)o).managerStopped((UpdateManagerEvent)o2);
            }
        };
        UpdateManager.suspendedDispatcher = new EventDispatcher.Dispatcher() {
            public void dispatch(final Object o, final Object o2) {
                ((UpdateManagerListener)o).managerSuspended((UpdateManagerEvent)o2);
            }
        };
        UpdateManager.resumedDispatcher = new EventDispatcher.Dispatcher() {
            public void dispatch(final Object o, final Object o2) {
                ((UpdateManagerListener)o).managerResumed((UpdateManagerEvent)o2);
            }
        };
        UpdateManager.updateStartedDispatcher = new EventDispatcher.Dispatcher() {
            public void dispatch(final Object o, final Object o2) {
                ((UpdateManagerListener)o).updateStarted((UpdateManagerEvent)o2);
            }
        };
        UpdateManager.updateCompletedDispatcher = new EventDispatcher.Dispatcher() {
            public void dispatch(final Object o, final Object o2) {
                ((UpdateManagerListener)o).updateCompleted((UpdateManagerEvent)o2);
            }
        };
        UpdateManager.updateFailedDispatcher = new EventDispatcher.Dispatcher() {
            public void dispatch(final Object o, final Object o2) {
                ((UpdateManagerListener)o).updateFailed((UpdateManagerEvent)o2);
            }
        };
    }
    
    protected class UpdateManagerRunHander extends RunnableQueue.RunHandlerAdapter
    {
        public void runnableStart(final RunnableQueue runnableQueue, final Runnable runnable) {
            if (UpdateManager.this.running && !(runnable instanceof NoRepaintRunnable) && UpdateManager.this.outOfDateTime == 0L) {
                UpdateManager.this.outOfDateTime = System.currentTimeMillis();
            }
        }
        
        public void runnableInvoked(final RunnableQueue runnableQueue, final Runnable runnable) {
            if (UpdateManager.this.running && !(runnable instanceof NoRepaintRunnable)) {
                UpdateManager.this.repaint();
            }
        }
        
        public void executionSuspended(final RunnableQueue runnableQueue) {
            synchronized (UpdateManager.this) {
                if (UpdateManager.this.suspendCalled) {
                    UpdateManager.this.running = false;
                    UpdateManager.this.fireEvent(UpdateManager.suspendedDispatcher, new UpdateManagerEvent(this, null, null));
                }
            }
        }
        
        public void executionResumed(final RunnableQueue runnableQueue) {
            synchronized (UpdateManager.this) {
                if (UpdateManager.this.suspendCalled && !UpdateManager.this.running) {
                    UpdateManager.this.running = true;
                    UpdateManager.this.suspendCalled = false;
                    UpdateManager.this.fireEvent(UpdateManager.resumedDispatcher, new UpdateManagerEvent(this, null, null));
                }
            }
        }
    }
    
    protected class RepaintTimerTask extends TimerTask
    {
        UpdateManager um;
        
        RepaintTimerTask(final UpdateManager um) {
            this.um = um;
        }
        
        public void run() {
            final RunnableQueue updateRunnableQueue = this.um.getUpdateRunnableQueue();
            if (updateRunnableQueue == null) {
                return;
            }
            updateRunnableQueue.invokeLater(new Runnable() {
                public void run() {
                }
            });
        }
    }
    
    protected class SuspensionInfo
    {
        int index;
        long resumeMilli;
        
        public SuspensionInfo(final int index, final long resumeMilli) {
            this.index = index;
            this.resumeMilli = resumeMilli;
        }
        
        public int getIndex() {
            return this.index;
        }
        
        public long getResumeMilli() {
            return this.resumeMilli;
        }
    }
}
