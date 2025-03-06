// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.commons.imaging.formats.psd;

import java.io.IOException;
import java.util.logging.Level;
import java.io.Writer;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.logging.Logger;

public class PsdHeaderInfo
{
    private static final Logger LOGGER;
    public final int version;
    private final byte[] reserved;
    public final int channels;
    public final int rows;
    public final int columns;
    public final int depth;
    public final int mode;
    
    public PsdHeaderInfo(final int version, final byte[] reserved, final int channels, final int rows, final int columns, final int depth, final int mode) {
        this.version = version;
        this.reserved = reserved.clone();
        this.channels = channels;
        this.rows = rows;
        this.columns = columns;
        this.depth = depth;
        this.mode = mode;
    }
    
    public byte[] getReserved() {
        return this.reserved.clone();
    }
    
    public void dump() {
        try (final StringWriter sw = new StringWriter();
             final PrintWriter pw = new PrintWriter(sw)) {
            this.dump(pw);
            pw.flush();
            sw.flush();
            PsdHeaderInfo.LOGGER.fine(sw.toString());
        }
        catch (IOException e) {
            PsdHeaderInfo.LOGGER.log(Level.SEVERE, e.getMessage(), e);
        }
    }
    
    public void dump(final PrintWriter pw) {
        pw.println("");
        pw.println("Header");
        pw.println("Version: " + this.version + " (" + Integer.toHexString(this.version) + ")");
        pw.println("Channels: " + this.channels + " (" + Integer.toHexString(this.channels) + ")");
        pw.println("Rows: " + this.rows + " (" + Integer.toHexString(this.rows) + ")");
        pw.println("Columns: " + this.columns + " (" + Integer.toHexString(this.columns) + ")");
        pw.println("Depth: " + this.depth + " (" + Integer.toHexString(this.depth) + ")");
        pw.println("Mode: " + this.mode + " (" + Integer.toHexString(this.mode) + ")");
        pw.println("Reserved: " + this.reserved.length);
        pw.println("");
        pw.flush();
    }
    
    static {
        LOGGER = Logger.getLogger(PsdHeaderInfo.class.getName());
    }
}
