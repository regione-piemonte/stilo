/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.fileOperation.clientws.DigestAlgID;
import it.eng.fileOperation.clientws.DigestEncID;

import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.zip.CRC32;
import java.util.zip.CheckedInputStream;

import org.apache.axis.encoding.Base64;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class DigestCtrl {
	
	private static Logger log = LogManager.getLogger(DigestCtrl.class);
	
	//input
	private DigestAlgID digestAlgId;
	private DigestEncID encoding;
	 
	public String operationType;

	public String execute(InputStream lInputStream) {
		DigestAlgID appliedAlg=getDigestAlgId();
		DigestEncID appliedEnc=getEncoding();
		String calculated;
		try {
			calculated = calculate(lInputStream, appliedAlg, appliedEnc);
		} catch (Exception e) {
			return null;
		}
		return calculated;
	}

	protected String calculate(InputStream lInputStream,DigestAlgID alg,DigestEncID encoding) throws Exception{
		log.debug("calulating digest algId:"+alg+" encoding:"+encoding);
		byte[] digest=calulateDigest(lInputStream, alg);
		return encodeDigest(digest, encoding);
	}
	
	private String encodeDigest(byte[] digest,DigestEncID encoding)throws Exception{
		String ret=null;
		if (encoding == DigestEncID.HEX) ret=Hex.encodeHexString(digest);
		else if (encoding == DigestEncID.BASE_64) ret=Base64.encode(digest);
		else throw new Exception("encoding non supportato");
		return ret;
	}
	
	private byte[] calulateDigest(InputStream lInputStream,DigestAlgID alg)throws Exception{
		byte[] ret=null;
		 
		if (alg == DigestAlgID.SHA_256) ret=DigestUtils.sha256(lInputStream);
		else if (alg == DigestAlgID.SHA_1) ret=DigestUtils.sha(lInputStream);
		else if (alg == DigestAlgID.MD_5) ret=DigestUtils.md5(lInputStream);
		else if (alg == DigestAlgID.CRC_32) ret=calculateCRC32(lInputStream);
		else throw new Exception("algoritmo non supportato");
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
	
	private byte[] calculateCRC32(InputStream file) throws Exception{

        
            CheckedInputStream cis = null;
            long fileSize = 0;
            
                // Computer CRC32 checksum
                cis = new CheckedInputStream(
                        file, new CRC32());

                fileSize = IOUtils.toByteArray(file).length;
               
            

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

	public void setOperationType(String operationType) {
		this.operationType = operationType;
	}

}
