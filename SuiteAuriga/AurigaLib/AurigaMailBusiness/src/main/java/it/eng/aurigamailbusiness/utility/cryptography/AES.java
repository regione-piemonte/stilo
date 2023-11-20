/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

// 18.12.2015 Federico Cacco
// Modificata classe di crittografia in modo da poterne
// parametrizzare il secrets

// Classe di test TestCriptazionePasswordEmailAccount

package it.eng.aurigamailbusiness.utility.cryptography;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.ObjectNotFoundException;

import it.eng.aurigamailbusiness.database.dao.DaoTParameters;
import it.eng.aurigamailbusiness.database.utility.DaoFactory;
import it.eng.aurigamailbusiness.database.utility.TParametersConfigKey;

/**
 * La classe implementa metodi di crittografia e decrittografia utilizzando l'algoritmo AES
 */
public class AES {

	private AES() {
		throw new IllegalStateException("Classe di utilità");
	}

	private static byte[] linebreak = {}; // Remove Base64 encoder default
											// linebreak
	private static Cipher cipher;
	private static Base64 coder;

	private static Logger logger = LogManager.getLogger(AES.class);

	static {
		try {
			cipher = Cipher.getInstance("AES/ECB/PKCS5Padding", "SunJCE");
			coder = new Base64(32, linebreak, true);
		} catch (Exception e) {
			logger.error(e);
		}
	}

	/**
	 * Metodo per eseguire la criptazione AES della password dell'account mail. La chiave viene letta dal parametro STRING#1 in T_PARAMETERS
	 * 
	 * @param plainText
	 *            Il desto da criptare
	 * @return Il testo criptato
	 * @throws Exception
	 */
	public static synchronized String encrypt(String plainText) throws InvalidEncryptionKeyException {
		// Estraggo la chiave di criptazione e inizializzo i componenti per la criptazione
		try {
			SecretKey key = getSecretKey();
			cipher.init(Cipher.ENCRYPT_MODE, key);
			// Eseguo la criptazione
			byte[] cipherText = cipher.doFinal(plainText.getBytes());
			return new String(coder.encode(cipherText));
		} catch (ObjectNotFoundException e) {
			throw new InvalidEncryptionKeyException("Chiave di cifratura non non trovata", e);
		} catch (Exception e) {
			throw new InvalidEncryptionKeyException("Errore nella criptazione", e);
		}
	}

	/**
	 * Metodo per eseguire la decriptazione AES della password dell'account mail. La chiave viene letta dal parametro STRING#1 in T_PARAMETERS
	 * 
	 * @param plainText
	 *            Il desto da decriptare
	 * @return Il testo decriptato
	 * @throws Exception
	 */
	public static synchronized String decrypt(String codedText) throws InvalidEncryptionKeyException {
		// Estraggo la chiave di decriptazione e inizializzo i componenti per la decriptazione
		try {
			SecretKey key = getSecretKey();
			byte[] encypted = coder.decode(codedText.getBytes());
			cipher.init(Cipher.DECRYPT_MODE, key);
			// Eseguo la decriptazione
			byte[] decrypted = cipher.doFinal(encypted);
			return new String(decrypted);
		} catch (ObjectNotFoundException e) {
			throw new InvalidEncryptionKeyException("Chiave di cifratura non non trovata", e);
		} catch (Exception e) {
			throw new InvalidEncryptionKeyException("Errore nella decriptazione", e);
		}
	}

	private static SecretKey getSecretKey() throws Exception {
		DaoTParameters daoParametri = (DaoTParameters) DaoFactory.getDao(DaoTParameters.class);
		String secret = daoParametri.get(TParametersConfigKey.STRING_1.keyname()).getStrValue();
		return new SecretKeySpec(secret.getBytes(), "AES");
	}

}