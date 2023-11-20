/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.security.MessageDigest;

import org.apache.log4j.Logger;

import sun.misc.BASE64Encoder;

@SuppressWarnings("restriction")
public class SHA1Encrypt {

	private static Logger log = Logger.getLogger(SHA1Encrypt.class);


    public static String encrypt(String arg) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA");
            md.update(arg.getBytes());
            byte dig[] = md.digest();
            String s = (new BASE64Encoder()).encode(dig);
            return s;
        }
        catch(Exception e) {
            log.warn(e);
        }
        return null;
    }
    
    public static void main(String[] args) {
    	String test1234 = SHA1Encrypt.encrypt("test1234");
    }
}