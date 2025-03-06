// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.xml;

import org.apache.batik.util.EncodingUtilities;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PushbackInputStream;
import java.io.Reader;
import java.io.InputStream;

public class XMLUtilities extends XMLCharacters
{
    protected XMLUtilities() {
    }
    
    public static boolean isXMLSpace(final char c) {
        return c <= ' ' && (4294977024L >> c & 0x1L) != 0x0L;
    }
    
    public static boolean isXMLNameFirstCharacter(final char c) {
        return (XMLUtilities.NAME_FIRST_CHARACTER[c / ' '] & 1 << c % ' ') != 0x0;
    }
    
    public static boolean isXML11NameFirstCharacter(final char c) {
        return (XMLUtilities.NAME11_FIRST_CHARACTER[c / ' '] & 1 << c % ' ') != 0x0;
    }
    
    public static boolean isXMLNameCharacter(final char c) {
        return (XMLUtilities.NAME_CHARACTER[c / ' '] & 1 << c % ' ') != 0x0;
    }
    
    public static boolean isXML11NameCharacter(final char c) {
        return (XMLUtilities.NAME11_CHARACTER[c / ' '] & 1 << c % ' ') != 0x0;
    }
    
    public static boolean isXMLCharacter(final int n) {
        return (XMLUtilities.XML_CHARACTER[n >>> 5] & 1 << (n & 0x1F)) != 0x0 || (n >= 65536 && n <= 1114111);
    }
    
    public static boolean isXML11Character(final int n) {
        return (n >= 1 && n <= 55295) || (n >= 57344 && n <= 65533) || (n >= 65536 && n <= 1114111);
    }
    
    public static boolean isXMLPublicIdCharacter(final char c) {
        return c < '\u0080' && (XMLUtilities.PUBLIC_ID_CHARACTER[c / ' '] & 1 << c % ' ') != 0x0;
    }
    
    public static boolean isXMLVersionCharacter(final char c) {
        return c < '\u0080' && (XMLUtilities.VERSION_CHARACTER[c / ' '] & 1 << c % ' ') != 0x0;
    }
    
    public static boolean isXMLAlphabeticCharacter(final char c) {
        return c < '\u0080' && (XMLUtilities.ALPHABETIC_CHARACTER[c / ' '] & 1 << c % ' ') != 0x0;
    }
    
    public static Reader createXMLDocumentReader(final InputStream in) throws IOException {
        final PushbackInputStream in2 = new PushbackInputStream(in, 128);
        final byte[] array = new byte[4];
        final int read = in2.read(array);
        if (read > 0) {
            in2.unread(array, 0, read);
        }
        if (read == 4) {
            switch (array[0] & 0xFF) {
                case 0: {
                    if (array[1] == 60 && array[2] == 0 && array[3] == 63) {
                        return new InputStreamReader(in2, "UnicodeBig");
                    }
                    break;
                }
                case 60: {
                    switch (array[1] & 0xFF) {
                        case 0: {
                            if (array[2] == 63 && array[3] == 0) {
                                return new InputStreamReader(in2, "UnicodeLittle");
                            }
                            break;
                        }
                        case 63: {
                            if (array[2] == 120 && array[3] == 109) {
                                return new InputStreamReader(in2, getXMLDeclarationEncoding(createXMLDeclarationReader(in2, "UTF8"), "UTF8"));
                            }
                            break;
                        }
                    }
                    break;
                }
                case 76: {
                    if (array[1] == 111 && (array[2] & 0xFF) == 0xA7 && (array[3] & 0xFF) == 0x94) {
                        return new InputStreamReader(in2, getXMLDeclarationEncoding(createXMLDeclarationReader(in2, "CP037"), "CP037"));
                    }
                    break;
                }
                case 254: {
                    if ((array[1] & 0xFF) == 0xFF) {
                        return new InputStreamReader(in2, "Unicode");
                    }
                    break;
                }
                case 255: {
                    if ((array[1] & 0xFF) == 0xFE) {
                        return new InputStreamReader(in2, "Unicode");
                    }
                    break;
                }
            }
        }
        return new InputStreamReader(in2, "UTF8");
    }
    
    protected static Reader createXMLDeclarationReader(final PushbackInputStream pushbackInputStream, final String charsetName) throws IOException {
        final byte[] buf = new byte[128];
        final int read = pushbackInputStream.read(buf);
        if (read > 0) {
            pushbackInputStream.unread(buf, 0, read);
        }
        return new InputStreamReader(new ByteArrayInputStream(buf, 4, read), charsetName);
    }
    
    protected static String getXMLDeclarationEncoding(final Reader reader, final String s) throws IOException {
        if (reader.read() != 108) {
            return s;
        }
        if (!isXMLSpace((char)reader.read())) {
            return s;
        }
        int read;
        while (isXMLSpace((char)(read = reader.read()))) {}
        if (read != 118) {
            return s;
        }
        if (reader.read() != 101) {
            return s;
        }
        if (reader.read() != 114) {
            return s;
        }
        if (reader.read() != 115) {
            return s;
        }
        if (reader.read() != 105) {
            return s;
        }
        if (reader.read() != 111) {
            return s;
        }
        if (reader.read() != 110) {
            return s;
        }
        int n;
        for (n = reader.read(); isXMLSpace((char)n); n = reader.read()) {}
        if (n != 61) {
            return s;
        }
        int read2;
        while (isXMLSpace((char)(read2 = reader.read()))) {}
        if (read2 != 34 && read2 != 39) {
            return s;
        }
        final char c = (char)read2;
        int read3;
        do {
            read3 = reader.read();
            if (read3 == c) {
                if (!isXMLSpace((char)reader.read())) {
                    return s;
                }
                int read4;
                while (isXMLSpace((char)(read4 = reader.read()))) {}
                if (read4 != 101) {
                    return s;
                }
                if (reader.read() != 110) {
                    return s;
                }
                if (reader.read() != 99) {
                    return s;
                }
                if (reader.read() != 111) {
                    return s;
                }
                if (reader.read() != 100) {
                    return s;
                }
                if (reader.read() != 105) {
                    return s;
                }
                if (reader.read() != 110) {
                    return s;
                }
                if (reader.read() != 103) {
                    return s;
                }
                int n2;
                for (n2 = reader.read(); isXMLSpace((char)n2); n2 = reader.read()) {}
                if (n2 != 61) {
                    return s;
                }
                int read5;
                while (isXMLSpace((char)(read5 = reader.read()))) {}
                if (read5 != 34 && read5 != 39) {
                    return s;
                }
                final char c2 = (char)read5;
                final StringBuffer sb = new StringBuffer();
                while (true) {
                    final int read6 = reader.read();
                    if (read6 == -1) {
                        return s;
                    }
                    if (read6 == c2) {
                        return encodingToJavaEncoding(sb.toString(), s);
                    }
                    sb.append((char)read6);
                }
            }
        } while (isXMLVersionCharacter((char)read3));
        return s;
    }
    
    public static String encodingToJavaEncoding(final String s, final String s2) {
        final String javaEncoding = EncodingUtilities.javaEncoding(s);
        return (javaEncoding == null) ? s2 : javaEncoding;
    }
}
