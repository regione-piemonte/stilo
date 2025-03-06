// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.svggen;

import org.w3c.dom.NodeList;
import org.w3c.dom.Document;
import java.io.OutputStreamWriter;
import java.io.Writer;
import org.w3c.dom.Node;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Element;
import org.w3c.dom.CDATASection;
import org.w3c.dom.Text;
import org.w3c.dom.Comment;
import java.io.IOException;
import org.w3c.dom.Attr;
import org.apache.batik.util.SVGConstants;

class XmlWriter implements SVGConstants
{
    private static String EOL;
    private static final String TAG_END = "/>";
    private static final String TAG_START = "</";
    private static final char[] SPACES;
    private static final int SPACES_LEN;
    
    private static void writeXml(final Attr attr, final IndentWriter indentWriter, final boolean b) throws IOException {
        indentWriter.write(attr.getName());
        indentWriter.write("=\"");
        writeChildrenXml(attr, indentWriter, b);
        indentWriter.write(34);
    }
    
    private static void writeChildrenXml(final Attr attr, final IndentWriter indentWriter, final boolean b) throws IOException {
        final char[] charArray = attr.getValue().toCharArray();
        if (charArray == null) {
            return;
        }
        final int length = charArray.length;
        int n = 0;
        int i;
        for (i = 0; i < length; ++i) {
            final char j = charArray[i];
            switch (j) {
                case 60: {
                    indentWriter.write(charArray, n, i - n);
                    n = i + 1;
                    indentWriter.write("&lt;");
                    break;
                }
                case 62: {
                    indentWriter.write(charArray, n, i - n);
                    n = i + 1;
                    indentWriter.write("&gt;");
                    break;
                }
                case 38: {
                    indentWriter.write(charArray, n, i - n);
                    n = i + 1;
                    indentWriter.write("&amp;");
                    break;
                }
                case 39: {
                    indentWriter.write(charArray, n, i - n);
                    n = i + 1;
                    indentWriter.write("&apos;");
                    break;
                }
                case 34: {
                    indentWriter.write(charArray, n, i - n);
                    n = i + 1;
                    indentWriter.write("&quot;");
                    break;
                }
                default: {
                    if (b && j > '\u007f') {
                        indentWriter.write(charArray, n, i - n);
                        final String string = "0000" + Integer.toHexString(j);
                        indentWriter.write("&#x" + string.substring(string.length() - 4) + ";");
                        n = i + 1;
                        break;
                    }
                    break;
                }
            }
        }
        indentWriter.write(charArray, n, i - n);
    }
    
    private static void writeXml(final Comment comment, final IndentWriter indentWriter, final boolean b) throws IOException {
        final char[] charArray = comment.getData().toCharArray();
        if (charArray == null) {
            indentWriter.write("<!---->");
            return;
        }
        indentWriter.write("<!--");
        int n = 0;
        final int length = charArray.length;
        int n2 = 0;
        int i;
        for (i = 0; i < length; ++i) {
            if (charArray[i] == '-') {
                if (n != 0) {
                    indentWriter.write(charArray, n2, i - n2);
                    n2 = i;
                    indentWriter.write(32);
                }
                n = 1;
            }
            else {
                n = 0;
            }
        }
        indentWriter.write(charArray, n2, i - n2);
        if (n != 0) {
            indentWriter.write(32);
        }
        indentWriter.write("-->");
    }
    
    private static void writeXml(final Text text, final IndentWriter indentWriter, final boolean b) throws IOException {
        writeXml(text, indentWriter, false, b);
    }
    
    private static void writeXml(final Text text, final IndentWriter indentWriter, final boolean b, final boolean b2) throws IOException {
        final char[] charArray = text.getData().toCharArray();
        if (charArray == null) {
            System.err.println("Null text data??");
            return;
        }
        final int length = charArray.length;
        int n = 0;
        int i = 0;
        if (b) {
        Label_0109:
            while (i < length) {
                switch (charArray[i]) {
                    case '\t':
                    case '\n':
                    case '\r':
                    case ' ': {
                        ++i;
                        continue;
                    }
                    default: {
                        break Label_0109;
                    }
                }
            }
            n = i;
        }
        while (i < length) {
            final char j = charArray[i];
            switch (j) {
                case 9:
                case 10:
                case 13:
                case 32: {
                    if (!b) {
                        break;
                    }
                    final int n2 = i;
                    ++i;
                Label_0269:
                    while (i < length) {
                        switch (charArray[i]) {
                            case '\t':
                            case '\n':
                            case '\r':
                            case ' ': {
                                ++i;
                                continue;
                            }
                            default: {
                                break Label_0269;
                            }
                        }
                    }
                    if (i == length) {
                        indentWriter.write(charArray, n, n2 - n);
                        return;
                    }
                    continue;
                }
                case 60: {
                    indentWriter.write(charArray, n, i - n);
                    n = i + 1;
                    indentWriter.write("&lt;");
                    break;
                }
                case 62: {
                    indentWriter.write(charArray, n, i - n);
                    n = i + 1;
                    indentWriter.write("&gt;");
                    break;
                }
                case 38: {
                    indentWriter.write(charArray, n, i - n);
                    n = i + 1;
                    indentWriter.write("&amp;");
                    break;
                }
                default: {
                    if (b2 && j > '\u007f') {
                        indentWriter.write(charArray, n, i - n);
                        final String string = "0000" + Integer.toHexString(j);
                        indentWriter.write("&#x" + string.substring(string.length() - 4) + ";");
                        n = i + 1;
                        break;
                    }
                    break;
                }
            }
            ++i;
        }
        indentWriter.write(charArray, n, i - n);
    }
    
    private static void writeXml(final CDATASection cdataSection, final IndentWriter indentWriter, final boolean b) throws IOException {
        final char[] charArray = cdataSection.getData().toCharArray();
        if (charArray == null) {
            indentWriter.write("<![CDATA[]]>");
            return;
        }
        indentWriter.write("<![CDATA[");
        final int length = charArray.length;
        int n = 0;
        int i = 0;
        while (i < length) {
            if (charArray[i] == ']' && i + 2 < charArray.length && charArray[i + 1] == ']' && charArray[i + 2] == '>') {
                indentWriter.write(charArray, n, i - n);
                n = i + 1;
                indentWriter.write("]]]]><![CDATA[>");
            }
            else {
                ++i;
            }
        }
        indentWriter.write(charArray, n, i - n);
        indentWriter.write("]]>");
    }
    
    private static void writeXml(final Element element, final IndentWriter indentWriter, final boolean b) throws IOException, SVGGraphics2DIOException {
        indentWriter.write("</", 0, 1);
        indentWriter.write(element.getTagName());
        final NamedNodeMap attributes = element.getAttributes();
        if (attributes != null) {
            for (int length = attributes.getLength(), i = 0; i < length; ++i) {
                final Attr attr = (Attr)attributes.item(i);
                indentWriter.write(32);
                writeXml(attr, indentWriter, b);
            }
        }
        final boolean b2 = element.getParentNode().getLastChild() == element;
        if (!element.hasChildNodes()) {
            if (b2) {
                indentWriter.setIndentLevel(indentWriter.getIndentLevel() - 2);
            }
            indentWriter.printIndent();
            indentWriter.write("/>", 0, 2);
            return;
        }
        final Node firstChild = element.getFirstChild();
        indentWriter.printIndent();
        indentWriter.write("/>", 1, 1);
        if (firstChild.getNodeType() != 3 || element.getLastChild() != firstChild) {
            indentWriter.setIndentLevel(indentWriter.getIndentLevel() + 2);
        }
        writeChildrenXml(element, indentWriter, b);
        indentWriter.write("</", 0, 2);
        indentWriter.write(element.getTagName());
        if (b2) {
            indentWriter.setIndentLevel(indentWriter.getIndentLevel() - 2);
        }
        indentWriter.printIndent();
        indentWriter.write("/>", 1, 1);
    }
    
    private static void writeChildrenXml(final Element element, final IndentWriter indentWriter, final boolean b) throws IOException, SVGGraphics2DIOException {
        for (Node node = element.getFirstChild(); node != null; node = node.getNextSibling()) {
            writeXml(node, indentWriter, b);
        }
    }
    
    private static void writeDocumentHeader(final IndentWriter indentWriter) throws IOException {
        String java2std = null;
        if (indentWriter.getProxied() instanceof OutputStreamWriter) {
            java2std = java2std(((OutputStreamWriter)indentWriter.getProxied()).getEncoding());
        }
        indentWriter.write("<?xml version=\"1.0\"");
        if (java2std != null) {
            indentWriter.write(" encoding=\"");
            indentWriter.write(java2std);
            indentWriter.write(34);
        }
        indentWriter.write("?>");
        indentWriter.write(XmlWriter.EOL);
        indentWriter.write("<!DOCTYPE svg PUBLIC '");
        indentWriter.write("-//W3C//DTD SVG 1.0//EN");
        indentWriter.write("'");
        indentWriter.write(XmlWriter.EOL);
        indentWriter.write("          '");
        indentWriter.write("http://www.w3.org/TR/2001/REC-SVG-20010904/DTD/svg10.dtd");
        indentWriter.write("'");
        indentWriter.write(">");
        indentWriter.write(XmlWriter.EOL);
    }
    
    private static void writeXml(final Document document, final IndentWriter indentWriter, final boolean b) throws IOException, SVGGraphics2DIOException {
        writeDocumentHeader(indentWriter);
        writeXml(document.getChildNodes(), indentWriter, b);
    }
    
    private static void writeXml(final NodeList list, final IndentWriter indentWriter, final boolean b) throws IOException, SVGGraphics2DIOException {
        final int length = list.getLength();
        if (length == 0) {
            return;
        }
        for (int i = 0; i < length; ++i) {
            writeXml(list.item(i), indentWriter, b);
            indentWriter.write(XmlWriter.EOL);
        }
    }
    
    static String java2std(final String s) {
        if (s == null) {
            return null;
        }
        if (s.startsWith("ISO8859_")) {
            return "ISO-8859-" + s.substring(8);
        }
        if (s.startsWith("8859_")) {
            return "ISO-8859-" + s.substring(5);
        }
        if ("ASCII7".equalsIgnoreCase(s) || "ASCII".equalsIgnoreCase(s)) {
            return "US-ASCII";
        }
        if ("UTF8".equalsIgnoreCase(s)) {
            return "UTF-8";
        }
        if (s.startsWith("Unicode")) {
            return "UTF-16";
        }
        if ("SJIS".equalsIgnoreCase(s)) {
            return "Shift_JIS";
        }
        if ("JIS".equalsIgnoreCase(s)) {
            return "ISO-2022-JP";
        }
        if ("EUCJIS".equalsIgnoreCase(s)) {
            return "EUC-JP";
        }
        return "UTF-8";
    }
    
    public static void writeXml(final Node node, final Writer writer, final boolean b) throws SVGGraphics2DIOException {
        try {
            IndentWriter indentWriter;
            if (writer instanceof IndentWriter) {
                indentWriter = (IndentWriter)writer;
            }
            else {
                indentWriter = new IndentWriter(writer);
            }
            switch (node.getNodeType()) {
                case 2: {
                    writeXml((Attr)node, indentWriter, b);
                    break;
                }
                case 8: {
                    writeXml((Comment)node, indentWriter, b);
                    break;
                }
                case 3: {
                    writeXml((Text)node, indentWriter, b);
                    break;
                }
                case 4: {
                    writeXml((CDATASection)node, indentWriter, b);
                    break;
                }
                case 9: {
                    writeXml((Document)node, indentWriter, b);
                    break;
                }
                case 11: {
                    writeDocumentHeader(indentWriter);
                    writeXml(node.getChildNodes(), indentWriter, b);
                    break;
                }
                case 1: {
                    writeXml((Element)node, indentWriter, b);
                    break;
                }
                default: {
                    throw new SVGGraphics2DRuntimeException("Unable to write node of type " + node.getClass().getName());
                }
            }
        }
        catch (IOException ex) {
            throw new SVGGraphics2DIOException(ex);
        }
    }
    
    static {
        SPACES = new char[] { ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' };
        SPACES_LEN = XmlWriter.SPACES.length;
        String property;
        try {
            property = System.getProperty("line.separator", "\n");
        }
        catch (SecurityException ex) {
            property = "\n";
        }
        XmlWriter.EOL = property;
    }
    
    static class IndentWriter extends Writer
    {
        protected Writer proxied;
        protected int indentLevel;
        protected int column;
        
        public IndentWriter(final Writer proxied) {
            if (proxied == null) {
                throw new SVGGraphics2DRuntimeException("proxy should not be null");
            }
            this.proxied = proxied;
        }
        
        public void setIndentLevel(final int indentLevel) {
            this.indentLevel = indentLevel;
        }
        
        public int getIndentLevel() {
            return this.indentLevel;
        }
        
        public void printIndent() throws IOException {
            this.proxied.write(XmlWriter.EOL);
            for (int i = this.indentLevel; i > 0; i -= XmlWriter.SPACES_LEN) {
                if (i <= XmlWriter.SPACES_LEN) {
                    this.proxied.write(XmlWriter.SPACES, 0, i);
                    break;
                }
                this.proxied.write(XmlWriter.SPACES, 0, XmlWriter.SPACES_LEN);
            }
            this.column = this.indentLevel;
        }
        
        public Writer getProxied() {
            return this.proxied;
        }
        
        public int getColumn() {
            return this.column;
        }
        
        public void write(final int c) throws IOException {
            ++this.column;
            this.proxied.write(c);
        }
        
        public void write(final char[] cbuf) throws IOException {
            this.column += cbuf.length;
            this.proxied.write(cbuf);
        }
        
        public void write(final char[] array, final int n, final int n2) throws IOException {
            this.column += n2;
            this.proxied.write(array, n, n2);
        }
        
        public void write(final String str) throws IOException {
            this.column += str.length();
            this.proxied.write(str);
        }
        
        public void write(final String str, final int off, final int len) throws IOException {
            this.column += len;
            this.proxied.write(str, off, len);
        }
        
        public void flush() throws IOException {
            this.proxied.flush();
        }
        
        public void close() throws IOException {
            this.column = -1;
            this.proxied.close();
        }
    }
}
