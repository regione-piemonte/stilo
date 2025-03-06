// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.css.parser;

import org.w3c.css.sac.LexicalUnit;

public abstract class CSSLexicalUnit implements LexicalUnit
{
    public static final String UNIT_TEXT_CENTIMETER = "cm";
    public static final String UNIT_TEXT_DEGREE = "deg";
    public static final String UNIT_TEXT_EM = "em";
    public static final String UNIT_TEXT_EX = "ex";
    public static final String UNIT_TEXT_GRADIAN = "grad";
    public static final String UNIT_TEXT_HERTZ = "Hz";
    public static final String UNIT_TEXT_INCH = "in";
    public static final String UNIT_TEXT_KILOHERTZ = "kHz";
    public static final String UNIT_TEXT_MILLIMETER = "mm";
    public static final String UNIT_TEXT_MILLISECOND = "ms";
    public static final String UNIT_TEXT_PERCENTAGE = "%";
    public static final String UNIT_TEXT_PICA = "pc";
    public static final String UNIT_TEXT_PIXEL = "px";
    public static final String UNIT_TEXT_POINT = "pt";
    public static final String UNIT_TEXT_RADIAN = "rad";
    public static final String UNIT_TEXT_REAL = "";
    public static final String UNIT_TEXT_SECOND = "s";
    public static final String TEXT_RGBCOLOR = "rgb";
    public static final String TEXT_RECT_FUNCTION = "rect";
    public static final String TEXT_COUNTER_FUNCTION = "counter";
    public static final String TEXT_COUNTERS_FUNCTION = "counters";
    protected short lexicalUnitType;
    protected LexicalUnit nextLexicalUnit;
    protected LexicalUnit previousLexicalUnit;
    
    protected CSSLexicalUnit(final short lexicalUnitType, final LexicalUnit previousLexicalUnit) {
        this.lexicalUnitType = lexicalUnitType;
        this.previousLexicalUnit = previousLexicalUnit;
        if (previousLexicalUnit != null) {
            ((CSSLexicalUnit)previousLexicalUnit).nextLexicalUnit = (LexicalUnit)this;
        }
    }
    
    public short getLexicalUnitType() {
        return this.lexicalUnitType;
    }
    
    public LexicalUnit getNextLexicalUnit() {
        return this.nextLexicalUnit;
    }
    
    public void setNextLexicalUnit(final LexicalUnit nextLexicalUnit) {
        this.nextLexicalUnit = nextLexicalUnit;
    }
    
    public LexicalUnit getPreviousLexicalUnit() {
        return this.previousLexicalUnit;
    }
    
    public void setPreviousLexicalUnit(final LexicalUnit previousLexicalUnit) {
        this.previousLexicalUnit = previousLexicalUnit;
    }
    
    public int getIntegerValue() {
        throw new IllegalStateException();
    }
    
    public float getFloatValue() {
        throw new IllegalStateException();
    }
    
    public String getDimensionUnitText() {
        switch (this.lexicalUnitType) {
            case 19: {
                return "cm";
            }
            case 28: {
                return "deg";
            }
            case 15: {
                return "em";
            }
            case 16: {
                return "ex";
            }
            case 29: {
                return "grad";
            }
            case 33: {
                return "Hz";
            }
            case 18: {
                return "in";
            }
            case 34: {
                return "kHz";
            }
            case 20: {
                return "mm";
            }
            case 31: {
                return "ms";
            }
            case 23: {
                return "%";
            }
            case 22: {
                return "pc";
            }
            case 17: {
                return "px";
            }
            case 21: {
                return "pt";
            }
            case 30: {
                return "rad";
            }
            case 14: {
                return "";
            }
            case 32: {
                return "s";
            }
            default: {
                throw new IllegalStateException("No Unit Text for type: " + this.lexicalUnitType);
            }
        }
    }
    
    public String getFunctionName() {
        throw new IllegalStateException();
    }
    
    public LexicalUnit getParameters() {
        throw new IllegalStateException();
    }
    
    public String getStringValue() {
        throw new IllegalStateException();
    }
    
    public LexicalUnit getSubValues() {
        throw new IllegalStateException();
    }
    
    public static CSSLexicalUnit createSimple(final short n, final LexicalUnit lexicalUnit) {
        return new SimpleLexicalUnit(n, lexicalUnit);
    }
    
    public static CSSLexicalUnit createInteger(final int n, final LexicalUnit lexicalUnit) {
        return new IntegerLexicalUnit(n, lexicalUnit);
    }
    
    public static CSSLexicalUnit createFloat(final short n, final float n2, final LexicalUnit lexicalUnit) {
        return new FloatLexicalUnit(n, n2, lexicalUnit);
    }
    
    public static CSSLexicalUnit createDimension(final float n, final String s, final LexicalUnit lexicalUnit) {
        return new DimensionLexicalUnit(n, s, lexicalUnit);
    }
    
    public static CSSLexicalUnit createFunction(final String s, final LexicalUnit lexicalUnit, final LexicalUnit lexicalUnit2) {
        return new FunctionLexicalUnit(s, lexicalUnit, lexicalUnit2);
    }
    
    public static CSSLexicalUnit createPredefinedFunction(final short n, final LexicalUnit lexicalUnit, final LexicalUnit lexicalUnit2) {
        return new PredefinedFunctionLexicalUnit(n, lexicalUnit, lexicalUnit2);
    }
    
    public static CSSLexicalUnit createString(final short n, final String s, final LexicalUnit lexicalUnit) {
        return new StringLexicalUnit(n, s, lexicalUnit);
    }
    
    protected static class StringLexicalUnit extends CSSLexicalUnit
    {
        protected String value;
        
        public StringLexicalUnit(final short n, final String value, final LexicalUnit lexicalUnit) {
            super(n, lexicalUnit);
            this.value = value;
        }
        
        public String getStringValue() {
            return this.value;
        }
    }
    
    protected static class PredefinedFunctionLexicalUnit extends CSSLexicalUnit
    {
        protected LexicalUnit parameters;
        
        public PredefinedFunctionLexicalUnit(final short n, final LexicalUnit parameters, final LexicalUnit lexicalUnit) {
            super(n, lexicalUnit);
            this.parameters = parameters;
        }
        
        public String getFunctionName() {
            switch (this.lexicalUnitType) {
                case 27: {
                    return "rgb";
                }
                case 38: {
                    return "rect";
                }
                case 25: {
                    return "counter";
                }
                case 26: {
                    return "counters";
                }
                default: {
                    return super.getFunctionName();
                }
            }
        }
        
        public LexicalUnit getParameters() {
            return this.parameters;
        }
    }
    
    protected static class FunctionLexicalUnit extends CSSLexicalUnit
    {
        protected String name;
        protected LexicalUnit parameters;
        
        public FunctionLexicalUnit(final String name, final LexicalUnit parameters, final LexicalUnit lexicalUnit) {
            super((short)41, lexicalUnit);
            this.name = name;
            this.parameters = parameters;
        }
        
        public String getFunctionName() {
            return this.name;
        }
        
        public LexicalUnit getParameters() {
            return this.parameters;
        }
    }
    
    protected static class DimensionLexicalUnit extends CSSLexicalUnit
    {
        protected float value;
        protected String dimension;
        
        public DimensionLexicalUnit(final float value, final String dimension, final LexicalUnit lexicalUnit) {
            super((short)42, lexicalUnit);
            this.value = value;
            this.dimension = dimension;
        }
        
        public float getFloatValue() {
            return this.value;
        }
        
        public String getDimensionUnitText() {
            return this.dimension;
        }
    }
    
    protected static class FloatLexicalUnit extends CSSLexicalUnit
    {
        protected float value;
        
        public FloatLexicalUnit(final short n, final float value, final LexicalUnit lexicalUnit) {
            super(n, lexicalUnit);
            this.value = value;
        }
        
        public float getFloatValue() {
            return this.value;
        }
    }
    
    protected static class IntegerLexicalUnit extends CSSLexicalUnit
    {
        protected int value;
        
        public IntegerLexicalUnit(final int value, final LexicalUnit lexicalUnit) {
            super((short)13, lexicalUnit);
            this.value = value;
        }
        
        public int getIntegerValue() {
            return this.value;
        }
    }
    
    protected static class SimpleLexicalUnit extends CSSLexicalUnit
    {
        public SimpleLexicalUnit(final short n, final LexicalUnit lexicalUnit) {
            super(n, lexicalUnit);
        }
    }
}
