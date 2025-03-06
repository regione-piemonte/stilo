package it.eng.utility.ui.html;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class HtmlNormalizeUtil {

	public static String getPlainXmlWithCarriageReturn(String pStrHtml){
		if (StringUtils.isNotEmpty(pStrHtml)){
			Document doc = Jsoup.parseBodyFragment(pStrHtml);
			if (!doc.body().text().equals(pStrHtml)){
				//E' html
				String text = Jsoup.parseBodyFragment(pStrHtml.replaceAll("\r\n", "br2nl").replaceAll("\n", "br2nl").replaceAll("\r", "br2nl").replaceAll("</br>", "br2nl").replaceAll("<br>", "br2nl").replaceAll("<br/>", "br2nl")).text();
			    text = text.replaceAll("br2nl ", "\n").replaceAll("br2nl", "\n").trim();
//			    text = text.replaceAll("\n", " ").trim();
				return text;
			} else return pStrHtml;
		} else return pStrHtml;		
	}
	
}
