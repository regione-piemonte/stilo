// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.parser;

import java.io.IOException;
import java.awt.Shape;
import java.io.Reader;
import java.awt.geom.GeneralPath;

public class AWTPolylineProducer implements PointsHandler, ShapeProducer
{
    protected GeneralPath path;
    protected boolean newPath;
    protected int windingRule;
    
    public static Shape createShape(final Reader reader, final int windingRule) throws IOException, ParseException {
        final PointsParser pointsParser = new PointsParser();
        final AWTPolylineProducer pointsHandler = new AWTPolylineProducer();
        pointsHandler.setWindingRule(windingRule);
        pointsParser.setPointsHandler(pointsHandler);
        pointsParser.parse(reader);
        return pointsHandler.getShape();
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
    
    public void startPoints() throws ParseException {
        this.path = new GeneralPath(this.windingRule);
        this.newPath = true;
    }
    
    public void point(final float n, final float n2) throws ParseException {
        if (this.newPath) {
            this.newPath = false;
            this.path.moveTo(n, n2);
        }
        else {
            this.path.lineTo(n, n2);
        }
    }
    
    public void endPoints() throws ParseException {
    }
}
