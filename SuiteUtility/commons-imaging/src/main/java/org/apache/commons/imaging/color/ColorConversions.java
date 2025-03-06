// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.commons.imaging.color;

public final class ColorConversions
{
    private static final double REF_X = 95.047;
    private static final double REF_Y = 100.0;
    private static final double REF_Z = 108.883;
    
    private ColorConversions() {
    }
    
    public static ColorCieLab convertXYZtoCIELab(final ColorXyz xyz) {
        return convertXYZtoCIELab(xyz.X, xyz.Y, xyz.Z);
    }
    
    public static ColorCieLab convertXYZtoCIELab(final double X, final double Y, final double Z) {
        double var_X = X / 95.047;
        double var_Y = Y / 100.0;
        double var_Z = Z / 108.883;
        if (var_X > 0.008856) {
            var_X = Math.pow(var_X, 0.3333333333333333);
        }
        else {
            var_X = 7.787 * var_X + 0.13793103448275862;
        }
        if (var_Y > 0.008856) {
            var_Y = Math.pow(var_Y, 0.3333333333333333);
        }
        else {
            var_Y = 7.787 * var_Y + 0.13793103448275862;
        }
        if (var_Z > 0.008856) {
            var_Z = Math.pow(var_Z, 0.3333333333333333);
        }
        else {
            var_Z = 7.787 * var_Z + 0.13793103448275862;
        }
        final double L = 116.0 * var_Y - 16.0;
        final double a = 500.0 * (var_X - var_Y);
        final double b = 200.0 * (var_Y - var_Z);
        return new ColorCieLab(L, a, b);
    }
    
    public static ColorXyz convertCIELabtoXYZ(final ColorCieLab cielab) {
        return convertCIELabtoXYZ(cielab.L, cielab.a, cielab.b);
    }
    
    public static ColorXyz convertCIELabtoXYZ(final double L, final double a, final double b) {
        double var_Y = (L + 16.0) / 116.0;
        double var_X = a / 500.0 + var_Y;
        double var_Z = var_Y - b / 200.0;
        if (Math.pow(var_Y, 3.0) > 0.008856) {
            var_Y = Math.pow(var_Y, 3.0);
        }
        else {
            var_Y = (var_Y - 0.13793103448275862) / 7.787;
        }
        if (Math.pow(var_X, 3.0) > 0.008856) {
            var_X = Math.pow(var_X, 3.0);
        }
        else {
            var_X = (var_X - 0.13793103448275862) / 7.787;
        }
        if (Math.pow(var_Z, 3.0) > 0.008856) {
            var_Z = Math.pow(var_Z, 3.0);
        }
        else {
            var_Z = (var_Z - 0.13793103448275862) / 7.787;
        }
        final double X = 95.047 * var_X;
        final double Y = 100.0 * var_Y;
        final double Z = 108.883 * var_Z;
        return new ColorXyz(X, Y, Z);
    }
    
    public static ColorHunterLab convertXYZtoHunterLab(final ColorXyz xyz) {
        return convertXYZtoHunterLab(xyz.X, xyz.Y, xyz.Z);
    }
    
    public static ColorHunterLab convertXYZtoHunterLab(final double X, final double Y, final double Z) {
        final double L = 10.0 * Math.sqrt(Y);
        final double a = 17.5 * ((1.02 * X - Y) / Math.sqrt(Y));
        final double b = 7.0 * ((Y - 0.847 * Z) / Math.sqrt(Y));
        return new ColorHunterLab(L, a, b);
    }
    
    public static ColorXyz convertHunterLabtoXYZ(final ColorHunterLab cielab) {
        return convertHunterLabtoXYZ(cielab.L, cielab.a, cielab.b);
    }
    
    public static ColorXyz convertHunterLabtoXYZ(final double L, final double a, final double b) {
        final double var_Y = L / 10.0;
        final double var_X = a / 17.5 * L / 10.0;
        final double var_Z = b / 7.0 * L / 10.0;
        final double Y = Math.pow(var_Y, 2.0);
        final double X = (var_X + Y) / 1.02;
        final double Z = -(var_Z - Y) / 0.847;
        return new ColorXyz(X, Y, Z);
    }
    
    public static int convertXYZtoRGB(final ColorXyz xyz) {
        return convertXYZtoRGB(xyz.X, xyz.Y, xyz.Z);
    }
    
    public static int convertXYZtoRGB(final double X, final double Y, final double Z) {
        final double var_X = X / 100.0;
        final double var_Y = Y / 100.0;
        final double var_Z = Z / 100.0;
        double var_R = var_X * 3.2406 + var_Y * -1.5372 + var_Z * -0.4986;
        double var_G = var_X * -0.9689 + var_Y * 1.8758 + var_Z * 0.0415;
        double var_B = var_X * 0.0557 + var_Y * -0.204 + var_Z * 1.057;
        if (var_R > 0.0031308) {
            var_R = 1.055 * Math.pow(var_R, 0.4166666666666667) - 0.055;
        }
        else {
            var_R *= 12.92;
        }
        if (var_G > 0.0031308) {
            var_G = 1.055 * Math.pow(var_G, 0.4166666666666667) - 0.055;
        }
        else {
            var_G *= 12.92;
        }
        if (var_B > 0.0031308) {
            var_B = 1.055 * Math.pow(var_B, 0.4166666666666667) - 0.055;
        }
        else {
            var_B *= 12.92;
        }
        final double R = var_R * 255.0;
        final double G = var_G * 255.0;
        final double B = var_B * 255.0;
        return convertRGBtoRGB(R, G, B);
    }
    
    public static ColorXyz convertRGBtoXYZ(final int rgb) {
        final int r = 0xFF & rgb >> 16;
        final int g = 0xFF & rgb >> 8;
        final int b = 0xFF & rgb >> 0;
        double var_R = r / 255.0;
        double var_G = g / 255.0;
        double var_B = b / 255.0;
        if (var_R > 0.04045) {
            var_R = Math.pow((var_R + 0.055) / 1.055, 2.4);
        }
        else {
            var_R /= 12.92;
        }
        if (var_G > 0.04045) {
            var_G = Math.pow((var_G + 0.055) / 1.055, 2.4);
        }
        else {
            var_G /= 12.92;
        }
        if (var_B > 0.04045) {
            var_B = Math.pow((var_B + 0.055) / 1.055, 2.4);
        }
        else {
            var_B /= 12.92;
        }
        var_R *= 100.0;
        var_G *= 100.0;
        var_B *= 100.0;
        final double X = var_R * 0.4124 + var_G * 0.3576 + var_B * 0.1805;
        final double Y = var_R * 0.2126 + var_G * 0.7152 + var_B * 0.0722;
        final double Z = var_R * 0.0193 + var_G * 0.1192 + var_B * 0.9505;
        return new ColorXyz(X, Y, Z);
    }
    
    public static ColorCmy convertRGBtoCMY(final int rgb) {
        final int R = 0xFF & rgb >> 16;
        final int G = 0xFF & rgb >> 8;
        final int B = 0xFF & rgb >> 0;
        final double C = 1.0 - R / 255.0;
        final double M = 1.0 - G / 255.0;
        final double Y = 1.0 - B / 255.0;
        return new ColorCmy(C, M, Y);
    }
    
    public static int convertCMYtoRGB(final ColorCmy cmy) {
        final double R = (1.0 - cmy.C) * 255.0;
        final double G = (1.0 - cmy.M) * 255.0;
        final double B = (1.0 - cmy.Y) * 255.0;
        return convertRGBtoRGB(R, G, B);
    }
    
    public static ColorCmyk convertCMYtoCMYK(final ColorCmy cmy) {
        double C = cmy.C;
        double M = cmy.M;
        double Y = cmy.Y;
        double var_K = 1.0;
        if (C < var_K) {
            var_K = C;
        }
        if (M < var_K) {
            var_K = M;
        }
        if (Y < var_K) {
            var_K = Y;
        }
        if (var_K == 1.0) {
            C = 0.0;
            M = 0.0;
            Y = 0.0;
        }
        else {
            C = (C - var_K) / (1.0 - var_K);
            M = (M - var_K) / (1.0 - var_K);
            Y = (Y - var_K) / (1.0 - var_K);
        }
        return new ColorCmyk(C, M, Y, var_K);
    }
    
    public static ColorCmy convertCMYKtoCMY(final ColorCmyk cmyk) {
        return convertCMYKtoCMY(cmyk.C, cmyk.M, cmyk.Y, cmyk.K);
    }
    
    public static ColorCmy convertCMYKtoCMY(double C, double M, double Y, final double K) {
        C = C * (1.0 - K) + K;
        M = M * (1.0 - K) + K;
        Y = Y * (1.0 - K) + K;
        return new ColorCmy(C, M, Y);
    }
    
    public static int convertCMYKtoRGB(final int c, final int m, final int y, final int k) {
        final double C = c / 255.0;
        final double M = m / 255.0;
        final double Y = y / 255.0;
        final double K = k / 255.0;
        return convertCMYtoRGB(convertCMYKtoCMY(C, M, Y, K));
    }
    
    public static ColorHsl convertRGBtoHSL(final int rgb) {
        final int R = 0xFF & rgb >> 16;
        final int G = 0xFF & rgb >> 8;
        final int B = 0xFF & rgb >> 0;
        final double var_R = R / 255.0;
        final double var_G = G / 255.0;
        final double var_B = B / 255.0;
        final double var_Min = Math.min(var_R, Math.min(var_G, var_B));
        boolean maxIsR = false;
        boolean maxIsG = false;
        double var_Max;
        if (var_R >= var_G && var_R >= var_B) {
            var_Max = var_R;
            maxIsR = true;
        }
        else if (var_G > var_B) {
            var_Max = var_G;
            maxIsG = true;
        }
        else {
            var_Max = var_B;
        }
        final double del_Max = var_Max - var_Min;
        final double L = (var_Max + var_Min) / 2.0;
        double H;
        double S;
        if (del_Max == 0.0) {
            H = 0.0;
            S = 0.0;
        }
        else {
            if (L < 0.5) {
                S = del_Max / (var_Max + var_Min);
            }
            else {
                S = del_Max / (2.0 - var_Max - var_Min);
            }
            final double del_R = ((var_Max - var_R) / 6.0 + del_Max / 2.0) / del_Max;
            final double del_G = ((var_Max - var_G) / 6.0 + del_Max / 2.0) / del_Max;
            final double del_B = ((var_Max - var_B) / 6.0 + del_Max / 2.0) / del_Max;
            if (maxIsR) {
                H = del_B - del_G;
            }
            else if (maxIsG) {
                H = 0.3333333333333333 + del_R - del_B;
            }
            else {
                H = 0.6666666666666666 + del_G - del_R;
            }
            if (H < 0.0) {
                ++H;
            }
            if (H > 1.0) {
                --H;
            }
        }
        return new ColorHsl(H, S, L);
    }
    
    public static int convertHSLtoRGB(final ColorHsl hsl) {
        return convertHSLtoRGB(hsl.H, hsl.S, hsl.L);
    }
    
    public static int convertHSLtoRGB(final double H, final double S, final double L) {
        double R;
        double G;
        double B;
        if (S == 0.0) {
            R = L * 255.0;
            G = L * 255.0;
            B = L * 255.0;
        }
        else {
            double var_2;
            if (L < 0.5) {
                var_2 = L * (1.0 + S);
            }
            else {
                var_2 = L + S - S * L;
            }
            final double var_3 = 2.0 * L - var_2;
            R = 255.0 * convertHuetoRGB(var_3, var_2, H + 0.3333333333333333);
            G = 255.0 * convertHuetoRGB(var_3, var_2, H);
            B = 255.0 * convertHuetoRGB(var_3, var_2, H - 0.3333333333333333);
        }
        return convertRGBtoRGB(R, G, B);
    }
    
    private static double convertHuetoRGB(final double v1, final double v2, double vH) {
        if (vH < 0.0) {
            ++vH;
        }
        if (vH > 1.0) {
            --vH;
        }
        if (6.0 * vH < 1.0) {
            return v1 + (v2 - v1) * 6.0 * vH;
        }
        if (2.0 * vH < 1.0) {
            return v2;
        }
        if (3.0 * vH < 2.0) {
            return v1 + (v2 - v1) * (0.6666666666666666 - vH) * 6.0;
        }
        return v1;
    }
    
    public static ColorHsv convertRGBtoHSV(final int rgb) {
        final int R = 0xFF & rgb >> 16;
        final int G = 0xFF & rgb >> 8;
        final int B = 0xFF & rgb >> 0;
        final double var_R = R / 255.0;
        final double var_G = G / 255.0;
        final double var_B = B / 255.0;
        final double var_Min = Math.min(var_R, Math.min(var_G, var_B));
        boolean maxIsR = false;
        boolean maxIsG = false;
        double var_Max;
        if (var_R >= var_G && var_R >= var_B) {
            var_Max = var_R;
            maxIsR = true;
        }
        else if (var_G > var_B) {
            var_Max = var_G;
            maxIsG = true;
        }
        else {
            var_Max = var_B;
        }
        final double del_Max = var_Max - var_Min;
        final double V = var_Max;
        double H;
        double S;
        if (del_Max == 0.0) {
            H = 0.0;
            S = 0.0;
        }
        else {
            S = del_Max / var_Max;
            final double del_R = ((var_Max - var_R) / 6.0 + del_Max / 2.0) / del_Max;
            final double del_G = ((var_Max - var_G) / 6.0 + del_Max / 2.0) / del_Max;
            final double del_B = ((var_Max - var_B) / 6.0 + del_Max / 2.0) / del_Max;
            if (maxIsR) {
                H = del_B - del_G;
            }
            else if (maxIsG) {
                H = 0.3333333333333333 + del_R - del_B;
            }
            else {
                H = 0.6666666666666666 + del_G - del_R;
            }
            if (H < 0.0) {
                ++H;
            }
            if (H > 1.0) {
                --H;
            }
        }
        return new ColorHsv(H, S, V);
    }
    
    public static int convertHSVtoRGB(final ColorHsv HSV) {
        return convertHSVtoRGB(HSV.H, HSV.S, HSV.V);
    }
    
    public static int convertHSVtoRGB(final double H, final double S, final double V) {
        double R;
        double G;
        double B;
        if (S == 0.0) {
            R = V * 255.0;
            G = V * 255.0;
            B = V * 255.0;
        }
        else {
            double var_h = H * 6.0;
            if (var_h == 6.0) {
                var_h = 0.0;
            }
            final double var_i = Math.floor(var_h);
            final double var_1 = V * (1.0 - S);
            final double var_2 = V * (1.0 - S * (var_h - var_i));
            final double var_3 = V * (1.0 - S * (1.0 - (var_h - var_i)));
            double var_r;
            double var_g;
            double var_b;
            if (var_i == 0.0) {
                var_r = V;
                var_g = var_3;
                var_b = var_1;
            }
            else if (var_i == 1.0) {
                var_r = var_2;
                var_g = V;
                var_b = var_1;
            }
            else if (var_i == 2.0) {
                var_r = var_1;
                var_g = V;
                var_b = var_3;
            }
            else if (var_i == 3.0) {
                var_r = var_1;
                var_g = var_2;
                var_b = V;
            }
            else if (var_i == 4.0) {
                var_r = var_3;
                var_g = var_1;
                var_b = V;
            }
            else {
                var_r = V;
                var_g = var_1;
                var_b = var_2;
            }
            R = var_r * 255.0;
            G = var_g * 255.0;
            B = var_b * 255.0;
        }
        return convertRGBtoRGB(R, G, B);
    }
    
    public static int convertCMYKtoRGB_Adobe(final int sc, final int sm, final int sy, final int sk) {
        final int red = 255 - (sc + sk);
        final int green = 255 - (sm + sk);
        final int blue = 255 - (sy + sk);
        return convertRGBtoRGB(red, green, blue);
    }
    
    private static double cube(final double f) {
        return f * f * f;
    }
    
    private static double square(final double f) {
        return f * f;
    }
    
    public static int convertCIELabtoARGBTest(final int cieL, final int cieA, final int cieB) {
        double var_Y = (cieL * 100.0 / 255.0 + 16.0) / 116.0;
        double var_X = cieA / 500.0 + var_Y;
        double var_Z = var_Y - cieB / 200.0;
        final double var_x_cube = cube(var_X);
        final double var_y_cube = cube(var_Y);
        final double var_z_cube = cube(var_Z);
        if (var_y_cube > 0.008856) {
            var_Y = var_y_cube;
        }
        else {
            var_Y = (var_Y - 0.13793103448275862) / 7.787;
        }
        if (var_x_cube > 0.008856) {
            var_X = var_x_cube;
        }
        else {
            var_X = (var_X - 0.13793103448275862) / 7.787;
        }
        if (var_z_cube > 0.008856) {
            var_Z = var_z_cube;
        }
        else {
            var_Z = (var_Z - 0.13793103448275862) / 7.787;
        }
        final double X = 95.047 * var_X;
        final double Y = 100.0 * var_Y;
        final double Z = 108.883 * var_Z;
        final double var_X2 = X / 100.0;
        final double var_Y2 = Y / 100.0;
        final double var_Z2 = Z / 100.0;
        double var_R = var_X2 * 3.2406 + var_Y2 * -1.5372 + var_Z2 * -0.4986;
        double var_G = var_X2 * -0.9689 + var_Y2 * 1.8758 + var_Z2 * 0.0415;
        double var_B = var_X2 * 0.0557 + var_Y2 * -0.204 + var_Z2 * 1.057;
        if (var_R > 0.0031308) {
            var_R = 1.055 * Math.pow(var_R, 0.4166666666666667) - 0.055;
        }
        else {
            var_R *= 12.92;
        }
        if (var_G > 0.0031308) {
            var_G = 1.055 * Math.pow(var_G, 0.4166666666666667) - 0.055;
        }
        else {
            var_G *= 12.92;
        }
        if (var_B > 0.0031308) {
            var_B = 1.055 * Math.pow(var_B, 0.4166666666666667) - 0.055;
        }
        else {
            var_B *= 12.92;
        }
        final double R = var_R * 255.0;
        final double G = var_G * 255.0;
        final double B = var_B * 255.0;
        return convertRGBtoRGB(R, G, B);
    }
    
    private static int convertRGBtoRGB(final double R, final double G, final double B) {
        int red = (int)Math.round(R);
        int green = (int)Math.round(G);
        int blue = (int)Math.round(B);
        red = Math.min(255, Math.max(0, red));
        green = Math.min(255, Math.max(0, green));
        blue = Math.min(255, Math.max(0, blue));
        final int alpha = 255;
        final int rgb = 0xFF000000 | red << 16 | green << 8 | blue << 0;
        return rgb;
    }
    
    private static int convertRGBtoRGB(int red, int green, int blue) {
        red = Math.min(255, Math.max(0, red));
        green = Math.min(255, Math.max(0, green));
        blue = Math.min(255, Math.max(0, blue));
        final int alpha = 255;
        final int rgb = 0xFF000000 | red << 16 | green << 8 | blue << 0;
        return rgb;
    }
    
    public static ColorCieLch convertCIELabtoCIELCH(final ColorCieLab cielab) {
        return convertCIELabtoCIELCH(cielab.L, cielab.a, cielab.b);
    }
    
    public static ColorCieLch convertCIELabtoCIELCH(final double L, final double a, final double b) {
        double var_H = Math.atan2(b, a);
        if (var_H > 0.0) {
            var_H = var_H / 3.141592653589793 * 180.0;
        }
        else {
            var_H = 360.0 - radian_2_degree(Math.abs(var_H));
        }
        final double C = Math.sqrt(square(a) + square(b));
        final double H = var_H;
        return new ColorCieLch(L, C, H);
    }
    
    public static ColorCieLab convertCIELCHtoCIELab(final ColorCieLch cielch) {
        return convertCIELCHtoCIELab(cielch.L, cielch.C, cielch.H);
    }
    
    public static ColorCieLab convertCIELCHtoCIELab(final double L, final double C, final double H) {
        final double a = Math.cos(degree_2_radian(H)) * C;
        final double b = Math.sin(degree_2_radian(H)) * C;
        return new ColorCieLab(L, a, b);
    }
    
    public static double degree_2_radian(final double degree) {
        return degree * 3.141592653589793 / 180.0;
    }
    
    public static double radian_2_degree(final double radian) {
        return radian * 180.0 / 3.141592653589793;
    }
    
    public static ColorCieLuv convertXYZtoCIELuv(final ColorXyz xyz) {
        return convertXYZtoCIELuv(xyz.X, xyz.Y, xyz.Z);
    }
    
    public static ColorCieLuv convertXYZtoCIELuv(final double X, final double Y, final double Z) {
        final double var_U = 4.0 * X / (X + 15.0 * Y + 3.0 * Z);
        final double var_V = 9.0 * Y / (X + 15.0 * Y + 3.0 * Z);
        double var_Y = Y / 100.0;
        if (var_Y > 0.008856) {
            var_Y = Math.pow(var_Y, 0.3333333333333333);
        }
        else {
            var_Y = 7.787 * var_Y + 0.13793103448275862;
        }
        final double ref_U = 0.19783982482140777;
        final double ref_V = 0.46833630293240974;
        final double L = 116.0 * var_Y - 16.0;
        final double u = 13.0 * L * (var_U - 0.19783982482140777);
        final double v = 13.0 * L * (var_V - 0.46833630293240974);
        return new ColorCieLuv(L, u, v);
    }
    
    public static ColorXyz convertCIELuvtoXYZ(final ColorCieLuv cielch) {
        return convertCIELuvtoXYZ(cielch.L, cielch.u, cielch.v);
    }
    
    public static ColorXyz convertCIELuvtoXYZ(final double L, final double u, final double v) {
        double var_Y = (L + 16.0) / 116.0;
        if (Math.pow(var_Y, 3.0) > 0.008856) {
            var_Y = Math.pow(var_Y, 3.0);
        }
        else {
            var_Y = (var_Y - 0.0) / 7.787;
        }
        final double ref_U = 0.19783982482140777;
        final double ref_V = 0.46833630293240974;
        final double var_U = u / (13.0 * L) + 0.19783982482140777;
        final double var_V = v / (13.0 * L) + 0.46833630293240974;
        final double Y = var_Y * 100.0;
        final double X = -(9.0 * Y * var_U) / ((var_U - 4.0) * var_V - var_U * var_V);
        final double Z = (9.0 * Y - 15.0 * var_V * Y - var_V * X) / (3.0 * var_V);
        return new ColorXyz(X, Y, Z);
    }
}
