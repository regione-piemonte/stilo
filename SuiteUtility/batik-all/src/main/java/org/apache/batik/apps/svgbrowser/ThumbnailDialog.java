// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.apps.svgbrowser;

import org.apache.batik.swing.svg.SVGDocumentLoaderEvent;
import org.apache.batik.swing.svg.SVGDocumentLoaderAdapter;
import java.awt.geom.Point2D;
import java.awt.event.MouseEvent;
import javax.swing.event.MouseInputAdapter;
import org.apache.batik.swing.gvt.GVTTreeRendererEvent;
import org.apache.batik.swing.gvt.GVTTreeRendererAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentAdapter;
import java.awt.Stroke;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import java.awt.Shape;
import org.apache.batik.swing.gvt.Overlay;
import java.util.Locale;
import java.awt.geom.Dimension2D;
import org.w3c.dom.svg.SVGSVGElement;
import org.w3c.dom.svg.SVGDocument;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.geom.AffineTransform;
import org.apache.batik.bridge.BridgeContext;
import org.w3c.dom.Element;
import org.apache.batik.bridge.ViewBox;
import org.apache.batik.gvt.CompositeGraphicsNode;
import org.apache.batik.gvt.CanvasGraphicsNode;
import org.apache.batik.gvt.GraphicsNode;
import java.awt.Component;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseListener;
import java.awt.Dimension;
import java.awt.event.ComponentListener;
import org.apache.batik.swing.svg.SVGDocumentLoaderListener;
import org.apache.batik.swing.gvt.GVTTreeRendererListener;
import java.awt.event.WindowListener;
import java.awt.Frame;
import org.apache.batik.swing.gvt.JGVTComponent;
import org.apache.batik.swing.JSVGCanvas;
import org.apache.batik.util.resources.ResourceManager;
import java.util.ResourceBundle;
import javax.swing.JDialog;

public class ThumbnailDialog extends JDialog
{
    protected static final String RESOURCES = "org.apache.batik.apps.svgbrowser.resources.ThumbnailDialog";
    protected static ResourceBundle bundle;
    protected static ResourceManager resources;
    protected JSVGCanvas svgCanvas;
    protected JGVTComponent svgThumbnailCanvas;
    protected boolean documentChanged;
    protected AreaOfInterestOverlay overlay;
    protected AreaOfInterestListener aoiListener;
    protected boolean interactionEnabled;
    
    public ThumbnailDialog(final Frame owner, final JSVGCanvas svgCanvas) {
        super(owner, ThumbnailDialog.resources.getString("Dialog.title"));
        this.interactionEnabled = true;
        this.addWindowListener(new ThumbnailListener());
        (this.svgCanvas = svgCanvas).addGVTTreeRendererListener(new ThumbnailGVTListener());
        svgCanvas.addSVGDocumentLoaderListener(new ThumbnailDocumentListener());
        svgCanvas.addComponentListener(new ThumbnailCanvasComponentListener());
        this.svgThumbnailCanvas = new JGVTComponent();
        this.overlay = new AreaOfInterestOverlay();
        this.svgThumbnailCanvas.getOverlays().add(this.overlay);
        this.svgThumbnailCanvas.setPreferredSize(new Dimension(150, 150));
        this.svgThumbnailCanvas.addComponentListener(new ThumbnailComponentListener());
        this.aoiListener = new AreaOfInterestListener();
        this.svgThumbnailCanvas.addMouseListener(this.aoiListener);
        this.svgThumbnailCanvas.addMouseMotionListener(this.aoiListener);
        this.getContentPane().add(this.svgThumbnailCanvas, "Center");
    }
    
    public void setInteractionEnabled(final boolean interactionEnabled) {
        if (interactionEnabled == this.interactionEnabled) {
            return;
        }
        this.interactionEnabled = interactionEnabled;
        if (interactionEnabled) {
            this.svgThumbnailCanvas.addMouseListener(this.aoiListener);
            this.svgThumbnailCanvas.addMouseMotionListener(this.aoiListener);
        }
        else {
            this.svgThumbnailCanvas.removeMouseListener(this.aoiListener);
            this.svgThumbnailCanvas.removeMouseMotionListener(this.aoiListener);
        }
    }
    
    public boolean getInteractionEnabled() {
        return this.interactionEnabled;
    }
    
    protected void updateThumbnailGraphicsNode() {
        this.svgThumbnailCanvas.setGraphicsNode(this.svgCanvas.getGraphicsNode());
        this.updateThumbnailRenderingTransform();
    }
    
    protected CanvasGraphicsNode getCanvasGraphicsNode(GraphicsNode graphicsNode) {
        if (!(graphicsNode instanceof CompositeGraphicsNode)) {
            return null;
        }
        final CompositeGraphicsNode compositeGraphicsNode = (CompositeGraphicsNode)graphicsNode;
        if (compositeGraphicsNode.getChildren().size() == 0) {
            return null;
        }
        graphicsNode = (GraphicsNode)compositeGraphicsNode.getChildren().get(0);
        if (!(graphicsNode instanceof CanvasGraphicsNode)) {
            return null;
        }
        return (CanvasGraphicsNode)graphicsNode;
    }
    
    protected void updateThumbnailRenderingTransform() {
        final SVGDocument svgDocument = this.svgCanvas.getSVGDocument();
        if (svgDocument != null) {
            final SVGSVGElement rootElement = svgDocument.getRootElement();
            final Dimension size = this.svgThumbnailCanvas.getSize();
            final String attributeNS = rootElement.getAttributeNS((String)null, "viewBox");
            AffineTransform renderingTransform;
            if (attributeNS.length() != 0) {
                renderingTransform = ViewBox.getPreserveAspectRatioTransform((Element)rootElement, attributeNS, rootElement.getAttributeNS((String)null, "preserveAspectRatio"), (float)size.width, (float)size.height, null);
            }
            else {
                final Dimension2D svgDocumentSize = this.svgCanvas.getSVGDocumentSize();
                final double min = Math.min(size.width / svgDocumentSize.getWidth(), size.height / svgDocumentSize.getHeight());
                renderingTransform = AffineTransform.getScaleInstance(min, min);
            }
            final CanvasGraphicsNode canvasGraphicsNode = this.getCanvasGraphicsNode(this.svgCanvas.getGraphicsNode());
            if (canvasGraphicsNode != null) {
                final AffineTransform viewingTransform = canvasGraphicsNode.getViewingTransform();
                if (viewingTransform != null && !viewingTransform.isIdentity()) {
                    try {
                        renderingTransform.concatenate(viewingTransform.createInverse());
                    }
                    catch (NoninvertibleTransformException ex) {}
                }
            }
            this.svgThumbnailCanvas.setRenderingTransform(renderingTransform);
            this.overlay.synchronizeAreaOfInterest();
        }
    }
    
    static {
        ThumbnailDialog.bundle = ResourceBundle.getBundle("org.apache.batik.apps.svgbrowser.resources.ThumbnailDialog", Locale.getDefault());
        ThumbnailDialog.resources = new ResourceManager(ThumbnailDialog.bundle);
    }
    
    protected class AreaOfInterestOverlay implements Overlay
    {
        protected Shape s;
        protected AffineTransform at;
        protected AffineTransform paintingTransform;
        
        protected AreaOfInterestOverlay() {
            this.paintingTransform = new AffineTransform();
        }
        
        public boolean contains(final int n, final int n2) {
            return this.s != null && this.s.contains(n, n2);
        }
        
        public AffineTransform getOverlayTransform() {
            return this.at;
        }
        
        public void setPaintingTransform(final AffineTransform paintingTransform) {
            this.paintingTransform = paintingTransform;
        }
        
        public AffineTransform getPaintingTransform() {
            return this.paintingTransform;
        }
        
        public void synchronizeAreaOfInterest() {
            this.paintingTransform = new AffineTransform();
            final Dimension size = ThumbnailDialog.this.svgCanvas.getSize();
            this.s = new Rectangle2D.Float(0.0f, 0.0f, (float)size.width, (float)size.height);
            try {
                (this.at = ThumbnailDialog.this.svgCanvas.getRenderingTransform().createInverse()).preConcatenate(ThumbnailDialog.this.svgThumbnailCanvas.getRenderingTransform());
                this.s = this.at.createTransformedShape(this.s);
            }
            catch (NoninvertibleTransformException ex) {
                final Dimension size2 = ThumbnailDialog.this.svgThumbnailCanvas.getSize();
                this.s = new Rectangle2D.Float(0.0f, 0.0f, (float)size2.width, (float)size2.height);
            }
        }
        
        public void paint(final Graphics graphics) {
            if (this.s != null) {
                final Graphics2D graphics2D = (Graphics2D)graphics;
                graphics2D.transform(this.paintingTransform);
                graphics2D.setColor(new Color(255, 255, 255, 128));
                graphics2D.fill(this.s);
                graphics2D.setColor(Color.black);
                graphics2D.setStroke(new BasicStroke());
                graphics2D.draw(this.s);
            }
        }
    }
    
    protected class ThumbnailCanvasComponentListener extends ComponentAdapter
    {
        public void componentResized(final ComponentEvent componentEvent) {
            ThumbnailDialog.this.updateThumbnailRenderingTransform();
        }
    }
    
    protected class ThumbnailComponentListener extends ComponentAdapter
    {
        public void componentResized(final ComponentEvent componentEvent) {
            ThumbnailDialog.this.updateThumbnailRenderingTransform();
        }
    }
    
    protected class ThumbnailListener extends WindowAdapter
    {
        public void windowOpened(final WindowEvent windowEvent) {
            ThumbnailDialog.this.updateThumbnailGraphicsNode();
        }
    }
    
    protected class ThumbnailGVTListener extends GVTTreeRendererAdapter
    {
        public void gvtRenderingCompleted(final GVTTreeRendererEvent gvtTreeRendererEvent) {
            if (ThumbnailDialog.this.documentChanged) {
                ThumbnailDialog.this.updateThumbnailGraphicsNode();
                ThumbnailDialog.this.documentChanged = false;
            }
            else {
                ThumbnailDialog.this.overlay.synchronizeAreaOfInterest();
                ThumbnailDialog.this.svgThumbnailCanvas.repaint();
            }
        }
        
        public void gvtRenderingCancelled(final GVTTreeRendererEvent gvtTreeRendererEvent) {
            if (ThumbnailDialog.this.documentChanged) {
                ThumbnailDialog.this.svgThumbnailCanvas.setGraphicsNode(null);
                ThumbnailDialog.this.svgThumbnailCanvas.setRenderingTransform(new AffineTransform());
            }
        }
        
        public void gvtRenderingFailed(final GVTTreeRendererEvent gvtTreeRendererEvent) {
            if (ThumbnailDialog.this.documentChanged) {
                ThumbnailDialog.this.svgThumbnailCanvas.setGraphicsNode(null);
                ThumbnailDialog.this.svgThumbnailCanvas.setRenderingTransform(new AffineTransform());
            }
        }
    }
    
    protected class AreaOfInterestListener extends MouseInputAdapter
    {
        protected int sx;
        protected int sy;
        protected boolean in;
        
        public void mousePressed(final MouseEvent mouseEvent) {
            this.sx = mouseEvent.getX();
            this.sy = mouseEvent.getY();
            this.in = ThumbnailDialog.this.overlay.contains(this.sx, this.sy);
            ThumbnailDialog.this.overlay.setPaintingTransform(new AffineTransform());
        }
        
        public void mouseDragged(final MouseEvent mouseEvent) {
            if (this.in) {
                ThumbnailDialog.this.overlay.setPaintingTransform(AffineTransform.getTranslateInstance(mouseEvent.getX() - this.sx, mouseEvent.getY() - this.sy));
                ThumbnailDialog.this.svgThumbnailCanvas.repaint();
            }
        }
        
        public void mouseReleased(final MouseEvent mouseEvent) {
            if (this.in) {
                this.in = false;
                final int n = mouseEvent.getX() - this.sx;
                final int n2 = mouseEvent.getY() - this.sy;
                final AffineTransform overlayTransform = ThumbnailDialog.this.overlay.getOverlayTransform();
                final Point2D.Float float1 = new Point2D.Float(0.0f, 0.0f);
                final Point2D.Float float2 = new Point2D.Float((float)n, (float)n2);
                try {
                    overlayTransform.inverseTransform(float1, float1);
                    overlayTransform.inverseTransform(float2, float2);
                    final double tx = float1.getX() - float2.getX();
                    final double ty = float1.getY() - float2.getY();
                    final AffineTransform renderingTransform = ThumbnailDialog.this.svgCanvas.getRenderingTransform();
                    renderingTransform.preConcatenate(AffineTransform.getTranslateInstance(tx, ty));
                    ThumbnailDialog.this.svgCanvas.setRenderingTransform(renderingTransform);
                }
                catch (NoninvertibleTransformException ex) {}
            }
        }
    }
    
    protected class ThumbnailDocumentListener extends SVGDocumentLoaderAdapter
    {
        public void documentLoadingStarted(final SVGDocumentLoaderEvent svgDocumentLoaderEvent) {
            ThumbnailDialog.this.documentChanged = true;
        }
    }
}
