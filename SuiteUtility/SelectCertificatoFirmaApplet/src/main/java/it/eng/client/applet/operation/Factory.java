package it.eng.client.applet.operation;

import static sun.security.pkcs11.wrapper.PKCS11Constants.CKF_OS_LOCKING_OK;
import it.eng.client.applet.exception.SmartCardException;
import it.eng.client.applet.i18N.MessageKeys;
import it.eng.client.applet.i18N.Messages;
import it.eng.client.applet.util.PreferenceKeys;
import it.eng.client.applet.util.PreferenceManager;
import it.eng.common.LogWriter;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.security.Security;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.security.auth.Subject;
import javax.security.auth.login.LoginException;
import javax.swing.JProgressBar;

import org.apache.commons.lang.StringUtils;

import sun.security.pkcs11.SunPKCS11;
import sun.security.pkcs11.wrapper.CK_C_INITIALIZE_ARGS;
import sun.security.pkcs11.wrapper.CK_TOKEN_INFO;
import sun.security.pkcs11.wrapper.PKCS11;
import sun.security.pkcs11.wrapper.PKCS11Exception;

public class Factory {


	//ultimo provider con login successo
	public static File lastDll=null;

	public static SunPKCS11 loginProvider( File dllFile,int slot,char[] pin ) throws Throwable {
		//LogWriter.writeLog("Tento il login con la dll " + dllFile + " utilizzando il pin " + new String(pin), false );
		LogWriter.writeLog("Tento il login con la dll " + dllFile );
		SunPKCS11 provider=null;
		if(dllFile.isFile()){	
			String os = System.getProperty("os.name");
			String osname = "windows";
			if(os.toLowerCase().startsWith("window")){
				osname = "windows";
			}else{
				osname = "linux";
			}
			if( osname.equals("windows") || 
					(osname.equals("linux") && dllFile.getAbsolutePath().toLowerCase().endsWith("so"))){
				StringBuffer cardConfig=new StringBuffer();
				cardConfig.append ("name=SmartCards \n");
				try {
					long[] slots = getSlotsWithTokens( dllFile.getAbsolutePath() );
					if( slots!=null ){
						for(int i=0;i<slots.length;i++){
							LogWriter.writeLog("Slot " + slots[i]);
						}
						if( slots.length>0)
							slot = new Long(slots[0]).intValue();
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				cardConfig.append ("slot="+slot+" \n");
				cardConfig.append ("library="+dllFile.getAbsolutePath()+" \n");
				cardConfig.append ("disabledMechanisms={ CKM_SHA1_RSA_PKCS }");
				LogWriter.writeLog("--->cardConfig " + cardConfig);

				try {
					ByteArrayInputStream confStream = new ByteArrayInputStream(cardConfig.toString().getBytes());
					provider = new sun.security.pkcs11.SunPKCS11(confStream);
	
					if( provider!=null ){
						LogWriter.writeLog("File Provider:"+provider.getInfo().toLowerCase().trim());
						
						//Tento una login per vedere se ho caricato la dll corretta
						
							provider.login(new Subject(), new PasswordHandler(pin));
							
							Security.removeProvider(provider.getName());
							Security.addProvider(provider); 
							LogWriter.writeLog( Messages.getMessage( MessageKeys.MSG_FIRMAMARCA_PROVIDERLOADED ) );
						
					}
				} catch (Exception e) {
					LogWriter.writeLog("Provider non caricato ", e);
					throw e;
				} catch (Throwable e) {
					LogWriter.writeLog("Provider non caricato ", e);
					throw e;
				}
			}
		}
		return provider;

	}

	/*
	 * Metodo che fa il login al provider e restituisce il signer
	 */
	public static AbstractSigner[] getSigner( char[] pin, int slot, boolean timemark) throws SmartCardException{
		//Devo controllare se la carta e' una CNS o meno
		LogWriter.writeLog("Inizio controllo provider disponibili");	

		LoginException loginex = null;
		AbstractSigner signer = null;
		List<AbstractSigner> lListSigners = new ArrayList<AbstractSigner>();
		//Carico i provider
		sun.security.pkcs11.SunPKCS11 provider = null;
		// tenta prima con l'ultimo andato bene per evitare tutte el dll
		LogWriter.writeLog("lastDll " + lastDll );	
		if(lastDll!=null){
			try{
				provider=loginProvider(lastDll, slot, pin);
				if(provider!=null){
					signer = new BaseSigner( provider);
					lListSigners.add(signer);
				}
			}catch(LoginException e){
				LogWriter.writeLog("Errore", e);
				signer = null;
				loginex = e;
			}catch(Exception e){
				LogWriter.writeLog("Errore", e);
				signer = null;
				LogWriter.writeLog("DLL non corretta tento con la successiva!");
			}catch(Throwable e){
				LogWriter.writeLog("Errore", e);
				signer = null;
				LogWriter.writeLog("DLL non corretta tento con la successiva!");
			}
		} else {
			LogWriter.writeLog("Cerco le dll in System32");
			lListSigners.addAll(findDllInSystem32(slot, pin));
			File dllDirectory = new File( AbstractSigner.dir + File.separator + AbstractSigner.cryptodll );
			LogWriter.writeLog("Carico le dll in " + dllDirectory );
			File[] dll = dllDirectory.listFiles(new FileFilter() {

				@Override
				public boolean accept(File pathname) {
					return (pathname.getName().toLowerCase().endsWith(".dll") || 
							pathname.getName().toLowerCase().endsWith(".so"));
				}
			});
			LogWriter.writeLog("trovate " + dll.length + " dll ");
			for(int i=0;i<dll.length;i++){
				if(dll[i].isFile()){
					try{
						provider=loginProvider(dll[i], slot, pin);
						if(provider!=null){
							signer = new BaseSigner( provider);
							lListSigners.add(signer);
						}
					} catch(LoginException e){
						LogWriter.writeLog("Errore", e);
						signer = null;
						loginex = e;
					} catch(Exception e){
						LogWriter.writeLog("Errore", e);
						signer = null;
						LogWriter.writeLog("DLL non corretta tento con la successiva!");
					}catch(Throwable e){
						LogWriter.writeLog("Errore", e);
						signer = null;
						LogWriter.writeLog("DLL non corretta tento con la successiva!");
					}
				}
			}
		}

		//Controllo sul file di configurazione se sono stati caricati riferimenti a nuovi provider
		try {
			File directoryConfig = new File( AbstractSigner.dir + File.separator + AbstractSigner.cryptoConfig);	 
			File configfile = new File( directoryConfig+File.separator+"crypto.config" );
			Properties prop = new Properties();
			prop.load( new FileInputStream(configfile));
			if(prop.containsKey(AbstractSigner.externalDllPath)){
				String[] paths = prop.getProperty(AbstractSigner.externalDllPath).split(";");
				//Ciclo i path e controllo se ne esite uno valido
				for(String path:paths){
					try{
						StringBuffer cardConfig=new StringBuffer();
						cardConfig.append ("name=SmartCards \n");
						cardConfig.append ("slot="+slot+" \n");
						cardConfig.append ("library="+path);     
						ByteArrayInputStream confStream = new ByteArrayInputStream(cardConfig.toString().getBytes());
						provider = new sun.security.pkcs11.SunPKCS11(confStream);

						signer = new BaseSigner(provider);
						Security.removeProvider(provider.getName());
						Security.addProvider(provider); 

						//Tento una login per vedere se ho caricato la dll corretta
						provider.login(new Subject(), new PasswordHandler(pin));

						LogWriter.writeLog("Provider esterno caricato, termino!. Caricato " + path);
						lListSigners.add(signer);
					}catch(LoginException e){
						LogWriter.writeLog("Errore", e);
						loginex = e;
						signer = null;
						LogWriter.writeLog("DLL non corretta tento con la successiva!");
					}catch(Exception e){
						LogWriter.writeLog("Errore", e);
						signer = null;
						LogWriter.writeLog("DLL non corretta tento con la successiva!");
					}
				}
			}
		}catch(Exception e){
			LogWriter.writeLog("Errore", e);
			signer = null;
			LogWriter.writeLog("Properties non caricate");
		}
		if(lListSigners.size()==0){
			if(loginex!=null){
				throw new SmartCardException( loginex );
			}
		}

		return lListSigners.toArray(new AbstractSigner[]{});
	}
	
	public static List<String> getPathProviders() throws SmartCardException{
		LogWriter.writeLog("Inizio controllo provider disponibili");	

		List<String> pathProviders = new ArrayList<String>();
		
		String os = System.getProperty("os.name");
		String osname = "windows";
		if(os.toLowerCase().startsWith("window")){
			osname = "windows";
		}else{
			osname = "linux";
		}

		String[] names = new String[0];
		if(osname.equals("windows")){
			names= PreferenceManager.getStringArray(PreferenceKeys.SMART_PROVIDER_WIND);
			if (names.length==1 && StringUtils.isEmpty(names[0])) names = new String[0];
			
			for (String lStrDllName : names){
				String dll32 = "C:\\windows\\System32\\" + lStrDllName;
				File dll32File = new File( dll32 );
				if( dll32File.exists() )
					pathProviders.add( dll32 );
				else 
					LogWriter.writeLog("Il provider " + dll32 + " non esiste.");
			}
			for (String lStrDllName : names){
				String dll = AbstractSigner.dir + File.separator + AbstractSigner.cryptodll + File.separator + lStrDllName;
				File dllFile = new File( dll );
				if( dllFile.exists() )
					pathProviders.add( dll );
				else 
					LogWriter.writeLog("Il provider " + dll + " non esiste.");
			}
		} else {
			names= PreferenceManager.getStringArray(PreferenceKeys.SMART_PROVIDER_LINUX);
			String dllDirectory = AbstractSigner.dir + File.separator + AbstractSigner.cryptodll + File.separator;
			for (String name : names ){
				File dllFile = new File( dllDirectory + name );
				if( dllFile.exists() )
					pathProviders.add( dllDirectory + name );
				else 
					LogWriter.writeLog("Il provider " + dllDirectory + name + " non esiste.");
			}
		}
		
		LogWriter.writeLog("Providers " + pathProviders);
		return pathProviders;
	}

	private static List<AbstractSigner> findDllInSystem32(int slot, char[] pin) {
		List<AbstractSigner> lList = new ArrayList<AbstractSigner>();
		String os = System.getProperty("os.name");
		String osname = "windows";
		if(os.toLowerCase().startsWith("window")){
			osname = "windows";
		}else{
			osname = "linux";
		}

		String[] names = new String[0];
		if(osname.equals("windows")){
			names= PreferenceManager.getStringArray(PreferenceKeys.SMART_PROVIDER_WIND);
			if (names.length==1 && StringUtils.isEmpty(names[0])) names = new String[0];
		}else{
			return lList;
		}
		//Cerco le dll
		for (String lStrDllName : names){
			LogWriter.writeLog("Cerco la dll " + lStrDllName);
			File lFile = new File("C:\\windows\\System32\\" + lStrDllName);
			String path = lFile.getAbsolutePath();
			if (lFile.exists()){
				LogWriter.writeLog("La dll " + lStrDllName + " esiste. Provo ad utilizzarla");
				try {
					StringBuffer cardConfig=new StringBuffer();
					cardConfig.append ("name=SmartCards \n");
					cardConfig.append ("slot="+slot+" \n");
					cardConfig.append ("library="+path);     
					ByteArrayInputStream confStream = new ByteArrayInputStream(cardConfig.toString().getBytes());
					SunPKCS11 provider = new sun.security.pkcs11.SunPKCS11(confStream);

					AbstractSigner signer = new BaseSigner(provider);
					Security.removeProvider(provider.getName());
					Security.addProvider(provider); 

					//Tento una login per vedere se ho caricato la dll corretta
					provider.login(new Subject(), new PasswordHandler(pin));
					LogWriter.writeLog("Provider system 32 caricato, termino!. Caricato " + path);
					lList.add(signer);
				} catch (Exception e) {
					LogWriter.writeLog("Provider system 32 non caricato");
				}
			}
		}
		return lList;
	}



	public void copyProvider(JProgressBar bar) throws Exception{
		LogWriter.writeLog("INIZIO COPIA PROVIDER!");
		String os = System.getProperty("os.name");
		String osname = "windows";
		if(os.toLowerCase().startsWith("window")){
			osname = "windows";
		}else{
			osname = "linux";
		}

		String[] names = new String[0];
		if(osname.equals("windows")){
			names= PreferenceManager.getStringArray(PreferenceKeys.SMART_PROVIDER_WIND);
			if (names.length==1 && StringUtils.isEmpty(names[0])) names = new String[0];
		}else{
			names= PreferenceManager.getStringArray(PreferenceKeys.SMART_PROVIDER_LINUX);
		}		 
		bar.setMinimum(0);
		bar.setMaximum(names.length);

		File directory = new File( AbstractSigner.dir + File.separator + AbstractSigner.cryptodll );
		//directory.delete();
		if(!directory.exists()){
			directory.mkdirs();
		}
		for(int i=0;i<names.length;i++){
			//			LogWriter.writeLog("File:"+names[i]);
			//			File file = new File(directory.getAbsolutePath()+File.separator+names[i]);
			//			bar.setValue(i+1);
			//			bar.setString("Caricamento provider "+names[i]+" ("+(i+1)+"/"+names.length+")");
			//			if(!file.exists()){
			//				FileOutputStream out =  new FileOutputStream(directory.getAbsolutePath()+File.separator+names[i]); 
			//				IOUtils.copy(this.getClass().getResourceAsStream("/it/eng/client/provider/"+osname+"/"+names[i]), out);
			//				out.flush();
			//				out.close();
			//			}
		}
		//Controllo la directory di configurazione dei provider
		File directoryConfig = new File( AbstractSigner.dir + File.separator + AbstractSigner.cryptoConfig);	 
		File configfile = new File(directoryConfig+File.separator+"crypto.config");
		if(!directoryConfig.exists()){
			directoryConfig.mkdirs();
		}
		if(configfile.exists()){
			configfile.createNewFile();
		}

		LogWriter.writeLog("FINE COPIA PROVIDER!");
	}

	/**
	 * verifica se il file passato è firmato, ritorna il tipo di busta riconoscito se rileva la firma
	 * null altrimenti
	 * @param file
	 * @return
	 * @throws CryptoSignerException
	 */
//	public static SignerType isSigned(File file)   {
//		SignerType ret=null;
//		//provo tutti gli envelop detector
//		for (int i = 0; i < clazzs.length; i++) {
//			try {
//				Object signer=clazzs[i].newInstance();
//				if(signer instanceof EnvelopeDetector){
//					ret=((EnvelopeDetector) signer).isSignedType(file);
//					if ( ret!=null ) {
//						break;//prendo il primo che mi rileva il formato
//					}
//				}
//			} catch (InstantiationException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} catch (IllegalAccessException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//
//		}
//		return ret;
//
//	}
//
//	public static InputStream extractContent(File file) throws Exception  {
//		InputStream ret=null;
//		//provo tutti gli envelop detector
//		for (int i = 0; i < clazzs.length; i++) {
//			try {
//				Object signer=clazzs[i].newInstance();
//				if(signer instanceof EnvelopeDetector){
//					SignerType type=((EnvelopeDetector) signer).isSignedType(file);
//					if ( type!=null ) {
//						ret=((EnvelopeDetector) signer).getContent(file);
//						break;//prendo il primo che mi rileva il formato
//					}
//				}
//			} catch (InstantiationException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} catch (IllegalAccessException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//
//		}
//		return ret;
//
//	}


	public static long[] getSlotsWithTokens(String libraryPath) throws Exception {
        CK_C_INITIALIZE_ARGS initArgs = new CK_C_INITIALIZE_ARGS();
        String functionList = "C_GetFunctionList";

        initArgs.flags = CKF_OS_LOCKING_OK;
        PKCS11 tmpPKCS11 = null;
        long[] slotList = null;
        try {
            try {
                tmpPKCS11 = PKCS11.getInstance(libraryPath, functionList, initArgs, false);
            } catch (Exception ex) {
                LogWriter.writeLog("", ex);
                throw ex;
            }
        } catch (PKCS11Exception e) {
            try {
                initArgs = null;
                tmpPKCS11 = PKCS11.getInstance(libraryPath, functionList, initArgs, true);
            } catch (Exception ex) {
            	 LogWriter.writeLog("", ex);
                 
            } 
        }

        try {
            slotList = tmpPKCS11.C_GetSlotList(true);

            for (long slot : slotList){
                CK_TOKEN_INFO tokenInfo = tmpPKCS11.C_GetTokenInfo(slot);
                LogWriter.writeLog("slot: "+slot+"\nmanufacturerID: "
                            + String.valueOf(tokenInfo.manufacturerID) + "\nmodel: "
                            + String.valueOf(tokenInfo.model));
                
            }
        } catch (PKCS11Exception ex) {
        	 LogWriter.writeLog("", ex);
             
        } catch (Throwable t) {
        	 LogWriter.writeLog("", t);
             
        }

        return slotList;

    }

}
