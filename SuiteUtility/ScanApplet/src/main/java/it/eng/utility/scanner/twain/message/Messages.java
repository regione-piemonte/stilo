package it.eng.utility.scanner.twain.message;

import java.io.BufferedReader;
import java.text.MessageFormat;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.StringTokenizer;

/**
 *Classi di utilità per accedere ai messaggi internazionalizzati
 */
public class Messages {
	
	/**fall back for messages (ie if using 1.3) */
	private static Map messages=null;

	/**log values not found so not repeateded*/
	private static Map reportedValueMissing=new HashMap();
	
	/**localized text bundle */
	protected static ResourceBundle bundle=null;
	
	private static boolean isInitialised=false;
	
	/**set bundle*/
	public static void setBundle(ResourceBundle newBundle){
		bundle=newBundle;
	
		if(!isInitialised)
			init();
	}
    public static ResourceBundle getBundle(){
		return bundle;
    }
    
	public static   String getMessage(String key,Object... values){
		return getMessage(key, key, values);
	}
	
    public  static  String getMessage(String key,String defaultValue,Object... values ){
		String ret="";
		String unformatted=null;
		try{
			  unformatted=bundle.getString(key);
		}catch(Exception ex){
//			 if(LogWriter.isOutput())
//	                LogWriter.writeLog("Exception: "+ex.getMessage());
		}
		//se non hai letto il valore dal properties usa il default
		if(unformatted==null){
			unformatted=defaultValue;
		}
		if(unformatted!=null && values!=null && values.length>0){
			ret=MessageFormat.format(unformatted, values);
		}else{
			ret=unformatted;
		}
		return ret;
	}
	/**
	 * display message from message bundle or name if problem
	 */
	public static String getMessage(String key) {
		
		String message=null;
		
		try{
			message=(String)messages.get(key);
			if(message==null)
			message=bundle.getString(key);
		}catch(Exception e){

            //tell user and log
//            if(LogWriter.isOutput())
//                LogWriter.writeLog("Exception: "+e.getMessage());
		}
		
		//trap for 1.3 or missing
		if(message==null){
			
			try{
				
				message=(String)messages.get(key);
				
			}catch(Exception e){
                //tell user and log
//                if(LogWriter.isOutput())
//                    LogWriter.writeLog("Exception: "+e.getMessage());
			}
		}
		
		//if still null use message key
		if(message==null)
			message=key;
		
		if(message.length()==0)
			message=key+"<<";
		return message;
	}
	
	/**
	 * reads message bundle manually if needed (bug in 1.3.0)
	 */
	private static void init(){
		
		isInitialised=true;

		String line;
		BufferedReader input_stream = null;
		//ClassLoader loader = Messages.class.getClassLoader();
		/**must use windows encoding because files were edited on Windows*/
		//String enc = "Cp1252";
		//int equalsIndex;
		
		try {
			
			//initialise inverse lookup (add space)
			messages=new HashMap();
			
//			 String customBundle=System.getProperty("org.jpedal.bundleLocation");
//			 customBundle="org.jpedal.international.messages"; //test code
//			 if(customBundle!=null){
//				 String customBundleLocation=customBundle.replaceAll("\\.","/");
//				 
//				 if(ID.length()==0 || ID.startsWith("en")){
//					 targetFile=".properties";
//					}else{
//						targetFile="_"+ID+".properties";
//					}
//				 
//				 input_stream =
//					 new BufferedReader(
//							 new InputStreamReader(
//									 loader.getResourceAsStream(
//											 customBundleLocation+targetFile),
//											 enc));
//				 
//			 }else{
//
//				 input_stream =
//					 new BufferedReader(
//							 new InputStreamReader(
//									 loader.getResourceAsStream(
//											 "org/jpedal/international/"+targetFile),
//											 enc));
//				 
//			 }
						
			// trap problems
//			if (input_stream == null)
//				LogWriter.writeLog("Unable to open messages.properties from jar");
	
			Enumeration keys = bundle.getKeys();

			while(keys.hasMoreElements()){
				String element = (String) keys.nextElement();
				//System.out.println("building "+element);
				//read in lines and place in map for fast lookup
				line = (String)bundle.getObject(element);
				if (line == null)
					break;

				//get raw string
				StringBuilder newMessage=new StringBuilder();

				//work through string converting #; values to unicde
				//Convert &#int to ascii
				StringTokenizer t = new StringTokenizer(line,"\\&;",true);
				String nextValue;
				boolean isAmpersand=false;

				while (t.hasMoreTokens()) {

					if(isAmpersand){
						nextValue="&";
						isAmpersand=false;
					}else	
						nextValue=t.nextToken();

					//if token is &, we have found an ascii char 
					//and need to convert
					//othwerwise just add back

					//Check for escape characters
					if(t.hasMoreTokens() && nextValue.equals("\\")){
						String ascii=t.nextToken(); //actual value
						char c=ascii.charAt(0);

						//Check to see if escape is a newline
						if(c=='n'){
							newMessage.append('\n');
						}else if(c==' '){
							newMessage.append(' ');
						}

						newMessage.append(ascii.substring(1));
					}else if(t.hasMoreTokens() && nextValue.equals("&")){

						String ascii=t.nextToken(); //actual value

						String end;
						if(t.hasMoreTokens()){
							end=t.nextToken(); //read and ignore ;

							if(end.equals("&")){
								newMessage.append('&');
								newMessage.append(ascii);
								isAmpersand=true;
							}else if(end.equals(";")){

								if(ascii.startsWith("#"))
									ascii=ascii.substring(1);

								//convert number to char
								char c=(char) Integer.parseInt(ascii);

								//add back to newMessage
								newMessage.append(c);

							}else{
								{

									//get next char and check ;
									if(t.hasMoreTokens())
										newMessage.append('&');
									newMessage.append(ascii);


								}
							}
						}else{ //and last token
							newMessage.append('&');
							newMessage.append(ascii);

						}
					}else
						newMessage.append(nextValue);
				}

				//  System.out.println("final value="+newMessage.toString()+"<<");

				//store converted message
				//System.out.println("el:"+element+" newMess:"+newMessage);
				messages.put(element,newMessage.toString());
			}
		}	
		
		
		catch (Exception e) {
			e.printStackTrace();
//			LogWriter.writeLog("Exception "+e+" loading resource bundle.\n" +
//					"Also check you have a file in org.jpedal.international.messages to support Locale="+java.util.Locale.getDefault());
		
			System.err.println("Exception loading resource bundle.\n" +
					"Also check you have a file in org.jpedal.international.messages to support Locale="+java.util.Locale.getDefault());
		
		}
		
		
		//ensure closed
		if(input_stream!=null){
			try{
				
				input_stream.close();
			}catch (Exception e) {
//				LogWriter.writeLog(
//						"Exception " + e + " reading lookup table for pdf  for abobe map");
			}		
		}	
	}

	public static void dispose() {
		
		messages=null;

		reportedValueMissing=null;
		
		bundle=null;
		
		isInitialised = false;
	}
}