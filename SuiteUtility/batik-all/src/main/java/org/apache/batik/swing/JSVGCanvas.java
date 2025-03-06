// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.swing;

import org.apache.batik.swing.svg.SVGDocumentLoaderEvent;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import java.awt.geom.AffineTransform;
import org.apache.batik.util.gui.JErrorPane;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import java.util.WeakHashMap;
import org.w3c.dom.Node;
import org.apache.batik.util.XMLConstants;
import org.apache.batik.swing.svg.AbstractJSVGComponent;
import java.awt.EventQueue;
import org.apache.batik.css.engine.CSSEngine;
import org.w3c.dom.Element;
import org.w3c.dom.events.Event;
import java.awt.event.MouseMotionAdapter;
import java.awt.Component;
import javax.swing.ToolTipManager;
import org.apache.batik.gvt.GraphicsNode;
import java.util.Iterator;
import org.apache.batik.dom.events.NodeEventTarget;
import org.apache.batik.swing.gvt.AbstractJGVTComponent;
import org.apache.batik.bridge.UserAgent;
import org.w3c.dom.svg.SVGDocument;
import java.beans.PropertyChangeListener;
import javax.swing.InputMap;
import javax.swing.KeyStroke;
import javax.swing.ActionMap;
import javax.swing.Action;
import java.util.List;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;
import java.awt.Dimension;
import org.apache.batik.swing.gvt.AbstractResetTransformInteractor;
import org.apache.batik.swing.gvt.AbstractRotateInteractor;
import org.apache.batik.swing.gvt.AbstractPanInteractor;
import org.apache.batik.swing.gvt.AbstractImageZoomInteractor;
import java.awt.event.InputEvent;
import org.apache.batik.swing.gvt.AbstractZoomInteractor;
import java.awt.event.MouseMotionListener;
import org.apache.batik.swing.svg.SVGUserAgent;
import org.apache.batik.swing.gvt.Interactor;
import org.w3c.dom.events.EventTarget;
import org.w3c.dom.events.EventListener;
import java.util.Map;
import java.beans.PropertyChangeSupport;
import org.apache.batik.swing.svg.JSVGComponent;

public class JSVGCanvas extends JSVGComponent
{
    public static final String SCROLL_RIGHT_ACTION = "ScrollRight";
    public static final String SCROLL_LEFT_ACTION = "ScrollLeft";
    public static final String SCROLL_UP_ACTION = "ScrollUp";
    public static final String SCROLL_DOWN_ACTION = "ScrollDown";
    public static final String FAST_SCROLL_RIGHT_ACTION = "FastScrollRight";
    public static final String FAST_SCROLL_LEFT_ACTION = "FastScrollLeft";
    public static final String FAST_SCROLL_UP_ACTION = "FastScrollUp";
    public static final String FAST_SCROLL_DOWN_ACTION = "FastScrollDown";
    public static final String ZOOM_IN_ACTION = "ZoomIn";
    public static final String ZOOM_OUT_ACTION = "ZoomOut";
    public static final String RESET_TRANSFORM_ACTION = "ResetTransform";
    private boolean isZoomInteractorEnabled;
    private boolean isImageZoomInteractorEnabled;
    private boolean isPanInteractorEnabled;
    private boolean isRotateInteractorEnabled;
    private boolean isResetTransformInteractorEnabled;
    protected PropertyChangeSupport pcs;
    protected String uri;
    protected LocationListener locationListener;
    protected Map toolTipMap;
    protected EventListener toolTipListener;
    protected EventTarget lastTarget;
    protected Map toolTipDocs;
    protected static final Object MAP_TOKEN;
    protected long lastToolTipEventTimeStamp;
    protected EventTarget lastToolTipEventTarget;
    protected Interactor zoomInteractor;
    protected Interactor imageZoomInteractor;
    protected Interactor panInteractor;
    protected Interactor rotateInteractor;
    protected Interactor resetTransformInteractor;
    
    public JSVGCanvas() {
        this(null, true, true);
        this.addMouseMotionListener(this.locationListener);
    }
    
    public JSVGCanvas(final SVGUserAgent svgUserAgent, final boolean b, final boolean b2) {
        super(svgUserAgent, b, b2);
        this.isZoomInteractorEnabled = true;
        this.isImageZoomInteractorEnabled = true;
        this.isPanInteractorEnabled = true;
        this.isRotateInteractorEnabled = true;
        this.isResetTransformInteractorEnabled = true;
        this.pcs = new PropertyChangeSupport(this);
        this.locationListener = new LocationListener();
        this.toolTipMap = null;
        this.toolTipListener = new ToolTipModifier();
        this.lastTarget = null;
        this.toolTipDocs = null;
        this.zoomInteractor = new AbstractZoomInteractor() {
            public boolean startInteraction(final InputEvent inputEvent) {
                final int modifiers = inputEvent.getModifiers();
                return inputEvent.getID() == 501 && (modifiers & 0x10) != 0x0 && (modifiers & 0x2) != 0x0;
            }
        };
        this.imageZoomInteractor = new AbstractImageZoomInteractor() {
            public boolean startInteraction(final InputEvent inputEvent) {
                final int modifiers = inputEvent.getModifiers();
                return inputEvent.getID() == 501 && (modifiers & 0x4) != 0x0 && (modifiers & 0x1) != 0x0;
            }
        };
        this.panInteractor = new AbstractPanInteractor() {
            public boolean startInteraction(final InputEvent inputEvent) {
                final int modifiers = inputEvent.getModifiers();
                return inputEvent.getID() == 501 && (modifiers & 0x10) != 0x0 && (modifiers & 0x1) != 0x0;
            }
        };
        this.rotateInteractor = new AbstractRotateInteractor() {
            public boolean startInteraction(final InputEvent inputEvent) {
                final int modifiers = inputEvent.getModifiers();
                return inputEvent.getID() == 501 && (modifiers & 0x4) != 0x0 && (modifiers & 0x2) != 0x0;
            }
        };
        this.resetTransformInteractor = new AbstractResetTransformInteractor() {
            public boolean startInteraction(final InputEvent inputEvent) {
                final int modifiers = inputEvent.getModifiers();
                return inputEvent.getID() == 500 && (modifiers & 0x4) != 0x0 && (modifiers & 0x1) != 0x0 && (modifiers & 0x2) != 0x0;
            }
        };
        this.setPreferredSize(new Dimension(200, 200));
        this.setMinimumSize(new Dimension(100, 100));
        final List interactors = this.getInteractors();
        interactors.add(this.zoomInteractor);
        interactors.add(this.imageZoomInteractor);
        interactors.add(this.panInteractor);
        interactors.add(this.rotateInteractor);
        interactors.add(this.resetTransformInteractor);
        this.installActions();
        if (b) {
            this.addMouseListener(new MouseAdapter() {
                public void mousePressed(final MouseEvent mouseEvent) {
                    JSVGCanvas.this.requestFocus();
                }
            });
            this.installKeyboardActions();
        }
        this.addMouseMotionListener(this.locationListener);
    }
    
    protected void installActions() {
        final ActionMap actionMap = this.getActionMap();
        actionMap.put("ScrollRight", new ScrollRightAction(10));
        actionMap.put("ScrollLeft", new ScrollLeftAction(10));
        actionMap.put("ScrollUp", new ScrollUpAction(10));
        actionMap.put("ScrollDown", new ScrollDownAction(10));
        actionMap.put("FastScrollRight", new ScrollRightAction(30));
        actionMap.put("FastScrollLeft", new ScrollLeftAction(30));
        actionMap.put("FastScrollUp", new ScrollUpAction(30));
        actionMap.put("FastScrollDown", new ScrollDownAction(30));
        actionMap.put("ZoomIn", new ZoomInAction());
        actionMap.put("ZoomOut", new ZoomOutAction());
        actionMap.put("ResetTransform", new ResetTransformAction());
    }
    
    public void setDisableInteractions(final boolean disableInteractions) {
        super.setDisableInteractions(disableInteractions);
        final ActionMap actionMap = this.getActionMap();
        actionMap.get("ScrollRight").setEnabled(!disableInteractions);
        actionMap.get("ScrollLeft").setEnabled(!disableInteractions);
        actionMap.get("ScrollUp").setEnabled(!disableInteractions);
        actionMap.get("ScrollDown").setEnabled(!disableInteractions);
        actionMap.get("FastScrollRight").setEnabled(!disableInteractions);
        actionMap.get("FastScrollLeft").setEnabled(!disableInteractions);
        actionMap.get("FastScrollUp").setEnabled(!disableInteractions);
        actionMap.get("FastScrollDown").setEnabled(!disableInteractions);
        actionMap.get("ZoomIn").setEnabled(!disableInteractions);
        actionMap.get("ZoomOut").setEnabled(!disableInteractions);
        actionMap.get("ResetTransform").setEnabled(!disableInteractions);
    }
    
    protected void installKeyboardActions() {
        final InputMap inputMap = this.getInputMap(0);
        inputMap.put(KeyStroke.getKeyStroke(39, 0), "ScrollRight");
        inputMap.put(KeyStroke.getKeyStroke(37, 0), "ScrollLeft");
        inputMap.put(KeyStroke.getKeyStroke(38, 0), "ScrollUp");
        inputMap.put(KeyStroke.getKeyStroke(40, 0), "ScrollDown");
        inputMap.put(KeyStroke.getKeyStroke(39, 1), "FastScrollRight");
        inputMap.put(KeyStroke.getKeyStroke(37, 1), "FastScrollLeft");
        inputMap.put(KeyStroke.getKeyStroke(38, 1), "FastScrollUp");
        inputMap.put(KeyStroke.getKeyStroke(40, 1), "FastScrollDown");
        inputMap.put(KeyStroke.getKeyStroke(73, 2), "ZoomIn");
        inputMap.put(KeyStroke.getKeyStroke(79, 2), "ZoomOut");
        inputMap.put(KeyStroke.getKeyStroke(84, 2), "ResetTransform");
    }
    
    public void addPropertyChangeListener(final PropertyChangeListener listener) {
        this.pcs.addPropertyChangeListener(listener);
    }
    
    public void removePropertyChangeListener(final PropertyChangeListener listener) {
        this.pcs.removePropertyChangeListener(listener);
    }
    
    public void addPropertyChangeListener(final String propertyName, final PropertyChangeListener listener) {
        this.pcs.addPropertyChangeListener(propertyName, listener);
    }
    
    public void removePropertyChangeListener(final String propertyName, final PropertyChangeListener listener) {
        this.pcs.removePropertyChangeListener(propertyName, listener);
    }
    
    public void setEnableZoomInteractor(final boolean b) {
        if (this.isZoomInteractorEnabled != b) {
            final boolean isZoomInteractorEnabled = this.isZoomInteractorEnabled;
            this.isZoomInteractorEnabled = b;
            if (this.isZoomInteractorEnabled) {
                this.getInteractors().add(this.zoomInteractor);
            }
            else {
                this.getInteractors().remove(this.zoomInteractor);
            }
            this.pcs.firePropertyChange("enableZoomInteractor", isZoomInteractorEnabled, b);
        }
    }
    
    public boolean getEnableZoomInteractor() {
        return this.isZoomInteractorEnabled;
    }
    
    public void setEnableImageZoomInteractor(final boolean b) {
        if (this.isImageZoomInteractorEnabled != b) {
            final boolean isImageZoomInteractorEnabled = this.isImageZoomInteractorEnabled;
            this.isImageZoomInteractorEnabled = b;
            if (this.isImageZoomInteractorEnabled) {
                this.getInteractors().add(this.imageZoomInteractor);
            }
            else {
                this.getInteractors().remove(this.imageZoomInteractor);
            }
            this.pcs.firePropertyChange("enableImageZoomInteractor", isImageZoomInteractorEnabled, b);
        }
    }
    
    public boolean getEnableImageZoomInteractor() {
        return this.isImageZoomInteractorEnabled;
    }
    
    public void setEnablePanInteractor(final boolean b) {
        if (this.isPanInteractorEnabled != b) {
            final boolean isPanInteractorEnabled = this.isPanInteractorEnabled;
            this.isPanInteractorEnabled = b;
            if (this.isPanInteractorEnabled) {
                this.getInteractors().add(this.panInteractor);
            }
            else {
                this.getInteractors().remove(this.panInteractor);
            }
            this.pcs.firePropertyChange("enablePanInteractor", isPanInteractorEnabled, b);
        }
    }
    
    public boolean getEnablePanInteractor() {
        return this.isPanInteractorEnabled;
    }
    
    public void setEnableRotateInteractor(final boolean b) {
        if (this.isRotateInteractorEnabled != b) {
            final boolean isRotateInteractorEnabled = this.isRotateInteractorEnabled;
            this.isRotateInteractorEnabled = b;
            if (this.isRotateInteractorEnabled) {
                this.getInteractors().add(this.rotateInteractor);
            }
            else {
                this.getInteractors().remove(this.rotateInteractor);
            }
            this.pcs.firePropertyChange("enableRotateInteractor", isRotateInteractorEnabled, b);
        }
    }
    
    public boolean getEnableRotateInteractor() {
        return this.isRotateInteractorEnabled;
    }
    
    public void setEnableResetTransformInteractor(final boolean b) {
        if (this.isResetTransformInteractorEnabled != b) {
            final boolean isResetTransformInteractorEnabled = this.isResetTransformInteractorEnabled;
            this.isResetTransformInteractorEnabled = b;
            if (this.isResetTransformInteractorEnabled) {
                this.getInteractors().add(this.resetTransformInteractor);
            }
            else {
                this.getInteractors().remove(this.resetTransformInteractor);
            }
            this.pcs.firePropertyChange("enableResetTransformInteractor", isResetTransformInteractorEnabled, b);
        }
    }
    
    public boolean getEnableResetTransformInteractor() {
        return this.isResetTransformInteractorEnabled;
    }
    
    public String getURI() {
        return this.uri;
    }
    
    public void setURI(final String uri) {
        final String uri2 = this.uri;
        this.uri = uri;
        if (this.uri != null) {
            this.loadSVGDocument(this.uri);
        }
        else {
            this.setSVGDocument(null);
        }
        this.pcs.firePropertyChange("URI", uri2, this.uri);
    }
    
    protected UserAgent createUserAgent() {
        return new CanvasUserAgent();
    }
    
    protected Listener createListener() {
        return new CanvasSVGListener();
    }
    
    protected void installSVGDocument(final SVGDocument svgDocument) {
        if (this.toolTipDocs != null) {
            for (final SVGDocument svgDocument2 : this.toolTipDocs.keySet()) {
                if (svgDocument2 == null) {
                    continue;
                }
                final NodeEventTarget nodeEventTarget = (NodeEventTarget)svgDocument2.getRootElement();
                if (nodeEventTarget == null) {
                    continue;
                }
                nodeEventTarget.removeEventListenerNS("http://www.w3.org/2001/xml-events", "mouseover", this.toolTipListener, false);
                nodeEventTarget.removeEventListenerNS("http://www.w3.org/2001/xml-events", "mouseout", this.toolTipListener, false);
            }
            this.toolTipDocs = null;
        }
        this.lastTarget = null;
        if (this.toolTipMap != null) {
            this.toolTipMap.clear();
        }
        super.installSVGDocument(svgDocument);
    }
    
    public void setLastToolTipEvent(final long lastToolTipEventTimeStamp, final EventTarget lastToolTipEventTarget) {
        this.lastToolTipEventTimeStamp = lastToolTipEventTimeStamp;
        this.lastToolTipEventTarget = lastToolTipEventTarget;
    }
    
    public boolean matchLastToolTipEvent(final long n, final EventTarget eventTarget) {
        return this.lastToolTipEventTimeStamp == n && this.lastToolTipEventTarget == eventTarget;
    }
    
    static {
        MAP_TOKEN = new Object();
    }
    
    protected class ToolTipRunnable implements Runnable
    {
        String theToolTip;
        
        public ToolTipRunnable(final String theToolTip) {
            this.theToolTip = theToolTip;
        }
        
        public void run() {
            JSVGCanvas.this.setToolTipText(this.theToolTip);
            if (this.theToolTip != null) {
                ToolTipManager.sharedInstance().mouseEntered(new MouseEvent(JSVGCanvas.this, 504, System.currentTimeMillis(), 0, JSVGCanvas.this.locationListener.getLastX(), JSVGCanvas.this.locationListener.getLastY(), 0, false));
                ToolTipManager.sharedInstance().mouseMoved(new MouseEvent(JSVGCanvas.this, 503, System.currentTimeMillis(), 0, JSVGCanvas.this.locationListener.getLastX(), JSVGCanvas.this.locationListener.getLastY(), 0, false));
            }
            else {
                ToolTipManager.sharedInstance().mouseMoved(new MouseEvent(JSVGCanvas.this, 503, System.currentTimeMillis(), 0, JSVGCanvas.this.locationListener.getLastX(), JSVGCanvas.this.locationListener.getLastY(), 0, false));
            }
        }
    }
    
    protected class LocationListener extends MouseMotionAdapter
    {
        protected int lastX;
        protected int lastY;
        
        public LocationListener() {
            this.lastX = 0;
            this.lastY = 0;
        }
        
        public void mouseMoved(final MouseEvent mouseEvent) {
            this.lastX = mouseEvent.getX();
            this.lastY = mouseEvent.getY();
        }
        
        public int getLastX() {
            return this.lastX;
        }
        
        public int getLastY() {
            return this.lastY;
        }
    }
    
    protected class ToolTipModifier implements EventListener
    {
        protected CanvasUserAgent canvasUserAgent;
        
        public ToolTipModifier() {
        }
        
        public void handleEvent(final Event event) {
            if (JSVGCanvas.this.matchLastToolTipEvent(event.getTimeStamp(), event.getTarget())) {
                return;
            }
            JSVGCanvas.this.setLastToolTipEvent(event.getTimeStamp(), event.getTarget());
            final EventTarget lastTarget = JSVGCanvas.this.lastTarget;
            if ("mouseover".equals(event.getType())) {
                JSVGCanvas.this.lastTarget = event.getTarget();
            }
            else if ("mouseout".equals(event.getType())) {
                JSVGCanvas.this.lastTarget = ((org.w3c.dom.events.MouseEvent)event).getRelatedTarget();
            }
            if (JSVGCanvas.this.toolTipMap != null) {
                Element parentCSSStylableElement = (Element)JSVGCanvas.this.lastTarget;
                Object value = null;
                while (parentCSSStylableElement != null) {
                    value = JSVGCanvas.this.toolTipMap.get(parentCSSStylableElement);
                    if (value != null) {
                        break;
                    }
                    parentCSSStylableElement = CSSEngine.getParentCSSStylableElement(parentCSSStylableElement);
                }
                final String s = (String)value;
                if (lastTarget != JSVGCanvas.this.lastTarget) {
                    EventQueue.invokeLater(new ToolTipRunnable(s));
                }
            }
        }
    }
    
    protected class CanvasUserAgent extends BridgeUserAgent implements XMLConstants
    {
        final String TOOLTIP_TITLE_ONLY = "JSVGCanvas.CanvasUserAgent.ToolTip.titleOnly";
        final String TOOLTIP_DESC_ONLY = "JSVGCanvas.CanvasUserAgent.ToolTip.descOnly";
        final String TOOLTIP_TITLE_AND_TEXT = "JSVGCanvas.CanvasUserAgent.ToolTip.titleAndDesc";
        private final /* synthetic */ JSVGCanvas this$0;
        
        public void handleElement(final Element element, final Object o) {
            super.handleElement(element, o);
            if (!JSVGCanvas.this.isInteractive()) {
                return;
            }
            if (!"http://www.w3.org/2000/svg".equals(element.getNamespaceURI())) {
                return;
            }
            if (element.getParentNode() == element.getOwnerDocument().getDocumentElement()) {
                return;
            }
            Element element2;
            if (o instanceof Element) {
                element2 = (Element)o;
            }
            else {
                element2 = (Element)element.getParentNode();
            }
            Node peerWithTag = null;
            Node peerWithTag2 = null;
            if (element.getLocalName().equals("title")) {
                if (o == Boolean.TRUE) {
                    peerWithTag2 = element;
                }
                peerWithTag = this.getPeerWithTag(element2, "http://www.w3.org/2000/svg", "desc");
            }
            else if (element.getLocalName().equals("desc")) {
                if (o == Boolean.TRUE) {
                    peerWithTag = element;
                }
                peerWithTag2 = this.getPeerWithTag(element2, "http://www.w3.org/2000/svg", "title");
            }
            String nodeValue = null;
            if (peerWithTag2 != null) {
                peerWithTag2.normalize();
                if (peerWithTag2.getFirstChild() != null) {
                    nodeValue = peerWithTag2.getFirstChild().getNodeValue();
                }
            }
            String nodeValue2 = null;
            if (peerWithTag != null) {
                peerWithTag.normalize();
                if (peerWithTag.getFirstChild() != null) {
                    nodeValue2 = peerWithTag.getFirstChild().getNodeValue();
                }
            }
            String s;
            if (nodeValue != null && nodeValue.length() != 0) {
                if (nodeValue2 != null && nodeValue2.length() != 0) {
                    s = Messages.formatMessage("JSVGCanvas.CanvasUserAgent.ToolTip.titleAndDesc", new Object[] { this.toFormattedHTML(nodeValue), this.toFormattedHTML(nodeValue2) });
                }
                else {
                    s = Messages.formatMessage("JSVGCanvas.CanvasUserAgent.ToolTip.titleOnly", new Object[] { this.toFormattedHTML(nodeValue) });
                }
            }
            else if (nodeValue2 != null && nodeValue2.length() != 0) {
                s = Messages.formatMessage("JSVGCanvas.CanvasUserAgent.ToolTip.descOnly", new Object[] { this.toFormattedHTML(nodeValue2) });
            }
            else {
                s = null;
            }
            if (s == null) {
                this.removeToolTip(element2);
                return;
            }
            if (JSVGCanvas.this.lastTarget != element2) {
                this.setToolTip(element2, s);
            }
            else {
                Object value = null;
                if (JSVGCanvas.this.toolTipMap != null) {
                    value = JSVGCanvas.this.toolTipMap.get(element2);
                    JSVGCanvas.this.toolTipMap.put(element2, s);
                }
                if (value != null) {
                    EventQueue.invokeLater(new Runnable() {
                        private final /* synthetic */ CanvasUserAgent this$1 = this$1;
                        
                        public void run() {
                            this.this$1.this$0.setToolTipText(s);
                            ToolTipManager.sharedInstance().mouseMoved(new MouseEvent(this.this$1.this$0, 503, System.currentTimeMillis(), 0, this.this$1.this$0.locationListener.getLastX(), this.this$1.this$0.locationListener.getLastY(), 0, false));
                        }
                    });
                }
                else {
                    EventQueue.invokeLater(new ToolTipRunnable(s));
                }
            }
        }
        
        public String toFormattedHTML(final String str) {
            final StringBuffer sb = new StringBuffer(str);
            this.replace(sb, '&', "&amp;");
            this.replace(sb, '<', "&lt;");
            this.replace(sb, '>', "&gt;");
            this.replace(sb, '\"', "&quot;");
            this.replace(sb, '\n', "<br>");
            return sb.toString();
        }
        
        protected void replace(final StringBuffer sb, final char ch, final String str) {
            final String string = sb.toString();
            int n = string.length();
            while ((n = string.lastIndexOf(ch, n - 1)) != -1) {
                sb.deleteCharAt(n);
                sb.insert(n, str);
            }
        }
        
        public Element getPeerWithTag(final Element element, final String s, final String s2) {
            if (element == null) {
                return null;
            }
            for (Node node = element.getFirstChild(); node != null; node = node.getNextSibling()) {
                if (s.equals(node.getNamespaceURI())) {
                    if (s2.equals(node.getLocalName())) {
                        if (node.getNodeType() == 1) {
                            return (Element)node;
                        }
                    }
                }
            }
            return null;
        }
        
        public boolean hasPeerWithTag(final Element element, final String s, final String s2) {
            return this.getPeerWithTag(element, s, s2) != null;
        }
        
        public void setToolTip(final Element element, final String s) {
            if (JSVGCanvas.this.toolTipMap == null) {
                JSVGCanvas.this.toolTipMap = new WeakHashMap();
            }
            if (JSVGCanvas.this.toolTipDocs == null) {
                JSVGCanvas.this.toolTipDocs = new WeakHashMap();
            }
            final SVGDocument svgDocument = (SVGDocument)element.getOwnerDocument();
            if (JSVGCanvas.this.toolTipDocs.put(svgDocument, JSVGCanvas.MAP_TOKEN) == null) {
                final NodeEventTarget nodeEventTarget = (NodeEventTarget)svgDocument.getRootElement();
                nodeEventTarget.addEventListenerNS("http://www.w3.org/2001/xml-events", "mouseover", JSVGCanvas.this.toolTipListener, false, null);
                nodeEventTarget.addEventListenerNS("http://www.w3.org/2001/xml-events", "mouseout", JSVGCanvas.this.toolTipListener, false, null);
            }
            JSVGCanvas.this.toolTipMap.put(element, s);
            if (element == JSVGCanvas.this.lastTarget) {
                EventQueue.invokeLater(new ToolTipRunnable(s));
            }
        }
        
        public void removeToolTip(final Element element) {
            if (JSVGCanvas.this.toolTipMap != null) {
                JSVGCanvas.this.toolTipMap.remove(element);
            }
            if (JSVGCanvas.this.lastTarget == element) {
                EventQueue.invokeLater(new ToolTipRunnable(null));
            }
        }
        
        public void displayError(final String message) {
            if (JSVGCanvas.this.svgUserAgent != null) {
                super.displayError(message);
            }
            else {
                final JDialog dialog = new JOptionPane(message, 0).createDialog(JSVGCanvas.this, "ERROR");
                dialog.setModal(false);
                dialog.setVisible(true);
            }
        }
        
        public void displayError(final Exception ex) {
            if (JSVGCanvas.this.svgUserAgent != null) {
                super.displayError(ex);
            }
            else {
                final JDialog dialog = new JErrorPane(ex, 0).createDialog(JSVGCanvas.this, "ERROR");
                dialog.setModal(false);
                dialog.setVisible(true);
            }
        }
    }
    
    public class ScrollDownAction extends ScrollAction
    {
        public ScrollDownAction(final int n) {
            super(0.0, -n);
        }
    }
    
    public class ScrollAction extends AffineAction
    {
        public ScrollAction(final double tx, final double ty) {
            super(AffineTransform.getTranslateInstance(tx, ty));
        }
    }
    
    public class AffineAction extends AbstractAction
    {
        AffineTransform at;
        
        public AffineAction(final AffineTransform at) {
            this.at = at;
        }
        
        public void actionPerformed(final ActionEvent actionEvent) {
            if (JSVGCanvas.this.gvtRoot == null) {
                return;
            }
            final AffineTransform renderingTransform = JSVGCanvas.this.getRenderingTransform();
            if (this.at != null) {
                final Dimension size = JSVGCanvas.this.getSize();
                final int n = size.width / 2;
                final int n2 = size.height / 2;
                final AffineTransform translateInstance = AffineTransform.getTranslateInstance(n, n2);
                translateInstance.concatenate(this.at);
                translateInstance.translate(-n, -n2);
                translateInstance.concatenate(renderingTransform);
                JSVGCanvas.this.setRenderingTransform(translateInstance);
            }
        }
    }
    
    public class ScrollUpAction extends ScrollAction
    {
        public ScrollUpAction(final int n) {
            super(0.0, n);
        }
    }
    
    public class ScrollLeftAction extends ScrollAction
    {
        public ScrollLeftAction(final int n) {
            super(n, 0.0);
        }
    }
    
    public class ScrollRightAction extends ScrollAction
    {
        public ScrollRightAction(final int n) {
            super(-n, 0.0);
        }
    }
    
    public class RotateAction extends AffineAction
    {
        public RotateAction(final double theta) {
            super(AffineTransform.getRotateInstance(theta));
        }
    }
    
    public class ZoomOutAction extends ZoomAction
    {
        ZoomOutAction() {
            super(0.5);
        }
    }
    
    public class ZoomAction extends AffineAction
    {
        public ZoomAction(final double n) {
            super(AffineTransform.getScaleInstance(n, n));
        }
        
        public ZoomAction(final double sx, final double sy) {
            super(AffineTransform.getScaleInstance(sx, sy));
        }
    }
    
    public class ZoomInAction extends ZoomAction
    {
        ZoomInAction() {
            super(2.0);
        }
    }
    
    public class ResetTransformAction extends AbstractAction
    {
        public void actionPerformed(final ActionEvent actionEvent) {
            JSVGCanvas.this.fragmentIdentifier = null;
            JSVGCanvas.this.resetRenderingTransform();
        }
    }
    
    protected class CanvasSVGListener extends ExtendedSVGListener
    {
        public void documentLoadingStarted(final SVGDocumentLoaderEvent svgDocumentLoaderEvent) {
            super.documentLoadingStarted(svgDocumentLoaderEvent);
            JSVGCanvas.this.setToolTipText(null);
        }
    }
}
