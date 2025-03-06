// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.parser;

import java.util.TimeZone;
import java.util.SimpleTimeZone;
import java.util.Calendar;
import java.io.IOException;
import org.apache.batik.xml.XMLUtilities;

public abstract class TimingParser extends AbstractParser
{
    protected static final int TIME_OFFSET = 0;
    protected static final int TIME_SYNCBASE = 1;
    protected static final int TIME_EVENTBASE = 2;
    protected static final int TIME_REPEAT = 3;
    protected static final int TIME_ACCESSKEY = 4;
    protected static final int TIME_ACCESSKEY_SVG12 = 5;
    protected static final int TIME_MEDIA_MARKER = 6;
    protected static final int TIME_WALLCLOCK = 7;
    protected static final int TIME_INDEFINITE = 8;
    protected boolean useSVG11AccessKeys;
    protected boolean useSVG12AccessKeys;
    
    public TimingParser(final boolean useSVG11AccessKeys, final boolean useSVG12AccessKeys) {
        this.useSVG11AccessKeys = useSVG11AccessKeys;
        this.useSVG12AccessKeys = useSVG12AccessKeys;
    }
    
    protected Object[] parseTimingSpecifier() throws ParseException, IOException {
        this.skipSpaces();
        boolean b = false;
        if (this.current == 92) {
            b = true;
            this.current = this.reader.read();
        }
        Object[] idValue = null;
        if (this.current == 43 || (this.current == 45 && !b) || (this.current >= 48 && this.current <= 57)) {
            idValue = new Object[] { new Integer(0), new Float(this.parseOffset()) };
        }
        else if (XMLUtilities.isXMLNameFirstCharacter((char)this.current)) {
            idValue = this.parseIDValue(b);
        }
        else {
            this.reportUnexpectedCharacterError(this.current);
        }
        return idValue;
    }
    
    protected String parseName() throws ParseException, IOException {
        final StringBuffer sb = new StringBuffer();
        boolean b;
        do {
            sb.append((char)this.current);
            this.current = this.reader.read();
            b = false;
            if (this.current == 92) {
                b = true;
                this.current = this.reader.read();
            }
        } while (XMLUtilities.isXMLNameCharacter((char)this.current) && (b || (this.current != 45 && this.current != 46)));
        return sb.toString();
    }
    
    protected Object[] parseIDValue(boolean b) throws ParseException, IOException {
        final String name = this.parseName();
        if (((name.equals("accessKey") && this.useSVG11AccessKeys) || name.equals("accesskey")) && !b) {
            if (this.current != 40) {
                this.reportUnexpectedCharacterError(this.current);
            }
            this.current = this.reader.read();
            if (this.current == -1) {
                this.reportError("end.of.stream", new Object[0]);
            }
            final char value = (char)this.current;
            this.current = this.reader.read();
            if (this.current != 41) {
                this.reportUnexpectedCharacterError(this.current);
            }
            this.current = this.reader.read();
            this.skipSpaces();
            float offset = 0.0f;
            if (this.current == 43 || this.current == 45) {
                offset = this.parseOffset();
            }
            return new Object[] { new Integer(4), new Float(offset), new Character(value) };
        }
        if (name.equals("accessKey") && this.useSVG12AccessKeys && !b) {
            if (this.current != 40) {
                this.reportUnexpectedCharacterError(this.current);
            }
            this.current = this.reader.read();
            final StringBuffer sb = new StringBuffer();
            while ((this.current >= 65 && this.current <= 90) || (this.current >= 97 && this.current <= 122) || (this.current >= 48 && this.current <= 57) || this.current == 43) {
                sb.append((char)this.current);
                this.current = this.reader.read();
            }
            if (this.current != 41) {
                this.reportUnexpectedCharacterError(this.current);
            }
            this.current = this.reader.read();
            this.skipSpaces();
            float offset2 = 0.0f;
            if (this.current == 43 || this.current == 45) {
                offset2 = this.parseOffset();
            }
            return new Object[] { new Integer(5), new Float(offset2), sb.toString() };
        }
        if (name.equals("wallclock") && !b) {
            if (this.current != 40) {
                this.reportUnexpectedCharacterError(this.current);
            }
            this.current = this.reader.read();
            this.skipSpaces();
            final Calendar wallclockValue = this.parseWallclockValue();
            this.skipSpaces();
            if (this.current != 41) {
                this.reportError("character.unexpected", new Object[] { new Integer(this.current) });
            }
            this.current = this.reader.read();
            return new Object[] { new Integer(7), wallclockValue };
        }
        if (name.equals("indefinite") && !b) {
            return new Object[] { new Integer(8) };
        }
        if (this.current != 46) {
            this.skipSpaces();
            float offset3 = 0.0f;
            if (this.current == 43 || this.current == 45) {
                offset3 = this.parseOffset();
            }
            return new Object[] { new Integer(2), new Float(offset3), null, name };
        }
        this.current = this.reader.read();
        if (this.current == 92) {
            b = true;
            this.current = this.reader.read();
        }
        if (!XMLUtilities.isXMLNameFirstCharacter((char)this.current)) {
            this.reportUnexpectedCharacterError(this.current);
        }
        final String name2 = this.parseName();
        if ((name2.equals("begin") || name2.equals("end")) && !b) {
            this.skipSpaces();
            float offset4 = 0.0f;
            if (this.current == 43 || this.current == 45) {
                offset4 = this.parseOffset();
            }
            return new Object[] { new Integer(1), new Float(offset4), name, name2 };
        }
        if (name2.equals("repeat") && !b) {
            Object o = null;
            if (this.current == 40) {
                this.current = this.reader.read();
                o = new Integer(this.parseDigits());
                if (this.current != 41) {
                    this.reportUnexpectedCharacterError(this.current);
                }
                this.current = this.reader.read();
            }
            this.skipSpaces();
            float offset5 = 0.0f;
            if (this.current == 43 || this.current == 45) {
                offset5 = this.parseOffset();
            }
            return new Object[] { new Integer(3), new Float(offset5), name, o };
        }
        if (name2.equals("marker") && !b) {
            if (this.current != 40) {
                this.reportUnexpectedCharacterError(this.current);
            }
            final String name3 = this.parseName();
            if (this.current != 41) {
                this.reportUnexpectedCharacterError(this.current);
            }
            this.current = this.reader.read();
            return new Object[] { new Integer(6), name, name3 };
        }
        this.skipSpaces();
        float offset6 = 0.0f;
        if (this.current == 43 || this.current == 45) {
            offset6 = this.parseOffset();
        }
        return new Object[] { new Integer(2), new Float(offset6), name, name2 };
    }
    
    protected float parseClockValue() throws ParseException, IOException {
        final int digits = this.parseDigits();
        float n;
        if (this.current == 58) {
            this.current = this.reader.read();
            final int digits2 = this.parseDigits();
            if (this.current == 58) {
                this.current = this.reader.read();
                n = (float)(digits * 3600 + digits2 * 60 + this.parseDigits());
            }
            else {
                n = (float)(digits * 60 + digits2);
            }
            if (this.current == 46) {
                this.current = this.reader.read();
                n += this.parseFraction();
            }
        }
        else if (this.current == 46) {
            this.current = this.reader.read();
            n = (this.parseFraction() + digits) * this.parseUnit();
        }
        else {
            n = digits * this.parseUnit();
        }
        return n;
    }
    
    protected float parseOffset() throws ParseException, IOException {
        boolean b = false;
        if (this.current == 45) {
            b = true;
            this.current = this.reader.read();
            this.skipSpaces();
        }
        else if (this.current == 43) {
            this.current = this.reader.read();
            this.skipSpaces();
        }
        if (b) {
            return -this.parseClockValue();
        }
        return this.parseClockValue();
    }
    
    protected int parseDigits() throws ParseException, IOException {
        int n = 0;
        if (this.current < 48 || this.current > 57) {
            this.reportUnexpectedCharacterError(this.current);
        }
        do {
            n = n * 10 + (this.current - 48);
            this.current = this.reader.read();
        } while (this.current >= 48 && this.current <= 57);
        return n;
    }
    
    protected float parseFraction() throws ParseException, IOException {
        float n = 0.0f;
        if (this.current < 48 || this.current > 57) {
            this.reportUnexpectedCharacterError(this.current);
        }
        float n2 = 0.1f;
        do {
            n += n2 * (this.current - 48);
            n2 *= 0.1f;
            this.current = this.reader.read();
        } while (this.current >= 48 && this.current <= 57);
        return n;
    }
    
    protected float parseUnit() throws ParseException, IOException {
        if (this.current == 104) {
            this.current = this.reader.read();
            return 3600.0f;
        }
        if (this.current == 109) {
            this.current = this.reader.read();
            if (this.current == 105) {
                this.current = this.reader.read();
                if (this.current != 110) {
                    this.reportUnexpectedCharacterError(this.current);
                }
                this.current = this.reader.read();
                return 60.0f;
            }
            if (this.current == 115) {
                this.current = this.reader.read();
                return 0.001f;
            }
            this.reportUnexpectedCharacterError(this.current);
        }
        else if (this.current == 115) {
            this.current = this.reader.read();
        }
        return 1.0f;
    }
    
    protected Calendar parseWallclockValue() throws ParseException, IOException {
        int n = 0;
        int digits = 0;
        int digits2 = 0;
        int n2 = 0;
        int digits3 = 0;
        int digits4 = 0;
        int digits5 = 0;
        int digits6 = 0;
        float fraction = 0.0f;
        boolean b = false;
        boolean b2 = false;
        boolean b3 = false;
        boolean b4 = false;
        String string = null;
        int n3 = this.parseDigits();
        Label_0443: {
            if (this.current == 45) {
                b = true;
                n = n3;
                this.current = this.reader.read();
                digits = this.parseDigits();
                if (this.current != 45) {
                    this.reportUnexpectedCharacterError(this.current);
                }
                this.current = this.reader.read();
                digits2 = this.parseDigits();
                if (this.current != 84) {
                    break Label_0443;
                }
                this.current = this.reader.read();
                n3 = this.parseDigits();
                if (this.current != 58) {
                    this.reportUnexpectedCharacterError(this.current);
                }
            }
            if (this.current == 58) {
                b2 = true;
                n2 = n3;
                this.current = this.reader.read();
                digits3 = this.parseDigits();
                if (this.current == 58) {
                    this.current = this.reader.read();
                    digits4 = this.parseDigits();
                    if (this.current == 46) {
                        this.current = this.reader.read();
                        fraction = this.parseFraction();
                    }
                }
                if (this.current == 90) {
                    b3 = true;
                    string = "UTC";
                    this.current = this.reader.read();
                }
                else if (this.current == 43 || this.current == 45) {
                    final StringBuffer sb = new StringBuffer();
                    b3 = true;
                    if (this.current == 45) {
                        b4 = true;
                        sb.append('-');
                    }
                    else {
                        sb.append('+');
                    }
                    this.current = this.reader.read();
                    digits5 = this.parseDigits();
                    if (digits5 < 10) {
                        sb.append('0');
                    }
                    sb.append(digits5);
                    if (this.current != 58) {
                        this.reportUnexpectedCharacterError(this.current);
                    }
                    sb.append(':');
                    this.current = this.reader.read();
                    digits6 = this.parseDigits();
                    if (digits6 < 10) {
                        sb.append('0');
                    }
                    sb.append(digits6);
                    string = sb.toString();
                }
            }
        }
        if (!b && !b2) {
            this.reportUnexpectedCharacterError(this.current);
        }
        Calendar calendar;
        if (b3) {
            calendar = Calendar.getInstance(new SimpleTimeZone((b4 ? -1 : 1) * (digits5 * 3600000 + digits6 * 60000), string));
        }
        else {
            calendar = Calendar.getInstance();
        }
        if (b && b2) {
            calendar.set(n, digits, digits2, n2, digits3, digits4);
        }
        else if (b) {
            calendar.set(n, digits, digits2, 0, 0, 0);
        }
        else {
            calendar.set(10, n2);
            calendar.set(12, digits3);
            calendar.set(13, digits4);
        }
        if (fraction == 0.0f) {
            calendar.set(14, (int)(fraction * 1000.0f));
        }
        else {
            calendar.set(14, 0);
        }
        return calendar;
    }
}
