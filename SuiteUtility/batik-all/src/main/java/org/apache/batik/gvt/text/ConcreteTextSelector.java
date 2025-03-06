// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.gvt.text;

import java.text.AttributedCharacterIterator;
import java.util.Iterator;
import org.apache.batik.gvt.event.SelectionListener;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import org.apache.batik.gvt.event.GraphicsNodeChangeListener;
import java.awt.Shape;
import org.apache.batik.gvt.TextNode;
import org.apache.batik.gvt.event.SelectionEvent;
import org.apache.batik.gvt.Selectable;
import org.apache.batik.gvt.event.GraphicsNodeChangeEvent;
import org.apache.batik.gvt.event.GraphicsNodeKeyEvent;
import org.apache.batik.gvt.event.GraphicsNodeEvent;
import org.apache.batik.gvt.event.GraphicsNodeMouseEvent;
import org.apache.batik.gvt.RootGraphicsNode;
import org.apache.batik.gvt.GraphicsNode;
import java.util.ArrayList;
import org.apache.batik.gvt.Selector;

public class ConcreteTextSelector implements Selector
{
    private ArrayList listeners;
    private GraphicsNode selectionNode;
    private RootGraphicsNode selectionNodeRoot;
    
    public void mouseClicked(final GraphicsNodeMouseEvent graphicsNodeMouseEvent) {
        this.checkSelectGesture(graphicsNodeMouseEvent);
    }
    
    public void mouseDragged(final GraphicsNodeMouseEvent graphicsNodeMouseEvent) {
        this.checkSelectGesture(graphicsNodeMouseEvent);
    }
    
    public void mouseEntered(final GraphicsNodeMouseEvent graphicsNodeMouseEvent) {
        this.checkSelectGesture(graphicsNodeMouseEvent);
    }
    
    public void mouseExited(final GraphicsNodeMouseEvent graphicsNodeMouseEvent) {
        this.checkSelectGesture(graphicsNodeMouseEvent);
    }
    
    public void mouseMoved(final GraphicsNodeMouseEvent graphicsNodeMouseEvent) {
    }
    
    public void mousePressed(final GraphicsNodeMouseEvent graphicsNodeMouseEvent) {
        this.checkSelectGesture(graphicsNodeMouseEvent);
    }
    
    public void mouseReleased(final GraphicsNodeMouseEvent graphicsNodeMouseEvent) {
        this.checkSelectGesture(graphicsNodeMouseEvent);
    }
    
    public void keyPressed(final GraphicsNodeKeyEvent graphicsNodeKeyEvent) {
        this.report(graphicsNodeKeyEvent, "keyPressed");
    }
    
    public void keyReleased(final GraphicsNodeKeyEvent graphicsNodeKeyEvent) {
        this.report(graphicsNodeKeyEvent, "keyReleased");
    }
    
    public void keyTyped(final GraphicsNodeKeyEvent graphicsNodeKeyEvent) {
        this.report(graphicsNodeKeyEvent, "keyTyped");
    }
    
    public void changeStarted(final GraphicsNodeChangeEvent graphicsNodeChangeEvent) {
    }
    
    public void changeCompleted(final GraphicsNodeChangeEvent graphicsNodeChangeEvent) {
        if (this.selectionNode == null) {
            return;
        }
        this.dispatchSelectionEvent(new SelectionEvent(this.getSelection(), 1, ((Selectable)this.selectionNode).getHighlightShape()));
    }
    
    public void setSelection(final Mark mark, final Mark mark2) {
        final TextNode textNode = mark.getTextNode();
        if (textNode != mark2.getTextNode()) {
            throw new Error("Markers not from same TextNode");
        }
        textNode.setSelection(mark, mark2);
        this.selectionNode = textNode;
        this.selectionNodeRoot = textNode.getRoot();
        this.dispatchSelectionEvent(new SelectionEvent(this.getSelection(), 2, textNode.getHighlightShape()));
    }
    
    public void clearSelection() {
        if (this.selectionNode == null) {
            return;
        }
        this.dispatchSelectionEvent(new SelectionEvent(null, 3, null));
        this.selectionNode = null;
        this.selectionNodeRoot = null;
    }
    
    protected void checkSelectGesture(final GraphicsNodeEvent graphicsNodeEvent) {
        GraphicsNodeMouseEvent graphicsNodeMouseEvent = null;
        if (graphicsNodeEvent instanceof GraphicsNodeMouseEvent) {
            graphicsNodeMouseEvent = (GraphicsNodeMouseEvent)graphicsNodeEvent;
        }
        final GraphicsNode graphicsNode = graphicsNodeEvent.getGraphicsNode();
        if (this.isDeselectGesture(graphicsNodeEvent)) {
            if (this.selectionNode != null) {
                this.selectionNodeRoot.removeTreeGraphicsNodeChangeListener(this);
            }
            this.clearSelection();
        }
        else if (graphicsNodeMouseEvent != null) {
            final Point2D.Double ptSrc = new Point2D.Double(graphicsNodeMouseEvent.getX(), graphicsNodeMouseEvent.getY());
            AffineTransform affineTransform = graphicsNode.getGlobalTransform();
            if (affineTransform == null) {
                affineTransform = new AffineTransform();
            }
            else {
                try {
                    affineTransform = affineTransform.createInverse();
                }
                catch (NoninvertibleTransformException ex) {}
            }
            final Point2D transform = affineTransform.transform(ptSrc, null);
            if (graphicsNode instanceof Selectable && this.isSelectStartGesture(graphicsNodeEvent)) {
                if (this.selectionNode != graphicsNode) {
                    if (this.selectionNode != null) {
                        this.selectionNodeRoot.removeTreeGraphicsNodeChangeListener(this);
                    }
                    if ((this.selectionNode = graphicsNode) != null) {
                        (this.selectionNodeRoot = graphicsNode.getRoot()).addTreeGraphicsNodeChangeListener(this);
                    }
                }
                ((Selectable)graphicsNode).selectAt(transform.getX(), transform.getY());
                this.dispatchSelectionEvent(new SelectionEvent(null, 4, null));
            }
            else if (this.isSelectEndGesture(graphicsNodeEvent)) {
                if (this.selectionNode == graphicsNode) {
                    ((Selectable)graphicsNode).selectTo(transform.getX(), transform.getY());
                }
                final Object selection = this.getSelection();
                if (this.selectionNode != null) {
                    this.dispatchSelectionEvent(new SelectionEvent(selection, 2, ((Selectable)this.selectionNode).getHighlightShape()));
                }
            }
            else if (this.isSelectContinueGesture(graphicsNodeEvent)) {
                if (this.selectionNode == graphicsNode && ((Selectable)graphicsNode).selectTo(transform.getX(), transform.getY())) {
                    this.dispatchSelectionEvent(new SelectionEvent(null, 1, ((Selectable)this.selectionNode).getHighlightShape()));
                }
            }
            else if (graphicsNode instanceof Selectable && this.isSelectAllGesture(graphicsNodeEvent)) {
                if (this.selectionNode != graphicsNode) {
                    if (this.selectionNode != null) {
                        this.selectionNodeRoot.removeTreeGraphicsNodeChangeListener(this);
                    }
                    if ((this.selectionNode = graphicsNode) != null) {
                        (this.selectionNodeRoot = graphicsNode.getRoot()).addTreeGraphicsNodeChangeListener(this);
                    }
                }
                ((Selectable)graphicsNode).selectAll(transform.getX(), transform.getY());
                this.dispatchSelectionEvent(new SelectionEvent(this.getSelection(), 2, ((Selectable)graphicsNode).getHighlightShape()));
            }
        }
    }
    
    private boolean isDeselectGesture(final GraphicsNodeEvent graphicsNodeEvent) {
        return graphicsNodeEvent.getID() == 500 && ((GraphicsNodeMouseEvent)graphicsNodeEvent).getClickCount() == 1;
    }
    
    private boolean isSelectStartGesture(final GraphicsNodeEvent graphicsNodeEvent) {
        return graphicsNodeEvent.getID() == 501;
    }
    
    private boolean isSelectEndGesture(final GraphicsNodeEvent graphicsNodeEvent) {
        return graphicsNodeEvent.getID() == 502;
    }
    
    private boolean isSelectContinueGesture(final GraphicsNodeEvent graphicsNodeEvent) {
        return graphicsNodeEvent.getID() == 506;
    }
    
    private boolean isSelectAllGesture(final GraphicsNodeEvent graphicsNodeEvent) {
        return graphicsNodeEvent.getID() == 500 && ((GraphicsNodeMouseEvent)graphicsNodeEvent).getClickCount() == 2;
    }
    
    public Object getSelection() {
        Object selection = null;
        if (this.selectionNode instanceof Selectable) {
            selection = ((Selectable)this.selectionNode).getSelection();
        }
        return selection;
    }
    
    public boolean isEmpty() {
        return this.getSelection() == null;
    }
    
    public void dispatchSelectionEvent(final SelectionEvent selectionEvent) {
        if (this.listeners != null) {
            final Iterator<SelectionListener> iterator = (Iterator<SelectionListener>)this.listeners.iterator();
            switch (selectionEvent.getID()) {
                case 2: {
                    while (iterator.hasNext()) {
                        iterator.next().selectionDone(selectionEvent);
                    }
                    break;
                }
                case 1: {
                    while (iterator.hasNext()) {
                        iterator.next().selectionChanged(selectionEvent);
                    }
                    break;
                }
                case 3: {
                    while (iterator.hasNext()) {
                        iterator.next().selectionCleared(selectionEvent);
                    }
                    break;
                }
                case 4: {
                    while (iterator.hasNext()) {
                        iterator.next().selectionStarted(selectionEvent);
                    }
                    break;
                }
            }
        }
    }
    
    public void addSelectionListener(final SelectionListener e) {
        if (this.listeners == null) {
            this.listeners = new ArrayList();
        }
        this.listeners.add(e);
    }
    
    public void removeSelectionListener(final SelectionListener o) {
        if (this.listeners != null) {
            this.listeners.remove(o);
        }
    }
    
    private void report(final GraphicsNodeEvent graphicsNodeEvent, final String str) {
        final GraphicsNode graphicsNode = graphicsNodeEvent.getGraphicsNode();
        String str2 = "(non-text node)";
        if (graphicsNode instanceof TextNode) {
            final AttributedCharacterIterator attributedCharacterIterator = ((TextNode)graphicsNode).getAttributedCharacterIterator();
            final char[] value = new char[attributedCharacterIterator.getEndIndex()];
            if (value.length > 0) {
                value[0] = attributedCharacterIterator.first();
            }
            for (int i = 1; i < value.length; ++i) {
                value[i] = attributedCharacterIterator.next();
            }
            str2 = new String(value);
        }
        System.out.println("Mouse " + str + " in " + str2);
    }
}
