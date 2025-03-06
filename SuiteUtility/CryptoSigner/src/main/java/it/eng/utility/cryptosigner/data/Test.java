package it.eng.utility.cryptosigner.data;

import java.io.File;
import java.io.FileOutputStream;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Security;

import javax.crypto.NoSuchPaddingException;

import org.apache.commons.io.IOUtils;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

public class Test {

	/**
	 * @param args
	 * @throws NoSuchPaddingException 
	 * @throws NoSuchProviderException 
	 * @throws NoSuchAlgorithmException 
	 */
	public static void main(String[] args) throws Exception {
		Security.addProvider(new BouncyCastleProvider());
		
		AbstractSigner signer = SignerUtil.newInstance().getSignerManager(new File("/home/michele/Progetti/DAX/Varie/RdP_Firma_NonValida.pdf"));
		
		System.out.println(signer.getFormat());
		
		//CMSTimeStampedDataParser data = new CMSTimeStampedDataParser(FileUtils.openInputStream(new File("/home/michele/Progetti/DAX/Varie/SUPP n.99 al B.U. del 12.10.2011 pII.pdf.p7m.tsd")));
		
		
		
		
		//IOUtils.copyLarge(signer.getUnsignedContent(),new FileOutputStream("/home/michele/Progetti/DAX/Varie/RdP_Firma_NonValida_32.pdf"));
		
		//Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding", "BC");

		//System.out.println(cipher);
	
	}
	

}
