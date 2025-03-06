package it.eng.suiteutility.mimedetector.implementations.mimeutils.detectors.util.signer;

import java.io.File;
import java.io.InputStream;
import java.util.Collection;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.bouncycastle.asn1.cms.AttributeTable;
import org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.bouncycastle.cms.CMSSignedData;
import org.bouncycastle.cms.CMSSignedDataGenerator;
import org.bouncycastle.cms.SignerInformation;
import org.bouncycastle.cms.SignerInformationStore;

/**
 * Implementa i controlli su firme di tipo CAdES.
 * Il contenuto di un file e riconosciuto se implementa le specifiche RFC5126
 * @author Stefano Zennaro
 *
 */
public class CAdESSigner extends P7MSigner {
	
	private SignerType type = null;
	
	/**
	 * Restituisce true se il contenuto del file e di tipo CMS e rispetta
	 * le seguenti condizioni:
	 * <ul>
	 * 	<li>L'algoritmo di digest deve essere SHA256</li>
	 *  <li>Il certificato di firma deve essere presente come attributo signing-certificate oppure ESS-signing-certificate-v2</li>
	 * </ul>
	 * Recupera inoltre il timestamp se presente come attributo non firmato (CAdES-T)
	 * @return boolean 
	 */
	protected boolean isSignedType(CMSSignedData cmsSignedDataInternal) {
		type = null;
		boolean signed = false;
		try {
			cmsSignedData = cmsSignedDataInternal;
			SignerInformationStore signersStore = cmsSignedData.getSignerInfos();
			Collection<SignerInformation> signers = signersStore.getSigners();
			if (signers==null || signers.isEmpty()) {
				signed = false;
			} else {
				//Controllo se l'algoritmo e di tipo SHA256 e che sia presente l'attributo contenente il certificato
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
					
					// I formati CAdES_T e CAdES_C sono piu restrittivi di CAdES_BES
					if (type == null)
						type = SignerType.CAdES_BES;
					
					//TODO Controllo da verificare
					AttributeTable unsignedTable  = signer.getUnsignedAttributes();
					if (unsignedTable!=null && unsignedTable.toHashtable().containsKey(PKCSObjectIdentifiers.id_aa_signatureTimeStampToken)) {
						type = SignerType.CAdES_T;
						//Controllo se sono presenti gli atttibuti CRL negli attributi unsigned
						if (unsignedTable.toHashtable().containsKey(PKCSObjectIdentifiers.id_aa_ets_certificateRefs) && unsignedTable.toHashtable().containsKey(PKCSObjectIdentifiers.id_aa_ets_revocationRefs)) {
							type = SignerType.CAdES_C;
						}
					}
					
				}
			}
		}catch(Exception e) {
			signed = false;
		}
		return signed;
	}
	
	

	public boolean isSignedType(File file) {
		boolean signed = false;
		InputStream stream = null;
		try {
			stream = FileUtils.openInputStream(file);
			CMSSignedData cmsSignedData = new CMSSignedData(stream);
			signed = isSignedType(cmsSignedData);
		}catch(Exception e) {
			signed = false;
		} finally {
			if (stream!=null) {
				IOUtils.closeQuietly(stream);
			}
		}
		return signed;
	}
	
	public boolean isSignedType(byte[] content) {
		boolean signed = false;
		try {
			CMSSignedData cmsSignedData = new CMSSignedData(content);
			signed = isSignedType(cmsSignedData);
		}catch(Exception e) {
			signed = false;
		}
		return signed;
	}
	
	public SignerType getFormat() {
		return type;
	}
}
