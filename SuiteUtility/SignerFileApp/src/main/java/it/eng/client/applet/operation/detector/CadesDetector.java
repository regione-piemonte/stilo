package it.eng.client.applet.operation.detector;

import it.eng.common.type.SignerType;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.security.cert.CertStore;
import java.util.Collection;

import org.apache.commons.codec.binary.Base64InputStream;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;
import org.apache.commons.lang.StringUtils;
import org.bouncycastle.asn1.cms.AttributeTable;
import org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.bouncycastle.cms.CMSSignedDataGenerator;
import org.bouncycastle.cms.CMSSignedDataParser;
import org.bouncycastle.cms.SignerInformation;
import org.bouncycastle.cms.SignerInformationStore;

public class CadesDetector extends P7MDetector implements EnvelopeDetector{
	 private SignerType signerType;
	/**
	 * Restituisce true se il contenuto del file � di tipo CMS e rispetta
	 * le seguenti condizioni:
	 * <ul>
	 * 	<li>L'algoritmo di digest deve essere SHA256</li>
	 *  <li>Il certificato di firma deve essere presente come attributo signing-certificate oppure ESS-signing-certificate-v2</li>
	 * </ul>
	 * Recupera inoltre il timestamp se presente come attributo non firmato (CAdES-T)
	 */
	protected boolean isSignedType(CMSSignedDataParser cmsSignedDataInternal) {
		 
		 
		
		boolean signed = false;
		try {
			cmsSignedDataInternal.getSignedContent().drain();
						
			SignerInformationStore signersStore = cmsSignedDataInternal.getSignerInfos();
			Collection<SignerInformation> signers = signersStore.getSigners();
			if (signers==null || signers.isEmpty()) {
				signed = false;
			} else {
				//Controllo se l'algoritmo � di tipo SHA256 e che sia presente l'attributo contenente il certificato
				for (SignerInformation signer:signers) {
					AttributeTable signedTable = signer.getSignedAttributes();
					boolean certv2 = signedTable.toHashtable().containsKey(PKCSObjectIdentifiers.id_aa_signingCertificateV2);
					boolean cert = signedTable.toHashtable().containsKey(PKCSObjectIdentifiers.id_aa_signingCertificate);
					if (certv2 && cert) {
						signed = false;
						break;
					} else {	
						// L'algoritmo di digest deve essere SHA256, inoltre deve essere presente il certificato
						// come attributo signing-certificate oppure ESS-signing-certificate-v2
						if (! (CMSSignedDataGenerator.DIGEST_SHA256.equals(signer.getDigestAlgOID()) && (certv2 || !cert)) ) {
							signed = false;
							break;
						}
					}
					signed = true;
					//FIXME 
//					CertStore store = cmsSignedDataInternal.getCertificatesAndCRLs("Collection", BouncyCastleProvider.PROVIDER_NAME);
//					Collection collection = store.getCertificates(null);				
//					signer.verify((X509Certificate)collection.iterator().next(), BouncyCastleProvider.PROVIDER_NAME);
//					hash = signer.getContentDigest();
					
					// I formati CAdES_T e CAdES_C sono pi� restrittivi di CAdES_BES
					if (signerType == null)
						signerType = SignerType.CAdES_BES;
					
					//TODO Controllo da verificare
					AttributeTable unsignedTable  = signer.getUnsignedAttributes();
					if (unsignedTable!=null && unsignedTable.toHashtable().containsKey(PKCSObjectIdentifiers.id_aa_signatureTimeStampToken)) {
						signerType = SignerType.CAdES_T;
						//Controllo se sono presenti gli atttibuti CRL negli attributi unsigned
						if (unsignedTable.toHashtable().containsKey(PKCSObjectIdentifiers.id_aa_ets_certificateRefs) && unsignedTable.toHashtable().containsKey(PKCSObjectIdentifiers.id_aa_ets_revocationRefs)) {
							signerType = it.eng.common.type.SignerType.CAdES_C;
						}
					}
					
				}
				
				
			}
		}catch(Exception e) {
			e.printStackTrace();
			signed = false;
			
		}
		return signed;
	}
	
	
	public SignerType isSignedType(File file) {
		boolean signed = false;
		InputStream stream = null;
		//this.file = file;
		File tmp = null;
		try {
			stream = FileUtils.openInputStream(file);
			CMSSignedDataParser parser = new CMSSignedDataParser(stream);
			signed = isSignedType(parser);
			stream.close();
			 
			//Controolo se è un base64
			if(!signed){
				throw new Exception();			
			}	
			
		}catch(Exception e) {
			//controllo se è in base64
			try {
				
				//Controllo se il file comincia per -----BEGIN
				LineIterator iterator = FileUtils.lineIterator(file);
				String firstline = iterator.nextLine();
				if(StringUtils.containsIgnoreCase(firstline,"-----BEGIN")){
					//Riscrivo il file				
					tmp = File.createTempFile("tmp", ".tmp");
					BufferedWriter writer = new BufferedWriter(new FileWriter(tmp));
					while(iterator.hasNext()){				
						String line = iterator.nextLine();
						if(!StringUtils.containsIgnoreCase(line,"-----END")){
							writer.write(line);
							writer.newLine();
							writer.flush();							
						}else{
							writer.close();							
						}
					}
					Base64InputStream streambase64 = new Base64InputStream(FileUtils.openInputStream(tmp));
					CMSSignedDataParser parser = new CMSSignedDataParser(streambase64);
					signed = isSignedType(parser);
					//setBase64(true);
					
				}else{
					Base64InputStream streambase64 = new Base64InputStream(FileUtils.openInputStream(file));
					CMSSignedDataParser parser = new CMSSignedDataParser(streambase64);
					signed = isSignedType(parser);
					//setBase64(true);	
				}
				
			}catch(Exception er) {
				signed = false;
					try {
						if(stream!=null){
							stream.close();
						}
					} catch (IOException e1) {}
			}finally{
				if(tmp!=null){
					FileUtils.deleteQuietly(tmp);
				}
				
			}
		}
		
		if(signed){
			return signerType;
		}else
			return null;
			
	}


	
}
