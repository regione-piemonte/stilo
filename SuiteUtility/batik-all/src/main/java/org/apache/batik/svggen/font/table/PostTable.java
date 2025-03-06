// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.svggen.font.table;

import java.io.IOException;
import java.io.RandomAccessFile;

public class PostTable implements Table
{
    private static final String[] macGlyphName;
    private int version;
    private int italicAngle;
    private short underlinePosition;
    private short underlineThickness;
    private int isFixedPitch;
    private int minMemType42;
    private int maxMemType42;
    private int minMemType1;
    private int maxMemType1;
    private int numGlyphs;
    private int[] glyphNameIndex;
    private String[] psGlyphName;
    
    protected PostTable(final DirectoryEntry directoryEntry, final RandomAccessFile randomAccessFile) throws IOException {
        randomAccessFile.seek(directoryEntry.getOffset());
        this.version = randomAccessFile.readInt();
        this.italicAngle = randomAccessFile.readInt();
        this.underlinePosition = randomAccessFile.readShort();
        this.underlineThickness = randomAccessFile.readShort();
        this.isFixedPitch = randomAccessFile.readInt();
        this.minMemType42 = randomAccessFile.readInt();
        this.maxMemType42 = randomAccessFile.readInt();
        this.minMemType1 = randomAccessFile.readInt();
        this.maxMemType1 = randomAccessFile.readInt();
        if (this.version == 131072) {
            this.numGlyphs = randomAccessFile.readUnsignedShort();
            this.glyphNameIndex = new int[this.numGlyphs];
            for (int i = 0; i < this.numGlyphs; ++i) {
                this.glyphNameIndex[i] = randomAccessFile.readUnsignedShort();
            }
            final int highestGlyphNameIndex = this.highestGlyphNameIndex();
            if (highestGlyphNameIndex > 257) {
                final int n = highestGlyphNameIndex - 257;
                this.psGlyphName = new String[n];
                for (int j = 0; j < n; ++j) {
                    final byte[] array = new byte[randomAccessFile.readUnsignedByte()];
                    randomAccessFile.readFully(array);
                    this.psGlyphName[j] = new String(array);
                }
            }
        }
        else if (this.version == 131077) {}
    }
    
    private int highestGlyphNameIndex() {
        int n = 0;
        for (int i = 0; i < this.numGlyphs; ++i) {
            if (n < this.glyphNameIndex[i]) {
                n = this.glyphNameIndex[i];
            }
        }
        return n;
    }
    
    public String getGlyphName(final int n) {
        if (this.version == 131072) {
            return (this.glyphNameIndex[n] > 257) ? this.psGlyphName[this.glyphNameIndex[n] - 258] : PostTable.macGlyphName[this.glyphNameIndex[n]];
        }
        return null;
    }
    
    public int getType() {
        return 1886352244;
    }
    
    static {
        macGlyphName = new String[] { ".notdef", "null", "CR", "space", "exclam", "quotedbl", "numbersign", "dollar", "percent", "ampersand", "quotesingle", "parenleft", "parenright", "asterisk", "plus", "comma", "hyphen", "period", "slash", "zero", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine", "colon", "semicolon", "less", "equal", "greater", "question", "at", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "bracketleft", "backslash", "bracketright", "asciicircum", "underscore", "grave", "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z", "braceleft", "bar", "braceright", "asciitilde", "Adieresis", "Aring", "Ccedilla", "Eacute", "Ntilde", "Odieresis", "Udieresis", "aacute", "agrave", "acircumflex", "adieresis", "atilde", "aring", "ccedilla", "eacute", "egrave", "ecircumflex", "edieresis", "iacute", "igrave", "icircumflex", "idieresis", "ntilde", "oacute", "ograve", "ocircumflex", "odieresis", "otilde", "uacute", "ugrave", "ucircumflex", "udieresis", "dagger", "degree", "cent", "sterling", "section", "bullet", "paragraph", "germandbls", "registered", "copyright", "trademark", "acute", "dieresis", "notequal", "AE", "Oslash", "infinity", "plusminus", "lessequal", "greaterequal", "yen", "mu", "partialdiff", "summation", "product", "pi", "integral'", "ordfeminine", "ordmasculine", "Omega", "ae", "oslash", "questiondown", "exclamdown", "logicalnot", "radical", "florin", "approxequal", "increment", "guillemotleft", "guillemotright", "ellipsis", "nbspace", "Agrave", "Atilde", "Otilde", "OE", "oe", "endash", "emdash", "quotedblleft", "quotedblright", "quoteleft", "quoteright", "divide", "lozenge", "ydieresis", "Ydieresis", "fraction", "currency", "guilsinglleft", "guilsinglright", "fi", "fl", "daggerdbl", "middot", "quotesinglbase", "quotedblbase", "perthousand", "Acircumflex", "Ecircumflex", "Aacute", "Edieresis", "Egrave", "Iacute", "Icircumflex", "Idieresis", "Igrave", "Oacute", "Ocircumflex", "", "Ograve", "Uacute", "Ucircumflex", "Ugrave", "dotlessi", "circumflex", "tilde", "overscore", "breve", "dotaccent", "ring", "cedilla", "hungarumlaut", "ogonek", "caron", "Lslash", "lslash", "Scaron", "scaron", "Zcaron", "zcaron", "brokenbar", "Eth", "eth", "Yacute", "yacute", "Thorn", "thorn", "minus", "multiply", "onesuperior", "twosuperior", "threesuperior", "onehalf", "onequarter", "threequarters", "franc", "Gbreve", "gbreve", "Idot", "Scedilla", "scedilla", "Cacute", "cacute", "Ccaron", "ccaron", "" };
    }
}
