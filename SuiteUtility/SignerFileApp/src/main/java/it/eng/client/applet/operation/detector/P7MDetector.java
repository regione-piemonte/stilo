package it.eng.client.applet.operation.detector;

import it.eng.common.type.SignerType;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;

import org.apache.commons.codec.binary.Base64InputStream;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.LineIterator;
import org.apache.commons.lang.StringUtils;
import org.bouncycastle.cms.CMSException;
import org.bouncycastle.cms.CMSSignedDataGenerator;
import org.bouncycastle.cms.CMSSignedDataParser;
import org.bouncycastle.cms.SignerInformation;
import org.bouncycastle.cms.SignerInformationStore;

public class P7MDetector implements EnvelopeDetector{
	protected byte[] hash = null;
	
	protected boolean isSignedType(CMSSignedDataParser signedData) throws CMSException {
		//Resetto il signer
	 
//		cmsSignedData = null;
		
		boolean signed = false;
		try {
			signedData.getSignedContent().drain();
		}catch(Exception e) {
			throw new CMSException("Errore firma",e);
		}
		SignerInformationStore signersStore = signedData.getSignerInfos();
		signersStore = signedData.getSignerInfos();
		
		Collection<?> signers = signersStore.getSigners();
		if (signers==null || signers.isEmpty()) {
			signed = false;
		} else {
			//Controllo se l'algoritmo � di tipo SHA1
			for (Object signer:signers) {
				if(signer instanceof SignerInformation){
					if (!CMSSignedDataGenerator.DIGEST_SHA1.equals(((SignerInformation)signer).getDigestAlgOID())) {
						signed = false;
						break;
					}
					signed = true;
					//FIXME non psso farlo sullo stream è chiuso 
					hash = ((SignerInformation)signer).getContentDigest();	
				} 
			}
		}
		
		return signed;
	}
	
	
	/**
	 * Restituisce SignerType.P7M se il contenuto del file � di tipo CMS e
	 * l'algoritmo di digest � di tipo SHA1
	 */
	public SignerType isSignedType(File file) {
		boolean signed = false;
		InputStream stream = null;
		//this.file = file;
		File tmp = null;
		try {
			stream = FileUtils.openInputStream(file);	
			CMSSignedDataParser cmsSignedData = new CMSSignedDataParser(stream);
			signed = isSignedType(cmsSignedData);
			stream.close();
		}catch(Exception e) {
			try {
				//Controllo se il file comincia per -----BEGIN
				LineIterator iterator = FileUtils.lineIterator(file);
				String firstline = iterator.nextLine();
				if(StringUtils.containsIgnoreCase(firstline,"-----BEGIN")){
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
				//er.printStackTrace();
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
		} finally {
			if (stream!=null) {
				IOUtils.closeQuietly(stream);
			}
		}
		if(signed)
			return SignerType.P7M;
		else 
			return null;
	}


	@Override
	public  InputStream getContent(File file) throws Exception {
		CMSSignedDataParser parser = new CMSSignedDataParser(FileUtils.openInputStream(file));
		return parser.getSignedContent().getContentStream();
		
	}


	public byte[] getHash() {
		return hash;
	}
	
	
}
