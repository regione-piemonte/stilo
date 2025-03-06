// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.util;

import java.util.LinkedList;
import java.net.URLConnection;
import java.net.HttpURLConnection;
import java.util.Iterator;
import java.net.MalformedURLException;
import java.net.URL;
import java.io.IOException;
import java.io.FilterInputStream;
import java.util.zip.ZipException;
import java.util.zip.InflaterInputStream;
import java.util.zip.GZIPInputStream;
import java.io.BufferedInputStream;
import java.io.InputStream;
import java.util.List;

public class ParsedURLData
{
    protected static final String HTTP_USER_AGENT_HEADER = "User-Agent";
    protected static final String HTTP_ACCEPT_HEADER = "Accept";
    protected static final String HTTP_ACCEPT_LANGUAGE_HEADER = "Accept-Language";
    protected static final String HTTP_ACCEPT_ENCODING_HEADER = "Accept-Encoding";
    protected static List acceptedEncodings;
    public static final byte[] GZIP_MAGIC;
    public String protocol;
    public String host;
    public int port;
    public String path;
    public String ref;
    public String contentType;
    public String contentEncoding;
    public InputStream stream;
    public boolean hasBeenOpened;
    protected String contentTypeMediaType;
    protected String contentTypeCharset;
    
    public static InputStream checkGZIP(InputStream inputStream) throws IOException {
        if (!inputStream.markSupported()) {
            inputStream = new BufferedInputStream(inputStream);
        }
        final byte[] array = new byte[2];
        try {
            inputStream.mark(2);
            inputStream.read(array);
            inputStream.reset();
        }
        catch (Exception ex) {
            inputStream.reset();
            return inputStream;
        }
        if (array[0] == ParsedURLData.GZIP_MAGIC[0] && array[1] == ParsedURLData.GZIP_MAGIC[1]) {
            return new GZIPInputStream(inputStream);
        }
        if ((array[0] & 0xF) == 0x8 && array[0] >>> 4 <= 7 && ((array[0] & 0xFF) * 256 + (array[1] & 0xFF)) % 31 == 0) {
            try {
                inputStream.mark(100);
                FilterInputStream in = new InflaterInputStream(inputStream);
                if (!in.markSupported()) {
                    in = new BufferedInputStream(in);
                }
                in.mark(2);
                in.read(array);
                inputStream.reset();
                return new InflaterInputStream(inputStream);
            }
            catch (ZipException ex2) {
                inputStream.reset();
                return inputStream;
            }
        }
        return inputStream;
    }
    
    public ParsedURLData() {
        this.protocol = null;
        this.host = null;
        this.port = -1;
        this.path = null;
        this.ref = null;
        this.contentType = null;
        this.contentEncoding = null;
        this.stream = null;
        this.hasBeenOpened = false;
    }
    
    public ParsedURLData(final URL url) {
        this.protocol = null;
        this.host = null;
        this.port = -1;
        this.path = null;
        this.ref = null;
        this.contentType = null;
        this.contentEncoding = null;
        this.stream = null;
        this.hasBeenOpened = false;
        this.protocol = url.getProtocol();
        if (this.protocol != null && this.protocol.length() == 0) {
            this.protocol = null;
        }
        this.host = url.getHost();
        if (this.host != null && this.host.length() == 0) {
            this.host = null;
        }
        this.port = url.getPort();
        this.path = url.getFile();
        if (this.path != null && this.path.length() == 0) {
            this.path = null;
        }
        this.ref = url.getRef();
        if (this.ref != null && this.ref.length() == 0) {
            this.ref = null;
        }
    }
    
    protected URL buildURL() throws MalformedURLException {
        if (this.protocol == null || this.host == null) {
            return new URL(this.toString());
        }
        String path = "";
        if (this.path != null) {
            path = this.path;
        }
        if (this.port == -1) {
            return new URL(this.protocol, this.host, path);
        }
        return new URL(this.protocol, this.host, this.port, path);
    }
    
    public int hashCode() {
        int port = this.port;
        if (this.protocol != null) {
            port ^= this.protocol.hashCode();
        }
        if (this.host != null) {
            port ^= this.host.hashCode();
        }
        if (this.path != null) {
            final int length = this.path.length();
            if (length > 20) {
                port ^= this.path.substring(length - 20).hashCode();
            }
            else {
                port ^= this.path.hashCode();
            }
        }
        if (this.ref != null) {
            final int length2 = this.ref.length();
            if (length2 > 20) {
                port ^= this.ref.substring(length2 - 20).hashCode();
            }
            else {
                port ^= this.ref.hashCode();
            }
        }
        return port;
    }
    
    public boolean equals(final Object o) {
        if (o == null) {
            return false;
        }
        if (!(o instanceof ParsedURLData)) {
            return false;
        }
        final ParsedURLData parsedURLData = (ParsedURLData)o;
        if (parsedURLData.port != this.port) {
            return false;
        }
        if (parsedURLData.protocol == null) {
            if (this.protocol != null) {
                return false;
            }
        }
        else {
            if (this.protocol == null) {
                return false;
            }
            if (!parsedURLData.protocol.equals(this.protocol)) {
                return false;
            }
        }
        if (parsedURLData.host == null) {
            if (this.host != null) {
                return false;
            }
        }
        else {
            if (this.host == null) {
                return false;
            }
            if (!parsedURLData.host.equals(this.host)) {
                return false;
            }
        }
        if (parsedURLData.ref == null) {
            if (this.ref != null) {
                return false;
            }
        }
        else {
            if (this.ref == null) {
                return false;
            }
            if (!parsedURLData.ref.equals(this.ref)) {
                return false;
            }
        }
        if (parsedURLData.path == null) {
            if (this.path != null) {
                return false;
            }
        }
        else {
            if (this.path == null) {
                return false;
            }
            if (!parsedURLData.path.equals(this.path)) {
                return false;
            }
        }
        return true;
    }
    
    public String getContentType(final String s) {
        if (this.contentType != null) {
            return this.contentType;
        }
        if (!this.hasBeenOpened) {
            try {
                this.openStreamInternal(s, null, null);
            }
            catch (IOException ex) {}
        }
        return this.contentType;
    }
    
    public String getContentTypeMediaType(final String s) {
        if (this.contentTypeMediaType != null) {
            return this.contentTypeMediaType;
        }
        this.extractContentTypeParts(s);
        return this.contentTypeMediaType;
    }
    
    public String getContentTypeCharset(final String s) {
        if (this.contentTypeMediaType != null) {
            return this.contentTypeCharset;
        }
        this.extractContentTypeParts(s);
        return this.contentTypeCharset;
    }
    
    public boolean hasContentTypeParameter(final String s, final String s2) {
        this.getContentType(s);
        if (this.contentType == null) {
            return false;
        }
        int i = 0;
        final int length = this.contentType.length();
        final int length2 = s2.length();
    Label_0081:
        while (i < length) {
            switch (this.contentType.charAt(i)) {
                case ' ':
                case ';': {
                    break Label_0081;
                }
                default: {
                    ++i;
                    continue;
                }
            }
        }
        if (i == length) {
            this.contentTypeMediaType = this.contentType;
        }
        else {
            this.contentTypeMediaType = this.contentType.substring(0, i);
        }
    Label_0111:
        while (true) {
            if (i < length && this.contentType.charAt(i) != ';') {
                ++i;
            }
            else {
                if (i == length) {
                    return false;
                }
                ++i;
                while (i < length && this.contentType.charAt(i) == ' ') {
                    ++i;
                }
                if (i >= length - length2 - 1) {
                    return false;
                }
                for (int j = 0; j < length2; ++j) {
                    if (this.contentType.charAt(i++) != s2.charAt(j)) {
                        continue Label_0111;
                    }
                }
                if (this.contentType.charAt(i) == '=') {
                    return true;
                }
                continue;
            }
        }
    }
    
    protected void extractContentTypeParts(final String s) {
        this.getContentType(s);
        if (this.contentType == null) {
            return;
        }
        int i = 0;
        final int length = this.contentType.length();
    Label_0073:
        while (i < length) {
            switch (this.contentType.charAt(i)) {
                case ' ':
                case ';': {
                    break Label_0073;
                }
                default: {
                    ++i;
                    continue;
                }
            }
        }
        if (i == length) {
            this.contentTypeMediaType = this.contentType;
        }
        else {
            this.contentTypeMediaType = this.contentType.substring(0, i);
        }
        while (true) {
            if (i < length && this.contentType.charAt(i) != ';') {
                ++i;
            }
            else {
                if (i == length) {
                    return;
                }
                ++i;
                while (i < length && this.contentType.charAt(i) == ' ') {
                    ++i;
                }
                if (i >= length - 8) {
                    return;
                }
                if (this.contentType.charAt(i++) != 'c') {
                    continue;
                }
                if (this.contentType.charAt(i++) != 'h') {
                    continue;
                }
                if (this.contentType.charAt(i++) != 'a') {
                    continue;
                }
                if (this.contentType.charAt(i++) != 'r') {
                    continue;
                }
                if (this.contentType.charAt(i++) != 's') {
                    continue;
                }
                if (this.contentType.charAt(i++) != 'e') {
                    continue;
                }
                if (this.contentType.charAt(i++) != 't') {
                    continue;
                }
                if (this.contentType.charAt(i++) != '=') {
                    continue;
                }
                final int beginIndex = i;
            Label_0369:
                while (i < length) {
                    switch (this.contentType.charAt(i)) {
                        case ' ':
                        case ';': {
                            break Label_0369;
                        }
                        default: {
                            ++i;
                            continue;
                        }
                    }
                }
                this.contentTypeCharset = this.contentType.substring(beginIndex, i);
            }
        }
    }
    
    public String getContentEncoding(final String s) {
        if (this.contentEncoding != null) {
            return this.contentEncoding;
        }
        if (!this.hasBeenOpened) {
            try {
                this.openStreamInternal(s, null, null);
            }
            catch (IOException ex) {}
        }
        return this.contentEncoding;
    }
    
    public boolean complete() {
        try {
            this.buildURL();
        }
        catch (MalformedURLException ex) {
            return false;
        }
        return true;
    }
    
    public InputStream openStream(final String s, final Iterator iterator) throws IOException {
        final InputStream openStreamInternal = this.openStreamInternal(s, iterator, ParsedURLData.acceptedEncodings.iterator());
        if (openStreamInternal == null) {
            return null;
        }
        this.stream = null;
        return checkGZIP(openStreamInternal);
    }
    
    public InputStream openStreamRaw(final String s, final Iterator iterator) throws IOException {
        final InputStream openStreamInternal = this.openStreamInternal(s, iterator, null);
        this.stream = null;
        return openStreamInternal;
    }
    
    protected InputStream openStreamInternal(final String value, final Iterator iterator, final Iterator iterator2) throws IOException {
        if (this.stream != null) {
            return this.stream;
        }
        this.hasBeenOpened = true;
        URL buildURL;
        try {
            buildURL = this.buildURL();
        }
        catch (MalformedURLException ex) {
            throw new IOException("Unable to make sense of URL for connection");
        }
        if (buildURL == null) {
            return null;
        }
        final URLConnection openConnection = buildURL.openConnection();
        if (openConnection instanceof HttpURLConnection) {
            if (value != null) {
                openConnection.setRequestProperty("User-Agent", value);
            }
            if (iterator != null) {
                String value2 = "";
                while (iterator.hasNext()) {
                    value2 += iterator.next();
                    if (iterator.hasNext()) {
                        value2 += ",";
                    }
                }
                openConnection.setRequestProperty("Accept", value2);
            }
            if (iterator2 != null) {
                String value3 = "";
                while (iterator2.hasNext()) {
                    value3 += iterator2.next();
                    if (iterator2.hasNext()) {
                        value3 += ",";
                    }
                }
                openConnection.setRequestProperty("Accept-Encoding", value3);
            }
            this.contentType = openConnection.getContentType();
            this.contentEncoding = openConnection.getContentEncoding();
        }
        return this.stream = openConnection.getInputStream();
    }
    
    public String getPortStr() {
        String s = "";
        if (this.protocol != null) {
            s = s + this.protocol + ":";
        }
        if (this.host != null || this.port != -1) {
            s += "//";
            if (this.host != null) {
                s += this.host;
            }
            if (this.port != -1) {
                s = s + ":" + this.port;
            }
        }
        return s;
    }
    
    protected boolean sameFile(final ParsedURLData parsedURLData) {
        return this == parsedURLData || (this.port == parsedURLData.port && (this.path == parsedURLData.path || (this.path != null && this.path.equals(parsedURLData.path))) && (this.host == parsedURLData.host || (this.host != null && this.host.equals(parsedURLData.host))) && (this.protocol == parsedURLData.protocol || (this.protocol != null && this.protocol.equals(parsedURLData.protocol))));
    }
    
    public String toString() {
        String s = this.getPortStr();
        if (this.path != null) {
            s += this.path;
        }
        if (this.ref != null) {
            s = s + "#" + this.ref;
        }
        return s;
    }
    
    static {
        (ParsedURLData.acceptedEncodings = new LinkedList()).add("gzip");
        GZIP_MAGIC = new byte[] { 31, -117 };
    }
}
