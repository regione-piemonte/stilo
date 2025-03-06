package it.eng.client.applet.util;

import it.eng.client.applet.SmartCard;
import it.eng.client.applet.bean.PrivateKeyAndCert;
import it.eng.client.applet.exception.SmartCardException;
import it.eng.client.applet.operation.AbstractSigner;
import it.eng.client.applet.operation.PasswordHandler;
import it.eng.common.LogWriter;
import it.eng.common.bean.SignerObjectBean;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.security.AuthProvider;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.Security;
import java.security.cert.X509Certificate;
import java.util.Enumeration;

import javax.security.auth.Subject;
import javax.security.auth.login.LoginException;

import sun.security.pkcs11.SunPKCS11;

public class SunPKCS11Utility {

	 
	
	/**
	 * ritorna il provider SunPCKS11 accedendo al token  
	 * @param pin
	 * @param slot
	 * @return
	 * @throws Exception
	 */
//	public static SunPKCS11 getProvider(char[]pin,int slot)throws Exception{
//		LogWriter.writeLog("Inizio controllo provider disponibili");	
//		 
//		LoginException loginex = null;
//		//Carico i provider
//		File tmp = new File(AbstractSigner.dir);
//		File directory = new File(tmp+File.separator+"dllCrypto");
//		File[] dll = directory.listFiles();
//		sun.security.pkcs11.SunPKCS11 provider = null;
//		for(int i=0;i<dll.length;i++){
//			if(dll[i].isFile() && dll[i].getName().startsWith("libsofthsm")){	
//				try{
//					String os = System.getProperty("os.name");
//					String osname = "windows";
//					if(os.toLowerCase().startsWith("window")){
//						 osname = "windows";
//					 }else{
//						 osname = "linux";
//					}
//					if(osname.equals("windows") || (osname.equals("linux") && dll[i].getAbsolutePath().toLowerCase().endsWith("so"))){
//						System.out.println(dll[i].getAbsolutePath());
//				    	StringBuffer cardConfig=new StringBuffer();
//				        cardConfig.append ("name=SmartCards \n");
//				        cardConfig.append ("slot="+slot+" \n");
//				        cardConfig.append ("library="+dll[i].getAbsolutePath());
//				        			        
//				        ByteArrayInputStream confStream = new ByteArrayInputStream(cardConfig.toString().getBytes());
//						provider = new sun.security.pkcs11.SunPKCS11(confStream);
//							
//						LogWriter.writeLog("File Provider:"+provider.getInfo().toLowerCase().trim());
//						Security.removeProvider(provider.getName());
//						Security.addProvider(provider); 
//										
//						//Tento una login per vedere se ho caricato la dll corretta
//						provider.login(new Subject(), new PasswordHandler(pin));
//						
//						LogWriter.writeLog("Provider caricato!");
//						break;
//					}
//				}catch(LoginException ex){
//					 
//					loginex = ex;
//				}catch(Exception e){
//					e.printStackTrace();//debug
//					 
//					LogWriter.writeLog("DLL non corretta tento con la successiva!",e);
//				}
//			}
//		}
//		return provider;
//	}
	
	/**
	 * ritorna il certificato e la chiave privata presenti nel supporto PKCS11
	 * FIXME ci possono essere più certificati
	 * @param provider
	 * @param pin
	 * @return
	 * @throws Exception
	 */
//	public static PrivateKeyAndCert getPrivateKeyAndCert(AuthProvider provider,char[]pin)throws Exception{
//		 try{
//		    	PrivateKeyAndCert privAndCert=new PrivateKeyAndCert();
//		    	provider.login(new Subject(), new PasswordHandler(pin));
//			    	    
//			 	KeyStore keyStore = KeyStore.getInstance("PKCS11",provider);
//			 	keyStore.load(null,null);		 	        
//		        //Get the first private key in the keystore:
//		        PrivateKey privateKey = null;
//		        Enumeration enumeration = keyStore.aliases();
//		        while(enumeration.hasMoreElements()){
//		        	
//		        	String alias = enumeration.nextElement().toString();
//		        	LogWriter.writeLog("ALIAS:"+alias);
//		        	privateKey = (PrivateKey)keyStore.getKey(alias, null); 
//		        	LogWriter.writeLog("PRIVATE KEY:"+privateKey);
//		            X509Certificate certificate=(X509Certificate)keyStore.getCertificate(alias);
//
//		            //controllo se � un certificato di firma
//		            boolean[] usage = certificate.getKeyUsage();
//		            for(boolean h : usage){
//		            	String us = "";
//		            	
//		            	if(h){
//		            		us+="1";
//		            	}else{
//		            		us+="0";
//		            	}
//		            	LogWriter.writeLog("KEY USAGE:"+us);
//		            }
//		            
//		            //Come da normativa deve contenere il flag di noRepudiation a true per essere un certificato di firma valido
//		            //String controlAll = System.getProperty(Constants.ALL_CERTIFICATE_USE);
//		            
//		            //TODO RM da togliere se si vuole controllare che il certificato � di tipo firma
//		            String controlAll = SmartCard.DEBUG;
//		            
//		            SignerObjectBean bean = null;
//		            if(usage[1] || controlAll.equals("0")){
//		            	privAndCert.setCertificate(certificate);
//		            	privAndCert.setPrivateKey(privateKey);
//		            	privAndCert.setChain( keyStore.getCertificateChain(alias));
//		            }
//		        }  
//		        return privAndCert ;
//		    }catch(Exception e){
//		    	e.printStackTrace();
//		    	throw new SmartCardException(e);
//		    }finally{
////		    	try{
////		    		provider.logout();
////		    	}catch(Exception e){
////		    		//ignore
////		    	}
//		    }
//	}
	 
}
