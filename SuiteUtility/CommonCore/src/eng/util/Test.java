package eng.util;

import java.util.HashMap;
import java.util.Iterator;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;

public class Test {

	
	public static void main(String[] args) {
		
	}
	
	/**
	 * @param args
	 */
	public String unescapexml(String xml) {
		String ret = xml;		
		//Ciclo i caratteri
		HashMap map = new HashMap();
		map.put("&#128;","€");
		map.put("&#129;","");
		map.put("&#130;","‚");
		map.put("&#131;","ƒ");
		map.put("&#132;","„");
		map.put("&#133;","…");
		map.put("&#134;","†");
		map.put("&#135;","‡");
		map.put("&#136;","ˆ");
		map.put("&#137;","‰");
		map.put("&#138;","Š");
		map.put("&#139;","‹");
		map.put("&#140;","Œ");
		map.put("&#141;","");
		map.put("&#142;","Ž");
		map.put("&#143;","");
		map.put("&#144;","");
		map.put("&#145;","‘");
		map.put("&#146;","’");
		map.put("&#147;","“");
		map.put("&#148;","”");
		map.put("&#149;","•");
		map.put("&#150;","–");
		map.put("&#151;","—");
		map.put("&#152;","˜");
		map.put("&#153;","™");
		map.put("&#154;","š");
		map.put("&#155;","›");
		map.put("&#156;","œ");
		map.put("&#157;","");
		map.put("&#158;","ž");
		map.put("&#159;","Ÿ");
		
		Iterator itera = map.keySet().iterator();
		while(itera.hasNext()){
			String key = itera.next().toString();
			if(StringUtils.contains(ret, key)){
				ret = StringUtils.replace(ret, key, map.get(key).toString());				
			}
		}
		ret = StringEscapeUtils.unescapeXml(ret);
		return ret;
	}

}
