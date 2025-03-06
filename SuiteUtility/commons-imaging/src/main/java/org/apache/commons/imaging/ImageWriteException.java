// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.commons.imaging;

public class ImageWriteException extends ImagingException
{
    private static final long serialVersionUID = -1L;
    
    public ImageWriteException(final String message) {
        super(message);
    }
    
    public ImageWriteException(final String message, final Throwable cause) {
        super(message, cause);
    }
    
    public ImageWriteException(final String message, final Object data) {
        super(message + ": " + data + " (" + getType(data) + ")");
    }
    
    private static String getType(final Object value) {
        if (value == null) {
            return "null";
        }
        if (value instanceof Object[]) {
            return "[Object[]: " + ((Object[])value).length + "]";
        }
        if (value instanceof char[]) {
            return "[char[]: " + ((char[])value).length + "]";
        }
        if (value instanceof byte[]) {
            return "[byte[]: " + ((byte[])value).length + "]";
        }
        if (value instanceof short[]) {
            return "[short[]: " + ((short[])value).length + "]";
        }
        if (value instanceof int[]) {
            return "[int[]: " + ((int[])value).length + "]";
        }
        if (value instanceof long[]) {
            return "[long[]: " + ((long[])value).length + "]";
        }
        if (value instanceof float[]) {
            return "[float[]: " + ((float[])value).length + "]";
        }
        if (value instanceof double[]) {
            return "[double[]: " + ((double[])value).length + "]";
        }
        if (value instanceof boolean[]) {
            return "[boolean[]: " + ((boolean[])value).length + "]";
        }
        return value.getClass().getName();
    }
}
