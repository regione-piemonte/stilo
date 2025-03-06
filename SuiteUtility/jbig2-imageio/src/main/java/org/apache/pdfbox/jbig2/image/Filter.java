// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.pdfbox.jbig2.image;

abstract class Filter
{
    final boolean cardinal;
    double support;
    double blur;
    
    public static String nameByType(final FilterType filterType) {
        if (filterType == null) {
            throw new IllegalArgumentException("type must not be null");
        }
        return filterType.name();
    }
    
    public static FilterType typeByName(final String s) {
        if (s == null) {
            throw new IllegalArgumentException("name must not be null");
        }
        return FilterType.valueOf(s);
    }
    
    public static Filter byType(final FilterType filterType) {
        switch (filterType) {
            case Bessel: {
                return new Bessel();
            }
            case Blackman: {
                return new Blackman();
            }
            case Box: {
                return new Box();
            }
            case Catrom: {
                return new Catrom();
            }
            case Cubic: {
                return new Cubic();
            }
            case Gaussian: {
                return new Gaussian();
            }
            case Hamming: {
                return new Hamming();
            }
            case Hanning: {
                return new Hanning();
            }
            case Hermite: {
                return new Hermite();
            }
            case Lanczos: {
                return new Lanczos();
            }
            case Mitchell: {
                return new Mitchell();
            }
            case Point: {
                return new Point();
            }
            case Quadratic: {
                return new Quadratic();
            }
            case Sinc: {
                return new Sinc();
            }
            case Triangle: {
                return new Triangle();
            }
            default: {
                throw new IllegalArgumentException("No filter for given type.");
            }
        }
    }
    
    protected Filter() {
        this(true, 1.0, 1.0);
    }
    
    protected Filter(final boolean cardinal, final double support, final double blur) {
        this.cardinal = cardinal;
        this.support = support;
        this.blur = blur;
    }
    
    public double fWindowed(final double n) {
        return (n < -this.support || n > this.support) ? 0.0 : this.f(n);
    }
    
    public abstract double f(final double p0);
    
    public String getName() {
        return this.getClass().getSimpleName();
    }
    
    public double getSupport() {
        return this.support;
    }
    
    public void setSupport(final double support) {
        this.support = support;
    }
    
    public double getBlur() {
        return this.blur;
    }
    
    public void setBlur(final double blur) {
        this.blur = blur;
    }
    
    public static final class Bessel extends Filter
    {
        public Bessel() {
            super(false, 3.2383, 1.0);
        }
        
        private double J1(final double n) {
            final double[] array = { 5.811993540016061E20, -6.672106568924916E19, 2.3164335806340024E18, -3.588817569910106E16, 2.9087952638347756E14, -1.3229834803321265E12, 3.4132341823017006E9, -4695753.530642996, 2701.1227108923235 };
            final double[] array2 = { 1.1623987080032122E21, 1.185770712190321E19, 6.0920613989175216E16, 2.0816612213076075E14, 5.2437102621676495E11, 1.013863514358674E9, 1501793.5949985855, 1606.9315734814877, 1.0 };
            double n2 = array[8];
            double n3 = array2[8];
            for (int i = 7; i >= 0; --i) {
                n2 = n2 * n * n + array[i];
                n3 = n3 * n * n + array2[i];
            }
            return n2 / n3;
        }
        
        private double P1(final double n) {
            final double[] array = { 35224.66491336798, 62758.84524716128, 31353.963110915956, 4985.4832060594335, 211.15291828539623, 1.2571716929145342 };
            final double[] array2 = { 35224.66491336798, 62694.34695935605, 31240.406381904104, 4930.396490181089, 203.07751891347593, 1.0 };
            double n2 = array[5];
            double n3 = array2[5];
            for (int i = 4; i >= 0; --i) {
                n2 = n2 * (8.0 / n) * (8.0 / n) + array[i];
                n3 = n3 * (8.0 / n) * (8.0 / n) + array2[i];
            }
            return n2 / n3;
        }
        
        private double Q1(final double n) {
            final double[] array = { 351.17519143035526, 721.0391804904475, 425.98730116544425, 83.18989576738508, 4.568171629551227, 0.03532840052740124 };
            final double[] array2 = { 7491.737417180912, 15414.177339265098, 9152.231701516992, 1811.1867005523513, 103.81875854621337, 1.0 };
            double n2 = array[5];
            double n3 = array2[5];
            for (int i = 4; i >= 0; --i) {
                n2 = n2 * (8.0 / n) * (8.0 / n) + array[i];
                n3 = n3 * (8.0 / n) * (8.0 / n) + array2[i];
            }
            return n2 / n3;
        }
        
        private double BesselOrderOne(double n) {
            if (n == 0.0) {
                return 0.0;
            }
            final double n2 = n;
            if (n < 0.0) {
                n = -n;
            }
            if (n < 8.0) {
                return n2 * this.J1(n);
            }
            double n3 = Math.sqrt(2.0 / (3.141592653589793 * n)) * (this.P1(n) * (1.0 / Math.sqrt(2.0) * (Math.sin(n) - Math.cos(n))) - 8.0 / n * this.Q1(n) * (-1.0 / Math.sqrt(2.0) * (Math.sin(n) + Math.cos(n))));
            if (n2 < 0.0) {
                n3 = -n3;
            }
            return n3;
        }
        
        @Override
        public double f(final double n) {
            if (n == 0.0) {
                return 0.7853981633974483;
            }
            return this.BesselOrderOne(3.141592653589793 * n) / (2.0 * n);
        }
    }
    
    public static final class Blackman extends Filter
    {
        @Override
        public double f(final double n) {
            return 0.42 + 0.5 * Math.cos(3.141592653589793 * n) + 0.08 * Math.cos(6.283185307179586 * n);
        }
    }
    
    public static class Box extends Filter
    {
        public Box() {
            super(true, 0.5, 1.0);
        }
        
        public Box(final double n) {
            super(true, n, 1.0);
        }
        
        @Override
        public double f(final double n) {
            if (n >= -0.5 && n < 0.5) {
                return 1.0;
            }
            return 0.0;
        }
    }
    
    public static final class Point extends Box
    {
        public Point() {
            super(0.0);
        }
        
        @Override
        public double fWindowed(final double n) {
            return super.f(n);
        }
    }
    
    public static final class Catrom extends Filter
    {
        public Catrom() {
            super(true, 2.0, 1.0);
        }
        
        @Override
        public double f(double n) {
            if (n < 0.0) {
                n = -n;
            }
            if (n < 1.0) {
                return 0.5 * (2.0 + n * n * (-5.0 + n * 3.0));
            }
            if (n < 2.0) {
                return 0.5 * (4.0 + n * (-8.0 + n * (5.0 - n)));
            }
            return 0.0;
        }
    }
    
    public static final class Cubic extends Filter
    {
        public Cubic() {
            super(false, 2.0, 1.0);
        }
        
        @Override
        public double f(double n) {
            if (n < 0.0) {
                n = -n;
            }
            if (n < 1.0) {
                return 0.5 * n * n * n - n * n + 0.6666666666666666;
            }
            if (n < 2.0) {
                n = 2.0 - n;
                return 0.16666666666666666 * n * n * n;
            }
            return 0.0;
        }
    }
    
    public static final class Gaussian extends Filter
    {
        public Gaussian() {
            super(false, 1.25, 1.0);
        }
        
        @Override
        public double f(final double n) {
            return Math.exp(-2.0 * n * n) * Math.sqrt(0.6366197723675814);
        }
    }
    
    public static final class Hamming extends Filter
    {
        @Override
        public double f(final double n) {
            return 0.54 + 0.46 * Math.cos(3.141592653589793 * n);
        }
    }
    
    public static final class Hanning extends Filter
    {
        @Override
        public double f(final double n) {
            return 0.5 + 0.5 * Math.cos(3.141592653589793 * n);
        }
    }
    
    public static final class Hermite extends Filter
    {
        @Override
        public double f(double n) {
            if (n < 0.0) {
                n = -n;
            }
            if (n < 1.0) {
                return (2.0 * n - 3.0) * n * n + 1.0;
            }
            return 0.0;
        }
    }
    
    public static final class Lanczos extends Filter
    {
        public Lanczos() {
            super(true, 3.0, 1.0);
        }
        
        @Override
        public double f(double n) {
            if (n < 0.0) {
                n = -n;
            }
            if (n < 3.0) {
                return (float)(this.sinc(n) * this.sinc(n / 3.0));
            }
            return 0.0;
        }
        
        private double sinc(double a) {
            if (a != 0.0) {
                a *= 3.141592653589793;
                return Math.sin(a) / a;
            }
            return 1.0;
        }
    }
    
    public static final class Mitchell extends Filter
    {
        public Mitchell() {
            super(false, 2.0, 1.0);
        }
        
        @Override
        public double f(double n) {
            final double n2 = 0.3333333333333333;
            final double n3 = 0.3333333333333333;
            if (n < 0.0) {
                n = -n;
            }
            if (n < 1.0) {
                n = (12.0 - 9.0 * n2 - 6.0 * n3) * (n * n * n) + (-18.0 + 12.0 * n2 + 6.0 * n3) * n * n + (6.0 - 2.0 * n2);
                return n / 6.0;
            }
            if (n < 2.0) {
                n = (-1.0 * n2 - 6.0 * n3) * (n * n * n) + (6.0 * n2 + 30.0 * n3) * n * n + (-12.0 * n2 - 48.0 * n3) * n + (8.0 * n2 + 24.0 * n3);
                return n / 6.0;
            }
            return 0.0;
        }
    }
    
    public static final class Quadratic extends Filter
    {
        public Quadratic() {
            super(false, 1.5, 1.0);
        }
        
        @Override
        public double f(double n) {
            if (n < 0.0) {
                n = -n;
            }
            if (n < 0.5) {
                return 0.75 - n * n;
            }
            if (n < 1.5) {
                n -= 1.5;
                return 0.5 * n * n;
            }
            return 0.0;
        }
    }
    
    public static final class Sinc extends Filter
    {
        public Sinc() {
            super(true, 4.0, 1.0);
        }
        
        @Override
        public double f(double a) {
            a *= 3.141592653589793;
            if (a != 0.0) {
                return Math.sin(a) / a;
            }
            return 1.0;
        }
    }
    
    public static final class Triangle extends Filter
    {
        @Override
        public double f(double n) {
            if (n < 0.0) {
                n = -n;
            }
            if (n < 1.0) {
                return 1.0 - n;
            }
            return 0.0;
        }
    }
}
