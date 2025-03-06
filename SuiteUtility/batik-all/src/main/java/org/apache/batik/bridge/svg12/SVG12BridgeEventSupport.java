// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.bridge.svg12;

import org.apache.batik.dom.events.DOMMouseEvent;
import java.awt.Point;
import org.w3c.dom.Node;
import java.awt.geom.Point2D;
import org.apache.batik.gvt.event.GraphicsNodeMouseEvent;
import org.apache.batik.dom.svg12.SVGOMWheelEvent;
import org.apache.batik.gvt.event.GraphicsNodeMouseWheelEvent;
import org.apache.batik.dom.events.DOMTextEvent;
import org.apache.batik.bridge.FocusManager;
import org.w3c.dom.events.Event;
import org.w3c.dom.views.AbstractView;
import org.apache.batik.dom.util.DOMUtilities;
import org.w3c.dom.events.DocumentEvent;
import org.apache.batik.dom.events.DOMKeyboardEvent;
import org.w3c.dom.Element;
import org.apache.batik.gvt.event.GraphicsNodeKeyEvent;
import org.apache.batik.gvt.event.EventDispatcher;
import org.apache.batik.bridge.UserAgent;
import org.w3c.dom.events.EventTarget;
import org.w3c.dom.events.EventListener;
import org.apache.batik.dom.events.NodeEventTarget;
import org.apache.batik.gvt.event.GraphicsNodeKeyListener;
import org.apache.batik.gvt.event.GraphicsNodeMouseWheelListener;
import org.apache.batik.gvt.event.GraphicsNodeMouseListener;
import org.w3c.dom.Document;
import org.apache.batik.bridge.BridgeContext;
import org.apache.batik.bridge.BridgeEventSupport;

public abstract class SVG12BridgeEventSupport extends BridgeEventSupport
{
    protected SVG12BridgeEventSupport() {
    }
    
    public static void addGVTListener(final BridgeContext bridgeContext, final Document document) {
        final UserAgent userAgent = bridgeContext.getUserAgent();
        if (userAgent != null) {
            final EventDispatcher eventDispatcher = userAgent.getEventDispatcher();
            if (eventDispatcher != null) {
                final Listener listener = new Listener(bridgeContext, userAgent);
                eventDispatcher.addGraphicsNodeMouseListener(listener);
                eventDispatcher.addGraphicsNodeMouseWheelListener(listener);
                eventDispatcher.addGraphicsNodeKeyListener(listener);
                final GVTUnloadListener gvtUnloadListener = new GVTUnloadListener(eventDispatcher, listener);
                final NodeEventTarget nodeEventTarget = (NodeEventTarget)document;
                nodeEventTarget.addEventListenerNS("http://www.w3.org/2001/xml-events", "SVGUnload", gvtUnloadListener, false, null);
                BridgeEventSupport.storeEventListenerNS(bridgeContext, nodeEventTarget, "http://www.w3.org/2001/xml-events", "SVGUnload", gvtUnloadListener, false);
            }
        }
    }
    
    protected static class Listener extends BridgeEventSupport.Listener implements GraphicsNodeMouseWheelListener
    {
        protected SVG12BridgeContext ctx12;
        protected static String[][] IDENTIFIER_KEY_CODES;
        
        public Listener(final BridgeContext bridgeContext, final UserAgent userAgent) {
            super(bridgeContext, userAgent);
            this.ctx12 = (SVG12BridgeContext)bridgeContext;
        }
        
        public void keyPressed(final GraphicsNodeKeyEvent graphicsNodeKeyEvent) {
            if (!this.isDown) {
                this.isDown = true;
                this.dispatchKeyboardEvent("keydown", graphicsNodeKeyEvent);
            }
            if (graphicsNodeKeyEvent.getKeyChar() == '\uffff') {
                this.dispatchTextEvent(graphicsNodeKeyEvent);
            }
        }
        
        public void keyReleased(final GraphicsNodeKeyEvent graphicsNodeKeyEvent) {
            this.dispatchKeyboardEvent("keyup", graphicsNodeKeyEvent);
            this.isDown = false;
        }
        
        public void keyTyped(final GraphicsNodeKeyEvent graphicsNodeKeyEvent) {
            this.dispatchTextEvent(graphicsNodeKeyEvent);
        }
        
        protected void dispatchKeyboardEvent(final String s, final GraphicsNodeKeyEvent graphicsNodeKeyEvent) {
            final FocusManager focusManager = this.context.getFocusManager();
            if (focusManager == null) {
                return;
            }
            Element documentElement = (Element)focusManager.getCurrentEventTarget();
            if (documentElement == null) {
                documentElement = this.context.getDocument().getDocumentElement();
            }
            final DOMKeyboardEvent domKeyboardEvent = (DOMKeyboardEvent)((DocumentEvent)documentElement.getOwnerDocument()).createEvent("KeyboardEvent");
            domKeyboardEvent.initKeyboardEventNS("http://www.w3.org/2001/xml-events", s, true, true, null, this.mapKeyCodeToIdentifier(graphicsNodeKeyEvent.getKeyCode()), this.mapKeyLocation(graphicsNodeKeyEvent.getKeyLocation()), DOMUtilities.getModifiersList(graphicsNodeKeyEvent.getLockState(), graphicsNodeKeyEvent.getModifiers()));
            try {
                ((EventTarget)documentElement).dispatchEvent(domKeyboardEvent);
            }
            catch (RuntimeException ex) {
                this.ua.displayError(ex);
            }
        }
        
        protected void dispatchTextEvent(final GraphicsNodeKeyEvent graphicsNodeKeyEvent) {
            final FocusManager focusManager = this.context.getFocusManager();
            if (focusManager == null) {
                return;
            }
            Element documentElement = (Element)focusManager.getCurrentEventTarget();
            if (documentElement == null) {
                documentElement = this.context.getDocument().getDocumentElement();
            }
            final DOMTextEvent domTextEvent = (DOMTextEvent)((DocumentEvent)documentElement.getOwnerDocument()).createEvent("TextEvent");
            domTextEvent.initTextEventNS("http://www.w3.org/2001/xml-events", "textInput", true, true, null, String.valueOf(graphicsNodeKeyEvent.getKeyChar()));
            try {
                ((EventTarget)documentElement).dispatchEvent(domTextEvent);
            }
            catch (RuntimeException ex) {
                this.ua.displayError(ex);
            }
        }
        
        protected int mapKeyLocation(final int n) {
            return n - 1;
        }
        
        protected static void putIdentifierKeyCode(final String s, final int n) {
            if (Listener.IDENTIFIER_KEY_CODES[n / 256] == null) {
                Listener.IDENTIFIER_KEY_CODES[n / 256] = new String[256];
            }
            Listener.IDENTIFIER_KEY_CODES[n / 256][n % 256] = s;
        }
        
        protected String mapKeyCodeToIdentifier(final int n) {
            final String[] array = Listener.IDENTIFIER_KEY_CODES[n / 256];
            if (array == null) {
                return "Unidentified";
            }
            return array[n % 256];
        }
        
        public void mouseWheelMoved(final GraphicsNodeMouseWheelEvent graphicsNodeMouseWheelEvent) {
            final Document document = this.context.getPrimaryBridgeContext().getDocument();
            final Element documentElement = document.getDocumentElement();
            final SVGOMWheelEvent svgomWheelEvent = (SVGOMWheelEvent)((DocumentEvent)document).createEvent("WheelEvent");
            svgomWheelEvent.initWheelEventNS("http://www.w3.org/2001/xml-events", "wheel", true, true, null, graphicsNodeMouseWheelEvent.getWheelDelta());
            try {
                ((EventTarget)documentElement).dispatchEvent(svgomWheelEvent);
            }
            catch (RuntimeException ex) {
                this.ua.displayError(ex);
            }
        }
        
        public void mouseEntered(final GraphicsNodeMouseEvent graphicsNodeMouseEvent) {
            final Point clientPoint = graphicsNodeMouseEvent.getClientPoint();
            final Element eventTarget = this.getEventTarget(graphicsNodeMouseEvent.getGraphicsNode(), new Point2D.Float(graphicsNodeMouseEvent.getX(), graphicsNodeMouseEvent.getY()));
            final Element relatedElement = this.getRelatedElement(graphicsNodeMouseEvent);
            int computeBubbleLimit = 0;
            if (relatedElement != null && eventTarget != null) {
                computeBubbleLimit = DefaultXBLManager.computeBubbleLimit(eventTarget, relatedElement);
            }
            this.dispatchMouseEvent("mouseover", eventTarget, relatedElement, clientPoint, graphicsNodeMouseEvent, true, computeBubbleLimit);
        }
        
        public void mouseExited(final GraphicsNodeMouseEvent graphicsNodeMouseEvent) {
            final Point clientPoint = graphicsNodeMouseEvent.getClientPoint();
            final Element eventTarget = this.getEventTarget(graphicsNodeMouseEvent.getRelatedNode(), clientPoint);
            if (this.lastTargetElement != null) {
                int computeBubbleLimit = 0;
                if (eventTarget != null) {
                    computeBubbleLimit = DefaultXBLManager.computeBubbleLimit(this.lastTargetElement, eventTarget);
                }
                this.dispatchMouseEvent("mouseout", this.lastTargetElement, eventTarget, clientPoint, graphicsNodeMouseEvent, true, computeBubbleLimit);
                this.lastTargetElement = null;
            }
        }
        
        public void mouseMoved(final GraphicsNodeMouseEvent graphicsNodeMouseEvent) {
            final Point clientPoint = graphicsNodeMouseEvent.getClientPoint();
            final Element eventTarget = this.getEventTarget(graphicsNodeMouseEvent.getGraphicsNode(), clientPoint);
            final Element lastTargetElement = this.lastTargetElement;
            if (lastTargetElement != eventTarget) {
                if (lastTargetElement != null) {
                    int computeBubbleLimit = 0;
                    if (eventTarget != null) {
                        computeBubbleLimit = DefaultXBLManager.computeBubbleLimit(lastTargetElement, eventTarget);
                    }
                    this.dispatchMouseEvent("mouseout", lastTargetElement, eventTarget, clientPoint, graphicsNodeMouseEvent, true, computeBubbleLimit);
                }
                if (eventTarget != null) {
                    int computeBubbleLimit2 = 0;
                    if (lastTargetElement != null) {
                        computeBubbleLimit2 = DefaultXBLManager.computeBubbleLimit(eventTarget, lastTargetElement);
                    }
                    this.dispatchMouseEvent("mouseover", eventTarget, lastTargetElement, clientPoint, graphicsNodeMouseEvent, true, computeBubbleLimit2);
                }
            }
            this.dispatchMouseEvent("mousemove", eventTarget, null, clientPoint, graphicsNodeMouseEvent, false, 0);
        }
        
        protected void dispatchMouseEvent(final String s, final Element element, final Element element2, final Point point, final GraphicsNodeMouseEvent graphicsNodeMouseEvent, final boolean b) {
            this.dispatchMouseEvent(s, element, element2, point, graphicsNodeMouseEvent, b, 0);
        }
        
        protected void dispatchMouseEvent(final String anObject, Element lastTargetElement, final Element element, final Point point, final GraphicsNodeMouseEvent graphicsNodeMouseEvent, final boolean b, final int bubbleLimit) {
            if (this.ctx12.mouseCaptureTarget != null) {
                NodeEventTarget parentNodeEventTarget = null;
                if (lastTargetElement != null) {
                    for (parentNodeEventTarget = (NodeEventTarget)lastTargetElement; parentNodeEventTarget != null && parentNodeEventTarget != this.ctx12.mouseCaptureTarget; parentNodeEventTarget = parentNodeEventTarget.getParentNodeEventTarget()) {}
                }
                if (parentNodeEventTarget == null) {
                    if (this.ctx12.mouseCaptureSendAll) {
                        lastTargetElement = (Element)this.ctx12.mouseCaptureTarget;
                    }
                    else {
                        lastTargetElement = null;
                    }
                }
            }
            if (lastTargetElement != null) {
                final Point screenPoint = graphicsNodeMouseEvent.getScreenPoint();
                final DOMMouseEvent domMouseEvent = (DOMMouseEvent)((DocumentEvent)lastTargetElement.getOwnerDocument()).createEvent("MouseEvents");
                domMouseEvent.initMouseEventNS("http://www.w3.org/2001/xml-events", anObject, true, b, null, graphicsNodeMouseEvent.getClickCount(), screenPoint.x, screenPoint.y, point.x, point.y, (short)(graphicsNodeMouseEvent.getButton() - 1), (EventTarget)element, DOMUtilities.getModifiersList(graphicsNodeMouseEvent.getLockState(), graphicsNodeMouseEvent.getModifiers()));
                domMouseEvent.setBubbleLimit(bubbleLimit);
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
            if (this.ctx12.mouseCaptureTarget != null && this.ctx12.mouseCaptureAutoRelease && "mouseup".equals(anObject)) {
                this.ctx12.stopMouseCapture();
            }
        }
        
        static {
            Listener.IDENTIFIER_KEY_CODES = new String[256][];
            putIdentifierKeyCode("U+0030", 48);
            putIdentifierKeyCode("U+0031", 49);
            putIdentifierKeyCode("U+0032", 50);
            putIdentifierKeyCode("U+0033", 51);
            putIdentifierKeyCode("U+0034", 52);
            putIdentifierKeyCode("U+0035", 53);
            putIdentifierKeyCode("U+0036", 54);
            putIdentifierKeyCode("U+0037", 55);
            putIdentifierKeyCode("U+0038", 56);
            putIdentifierKeyCode("U+0039", 57);
            putIdentifierKeyCode("Accept", 30);
            putIdentifierKeyCode("Again", 65481);
            putIdentifierKeyCode("U+0041", 65);
            putIdentifierKeyCode("AllCandidates", 256);
            putIdentifierKeyCode("Alphanumeric", 240);
            putIdentifierKeyCode("AltGraph", 65406);
            putIdentifierKeyCode("Alt", 18);
            putIdentifierKeyCode("U+0026", 150);
            putIdentifierKeyCode("U+0027", 222);
            putIdentifierKeyCode("U+002A", 151);
            putIdentifierKeyCode("U+0040", 512);
            putIdentifierKeyCode("U+005C", 92);
            putIdentifierKeyCode("U+0008", 8);
            putIdentifierKeyCode("U+0042", 66);
            putIdentifierKeyCode("U+0018", 3);
            putIdentifierKeyCode("CapsLock", 20);
            putIdentifierKeyCode("U+005E", 514);
            putIdentifierKeyCode("U+0043", 67);
            putIdentifierKeyCode("Clear", 12);
            putIdentifierKeyCode("CodeInput", 258);
            putIdentifierKeyCode("U+003A", 513);
            putIdentifierKeyCode("U+0301", 129);
            putIdentifierKeyCode("U+0306", 133);
            putIdentifierKeyCode("U+030C", 138);
            putIdentifierKeyCode("U+0327", 139);
            putIdentifierKeyCode("U+0302", 130);
            putIdentifierKeyCode("U+0308", 135);
            putIdentifierKeyCode("U+0307", 134);
            putIdentifierKeyCode("U+030B", 137);
            putIdentifierKeyCode("U+0300", 128);
            putIdentifierKeyCode("U+0345", 141);
            putIdentifierKeyCode("U+0304", 132);
            putIdentifierKeyCode("U+0328", 140);
            putIdentifierKeyCode("U+030A", 136);
            putIdentifierKeyCode("U+0303", 131);
            putIdentifierKeyCode("U+002C", 44);
            putIdentifierKeyCode("Compose", 65312);
            putIdentifierKeyCode("Control", 17);
            putIdentifierKeyCode("Convert", 28);
            putIdentifierKeyCode("Copy", 65485);
            putIdentifierKeyCode("Cut", 65489);
            putIdentifierKeyCode("U+007F", 127);
            putIdentifierKeyCode("U+0044", 68);
            putIdentifierKeyCode("U+0024", 515);
            putIdentifierKeyCode("Down", 40);
            putIdentifierKeyCode("U+0045", 69);
            putIdentifierKeyCode("End", 35);
            putIdentifierKeyCode("Enter", 10);
            putIdentifierKeyCode("U+003D", 61);
            putIdentifierKeyCode("U+001B", 27);
            putIdentifierKeyCode("U+20AC", 516);
            putIdentifierKeyCode("U+0021", 517);
            putIdentifierKeyCode("F10", 121);
            putIdentifierKeyCode("F11", 122);
            putIdentifierKeyCode("F12", 123);
            putIdentifierKeyCode("F13", 61440);
            putIdentifierKeyCode("F14", 61441);
            putIdentifierKeyCode("F15", 61442);
            putIdentifierKeyCode("F16", 61443);
            putIdentifierKeyCode("F17", 61444);
            putIdentifierKeyCode("F18", 61445);
            putIdentifierKeyCode("F19", 61446);
            putIdentifierKeyCode("F1", 112);
            putIdentifierKeyCode("F20", 61447);
            putIdentifierKeyCode("F21", 61448);
            putIdentifierKeyCode("F22", 61449);
            putIdentifierKeyCode("F23", 61450);
            putIdentifierKeyCode("F24", 61451);
            putIdentifierKeyCode("F2", 113);
            putIdentifierKeyCode("F3", 114);
            putIdentifierKeyCode("F4", 115);
            putIdentifierKeyCode("F5", 116);
            putIdentifierKeyCode("F6", 117);
            putIdentifierKeyCode("F7", 118);
            putIdentifierKeyCode("F8", 119);
            putIdentifierKeyCode("F9", 120);
            putIdentifierKeyCode("FinalMode", 24);
            putIdentifierKeyCode("Find", 65488);
            putIdentifierKeyCode("U+0046", 70);
            putIdentifierKeyCode("U+002E", 46);
            putIdentifierKeyCode("FullWidth", 243);
            putIdentifierKeyCode("U+0047", 71);
            putIdentifierKeyCode("U+0060", 192);
            putIdentifierKeyCode("U+003E", 160);
            putIdentifierKeyCode("HalfWidth", 244);
            putIdentifierKeyCode("U+0023", 520);
            putIdentifierKeyCode("Help", 156);
            putIdentifierKeyCode("Hiragana", 242);
            putIdentifierKeyCode("U+0048", 72);
            putIdentifierKeyCode("Home", 36);
            putIdentifierKeyCode("U+0049", 73);
            putIdentifierKeyCode("Insert", 155);
            putIdentifierKeyCode("U+00A1", 518);
            putIdentifierKeyCode("JapaneseHiragana", 260);
            putIdentifierKeyCode("JapaneseKatakana", 259);
            putIdentifierKeyCode("JapaneseRomaji", 261);
            putIdentifierKeyCode("U+004A", 74);
            putIdentifierKeyCode("KanaMode", 262);
            putIdentifierKeyCode("KanjiMode", 25);
            putIdentifierKeyCode("Katakana", 241);
            putIdentifierKeyCode("U+004B", 75);
            putIdentifierKeyCode("U+007B", 161);
            putIdentifierKeyCode("Left", 37);
            putIdentifierKeyCode("U+0028", 519);
            putIdentifierKeyCode("U+005B", 91);
            putIdentifierKeyCode("U+003C", 153);
            putIdentifierKeyCode("U+004C", 76);
            putIdentifierKeyCode("Meta", 157);
            putIdentifierKeyCode("Meta", 157);
            putIdentifierKeyCode("U+002D", 45);
            putIdentifierKeyCode("U+004D", 77);
            putIdentifierKeyCode("ModeChange", 31);
            putIdentifierKeyCode("U+004E", 78);
            putIdentifierKeyCode("Nonconvert", 29);
            putIdentifierKeyCode("NumLock", 144);
            putIdentifierKeyCode("NumLock", 144);
            putIdentifierKeyCode("U+004F", 79);
            putIdentifierKeyCode("PageDown", 34);
            putIdentifierKeyCode("PageUp", 33);
            putIdentifierKeyCode("Paste", 65487);
            putIdentifierKeyCode("Pause", 19);
            putIdentifierKeyCode("U+0050", 80);
            putIdentifierKeyCode("U+002B", 521);
            putIdentifierKeyCode("PreviousCandidate", 257);
            putIdentifierKeyCode("PrintScreen", 154);
            putIdentifierKeyCode("Props", 65482);
            putIdentifierKeyCode("U+0051", 81);
            putIdentifierKeyCode("U+0022", 152);
            putIdentifierKeyCode("U+007D", 162);
            putIdentifierKeyCode("Right", 39);
            putIdentifierKeyCode("U+0029", 522);
            putIdentifierKeyCode("U+005D", 93);
            putIdentifierKeyCode("U+0052", 82);
            putIdentifierKeyCode("RomanCharacters", 245);
            putIdentifierKeyCode("Scroll", 145);
            putIdentifierKeyCode("Scroll", 145);
            putIdentifierKeyCode("U+003B", 59);
            putIdentifierKeyCode("U+309A", 143);
            putIdentifierKeyCode("Shift", 16);
            putIdentifierKeyCode("Shift", 16);
            putIdentifierKeyCode("U+0053", 83);
            putIdentifierKeyCode("U+002F", 47);
            putIdentifierKeyCode("U+0020", 32);
            putIdentifierKeyCode("Stop", 65480);
            putIdentifierKeyCode("U+0009", 9);
            putIdentifierKeyCode("U+0054", 84);
            putIdentifierKeyCode("U+0055", 85);
            putIdentifierKeyCode("U+005F", 523);
            putIdentifierKeyCode("Undo", 65483);
            putIdentifierKeyCode("Unidentified", 0);
            putIdentifierKeyCode("Up", 38);
            putIdentifierKeyCode("U+0056", 86);
            putIdentifierKeyCode("U+3099", 142);
            putIdentifierKeyCode("U+0057", 87);
            putIdentifierKeyCode("U+0058", 88);
            putIdentifierKeyCode("U+0059", 89);
            putIdentifierKeyCode("U+005A", 90);
            putIdentifierKeyCode("U+0030", 96);
            putIdentifierKeyCode("U+0031", 97);
            putIdentifierKeyCode("U+0032", 98);
            putIdentifierKeyCode("U+0033", 99);
            putIdentifierKeyCode("U+0034", 100);
            putIdentifierKeyCode("U+0035", 101);
            putIdentifierKeyCode("U+0036", 102);
            putIdentifierKeyCode("U+0037", 103);
            putIdentifierKeyCode("U+0038", 104);
            putIdentifierKeyCode("U+0039", 105);
            putIdentifierKeyCode("U+002A", 106);
            putIdentifierKeyCode("Down", 225);
            putIdentifierKeyCode("U+002E", 110);
            putIdentifierKeyCode("Left", 226);
            putIdentifierKeyCode("U+002D", 109);
            putIdentifierKeyCode("U+002B", 107);
            putIdentifierKeyCode("Right", 227);
            putIdentifierKeyCode("U+002F", 111);
            putIdentifierKeyCode("Up", 224);
        }
    }
}
