// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.commons.imaging.formats.tiff;

import org.apache.commons.imaging.formats.tiff.constants.TiffTagConstants;
import java.io.IOException;
import java.util.logging.Level;
import java.io.Writer;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Date;
import org.apache.commons.imaging.ImageReadException;
import org.apache.commons.imaging.common.BinaryFunctions;
import java.nio.ByteOrder;
import org.apache.commons.imaging.formats.tiff.fieldtypes.FieldType;
import org.apache.commons.imaging.formats.tiff.taginfos.TagInfo;
import java.util.logging.Logger;

public class TiffField
{
    private static final Logger LOGGER;
    private final TagInfo tagInfo;
    private final int tag;
    private final int directoryType;
    private final FieldType fieldType;
    private final long count;
    private final long offset;
    private final byte[] value;
    private final ByteOrder byteOrder;
    private final int sortHint;
    
    public TiffField(final int tag, final int directoryType, final FieldType fieldType, final long count, final long offset, final byte[] value, final ByteOrder byteOrder, final int sortHint) {
        this.tag = tag;
        this.directoryType = directoryType;
        this.fieldType = fieldType;
        this.count = count;
        this.offset = offset;
        this.value = value;
        this.byteOrder = byteOrder;
        this.sortHint = sortHint;
        this.tagInfo = TiffTags.getTag(directoryType, tag);
    }
    
    public int getDirectoryType() {
        return this.directoryType;
    }
    
    public TagInfo getTagInfo() {
        return this.tagInfo;
    }
    
    public int getTag() {
        return this.tag;
    }
    
    public FieldType getFieldType() {
        return this.fieldType;
    }
    
    public long getCount() {
        return this.count;
    }
    
    public int getOffset() {
        return (int)this.offset;
    }
    
    public ByteOrder getByteOrder() {
        return this.byteOrder;
    }
    
    public int getSortHint() {
        return this.sortHint;
    }
    
    public boolean isLocalValue() {
        return this.count * this.fieldType.getSize() <= 4L;
    }
    
    public int getBytesLength() {
        return (int)this.count * this.fieldType.getSize();
    }
    
    public byte[] getByteArrayValue() {
        return BinaryFunctions.head(this.value, this.getBytesLength());
    }
    
    public TiffElement getOversizeValueElement() {
        if (this.isLocalValue()) {
            return null;
        }
        return new OversizeValueElement(this.getOffset(), this.value.length);
    }
    
    public String getValueDescription() {
        try {
            return this.getValueDescription(this.getValue());
        }
        catch (ImageReadException e) {
            return "Invalid value: " + e.getMessage();
        }
    }
    
    private String getValueDescription(final Object o) {
        if (o == null) {
            return null;
        }
        if (o instanceof Number) {
            return o.toString();
        }
        if (o instanceof String) {
            return "'" + o.toString().trim() + "'";
        }
        if (o instanceof Date) {
            final DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ", Locale.ENGLISH);
            return df.format((Date)o);
        }
        if (o instanceof Object[]) {
            final Object[] objects = (Object[])o;
            final StringBuilder result = new StringBuilder();
            for (int i = 0; i < objects.length; ++i) {
                final Object object = objects[i];
                if (i > 50) {
                    result.append("... (" + objects.length + ")");
                    break;
                }
                if (i > 0) {
                    result.append(", ");
                }
                result.append(object.toString());
            }
            return result.toString();
        }
        if (o instanceof short[]) {
            final short[] values = (short[])o;
            final StringBuilder result = new StringBuilder();
            for (int i = 0; i < values.length; ++i) {
                final short sval = values[i];
                if (i > 50) {
                    result.append("... (" + values.length + ")");
                    break;
                }
                if (i > 0) {
                    result.append(", ");
                }
                result.append(Short.toString(sval));
            }
            return result.toString();
        }
        if (o instanceof int[]) {
            final int[] values2 = (int[])o;
            final StringBuilder result = new StringBuilder();
            for (int i = 0; i < values2.length; ++i) {
                final int iVal = values2[i];
                if (i > 50) {
                    result.append("... (" + values2.length + ")");
                    break;
                }
                if (i > 0) {
                    result.append(", ");
                }
                result.append(Integer.toString(iVal));
            }
            return result.toString();
        }
        if (o instanceof long[]) {
            final long[] values3 = (long[])o;
            final StringBuilder result = new StringBuilder();
            for (int i = 0; i < values3.length; ++i) {
                final long lVal = values3[i];
                if (i > 50) {
                    result.append("... (" + values3.length + ")");
                    break;
                }
                if (i > 0) {
                    result.append(", ");
                }
                result.append(Long.toString(lVal));
            }
            return result.toString();
        }
        if (o instanceof double[]) {
            final double[] values4 = (double[])o;
            final StringBuilder result = new StringBuilder();
            for (int i = 0; i < values4.length; ++i) {
                final double dVal = values4[i];
                if (i > 50) {
                    result.append("... (" + values4.length + ")");
                    break;
                }
                if (i > 0) {
                    result.append(", ");
                }
                result.append(Double.toString(dVal));
            }
            return result.toString();
        }
        if (o instanceof byte[]) {
            final byte[] values5 = (byte[])o;
            final StringBuilder result = new StringBuilder();
            for (int i = 0; i < values5.length; ++i) {
                final byte bVal = values5[i];
                if (i > 50) {
                    result.append("... (" + values5.length + ")");
                    break;
                }
                if (i > 0) {
                    result.append(", ");
                }
                result.append(Byte.toString(bVal));
            }
            return result.toString();
        }
        if (o instanceof char[]) {
            final char[] values6 = (char[])o;
            final StringBuilder result = new StringBuilder();
            for (int i = 0; i < values6.length; ++i) {
                final char cVal = values6[i];
                if (i > 50) {
                    result.append("... (" + values6.length + ")");
                    break;
                }
                if (i > 0) {
                    result.append(", ");
                }
                result.append(Character.toString(cVal));
            }
            return result.toString();
        }
        if (o instanceof float[]) {
            final float[] values7 = (float[])o;
            final StringBuilder result = new StringBuilder();
            for (int i = 0; i < values7.length; ++i) {
                final float fVal = values7[i];
                if (i > 50) {
                    result.append("... (" + values7.length + ")");
                    break;
                }
                if (i > 0) {
                    result.append(", ");
                }
                result.append(Float.toString(fVal));
            }
            return result.toString();
        }
        return "Unknown: " + o.getClass().getName();
    }
    
    public void dump() {
        try (final StringWriter sw = new StringWriter();
             final PrintWriter pw = new PrintWriter(sw)) {
            this.dump(pw);
            pw.flush();
            sw.flush();
            TiffField.LOGGER.fine(sw.toString());
        }
        catch (IOException e) {
            TiffField.LOGGER.log(Level.SEVERE, e.getMessage(), e);
        }
    }
    
    public void dump(final PrintWriter pw) {
        this.dump(pw, null);
    }
    
    public void dump(final PrintWriter pw, final String prefix) {
        if (prefix != null) {
            pw.print(prefix + ": ");
        }
        pw.println(this.toString());
        pw.flush();
    }
    
    public String getDescriptionWithoutValue() {
        return this.getTag() + " (0x" + Integer.toHexString(this.getTag()) + ": " + this.getTagInfo().name + "): ";
    }
    
    @Override
    public String toString() {
        final StringBuilder result = new StringBuilder();
        result.append(this.getTag() + " (0x" + Integer.toHexString(this.getTag()) + ": " + this.getTagInfo().name + "): ");
        result.append(this.getValueDescription() + " (" + this.getCount() + " " + this.getFieldType().getName() + ")");
        return result.toString();
    }
    
    public String getTagName() {
        if (this.getTagInfo() == TiffTagConstants.TIFF_TAG_UNKNOWN) {
            return this.getTagInfo().name + " (0x" + Integer.toHexString(this.getTag()) + ")";
        }
        return this.getTagInfo().name;
    }
    
    public String getFieldTypeName() {
        return this.getFieldType().getName();
    }
    
    public Object getValue() throws ImageReadException {
        return this.getTagInfo().getValue(this);
    }
    
    public String getStringValue() throws ImageReadException {
        final Object o = this.getValue();
        if (o == null) {
            return null;
        }
        if (!(o instanceof String)) {
            throw new ImageReadException("Expected String value(" + this.getTagInfo().getDescription() + "): " + o);
        }
        return (String)o;
    }
    
    public int[] getIntArrayValue() throws ImageReadException {
        final Object o = this.getValue();
        if (o instanceof Number) {
            return new int[] { ((Number)o).intValue() };
        }
        if (o instanceof Number[]) {
            final Number[] numbers = (Number[])o;
            final int[] result = new int[numbers.length];
            for (int i = 0; i < numbers.length; ++i) {
                result[i] = numbers[i].intValue();
            }
            return result;
        }
        if (o instanceof short[]) {
            final short[] numbers2 = (short[])o;
            final int[] result = new int[numbers2.length];
            for (int i = 0; i < numbers2.length; ++i) {
                result[i] = (0xFFFF & numbers2[i]);
            }
            return result;
        }
        if (o instanceof int[]) {
            final int[] numbers3 = (int[])o;
            final int[] result = new int[numbers3.length];
            System.arraycopy(numbers3, 0, result, 0, numbers3.length);
            return result;
        }
        throw new ImageReadException("Unknown value: " + o + " for: " + this.getTagInfo().getDescription());
    }
    
    public double[] getDoubleArrayValue() throws ImageReadException {
        final Object o = this.getValue();
        if (o instanceof Number) {
            return new double[] { ((Number)o).doubleValue() };
        }
        if (o instanceof Number[]) {
            final Number[] numbers = (Number[])o;
            final double[] result = new double[numbers.length];
            for (int i = 0; i < numbers.length; ++i) {
                result[i] = numbers[i].doubleValue();
            }
            return result;
        }
        if (o instanceof short[]) {
            final short[] numbers2 = (short[])o;
            final double[] result = new double[numbers2.length];
            for (int i = 0; i < numbers2.length; ++i) {
                result[i] = numbers2[i];
            }
            return result;
        }
        if (o instanceof int[]) {
            final int[] numbers3 = (int[])o;
            final double[] result = new double[numbers3.length];
            for (int i = 0; i < numbers3.length; ++i) {
                result[i] = numbers3[i];
            }
            return result;
        }
        if (o instanceof float[]) {
            final float[] numbers4 = (float[])o;
            final double[] result = new double[numbers4.length];
            for (int i = 0; i < numbers4.length; ++i) {
                result[i] = numbers4[i];
            }
            return result;
        }
        if (o instanceof double[]) {
            final double[] numbers5 = (double[])o;
            final double[] result = new double[numbers5.length];
            System.arraycopy(numbers5, 0, result, 0, numbers5.length);
            return result;
        }
        throw new ImageReadException("Unknown value: " + o + " for: " + this.getTagInfo().getDescription());
    }
    
    public int getIntValueOrArraySum() throws ImageReadException {
        final Object o = this.getValue();
        if (o instanceof Number) {
            return ((Number)o).intValue();
        }
        if (o instanceof Number[]) {
            final Number[] numbers = (Number[])o;
            int sum = 0;
            for (final Number number : numbers) {
                sum += number.intValue();
            }
            return sum;
        }
        if (o instanceof short[]) {
            final short[] numbers2 = (short[])o;
            int sum = 0;
            for (final short number2 : numbers2) {
                sum += number2;
            }
            return sum;
        }
        if (o instanceof int[]) {
            final int[] numbers3 = (int[])o;
            int sum = 0;
            for (final int number3 : numbers3) {
                sum += number3;
            }
            return sum;
        }
        throw new ImageReadException("Unknown value: " + o + " for: " + this.getTagInfo().getDescription());
    }
    
    public int getIntValue() throws ImageReadException {
        final Object o = this.getValue();
        if (o == null) {
            throw new ImageReadException("Missing value: " + this.getTagInfo().getDescription());
        }
        return ((Number)o).intValue();
    }
    
    public double getDoubleValue() throws ImageReadException {
        final Object o = this.getValue();
        if (o == null) {
            throw new ImageReadException("Missing value: " + this.getTagInfo().getDescription());
        }
        return ((Number)o).doubleValue();
    }
    
    static {
        LOGGER = Logger.getLogger(TiffField.class.getName());
    }
    
    public final class OversizeValueElement extends TiffElement
    {
        public OversizeValueElement(final int offset, final int length) {
            super(offset, length);
        }
        
        @Override
        public String getElementDescription() {
            return "OversizeValueElement, tag: " + TiffField.this.getTagInfo().name + ", fieldType: " + TiffField.this.getFieldType().getName();
        }
    }
}
