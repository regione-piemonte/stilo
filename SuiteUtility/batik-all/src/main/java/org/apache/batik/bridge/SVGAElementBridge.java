// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.bridge;

import org.apache.batik.dom.svg.SVGOMAnimationElement;
import org.apache.batik.dom.svg.SVGOMDocument;
import org.apache.batik.util.ParsedURL;
import org.apache.batik.dom.svg.SVGOMAElement;
import java.util.Iterator;
import java.util.List;
import org.apache.batik.dom.events.AbstractEvent;
import org.w3c.dom.events.Event;
import java.awt.Cursor;
import org.w3c.dom.svg.SVGAElement;
import org.w3c.dom.events.EventTarget;
import org.w3c.dom.events.EventListener;
import org.apache.batik.dom.events.NodeEventTarget;
import org.apache.batik.gvt.GraphicsNode;
import org.w3c.dom.Element;

public class SVGAElementBridge extends SVGGElementBridge
{
    protected AnchorListener al;
    protected CursorMouseOverListener bl;
    protected CursorMouseOutListener cl;
    
    public String getLocalName() {
        return "a";
    }
    
    public Bridge getInstance() {
        return new SVGAElementBridge();
    }
    
    public void buildGraphicsNode(final BridgeContext bridgeContext, final Element element, final GraphicsNode graphicsNode) {
        super.buildGraphicsNode(bridgeContext, element, graphicsNode);
        if (bridgeContext.isInteractive()) {
            final NodeEventTarget nodeEventTarget = (NodeEventTarget)element;
            final CursorHolder cursorHolder = new CursorHolder(CursorManager.DEFAULT_CURSOR);
            nodeEventTarget.addEventListenerNS("http://www.w3.org/2001/xml-events", "click", this.al = new AnchorListener(bridgeContext.getUserAgent(), cursorHolder), false, null);
            bridgeContext.storeEventListenerNS(nodeEventTarget, "http://www.w3.org/2001/xml-events", "click", this.al, false);
            nodeEventTarget.addEventListenerNS("http://www.w3.org/2001/xml-events", "mouseover", this.bl = new CursorMouseOverListener(bridgeContext.getUserAgent(), cursorHolder), false, null);
            bridgeContext.storeEventListenerNS(nodeEventTarget, "http://www.w3.org/2001/xml-events", "mouseover", this.bl, false);
            nodeEventTarget.addEventListenerNS("http://www.w3.org/2001/xml-events", "mouseout", this.cl = new CursorMouseOutListener(bridgeContext.getUserAgent(), cursorHolder), false, null);
            bridgeContext.storeEventListenerNS(nodeEventTarget, "http://www.w3.org/2001/xml-events", "mouseout", this.cl, false);
        }
    }
    
    public void dispose() {
        final NodeEventTarget nodeEventTarget = (NodeEventTarget)this.e;
        if (this.al != null) {
            nodeEventTarget.removeEventListenerNS("http://www.w3.org/2001/xml-events", "click", this.al, false);
            this.al = null;
        }
        if (this.bl != null) {
            nodeEventTarget.removeEventListenerNS("http://www.w3.org/2001/xml-events", "mouseover", this.bl, false);
            this.bl = null;
        }
        if (this.cl != null) {
            nodeEventTarget.removeEventListenerNS("http://www.w3.org/2001/xml-events", "mouseout", this.cl, false);
            this.cl = null;
        }
        super.dispose();
    }
    
    public boolean isComposite() {
        return true;
    }
    
    public static class MouseOutDefaultActionable implements Runnable
    {
        protected SVGAElement elt;
        protected UserAgent userAgent;
        protected CursorHolder holder;
        
        public MouseOutDefaultActionable(final SVGAElement elt, final UserAgent userAgent, final CursorHolder holder) {
            this.elt = elt;
            this.userAgent = userAgent;
            this.holder = holder;
        }
        
        public void run() {
            if (this.elt != null) {
                this.userAgent.displayMessage("");
            }
        }
    }
    
    public static class CursorHolder
    {
        Cursor cursor;
        
        public CursorHolder(final Cursor cursor) {
            this.cursor = null;
            this.cursor = cursor;
        }
        
        public void holdCursor(final Cursor cursor) {
            this.cursor = cursor;
        }
        
        public Cursor getCursor() {
            return this.cursor;
        }
    }
    
    public static class CursorMouseOutListener implements EventListener
    {
        protected UserAgent userAgent;
        protected CursorHolder holder;
        
        public CursorMouseOutListener(final UserAgent userAgent, final CursorHolder holder) {
            this.userAgent = userAgent;
            this.holder = holder;
        }
        
        public void handleEvent(final Event event) {
            if (!(event instanceof AbstractEvent)) {
                return;
            }
            final AbstractEvent abstractEvent = (AbstractEvent)event;
            final List defaultActions = abstractEvent.getDefaultActions();
            if (defaultActions != null) {
                final Iterator<Object> iterator = defaultActions.iterator();
                while (iterator.hasNext()) {
                    if (iterator.next() instanceof MouseOutDefaultActionable) {
                        return;
                    }
                }
            }
            abstractEvent.addDefaultAction(new MouseOutDefaultActionable((SVGAElement)event.getCurrentTarget(), this.userAgent, this.holder));
        }
    }
    
    public static class MouseOverDefaultActionable implements Runnable
    {
        protected Element target;
        protected SVGAElement elt;
        protected UserAgent userAgent;
        protected CursorHolder holder;
        
        public MouseOverDefaultActionable(final Element target, final SVGAElement elt, final UserAgent userAgent, final CursorHolder holder) {
            this.target = target;
            this.elt = elt;
            this.userAgent = userAgent;
            this.holder = holder;
        }
        
        public void run() {
            if (CSSUtilities.isAutoCursor(this.target)) {
                this.holder.holdCursor(CursorManager.DEFAULT_CURSOR);
                this.userAgent.setSVGCursor(CursorManager.ANCHOR_CURSOR);
            }
            if (this.elt != null) {
                this.userAgent.displayMessage(this.elt.getHref().getAnimVal());
            }
        }
    }
    
    public static class CursorMouseOverListener implements EventListener
    {
        protected UserAgent userAgent;
        protected CursorHolder holder;
        
        public CursorMouseOverListener(final UserAgent userAgent, final CursorHolder holder) {
            this.userAgent = userAgent;
            this.holder = holder;
        }
        
        public void handleEvent(final Event event) {
            if (!(event instanceof AbstractEvent)) {
                return;
            }
            final AbstractEvent abstractEvent = (AbstractEvent)event;
            final List defaultActions = abstractEvent.getDefaultActions();
            if (defaultActions != null) {
                final Iterator<Object> iterator = defaultActions.iterator();
                while (iterator.hasNext()) {
                    if (iterator.next() instanceof MouseOverDefaultActionable) {
                        return;
                    }
                }
            }
            abstractEvent.addDefaultAction(new MouseOverDefaultActionable((Element)abstractEvent.getTarget(), (SVGAElement)abstractEvent.getCurrentTarget(), this.userAgent, this.holder));
        }
    }
    
    public static class AnchorDefaultActionable implements Runnable
    {
        protected SVGOMAElement elt;
        protected UserAgent userAgent;
        protected CursorHolder holder;
        
        public AnchorDefaultActionable(final SVGAElement svgaElement, final UserAgent userAgent, final CursorHolder holder) {
            this.elt = (SVGOMAElement)svgaElement;
            this.userAgent = userAgent;
            this.holder = holder;
        }
        
        public void run() {
            this.userAgent.setSVGCursor(this.holder.getCursor());
            final ParsedURL parsedURL = new ParsedURL(this.elt.getBaseURI(), this.elt.getHref().getAnimVal());
            final SVGOMDocument svgomDocument = (SVGOMDocument)this.elt.getOwnerDocument();
            if (parsedURL.sameFile(svgomDocument.getParsedURL())) {
                final String ref = parsedURL.getRef();
                if (ref != null && ref.length() != 0) {
                    final Element elementById = svgomDocument.getElementById(ref);
                    if (elementById instanceof SVGOMAnimationElement) {
                        final SVGOMAnimationElement svgomAnimationElement = (SVGOMAnimationElement)elementById;
                        final float hyperlinkBeginTime = svgomAnimationElement.getHyperlinkBeginTime();
                        if (Float.isNaN(hyperlinkBeginTime)) {
                            svgomAnimationElement.beginElement();
                        }
                        else {
                            svgomDocument.getRootElement().setCurrentTime(hyperlinkBeginTime);
                        }
                        return;
                    }
                }
            }
            this.userAgent.openLink((SVGAElement)this.elt);
        }
    }
    
    public static class AnchorListener implements EventListener
    {
        protected UserAgent userAgent;
        protected CursorHolder holder;
        
        public AnchorListener(final UserAgent userAgent, final CursorHolder holder) {
            this.userAgent = userAgent;
            this.holder = holder;
        }
        
        public void handleEvent(final Event event) {
            if (!(event instanceof AbstractEvent)) {
                return;
            }
            final AbstractEvent abstractEvent = (AbstractEvent)event;
            final List defaultActions = abstractEvent.getDefaultActions();
            if (defaultActions != null) {
                final Iterator<Object> iterator = defaultActions.iterator();
                while (iterator.hasNext()) {
                    if (iterator.next() instanceof AnchorDefaultActionable) {
                        return;
                    }
                }
            }
            abstractEvent.addDefaultAction(new AnchorDefaultActionable((SVGAElement)event.getCurrentTarget(), this.userAgent, this.holder));
        }
    }
}
