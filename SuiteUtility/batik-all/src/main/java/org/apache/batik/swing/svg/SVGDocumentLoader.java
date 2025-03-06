// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.swing.svg;

import java.io.InterruptedIOException;
import org.w3c.dom.svg.SVGDocument;
import java.util.Collections;
import java.util.LinkedList;
import org.apache.batik.util.EventDispatcher;
import java.util.List;
import org.apache.batik.bridge.DocumentLoader;
import org.apache.batik.util.HaltingThread;

public class SVGDocumentLoader extends HaltingThread
{
    protected String url;
    protected DocumentLoader loader;
    protected Exception exception;
    protected List listeners;
    static EventDispatcher.Dispatcher startedDispatcher;
    static EventDispatcher.Dispatcher completedDispatcher;
    static EventDispatcher.Dispatcher cancelledDispatcher;
    static EventDispatcher.Dispatcher failedDispatcher;
    
    public SVGDocumentLoader(final String url, final DocumentLoader loader) {
        this.listeners = Collections.synchronizedList(new LinkedList<Object>());
        this.url = url;
        this.loader = loader;
    }
    
    public void run() {
        SVGDocumentLoaderEvent svgDocumentLoaderEvent = new SVGDocumentLoaderEvent(this, null);
        try {
            this.fireEvent(SVGDocumentLoader.startedDispatcher, svgDocumentLoaderEvent);
            if (this.isHalted()) {
                this.fireEvent(SVGDocumentLoader.cancelledDispatcher, svgDocumentLoaderEvent);
                return;
            }
            final SVGDocument svgDocument = (SVGDocument)this.loader.loadDocument(this.url);
            if (this.isHalted()) {
                this.fireEvent(SVGDocumentLoader.cancelledDispatcher, svgDocumentLoaderEvent);
                return;
            }
            svgDocumentLoaderEvent = new SVGDocumentLoaderEvent(this, svgDocument);
            this.fireEvent(SVGDocumentLoader.completedDispatcher, svgDocumentLoaderEvent);
        }
        catch (InterruptedIOException ex) {
            this.fireEvent(SVGDocumentLoader.cancelledDispatcher, svgDocumentLoaderEvent);
        }
        catch (Exception exception) {
            this.exception = exception;
            this.fireEvent(SVGDocumentLoader.failedDispatcher, svgDocumentLoaderEvent);
        }
        catch (ThreadDeath threadDeath) {
            this.exception = new Exception(threadDeath.getMessage());
            this.fireEvent(SVGDocumentLoader.failedDispatcher, svgDocumentLoaderEvent);
            throw threadDeath;
        }
        catch (Throwable t) {
            t.printStackTrace();
            this.exception = new Exception(t.getMessage());
            this.fireEvent(SVGDocumentLoader.failedDispatcher, svgDocumentLoaderEvent);
        }
    }
    
    public Exception getException() {
        return this.exception;
    }
    
    public void addSVGDocumentLoaderListener(final SVGDocumentLoaderListener svgDocumentLoaderListener) {
        this.listeners.add(svgDocumentLoaderListener);
    }
    
    public void removeSVGDocumentLoaderListener(final SVGDocumentLoaderListener svgDocumentLoaderListener) {
        this.listeners.remove(svgDocumentLoaderListener);
    }
    
    public void fireEvent(final EventDispatcher.Dispatcher dispatcher, final Object o) {
        EventDispatcher.fireEvent(dispatcher, this.listeners, o, true);
    }
    
    static {
        SVGDocumentLoader.startedDispatcher = new EventDispatcher.Dispatcher() {
            public void dispatch(final Object o, final Object o2) {
                ((SVGDocumentLoaderListener)o).documentLoadingStarted((SVGDocumentLoaderEvent)o2);
            }
        };
        SVGDocumentLoader.completedDispatcher = new EventDispatcher.Dispatcher() {
            public void dispatch(final Object o, final Object o2) {
                ((SVGDocumentLoaderListener)o).documentLoadingCompleted((SVGDocumentLoaderEvent)o2);
            }
        };
        SVGDocumentLoader.cancelledDispatcher = new EventDispatcher.Dispatcher() {
            public void dispatch(final Object o, final Object o2) {
                ((SVGDocumentLoaderListener)o).documentLoadingCancelled((SVGDocumentLoaderEvent)o2);
            }
        };
        SVGDocumentLoader.failedDispatcher = new EventDispatcher.Dispatcher() {
            public void dispatch(final Object o, final Object o2) {
                ((SVGDocumentLoaderListener)o).documentLoadingFailed((SVGDocumentLoaderEvent)o2);
            }
        };
    }
}
