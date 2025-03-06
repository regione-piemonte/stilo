// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.parser;

import java.io.Reader;
import java.awt.geom.AffineTransform;

public class AWTTransformProducer implements TransformListHandler
{
    protected AffineTransform affineTransform;
    
    public static AffineTransform createAffineTransform(final Reader reader) throws ParseException {
        final TransformListParser transformListParser = new TransformListParser();
        final AWTTransformProducer transformListHandler = new AWTTransformProducer();
        transformListParser.setTransformListHandler(transformListHandler);
        transformListParser.parse(reader);
        return transformListHandler.getAffineTransform();
    }
    
    public static AffineTransform createAffineTransform(final String s) throws ParseException {
        final TransformListParser transformListParser = new TransformListParser();
        final AWTTransformProducer transformListHandler = new AWTTransformProducer();
        transformListParser.setTransformListHandler(transformListHandler);
        transformListParser.parse(s);
        return transformListHandler.getAffineTransform();
    }
    
    public AffineTransform getAffineTransform() {
        return this.affineTransform;
    }
    
    public void startTransformList() throws ParseException {
        this.affineTransform = new AffineTransform();
    }
    
    public void matrix(final float m00, final float m2, final float m3, final float m4, final float m5, final float m6) throws ParseException {
        this.affineTransform.concatenate(new AffineTransform(m00, m2, m3, m4, m5, m6));
    }
    
    public void rotate(final float n) throws ParseException {
        this.affineTransform.concatenate(AffineTransform.getRotateInstance(Math.toRadians(n)));
    }
    
    public void rotate(final float n, final float n2, final float n3) throws ParseException {
        this.affineTransform.concatenate(AffineTransform.getRotateInstance(Math.toRadians(n), n2, n3));
    }
    
    public void translate(final float n) throws ParseException {
        this.affineTransform.concatenate(AffineTransform.getTranslateInstance(n, 0.0));
    }
    
    public void translate(final float n, final float n2) throws ParseException {
        this.affineTransform.concatenate(AffineTransform.getTranslateInstance(n, n2));
    }
    
    public void scale(final float n) throws ParseException {
        this.affineTransform.concatenate(AffineTransform.getScaleInstance(n, n));
    }
    
    public void scale(final float n, final float n2) throws ParseException {
        this.affineTransform.concatenate(AffineTransform.getScaleInstance(n, n2));
    }
    
    public void skewX(final float n) throws ParseException {
        this.affineTransform.concatenate(AffineTransform.getShearInstance(Math.tan(Math.toRadians(n)), 0.0));
    }
    
    public void skewY(final float n) throws ParseException {
        this.affineTransform.concatenate(AffineTransform.getShearInstance(0.0, Math.tan(Math.toRadians(n))));
    }
    
    public void endTransformList() throws ParseException {
    }
}
