// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.parser;

import java.awt.geom.Point2D;
import java.io.IOException;
import java.awt.Shape;
import java.io.Reader;
import org.apache.batik.ext.awt.geom.ExtendedGeneralPath;

public class AWTPathProducer implements PathHandler, ShapeProducer
{
    protected ExtendedGeneralPath path;
    protected float currentX;
    protected float currentY;
    protected float xCenter;
    protected float yCenter;
    protected int windingRule;
    
    public static Shape createShape(final Reader reader, final int windingRule) throws IOException, ParseException {
        final PathParser pathParser = new PathParser();
        final AWTPathProducer pathHandler = new AWTPathProducer();
        pathHandler.setWindingRule(windingRule);
        pathParser.setPathHandler(pathHandler);
        pathParser.parse(reader);
        return pathHandler.getShape();
    }
    
    public void setWindingRule(final int windingRule) {
        this.windingRule = windingRule;
    }
    
    public int getWindingRule() {
        return this.windingRule;
    }
    
    public Shape getShape() {
        return this.path;
    }
    
    public void startPath() throws ParseException {
        this.currentX = 0.0f;
        this.currentY = 0.0f;
        this.xCenter = 0.0f;
        this.yCenter = 0.0f;
        this.path = new ExtendedGeneralPath(this.windingRule);
    }
    
    public void endPath() throws ParseException {
    }
    
    public void movetoRel(final float n, final float n2) throws ParseException {
        final ExtendedGeneralPath path = this.path;
        final float n3 = this.currentX + n;
        this.currentX = n3;
        this.xCenter = n3;
        final float n4 = this.currentY + n2;
        this.currentY = n4;
        path.moveTo(n3, this.yCenter = n4);
    }
    
    public void movetoAbs(final float n, final float n2) throws ParseException {
        final ExtendedGeneralPath path = this.path;
        this.currentX = n;
        this.xCenter = n;
        this.currentY = n2;
        path.moveTo(n, this.yCenter = n2);
    }
    
    public void closePath() throws ParseException {
        this.path.closePath();
        final Point2D currentPoint = this.path.getCurrentPoint();
        this.currentX = (float)currentPoint.getX();
        this.currentY = (float)currentPoint.getY();
    }
    
    public void linetoRel(final float n, final float n2) throws ParseException {
        final ExtendedGeneralPath path = this.path;
        final float n3 = this.currentX + n;
        this.currentX = n3;
        this.xCenter = n3;
        final float n4 = this.currentY + n2;
        this.currentY = n4;
        path.lineTo(n3, this.yCenter = n4);
    }
    
    public void linetoAbs(final float n, final float n2) throws ParseException {
        final ExtendedGeneralPath path = this.path;
        this.currentX = n;
        this.xCenter = n;
        this.currentY = n2;
        path.lineTo(n, this.yCenter = n2);
    }
    
    public void linetoHorizontalRel(final float n) throws ParseException {
        final ExtendedGeneralPath path = this.path;
        final float n2 = this.currentX + n;
        this.currentX = n2;
        path.lineTo(this.xCenter = n2, this.yCenter = this.currentY);
    }
    
    public void linetoHorizontalAbs(final float n) throws ParseException {
        final ExtendedGeneralPath path = this.path;
        this.currentX = n;
        path.lineTo(this.xCenter = n, this.yCenter = this.currentY);
    }
    
    public void linetoVerticalRel(final float n) throws ParseException {
        final ExtendedGeneralPath path = this.path;
        final float currentX = this.currentX;
        this.xCenter = currentX;
        final float n2 = this.currentY + n;
        this.currentY = n2;
        path.lineTo(currentX, this.yCenter = n2);
    }
    
    public void linetoVerticalAbs(final float n) throws ParseException {
        final ExtendedGeneralPath path = this.path;
        final float currentX = this.currentX;
        this.xCenter = currentX;
        this.currentY = n;
        path.lineTo(currentX, this.yCenter = n);
    }
    
    public void curvetoCubicRel(final float n, final float n2, final float n3, final float n4, final float n5, final float n6) throws ParseException {
        this.path.curveTo(this.currentX + n, this.currentY + n2, this.xCenter = this.currentX + n3, this.yCenter = this.currentY + n4, this.currentX += n5, this.currentY += n6);
    }
    
    public void curvetoCubicAbs(final float n, final float n2, final float xCenter, final float yCenter, final float currentX, final float currentY) throws ParseException {
        this.path.curveTo(n, n2, this.xCenter = xCenter, this.yCenter = yCenter, this.currentX = currentX, this.currentY = currentY);
    }
    
    public void curvetoCubicSmoothRel(final float n, final float n2, final float n3, final float n4) throws ParseException {
        this.path.curveTo(this.currentX * 2.0f - this.xCenter, this.currentY * 2.0f - this.yCenter, this.xCenter = this.currentX + n, this.yCenter = this.currentY + n2, this.currentX += n3, this.currentY += n4);
    }
    
    public void curvetoCubicSmoothAbs(final float xCenter, final float yCenter, final float currentX, final float currentY) throws ParseException {
        this.path.curveTo(this.currentX * 2.0f - this.xCenter, this.currentY * 2.0f - this.yCenter, this.xCenter = xCenter, this.yCenter = yCenter, this.currentX = currentX, this.currentY = currentY);
    }
    
    public void curvetoQuadraticRel(final float n, final float n2, final float n3, final float n4) throws ParseException {
        this.path.quadTo(this.xCenter = this.currentX + n, this.yCenter = this.currentY + n2, this.currentX += n3, this.currentY += n4);
    }
    
    public void curvetoQuadraticAbs(final float xCenter, final float yCenter, final float currentX, final float currentY) throws ParseException {
        this.path.quadTo(this.xCenter = xCenter, this.yCenter = yCenter, this.currentX = currentX, this.currentY = currentY);
    }
    
    public void curvetoQuadraticSmoothRel(final float n, final float n2) throws ParseException {
        this.path.quadTo(this.xCenter = this.currentX * 2.0f - this.xCenter, this.yCenter = this.currentY * 2.0f - this.yCenter, this.currentX += n, this.currentY += n2);
    }
    
    public void curvetoQuadraticSmoothAbs(final float currentX, final float currentY) throws ParseException {
        this.path.quadTo(this.xCenter = this.currentX * 2.0f - this.xCenter, this.yCenter = this.currentY * 2.0f - this.yCenter, this.currentX = currentX, this.currentY = currentY);
    }
    
    public void arcRel(final float n, final float n2, final float n3, final boolean b, final boolean b2, final float n4, final float n5) throws ParseException {
        final ExtendedGeneralPath path = this.path;
        final float n6 = this.currentX + n4;
        this.currentX = n6;
        this.xCenter = n6;
        final float n7 = this.currentY + n5;
        this.currentY = n7;
        path.arcTo(n, n2, n3, b, b2, n6, this.yCenter = n7);
    }
    
    public void arcAbs(final float n, final float n2, final float n3, final boolean b, final boolean b2, final float n4, final float n5) throws ParseException {
        final ExtendedGeneralPath path = this.path;
        this.currentX = n4;
        this.xCenter = n4;
        this.currentY = n5;
        path.arcTo(n, n2, n3, b, b2, n4, this.yCenter = n5);
    }
}
