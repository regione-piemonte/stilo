/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

// 18.12.2015 Federico Cacco
// Modificata classe di crittografia in modo da poterne
// parametrizzare il secrets

// Classe di test TestCriptazionePasswordEmailAccount

package it.eng.utility.cryptography;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;
import org.hibernate.ObjectNotFoundException;

/**
 * La classe implementa metodi di crittografia e decrittografia utilizzando
 * l'algoritmo AES
 */
public class AES {
	private static byte[] linebreak = {}; // Remove Base64 encoder default
											// linebreak
	// private static String secret = "tvnw63ufg9gh5392"; // secret key length
	// must be 16
	// private static SecretKey key;
	private static Cipher cipher;
	private static Base64 coder;
	
	private static Logger mLogger = Logger.getLogger(AES.class);

	static {
		try {
			// key = new SecretKeySpec(secret.getBytes(), "AES");
			cipher = Cipher.getInstance("AES/ECB/PKCS5Padding", "SunJCE");
			coder = new Base64(32, linebreak, true);
		} catch (Throwable t) {
			mLogger.warn(t);
		}
	}

	/**
	 * Metodo per eseguire la criptazione AES della password dell'account mail. La chiave viene letta dal parametro STRING#1 in T_PARAMETERS
	 * @param plainText Il desto da criptare
	 * @return Il testo criptato
	 * @throws Exception
	 */
	public static synchronized String encrypt(String plainText, String key) throws InvalidEncryptionKeyException {
		// Estraggo la chiave di criptazione e inizializzo i componenti per la criptazione
		try{
			SecretKey secretKey = new SecretKeySpec(key.getBytes(), "AES");
			cipher.init(Cipher.ENCRYPT_MODE, secretKey);
			// Eseguo la criptazione
			byte[] cipherText = cipher.doFinal(plainText.getBytes());
			return new String(coder.encode(cipherText));
		}catch (ObjectNotFoundException e){
			throw new InvalidEncryptionKeyException("Chiave di cifratura non trovata", e);
		}catch (Exception e){
			throw new InvalidEncryptionKeyException("Errore nella criptazione", e);
		}
	}

	/**
	 * Metodo per eseguire la decriptazione AES della password dell'account mail. La chiave viene letta dal parametro STRING#1 in T_PARAMETERS
	 * @param plainText Il desto da decriptare
	 * @return Il testo decriptato
	 * @throws Exception
	 */
	public static synchronized String decrypt(String codedText, String key) throws InvalidEncryptionKeyException {
		// Estraggo la chiave di decriptazione e inizializzo i componenti per la decriptazione
		try{
			SecretKey secretKey = new SecretKeySpec(key.getBytes(), "AES");
			byte[] encypted = coder.decode(codedText.getBytes());
			cipher.init(Cipher.DECRYPT_MODE, secretKey);
			// Eseguo la decriptazione
			byte[] decrypted = cipher.doFinal(encypted);
			return new String(decrypted);
		}catch (ObjectNotFoundException e){
			throw new InvalidEncryptionKeyException("Chiave di cifratura non trovata", e);
		}catch (Exception e){
			throw new InvalidEncryptionKeyException("Errore nella decriptazione", e);
		}
	}

}