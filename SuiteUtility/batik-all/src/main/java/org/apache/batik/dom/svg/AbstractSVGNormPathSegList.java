// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.dom.svg;

import java.awt.geom.PathIterator;
import java.awt.geom.Arc2D;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import org.apache.batik.ext.awt.geom.ExtendedGeneralPath;
import org.apache.batik.parser.DefaultPathHandler;
import org.apache.batik.parser.ParseException;
import org.apache.batik.parser.PathHandler;
import org.apache.batik.parser.PathParser;

public abstract class AbstractSVGNormPathSegList extends AbstractSVGPathSegList
{
    protected AbstractSVGNormPathSegList() {
    }
    
    protected void doParse(final String s, final ListHandler listHandler) throws ParseException {
        final PathParser pathParser = new PathParser();
        pathParser.setPathHandler(new NormalizedPathSegListBuilder(listHandler));
        pathParser.parse(s);
    }
    
    protected class SVGPathSegGenericItem extends SVGPathSegItem
    {
        public SVGPathSegGenericItem(final short n, final String s, final float n2, final float n3, final float n4, final float n5, final float x, final float y) {
            super(n, s);
            this.x1 = n4;
            this.y1 = n5;
            this.x2 = n4;
            this.y2 = n5;
            this.x = x;
            this.y = y;
        }
        
        public void setValue(final float n, final float n2, final float n3, final float n4, final float x, final float y) {
            this.x1 = n3;
            this.y1 = n4;
            this.x2 = n3;
            this.y2 = n4;
            this.x = x;
            this.y = y;
        }
        
        public void setValue(final float x, final float y) {
            this.x = x;
            this.y = y;
        }
        
        public void setPathSegType(final short type) {
            this.type = type;
        }
        
        public float getX() {
            return this.x;
        }
        
        public float getY() {
            return this.y;
        }
        
        public void setX(final float x) {
            this.x = x;
        }
        
        public void setY(final float y) {
            this.y = y;
        }
        
        public float getX1() {
            return this.x1;
        }
        
        public float getY1() {
            return this.y1;
        }
        
        public void setX1(final float x1) {
            this.x1 = x1;
        }
        
        public void setY1(final float y1) {
            this.y1 = y1;
        }
        
        public float getX2() {
            return this.x2;
        }
        
        public float getY2() {
            return this.y2;
        }
        
        public void setX2(final float x2) {
            this.x2 = x2;
        }
        
        public void setY2(final float y2) {
            this.y2 = y2;
        }
    }
    
    protected class NormalizedPathSegListBuilder extends DefaultPathHandler
    {
        protected ListHandler listHandler;
        protected SVGPathSegGenericItem lastAbs;
        
        public NormalizedPathSegListBuilder(final ListHandler listHandler) {
            this.listHandler = listHandler;
        }
        
        public void startPath() throws ParseException {
            this.listHandler.startList();
            this.lastAbs = new SVGPathSegGenericItem((short)2, "M", 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f);
        }
        
        public void endPath() throws ParseException {
            this.listHandler.endList();
        }
        
        public void movetoRel(final float n, final float n2) throws ParseException {
            this.movetoAbs(this.lastAbs.getX() + n, this.lastAbs.getY() + n2);
        }
        
        public void movetoAbs(final float x, final float y) throws ParseException {
            this.listHandler.item(new SVGPathSegMovetoLinetoItem((short)2, "M", x, y));
            this.lastAbs.setX(x);
            this.lastAbs.setY(y);
            this.lastAbs.setPathSegType((short)2);
        }
        
        public void closePath() throws ParseException {
            this.listHandler.item(new SVGPathSegItem((short)1, "z"));
        }
        
        public void linetoRel(final float n, final float n2) throws ParseException {
            this.linetoAbs(this.lastAbs.getX() + n, this.lastAbs.getY() + n2);
        }
        
        public void linetoAbs(final float x, final float y) throws ParseException {
            this.listHandler.item(new SVGPathSegMovetoLinetoItem((short)4, "L", x, y));
            this.lastAbs.setX(x);
            this.lastAbs.setY(y);
            this.lastAbs.setPathSegType((short)4);
        }
        
        public void linetoHorizontalRel(final float n) throws ParseException {
            this.linetoAbs(this.lastAbs.getX() + n, this.lastAbs.getY());
        }
        
        public void linetoHorizontalAbs(final float n) throws ParseException {
            this.linetoAbs(n, this.lastAbs.getY());
        }
        
        public void linetoVerticalRel(final float n) throws ParseException {
            this.linetoAbs(this.lastAbs.getX(), this.lastAbs.getY() + n);
        }
        
        public void linetoVerticalAbs(final float n) throws ParseException {
            this.linetoAbs(this.lastAbs.getX(), n);
        }
        
        public void curvetoCubicRel(final float n, final float n2, final float n3, final float n4, final float n5, final float n6) throws ParseException {
            this.curvetoCubicAbs(this.lastAbs.getX() + n, this.lastAbs.getY() + n2, this.lastAbs.getX() + n3, this.lastAbs.getY() + n4, this.lastAbs.getX() + n5, this.lastAbs.getY() + n6);
        }
        
        public void curvetoCubicAbs(final float n, final float n2, final float n3, final float n4, final float n5, final float n6) throws ParseException {
            this.listHandler.item(new SVGPathSegCurvetoCubicItem((short)6, "C", n, n2, n3, n4, n5, n6));
            this.lastAbs.setValue(n, n2, n3, n4, n5, n6);
            this.lastAbs.setPathSegType((short)6);
        }
        
        public void curvetoCubicSmoothRel(final float n, final float n2, final float n3, final float n4) throws ParseException {
            this.curvetoCubicSmoothAbs(this.lastAbs.getX() + n, this.lastAbs.getY() + n2, this.lastAbs.getX() + n3, this.lastAbs.getY() + n4);
        }
        
        public void curvetoCubicSmoothAbs(final float n, final float n2, final float n3, final float n4) throws ParseException {
            if (this.lastAbs.getPathSegType() == 6) {
                this.curvetoCubicAbs(this.lastAbs.getX() + (this.lastAbs.getX() - this.lastAbs.getX2()), this.lastAbs.getY() + (this.lastAbs.getY() - this.lastAbs.getY2()), n, n2, n3, n4);
            }
            else {
                this.curvetoCubicAbs(this.lastAbs.getX(), this.lastAbs.getY(), n, n2, n3, n4);
            }
        }
        
        public void curvetoQuadraticRel(final float n, final float n2, final float n3, final float n4) throws ParseException {
            this.curvetoQuadraticAbs(this.lastAbs.getX() + n, this.lastAbs.getY() + n2, this.lastAbs.getX() + n3, this.lastAbs.getY() + n4);
        }
        
        public void curvetoQuadraticAbs(final float x1, final float y1, final float n, final float n2) throws ParseException {
            this.curvetoCubicAbs(this.lastAbs.getX() + 2.0f * (x1 - this.lastAbs.getX()) / 3.0f, this.lastAbs.getY() + 2.0f * (y1 - this.lastAbs.getY()) / 3.0f, n + 2.0f * (x1 - n) / 3.0f, n2 + 2.0f * (y1 - n2) / 3.0f, n, n2);
            this.lastAbs.setX1(x1);
            this.lastAbs.setY1(y1);
            this.lastAbs.setPathSegType((short)8);
        }
        
        public void curvetoQuadraticSmoothRel(final float n, final float n2) throws ParseException {
            this.curvetoQuadraticSmoothAbs(this.lastAbs.getX() + n, this.lastAbs.getY() + n2);
        }
        
        public void curvetoQuadraticSmoothAbs(final float n, final float n2) throws ParseException {
            if (this.lastAbs.getPathSegType() == 8) {
                this.curvetoQuadraticAbs(this.lastAbs.getX() + (this.lastAbs.getX() - this.lastAbs.getX1()), this.lastAbs.getY() + (this.lastAbs.getY() - this.lastAbs.getY1()), n, n2);
            }
            else {
                this.curvetoQuadraticAbs(this.lastAbs.getX(), this.lastAbs.getY(), n, n2);
            }
        }
        
        public void arcRel(final float n, final float n2, final float n3, final boolean b, final boolean b2, final float n4, final float n5) throws ParseException {
            this.arcAbs(n, n2, n3, b, b2, this.lastAbs.getX() + n4, this.lastAbs.getY() + n5);
        }
        
        public void arcAbs(final float n, final float n2, final float n3, final boolean b, final boolean b2, final float n4, final float n5) throws ParseException {
            if (n == 0.0f || n2 == 0.0f) {
                this.linetoAbs(n4, n5);
                return;
            }
            final double n6 = this.lastAbs.getX();
            final double n7 = this.lastAbs.getY();
            if (n6 == n4 && n7 == n5) {
                return;
            }
            final Arc2D computeArc = ExtendedGeneralPath.computeArc(n6, n7, n, n2, n3, b, b2, n4, n5);
            if (computeArc == null) {
                return;
            }
            final PathIterator pathIterator = AffineTransform.getRotateInstance(Math.toRadians(n3), computeArc.getCenterX(), computeArc.getCenterY()).createTransformedShape(computeArc).getPathIterator(new AffineTransform());
            final float[] array = { 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f };
            while (!pathIterator.isDone()) {
                switch (pathIterator.currentSegment(array)) {
                    case 3: {
                        this.curvetoCubicAbs(array[0], array[1], array[2], array[3], array[4], array[5]);
                        break;
                    }
                }
                pathIterator.next();
            }
            this.lastAbs.setPathSegType((short)10);
        }
    }
}
