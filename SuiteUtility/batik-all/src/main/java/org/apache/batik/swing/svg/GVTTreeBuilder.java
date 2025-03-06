// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.swing.svg;

import org.apache.batik.bridge.BridgeException;
import org.apache.batik.bridge.InterruptedBridgeException;
import org.w3c.dom.Document;
import org.apache.batik.bridge.GVTBuilder;
import org.apache.batik.bridge.DynamicGVTBuilder;
import org.apache.batik.gvt.GraphicsNode;
import java.util.Collections;
import java.util.LinkedList;
import org.apache.batik.util.EventDispatcher;
import java.util.List;
import org.apache.batik.bridge.BridgeContext;
import org.w3c.dom.svg.SVGDocument;
import org.apache.batik.util.HaltingThread;

public class GVTTreeBuilder extends HaltingThread
{
    protected SVGDocument svgDocument;
    protected BridgeContext bridgeContext;
    protected List listeners;
    protected Exception exception;
    static EventDispatcher.Dispatcher startedDispatcher;
    static EventDispatcher.Dispatcher completedDispatcher;
    static EventDispatcher.Dispatcher cancelledDispatcher;
    static EventDispatcher.Dispatcher failedDispatcher;
    
    public GVTTreeBuilder(final SVGDocument svgDocument, final BridgeContext bridgeContext) {
        this.listeners = Collections.synchronizedList(new LinkedList<Object>());
        this.svgDocument = svgDocument;
        this.bridgeContext = bridgeContext;
    }
    
    public void run() {
        GVTTreeBuilderEvent gvtTreeBuilderEvent = new GVTTreeBuilderEvent(this, null);
        try {
            this.fireEvent(GVTTreeBuilder.startedDispatcher, gvtTreeBuilderEvent);
            if (this.isHalted()) {
                this.fireEvent(GVTTreeBuilder.cancelledDispatcher, gvtTreeBuilderEvent);
                return;
            }
            GVTBuilder gvtBuilder;
            if (this.bridgeContext.isDynamic()) {
                gvtBuilder = new DynamicGVTBuilder();
            }
            else {
                gvtBuilder = new GVTBuilder();
            }
            final GraphicsNode build = gvtBuilder.build(this.bridgeContext, (Document)this.svgDocument);
            if (this.isHalted()) {
                this.fireEvent(GVTTreeBuilder.cancelledDispatcher, gvtTreeBuilderEvent);
                return;
            }
            gvtTreeBuilderEvent = new GVTTreeBuilderEvent(this, build);
            this.fireEvent(GVTTreeBuilder.completedDispatcher, gvtTreeBuilderEvent);
        }
        catch (InterruptedBridgeException ex) {
            this.fireEvent(GVTTreeBuilder.cancelledDispatcher, gvtTreeBuilderEvent);
        }
        catch (BridgeException exception) {
            this.exception = exception;
            this.fireEvent(GVTTreeBuilder.failedDispatcher, new GVTTreeBuilderEvent(this, exception.getGraphicsNode()));
        }
        catch (Exception exception2) {
            this.exception = exception2;
            this.fireEvent(GVTTreeBuilder.failedDispatcher, gvtTreeBuilderEvent);
        }
        catch (ThreadDeath threadDeath) {
            this.exception = new Exception(threadDeath.getMessage());
            this.fireEvent(GVTTreeBuilder.failedDispatcher, gvtTreeBuilderEvent);
            throw threadDeath;
        }
        catch (Throwable t) {
            t.printStackTrace();
            this.exception = new Exception(t.getMessage());
            this.fireEvent(GVTTreeBuilder.failedDispatcher, gvtTreeBuilderEvent);
        }
    }
    
    public Exception getException() {
        return this.exception;
    }
    
    public void addGVTTreeBuilderListener(final GVTTreeBuilderListener gvtTreeBuilderListener) {
        this.listeners.add(gvtTreeBuilderListener);
    }
    
    public void removeGVTTreeBuilderListener(final GVTTreeBuilderListener gvtTreeBuilderListener) {
        this.listeners.remove(gvtTreeBuilderListener);
    }
    
    public void fireEvent(final EventDispatcher.Dispatcher dispatcher, final Object o) {
        EventDispatcher.fireEvent(dispatcher, this.listeners, o, true);
    }
    
    static {
        GVTTreeBuilder.startedDispatcher = new EventDispatcher.Dispatcher() {
            public void dispatch(final Object o, final Object o2) {
                ((GVTTreeBuilderListener)o).gvtBuildStarted((GVTTreeBuilderEvent)o2);
            }
        };
        GVTTreeBuilder.completedDispatcher = new EventDispatcher.Dispatcher() {
            public void dispatch(final Object o, final Object o2) {
                ((GVTTreeBuilderListener)o).gvtBuildCompleted((GVTTreeBuilderEvent)o2);
            }
        };
        GVTTreeBuilder.cancelledDispatcher = new EventDispatcher.Dispatcher() {
            public void dispatch(final Object o, final Object o2) {
                ((GVTTreeBuilderListener)o).gvtBuildCancelled((GVTTreeBuilderEvent)o2);
            }
        };
        GVTTreeBuilder.failedDispatcher = new EventDispatcher.Dispatcher() {
            public void dispatch(final Object o, final Object o2) {
                ((GVTTreeBuilderListener)o).gvtBuildFailed((GVTTreeBuilderEvent)o2);
            }
        };
    }
}
