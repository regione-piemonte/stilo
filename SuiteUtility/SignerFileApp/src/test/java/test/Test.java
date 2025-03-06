package test;


import java.io.FileInputStream;
import java.io.InputStream;
import java.security.Security;
import java.security.cert.CertStore;
import java.security.cert.X509Certificate;

import org.apache.commons.io.FilenameUtils;
import org.bouncycastle.cms.CMSSignedData;
import org.bouncycastle.cms.SignerInformation;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

public class Test {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		
		
		String ret = FilenameUtils.getFullPath("C:/pippo/ieie.txt");
		System.out.println(ret);
		
		
		
		
//		//
//		Security.addProvider(new BouncyCastleProvider());
//		
//		InputStream stremodgs = new FileInputStream("c:\\testoodt.odt.p7m");
//		
//		
//		//Prendo il contenuto firmato di ODG
//		CMSSignedData cmsODGS = new CMSSignedData(stremodgs);		
//		
//		//Recupero l'algoritmo e l'hash
//		SignerInformation signerodg = (SignerInformation)cmsODGS.getSignerInfos().getSigners().iterator().next();
//		
//		CertStore collODG = (CertStore)cmsODGS.getCertificatesAndCRLs("Collection",  BouncyCastleProvider.PROVIDER_NAME);
//		
//			
//		System.out.println(signerodg.getDigestAlgOID());
//		
//		X509Certificate cert = (X509Certificate)collODG.getCertificates(null).iterator().next();
//		
//		System.out.println(cert.getPublicKey());
//		
//		boolean odgsverify = signerodg.verify((X509Certificate)collODG.getCertificates(null).iterator().next(), BouncyCastleProvider.PROVIDER_NAME);
		
		

	}

}
