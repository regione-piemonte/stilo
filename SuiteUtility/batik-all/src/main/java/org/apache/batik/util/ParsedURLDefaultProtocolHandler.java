// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.util;

import java.net.MalformedURLException;
import java.net.URL;

public class ParsedURLDefaultProtocolHandler extends AbstractParsedURLProtocolHandler
{
    public ParsedURLDefaultProtocolHandler() {
        super(null);
    }
    
    protected ParsedURLDefaultProtocolHandler(final String s) {
        super(s);
    }
    
    protected ParsedURLData constructParsedURLData() {
        return new ParsedURLData();
    }
    
    protected ParsedURLData constructParsedURLData(final URL url) {
        return new ParsedURLData(url);
    }
    
    public ParsedURLData parseURL(String substring) {
        try {
            return this.constructParsedURLData(new URL(substring));
        }
        catch (MalformedURLException ex) {
            final ParsedURLData constructParsedURLData = this.constructParsedURLData();
            if (substring == null) {
                return constructParsedURLData;
            }
            int n = 0;
            int n2 = substring.length();
            final int index = substring.indexOf(35);
            constructParsedURLData.ref = null;
            if (index != -1) {
                if (index + 1 < n2) {
                    constructParsedURLData.ref = substring.substring(index + 1);
                }
                substring = substring.substring(0, index);
                n2 = substring.length();
            }
            if (n2 == 0) {
                return constructParsedURLData;
            }
            int endIndex;
            int n3;
            for (endIndex = 0, n3 = substring.charAt(endIndex); n3 == 45 || n3 == 43 || n3 == 46 || (n3 >= 97 && n3 <= 122) || (n3 >= 65 && n3 <= 90); n3 = substring.charAt(endIndex)) {
                if (++endIndex == n2) {
                    n3 = 0;
                    break;
                }
            }
            if (n3 == 58) {
                constructParsedURLData.protocol = substring.substring(n, endIndex).toLowerCase();
                n = endIndex + 1;
            }
            final int index2 = substring.indexOf(47);
            if (index2 == -1 || (n + 2 < n2 && substring.charAt(n) == '/' && substring.charAt(n + 1) == '/')) {
                if (index2 != -1) {
                    n += 2;
                }
                final int index3 = substring.indexOf(47, n);
                String host;
                if (index3 == -1) {
                    host = substring.substring(n);
                }
                else {
                    host = substring.substring(n, index3);
                }
                final int n4 = index3;
                final int index4 = host.indexOf(58);
                constructParsedURLData.port = -1;
                if (index4 == -1) {
                    if (host.length() == 0) {
                        constructParsedURLData.host = null;
                    }
                    else {
                        constructParsedURLData.host = host;
                    }
                }
                else {
                    if (index4 == 0) {
                        constructParsedURLData.host = null;
                    }
                    else {
                        constructParsedURLData.host = host.substring(0, index4);
                    }
                    if (index4 + 1 < host.length()) {
                        final String substring2 = host.substring(index4 + 1);
                        try {
                            constructParsedURLData.port = Integer.parseInt(substring2);
                        }
                        catch (NumberFormatException ex2) {}
                    }
                }
                if ((constructParsedURLData.host == null || constructParsedURLData.host.indexOf(46) == -1) && constructParsedURLData.port == -1) {
                    constructParsedURLData.host = null;
                }
                else {
                    n = n4;
                }
            }
            if (n == -1 || n >= n2) {
                return constructParsedURLData;
            }
            constructParsedURLData.path = substring.substring(n);
            return constructParsedURLData;
        }
    }
    
    public static String unescapeStr(final String s) {
        int i = s.indexOf(37);
        if (i == -1) {
            return s;
        }
        int n = 0;
        final StringBuffer sb = new StringBuffer();
        while (i != -1) {
            if (i != n) {
                sb.append(s.substring(n, i));
            }
            if (i + 2 >= s.length()) {
                break;
            }
            n = i + 3;
            i = s.indexOf(37, n);
            final int charToHex = charToHex(s.charAt(i + 1));
            final int charToHex2 = charToHex(s.charAt(i + 1));
            if (charToHex == -1) {
                continue;
            }
            if (charToHex2 == -1) {
                continue;
            }
            sb.append((char)(charToHex << 4 | charToHex2));
        }
        return sb.toString();
    }
    
    public static int charToHex(final int n) {
        switch (n) {
            case 48:
            case 49:
            case 50:
            case 51:
            case 52:
            case 53:
            case 54:
            case 55:
            case 56:
            case 57: {
                return n - 48;
            }
            case 65:
            case 97: {
                return 10;
            }
            case 66:
            case 98: {
                return 11;
            }
            case 67:
            case 99: {
                return 12;
            }
            case 68:
            case 100: {
                return 13;
            }
            case 69:
            case 101: {
                return 14;
            }
            case 70:
            case 102: {
                return 15;
            }
            default: {
                return -1;
            }
        }
    }
    
    public ParsedURLData parseURL(final ParsedURL parsedURL, String substring) {
        if (substring.length() == 0) {
            return parsedURL.data;
        }
        int beginIndex = 0;
        final int length = substring.length();
        if (length == 0) {
            return parsedURL.data;
        }
        int n;
        for (n = substring.charAt(beginIndex); n == 45 || n == 43 || n == 46 || (n >= 97 && n <= 122) || (n >= 65 && n <= 90); n = substring.charAt(beginIndex)) {
            if (++beginIndex == length) {
                n = 0;
                break;
            }
        }
        String lowerCase = null;
        if (n == 58) {
            lowerCase = substring.substring(0, beginIndex).toLowerCase();
        }
        if (lowerCase != null) {
            if (!lowerCase.equals(parsedURL.getProtocol())) {
                return this.parseURL(substring);
            }
            if (++beginIndex == substring.length()) {
                return this.parseURL(substring);
            }
            if (substring.charAt(beginIndex) == '/') {
                return this.parseURL(substring);
            }
            substring = substring.substring(beginIndex);
        }
        if (substring.startsWith("/")) {
            if (substring.length() > 1 && substring.charAt(1) == '/') {
                return this.parseURL(parsedURL.getProtocol() + ":" + substring);
            }
            return this.parseURL(parsedURL.getPortStr() + substring);
        }
        else {
            if (substring.startsWith("#")) {
                String s = parsedURL.getPortStr();
                if (parsedURL.getPath() != null) {
                    s += parsedURL.getPath();
                }
                return this.parseURL(s + substring);
            }
            String path = parsedURL.getPath();
            if (path == null) {
                path = "";
            }
            final int lastIndex = path.lastIndexOf(47);
            String substring2;
            if (lastIndex == -1) {
                substring2 = "";
            }
            else {
                substring2 = path.substring(0, lastIndex + 1);
            }
            return this.parseURL(parsedURL.getPortStr() + substring2 + substring);
        }
    }
}
