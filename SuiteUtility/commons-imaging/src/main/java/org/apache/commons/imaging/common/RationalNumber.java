// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.commons.imaging.common;

import java.text.NumberFormat;

public class RationalNumber extends Number
{
    private static final long serialVersionUID = -8412262656468158691L;
    private static final double TOLERANCE = 1.0E-8;
    public final int numerator;
    public final int divisor;
    
    public RationalNumber(final int numerator, final int divisor) {
        this.numerator = numerator;
        this.divisor = divisor;
    }
    
    static RationalNumber factoryMethod(long n, long d) {
        if (n > 2147483647L || n < -2147483648L || d > 2147483647L || d < -2147483648L) {
            while ((n > 2147483647L || n < -2147483648L || d > 2147483647L || d < -2147483648L) && Math.abs(n) > 1L && Math.abs(d) > 1L) {
                n >>= 1;
                d >>= 1;
            }
            if (d == 0L) {
                throw new NumberFormatException("Invalid value, numerator: " + n + ", divisor: " + d);
            }
        }
        final long gcd = gcd(n, d);
        d /= gcd;
        n /= gcd;
        return new RationalNumber((int)n, (int)d);
    }
    
    private static long gcd(final long a, final long b) {
        if (b == 0L) {
            return a;
        }
        return gcd(b, a % b);
    }
    
    public RationalNumber negate() {
        return new RationalNumber(-this.numerator, this.divisor);
    }
    
    @Override
    public double doubleValue() {
        return this.numerator / (double)this.divisor;
    }
    
    @Override
    public float floatValue() {
        return this.numerator / (float)this.divisor;
    }
    
    @Override
    public int intValue() {
        return this.numerator / this.divisor;
    }
    
    @Override
    public long longValue() {
        return this.numerator / (long)this.divisor;
    }
    
    @Override
    public String toString() {
        if (this.divisor == 0) {
            return "Invalid rational (" + this.numerator + "/" + this.divisor + ")";
        }
        final NumberFormat nf = NumberFormat.getInstance();
        if (this.numerator % this.divisor == 0) {
            return nf.format(this.numerator / this.divisor);
        }
        return this.numerator + "/" + this.divisor + " (" + nf.format(this.numerator / (double)this.divisor) + ")";
    }
    
    public String toDisplayString() {
        if (this.numerator % this.divisor == 0) {
            return Integer.toString(this.numerator / this.divisor);
        }
        final NumberFormat nf = NumberFormat.getInstance();
        nf.setMaximumFractionDigits(3);
        return nf.format(this.numerator / (double)this.divisor);
    }
    
    public static RationalNumber valueOf(double value) {
        if (value >= 2.147483647E9) {
            return new RationalNumber(Integer.MAX_VALUE, 1);
        }
        if (value <= -2.147483647E9) {
            return new RationalNumber(-2147483647, 1);
        }
        boolean negative = false;
        if (value < 0.0) {
            negative = true;
            value = Math.abs(value);
        }
        if (value == 0.0) {
            return new RationalNumber(0, 1);
        }
        RationalNumber l;
        RationalNumber h;
        if (value >= 1.0) {
            final int approx = (int)value;
            if (approx < value) {
                l = new RationalNumber(approx, 1);
                h = new RationalNumber(approx + 1, 1);
            }
            else {
                l = new RationalNumber(approx - 1, 1);
                h = new RationalNumber(approx, 1);
            }
        }
        else {
            final int approx = (int)(1.0 / value);
            if (1.0 / approx < value) {
                l = new RationalNumber(1, approx);
                h = new RationalNumber(1, approx - 1);
            }
            else {
                l = new RationalNumber(1, approx + 1);
                h = new RationalNumber(1, approx);
            }
        }
        Option low = Option.factory(l, value);
        Option high = Option.factory(h, value);
        Option bestOption = (low.error < high.error) ? low : high;
        final int maxIterations = 100;
        for (int count = 0; bestOption.error > 1.0E-8 && count < 100; ++count) {
            final RationalNumber mediant = factoryMethod(low.rationalNumber.numerator + (long)high.rationalNumber.numerator, low.rationalNumber.divisor + (long)high.rationalNumber.divisor);
            final Option mediantOption = Option.factory(mediant, value);
            if (value < mediant.doubleValue()) {
                if (high.error <= mediantOption.error) {
                    break;
                }
                high = mediantOption;
            }
            else {
                if (low.error <= mediantOption.error) {
                    break;
                }
                low = mediantOption;
            }
            if (mediantOption.error < bestOption.error) {
                bestOption = mediantOption;
            }
        }
        return negative ? bestOption.rationalNumber.negate() : bestOption.rationalNumber;
    }
    
    private static final class Option
    {
        public final RationalNumber rationalNumber;
        public final double error;
        
        private Option(final RationalNumber rationalNumber, final double error) {
            this.rationalNumber = rationalNumber;
            this.error = error;
        }
        
        public static Option factory(final RationalNumber rationalNumber, final double value) {
            return new Option(rationalNumber, Math.abs(rationalNumber.doubleValue() - value));
        }
        
        @Override
        public String toString() {
            return this.rationalNumber.toString();
        }
    }
}
