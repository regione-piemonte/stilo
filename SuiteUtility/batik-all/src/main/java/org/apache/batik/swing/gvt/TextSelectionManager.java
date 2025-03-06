// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.swing.gvt;

import org.apache.batik.gvt.Selectable;
import org.apache.batik.gvt.event.GraphicsNodeMouseEvent;
import java.awt.geom.AffineTransform;
import org.apache.batik.gvt.event.SelectionEvent;
import java.awt.Stroke;
import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.Graphics;
import java.awt.Rectangle;
import org.apache.batik.gvt.text.Mark;
import org.apache.batik.gvt.event.GraphicsNodeMouseListener;
import org.apache.batik.gvt.event.EventDispatcher;
import java.awt.Color;
import org.apache.batik.gvt.event.SelectionListener;
import java.awt.Shape;
import org.apache.batik.gvt.text.ConcreteTextSelector;
import java.awt.Cursor;

public class TextSelectionManager
{
    public static final Cursor TEXT_CURSOR;
    protected ConcreteTextSelector textSelector;
    protected AbstractJGVTComponent component;
    protected Overlay selectionOverlay;
    protected MouseListener mouseListener;
    protected Cursor previousCursor;
    protected Shape selectionHighlight;
    protected SelectionListener textSelectionListener;
    protected Color selectionOverlayColor;
    protected Color selectionOverlayStrokeColor;
    protected boolean xorMode;
    Object selection;
    
    public TextSelectionManager(final AbstractJGVTComponent component, final EventDispatcher eventDispatcher) {
        this.selectionOverlay = new SelectionOverlay();
        this.selectionOverlayColor = new Color(100, 100, 255, 100);
        this.selectionOverlayStrokeColor = Color.white;
        this.xorMode = false;
        this.selection = null;
        this.textSelector = new ConcreteTextSelector();
        this.textSelectionListener = new TextSelectionListener();
        this.textSelector.addSelectionListener(this.textSelectionListener);
        this.mouseListener = new MouseListener();
        this.component = component;
        this.component.getOverlays().add(this.selectionOverlay);
        eventDispatcher.addGraphicsNodeMouseListener(this.mouseListener);
    }
    
    public void addSelectionListener(final SelectionListener selectionListener) {
        this.textSelector.addSelectionListener(selectionListener);
    }
    
    public void removeSelectionListener(final SelectionListener selectionListener) {
        this.textSelector.removeSelectionListener(selectionListener);
    }
    
    public void setSelectionOverlayColor(final Color selectionOverlayColor) {
        this.selectionOverlayColor = selectionOverlayColor;
    }
    
    public Color getSelectionOverlayColor() {
        return this.selectionOverlayColor;
    }
    
    public void setSelectionOverlayStrokeColor(final Color selectionOverlayStrokeColor) {
        this.selectionOverlayStrokeColor = selectionOverlayStrokeColor;
    }
    
    public Color getSelectionOverlayStrokeColor() {
        return this.selectionOverlayStrokeColor;
    }
    
    public void setSelectionOverlayXORMode(final boolean xorMode) {
        this.xorMode = xorMode;
    }
    
    public boolean isSelectionOverlayXORMode() {
        return this.xorMode;
    }
    
    public Overlay getSelectionOverlay() {
        return this.selectionOverlay;
    }
    
    public Object getSelection() {
        return this.selection;
    }
    
    public void setSelection(final Mark mark, final Mark mark2) {
        this.textSelector.setSelection(mark, mark2);
    }
    
    public void clearSelection() {
        this.textSelector.clearSelection();
    }
    
    protected Rectangle outset(final Rectangle rectangle, final int n) {
        rectangle.x -= n;
        rectangle.y -= n;
        rectangle.width += 2 * n;
        rectangle.height += 2 * n;
        return rectangle;
    }
    
    protected Rectangle getHighlightBounds() {
        return this.outset(this.component.getRenderingTransform().createTransformedShape(this.selectionHighlight).getBounds(), 1);
    }
    
    static {
        TEXT_CURSOR = new Cursor(2);
    }
    
    protected class SelectionOverlay implements Overlay
    {
        public void paint(final Graphics graphics) {
            if (TextSelectionManager.this.selectionHighlight != null) {
                final Shape transformedShape = TextSelectionManager.this.component.getRenderingTransform().createTransformedShape(TextSelectionManager.this.selectionHighlight);
                final Graphics2D graphics2D = (Graphics2D)graphics;
                if (TextSelectionManager.this.xorMode) {
                    graphics2D.setColor(Color.black);
                    graphics2D.setXORMode(Color.white);
                    graphics2D.fill(transformedShape);
                }
                else {
                    graphics2D.setColor(TextSelectionManager.this.selectionOverlayColor);
                    graphics2D.fill(transformedShape);
                    if (TextSelectionManager.this.selectionOverlayStrokeColor != null) {
                        graphics2D.setStroke(new BasicStroke(1.0f));
                        graphics2D.setColor(TextSelectionManager.this.selectionOverlayStrokeColor);
                        graphics2D.draw(transformedShape);
                    }
                }
            }
        }
    }
    
    protected class TextSelectionListener implements SelectionListener
    {
        public void selectionDone(final SelectionEvent selectionEvent) {
            this.selectionChanged(selectionEvent);
            TextSelectionManager.this.selection = selectionEvent.getSelection();
        }
        
        public void selectionCleared(final SelectionEvent selectionEvent) {
            this.selectionStarted(selectionEvent);
        }
        
        public void selectionStarted(final SelectionEvent selectionEvent) {
            if (TextSelectionManager.this.selectionHighlight != null) {
                final Rectangle highlightBounds = TextSelectionManager.this.getHighlightBounds();
                TextSelectionManager.this.selectionHighlight = null;
                TextSelectionManager.this.component.repaint(highlightBounds);
            }
            TextSelectionManager.this.selection = null;
        }
        
        public void selectionChanged(final SelectionEvent selectionEvent) {
            Rectangle bounds = null;
            final AffineTransform renderingTransform = TextSelectionManager.this.component.getRenderingTransform();
            if (TextSelectionManager.this.selectionHighlight != null) {
                bounds = renderingTransform.createTransformedShape(TextSelectionManager.this.selectionHighlight).getBounds();
                TextSelectionManager.this.outset(bounds, 1);
            }
            TextSelectionManager.this.selectionHighlight = selectionEvent.getHighlightShape();
            if (TextSelectionManager.this.selectionHighlight != null) {
                if (bounds != null) {
                    final Rectangle highlightBounds = TextSelectionManager.this.getHighlightBounds();
                    highlightBounds.add(bounds);
                    TextSelectionManager.this.component.repaint(highlightBounds);
                }
                else {
                    TextSelectionManager.this.component.repaint(TextSelectionManager.this.getHighlightBounds());
                }
            }
            else if (bounds != null) {
                TextSelectionManager.this.component.repaint(bounds);
            }
        }
    }
    
    protected class MouseListener implements GraphicsNodeMouseListener
    {
        public void mouseClicked(final GraphicsNodeMouseEvent graphicsNodeMouseEvent) {
            if (graphicsNodeMouseEvent.getSource() instanceof Selectable) {
                TextSelectionManager.this.textSelector.mouseClicked(graphicsNodeMouseEvent);
            }
        }
        
        public void mousePressed(final GraphicsNodeMouseEvent graphicsNodeMouseEvent) {
            if (graphicsNodeMouseEvent.getSource() instanceof Selectable) {
                TextSelectionManager.this.textSelector.mousePressed(graphicsNodeMouseEvent);
            }
            else if (TextSelectionManager.this.selectionHighlight != null) {
                TextSelectionManager.this.textSelector.clearSelection();
            }
        }
        
        public void mouseReleased(final GraphicsNodeMouseEvent graphicsNodeMouseEvent) {
            TextSelectionManager.this.textSelector.mouseReleased(graphicsNodeMouseEvent);
        }
        
        public void mouseEntered(final GraphicsNodeMouseEvent graphicsNodeMouseEvent) {
            if (graphicsNodeMouseEvent.getSource() instanceof Selectable) {
                TextSelectionManager.this.textSelector.mouseEntered(graphicsNodeMouseEvent);
                TextSelectionManager.this.previousCursor = TextSelectionManager.this.component.getCursor();
                if (TextSelectionManager.this.previousCursor.getType() == 0) {
                    TextSelectionManager.this.component.setCursor(TextSelectionManager.TEXT_CURSOR);
                }
            }
        }
        
        public void mouseExited(final GraphicsNodeMouseEvent graphicsNodeMouseEvent) {
            if (graphicsNodeMouseEvent.getSource() instanceof Selectable) {
                TextSelectionManager.this.textSelector.mouseExited(graphicsNodeMouseEvent);
                if (TextSelectionManager.this.component.getCursor() == TextSelectionManager.TEXT_CURSOR) {
                    TextSelectionManager.this.component.setCursor(TextSelectionManager.this.previousCursor);
                }
            }
        }
        
        public void mouseDragged(final GraphicsNodeMouseEvent graphicsNodeMouseEvent) {
            if (graphicsNodeMouseEvent.getSource() instanceof Selectable) {
                TextSelectionManager.this.textSelector.mouseDragged(graphicsNodeMouseEvent);
            }
        }
        
        public void mouseMoved(final GraphicsNodeMouseEvent graphicsNodeMouseEvent) {
        }
    }
}
