// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.dom.svg;

import org.apache.batik.parser.AngleHandler;
import org.apache.batik.parser.ParseException;
import org.apache.batik.parser.DefaultAngleHandler;
import org.apache.batik.parser.AngleParser;
import org.w3c.dom.DOMException;
import org.w3c.dom.svg.SVGAngle;

public class SVGOMAngle implements SVGAngle
{
    protected short unitType;
    protected float value;
    protected static final String[] UNITS;
    protected static double[][] K;
    
    public short getUnitType() {
        this.revalidate();
        return this.unitType;
    }
    
    public float getValue() {
        this.revalidate();
        return toUnit(this.unitType, this.value, (short)2);
    }
    
    public void setValue(final float value) throws DOMException {
        this.revalidate();
        this.unitType = 2;
        this.value = value;
        this.reset();
    }
    
    public float getValueInSpecifiedUnits() {
        this.revalidate();
        return this.value;
    }
    
    public void setValueInSpecifiedUnits(final float value) throws DOMException {
        this.revalidate();
        this.value = value;
        this.reset();
    }
    
    public String getValueAsString() {
        this.revalidate();
        return Float.toString(this.value) + SVGOMAngle.UNITS[this.unitType];
    }
    
    public void setValueAsString(final String s) throws DOMException {
        this.parse(s);
        this.reset();
    }
    
    public void newValueSpecifiedUnits(final short unitType, final float value) {
        this.unitType = unitType;
        this.value = value;
        this.reset();
    }
    
    public void convertToSpecifiedUnits(final short unitType) {
        this.value = toUnit(this.unitType, this.value, unitType);
        this.unitType = unitType;
    }
    
    protected void reset() {
    }
    
    protected void revalidate() {
    }
    
    protected void parse(final String s) {
        try {
            final AngleParser angleParser = new AngleParser();
            angleParser.setAngleHandler(new DefaultAngleHandler() {
                public void angleValue(final float value) throws ParseException {
                    SVGOMAngle.this.value = value;
                }
                
                public void deg() throws ParseException {
                    SVGOMAngle.this.unitType = 2;
                }
                
                public void rad() throws ParseException {
                    SVGOMAngle.this.unitType = 3;
                }
                
                public void grad() throws ParseException {
                    SVGOMAngle.this.unitType = 4;
                }
            });
            this.unitType = 1;
            angleParser.parse(s);
        }
        catch (ParseException ex) {
            this.unitType = 0;
            this.value = 0.0f;
        }
    }
    
    public static float toUnit(short n, final float n2, short n3) {
        if (n == 1) {
            n = 2;
        }
        if (n3 == 1) {
            n3 = 2;
        }
        return (float)(SVGOMAngle.K[n - 2][n3 - 2] * n2);
    }
    
    static {
        UNITS = new String[] { "", "", "deg", "rad", "grad" };
        SVGOMAngle.K = new double[][] { { 1.0, 0.017453292519943295, 0.015707963267948967 }, { 57.29577951308232, 1.0, 63.66197723675813 }, { 0.9, 0.015707963267948967, 1.0 } };
    }
}
