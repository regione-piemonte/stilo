// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.parser;

import java.io.IOException;

public class PointsParser extends NumberParser
{
    protected PointsHandler pointsHandler;
    protected boolean eRead;
    
    public PointsParser() {
        this.pointsHandler = DefaultPointsHandler.INSTANCE;
    }
    
    public void setPointsHandler(final PointsHandler pointsHandler) {
        this.pointsHandler = pointsHandler;
    }
    
    public PointsHandler getPointsHandler() {
        return this.pointsHandler;
    }
    
    protected void doParse() throws ParseException, IOException {
        this.pointsHandler.startPoints();
        this.current = this.reader.read();
        this.skipSpaces();
        while (this.current != -1) {
            final float float1 = this.parseFloat();
            this.skipCommaSpaces();
            this.pointsHandler.point(float1, this.parseFloat());
            this.skipCommaSpaces();
        }
        this.pointsHandler.endPoints();
    }
}
