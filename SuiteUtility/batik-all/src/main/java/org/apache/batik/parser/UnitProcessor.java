// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.parser;

import org.w3c.dom.Element;

public abstract class UnitProcessor
{
    public static final short HORIZONTAL_LENGTH = 2;
    public static final short VERTICAL_LENGTH = 1;
    public static final short OTHER_LENGTH = 0;
    static final double SQRT2;
    
    protected UnitProcessor() {
    }
    
    public static float svgToObjectBoundingBox(final String s, final String s2, final short n, final Context context) throws ParseException {
        final LengthParser lengthParser = new LengthParser();
        final UnitResolver lengthHandler = new UnitResolver();
        lengthParser.setLengthHandler(lengthHandler);
        lengthParser.parse(s);
        return svgToObjectBoundingBox(lengthHandler.value, lengthHandler.unit, n, context);
    }
    
    public static float svgToObjectBoundingBox(final float n, final short n2, final short n3, final Context context) {
        switch (n2) {
            case 1: {
                return n;
            }
            case 2: {
                return n / 100.0f;
            }
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10: {
                return svgToUserSpace(n, n2, n3, context);
            }
            default: {
                throw new IllegalArgumentException("Length has unknown type");
            }
        }
    }
    
    public static float svgToUserSpace(final String s, final String s2, final short n, final Context context) throws ParseException {
        final LengthParser lengthParser = new LengthParser();
        final UnitResolver lengthHandler = new UnitResolver();
        lengthParser.setLengthHandler(lengthHandler);
        lengthParser.parse(s);
        return svgToUserSpace(lengthHandler.value, lengthHandler.unit, n, context);
    }
    
    public static float svgToUserSpace(final float n, final short n2, final short n3, final Context context) {
        switch (n2) {
            case 1:
            case 5: {
                return n;
            }
            case 7: {
                return n / context.getPixelUnitToMillimeter();
            }
            case 6: {
                return n * 10.0f / context.getPixelUnitToMillimeter();
            }
            case 8: {
                return n * 25.4f / context.getPixelUnitToMillimeter();
            }
            case 9: {
                return n * 25.4f / (72.0f * context.getPixelUnitToMillimeter());
            }
            case 10: {
                return n * 25.4f / (6.0f * context.getPixelUnitToMillimeter());
            }
            case 3: {
                return emsToPixels(n, n3, context);
            }
            case 4: {
                return exsToPixels(n, n3, context);
            }
            case 2: {
                return percentagesToPixels(n, n3, context);
            }
            default: {
                throw new IllegalArgumentException("Length has unknown type");
            }
        }
    }
    
    public static float userSpaceToSVG(final float n, final short n2, final short n3, final Context context) {
        switch (n2) {
            case 1:
            case 5: {
                return n;
            }
            case 7: {
                return n * context.getPixelUnitToMillimeter();
            }
            case 6: {
                return n * context.getPixelUnitToMillimeter() / 10.0f;
            }
            case 8: {
                return n * context.getPixelUnitToMillimeter() / 25.4f;
            }
            case 9: {
                return n * (72.0f * context.getPixelUnitToMillimeter()) / 25.4f;
            }
            case 10: {
                return n * (6.0f * context.getPixelUnitToMillimeter()) / 25.4f;
            }
            case 3: {
                return pixelsToEms(n, n3, context);
            }
            case 4: {
                return pixelsToExs(n, n3, context);
            }
            case 2: {
                return pixelsToPercentages(n, n3, context);
            }
            default: {
                throw new IllegalArgumentException("Length has unknown type");
            }
        }
    }
    
    protected static float percentagesToPixels(final float n, final short n2, final Context context) {
        if (n2 == 2) {
            return context.getViewportWidth() * n / 100.0f;
        }
        if (n2 == 1) {
            return context.getViewportHeight() * n / 100.0f;
        }
        final double n3 = context.getViewportWidth();
        final double n4 = context.getViewportHeight();
        return (float)(Math.sqrt(n3 * n3 + n4 * n4) / UnitProcessor.SQRT2 * n / 100.0);
    }
    
    protected static float pixelsToPercentages(final float n, final short n2, final Context context) {
        if (n2 == 2) {
            return n * 100.0f / context.getViewportWidth();
        }
        if (n2 == 1) {
            return n * 100.0f / context.getViewportHeight();
        }
        final double n3 = context.getViewportWidth();
        final double n4 = context.getViewportHeight();
        return (float)(n * 100.0 / (Math.sqrt(n3 * n3 + n4 * n4) / UnitProcessor.SQRT2));
    }
    
    protected static float pixelsToEms(final float n, final short n2, final Context context) {
        return n / context.getFontSize();
    }
    
    protected static float emsToPixels(final float n, final short n2, final Context context) {
        return n * context.getFontSize();
    }
    
    protected static float pixelsToExs(final float n, final short n2, final Context context) {
        return n / context.getXHeight() / context.getFontSize();
    }
    
    protected static float exsToPixels(final float n, final short n2, final Context context) {
        return n * context.getXHeight() * context.getFontSize();
    }
    
    static {
        SQRT2 = Math.sqrt(2.0);
    }
    
    public interface Context
    {
        Element getElement();
        
        float getPixelUnitToMillimeter();
        
        float getPixelToMM();
        
        float getFontSize();
        
        float getXHeight();
        
        float getViewportWidth();
        
        float getViewportHeight();
    }
    
    public static class UnitResolver implements LengthHandler
    {
        public float value;
        public short unit;
        
        public UnitResolver() {
            this.unit = 1;
        }
        
        public void startLength() throws ParseException {
        }
        
        public void lengthValue(final float value) throws ParseException {
            this.value = value;
        }
        
        public void em() throws ParseException {
            this.unit = 3;
        }
        
        public void ex() throws ParseException {
            this.unit = 4;
        }
        
        public void in() throws ParseException {
            this.unit = 8;
        }
        
        public void cm() throws ParseException {
            this.unit = 6;
        }
        
        public void mm() throws ParseException {
            this.unit = 7;
        }
        
        public void pc() throws ParseException {
            this.unit = 10;
        }
        
        public void pt() throws ParseException {
            this.unit = 9;
        }
        
        public void px() throws ParseException {
            this.unit = 5;
        }
        
        public void percentage() throws ParseException {
            this.unit = 2;
        }
        
        public void endLength() throws ParseException {
        }
    }
}
