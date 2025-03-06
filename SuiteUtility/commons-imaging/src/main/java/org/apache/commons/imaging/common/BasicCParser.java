// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.commons.imaging.common;

import java.io.ByteArrayOutputStream;
import java.util.Map;
import java.io.IOException;
import org.apache.commons.imaging.ImageReadException;
import java.io.InputStream;
import java.io.ByteArrayInputStream;
import java.io.PushbackInputStream;

public class BasicCParser
{
    private final PushbackInputStream is;
    
    public BasicCParser(final ByteArrayInputStream is) {
        this.is = new PushbackInputStream(is);
    }
    
    public String nextToken() throws IOException, ImageReadException {
        boolean inString = false;
        boolean inIdentifier = false;
        boolean hadBackSlash = false;
        final StringBuilder token = new StringBuilder();
        for (int c = this.is.read(); c != -1; c = this.is.read()) {
            if (inString) {
                if (c == 92) {
                    token.append('\\');
                    hadBackSlash = !hadBackSlash;
                }
                else if (c == 34) {
                    token.append('\"');
                    if (!hadBackSlash) {
                        return token.toString();
                    }
                    hadBackSlash = false;
                }
                else {
                    if (c == 13 || c == 10) {
                        throw new ImageReadException("Unterminated string in XPM file");
                    }
                    token.append((char)c);
                    hadBackSlash = false;
                }
            }
            else if (inIdentifier) {
                if (!Character.isLetterOrDigit(c) && c != 95) {
                    this.is.unread(c);
                    return token.toString();
                }
                token.append((char)c);
            }
            else if (c == 34) {
                token.append('\"');
                inString = true;
            }
            else if (Character.isLetterOrDigit(c) || c == 95) {
                token.append((char)c);
                inIdentifier = true;
            }
            else {
                if (c == 123 || c == 125 || c == 91 || c == 93 || c == 42 || c == 59 || c == 61 || c == 44) {
                    token.append((char)c);
                    return token.toString();
                }
                if (c != 32 && c != 9 && c != 13) {
                    if (c != 10) {
                        throw new ImageReadException("Unhandled/invalid character '" + (char)c + "' found in XPM file");
                    }
                }
            }
        }
        if (inIdentifier) {
            return token.toString();
        }
        if (inString) {
            throw new ImageReadException("Unterminated string ends XMP file");
        }
        return null;
    }
    
    public static ByteArrayOutputStream preprocess(final InputStream is, final StringBuilder firstComment, final Map<String, String> defines) throws IOException, ImageReadException {
        boolean inSingleQuotes = false;
        boolean inString = false;
        boolean inComment = false;
        boolean inDirective = false;
        boolean hadSlash = false;
        boolean hadStar = false;
        boolean hadBackSlash = false;
        final ByteArrayOutputStream out = new ByteArrayOutputStream();
        boolean seenFirstComment = firstComment == null;
        final StringBuilder directiveBuffer = new StringBuilder();
        for (int c = is.read(); c != -1; c = is.read()) {
            if (inComment) {
                if (c == 42) {
                    if (hadStar && !seenFirstComment) {
                        firstComment.append('*');
                    }
                    hadStar = true;
                }
                else if (c == 47) {
                    if (hadStar) {
                        hadStar = false;
                        inComment = false;
                        seenFirstComment = true;
                    }
                    else if (!seenFirstComment) {
                        firstComment.append((char)c);
                    }
                }
                else {
                    if (hadStar && !seenFirstComment) {
                        firstComment.append('*');
                    }
                    hadStar = false;
                    if (!seenFirstComment) {
                        firstComment.append((char)c);
                    }
                }
            }
            else if (inSingleQuotes) {
                if (c == 92) {
                    if (hadBackSlash) {
                        out.write(92);
                        out.write(92);
                        hadBackSlash = false;
                    }
                    else {
                        hadBackSlash = true;
                    }
                }
                else if (c == 39) {
                    if (hadBackSlash) {
                        out.write(92);
                        hadBackSlash = false;
                    }
                    else {
                        inSingleQuotes = false;
                    }
                    out.write(39);
                }
                else {
                    if (c == 13 || c == 10) {
                        throw new ImageReadException("Unterminated single quote in file");
                    }
                    if (hadBackSlash) {
                        out.write(92);
                        hadBackSlash = false;
                    }
                    out.write(c);
                }
            }
            else if (inString) {
                if (c == 92) {
                    if (hadBackSlash) {
                        out.write(92);
                        out.write(92);
                        hadBackSlash = false;
                    }
                    else {
                        hadBackSlash = true;
                    }
                }
                else if (c == 34) {
                    if (hadBackSlash) {
                        out.write(92);
                        hadBackSlash = false;
                    }
                    else {
                        inString = false;
                    }
                    out.write(34);
                }
                else {
                    if (c == 13 || c == 10) {
                        throw new ImageReadException("Unterminated string in file");
                    }
                    if (hadBackSlash) {
                        out.write(92);
                        hadBackSlash = false;
                    }
                    out.write(c);
                }
            }
            else if (inDirective) {
                if (c == 13 || c == 10) {
                    inDirective = false;
                    final String[] tokens = tokenizeRow(directiveBuffer.toString());
                    if (tokens.length < 2 || tokens.length > 3) {
                        throw new ImageReadException("Bad preprocessor directive");
                    }
                    if (!tokens[0].equals("define")) {
                        throw new ImageReadException("Invalid/unsupported preprocessor directive '" + tokens[0] + "'");
                    }
                    defines.put(tokens[1], (tokens.length == 3) ? tokens[2] : null);
                    directiveBuffer.setLength(0);
                }
                else {
                    directiveBuffer.append((char)c);
                }
            }
            else if (c == 47) {
                if (hadSlash) {
                    out.write(47);
                }
                hadSlash = true;
            }
            else if (c == 42) {
                if (hadSlash) {
                    inComment = true;
                    hadSlash = false;
                }
                else {
                    out.write(c);
                }
            }
            else if (c == 39) {
                if (hadSlash) {
                    out.write(47);
                }
                hadSlash = false;
                out.write(c);
                inSingleQuotes = true;
            }
            else if (c == 34) {
                if (hadSlash) {
                    out.write(47);
                }
                hadSlash = false;
                out.write(c);
                inString = true;
            }
            else if (c == 35) {
                if (defines == null) {
                    throw new ImageReadException("Unexpected preprocessor directive");
                }
                inDirective = true;
            }
            else {
                if (hadSlash) {
                    out.write(47);
                }
                hadSlash = false;
                out.write(c);
                if (c != 32 && c != 9 && c != 13 && c != 10) {
                    seenFirstComment = true;
                }
            }
        }
        if (hadSlash) {
            out.write(47);
        }
        if (hadStar) {
            out.write(42);
        }
        if (inString) {
            throw new ImageReadException("Unterminated string at the end of file");
        }
        if (inComment) {
            throw new ImageReadException("Unterminated comment at the end of file");
        }
        return out;
    }
    
    public static String[] tokenizeRow(final String row) {
        final String[] tokens = row.split("[ \t]");
        int numLiveTokens = 0;
        for (final String token : tokens) {
            if (token != null && token.length() > 0) {
                ++numLiveTokens;
            }
        }
        final String[] liveTokens = new String[numLiveTokens];
        int next = 0;
        for (final String token2 : tokens) {
            if (token2 != null && token2.length() > 0) {
                liveTokens[next++] = token2;
            }
        }
        return liveTokens;
    }
    
    public static void unescapeString(final StringBuilder stringBuilder, final String string) throws ImageReadException {
        if (string.length() < 2) {
            throw new ImageReadException("Parsing XPM file failed, string is too short");
        }
        if (string.charAt(0) != '\"' || string.charAt(string.length() - 1) != '\"') {
            throw new ImageReadException("Parsing XPM file failed, string not surrounded by '\"'");
        }
        boolean hadBackSlash = false;
        for (int i = 1; i < string.length() - 1; ++i) {
            final char c = string.charAt(i);
            if (hadBackSlash) {
                if (c == '\\') {
                    stringBuilder.append('\\');
                }
                else if (c == '\"') {
                    stringBuilder.append('\"');
                }
                else if (c == '\'') {
                    stringBuilder.append('\'');
                }
                else if (c == 'x') {
                    if (i + 2 >= string.length()) {
                        throw new ImageReadException("Parsing XPM file failed, hex constant in string too short");
                    }
                    final char hex1 = string.charAt(i + 1);
                    final char hex2 = string.charAt(i + 2);
                    i += 2;
                    int constant;
                    try {
                        constant = Integer.parseInt(Character.toString(hex1) + Character.toString(hex2), 16);
                    }
                    catch (NumberFormatException nfe) {
                        throw new ImageReadException("Parsing XPM file failed, hex constant invalid", nfe);
                    }
                    stringBuilder.append((char)constant);
                }
                else if (c == '0' || c == '1' || c == '2' || c == '3' || c == '4' || c == '5' || c == '6' || c == '7') {
                    int length = 1;
                    if (i + 1 < string.length() && '0' <= string.charAt(i + 1) && string.charAt(i + 1) <= '7') {
                        ++length;
                    }
                    if (i + 2 < string.length() && '0' <= string.charAt(i + 2) && string.charAt(i + 2) <= '7') {
                        ++length;
                    }
                    int constant2 = 0;
                    for (int j = 0; j < length; ++j) {
                        constant2 *= 8;
                        constant2 += string.charAt(i + j) - '0';
                    }
                    i += length - 1;
                    stringBuilder.append((char)constant2);
                }
                else if (c == 'a') {
                    stringBuilder.append('\u0007');
                }
                else if (c == 'b') {
                    stringBuilder.append('\b');
                }
                else if (c == 'f') {
                    stringBuilder.append('\f');
                }
                else if (c == 'n') {
                    stringBuilder.append('\n');
                }
                else if (c == 'r') {
                    stringBuilder.append('\r');
                }
                else if (c == 't') {
                    stringBuilder.append('\t');
                }
                else {
                    if (c != 'v') {
                        throw new ImageReadException("Parsing XPM file failed, invalid escape sequence");
                    }
                    stringBuilder.append('\u000b');
                }
                hadBackSlash = false;
            }
            else if (c == '\\') {
                hadBackSlash = true;
            }
            else {
                if (c == '\"') {
                    throw new ImageReadException("Parsing XPM file failed, extra '\"' found in string");
                }
                stringBuilder.append(c);
            }
        }
        if (hadBackSlash) {
            throw new ImageReadException("Parsing XPM file failed, unterminated escape sequence found in string");
        }
    }
}
