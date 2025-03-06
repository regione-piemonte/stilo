package test;
import it.eng.client.applet.operation.AbstractSigner;
import it.eng.client.applet.operation.BaseSigner;
import it.eng.client.applet.operation.PasswordHandler;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.security.Security;

import javax.security.auth.Subject;
import javax.security.auth.login.LoginException;

import org.bouncycastle.jce.provider.BouncyCastleProvider;


public class MainTest {

	public static void main(String[] args) {
		System.out.println("INIZO CARICAMENTO PROVIDER BOUNCY CASTLE");
		Security.addProvider(new BouncyCastleProvider());
		
		File tmp = new File(AbstractSigner.dir);
		File directory = new File("/home/michele/driverpkcs11");
		File[] dll = directory.listFiles();
		sun.security.pkcs11.SunPKCS11 provider = null;
		
		for(int i=0;i<dll.length;i++){
			if(dll[i].isFile()){
				System.out.println("SO:"+dll[i].getAbsolutePath());
				if(dll[i].getAbsolutePath().endsWith(".so")){
					try{
						System.out.println(dll[i].getAbsolutePath());
				    	StringBuffer cardConfig=new StringBuffer();
				        cardConfig.append ("name=SmartCards \n");
				        cardConfig.append ("library="+dll[i].getAbsolutePath());     
				        ByteArrayInputStream confStream = new ByteArrayInputStream(cardConfig.toString().getBytes());
						provider = new sun.security.pkcs11.SunPKCS11(confStream);
							
						System.out.println("DLL:"+provider.getInfo().toLowerCase().trim());
						
						Security.addProvider(provider); 
										
						//Tento una login per vedere se ho caricato la dll corretta
						provider.login(new Subject(), new PasswordHandler("12345678".toCharArray()));
						
						System.out.println("Provider caricato!");
						break;
					}catch(LoginException ex){
						ex.printStackTrace();
					}catch(Exception e){
						e.printStackTrace();
					}
				}
			}
		}
		
		
		
		
	}
	
}
