package test;
import it.eng.client.applet.operation.pdf.PdfPKCS7;
import it.eng.client.applet.operation.pdf.TSABouncy;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.Provider;
import java.security.Security;
import java.security.cert.Certificate;
import java.security.cert.X509CRL;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FileUtils;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import sun.security.pkcs11.wrapper.PKCS11;

import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfDate;
import com.itextpdf.text.pdf.PdfDictionary;
import com.itextpdf.text.pdf.PdfName;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfSignatureAppearance;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.PdfString;


public class MyPdfSigner {

	public void signerPDF(PrivateKey key,X509Certificate certificate,Provider provider) throws Exception{
		
		Security.addProvider(new BouncyCastleProvider());
		
		InputStream stream = new FileInputStream(new File("C:\\odg.pdf"));
		PdfReader reader = new PdfReader(stream);
		ByteArrayOutputStream fout = new ByteArrayOutputStream();
		PdfStamper stp = PdfStamper.createSignature(reader, fout, '\0');
		PdfSignatureAppearance sap = stp.getSignatureAppearance();
				
		Rectangle rect = reader.getPageSize(1);
		float w = rect.getWidth();
		float h = rect.getHeight();
		
		// comment next line to have an invisible signature
		sap.setVisibleSignature(new Rectangle(w-20,h-20,w-120,h-120), 1, null);
		sap.setLayer2Text("Firma Digitale.");
		Calendar cal = Calendar.getInstance();
		PdfDictionary dic = new PdfDictionary();
		dic.put(PdfName.FT, PdfName.SIG);
		dic.put(PdfName.FILTER,PdfName.ADOBE_PPKLITE);
		dic.put(PdfName.SUBFILTER, PdfName.ADBE_PKCS7_DETACHED);
		dic.put(PdfName.M,new PdfDate(sap.getSignDate()));
		//dic.put(PdfName.M, new PdfDate(cal));
		sap.setCryptoDictionary(dic);
		HashMap exc = new HashMap();
		exc.put(PdfName.CONTENTS, new Integer(15002));
		sap.preClose(exc);
		
		byte[] hash = DigestUtils.sha(sap.getRangeStream());
		
		PdfPKCS7 pk7 = new PdfPKCS7(key, new Certificate[]{certificate}, null, "SHA-1", provider.getName(), false);
		
		byte sh[] = pk7.getAuthenticatedAttributeBytes(hash,cal,null);
		pk7.update(sh, 0, sh.length);
		
		
		//REcupero il timestamp token
		//TimeStampTokenGetter getter = new TimeStampTokenGetter("http://timestamping.edelweb.fr/service/tsp", hash);
		
//		 byte[] ocsp = null; 
//         if (chain.length >= 2) { 
//             String url = PdfPKCS7.getOCSPURL(certificate); 
//             if (url != null && url.length() > 0) 
//                 ocsp = new OcspClientBouncyCastle(certificate,url).getEncoded(); 
//         }
		
		byte sg[] = pk7.getEncodedPKCS7(hash, cal, new TSABouncy("http://timestamping.edelweb.fr/service/tsp"));
       
        //Setto i certificati e le CRL
        //bean.getCertificates().toArray(new Certificate[0]);
        
        List<X509Certificate> certificates = new ArrayList<X509Certificate>();
        certificates.add(certificate);
        List<X509CRL> crls = new ArrayList<X509CRL>();
//        Util util = new Util();
//        for(X509Certificate certificate:certificates){
//        	crls.add(util.getCRL(certificate));
//        }
        
        //CertStore store = getter.getTimeStampToken().getCertificatesAndCRLs("SHA1", "BC");
                
        //Collection<X509Certificate> coll = (Collection<X509Certificate>)store.getCertificates(null);
        
        //certificates.addAll(coll);
        
        //sap.setCrypto(null, (Certificate[])certificates.toArray(new Certificate[0]) , (CRL[])crls.toArray(new CRL[0]), null);
               
        //sg = bean.getSignerInfo();
		PdfDictionary dic2 = new PdfDictionary();
		byte out[] = new byte[15000/2];
		
		System.out.println(sg.length);
		
		System.arraycopy(sg, 0, out, 0, sg.length);
		dic2.put(PdfName.CONTENTS, new PdfString(out).setHexWriting(true));
		sap.close(dic2);
		
		File firmato = new File("c:\\ODG2_signed.pdf");
		
		FileUtils.writeByteArrayToFile(firmato, fout.toByteArray());
	}

	public Provider loadAllCertificate(char[] pin) throws Exception{
		
		boolean isLoad = false;
		//Carico i provider
		String dir = "c:\\Temp";
		File tmp = new File(dir);
		File directory = new File(tmp+File.separator+"dllCrypto");
		File[] dll = directory.listFiles();
	 	Provider pkcs11Provider = null;
	 	KeyStore keyStore = null;
	 	 PKCS11.loadNative();
	 	for(int i=0;i<dll.length;i++){
	 		try{
	 			
		    	StringBuffer cardConfig=new StringBuffer();
		        cardConfig.append ("name=SmartCards \n");
		        cardConfig.append ("library="+dll[i].getAbsolutePath()+" \n");
		        
		        
		        ByteArrayInputStream confStream = new ByteArrayInputStream(cardConfig.toString().getBytes());
		               
		        // Create the provider
		        Class sunPkcs11Class = Class.forName("sun.security.pkcs11.SunPKCS11");
		        Constructor pkcs11Constr = sunPkcs11Class.getConstructor(java.io.InputStream.class);
		        pkcs11Provider = (Provider)pkcs11Constr.newInstance(confStream);
		            
		        Iterator<String> itera = pkcs11Provider.stringPropertyNames().iterator();
		        while(itera.hasNext()){
		        	String key = itera.next();
		        	System.out.println(key+":"+pkcs11Provider.getProperty(key));
		        }        
        
			 	keyStore = KeyStore.getInstance("PKCS11",pkcs11Provider);
		        keyStore.load(null, pin);
		        	        
		        isLoad = true;
		        Security.addProvider(pkcs11Provider);
		        
		        return pkcs11Provider;
		 	}catch(Exception e){
		 		e.printStackTrace();
		 	}
	 	}	
	 	if(!isLoad){
	 		throw new Exception("Errore loading Certificati!");
	 		
	 	}
	 	return null;
	 	
 	 }

	public static void main(String[] args) throws Exception {
		MyPdfSigner signer = new MyPdfSigner();
		Provider provider = signer.loadAllCertificate("94653500".toCharArray());
		
		
	 	KeyStore keyStore = KeyStore.getInstance("PKCS11",provider);
	 	keyStore.load(null, "94653500".toCharArray());
		
	 	 PrivateKey privateKey;
	        
	        Enumeration enumeration = keyStore.aliases();
	        
	        while(enumeration.hasMoreElements()){
	        	
	        	String alias = enumeration.nextElement().toString();
         
	        	privateKey = (PrivateKey)keyStore.getKey(alias, null); 
	        
	            X509Certificate certificate=(X509Certificate)keyStore.getCertificate(alias);
	            
	            //controllo se e' un certificato di firma
	            
	            boolean[] usage = certificate.getKeyUsage();
	            
	            for(boolean h : usage){
	            	String us = "";
	            	
	            	if(h){
	            		us+="1";
	            	}else{
	            		us+="0";
	            	}
	            	
	            }
	 	
	            signer.signerPDF(privateKey, certificate, provider);
	            
	            break;
	    }
	}
	
	
}
