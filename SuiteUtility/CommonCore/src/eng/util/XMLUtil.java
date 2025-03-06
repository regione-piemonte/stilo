package eng.util;

import java.util.HashMap;
import java.util.Iterator;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

public class XMLUtil {

  public static final String[] verySpecialChars= new String[]{new String(new char[]{(char)128}),"§#01"};
  public static final int MAX_ROWS = 20;
  public static final String[] xml_entities = new String[]{
	  "&","&amp;",
	  ">","&gt;",
      "<","&lt;",
      "\"","&quot;",
	  "'","&apos;"
  };
  public static final String[] special_chars= new String[]{
	  				"&","&amp;","&#038;",
	  				"%","","&#037;",			
	  				" ","&nbsp;","&#160;",
                    ">","&gt;","&#062;",
                    "<","&lt;","&#060;",
                    "\"","&quot;","&quot;",
					"'","&apos;","&#039;",
					"Ð","&ETH;","&#208;",
					"¡","&iexcl;","&#161;",
					"Ñ","&Ntilde;","&#209;",
					"¢","&cent;","&#162;",
					"Ò","&Ograve;","&#210;",
					"£","&pound;","&#163;",
					"Ó","&Oacute;","&#211;",
					"¤","&curren;","&#164;",
					"Ô","&Ocirc;","&#212;",
					"¥","&yen;","&#165;",
					"Õ","&Otilde;","&#213;",
					"¦","&brvbar;","&#166;",
					"Ö","&Ouml;","&#214;",
					"§","&sect;","&#167;",
					"×","&times;","&#215;",
					"¨","&uml;","&#168;",
					"Ø","&Oslash;","&#216;",
					"©","&copy;","&#169;",
					"Ù","&Ugrave;","&#217;",
					"ª","&ordf;","&#170;",
					"Ú","&Uacute;","&#218;",
					"«","&laquo;","&#171;",
					"Û","&Ucirc;","&#219;",
					"¬","&not;","&#172;",
					"Ü","&Uuml;","&#220;",
					"­","&shy;","&#173;",
					"Ý","&Yacute;","&#221;",
					"®","&reg;","&#174;",
					"Þ","&THORN;","&#222;",
					"¯","&macr;","&#175;",
					"ß","&szlig;","&#223;",
					"°","&deg;","&#176;",
					"à","&agrave;","&#224;",
					"±","&plusmn;","&#177;",
					"á","&aacute;","&#225;",
					"²","&sup2;","&#178;",
					"â","&acirc;","&#226;",
					"³","&sup3;","&#179;",
					"ã","&atilde;","&#227;",
					"´","&acute;","&#180;",
					"ä","&auml;","&#228;",
					"µ","&micro;","&#181;",
					"å","&aring;","&#229;",
					"¶","&para;","&#182;",
					"æ","&aelig;","&#230;",
					"·","&middot;","&#183;",
					"ç","&ccedil;","&#231;",
					"¸","&cedil;","&#184;",
					"è","&egrave;","&#232;",
					"¹","&sup1;","&#185;",
					"é","&eacute;","&#233;",
					"º","&ordm;","&#186;",
					"ê","&ecirc;","&#234;",
					"»","&raquo;","&#187;",
					"ë","&euml;","&#235;",
					"¼","&frac14;","&#188;",
					"ì","&igrave;","&#236;",
					"½","&frac12;","&#189;",
					"í","&iacute;","&#237;",
					"¾","&frac34;","&#190;",
					"î","&icirc;","&#238;",
					"¿","&iquest;","&#191;",
					"ï","&iuml;","&#239;",
					"À","&Agrave;","&#192;",
					"ð","&eth;","&#240;",
					"Á","&Aacute;","&#193;",
					"ñ","&ntilde;","&#241;",
					"Â","&Acirc; ","&#194;",
					"ò","&ograve;","&#242;",
					"Ã","&Atilde;","&#195;",
					"ó","&oacute;","&#243;",
					"Ä","&Auml;","&#196;",
					"ô","&ocirc;","&#244;",
					"Å","&Aring;","&#197;",
					"õ","&otilde;","&#245;",
					"Æ","&AElig;","&#198;",
					"ö","&ouml;","&#246;",
					"Ç","&Ccedil;","&#199;",
					"÷","&divide;","&#247;",
					"È","&Egrave;","&#200;",
					"ø","&oslash;","&#248;",
					"É","&Eacute;","&#201;",
					"ù","&ugrave;","&#249;",
					"Ê","&Ecirc; ","&#202;",
					"ú","&uacute;","&#250;",
					"Ë","&Euml;","&#203;",
					"û","&ucirc;","&#251;",
					"Ì","&Igrave;","&#204;",
					"ü","&uuml;","&#252;",
					"Í","&Iacute;","&#205;",
					"ý","&yacute;","&#253;",
					"Î","&Icirc;","&#206;",
					"þ","&thorn;","&#254;",
					"Ï","&Iuml;","&#207;",
					"ÿ","&yuml;","&#255;"
  };
  
  public static final String[] special_uri_chars= new String[]{
		//" ","%20",
		"%","%25",
		"&","%26",
		" ","%20",
		" ","%20", //EBR: Altro tipo di spazio (probabilmente codificato in maniera diversa)
        ">","%3E",
        "<","%3C",
        //"\"","%5C",
        "\\","%5C",
        "\"","%22",
		"'","%27",
		"Ð","%D0",
		"¡","%A1",
		"Ñ","%D1",
		"¢","%A2",
		"Ò","%D2",
		"£","%A3",
		"Ó","%D3",
		"¤","%A4",
		"Ô","%D4",
		"¥","%A5",
		"Õ","%D5",
		"¦","%A6",
		"Ö","%D6",
		"§","%A7",
		"×","%D7",
		"¨","%A8",
		"Ø","%D8",
		"©","%A9",
		"Ù","%D9",
		"ª","%AA",
		"Ú","%DA",
		"«","%AB",
		"Û","%DB",
		"¬","%AC",
		"Ü","%DC",
		"­","%AD",
		"Ý","%DD",
		"®","%AE",
		"Þ","%DE",
		"¯","%AF",
		"ß","%DF",
		"°","%B0",
		"à","%E0",
		"±","%B1",
		"á","%E1",
		"²","%B2",
		"â","%E2",
		"³","%B3",
		"ã","%E3",
		"´","%B4",
		"ä","%E4",
		"µ","%B5",
		"å","%E5",
		"¶","%B6",
		"æ","%E6",
		"·","%B7",
		"ç","%E7",
		"¸","%B8",
		"è","%E8",
		"¹","5B9",
		"é","%E9",
		"º","%BA",
		"ê","%EA",
		"»","%BB",
		"ë","%EB",
		"¼","%BC",
		"ì","%EC",
		"½","%BD",
		"í","%ED",
		"¾","%BE",
		"î","%EE",
		"¿","%BF",
		"ï","%EF",
		"À","%C0",
		"ð","%F0",
		"Á","%C1",
		"ñ","%F1",
		"Â","%C2",
		"ò","%F2",
		"Ã","%C3",
		"ó","%F3",
		"Ä","%C4",
		"ô","%F4",
		"Å","%C5",
		"õ","%F5",
		"Æ","%C6",
		"ö","%F6",
		"Ç","%C7",
		"÷","%F7",
		"È","%C8",
		"ø","%F8",
		"É","%C9",
		"ù","%F9",
		"Ê","%CA ",
		"ú","%FA",
		"Ë","%CB",
		"û","%FB",
		"Ì","%CC",
		"ü","%FC",
		"Í","%CD",
		"ý","%FD",
		"Î","%CE",
		"þ","%FE",
		"Ï","%CF",
		"ÿ","%FF",
		"+","%2B",
		"=","%3D"
  };
  
  public XMLUtil() {
  }

  public static String verySpecialEscape(String text)
  {
    if( text == null) return "";
    int i=0;
    for (i=0; i < verySpecialChars.length; i+=2)
    {
      text = string_replace(verySpecialChars[i], verySpecialChars[i+1], text);
    }

    return text;
  }
  
  public static String capitalize(String text)
  {
	  if (text == null) return "";
	  if (text.length() == 0) return text;
	  java.util.StringTokenizer tokenizer = new java.util.StringTokenizer(text);
	  String toReturn = "";
	  String nextToken = "";
	  while(tokenizer.hasMoreTokens())
	  {
		  nextToken = tokenizer.nextToken();
		  toReturn = toReturn +" "+ nextToken.substring(0, 1).toUpperCase() + nextToken.substring(1).toLowerCase();
	  }
      return toReturn;
  }
  
  public static String xmlEscapeEntities(String text)
  {
	  	int passo=1;
	
	  	if( text == null) return "";
	  	int i=0;
	  	int inizio=0;
	  	for (i=inizio; i < xml_entities.length; i+=2)
	  	{
	  		text = string_replace(xml_entities[i], xml_entities[i+passo], text);
	  	}
	  	return text;
  }
  
  public static String xmlEscape(String text, int escapeBlank)
  {
  	return xmlEscape(text, 0, escapeBlank);
  }

    public static String xmlEscape(String text)
  {
  	return xmlEscape(text, 0, 0);
  }
  
	/*
	Il metodo effettua l'escaping dei caratteri per renderli 
	compatibili con i docuemnti xml
	
	Parametri:
	String text: la stringa sulla quale effettuare escaping
	int tipoEscape: 
		1  : escaping di tutto tranne lo spazio e &
		2  : escaping di tutto tranne lo spazio
		>2 : escaping di tutto
	*/

    /*
     * Antonio 20/11/2006
     * nel caso si passi ad 1 escapeBlank allora evita di fare escaping del carattere spazio
     * saltando il primo elemento dell'array che contiene la codifica dei caratteri speciali
     */
  public static String xmlEscape(String text, int tipoEscape, int escapeBlank)
  {
  		int passo=2;
  		if ( tipoEscape == 1) passo = 1;
  	
    	if( text == null) return "";
    	int i=0;
    	int inizio=0;
    	//if ( escapeBlank==1 ) inizio=3;
    	//String tmp = "";
    	for (i=inizio; i < special_chars.length; i+=3)
    	{
    		//if ( (escapeBlank==1 && !special_chars[i].equals(" ")) || escapeBlank!=1)
    		if ((escapeBlank==1 && !special_chars[i].equals(" ") && !special_chars[i].equals("&")) || 
    		    (escapeBlank==2 && !special_chars[i].equals(" ")) || 
    		    escapeBlank==0 || escapeBlank>2)
    			text = string_replace(special_chars[i], special_chars[i+passo], text);
    	}
    	return text;
  }

  
  /*
   * Antonio 03/01/2007
   * metodo per sostituire gli spazi con il carattere %20 negli uri
   * ES. questi sono uri errati
   * <a href="/commercio/servlet/CacheDispatcher?pageName=ListaDizionario&amp;pageLevel=3&amp;pageScope=mod&amp;b_Modifica=1&amp;p_parForDet1=ATTIVITA&amp;p_parForDet2=Alimentari confezionati">uri</a>
   * <a href="/commercio/servlet/CacheDispatcher?pageName=ListaDizionario&amp;pageLevel=3&amp;pageScope=mod&amp;b_Modifica=1&amp;p_parForDet1=ATTIVITA&amp;p_parForDet2=Alimentari&nbsp;confezionati">uri</a>
   * 
   * questo e' corretto
   * <a href="/commercio/servlet/CacheDispatcher?pageName=ListaDizionario&amp;pageLevel=3&amp;pageScope=mod&amp;b_Modifica=1&amp;p_parForDet1=ATTIVITA&amp;p_parForDet2=Alimentari%20confezionati">pagina vuota</a>
   */
	public static String uriEscape(String uri)
	{
		//int passo=1;
		if( uri == null) return "";
		//int i=0;
		//int inizio=0;
		for (int i=0; i < special_uri_chars.length; i+=2)
		{
			uri = string_replace(special_uri_chars[i], special_uri_chars[i+1], uri);
		}
		return uri;
	}
	
	public static String uriEscapeJ(String uri)
	{
		String newUri = uri;
		try
		{
			newUri = java.net.URLEncoder.encode(uri, "ISO-8859-1");
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		return newUri;
	}

	
	  public static String xmlUnEscape(String text)
	  {
	  		if( text == null) return "";
	    	for (int i=0; i < special_chars.length; i+=3)
	    	{
	    		text = string_replace(special_chars[i], special_chars[0], text);
	    	}
	    	return text;
	  }
	  
	  
//	  public static String newUnescapeXML(String xml) {
//			String ret = xml;		
//			//Ciclo i caratteri
//			HashMap map = new HashMap();
//			map.put("&#128;","€");
//			map.put("&#129;","");
//			map.put("&#130;","‚");
//			map.put("&#131;","ƒ");
//			map.put("&#132;","„");
//			map.put("&#133;","…");
//			map.put("&#134;","†");
//			map.put("&#135;","‡");
//			map.put("&#136;","ˆ");
//			map.put("&#137;","‰");
//			map.put("&#138;","Š");
//			map.put("&#139;","‹");
//			map.put("&#140;","Œ");
//			map.put("&#141;","");
//			map.put("&#142;","Ž");
//			map.put("&#143;","");
//			map.put("&#144;","");
//			map.put("&#145;","‘");
//			map.put("&#146;","’");
//			map.put("&#147;","“");
//			map.put("&#148;","”");
//			map.put("&#149;","•");
//			map.put("&#150;","–");
//			map.put("&#151;","—");
//			map.put("&#152;","˜");
//			map.put("&#153;","™");
//			map.put("&#154;","š");
//			map.put("&#155;","›");
//			map.put("&#156;","œ");
//			map.put("&#157;","");
//			map.put("&#158;","ž");
//			map.put("&#159;","Ÿ");
//			
//			Iterator itera = map.keySet().iterator();
//			while(itera.hasNext()){
//				String key = itera.next().toString();
//				if(StringUtils.contains(ret, key)){
//					ret = StringUtils.replace(ret, key, map.get(key).toString());				
//				}
//			}
//			ret = StringEscapeUtils.unescapeXml(ret);
//			return ret;
//		}
	
	
  /**
   * Deve essere utilizzata quando un parametro viene utilizzata all'interno di una stringa javascript
   * Effettua l'escaping di ', ", \ e \n
   * @param text
   * @return
   */
  public static String safeScriptString(String text)
  {
    String oldText = text;
    if( text == null) return "";

    //text = string_replace("\'","\\\'", text);
    text = string_replace("\n","\\n", text);
    if (text.equals(oldText))
    {
      text = string_replace("\\", "\\\\", text);
      if (text.equals(oldText))
      {
        text = string_replace("\"","\\\"", text);
      }

    }

    // Mirko - 23/02/2004
    text = string_replace("'","\'", text);

    return text;
  }


  public static String safeScriptString(String text, String prima, String dopo)
  {
    if( text == null) return "";

    text = string_replace(prima,dopo, text);

    return text;
  }




  /**
   * Rimpiazza tuttle le occorrenze di s2 in s3 con s1
   *
   * Es: string_replace("A","P","PIPPO") => "AIAAO"
   *
   * @param s1
   * @param s2
   * @param s3
   * @return
   */
  public static String string_replace(String s1, String s2, String s3)
  {
	   String string="";
	   string = "";

    	   if (s2 == null || s3 == null) return s3;

	   int pos_i = 0; // posizione corrente.
	   int pos_f = 0; // posizione corrente finale

	   int len = s1.length();
       //System.out.print("Entrato");
	   while ( (pos_f = s3.indexOf(s1, pos_i)) >= 0)
	   {
		   string += s3.substring(pos_i,pos_f)+s2;
		   //+string.substring(pos+ s2.length());
		   pos_f = pos_i = pos_f + len;

	   }
	   string += s3.substring(pos_i);
	   return string;
 }

 public static String getEstensioneFile(String filename  )
 {
        if (filename == null) return "";

        String ext = "";
        if (filename.lastIndexOf(".") != -1 )
        {
                  ext = filename.substring(filename.lastIndexOf("."));

                  if (ext.toLowerCase().equals(".p7m"))
                  {
                        // Vediamo se c'e' più di una estensione...
                        return getEstensioneFile(filename.substring(0, filename.length() - 4 )) + ext;
                  }
        }
        return ext;
 }

 public static String creaQueryPaginazione(String sql, String numPagina  )
 {
      String sqlTot = "";

      if ((numPagina == null) || (numPagina.equals(""))) numPagina = "1";
      sqlTot = "SELECT TTT.* ";
      sqlTot += "from ( SELECT TT.*, rownum contaRighe from ( ";
      sqlTot += sql;
      sqlTot += ") TT ) TTT ";
      sqlTot += " WHERE TTT.contaRighe between ";
      sqlTot += String.valueOf(1+(MAX_ROWS*(Integer.parseInt(numPagina)-1)));
      sqlTot += " and ";
      sqlTot += String.valueOf(1+MAX_ROWS+(MAX_ROWS*(Integer.parseInt(numPagina)-1)));

      return sqlTot;
 }

 public static String nvl(Object obj, String defaultValue)
 {
     if (obj == null) return defaultValue;
     return obj.toString();
 }
 
 public static String underlineWords(String word, String charList)
 {
     int listL = charList.length();
     int[] lastPos = new int[listL];
     String prefix ="";
     String suffix ="";
     //Inizializzo l'array a 0
     for (int i=0; i<listL; i++){
	     lastPos[i]=0;
     }
     String curChar = "";
     for(int i=0; i<listL; i++){
	     curChar = charList.substring(i,i+1);
	     //Cerco la prima occorrenza del carattere dentro la stringa
	     lastPos[i]=lastPos[i]>word.indexOf(curChar,lastPos[i])?lastPos[i]:word.indexOf(curChar,lastPos[i]);
	     //sostituisco se trovo un'occorrenza
	     if(lastPos[i]>=0){
		     //Allora sostituisco
		     prefix = word.substring(0,lastPos[i]);
		     suffix = "<span class=\"underlined\">"+curChar+"</span>"+word.substring(lastPos[i]+1);
		     word = prefix + suffix;
	     }
     }
     return word;
 }
 
}
