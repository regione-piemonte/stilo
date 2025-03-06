package it.eng.utility.crypto;

import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.codehaus.plexus.util.StringUtils;

public class CryptoUtility {
	
	private static final String DEFAULT_PSW = "Cassiopea";     // password di crittografia di default.
	private static final String DEFAULT_SEC_ALG = "Blowfish";  // algoritmo di crittografia di default
	private static final String DEFAULT_DIGEST_ALG = "SHA-1";  // algoritmo di default per creare il message digest
	
	
	private CryptoUtility() {
	    throw new IllegalStateException("Utility class");
	  }
	
	/**
	 * 
	 * @param dataToEnc  array di byte da crittografare secondo l'algoritmo.
	 * @param secAlg     algoritmo di codifica. Se non specificato utilizza quello di default.
	 * @param key        chiave di codifica. se non speficificata utilizza quella di default.
	 * @return           array di byte crittografato. 
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchPaddingException
	 * @throws InvalidKeyException
	 * @throws IllegalBlockSizeException
	 * @throws BadPaddingException
	 */
	public static byte[] encrypt(byte[] dataToEnc, String secAlg, String key) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
		String lsecAlg = StringUtils.isEmpty(secAlg) ? DEFAULT_SEC_ALG : secAlg;
		String lKey = StringUtils.isEmpty(key) ? DEFAULT_PSW : key;
		SecretKeySpec desKey = createKey(lKey,lsecAlg, retrieveKeyLenFromAlg(lsecAlg));
		Cipher encrypt = Cipher.getInstance(lsecAlg);
		encrypt.init(Cipher.ENCRYPT_MODE, desKey);
		return encrypt.doFinal(dataToEnc);
	}
	
	/**
	 * 
	 * @param dataToEnc  array di byte da crittografare secondo l'algoritmo e chiave di default.
	 * @return           array di byte crittografato. 
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchPaddingException
	 * @throws InvalidKeyException
	 * @throws IllegalBlockSizeException
	 * @throws BadPaddingException
	 */
	public static byte[] encrypt(byte[] dataToEnc) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
		return encrypt(dataToEnc,null,null);
	}
	
	
	/**
	 * 
	 * @param dataToEnc  array di byte da crittografare secondo l'algoritmo.
	 * @param secAlg     algoritmo di codifica. Se non specificato utilizza quello di default.
	 * @param key        chiave di codifica.
	 * @return           array di byte crittografato. 
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchPaddingException
	 * @throws InvalidKeyException
	 * @throws IllegalBlockSizeException
	 * @throws BadPaddingException
	 */
	public static byte[] encryptWithSecretKey(byte[] dataToEnc, String secAlg, SecretKey key) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
		Cipher encrypt = Cipher.getInstance(secAlg);
		encrypt.init(Cipher.ENCRYPT_MODE, key);
		return encrypt.doFinal(dataToEnc);
	}
	
	/**
	 * 
	 * @param dataToDec    array di byte da decrittografare secondo l'algoritmo.
	 * @param secAlg       algoritmo di decodifica. Se non specificato utilizza quello di default.
	 * @param key          chiave di decodifica. se non speficificata utilizza quella di default.
	 * @return             array di byte decrittografato.
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchPaddingException
	 * @throws InvalidKeyException
	 * @throws IllegalBlockSizeException
	 * @throws BadPaddingException
	 */
	public static byte[] decrypt(byte[] dataToDec, String secAlg, String key) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException{
		String lsecAlg = StringUtils.isEmpty(secAlg) ? DEFAULT_SEC_ALG : secAlg;
		String lKey = StringUtils.isEmpty(key) ? DEFAULT_PSW : key;
		Cipher decrypt = Cipher.getInstance(lsecAlg);
		SecretKeySpec desKey = createKey(lKey,lsecAlg, retrieveKeyLenFromAlg(lsecAlg));
		decrypt.init(Cipher.DECRYPT_MODE, desKey);
		return decrypt.doFinal(dataToDec); 
	}
	
	/**
	 * 
	 * @param dataToDec    array di byte da decrittografare secondo l'algoritmo e chiave di default.
	 * @return             array di byte decrittografato
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchPaddingException
	 * @throws InvalidKeyException
	 * @throws IllegalBlockSizeException
	 * @throws BadPaddingException
	 */
	public static byte[] decrypt(byte[] dataToDec) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException{
		return decrypt(dataToDec,null,null);
	}
	
	/**
	 * 
	 * @param dataToDec    array di byte da decrittografare secondo l'algoritmo.
	 * @param secAlg       algoritmo di decodifica.
	 * @param key          chiave di decodifica.
	 * @return             array di byte decrittografato
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchPaddingException
	 * @throws InvalidKeyException
	 * @throws IllegalBlockSizeException
	 * @throws BadPaddingException
	 */
	public static byte[] decryptWithSecretKey(byte[] dataToDec, String secAlg, SecretKey key) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException{
		Cipher decrypt = Cipher.getInstance(secAlg);
		decrypt.init(Cipher.DECRYPT_MODE, key);
		return decrypt.doFinal(dataToDec); 
	}
	
	/**
	 * 
	 * @param data       stringa da crittografare secondo l'algoritmo di default.
	 * @param key        chiave di decodifica. se non specificata utilizza quella di default.
	 * @return           stringa crittografata e codificata in Base64.
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchPaddingException
	 * @throws InvalidKeyException
	 * @throws IllegalBlockSizeException
	 * @throws BadPaddingException
	 */
	public static String encrypt64FromString(String data, String key) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
		String lKey = StringUtils.isEmpty(key) ? DEFAULT_PSW : key;
		byte[] dataByte = data.getBytes();
		SecretKeySpec desKey = new SecretKeySpec(lKey.getBytes(), DEFAULT_SEC_ALG);
		Cipher encrypt = Cipher.getInstance(DEFAULT_SEC_ALG);
		encrypt.init(Cipher.ENCRYPT_MODE, desKey);
		byte[] encodeEncrypt = Base64.encodeBase64(encrypt.doFinal(dataByte)); 
		return new String(encodeEncrypt);
	}
	
	/**
	 * 
	 * @param data       stringa da crittografare secondo l'algoritmo e chiave di default.
	 * @return           stringa crittografata e codificata in Base64.
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchPaddingException
	 * @throws InvalidKeyException
	 * @throws IllegalBlockSizeException
	 * @throws BadPaddingException
	 */
	public static String encrypt64FromString(String data) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
		return encrypt64FromString(data,null);
	}
	
	
	/**
	 * 
	 * @param data      stringa in Base64 di una stringa da decrittografare secondo l'algoritmo.
	 * @param key       chiave di decodifica. se non speficificata utilizza quella di default.
	 * @return          stringa decrittografata.
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchPaddingException
	 * @throws InvalidKeyException
	 * @throws IllegalBlockSizeException
	 * @throws BadPaddingException
	 */
	public static String decrypt64FromString(String data, String key) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
		String lKey = StringUtils.isEmpty(key) ? DEFAULT_PSW : key;
		Cipher decrypt = Cipher.getInstance(DEFAULT_SEC_ALG);
		SecretKeySpec desKey = new SecretKeySpec(lKey.getBytes(), DEFAULT_SEC_ALG);
		decrypt.init(Cipher.DECRYPT_MODE, desKey);
		byte[] decryptReceived = decrypt.doFinal(Base64.decodeBase64(data.getBytes()));
		return new String(decryptReceived);
	}
	
	/**
	 * 
	 * @param data      stringa in Base64 di una stringa da decrittografare secondo l'algoritmo e chiave di default.
	 * @return          stringa decrittografata.
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchPaddingException
	 * @throws InvalidKeyException
	 * @throws IllegalBlockSizeException
	 * @throws BadPaddingException
	 */
	public static String decrypt64FromString(String data) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException{
		return decrypt64FromString(data,null);
	}
	
	
	/**
	 * 
	 * @param myKey    chiave di crittografia.
	 * @param secAlg   algoritmo di crittografia. 
	 * @param keyLen   lunghezza della chiave da considerare. 
	 * @return         ritorna la chiave segreta per l'algoritmo di crittografia selezionato, a partire da un message digest di lunghezza keyLen generato a partire dalla chiave myKey. 
	 * @throws NoSuchAlgorithmException
	 */
	public static SecretKeySpec createKey(String myKey, String secAlg, int keyLen) throws NoSuchAlgorithmException{
        MessageDigest sha = null;
        byte[] key = myKey.getBytes();
        sha = MessageDigest.getInstance(DEFAULT_DIGEST_ALG);
        key = sha.digest(key);
        key = Arrays.copyOf(key, keyLen);
        return new SecretKeySpec(key, secAlg);
    }
	
	
	/**
	 * 
	 * @param secAlg algoritmo di crittografia.
	 * @return        una secretkey generata randomicamente.
	 * @throws NoSuchAlgorithmException
	 */
	public static SecretKey createRandomKey(String secAlg) throws NoSuchAlgorithmException {
		return KeyGenerator.getInstance(secAlg).generateKey();
	}
	
	
	/**
	 * 
	 * @param secAlg algoritmo di sicurezza da utilizzare
	 * @return la lunghezza della chiave da utilizzare in base all'algoritmo in input
	 */
	private static int retrieveKeyLenFromAlg(String secAlg) {
		//TODO: aggiungere nuovi casi
		switch(secAlg) {
			case "AES":
				return 16;
			case DEFAULT_SEC_ALG:
				return 16;
			default:
				return 16;
		}
	}
	
}
