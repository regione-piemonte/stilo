// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.gvt;

import java.io.ObjectStreamException;
import java.io.Serializable;
import org.apache.batik.gvt.text.GVTAttributedCharacterIterator;
import org.apache.batik.gvt.text.TextSpanLayout;
import java.awt.geom.GeneralPath;
import java.awt.Graphics2D;
import org.apache.batik.gvt.text.AttributedCharacterSpanIterator;
import org.apache.batik.gvt.text.TextPaintInfo;
import org.apache.batik.gvt.renderer.StrokingTextPainter;
import java.awt.Shape;
import java.awt.geom.Rectangle2D;
import java.util.List;
import org.apache.batik.gvt.text.Mark;
import java.awt.geom.Point2D;
import java.text.AttributedCharacterIterator;

public class TextNode extends AbstractGraphicsNode implements Selectable
{
    public static final AttributedCharacterIterator.Attribute PAINT_INFO;
    protected Point2D location;
    protected AttributedCharacterIterator aci;
    protected String text;
    protected Mark beginMark;
    protected Mark endMark;
    protected List textRuns;
    protected TextPainter textPainter;
    private Rectangle2D geometryBounds;
    private Rectangle2D primitiveBounds;
    private Shape outline;
    
    public TextNode() {
        this.location = new Point2D.Float(0.0f, 0.0f);
        this.beginMark = null;
        this.endMark = null;
        this.textPainter = StrokingTextPainter.getInstance();
    }
    
    public void setTextPainter(final TextPainter textPainter) {
        if (textPainter == null) {
            this.textPainter = StrokingTextPainter.getInstance();
        }
        else {
            this.textPainter = textPainter;
        }
    }
    
    public TextPainter getTextPainter() {
        return this.textPainter;
    }
    
    public List getTextRuns() {
        return this.textRuns;
    }
    
    public void setTextRuns(final List textRuns) {
        this.textRuns = textRuns;
    }
    
    public String getText() {
        if (this.text != null) {
            return this.text;
        }
        if (this.aci == null) {
            this.text = "";
        }
        else {
            final StringBuffer sb = new StringBuffer(this.aci.getEndIndex());
            for (char c = this.aci.first(); c != '\uffff'; c = this.aci.next()) {
                sb.append(c);
            }
            this.text = sb.toString();
        }
        return this.text;
    }
    
    public void setLocation(final Point2D location) {
        this.fireGraphicsNodeChangeStarted();
        this.invalidateGeometryCache();
        this.location = location;
        this.fireGraphicsNodeChangeCompleted();
    }
    
    public Point2D getLocation() {
        return this.location;
    }
    
    public void swapTextPaintInfo(final TextPaintInfo textPaintInfo, final TextPaintInfo textPaintInfo2) {
        this.fireGraphicsNodeChangeStarted();
        this.invalidateGeometryCache();
        textPaintInfo2.set(textPaintInfo);
        this.fireGraphicsNodeChangeCompleted();
    }
    
    public void setAttributedCharacterIterator(final AttributedCharacterIterator aci) {
        this.fireGraphicsNodeChangeStarted();
        this.invalidateGeometryCache();
        this.aci = aci;
        this.text = null;
        this.textRuns = null;
        this.fireGraphicsNodeChangeCompleted();
    }
    
    public AttributedCharacterIterator getAttributedCharacterIterator() {
        return this.aci;
    }
    
    protected void invalidateGeometryCache() {
        super.invalidateGeometryCache();
        this.primitiveBounds = null;
        this.geometryBounds = null;
        this.outline = null;
    }
    
    public Rectangle2D getPrimitiveBounds() {
        if (this.primitiveBounds == null && this.aci != null) {
            this.primitiveBounds = this.textPainter.getBounds2D(this);
        }
        return this.primitiveBounds;
    }
    
    public Rectangle2D getGeometryBounds() {
        if (this.geometryBounds == null && this.aci != null) {
            this.geometryBounds = this.textPainter.getGeometryBounds(this);
        }
        return this.geometryBounds;
    }
    
    public Rectangle2D getSensitiveBounds() {
        return this.getGeometryBounds();
    }
    
    public Shape getOutline() {
        if (this.outline == null && this.aci != null) {
            this.outline = this.textPainter.getOutline(this);
        }
        return this.outline;
    }
    
    public Mark getMarkerForChar(final int n, final boolean b) {
        return this.textPainter.getMark(this, n, b);
    }
    
    public void setSelection(final Mark beginMark, final Mark endMark) {
        if (beginMark.getTextNode() != this || endMark.getTextNode() != this) {
            throw new Error("Markers not from this TextNode");
        }
        this.beginMark = beginMark;
        this.endMark = endMark;
    }
    
    public boolean selectAt(final double n, final double n2) {
        this.beginMark = this.textPainter.selectAt(n, n2, this);
        return true;
    }
    
    public boolean selectTo(final double n, final double n2) {
        final Mark selectTo = this.textPainter.selectTo(n, n2, this.beginMark);
        if (selectTo == null) {
            return false;
        }
        if (selectTo != this.endMark) {
            this.endMark = selectTo;
            return true;
        }
        return false;
    }
    
    public boolean selectAll(final double n, final double n2) {
        this.beginMark = this.textPainter.selectFirst(this);
        this.endMark = this.textPainter.selectLast(this);
        return true;
    }
    
    public Object getSelection() {
        Object o = null;
        if (this.aci == null) {
            return o;
        }
        final int[] selected = this.textPainter.getSelected(this.beginMark, this.endMark);
        if (selected != null && selected.length > 1) {
            if (selected[0] > selected[1]) {
                final int n = selected[1];
                selected[1] = selected[0];
                selected[0] = n;
            }
            o = new AttributedCharacterSpanIterator(this.aci, selected[0], selected[1] + 1);
        }
        return o;
    }
    
    public Shape getHighlightShape() {
        return this.getGlobalTransform().createTransformedShape(this.textPainter.getHighlightShape(this.beginMark, this.endMark));
    }
    
    public void primitivePaint(final Graphics2D graphics2D) {
        final Shape clip = graphics2D.getClip();
        if (clip != null && !(clip instanceof GeneralPath)) {
            graphics2D.setClip(new GeneralPath(clip));
        }
        this.textPainter.paint(this, graphics2D);
    }
    
    public boolean contains(final Point2D point2D) {
        if (!super.contains(point2D)) {
            return false;
        }
        final List textRuns = this.getTextRuns();
        for (int i = 0; i < textRuns.size(); ++i) {
            final TextSpanLayout layout = textRuns.get(i).getLayout();
            if (layout.hitTestChar((float)point2D.getX(), (float)point2D.getY()) != null && this.contains(point2D, layout.getBounds2D())) {
                return true;
            }
        }
        return false;
    }
    
    protected boolean contains(final Point2D p2, final Rectangle2D rectangle2D) {
        if (rectangle2D == null || !rectangle2D.contains(p2)) {
            return false;
        }
        switch (this.pointerEventType) {
            case 0:
            case 1:
            case 2:
            case 3: {
                return this.isVisible;
            }
            case 4:
            case 5:
            case 6:
            case 7: {
                return true;
            }
            case 8: {
                return false;
            }
            default: {
                return false;
            }
        }
    }
    
    static {
        PAINT_INFO = GVTAttributedCharacterIterator.TextAttribute.PAINT_INFO;
    }
    
    public static final class Anchor implements Serializable
    {
        public static final int ANCHOR_START = 0;
        public static final int ANCHOR_MIDDLE = 1;
        public static final int ANCHOR_END = 2;
        public static final Anchor START;
        public static final Anchor MIDDLE;
        public static final Anchor END;
        private int type;
        
        private Anchor(final int type) {
            this.type = type;
        }
        
        public int getType() {
            return this.type;
        }
        
        private Object readResolve() throws ObjectStreamException {
            switch (this.type) {
                case 0: {
                    return Anchor.START;
                }
                case 1: {
                    return Anchor.MIDDLE;
                }
                case 2: {
                    return Anchor.END;
                }
                default: {
                    throw new Error("Unknown Anchor type");
                }
            }
        }
        
        static {
            START = new Anchor(0);
            MIDDLE = new Anchor(1);
            END = new Anchor(2);
        }
    }
}
