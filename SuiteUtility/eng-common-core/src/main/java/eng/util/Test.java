// 
// Decompiled by Procyon v0.5.36
// 

package eng.util;

import java.util.Iterator;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import java.util.HashMap;

public class Test
{
    public static void main(final String[] args) {
    }
    
    public String unescapexml(final String xml) {
        String ret = xml;
        final HashMap map = new HashMap();
        map.put("&#128;", "\u20ac");
        map.put("&#129;", "");
        map.put("&#130;", "\u201a");
        map.put("&#131;", "\u0192");
        map.put("&#132;", "\u201e");
        map.put("&#133;", "\u2026");
        map.put("&#134;", "\u2020");
        map.put("&#135;", "\u2021");
        map.put("&#136;", "\u02c6");
        map.put("&#137;", "\u2030");
        map.put("&#138;", "\u0160");
        map.put("&#139;", "\u2039");
        map.put("&#140;", "\u0152");
        map.put("&#141;", "");
        map.put("&#142;", "\u017d");
        map.put("&#143;", "");
        map.put("&#144;", "");
        map.put("&#145;", "\u2018");
        map.put("&#146;", "\u2019");
        map.put("&#147;", "\u201c");
        map.put("&#148;", "\u201d");
        map.put("&#149;", "\u2022");
        map.put("&#150;", "\u2013");
        map.put("&#151;", "\u2014");
        map.put("&#152;", "\u02dc");
        map.put("&#153;", "\u2122");
        map.put("&#154;", "\u0161");
        map.put("&#155;", "\u203a");
        map.put("&#156;", "\u0153");
        map.put("&#157;", "");
        map.put("&#158;", "\u017e");
        map.put("&#159;", "\u0178");
        final Iterator itera = map.keySet().iterator();
        while (itera.hasNext()) {
            final String key = itera.next().toString();
            if (StringUtils.contains(ret, key)) {
                ret = StringUtils.replace(ret, key, map.get(key).toString());
            }
        }
        ret = StringEscapeUtils.unescapeXml(ret);
        return ret;
    }
}
