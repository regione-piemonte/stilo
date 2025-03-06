// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.swing.svg;

import java.awt.event.ComponentEvent;
import java.awt.event.ComponentAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.KeyEvent;
import org.apache.batik.bridge.UpdateManagerEvent;
import org.apache.batik.swing.gvt.GVTTreeRendererEvent;
import java.awt.EventQueue;
import java.net.URL;
import org.w3c.dom.Node;
import org.apache.batik.bridge.RelaxedExternalResourceSecurity;
import org.apache.batik.bridge.ExternalResourceSecurity;
import org.apache.batik.bridge.DefaultScriptSecurity;
import org.apache.batik.bridge.ScriptSecurity;
import org.apache.batik.bridge.BridgeExtension;
import java.awt.Point;
import org.apache.batik.util.XMLResourceDescriptor;
import org.apache.batik.gvt.text.Mark;
import java.awt.Cursor;
import org.apache.batik.dom.util.XLinkSupport;
import org.w3c.dom.svg.SVGAElement;
import org.apache.batik.gvt.event.EventDispatcher;
import java.util.HashMap;
import java.util.Map;
import org.apache.batik.util.SVGFeatureStrings;
import java.util.HashSet;
import org.apache.batik.gvt.event.AWTEventDispatcher;
import java.awt.image.BufferedImage;
import org.apache.batik.swing.gvt.AbstractJGVTComponent;
import javax.swing.Icon;
import java.awt.Component;
import javax.swing.JOptionPane;
import org.apache.batik.bridge.UpdateManagerListener;
import org.apache.batik.util.RunnableQueue;
import java.awt.Shape;
import org.apache.batik.bridge.BridgeException;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.geom.Point2D;
import org.w3c.dom.Element;
import org.apache.batik.bridge.ViewBox;
import org.apache.batik.gvt.CompositeGraphicsNode;
import org.apache.batik.gvt.CanvasGraphicsNode;
import org.apache.batik.gvt.renderer.ImageRenderer;
import org.apache.batik.bridge.svg12.SVG12BridgeContext;
import org.w3c.dom.svg.SVGSVGElement;
import java.awt.event.ComponentListener;
import org.apache.batik.swing.gvt.JGVTComponentListener;
import org.apache.batik.gvt.GraphicsNode;
import org.apache.batik.ext.awt.image.spi.ImageTagRegistry;
import java.awt.geom.Dimension2D;
import java.awt.Rectangle;
import org.apache.batik.dom.svg.SVGOMDocument;
import org.apache.batik.dom.util.DOMUtilities;
import org.apache.batik.dom.svg.SVGDOMImplementation;
import java.util.Iterator;
import org.apache.batik.util.ParsedURL;
import org.w3c.dom.Document;
import java.util.LinkedList;
import java.util.Set;
import java.awt.geom.AffineTransform;
import java.awt.Dimension;
import org.apache.batik.bridge.BridgeContext;
import org.apache.batik.bridge.UserAgent;
import java.util.List;
import org.w3c.dom.svg.SVGDocument;
import org.apache.batik.bridge.UpdateManager;
import org.apache.batik.bridge.DocumentLoader;
import org.apache.batik.swing.gvt.JGVTComponent;

public class AbstractJSVGComponent extends JGVTComponent
{
    public static final int AUTODETECT = 0;
    public static final int ALWAYS_DYNAMIC = 1;
    public static final int ALWAYS_STATIC = 2;
    public static final int ALWAYS_INTERACTIVE = 3;
    public static final String SCRIPT_ALERT = "script.alert";
    public static final String SCRIPT_PROMPT = "script.prompt";
    public static final String SCRIPT_CONFIRM = "script.confirm";
    public static final String BROKEN_LINK_TITLE = "broken.link.title";
    protected SVGDocumentLoader documentLoader;
    protected SVGDocumentLoader nextDocumentLoader;
    protected DocumentLoader loader;
    protected GVTTreeBuilder gvtTreeBuilder;
    protected GVTTreeBuilder nextGVTTreeBuilder;
    protected SVGLoadEventDispatcher svgLoadEventDispatcher;
    protected UpdateManager updateManager;
    protected UpdateManager nextUpdateManager;
    protected SVGDocument svgDocument;
    protected List svgDocumentLoaderListeners;
    protected List gvtTreeBuilderListeners;
    protected List svgLoadEventDispatcherListeners;
    protected List linkActivationListeners;
    protected List updateManagerListeners;
    protected UserAgent userAgent;
    protected SVGUserAgent svgUserAgent;
    protected BridgeContext bridgeContext;
    protected String fragmentIdentifier;
    protected boolean isDynamicDocument;
    protected boolean isInteractiveDocument;
    protected boolean selfCallingDisableInteractions;
    protected boolean userSetDisableInteractions;
    protected int documentState;
    protected Dimension prevComponentSize;
    protected Runnable afterStopRunnable;
    protected SVGUpdateOverlay updateOverlay;
    protected boolean recenterOnResize;
    protected AffineTransform viewingTransform;
    protected int animationLimitingMode;
    protected float animationLimitingAmount;
    protected JSVGComponentListener jsvgComponentListener;
    protected static final Set FEATURES;
    
    public AbstractJSVGComponent() {
        this(null, false, false);
    }
    
    public AbstractJSVGComponent(final SVGUserAgent svgUserAgent, final boolean b, final boolean b2) {
        super(b, b2);
        this.svgDocumentLoaderListeners = new LinkedList();
        this.gvtTreeBuilderListeners = new LinkedList();
        this.svgLoadEventDispatcherListeners = new LinkedList();
        this.linkActivationListeners = new LinkedList();
        this.updateManagerListeners = new LinkedList();
        this.selfCallingDisableInteractions = false;
        this.userSetDisableInteractions = false;
        this.afterStopRunnable = null;
        this.recenterOnResize = true;
        this.viewingTransform = null;
        this.jsvgComponentListener = new JSVGComponentListener();
        this.svgUserAgent = svgUserAgent;
        this.userAgent = new BridgeUserAgentWrapper(this.createUserAgent());
        this.addSVGDocumentLoaderListener((SVGDocumentLoaderListener)this.listener);
        this.addGVTTreeBuilderListener((GVTTreeBuilderListener)this.listener);
        this.addSVGLoadEventDispatcherListener((SVGLoadEventDispatcherListener)this.listener);
        if (this.updateOverlay != null) {
            this.getOverlays().add(this.updateOverlay);
        }
    }
    
    public void dispose() {
        this.setSVGDocument(null);
    }
    
    public void setDisableInteractions(final boolean disableInteractions) {
        super.setDisableInteractions(disableInteractions);
        if (!this.selfCallingDisableInteractions) {
            this.userSetDisableInteractions = true;
        }
    }
    
    public void clearUserSetDisableInteractions() {
        this.userSetDisableInteractions = false;
        this.updateZoomAndPanEnable((Document)this.svgDocument);
    }
    
    public void updateZoomAndPanEnable(final Document document) {
        if (this.userSetDisableInteractions) {
            return;
        }
        if (document == null) {
            return;
        }
        try {
            final boolean equals = "magnify".equals(document.getDocumentElement().getAttributeNS(null, "zoomAndPan"));
            this.selfCallingDisableInteractions = true;
            this.setDisableInteractions(!equals);
        }
        finally {
            this.selfCallingDisableInteractions = false;
        }
    }
    
    public boolean getRecenterOnResize() {
        return this.recenterOnResize;
    }
    
    public void setRecenterOnResize(final boolean recenterOnResize) {
        this.recenterOnResize = recenterOnResize;
    }
    
    public boolean isDynamic() {
        return this.isDynamicDocument;
    }
    
    public boolean isInteractive() {
        return this.isInteractiveDocument;
    }
    
    public void setDocumentState(final int documentState) {
        this.documentState = documentState;
    }
    
    public UpdateManager getUpdateManager() {
        if (this.svgLoadEventDispatcher != null) {
            return this.svgLoadEventDispatcher.getUpdateManager();
        }
        if (this.nextUpdateManager != null) {
            return this.nextUpdateManager;
        }
        return this.updateManager;
    }
    
    public void resumeProcessing() {
        if (this.updateManager != null) {
            this.updateManager.resume();
        }
    }
    
    public void suspendProcessing() {
        if (this.updateManager != null) {
            this.updateManager.suspend();
        }
    }
    
    public void stopProcessing() {
        this.nextDocumentLoader = null;
        this.nextGVTTreeBuilder = null;
        if (this.documentLoader != null) {
            this.documentLoader.halt();
        }
        if (this.gvtTreeBuilder != null) {
            this.gvtTreeBuilder.halt();
        }
        if (this.svgLoadEventDispatcher != null) {
            this.svgLoadEventDispatcher.halt();
        }
        if (this.nextUpdateManager != null) {
            this.nextUpdateManager.interrupt();
            this.nextUpdateManager = null;
        }
        if (this.updateManager != null) {
            this.updateManager.interrupt();
        }
        super.stopProcessing();
    }
    
    public void loadSVGDocument(final String s) {
        String url = null;
        if (this.svgDocument != null) {
            url = this.svgDocument.getURL();
        }
        this.stopThenRun(new Runnable() {
            private final /* synthetic */ ParsedURL val$newURI = new ParsedURL(url, s);
            
            public void run() {
                final String string = this.val$newURI.toString();
                AbstractJSVGComponent.this.fragmentIdentifier = this.val$newURI.getRef();
                AbstractJSVGComponent.this.loader = new DocumentLoader(AbstractJSVGComponent.this.userAgent);
                (AbstractJSVGComponent.this.nextDocumentLoader = new SVGDocumentLoader(string, AbstractJSVGComponent.this.loader)).setPriority(1);
                final Iterator<SVGDocumentLoaderListener> iterator = AbstractJSVGComponent.this.svgDocumentLoaderListeners.iterator();
                while (iterator.hasNext()) {
                    AbstractJSVGComponent.this.nextDocumentLoader.addSVGDocumentLoaderListener(iterator.next());
                }
                AbstractJSVGComponent.this.startDocumentLoader();
            }
        });
    }
    
    private void startDocumentLoader() {
        this.documentLoader = this.nextDocumentLoader;
        this.nextDocumentLoader = null;
        this.documentLoader.start();
    }
    
    public void setDocument(Document deepCloneDocument) {
        if (deepCloneDocument != null && !(deepCloneDocument.getImplementation() instanceof SVGDOMImplementation)) {
            deepCloneDocument = DOMUtilities.deepCloneDocument(deepCloneDocument, SVGDOMImplementation.getDOMImplementation());
        }
        this.setSVGDocument((SVGDocument)deepCloneDocument);
    }
    
    public void setSVGDocument(SVGDocument svgDocument) {
        if (svgDocument != null && !(svgDocument.getImplementation() instanceof SVGDOMImplementation)) {
            svgDocument = (SVGDocument)DOMUtilities.deepCloneDocument((Document)svgDocument, SVGDOMImplementation.getDOMImplementation());
        }
        this.stopThenRun(new Runnable() {
            public void run() {
                AbstractJSVGComponent.this.installSVGDocument(svgDocument);
            }
        });
    }
    
    protected void stopThenRun(final Runnable runnable) {
        if (this.afterStopRunnable != null) {
            this.afterStopRunnable = runnable;
            return;
        }
        this.afterStopRunnable = runnable;
        this.stopProcessing();
        if (this.documentLoader == null && this.gvtTreeBuilder == null && this.gvtTreeRenderer == null && this.svgLoadEventDispatcher == null && this.nextUpdateManager == null && this.updateManager == null) {
            final Runnable afterStopRunnable = this.afterStopRunnable;
            this.afterStopRunnable = null;
            afterStopRunnable.run();
        }
    }
    
    protected void installSVGDocument(final SVGDocument svgDocument) {
        this.svgDocument = svgDocument;
        if (this.bridgeContext != null) {
            this.bridgeContext.dispose();
            this.bridgeContext = null;
        }
        this.releaseRenderingReferences();
        if (svgDocument == null) {
            this.isDynamicDocument = false;
            this.isInteractiveDocument = false;
            this.disableInteractions = true;
            this.setRenderingTransform(this.initialTransform = new AffineTransform(), false);
            final Rectangle renderRect = this.getRenderRect();
            this.repaint(renderRect.x, renderRect.y, renderRect.width, renderRect.height);
            return;
        }
        this.bridgeContext = this.createBridgeContext((SVGOMDocument)svgDocument);
        switch (this.documentState) {
            case 2: {
                this.isDynamicDocument = false;
                this.isInteractiveDocument = false;
                break;
            }
            case 3: {
                this.isDynamicDocument = false;
                this.isInteractiveDocument = true;
                break;
            }
            case 1: {
                this.isDynamicDocument = true;
                this.isInteractiveDocument = true;
                break;
            }
            case 0: {
                this.isDynamicDocument = this.bridgeContext.isDynamicDocument((Document)svgDocument);
                this.isInteractiveDocument = (this.isDynamicDocument || this.bridgeContext.isInteractiveDocument((Document)svgDocument));
                break;
            }
        }
        if (this.isInteractiveDocument) {
            if (this.isDynamicDocument) {
                this.bridgeContext.setDynamicState(2);
            }
            else {
                this.bridgeContext.setDynamicState(1);
            }
        }
        this.setBridgeContextAnimationLimitingMode();
        this.updateZoomAndPanEnable((Document)svgDocument);
        (this.nextGVTTreeBuilder = new GVTTreeBuilder(svgDocument, this.bridgeContext)).setPriority(1);
        final Iterator<GVTTreeBuilderListener> iterator = this.gvtTreeBuilderListeners.iterator();
        while (iterator.hasNext()) {
            this.nextGVTTreeBuilder.addGVTTreeBuilderListener(iterator.next());
        }
        this.initializeEventHandling();
        if (this.gvtTreeBuilder == null && this.documentLoader == null && this.gvtTreeRenderer == null && this.svgLoadEventDispatcher == null && this.updateManager == null) {
            this.startGVTTreeBuilder();
        }
    }
    
    protected void startGVTTreeBuilder() {
        this.gvtTreeBuilder = this.nextGVTTreeBuilder;
        this.nextGVTTreeBuilder = null;
        this.gvtTreeBuilder.start();
    }
    
    public SVGDocument getSVGDocument() {
        return this.svgDocument;
    }
    
    public Dimension2D getSVGDocumentSize() {
        return this.bridgeContext.getDocumentSize();
    }
    
    public String getFragmentIdentifier() {
        return this.fragmentIdentifier;
    }
    
    public void setFragmentIdentifier(final String fragmentIdentifier) {
        this.fragmentIdentifier = fragmentIdentifier;
        if (this.computeRenderingTransform()) {
            this.scheduleGVTRendering();
        }
    }
    
    public void flushImageCache() {
        ImageTagRegistry.getRegistry().flushCache();
    }
    
    public void setGraphicsNode(final GraphicsNode graphicsNode, final boolean b) {
        final Dimension2D documentSize = this.bridgeContext.getDocumentSize();
        this.setMySize(new Dimension((int)documentSize.getWidth(), (int)documentSize.getHeight()));
        final SVGSVGElement rootElement = this.svgDocument.getRootElement();
        this.prevComponentSize = this.getSize();
        this.getCanvasGraphicsNode(graphicsNode).setViewingTransform(this.calculateViewingTransform(this.fragmentIdentifier, rootElement));
        this.viewingTransform = null;
        this.setRenderingTransform(this.initialTransform = new AffineTransform(), false);
        this.jsvgComponentListener.updateMatrix(this.initialTransform);
        this.addJGVTComponentListener(this.jsvgComponentListener);
        this.addComponentListener(this.jsvgComponentListener);
        super.setGraphicsNode(graphicsNode, b);
    }
    
    protected BridgeContext createBridgeContext(final SVGOMDocument svgomDocument) {
        if (this.loader == null) {
            this.loader = new DocumentLoader(this.userAgent);
        }
        BridgeContext bridgeContext;
        if (svgomDocument.isSVG12()) {
            bridgeContext = new SVG12BridgeContext(this.userAgent, this.loader);
        }
        else {
            bridgeContext = new BridgeContext(this.userAgent, this.loader);
        }
        return bridgeContext;
    }
    
    protected void startSVGLoadEventDispatcher(final GraphicsNode graphicsNode) {
        this.svgLoadEventDispatcher = new SVGLoadEventDispatcher(graphicsNode, this.svgDocument, this.bridgeContext, new UpdateManager(this.bridgeContext, graphicsNode, (Document)this.svgDocument));
        final Iterator<SVGLoadEventDispatcherListener> iterator = this.svgLoadEventDispatcherListeners.iterator();
        while (iterator.hasNext()) {
            this.svgLoadEventDispatcher.addSVGLoadEventDispatcherListener(iterator.next());
        }
        this.svgLoadEventDispatcher.start();
    }
    
    protected ImageRenderer createImageRenderer() {
        if (this.isDynamicDocument) {
            return this.rendererFactory.createDynamicImageRenderer();
        }
        return this.rendererFactory.createStaticImageRenderer();
    }
    
    public CanvasGraphicsNode getCanvasGraphicsNode() {
        return this.getCanvasGraphicsNode(this.gvtRoot);
    }
    
    protected CanvasGraphicsNode getCanvasGraphicsNode(GraphicsNode graphicsNode) {
        if (!(graphicsNode instanceof CompositeGraphicsNode)) {
            return null;
        }
        final List children = ((CompositeGraphicsNode)graphicsNode).getChildren();
        if (children.size() == 0) {
            return null;
        }
        graphicsNode = children.get(0);
        if (!(graphicsNode instanceof CanvasGraphicsNode)) {
            return null;
        }
        return (CanvasGraphicsNode)graphicsNode;
    }
    
    public AffineTransform getViewingTransform() {
        AffineTransform affineTransform;
        synchronized (this) {
            affineTransform = this.viewingTransform;
            if (affineTransform == null) {
                final CanvasGraphicsNode canvasGraphicsNode = this.getCanvasGraphicsNode();
                if (canvasGraphicsNode != null) {
                    affineTransform = canvasGraphicsNode.getViewingTransform();
                }
            }
        }
        return affineTransform;
    }
    
    public AffineTransform getViewBoxTransform() {
        final AffineTransform renderingTransform = this.getRenderingTransform();
        AffineTransform affineTransform;
        if (renderingTransform == null) {
            affineTransform = new AffineTransform();
        }
        else {
            affineTransform = new AffineTransform(renderingTransform);
        }
        final AffineTransform viewingTransform = this.getViewingTransform();
        if (viewingTransform != null) {
            affineTransform.concatenate(viewingTransform);
        }
        return affineTransform;
    }
    
    protected boolean computeRenderingTransform() {
        if (this.svgDocument == null || this.gvtRoot == null) {
            return false;
        }
        boolean updateRenderingTransform = this.updateRenderingTransform();
        this.initialTransform = new AffineTransform();
        if (!this.initialTransform.equals(this.getRenderingTransform())) {
            this.setRenderingTransform(this.initialTransform, false);
            updateRenderingTransform = true;
        }
        return updateRenderingTransform;
    }
    
    protected AffineTransform calculateViewingTransform(final String s, final SVGSVGElement svgsvgElement) {
        final Dimension size = this.getSize();
        if (size.width < 1) {
            size.width = 1;
        }
        if (size.height < 1) {
            size.height = 1;
        }
        return ViewBox.getViewTransform(s, (Element)svgsvgElement, (float)size.width, (float)size.height, this.bridgeContext);
    }
    
    protected boolean updateRenderingTransform() {
        if (this.svgDocument == null || this.gvtRoot == null) {
            return false;
        }
        try {
            final SVGSVGElement rootElement = this.svgDocument.getRootElement();
            final Dimension size = this.getSize();
            Dimension prevComponentSize = this.prevComponentSize;
            if (prevComponentSize == null) {
                prevComponentSize = size;
            }
            this.prevComponentSize = size;
            if (size.width < 1) {
                size.width = 1;
            }
            if (size.height < 1) {
                size.height = 1;
            }
            final AffineTransform calculateViewingTransform = this.calculateViewingTransform(this.fragmentIdentifier, rootElement);
            final AffineTransform viewingTransform = this.getViewingTransform();
            if (calculateViewingTransform.equals(viewingTransform)) {
                return prevComponentSize.width != size.width || prevComponentSize.height != size.height;
            }
            if (!this.recenterOnResize) {
                return true;
            }
            Point2D point2D = new Point2D.Float(prevComponentSize.width / 2.0f, prevComponentSize.height / 2.0f);
            final AffineTransform renderingTransform = this.getRenderingTransform();
            if (renderingTransform != null) {
                try {
                    point2D = renderingTransform.createInverse().transform(point2D, null);
                }
                catch (NoninvertibleTransformException ex2) {}
            }
            if (viewingTransform != null) {
                try {
                    point2D = viewingTransform.createInverse().transform(point2D, null);
                }
                catch (NoninvertibleTransformException ex3) {}
            }
            if (calculateViewingTransform != null) {
                point2D = calculateViewingTransform.transform(point2D, null);
            }
            if (renderingTransform != null) {
                point2D = renderingTransform.transform(point2D, null);
            }
            final float n = (float)(size.width / 2.0f - point2D.getX());
            final float n2 = (float)(size.height / 2.0f - point2D.getY());
            final float n3 = (float)(int)((n < 0.0f) ? (n - 0.5) : (n + 0.5));
            final float n4 = (float)(int)((n2 < 0.0f) ? (n2 - 0.5) : (n2 + 0.5));
            if (n3 != 0.0f || n4 != 0.0f) {
                renderingTransform.preConcatenate(AffineTransform.getTranslateInstance(n3, n4));
                this.setRenderingTransform(renderingTransform, false);
            }
            synchronized (this) {
                this.viewingTransform = calculateViewingTransform;
            }
            final Runnable runnable = new Runnable() {
                AffineTransform myAT = calculateViewingTransform;
                CanvasGraphicsNode myCGN = AbstractJSVGComponent.this.getCanvasGraphicsNode();
                
                public void run() {
                    synchronized (AbstractJSVGComponent.this) {
                        this.myCGN.setViewingTransform(this.myAT);
                        if (AbstractJSVGComponent.this.viewingTransform == this.myAT) {
                            AbstractJSVGComponent.this.viewingTransform = null;
                        }
                    }
                }
            };
            final UpdateManager updateManager = this.getUpdateManager();
            if (updateManager != null) {
                updateManager.getUpdateRunnableQueue().invokeLater(runnable);
            }
            else {
                runnable.run();
            }
        }
        catch (BridgeException ex) {
            this.userAgent.displayError(ex);
        }
        return true;
    }
    
    protected void renderGVTTree() {
        if (!this.isInteractiveDocument || this.updateManager == null || !this.updateManager.isRunning()) {
            super.renderGVTTree();
            return;
        }
        final Rectangle renderRect = this.getRenderRect();
        if (this.gvtRoot == null || renderRect.width <= 0 || renderRect.height <= 0) {
            return;
        }
        AffineTransform inverse = null;
        try {
            inverse = this.renderingTransform.createInverse();
        }
        catch (NoninvertibleTransformException ex) {}
        Shape transformedShape;
        if (inverse == null) {
            transformedShape = renderRect;
        }
        else {
            transformedShape = inverse.createTransformedShape(renderRect);
        }
        final RunnableQueue updateRunnableQueue = this.updateManager.getUpdateRunnableQueue();
        synchronized (updateRunnableQueue.getIteratorLock()) {
            for (final UpdateRenderingRunnable next : updateRunnableQueue) {
                if (next instanceof UpdateRenderingRunnable) {
                    next.deactivate();
                }
            }
        }
        updateRunnableQueue.invokeLater(new UpdateRenderingRunnable(this.renderingTransform, this.doubleBufferedRendering, true, transformedShape, renderRect.width, renderRect.height));
    }
    
    protected void handleException(final Exception ex) {
        this.userAgent.displayError(ex);
    }
    
    public void addSVGDocumentLoaderListener(final SVGDocumentLoaderListener svgDocumentLoaderListener) {
        this.svgDocumentLoaderListeners.add(svgDocumentLoaderListener);
    }
    
    public void removeSVGDocumentLoaderListener(final SVGDocumentLoaderListener svgDocumentLoaderListener) {
        this.svgDocumentLoaderListeners.remove(svgDocumentLoaderListener);
    }
    
    public void addGVTTreeBuilderListener(final GVTTreeBuilderListener gvtTreeBuilderListener) {
        this.gvtTreeBuilderListeners.add(gvtTreeBuilderListener);
    }
    
    public void removeGVTTreeBuilderListener(final GVTTreeBuilderListener gvtTreeBuilderListener) {
        this.gvtTreeBuilderListeners.remove(gvtTreeBuilderListener);
    }
    
    public void addSVGLoadEventDispatcherListener(final SVGLoadEventDispatcherListener svgLoadEventDispatcherListener) {
        this.svgLoadEventDispatcherListeners.add(svgLoadEventDispatcherListener);
    }
    
    public void removeSVGLoadEventDispatcherListener(final SVGLoadEventDispatcherListener svgLoadEventDispatcherListener) {
        this.svgLoadEventDispatcherListeners.remove(svgLoadEventDispatcherListener);
    }
    
    public void addLinkActivationListener(final LinkActivationListener linkActivationListener) {
        this.linkActivationListeners.add(linkActivationListener);
    }
    
    public void removeLinkActivationListener(final LinkActivationListener linkActivationListener) {
        this.linkActivationListeners.remove(linkActivationListener);
    }
    
    public void addUpdateManagerListener(final UpdateManagerListener updateManagerListener) {
        this.updateManagerListeners.add(updateManagerListener);
    }
    
    public void removeUpdateManagerListener(final UpdateManagerListener updateManagerListener) {
        this.updateManagerListeners.remove(updateManagerListener);
    }
    
    public void showAlert(final String s) {
        JOptionPane.showMessageDialog(this, Messages.formatMessage("script.alert", new Object[] { s }));
    }
    
    public String showPrompt(final String s) {
        return JOptionPane.showInputDialog(this, Messages.formatMessage("script.prompt", new Object[] { s }));
    }
    
    public String showPrompt(final String s, final String initialSelectionValue) {
        return (String)JOptionPane.showInputDialog(this, Messages.formatMessage("script.prompt", new Object[] { s }), null, -1, null, null, initialSelectionValue);
    }
    
    public boolean showConfirm(final String s) {
        return JOptionPane.showConfirmDialog(this, Messages.formatMessage("script.confirm", new Object[] { s }), "Confirm", 0) == 0;
    }
    
    public void setMySize(final Dimension preferredSize) {
        this.setPreferredSize(preferredSize);
        this.invalidate();
    }
    
    public void setAnimationLimitingNone() {
        this.animationLimitingMode = 0;
        if (this.bridgeContext != null) {
            this.setBridgeContextAnimationLimitingMode();
        }
    }
    
    public void setAnimationLimitingCPU(final float animationLimitingAmount) {
        this.animationLimitingMode = 1;
        this.animationLimitingAmount = animationLimitingAmount;
        if (this.bridgeContext != null) {
            this.setBridgeContextAnimationLimitingMode();
        }
    }
    
    public void setAnimationLimitingFPS(final float animationLimitingAmount) {
        this.animationLimitingMode = 2;
        this.animationLimitingAmount = animationLimitingAmount;
        if (this.bridgeContext != null) {
            this.setBridgeContextAnimationLimitingMode();
        }
    }
    
    protected void setBridgeContextAnimationLimitingMode() {
        switch (this.animationLimitingMode) {
            case 0: {
                this.bridgeContext.setAnimationLimitingNone();
                break;
            }
            case 1: {
                this.bridgeContext.setAnimationLimitingCPU(this.animationLimitingAmount);
                break;
            }
            case 2: {
                this.bridgeContext.setAnimationLimitingFPS(this.animationLimitingAmount);
                break;
            }
        }
    }
    
    protected Listener createListener() {
        return new SVGListener();
    }
    
    protected UserAgent createUserAgent() {
        return new BridgeUserAgent();
    }
    
    static {
        SVGFeatureStrings.addSupportedFeatureStrings(FEATURES = new HashSet());
    }
    
    protected class BridgeUserAgent implements UserAgent
    {
        protected Map extensions;
        
        protected BridgeUserAgent() {
            this.extensions = new HashMap();
        }
        
        public Dimension2D getViewportSize() {
            return AbstractJSVGComponent.this.getSize();
        }
        
        public EventDispatcher getEventDispatcher() {
            return AbstractJSVGComponent.this.eventDispatcher;
        }
        
        public void displayError(final String s) {
            if (AbstractJSVGComponent.this.svgUserAgent != null) {
                AbstractJSVGComponent.this.svgUserAgent.displayError(s);
            }
        }
        
        public void displayError(final Exception ex) {
            if (AbstractJSVGComponent.this.svgUserAgent != null) {
                AbstractJSVGComponent.this.svgUserAgent.displayError(ex);
            }
        }
        
        public void displayMessage(final String s) {
            if (AbstractJSVGComponent.this.svgUserAgent != null) {
                AbstractJSVGComponent.this.svgUserAgent.displayMessage(s);
            }
        }
        
        public void showAlert(final String s) {
            if (AbstractJSVGComponent.this.svgUserAgent != null) {
                AbstractJSVGComponent.this.svgUserAgent.showAlert(s);
                return;
            }
            AbstractJSVGComponent.this.showAlert(s);
        }
        
        public String showPrompt(final String s) {
            if (AbstractJSVGComponent.this.svgUserAgent != null) {
                return AbstractJSVGComponent.this.svgUserAgent.showPrompt(s);
            }
            return AbstractJSVGComponent.this.showPrompt(s);
        }
        
        public String showPrompt(final String s, final String s2) {
            if (AbstractJSVGComponent.this.svgUserAgent != null) {
                return AbstractJSVGComponent.this.svgUserAgent.showPrompt(s, s2);
            }
            return AbstractJSVGComponent.this.showPrompt(s, s2);
        }
        
        public boolean showConfirm(final String s) {
            if (AbstractJSVGComponent.this.svgUserAgent != null) {
                return AbstractJSVGComponent.this.svgUserAgent.showConfirm(s);
            }
            return AbstractJSVGComponent.this.showConfirm(s);
        }
        
        public float getPixelUnitToMillimeter() {
            if (AbstractJSVGComponent.this.svgUserAgent != null) {
                return AbstractJSVGComponent.this.svgUserAgent.getPixelUnitToMillimeter();
            }
            return 0.26458332f;
        }
        
        public float getPixelToMM() {
            return this.getPixelUnitToMillimeter();
        }
        
        public String getDefaultFontFamily() {
            if (AbstractJSVGComponent.this.svgUserAgent != null) {
                return AbstractJSVGComponent.this.svgUserAgent.getDefaultFontFamily();
            }
            return "Arial, Helvetica, sans-serif";
        }
        
        public float getMediumFontSize() {
            if (AbstractJSVGComponent.this.svgUserAgent != null) {
                return AbstractJSVGComponent.this.svgUserAgent.getMediumFontSize();
            }
            return 228.59999f / (72.0f * this.getPixelUnitToMillimeter());
        }
        
        public float getLighterFontWeight(final float f) {
            if (AbstractJSVGComponent.this.svgUserAgent != null) {
                return AbstractJSVGComponent.this.svgUserAgent.getLighterFontWeight(f);
            }
            switch ((int)((f + 50.0f) / 100.0f) * 100) {
                case 100: {
                    return 100.0f;
                }
                case 200: {
                    return 100.0f;
                }
                case 300: {
                    return 200.0f;
                }
                case 400: {
                    return 300.0f;
                }
                case 500: {
                    return 400.0f;
                }
                case 600: {
                    return 400.0f;
                }
                case 700: {
                    return 400.0f;
                }
                case 800: {
                    return 400.0f;
                }
                case 900: {
                    return 400.0f;
                }
                default: {
                    throw new IllegalArgumentException("Bad Font Weight: " + f);
                }
            }
        }
        
        public float getBolderFontWeight(final float f) {
            if (AbstractJSVGComponent.this.svgUserAgent != null) {
                return AbstractJSVGComponent.this.svgUserAgent.getBolderFontWeight(f);
            }
            switch ((int)((f + 50.0f) / 100.0f) * 100) {
                case 100: {
                    return 600.0f;
                }
                case 200: {
                    return 600.0f;
                }
                case 300: {
                    return 600.0f;
                }
                case 400: {
                    return 600.0f;
                }
                case 500: {
                    return 600.0f;
                }
                case 600: {
                    return 700.0f;
                }
                case 700: {
                    return 800.0f;
                }
                case 800: {
                    return 900.0f;
                }
                case 900: {
                    return 900.0f;
                }
                default: {
                    throw new IllegalArgumentException("Bad Font Weight: " + f);
                }
            }
        }
        
        public String getLanguages() {
            if (AbstractJSVGComponent.this.svgUserAgent != null) {
                return AbstractJSVGComponent.this.svgUserAgent.getLanguages();
            }
            return "en";
        }
        
        public String getUserStyleSheetURI() {
            if (AbstractJSVGComponent.this.svgUserAgent != null) {
                return AbstractJSVGComponent.this.svgUserAgent.getUserStyleSheetURI();
            }
            return null;
        }
        
        public void openLink(final SVGAElement svgaElement) {
            final String xLinkShow = XLinkSupport.getXLinkShow((Element)svgaElement);
            String s = svgaElement.getHref().getAnimVal();
            if (xLinkShow.equals("new")) {
                this.fireLinkActivatedEvent(svgaElement, s);
                if (AbstractJSVGComponent.this.svgUserAgent != null) {
                    final String url = AbstractJSVGComponent.this.svgDocument.getURL();
                    if (svgaElement.getOwnerDocument() != AbstractJSVGComponent.this.svgDocument) {
                        s = new ParsedURL(((SVGDocument)svgaElement.getOwnerDocument()).getURL(), s).toString();
                    }
                    AbstractJSVGComponent.this.svgUserAgent.openLink(new ParsedURL(url, s).toString(), true);
                }
                else {
                    AbstractJSVGComponent.this.loadSVGDocument(s);
                }
                return;
            }
            final ParsedURL parsedURL = new ParsedURL(((SVGDocument)svgaElement.getOwnerDocument()).getURL(), s);
            final String string = parsedURL.toString();
            if (AbstractJSVGComponent.this.svgDocument != null && parsedURL.sameFile(new ParsedURL(AbstractJSVGComponent.this.svgDocument.getURL()))) {
                final String ref = parsedURL.getRef();
                if (AbstractJSVGComponent.this.fragmentIdentifier != ref && (ref == null || !ref.equals(AbstractJSVGComponent.this.fragmentIdentifier))) {
                    AbstractJSVGComponent.this.fragmentIdentifier = ref;
                    if (AbstractJSVGComponent.this.computeRenderingTransform()) {
                        AbstractJGVTComponent.this.scheduleGVTRendering();
                    }
                }
                this.fireLinkActivatedEvent(svgaElement, string);
                return;
            }
            this.fireLinkActivatedEvent(svgaElement, string);
            if (AbstractJSVGComponent.this.svgUserAgent != null) {
                AbstractJSVGComponent.this.svgUserAgent.openLink(string, false);
            }
            else {
                AbstractJSVGComponent.this.loadSVGDocument(string);
            }
        }
        
        protected void fireLinkActivatedEvent(final SVGAElement svgaElement, final String s) {
            final Object[] array = AbstractJSVGComponent.this.linkActivationListeners.toArray();
            if (array.length > 0) {
                final LinkActivationEvent linkActivationEvent = new LinkActivationEvent(AbstractJSVGComponent.this, svgaElement, s);
                for (int i = 0; i < array.length; ++i) {
                    ((LinkActivationListener)array[i]).linkActivated(linkActivationEvent);
                }
            }
        }
        
        public void setSVGCursor(final Cursor cursor) {
            if (cursor != AbstractJSVGComponent.this.getCursor()) {
                AbstractJSVGComponent.this.setCursor(cursor);
            }
        }
        
        public void setTextSelection(final Mark mark, final Mark mark2) {
            AbstractJSVGComponent.this.select(mark, mark2);
        }
        
        public void deselectAll() {
            AbstractJSVGComponent.this.deselectAll();
        }
        
        public String getXMLParserClassName() {
            if (AbstractJSVGComponent.this.svgUserAgent != null) {
                return AbstractJSVGComponent.this.svgUserAgent.getXMLParserClassName();
            }
            return XMLResourceDescriptor.getXMLParserClassName();
        }
        
        public boolean isXMLParserValidating() {
            return AbstractJSVGComponent.this.svgUserAgent != null && AbstractJSVGComponent.this.svgUserAgent.isXMLParserValidating();
        }
        
        public AffineTransform getTransform() {
            return AbstractJSVGComponent.this.renderingTransform;
        }
        
        public void setTransform(final AffineTransform renderingTransform) {
            AbstractJSVGComponent.this.setRenderingTransform(renderingTransform);
        }
        
        public String getMedia() {
            if (AbstractJSVGComponent.this.svgUserAgent != null) {
                return AbstractJSVGComponent.this.svgUserAgent.getMedia();
            }
            return "screen";
        }
        
        public String getAlternateStyleSheet() {
            if (AbstractJSVGComponent.this.svgUserAgent != null) {
                return AbstractJSVGComponent.this.svgUserAgent.getAlternateStyleSheet();
            }
            return null;
        }
        
        public Point getClientAreaLocationOnScreen() {
            return AbstractJSVGComponent.this.getLocationOnScreen();
        }
        
        public boolean hasFeature(final String s) {
            return AbstractJSVGComponent.FEATURES.contains(s);
        }
        
        public boolean supportExtension(final String s) {
            return (AbstractJSVGComponent.this.svgUserAgent != null && AbstractJSVGComponent.this.svgUserAgent.supportExtension(s)) || this.extensions.containsKey(s);
        }
        
        public void registerExtension(final BridgeExtension bridgeExtension) {
            final Iterator implementedExtensions = bridgeExtension.getImplementedExtensions();
            while (implementedExtensions.hasNext()) {
                this.extensions.put(implementedExtensions.next(), bridgeExtension);
            }
        }
        
        public void handleElement(final Element element, final Object o) {
            if (AbstractJSVGComponent.this.svgUserAgent != null) {
                AbstractJSVGComponent.this.svgUserAgent.handleElement(element, o);
            }
        }
        
        public ScriptSecurity getScriptSecurity(final String s, final ParsedURL parsedURL, final ParsedURL parsedURL2) {
            if (AbstractJSVGComponent.this.svgUserAgent != null) {
                return AbstractJSVGComponent.this.svgUserAgent.getScriptSecurity(s, parsedURL, parsedURL2);
            }
            return new DefaultScriptSecurity(s, parsedURL, parsedURL2);
        }
        
        public void checkLoadScript(final String s, final ParsedURL parsedURL, final ParsedURL parsedURL2) throws SecurityException {
            if (AbstractJSVGComponent.this.svgUserAgent != null) {
                AbstractJSVGComponent.this.svgUserAgent.checkLoadScript(s, parsedURL, parsedURL2);
            }
            else {
                final ScriptSecurity scriptSecurity = this.getScriptSecurity(s, parsedURL, parsedURL2);
                if (scriptSecurity != null) {
                    scriptSecurity.checkLoadScript();
                }
            }
        }
        
        public ExternalResourceSecurity getExternalResourceSecurity(final ParsedURL parsedURL, final ParsedURL parsedURL2) {
            if (AbstractJSVGComponent.this.svgUserAgent != null) {
                return AbstractJSVGComponent.this.svgUserAgent.getExternalResourceSecurity(parsedURL, parsedURL2);
            }
            return new RelaxedExternalResourceSecurity(parsedURL, parsedURL2);
        }
        
        public void checkLoadExternalResource(final ParsedURL parsedURL, final ParsedURL parsedURL2) throws SecurityException {
            if (AbstractJSVGComponent.this.svgUserAgent != null) {
                AbstractJSVGComponent.this.svgUserAgent.checkLoadExternalResource(parsedURL, parsedURL2);
            }
            else {
                final ExternalResourceSecurity externalResourceSecurity = this.getExternalResourceSecurity(parsedURL, parsedURL2);
                if (externalResourceSecurity != null) {
                    externalResourceSecurity.checkLoadExternalResource();
                }
            }
        }
        
        public SVGDocument getBrokenLinkDocument(final Element element, final String s, final String s2) {
            final URL resource = AbstractJSVGComponent.class.getResource("resources/BrokenLink.svg");
            if (resource == null) {
                throw new BridgeException(AbstractJSVGComponent.this.bridgeContext, element, "uri.image.broken", new Object[] { s, s2 });
            }
            final DocumentLoader documentLoader = AbstractJSVGComponent.this.bridgeContext.getDocumentLoader();
            SVGDocument svgDocument2;
            try {
                final SVGDocument svgDocument = (SVGDocument)documentLoader.loadDocument(resource.toString());
                if (svgDocument == null) {
                    return svgDocument;
                }
                svgDocument2 = (SVGDocument)DOMUtilities.deepCloneDocument((Document)svgDocument, SVGDOMImplementation.getDOMImplementation());
                final Element elementById = svgDocument2.getElementById("__More_About");
                if (elementById == null) {
                    return svgDocument2;
                }
                final Element elementNS = svgDocument2.createElementNS("http://www.w3.org/2000/svg", "title");
                elementNS.appendChild(svgDocument2.createTextNode(Messages.formatMessage("broken.link.title", null)));
                final Element elementNS2 = svgDocument2.createElementNS("http://www.w3.org/2000/svg", "desc");
                elementNS2.appendChild(svgDocument2.createTextNode(s2));
                elementById.insertBefore(elementNS2, elementById.getFirstChild());
                elementById.insertBefore(elementNS, elementNS2);
            }
            catch (Exception ex) {
                throw new BridgeException(AbstractJSVGComponent.this.bridgeContext, element, ex, "uri.image.broken", new Object[] { s, s2 });
            }
            return svgDocument2;
        }
    }
    
    protected static class BridgeUserAgentWrapper implements UserAgent
    {
        protected UserAgent userAgent;
        
        public BridgeUserAgentWrapper(final UserAgent userAgent) {
            this.userAgent = userAgent;
        }
        
        public EventDispatcher getEventDispatcher() {
            if (EventQueue.isDispatchThread()) {
                return this.userAgent.getEventDispatcher();
            }
            final AbstractJSVGComponent.Query query = new AbstractJSVGComponent.Query(this);
            this.invokeAndWait(query);
            return query.result;
        }
        
        public Dimension2D getViewportSize() {
            if (EventQueue.isDispatchThread()) {
                return this.userAgent.getViewportSize();
            }
            final AbstractJSVGComponent.Query query = new AbstractJSVGComponent.Query(this);
            this.invokeAndWait(query);
            return query.result;
        }
        
        public void displayError(final Exception ex) {
            if (EventQueue.isDispatchThread()) {
                this.userAgent.displayError(ex);
            }
            else {
                EventQueue.invokeLater(new Runnable() {
                    private final /* synthetic */ BridgeUserAgentWrapper this$0 = this$0;
                    
                    public void run() {
                        this.this$0.userAgent.displayError(ex);
                    }
                });
            }
        }
        
        public void displayMessage(final String s) {
            if (EventQueue.isDispatchThread()) {
                this.userAgent.displayMessage(s);
            }
            else {
                EventQueue.invokeLater(new Runnable() {
                    private final /* synthetic */ BridgeUserAgentWrapper this$0 = this$0;
                    
                    public void run() {
                        this.this$0.userAgent.displayMessage(s);
                    }
                });
            }
        }
        
        public void showAlert(final String s) {
            if (EventQueue.isDispatchThread()) {
                this.userAgent.showAlert(s);
            }
            else {
                this.invokeAndWait(new Runnable() {
                    private final /* synthetic */ BridgeUserAgentWrapper this$0 = this$0;
                    
                    public void run() {
                        this.this$0.userAgent.showAlert(s);
                    }
                });
            }
        }
        
        public String showPrompt(final String s) {
            if (EventQueue.isDispatchThread()) {
                return this.userAgent.showPrompt(s);
            }
            final AbstractJSVGComponent.Query query = new AbstractJSVGComponent.Query(this, s);
            this.invokeAndWait(query);
            return query.result;
        }
        
        public String showPrompt(final String s, final String s2) {
            if (EventQueue.isDispatchThread()) {
                return this.userAgent.showPrompt(s, s2);
            }
            final AbstractJSVGComponent.Query query = new AbstractJSVGComponent.Query(this, s, s2);
            this.invokeAndWait(query);
            return query.result;
        }
        
        public boolean showConfirm(final String s) {
            if (EventQueue.isDispatchThread()) {
                return this.userAgent.showConfirm(s);
            }
            final AbstractJSVGComponent.Query query = new AbstractJSVGComponent.Query(this, s);
            this.invokeAndWait(query);
            return query.result;
        }
        
        public float getPixelUnitToMillimeter() {
            if (EventQueue.isDispatchThread()) {
                return this.userAgent.getPixelUnitToMillimeter();
            }
            final AbstractJSVGComponent.Query query = new AbstractJSVGComponent.Query(this);
            this.invokeAndWait(query);
            return query.result;
        }
        
        public float getPixelToMM() {
            return this.getPixelUnitToMillimeter();
        }
        
        public String getDefaultFontFamily() {
            if (EventQueue.isDispatchThread()) {
                return this.userAgent.getDefaultFontFamily();
            }
            final AbstractJSVGComponent.Query query = new AbstractJSVGComponent.Query(this);
            this.invokeAndWait(query);
            return query.result;
        }
        
        public float getMediumFontSize() {
            if (EventQueue.isDispatchThread()) {
                return this.userAgent.getMediumFontSize();
            }
            final AbstractJSVGComponent.Query query = new AbstractJSVGComponent.Query(this);
            this.invokeAndWait(query);
            return query.result;
        }
        
        public float getLighterFontWeight(final float n) {
            if (EventQueue.isDispatchThread()) {
                return this.userAgent.getLighterFontWeight(n);
            }
            final AbstractJSVGComponent.Query query = new AbstractJSVGComponent.Query(this, n);
            this.invokeAndWait(query);
            return query.result;
        }
        
        public float getBolderFontWeight(final float n) {
            if (EventQueue.isDispatchThread()) {
                return this.userAgent.getBolderFontWeight(n);
            }
            final AbstractJSVGComponent.Query query = new AbstractJSVGComponent.Query(this, n);
            this.invokeAndWait(query);
            return query.result;
        }
        
        public String getLanguages() {
            if (EventQueue.isDispatchThread()) {
                return this.userAgent.getLanguages();
            }
            final AbstractJSVGComponent.Query query = new AbstractJSVGComponent.Query(this);
            this.invokeAndWait(query);
            return query.result;
        }
        
        public String getUserStyleSheetURI() {
            if (EventQueue.isDispatchThread()) {
                return this.userAgent.getUserStyleSheetURI();
            }
            final AbstractJSVGComponent.Query query = new AbstractJSVGComponent.Query(this);
            this.invokeAndWait(query);
            return query.result;
        }
        
        public void openLink(final SVGAElement svgaElement) {
            if (EventQueue.isDispatchThread()) {
                this.userAgent.openLink(svgaElement);
            }
            else {
                EventQueue.invokeLater(new Runnable() {
                    private final /* synthetic */ BridgeUserAgentWrapper this$0 = this$0;
                    
                    public void run() {
                        this.this$0.userAgent.openLink(svgaElement);
                    }
                });
            }
        }
        
        public void setSVGCursor(final Cursor svgCursor) {
            if (EventQueue.isDispatchThread()) {
                this.userAgent.setSVGCursor(svgCursor);
            }
            else {
                EventQueue.invokeLater(new Runnable() {
                    private final /* synthetic */ BridgeUserAgentWrapper this$0 = this$0;
                    
                    public void run() {
                        this.this$0.userAgent.setSVGCursor(svgCursor);
                    }
                });
            }
        }
        
        public void setTextSelection(final Mark mark, final Mark mark2) {
            if (EventQueue.isDispatchThread()) {
                this.userAgent.setTextSelection(mark, mark2);
            }
            else {
                EventQueue.invokeLater(new Runnable() {
                    private final /* synthetic */ BridgeUserAgentWrapper this$0 = this$0;
                    
                    public void run() {
                        this.this$0.userAgent.setTextSelection(mark, mark2);
                    }
                });
            }
        }
        
        public void deselectAll() {
            if (EventQueue.isDispatchThread()) {
                this.userAgent.deselectAll();
            }
            else {
                EventQueue.invokeLater(new Runnable() {
                    private final /* synthetic */ BridgeUserAgentWrapper this$0 = this$0;
                    
                    public void run() {
                        this.this$0.userAgent.deselectAll();
                    }
                });
            }
        }
        
        public String getXMLParserClassName() {
            if (EventQueue.isDispatchThread()) {
                return this.userAgent.getXMLParserClassName();
            }
            final AbstractJSVGComponent.Query query = new AbstractJSVGComponent.Query(this);
            this.invokeAndWait(query);
            return query.result;
        }
        
        public boolean isXMLParserValidating() {
            if (EventQueue.isDispatchThread()) {
                return this.userAgent.isXMLParserValidating();
            }
            final AbstractJSVGComponent.Query query = new AbstractJSVGComponent.Query(this);
            this.invokeAndWait(query);
            return query.result;
        }
        
        public AffineTransform getTransform() {
            if (EventQueue.isDispatchThread()) {
                return this.userAgent.getTransform();
            }
            final AbstractJSVGComponent.Query query = new AbstractJSVGComponent.Query(this);
            this.invokeAndWait(query);
            return query.result;
        }
        
        public void setTransform(final AffineTransform transform) {
            if (EventQueue.isDispatchThread()) {
                this.userAgent.setTransform(transform);
            }
            else {
                this.invokeAndWait(new AbstractJSVGComponent.Query(this, transform));
            }
        }
        
        public String getMedia() {
            if (EventQueue.isDispatchThread()) {
                return this.userAgent.getMedia();
            }
            final AbstractJSVGComponent.Query query = new AbstractJSVGComponent.Query(this);
            this.invokeAndWait(query);
            return query.result;
        }
        
        public String getAlternateStyleSheet() {
            if (EventQueue.isDispatchThread()) {
                return this.userAgent.getAlternateStyleSheet();
            }
            final AbstractJSVGComponent.Query query = new AbstractJSVGComponent.Query(this);
            this.invokeAndWait(query);
            return query.result;
        }
        
        public Point getClientAreaLocationOnScreen() {
            if (EventQueue.isDispatchThread()) {
                return this.userAgent.getClientAreaLocationOnScreen();
            }
            final AbstractJSVGComponent.Query query = new AbstractJSVGComponent.Query(this);
            this.invokeAndWait(query);
            return query.result;
        }
        
        public boolean hasFeature(final String s) {
            if (EventQueue.isDispatchThread()) {
                return this.userAgent.hasFeature(s);
            }
            final AbstractJSVGComponent.Query query = new AbstractJSVGComponent.Query(this, s);
            this.invokeAndWait(query);
            return query.result;
        }
        
        public boolean supportExtension(final String s) {
            if (EventQueue.isDispatchThread()) {
                return this.userAgent.supportExtension(s);
            }
            final AbstractJSVGComponent.Query query = new AbstractJSVGComponent.Query(this, s);
            this.invokeAndWait(query);
            return query.result;
        }
        
        public void registerExtension(final BridgeExtension bridgeExtension) {
            if (EventQueue.isDispatchThread()) {
                this.userAgent.registerExtension(bridgeExtension);
            }
            else {
                EventQueue.invokeLater(new Runnable() {
                    private final /* synthetic */ BridgeUserAgentWrapper this$0 = this$0;
                    
                    public void run() {
                        this.this$0.userAgent.registerExtension(bridgeExtension);
                    }
                });
            }
        }
        
        public void handleElement(final Element element, final Object o) {
            if (EventQueue.isDispatchThread()) {
                this.userAgent.handleElement(element, o);
            }
            else {
                EventQueue.invokeLater(new Runnable() {
                    private final /* synthetic */ BridgeUserAgentWrapper this$0 = this$0;
                    
                    public void run() {
                        this.this$0.userAgent.handleElement(element, o);
                    }
                });
            }
        }
        
        public ScriptSecurity getScriptSecurity(final String s, final ParsedURL parsedURL, final ParsedURL parsedURL2) {
            if (EventQueue.isDispatchThread()) {
                return this.userAgent.getScriptSecurity(s, parsedURL, parsedURL2);
            }
            final AbstractJSVGComponent.Query query = new AbstractJSVGComponent.Query(this, s, parsedURL, parsedURL2);
            this.invokeAndWait(query);
            return query.result;
        }
        
        public void checkLoadScript(final String s, final ParsedURL parsedURL, final ParsedURL parsedURL2) throws SecurityException {
            if (EventQueue.isDispatchThread()) {
                this.userAgent.checkLoadScript(s, parsedURL, parsedURL2);
            }
            else {
                final AbstractJSVGComponent.Query query = new AbstractJSVGComponent.Query(this, s, parsedURL, parsedURL2);
                this.invokeAndWait(query);
                if (query.se != null) {
                    query.se.fillInStackTrace();
                    throw query.se;
                }
            }
        }
        
        public ExternalResourceSecurity getExternalResourceSecurity(final ParsedURL parsedURL, final ParsedURL parsedURL2) {
            if (EventQueue.isDispatchThread()) {
                return this.userAgent.getExternalResourceSecurity(parsedURL, parsedURL2);
            }
            final AbstractJSVGComponent.Query query = new AbstractJSVGComponent.Query(this, parsedURL, parsedURL2);
            this.invokeAndWait(query);
            return query.result;
        }
        
        public void checkLoadExternalResource(final ParsedURL parsedURL, final ParsedURL parsedURL2) throws SecurityException {
            if (EventQueue.isDispatchThread()) {
                this.userAgent.checkLoadExternalResource(parsedURL, parsedURL2);
            }
            else {
                final AbstractJSVGComponent.Query query = new AbstractJSVGComponent.Query(this, parsedURL, parsedURL2);
                this.invokeAndWait(query);
                if (query.se != null) {
                    query.se.fillInStackTrace();
                    throw query.se;
                }
            }
        }
        
        public SVGDocument getBrokenLinkDocument(final Element element, final String s, final String s2) {
            if (EventQueue.isDispatchThread()) {
                return this.userAgent.getBrokenLinkDocument(element, s, s2);
            }
            final AbstractJSVGComponent.Query query = new AbstractJSVGComponent.Query(this, element, s, s2);
            this.invokeAndWait(query);
            if (query.rex != null) {
                throw query.rex;
            }
            return query.doc;
        }
        
        protected void invokeAndWait(final Runnable runnable) {
            try {
                EventQueue.invokeAndWait(runnable);
            }
            catch (Exception ex) {}
        }
    }
    
    protected class SVGListener extends ExtendedListener implements SVGDocumentLoaderListener, GVTTreeBuilderListener, SVGLoadEventDispatcherListener, UpdateManagerListener
    {
        private final /* synthetic */ AbstractJSVGComponent this$0;
        
        public void documentLoadingStarted(final SVGDocumentLoaderEvent svgDocumentLoaderEvent) {
        }
        
        public void documentLoadingCompleted(final SVGDocumentLoaderEvent svgDocumentLoaderEvent) {
            if (AbstractJSVGComponent.this.nextDocumentLoader != null) {
                AbstractJSVGComponent.this.startDocumentLoader();
                return;
            }
            AbstractJSVGComponent.this.documentLoader = null;
            if (AbstractJSVGComponent.this.afterStopRunnable != null) {
                EventQueue.invokeLater(AbstractJSVGComponent.this.afterStopRunnable);
                AbstractJSVGComponent.this.afterStopRunnable = null;
                return;
            }
            AbstractJSVGComponent.this.setSVGDocument(svgDocumentLoaderEvent.getSVGDocument());
        }
        
        public void documentLoadingCancelled(final SVGDocumentLoaderEvent svgDocumentLoaderEvent) {
            if (AbstractJSVGComponent.this.nextDocumentLoader != null) {
                AbstractJSVGComponent.this.startDocumentLoader();
                return;
            }
            AbstractJSVGComponent.this.documentLoader = null;
            if (AbstractJSVGComponent.this.afterStopRunnable != null) {
                EventQueue.invokeLater(AbstractJSVGComponent.this.afterStopRunnable);
                AbstractJSVGComponent.this.afterStopRunnable = null;
                return;
            }
            if (AbstractJSVGComponent.this.nextGVTTreeBuilder != null) {
                AbstractJSVGComponent.this.startGVTTreeBuilder();
            }
        }
        
        public void documentLoadingFailed(final SVGDocumentLoaderEvent svgDocumentLoaderEvent) {
            if (AbstractJSVGComponent.this.nextDocumentLoader != null) {
                AbstractJSVGComponent.this.startDocumentLoader();
                return;
            }
            AbstractJSVGComponent.this.documentLoader = null;
            AbstractJSVGComponent.this.userAgent.displayError(((SVGDocumentLoader)svgDocumentLoaderEvent.getSource()).getException());
            if (AbstractJSVGComponent.this.afterStopRunnable != null) {
                EventQueue.invokeLater(AbstractJSVGComponent.this.afterStopRunnable);
                AbstractJSVGComponent.this.afterStopRunnable = null;
                return;
            }
            if (AbstractJSVGComponent.this.nextGVTTreeBuilder != null) {
                AbstractJSVGComponent.this.startGVTTreeBuilder();
            }
        }
        
        public void gvtBuildStarted(final GVTTreeBuilderEvent gvtTreeBuilderEvent) {
            AbstractJSVGComponent.this.removeJGVTComponentListener(AbstractJSVGComponent.this.jsvgComponentListener);
            AbstractJSVGComponent.this.removeComponentListener(AbstractJSVGComponent.this.jsvgComponentListener);
        }
        
        public void gvtBuildCompleted(final GVTTreeBuilderEvent gvtTreeBuilderEvent) {
            if (AbstractJSVGComponent.this.nextGVTTreeBuilder != null) {
                AbstractJSVGComponent.this.startGVTTreeBuilder();
                return;
            }
            AbstractJSVGComponent.this.loader = null;
            AbstractJSVGComponent.this.gvtTreeBuilder = null;
            if (AbstractJSVGComponent.this.afterStopRunnable != null) {
                EventQueue.invokeLater(AbstractJSVGComponent.this.afterStopRunnable);
                AbstractJSVGComponent.this.afterStopRunnable = null;
                return;
            }
            if (AbstractJSVGComponent.this.nextDocumentLoader != null) {
                AbstractJSVGComponent.this.startDocumentLoader();
                return;
            }
            AbstractJSVGComponent.this.gvtRoot = null;
            if (AbstractJSVGComponent.this.isDynamicDocument && AbstractJSVGComponent.this.eventsEnabled) {
                AbstractJSVGComponent.this.startSVGLoadEventDispatcher(gvtTreeBuilderEvent.getGVTRoot());
            }
            else {
                if (AbstractJSVGComponent.this.isInteractiveDocument) {
                    AbstractJSVGComponent.this.nextUpdateManager = new UpdateManager(AbstractJSVGComponent.this.bridgeContext, gvtTreeBuilderEvent.getGVTRoot(), (Document)AbstractJSVGComponent.this.svgDocument);
                }
                AbstractJSVGComponent.this.setGraphicsNode(gvtTreeBuilderEvent.getGVTRoot(), false);
                AbstractJGVTComponent.this.scheduleGVTRendering();
            }
        }
        
        public void gvtBuildCancelled(final GVTTreeBuilderEvent gvtTreeBuilderEvent) {
            if (AbstractJSVGComponent.this.nextGVTTreeBuilder != null) {
                AbstractJSVGComponent.this.startGVTTreeBuilder();
                return;
            }
            AbstractJSVGComponent.this.loader = null;
            AbstractJSVGComponent.this.gvtTreeBuilder = null;
            if (AbstractJSVGComponent.this.afterStopRunnable != null) {
                EventQueue.invokeLater(AbstractJSVGComponent.this.afterStopRunnable);
                AbstractJSVGComponent.this.afterStopRunnable = null;
                return;
            }
            if (AbstractJSVGComponent.this.nextDocumentLoader != null) {
                AbstractJSVGComponent.this.startDocumentLoader();
                return;
            }
            AbstractJSVGComponent.this.image = null;
            AbstractJSVGComponent.this.repaint();
        }
        
        public void gvtBuildFailed(final GVTTreeBuilderEvent gvtTreeBuilderEvent) {
            if (AbstractJSVGComponent.this.nextGVTTreeBuilder != null) {
                AbstractJSVGComponent.this.startGVTTreeBuilder();
                return;
            }
            AbstractJSVGComponent.this.loader = null;
            AbstractJSVGComponent.this.gvtTreeBuilder = null;
            if (AbstractJSVGComponent.this.afterStopRunnable != null) {
                EventQueue.invokeLater(AbstractJSVGComponent.this.afterStopRunnable);
                AbstractJSVGComponent.this.afterStopRunnable = null;
                return;
            }
            if (AbstractJSVGComponent.this.nextDocumentLoader != null) {
                AbstractJSVGComponent.this.startDocumentLoader();
                return;
            }
            final GraphicsNode gvtRoot = gvtTreeBuilderEvent.getGVTRoot();
            if (gvtRoot == null) {
                AbstractJSVGComponent.this.image = null;
                AbstractJSVGComponent.this.repaint();
            }
            else {
                AbstractJSVGComponent.this.setGraphicsNode(gvtRoot, false);
                AbstractJSVGComponent.this.computeRenderingTransform();
            }
            AbstractJSVGComponent.this.userAgent.displayError(((GVTTreeBuilder)gvtTreeBuilderEvent.getSource()).getException());
        }
        
        public void svgLoadEventDispatchStarted(final SVGLoadEventDispatcherEvent svgLoadEventDispatcherEvent) {
        }
        
        public void svgLoadEventDispatchCompleted(final SVGLoadEventDispatcherEvent svgLoadEventDispatcherEvent) {
            AbstractJSVGComponent.this.nextUpdateManager = AbstractJSVGComponent.this.svgLoadEventDispatcher.getUpdateManager();
            AbstractJSVGComponent.this.svgLoadEventDispatcher = null;
            if (AbstractJSVGComponent.this.afterStopRunnable != null) {
                AbstractJSVGComponent.this.nextUpdateManager.interrupt();
                AbstractJSVGComponent.this.nextUpdateManager = null;
                EventQueue.invokeLater(AbstractJSVGComponent.this.afterStopRunnable);
                AbstractJSVGComponent.this.afterStopRunnable = null;
                return;
            }
            if (AbstractJSVGComponent.this.nextGVTTreeBuilder != null) {
                AbstractJSVGComponent.this.nextUpdateManager.interrupt();
                AbstractJSVGComponent.this.nextUpdateManager = null;
                AbstractJSVGComponent.this.startGVTTreeBuilder();
                return;
            }
            if (AbstractJSVGComponent.this.nextDocumentLoader != null) {
                AbstractJSVGComponent.this.nextUpdateManager.interrupt();
                AbstractJSVGComponent.this.nextUpdateManager = null;
                AbstractJSVGComponent.this.startDocumentLoader();
                return;
            }
            AbstractJSVGComponent.this.setGraphicsNode(svgLoadEventDispatcherEvent.getGVTRoot(), false);
            AbstractJGVTComponent.this.scheduleGVTRendering();
        }
        
        public void svgLoadEventDispatchCancelled(final SVGLoadEventDispatcherEvent svgLoadEventDispatcherEvent) {
            AbstractJSVGComponent.this.nextUpdateManager = AbstractJSVGComponent.this.svgLoadEventDispatcher.getUpdateManager();
            AbstractJSVGComponent.this.svgLoadEventDispatcher = null;
            AbstractJSVGComponent.this.nextUpdateManager.interrupt();
            AbstractJSVGComponent.this.nextUpdateManager = null;
            if (AbstractJSVGComponent.this.afterStopRunnable != null) {
                EventQueue.invokeLater(AbstractJSVGComponent.this.afterStopRunnable);
                AbstractJSVGComponent.this.afterStopRunnable = null;
                return;
            }
            if (AbstractJSVGComponent.this.nextGVTTreeBuilder != null) {
                AbstractJSVGComponent.this.startGVTTreeBuilder();
                return;
            }
            if (AbstractJSVGComponent.this.nextDocumentLoader != null) {
                AbstractJSVGComponent.this.startDocumentLoader();
            }
        }
        
        public void svgLoadEventDispatchFailed(final SVGLoadEventDispatcherEvent svgLoadEventDispatcherEvent) {
            AbstractJSVGComponent.this.nextUpdateManager = AbstractJSVGComponent.this.svgLoadEventDispatcher.getUpdateManager();
            AbstractJSVGComponent.this.svgLoadEventDispatcher = null;
            AbstractJSVGComponent.this.nextUpdateManager.interrupt();
            AbstractJSVGComponent.this.nextUpdateManager = null;
            if (AbstractJSVGComponent.this.afterStopRunnable != null) {
                EventQueue.invokeLater(AbstractJSVGComponent.this.afterStopRunnable);
                AbstractJSVGComponent.this.afterStopRunnable = null;
                return;
            }
            if (AbstractJSVGComponent.this.nextGVTTreeBuilder != null) {
                AbstractJSVGComponent.this.startGVTTreeBuilder();
                return;
            }
            if (AbstractJSVGComponent.this.nextDocumentLoader != null) {
                AbstractJSVGComponent.this.startDocumentLoader();
                return;
            }
            final GraphicsNode gvtRoot = svgLoadEventDispatcherEvent.getGVTRoot();
            if (gvtRoot == null) {
                AbstractJSVGComponent.this.image = null;
                AbstractJSVGComponent.this.repaint();
            }
            else {
                AbstractJSVGComponent.this.setGraphicsNode(gvtRoot, false);
                AbstractJSVGComponent.this.computeRenderingTransform();
            }
            AbstractJSVGComponent.this.userAgent.displayError(((SVGLoadEventDispatcher)svgLoadEventDispatcherEvent.getSource()).getException());
        }
        
        public void gvtRenderingCompleted(final GVTTreeRendererEvent gvtTreeRendererEvent) {
            super.gvtRenderingCompleted(gvtTreeRendererEvent);
            if (AbstractJSVGComponent.this.afterStopRunnable != null) {
                if (AbstractJSVGComponent.this.nextUpdateManager != null) {
                    AbstractJSVGComponent.this.nextUpdateManager.interrupt();
                    AbstractJSVGComponent.this.nextUpdateManager = null;
                }
                EventQueue.invokeLater(AbstractJSVGComponent.this.afterStopRunnable);
                AbstractJSVGComponent.this.afterStopRunnable = null;
                return;
            }
            if (AbstractJSVGComponent.this.nextGVTTreeBuilder != null) {
                if (AbstractJSVGComponent.this.nextUpdateManager != null) {
                    AbstractJSVGComponent.this.nextUpdateManager.interrupt();
                    AbstractJSVGComponent.this.nextUpdateManager = null;
                }
                AbstractJSVGComponent.this.startGVTTreeBuilder();
                return;
            }
            if (AbstractJSVGComponent.this.nextDocumentLoader != null) {
                if (AbstractJSVGComponent.this.nextUpdateManager != null) {
                    AbstractJSVGComponent.this.nextUpdateManager.interrupt();
                    AbstractJSVGComponent.this.nextUpdateManager = null;
                }
                AbstractJSVGComponent.this.startDocumentLoader();
                return;
            }
            if (AbstractJSVGComponent.this.nextUpdateManager != null) {
                AbstractJSVGComponent.this.updateManager = AbstractJSVGComponent.this.nextUpdateManager;
                AbstractJSVGComponent.this.nextUpdateManager = null;
                AbstractJSVGComponent.this.updateManager.addUpdateManagerListener(this);
                AbstractJSVGComponent.this.updateManager.manageUpdates(AbstractJSVGComponent.this.renderer);
            }
        }
        
        public void gvtRenderingCancelled(final GVTTreeRendererEvent gvtTreeRendererEvent) {
            super.gvtRenderingCancelled(gvtTreeRendererEvent);
            if (AbstractJSVGComponent.this.afterStopRunnable != null) {
                if (AbstractJSVGComponent.this.nextUpdateManager != null) {
                    AbstractJSVGComponent.this.nextUpdateManager.interrupt();
                    AbstractJSVGComponent.this.nextUpdateManager = null;
                }
                EventQueue.invokeLater(AbstractJSVGComponent.this.afterStopRunnable);
                AbstractJSVGComponent.this.afterStopRunnable = null;
                return;
            }
            if (AbstractJSVGComponent.this.nextGVTTreeBuilder != null) {
                if (AbstractJSVGComponent.this.nextUpdateManager != null) {
                    AbstractJSVGComponent.this.nextUpdateManager.interrupt();
                    AbstractJSVGComponent.this.nextUpdateManager = null;
                }
                AbstractJSVGComponent.this.startGVTTreeBuilder();
                return;
            }
            if (AbstractJSVGComponent.this.nextDocumentLoader != null) {
                if (AbstractJSVGComponent.this.nextUpdateManager != null) {
                    AbstractJSVGComponent.this.nextUpdateManager.interrupt();
                    AbstractJSVGComponent.this.nextUpdateManager = null;
                }
                AbstractJSVGComponent.this.startDocumentLoader();
            }
        }
        
        public void gvtRenderingFailed(final GVTTreeRendererEvent gvtTreeRendererEvent) {
            super.gvtRenderingFailed(gvtTreeRendererEvent);
            if (AbstractJSVGComponent.this.afterStopRunnable != null) {
                if (AbstractJSVGComponent.this.nextUpdateManager != null) {
                    AbstractJSVGComponent.this.nextUpdateManager.interrupt();
                    AbstractJSVGComponent.this.nextUpdateManager = null;
                }
                EventQueue.invokeLater(AbstractJSVGComponent.this.afterStopRunnable);
                AbstractJSVGComponent.this.afterStopRunnable = null;
                return;
            }
            if (AbstractJSVGComponent.this.nextGVTTreeBuilder != null) {
                if (AbstractJSVGComponent.this.nextUpdateManager != null) {
                    AbstractJSVGComponent.this.nextUpdateManager.interrupt();
                    AbstractJSVGComponent.this.nextUpdateManager = null;
                }
                AbstractJSVGComponent.this.startGVTTreeBuilder();
                return;
            }
            if (AbstractJSVGComponent.this.nextDocumentLoader != null) {
                if (AbstractJSVGComponent.this.nextUpdateManager != null) {
                    AbstractJSVGComponent.this.nextUpdateManager.interrupt();
                    AbstractJSVGComponent.this.nextUpdateManager = null;
                }
                AbstractJSVGComponent.this.startDocumentLoader();
            }
        }
        
        public void managerStarted(final UpdateManagerEvent updateManagerEvent) {
            EventQueue.invokeLater(new Runnable() {
                private final /* synthetic */ SVGListener this$1 = this$1;
                
                public void run() {
                    this.this$1.this$0.suspendInteractions = false;
                    final Object[] array = this.this$1.this$0.updateManagerListeners.toArray();
                    if (array.length > 0) {
                        for (int i = 0; i < array.length; ++i) {
                            ((UpdateManagerListener)array[i]).managerStarted(updateManagerEvent);
                        }
                    }
                }
            });
        }
        
        public void managerSuspended(final UpdateManagerEvent updateManagerEvent) {
            EventQueue.invokeLater(new Runnable() {
                private final /* synthetic */ SVGListener this$1 = this$1;
                
                public void run() {
                    final Object[] array = this.this$1.this$0.updateManagerListeners.toArray();
                    if (array.length > 0) {
                        for (int i = 0; i < array.length; ++i) {
                            ((UpdateManagerListener)array[i]).managerSuspended(updateManagerEvent);
                        }
                    }
                }
            });
        }
        
        public void managerResumed(final UpdateManagerEvent updateManagerEvent) {
            EventQueue.invokeLater(new Runnable() {
                private final /* synthetic */ SVGListener this$1 = this$1;
                
                public void run() {
                    final Object[] array = this.this$1.this$0.updateManagerListeners.toArray();
                    if (array.length > 0) {
                        for (int i = 0; i < array.length; ++i) {
                            ((UpdateManagerListener)array[i]).managerResumed(updateManagerEvent);
                        }
                    }
                }
            });
        }
        
        public void managerStopped(final UpdateManagerEvent updateManagerEvent) {
            EventQueue.invokeLater(new Runnable() {
                private final /* synthetic */ SVGListener this$1 = this$1;
                
                public void run() {
                    this.this$1.this$0.updateManager = null;
                    final Object[] array = this.this$1.this$0.updateManagerListeners.toArray();
                    if (array.length > 0) {
                        for (int i = 0; i < array.length; ++i) {
                            ((UpdateManagerListener)array[i]).managerStopped(updateManagerEvent);
                        }
                    }
                    if (this.this$1.this$0.afterStopRunnable != null) {
                        EventQueue.invokeLater(this.this$1.this$0.afterStopRunnable);
                        this.this$1.this$0.afterStopRunnable = null;
                        return;
                    }
                    if (this.this$1.this$0.nextGVTTreeBuilder != null) {
                        this.this$1.this$0.startGVTTreeBuilder();
                        return;
                    }
                    if (this.this$1.this$0.nextDocumentLoader != null) {
                        AbstractJSVGComponent.this.startDocumentLoader();
                    }
                }
            });
        }
        
        public void updateStarted(final UpdateManagerEvent updateManagerEvent) {
            EventQueue.invokeLater(new Runnable() {
                private final /* synthetic */ SVGListener this$1 = this$1;
                
                public void run() {
                    if (!this.this$1.this$0.doubleBufferedRendering) {
                        this.this$1.this$0.image = updateManagerEvent.getImage();
                    }
                    final Object[] array = this.this$1.this$0.updateManagerListeners.toArray();
                    if (array.length > 0) {
                        for (int i = 0; i < array.length; ++i) {
                            ((UpdateManagerListener)array[i]).updateStarted(updateManagerEvent);
                        }
                    }
                }
            });
        }
        
        public void updateCompleted(final UpdateManagerEvent updateManagerEvent) {
            try {
                EventQueue.invokeAndWait(new Runnable() {
                    private final /* synthetic */ SVGListener this$1 = this$1;
                    
                    public void run() {
                        this.this$1.this$0.image = updateManagerEvent.getImage();
                        if (updateManagerEvent.getClearPaintingTransform()) {
                            this.this$1.this$0.paintingTransform = null;
                        }
                        final List dirtyAreas = updateManagerEvent.getDirtyAreas();
                        if (dirtyAreas != null) {
                            for (Rectangle renderRect : dirtyAreas) {
                                if (this.this$1.this$0.updateOverlay != null) {
                                    this.this$1.this$0.updateOverlay.addRect(renderRect);
                                    renderRect = this.this$1.this$0.getRenderRect();
                                }
                                if (this.this$1.this$0.doubleBufferedRendering) {
                                    this.this$1.this$0.repaint(renderRect);
                                }
                                else {
                                    this.this$1.this$0.paintImmediately(renderRect);
                                }
                            }
                            if (this.this$1.this$0.updateOverlay != null) {
                                this.this$1.this$0.updateOverlay.endUpdate();
                            }
                        }
                        this.this$1.this$0.suspendInteractions = false;
                    }
                });
            }
            catch (Exception ex) {}
            EventQueue.invokeLater(new Runnable() {
                private final /* synthetic */ SVGListener this$1 = this$1;
                
                public void run() {
                    final Object[] array = this.this$1.this$0.updateManagerListeners.toArray();
                    if (array.length > 0) {
                        for (int i = 0; i < array.length; ++i) {
                            ((UpdateManagerListener)array[i]).updateCompleted(updateManagerEvent);
                        }
                    }
                }
            });
        }
        
        public void updateFailed(final UpdateManagerEvent updateManagerEvent) {
            EventQueue.invokeLater(new Runnable() {
                private final /* synthetic */ SVGListener this$1 = this$1;
                
                public void run() {
                    final Object[] array = this.this$1.this$0.updateManagerListeners.toArray();
                    if (array.length > 0) {
                        for (int i = 0; i < array.length; ++i) {
                            ((UpdateManagerListener)array[i]).updateFailed(updateManagerEvent);
                        }
                    }
                }
            });
        }
        
        protected void dispatchKeyTyped(final KeyEvent keyEvent) {
            if (!AbstractJSVGComponent.this.isDynamicDocument) {
                super.dispatchKeyTyped(keyEvent);
                return;
            }
            if (AbstractJSVGComponent.this.updateManager != null && AbstractJSVGComponent.this.updateManager.isRunning()) {
                AbstractJSVGComponent.this.updateManager.getUpdateRunnableQueue().invokeLater(new Runnable() {
                    private final /* synthetic */ SVGListener this$1 = this$1;
                    
                    public void run() {
                        this.this$1.this$0.eventDispatcher.keyTyped(keyEvent);
                    }
                });
            }
        }
        
        protected void dispatchKeyPressed(final KeyEvent keyEvent) {
            if (!AbstractJSVGComponent.this.isDynamicDocument) {
                super.dispatchKeyPressed(keyEvent);
                return;
            }
            if (AbstractJSVGComponent.this.updateManager != null && AbstractJSVGComponent.this.updateManager.isRunning()) {
                AbstractJSVGComponent.this.updateManager.getUpdateRunnableQueue().invokeLater(new Runnable() {
                    private final /* synthetic */ SVGListener this$1 = this$1;
                    
                    public void run() {
                        this.this$1.this$0.eventDispatcher.keyPressed(keyEvent);
                    }
                });
            }
        }
        
        protected void dispatchKeyReleased(final KeyEvent keyEvent) {
            if (!AbstractJSVGComponent.this.isDynamicDocument) {
                super.dispatchKeyReleased(keyEvent);
                return;
            }
            if (AbstractJSVGComponent.this.updateManager != null && AbstractJSVGComponent.this.updateManager.isRunning()) {
                AbstractJSVGComponent.this.updateManager.getUpdateRunnableQueue().invokeLater(new Runnable() {
                    private final /* synthetic */ SVGListener this$1 = this$1;
                    
                    public void run() {
                        this.this$1.this$0.eventDispatcher.keyReleased(keyEvent);
                    }
                });
            }
        }
        
        protected void dispatchMouseClicked(final MouseEvent mouseEvent) {
            if (!AbstractJSVGComponent.this.isInteractiveDocument) {
                super.dispatchMouseClicked(mouseEvent);
                return;
            }
            if (AbstractJSVGComponent.this.updateManager != null && AbstractJSVGComponent.this.updateManager.isRunning()) {
                AbstractJSVGComponent.this.updateManager.getUpdateRunnableQueue().invokeLater(new Runnable() {
                    private final /* synthetic */ SVGListener this$1 = this$1;
                    
                    public void run() {
                        this.this$1.this$0.eventDispatcher.mouseClicked(mouseEvent);
                    }
                });
            }
        }
        
        protected void dispatchMousePressed(final MouseEvent mouseEvent) {
            if (!AbstractJSVGComponent.this.isDynamicDocument) {
                super.dispatchMousePressed(mouseEvent);
                return;
            }
            if (AbstractJSVGComponent.this.updateManager != null && AbstractJSVGComponent.this.updateManager.isRunning()) {
                AbstractJSVGComponent.this.updateManager.getUpdateRunnableQueue().invokeLater(new Runnable() {
                    private final /* synthetic */ SVGListener this$1 = this$1;
                    
                    public void run() {
                        this.this$1.this$0.eventDispatcher.mousePressed(mouseEvent);
                    }
                });
            }
        }
        
        protected void dispatchMouseReleased(final MouseEvent mouseEvent) {
            if (!AbstractJSVGComponent.this.isDynamicDocument) {
                super.dispatchMouseReleased(mouseEvent);
                return;
            }
            if (AbstractJSVGComponent.this.updateManager != null && AbstractJSVGComponent.this.updateManager.isRunning()) {
                AbstractJSVGComponent.this.updateManager.getUpdateRunnableQueue().invokeLater(new Runnable() {
                    private final /* synthetic */ SVGListener this$1 = this$1;
                    
                    public void run() {
                        this.this$1.this$0.eventDispatcher.mouseReleased(mouseEvent);
                    }
                });
            }
        }
        
        protected void dispatchMouseEntered(final MouseEvent mouseEvent) {
            if (!AbstractJSVGComponent.this.isInteractiveDocument) {
                super.dispatchMouseEntered(mouseEvent);
                return;
            }
            if (AbstractJSVGComponent.this.updateManager != null && AbstractJSVGComponent.this.updateManager.isRunning()) {
                AbstractJSVGComponent.this.updateManager.getUpdateRunnableQueue().invokeLater(new Runnable() {
                    private final /* synthetic */ SVGListener this$1 = this$1;
                    
                    public void run() {
                        this.this$1.this$0.eventDispatcher.mouseEntered(mouseEvent);
                    }
                });
            }
        }
        
        protected void dispatchMouseExited(final MouseEvent mouseEvent) {
            if (!AbstractJSVGComponent.this.isInteractiveDocument) {
                super.dispatchMouseExited(mouseEvent);
                return;
            }
            if (AbstractJSVGComponent.this.updateManager != null && AbstractJSVGComponent.this.updateManager.isRunning()) {
                AbstractJSVGComponent.this.updateManager.getUpdateRunnableQueue().invokeLater(new Runnable() {
                    private final /* synthetic */ SVGListener this$1 = this$1;
                    
                    public void run() {
                        this.this$1.this$0.eventDispatcher.mouseExited(mouseEvent);
                    }
                });
            }
        }
        
        protected void dispatchMouseDragged(final MouseEvent event) {
            if (!AbstractJSVGComponent.this.isDynamicDocument) {
                super.dispatchMouseDragged(event);
                return;
            }
            if (AbstractJSVGComponent.this.updateManager != null && AbstractJSVGComponent.this.updateManager.isRunning()) {
                final RunnableQueue updateRunnableQueue = AbstractJSVGComponent.this.updateManager.getUpdateRunnableQueue();
                synchronized (updateRunnableQueue.getIteratorLock()) {
                    for (final MouseDraggedRunnable next : updateRunnableQueue) {
                        if (next instanceof MouseDraggedRunnable) {
                            final MouseDraggedRunnable mouseDraggedRunnable = next;
                            if (mouseDraggedRunnable.event.getModifiers() == event.getModifiers()) {
                                mouseDraggedRunnable.event = event;
                            }
                            return;
                        }
                    }
                }
                updateRunnableQueue.invokeLater(new MouseDraggedRunnable(this, event));
            }
        }
        
        protected void dispatchMouseMoved(final MouseEvent event) {
            if (!AbstractJSVGComponent.this.isInteractiveDocument) {
                super.dispatchMouseMoved(event);
                return;
            }
            if (AbstractJSVGComponent.this.updateManager != null && AbstractJSVGComponent.this.updateManager.isRunning()) {
                final RunnableQueue updateRunnableQueue = AbstractJSVGComponent.this.updateManager.getUpdateRunnableQueue();
                int n = 0;
                synchronized (updateRunnableQueue.getIteratorLock()) {
                    for (final MouseMovedRunnable next : updateRunnableQueue) {
                        if (next instanceof MouseMovedRunnable) {
                            final MouseMovedRunnable mouseMovedRunnable = next;
                            if (mouseMovedRunnable.event.getModifiers() == event.getModifiers()) {
                                mouseMovedRunnable.event = event;
                            }
                            return;
                        }
                        ++n;
                    }
                }
                updateRunnableQueue.invokeLater(new MouseMovedRunnable(this, event));
            }
        }
    }
    
    protected class JSVGComponentListener extends ComponentAdapter implements JGVTComponentListener
    {
        float prevScale;
        float prevTransX;
        float prevTransY;
        private final /* synthetic */ AbstractJSVGComponent this$0;
        
        protected JSVGComponentListener() {
            this.prevScale = 0.0f;
            this.prevTransX = 0.0f;
            this.prevTransY = 0.0f;
        }
        
        public void componentResized(final ComponentEvent componentEvent) {
            if (AbstractJSVGComponent.this.isDynamicDocument && AbstractJSVGComponent.this.updateManager != null && AbstractJSVGComponent.this.updateManager.isRunning()) {
                AbstractJSVGComponent.this.updateManager.getUpdateRunnableQueue().invokeLater(new Runnable() {
                    private final /* synthetic */ JSVGComponentListener this$1 = this$1;
                    
                    public void run() {
                        try {
                            this.this$1.this$0.updateManager.dispatchSVGResizeEvent();
                        }
                        catch (InterruptedException ex) {}
                    }
                });
            }
        }
        
        public void componentTransformChanged(final ComponentEvent componentEvent) {
            final AffineTransform renderingTransform = AbstractJSVGComponent.this.getRenderingTransform();
            final float prevScale = (float)Math.sqrt(renderingTransform.getDeterminant());
            final float prevTransX = (float)renderingTransform.getTranslateX();
            final float prevTransY = (float)renderingTransform.getTranslateY();
            final boolean b = prevScale != this.prevScale;
            final boolean b2 = prevTransX != this.prevTransX || prevTransY != this.prevTransY;
            if (AbstractJSVGComponent.this.isDynamicDocument && AbstractJSVGComponent.this.updateManager != null && AbstractJSVGComponent.this.updateManager.isRunning()) {
                AbstractJSVGComponent.this.updateManager.getUpdateRunnableQueue().invokeLater(new Runnable() {
                    private final /* synthetic */ JSVGComponentListener this$1 = this$1;
                    
                    public void run() {
                        try {
                            if (b) {
                                this.this$1.this$0.updateManager.dispatchSVGZoomEvent();
                            }
                            if (b2) {
                                this.this$1.this$0.updateManager.dispatchSVGScrollEvent();
                            }
                        }
                        catch (InterruptedException ex) {}
                    }
                });
            }
            this.prevScale = prevScale;
            this.prevTransX = prevTransX;
            this.prevTransY = prevTransY;
        }
        
        public void updateMatrix(final AffineTransform affineTransform) {
            this.prevScale = (float)Math.sqrt(affineTransform.getDeterminant());
            this.prevTransX = (float)affineTransform.getTranslateX();
            this.prevTransY = (float)affineTransform.getTranslateY();
        }
    }
    
    class UpdateRenderingRunnable implements Runnable
    {
        AffineTransform at;
        boolean doubleBuf;
        boolean clearPaintTrans;
        Shape aoi;
        int width;
        int height;
        boolean active;
        
        public UpdateRenderingRunnable(final AffineTransform affineTransform, final boolean b, final boolean b2, final Shape shape, final int n, final int n2) {
            this.updateInfo(affineTransform, b, b2, shape, n, n2);
            this.active = true;
        }
        
        public void updateInfo(final AffineTransform at, final boolean doubleBuf, final boolean clearPaintTrans, final Shape aoi, final int width, final int height) {
            this.at = at;
            this.doubleBuf = doubleBuf;
            this.clearPaintTrans = clearPaintTrans;
            this.aoi = aoi;
            this.width = width;
            this.height = height;
            this.active = true;
        }
        
        public void deactivate() {
            this.active = false;
        }
        
        public void run() {
            if (!this.active) {
                return;
            }
            AbstractJSVGComponent.this.updateManager.updateRendering(this.at, this.doubleBuf, this.clearPaintTrans, this.aoi, this.width, this.height);
        }
    }
    
    class Query implements Runnable
    {
        float result;
        private final /* synthetic */ float val$ff;
        private final /* synthetic */ BridgeUserAgentWrapper this$0;
        
        Query(final BridgeUserAgentWrapper this$0, final float val$ff) {
            this.this$0 = this$0;
            this.val$ff = val$ff;
        }
        
        public void run() {
            this.result = this.this$0.userAgent.getBolderFontWeight(this.val$ff);
        }
    }
    
    class Query implements Runnable
    {
        String result;
        private final /* synthetic */ BridgeUserAgentWrapper this$0;
        
        Query(final BridgeUserAgentWrapper this$0) {
            this.this$0 = this$0;
        }
        
        public void run() {
            this.result = this.this$0.userAgent.getLanguages();
        }
    }
    
    class Query implements Runnable
    {
        String result;
        private final /* synthetic */ BridgeUserAgentWrapper this$0;
        
        Query(final BridgeUserAgentWrapper this$0) {
            this.this$0 = this$0;
        }
        
        public void run() {
            this.result = this.this$0.userAgent.getUserStyleSheetURI();
        }
    }
    
    class Query implements Runnable
    {
        String result;
        private final /* synthetic */ BridgeUserAgentWrapper this$0;
        
        Query(final BridgeUserAgentWrapper this$0) {
            this.this$0 = this$0;
        }
        
        public void run() {
            this.result = this.this$0.userAgent.getXMLParserClassName();
        }
    }
    
    class Query implements Runnable
    {
        boolean result;
        private final /* synthetic */ BridgeUserAgentWrapper this$0;
        
        Query(final BridgeUserAgentWrapper this$0) {
            this.this$0 = this$0;
        }
        
        public void run() {
            this.result = this.this$0.userAgent.isXMLParserValidating();
        }
    }
    
    class Query implements Runnable
    {
        AffineTransform result;
        private final /* synthetic */ BridgeUserAgentWrapper this$0;
        
        Query(final BridgeUserAgentWrapper this$0) {
            this.this$0 = this$0;
        }
        
        public void run() {
            this.result = this.this$0.userAgent.getTransform();
        }
    }
    
    class Query implements Runnable
    {
        private final /* synthetic */ AffineTransform val$affine;
        private final /* synthetic */ BridgeUserAgentWrapper this$0;
        
        Query(final BridgeUserAgentWrapper this$0, final AffineTransform val$affine) {
            this.this$0 = this$0;
            this.val$affine = val$affine;
        }
        
        public void run() {
            this.this$0.userAgent.setTransform(this.val$affine);
        }
    }
    
    class Query implements Runnable
    {
        String result;
        private final /* synthetic */ BridgeUserAgentWrapper this$0;
        
        Query(final BridgeUserAgentWrapper this$0) {
            this.this$0 = this$0;
        }
        
        public void run() {
            this.result = this.this$0.userAgent.getMedia();
        }
    }
    
    class Query implements Runnable
    {
        String result;
        private final /* synthetic */ BridgeUserAgentWrapper this$0;
        
        Query(final BridgeUserAgentWrapper this$0) {
            this.this$0 = this$0;
        }
        
        public void run() {
            this.result = this.this$0.userAgent.getAlternateStyleSheet();
        }
    }
    
    class Query implements Runnable
    {
        Point result;
        private final /* synthetic */ BridgeUserAgentWrapper this$0;
        
        Query(final BridgeUserAgentWrapper this$0) {
            this.this$0 = this$0;
        }
        
        public void run() {
            this.result = this.this$0.userAgent.getClientAreaLocationOnScreen();
        }
    }
    
    class MouseDraggedRunnable implements Runnable
    {
        MouseEvent event;
        private final /* synthetic */ SVGListener this$1;
        
        MouseDraggedRunnable(final SVGListener this$1, final MouseEvent event) {
            this.this$1 = this$1;
            this.event = event;
        }
        
        public void run() {
            this.this$1.this$0.eventDispatcher.mouseDragged(this.event);
        }
    }
    
    class MouseMovedRunnable implements Runnable
    {
        MouseEvent event;
        private final /* synthetic */ SVGListener this$1;
        
        MouseMovedRunnable(final SVGListener this$1, final MouseEvent event) {
            this.this$1 = this$1;
            this.event = event;
        }
        
        public void run() {
            this.this$1.this$0.eventDispatcher.mouseMoved(this.event);
        }
    }
    
    class Query implements Runnable
    {
        EventDispatcher result;
        private final /* synthetic */ BridgeUserAgentWrapper this$0;
        
        Query(final BridgeUserAgentWrapper this$0) {
            this.this$0 = this$0;
        }
        
        public void run() {
            this.result = this.this$0.userAgent.getEventDispatcher();
        }
    }
    
    class Query implements Runnable
    {
        boolean result;
        private final /* synthetic */ String val$s;
        private final /* synthetic */ BridgeUserAgentWrapper this$0;
        
        Query(final BridgeUserAgentWrapper this$0, final String val$s) {
            this.this$0 = this$0;
            this.val$s = val$s;
        }
        
        public void run() {
            this.result = this.this$0.userAgent.hasFeature(this.val$s);
        }
    }
    
    class Query implements Runnable
    {
        boolean result;
        private final /* synthetic */ String val$s;
        private final /* synthetic */ BridgeUserAgentWrapper this$0;
        
        Query(final BridgeUserAgentWrapper this$0, final String val$s) {
            this.this$0 = this$0;
            this.val$s = val$s;
        }
        
        public void run() {
            this.result = this.this$0.userAgent.supportExtension(this.val$s);
        }
    }
    
    class Query implements Runnable
    {
        ScriptSecurity result;
        private final /* synthetic */ String val$st;
        private final /* synthetic */ ParsedURL val$sPURL;
        private final /* synthetic */ ParsedURL val$dPURL;
        private final /* synthetic */ BridgeUserAgentWrapper this$0;
        
        Query(final BridgeUserAgentWrapper this$0, final String val$st, final ParsedURL val$sPURL, final ParsedURL val$dPURL) {
            this.this$0 = this$0;
            this.val$st = val$st;
            this.val$sPURL = val$sPURL;
            this.val$dPURL = val$dPURL;
        }
        
        public void run() {
            this.result = this.this$0.userAgent.getScriptSecurity(this.val$st, this.val$sPURL, this.val$dPURL);
        }
    }
    
    class Query implements Runnable
    {
        SecurityException se;
        private final /* synthetic */ String val$st;
        private final /* synthetic */ ParsedURL val$sPURL;
        private final /* synthetic */ ParsedURL val$dPURL;
        private final /* synthetic */ BridgeUserAgentWrapper this$0;
        
        Query(final BridgeUserAgentWrapper this$0, final String val$st, final ParsedURL val$sPURL, final ParsedURL val$dPURL) {
            this.this$0 = this$0;
            this.val$st = val$st;
            this.val$sPURL = val$sPURL;
            this.val$dPURL = val$dPURL;
            this.se = null;
        }
        
        public void run() {
            try {
                this.this$0.userAgent.checkLoadScript(this.val$st, this.val$sPURL, this.val$dPURL);
            }
            catch (SecurityException se) {
                this.se = se;
            }
        }
    }
    
    class Query implements Runnable
    {
        ExternalResourceSecurity result;
        private final /* synthetic */ ParsedURL val$rPURL;
        private final /* synthetic */ ParsedURL val$dPURL;
        private final /* synthetic */ BridgeUserAgentWrapper this$0;
        
        Query(final BridgeUserAgentWrapper this$0, final ParsedURL val$rPURL, final ParsedURL val$dPURL) {
            this.this$0 = this$0;
            this.val$rPURL = val$rPURL;
            this.val$dPURL = val$dPURL;
        }
        
        public void run() {
            this.result = this.this$0.userAgent.getExternalResourceSecurity(this.val$rPURL, this.val$dPURL);
        }
    }
    
    class Query implements Runnable
    {
        SecurityException se;
        private final /* synthetic */ ParsedURL val$rPURL;
        private final /* synthetic */ ParsedURL val$dPURL;
        private final /* synthetic */ BridgeUserAgentWrapper this$0;
        
        Query(final BridgeUserAgentWrapper this$0, final ParsedURL val$rPURL, final ParsedURL val$dPURL) {
            this.this$0 = this$0;
            this.val$rPURL = val$rPURL;
            this.val$dPURL = val$dPURL;
        }
        
        public void run() {
            try {
                this.this$0.userAgent.checkLoadExternalResource(this.val$rPURL, this.val$dPURL);
            }
            catch (SecurityException se) {
                this.se = se;
            }
        }
    }
    
    class Query implements Runnable
    {
        SVGDocument doc;
        RuntimeException rex;
        private final /* synthetic */ Element val$e;
        private final /* synthetic */ String val$url;
        private final /* synthetic */ String val$msg;
        private final /* synthetic */ BridgeUserAgentWrapper this$0;
        
        Query(final BridgeUserAgentWrapper this$0, final Element val$e, final String val$url, final String val$msg) {
            this.this$0 = this$0;
            this.val$e = val$e;
            this.val$url = val$url;
            this.val$msg = val$msg;
            this.rex = null;
        }
        
        public void run() {
            try {
                this.doc = this.this$0.userAgent.getBrokenLinkDocument(this.val$e, this.val$url, this.val$msg);
            }
            catch (RuntimeException rex) {
                this.rex = rex;
            }
        }
    }
    
    class Query implements Runnable
    {
        Dimension2D result;
        private final /* synthetic */ BridgeUserAgentWrapper this$0;
        
        Query(final BridgeUserAgentWrapper this$0) {
            this.this$0 = this$0;
        }
        
        public void run() {
            this.result = this.this$0.userAgent.getViewportSize();
        }
    }
    
    class Query implements Runnable
    {
        String result;
        private final /* synthetic */ String val$message;
        private final /* synthetic */ BridgeUserAgentWrapper this$0;
        
        Query(final BridgeUserAgentWrapper this$0, final String val$message) {
            this.this$0 = this$0;
            this.val$message = val$message;
        }
        
        public void run() {
            this.result = this.this$0.userAgent.showPrompt(this.val$message);
        }
    }
    
    class Query implements Runnable
    {
        String result;
        private final /* synthetic */ String val$message;
        private final /* synthetic */ String val$defaultValue;
        private final /* synthetic */ BridgeUserAgentWrapper this$0;
        
        Query(final BridgeUserAgentWrapper this$0, final String val$message, final String val$defaultValue) {
            this.this$0 = this$0;
            this.val$message = val$message;
            this.val$defaultValue = val$defaultValue;
        }
        
        public void run() {
            this.result = this.this$0.userAgent.showPrompt(this.val$message, this.val$defaultValue);
        }
    }
    
    class Query implements Runnable
    {
        boolean result;
        private final /* synthetic */ String val$message;
        private final /* synthetic */ BridgeUserAgentWrapper this$0;
        
        Query(final BridgeUserAgentWrapper this$0, final String val$message) {
            this.this$0 = this$0;
            this.val$message = val$message;
        }
        
        public void run() {
            this.result = this.this$0.userAgent.showConfirm(this.val$message);
        }
    }
    
    class Query implements Runnable
    {
        float result;
        private final /* synthetic */ BridgeUserAgentWrapper this$0;
        
        Query(final BridgeUserAgentWrapper this$0) {
            this.this$0 = this$0;
        }
        
        public void run() {
            this.result = this.this$0.userAgent.getPixelUnitToMillimeter();
        }
    }
    
    class Query implements Runnable
    {
        String result;
        private final /* synthetic */ BridgeUserAgentWrapper this$0;
        
        Query(final BridgeUserAgentWrapper this$0) {
            this.this$0 = this$0;
        }
        
        public void run() {
            this.result = this.this$0.userAgent.getDefaultFontFamily();
        }
    }
    
    class Query implements Runnable
    {
        float result;
        private final /* synthetic */ BridgeUserAgentWrapper this$0;
        
        Query(final BridgeUserAgentWrapper this$0) {
            this.this$0 = this$0;
        }
        
        public void run() {
            this.result = this.this$0.userAgent.getMediumFontSize();
        }
    }
    
    class Query implements Runnable
    {
        float result;
        private final /* synthetic */ float val$ff;
        private final /* synthetic */ BridgeUserAgentWrapper this$0;
        
        Query(final BridgeUserAgentWrapper this$0, final float val$ff) {
            this.this$0 = this$0;
            this.val$ff = val$ff;
        }
        
        public void run() {
            this.result = this.this$0.userAgent.getLighterFontWeight(this.val$ff);
        }
    }
}
