package it.eng.client.applet.util;

import it.eng.common.LogWriter;

import it.eng.fileOperation.clientws.DigestAlgID;

import java.io.File;
import java.io.FileInputStream;
import java.nio.ByteBuffer;
import java.util.zip.CRC32;
import java.util.zip.CheckedInputStream;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;


public class DigestUtility  {

	public static String getDigest(File file, DigestAlgID algId, it.eng.fileOperation.clientws.DigestEncID encId) {
		String calculated = "";
		try {
			byte[] digest=calulateDigest( file, algId);
			calculated = encodeDigest(digest, encId);
			LogWriter.writeLog("digest encoded " + calculated);
		} catch (Exception e) {
			LogWriter.writeLog("Errore nel calcolo del digest del file" );
		}
		return calculated;
	}
	
	public static String getDigest(String string, DigestAlgID algId, it.eng.fileOperation.clientws.DigestEncID encId) {
		String calculated = "";
		try {
			byte[] digest=calulateDigest( string, algId);
			calculated = encodeDigest(digest, encId);
			LogWriter.writeLog("digest encoded " + calculated);
		} catch (Exception e) {
			LogWriter.writeLog("Errore nel calcolo del digest del file" );
		}
		return calculated;
	}

	private static String encodeDigest(byte[] digest, it.eng.fileOperation.clientws.DigestEncID encoding)throws Exception{
		String ret=null;
		switch (encoding) {
			case HEX: {
				ret=Hex.encodeHexString(digest);
				break;
			}
			case BASE_64: {
				ret=Base64.encodeBase64String(digest);
				break;
			}
			default:
				throw new Exception("encoding non supportato");
		}
		return ret;
	}

	private static byte[] calulateDigest(File theFile,DigestAlgID alg)throws Exception{
		byte[] ret=null;

		switch (alg) {
		case SHA_256:
			ret=DigestUtils.sha256(new FileInputStream(theFile));
			break;
		case SHA_1:
			ret=DigestUtils.sha(new FileInputStream(theFile));
			break;
		case MD_5:
			ret=DigestUtils.md5(new FileInputStream(theFile));
			break;
		case CRC_32:
			ret=calculateCRC32(theFile);
			break;
		default:
			throw new Exception("algoritmo non supportato");
			//log.warn("uso default.. sha256");
			//ret=DigestUtils.sha256(new FileInputStream(theFile));
		}
		return ret;
	}
	
	private static byte[] calulateDigest(String string,DigestAlgID alg)throws Exception{
		byte[] ret=null;

		switch (alg) {
		case SHA_256:
			ret=DigestUtils.sha256(string);
			break;
		case SHA_1:
			ret=DigestUtils.sha(string);
			break;
		case MD_5:
			ret=DigestUtils.md5(string);
			break;
		case CRC_32:
			throw new Exception("algoritmo non supportato");
		default:
			throw new Exception("algoritmo non supportato");
			//log.warn("uso default.. sha256");
			//ret=DigestUtils.sha256(new FileInputStream(theFile));
		}
		return ret;
	}

	private static byte[] calculateCRC32(File file) throws Exception{
		CheckedInputStream cis = null;
		// Computer CRC32 checksum
		cis = new CheckedInputStream( new FileInputStream(file), new CRC32() );

		byte[] buf = new byte[128];
		while(cis.read(buf) >= 0) {
		}

		Long checksum = cis.getChecksum().getValue();
		LogWriter.writeLog("crc32 long:"+checksum);

		return   ByteBuffer.allocate(8).putLong(checksum).array();
	} 

}
