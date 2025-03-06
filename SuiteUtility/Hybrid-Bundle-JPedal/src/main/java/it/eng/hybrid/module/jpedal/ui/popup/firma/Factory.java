package it.eng.hybrid.module.jpedal.ui.popup.firma;

import it.eng.hybrid.module.jpedal.preferences.ConfigConstants;
import it.eng.hybrid.module.jpedal.preferences.PreferenceManager;
import it.eng.hybrid.module.jpedal.ui.JPedalApplication;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.Security;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import javax.security.auth.Subject;
import javax.security.auth.login.LoginException;
import javax.swing.JProgressBar;

import net.sf.jsignpdf.AbstractSigner;
import net.sf.jsignpdf.SignerType;
import net.sf.jsignpdf.types.HashAlgorithm;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import sun.security.pkcs11.SunPKCS11;

public class Factory {
	
	public final static Logger logger = Logger.getLogger(Factory.class);
	
	public static String dir = System.getProperty("user.home");
	public static String cryptodll = "dllCrypto";
	public static String cryptoconfig = cryptodll+File.separator+"config";
	
	public static String externalDllPath = "dllPath";
	
	public void copyProvider(JProgressBar bar, PreferenceManager preferenceManager) throws Exception{
		 
		 String os = System.getProperty("os.name");
		 String osname = "windows";
		 if(os.toLowerCase().startsWith("window")){
			 osname = "windows";
		 }else{
			 osname = "linux";
		 }
		 //logger.info("osname " + osname);
		 
		 String[] names = new String[0];
		 if(osname.equals("windows")){
			 names= preferenceManager.getConfiguration().getStringArray( ConfigConstants.PROPERTY_SUNPKCS11LIB_WINDOWS );
			 if (names.length==1 && StringUtils.isEmpty(names[0])) names = new String[0];
		 }else{
			 names = preferenceManager.getConfiguration().getStringArray( ConfigConstants.PROPERTY_SUNPKCS11LIB_LINUX );
			 //names = new String[]{"libbit4ipki.so","libbit4ipki.so_pin.py","libbit4ipki.so.conf", "libsofthsm.so"};
			 //names= PreferenceManager.getConfiguration().getStringArray(PreferenceKeys.SMART_PROVIDER_LINUX);
		 }		 
		 bar.setMinimum(0);
		 bar.setMaximum(names.length);
		 
		 File tmp = new File(Factory.dir);
		 File directory = new File( tmp + File.separator + Factory.cryptodll );
		 
		 //directory.delete();
		 if(!directory.exists()){
			 directory.mkdirs();
		 }
		 for(int i=0;i<names.length;i++){
			 File file = new File(directory.getAbsolutePath()+File.separator+names[i]);
			 bar.setValue(i+1);
			 bar.setString("Caricamento provider "+names[i]+" ("+(i+1)+"/"+names.length+")");
			 logger.info("Caricamento provider "+names[i]+" ("+(i+1)+"/"+names.length+")");
			 if(!file.exists()){
				 FileOutputStream out =  new FileOutputStream(directory.getAbsolutePath()+File.separator+names[i]); 
				 IOUtils.copy(this.getClass().getResourceAsStream("/it/eng/client/provider/"+osname+"/"+names[i]), out);
				 out.flush();
				 out.close();
			 }
		 }
		 //Controllo la directory di configurazione dei provider
		 File directoryConfig = new File(tmp+File.separator+Factory.cryptoconfig);	 
		 File configfile = new File(directoryConfig+File.separator+"crypto.config");
		 if(!directoryConfig.exists()){
			 directoryConfig.mkdirs();
		 }
		 if(configfile.exists()){
			 configfile.createNewFile();
		 }
		 		 
		 
	 }
	
	
	public static sun.security.pkcs11.SunPKCS11 getProvider(char[] pin,int slot, PreferenceManager preferenceManager) throws LoginException{
		//Devo controllare se la carta e' una CNS o meno
		logger.info("Inizio controllo provider disponibili");	 
		 
		LoginException loginex = null;
		List<File> lListFiles = new ArrayList<File>();
		
		lListFiles.addAll( findDllInSystem32(slot, pin, preferenceManager));
		//Carico i provider
		File tmp = new File(Factory.dir);
		File directory = new File(tmp+File.separator+"dllCrypto");
		File[] dll = directory.listFiles(new FileFilter() {

			@Override
			public boolean accept(File pathname) {
				return (pathname.getName().toLowerCase().endsWith(".dll") || 
						pathname.getName().toLowerCase().endsWith(".so"));
			}
		});
		lListFiles.addAll(Arrays.asList(dll));
		sun.security.pkcs11.SunPKCS11 provider = null;
		for(int i=0;i<lListFiles.size();i++){
			if(lListFiles.get(i).isFile()){	
				try{
					String os = System.getProperty("os.name");
					String osname = "windows";
					if(os.toLowerCase().startsWith("window")){
						 osname = "windows";
					 }else{
						 osname = "linux";
					}
					if(osname.equals("windows") || (osname.equals("linux") && lListFiles.get(i).getAbsolutePath().toLowerCase().endsWith("so"))){
						StringBuffer cardConfig=new StringBuffer();
				        cardConfig.append ("name=SmartCards \n");
				        cardConfig.append ("slot="+slot+" \n");
				        cardConfig.append ("library="+lListFiles.get(i).getAbsolutePath());
//				        cardConfig.append ("attributes(generate, *, *) = {\n");
//				        cardConfig.append (" CKA_TOKEN = true \n");
//				        cardConfig.append ("} \n");
				        			        
				        ByteArrayInputStream confStream = new ByteArrayInputStream(cardConfig.toString().getBytes());
						provider = new sun.security.pkcs11.SunPKCS11(confStream);
							
						 
						//signer = new BaseSigner(digest,signerType,provider);
						System.out.println("provider " + provider);
						Security.removeProvider( provider.getName() );
						int test = Security.addProvider(provider); 
						System.out.println("add provider " + test);
										
						//Tento una login per vedere se ho caricato la dll corretta
						provider.login(new Subject(), new PasswordHandler(pin));
						System.out.println("Provider caricato!");
						
						break;
					}
//				}catch(LoginException ex){
//				 
//					loginex = ex;
				}catch(Exception e){
					//e.printStackTrace();//debug
					System.out.println("DLL non corretta tento con la successiva!");
				}
			}
		}
        
        if(provider==null){
        	//Controllo sul file di configurazione se sono stati caricati riferimenti a nuovi provider
   		 	try{
	        	File directoryConfig = new File(tmp+File.separator+Factory.cryptoconfig);	 
	   		 	File configfile = new File(directoryConfig+File.separator+"crypto.config");
	   		 	Properties prop = new Properties();
	   		 	prop.load(new FileInputStream(configfile));
	   		 	if(prop.containsKey(Factory.externalDllPath)){
	   		 		String[] paths = prop.getProperty(Factory.externalDllPath).split(";");
	   		 		//Ciclo i path e controllo se ne esite uno valido
	   		 		for(String path:paths){
				    	StringBuffer cardConfig=new StringBuffer();
				        cardConfig.append ("name=SmartCards \n");
				        cardConfig.append ("slot="+slot+" \n");
				        cardConfig.append ("library="+path);     
				        ByteArrayInputStream confStream = new ByteArrayInputStream(cardConfig.toString().getBytes());
						provider = new sun.security.pkcs11.SunPKCS11(confStream);
	   		 			
						System.out.println("DLL:"+provider.getInfo().toLowerCase().trim());
						//signer = new BaseSigner(digest,signerType,provider);
						Security.addProvider(provider); 
						
						//Tento una login per vedere se ho caricato la dll corretta
						provider.login(new Subject(), new PasswordHandler(pin));
						
						System.out.println("Provider esterno caricato!");
	   		 		}
	   		 	}
	   		}catch(LoginException ex){
				loginex = ex;
			 
			}catch(Exception e){
   		  		 
   		  		System.out.println("DLL non corretta tento con la successiva!");
   		 	}
        }
        
        	if(loginex!=null){
        		throw loginex;
        	}
        
        return provider;
	}
	
	private static List<File> findDllInSystem32(int slot, char[] pin, PreferenceManager preferenceManager) {
		List<File> lList = new ArrayList<File>();
		String os = System.getProperty("os.name");
		String osname = "windows";
		if(os.toLowerCase().startsWith("window")){
			osname = "windows";
		}else{
			osname = "linux";
		}

		String[] names = new String[0];
		if(osname.equals("windows")){
			try {
				names= preferenceManager.getConfiguration().getStringArray(ConfigConstants.PROPERTY_SUNPKCS11LIB_WINDOWS);
				if (names.length==1 && StringUtils.isEmpty(names[0])) names = new String[0];
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else{
			return lList;
		}
		//Cerco le dll
		for (String lStrDllName : names){
			logger.info("Cerco la dll " + lStrDllName);
			File lFile = new File("C:\\windows\\System32\\" + lStrDllName);
			String path = lFile.getAbsolutePath();
			if (lFile.exists()){
				logger.info("La dll " + lStrDllName + " esiste. Provo ad utilizzarla");
				try {
					StringBuffer cardConfig=new StringBuffer();
					cardConfig.append ("name=SmartCards \n");
					cardConfig.append ("slot="+slot+" \n");
					cardConfig.append ("library="+path);     
					ByteArrayInputStream confStream = new ByteArrayInputStream(cardConfig.toString().getBytes());
					SunPKCS11 provider = new sun.security.pkcs11.SunPKCS11(confStream);

						Security.removeProvider(provider.getName());
					Security.addProvider(provider); 

					//Tento una login per vedere se ho caricato la dll corretta
					provider.login(new Subject(), new PasswordHandler(pin));
					logger.info("Provider system 32 caricato, termino!. Caricato " + path);
					lList.add(lFile);
				} catch (Exception e) {
					logger.info("Provider system 32 non caricato");
				}
			}
		}
		return lList;
	}
	
}
