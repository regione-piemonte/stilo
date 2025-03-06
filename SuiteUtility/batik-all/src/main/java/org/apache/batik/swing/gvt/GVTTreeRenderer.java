// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.swing.gvt;

import org.apache.batik.bridge.InterruptedBridgeException;
import java.awt.image.BufferedImage;
import java.util.Collections;
import java.util.LinkedList;
import org.apache.batik.util.EventDispatcher;
import java.util.List;
import java.awt.geom.AffineTransform;
import java.awt.Shape;
import org.apache.batik.gvt.renderer.ImageRenderer;
import org.apache.batik.util.HaltingThread;

public class GVTTreeRenderer extends HaltingThread
{
    protected ImageRenderer renderer;
    protected Shape areaOfInterest;
    protected int width;
    protected int height;
    protected AffineTransform user2DeviceTransform;
    protected boolean doubleBuffering;
    protected List listeners;
    static EventDispatcher.Dispatcher prepareDispatcher;
    static EventDispatcher.Dispatcher startedDispatcher;
    static EventDispatcher.Dispatcher cancelledDispatcher;
    static EventDispatcher.Dispatcher completedDispatcher;
    static EventDispatcher.Dispatcher failedDispatcher;
    
    public GVTTreeRenderer(final ImageRenderer renderer, final AffineTransform user2DeviceTransform, final boolean doubleBuffering, final Shape areaOfInterest, final int width, final int height) {
        this.listeners = Collections.synchronizedList(new LinkedList<Object>());
        this.renderer = renderer;
        this.areaOfInterest = areaOfInterest;
        this.user2DeviceTransform = user2DeviceTransform;
        this.doubleBuffering = doubleBuffering;
        this.width = width;
        this.height = height;
    }
    
    public void run() {
        GVTTreeRendererEvent gvtTreeRendererEvent = new GVTTreeRendererEvent(this, null);
        try {
            this.fireEvent(GVTTreeRenderer.prepareDispatcher, gvtTreeRendererEvent);
            this.renderer.setTransform(this.user2DeviceTransform);
            this.renderer.setDoubleBuffered(this.doubleBuffering);
            this.renderer.updateOffScreen(this.width, this.height);
            this.renderer.clearOffScreen();
            if (this.isHalted()) {
                this.fireEvent(GVTTreeRenderer.cancelledDispatcher, gvtTreeRendererEvent);
                return;
            }
            gvtTreeRendererEvent = new GVTTreeRendererEvent(this, this.renderer.getOffScreen());
            this.fireEvent(GVTTreeRenderer.startedDispatcher, gvtTreeRendererEvent);
            if (this.isHalted()) {
                this.fireEvent(GVTTreeRenderer.cancelledDispatcher, gvtTreeRendererEvent);
                return;
            }
            this.renderer.repaint(this.areaOfInterest);
            if (this.isHalted()) {
                this.fireEvent(GVTTreeRenderer.cancelledDispatcher, gvtTreeRendererEvent);
                return;
            }
            gvtTreeRendererEvent = new GVTTreeRendererEvent(this, this.renderer.getOffScreen());
            this.fireEvent(GVTTreeRenderer.completedDispatcher, gvtTreeRendererEvent);
        }
        catch (NoClassDefFoundError noClassDefFoundError) {}
        catch (InterruptedBridgeException ex) {
            this.fireEvent(GVTTreeRenderer.cancelledDispatcher, gvtTreeRendererEvent);
        }
        catch (ThreadDeath threadDeath) {
            this.fireEvent(GVTTreeRenderer.failedDispatcher, gvtTreeRendererEvent);
            throw threadDeath;
        }
        catch (Throwable t) {
            t.printStackTrace();
            this.fireEvent(GVTTreeRenderer.failedDispatcher, gvtTreeRendererEvent);
        }
    }
    
    public void fireEvent(final EventDispatcher.Dispatcher dispatcher, final Object o) {
        EventDispatcher.fireEvent(dispatcher, this.listeners, o, true);
    }
    
    public void addGVTTreeRendererListener(final GVTTreeRendererListener gvtTreeRendererListener) {
        this.listeners.add(gvtTreeRendererListener);
    }
    
    public void removeGVTTreeRendererListener(final GVTTreeRendererListener gvtTreeRendererListener) {
        this.listeners.remove(gvtTreeRendererListener);
    }
    
    static {
        GVTTreeRenderer.prepareDispatcher = new EventDispatcher.Dispatcher() {
            public void dispatch(final Object o, final Object o2) {
                ((GVTTreeRendererListener)o).gvtRenderingPrepare((GVTTreeRendererEvent)o2);
            }
        };
        GVTTreeRenderer.startedDispatcher = new EventDispatcher.Dispatcher() {
            public void dispatch(final Object o, final Object o2) {
                ((GVTTreeRendererListener)o).gvtRenderingStarted((GVTTreeRendererEvent)o2);
            }
        };
        GVTTreeRenderer.cancelledDispatcher = new EventDispatcher.Dispatcher() {
            public void dispatch(final Object o, final Object o2) {
                ((GVTTreeRendererListener)o).gvtRenderingCancelled((GVTTreeRendererEvent)o2);
            }
        };
        GVTTreeRenderer.completedDispatcher = new EventDispatcher.Dispatcher() {
            public void dispatch(final Object o, final Object o2) {
                ((GVTTreeRendererListener)o).gvtRenderingCompleted((GVTTreeRendererEvent)o2);
            }
        };
        GVTTreeRenderer.failedDispatcher = new EventDispatcher.Dispatcher() {
            public void dispatch(final Object o, final Object o2) {
                ((GVTTreeRendererListener)o).gvtRenderingFailed((GVTTreeRendererEvent)o2);
            }
        };
    }
}
