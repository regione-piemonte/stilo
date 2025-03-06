// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.swing;

import org.w3c.dom.events.Event;
import org.w3c.dom.events.EventListener;
import org.apache.batik.dom.events.NodeEventTarget;
import org.apache.batik.swing.svg.SVGDocumentLoaderEvent;
import org.apache.batik.swing.svg.SVGDocumentLoaderAdapter;
import javax.swing.BoundedRangeModel;
import javax.swing.event.ChangeEvent;
import org.apache.batik.bridge.UpdateManagerEvent;
import org.apache.batik.swing.gvt.GVTTreeRendererEvent;
import org.apache.batik.swing.svg.GVTTreeBuilderEvent;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentAdapter;
import org.apache.batik.gvt.GraphicsNode;
import org.w3c.dom.svg.SVGSVGElement;
import org.w3c.dom.svg.SVGDocument;
import org.apache.batik.bridge.BridgeContext;
import org.w3c.dom.Element;
import org.apache.batik.bridge.ViewBox;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import org.apache.batik.swing.svg.SVGDocumentLoaderListener;
import org.apache.batik.bridge.UpdateManagerListener;
import org.apache.batik.swing.svg.GVTTreeBuilderListener;
import org.apache.batik.swing.gvt.JGVTComponentListener;
import org.apache.batik.swing.gvt.GVTTreeRendererListener;
import java.awt.event.ComponentListener;
import javax.swing.event.ChangeListener;
import javax.swing.Box;
import java.awt.Dimension;
import java.awt.LayoutManager;
import java.awt.BorderLayout;
import java.awt.geom.Rectangle2D;
import java.awt.Component;
import javax.swing.JScrollBar;
import javax.swing.JPanel;

public class JSVGScrollPane extends JPanel
{
    protected JSVGCanvas canvas;
    protected JPanel horizontalPanel;
    protected JScrollBar vertical;
    protected JScrollBar horizontal;
    protected Component cornerBox;
    protected boolean scrollbarsAlwaysVisible;
    protected SBListener hsbListener;
    protected SBListener vsbListener;
    protected Rectangle2D viewBox;
    protected boolean ignoreScrollChange;
    
    public JSVGScrollPane(final JSVGCanvas jsvgCanvas) {
        this.scrollbarsAlwaysVisible = false;
        this.viewBox = null;
        this.ignoreScrollChange = false;
        (this.canvas = jsvgCanvas).setRecenterOnResize(false);
        this.vertical = new JScrollBar(1, 0, 0, 0, 0);
        this.horizontal = new JScrollBar(0, 0, 0, 0, 0);
        (this.horizontalPanel = new JPanel(new BorderLayout())).add(this.horizontal, "Center");
        this.cornerBox = Box.createRigidArea(new Dimension(this.vertical.getPreferredSize().width, this.horizontal.getPreferredSize().height));
        this.horizontalPanel.add(this.cornerBox, "East");
        this.hsbListener = this.createScrollBarListener(false);
        this.horizontal.getModel().addChangeListener(this.hsbListener);
        this.vsbListener = this.createScrollBarListener(true);
        this.vertical.getModel().addChangeListener(this.vsbListener);
        this.updateScrollbarState(false, false);
        this.setLayout(new BorderLayout());
        this.add(jsvgCanvas, "Center");
        this.add(this.vertical, "East");
        this.add(this.horizontalPanel, "South");
        jsvgCanvas.addSVGDocumentLoaderListener(this.createLoadListener());
        final ScrollListener scrollListener = this.createScrollListener();
        this.addComponentListener(scrollListener);
        jsvgCanvas.addGVTTreeRendererListener(scrollListener);
        jsvgCanvas.addJGVTComponentListener(scrollListener);
        jsvgCanvas.addGVTTreeBuilderListener(scrollListener);
        jsvgCanvas.addUpdateManagerListener(scrollListener);
    }
    
    public boolean getScrollbarsAlwaysVisible() {
        return this.scrollbarsAlwaysVisible;
    }
    
    public void setScrollbarsAlwaysVisible(final boolean scrollbarsAlwaysVisible) {
        this.scrollbarsAlwaysVisible = scrollbarsAlwaysVisible;
        this.resizeScrollBars();
    }
    
    protected SBListener createScrollBarListener(final boolean b) {
        return new SBListener(b);
    }
    
    protected ScrollListener createScrollListener() {
        return new ScrollListener();
    }
    
    protected SVGDocumentLoaderListener createLoadListener() {
        return new SVGScrollDocumentLoaderListener();
    }
    
    public JSVGCanvas getCanvas() {
        return this.canvas;
    }
    
    public void reset() {
        this.viewBox = null;
        this.updateScrollbarState(false, false);
        this.revalidate();
    }
    
    protected void setScrollPosition() {
        this.checkAndSetViewBoxRect();
        if (this.viewBox == null) {
            return;
        }
        AffineTransform renderingTransform = this.canvas.getRenderingTransform();
        AffineTransform viewBoxTransform = this.canvas.getViewBoxTransform();
        if (renderingTransform == null) {
            renderingTransform = new AffineTransform();
        }
        if (viewBoxTransform == null) {
            viewBoxTransform = new AffineTransform();
        }
        final Rectangle bounds = viewBoxTransform.createTransformedShape(this.viewBox).getBounds();
        int n = 0;
        int n2 = 0;
        if (bounds.x < 0) {
            n -= bounds.x;
        }
        if (bounds.y < 0) {
            n2 -= bounds.y;
        }
        renderingTransform.preConcatenate(AffineTransform.getTranslateInstance(-(this.horizontal.getValue() - n), -(this.vertical.getValue() - n2)));
        this.canvas.setRenderingTransform(renderingTransform);
    }
    
    protected void resizeScrollBars() {
        this.ignoreScrollChange = true;
        this.checkAndSetViewBoxRect();
        if (this.viewBox == null) {
            return;
        }
        AffineTransform viewBoxTransform = this.canvas.getViewBoxTransform();
        if (viewBoxTransform == null) {
            viewBoxTransform = new AffineTransform();
        }
        final Rectangle bounds = viewBoxTransform.createTransformedShape(this.viewBox).getBounds();
        int width = bounds.width;
        int height = bounds.height;
        int newValue = 0;
        int newValue2 = 0;
        if (bounds.x > 0) {
            width += bounds.x;
        }
        else {
            newValue -= bounds.x;
        }
        if (bounds.y > 0) {
            height += bounds.y;
        }
        else {
            newValue2 -= bounds.y;
        }
        final Dimension updateScrollbarVisibility = this.updateScrollbarVisibility(newValue, newValue2, width, height);
        this.vertical.setValues(newValue2, updateScrollbarVisibility.height, 0, height);
        this.horizontal.setValues(newValue, updateScrollbarVisibility.width, 0, width);
        this.vertical.setBlockIncrement((int)(0.9f * updateScrollbarVisibility.height));
        this.horizontal.setBlockIncrement((int)(0.9f * updateScrollbarVisibility.width));
        this.vertical.setUnitIncrement((int)(0.2f * updateScrollbarVisibility.height));
        this.horizontal.setUnitIncrement((int)(0.2f * updateScrollbarVisibility.width));
        this.doLayout();
        this.horizontalPanel.doLayout();
        this.horizontal.doLayout();
        this.vertical.doLayout();
        this.ignoreScrollChange = false;
    }
    
    protected Dimension updateScrollbarVisibility(final int n, final int n2, final int n3, final int n4) {
        final Dimension size = this.canvas.getSize();
        int width = size.width;
        int width2 = size.width;
        int height = size.height;
        int height2 = size.height;
        if (this.vertical.isVisible()) {
            width += this.vertical.getPreferredSize().width;
        }
        else {
            width2 -= this.vertical.getPreferredSize().width;
        }
        if (this.horizontalPanel.isVisible()) {
            height += this.horizontal.getPreferredSize().height;
        }
        else {
            height2 -= this.horizontal.getPreferredSize().height;
        }
        final Dimension dimension = new Dimension();
        boolean b;
        boolean b2;
        if (this.scrollbarsAlwaysVisible) {
            b = (n3 > width2);
            b2 = (n4 > height2);
            dimension.width = width2;
            dimension.height = height2;
        }
        else {
            b = (n3 > width || n != 0);
            b2 = (n4 > height || n2 != 0);
            if (b2 && !b) {
                b = (n3 > width2);
            }
            else if (b && !b2) {
                b2 = (n4 > height2);
            }
            dimension.width = (b ? width2 : width);
            dimension.height = (b2 ? height2 : height);
        }
        this.updateScrollbarState(b, b2);
        return dimension;
    }
    
    protected void updateScrollbarState(final boolean b, final boolean b2) {
        this.horizontal.setEnabled(b);
        this.vertical.setEnabled(b2);
        if (this.scrollbarsAlwaysVisible) {
            this.horizontalPanel.setVisible(true);
            this.vertical.setVisible(true);
            this.cornerBox.setVisible(true);
        }
        else {
            this.horizontalPanel.setVisible(b);
            this.vertical.setVisible(b2);
            this.cornerBox.setVisible(b && b2);
        }
    }
    
    protected void checkAndSetViewBoxRect() {
        if (this.viewBox != null) {
            return;
        }
        this.viewBox = this.getViewBoxRect();
    }
    
    protected Rectangle2D getViewBoxRect() {
        final SVGDocument svgDocument = this.canvas.getSVGDocument();
        if (svgDocument == null) {
            return null;
        }
        final SVGSVGElement rootElement = svgDocument.getRootElement();
        if (rootElement == null) {
            return null;
        }
        final String attributeNS = rootElement.getAttributeNS((String)null, "viewBox");
        if (attributeNS.length() != 0) {
            final float[] viewBoxAttribute = ViewBox.parseViewBoxAttribute((Element)rootElement, attributeNS, null);
            return new Rectangle2D.Float(viewBoxAttribute[0], viewBoxAttribute[1], viewBoxAttribute[2], viewBoxAttribute[3]);
        }
        final GraphicsNode graphicsNode = this.canvas.getGraphicsNode();
        if (graphicsNode == null) {
            return null;
        }
        final Rectangle2D bounds = graphicsNode.getBounds();
        if (bounds == null) {
            return null;
        }
        return (Rectangle2D)bounds.clone();
    }
    
    public void scaleChange(final float n) {
    }
    
    protected class ScrollListener extends ComponentAdapter implements JGVTComponentListener, GVTTreeBuilderListener, GVTTreeRendererListener, UpdateManagerListener
    {
        protected boolean isReady;
        
        protected ScrollListener() {
            this.isReady = false;
        }
        
        public void componentTransformChanged(final ComponentEvent componentEvent) {
            if (this.isReady) {
                JSVGScrollPane.this.resizeScrollBars();
            }
        }
        
        public void componentResized(final ComponentEvent componentEvent) {
            if (this.isReady) {
                JSVGScrollPane.this.resizeScrollBars();
            }
        }
        
        public void gvtBuildStarted(final GVTTreeBuilderEvent gvtTreeBuilderEvent) {
            this.isReady = false;
            JSVGScrollPane.this.updateScrollbarState(false, false);
        }
        
        public void gvtBuildCompleted(final GVTTreeBuilderEvent gvtTreeBuilderEvent) {
            this.isReady = true;
            JSVGScrollPane.this.viewBox = null;
        }
        
        public void gvtRenderingCompleted(final GVTTreeRendererEvent gvtTreeRendererEvent) {
            if (JSVGScrollPane.this.viewBox == null) {
                JSVGScrollPane.this.resizeScrollBars();
                return;
            }
            final Rectangle2D viewBoxRect = JSVGScrollPane.this.getViewBoxRect();
            if (viewBoxRect.getX() != JSVGScrollPane.this.viewBox.getX() || viewBoxRect.getY() != JSVGScrollPane.this.viewBox.getY() || viewBoxRect.getWidth() != JSVGScrollPane.this.viewBox.getWidth() || viewBoxRect.getHeight() != JSVGScrollPane.this.viewBox.getHeight()) {
                JSVGScrollPane.this.viewBox = viewBoxRect;
                JSVGScrollPane.this.resizeScrollBars();
            }
        }
        
        public void updateCompleted(final UpdateManagerEvent updateManagerEvent) {
            if (JSVGScrollPane.this.viewBox == null) {
                JSVGScrollPane.this.resizeScrollBars();
                return;
            }
            final Rectangle2D viewBoxRect = JSVGScrollPane.this.getViewBoxRect();
            if (viewBoxRect.getX() != JSVGScrollPane.this.viewBox.getX() || viewBoxRect.getY() != JSVGScrollPane.this.viewBox.getY() || viewBoxRect.getWidth() != JSVGScrollPane.this.viewBox.getWidth() || viewBoxRect.getHeight() != JSVGScrollPane.this.viewBox.getHeight()) {
                JSVGScrollPane.this.viewBox = viewBoxRect;
                JSVGScrollPane.this.resizeScrollBars();
            }
        }
        
        public void gvtBuildCancelled(final GVTTreeBuilderEvent gvtTreeBuilderEvent) {
        }
        
        public void gvtBuildFailed(final GVTTreeBuilderEvent gvtTreeBuilderEvent) {
        }
        
        public void gvtRenderingPrepare(final GVTTreeRendererEvent gvtTreeRendererEvent) {
        }
        
        public void gvtRenderingStarted(final GVTTreeRendererEvent gvtTreeRendererEvent) {
        }
        
        public void gvtRenderingCancelled(final GVTTreeRendererEvent gvtTreeRendererEvent) {
        }
        
        public void gvtRenderingFailed(final GVTTreeRendererEvent gvtTreeRendererEvent) {
        }
        
        public void managerStarted(final UpdateManagerEvent updateManagerEvent) {
        }
        
        public void managerSuspended(final UpdateManagerEvent updateManagerEvent) {
        }
        
        public void managerResumed(final UpdateManagerEvent updateManagerEvent) {
        }
        
        public void managerStopped(final UpdateManagerEvent updateManagerEvent) {
        }
        
        public void updateStarted(final UpdateManagerEvent updateManagerEvent) {
        }
        
        public void updateFailed(final UpdateManagerEvent updateManagerEvent) {
        }
    }
    
    protected class SBListener implements ChangeListener
    {
        protected boolean inDrag;
        protected int startValue;
        protected boolean isVertical;
        
        public SBListener(final boolean isVertical) {
            this.inDrag = false;
            this.isVertical = isVertical;
        }
        
        public synchronized void stateChanged(final ChangeEvent changeEvent) {
            if (JSVGScrollPane.this.ignoreScrollChange) {
                return;
            }
            final Object source = changeEvent.getSource();
            if (!(source instanceof BoundedRangeModel)) {
                return;
            }
            final int startValue = this.isVertical ? JSVGScrollPane.this.vertical.getValue() : JSVGScrollPane.this.horizontal.getValue();
            if (((BoundedRangeModel)source).getValueIsAdjusting()) {
                if (!this.inDrag) {
                    this.inDrag = true;
                    this.startValue = startValue;
                }
                else {
                    AffineTransform paintingTransform;
                    if (this.isVertical) {
                        paintingTransform = AffineTransform.getTranslateInstance(0.0, this.startValue - startValue);
                    }
                    else {
                        paintingTransform = AffineTransform.getTranslateInstance(this.startValue - startValue, 0.0);
                    }
                    JSVGScrollPane.this.canvas.setPaintingTransform(paintingTransform);
                }
            }
            else {
                if (this.inDrag) {
                    this.inDrag = false;
                    if (startValue == this.startValue) {
                        JSVGScrollPane.this.canvas.setPaintingTransform(new AffineTransform());
                        return;
                    }
                }
                JSVGScrollPane.this.setScrollPosition();
            }
        }
    }
    
    class SVGScrollDocumentLoaderListener extends SVGDocumentLoaderAdapter
    {
        private final /* synthetic */ JSVGScrollPane this$0;
        
        public void documentLoadingCompleted(final SVGDocumentLoaderEvent svgDocumentLoaderEvent) {
            ((NodeEventTarget)svgDocumentLoaderEvent.getSVGDocument().getRootElement()).addEventListenerNS("http://www.w3.org/2001/xml-events", "SVGZoom", new EventListener() {
                private final /* synthetic */ SVGScrollDocumentLoaderListener this$1 = this$1;
                
                public void handleEvent(final Event event) {
                    if (!(event.getTarget() instanceof SVGSVGElement)) {
                        return;
                    }
                    this.this$1.this$0.scaleChange(((SVGSVGElement)event.getTarget()).getCurrentScale());
                }
            }, false, null);
        }
    }
}
