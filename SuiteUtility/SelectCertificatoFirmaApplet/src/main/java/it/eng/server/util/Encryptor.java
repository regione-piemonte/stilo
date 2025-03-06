/**
 * La classe definisce e implementa i metodi utilizzati per eseguire la criptazione/decriptazione di un valore che viene fornito
 */
package it.eng.server.util;

import org.apache.commons.codec.binary.Base64;

public class Encryptor {

	// AES/CBC/PKCS5PADDING è il tipo di criptazione che si è deciso di utilizzare
	// private static String algorithm = "AES";
	// private static String key = "Bar12345Bar12345"; // 128 bit key
	// // private static String initVector = "RandomInitVector"; // 16 bytes IV
	//
	// public static String encrypt(String value) {
	//
	// try {
	// // Parametri utilizzati per l'inizializzazione del meccanismo di criptazione
	// SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");
	//
	// /*
	// * Inizializzazione dell'oggetto per eseguire la criptazione
	// */
	// Cipher cipher = Cipher.getInstance(algorithm);
	// cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
	//
	// String returnResult = new String(cipher.doFinal(value.getBytes()));
	// // returnResult = returnResult.replaceAll("\"", "#@@#");
	//
	// // Eseguo la criptazione del valore desiderato
	// return returnResult;
	// } catch (Exception ex) {
	// ex.printStackTrace();
	// }
	//
	// return null;
	// }
	//
	// public static String decrypt(String encrypted) {
	//
	// try {
	//
	// LogWriter.writeLog("encrypted: " + encrypted);
	//
	// // encrypted = encrypted.replaceAll("#@@#", "\"");
	// LogWriter.writeLog("Appena tolto il carattere speciale" + encrypted);
	//
	// // Parametri utilizzati per l'inizializzazione del meccanismo di decriptazione
	// SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");
	//
	// LogWriter.writeLog("Creata la chiave");
	// /*
	// * Inizializzazione dell'oggetto per eseguire la decriptazione
	// */
	// Cipher cipher = Cipher.getInstance(algorithm);
	// cipher.init(Cipher.DECRYPT_MODE, skeySpec);
	//
	// LogWriter.writeLog("Inzializzato il cipher");
	//
	// // Eseguo la decriptazione del valore desiderato
	// byte[] original = cipher.doFinal(encrypted.getBytes());
	// LogWriter.writeLog("Eseguito il doFinal");
	//
	// String returnResult = new String(original);
	// LogWriter.writeLog("Creata la variabile e ritorno");
	//
	// return returnResult;
	// } catch (Exception ex) {
	// ex.printStackTrace();
	// }
	//
	// return null;
	// }

	// private static String codifica(String pin) {
	// String codifiedPin = "";
	//
	// String pinBase64 = new String(Base64.encodeBase64(pin.getBytes()));
	//
	// char[] pinCharacters = pinBase64.toCharArray();
	//
	// for (char character : pinCharacters) {
	// codifiedPin += (int) character + "#";
	// }
	//
	// return codifiedPin;
	// }
	//
	// private static String decodifica(String codifiedPin) {
	// String pin = "";
	//
	// // Prelevo ogni valore all'interno della stringa separato dal delimitatore
	// String[] charBase64 = codifiedPin.split("#");
	//
	// // Trasformo ogni valore nel corrispettivo carattere
	// for (int indexChar = 0; indexChar < charBase64.length; indexChar++) {
	// pin += (char) Integer.parseInt(charBase64[indexChar]);
	// }
	//
	// return new String(Base64.decodeBase64(pin));
	// }

	/**
	 * Questo metodo codifica il valore del pin che viene passato utilizzato dapprima la codifica in base 64
	 * e, successivamente, codificando ogni valore con la rispettiva codifica ASCII
	 * @return il valore del pin codificato
	 */
	public static String codificaConZero(String pin) {
		String codifiedPin = "";

		//Richiedo la codifica in base 64 del pin
		String pinBase64 = new String(Base64.encodeBase64(pin.getBytes()));

		//Prelevo il rispettivo array di caratteri
		char[] pinCharacters = pinBase64.toCharArray();

		//Per ogni carattere
		for (char character : pinCharacters) {
			//Prendo il valore numerico del carattere
			int value = (int) character;

			//Per ogni carattere voglio una codifica a 3 numeri
			if (value < 100) {
				//Se è minore di 100 inserisco uno 0 davanti
				codifiedPin += '0';
			}//Altrimenti uso direttamente il valore
			codifiedPin += value;
		}

		return codifiedPin;
	}

	/**
	 * Metodo che decodifica il pin precedentemente codificato con il metodo codificaConZero
	 * 
	 * Viene passato come parametro il pin codificato e, dapprima si preleva ogni valore numerico, mentre successivamente
	 * si decodifica in base 64
	 */
	public static String decodificaConZero(String codifiedPin) {
		String pin = "";

		// Prelevo ogni valore all'interno della stringa costituito da 3 valori numerici
		String[] charBase64 = new String[codifiedPin.length() / 3]; // 3 è il numero di caratteri con il quale viene codificato ogni valore

		//Inserito in charBase64 ogni valore a 3 cifre
		for (int indexArray = 0; indexArray < codifiedPin.length() / 3; indexArray++) {
			charBase64[indexArray] = new String();
			charBase64[indexArray] += codifiedPin.charAt(indexArray * 3);
			charBase64[indexArray] += codifiedPin.charAt(indexArray * 3 + 1);
			charBase64[indexArray] += codifiedPin.charAt(indexArray * 3 + 2);
		}

		// Trasformo ogni valore nel corrispettivo carattere
		for (int indexChar = 0; indexChar < charBase64.length; indexChar++) {
			pin += (char) Integer.parseInt(charBase64[indexChar]);
		}

		return new String(Base64.decodeBase64(pin));
	}
}