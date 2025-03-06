package it.eng.client.applet.util;

import it.eng.client.applet.operation.jsignpdf.HashAlgorithm;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;
import org.bouncycastle.crypto.tls.DigestAlgorithm;
import org.bouncycastle.util.encoders.Base64;

public class DigestUtil {
	
	public static String encodeDigest(byte[] digest,DigestEncID encoding)throws Exception{
		String ret=null;
		switch (encoding) {
		case HEX:
			ret=Hex.encodeHexString(digest);
			break;
		case BASE_64:
			ret=new String(Base64.encode(digest));
		default:
			throw new Exception("encoding non supportato");
			//ret=Base64.encode(digest);
		}
		return ret;
	}
	
	public static byte[] calulateDigest(File theFile,HashAlgorithm alg)throws Exception{
		 
		return calulateDigest(new FileInputStream(theFile),alg);
	}
	
	public static byte[] calulateDigest(InputStream stream,HashAlgorithm alg)throws Exception{
		byte[] ret=null;
		 
		switch (alg) {
		case SHA256:
				ret=DigestUtils.sha256( stream);
			break;
		case SHA1:
				ret=DigestUtils.sha(stream);
			break;
//		case MD_5:
//			ret=DigestUtils.md5(new FileInputStream(theFile));
//			break;
//		case CRC_32:
//			ret=calculateCRC32(theFile);
//			break;
		default:
			throw new Exception("algoritmo non supportato");
			//log.warn("uso default.. sha256");
			//ret=DigestUtils.sha256(new FileInputStream(theFile));
		}
		return ret;
	}
}
