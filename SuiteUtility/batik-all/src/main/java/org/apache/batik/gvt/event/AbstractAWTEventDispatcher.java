// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.gvt.event;

import java.awt.Point;
import java.awt.geom.Point2D;
import java.awt.Toolkit;
import java.awt.event.InputEvent;
import java.lang.reflect.Array;
import java.util.EventListener;
import java.awt.event.KeyEvent;
import java.util.EventObject;
import java.awt.event.MouseEvent;
import java.util.LinkedList;
import java.util.List;
import javax.swing.event.EventListenerList;
import java.awt.geom.AffineTransform;
import org.apache.batik.gvt.GraphicsNode;
import java.awt.event.KeyListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseListener;

public abstract class AbstractAWTEventDispatcher implements EventDispatcher, MouseListener, MouseMotionListener, KeyListener
{
    protected GraphicsNode root;
    protected AffineTransform baseTransform;
    protected EventListenerList glisteners;
    protected GraphicsNode lastHit;
    protected GraphicsNode currentKeyEventTarget;
    protected List eventQueue;
    protected boolean eventDispatchEnabled;
    protected int eventQueueMaxSize;
    static final int MAX_QUEUE_SIZE = 10;
    private int nodeIncrementEventID;
    private int nodeIncrementEventCode;
    private int nodeIncrementEventModifiers;
    private int nodeDecrementEventID;
    private int nodeDecrementEventCode;
    private int nodeDecrementEventModifiers;
    
    public AbstractAWTEventDispatcher() {
        this.eventQueue = new LinkedList();
        this.eventDispatchEnabled = true;
        this.eventQueueMaxSize = 10;
        this.nodeIncrementEventID = 401;
        this.nodeIncrementEventCode = 9;
        this.nodeIncrementEventModifiers = 0;
        this.nodeDecrementEventID = 401;
        this.nodeDecrementEventCode = 9;
        this.nodeDecrementEventModifiers = 1;
    }
    
    public void setRootNode(final GraphicsNode root) {
        if (this.root != root) {
            this.eventQueue.clear();
        }
        this.root = root;
    }
    
    public GraphicsNode getRootNode() {
        return this.root;
    }
    
    public void setBaseTransform(final AffineTransform affineTransform) {
        if (this.baseTransform != affineTransform && (this.baseTransform == null || !this.baseTransform.equals(affineTransform))) {
            this.eventQueue.clear();
        }
        this.baseTransform = affineTransform;
    }
    
    public AffineTransform getBaseTransform() {
        return new AffineTransform(this.baseTransform);
    }
    
    public void mousePressed(final MouseEvent mouseEvent) {
        this.dispatchEvent(mouseEvent);
    }
    
    public void mouseReleased(final MouseEvent mouseEvent) {
        this.dispatchEvent(mouseEvent);
    }
    
    public void mouseEntered(final MouseEvent mouseEvent) {
        this.dispatchEvent(mouseEvent);
    }
    
    public void mouseExited(final MouseEvent mouseEvent) {
        this.dispatchEvent(mouseEvent);
    }
    
    public void mouseClicked(final MouseEvent mouseEvent) {
        this.dispatchEvent(mouseEvent);
    }
    
    public void mouseMoved(final MouseEvent mouseEvent) {
        this.dispatchEvent(mouseEvent);
    }
    
    public void mouseDragged(final MouseEvent mouseEvent) {
        this.dispatchEvent(mouseEvent);
    }
    
    public void keyPressed(final KeyEvent keyEvent) {
        this.dispatchEvent(keyEvent);
    }
    
    public void keyReleased(final KeyEvent keyEvent) {
        this.dispatchEvent(keyEvent);
    }
    
    public void keyTyped(final KeyEvent keyEvent) {
        this.dispatchEvent(keyEvent);
    }
    
    public void addGraphicsNodeMouseListener(final GraphicsNodeMouseListener l) {
        if (this.glisteners == null) {
            this.glisteners = new EventListenerList();
        }
        this.glisteners.add(GraphicsNodeMouseListener.class, l);
    }
    
    public void removeGraphicsNodeMouseListener(final GraphicsNodeMouseListener l) {
        if (this.glisteners != null) {
            this.glisteners.remove(GraphicsNodeMouseListener.class, l);
        }
    }
    
    public void addGraphicsNodeMouseWheelListener(final GraphicsNodeMouseWheelListener l) {
        if (this.glisteners == null) {
            this.glisteners = new EventListenerList();
        }
        this.glisteners.add(GraphicsNodeMouseWheelListener.class, l);
    }
    
    public void removeGraphicsNodeMouseWheelListener(final GraphicsNodeMouseWheelListener l) {
        if (this.glisteners != null) {
            this.glisteners.remove(GraphicsNodeMouseWheelListener.class, l);
        }
    }
    
    public void addGraphicsNodeKeyListener(final GraphicsNodeKeyListener l) {
        if (this.glisteners == null) {
            this.glisteners = new EventListenerList();
        }
        this.glisteners.add(GraphicsNodeKeyListener.class, l);
    }
    
    public void removeGraphicsNodeKeyListener(final GraphicsNodeKeyListener l) {
        if (this.glisteners != null) {
            this.glisteners.remove(GraphicsNodeKeyListener.class, l);
        }
    }
    
    public EventListener[] getListeners(final Class obj) {
        final Object instance = Array.newInstance(obj, this.glisteners.getListenerCount(obj));
        final Object[] listenerList = this.glisteners.getListenerList();
        int i = 0;
        int n = 0;
        while (i < listenerList.length - 1) {
            if (listenerList[i].equals(obj)) {
                Array.set(instance, n, listenerList[i + 1]);
                ++n;
            }
            i += 2;
        }
        return (EventListener[])instance;
    }
    
    public void setEventDispatchEnabled(final boolean eventDispatchEnabled) {
        this.eventDispatchEnabled = eventDispatchEnabled;
        if (this.eventDispatchEnabled) {
            while (this.eventQueue.size() > 0) {
                this.dispatchEvent(this.eventQueue.remove(0));
            }
        }
    }
    
    public void setEventQueueMaxSize(final int eventQueueMaxSize) {
        this.eventQueueMaxSize = eventQueueMaxSize;
        if (eventQueueMaxSize == 0) {
            this.eventQueue.clear();
        }
        while (this.eventQueue.size() > this.eventQueueMaxSize) {
            this.eventQueue.remove(0);
        }
    }
    
    public void dispatchEvent(final EventObject eventObject) {
        if (this.root == null) {
            return;
        }
        if (!this.eventDispatchEnabled) {
            if (this.eventQueueMaxSize > 0) {
                this.eventQueue.add(eventObject);
                while (this.eventQueue.size() > this.eventQueueMaxSize) {
                    this.eventQueue.remove(0);
                }
            }
            return;
        }
        if (eventObject instanceof MouseEvent) {
            this.dispatchMouseEvent((MouseEvent)eventObject);
        }
        else if (eventObject instanceof KeyEvent) {
            final InputEvent inputEvent = (InputEvent)eventObject;
            if (this.isNodeIncrementEvent(inputEvent)) {
                this.incrementKeyTarget();
            }
            else if (this.isNodeDecrementEvent(inputEvent)) {
                this.decrementKeyTarget();
            }
            else {
                this.dispatchKeyEvent((KeyEvent)eventObject);
            }
        }
    }
    
    protected int getCurrentLockState() {
        final Toolkit defaultToolkit = Toolkit.getDefaultToolkit();
        int n = 0;
        try {
            if (defaultToolkit.getLockingKeyState(262)) {
                ++n;
            }
        }
        catch (UnsupportedOperationException ex) {}
        int n2 = n << 1;
        try {
            if (defaultToolkit.getLockingKeyState(145)) {
                ++n2;
            }
        }
        catch (UnsupportedOperationException ex2) {}
        int n3 = n2 << 1;
        try {
            if (defaultToolkit.getLockingKeyState(144)) {
                ++n3;
            }
        }
        catch (UnsupportedOperationException ex3) {}
        int n4 = n3 << 1;
        try {
            if (defaultToolkit.getLockingKeyState(20)) {
                ++n4;
            }
        }
        catch (UnsupportedOperationException ex4) {}
        return n4;
    }
    
    protected abstract void dispatchKeyEvent(final KeyEvent p0);
    
    protected abstract int getModifiers(final InputEvent p0);
    
    protected abstract int getButton(final MouseEvent p0);
    
    protected void dispatchMouseEvent(final MouseEvent mouseEvent) {
        Point2D transform;
        final Point2D.Float ptSrc = (Point2D.Float)(transform = new Point2D.Float((float)mouseEvent.getX(), (float)mouseEvent.getY()));
        if (this.baseTransform != null) {
            transform = this.baseTransform.transform(ptSrc, null);
        }
        final GraphicsNode nodeHit = this.root.nodeHitAt(transform);
        Point point;
        if (!mouseEvent.getComponent().isShowing()) {
            point = new Point(0, 0);
        }
        else {
            final Point locationOnScreen;
            point = (locationOnScreen = mouseEvent.getComponent().getLocationOnScreen());
            locationOnScreen.x += mouseEvent.getX();
            final Point point2 = point;
            point2.y += mouseEvent.getY();
        }
        final int currentLockState = this.getCurrentLockState();
        if (this.lastHit != nodeHit) {
            if (this.lastHit != null) {
                this.processMouseEvent(new GraphicsNodeMouseEvent(this.lastHit, 505, mouseEvent.getWhen(), this.getModifiers(mouseEvent), currentLockState, this.getButton(mouseEvent), (float)transform.getX(), (float)transform.getY(), (int)Math.floor(ptSrc.getX()), (int)Math.floor(ptSrc.getY()), point.x, point.y, mouseEvent.getClickCount(), nodeHit));
            }
            if (nodeHit != null) {
                this.processMouseEvent(new GraphicsNodeMouseEvent(nodeHit, 504, mouseEvent.getWhen(), this.getModifiers(mouseEvent), currentLockState, this.getButton(mouseEvent), (float)transform.getX(), (float)transform.getY(), (int)Math.floor(ptSrc.getX()), (int)Math.floor(ptSrc.getY()), point.x, point.y, mouseEvent.getClickCount(), this.lastHit));
            }
        }
        if (nodeHit != null) {
            this.processMouseEvent(new GraphicsNodeMouseEvent(nodeHit, mouseEvent.getID(), mouseEvent.getWhen(), this.getModifiers(mouseEvent), currentLockState, this.getButton(mouseEvent), (float)transform.getX(), (float)transform.getY(), (int)Math.floor(ptSrc.getX()), (int)Math.floor(ptSrc.getY()), point.x, point.y, mouseEvent.getClickCount(), null));
        }
        else {
            this.processMouseEvent(new GraphicsNodeMouseEvent(this.root, mouseEvent.getID(), mouseEvent.getWhen(), this.getModifiers(mouseEvent), currentLockState, this.getButton(mouseEvent), (float)transform.getX(), (float)transform.getY(), (int)Math.floor(ptSrc.getX()), (int)Math.floor(ptSrc.getY()), point.x, point.y, mouseEvent.getClickCount(), null));
        }
        this.lastHit = nodeHit;
    }
    
    protected void processMouseEvent(final GraphicsNodeMouseEvent graphicsNodeMouseEvent) {
        if (this.glisteners != null) {
            final GraphicsNodeMouseListener[] array = (GraphicsNodeMouseListener[])this.getListeners(GraphicsNodeMouseListener.class);
            switch (graphicsNodeMouseEvent.getID()) {
                case 503: {
                    for (int i = 0; i < array.length; ++i) {
                        array[i].mouseMoved(graphicsNodeMouseEvent);
                    }
                    break;
                }
                case 506: {
                    for (int j = 0; j < array.length; ++j) {
                        array[j].mouseDragged(graphicsNodeMouseEvent);
                    }
                    break;
                }
                case 504: {
                    for (int k = 0; k < array.length; ++k) {
                        array[k].mouseEntered(graphicsNodeMouseEvent);
                    }
                    break;
                }
                case 505: {
                    for (int l = 0; l < array.length; ++l) {
                        array[l].mouseExited(graphicsNodeMouseEvent);
                    }
                    break;
                }
                case 500: {
                    for (int n = 0; n < array.length; ++n) {
                        array[n].mouseClicked(graphicsNodeMouseEvent);
                    }
                    break;
                }
                case 501: {
                    for (int n2 = 0; n2 < array.length; ++n2) {
                        array[n2].mousePressed(graphicsNodeMouseEvent);
                    }
                    break;
                }
                case 502: {
                    for (int n3 = 0; n3 < array.length; ++n3) {
                        array[n3].mouseReleased(graphicsNodeMouseEvent);
                    }
                    break;
                }
                default: {
                    throw new IllegalArgumentException("Unknown Mouse Event type: " + graphicsNodeMouseEvent.getID());
                }
            }
        }
    }
    
    public void processKeyEvent(final GraphicsNodeKeyEvent graphicsNodeKeyEvent) {
        if (this.glisteners != null) {
            final GraphicsNodeKeyListener[] array = (GraphicsNodeKeyListener[])this.getListeners(GraphicsNodeKeyListener.class);
            switch (graphicsNodeKeyEvent.getID()) {
                case 401: {
                    for (int i = 0; i < array.length; ++i) {
                        array[i].keyPressed(graphicsNodeKeyEvent);
                    }
                    break;
                }
                case 402: {
                    for (int j = 0; j < array.length; ++j) {
                        array[j].keyReleased(graphicsNodeKeyEvent);
                    }
                    break;
                }
                case 400: {
                    for (int k = 0; k < array.length; ++k) {
                        array[k].keyTyped(graphicsNodeKeyEvent);
                    }
                    break;
                }
                default: {
                    throw new IllegalArgumentException("Unknown Key Event type: " + graphicsNodeKeyEvent.getID());
                }
            }
        }
        graphicsNodeKeyEvent.consume();
    }
    
    private void incrementKeyTarget() {
        throw new UnsupportedOperationException("Increment not implemented.");
    }
    
    private void decrementKeyTarget() {
        throw new UnsupportedOperationException("Decrement not implemented.");
    }
    
    public void setNodeIncrementEvent(final InputEvent inputEvent) {
        this.nodeIncrementEventID = inputEvent.getID();
        if (inputEvent instanceof KeyEvent) {
            this.nodeIncrementEventCode = ((KeyEvent)inputEvent).getKeyCode();
        }
        this.nodeIncrementEventModifiers = inputEvent.getModifiers();
    }
    
    public void setNodeDecrementEvent(final InputEvent inputEvent) {
        this.nodeDecrementEventID = inputEvent.getID();
        if (inputEvent instanceof KeyEvent) {
            this.nodeDecrementEventCode = ((KeyEvent)inputEvent).getKeyCode();
        }
        this.nodeDecrementEventModifiers = inputEvent.getModifiers();
    }
    
    protected boolean isNodeIncrementEvent(final InputEvent inputEvent) {
        return inputEvent.getID() == this.nodeIncrementEventID && (!(inputEvent instanceof KeyEvent) || ((KeyEvent)inputEvent).getKeyCode() == this.nodeIncrementEventCode) && (inputEvent.getModifiers() & this.nodeIncrementEventModifiers) != 0x0;
    }
    
    protected boolean isNodeDecrementEvent(final InputEvent inputEvent) {
        return inputEvent.getID() == this.nodeDecrementEventID && (!(inputEvent instanceof KeyEvent) || ((KeyEvent)inputEvent).getKeyCode() == this.nodeDecrementEventCode) && (inputEvent.getModifiers() & this.nodeDecrementEventModifiers) != 0x0;
    }
}
