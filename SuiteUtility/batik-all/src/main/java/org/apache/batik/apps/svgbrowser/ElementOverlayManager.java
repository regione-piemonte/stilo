// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.apps.svgbrowser;

import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.Stroke;
import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.Graphics;
import org.apache.batik.gvt.GraphicsNode;
import org.w3c.dom.Node;
import java.awt.Rectangle;
import org.w3c.dom.Element;
import java.util.ArrayList;
import org.apache.batik.swing.gvt.Overlay;
import org.apache.batik.swing.JSVGCanvas;
import java.awt.Color;

public class ElementOverlayManager
{
    protected Color elementOverlayStrokeColor;
    protected Color elementOverlayColor;
    protected boolean xorMode;
    protected JSVGCanvas canvas;
    protected Overlay elementOverlay;
    protected ArrayList elements;
    protected ElementOverlayController controller;
    protected boolean isOverlayEnabled;
    
    public ElementOverlayManager(final JSVGCanvas canvas) {
        this.elementOverlayStrokeColor = Color.black;
        this.elementOverlayColor = Color.white;
        this.xorMode = true;
        this.elementOverlay = new ElementOverlay();
        this.isOverlayEnabled = true;
        this.canvas = canvas;
        this.elements = new ArrayList();
        canvas.getOverlays().add(this.elementOverlay);
    }
    
    public void addElement(final Element e) {
        this.elements.add(e);
    }
    
    public void removeElement(final Element o) {
        if (this.elements.remove(o)) {}
    }
    
    public void removeElements() {
        this.elements.clear();
        this.repaint();
    }
    
    protected Rectangle getAllElementsBounds() {
        Rectangle rectangle = null;
        for (int size = this.elements.size(), i = 0; i < size; ++i) {
            final Rectangle elementBounds = this.getElementBounds(this.elements.get(i));
            if (rectangle == null) {
                rectangle = elementBounds;
            }
            else {
                rectangle.add(elementBounds);
            }
        }
        return rectangle;
    }
    
    protected Rectangle getElementBounds(final Element element) {
        return this.getElementBounds(this.canvas.getUpdateManager().getBridgeContext().getGraphicsNode(element));
    }
    
    protected Rectangle getElementBounds(final GraphicsNode graphicsNode) {
        if (graphicsNode == null) {
            return null;
        }
        return this.outset(this.canvas.getRenderingTransform().createTransformedShape(graphicsNode.getOutline()).getBounds(), 1);
    }
    
    protected Rectangle outset(final Rectangle rectangle, final int n) {
        rectangle.x -= n;
        rectangle.y -= n;
        rectangle.width += 2 * n;
        rectangle.height += 2 * n;
        return rectangle;
    }
    
    public void repaint() {
        this.canvas.repaint();
    }
    
    public Color getElementOverlayColor() {
        return this.elementOverlayColor;
    }
    
    public void setElementOverlayColor(final Color elementOverlayColor) {
        this.elementOverlayColor = elementOverlayColor;
    }
    
    public Color getElementOverlayStrokeColor() {
        return this.elementOverlayStrokeColor;
    }
    
    public void setElementOverlayStrokeColor(final Color elementOverlayStrokeColor) {
        this.elementOverlayStrokeColor = elementOverlayStrokeColor;
    }
    
    public boolean isXorMode() {
        return this.xorMode;
    }
    
    public void setXorMode(final boolean xorMode) {
        this.xorMode = xorMode;
    }
    
    public Overlay getElementOverlay() {
        return this.elementOverlay;
    }
    
    public void removeOverlay() {
        this.canvas.getOverlays().remove(this.elementOverlay);
    }
    
    public void setController(final ElementOverlayController controller) {
        this.controller = controller;
    }
    
    public boolean isOverlayEnabled() {
        return this.isOverlayEnabled;
    }
    
    public void setOverlayEnabled(final boolean isOverlayEnabled) {
        this.isOverlayEnabled = isOverlayEnabled;
    }
    
    public class ElementOverlay implements Overlay
    {
        public void paint(final Graphics graphics) {
            if (ElementOverlayManager.this.controller.isOverlayEnabled() && ElementOverlayManager.this.isOverlayEnabled()) {
                for (int size = ElementOverlayManager.this.elements.size(), i = 0; i < size; ++i) {
                    final GraphicsNode graphicsNode = ElementOverlayManager.this.canvas.getUpdateManager().getBridgeContext().getGraphicsNode(ElementOverlayManager.this.elements.get(i));
                    if (graphicsNode != null) {
                        final AffineTransform globalTransform = graphicsNode.getGlobalTransform();
                        final Shape outline = graphicsNode.getOutline();
                        final AffineTransform renderingTransform = ElementOverlayManager.this.canvas.getRenderingTransform();
                        renderingTransform.concatenate(globalTransform);
                        final Shape transformedShape = renderingTransform.createTransformedShape(outline);
                        if (transformedShape == null) {
                            break;
                        }
                        final Graphics2D graphics2D = (Graphics2D)graphics;
                        if (ElementOverlayManager.this.xorMode) {
                            graphics2D.setColor(Color.black);
                            graphics2D.setXORMode(Color.yellow);
                            graphics2D.fill(transformedShape);
                            graphics2D.draw(transformedShape);
                        }
                        else {
                            graphics2D.setColor(ElementOverlayManager.this.elementOverlayColor);
                            graphics2D.setStroke(new BasicStroke(1.8f));
                            graphics2D.setColor(ElementOverlayManager.this.elementOverlayStrokeColor);
                            graphics2D.draw(transformedShape);
                        }
                    }
                }
            }
        }
    }
}
