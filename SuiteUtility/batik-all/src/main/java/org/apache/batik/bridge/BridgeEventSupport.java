// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.bridge;

import java.awt.geom.Rectangle2D;
import org.apache.batik.gvt.text.TextHit;
import org.apache.batik.gvt.text.TextSpanLayout;
import java.util.List;
import java.lang.ref.SoftReference;
import org.apache.batik.gvt.renderer.StrokingTextPainter;
import java.awt.geom.NoninvertibleTransformException;
import org.apache.batik.gvt.TextNode;
import org.apache.batik.gvt.GraphicsNode;
import org.apache.batik.dom.util.DOMUtilities;
import org.apache.batik.dom.events.DOMMouseEvent;
import java.awt.Point;
import java.awt.geom.Point2D;
import org.apache.batik.gvt.event.GraphicsNodeMouseEvent;
import org.w3c.dom.events.Event;
import org.w3c.dom.views.AbstractView;
import org.w3c.dom.events.DocumentEvent;
import org.apache.batik.dom.events.DOMKeyEvent;
import org.apache.batik.gvt.event.GraphicsNodeKeyEvent;
import org.w3c.dom.Element;
import org.apache.batik.gvt.text.GVTAttributedCharacterIterator;
import org.apache.batik.gvt.event.EventDispatcher;
import org.w3c.dom.events.EventTarget;
import org.w3c.dom.events.EventListener;
import org.apache.batik.dom.events.NodeEventTarget;
import org.apache.batik.gvt.event.GraphicsNodeKeyListener;
import org.apache.batik.gvt.event.GraphicsNodeMouseListener;
import org.w3c.dom.Document;
import java.text.AttributedCharacterIterator;
import org.apache.batik.util.SVGConstants;

public abstract class BridgeEventSupport implements SVGConstants
{
    public static final AttributedCharacterIterator.Attribute TEXT_COMPOUND_ID;
    
    protected BridgeEventSupport() {
    }
    
    public static void addGVTListener(final BridgeContext bridgeContext, final Document document) {
        final UserAgent userAgent = bridgeContext.getUserAgent();
        if (userAgent != null) {
            final EventDispatcher eventDispatcher = userAgent.getEventDispatcher();
            if (eventDispatcher != null) {
                final Listener listener = new Listener(bridgeContext, userAgent);
                eventDispatcher.addGraphicsNodeMouseListener(listener);
                eventDispatcher.addGraphicsNodeKeyListener(listener);
                final GVTUnloadListener gvtUnloadListener = new GVTUnloadListener(eventDispatcher, listener);
                final NodeEventTarget nodeEventTarget = (NodeEventTarget)document;
                nodeEventTarget.addEventListenerNS("http://www.w3.org/2001/xml-events", "SVGUnload", gvtUnloadListener, false, null);
                storeEventListenerNS(bridgeContext, nodeEventTarget, "http://www.w3.org/2001/xml-events", "SVGUnload", gvtUnloadListener, false);
            }
        }
    }
    
    protected static void storeEventListener(final BridgeContext bridgeContext, final EventTarget eventTarget, final String s, final EventListener eventListener, final boolean b) {
        bridgeContext.storeEventListener(eventTarget, s, eventListener, b);
    }
    
    protected static void storeEventListenerNS(final BridgeContext bridgeContext, final EventTarget eventTarget, final String s, final String s2, final EventListener eventListener, final boolean b) {
        bridgeContext.storeEventListenerNS(eventTarget, s, s2, eventListener, b);
    }
    
    static {
        TEXT_COMPOUND_ID = GVTAttributedCharacterIterator.TextAttribute.TEXT_COMPOUND_ID;
    }
    
    protected static class Listener implements GraphicsNodeMouseListener, GraphicsNodeKeyListener
    {
        protected BridgeContext context;
        protected UserAgent ua;
        protected Element lastTargetElement;
        protected boolean isDown;
        
        public Listener(final BridgeContext context, final UserAgent ua) {
            this.context = context;
            this.ua = ua;
        }
        
        public void keyPressed(final GraphicsNodeKeyEvent graphicsNodeKeyEvent) {
            if (!this.isDown) {
                this.isDown = true;
                this.dispatchKeyEvent("keydown", graphicsNodeKeyEvent);
            }
            if (graphicsNodeKeyEvent.getKeyChar() == '\uffff') {
                this.dispatchKeyEvent("keypress", graphicsNodeKeyEvent);
            }
        }
        
        public void keyReleased(final GraphicsNodeKeyEvent graphicsNodeKeyEvent) {
            this.dispatchKeyEvent("keyup", graphicsNodeKeyEvent);
            this.isDown = false;
        }
        
        public void keyTyped(final GraphicsNodeKeyEvent graphicsNodeKeyEvent) {
            this.dispatchKeyEvent("keypress", graphicsNodeKeyEvent);
        }
        
        protected void dispatchKeyEvent(final String s, final GraphicsNodeKeyEvent graphicsNodeKeyEvent) {
            final FocusManager focusManager = this.context.getFocusManager();
            if (focusManager == null) {
                return;
            }
            Element documentElement = (Element)focusManager.getCurrentEventTarget();
            if (documentElement == null) {
                documentElement = this.context.getDocument().getDocumentElement();
            }
            final DOMKeyEvent domKeyEvent = (DOMKeyEvent)((DocumentEvent)documentElement.getOwnerDocument()).createEvent("KeyEvents");
            domKeyEvent.initKeyEvent(s, true, true, graphicsNodeKeyEvent.isControlDown(), graphicsNodeKeyEvent.isAltDown(), graphicsNodeKeyEvent.isShiftDown(), graphicsNodeKeyEvent.isMetaDown(), this.mapKeyCode(graphicsNodeKeyEvent.getKeyCode()), graphicsNodeKeyEvent.getKeyChar(), null);
            try {
                ((EventTarget)documentElement).dispatchEvent(domKeyEvent);
            }
            catch (RuntimeException ex) {
                this.ua.displayError(ex);
            }
        }
        
        protected final int mapKeyCode(final int n) {
            switch (n) {
                case 10: {
                    return 13;
                }
                case 262: {
                    return 0;
                }
                case 263: {
                    return 0;
                }
                default: {
                    return n;
                }
            }
        }
        
        public void mouseClicked(final GraphicsNodeMouseEvent graphicsNodeMouseEvent) {
            this.dispatchMouseEvent("click", graphicsNodeMouseEvent, true);
        }
        
        public void mousePressed(final GraphicsNodeMouseEvent graphicsNodeMouseEvent) {
            this.dispatchMouseEvent("mousedown", graphicsNodeMouseEvent, true);
        }
        
        public void mouseReleased(final GraphicsNodeMouseEvent graphicsNodeMouseEvent) {
            this.dispatchMouseEvent("mouseup", graphicsNodeMouseEvent, true);
        }
        
        public void mouseEntered(final GraphicsNodeMouseEvent graphicsNodeMouseEvent) {
            this.dispatchMouseEvent("mouseover", this.getEventTarget(graphicsNodeMouseEvent.getGraphicsNode(), new Point2D.Float(graphicsNodeMouseEvent.getX(), graphicsNodeMouseEvent.getY())), this.getRelatedElement(graphicsNodeMouseEvent), graphicsNodeMouseEvent.getClientPoint(), graphicsNodeMouseEvent, true);
        }
        
        public void mouseExited(final GraphicsNodeMouseEvent graphicsNodeMouseEvent) {
            final Point clientPoint = graphicsNodeMouseEvent.getClientPoint();
            final Element eventTarget = this.getEventTarget(graphicsNodeMouseEvent.getRelatedNode(), clientPoint);
            if (this.lastTargetElement != null) {
                this.dispatchMouseEvent("mouseout", this.lastTargetElement, eventTarget, clientPoint, graphicsNodeMouseEvent, true);
                this.lastTargetElement = null;
            }
        }
        
        public void mouseDragged(final GraphicsNodeMouseEvent graphicsNodeMouseEvent) {
            this.dispatchMouseEvent("mousemove", graphicsNodeMouseEvent, false);
        }
        
        public void mouseMoved(final GraphicsNodeMouseEvent graphicsNodeMouseEvent) {
            final Point clientPoint = graphicsNodeMouseEvent.getClientPoint();
            final Element eventTarget = this.getEventTarget(graphicsNodeMouseEvent.getGraphicsNode(), clientPoint);
            final Element lastTargetElement = this.lastTargetElement;
            if (lastTargetElement != eventTarget) {
                if (lastTargetElement != null) {
                    this.dispatchMouseEvent("mouseout", lastTargetElement, eventTarget, clientPoint, graphicsNodeMouseEvent, true);
                }
                if (eventTarget != null) {
                    this.dispatchMouseEvent("mouseover", eventTarget, lastTargetElement, clientPoint, graphicsNodeMouseEvent, true);
                }
            }
            this.dispatchMouseEvent("mousemove", eventTarget, null, clientPoint, graphicsNodeMouseEvent, false);
        }
        
        protected void dispatchMouseEvent(final String s, final GraphicsNodeMouseEvent graphicsNodeMouseEvent, final boolean b) {
            this.dispatchMouseEvent(s, this.getEventTarget(graphicsNodeMouseEvent.getGraphicsNode(), new Point2D.Float(graphicsNodeMouseEvent.getX(), graphicsNodeMouseEvent.getY())), this.getRelatedElement(graphicsNodeMouseEvent), graphicsNodeMouseEvent.getClientPoint(), graphicsNodeMouseEvent, b);
        }
        
        protected void dispatchMouseEvent(final String s, final Element lastTargetElement, final Element element, final Point point, final GraphicsNodeMouseEvent graphicsNodeMouseEvent, final boolean b) {
            if (lastTargetElement == null) {
                return;
            }
            final Point screenPoint = graphicsNodeMouseEvent.getScreenPoint();
            final DOMMouseEvent domMouseEvent = (DOMMouseEvent)((DocumentEvent)lastTargetElement.getOwnerDocument()).createEvent("MouseEvents");
            domMouseEvent.initMouseEventNS("http://www.w3.org/2001/xml-events", s, true, b, null, graphicsNodeMouseEvent.getClickCount(), screenPoint.x, screenPoint.y, point.x, point.y, (short)(graphicsNodeMouseEvent.getButton() - 1), (EventTarget)element, DOMUtilities.getModifiersList(graphicsNodeMouseEvent.getLockState(), graphicsNodeMouseEvent.getModifiers()));
            try {
                ((EventTarget)lastTargetElement).dispatchEvent(domMouseEvent);
            }
            catch (RuntimeException ex) {
                this.ua.displayError(ex);
            }
            finally {
                this.lastTargetElement = lastTargetElement;
            }
        }
        
        protected Element getRelatedElement(final GraphicsNodeMouseEvent graphicsNodeMouseEvent) {
            final GraphicsNode relatedNode = graphicsNodeMouseEvent.getRelatedNode();
            Element element = null;
            if (relatedNode != null) {
                element = this.context.getElement(relatedNode);
            }
            return element;
        }
        
        protected Element getEventTarget(final GraphicsNode graphicsNode, final Point2D point2D) {
            final Element element = this.context.getElement(graphicsNode);
            if (element != null && graphicsNode instanceof TextNode) {
                final List textRuns = ((TextNode)graphicsNode).getTextRuns();
                final Point2D point2D2 = (Point2D)point2D.clone();
                try {
                    graphicsNode.getGlobalTransform().createInverse().transform(point2D2, point2D2);
                }
                catch (NoninvertibleTransformException ex) {}
                if (textRuns != null) {
                    for (int i = 0; i < textRuns.size(); ++i) {
                        final StrokingTextPainter.TextRun textRun = textRuns.get(i);
                        final AttributedCharacterIterator aci = textRun.getACI();
                        final TextSpanLayout layout = textRun.getLayout();
                        final float n = (float)point2D2.getX();
                        final float n2 = (float)point2D2.getY();
                        final TextHit hitTestChar = layout.hitTestChar(n, n2);
                        final Rectangle2D bounds2D = layout.getBounds2D();
                        if (hitTestChar != null && bounds2D != null && bounds2D.contains(n, n2)) {
                            final Object value = ((SoftReference)aci.getAttribute(BridgeEventSupport.TEXT_COMPOUND_ID)).get();
                            if (value instanceof Element) {
                                return (Element)value;
                            }
                        }
                    }
                }
            }
            return element;
        }
    }
    
    protected static class GVTUnloadListener implements EventListener
    {
        protected EventDispatcher dispatcher;
        protected Listener listener;
        
        public GVTUnloadListener(final EventDispatcher dispatcher, final Listener listener) {
            this.dispatcher = dispatcher;
            this.listener = listener;
        }
        
        public void handleEvent(final Event event) {
            this.dispatcher.removeGraphicsNodeMouseListener(this.listener);
            this.dispatcher.removeGraphicsNodeKeyListener(this.listener);
            ((NodeEventTarget)event.getTarget()).removeEventListenerNS("http://www.w3.org/2001/xml-events", "SVGUnload", this, false);
        }
    }
}
