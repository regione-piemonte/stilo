// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.svggen.font;

import java.io.OutputStream;
import java.io.FileOutputStream;
import org.apache.batik.svggen.font.table.KerningPair;
import org.apache.batik.svggen.font.table.KernSubtable;
import org.apache.batik.svggen.font.table.Feature;
import org.apache.batik.svggen.font.table.LangSys;
import org.apache.batik.svggen.font.table.Script;
import org.apache.batik.svggen.font.table.CmapFormat;
import org.apache.batik.svggen.font.table.PostTable;
import org.apache.batik.svggen.font.table.KernTable;
import org.apache.batik.svggen.font.table.SingleSubst;
import org.apache.batik.svggen.font.table.GsubTable;
import java.io.PrintStream;
import org.apache.batik.svggen.font.table.FeatureTags;
import org.apache.batik.svggen.font.table.ScriptTags;
import org.apache.batik.util.SVGConstants;
import org.apache.batik.util.XMLConstants;

public class SVGFont implements XMLConstants, SVGConstants, ScriptTags, FeatureTags
{
    static final String EOL;
    static final String PROPERTY_LINE_SEPARATOR = "line.separator";
    static final String PROPERTY_LINE_SEPARATOR_DEFAULT = "\n";
    static final int DEFAULT_FIRST = 32;
    static final int DEFAULT_LAST = 128;
    private static String QUOT_EOL;
    private static String CONFIG_USAGE;
    private static String CONFIG_SVG_BEGIN;
    private static String CONFIG_SVG_TEST_CARD_START;
    private static String CONFIG_SVG_TEST_CARD_END;
    public static final char ARG_KEY_START_CHAR = '-';
    public static final String ARG_KEY_CHAR_RANGE_LOW = "-l";
    public static final String ARG_KEY_CHAR_RANGE_HIGH = "-h";
    public static final String ARG_KEY_ID = "-id";
    public static final String ARG_KEY_ASCII = "-ascii";
    public static final String ARG_KEY_TESTCARD = "-testcard";
    public static final String ARG_KEY_AUTO_RANGE = "-autorange";
    public static final String ARG_KEY_OUTPUT_PATH = "-o";
    
    protected static String encodeEntities(final String s) {
        final StringBuffer sb = new StringBuffer();
        for (int i = 0; i < s.length(); ++i) {
            if (s.charAt(i) == '<') {
                sb.append("&lt;");
            }
            else if (s.charAt(i) == '>') {
                sb.append("&gt;");
            }
            else if (s.charAt(i) == '&') {
                sb.append("&amp;");
            }
            else if (s.charAt(i) == '\'') {
                sb.append("&apos;");
            }
            else if (s.charAt(i) == '\"') {
                sb.append("&quot;");
            }
            else {
                sb.append(s.charAt(i));
            }
        }
        return sb.toString();
    }
    
    protected static String getContourAsSVGPathData(final Glyph glyph, final int n, final int n2) {
        if (glyph.getPoint(n).endOfContour) {
            return "";
        }
        final StringBuffer sb = new StringBuffer();
        int i = 0;
        while (i < n2) {
            final Point point = glyph.getPoint(n + i % n2);
            final Point point2 = glyph.getPoint(n + (i + 1) % n2);
            final Point point3 = glyph.getPoint(n + (i + 2) % n2);
            if (i == 0) {
                sb.append("M").append(String.valueOf(point.x)).append(" ").append(String.valueOf(point.y));
            }
            if (point.onCurve && point2.onCurve) {
                if (point2.x == point.x) {
                    sb.append("V").append(String.valueOf(point2.y));
                }
                else if (point2.y == point.y) {
                    sb.append("H").append(String.valueOf(point2.x));
                }
                else {
                    sb.append("L").append(String.valueOf(point2.x)).append(" ").append(String.valueOf(point2.y));
                }
                ++i;
            }
            else if (point.onCurve && !point2.onCurve && point3.onCurve) {
                sb.append("Q").append(String.valueOf(point2.x)).append(" ").append(String.valueOf(point2.y)).append(" ").append(String.valueOf(point3.x)).append(" ").append(String.valueOf(point3.y));
                i += 2;
            }
            else if (point.onCurve && !point2.onCurve && !point3.onCurve) {
                sb.append("Q").append(String.valueOf(point2.x)).append(" ").append(String.valueOf(point2.y)).append(" ").append(String.valueOf(midValue(point2.x, point3.x))).append(" ").append(String.valueOf(midValue(point2.y, point3.y)));
                i += 2;
            }
            else if (!point.onCurve && !point2.onCurve) {
                sb.append("T").append(String.valueOf(midValue(point.x, point2.x))).append(" ").append(String.valueOf(midValue(point.y, point2.y)));
                ++i;
            }
            else {
                if (point.onCurve || !point2.onCurve) {
                    System.out.println("drawGlyph case not catered for!!");
                    break;
                }
                sb.append("T").append(String.valueOf(point2.x)).append(" ").append(String.valueOf(point2.y));
                ++i;
            }
        }
        sb.append("Z");
        return sb.toString();
    }
    
    protected static String getSVGFontFaceElement(final Font font) {
        final StringBuffer sb = new StringBuffer();
        sb.append("<").append("font-face").append(SVGFont.EOL).append("    ").append("font-family").append("=\"").append(font.getNameTable().getRecord((short)1)).append(SVGFont.QUOT_EOL).append("    ").append("units-per-em").append("=\"").append(font.getHeadTable().getUnitsPerEm()).append(SVGFont.QUOT_EOL).append("    ").append("panose-1").append("=\"").append(font.getOS2Table().getPanose().toString()).append(SVGFont.QUOT_EOL).append("    ").append("ascent").append("=\"").append(font.getHheaTable().getAscender()).append(SVGFont.QUOT_EOL).append("    ").append("descent").append("=\"").append(font.getHheaTable().getDescender()).append(SVGFont.QUOT_EOL).append("    ").append("alphabetic").append("=\"").append(0).append('\"').append(" />").append(SVGFont.EOL);
        return sb.toString();
    }
    
    protected static void writeFontAsSVGFragment(final PrintStream printStream, final Font font, final String s, int first, int last, final boolean b, final boolean b2) throws Exception {
        final short avgCharWidth = font.getOS2Table().getAvgCharWidth();
        printStream.print("<");
        printStream.print("font");
        printStream.print(" ");
        if (s != null) {
            printStream.print("id");
            printStream.print("=\"");
            printStream.print(s);
            printStream.print('\"');
            printStream.print(" ");
        }
        printStream.print("horiz-adv-x");
        printStream.print("=\"");
        printStream.print(avgCharWidth);
        printStream.print('\"');
        printStream.print(" >");
        printStream.print(getSVGFontFaceElement(font));
        CmapFormat cmapFormat;
        if (b2) {
            cmapFormat = font.getCmapTable().getCmapFormat((short)1, (short)0);
        }
        else {
            cmapFormat = font.getCmapTable().getCmapFormat((short)3, (short)1);
            if (cmapFormat == null) {
                cmapFormat = font.getCmapTable().getCmapFormat((short)3, (short)0);
            }
        }
        if (cmapFormat == null) {
            throw new Exception("Cannot find a suitable cmap table");
        }
        final GsubTable gsubTable = (GsubTable)font.getTable(1196643650);
        SingleSubst singleSubst = null;
        SingleSubst singleSubst2 = null;
        SingleSubst singleSubst3 = null;
        if (gsubTable != null) {
            final Script script = gsubTable.getScriptList().findScript("arab");
            if (script != null) {
                final LangSys defaultLangSys = script.getDefaultLangSys();
                if (defaultLangSys != null) {
                    final Feature feature = gsubTable.getFeatureList().findFeature(defaultLangSys, "init");
                    final Feature feature2 = gsubTable.getFeatureList().findFeature(defaultLangSys, "medi");
                    final Feature feature3 = gsubTable.getFeatureList().findFeature(defaultLangSys, "fina");
                    singleSubst = (SingleSubst)gsubTable.getLookupList().getLookup(feature, 0).getSubtable(0);
                    singleSubst2 = (SingleSubst)gsubTable.getLookupList().getLookup(feature2, 0).getSubtable(0);
                    singleSubst3 = (SingleSubst)gsubTable.getLookupList().getLookup(feature3, 0).getSubtable(0);
                }
            }
        }
        printStream.println(getGlyphAsSVG(font, font.getGlyph(0), 0, avgCharWidth, singleSubst, singleSubst2, singleSubst3, ""));
        try {
            if (first == -1) {
                if (!b) {
                    first = 32;
                }
                else {
                    first = cmapFormat.getFirst();
                }
            }
            if (last == -1) {
                if (!b) {
                    last = 128;
                }
                else {
                    last = cmapFormat.getLast();
                }
            }
            for (int i = first; i <= last; ++i) {
                final int mapCharCode = cmapFormat.mapCharCode(i);
                if (mapCharCode > 0) {
                    printStream.println(getGlyphAsSVG(font, font.getGlyph(mapCharCode), mapCharCode, avgCharWidth, singleSubst, singleSubst2, singleSubst3, (32 <= i && i <= 127) ? encodeEntities(String.valueOf((char)i)) : ("&#x" + Integer.toHexString(i) + ";")));
                }
            }
            final KernTable kernTable = (KernTable)font.getTable(1801810542);
            if (kernTable != null) {
                final KernSubtable subtable = kernTable.getSubtable(0);
                final PostTable postTable = (PostTable)font.getTable(1886352244);
                for (int j = 0; j < subtable.getKerningPairCount(); ++j) {
                    printStream.println(getKerningPairAsSVG(subtable.getKerningPair(j), postTable));
                }
            }
        }
        catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
        printStream.print("</");
        printStream.print("font");
        printStream.println(">");
    }
    
    protected static String getGlyphAsSVG(final Font font, final Glyph glyph, final int n, final int n2, final String str, final String str2) {
        final StringBuffer sb = new StringBuffer();
        int n3 = 0;
        int n4 = 0;
        final int advanceWidth = font.getHmtxTable().getAdvanceWidth(n);
        if (n == 0) {
            sb.append("<");
            sb.append("missing-glyph");
        }
        else {
            sb.append("<").append("glyph").append(" ").append("unicode").append("=\"").append(str2).append('\"');
            sb.append(" ").append("glyph-name").append("=\"").append(font.getPostTable().getGlyphName(n)).append('\"');
        }
        if (advanceWidth != n2) {
            sb.append(" ").append("horiz-adv-x").append("=\"").append(advanceWidth).append('\"');
        }
        if (str != null) {
            sb.append(str);
        }
        if (glyph != null) {
            sb.append(" ").append("d").append("=\"");
            for (int i = 0; i < glyph.getPointCount(); ++i) {
                ++n4;
                if (glyph.getPoint(i).endOfContour) {
                    sb.append(getContourAsSVGPathData(glyph, n3, n4));
                    n3 = i + 1;
                    n4 = 0;
                }
            }
            sb.append('\"');
        }
        sb.append(" />");
        chopUpStringBuffer(sb);
        return sb.toString();
    }
    
    protected static String getGlyphAsSVG(final Font font, final Glyph glyph, final int n, final int n2, final SingleSubst singleSubst, final SingleSubst singleSubst2, final SingleSubst singleSubst3, final String s) {
        final StringBuffer sb = new StringBuffer();
        boolean b = false;
        int substitute = n;
        int substitute2 = n;
        int substitute3 = n;
        if (singleSubst != null) {
            substitute = singleSubst.substitute(n);
        }
        if (singleSubst2 != null) {
            substitute2 = singleSubst2.substitute(n);
        }
        if (singleSubst3 != null) {
            substitute3 = singleSubst3.substitute(n);
        }
        if (substitute != n) {
            sb.append(getGlyphAsSVG(font, font.getGlyph(substitute), substitute, n2, " arabic-form=\"initial\"", s));
            sb.append(SVGFont.EOL);
            b = true;
        }
        if (substitute2 != n) {
            sb.append(getGlyphAsSVG(font, font.getGlyph(substitute2), substitute2, n2, " arabic-form=\"medial\"", s));
            sb.append(SVGFont.EOL);
            b = true;
        }
        if (substitute3 != n) {
            sb.append(getGlyphAsSVG(font, font.getGlyph(substitute3), substitute3, n2, " arabic-form=\"terminal\"", s));
            sb.append(SVGFont.EOL);
            b = true;
        }
        if (b) {
            sb.append(getGlyphAsSVG(font, glyph, n, n2, " arabic-form=\"isolated\"", s));
        }
        else {
            sb.append(getGlyphAsSVG(font, glyph, n, n2, null, s));
        }
        return sb.toString();
    }
    
    protected static String getKerningPairAsSVG(final KerningPair kerningPair, final PostTable postTable) {
        final StringBuffer sb = new StringBuffer();
        sb.append("<").append("hkern").append(" ");
        sb.append("g1").append("=\"");
        sb.append(postTable.getGlyphName(kerningPair.getLeft()));
        sb.append('\"').append(" ").append("g2").append("=\"");
        sb.append(postTable.getGlyphName(kerningPair.getRight()));
        sb.append('\"').append(" ").append("k").append("=\"");
        sb.append(-kerningPair.getValue());
        sb.append('\"').append(" />");
        return sb.toString();
    }
    
    protected static void writeSvgBegin(final PrintStream printStream) {
        printStream.println(Messages.formatMessage(SVGFont.CONFIG_SVG_BEGIN, new Object[] { "-//W3C//DTD SVG 1.0//EN", "http://www.w3.org/TR/2001/REC-SVG-20010904/DTD/svg10.dtd" }));
    }
    
    protected static void writeSvgDefsBegin(final PrintStream printStream) {
        printStream.println("<defs >");
    }
    
    protected static void writeSvgDefsEnd(final PrintStream printStream) {
        printStream.println("</defs>");
    }
    
    protected static void writeSvgEnd(final PrintStream printStream) {
        printStream.println("</svg>");
    }
    
    protected static void writeSvgTestCard(final PrintStream printStream, final String x) {
        printStream.println(Messages.formatMessage(SVGFont.CONFIG_SVG_TEST_CARD_START, null));
        printStream.println(x);
        printStream.println(Messages.formatMessage(SVGFont.CONFIG_SVG_TEST_CARD_END, null));
    }
    
    public static void main(final String[] array) {
        try {
            final String args = parseArgs(array, null);
            final String args2 = parseArgs(array, "-l");
            final String args3 = parseArgs(array, "-h");
            final String args4 = parseArgs(array, "-id");
            final String args5 = parseArgs(array, "-ascii");
            final String args6 = parseArgs(array, "-testcard");
            final String args7 = parseArgs(array, "-o");
            final String args8 = parseArgs(array, "-autorange");
            FileOutputStream out = null;
            PrintStream out2;
            if (args7 != null) {
                out = new FileOutputStream(args7);
                out2 = new PrintStream(out);
            }
            else {
                out2 = System.out;
            }
            if (args != null) {
                final Font create = Font.create(args);
                writeSvgBegin(out2);
                writeSvgDefsBegin(out2);
                writeFontAsSVGFragment(out2, create, args4, (args2 != null) ? Integer.parseInt(args2) : -1, (args3 != null) ? Integer.parseInt(args3) : -1, args8 != null, args5 != null);
                writeSvgDefsEnd(out2);
                if (args6 != null) {
                    writeSvgTestCard(out2, create.getNameTable().getRecord((short)1));
                }
                writeSvgEnd(out2);
                if (out != null) {
                    out.close();
                }
            }
            else {
                usage();
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
            System.err.println(ex.getMessage());
            usage();
        }
    }
    
    private static void chopUpStringBuffer(final StringBuffer sb) {
        if (sb.length() < 256) {
            return;
        }
        for (int i = 240; i < sb.length(); ++i) {
            if (sb.charAt(i) == ' ') {
                sb.setCharAt(i, '\n');
                i += 240;
            }
        }
    }
    
    private static int midValue(final int n, final int n2) {
        return n + (n2 - n) / 2;
    }
    
    private static String parseArgs(final String[] array, final String s) {
        for (int i = 0; i < array.length; ++i) {
            if (s == null) {
                if (array[i].charAt(0) != '-') {
                    return array[i];
                }
            }
            else if (s.equalsIgnoreCase(array[i])) {
                if (i < array.length - 1 && array[i + 1].charAt(0) != '-') {
                    return array[i + 1];
                }
                return array[i];
            }
        }
        return null;
    }
    
    private static void usage() {
        System.err.println(Messages.formatMessage(SVGFont.CONFIG_USAGE, null));
    }
    
    static {
        String property;
        try {
            property = System.getProperty("line.separator", "\n");
        }
        catch (SecurityException ex) {
            property = "\n";
        }
        EOL = property;
        SVGFont.QUOT_EOL = '\"' + SVGFont.EOL;
        SVGFont.CONFIG_USAGE = "SVGFont.config.usage";
        SVGFont.CONFIG_SVG_BEGIN = "SVGFont.config.svg.begin";
        SVGFont.CONFIG_SVG_TEST_CARD_START = "SVGFont.config.svg.test.card.start";
        SVGFont.CONFIG_SVG_TEST_CARD_END = "SVGFont.config.svg.test.card.end";
    }
}
