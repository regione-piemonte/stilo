// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.commons.imaging.icc;

import java.io.IOException;
import org.apache.commons.imaging.ImageReadException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import org.apache.commons.imaging.common.BinaryFunctions;
import java.nio.ByteOrder;
import java.io.ByteArrayInputStream;
import java.util.logging.Logger;

public enum IccTagDataTypes implements IccTagDataType
{
    DESC_TYPE("descType", 1684370275) {
        @Override
        public void dump(final String prefix, final byte[] bytes) throws ImageReadException, IOException {
            try (final InputStream bis = new ByteArrayInputStream(bytes)) {
                BinaryFunctions.read4Bytes("type_signature", bis, "ICC: corrupt tag data", ByteOrder.BIG_ENDIAN);
                BinaryFunctions.read4Bytes("ignore", bis, "ICC: corrupt tag data", ByteOrder.BIG_ENDIAN);
                final int stringLength = BinaryFunctions.read4Bytes("stringLength", bis, "ICC: corrupt tag data", ByteOrder.BIG_ENDIAN);
                final String s = new String(bytes, 12, stringLength - 1, StandardCharsets.US_ASCII);
                IccTagDataTypes.LOGGER.fine(prefix + "s: '" + s + "'");
            }
        }
    }, 
    DATA_TYPE("dataType", 1684108385) {
        @Override
        public void dump(final String prefix, final byte[] bytes) throws ImageReadException, IOException {
            try (final InputStream bis = new ByteArrayInputStream(bytes)) {
                BinaryFunctions.read4Bytes("type_signature", bis, "ICC: corrupt tag data", ByteOrder.BIG_ENDIAN);
            }
        }
    }, 
    MULTI_LOCALIZED_UNICODE_TYPE("multiLocalizedUnicodeType", 1835824483) {
        @Override
        public void dump(final String prefix, final byte[] bytes) throws ImageReadException, IOException {
            try (final InputStream bis = new ByteArrayInputStream(bytes)) {
                BinaryFunctions.read4Bytes("type_signature", bis, "ICC: corrupt tag data", ByteOrder.BIG_ENDIAN);
            }
        }
    }, 
    SIGNATURE_TYPE("signatureType", 1936287520) {
        @Override
        public void dump(final String prefix, final byte[] bytes) throws ImageReadException, IOException {
            try (final InputStream bis = new ByteArrayInputStream(bytes)) {
                BinaryFunctions.read4Bytes("type_signature", bis, "ICC: corrupt tag data", ByteOrder.BIG_ENDIAN);
                BinaryFunctions.read4Bytes("ignore", bis, "ICC: corrupt tag data", ByteOrder.BIG_ENDIAN);
                final int thesignature = BinaryFunctions.read4Bytes("thesignature ", bis, "ICC: corrupt tag data", ByteOrder.BIG_ENDIAN);
                IccTagDataTypes.LOGGER.fine(prefix + "thesignature: " + Integer.toHexString(thesignature) + " (" + new String(new byte[] { (byte)(0xFF & thesignature >> 24), (byte)(0xFF & thesignature >> 16), (byte)(0xFF & thesignature >> 8), (byte)(0xFF & thesignature >> 0) }, StandardCharsets.US_ASCII) + ")");
            }
        }
    }, 
    TEXT_TYPE("textType", 1952807028) {
        @Override
        public void dump(final String prefix, final byte[] bytes) throws ImageReadException, IOException {
            try (final InputStream bis = new ByteArrayInputStream(bytes)) {
                BinaryFunctions.read4Bytes("type_signature", bis, "ICC: corrupt tag data", ByteOrder.BIG_ENDIAN);
                BinaryFunctions.read4Bytes("ignore", bis, "ICC: corrupt tag data", ByteOrder.BIG_ENDIAN);
                final String s = new String(bytes, 8, bytes.length - 8, StandardCharsets.US_ASCII);
                IccTagDataTypes.LOGGER.fine(prefix + "s: '" + s + "'");
            }
        }
    };
    
    private static final Logger LOGGER;
    public final String name;
    public final int signature;
    
    private IccTagDataTypes(final String name, final int signature) {
        this.name = name;
        this.signature = signature;
    }
    
    @Override
    public String getName() {
        return this.name;
    }
    
    @Override
    public int getSignature() {
        return this.signature;
    }
    
    static {
        LOGGER = Logger.getLogger(IccTagDataTypes.class.getName());
    }
}
