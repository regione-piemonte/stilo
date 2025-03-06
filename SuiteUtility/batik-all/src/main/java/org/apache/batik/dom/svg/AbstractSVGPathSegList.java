// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.dom.svg;

import org.w3c.dom.svg.SVGPathSegArcRel;
import org.w3c.dom.svg.SVGPathSegArcAbs;
import org.w3c.dom.svg.SVGPathSegCurvetoQuadraticSmoothRel;
import org.w3c.dom.svg.SVGPathSegCurvetoQuadraticSmoothAbs;
import org.w3c.dom.svg.SVGPathSegCurvetoQuadraticRel;
import org.w3c.dom.svg.SVGPathSegCurvetoQuadraticAbs;
import org.w3c.dom.svg.SVGPathSegCurvetoCubicSmoothRel;
import org.w3c.dom.svg.SVGPathSegCurvetoCubicSmoothAbs;
import org.w3c.dom.svg.SVGPathSegCurvetoCubicRel;
import org.w3c.dom.svg.SVGPathSegCurvetoCubicAbs;
import org.w3c.dom.svg.SVGPathSegLinetoVerticalRel;
import org.w3c.dom.svg.SVGPathSegLinetoVerticalAbs;
import org.w3c.dom.svg.SVGPathSegLinetoHorizontalRel;
import org.w3c.dom.svg.SVGPathSegLinetoHorizontalAbs;
import org.w3c.dom.svg.SVGPathSegClosePath;
import org.w3c.dom.svg.SVGPathSegLinetoRel;
import org.w3c.dom.svg.SVGPathSegLinetoAbs;
import org.w3c.dom.svg.SVGPathSegMovetoRel;
import org.w3c.dom.svg.SVGPathSegMovetoAbs;
import org.apache.batik.parser.DefaultPathHandler;
import org.apache.batik.parser.ParseException;
import org.apache.batik.parser.PathHandler;
import org.apache.batik.parser.PathParser;
import org.w3c.dom.DOMException;
import org.w3c.dom.svg.SVGPathSeg;
import org.w3c.dom.svg.SVGException;
import org.w3c.dom.svg.SVGPathSegList;

public abstract class AbstractSVGPathSegList extends AbstractSVGList implements SVGPathSegList, SVGPathSegConstants
{
    public static final String SVG_PATHSEG_LIST_SEPARATOR = " ";
    
    protected AbstractSVGPathSegList() {
    }
    
    protected String getItemSeparator() {
        return " ";
    }
    
    protected abstract SVGException createSVGException(final short p0, final String p1, final Object[] p2);
    
    public SVGPathSeg initialize(final SVGPathSeg svgPathSeg) throws DOMException, SVGException {
        return (SVGPathSeg)this.initializeImpl(svgPathSeg);
    }
    
    public SVGPathSeg getItem(final int n) throws DOMException {
        return (SVGPathSeg)this.getItemImpl(n);
    }
    
    public SVGPathSeg insertItemBefore(final SVGPathSeg svgPathSeg, final int n) throws DOMException, SVGException {
        return (SVGPathSeg)this.insertItemBeforeImpl(svgPathSeg, n);
    }
    
    public SVGPathSeg replaceItem(final SVGPathSeg svgPathSeg, final int n) throws DOMException, SVGException {
        return (SVGPathSeg)this.replaceItemImpl(svgPathSeg, n);
    }
    
    public SVGPathSeg removeItem(final int n) throws DOMException {
        return (SVGPathSeg)this.removeItemImpl(n);
    }
    
    public SVGPathSeg appendItem(final SVGPathSeg svgPathSeg) throws DOMException, SVGException {
        return (SVGPathSeg)this.appendItemImpl(svgPathSeg);
    }
    
    protected SVGItem createSVGItem(final Object o) {
        return this.createPathSegItem((SVGPathSeg)o);
    }
    
    protected void doParse(final String s, final ListHandler listHandler) throws ParseException {
        final PathParser pathParser = new PathParser();
        pathParser.setPathHandler(new PathSegListBuilder(listHandler));
        pathParser.parse(s);
    }
    
    protected void checkItemType(final Object o) {
        if (!(o instanceof SVGPathSeg)) {
            this.createSVGException((short)0, "expected SVGPathSeg", null);
        }
    }
    
    protected SVGPathSegItem createPathSegItem(final SVGPathSeg svgPathSeg) {
        SVGPathSegItem svgPathSegItem = null;
        switch (svgPathSeg.getPathSegType()) {
            case 10:
            case 11: {
                svgPathSegItem = new SVGPathSegArcItem(svgPathSeg);
                break;
            }
            case 1: {
                svgPathSegItem = new SVGPathSegItem(svgPathSeg);
                break;
            }
            case 6:
            case 7: {
                svgPathSegItem = new SVGPathSegCurvetoCubicItem(svgPathSeg);
                break;
            }
            case 16:
            case 17: {
                svgPathSegItem = new SVGPathSegCurvetoCubicSmoothItem(svgPathSeg);
                break;
            }
            case 8:
            case 9: {
                svgPathSegItem = new SVGPathSegCurvetoQuadraticItem(svgPathSeg);
                break;
            }
            case 18:
            case 19: {
                svgPathSegItem = new SVGPathSegCurvetoQuadraticSmoothItem(svgPathSeg);
                break;
            }
            case 2:
            case 3:
            case 4:
            case 5: {
                svgPathSegItem = new SVGPathSegMovetoLinetoItem(svgPathSeg);
                break;
            }
            case 12:
            case 13: {
                svgPathSegItem = new SVGPathSegLinetoHorizontalItem(svgPathSeg);
                break;
            }
            case 14:
            case 15: {
                svgPathSegItem = new SVGPathSegLinetoVerticalItem(svgPathSeg);
                break;
            }
        }
        return svgPathSegItem;
    }
    
    protected class PathSegListBuilder extends DefaultPathHandler
    {
        protected ListHandler listHandler;
        
        public PathSegListBuilder(final ListHandler listHandler) {
            this.listHandler = listHandler;
        }
        
        public void startPath() throws ParseException {
            this.listHandler.startList();
        }
        
        public void endPath() throws ParseException {
            this.listHandler.endList();
        }
        
        public void movetoRel(final float n, final float n2) throws ParseException {
            this.listHandler.item(new SVGPathSegMovetoLinetoItem((short)3, "m", n, n2));
        }
        
        public void movetoAbs(final float n, final float n2) throws ParseException {
            this.listHandler.item(new SVGPathSegMovetoLinetoItem((short)2, "M", n, n2));
        }
        
        public void closePath() throws ParseException {
            this.listHandler.item(new SVGPathSegItem((short)1, "z"));
        }
        
        public void linetoRel(final float n, final float n2) throws ParseException {
            this.listHandler.item(new SVGPathSegMovetoLinetoItem((short)5, "l", n, n2));
        }
        
        public void linetoAbs(final float n, final float n2) throws ParseException {
            this.listHandler.item(new SVGPathSegMovetoLinetoItem((short)4, "L", n, n2));
        }
        
        public void linetoHorizontalRel(final float n) throws ParseException {
            this.listHandler.item(new SVGPathSegLinetoHorizontalItem((short)13, "h", n));
        }
        
        public void linetoHorizontalAbs(final float n) throws ParseException {
            this.listHandler.item(new SVGPathSegLinetoHorizontalItem((short)12, "H", n));
        }
        
        public void linetoVerticalRel(final float n) throws ParseException {
            this.listHandler.item(new SVGPathSegLinetoVerticalItem((short)15, "v", n));
        }
        
        public void linetoVerticalAbs(final float n) throws ParseException {
            this.listHandler.item(new SVGPathSegLinetoVerticalItem((short)14, "V", n));
        }
        
        public void curvetoCubicRel(final float n, final float n2, final float n3, final float n4, final float n5, final float n6) throws ParseException {
            this.listHandler.item(new SVGPathSegCurvetoCubicItem((short)7, "c", n, n2, n3, n4, n5, n6));
        }
        
        public void curvetoCubicAbs(final float n, final float n2, final float n3, final float n4, final float n5, final float n6) throws ParseException {
            this.listHandler.item(new SVGPathSegCurvetoCubicItem((short)6, "C", n, n2, n3, n4, n5, n6));
        }
        
        public void curvetoCubicSmoothRel(final float n, final float n2, final float n3, final float n4) throws ParseException {
            this.listHandler.item(new SVGPathSegCurvetoCubicSmoothItem((short)17, "s", n, n2, n3, n4));
        }
        
        public void curvetoCubicSmoothAbs(final float n, final float n2, final float n3, final float n4) throws ParseException {
            this.listHandler.item(new SVGPathSegCurvetoCubicSmoothItem((short)16, "S", n, n2, n3, n4));
        }
        
        public void curvetoQuadraticRel(final float n, final float n2, final float n3, final float n4) throws ParseException {
            this.listHandler.item(new SVGPathSegCurvetoQuadraticItem((short)9, "q", n, n2, n3, n4));
        }
        
        public void curvetoQuadraticAbs(final float n, final float n2, final float n3, final float n4) throws ParseException {
            this.listHandler.item(new SVGPathSegCurvetoQuadraticItem((short)8, "Q", n, n2, n3, n4));
        }
        
        public void curvetoQuadraticSmoothRel(final float n, final float n2) throws ParseException {
            this.listHandler.item(new SVGPathSegCurvetoQuadraticSmoothItem((short)19, "t", n, n2));
        }
        
        public void curvetoQuadraticSmoothAbs(final float n, final float n2) throws ParseException {
            this.listHandler.item(new SVGPathSegCurvetoQuadraticSmoothItem((short)18, "T", n, n2));
        }
        
        public void arcRel(final float n, final float n2, final float n3, final boolean b, final boolean b2, final float n4, final float n5) throws ParseException {
            this.listHandler.item(new SVGPathSegArcItem((short)11, "a", n, n2, n3, b, b2, n4, n5));
        }
        
        public void arcAbs(final float n, final float n2, final float n3, final boolean b, final boolean b2, final float n4, final float n5) throws ParseException {
            this.listHandler.item(new SVGPathSegArcItem((short)10, "A", n, n2, n3, b, b2, n4, n5));
        }
    }
    
    public class SVGPathSegMovetoLinetoItem extends SVGPathSegItem implements SVGPathSegMovetoAbs, SVGPathSegMovetoRel, SVGPathSegLinetoAbs, SVGPathSegLinetoRel
    {
        public SVGPathSegMovetoLinetoItem(final short n, final String s, final float x, final float y) {
            super(n, s);
            this.x = x;
            this.y = y;
        }
        
        public SVGPathSegMovetoLinetoItem(final SVGPathSeg svgPathSeg) {
            switch (this.type = svgPathSeg.getPathSegType()) {
                case 5: {
                    this.letter = "l";
                    this.x = ((SVGPathSegLinetoRel)svgPathSeg).getX();
                    this.y = ((SVGPathSegLinetoRel)svgPathSeg).getY();
                    break;
                }
                case 4: {
                    this.letter = "L";
                    this.x = ((SVGPathSegLinetoAbs)svgPathSeg).getX();
                    this.y = ((SVGPathSegLinetoAbs)svgPathSeg).getY();
                    break;
                }
                case 3: {
                    this.letter = "m";
                    this.x = ((SVGPathSegMovetoRel)svgPathSeg).getX();
                    this.y = ((SVGPathSegMovetoRel)svgPathSeg).getY();
                    break;
                }
                case 2: {
                    this.letter = "M";
                    this.x = ((SVGPathSegMovetoAbs)svgPathSeg).getX();
                    this.y = ((SVGPathSegMovetoAbs)svgPathSeg).getY();
                    break;
                }
            }
        }
        
        public float getX() {
            return this.x;
        }
        
        public float getY() {
            return this.y;
        }
        
        public void setX(final float x) {
            this.x = x;
            this.resetAttribute();
        }
        
        public void setY(final float y) {
            this.y = y;
            this.resetAttribute();
        }
        
        protected String getStringValue() {
            return this.letter + ' ' + Float.toString(this.x) + ' ' + Float.toString(this.y);
        }
    }
    
    protected class SVGPathSegItem extends AbstractSVGItem implements SVGPathSeg, SVGPathSegClosePath
    {
        protected short type;
        protected String letter;
        protected float x;
        protected float y;
        protected float x1;
        protected float y1;
        protected float x2;
        protected float y2;
        protected float r1;
        protected float r2;
        protected float angle;
        protected boolean largeArcFlag;
        protected boolean sweepFlag;
        
        protected SVGPathSegItem() {
        }
        
        public SVGPathSegItem(final short type, final String letter) {
            this.type = type;
            this.letter = letter;
        }
        
        public SVGPathSegItem(final SVGPathSeg svgPathSeg) {
            switch (this.type = svgPathSeg.getPathSegType()) {
                case 1: {
                    this.letter = "z";
                    break;
                }
            }
        }
        
        protected String getStringValue() {
            return this.letter;
        }
        
        public short getPathSegType() {
            return this.type;
        }
        
        public String getPathSegTypeAsLetter() {
            return this.letter;
        }
    }
    
    public class SVGPathSegLinetoHorizontalItem extends SVGPathSegItem implements SVGPathSegLinetoHorizontalAbs, SVGPathSegLinetoHorizontalRel
    {
        public SVGPathSegLinetoHorizontalItem(final short n, final String s, final float x) {
            super(n, s);
            this.x = x;
        }
        
        public SVGPathSegLinetoHorizontalItem(final SVGPathSeg svgPathSeg) {
            switch (this.type = svgPathSeg.getPathSegType()) {
                case 12: {
                    this.letter = "H";
                    this.x = ((SVGPathSegLinetoHorizontalAbs)svgPathSeg).getX();
                    break;
                }
                case 13: {
                    this.letter = "h";
                    this.x = ((SVGPathSegLinetoHorizontalRel)svgPathSeg).getX();
                    break;
                }
            }
        }
        
        public float getX() {
            return this.x;
        }
        
        public void setX(final float x) {
            this.x = x;
            this.resetAttribute();
        }
        
        protected String getStringValue() {
            return this.letter + ' ' + Float.toString(this.x);
        }
    }
    
    public class SVGPathSegLinetoVerticalItem extends SVGPathSegItem implements SVGPathSegLinetoVerticalAbs, SVGPathSegLinetoVerticalRel
    {
        public SVGPathSegLinetoVerticalItem(final short n, final String s, final float y) {
            super(n, s);
            this.y = y;
        }
        
        public SVGPathSegLinetoVerticalItem(final SVGPathSeg svgPathSeg) {
            switch (this.type = svgPathSeg.getPathSegType()) {
                case 14: {
                    this.letter = "V";
                    this.y = ((SVGPathSegLinetoVerticalAbs)svgPathSeg).getY();
                    break;
                }
                case 15: {
                    this.letter = "v";
                    this.y = ((SVGPathSegLinetoVerticalRel)svgPathSeg).getY();
                    break;
                }
            }
        }
        
        public float getY() {
            return this.y;
        }
        
        public void setY(final float y) {
            this.y = y;
            this.resetAttribute();
        }
        
        protected String getStringValue() {
            return this.letter + ' ' + Float.toString(this.y);
        }
    }
    
    public class SVGPathSegCurvetoCubicItem extends SVGPathSegItem implements SVGPathSegCurvetoCubicAbs, SVGPathSegCurvetoCubicRel
    {
        public SVGPathSegCurvetoCubicItem(final short n, final String s, final float x1, final float y1, final float x2, final float y2, final float x3, final float y3) {
            super(n, s);
            this.x = x3;
            this.y = y3;
            this.x1 = x1;
            this.y1 = y1;
            this.x2 = x2;
            this.y2 = y2;
        }
        
        public SVGPathSegCurvetoCubicItem(final SVGPathSeg svgPathSeg) {
            switch (this.type = svgPathSeg.getPathSegType()) {
                case 6: {
                    this.letter = "C";
                    this.x = ((SVGPathSegCurvetoCubicAbs)svgPathSeg).getX();
                    this.y = ((SVGPathSegCurvetoCubicAbs)svgPathSeg).getY();
                    this.x1 = ((SVGPathSegCurvetoCubicAbs)svgPathSeg).getX1();
                    this.y1 = ((SVGPathSegCurvetoCubicAbs)svgPathSeg).getY1();
                    this.x2 = ((SVGPathSegCurvetoCubicAbs)svgPathSeg).getX2();
                    this.y2 = ((SVGPathSegCurvetoCubicAbs)svgPathSeg).getY2();
                    break;
                }
                case 7: {
                    this.letter = "c";
                    this.x = ((SVGPathSegCurvetoCubicRel)svgPathSeg).getX();
                    this.y = ((SVGPathSegCurvetoCubicRel)svgPathSeg).getY();
                    this.x1 = ((SVGPathSegCurvetoCubicRel)svgPathSeg).getX1();
                    this.y1 = ((SVGPathSegCurvetoCubicRel)svgPathSeg).getY1();
                    this.x2 = ((SVGPathSegCurvetoCubicRel)svgPathSeg).getX2();
                    this.y2 = ((SVGPathSegCurvetoCubicRel)svgPathSeg).getY2();
                    break;
                }
            }
        }
        
        public float getX() {
            return this.x;
        }
        
        public float getY() {
            return this.y;
        }
        
        public void setX(final float x) {
            this.x = x;
            this.resetAttribute();
        }
        
        public void setY(final float y) {
            this.y = y;
            this.resetAttribute();
        }
        
        public float getX1() {
            return this.x1;
        }
        
        public float getY1() {
            return this.y1;
        }
        
        public void setX1(final float x1) {
            this.x1 = x1;
            this.resetAttribute();
        }
        
        public void setY1(final float y1) {
            this.y1 = y1;
            this.resetAttribute();
        }
        
        public float getX2() {
            return this.x2;
        }
        
        public float getY2() {
            return this.y2;
        }
        
        public void setX2(final float x2) {
            this.x2 = x2;
            this.resetAttribute();
        }
        
        public void setY2(final float y2) {
            this.y2 = y2;
            this.resetAttribute();
        }
        
        protected String getStringValue() {
            return this.letter + ' ' + Float.toString(this.x1) + ' ' + Float.toString(this.y1) + ' ' + Float.toString(this.x2) + ' ' + Float.toString(this.y2) + ' ' + Float.toString(this.x) + ' ' + Float.toString(this.y);
        }
    }
    
    public class SVGPathSegCurvetoCubicSmoothItem extends SVGPathSegItem implements SVGPathSegCurvetoCubicSmoothAbs, SVGPathSegCurvetoCubicSmoothRel
    {
        public SVGPathSegCurvetoCubicSmoothItem(final short n, final String s, final float x2, final float y2, final float x3, final float y3) {
            super(n, s);
            this.x = x3;
            this.y = y3;
            this.x2 = x2;
            this.y2 = y2;
        }
        
        public SVGPathSegCurvetoCubicSmoothItem(final SVGPathSeg svgPathSeg) {
            switch (this.type = svgPathSeg.getPathSegType()) {
                case 16: {
                    this.letter = "S";
                    this.x = ((SVGPathSegCurvetoCubicSmoothAbs)svgPathSeg).getX();
                    this.y = ((SVGPathSegCurvetoCubicSmoothAbs)svgPathSeg).getY();
                    this.x2 = ((SVGPathSegCurvetoCubicSmoothAbs)svgPathSeg).getX2();
                    this.y2 = ((SVGPathSegCurvetoCubicSmoothAbs)svgPathSeg).getY2();
                    break;
                }
                case 17: {
                    this.letter = "s";
                    this.x = ((SVGPathSegCurvetoCubicSmoothRel)svgPathSeg).getX();
                    this.y = ((SVGPathSegCurvetoCubicSmoothRel)svgPathSeg).getY();
                    this.x2 = ((SVGPathSegCurvetoCubicSmoothRel)svgPathSeg).getX2();
                    this.y2 = ((SVGPathSegCurvetoCubicSmoothRel)svgPathSeg).getY2();
                    break;
                }
            }
        }
        
        public float getX() {
            return this.x;
        }
        
        public float getY() {
            return this.y;
        }
        
        public void setX(final float x) {
            this.x = x;
            this.resetAttribute();
        }
        
        public void setY(final float y) {
            this.y = y;
            this.resetAttribute();
        }
        
        public float getX2() {
            return this.x2;
        }
        
        public float getY2() {
            return this.y2;
        }
        
        public void setX2(final float x2) {
            this.x2 = x2;
            this.resetAttribute();
        }
        
        public void setY2(final float y2) {
            this.y2 = y2;
            this.resetAttribute();
        }
        
        protected String getStringValue() {
            return this.letter + ' ' + Float.toString(this.x2) + ' ' + Float.toString(this.y2) + ' ' + Float.toString(this.x) + ' ' + Float.toString(this.y);
        }
    }
    
    public class SVGPathSegCurvetoQuadraticItem extends SVGPathSegItem implements SVGPathSegCurvetoQuadraticAbs, SVGPathSegCurvetoQuadraticRel
    {
        public SVGPathSegCurvetoQuadraticItem(final short n, final String s, final float x1, final float y1, final float x2, final float y2) {
            super(n, s);
            this.x = x2;
            this.y = y2;
            this.x1 = x1;
            this.y1 = y1;
        }
        
        public SVGPathSegCurvetoQuadraticItem(final SVGPathSeg svgPathSeg) {
            switch (this.type = svgPathSeg.getPathSegType()) {
                case 8: {
                    this.letter = "Q";
                    this.x = ((SVGPathSegCurvetoQuadraticAbs)svgPathSeg).getX();
                    this.y = ((SVGPathSegCurvetoQuadraticAbs)svgPathSeg).getY();
                    this.x1 = ((SVGPathSegCurvetoQuadraticAbs)svgPathSeg).getX1();
                    this.y1 = ((SVGPathSegCurvetoQuadraticAbs)svgPathSeg).getY1();
                    break;
                }
                case 9: {
                    this.letter = "q";
                    this.x = ((SVGPathSegCurvetoQuadraticRel)svgPathSeg).getX();
                    this.y = ((SVGPathSegCurvetoQuadraticRel)svgPathSeg).getY();
                    this.x1 = ((SVGPathSegCurvetoQuadraticRel)svgPathSeg).getX1();
                    this.y1 = ((SVGPathSegCurvetoQuadraticRel)svgPathSeg).getY1();
                    break;
                }
            }
        }
        
        public float getX() {
            return this.x;
        }
        
        public float getY() {
            return this.y;
        }
        
        public void setX(final float x) {
            this.x = x;
            this.resetAttribute();
        }
        
        public void setY(final float y) {
            this.y = y;
            this.resetAttribute();
        }
        
        public float getX1() {
            return this.x1;
        }
        
        public float getY1() {
            return this.y1;
        }
        
        public void setX1(final float x1) {
            this.x1 = x1;
            this.resetAttribute();
        }
        
        public void setY1(final float y1) {
            this.y1 = y1;
            this.resetAttribute();
        }
        
        protected String getStringValue() {
            return this.letter + ' ' + Float.toString(this.x1) + ' ' + Float.toString(this.y1) + ' ' + Float.toString(this.x) + ' ' + Float.toString(this.y);
        }
    }
    
    public class SVGPathSegCurvetoQuadraticSmoothItem extends SVGPathSegItem implements SVGPathSegCurvetoQuadraticSmoothAbs, SVGPathSegCurvetoQuadraticSmoothRel
    {
        public SVGPathSegCurvetoQuadraticSmoothItem(final short n, final String s, final float x, final float y) {
            super(n, s);
            this.x = x;
            this.y = y;
        }
        
        public SVGPathSegCurvetoQuadraticSmoothItem(final SVGPathSeg svgPathSeg) {
            switch (this.type = svgPathSeg.getPathSegType()) {
                case 18: {
                    this.letter = "T";
                    this.x = ((SVGPathSegCurvetoQuadraticSmoothAbs)svgPathSeg).getX();
                    this.y = ((SVGPathSegCurvetoQuadraticSmoothAbs)svgPathSeg).getY();
                    break;
                }
                case 19: {
                    this.letter = "t";
                    this.x = ((SVGPathSegCurvetoQuadraticSmoothRel)svgPathSeg).getX();
                    this.y = ((SVGPathSegCurvetoQuadraticSmoothRel)svgPathSeg).getY();
                    break;
                }
            }
        }
        
        public float getX() {
            return this.x;
        }
        
        public float getY() {
            return this.y;
        }
        
        public void setX(final float x) {
            this.x = x;
            this.resetAttribute();
        }
        
        public void setY(final float y) {
            this.y = y;
            this.resetAttribute();
        }
        
        protected String getStringValue() {
            return this.letter + ' ' + Float.toString(this.x) + ' ' + Float.toString(this.y);
        }
    }
    
    public class SVGPathSegArcItem extends SVGPathSegItem implements SVGPathSegArcAbs, SVGPathSegArcRel
    {
        public SVGPathSegArcItem(final short n, final String s, final float r1, final float r2, final float angle, final boolean largeArcFlag, final boolean sweepFlag, final float x, final float y) {
            super(n, s);
            this.x = x;
            this.y = y;
            this.r1 = r1;
            this.r2 = r2;
            this.angle = angle;
            this.largeArcFlag = largeArcFlag;
            this.sweepFlag = sweepFlag;
        }
        
        public SVGPathSegArcItem(final SVGPathSeg svgPathSeg) {
            switch (this.type = svgPathSeg.getPathSegType()) {
                case 10: {
                    this.letter = "A";
                    this.x = ((SVGPathSegArcAbs)svgPathSeg).getX();
                    this.y = ((SVGPathSegArcAbs)svgPathSeg).getY();
                    this.r1 = ((SVGPathSegArcAbs)svgPathSeg).getR1();
                    this.r2 = ((SVGPathSegArcAbs)svgPathSeg).getR2();
                    this.angle = ((SVGPathSegArcAbs)svgPathSeg).getAngle();
                    this.largeArcFlag = ((SVGPathSegArcAbs)svgPathSeg).getLargeArcFlag();
                    this.sweepFlag = ((SVGPathSegArcAbs)svgPathSeg).getSweepFlag();
                    break;
                }
                case 11: {
                    this.letter = "a";
                    this.x = ((SVGPathSegArcRel)svgPathSeg).getX();
                    this.y = ((SVGPathSegArcRel)svgPathSeg).getY();
                    this.r1 = ((SVGPathSegArcRel)svgPathSeg).getR1();
                    this.r2 = ((SVGPathSegArcRel)svgPathSeg).getR2();
                    this.angle = ((SVGPathSegArcRel)svgPathSeg).getAngle();
                    this.largeArcFlag = ((SVGPathSegArcRel)svgPathSeg).getLargeArcFlag();
                    this.sweepFlag = ((SVGPathSegArcRel)svgPathSeg).getSweepFlag();
                    break;
                }
            }
        }
        
        public float getX() {
            return this.x;
        }
        
        public float getY() {
            return this.y;
        }
        
        public void setX(final float x) {
            this.x = x;
            this.resetAttribute();
        }
        
        public void setY(final float y) {
            this.y = y;
            this.resetAttribute();
        }
        
        public float getR1() {
            return this.r1;
        }
        
        public float getR2() {
            return this.r2;
        }
        
        public void setR1(final float r1) {
            this.r1 = r1;
            this.resetAttribute();
        }
        
        public void setR2(final float r2) {
            this.r2 = r2;
            this.resetAttribute();
        }
        
        public float getAngle() {
            return this.angle;
        }
        
        public void setAngle(final float angle) {
            this.angle = angle;
            this.resetAttribute();
        }
        
        public boolean getSweepFlag() {
            return this.sweepFlag;
        }
        
        public void setSweepFlag(final boolean sweepFlag) {
            this.sweepFlag = sweepFlag;
            this.resetAttribute();
        }
        
        public boolean getLargeArcFlag() {
            return this.largeArcFlag;
        }
        
        public void setLargeArcFlag(final boolean largeArcFlag) {
            this.largeArcFlag = largeArcFlag;
            this.resetAttribute();
        }
        
        protected String getStringValue() {
            return this.letter + ' ' + Float.toString(this.r1) + ' ' + Float.toString(this.r2) + ' ' + Float.toString(this.angle) + ' ' + (this.largeArcFlag ? "1" : "0") + ' ' + (this.sweepFlag ? "1" : "0") + ' ' + Float.toString(this.x) + ' ' + Float.toString(this.y);
        }
    }
}
