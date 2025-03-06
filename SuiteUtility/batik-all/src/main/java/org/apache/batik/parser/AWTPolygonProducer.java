// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.parser;

import java.io.IOException;
import java.awt.Shape;
import java.io.Reader;

public class AWTPolygonProducer extends AWTPolylineProducer
{
    public static Shape createShape(final Reader reader, final int windingRule) throws IOException, ParseException {
        final PointsParser pointsParser = new PointsParser();
        final AWTPolygonProducer pointsHandler = new AWTPolygonProducer();
        pointsHandler.setWindingRule(windingRule);
        pointsParser.setPointsHandler(pointsHandler);
        pointsParser.parse(reader);
        return pointsHandler.getShape();
    }
    
    public void endPoints() throws ParseException {
        this.path.closePath();
    }
}
