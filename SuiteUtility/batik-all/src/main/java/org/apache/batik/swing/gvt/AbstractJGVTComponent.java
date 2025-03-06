// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.swing.gvt;

import org.apache.batik.util.Platform;
import java.awt.event.MouseEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.StringSelection;
import java.awt.Toolkit;
import java.text.CharacterIterator;
import org.apache.batik.gvt.event.SelectionEvent;
import org.apache.batik.gvt.event.SelectionAdapter;
import java.awt.Shape;
import java.awt.Component;
import java.awt.geom.NoninvertibleTransformException;
import java.util.Iterator;
import java.awt.RenderingHints;
import java.awt.image.RenderedImage;
import java.awt.Paint;
import java.awt.Composite;
import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Graphics;
import java.awt.EventQueue;
import java.awt.Dimension;
import java.awt.Rectangle;
import org.apache.batik.gvt.text.Mark;
import org.apache.batik.gvt.event.SelectionListener;
import org.apache.batik.gvt.event.EventDispatcher;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseListener;
import java.awt.event.KeyListener;
import java.awt.event.ComponentListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentAdapter;
import java.awt.Color;
import java.util.Collections;
import java.util.LinkedList;
import org.apache.batik.gvt.renderer.ConcreteImageRendererFactory;
import org.apache.batik.gvt.event.AWTEventDispatcher;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import org.apache.batik.util.HaltingThread;
import java.util.List;
import org.apache.batik.gvt.renderer.ImageRenderer;
import org.apache.batik.gvt.renderer.ImageRendererFactory;
import org.apache.batik.gvt.GraphicsNode;
import javax.swing.JComponent;

public abstract class AbstractJGVTComponent extends JComponent
{
    protected Listener listener;
    protected GVTTreeRenderer gvtTreeRenderer;
    protected GraphicsNode gvtRoot;
    protected ImageRendererFactory rendererFactory;
    protected ImageRenderer renderer;
    protected List gvtTreeRendererListeners;
    protected boolean needRender;
    protected boolean progressivePaint;
    protected HaltingThread progressivePaintThread;
    protected BufferedImage image;
    protected AffineTransform initialTransform;
    protected AffineTransform renderingTransform;
    protected AffineTransform paintingTransform;
    protected List interactors;
    protected Interactor interactor;
    protected List overlays;
    protected List jgvtListeners;
    protected AWTEventDispatcher eventDispatcher;
    protected TextSelectionManager textSelectionManager;
    protected boolean doubleBufferedRendering;
    protected boolean eventsEnabled;
    protected boolean selectableText;
    protected boolean useUnixTextSelection;
    protected boolean suspendInteractions;
    protected boolean disableInteractions;
    
    public AbstractJGVTComponent() {
        this(false, false);
    }
    
    public AbstractJGVTComponent(final boolean eventsEnabled, final boolean selectableText) {
        this.rendererFactory = new ConcreteImageRendererFactory();
        this.gvtTreeRendererListeners = Collections.synchronizedList(new LinkedList<Object>());
        this.initialTransform = new AffineTransform();
        this.renderingTransform = new AffineTransform();
        this.interactors = new LinkedList();
        this.overlays = new LinkedList();
        this.jgvtListeners = null;
        this.useUnixTextSelection = true;
        this.setBackground(Color.white);
        this.eventsEnabled = eventsEnabled;
        this.selectableText = selectableText;
        this.listener = this.createListener();
        this.addAWTListeners();
        this.addGVTTreeRendererListener(this.listener);
        this.addComponentListener(new ComponentAdapter() {
            public void componentResized(final ComponentEvent componentEvent) {
                if (AbstractJGVTComponent.this.updateRenderingTransform()) {
                    AbstractJGVTComponent.this.scheduleGVTRendering();
                }
            }
        });
    }
    
    protected void addAWTListeners() {
        this.addKeyListener(this.listener);
        this.addMouseListener(this.listener);
        this.addMouseMotionListener(this.listener);
    }
    
    public void setDisableInteractions(final boolean disableInteractions) {
        this.disableInteractions = disableInteractions;
    }
    
    public boolean getDisableInteractions() {
        return this.disableInteractions;
    }
    
    public void setUseUnixTextSelection(final boolean useUnixTextSelection) {
        this.useUnixTextSelection = useUnixTextSelection;
    }
    
    public void getUseUnixTextSelection(final boolean useUnixTextSelection) {
        this.useUnixTextSelection = useUnixTextSelection;
    }
    
    public List getInteractors() {
        return this.interactors;
    }
    
    public List getOverlays() {
        return this.overlays;
    }
    
    public BufferedImage getOffScreen() {
        return this.image;
    }
    
    public void addJGVTComponentListener(final JGVTComponentListener jgvtComponentListener) {
        if (this.jgvtListeners == null) {
            this.jgvtListeners = new LinkedList();
        }
        this.jgvtListeners.add(jgvtComponentListener);
    }
    
    public void removeJGVTComponentListener(final JGVTComponentListener jgvtComponentListener) {
        if (this.jgvtListeners == null) {
            return;
        }
        this.jgvtListeners.remove(jgvtComponentListener);
    }
    
    public void resetRenderingTransform() {
        this.setRenderingTransform(this.initialTransform);
    }
    
    public void stopProcessing() {
        if (this.gvtTreeRenderer != null) {
            this.needRender = false;
            this.gvtTreeRenderer.halt();
            this.haltProgressivePaintThread();
        }
    }
    
    public GraphicsNode getGraphicsNode() {
        return this.gvtRoot;
    }
    
    public void setGraphicsNode(final GraphicsNode graphicsNode) {
        this.setGraphicsNode(graphicsNode, true);
        this.initialTransform = new AffineTransform();
        this.updateRenderingTransform();
        this.setRenderingTransform(this.initialTransform, true);
    }
    
    protected void setGraphicsNode(final GraphicsNode graphicsNode, final boolean b) {
        this.gvtRoot = graphicsNode;
        if (graphicsNode != null && b) {
            this.initializeEventHandling();
        }
        if (this.eventDispatcher != null) {
            this.eventDispatcher.setRootNode(graphicsNode);
        }
    }
    
    protected void initializeEventHandling() {
        if (this.eventsEnabled) {
            this.eventDispatcher = new AWTEventDispatcher();
            if (this.selectableText) {
                (this.textSelectionManager = this.createTextSelectionManager(this.eventDispatcher)).addSelectionListener(new UnixTextSelectionListener());
            }
        }
    }
    
    protected TextSelectionManager createTextSelectionManager(final EventDispatcher eventDispatcher) {
        return new TextSelectionManager(this, eventDispatcher);
    }
    
    public TextSelectionManager getTextSelectionManager() {
        return this.textSelectionManager;
    }
    
    public void setSelectionOverlayColor(final Color selectionOverlayColor) {
        if (this.textSelectionManager != null) {
            this.textSelectionManager.setSelectionOverlayColor(selectionOverlayColor);
        }
    }
    
    public Color getSelectionOverlayColor() {
        if (this.textSelectionManager != null) {
            return this.textSelectionManager.getSelectionOverlayColor();
        }
        return null;
    }
    
    public void setSelectionOverlayStrokeColor(final Color selectionOverlayStrokeColor) {
        if (this.textSelectionManager != null) {
            this.textSelectionManager.setSelectionOverlayStrokeColor(selectionOverlayStrokeColor);
        }
    }
    
    public Color getSelectionOverlayStrokeColor() {
        if (this.textSelectionManager != null) {
            return this.textSelectionManager.getSelectionOverlayStrokeColor();
        }
        return null;
    }
    
    public void setSelectionOverlayXORMode(final boolean selectionOverlayXORMode) {
        if (this.textSelectionManager != null) {
            this.textSelectionManager.setSelectionOverlayXORMode(selectionOverlayXORMode);
        }
    }
    
    public boolean isSelectionOverlayXORMode() {
        return this.textSelectionManager != null && this.textSelectionManager.isSelectionOverlayXORMode();
    }
    
    public void select(final Mark mark, final Mark mark2) {
        if (this.textSelectionManager != null) {
            this.textSelectionManager.setSelection(mark, mark2);
        }
    }
    
    public void deselectAll() {
        if (this.textSelectionManager != null) {
            this.textSelectionManager.clearSelection();
        }
    }
    
    public void setProgressivePaint(final boolean progressivePaint) {
        if (this.progressivePaint != progressivePaint) {
            this.progressivePaint = progressivePaint;
            this.haltProgressivePaintThread();
        }
    }
    
    public boolean getProgressivePaint() {
        return this.progressivePaint;
    }
    
    public Rectangle getRenderRect() {
        final Dimension size = this.getSize();
        return new Rectangle(0, 0, size.width, size.height);
    }
    
    public void immediateRepaint() {
        if (EventQueue.isDispatchThread()) {
            final Rectangle renderRect = this.getRenderRect();
            if (this.doubleBufferedRendering) {
                this.repaint(renderRect.x, renderRect.y, renderRect.width, renderRect.height);
            }
            else {
                this.paintImmediately(renderRect.x, renderRect.y, renderRect.width, renderRect.height);
            }
        }
        else {
            try {
                EventQueue.invokeAndWait(new Runnable() {
                    public void run() {
                        final Rectangle renderRect = AbstractJGVTComponent.this.getRenderRect();
                        if (AbstractJGVTComponent.this.doubleBufferedRendering) {
                            AbstractJGVTComponent.this.repaint(renderRect.x, renderRect.y, renderRect.width, renderRect.height);
                        }
                        else {
                            AbstractJGVTComponent.this.paintImmediately(renderRect.x, renderRect.y, renderRect.width, renderRect.height);
                        }
                    }
                });
            }
            catch (Exception ex) {}
        }
    }
    
    public void paintComponent(final Graphics g) {
        super.paintComponent(g);
        final Graphics2D graphics2D = (Graphics2D)g;
        final Rectangle renderRect = this.getRenderRect();
        graphics2D.setComposite(AlphaComposite.SrcOver);
        graphics2D.setPaint(this.getBackground());
        graphics2D.fillRect(renderRect.x, renderRect.y, renderRect.width, renderRect.height);
        if (this.image != null) {
            if (this.paintingTransform != null) {
                graphics2D.transform(this.paintingTransform);
            }
            graphics2D.drawRenderedImage(this.image, null);
            graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
            final Iterator<Overlay> iterator = this.overlays.iterator();
            while (iterator.hasNext()) {
                iterator.next().paint(g);
            }
        }
    }
    
    public void setPaintingTransform(final AffineTransform paintingTransform) {
        this.paintingTransform = paintingTransform;
        this.immediateRepaint();
    }
    
    public AffineTransform getPaintingTransform() {
        return this.paintingTransform;
    }
    
    public void setRenderingTransform(final AffineTransform affineTransform) {
        this.setRenderingTransform(affineTransform, true);
    }
    
    public void setRenderingTransform(final AffineTransform tx, final boolean b) {
        this.renderingTransform = new AffineTransform(tx);
        this.suspendInteractions = true;
        if (this.eventDispatcher != null) {
            try {
                this.eventDispatcher.setBaseTransform(this.renderingTransform.createInverse());
            }
            catch (NoninvertibleTransformException ex) {
                this.handleException(ex);
            }
        }
        if (this.jgvtListeners != null) {
            final Iterator<JGVTComponentListener> iterator = (Iterator<JGVTComponentListener>)this.jgvtListeners.iterator();
            final ComponentEvent componentEvent = new ComponentEvent(this, 1337);
            while (iterator.hasNext()) {
                iterator.next().componentTransformChanged(componentEvent);
            }
        }
        if (b) {
            this.scheduleGVTRendering();
        }
    }
    
    public AffineTransform getInitialTransform() {
        return new AffineTransform(this.initialTransform);
    }
    
    public AffineTransform getRenderingTransform() {
        return new AffineTransform(this.renderingTransform);
    }
    
    public void setDoubleBufferedRendering(final boolean doubleBufferedRendering) {
        this.doubleBufferedRendering = doubleBufferedRendering;
    }
    
    public boolean getDoubleBufferedRendering() {
        return this.doubleBufferedRendering;
    }
    
    public void addGVTTreeRendererListener(final GVTTreeRendererListener gvtTreeRendererListener) {
        this.gvtTreeRendererListeners.add(gvtTreeRendererListener);
    }
    
    public void removeGVTTreeRendererListener(final GVTTreeRendererListener gvtTreeRendererListener) {
        this.gvtTreeRendererListeners.remove(gvtTreeRendererListener);
    }
    
    public void flush() {
        this.renderer.flush();
    }
    
    public void flush(final Rectangle rectangle) {
        this.renderer.flush(rectangle);
    }
    
    protected ImageRenderer createImageRenderer() {
        return this.rendererFactory.createStaticImageRenderer();
    }
    
    protected void renderGVTTree() {
        final Rectangle renderRect = this.getRenderRect();
        if (this.gvtRoot == null || renderRect.width <= 0 || renderRect.height <= 0) {
            return;
        }
        if (this.renderer == null || this.renderer.getTree() != this.gvtRoot) {
            (this.renderer = this.createImageRenderer()).setTree(this.gvtRoot);
        }
        AffineTransform inverse;
        try {
            inverse = this.renderingTransform.createInverse();
        }
        catch (NoninvertibleTransformException ex) {
            throw new IllegalStateException("NoninvertibleTransformEx:" + ex.getMessage());
        }
        (this.gvtTreeRenderer = new GVTTreeRenderer(this.renderer, this.renderingTransform, this.doubleBufferedRendering, inverse.createTransformedShape(renderRect), renderRect.width, renderRect.height)).setPriority(1);
        final Iterator<GVTTreeRendererListener> iterator = this.gvtTreeRendererListeners.iterator();
        while (iterator.hasNext()) {
            this.gvtTreeRenderer.addGVTTreeRendererListener(iterator.next());
        }
        if (this.eventDispatcher != null) {
            this.eventDispatcher.setEventDispatchEnabled(false);
        }
        this.gvtTreeRenderer.start();
    }
    
    protected boolean computeRenderingTransform() {
        this.initialTransform = new AffineTransform();
        if (!this.initialTransform.equals(this.renderingTransform)) {
            this.setRenderingTransform(this.initialTransform, false);
            return true;
        }
        return false;
    }
    
    protected boolean updateRenderingTransform() {
        return false;
    }
    
    protected void handleException(final Exception ex) {
    }
    
    protected void releaseRenderingReferences() {
        this.eventDispatcher = null;
        if (this.textSelectionManager != null) {
            this.overlays.remove(this.textSelectionManager.getSelectionOverlay());
            this.textSelectionManager = null;
        }
        this.renderer = null;
        this.image = null;
        this.gvtRoot = null;
    }
    
    protected void scheduleGVTRendering() {
        if (this.gvtTreeRenderer != null) {
            this.needRender = true;
            this.gvtTreeRenderer.halt();
        }
        else {
            this.renderGVTTree();
        }
    }
    
    private void haltProgressivePaintThread() {
        if (this.progressivePaintThread != null) {
            this.progressivePaintThread.halt();
            this.progressivePaintThread = null;
        }
    }
    
    protected Listener createListener() {
        return new Listener();
    }
    
    protected class UnixTextSelectionListener extends SelectionAdapter
    {
        public void selectionDone(final SelectionEvent selectionEvent) {
            if (!AbstractJGVTComponent.this.useUnixTextSelection) {
                return;
            }
            final Object selection = selectionEvent.getSelection();
            if (!(selection instanceof CharacterIterator)) {
                return;
            }
            final CharacterIterator characterIterator = (CharacterIterator)selection;
            final SecurityManager securityManager = System.getSecurityManager();
            if (securityManager != null) {
                try {
                    securityManager.checkSystemClipboardAccess();
                }
                catch (SecurityException ex) {
                    return;
                }
            }
            final int n = characterIterator.getEndIndex() - characterIterator.getBeginIndex();
            if (n == 0) {
                return;
            }
            final char[] value = new char[n];
            value[0] = characterIterator.first();
            for (int i = 1; i < value.length; ++i) {
                value[i] = characterIterator.next();
            }
            new Thread() {
                private final /* synthetic */ String val$strSel = new String(value);
                
                public void run() {
                    final Clipboard systemClipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                    final StringSelection stringSelection = new StringSelection(this.val$strSel);
                    systemClipboard.setContents(stringSelection, stringSelection);
                }
            }.start();
        }
    }
    
    protected class Listener implements GVTTreeRendererListener, KeyListener, MouseListener, MouseMotionListener
    {
        boolean checkClick;
        boolean hadDrag;
        int startX;
        int startY;
        long startTime;
        long fakeClickTime;
        int MAX_DISP;
        long CLICK_TIME;
        private final /* synthetic */ AbstractJGVTComponent this$0;
        
        protected Listener() {
            this.checkClick = false;
            this.hadDrag = false;
            this.MAX_DISP = 16;
            this.CLICK_TIME = 200L;
        }
        
        public void gvtRenderingPrepare(final GVTTreeRendererEvent gvtTreeRendererEvent) {
            AbstractJGVTComponent.this.suspendInteractions = true;
            if (!AbstractJGVTComponent.this.progressivePaint && !AbstractJGVTComponent.this.doubleBufferedRendering) {
                AbstractJGVTComponent.this.image = null;
            }
        }
        
        public void gvtRenderingStarted(final GVTTreeRendererEvent gvtTreeRendererEvent) {
            if (AbstractJGVTComponent.this.progressivePaint && !AbstractJGVTComponent.this.doubleBufferedRendering) {
                AbstractJGVTComponent.this.image = gvtTreeRendererEvent.getImage();
                (AbstractJGVTComponent.this.progressivePaintThread = new HaltingThread() {
                    private final /* synthetic */ Listener this$1 = this$1;
                    
                    public void run() {
                        try {
                            while (!HaltingThread.hasBeenHalted()) {
                                EventQueue.invokeLater(new Runnable() {
                                    private final /* synthetic */ Thread val$thisThread = this;
                                    private final /* synthetic */ AbstractJGVTComponent$3 this$2 = this$2;
                                    
                                    public void run() {
                                        if (this.this$2.this$1.this$0.progressivePaintThread == this.val$thisThread) {
                                            final Rectangle renderRect = this.this$2.this$1.this$0.getRenderRect();
                                            this.this$2.this$1.this$0.repaint(renderRect.x, renderRect.y, renderRect.width, renderRect.height);
                                        }
                                    }
                                });
                                Thread.sleep(200L);
                            }
                        }
                        catch (InterruptedException ex) {}
                        catch (ThreadDeath threadDeath) {
                            throw threadDeath;
                        }
                        catch (Throwable t) {
                            t.printStackTrace();
                        }
                    }
                }).setPriority(2);
                AbstractJGVTComponent.this.progressivePaintThread.start();
            }
            if (!AbstractJGVTComponent.this.doubleBufferedRendering) {
                AbstractJGVTComponent.this.paintingTransform = null;
                AbstractJGVTComponent.this.suspendInteractions = false;
            }
        }
        
        public void gvtRenderingCompleted(final GVTTreeRendererEvent gvtTreeRendererEvent) {
            AbstractJGVTComponent.this.haltProgressivePaintThread();
            if (AbstractJGVTComponent.this.doubleBufferedRendering) {
                AbstractJGVTComponent.this.paintingTransform = null;
                AbstractJGVTComponent.this.suspendInteractions = false;
            }
            AbstractJGVTComponent.this.gvtTreeRenderer = null;
            if (AbstractJGVTComponent.this.needRender) {
                AbstractJGVTComponent.this.renderGVTTree();
                AbstractJGVTComponent.this.needRender = false;
            }
            else {
                AbstractJGVTComponent.this.image = gvtTreeRendererEvent.getImage();
                AbstractJGVTComponent.this.immediateRepaint();
            }
            if (AbstractJGVTComponent.this.eventDispatcher != null) {
                AbstractJGVTComponent.this.eventDispatcher.setEventDispatchEnabled(true);
            }
        }
        
        public void gvtRenderingCancelled(final GVTTreeRendererEvent gvtTreeRendererEvent) {
            this.renderingStopped();
        }
        
        public void gvtRenderingFailed(final GVTTreeRendererEvent gvtTreeRendererEvent) {
            this.renderingStopped();
        }
        
        private void renderingStopped() {
            AbstractJGVTComponent.this.haltProgressivePaintThread();
            if (AbstractJGVTComponent.this.doubleBufferedRendering) {
                AbstractJGVTComponent.this.suspendInteractions = false;
            }
            AbstractJGVTComponent.this.gvtTreeRenderer = null;
            if (AbstractJGVTComponent.this.needRender) {
                AbstractJGVTComponent.this.renderGVTTree();
                AbstractJGVTComponent.this.needRender = false;
            }
            else {
                AbstractJGVTComponent.this.immediateRepaint();
            }
            if (AbstractJGVTComponent.this.eventDispatcher != null) {
                AbstractJGVTComponent.this.eventDispatcher.setEventDispatchEnabled(true);
            }
        }
        
        public void keyTyped(final KeyEvent keyEvent) {
            this.selectInteractor(keyEvent);
            if (AbstractJGVTComponent.this.interactor != null) {
                AbstractJGVTComponent.this.interactor.keyTyped(keyEvent);
                this.deselectInteractor();
            }
            else if (AbstractJGVTComponent.this.eventDispatcher != null) {
                this.dispatchKeyTyped(keyEvent);
            }
        }
        
        protected void dispatchKeyTyped(final KeyEvent keyEvent) {
            AbstractJGVTComponent.this.eventDispatcher.keyTyped(keyEvent);
        }
        
        public void keyPressed(final KeyEvent keyEvent) {
            this.selectInteractor(keyEvent);
            if (AbstractJGVTComponent.this.interactor != null) {
                AbstractJGVTComponent.this.interactor.keyPressed(keyEvent);
                this.deselectInteractor();
            }
            else if (AbstractJGVTComponent.this.eventDispatcher != null) {
                this.dispatchKeyPressed(keyEvent);
            }
        }
        
        protected void dispatchKeyPressed(final KeyEvent keyEvent) {
            AbstractJGVTComponent.this.eventDispatcher.keyPressed(keyEvent);
        }
        
        public void keyReleased(final KeyEvent keyEvent) {
            this.selectInteractor(keyEvent);
            if (AbstractJGVTComponent.this.interactor != null) {
                AbstractJGVTComponent.this.interactor.keyReleased(keyEvent);
                this.deselectInteractor();
            }
            else if (AbstractJGVTComponent.this.eventDispatcher != null) {
                this.dispatchKeyReleased(keyEvent);
            }
        }
        
        protected void dispatchKeyReleased(final KeyEvent keyEvent) {
            AbstractJGVTComponent.this.eventDispatcher.keyReleased(keyEvent);
        }
        
        public void mouseClicked(final MouseEvent mouseEvent) {
            if (this.fakeClickTime != mouseEvent.getWhen()) {
                this.handleMouseClicked(mouseEvent);
            }
        }
        
        public void handleMouseClicked(final MouseEvent mouseEvent) {
            this.selectInteractor(mouseEvent);
            if (AbstractJGVTComponent.this.interactor != null) {
                AbstractJGVTComponent.this.interactor.mouseClicked(mouseEvent);
                this.deselectInteractor();
            }
            else if (AbstractJGVTComponent.this.eventDispatcher != null) {
                this.dispatchMouseClicked(mouseEvent);
            }
        }
        
        protected void dispatchMouseClicked(final MouseEvent mouseEvent) {
            AbstractJGVTComponent.this.eventDispatcher.mouseClicked(mouseEvent);
        }
        
        public void mousePressed(final MouseEvent mouseEvent) {
            this.startX = mouseEvent.getX();
            this.startY = mouseEvent.getY();
            this.startTime = mouseEvent.getWhen();
            this.checkClick = true;
            this.selectInteractor(mouseEvent);
            if (AbstractJGVTComponent.this.interactor != null) {
                AbstractJGVTComponent.this.interactor.mousePressed(mouseEvent);
                this.deselectInteractor();
            }
            else if (AbstractJGVTComponent.this.eventDispatcher != null) {
                this.dispatchMousePressed(mouseEvent);
            }
        }
        
        protected void dispatchMousePressed(final MouseEvent mouseEvent) {
            AbstractJGVTComponent.this.eventDispatcher.mousePressed(mouseEvent);
        }
        
        public void mouseReleased(final MouseEvent mouseEvent) {
            if (this.checkClick && this.hadDrag) {
                final int n = this.startX - mouseEvent.getX();
                final int n2 = this.startY - mouseEvent.getY();
                final long when = mouseEvent.getWhen();
                if (n * n + n2 * n2 < this.MAX_DISP && when - this.startTime < this.CLICK_TIME) {
                    final MouseEvent mouseEvent2 = new MouseEvent(mouseEvent.getComponent(), 500, mouseEvent.getWhen(), mouseEvent.getModifiers(), mouseEvent.getX(), mouseEvent.getY(), mouseEvent.getClickCount(), mouseEvent.isPopupTrigger());
                    this.fakeClickTime = mouseEvent2.getWhen();
                    this.handleMouseClicked(mouseEvent2);
                }
            }
            this.checkClick = false;
            this.hadDrag = false;
            this.selectInteractor(mouseEvent);
            if (AbstractJGVTComponent.this.interactor != null) {
                AbstractJGVTComponent.this.interactor.mouseReleased(mouseEvent);
                this.deselectInteractor();
            }
            else if (AbstractJGVTComponent.this.eventDispatcher != null) {
                this.dispatchMouseReleased(mouseEvent);
            }
        }
        
        protected void dispatchMouseReleased(final MouseEvent mouseEvent) {
            AbstractJGVTComponent.this.eventDispatcher.mouseReleased(mouseEvent);
        }
        
        public void mouseEntered(final MouseEvent mouseEvent) {
            this.selectInteractor(mouseEvent);
            if (AbstractJGVTComponent.this.interactor != null) {
                AbstractJGVTComponent.this.interactor.mouseEntered(mouseEvent);
                this.deselectInteractor();
            }
            else if (AbstractJGVTComponent.this.eventDispatcher != null) {
                this.dispatchMouseEntered(mouseEvent);
            }
        }
        
        protected void dispatchMouseEntered(final MouseEvent mouseEvent) {
            AbstractJGVTComponent.this.eventDispatcher.mouseEntered(mouseEvent);
        }
        
        public void mouseExited(final MouseEvent mouseEvent) {
            this.selectInteractor(mouseEvent);
            if (AbstractJGVTComponent.this.interactor != null) {
                AbstractJGVTComponent.this.interactor.mouseExited(mouseEvent);
                this.deselectInteractor();
            }
            else if (AbstractJGVTComponent.this.eventDispatcher != null) {
                this.dispatchMouseExited(mouseEvent);
            }
        }
        
        protected void dispatchMouseExited(final MouseEvent mouseEvent) {
            AbstractJGVTComponent.this.eventDispatcher.mouseExited(mouseEvent);
        }
        
        public void mouseDragged(final MouseEvent mouseEvent) {
            this.hadDrag = true;
            final int n = this.startX - mouseEvent.getX();
            final int n2 = this.startY - mouseEvent.getY();
            if (n * n + n2 * n2 > this.MAX_DISP) {
                this.checkClick = false;
            }
            this.selectInteractor(mouseEvent);
            if (AbstractJGVTComponent.this.interactor != null) {
                AbstractJGVTComponent.this.interactor.mouseDragged(mouseEvent);
                this.deselectInteractor();
            }
            else if (AbstractJGVTComponent.this.eventDispatcher != null) {
                this.dispatchMouseDragged(mouseEvent);
            }
        }
        
        protected void dispatchMouseDragged(final MouseEvent mouseEvent) {
            AbstractJGVTComponent.this.eventDispatcher.mouseDragged(mouseEvent);
        }
        
        public void mouseMoved(final MouseEvent mouseEvent) {
            this.selectInteractor(mouseEvent);
            if (AbstractJGVTComponent.this.interactor != null) {
                if (Platform.isOSX && AbstractJGVTComponent.this.interactor instanceof AbstractZoomInteractor) {
                    this.mouseDragged(mouseEvent);
                }
                else {
                    AbstractJGVTComponent.this.interactor.mouseMoved(mouseEvent);
                }
                this.deselectInteractor();
            }
            else if (AbstractJGVTComponent.this.eventDispatcher != null) {
                this.dispatchMouseMoved(mouseEvent);
            }
        }
        
        protected void dispatchMouseMoved(final MouseEvent mouseEvent) {
            AbstractJGVTComponent.this.eventDispatcher.mouseMoved(mouseEvent);
        }
        
        protected void selectInteractor(final InputEvent inputEvent) {
            if (!AbstractJGVTComponent.this.disableInteractions && !AbstractJGVTComponent.this.suspendInteractions && AbstractJGVTComponent.this.interactor == null && AbstractJGVTComponent.this.gvtRoot != null) {
                for (final Interactor interactor : AbstractJGVTComponent.this.interactors) {
                    if (interactor.startInteraction(inputEvent)) {
                        AbstractJGVTComponent.this.interactor = interactor;
                        break;
                    }
                }
            }
        }
        
        protected void deselectInteractor() {
            if (AbstractJGVTComponent.this.interactor.endInteraction()) {
                AbstractJGVTComponent.this.interactor = null;
            }
        }
    }
}
