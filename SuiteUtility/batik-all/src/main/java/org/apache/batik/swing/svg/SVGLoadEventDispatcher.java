// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.swing.svg;

import org.apache.batik.bridge.InterruptedBridgeException;
import java.util.Collections;
import java.util.LinkedList;
import org.apache.batik.util.EventDispatcher;
import java.util.List;
import org.apache.batik.bridge.UpdateManager;
import org.apache.batik.bridge.BridgeContext;
import org.apache.batik.gvt.GraphicsNode;
import org.w3c.dom.svg.SVGDocument;
import org.apache.batik.util.HaltingThread;

public class SVGLoadEventDispatcher extends HaltingThread
{
    protected SVGDocument svgDocument;
    protected GraphicsNode root;
    protected BridgeContext bridgeContext;
    protected UpdateManager updateManager;
    protected List listeners;
    protected Exception exception;
    static EventDispatcher.Dispatcher startedDispatcher;
    static EventDispatcher.Dispatcher completedDispatcher;
    static EventDispatcher.Dispatcher cancelledDispatcher;
    static EventDispatcher.Dispatcher failedDispatcher;
    
    public SVGLoadEventDispatcher(final GraphicsNode root, final SVGDocument svgDocument, final BridgeContext bridgeContext, final UpdateManager updateManager) {
        this.listeners = Collections.synchronizedList(new LinkedList<Object>());
        this.svgDocument = svgDocument;
        this.root = root;
        this.bridgeContext = bridgeContext;
        this.updateManager = updateManager;
    }
    
    public void run() {
        final SVGLoadEventDispatcherEvent svgLoadEventDispatcherEvent = new SVGLoadEventDispatcherEvent(this, this.root);
        try {
            this.fireEvent(SVGLoadEventDispatcher.startedDispatcher, svgLoadEventDispatcherEvent);
            if (this.isHalted()) {
                this.fireEvent(SVGLoadEventDispatcher.cancelledDispatcher, svgLoadEventDispatcherEvent);
                return;
            }
            this.updateManager.dispatchSVGLoadEvent();
            if (this.isHalted()) {
                this.fireEvent(SVGLoadEventDispatcher.cancelledDispatcher, svgLoadEventDispatcherEvent);
                return;
            }
            this.fireEvent(SVGLoadEventDispatcher.completedDispatcher, svgLoadEventDispatcherEvent);
        }
        catch (InterruptedException ex) {
            this.fireEvent(SVGLoadEventDispatcher.cancelledDispatcher, svgLoadEventDispatcherEvent);
        }
        catch (InterruptedBridgeException ex2) {
            this.fireEvent(SVGLoadEventDispatcher.cancelledDispatcher, svgLoadEventDispatcherEvent);
        }
        catch (Exception exception) {
            this.exception = exception;
            this.fireEvent(SVGLoadEventDispatcher.failedDispatcher, svgLoadEventDispatcherEvent);
        }
        catch (ThreadDeath threadDeath) {
            this.exception = new Exception(threadDeath.getMessage());
            this.fireEvent(SVGLoadEventDispatcher.failedDispatcher, svgLoadEventDispatcherEvent);
            throw threadDeath;
        }
        catch (Throwable t) {
            t.printStackTrace();
            this.exception = new Exception(t.getMessage());
            this.fireEvent(SVGLoadEventDispatcher.failedDispatcher, svgLoadEventDispatcherEvent);
        }
    }
    
    public UpdateManager getUpdateManager() {
        return this.updateManager;
    }
    
    public Exception getException() {
        return this.exception;
    }
    
    public void addSVGLoadEventDispatcherListener(final SVGLoadEventDispatcherListener svgLoadEventDispatcherListener) {
        this.listeners.add(svgLoadEventDispatcherListener);
    }
    
    public void removeSVGLoadEventDispatcherListener(final SVGLoadEventDispatcherListener svgLoadEventDispatcherListener) {
        this.listeners.remove(svgLoadEventDispatcherListener);
    }
    
    public void fireEvent(final EventDispatcher.Dispatcher dispatcher, final Object o) {
        EventDispatcher.fireEvent(dispatcher, this.listeners, o, true);
    }
    
    static {
        SVGLoadEventDispatcher.startedDispatcher = new EventDispatcher.Dispatcher() {
            public void dispatch(final Object o, final Object o2) {
                ((SVGLoadEventDispatcherListener)o).svgLoadEventDispatchStarted((SVGLoadEventDispatcherEvent)o2);
            }
        };
        SVGLoadEventDispatcher.completedDispatcher = new EventDispatcher.Dispatcher() {
            public void dispatch(final Object o, final Object o2) {
                ((SVGLoadEventDispatcherListener)o).svgLoadEventDispatchCompleted((SVGLoadEventDispatcherEvent)o2);
            }
        };
        SVGLoadEventDispatcher.cancelledDispatcher = new EventDispatcher.Dispatcher() {
            public void dispatch(final Object o, final Object o2) {
                ((SVGLoadEventDispatcherListener)o).svgLoadEventDispatchCancelled((SVGLoadEventDispatcherEvent)o2);
            }
        };
        SVGLoadEventDispatcher.failedDispatcher = new EventDispatcher.Dispatcher() {
            public void dispatch(final Object o, final Object o2) {
                ((SVGLoadEventDispatcherListener)o).svgLoadEventDispatchFailed((SVGLoadEventDispatcherEvent)o2);
            }
        };
    }
}
