// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.util;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

public class ParsedURLDataProtocolHandler extends AbstractParsedURLProtocolHandler
{
    static final String DATA_PROTOCOL = "data";
    static final String BASE64 = "base64";
    static final String CHARSET = "charset";
    
    public ParsedURLDataProtocolHandler() {
        super("data");
    }
    
    public ParsedURLData parseURL(final ParsedURL parsedURL, final String s) {
        return this.parseURL(s);
    }
    
    public ParsedURLData parseURL(String substring) {
        final DataParsedURLData dataParsedURLData = new DataParsedURLData();
        int n = 0;
        final int length = substring.length();
        final int index = substring.indexOf(35);
        dataParsedURLData.ref = null;
        if (index != -1) {
            if (index + 1 < length) {
                dataParsedURLData.ref = substring.substring(index + 1);
            }
            substring = substring.substring(0, index);
            substring.length();
        }
        final int index2 = substring.indexOf(58);
        if (index2 != -1) {
            dataParsedURLData.protocol = substring.substring(n, index2);
            if (dataParsedURLData.protocol.indexOf(47) == -1) {
                n = index2 + 1;
            }
            else {
                dataParsedURLData.protocol = null;
                n = 0;
            }
        }
        final int index3 = substring.indexOf(44, n);
        if (index3 != -1 && index3 != n) {
            dataParsedURLData.host = substring.substring(n, index3);
            n = index3 + 1;
            final int lastIndex = dataParsedURLData.host.lastIndexOf(59);
            if (lastIndex == -1 || lastIndex == dataParsedURLData.host.length()) {
                dataParsedURLData.contentType = dataParsedURLData.host;
            }
            else {
                final String substring2 = dataParsedURLData.host.substring(lastIndex + 1);
                if (substring2.indexOf(61) == -1) {
                    dataParsedURLData.contentEncoding = substring2;
                    dataParsedURLData.contentType = dataParsedURLData.host.substring(0, lastIndex);
                }
                else {
                    dataParsedURLData.contentType = dataParsedURLData.host;
                }
                final int index4 = dataParsedURLData.contentType.indexOf(59, 0);
                if (index4 != -1) {
                    int endIndex;
                    for (int i = index4 + 1; i < dataParsedURLData.contentType.length(); i = endIndex + 1) {
                        endIndex = dataParsedURLData.contentType.indexOf(59, i);
                        if (endIndex == -1) {
                            endIndex = dataParsedURLData.contentType.length();
                        }
                        final String substring3 = dataParsedURLData.contentType.substring(i, endIndex);
                        final int index5 = substring3.indexOf(61);
                        if (index5 != -1 && "charset".equals(substring3.substring(0, index5))) {
                            dataParsedURLData.charset = substring3.substring(index5 + 1);
                        }
                    }
                }
            }
        }
        if (n < substring.length()) {
            dataParsedURLData.path = substring.substring(n);
        }
        return dataParsedURLData;
    }
    
    static class DataParsedURLData extends ParsedURLData
    {
        String charset;
        
        public boolean complete() {
            return this.path != null;
        }
        
        public String getPortStr() {
            String string = "data:";
            if (this.host != null) {
                string += this.host;
            }
            return string + ",";
        }
        
        public String toString() {
            String s = this.getPortStr();
            if (this.path != null) {
                s += this.path;
            }
            if (this.ref != null) {
                s = s + '#' + this.ref;
            }
            return s;
        }
        
        public String getContentType(final String s) {
            return this.contentType;
        }
        
        public String getContentEncoding(final String s) {
            return this.contentEncoding;
        }
        
        protected InputStream openStreamInternal(final String s, final Iterator iterator, final Iterator iterator2) throws IOException {
            this.stream = decode(this.path);
            if ("base64".equals(this.contentEncoding)) {
                this.stream = new Base64DecodeStream(this.stream);
            }
            return this.stream;
        }
        
        public static InputStream decode(final String s) {
            final int length = s.length();
            final byte[] buf = new byte[length];
            int length2 = 0;
            for (int i = 0; i < length; ++i) {
                final char char1 = s.charAt(i);
                switch (char1) {
                    default: {
                        buf[length2++] = (byte)char1;
                        break;
                    }
                    case 37: {
                        if (i + 2 < length) {
                            i += 2;
                            final char char2 = s.charAt(i - 1);
                            byte b;
                            if (char2 >= '0' && char2 <= '9') {
                                b = (byte)(char2 - '0');
                            }
                            else if (char2 >= 'a' && char2 <= 'z') {
                                b = (byte)(char2 - 'a' + 10);
                            }
                            else {
                                if (char2 < 'A' || char2 > 'Z') {
                                    break;
                                }
                                b = (byte)(char2 - 'A' + 10);
                            }
                            final byte b2 = (byte)(b * 16);
                            final char char3 = s.charAt(i);
                            byte b3;
                            if (char3 >= '0' && char3 <= '9') {
                                b3 = (byte)(b2 + (byte)(char3 - '0'));
                            }
                            else if (char3 >= 'a' && char3 <= 'z') {
                                b3 = (byte)(b2 + (byte)(char3 - 'a' + 10));
                            }
                            else {
                                if (char3 < 'A' || char3 > 'Z') {
                                    break;
                                }
                                b3 = (byte)(b2 + (byte)(char3 - 'A' + 10));
                            }
                            buf[length2++] = b3;
                            break;
                        }
                        break;
                    }
                }
            }
            return new ByteArrayInputStream(buf, 0, length2);
        }
    }
}
