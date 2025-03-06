package eng.util;

import java.util.StringTokenizer;

public class BrowserUtil {

	public static final String[] TextBrowsers = new String[] {
			"Lynx"
	};
	
	public static final String[] PalmSO = new String[] {
		"WinCE",
		"Windows CE"
	};
	
	public static boolean IsTextBrowser(String userAgent){
		//Parse the user agent string
		StringTokenizer token = new StringTokenizer(userAgent,"/");
		String brow_name="";
		if(token.countTokens()>=1){
			//Il primo token è il nome del browser
			brow_name=token.nextToken();
			for(int i=0; i<TextBrowsers.length; i++){
				if(TextBrowsers[i].equalsIgnoreCase(brow_name)){
						return true;
				}
			}
		}
		return false;
	}
	
	public static boolean IsPalmSO(String userAgent){
		//Parse the user agent string
		for(int i=0; i<PalmSO.length; i++){
			if(userAgent.toLowerCase().indexOf(PalmSO[i].toLowerCase())>=0){
					return true;
			}
		}
		return false;
	}
	
	public static boolean IsIEBrowser(String userAgent)
	{
		if(userAgent.toLowerCase().indexOf("MSIE".toLowerCase())>=0){
			return true;
		}else{
			return false;
		}
	}
}
