// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.commons.imaging.icc;

import java.util.Arrays;
import java.nio.charset.StandardCharsets;
import org.apache.commons.imaging.ImageReadException;
import java.io.Writer;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.IOException;
import java.io.InputStream;
import org.apache.commons.imaging.common.BinaryFunctions;
import java.nio.ByteOrder;
import java.io.ByteArrayInputStream;
import java.util.logging.Logger;

public class IccTag
{
    private static final Logger LOGGER;
    public final int signature;
    public final int offset;
    public final int length;
    public final IccTagType fIccTagType;
    private byte[] data;
    private IccTagDataType itdt;
    private int dataTypeSignature;
    
    public IccTag(final int signature, final int offset, final int length, final IccTagType fIccTagType) {
        this.signature = signature;
        this.offset = offset;
        this.length = length;
        this.fIccTagType = fIccTagType;
    }
    
    public void setData(final byte[] bytes) throws IOException {
        this.data = bytes;
        try (final InputStream bis = new ByteArrayInputStream(bytes)) {
            this.dataTypeSignature = BinaryFunctions.read4Bytes("data type signature", bis, "ICC: corrupt tag data", ByteOrder.BIG_ENDIAN);
            this.itdt = this.getIccTagDataType(this.dataTypeSignature);
        }
    }
    
    private IccTagDataType getIccTagDataType(final int quad) {
        for (final IccTagDataType iccTagDataType : IccTagDataTypes.values()) {
            if (iccTagDataType.getSignature() == quad) {
                return iccTagDataType;
            }
        }
        return null;
    }
    
    public void dump(final String prefix) throws ImageReadException, IOException {
        try (final StringWriter sw = new StringWriter();
             final PrintWriter pw = new PrintWriter(sw)) {
            this.dump(pw, prefix);
            pw.flush();
            sw.flush();
            IccTag.LOGGER.fine(sw.toString());
        }
    }
    
    public void dump(final PrintWriter pw, final String prefix) throws ImageReadException, IOException {
        pw.println(prefix + "tag signature: " + Integer.toHexString(this.signature) + " (" + new String(new byte[] { (byte)(0xFF & this.signature >> 24), (byte)(0xFF & this.signature >> 16), (byte)(0xFF & this.signature >> 8), (byte)(0xFF & this.signature >> 0) }, StandardCharsets.US_ASCII) + ")");
        if (this.data == null) {
            pw.println(prefix + "data: " + Arrays.toString(this.data));
        }
        else {
            pw.println(prefix + "data: " + this.data.length);
            pw.println(prefix + "data type signature: " + Integer.toHexString(this.dataTypeSignature) + " (" + new String(new byte[] { (byte)(0xFF & this.dataTypeSignature >> 24), (byte)(0xFF & this.dataTypeSignature >> 16), (byte)(0xFF & this.dataTypeSignature >> 8), (byte)(0xFF & this.dataTypeSignature >> 0) }, StandardCharsets.US_ASCII) + ")");
            if (this.itdt == null) {
                pw.println(prefix + "IccTagType : unknown");
            }
            else {
                pw.println(prefix + "IccTagType : " + this.itdt.getName());
                this.itdt.dump(prefix, this.data);
            }
        }
        pw.println("");
        pw.flush();
    }
    
    static {
        LOGGER = Logger.getLogger(IccTag.class.getName());
    }
}
