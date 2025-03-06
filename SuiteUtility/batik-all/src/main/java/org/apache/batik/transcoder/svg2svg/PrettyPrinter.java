// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.transcoder.svg2svg;

import java.util.List;
import java.util.LinkedList;
import java.io.IOException;
import org.apache.batik.xml.XMLException;
import org.apache.batik.transcoder.TranscoderException;
import java.io.Reader;
import org.apache.batik.transcoder.ErrorHandler;
import java.io.Writer;
import org.apache.batik.xml.XMLScanner;

public class PrettyPrinter
{
    public static final int DOCTYPE_CHANGE = 0;
    public static final int DOCTYPE_REMOVE = 1;
    public static final int DOCTYPE_KEEP_UNCHANGED = 2;
    protected XMLScanner scanner;
    protected OutputManager output;
    protected Writer writer;
    protected ErrorHandler errorHandler;
    protected String newline;
    protected boolean format;
    protected int tabulationWidth;
    protected int documentWidth;
    protected int doctypeOption;
    protected String publicId;
    protected String systemId;
    protected String xmlDeclaration;
    protected int type;
    
    public PrettyPrinter() {
        this.errorHandler = SVGTranscoder.DEFAULT_ERROR_HANDLER;
        this.newline = "\n";
        this.format = true;
        this.tabulationWidth = 4;
        this.documentWidth = 80;
        this.doctypeOption = 2;
    }
    
    public void setXMLDeclaration(final String xmlDeclaration) {
        this.xmlDeclaration = xmlDeclaration;
    }
    
    public void setDoctypeOption(final int doctypeOption) {
        this.doctypeOption = doctypeOption;
    }
    
    public void setPublicId(final String publicId) {
        this.publicId = publicId;
    }
    
    public void setSystemId(final String systemId) {
        this.systemId = systemId;
    }
    
    public void setNewline(final String newline) {
        this.newline = newline;
    }
    
    public String getNewline() {
        return this.newline;
    }
    
    public void setFormat(final boolean format) {
        this.format = format;
    }
    
    public boolean getFormat() {
        return this.format;
    }
    
    public void setTabulationWidth(final int a) {
        this.tabulationWidth = Math.max(a, 0);
    }
    
    public int getTabulationWidth() {
        return this.tabulationWidth;
    }
    
    public void setDocumentWidth(final int a) {
        this.documentWidth = Math.max(a, 0);
    }
    
    public int getDocumentWidth() {
        return this.documentWidth;
    }
    
    public void print(final Reader reader, final Writer writer) throws TranscoderException, IOException {
        Label_0442: {
            try {
                this.scanner = new XMLScanner(reader);
                this.output = new OutputManager(this, writer);
                this.writer = writer;
                this.type = this.scanner.next();
                this.printXMLDecl();
                while (true) {
                    switch (this.type) {
                        case 1: {
                            this.output.printTopSpaces(this.getCurrentValue());
                            this.scanner.clearBuffer();
                            this.type = this.scanner.next();
                            continue;
                        }
                        case 4: {
                            this.output.printComment(this.getCurrentValue());
                            this.scanner.clearBuffer();
                            this.type = this.scanner.next();
                            continue;
                        }
                        case 5: {
                            this.printPI();
                            continue;
                        }
                        default: {
                            this.printDoctype();
                            while (true) {
                                this.scanner.clearBuffer();
                                switch (this.type) {
                                    case 1: {
                                        this.output.printTopSpaces(this.getCurrentValue());
                                        this.scanner.clearBuffer();
                                        this.type = this.scanner.next();
                                        continue;
                                    }
                                    case 4: {
                                        this.output.printComment(this.getCurrentValue());
                                        this.scanner.clearBuffer();
                                        this.type = this.scanner.next();
                                        continue;
                                    }
                                    case 5: {
                                        this.printPI();
                                        continue;
                                    }
                                    default: {
                                        if (this.type != 9) {
                                            throw this.fatalError("element", null);
                                        }
                                        this.printElement();
                                        while (true) {
                                            switch (this.type) {
                                                case 1: {
                                                    this.output.printTopSpaces(this.getCurrentValue());
                                                    this.scanner.clearBuffer();
                                                    this.type = this.scanner.next();
                                                    continue;
                                                }
                                                case 4: {
                                                    this.output.printComment(this.getCurrentValue());
                                                    this.scanner.clearBuffer();
                                                    this.type = this.scanner.next();
                                                    continue;
                                                }
                                                case 5: {
                                                    this.printPI();
                                                    continue;
                                                }
                                                default: {
                                                    break Label_0442;
                                                }
                                            }
                                        }
                                        break;
                                    }
                                }
                            }
                            break;
                        }
                    }
                }
            }
            catch (XMLException ex) {
                this.errorHandler.fatalError(new TranscoderException(ex.getMessage()));
            }
        }
    }
    
    protected void printXMLDecl() throws TranscoderException, XMLException, IOException {
        if (this.xmlDeclaration == null) {
            if (this.type == 2) {
                if (this.scanner.next() != 1) {
                    throw this.fatalError("space", null);
                }
                final char[] currentValue = this.getCurrentValue();
                if (this.scanner.next() != 22) {
                    throw this.fatalError("token", new Object[] { "version" });
                }
                this.type = this.scanner.next();
                char[] currentValue2 = null;
                if (this.type == 1) {
                    currentValue2 = this.getCurrentValue();
                    this.type = this.scanner.next();
                }
                if (this.type != 15) {
                    throw this.fatalError("token", new Object[] { "=" });
                }
                this.type = this.scanner.next();
                char[] currentValue3 = null;
                if (this.type == 1) {
                    currentValue3 = this.getCurrentValue();
                    this.type = this.scanner.next();
                }
                if (this.type != 25) {
                    throw this.fatalError("string", null);
                }
                final char[] currentValue4 = this.getCurrentValue();
                final char stringDelimiter = this.scanner.getStringDelimiter();
                char[] currentValue5 = null;
                char[] currentValue6 = null;
                char[] currentValue7 = null;
                char[] currentValue8 = null;
                char stringDelimiter2 = '\0';
                char[] currentValue9 = null;
                char[] currentValue10 = null;
                char[] currentValue11 = null;
                char[] currentValue12 = null;
                char stringDelimiter3 = '\0';
                char[] currentValue13 = null;
                this.type = this.scanner.next();
                if (this.type == 1) {
                    currentValue5 = this.getCurrentValue();
                    this.type = this.scanner.next();
                    if (this.type == 23) {
                        this.type = this.scanner.next();
                        if (this.type == 1) {
                            currentValue6 = this.getCurrentValue();
                            this.type = this.scanner.next();
                        }
                        if (this.type != 15) {
                            throw this.fatalError("token", new Object[] { "=" });
                        }
                        this.type = this.scanner.next();
                        if (this.type == 1) {
                            currentValue7 = this.getCurrentValue();
                            this.type = this.scanner.next();
                        }
                        if (this.type != 25) {
                            throw this.fatalError("string", null);
                        }
                        currentValue8 = this.getCurrentValue();
                        stringDelimiter2 = this.scanner.getStringDelimiter();
                        this.type = this.scanner.next();
                        if (this.type == 1) {
                            currentValue9 = this.getCurrentValue();
                            this.type = this.scanner.next();
                        }
                    }
                    if (this.type == 24) {
                        this.type = this.scanner.next();
                        if (this.type == 1) {
                            currentValue10 = this.getCurrentValue();
                            this.type = this.scanner.next();
                        }
                        if (this.type != 15) {
                            throw this.fatalError("token", new Object[] { "=" });
                        }
                        this.type = this.scanner.next();
                        if (this.type == 1) {
                            currentValue11 = this.getCurrentValue();
                            this.type = this.scanner.next();
                        }
                        if (this.type != 25) {
                            throw this.fatalError("string", null);
                        }
                        currentValue12 = this.getCurrentValue();
                        stringDelimiter3 = this.scanner.getStringDelimiter();
                        this.type = this.scanner.next();
                        if (this.type == 1) {
                            currentValue13 = this.getCurrentValue();
                            this.type = this.scanner.next();
                        }
                    }
                }
                if (this.type != 7) {
                    throw this.fatalError("pi.end", null);
                }
                this.output.printXMLDecl(currentValue, currentValue2, currentValue3, currentValue4, stringDelimiter, currentValue5, currentValue6, currentValue7, currentValue8, stringDelimiter2, currentValue9, currentValue10, currentValue11, currentValue12, stringDelimiter3, currentValue13);
                this.type = this.scanner.next();
            }
        }
        else {
            this.output.printString(this.xmlDeclaration);
            this.output.printNewline();
            if (this.type == 2) {
                if (this.scanner.next() != 1) {
                    throw this.fatalError("space", null);
                }
                if (this.scanner.next() != 22) {
                    throw this.fatalError("token", new Object[] { "version" });
                }
                this.type = this.scanner.next();
                if (this.type == 1) {
                    this.type = this.scanner.next();
                }
                if (this.type != 15) {
                    throw this.fatalError("token", new Object[] { "=" });
                }
                this.type = this.scanner.next();
                if (this.type == 1) {
                    this.type = this.scanner.next();
                }
                if (this.type != 25) {
                    throw this.fatalError("string", null);
                }
                this.type = this.scanner.next();
                if (this.type == 1) {
                    this.type = this.scanner.next();
                    if (this.type == 23) {
                        this.type = this.scanner.next();
                        if (this.type == 1) {
                            this.type = this.scanner.next();
                        }
                        if (this.type != 15) {
                            throw this.fatalError("token", new Object[] { "=" });
                        }
                        this.type = this.scanner.next();
                        if (this.type == 1) {
                            this.type = this.scanner.next();
                        }
                        if (this.type != 25) {
                            throw this.fatalError("string", null);
                        }
                        this.type = this.scanner.next();
                        if (this.type == 1) {
                            this.type = this.scanner.next();
                        }
                    }
                    if (this.type == 24) {
                        this.type = this.scanner.next();
                        if (this.type == 1) {
                            this.type = this.scanner.next();
                        }
                        if (this.type != 15) {
                            throw this.fatalError("token", new Object[] { "=" });
                        }
                        this.type = this.scanner.next();
                        if (this.type == 1) {
                            this.type = this.scanner.next();
                        }
                        if (this.type != 25) {
                            throw this.fatalError("string", null);
                        }
                        this.type = this.scanner.next();
                        if (this.type == 1) {
                            this.type = this.scanner.next();
                        }
                    }
                }
                if (this.type != 7) {
                    throw this.fatalError("pi.end", null);
                }
                this.type = this.scanner.next();
            }
        }
    }
    
    protected void printPI() throws TranscoderException, XMLException, IOException {
        final char[] currentValue = this.getCurrentValue();
        this.type = this.scanner.next();
        char[] currentValue2 = new char[0];
        if (this.type == 1) {
            currentValue2 = this.getCurrentValue();
            this.type = this.scanner.next();
        }
        if (this.type != 6) {
            throw this.fatalError("pi.data", null);
        }
        final char[] currentValue3 = this.getCurrentValue();
        this.type = this.scanner.next();
        if (this.type != 7) {
            throw this.fatalError("pi.end", null);
        }
        this.output.printPI(currentValue, currentValue2, currentValue3);
        this.type = this.scanner.next();
    }
    
    protected void printDoctype() throws TranscoderException, XMLException, IOException {
        switch (this.doctypeOption) {
            default: {
                if (this.type == 3) {
                    this.type = this.scanner.next();
                    if (this.type != 1) {
                        throw this.fatalError("space", null);
                    }
                    final char[] currentValue = this.getCurrentValue();
                    this.type = this.scanner.next();
                    if (this.type != 14) {
                        throw this.fatalError("name", null);
                    }
                    final char[] currentValue2 = this.getCurrentValue();
                    char[] currentValue3 = null;
                    String s = null;
                    char[] array = null;
                    char[] array2 = null;
                    char c = '\0';
                    char[] array3 = null;
                    char[] array4 = null;
                    char stringDelimiter = '\0';
                    char[] currentValue4 = null;
                    this.type = this.scanner.next();
                    if (this.type == 1) {
                        currentValue3 = this.getCurrentValue();
                        switch (this.type = this.scanner.next()) {
                            case 27: {
                                s = "PUBLIC";
                                this.type = this.scanner.next();
                                if (this.type != 1) {
                                    throw this.fatalError("space", null);
                                }
                                array = this.getCurrentValue();
                                this.type = this.scanner.next();
                                if (this.type != 25) {
                                    throw this.fatalError("string", null);
                                }
                                array2 = this.getCurrentValue();
                                c = this.scanner.getStringDelimiter();
                                this.type = this.scanner.next();
                                if (this.type != 1) {
                                    throw this.fatalError("space", null);
                                }
                                array3 = this.getCurrentValue();
                                this.type = this.scanner.next();
                                if (this.type != 25) {
                                    throw this.fatalError("string", null);
                                }
                                array4 = this.getCurrentValue();
                                stringDelimiter = this.scanner.getStringDelimiter();
                                this.type = this.scanner.next();
                                if (this.type == 1) {
                                    currentValue4 = this.getCurrentValue();
                                    this.type = this.scanner.next();
                                    break;
                                }
                                break;
                            }
                            case 26: {
                                s = "SYSTEM";
                                this.type = this.scanner.next();
                                if (this.type != 1) {
                                    throw this.fatalError("space", null);
                                }
                                array = this.getCurrentValue();
                                this.type = this.scanner.next();
                                if (this.type != 25) {
                                    throw this.fatalError("string", null);
                                }
                                array2 = this.getCurrentValue();
                                c = this.scanner.getStringDelimiter();
                                this.type = this.scanner.next();
                                if (this.type == 1) {
                                    array3 = this.getCurrentValue();
                                    this.type = this.scanner.next();
                                    break;
                                }
                                break;
                            }
                        }
                    }
                    if (this.doctypeOption == 0) {
                        if (this.publicId != null) {
                            s = "PUBLIC";
                            array2 = this.publicId.toCharArray();
                            c = '\"';
                            if (this.systemId != null) {
                                array4 = this.systemId.toCharArray();
                                stringDelimiter = '\"';
                            }
                        }
                        else if (this.systemId != null) {
                            s = "SYSTEM";
                            array2 = this.systemId.toCharArray();
                            c = '\"';
                            array4 = null;
                        }
                    }
                    this.output.printDoctypeStart(currentValue, currentValue2, currentValue3, s, array, array2, c, array3, array4, stringDelimiter, currentValue4);
                    Label_0986: {
                        if (this.type == 28) {
                            this.output.printCharacter('[');
                            this.type = this.scanner.next();
                            while (true) {
                                switch (this.type) {
                                    case 1: {
                                        this.output.printSpaces(this.getCurrentValue(), true);
                                        this.scanner.clearBuffer();
                                        this.type = this.scanner.next();
                                        continue;
                                    }
                                    case 4: {
                                        this.output.printComment(this.getCurrentValue());
                                        this.scanner.clearBuffer();
                                        this.type = this.scanner.next();
                                        continue;
                                    }
                                    case 5: {
                                        this.printPI();
                                        continue;
                                    }
                                    case 34: {
                                        this.output.printParameterEntityReference(this.getCurrentValue());
                                        this.scanner.clearBuffer();
                                        this.type = this.scanner.next();
                                        continue;
                                    }
                                    case 30: {
                                        this.scanner.clearBuffer();
                                        this.printElementDeclaration();
                                        continue;
                                    }
                                    case 31: {
                                        this.scanner.clearBuffer();
                                        this.printAttlist();
                                        continue;
                                    }
                                    case 33: {
                                        this.scanner.clearBuffer();
                                        this.printNotation();
                                        continue;
                                    }
                                    case 32: {
                                        this.scanner.clearBuffer();
                                        this.printEntityDeclaration();
                                        continue;
                                    }
                                    case 29: {
                                        this.output.printCharacter(']');
                                        this.scanner.clearBuffer();
                                        this.type = this.scanner.next();
                                        break Label_0986;
                                    }
                                    default: {
                                        throw this.fatalError("xml", null);
                                    }
                                }
                            }
                        }
                    }
                    char[] currentValue5 = null;
                    if (this.type == 1) {
                        currentValue5 = this.getCurrentValue();
                        this.type = this.scanner.next();
                    }
                    if (this.type != 20) {
                        throw this.fatalError("end", null);
                    }
                    this.type = this.scanner.next();
                    this.output.printDoctypeEnd(currentValue5);
                    break;
                }
                else {
                    if (this.doctypeOption == 0) {
                        String s2 = "PUBLIC";
                        char[] array5 = "-//W3C//DTD SVG 1.0//EN".toCharArray();
                        char[] array6 = "http://www.w3.org/TR/2001/REC-SVG-20010904/DTD/svg10.dtd".toCharArray();
                        if (this.publicId != null) {
                            array5 = this.publicId.toCharArray();
                            if (this.systemId != null) {
                                array6 = this.systemId.toCharArray();
                            }
                        }
                        else if (this.systemId != null) {
                            s2 = "SYSTEM";
                            array5 = this.systemId.toCharArray();
                            array6 = null;
                        }
                        this.output.printDoctypeStart(new char[] { ' ' }, new char[] { 's', 'v', 'g' }, new char[] { ' ' }, s2, new char[] { ' ' }, array5, '\"', new char[] { ' ' }, array6, '\"', null);
                        this.output.printDoctypeEnd(null);
                        break;
                    }
                    break;
                }
                break;
            }
            case 1: {
                if (this.type == 3) {
                    this.type = this.scanner.next();
                    if (this.type != 1) {
                        throw this.fatalError("space", null);
                    }
                    this.type = this.scanner.next();
                    if (this.type != 14) {
                        throw this.fatalError("name", null);
                    }
                    this.type = this.scanner.next();
                    if (this.type == 1) {
                        switch (this.type = this.scanner.next()) {
                            case 27: {
                                this.type = this.scanner.next();
                                if (this.type != 1) {
                                    throw this.fatalError("space", null);
                                }
                                this.type = this.scanner.next();
                                if (this.type != 25) {
                                    throw this.fatalError("string", null);
                                }
                                this.type = this.scanner.next();
                                if (this.type != 1) {
                                    throw this.fatalError("space", null);
                                }
                                this.type = this.scanner.next();
                                if (this.type != 25) {
                                    throw this.fatalError("string", null);
                                }
                                this.type = this.scanner.next();
                                if (this.type == 1) {
                                    this.type = this.scanner.next();
                                    break;
                                }
                                break;
                            }
                            case 26: {
                                this.type = this.scanner.next();
                                if (this.type != 1) {
                                    throw this.fatalError("space", null);
                                }
                                this.type = this.scanner.next();
                                if (this.type != 25) {
                                    throw this.fatalError("string", null);
                                }
                                this.type = this.scanner.next();
                                if (this.type == 1) {
                                    this.type = this.scanner.next();
                                    break;
                                }
                                break;
                            }
                        }
                    }
                    if (this.type == 28) {
                        do {
                            this.type = this.scanner.next();
                        } while (this.type != 29);
                    }
                    if (this.type == 1) {
                        this.type = this.scanner.next();
                    }
                    if (this.type != 20) {
                        throw this.fatalError("end", null);
                    }
                }
                this.type = this.scanner.next();
                break;
            }
        }
    }
    
    protected String printElement() throws TranscoderException, XMLException, IOException {
        final char[] currentValue = this.getCurrentValue();
        final String s = new String(currentValue);
        final LinkedList<OutputManager.AttributeInfo> list = new LinkedList<OutputManager.AttributeInfo>();
        char[] currentValue2 = null;
        this.type = this.scanner.next();
    Label_0036:
        while (true) {
            while (this.type == 1) {
                currentValue2 = this.getCurrentValue();
                this.type = this.scanner.next();
                if (this.type == 14) {
                    final char[] currentValue3 = this.getCurrentValue();
                    char[] currentValue4 = null;
                    this.type = this.scanner.next();
                    if (this.type == 1) {
                        currentValue4 = this.getCurrentValue();
                        this.type = this.scanner.next();
                    }
                    if (this.type != 15) {
                        throw this.fatalError("token", new Object[] { "=" });
                    }
                    this.type = this.scanner.next();
                    char[] currentValue5 = null;
                    if (this.type == 1) {
                        currentValue5 = this.getCurrentValue();
                        this.type = this.scanner.next();
                    }
                    if (this.type != 25 && this.type != 16) {
                        throw this.fatalError("string", null);
                    }
                    final char stringDelimiter = this.scanner.getStringDelimiter();
                    boolean b = false;
                    final StringBuffer buffer = new StringBuffer();
                    buffer.append(this.getCurrentValue());
                    while (true) {
                        this.scanner.clearBuffer();
                        switch (this.type = this.scanner.next()) {
                            case 16:
                            case 17:
                            case 18:
                            case 25: {
                                buffer.append(this.getCurrentValue());
                                continue;
                            }
                            case 12: {
                                b = true;
                                buffer.append("&#");
                                buffer.append(this.getCurrentValue());
                                buffer.append(";");
                                continue;
                            }
                            case 13: {
                                b = true;
                                buffer.append("&");
                                buffer.append(this.getCurrentValue());
                                buffer.append(";");
                                continue;
                            }
                            default: {
                                list.add(new OutputManager.AttributeInfo(currentValue2, currentValue3, currentValue4, currentValue5, new String(buffer), stringDelimiter, b));
                                currentValue2 = null;
                                continue Label_0036;
                            }
                        }
                    }
                }
            }
            break;
        }
        this.output.printElementStart(currentValue, list, currentValue2);
        switch (this.type) {
            default: {
                throw this.fatalError("xml", null);
            }
            case 19: {
                this.output.printElementEnd(null, null);
                break;
            }
            case 20: {
                this.output.printCharacter('>');
                this.type = this.scanner.next();
                this.printContent(this.allowSpaceAtStart(s));
                if (this.type != 10) {
                    throw this.fatalError("end.tag", null);
                }
                final char[] currentValue6 = this.getCurrentValue();
                this.type = this.scanner.next();
                char[] currentValue7 = null;
                if (this.type == 1) {
                    currentValue7 = this.getCurrentValue();
                    this.type = this.scanner.next();
                }
                this.output.printElementEnd(currentValue6, currentValue7);
                if (this.type != 20) {
                    throw this.fatalError("end", null);
                }
                break;
            }
        }
        this.type = this.scanner.next();
        return s;
    }
    
    boolean allowSpaceAtStart(final String s) {
        return true;
    }
    
    protected void printContent(boolean allowSpaceAtStart) throws TranscoderException, XMLException, IOException {
        boolean printCharacterData = false;
        while (true) {
            switch (this.type) {
                case 4: {
                    this.output.printComment(this.getCurrentValue());
                    this.scanner.clearBuffer();
                    this.type = this.scanner.next();
                    printCharacterData = false;
                    continue;
                }
                case 5: {
                    this.printPI();
                    printCharacterData = false;
                    continue;
                }
                case 8: {
                    printCharacterData = this.output.printCharacterData(this.getCurrentValue(), allowSpaceAtStart, printCharacterData);
                    this.scanner.clearBuffer();
                    this.type = this.scanner.next();
                    allowSpaceAtStart = false;
                    continue;
                }
                case 11: {
                    this.type = this.scanner.next();
                    if (this.type != 8) {
                        throw this.fatalError("character.data", null);
                    }
                    this.output.printCDATASection(this.getCurrentValue());
                    if (this.scanner.next() != 21) {
                        throw this.fatalError("section.end", null);
                    }
                    this.scanner.clearBuffer();
                    this.type = this.scanner.next();
                    printCharacterData = false;
                    allowSpaceAtStart = false;
                    continue;
                }
                case 9: {
                    allowSpaceAtStart = this.allowSpaceAtStart(this.printElement());
                    continue;
                }
                case 12: {
                    this.output.printCharacterEntityReference(this.getCurrentValue(), allowSpaceAtStart, printCharacterData);
                    this.scanner.clearBuffer();
                    this.type = this.scanner.next();
                    allowSpaceAtStart = false;
                    printCharacterData = false;
                    continue;
                }
                case 13: {
                    this.output.printEntityReference(this.getCurrentValue(), allowSpaceAtStart);
                    this.scanner.clearBuffer();
                    this.type = this.scanner.next();
                    allowSpaceAtStart = false;
                    printCharacterData = false;
                    continue;
                }
                default: {}
            }
        }
    }
    
    protected void printNotation() throws TranscoderException, XMLException, IOException {
        if (this.scanner.next() != 1) {
            throw this.fatalError("space", null);
        }
        final char[] currentValue = this.getCurrentValue();
        if (this.scanner.next() != 14) {
            throw this.fatalError("name", null);
        }
        final char[] currentValue2 = this.getCurrentValue();
        if (this.scanner.next() != 1) {
            throw this.fatalError("space", null);
        }
        final char[] currentValue3 = this.getCurrentValue();
        final int next = this.scanner.next();
        char[] currentValue4 = null;
        char[] currentValue5 = null;
        char stringDelimiter = '\0';
        String s = null;
        char[] array = null;
        char[] array2 = null;
        char c = '\0';
        int n = 0;
        switch (next) {
            default: {
                throw this.fatalError("notation.definition", null);
            }
            case 27: {
                s = "PUBLIC";
                if (this.scanner.next() != 1) {
                    throw this.fatalError("space", null);
                }
                array = this.getCurrentValue();
                if (this.scanner.next() != 25) {
                    throw this.fatalError("string", null);
                }
                array2 = this.getCurrentValue();
                c = this.scanner.getStringDelimiter();
                n = this.scanner.next();
                if (n != 1) {
                    break;
                }
                currentValue4 = this.getCurrentValue();
                n = this.scanner.next();
                if (n == 25) {
                    currentValue5 = this.getCurrentValue();
                    stringDelimiter = this.scanner.getStringDelimiter();
                    n = this.scanner.next();
                    break;
                }
                break;
            }
            case 26: {
                s = "SYSTEM";
                if (this.scanner.next() != 1) {
                    throw this.fatalError("space", null);
                }
                array = this.getCurrentValue();
                if (this.scanner.next() != 25) {
                    throw this.fatalError("string", null);
                }
                array2 = this.getCurrentValue();
                c = this.scanner.getStringDelimiter();
                n = this.scanner.next();
                break;
            }
        }
        char[] currentValue6 = null;
        if (n == 1) {
            currentValue6 = this.getCurrentValue();
            n = this.scanner.next();
        }
        if (n != 20) {
            throw this.fatalError("end", null);
        }
        this.output.printNotation(currentValue, currentValue2, currentValue3, s, array, array2, c, currentValue4, currentValue5, stringDelimiter, currentValue6);
        this.scanner.next();
    }
    
    protected void printAttlist() throws TranscoderException, XMLException, IOException {
        this.type = this.scanner.next();
        if (this.type != 1) {
            throw this.fatalError("space", null);
        }
        char[] array = this.getCurrentValue();
        this.type = this.scanner.next();
        if (this.type != 14) {
            throw this.fatalError("name", null);
        }
        final char[] currentValue = this.getCurrentValue();
        this.type = this.scanner.next();
        this.output.printAttlistStart(array, currentValue);
        while (this.type == 1) {
            array = this.getCurrentValue();
            this.type = this.scanner.next();
            if (this.type != 14) {
                break;
            }
            final char[] currentValue2 = this.getCurrentValue();
            this.type = this.scanner.next();
            if (this.type != 1) {
                throw this.fatalError("space", null);
            }
            final char[] currentValue3 = this.getCurrentValue();
            this.type = this.scanner.next();
            this.output.printAttName(array, currentValue2, currentValue3);
            Label_0977: {
                switch (this.type) {
                    case 45:
                    case 46:
                    case 47:
                    case 48:
                    case 49:
                    case 50:
                    case 51:
                    case 52: {
                        this.output.printCharacters(this.getCurrentValue());
                        this.type = this.scanner.next();
                        break;
                    }
                    case 57: {
                        this.output.printCharacters(this.getCurrentValue());
                        this.type = this.scanner.next();
                        if (this.type != 1) {
                            throw this.fatalError("space", null);
                        }
                        this.output.printSpaces(this.getCurrentValue(), false);
                        this.type = this.scanner.next();
                        if (this.type != 40) {
                            throw this.fatalError("left.brace", null);
                        }
                        this.type = this.scanner.next();
                        final LinkedList<OutputManager.NameInfo> list = new LinkedList<OutputManager.NameInfo>();
                        char[] currentValue4 = null;
                        if (this.type == 1) {
                            currentValue4 = this.getCurrentValue();
                            this.type = this.scanner.next();
                        }
                        if (this.type != 14) {
                            throw this.fatalError("name", null);
                        }
                        final char[] currentValue5 = this.getCurrentValue();
                        this.type = this.scanner.next();
                        char[] currentValue6 = null;
                        if (this.type == 1) {
                            currentValue6 = this.getCurrentValue();
                            this.type = this.scanner.next();
                        }
                        list.add(new OutputManager.NameInfo(currentValue4, currentValue5, currentValue6));
                        while (true) {
                            switch (this.type) {
                                default: {
                                    if (this.type != 41) {
                                        throw this.fatalError("right.brace", null);
                                    }
                                    this.output.printEnumeration(list);
                                    this.type = this.scanner.next();
                                    break Label_0977;
                                }
                                case 42: {
                                    this.type = this.scanner.next();
                                    char[] currentValue7 = null;
                                    if (this.type == 1) {
                                        currentValue7 = this.getCurrentValue();
                                        this.type = this.scanner.next();
                                    }
                                    if (this.type != 14) {
                                        throw this.fatalError("name", null);
                                    }
                                    final char[] currentValue8 = this.getCurrentValue();
                                    this.type = this.scanner.next();
                                    char[] currentValue9 = null;
                                    if (this.type == 1) {
                                        currentValue9 = this.getCurrentValue();
                                        this.type = this.scanner.next();
                                    }
                                    list.add(new OutputManager.NameInfo(currentValue7, currentValue8, currentValue9));
                                    continue;
                                }
                            }
                        }
                        break;
                    }
                    case 40: {
                        this.type = this.scanner.next();
                        final LinkedList<OutputManager.NameInfo> list2 = new LinkedList<OutputManager.NameInfo>();
                        char[] currentValue10 = null;
                        if (this.type == 1) {
                            currentValue10 = this.getCurrentValue();
                            this.type = this.scanner.next();
                        }
                        if (this.type != 56) {
                            throw this.fatalError("nmtoken", null);
                        }
                        final char[] currentValue11 = this.getCurrentValue();
                        this.type = this.scanner.next();
                        char[] currentValue12 = null;
                        if (this.type == 1) {
                            currentValue12 = this.getCurrentValue();
                            this.type = this.scanner.next();
                        }
                        list2.add(new OutputManager.NameInfo(currentValue10, currentValue11, currentValue12));
                        while (true) {
                            switch (this.type) {
                                default: {
                                    if (this.type != 41) {
                                        throw this.fatalError("right.brace", null);
                                    }
                                    this.output.printEnumeration(list2);
                                    this.type = this.scanner.next();
                                    break Label_0977;
                                }
                                case 42: {
                                    this.type = this.scanner.next();
                                    char[] currentValue13 = null;
                                    if (this.type == 1) {
                                        currentValue13 = this.getCurrentValue();
                                        this.type = this.scanner.next();
                                    }
                                    if (this.type != 56) {
                                        throw this.fatalError("nmtoken", null);
                                    }
                                    final char[] currentValue14 = this.getCurrentValue();
                                    this.type = this.scanner.next();
                                    char[] currentValue15 = null;
                                    if (this.type == 1) {
                                        currentValue15 = this.getCurrentValue();
                                        this.type = this.scanner.next();
                                    }
                                    list2.add(new OutputManager.NameInfo(currentValue13, currentValue14, currentValue15));
                                    continue;
                                }
                            }
                        }
                        break;
                    }
                }
            }
            if (this.type == 1) {
                this.output.printSpaces(this.getCurrentValue(), true);
                this.type = this.scanner.next();
            }
            Label_1391: {
                switch (this.type) {
                    default: {
                        throw this.fatalError("default.decl", null);
                    }
                    case 53:
                    case 54: {
                        this.output.printCharacters(this.getCurrentValue());
                        this.type = this.scanner.next();
                        break;
                    }
                    case 55: {
                        this.output.printCharacters(this.getCurrentValue());
                        this.type = this.scanner.next();
                        if (this.type != 1) {
                            throw this.fatalError("space", null);
                        }
                        this.output.printSpaces(this.getCurrentValue(), false);
                        this.type = this.scanner.next();
                        if (this.type != 25 && this.type != 16) {
                            throw this.fatalError("space", null);
                        }
                    }
                    case 16:
                    case 25: {
                        this.output.printCharacter(this.scanner.getStringDelimiter());
                        this.output.printCharacters(this.getCurrentValue());
                        while (true) {
                            switch (this.type = this.scanner.next()) {
                                case 16:
                                case 17:
                                case 18:
                                case 25: {
                                    this.output.printCharacters(this.getCurrentValue());
                                    continue;
                                }
                                case 12: {
                                    this.output.printString("&#");
                                    this.output.printCharacters(this.getCurrentValue());
                                    this.output.printCharacter(';');
                                    continue;
                                }
                                case 13: {
                                    this.output.printCharacter('&');
                                    this.output.printCharacters(this.getCurrentValue());
                                    this.output.printCharacter(';');
                                    continue;
                                }
                                default: {
                                    this.output.printCharacter(this.scanner.getStringDelimiter());
                                    break Label_1391;
                                }
                            }
                        }
                        break;
                    }
                }
            }
            array = null;
        }
        if (this.type != 20) {
            throw this.fatalError("end", null);
        }
        this.output.printAttlistEnd(array);
        this.type = this.scanner.next();
    }
    
    protected void printEntityDeclaration() throws TranscoderException, XMLException, IOException {
        this.writer.write("<!ENTITY");
        this.type = this.scanner.next();
        if (this.type != 1) {
            throw this.fatalError("space", null);
        }
        this.writer.write(this.getCurrentValue());
        this.type = this.scanner.next();
        boolean b = false;
        switch (this.type) {
            default: {
                throw this.fatalError("xml", null);
            }
            case 14: {
                this.writer.write(this.getCurrentValue());
                this.type = this.scanner.next();
                break;
            }
            case 58: {
                b = true;
                this.writer.write(37);
                this.type = this.scanner.next();
                if (this.type != 1) {
                    throw this.fatalError("space", null);
                }
                this.writer.write(this.getCurrentValue());
                this.type = this.scanner.next();
                if (this.type != 14) {
                    throw this.fatalError("name", null);
                }
                this.writer.write(this.getCurrentValue());
                this.type = this.scanner.next();
                break;
            }
        }
        if (this.type != 1) {
            throw this.fatalError("space", null);
        }
        this.writer.write(this.getCurrentValue());
        switch (this.type = this.scanner.next()) {
            case 16:
            case 25: {
                final char stringDelimiter = this.scanner.getStringDelimiter();
                this.writer.write(stringDelimiter);
                while (true) {
                    switch (this.type) {
                        case 16:
                        case 17:
                        case 18:
                        case 25: {
                            this.writer.write(this.getCurrentValue());
                            break;
                        }
                        case 13: {
                            this.writer.write(38);
                            this.writer.write(this.getCurrentValue());
                            this.writer.write(59);
                            break;
                        }
                        case 34: {
                            this.writer.write(38);
                            this.writer.write(this.getCurrentValue());
                            this.writer.write(59);
                            break;
                        }
                        default: {
                            this.writer.write(stringDelimiter);
                            if (this.type == 1) {
                                this.writer.write(this.getCurrentValue());
                                this.type = this.scanner.next();
                            }
                            if (this.type != 20) {
                                throw this.fatalError("end", null);
                            }
                            this.writer.write(">");
                            this.type = this.scanner.next();
                            return;
                        }
                    }
                    this.type = this.scanner.next();
                }
                break;
            }
            case 27: {
                this.writer.write("PUBLIC");
                this.type = this.scanner.next();
                if (this.type != 1) {
                    throw this.fatalError("space", null);
                }
                this.type = this.scanner.next();
                if (this.type != 25) {
                    throw this.fatalError("string", null);
                }
                this.writer.write(" \"");
                this.writer.write(this.getCurrentValue());
                this.writer.write("\" \"");
                this.type = this.scanner.next();
                if (this.type != 1) {
                    throw this.fatalError("space", null);
                }
                this.type = this.scanner.next();
                if (this.type != 25) {
                    throw this.fatalError("string", null);
                }
                this.writer.write(this.getCurrentValue());
                this.writer.write(34);
                break;
            }
            case 26: {
                this.writer.write("SYSTEM");
                this.type = this.scanner.next();
                if (this.type != 1) {
                    throw this.fatalError("space", null);
                }
                this.type = this.scanner.next();
                if (this.type != 25) {
                    throw this.fatalError("string", null);
                }
                this.writer.write(" \"");
                this.writer.write(this.getCurrentValue());
                this.writer.write(34);
                break;
            }
        }
        this.type = this.scanner.next();
        if (this.type == 1) {
            this.writer.write(this.getCurrentValue());
            this.type = this.scanner.next();
            if (!b && this.type == 59) {
                this.writer.write("NDATA");
                this.type = this.scanner.next();
                if (this.type != 1) {
                    throw this.fatalError("space", null);
                }
                this.writer.write(this.getCurrentValue());
                this.type = this.scanner.next();
                if (this.type != 14) {
                    throw this.fatalError("name", null);
                }
                this.writer.write(this.getCurrentValue());
                this.type = this.scanner.next();
            }
            if (this.type == 1) {
                this.writer.write(this.getCurrentValue());
                this.type = this.scanner.next();
            }
        }
        if (this.type != 20) {
            throw this.fatalError("end", null);
        }
        this.writer.write(62);
        this.type = this.scanner.next();
    }
    
    protected void printElementDeclaration() throws TranscoderException, XMLException, IOException {
        this.writer.write("<!ELEMENT");
        this.type = this.scanner.next();
        if (this.type != 1) {
            throw this.fatalError("space", null);
        }
        this.writer.write(this.getCurrentValue());
        switch (this.type = this.scanner.next()) {
            default: {
                throw this.fatalError("name", null);
            }
            case 14: {
                this.writer.write(this.getCurrentValue());
                this.type = this.scanner.next();
                if (this.type != 1) {
                    throw this.fatalError("space", null);
                }
                this.writer.write(this.getCurrentValue());
                Label_0690: {
                    switch (this.type = this.scanner.next()) {
                        case 35: {
                            this.writer.write("EMPTY");
                            this.type = this.scanner.next();
                            break;
                        }
                        case 36: {
                            this.writer.write("ANY");
                            this.type = this.scanner.next();
                            break;
                        }
                        case 40: {
                            this.writer.write(40);
                            this.type = this.scanner.next();
                            if (this.type == 1) {
                                this.writer.write(this.getCurrentValue());
                                this.type = this.scanner.next();
                            }
                            switch (this.type) {
                                case 44: {
                                    this.writer.write("#PCDATA");
                                    this.type = this.scanner.next();
                                    while (true) {
                                        switch (this.type) {
                                            case 1: {
                                                this.writer.write(this.getCurrentValue());
                                                this.type = this.scanner.next();
                                                continue;
                                            }
                                            case 42: {
                                                this.writer.write(124);
                                                this.type = this.scanner.next();
                                                if (this.type == 1) {
                                                    this.writer.write(this.getCurrentValue());
                                                    this.type = this.scanner.next();
                                                }
                                                if (this.type != 14) {
                                                    throw this.fatalError("name", null);
                                                }
                                                this.writer.write(this.getCurrentValue());
                                                this.type = this.scanner.next();
                                                continue;
                                            }
                                            case 41: {
                                                this.writer.write(41);
                                                this.type = this.scanner.next();
                                                break Label_0690;
                                            }
                                        }
                                    }
                                    break;
                                }
                                case 14:
                                case 40: {
                                    this.printChildren();
                                    if (this.type != 41) {
                                        throw this.fatalError("right.brace", null);
                                    }
                                    this.writer.write(41);
                                    this.type = this.scanner.next();
                                    if (this.type == 1) {
                                        this.writer.write(this.getCurrentValue());
                                        this.type = this.scanner.next();
                                    }
                                    switch (this.type) {
                                        case 37: {
                                            this.writer.write(63);
                                            this.type = this.scanner.next();
                                            break Label_0690;
                                        }
                                        case 39: {
                                            this.writer.write(42);
                                            this.type = this.scanner.next();
                                            break Label_0690;
                                        }
                                        case 38: {
                                            this.writer.write(43);
                                            this.type = this.scanner.next();
                                            break Label_0690;
                                        }
                                    }
                                    break;
                                }
                            }
                            break;
                        }
                    }
                }
                if (this.type == 1) {
                    this.writer.write(this.getCurrentValue());
                    this.type = this.scanner.next();
                }
                if (this.type != 20) {
                    throw this.fatalError("end", null);
                }
                this.writer.write(62);
                this.scanner.next();
            }
        }
    }
    
    protected void printChildren() throws TranscoderException, XMLException, IOException {
        int n = 0;
        while (true) {
            switch (this.type) {
                default: {
                    throw new RuntimeException("Invalid XML");
                }
                case 14: {
                    this.writer.write(this.getCurrentValue());
                    this.type = this.scanner.next();
                    break;
                }
                case 40: {
                    this.writer.write(40);
                    this.type = this.scanner.next();
                    if (this.type == 1) {
                        this.writer.write(this.getCurrentValue());
                        this.type = this.scanner.next();
                    }
                    this.printChildren();
                    if (this.type != 41) {
                        throw this.fatalError("right.brace", null);
                    }
                    this.writer.write(41);
                    this.type = this.scanner.next();
                    break;
                }
            }
            if (this.type == 1) {
                this.writer.write(this.getCurrentValue());
                this.type = this.scanner.next();
            }
            switch (this.type) {
                case 41: {
                    return;
                }
                case 39: {
                    this.writer.write(42);
                    this.type = this.scanner.next();
                    break;
                }
                case 37: {
                    this.writer.write(63);
                    this.type = this.scanner.next();
                    break;
                }
                case 38: {
                    this.writer.write(43);
                    this.type = this.scanner.next();
                    break;
                }
            }
            if (this.type == 1) {
                this.writer.write(this.getCurrentValue());
                this.type = this.scanner.next();
            }
            switch (this.type) {
                case 42: {
                    if (n != 0 && n != this.type) {
                        throw new RuntimeException("Invalid XML");
                    }
                    this.writer.write(124);
                    n = this.type;
                    this.type = this.scanner.next();
                    break;
                }
                case 43: {
                    if (n != 0 && n != this.type) {
                        throw new RuntimeException("Invalid XML");
                    }
                    this.writer.write(44);
                    n = this.type;
                    this.type = this.scanner.next();
                    break;
                }
            }
            if (this.type == 1) {
                this.writer.write(this.getCurrentValue());
                this.type = this.scanner.next();
            }
        }
    }
    
    protected char[] getCurrentValue() {
        final int n = this.scanner.getStart() + this.scanner.getStartOffset();
        final int n2 = this.scanner.getEnd() + this.scanner.getEndOffset() - n;
        final char[] array = new char[n2];
        System.arraycopy(this.scanner.getBuffer(), n, array, 0, n2);
        return array;
    }
    
    protected TranscoderException fatalError(final String s, final Object[] array) throws TranscoderException {
        final TranscoderException ex = new TranscoderException(s);
        this.errorHandler.fatalError(ex);
        return ex;
    }
}
