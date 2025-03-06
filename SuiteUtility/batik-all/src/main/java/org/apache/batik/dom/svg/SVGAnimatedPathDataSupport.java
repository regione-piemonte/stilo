// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.dom.svg;

import org.w3c.dom.svg.SVGPathSeg;
import org.w3c.dom.svg.SVGPathSegCurvetoQuadraticSmoothRel;
import org.w3c.dom.svg.SVGPathSegCurvetoQuadraticSmoothAbs;
import org.w3c.dom.svg.SVGPathSegCurvetoCubicSmoothRel;
import org.w3c.dom.svg.SVGPathSegCurvetoCubicSmoothAbs;
import org.w3c.dom.svg.SVGPathSegLinetoVerticalRel;
import org.w3c.dom.svg.SVGPathSegLinetoVerticalAbs;
import org.w3c.dom.svg.SVGPathSegLinetoHorizontalRel;
import org.w3c.dom.svg.SVGPathSegLinetoHorizontalAbs;
import org.w3c.dom.svg.SVGPathSegArcRel;
import org.w3c.dom.svg.SVGPathSegArcAbs;
import org.w3c.dom.svg.SVGPathSegCurvetoQuadraticRel;
import org.w3c.dom.svg.SVGPathSegCurvetoQuadraticAbs;
import org.w3c.dom.svg.SVGPathSegCurvetoCubicRel;
import org.w3c.dom.svg.SVGPathSegCurvetoCubicAbs;
import org.w3c.dom.svg.SVGPathSegLinetoRel;
import org.w3c.dom.svg.SVGPathSegLinetoAbs;
import org.w3c.dom.svg.SVGPathSegMovetoRel;
import org.w3c.dom.svg.SVGPathSegMovetoAbs;
import org.apache.batik.parser.PathHandler;
import org.w3c.dom.svg.SVGPathSegList;

public abstract class SVGAnimatedPathDataSupport
{
    public static void handlePathSegList(final SVGPathSegList list, final PathHandler pathHandler) {
        final int numberOfItems = list.getNumberOfItems();
        pathHandler.startPath();
        for (int i = 0; i < numberOfItems; ++i) {
            final SVGPathSeg item = list.getItem(i);
            switch (item.getPathSegType()) {
                case 1: {
                    pathHandler.closePath();
                    break;
                }
                case 2: {
                    final SVGPathSegMovetoAbs svgPathSegMovetoAbs = (SVGPathSegMovetoAbs)item;
                    pathHandler.movetoAbs(svgPathSegMovetoAbs.getX(), svgPathSegMovetoAbs.getY());
                    break;
                }
                case 3: {
                    final SVGPathSegMovetoRel svgPathSegMovetoRel = (SVGPathSegMovetoRel)item;
                    pathHandler.movetoRel(svgPathSegMovetoRel.getX(), svgPathSegMovetoRel.getY());
                    break;
                }
                case 4: {
                    final SVGPathSegLinetoAbs svgPathSegLinetoAbs = (SVGPathSegLinetoAbs)item;
                    pathHandler.linetoAbs(svgPathSegLinetoAbs.getX(), svgPathSegLinetoAbs.getY());
                    break;
                }
                case 5: {
                    final SVGPathSegLinetoRel svgPathSegLinetoRel = (SVGPathSegLinetoRel)item;
                    pathHandler.linetoRel(svgPathSegLinetoRel.getX(), svgPathSegLinetoRel.getY());
                    break;
                }
                case 6: {
                    final SVGPathSegCurvetoCubicAbs svgPathSegCurvetoCubicAbs = (SVGPathSegCurvetoCubicAbs)item;
                    pathHandler.curvetoCubicAbs(svgPathSegCurvetoCubicAbs.getX1(), svgPathSegCurvetoCubicAbs.getY1(), svgPathSegCurvetoCubicAbs.getX2(), svgPathSegCurvetoCubicAbs.getY2(), svgPathSegCurvetoCubicAbs.getX(), svgPathSegCurvetoCubicAbs.getY());
                    break;
                }
                case 7: {
                    final SVGPathSegCurvetoCubicRel svgPathSegCurvetoCubicRel = (SVGPathSegCurvetoCubicRel)item;
                    pathHandler.curvetoCubicRel(svgPathSegCurvetoCubicRel.getX1(), svgPathSegCurvetoCubicRel.getY1(), svgPathSegCurvetoCubicRel.getX2(), svgPathSegCurvetoCubicRel.getY2(), svgPathSegCurvetoCubicRel.getX(), svgPathSegCurvetoCubicRel.getY());
                    break;
                }
                case 8: {
                    final SVGPathSegCurvetoQuadraticAbs svgPathSegCurvetoQuadraticAbs = (SVGPathSegCurvetoQuadraticAbs)item;
                    pathHandler.curvetoQuadraticAbs(svgPathSegCurvetoQuadraticAbs.getX1(), svgPathSegCurvetoQuadraticAbs.getY1(), svgPathSegCurvetoQuadraticAbs.getX(), svgPathSegCurvetoQuadraticAbs.getY());
                    break;
                }
                case 9: {
                    final SVGPathSegCurvetoQuadraticRel svgPathSegCurvetoQuadraticRel = (SVGPathSegCurvetoQuadraticRel)item;
                    pathHandler.curvetoQuadraticRel(svgPathSegCurvetoQuadraticRel.getX1(), svgPathSegCurvetoQuadraticRel.getY1(), svgPathSegCurvetoQuadraticRel.getX(), svgPathSegCurvetoQuadraticRel.getY());
                    break;
                }
                case 10: {
                    final SVGPathSegArcAbs svgPathSegArcAbs = (SVGPathSegArcAbs)item;
                    pathHandler.arcAbs(svgPathSegArcAbs.getR1(), svgPathSegArcAbs.getR2(), svgPathSegArcAbs.getAngle(), svgPathSegArcAbs.getLargeArcFlag(), svgPathSegArcAbs.getSweepFlag(), svgPathSegArcAbs.getX(), svgPathSegArcAbs.getY());
                    break;
                }
                case 11: {
                    final SVGPathSegArcRel svgPathSegArcRel = (SVGPathSegArcRel)item;
                    pathHandler.arcRel(svgPathSegArcRel.getR1(), svgPathSegArcRel.getR2(), svgPathSegArcRel.getAngle(), svgPathSegArcRel.getLargeArcFlag(), svgPathSegArcRel.getSweepFlag(), svgPathSegArcRel.getX(), svgPathSegArcRel.getY());
                    break;
                }
                case 12: {
                    pathHandler.linetoHorizontalAbs(((SVGPathSegLinetoHorizontalAbs)item).getX());
                    break;
                }
                case 13: {
                    pathHandler.linetoHorizontalRel(((SVGPathSegLinetoHorizontalRel)item).getX());
                    break;
                }
                case 14: {
                    pathHandler.linetoVerticalAbs(((SVGPathSegLinetoVerticalAbs)item).getY());
                    break;
                }
                case 15: {
                    pathHandler.linetoVerticalRel(((SVGPathSegLinetoVerticalRel)item).getY());
                    break;
                }
                case 16: {
                    final SVGPathSegCurvetoCubicSmoothAbs svgPathSegCurvetoCubicSmoothAbs = (SVGPathSegCurvetoCubicSmoothAbs)item;
                    pathHandler.curvetoCubicSmoothAbs(svgPathSegCurvetoCubicSmoothAbs.getX2(), svgPathSegCurvetoCubicSmoothAbs.getY2(), svgPathSegCurvetoCubicSmoothAbs.getX(), svgPathSegCurvetoCubicSmoothAbs.getY());
                    break;
                }
                case 17: {
                    final SVGPathSegCurvetoCubicSmoothRel svgPathSegCurvetoCubicSmoothRel = (SVGPathSegCurvetoCubicSmoothRel)item;
                    pathHandler.curvetoCubicSmoothRel(svgPathSegCurvetoCubicSmoothRel.getX2(), svgPathSegCurvetoCubicSmoothRel.getY2(), svgPathSegCurvetoCubicSmoothRel.getX(), svgPathSegCurvetoCubicSmoothRel.getY());
                    break;
                }
                case 18: {
                    final SVGPathSegCurvetoQuadraticSmoothAbs svgPathSegCurvetoQuadraticSmoothAbs = (SVGPathSegCurvetoQuadraticSmoothAbs)item;
                    pathHandler.curvetoQuadraticSmoothAbs(svgPathSegCurvetoQuadraticSmoothAbs.getX(), svgPathSegCurvetoQuadraticSmoothAbs.getY());
                    break;
                }
                case 19: {
                    final SVGPathSegCurvetoQuadraticSmoothRel svgPathSegCurvetoQuadraticSmoothRel = (SVGPathSegCurvetoQuadraticSmoothRel)item;
                    pathHandler.curvetoQuadraticSmoothRel(svgPathSegCurvetoQuadraticSmoothRel.getX(), svgPathSegCurvetoQuadraticSmoothRel.getY());
                    break;
                }
            }
        }
        pathHandler.endPath();
    }
}
