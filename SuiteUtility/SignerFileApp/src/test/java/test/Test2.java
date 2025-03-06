package test;
import it.eng.client.applet.thread.CardPresentThread;

import javax.smartcardio.Card;
import javax.smartcardio.CardException;
import javax.smartcardio.CardTerminal;
import javax.smartcardio.TerminalFactory;



public class Test2 {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		
		TerminalFactory factory = TerminalFactory.getDefault();
		
		for(CardTerminal ter:factory.terminals().list()){
			
			System.out.println(ter.getName());
						
			Card card = ter.connect("*");
			System.out.println(card.getBasicChannel().getChannelNumber());
			
			card.disconnect(true);
		
		}
		
		
		
		
//		
		
		
		
//		while(true){
//			try{
//				System.out.println(factory.terminals().list().size());
//			}catch(CardException e){
//				System.out.println("Nessun dispositivo presente!");
//			}
//			Thread.sleep(1000);
//		}
//		
//		CardTerminals terminale =  factory.terminals();
//		
//		thread.start();
//		
//		thread.setTerminal(terminale.list().get(0));
		
		
				
		
			
		
//		System.out.println(System.getProperty("java.library.path"));
// 		Token ob = new Token();
// 		ob.init(); 	
// 			
// 		ob.login("11433");
// 		String[] strt = ob.listObjects("", Token.ALL_CERT);
// 		System.out.println(strt.length);
// 				
// 		ob.logout();
 		
		
//		List<CardTerminal> lst = TerminalFactory.getDefault().terminals().list();
//		for(CardTerminal terminal:lst){
//			System.out.println(terminal.isCardPresent());	
//			 Card card = terminal.connect("*");
//		     		 
//			 
//			 
//			 
//		     card.disconnect(true);
//		}
		
//		boolean isLoad = false;
//		//Carico i provider
//		String dir = "C:\\Documents and Settings\\Administrator.S4927";
//		char[] pin = "11433".toCharArray();
//		File tmp = new File(dir);
//		File directory = new File(tmp+File.separator+"dllCrypto");
//		File[] dll = directory.listFiles();
//	 	Provider pkcs11Provider = null;
//	 	KeyStore keyStore = null;
//	 	 PKCS11.loadNative();
//	 	for(int i=0;i<dll.length;i++){
//	 		try{
//	 			
//		    	StringBuffer cardConfig=new StringBuffer();
//		        cardConfig.append ("name=SmartCards \n");
//		        cardConfig.append ("library="+dll[i].getAbsolutePath()+" \n");
//		        //log.debug("Config:"+cardConfig.toString());
//		        
//		        ByteArrayInputStream confStream = new ByteArrayInputStream(cardConfig.toString().getBytes());
//		               
//		        // Create the provider
//		        Class sunPkcs11Class = Class.forName("sun.security.pkcs11.SunPKCS11");
//		        Constructor pkcs11Constr = sunPkcs11Class.getConstructor(java.io.InputStream.class);
//		        pkcs11Provider = (Provider)pkcs11Constr.newInstance(confStream);
//		            
//		        Iterator<String> itera = pkcs11Provider.stringPropertyNames().iterator();
//		        while(itera.hasNext()){
//		        	String key = itera.next();
//		        	System.out.println(key+":"+pkcs11Provider.getProperty(key));
//		        }        
//        
//			 	keyStore = KeyStore.getInstance("PKCS11",pkcs11Provider);
//		        keyStore.load(null, pin);
//		        	        
//		        //isLoad = true;
//		        Security.addProvider(pkcs11Provider);
//		        
//		        //Setto l'ATR della carta con il provider trovato
//		               
//		        
//		        //log.debug("Provider caricato!");
//		        break;
//		 	}catch(Exception e){
//		 		e.printStackTrace();
//		 		//log.warn("WARNING DLL NON VALIDA", e);
//		 	}
//	 	}
//	 	
//	 	//Nessuna DLL controllo se  e' una CNS Actalis
//	 	if(!isLoad){
//		 	try{
//		 		System.out.println("CNS");
//		 		Token ob = new Token();
//		 		ob.init(); 	
//		 		ob.login(new String(pin));
//		 		pkcs11Provider = new ActalisProvider();
//		 		Security.addProvider(pkcs11Provider); 	
//		 		isLoad = true;
//		 	}catch(Exception e){
//		 		 isLoad = false;
//		 	}
//	 	}
//	 	if(!isLoad){
//	 		throw new Exception("Errore loading Certificati!");
//	 		
//	 	}
	 	//log.debug("FINE COPIA CERTIFICATI");
	 	//System.out.println("FINE OK");
		
		
	}
	

	
	
}