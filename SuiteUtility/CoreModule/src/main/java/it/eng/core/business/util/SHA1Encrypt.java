package it.eng.core.business.util;

import java.security.MessageDigest;

import org.apache.log4j.Logger;

import sun.misc.BASE64Encoder;

@SuppressWarnings("restriction")
public class SHA1Encrypt {

	private static final Logger log = Logger.getLogger(SHA1Encrypt.class);

	public static String encrypt(String arg) {
		try {
			MessageDigest md = MessageDigest.getInstance("SHA");
			md.update(arg.getBytes());
			byte dig[] = md.digest();
			String s = (new BASE64Encoder()).encode(dig);
			return s;
		} catch (Exception ex) {
			log.warn("Encrypt error: ", ex);
		}
		return null;
	}
}