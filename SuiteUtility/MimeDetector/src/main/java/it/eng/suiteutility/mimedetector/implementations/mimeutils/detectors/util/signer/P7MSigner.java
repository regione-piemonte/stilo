package it.eng.suiteutility.mimedetector.implementations.mimeutils.detectors.util.signer;

import java.io.File;
import java.io.InputStream;
import java.util.Collection;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.bouncycastle.cms.CMSException;
import org.bouncycastle.cms.CMSSignedData;
import org.bouncycastle.cms.CMSSignedDataGenerator;
import org.bouncycastle.cms.SignerInformation;
import org.bouncycastle.cms.SignerInformationStore;

/**
 * Implementa i controlli su firme di tipo P7M.
 * Il contenuto di un file e riconosciuto se implementa le specifiche PKCS #7
 * @author Stefano Zennaro
 *
 */
public class P7MSigner extends AbstractSigner {
	
	/**
	 * Contenuto CMS
	 */
	protected CMSSignedData cmsSignedData = null;

	protected boolean isSignedType(CMSSignedData signedData) {
		boolean signed = false;
		SignerInformationStore signersStore = signedData.getSignerInfos();
		Collection<SignerInformation> signers = signersStore.getSigners();
		if (signers==null || signers.isEmpty()) {
			signed = false;
		} else {
			//Controllo se l'algoritmo e di tipo SHA1
			for (SignerInformation signer:signers) {
				if (!CMSSignedDataGenerator.DIGEST_SHA1.equals(signer.getDigestAlgOID())) {
					signed = false;
					break;
				}
				signed = true;
			}
		}
		return signed;
	}
	
	
	/**
	 * Restituisce true se il contenuto del file e di tipo CMS e
	 * l'algoritmo di digest e di tipo SHA1
	 * @return boolean 
	 */
	public boolean isSignedType(File file) {
		boolean signed = false;
		InputStream stream = null;
		try {
			stream = FileUtils.openInputStream(file);
			cmsSignedData= new CMSSignedData(stream);
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
			cmsSignedData= new CMSSignedData(content);
			signed = isSignedType(cmsSignedData);
		} catch (CMSException e) {
			signed = false;
		}
		return signed;
	}
	
	public SignerType getFormat() {
		return SignerType.P7M;
	}
	


}