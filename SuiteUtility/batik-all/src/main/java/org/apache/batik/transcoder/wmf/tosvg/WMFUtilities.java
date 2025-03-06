// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.transcoder.wmf.tosvg;

import java.io.UnsupportedEncodingException;

public class WMFUtilities
{
    public static String decodeString(final WMFFont wmfFont, final byte[] bytes) {
        try {
            switch (wmfFont.charset) {
                case 0: {
                    return new String(bytes, "ISO-8859-1");
                }
                case 1: {
                    return new String(bytes, "US-ASCII");
                }
                case 128: {
                    return new String(bytes, "Shift_JIS");
                }
                case 129: {
                    return new String(bytes, "cp949");
                }
                case 130: {
                    return new String(bytes, "x-Johab");
                }
                case 134: {
                    return new String(bytes, "GB2312");
                }
                case 136: {
                    return new String(bytes, "Big5");
                }
                case 161: {
                    return new String(bytes, "windows-1253");
                }
                case 162: {
                    return new String(bytes, "cp1254");
                }
                case 163: {
                    return new String(bytes, "cp1258");
                }
                case 177: {
                    return new String(bytes, "windows-1255");
                }
                case 178: {
                    return new String(bytes, "windows-1256");
                }
                case 204: {
                    return new String(bytes, "windows-1251");
                }
                case 222: {
                    return new String(bytes, "cp874");
                }
                case 238: {
                    return new String(bytes, "cp1250");
                }
                case 255: {
                    return new String(bytes, "cp437");
                }
            }
        }
        catch (UnsupportedEncodingException ex) {}
        return new String(bytes);
    }
    
    public static int getHorizontalAlignment(final int n) {
        final int n2 = n % 24 % 8;
        if (n2 >= 6) {
            return 6;
        }
        if (n2 >= 2) {
            return 2;
        }
        return 0;
    }
    
    public static int getVerticalAlignment(final int n) {
        if (n / 24 != 0) {
            return 24;
        }
        if (n % 24 / 8 != 0) {
            return 8;
        }
        return 0;
    }
}
