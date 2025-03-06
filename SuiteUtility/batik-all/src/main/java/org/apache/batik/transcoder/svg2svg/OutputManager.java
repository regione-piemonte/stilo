// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.transcoder.svg2svg;

import org.apache.batik.xml.XMLUtilities;
import java.util.Iterator;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.io.Writer;

public class OutputManager
{
    protected PrettyPrinter prettyPrinter;
    protected Writer writer;
    protected int level;
    protected StringBuffer margin;
    protected int line;
    protected int column;
    protected List xmlSpace;
    protected boolean canIndent;
    protected List startingLines;
    protected boolean lineAttributes;
    
    public OutputManager(final PrettyPrinter prettyPrinter, final Writer writer) {
        this.margin = new StringBuffer();
        this.line = 1;
        (this.xmlSpace = new LinkedList()).add(Boolean.FALSE);
        this.canIndent = true;
        this.startingLines = new LinkedList();
        this.lineAttributes = false;
        this.prettyPrinter = prettyPrinter;
        this.writer = writer;
    }
    
    public void printCharacter(final char c) throws IOException {
        if (c == '\n') {
            this.printNewline();
        }
        else {
            ++this.column;
            this.writer.write(c);
        }
    }
    
    public void printNewline() throws IOException {
        final String newline = this.prettyPrinter.getNewline();
        for (int i = 0; i < newline.length(); ++i) {
            this.writer.write(newline.charAt(i));
        }
        this.column = 0;
        ++this.line;
    }
    
    public void printString(final String s) throws IOException {
        for (int i = 0; i < s.length(); ++i) {
            this.printCharacter(s.charAt(i));
        }
    }
    
    public void printCharacters(final char[] array) throws IOException {
        for (int i = 0; i < array.length; ++i) {
            this.printCharacter(array[i]);
        }
    }
    
    public void printSpaces(final char[] array, final boolean b) throws IOException {
        if (this.prettyPrinter.getFormat()) {
            if (!b) {
                this.printCharacter(' ');
            }
        }
        else {
            this.printCharacters(array);
        }
    }
    
    public void printTopSpaces(final char[] array) throws IOException {
        if (this.prettyPrinter.getFormat()) {
            for (int newlines = this.newlines(array), i = 0; i < newlines; ++i) {
                this.printNewline();
            }
        }
        else {
            this.printCharacters(array);
        }
    }
    
    public void printComment(final char[] array) throws IOException {
        if (this.prettyPrinter.getFormat()) {
            if (this.canIndent) {
                this.printNewline();
                this.printString(this.margin.toString());
            }
            this.printString("<!--");
            if (this.column + array.length + 3 < this.prettyPrinter.getDocumentWidth()) {
                this.printCharacters(array);
            }
            else {
                this.formatText(array, this.margin.toString(), false);
                this.printCharacter(' ');
            }
            if (this.column + 3 > this.prettyPrinter.getDocumentWidth()) {
                this.printNewline();
                this.printString(this.margin.toString());
            }
            this.printString("-->");
        }
        else {
            this.printString("<!--");
            this.printCharacters(array);
            this.printString("-->");
        }
    }
    
    public void printXMLDecl(final char[] array, final char[] array2, final char[] array3, final char[] array4, final char c, final char[] array5, final char[] array6, final char[] array7, final char[] array8, final char c2, final char[] array9, final char[] array10, final char[] array11, final char[] array12, final char c3, final char[] array13) throws IOException {
        this.printString("<?xml");
        this.printSpaces(array, false);
        this.printString("version");
        if (array2 != null) {
            this.printSpaces(array2, true);
        }
        this.printCharacter('=');
        if (array3 != null) {
            this.printSpaces(array3, true);
        }
        this.printCharacter(c);
        this.printCharacters(array4);
        this.printCharacter(c);
        if (array5 != null) {
            this.printSpaces(array5, false);
            if (array8 != null) {
                this.printString("encoding");
                if (array6 != null) {
                    this.printSpaces(array6, true);
                }
                this.printCharacter('=');
                if (array7 != null) {
                    this.printSpaces(array7, true);
                }
                this.printCharacter(c2);
                this.printCharacters(array8);
                this.printCharacter(c2);
                if (array9 != null) {
                    this.printSpaces(array9, array12 == null);
                }
            }
            if (array12 != null) {
                this.printString("standalone");
                if (array10 != null) {
                    this.printSpaces(array10, true);
                }
                this.printCharacter('=');
                if (array11 != null) {
                    this.printSpaces(array11, true);
                }
                this.printCharacter(c3);
                this.printCharacters(array12);
                this.printCharacter(c3);
                if (array13 != null) {
                    this.printSpaces(array13, true);
                }
            }
        }
        this.printString("?>");
    }
    
    public void printPI(final char[] array, final char[] array2, final char[] array3) throws IOException {
        if (this.prettyPrinter.getFormat() && this.canIndent) {
            this.printNewline();
            this.printString(this.margin.toString());
        }
        this.printString("<?");
        this.printCharacters(array);
        this.printSpaces(array2, false);
        this.printCharacters(array3);
        this.printString("?>");
    }
    
    public void printDoctypeStart(final char[] array, final char[] array2, final char[] array3, final String s, final char[] array4, final char[] array5, final char c, final char[] array6, final char[] array7, final char c2, final char[] array8) throws IOException {
        if (this.prettyPrinter.getFormat()) {
            this.printString("<!DOCTYPE");
            this.printCharacter(' ');
            this.printCharacters(array2);
            if (array3 != null) {
                this.printCharacter(' ');
                this.printString(s);
                this.printCharacter(' ');
                this.printCharacter(c);
                this.printCharacters(array5);
                this.printCharacter(c);
                if (array6 != null && array7 != null) {
                    if (this.column + array7.length + 3 > this.prettyPrinter.getDocumentWidth()) {
                        this.printNewline();
                        for (int i = 0; i < this.prettyPrinter.getTabulationWidth(); ++i) {
                            this.printCharacter(' ');
                        }
                    }
                    else {
                        this.printCharacter(' ');
                    }
                    this.printCharacter(c2);
                    this.printCharacters(array7);
                    this.printCharacter(c2);
                    this.printCharacter(' ');
                }
            }
        }
        else {
            this.printString("<!DOCTYPE");
            this.printSpaces(array, false);
            this.printCharacters(array2);
            if (array3 != null) {
                this.printSpaces(array3, false);
                this.printString(s);
                this.printSpaces(array4, false);
                this.printCharacter(c);
                this.printCharacters(array5);
                this.printCharacter(c);
                if (array6 != null) {
                    this.printSpaces(array6, array7 == null);
                    if (array7 != null) {
                        this.printCharacter(c2);
                        this.printCharacters(array7);
                        this.printCharacter(c2);
                        if (array8 != null) {
                            this.printSpaces(array8, true);
                        }
                    }
                }
            }
        }
    }
    
    public void printDoctypeEnd(final char[] array) throws IOException {
        if (array != null) {
            this.printSpaces(array, true);
        }
        this.printCharacter('>');
    }
    
    public void printParameterEntityReference(final char[] array) throws IOException {
        this.printCharacter('%');
        this.printCharacters(array);
        this.printCharacter(';');
    }
    
    public void printEntityReference(final char[] array, final boolean b) throws IOException {
        if (this.prettyPrinter.getFormat() && this.xmlSpace.get(0) != Boolean.TRUE && b) {
            this.printNewline();
            this.printString(this.margin.toString());
        }
        this.printCharacter('&');
        this.printCharacters(array);
        this.printCharacter(';');
    }
    
    public void printCharacterEntityReference(final char[] array, final boolean b, final boolean b2) throws IOException {
        if (this.prettyPrinter.getFormat() && this.xmlSpace.get(0) != Boolean.TRUE) {
            if (b) {
                this.printNewline();
                this.printString(this.margin.toString());
            }
            else if (b2) {
                if (this.column + array.length + 3 > this.prettyPrinter.getDocumentWidth()) {
                    this.printNewline();
                    this.printString(this.margin.toString());
                }
                else {
                    this.printCharacter(' ');
                }
            }
        }
        this.printString("&#");
        this.printCharacters(array);
        this.printCharacter(';');
    }
    
    public void printElementStart(final char[] array, final List list, final char[] array2) throws IOException {
        this.xmlSpace.add(0, this.xmlSpace.get(0));
        this.startingLines.add(0, new Integer(this.line));
        if (this.prettyPrinter.getFormat() && this.canIndent) {
            this.printNewline();
            this.printString(this.margin.toString());
        }
        this.printCharacter('<');
        this.printCharacters(array);
        if (this.prettyPrinter.getFormat()) {
            final Iterator<AttributeInfo> iterator = list.iterator();
            if (iterator.hasNext()) {
                final AttributeInfo attributeInfo = iterator.next();
                if (attributeInfo.isAttribute("xml:space")) {
                    this.xmlSpace.set(0, attributeInfo.value.equals("preserve") ? Boolean.TRUE : Boolean.FALSE);
                }
                this.printCharacter(' ');
                this.printCharacters(attributeInfo.name);
                this.printCharacter('=');
                this.printCharacter(attributeInfo.delimiter);
                this.printString(attributeInfo.value);
                this.printCharacter(attributeInfo.delimiter);
            }
            while (iterator.hasNext()) {
                final AttributeInfo attributeInfo2 = iterator.next();
                if (attributeInfo2.isAttribute("xml:space")) {
                    this.xmlSpace.set(0, attributeInfo2.value.equals("preserve") ? Boolean.TRUE : Boolean.FALSE);
                }
                final int n = attributeInfo2.name.length + attributeInfo2.value.length() + 4;
                if (this.lineAttributes || n + this.column > this.prettyPrinter.getDocumentWidth()) {
                    this.printNewline();
                    this.printString(this.margin.toString());
                    for (int i = 0; i < array.length + 2; ++i) {
                        this.printCharacter(' ');
                    }
                }
                else {
                    this.printCharacter(' ');
                }
                this.printCharacters(attributeInfo2.name);
                this.printCharacter('=');
                this.printCharacter(attributeInfo2.delimiter);
                this.printString(attributeInfo2.value);
                this.printCharacter(attributeInfo2.delimiter);
            }
        }
        else {
            for (final AttributeInfo attributeInfo3 : list) {
                if (attributeInfo3.isAttribute("xml:space")) {
                    this.xmlSpace.set(0, attributeInfo3.value.equals("preserve") ? Boolean.TRUE : Boolean.FALSE);
                }
                this.printSpaces(attributeInfo3.space, false);
                this.printCharacters(attributeInfo3.name);
                if (attributeInfo3.space1 != null) {
                    this.printSpaces(attributeInfo3.space1, true);
                }
                this.printCharacter('=');
                if (attributeInfo3.space2 != null) {
                    this.printSpaces(attributeInfo3.space2, true);
                }
                this.printCharacter(attributeInfo3.delimiter);
                this.printString(attributeInfo3.value);
                this.printCharacter(attributeInfo3.delimiter);
            }
        }
        if (array2 != null) {
            this.printSpaces(array2, true);
        }
        ++this.level;
        for (int j = 0; j < this.prettyPrinter.getTabulationWidth(); ++j) {
            this.margin.append(' ');
        }
        this.canIndent = true;
    }
    
    public void printElementEnd(final char[] array, final char[] array2) throws IOException {
        for (int i = 0; i < this.prettyPrinter.getTabulationWidth(); ++i) {
            this.margin.deleteCharAt(0);
        }
        --this.level;
        if (array != null) {
            if (this.prettyPrinter.getFormat() && this.xmlSpace.get(0) != Boolean.TRUE && (this.line != this.startingLines.get(0) || this.column + array.length + 3 >= this.prettyPrinter.getDocumentWidth())) {
                this.printNewline();
                this.printString(this.margin.toString());
            }
            this.printString("</");
            this.printCharacters(array);
            if (array2 != null) {
                this.printSpaces(array2, true);
            }
            this.printCharacter('>');
        }
        else {
            this.printString("/>");
        }
        this.startingLines.remove(0);
        this.xmlSpace.remove(0);
    }
    
    public boolean printCharacterData(final char[] array, final boolean b, final boolean b2) throws IOException {
        if (!this.prettyPrinter.getFormat()) {
            this.printCharacters(array);
            return false;
        }
        this.canIndent = true;
        if (this.isWhiteSpace(array)) {
            for (int newlines = this.newlines(array), i = 0; i < newlines - 1; ++i) {
                this.printNewline();
            }
            return true;
        }
        if (this.xmlSpace.get(0) == Boolean.TRUE) {
            this.printCharacters(array);
            return this.canIndent = false;
        }
        if (b) {
            this.printNewline();
            this.printString(this.margin.toString());
        }
        return this.formatText(array, this.margin.toString(), b2);
    }
    
    public void printCDATASection(final char[] array) throws IOException {
        this.printString("<![CDATA[");
        this.printCharacters(array);
        this.printString("]]>");
    }
    
    public void printNotation(final char[] array, final char[] cbuf, final char[] array2, final String str, final char[] array3, final char[] cbuf2, final char c, final char[] array4, final char[] cbuf3, final char c2, final char[] array5) throws IOException {
        this.writer.write("<!NOTATION");
        this.printSpaces(array, false);
        this.writer.write(cbuf);
        this.printSpaces(array2, false);
        this.writer.write(str);
        this.printSpaces(array3, false);
        this.writer.write(c);
        this.writer.write(cbuf2);
        this.writer.write(c);
        if (array4 != null) {
            this.printSpaces(array4, false);
            if (cbuf3 != null) {
                this.writer.write(c2);
                this.writer.write(cbuf3);
                this.writer.write(c2);
            }
        }
        if (array5 != null) {
            this.printSpaces(array5, true);
        }
        this.writer.write(62);
    }
    
    public void printAttlistStart(final char[] array, final char[] cbuf) throws IOException {
        this.writer.write("<!ATTLIST");
        this.printSpaces(array, false);
        this.writer.write(cbuf);
    }
    
    public void printAttlistEnd(final char[] array) throws IOException {
        if (array != null) {
            this.printSpaces(array, false);
        }
        this.writer.write(62);
    }
    
    public void printAttName(final char[] array, final char[] cbuf, final char[] array2) throws IOException {
        this.printSpaces(array, false);
        this.writer.write(cbuf);
        this.printSpaces(array2, false);
    }
    
    public void printEnumeration(final List list) throws IOException {
        this.writer.write(40);
        final Iterator<NameInfo> iterator = list.iterator();
        final NameInfo nameInfo = iterator.next();
        if (nameInfo.space1 != null) {
            this.printSpaces(nameInfo.space1, true);
        }
        this.writer.write(nameInfo.name);
        if (nameInfo.space2 != null) {
            this.printSpaces(nameInfo.space2, true);
        }
        while (iterator.hasNext()) {
            this.writer.write(124);
            final NameInfo nameInfo2 = iterator.next();
            if (nameInfo2.space1 != null) {
                this.printSpaces(nameInfo2.space1, true);
            }
            this.writer.write(nameInfo2.name);
            if (nameInfo2.space2 != null) {
                this.printSpaces(nameInfo2.space2, true);
            }
        }
        this.writer.write(41);
    }
    
    protected int newlines(final char[] array) {
        int n = 0;
        for (int i = 0; i < array.length; ++i) {
            if (array[i] == '\n') {
                ++n;
            }
        }
        return n;
    }
    
    protected boolean isWhiteSpace(final char[] array) {
        for (int i = 0; i < array.length; ++i) {
            if (!XMLUtilities.isXMLSpace(array[i])) {
                return false;
            }
        }
        return true;
    }
    
    protected boolean formatText(final char[] array, final String s, final boolean b) throws IOException {
        int i = 0;
        boolean b2 = b;
    Label_0006:
        while (i < array.length) {
            while (i < array.length) {
                if (!XMLUtilities.isXMLSpace(array[i])) {
                    final StringBuffer sb = new StringBuffer();
                    while (i < array.length && !XMLUtilities.isXMLSpace(array[i])) {
                        sb.append(array[i++]);
                    }
                    if (sb.length() == 0) {
                        return b2;
                    }
                    if (b2) {
                        if (this.column + sb.length() >= this.prettyPrinter.getDocumentWidth() - 1 && (s.length() + sb.length() < this.prettyPrinter.getDocumentWidth() - 1 || s.length() < this.column)) {
                            this.printNewline();
                            this.printString(s);
                        }
                        else if (this.column > s.length()) {
                            this.printCharacter(' ');
                        }
                    }
                    this.printString(sb.toString());
                    b2 = false;
                    continue Label_0006;
                }
                else {
                    b2 = true;
                    ++i;
                }
            }
            break;
        }
        return b2;
    }
    
    public static class AttributeInfo
    {
        public char[] space;
        public char[] name;
        public char[] space1;
        public char[] space2;
        public String value;
        public char delimiter;
        public boolean entityReferences;
        
        public AttributeInfo(final char[] space, final char[] name, final char[] space2, final char[] space3, final String value, final char delimiter, final boolean entityReferences) {
            this.space = space;
            this.name = name;
            this.space1 = space2;
            this.space2 = space3;
            this.value = value;
            this.delimiter = delimiter;
            this.entityReferences = entityReferences;
        }
        
        public boolean isAttribute(final String s) {
            if (this.name.length == s.length()) {
                for (int i = 0; i < this.name.length; ++i) {
                    if (this.name[i] != s.charAt(i)) {
                        return false;
                    }
                }
                return true;
            }
            return false;
        }
    }
    
    public static class NameInfo
    {
        public char[] space1;
        public char[] name;
        public char[] space2;
        
        public NameInfo(final char[] space1, final char[] name, final char[] space2) {
            this.space1 = space1;
            this.name = name;
            this.space2 = space2;
        }
    }
}
