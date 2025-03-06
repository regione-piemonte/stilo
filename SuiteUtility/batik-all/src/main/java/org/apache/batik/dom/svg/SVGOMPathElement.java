// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.dom.svg;

import org.w3c.dom.Node;
import org.w3c.dom.svg.SVGPathSegArcRel;
import org.w3c.dom.svg.SVGPathSegArcAbs;
import org.w3c.dom.svg.SVGPathSegCurvetoQuadraticSmoothRel;
import org.w3c.dom.svg.SVGPathSegCurvetoQuadraticSmoothAbs;
import org.w3c.dom.svg.SVGPathSegCurvetoCubicSmoothRel;
import org.w3c.dom.svg.SVGPathSegCurvetoCubicSmoothAbs;
import org.w3c.dom.svg.SVGPathSegCurvetoQuadraticRel;
import org.w3c.dom.svg.SVGPathSegCurvetoQuadraticAbs;
import org.w3c.dom.svg.SVGPathSegCurvetoCubicRel;
import org.w3c.dom.svg.SVGPathSegCurvetoCubicAbs;
import org.w3c.dom.svg.SVGPathSegLinetoVerticalRel;
import org.w3c.dom.svg.SVGPathSegLinetoVerticalAbs;
import org.w3c.dom.svg.SVGPathSegLinetoHorizontalRel;
import org.w3c.dom.svg.SVGPathSegLinetoHorizontalAbs;
import org.w3c.dom.svg.SVGPathSegLinetoRel;
import org.w3c.dom.svg.SVGPathSegLinetoAbs;
import org.w3c.dom.svg.SVGPathSegMovetoRel;
import org.w3c.dom.svg.SVGPathSegMovetoAbs;
import org.w3c.dom.svg.SVGPathSegClosePath;
import org.w3c.dom.svg.SVGPathSegList;
import org.w3c.dom.svg.SVGPoint;
import org.w3c.dom.svg.SVGAnimatedNumber;
import org.apache.batik.dom.AbstractDocument;
import org.apache.batik.util.DoublyIndexedTable;
import org.w3c.dom.svg.SVGPathElement;

public class SVGOMPathElement extends SVGGraphicsElement implements SVGPathElement, SVGPathSegConstants
{
    protected static DoublyIndexedTable xmlTraitInformation;
    protected SVGOMAnimatedPathData d;
    
    protected SVGOMPathElement() {
    }
    
    public SVGOMPathElement(final String s, final AbstractDocument abstractDocument) {
        super(s, abstractDocument);
        this.initializeLiveAttributes();
    }
    
    protected void initializeAllLiveAttributes() {
        super.initializeAllLiveAttributes();
        this.initializeLiveAttributes();
    }
    
    private void initializeLiveAttributes() {
        this.d = this.createLiveAnimatedPathData(null, "d", "");
    }
    
    public String getLocalName() {
        return "path";
    }
    
    public SVGAnimatedNumber getPathLength() {
        throw new UnsupportedOperationException("SVGPathElement.getPathLength is not implemented");
    }
    
    public float getTotalLength() {
        return SVGPathSupport.getTotalLength(this);
    }
    
    public SVGPoint getPointAtLength(final float n) {
        return SVGPathSupport.getPointAtLength(this, n);
    }
    
    public int getPathSegAtLength(final float n) {
        return SVGPathSupport.getPathSegAtLength(this, n);
    }
    
    public SVGOMAnimatedPathData getAnimatedPathData() {
        return this.d;
    }
    
    public SVGPathSegList getPathSegList() {
        return this.d.getPathSegList();
    }
    
    public SVGPathSegList getNormalizedPathSegList() {
        return this.d.getNormalizedPathSegList();
    }
    
    public SVGPathSegList getAnimatedPathSegList() {
        return this.d.getAnimatedPathSegList();
    }
    
    public SVGPathSegList getAnimatedNormalizedPathSegList() {
        return this.d.getAnimatedNormalizedPathSegList();
    }
    
    public SVGPathSegClosePath createSVGPathSegClosePath() {
        return (SVGPathSegClosePath)new SVGPathSegClosePath() {
            public short getPathSegType() {
                return 1;
            }
            
            public String getPathSegTypeAsLetter() {
                return "z";
            }
        };
    }
    
    public SVGPathSegMovetoAbs createSVGPathSegMovetoAbs(final float n, final float n2) {
        return (SVGPathSegMovetoAbs)new SVGPathSegMovetoAbs() {
            protected float x = n;
            protected float y = n2;
            
            public short getPathSegType() {
                return 2;
            }
            
            public String getPathSegTypeAsLetter() {
                return "M";
            }
            
            public float getX() {
                return this.x;
            }
            
            public void setX(final float x) {
                this.x = x;
            }
            
            public float getY() {
                return this.y;
            }
            
            public void setY(final float y) {
                this.y = y;
            }
        };
    }
    
    public SVGPathSegMovetoRel createSVGPathSegMovetoRel(final float n, final float n2) {
        return (SVGPathSegMovetoRel)new SVGPathSegMovetoRel() {
            protected float x = n;
            protected float y = n2;
            
            public short getPathSegType() {
                return 3;
            }
            
            public String getPathSegTypeAsLetter() {
                return "m";
            }
            
            public float getX() {
                return this.x;
            }
            
            public void setX(final float x) {
                this.x = x;
            }
            
            public float getY() {
                return this.y;
            }
            
            public void setY(final float y) {
                this.y = y;
            }
        };
    }
    
    public SVGPathSegLinetoAbs createSVGPathSegLinetoAbs(final float n, final float n2) {
        return (SVGPathSegLinetoAbs)new SVGPathSegLinetoAbs() {
            protected float x = n;
            protected float y = n2;
            
            public short getPathSegType() {
                return 4;
            }
            
            public String getPathSegTypeAsLetter() {
                return "L";
            }
            
            public float getX() {
                return this.x;
            }
            
            public void setX(final float x) {
                this.x = x;
            }
            
            public float getY() {
                return this.y;
            }
            
            public void setY(final float y) {
                this.y = y;
            }
        };
    }
    
    public SVGPathSegLinetoRel createSVGPathSegLinetoRel(final float n, final float n2) {
        return (SVGPathSegLinetoRel)new SVGPathSegLinetoRel() {
            protected float x = n;
            protected float y = n2;
            
            public short getPathSegType() {
                return 5;
            }
            
            public String getPathSegTypeAsLetter() {
                return "l";
            }
            
            public float getX() {
                return this.x;
            }
            
            public void setX(final float x) {
                this.x = x;
            }
            
            public float getY() {
                return this.y;
            }
            
            public void setY(final float y) {
                this.y = y;
            }
        };
    }
    
    public SVGPathSegLinetoHorizontalAbs createSVGPathSegLinetoHorizontalAbs(final float n) {
        return (SVGPathSegLinetoHorizontalAbs)new SVGPathSegLinetoHorizontalAbs() {
            protected float x = n;
            
            public short getPathSegType() {
                return 12;
            }
            
            public String getPathSegTypeAsLetter() {
                return "H";
            }
            
            public float getX() {
                return this.x;
            }
            
            public void setX(final float x) {
                this.x = x;
            }
        };
    }
    
    public SVGPathSegLinetoHorizontalRel createSVGPathSegLinetoHorizontalRel(final float n) {
        return (SVGPathSegLinetoHorizontalRel)new SVGPathSegLinetoHorizontalRel() {
            protected float x = n;
            
            public short getPathSegType() {
                return 13;
            }
            
            public String getPathSegTypeAsLetter() {
                return "h";
            }
            
            public float getX() {
                return this.x;
            }
            
            public void setX(final float x) {
                this.x = x;
            }
        };
    }
    
    public SVGPathSegLinetoVerticalAbs createSVGPathSegLinetoVerticalAbs(final float n) {
        return (SVGPathSegLinetoVerticalAbs)new SVGPathSegLinetoVerticalAbs() {
            protected float y = n;
            
            public short getPathSegType() {
                return 14;
            }
            
            public String getPathSegTypeAsLetter() {
                return "V";
            }
            
            public float getY() {
                return this.y;
            }
            
            public void setY(final float y) {
                this.y = y;
            }
        };
    }
    
    public SVGPathSegLinetoVerticalRel createSVGPathSegLinetoVerticalRel(final float n) {
        return (SVGPathSegLinetoVerticalRel)new SVGPathSegLinetoVerticalRel() {
            protected float y = n;
            
            public short getPathSegType() {
                return 15;
            }
            
            public String getPathSegTypeAsLetter() {
                return "v";
            }
            
            public float getY() {
                return this.y;
            }
            
            public void setY(final float y) {
                this.y = y;
            }
        };
    }
    
    public SVGPathSegCurvetoCubicAbs createSVGPathSegCurvetoCubicAbs(final float n, final float n2, final float n3, final float n4, final float n5, final float n6) {
        return (SVGPathSegCurvetoCubicAbs)new SVGPathSegCurvetoCubicAbs() {
            protected float x = n;
            protected float y = n2;
            protected float x1 = n3;
            protected float y1 = n4;
            protected float x2 = n5;
            protected float y2 = n6;
            
            public short getPathSegType() {
                return 6;
            }
            
            public String getPathSegTypeAsLetter() {
                return "C";
            }
            
            public float getX() {
                return this.x;
            }
            
            public void setX(final float x) {
                this.x = x;
            }
            
            public float getY() {
                return this.y;
            }
            
            public void setY(final float y) {
                this.y = y;
            }
            
            public float getX1() {
                return this.x1;
            }
            
            public void setX1(final float x1) {
                this.x1 = x1;
            }
            
            public float getY1() {
                return this.y1;
            }
            
            public void setY1(final float y1) {
                this.y1 = y1;
            }
            
            public float getX2() {
                return this.x2;
            }
            
            public void setX2(final float x2) {
                this.x2 = x2;
            }
            
            public float getY2() {
                return this.y2;
            }
            
            public void setY2(final float y2) {
                this.y2 = y2;
            }
        };
    }
    
    public SVGPathSegCurvetoCubicRel createSVGPathSegCurvetoCubicRel(final float n, final float n2, final float n3, final float n4, final float n5, final float n6) {
        return (SVGPathSegCurvetoCubicRel)new SVGPathSegCurvetoCubicRel() {
            protected float x = n;
            protected float y = n2;
            protected float x1 = n3;
            protected float y1 = n4;
            protected float x2 = n5;
            protected float y2 = n6;
            
            public short getPathSegType() {
                return 7;
            }
            
            public String getPathSegTypeAsLetter() {
                return "c";
            }
            
            public float getX() {
                return this.x;
            }
            
            public void setX(final float x) {
                this.x = x;
            }
            
            public float getY() {
                return this.y;
            }
            
            public void setY(final float y) {
                this.y = y;
            }
            
            public float getX1() {
                return this.x1;
            }
            
            public void setX1(final float x1) {
                this.x1 = x1;
            }
            
            public float getY1() {
                return this.y1;
            }
            
            public void setY1(final float y1) {
                this.y1 = y1;
            }
            
            public float getX2() {
                return this.x2;
            }
            
            public void setX2(final float x2) {
                this.x2 = x2;
            }
            
            public float getY2() {
                return this.y2;
            }
            
            public void setY2(final float y2) {
                this.y2 = y2;
            }
        };
    }
    
    public SVGPathSegCurvetoQuadraticAbs createSVGPathSegCurvetoQuadraticAbs(final float n, final float n2, final float n3, final float n4) {
        return (SVGPathSegCurvetoQuadraticAbs)new SVGPathSegCurvetoQuadraticAbs() {
            protected float x = n;
            protected float y = n2;
            protected float x1 = n3;
            protected float y1 = n4;
            
            public short getPathSegType() {
                return 8;
            }
            
            public String getPathSegTypeAsLetter() {
                return "Q";
            }
            
            public float getX() {
                return this.x;
            }
            
            public void setX(final float x) {
                this.x = x;
            }
            
            public float getY() {
                return this.y;
            }
            
            public void setY(final float y) {
                this.y = y;
            }
            
            public float getX1() {
                return this.x1;
            }
            
            public void setX1(final float x1) {
                this.x1 = x1;
            }
            
            public float getY1() {
                return this.y1;
            }
            
            public void setY1(final float y1) {
                this.y1 = y1;
            }
        };
    }
    
    public SVGPathSegCurvetoQuadraticRel createSVGPathSegCurvetoQuadraticRel(final float n, final float n2, final float n3, final float n4) {
        return (SVGPathSegCurvetoQuadraticRel)new SVGPathSegCurvetoQuadraticRel() {
            protected float x = n;
            protected float y = n2;
            protected float x1 = n3;
            protected float y1 = n4;
            
            public short getPathSegType() {
                return 9;
            }
            
            public String getPathSegTypeAsLetter() {
                return "q";
            }
            
            public float getX() {
                return this.x;
            }
            
            public void setX(final float x) {
                this.x = x;
            }
            
            public float getY() {
                return this.y;
            }
            
            public void setY(final float y) {
                this.y = y;
            }
            
            public float getX1() {
                return this.x1;
            }
            
            public void setX1(final float x1) {
                this.x1 = x1;
            }
            
            public float getY1() {
                return this.y1;
            }
            
            public void setY1(final float y1) {
                this.y1 = y1;
            }
        };
    }
    
    public SVGPathSegCurvetoCubicSmoothAbs createSVGPathSegCurvetoCubicSmoothAbs(final float n, final float n2, final float n3, final float n4) {
        return (SVGPathSegCurvetoCubicSmoothAbs)new SVGPathSegCurvetoCubicSmoothAbs() {
            protected float x = n;
            protected float y = n2;
            protected float x2 = n3;
            protected float y2 = n4;
            
            public short getPathSegType() {
                return 16;
            }
            
            public String getPathSegTypeAsLetter() {
                return "S";
            }
            
            public float getX() {
                return this.x;
            }
            
            public void setX(final float x) {
                this.x = x;
            }
            
            public float getY() {
                return this.y;
            }
            
            public void setY(final float y) {
                this.y = y;
            }
            
            public float getX2() {
                return this.x2;
            }
            
            public void setX2(final float x2) {
                this.x2 = x2;
            }
            
            public float getY2() {
                return this.y2;
            }
            
            public void setY2(final float y2) {
                this.y2 = y2;
            }
        };
    }
    
    public SVGPathSegCurvetoCubicSmoothRel createSVGPathSegCurvetoCubicSmoothRel(final float n, final float n2, final float n3, final float n4) {
        return (SVGPathSegCurvetoCubicSmoothRel)new SVGPathSegCurvetoCubicSmoothRel() {
            protected float x = n;
            protected float y = n2;
            protected float x2 = n3;
            protected float y2 = n4;
            
            public short getPathSegType() {
                return 17;
            }
            
            public String getPathSegTypeAsLetter() {
                return "s";
            }
            
            public float getX() {
                return this.x;
            }
            
            public void setX(final float x) {
                this.x = x;
            }
            
            public float getY() {
                return this.y;
            }
            
            public void setY(final float y) {
                this.y = y;
            }
            
            public float getX2() {
                return this.x2;
            }
            
            public void setX2(final float x2) {
                this.x2 = x2;
            }
            
            public float getY2() {
                return this.y2;
            }
            
            public void setY2(final float y2) {
                this.y2 = y2;
            }
        };
    }
    
    public SVGPathSegCurvetoQuadraticSmoothAbs createSVGPathSegCurvetoQuadraticSmoothAbs(final float n, final float n2) {
        return (SVGPathSegCurvetoQuadraticSmoothAbs)new SVGPathSegCurvetoQuadraticSmoothAbs() {
            protected float x = n;
            protected float y = n2;
            
            public short getPathSegType() {
                return 18;
            }
            
            public String getPathSegTypeAsLetter() {
                return "T";
            }
            
            public float getX() {
                return this.x;
            }
            
            public void setX(final float x) {
                this.x = x;
            }
            
            public float getY() {
                return this.y;
            }
            
            public void setY(final float y) {
                this.y = y;
            }
        };
    }
    
    public SVGPathSegCurvetoQuadraticSmoothRel createSVGPathSegCurvetoQuadraticSmoothRel(final float n, final float n2) {
        return (SVGPathSegCurvetoQuadraticSmoothRel)new SVGPathSegCurvetoQuadraticSmoothRel() {
            protected float x = n;
            protected float y = n2;
            
            public short getPathSegType() {
                return 19;
            }
            
            public String getPathSegTypeAsLetter() {
                return "t";
            }
            
            public float getX() {
                return this.x;
            }
            
            public void setX(final float x) {
                this.x = x;
            }
            
            public float getY() {
                return this.y;
            }
            
            public void setY(final float y) {
                this.y = y;
            }
        };
    }
    
    public SVGPathSegArcAbs createSVGPathSegArcAbs(final float n, final float n2, final float n3, final float n4, final float n5, final boolean b, final boolean b2) {
        return (SVGPathSegArcAbs)new SVGPathSegArcAbs() {
            protected float x = n;
            protected float y = n2;
            protected float r1 = n3;
            protected float r2 = n4;
            protected float angle = n5;
            protected boolean largeArcFlag = b;
            protected boolean sweepFlag = b2;
            
            public short getPathSegType() {
                return 10;
            }
            
            public String getPathSegTypeAsLetter() {
                return "A";
            }
            
            public float getX() {
                return this.x;
            }
            
            public void setX(final float x) {
                this.x = x;
            }
            
            public float getY() {
                return this.y;
            }
            
            public void setY(final float y) {
                this.y = y;
            }
            
            public float getR1() {
                return this.r1;
            }
            
            public void setR1(final float r1) {
                this.r1 = r1;
            }
            
            public float getR2() {
                return this.r2;
            }
            
            public void setR2(final float r2) {
                this.r2 = r2;
            }
            
            public float getAngle() {
                return this.angle;
            }
            
            public void setAngle(final float angle) {
                this.angle = angle;
            }
            
            public boolean getLargeArcFlag() {
                return this.largeArcFlag;
            }
            
            public void setLargeArcFlag(final boolean largeArcFlag) {
                this.largeArcFlag = largeArcFlag;
            }
            
            public boolean getSweepFlag() {
                return this.sweepFlag;
            }
            
            public void setSweepFlag(final boolean sweepFlag) {
                this.sweepFlag = sweepFlag;
            }
        };
    }
    
    public SVGPathSegArcRel createSVGPathSegArcRel(final float n, final float n2, final float n3, final float n4, final float n5, final boolean b, final boolean b2) {
        return (SVGPathSegArcRel)new SVGPathSegArcRel() {
            protected float x = n;
            protected float y = n2;
            protected float r1 = n3;
            protected float r2 = n4;
            protected float angle = n5;
            protected boolean largeArcFlag = b;
            protected boolean sweepFlag = b2;
            
            public short getPathSegType() {
                return 11;
            }
            
            public String getPathSegTypeAsLetter() {
                return "a";
            }
            
            public float getX() {
                return this.x;
            }
            
            public void setX(final float x) {
                this.x = x;
            }
            
            public float getY() {
                return this.y;
            }
            
            public void setY(final float y) {
                this.y = y;
            }
            
            public float getR1() {
                return this.r1;
            }
            
            public void setR1(final float r1) {
                this.r1 = r1;
            }
            
            public float getR2() {
                return this.r2;
            }
            
            public void setR2(final float r2) {
                this.r2 = r2;
            }
            
            public float getAngle() {
                return this.angle;
            }
            
            public void setAngle(final float angle) {
                this.angle = angle;
            }
            
            public boolean getLargeArcFlag() {
                return this.largeArcFlag;
            }
            
            public void setLargeArcFlag(final boolean largeArcFlag) {
                this.largeArcFlag = largeArcFlag;
            }
            
            public boolean getSweepFlag() {
                return this.sweepFlag;
            }
            
            public void setSweepFlag(final boolean sweepFlag) {
                this.sweepFlag = sweepFlag;
            }
        };
    }
    
    protected Node newNode() {
        return new SVGOMPathElement();
    }
    
    protected DoublyIndexedTable getTraitInformationTable() {
        return SVGOMPathElement.xmlTraitInformation;
    }
    
    static {
        final DoublyIndexedTable xmlTraitInformation = new DoublyIndexedTable(SVGGraphicsElement.xmlTraitInformation);
        xmlTraitInformation.put(null, "d", new TraitInformation(true, 22));
        xmlTraitInformation.put(null, "pathLength", new TraitInformation(true, 2));
        SVGOMPathElement.xmlTraitInformation = xmlTraitInformation;
    }
}
