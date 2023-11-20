/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.File;
import java.io.FileInputStream;
import java.nio.ByteBuffer;
import java.util.zip.CRC32;
import java.util.zip.CheckedInputStream;

import org.apache.axis.encoding.Base64;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import it.eng.module.foutility.beans.OutputOperations;
import it.eng.module.foutility.beans.generated.AbstractInputOperationType;
import it.eng.module.foutility.beans.generated.DigestAlgID;
import it.eng.module.foutility.beans.generated.DigestEncID;
import it.eng.module.foutility.beans.generated.InputDigestType;
import it.eng.module.foutility.beans.generated.ResponseFileDigestType;
import it.eng.module.foutility.beans.generated.VerificationStatusType;
import it.eng.module.foutility.util.FileOpMessage;

public class DigestCtrl extends AbstractFileController {
	
	private static Logger log = LogManager.getLogger(DigestCtrl.class);
	
	//input
	private DigestAlgID digestAlgId;
	private DigestEncID encoding;
	 
	public String operationType;
	
	// Rapporto verifica
	public static String RAPPORTO_VERIFICA_Digest = "RAPPORTO_VERIFICA_DIGEST";
	public static String RAPPORTO_VERIFICA_Algoritmo = "RAPPORTO_VERIFICA_ALGORITMO";
	public static String RAPPORTO_VERIFICA_Encoding = "RAPPORTO_VERIFICA_ENCODING";

	@Override
	public boolean execute(InputFileBean input, AbstractInputOperationType customInput, OutputOperations output, String requestKey)  {
		boolean ret=false;
		ResponseFileDigestType response= new ResponseFileDigestType();
		
		File file = input.getInputFile();
		log.debug("Metodo execute di DigestCtrl sul file " + file );
		
		//merge default config with provided data
		InputDigestType idt=null;
		DigestAlgID appliedAlg = getDigestAlgId();
		DigestEncID appliedEnc= getEncoding();
		if(customInput instanceof InputDigestType){
			idt=((InputDigestType) customInput);
			if(idt!=null){
				appliedAlg=idt.getDigestAlgId();
				appliedEnc=idt.getEncoding();
			}
		}
		String calculated;
		try {
			calculated = calculate(file, appliedAlg, appliedEnc);
			log.debug("Digest " + calculated );
			
			output.addProperty(RAPPORTO_VERIFICA_Digest, calculated);
			output.addProperty(RAPPORTO_VERIFICA_Algoritmo, appliedAlg);
			output.addProperty(RAPPORTO_VERIFICA_Encoding, appliedEnc);
			
			response.setResult(calculated);
			response.setDigestAlgId(appliedAlg);
			response.setEncoding(appliedEnc);
			response.setVerificationStatus(VerificationStatusType.OK);
		} catch (Exception e) {
			log.fatal("fatal calculating digest",e);
			OutputOperations.addError(response, FileOpMessage.DIGEST_OP_ERROR, VerificationStatusType.KO, e.getMessage() );
		}
		output.addResult(this.getClass().getName(), response);
		return ret;
	}

	protected String calculate(File theFile,DigestAlgID alg,DigestEncID encoding) throws Exception{
		log.debug("Calcolo il digest con algId:" + alg + " encoding:" + encoding);
		byte[] digest=calulateDigest(theFile, alg);
		return encodeDigest(digest, encoding);
	}
	
	private String encodeDigest(byte[] digest,DigestEncID encoding)throws Exception{
		String ret=null;
		switch ( encoding ) {
		case HEX:
			ret=Hex.encodeHexString(digest);
			break;
		case BASE_64:
			ret=Base64.encode(digest);
			break;
		default:
			throw new Exception("encoding non supportato");
			//ret=Base64.encode(digest);
		}
		return ret;
	}
	
	private byte[] calulateDigest(File theFile,DigestAlgID alg)throws Exception{
		byte[] ret=null;
		
		FileInputStream stream = new FileInputStream( theFile );
		switch (alg) {
		case SHA_256:
				ret=DigestUtils.sha256( stream );
			break;
		case SHA_1:
				ret=DigestUtils.sha( stream);
			break;
		case MD_5:
			ret=DigestUtils.md5(stream);
			break;
		case CRC_32:
			ret=calculateCRC32(theFile);
			break;
		default:
			throw new Exception("algoritmo non supportato");
			//log.warn("uso default.. sha256");
			//ret=DigestUtils.sha256(new FileInputStream(theFile));
		}
		stream.close();
		return ret;
	}
	
	public DigestAlgID getDigestAlgId() {
		return digestAlgId;
	}


	public void setDigestAlgId(DigestAlgID digestAlgId) {
		this.digestAlgId = digestAlgId;
	}


	public DigestEncID getEncoding() {
		return encoding;
	}


	public void setEncoding(DigestEncID encoding) {
		this.encoding = encoding;
	}
	
	private byte[] calculateCRC32(File file) throws Exception{
		CheckedInputStream cis = null;
		long fileSize = 0;

		// Computer CRC32 checksum
		cis = new CheckedInputStream( new FileInputStream( file ), new CRC32());
		fileSize = file.length();

		byte[] buf = new byte[128];
		while(cis.read(buf) >= 0) {
		}

		Long checksum = cis.getChecksum().getValue();
		log.debug("crc32 long:"+checksum);

		return   ByteBuffer.allocate(8).putLong(checksum).array();

		//            ByteArrayOutputStream baos = new ByteArrayOutputStream();
		//            DataOutputStream dos = new DataOutputStream(baos);
		//            dos.writeLong(checksum);
		//            dos.close();
		//            return baos.toByteArray();
	} 

	@Override
	public String getOperationType() {
		return operationType;
	}

	public void setOperationType(String operationType) {
		this.operationType = operationType;
	}

}
