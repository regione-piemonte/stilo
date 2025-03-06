// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.commons.imaging.common;

import java.io.IOException;
import java.util.logging.Level;
import java.io.Writer;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.ByteOrder;
import java.util.logging.Logger;

public class BinaryFileParser
{
    private static final Logger LOGGER;
    private ByteOrder byteOrder;
    
    public BinaryFileParser(final ByteOrder byteOrder) {
        this.byteOrder = ByteOrder.BIG_ENDIAN;
        this.byteOrder = byteOrder;
    }
    
    public BinaryFileParser() {
        this.byteOrder = ByteOrder.BIG_ENDIAN;
    }
    
    protected void setByteOrder(final ByteOrder byteOrder) {
        this.byteOrder = byteOrder;
    }
    
    public ByteOrder getByteOrder() {
        return this.byteOrder;
    }
    
    protected final void debugNumber(final String msg, final int data, final int bytes) {
        try (final StringWriter sw = new StringWriter();
             final PrintWriter pw = new PrintWriter(sw)) {
            this.debugNumber(pw, msg, data, bytes);
            pw.flush();
            sw.flush();
            BinaryFileParser.LOGGER.fine(sw.toString());
        }
        catch (IOException e) {
            BinaryFileParser.LOGGER.log(Level.SEVERE, e.getMessage(), e);
        }
    }
    
    protected final void debugNumber(final PrintWriter pw, final String msg, final int data, final int bytes) {
        pw.print(msg + ": " + data + " (");
        int byteData = data;
        for (int i = 0; i < bytes; ++i) {
            if (i > 0) {
                pw.print(",");
            }
            final int singleByte = 0xFF & byteData;
            pw.print((char)singleByte + " [" + singleByte + "]");
            byteData >>= 8;
        }
        pw.println(") [0x" + Integer.toHexString(data) + ", " + Integer.toBinaryString(data) + "]");
        pw.flush();
    }
    
    static {
        LOGGER = Logger.getLogger(BinaryFileParser.class.getName());
    }
}
