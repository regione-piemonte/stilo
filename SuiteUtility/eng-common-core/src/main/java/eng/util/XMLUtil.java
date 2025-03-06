// 
// Decompiled by Procyon v0.5.36
// 

package eng.util;

import java.net.URLEncoder;
import java.util.StringTokenizer;

public class XMLUtil
{
    public static final String[] verySpecialChars;
    public static final int MAX_ROWS = 20;
    public static final String[] xml_entities;
    public static final String[] special_chars;
    public static final String[] special_uri_chars;
    
    public static String verySpecialEscape(String text) {
        if (text == null) {
            return "";
        }
        int i;
        for (i = 0, i = 0; i < XMLUtil.verySpecialChars.length; i += 2) {
            text = string_replace(XMLUtil.verySpecialChars[i], XMLUtil.verySpecialChars[i + 1], text);
        }
        return text;
    }
    
    public static String capitalize(final String text) {
        if (text == null) {
            return "";
        }
        if (text.length() == 0) {
            return text;
        }
        final StringTokenizer tokenizer = new StringTokenizer(text);
        String toReturn = "";
        String nextToken = "";
        while (tokenizer.hasMoreTokens()) {
            nextToken = tokenizer.nextToken();
            toReturn = toReturn + " " + nextToken.substring(0, 1).toUpperCase() + nextToken.substring(1).toLowerCase();
        }
        return toReturn;
    }
    
    public static String xmlEscapeEntities(String text) {
        final int passo = 1;
        if (text == null) {
            return "";
        }
        for (int i = 0, inizio = i = 0; i < XMLUtil.xml_entities.length; i += 2) {
            text = string_replace(XMLUtil.xml_entities[i], XMLUtil.xml_entities[i + passo], text);
        }
        return text;
    }
    
    public static String xmlEscape(final String text, final int escapeBlank) {
        return xmlEscape(text, 0, escapeBlank);
    }
    
    public static String xmlEscape(final String text) {
        return xmlEscape(text, 0, 0);
    }
    
    public static String xmlEscape(String text, final int tipoEscape, final int escapeBlank) {
        int passo = 2;
        if (tipoEscape == 1) {
            passo = 1;
        }
        if (text == null) {
            return "";
        }
        for (int i = 0, inizio = i = 0; i < XMLUtil.special_chars.length; i += 3) {
            if ((escapeBlank == 1 && !XMLUtil.special_chars[i].equals(" ") && !XMLUtil.special_chars[i].equals("&")) || (escapeBlank == 2 && !XMLUtil.special_chars[i].equals(" ")) || escapeBlank == 0 || escapeBlank > 2) {
                text = string_replace(XMLUtil.special_chars[i], XMLUtil.special_chars[i + passo], text);
            }
        }
        return text;
    }
    
    public static String uriEscape(String uri) {
        if (uri == null) {
            return "";
        }
        for (int i = 0; i < XMLUtil.special_uri_chars.length; i += 2) {
            uri = string_replace(XMLUtil.special_uri_chars[i], XMLUtil.special_uri_chars[i + 1], uri);
        }
        return uri;
    }
    
    public static String uriEscapeJ(final String uri) {
        String newUri = uri;
        try {
            newUri = URLEncoder.encode(uri, "ISO-8859-1");
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return newUri;
    }
    
    public static String xmlUnEscape(String text) {
        if (text == null) {
            return "";
        }
        for (int i = 0; i < XMLUtil.special_chars.length; i += 3) {
            text = string_replace(XMLUtil.special_chars[i], XMLUtil.special_chars[0], text);
        }
        return text;
    }
    
    public static String safeScriptString(String text) {
        final String oldText = text;
        if (text == null) {
            return "";
        }
        text = string_replace("\n", "\\n", text);
        if (text.equals(oldText)) {
            text = string_replace("\\", "\\\\", text);
            if (text.equals(oldText)) {
                text = string_replace("\"", "\\\"", text);
            }
        }
        text = string_replace("'", "'", text);
        return text;
    }
    
    public static String safeScriptString(String text, final String prima, final String dopo) {
        if (text == null) {
            return "";
        }
        text = string_replace(prima, dopo, text);
        return text;
    }
    
    public static String string_replace(final String s1, final String s2, final String s3) {
        String string = "";
        string = "";
        if (s2 == null || s3 == null) {
            return s3;
        }
        int pos_i = 0;
        for (int pos_f = 0, len = s1.length(); (pos_f = s3.indexOf(s1, pos_i)) >= 0; pos_i = (pos_f += len)) {
            string = string + s3.substring(pos_i, pos_f) + s2;
        }
        string += s3.substring(pos_i);
        return string;
    }
    
    public static String getEstensioneFile(final String filename) {
        if (filename == null) {
            return "";
        }
        String ext = "";
        if (filename.lastIndexOf(".") != -1) {
            ext = filename.substring(filename.lastIndexOf("."));
            if (ext.toLowerCase().equals(".p7m")) {
                return getEstensioneFile(filename.substring(0, filename.length() - 4)) + ext;
            }
        }
        return ext;
    }
    
    public static String creaQueryPaginazione(final String sql, String numPagina) {
        String sqlTot = "";
        if (numPagina == null || numPagina.equals("")) {
            numPagina = "1";
        }
        sqlTot = "SELECT TTT.* ";
        sqlTot += "from ( SELECT TT.*, rownum contaRighe from ( ";
        sqlTot += sql;
        sqlTot += ") TT ) TTT ";
        sqlTot += " WHERE TTT.contaRighe between ";
        sqlTot += String.valueOf(1 + 20 * (Integer.parseInt(numPagina) - 1));
        sqlTot += " and ";
        sqlTot += String.valueOf(21 + 20 * (Integer.parseInt(numPagina) - 1));
        return sqlTot;
    }
    
    public static String nvl(final Object obj, final String defaultValue) {
        if (obj == null) {
            return defaultValue;
        }
        return obj.toString();
    }
    
    public static String underlineWords(String word, final String charList) {
        final int listL = charList.length();
        final int[] lastPos = new int[listL];
        String prefix = "";
        String suffix = "";
        for (int i = 0; i < listL; ++i) {
            lastPos[i] = 0;
        }
        String curChar = "";
        for (int j = 0; j < listL; ++j) {
            curChar = charList.substring(j, j + 1);
            lastPos[j] = ((lastPos[j] > word.indexOf(curChar, lastPos[j])) ? lastPos[j] : word.indexOf(curChar, lastPos[j]));
            if (lastPos[j] >= 0) {
                prefix = word.substring(0, lastPos[j]);
                suffix = "<span class=\"underlined\">" + curChar + "</span>" + word.substring(lastPos[j] + 1);
                word = prefix + suffix;
            }
        }
        return word;
    }
    
    static {
        verySpecialChars = new String[] { new String(new char[] { '\u0080' }), "§#01" };
        xml_entities = new String[] { "&", "&amp;", ">", "&gt;", "<", "&lt;", "\"", "&quot;", "'", "&apos;" };
        special_chars = new String[] { "&", "&amp;", "&#038;", "%", "", "&#037;", " ", "&nbsp;", "&#160;", ">", "&gt;", "&#062;", "<", "&lt;", "&#060;", "\"", "&quot;", "&quot;", "'", "&apos;", "&#039;", "\u00d0", "&ETH;", "&#208;", "¡", "&iexcl;", "&#161;", "\u00d1", "&Ntilde;", "&#209;", "¢", "&cent;", "&#162;", "\u00d2", "&Ograve;", "&#210;", "£", "&pound;", "&#163;", "\u00d3", "&Oacute;", "&#211;", "¤", "&curren;", "&#164;", "\u00d4", "&Ocirc;", "&#212;", "¥", "&yen;", "&#165;", "\u00d5", "&Otilde;", "&#213;", "¦", "&brvbar;", "&#166;", "\u00d6", "&Ouml;", "&#214;", "§", "&sect;", "&#167;", "\u00d7", "&times;", "&#215;", "¨", "&uml;", "&#168;", "\u00d8", "&Oslash;", "&#216;", "©", "&copy;", "&#169;", "\u00d9", "&Ugrave;", "&#217;", "ª", "&ordf;", "&#170;", "\u00da", "&Uacute;", "&#218;", "«", "&laquo;", "&#171;", "\u00db", "&Ucirc;", "&#219;", "¬", "&not;", "&#172;", "\u00dc", "&Uuml;", "&#220;", "\u00ad", "&shy;", "&#173;", "\u00dd", "&Yacute;", "&#221;", "®", "&reg;", "&#174;", "\u00de", "&THORN;", "&#222;", "¯", "&macr;", "&#175;", "\u00df", "&szlig;", "&#223;", "°", "&deg;", "&#176;", "\u00e0", "&agrave;", "&#224;", "±", "&plusmn;", "&#177;", "\u00e1", "&aacute;", "&#225;", "²", "&sup2;", "&#178;", "\u00e2", "&acirc;", "&#226;", "³", "&sup3;", "&#179;", "\u00e3", "&atilde;", "&#227;", "´", "&acute;", "&#180;", "\u00e4", "&auml;", "&#228;", "µ", "&micro;", "&#181;", "\u00e5", "&aring;", "&#229;", "¶", "&para;", "&#182;", "\u00e6", "&aelig;", "&#230;", "·", "&middot;", "&#183;", "\u00e7", "&ccedil;", "&#231;", "¸", "&cedil;", "&#184;", "\u00e8", "&egrave;", "&#232;", "¹", "&sup1;", "&#185;", "\u00e9", "&eacute;", "&#233;", "º", "&ordm;", "&#186;", "\u00ea", "&ecirc;", "&#234;", "»", "&raquo;", "&#187;", "\u00eb", "&euml;", "&#235;", "¼", "&frac14;", "&#188;", "\u00ec", "&igrave;", "&#236;", "½", "&frac12;", "&#189;", "\u00ed", "&iacute;", "&#237;", "¾", "&frac34;", "&#190;", "\u00ee", "&icirc;", "&#238;", "¿", "&iquest;", "&#191;", "\u00ef", "&iuml;", "&#239;", "\u00c0", "&Agrave;", "&#192;", "\u00f0", "&eth;", "&#240;", "\u00c1", "&Aacute;", "&#193;", "\u00f1", "&ntilde;", "&#241;", "\u00c2", "&Acirc; ", "&#194;", "\u00f2", "&ograve;", "&#242;", "\u00c3", "&Atilde;", "&#195;", "\u00f3", "&oacute;", "&#243;", "\u00c4", "&Auml;", "&#196;", "\u00f4", "&ocirc;", "&#244;", "\u00c5", "&Aring;", "&#197;", "\u00f5", "&otilde;", "&#245;", "\u00c6", "&AElig;", "&#198;", "\u00f6", "&ouml;", "&#246;", "\u00c7", "&Ccedil;", "&#199;", "\u00f7", "&divide;", "&#247;", "\u00c8", "&Egrave;", "&#200;", "\u00f8", "&oslash;", "&#248;", "\u00c9", "&Eacute;", "&#201;", "\u00f9", "&ugrave;", "&#249;", "\u00ca", "&Ecirc; ", "&#202;", "\u00fa", "&uacute;", "&#250;", "\u00cb", "&Euml;", "&#203;", "\u00fb", "&ucirc;", "&#251;", "\u00cc", "&Igrave;", "&#204;", "\u00fc", "&uuml;", "&#252;", "\u00cd", "&Iacute;", "&#205;", "\u00fd", "&yacute;", "&#253;", "\u00ce", "&Icirc;", "&#206;", "\u00fe", "&thorn;", "&#254;", "\u00cf", "&Iuml;", "&#207;", "\u00ff", "&yuml;", "&#255;" };
        special_uri_chars = new String[] { "%", "%25", "&", "%26", " ", "%20", " ", "%20", ">", "%3E", "<", "%3C", "\\", "%5C", "\"", "%22", "'", "%27", "\u00d0", "%D0", "¡", "%A1", "\u00d1", "%D1", "¢", "%A2", "\u00d2", "%D2", "£", "%A3", "\u00d3", "%D3", "¤", "%A4", "\u00d4", "%D4", "¥", "%A5", "\u00d5", "%D5", "¦", "%A6", "\u00d6", "%D6", "§", "%A7", "\u00d7", "%D7", "¨", "%A8", "\u00d8", "%D8", "©", "%A9", "\u00d9", "%D9", "ª", "%AA", "\u00da", "%DA", "«", "%AB", "\u00db", "%DB", "¬", "%AC", "\u00dc", "%DC", "\u00ad", "%AD", "\u00dd", "%DD", "®", "%AE", "\u00de", "%DE", "¯", "%AF", "\u00df", "%DF", "°", "%B0", "\u00e0", "%E0", "±", "%B1", "\u00e1", "%E1", "²", "%B2", "\u00e2", "%E2", "³", "%B3", "\u00e3", "%E3", "´", "%B4", "\u00e4", "%E4", "µ", "%B5", "\u00e5", "%E5", "¶", "%B6", "\u00e6", "%E6", "·", "%B7", "\u00e7", "%E7", "¸", "%B8", "\u00e8", "%E8", "¹", "5B9", "\u00e9", "%E9", "º", "%BA", "\u00ea", "%EA", "»", "%BB", "\u00eb", "%EB", "¼", "%BC", "\u00ec", "%EC", "½", "%BD", "\u00ed", "%ED", "¾", "%BE", "\u00ee", "%EE", "¿", "%BF", "\u00ef", "%EF", "\u00c0", "%C0", "\u00f0", "%F0", "\u00c1", "%C1", "\u00f1", "%F1", "\u00c2", "%C2", "\u00f2", "%F2", "\u00c3", "%C3", "\u00f3", "%F3", "\u00c4", "%C4", "\u00f4", "%F4", "\u00c5", "%C5", "\u00f5", "%F5", "\u00c6", "%C6", "\u00f6", "%F6", "\u00c7", "%C7", "\u00f7", "%F7", "\u00c8", "%C8", "\u00f8", "%F8", "\u00c9", "%C9", "\u00f9", "%F9", "\u00ca", "%CA ", "\u00fa", "%FA", "\u00cb", "%CB", "\u00fb", "%FB", "\u00cc", "%CC", "\u00fc", "%FC", "\u00cd", "%CD", "\u00fd", "%FD", "\u00ce", "%CE", "\u00fe", "%FE", "\u00cf", "%CF", "\u00ff", "%FF", "+", "%2B", "=", "%3D" };
    }
}
