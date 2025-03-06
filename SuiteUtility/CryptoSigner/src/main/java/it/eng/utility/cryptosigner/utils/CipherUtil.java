package it.eng.utility.cryptosigner.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;

public class CipherUtil {

	private static String keyString = "CDCDM2014";

	private static final Logger log = Logger.getLogger(CipherUtil.class);

	public static SecretKeySpec getKey() {
		MessageDigest sha = null;
		try {
			byte[] key = keyString.getBytes("UTF-8");
			sha = MessageDigest.getInstance("SHA-1");
			// key = sha.digest(key);
			key = Arrays.copyOf(key, 16); // use only first 128 bit
			return new SecretKeySpec(key, "AES");
		} catch (NoSuchAlgorithmException e) {
			log.error("Eccezione getKey", e);
		} catch (UnsupportedEncodingException e) {
			log.error("Eccezione getKey", e);
		}
		return null;
	}

	public static String encrypt(String strToEncrypt) {
		try {
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
			cipher.init(Cipher.ENCRYPT_MODE, getKey());
			return Base64.encodeBase64String(cipher.doFinal(strToEncrypt.getBytes("UTF-8")));
		} catch (Exception e) {
			log.error("Eccezione encrypt", e);
		}
		return null;

	}

	public static String decrypt(String strToDecrypt) {
		try {
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
			cipher.init(Cipher.DECRYPT_MODE, getKey());
			return new String(cipher.doFinal(Base64.decodeBase64(strToDecrypt)));
		} catch (Exception e) {
			log.error("Eccezione decrypt", e);
		}
		return null;
	}

	public static void main(String[] args) {
		final String strToEncrypt = "Test";

		String passwordCifrata = CipherUtil.encrypt(strToEncrypt.trim());
		System.out.println(passwordCifrata);

		String passwordDecifrata = CipherUtil.decrypt(passwordCifrata.trim());
		System.out.println(passwordDecifrata);

	}
}
