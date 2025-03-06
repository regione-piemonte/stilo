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

  public static final String[] verySpecialChars= new String[]{new String(new char[]{(char)128}),"�#01"};
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
					"�","&ETH;","&#208;",
					"�","&iexcl;","&#161;",
					"�","&Ntilde;","&#209;",
					"�","&cent;","&#162;",
					"�","&Ograve;","&#210;",
					"�","&pound;","&#163;",
					"�","&Oacute;","&#211;",
					"�","&curren;","&#164;",
					"�","&Ocirc;","&#212;",
					"�","&yen;","&#165;",
					"�","&Otilde;","&#213;",
					"�","&brvbar;","&#166;",
					"�","&Ouml;","&#214;",
					"�","&sect;","&#167;",
					"�","&times;","&#215;",
					"�","&uml;","&#168;",
					"�","&Oslash;","&#216;",
					"�","&copy;","&#169;",
					"�","&Ugrave;","&#217;",
					"�","&ordf;","&#170;",
					"�","&Uacute;","&#218;",
					"�","&laquo;","&#171;",
					"�","&Ucirc;","&#219;",
					"�","&not;","&#172;",
					"�","&Uuml;","&#220;",
					"�","&shy;","&#173;",
					"�","&Yacute;","&#221;",
					"�","&reg;","&#174;",
					"�","&THORN;","&#222;",
					"�","&macr;","&#175;",
					"�","&szlig;","&#223;",
					"�","&deg;","&#176;",
					"�","&agrave;","&#224;",
					"�","&plusmn;","&#177;",
					"�","&aacute;","&#225;",
					"�","&sup2;","&#178;",
					"�","&acirc;","&#226;",
					"�","&sup3;","&#179;",
					"�","&atilde;","&#227;",
					"�","&acute;","&#180;",
					"�","&auml;","&#228;",
					"�","&micro;","&#181;",
					"�","&aring;","&#229;",
					"�","&para;","&#182;",
					"�","&aelig;","&#230;",
					"�","&middot;","&#183;",
					"�","&ccedil;","&#231;",
					"�","&cedil;","&#184;",
					"�","&egrave;","&#232;",
					"�","&sup1;","&#185;",
					"�","&eacute;","&#233;",
					"�","&ordm;","&#186;",
					"�","&ecirc;","&#234;",
					"�","&raquo;","&#187;",
					"�","&euml;","&#235;",
					"�","&frac14;","&#188;",
					"�","&igrave;","&#236;",
					"�","&frac12;","&#189;",
					"�","&iacute;","&#237;",
					"�","&frac34;","&#190;",
					"�","&icirc;","&#238;",
					"�","&iquest;","&#191;",
					"�","&iuml;","&#239;",
					"�","&Agrave;","&#192;",
					"�","&eth;","&#240;",
					"�","&Aacute;","&#193;",
					"�","&ntilde;","&#241;",
					"�","&Acirc; ","&#194;",
					"�","&ograve;","&#242;",
					"�","&Atilde;","&#195;",
					"�","&oacute;","&#243;",
					"�","&Auml;","&#196;",
					"�","&ocirc;","&#244;",
					"�","&Aring;","&#197;",
					"�","&otilde;","&#245;",
					"�","&AElig;","&#198;",
					"�","&ouml;","&#246;",
					"�","&Ccedil;","&#199;",
					"�","&divide;","&#247;",
					"�","&Egrave;","&#200;",
					"�","&oslash;","&#248;",
					"�","&Eacute;","&#201;",
					"�","&ugrave;","&#249;",
					"�","&Ecirc; ","&#202;",
					"�","&uacute;","&#250;",
					"�","&Euml;","&#203;",
					"�","&ucirc;","&#251;",
					"�","&Igrave;","&#204;",
					"�","&uuml;","&#252;",
					"�","&Iacute;","&#205;",
					"�","&yacute;","&#253;",
					"�","&Icirc;","&#206;",
					"�","&thorn;","&#254;",
					"�","&Iuml;","&#207;",
					"�","&yuml;","&#255;"
  };
  
  public static final String[] special_uri_chars= new String[]{
		//" ","%20",
		"%","%25",
		"&","%26",
		" ","%20",
		"�","%20", //EBR: Altro tipo di spazio (probabilmente codificato in maniera diversa)
        ">","%3E",
        "<","%3C",
        //"\"","%5C",
        "\\","%5C",
        "\"","%22",
		"'","%27",
		"�","%D0",
		"�","%A1",
		"�","%D1",
		"�","%A2",
		"�","%D2",
		"�","%A3",
		"�","%D3",
		"�","%A4",
		"�","%D4",
		"�","%A5",
		"�","%D5",
		"�","%A6",
		"�","%D6",
		"�","%A7",
		"�","%D7",
		"�","%A8",
		"�","%D8",
		"�","%A9",
		"�","%D9",
		"�","%AA",
		"�","%DA",
		"�","%AB",
		"�","%DB",
		"�","%AC",
		"�","%DC",
		"�","%AD",
		"�","%DD",
		"�","%AE",
		"�","%DE",
		"�","%AF",
		"�","%DF",
		"�","%B0",
		"�","%E0",
		"�","%B1",
		"�","%E1",
		"�","%B2",
		"�","%E2",
		"�","%B3",
		"�","%E3",
		"�","%B4",
		"�","%E4",
		"�","%B5",
		"�","%E5",
		"�","%B6",
		"�","%E6",
		"�","%B7",
		"�","%E7",
		"�","%B8",
		"�","%E8",
		"�","5B9",
		"�","%E9",
		"�","%BA",
		"�","%EA",
		"�","%BB",
		"�","%EB",
		"�","%BC",
		"�","%EC",
		"�","%BD",
		"�","%ED",
		"�","%BE",
		"�","%EE",
		"�","%BF",
		"�","%EF",
		"�","%C0",
		"�","%F0",
		"�","%C1",
		"�","%F1",
		"�","%C2",
		"�","%F2",
		"�","%C3",
		"�","%F3",
		"�","%C4",
		"�","%F4",
		"�","%C5",
		"�","%F5",
		"�","%C6",
		"�","%F6",
		"�","%C7",
		"�","%F7",
		"�","%C8",
		"�","%F8",
		"�","%C9",
		"�","%F9",
		"�","%CA ",
		"�","%FA",
		"�","%CB",
		"�","%FB",
		"�","%CC",
		"�","%FC",
		"�","%CD",
		"�","%FD",
		"�","%CE",
		"�","%FE",
		"�","%CF",
		"�","%FF",
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
//			map.put("&#128;","�");
//			map.put("&#129;","");
//			map.put("&#130;","�");
//			map.put("&#131;","�");
//			map.put("&#132;","�");
//			map.put("&#133;","�");
//			map.put("&#134;","�");
//			map.put("&#135;","�");
//			map.put("&#136;","�");
//			map.put("&#137;","�");
//			map.put("&#138;","�");
//			map.put("&#139;","�");
//			map.put("&#140;","�");
//			map.put("&#141;","");
//			map.put("&#142;","�");
//			map.put("&#143;","");
//			map.put("&#144;","");
//			map.put("&#145;","�");
//			map.put("&#146;","�");
//			map.put("&#147;","�");
//			map.put("&#148;","�");
//			map.put("&#149;","�");
//			map.put("&#150;","�");
//			map.put("&#151;","�");
//			map.put("&#152;","�");
//			map.put("&#153;","�");
//			map.put("&#154;","�");
//			map.put("&#155;","�");
//			map.put("&#156;","�");
//			map.put("&#157;","");
//			map.put("&#158;","�");
//			map.put("&#159;","�");
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
                        // Vediamo se c'e' pi� di una estensione...
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
