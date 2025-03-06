// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.commons.imaging.internal;

import java.util.Iterator;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Calendar;
import java.util.Date;
import java.io.File;
import java.awt.color.ICC_Profile;
import java.util.List;
import java.util.Collection;
import java.util.ArrayList;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class Debug
{
    private static final Logger LOGGER;
    private static final String NEWLINE = "\r\n";
    private static long counter;
    
    public static void debug(final String message) {
        if (Debug.LOGGER.isLoggable(Level.FINEST)) {
            Debug.LOGGER.finest(message);
        }
    }
    
    public static void debug() {
        if (Debug.LOGGER.isLoggable(Level.FINEST)) {
            Debug.LOGGER.finest("\r\n");
        }
    }
    
    private static String getDebug(final String message, final int[] v) {
        final StringBuilder result = new StringBuilder();
        if (v == null) {
            result.append(message + " (" + (Object)null + ")" + "\r\n");
        }
        else {
            result.append(message + " (" + v.length + ")" + "\r\n");
            for (final int element : v) {
                result.append("\t" + element + "\r\n");
            }
            result.append("\r\n");
        }
        return result.toString();
    }
    
    private static String getDebug(final String message, final byte[] v) {
        final int max = 250;
        return getDebug(message, v, 250);
    }
    
    private static String getDebug(final String message, final byte[] v, final int max) {
        final StringBuilder result = new StringBuilder();
        if (v == null) {
            result.append(message + " (" + (Object)null + ")" + "\r\n");
        }
        else {
            result.append(message + " (" + v.length + ")" + "\r\n");
            for (int i = 0; i < max && i < v.length; ++i) {
                final int b = 0xFF & v[i];
                char c;
                if (b == 0 || b == 10 || b == 11 || b == 13) {
                    c = ' ';
                }
                else {
                    c = (char)b;
                }
                result.append("\t" + i + ": " + b + " (" + c + ", 0x" + Integer.toHexString(b) + ")" + "\r\n");
            }
            if (v.length > max) {
                result.append("\t...\r\n");
            }
            result.append("\r\n");
        }
        return result.toString();
    }
    
    private static String getDebug(final String message, final char[] v) {
        final StringBuilder result = new StringBuilder();
        if (v == null) {
            result.append(message + " (" + (Object)null + ")" + "\r\n");
        }
        else {
            result.append(message + " (" + v.length + ")" + "\r\n");
            for (final char element : v) {
                result.append("\t" + element + " (" + ('\u00ff' & element) + ")" + "\r\n");
            }
            result.append("\r\n");
        }
        return result.toString();
    }
    
    private static void debug(final String message, final Map<?, ?> map) {
        debug(getDebug(message, map));
    }
    
    private static String getDebug(final String message, final Map<?, ?> map) {
        final StringBuilder result = new StringBuilder();
        if (map == null) {
            return message + " map: " + (Object)null;
        }
        final List<Object> keys = new ArrayList<Object>(map.keySet());
        result.append(message + " map: " + keys.size() + "\r\n");
        for (int i = 0; i < keys.size(); ++i) {
            final Object key = keys.get(i);
            final Object value = map.get(key);
            result.append("\t" + i + ": '" + key + "' -> '" + value + "'" + "\r\n");
        }
        result.append("\r\n");
        return result.toString();
    }
    
    private static String byteQuadToString(final int bytequad) {
        final byte b1 = (byte)(bytequad >> 24 & 0xFF);
        final byte b2 = (byte)(bytequad >> 16 & 0xFF);
        final byte b3 = (byte)(bytequad >> 8 & 0xFF);
        final byte b4 = (byte)(bytequad >> 0 & 0xFF);
        final char c1 = (char)b1;
        final char c2 = (char)b2;
        final char c3 = (char)b3;
        final char c4 = (char)b4;
        final StringBuilder buffer = new StringBuilder(31);
        buffer.append(new String(new char[] { c1, c2, c3, c4 }));
        buffer.append(" bytequad: ");
        buffer.append(bytequad);
        buffer.append(" b1: ");
        buffer.append(b1);
        buffer.append(" b2: ");
        buffer.append(b2);
        buffer.append(" b3: ");
        buffer.append(b3);
        buffer.append(" b4: ");
        buffer.append(b4);
        return buffer.toString();
    }
    
    public static void debug(final String message, final Object value) {
        if (value == null) {
            debug(message, "null");
        }
        else if (value instanceof char[]) {
            debug(message, (char[])value);
        }
        else if (value instanceof byte[]) {
            debug(message, (byte[])value);
        }
        else if (value instanceof int[]) {
            debug(message, (int[])value);
        }
        else if (value instanceof String) {
            debug(message, (String)value);
        }
        else if (value instanceof List) {
            debug(message, (List<?>)value);
        }
        else if (value instanceof Map) {
            debug(message, (Map<?, ?>)value);
        }
        else if (value instanceof ICC_Profile) {
            debug(message, (ICC_Profile)value);
        }
        else if (value instanceof File) {
            debug(message, (File)value);
        }
        else if (value instanceof Date) {
            debug(message, (Date)value);
        }
        else if (value instanceof Calendar) {
            debug(message, (Calendar)value);
        }
        else {
            debug(message, value.toString());
        }
    }
    
    private static void debug(final String message, final byte[] v) {
        debug(getDebug(message, v));
    }
    
    private static void debug(final String message, final char[] v) {
        debug(getDebug(message, v));
    }
    
    private static void debug(final String message, final Calendar value) {
        final DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss", Locale.ENGLISH);
        debug(message, (value == null) ? "null" : df.format(value.getTime()));
    }
    
    private static void debug(final String message, final Date value) {
        final DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss", Locale.ENGLISH);
        debug(message, (value == null) ? "null" : df.format(value));
    }
    
    private static void debug(final String message, final File file) {
        debug(message + ": " + ((file == null) ? "null" : file.getPath()));
    }
    
    private static void debug(final String message, final ICC_Profile value) {
        debug("ICC_Profile " + message + ": " + ((value == null) ? "null" : value.toString()));
        if (value != null) {
            debug("\t getProfileClass: " + byteQuadToString(value.getProfileClass()));
            debug("\t getPCSType: " + byteQuadToString(value.getPCSType()));
            debug("\t getColorSpaceType() : " + byteQuadToString(value.getColorSpaceType()));
        }
    }
    
    private static void debug(final String message, final int[] v) {
        debug(getDebug(message, v));
    }
    
    private static void debug(final String message, final List<?> v) {
        final String suffix = " [" + Debug.counter++ + "]";
        debug(message + " (" + v.size() + ")" + suffix);
        for (final Object aV : v) {
            debug("\t" + aV.toString() + suffix);
        }
        debug();
    }
    
    private static void debug(final String message, final String value) {
        debug(message + " " + value);
    }
    
    public static void debug(final Throwable e) {
        debug(getDebug(e));
    }
    
    public static void debug(final Throwable e, final int value) {
        debug(getDebug(e, value));
    }
    
    private static String getDebug(final Throwable e) {
        return getDebug(e, -1);
    }
    
    private static String getDebug(final Throwable e, final int max) {
        final StringBuilder result = new StringBuilder(35);
        final SimpleDateFormat timestamp = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss:SSS", Locale.ENGLISH);
        final String datetime = timestamp.format(new Date()).toLowerCase();
        result.append("\r\n");
        result.append("Throwable: " + ((e == null) ? "" : ("(" + e.getClass().getName() + ")")) + ":" + datetime + "\r\n");
        result.append("Throwable: " + ((e == null) ? "null" : e.getLocalizedMessage()) + "\r\n");
        result.append("\r\n");
        result.append(getStackTrace(e, max));
        result.append("Caught here:\r\n");
        result.append(getStackTrace(new Exception(), max, 1));
        result.append("\r\n");
        return result.toString();
    }
    
    private static String getStackTrace(final Throwable e, final int limit) {
        return getStackTrace(e, limit, 0);
    }
    
    private static String getStackTrace(final Throwable e, final int limit, final int skip) {
        final StringBuilder result = new StringBuilder();
        if (e != null) {
            final StackTraceElement[] stes = e.getStackTrace();
            if (stes != null) {
                for (int i = skip; i < stes.length && (limit < 0 || i < limit); ++i) {
                    final StackTraceElement ste = stes[i];
                    result.append("\tat " + ste.getClassName() + "." + ste.getMethodName() + "(" + ste.getFileName() + ":" + ste.getLineNumber() + ")" + "\r\n");
                }
                if (limit >= 0 && stes.length > limit) {
                    result.append("\t...\r\n");
                }
            }
            result.append("\r\n");
        }
        return result.toString();
    }
    
    private Debug() {
    }
    
    static {
        LOGGER = Logger.getLogger(Debug.class.getName());
    }
}
